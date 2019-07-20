package com.stwalkerster.android.apps.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {
    LoginButton loginButtons;
    CallbackManager callbackManager;
    private FirebaseAuth myAuth;
    EditText txtForgot;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        myAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        loginButtons = findViewById(R.id.login_button);
        next = findViewById(R.id.bNext);
        txtForgot = findViewById(R.id.txtForgot);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        String value = txtForgot.getText().toString().trim();

                        if(TextUtils.isEmpty(value)){
                            txtForgot.setError("Enter Valid Email or PhoneNo");
                            txtForgot.setEnabled(true);
                        }

                        for(int i = 0;i<value.length();i++){
                                    if(value.charAt(i) == '@'){
                                       mailForgot(value);
                                        return;
                                    }

                        }
                phoneNoForgot(value);
            }
        });

        //Login through facebook
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // On success action
                        startActivity(new Intent(forgotPassword.this,MainActivity.class));
                        Toast.makeText(forgotPassword.this, "Hogya", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        //On cancel action
                        Toast.makeText(forgotPassword.this, "Cancle kr dia", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // On error action
                        Toast.makeText(forgotPassword.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


    //Forgot Mail
    private void mailForgot(String mail){

        myAuth.getInstance().sendPasswordResetEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(forgotPassword.this, "Reset Passowrd Sent to Mail", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(forgotPassword.this,login.class));

                        }
                    }
                });

    }

    //Forgot through phoneNo
    private void phoneNoForgot(String phoneno){

           String PhoneNo = "+" + "91" + phoneno;

            Intent i = new Intent(forgotPassword.this, otpScreen.class);
            i.putExtra("Number", PhoneNo);

            startActivity(i);
    }


}
