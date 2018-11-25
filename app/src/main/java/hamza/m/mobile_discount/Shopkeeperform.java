package hamza.m.mobile_discount;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import hamza.m.Model.ShopkeeperData;

public class Shopkeeperform extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;
    EditText p_name, p_desc, p_price, p_discount, p_shop;
    Button saveformdata;
    RelativeLayout parentlayout;
    CircleImageView productUploadImage;
    Uri imageHoldUri = null;
    Spinner p_spinner;

    //initializing shopkeperdata class
    ShopkeeperData shopkeeperData;

    //firebase initialization
    FirebaseDatabase shopkeeperdatabse;
    DatabaseReference db_sk_reference;
    StorageReference mStorageRef;

    ProgressDialog imageproductdialog;

    StorageReference mChildStorage;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperform);

        sharedPreferences = getSharedPreferences("location", MODE_PRIVATE);

        //initializing the fields here

        p_name = (EditText) findViewById(R.id.product_name_sk);
        p_spinner = (Spinner) findViewById(R.id.Product_type_sk);
        p_desc = (EditText) findViewById(R.id.product_description_sk);
        p_price = (EditText) findViewById(R.id.product_price_sk);
        p_discount = (EditText) findViewById(R.id.product_disocunt_sk);
        p_shop = (EditText) findViewById(R.id.shop_name);
        productUploadImage = (CircleImageView) findViewById(R.id.product_image);


        saveformdata = (Button) findViewById(R.id.saveformsk_btn);
        parentlayout = (RelativeLayout) findViewById(R.id.relativelayoutOfForm);
//        shopkeeperData = new ShopkeeperData();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        shopkeeperdatabse = FirebaseDatabase.getInstance();
        db_sk_reference = shopkeeperdatabse.getReference("discount-mobile");


        saveformdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  db_sk_reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        getValues();
                        //passing object to the firbase for storage
                        String key =db_sk_reference.push().getKey();
                        db_sk_reference.child("products_data_shopkeeper").setValue(shopkeeperData);
                        Snackbar.make(parentlayout,"Product Inserted Successfully",Snackbar.LENGTH_LONG)

                                .setActionTextColor(Color.GREEN)
                                .show();
                        p_name.setText("");
                        p_desc.setText("");
                        p_type.setText("");
                        p_price.setText("");
                        p_discount.setText("");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
//              getValues();
//                String name = p_name.getText().toString();
//                String description = p_desc.getText().toString();
//                String type = p_type.getText().toString();
//                String Price = p_price.getText().toString();
//                String discount = p_discount.getText().toString();
////                String profilePicUrl = imageHoldUri.getLastPathSegment();
//                String image_p = productUploadImage.toString();
//
//
//
//
//              String key = db_sk_reference.push().getKey();
//              ShopkeeperData shopkeeperData = new ShopkeeperData();
//              shopkeeperData.setpName(name);
//              shopkeeperData.setpDesc(description);
//              shopkeeperData.setpType(type);
//              shopkeeperData.setpPrice(Price);
//              shopkeeperData.setPdiscount(discount);
//              shopkeeperData.setpImage(image_p);
//
//              db_sk_reference.child(key).setValue(shopkeeperData);

                saveUserProfile();

//                finish();


            }
        });

        productUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePicSelection();
            }
        });

        imageproductdialog = new ProgressDialog(Shopkeeperform.this);
    }

    /*private void getValues()
    {
       shopkeeperData.setpName(p_name.getText().toString());
       shopkeeperData.setpType(p_type.getText().toString());
       shopkeeperData.setpDesc(p_desc.getText().toString());
       shopkeeperData.setpPrice(p_price.getText().toString());
       shopkeeperData.setPdiscount(p_discount.getText().toString());

    }*/

//save form image

    private void saveUserProfile() {

        if (imageHoldUri != null) {
            imageproductdialog.setMessage("Please Wait... ");
            imageproductdialog.setCancelable(false);
            imageproductdialog.setCanceledOnTouchOutside(false);

//                mProgress.setMessage("Please wait....");
            imageproductdialog.show();
            mChildStorage = mStorageRef.child("shopkeeper_data").child(imageHoldUri.getLastPathSegment());
            mChildStorage.putFile(imageHoldUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

//                        final Uri imageUrl = taskSnapshot.getDownloadUrl();
//                        Task<Uri> u = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    mChildStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String name = p_name.getText().toString();
                            String description = p_desc.getText().toString();
                            String type = p_spinner.getSelectedItem().toString();
                            String Price = p_price.getText().toString();
                            String discount = p_discount.getText().toString();
                            String shop = p_shop.getText().toString();
//                String profilePicUrl = imageHoldUri.getLastPathSegment();
                            String image_p = productUploadImage.toString();

                            String key = db_sk_reference.push().getKey();
                            ShopkeeperData shopkeeperData = new ShopkeeperData();
                            shopkeeperData.setpName(name);
                            shopkeeperData.setpDesc(description);
                            shopkeeperData.setpType(type);
                            shopkeeperData.setpPrice(Price);
                            shopkeeperData.setPdiscount(discount);
                            shopkeeperData.setpImage(uri.toString());
                            shopkeeperData.setpShop(shop);
                            shopkeeperData.setLat(sharedPreferences.getString("lat", ""));
                            shopkeeperData.setLng(sharedPreferences.getString("lng", ""));

                            db_sk_reference.child(key).setValue(shopkeeperData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Snackbar.make(parentlayout, "Product Inserted Successfully ", Snackbar.LENGTH_LONG)

                                            .setActionTextColor(Color.GREEN)
                                            .show();

                                    p_name.setText("");
                                    p_desc.setText("");
                                    p_spinner.setAdapter(null);
                                    p_price.setText("");
                                    p_discount.setText("");

                                    finish();
                                }
                            });
                        }
                    });

                }
            });
        } else {

            Toast.makeText(Shopkeeperform.this, "Please select the product pic", Toast.LENGTH_LONG).show();

        }


    }
    //


    //image view click .....


    private void profilePicSelection() {


        //DISPLAY DIALOG TO CHOOSE CAMERA OR GALLERY

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Shopkeeperform.this);
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
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            //SAVE URI FROM CAMERA

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        }


        //image crop library code
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageHoldUri = result.getUri();

                productUploadImage.setImageURI(imageHoldUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    Random r = new Random();
//    double lat = (r.nextDouble((double)((31.400640-31.400625)*10+1))+31.400625*10)/10.0;


    ///image of product...

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
