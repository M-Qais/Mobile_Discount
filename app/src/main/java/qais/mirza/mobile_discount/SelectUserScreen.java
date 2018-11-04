package qais.mirza.mobile_discount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SelectUserScreen extends AppCompatActivity {

    private CardView cont_user;
    private CardView cont_shopkeeper;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_screen);

        cont_user = (CardView) findViewById(R.id.cont_as_user);
        cont_shopkeeper = (CardView) findViewById(R.id.cont_as_shopkeeper);
        mAuth = FirebaseAuth.getInstance();

        cont_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent userintent = new Intent(SelectUserScreen.this, VerifyScreen.class);
                userintent.putExtra("userType", "user");
                startActivity(userintent);

            }
        });

        cont_shopkeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent userintent = new Intent(SelectUserScreen.this, VerifyScreen.class);
                userintent.putExtra("userType", "shopkeeper");
                startActivity(userintent);

            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {

            Intent authintent = new Intent(SelectUserScreen.this,VerifyScreen.class);
            startActivity(authintent);
            finish();
        }

    }
}
