package qais.mirza.mobile_discount;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
/*this project is created by nabeeel and qais */
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;



public class VerifyScreen extends AppCompatActivity {

    private CountryCodePicker codePicker;
    private TextView countryName;
    private EditText numberET;
    private LinearLayout p_linearlayout;
    private LinearLayout v_linearlayout;
    private EditText verify_code;
    private ProgressBar p_progressbar;
    private ProgressBar v_progressbar;
    private Button verify_btnn;
    private FirebaseAuth mAuth;
    private TextView authent_eror;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private int btn_type = 0;

    String regexStr = "^[+][0-9]{10,13}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_screen);

        codePicker = (CountryCodePicker) findViewById(R.id.countryPicker);
        countryName = (TextView) findViewById(R.id.countryName);
        numberET = (EditText) findViewById(R.id.number_et);
        p_linearlayout=(LinearLayout)findViewById(R.id.phn_linear_layout);
        v_linearlayout=(LinearLayout)findViewById(R.id.verify_linear_layout);
        verify_code=(EditText)findViewById(R.id.verifcation_code);
        p_progressbar=(ProgressBar) findViewById(R.id.phone_number_progressbar);
        v_progressbar=(ProgressBar) findViewById(R.id.verification_code_bar);
        verify_btnn = (Button) findViewById(R.id.next_profile_btn);
        authent_eror = (TextView) findViewById(R.id.error_authent);

        //firebase auth
        mAuth = FirebaseAuth.getInstance();

        codePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryName.setText(codePicker.getSelectedCountryName());
            }
        });

        verify_btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn_type == 0) {
                    p_progressbar.setVisibility(View.VISIBLE);
                    numberET.setEnabled(false);
                    verify_btnn.setEnabled(false);


                    String number = codePicker.getSelectedCountryCodeWithPlus() + numberET.getText().toString();

                    if (number.length() < 13 || number.length() > 13 || number.matches(regexStr) == false) {
                        Toast.makeText(VerifyScreen.this, "Please enter " + "\n" + " valid phone number", Toast.LENGTH_SHORT).show();
                        // am_checked=0;
                    } else {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                number,
                                30,
                                TimeUnit.SECONDS,
                                VerifyScreen.this,
                                mcallback


                        );

                    }
                } else
                {
                    verify_btnn.setEnabled(false);
                    v_progressbar.setVisibility(View.VISIBLE);

                    String verificationCode = verify_code.getText().toString();
                    PhoneAuthCredential credentials = PhoneAuthProvider.getCredential(mVerificationId,verificationCode);
                    signInWithPhoneAuthCredential(credentials);
                }

            }
        });

        mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
               signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                authent_eror.setText("there was some error in Verification.");
                authent_eror.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
//                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                btn_type =1;
                p_progressbar.setVisibility(View.INVISIBLE);
                v_linearlayout.setVisibility(View.VISIBLE);
                verify_btnn.setText("Verify Code");

                verify_btnn.setEnabled(true);


                // ...
            }
        };


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Intent intentforsucess = new Intent(VerifyScreen.this,ProfileActivity.class);
                            startActivity(intentforsucess);
                            // ...
                        } else {
                            authent_eror.setText("there was some error in logging in.");
                            authent_eror.setVisibility(View.VISIBLE);
                            // Sign in failed, display a message and update the UI
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                            }
                        }
                    }
                });
    }


}
