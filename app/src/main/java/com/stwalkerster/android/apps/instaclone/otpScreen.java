package com.stwalkerster.android.apps.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpScreen extends AppCompatActivity {
    String verificationId;
    EditText otps;
    private FirebaseAuth myAuth;
    Button phoneLogin;
    String mOtp;
    String no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);
        myAuth = FirebaseAuth.getInstance();

        no = getIntent().getStringExtra("Number");
        sendVerificationCode(no);
        otps = findViewById(R.id.otpCome);
        phoneLogin = findViewById(R.id.bNextss);

        phoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOtp = otps.getText().toString().trim();
                sendVerificationCode(no);
            }
        });


    }

    //Method for send berification code
    public void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60, //validation of ssec
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    //Verification process
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if(code != null){
                otps.setText(code);
                verifyCode(code);
            }
            else if(mOtp != null){
                otps.setText(mOtp);
                verifyCode(mOtp);
            }
        }



        @Override
        public void onVerificationFailed(@org.jetbrains.annotations.NotNull FirebaseException e) {
            Toast.makeText(otpScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();


        }


    };

    //Verify code
    public void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);

        signInWithCredential(credential);

    }

    //Sign in with phone no using Firebase
    public void signInWithCredential(PhoneAuthCredential credential){
        myAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(otpScreen.this,MainActivity.class));
                            Toast.makeText(otpScreen.this, "Login successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(otpScreen.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
