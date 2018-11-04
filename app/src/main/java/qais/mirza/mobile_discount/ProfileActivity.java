package qais.mirza.mobile_discount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TextView welcm_txt;
    private Button Lg_btn;
    private FirebaseAuth mAuth;
    private CircleImageView circleImageView;
    private EditText mName;
    private Button done_btn_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        welcm_txt = (TextView)findViewById(R.id.welcm_profile);
//        Lg_btn = (Button)findViewById(R.id.logout_profile);
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        mName = (EditText) findViewById(R.id.name_profiles);
        done_btn_profile = (Button) findViewById(R.id.next_profile_btn);
        mAuth = FirebaseAuth.getInstance();


        Lg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                sendToAuth();
            }
        });
    }

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
