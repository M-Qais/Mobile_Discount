package hamza.m.mobile_discount;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;

    private TextView welcm_txt;
    private Button Lg_btn;

    //firebase initialization

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CircleImageView circleImageView;

    //firebase database initialiazation of fields
    DatabaseReference mUserDatabase;

    StorageReference mStorageRef;

    StorageReference mChildStorage;

    //IMAGE HOLD URI
    Uri imageHoldUri = null;

    //PROGRESS DIALOG
    ProgressDialog mProgress;

    private EditText mName;
    Context mcontext = ProfileActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //for font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/CaviarDreams.ttf").
                        setFontAttrId(R.attr.fontPath).
                        build());


        //

//        welcm_txt = (TextView)findViewById(R.id.welcm_profile);
//        Lg_btn = (Button)findViewById(R.id.logout_profile);
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        mName = (EditText) findViewById(R.id.name_profiles);
        Lg_btn = (Button) findViewById(R.id.done_p_image);

        //assigning insatance to firebase
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               //check user

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    finish();
                    Intent yesMove = new Intent(ProfileActivity.this,HomeActivity.class);
//                    yesMove.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(yesMove);

                }
            }
        };


        Lg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*mAuth.signOut();
                sendToAuth();*/

                //logic for saving profile
                saveUserProfile();

            }
        });

        //userimageview listener

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logic for picking image
                profilePicSelection();
            }
        });

        //database instance
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(ProfileActivity.this);
    }

    //methods for image and saving profile** no hustle ** //

    private void saveUserProfile() {

        final String username;
        username = mName.getText().toString().trim();
        if( !TextUtils.isEmpty(username))
        {
            if( imageHoldUri != null )
            {
//                mProgress.setTitle("Saving Profile");
//                mProgress.setMessage(mcontext.getResource().getString(R.string.ats_unavailable_service));
                mProgress.setTitle(mcontext.getResources().getString(R.string.title));
                mProgress.setMessage(mcontext.getResources().getString(R.string.message));

//                mProgress.setMessage("Please wait....");
                mProgress.show();
                mChildStorage = mStorageRef.child("User_Profile").child(imageHoldUri.getLastPathSegment());
                String profilePicUrl = imageHoldUri.getLastPathSegment();
                mChildStorage.putFile(imageHoldUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

//                        final Uri imageUrl = taskSnapshot.getDownloadUrl();
//                        Task<Uri> u = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                        mChildStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                mUserDatabase.child("username").setValue(username);
                                mUserDatabase.child("userid").setValue(mAuth.getCurrentUser().getUid());
                                mUserDatabase.child("imageurl").setValue(uri.toString());

                                mProgress.dismiss();

                                finish();
                                Intent moveToHome = new Intent(ProfileActivity.this, HomeActivity.class);
                                moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(moveToHome);

                            }
                        });

                    }
                });
            }else
            {

                Toast.makeText(ProfileActivity.this, "Please select the profile pic", Toast.LENGTH_LONG).show();

            }

        }else
        {

            Toast.makeText(ProfileActivity.this, "Please enter username", Toast.LENGTH_LONG).show();

        }

    }

    private void profilePicSelection() {


        //DISPLAY DIALOG TO CHOOSE CAMERA OR GALLERY

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");

        //SET ITEMS AND THERE LISTENERS
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void cameraIntent() {

        //CHOOSE CAMERA
        Log.d("gola", "entered here");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {

        //CHOOSE IMAGE FROM GALLERY
        Log.d("gola", "entered here");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //SAVE URI FROM GALLERY
        if(requestCode == SELECT_FILE && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }else if ( requestCode == REQUEST_CAMERA && resultCode == RESULT_OK ){
            //SAVE URI FROM CAMERA

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }


        //image crop library code
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageHoldUri = result.getUri();

                circleImageView.setImageURI(imageHoldUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }



//    thats it


    private void sendToAuth() {

        Intent authIntent = new Intent(ProfileActivity.this,VerifyScreen.class);
        startActivity(authIntent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {

            Intent authintent = new Intent(ProfileActivity.this,VerifyScreen.class);
            startActivity(authintent);
            finish();
        }

    }
}
