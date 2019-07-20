package com.stwalkerster.android.apps.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.LinearLayout;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    LoginButton loginButton;
    CallbackManager callbackManager;
    private FirebaseAuth myAuth;
    EditText signInEmail,signInPassword;
    Button bSignIn,bSignUp;
    String mails,passwords;
    LinearLayout forgotP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        //Set up ids
        loginButton = findViewById(R.id.login_button);
        signInEmail = findViewById(R.id.signInEmail);
        signInPassword = findViewById(R.id.signInPassword);
        bSignIn = findViewById(R.id.bSignIn);
        bSignUp = findViewById(R.id.bsignUp);
        forgotP = findViewById(R.id.forgotPassowrd);

        //login Process
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fetching Email & Password
                mails = signInEmail.getText().toString();
                passwords = signInPassword.getText().toString();

                if(TextUtils.isEmpty(mails)){
                    signInEmail.setError("Enter Valid Email");
                    signInEmail.setEnabled(true);
                }
                else if(TextUtils.isEmpty(passwords)){
                    signInPassword.setError("Enter Valid Password");
                    signInPassword.setEnabled(true);
                }
                else{
                SignInWithMail(mails,passwords);}
            }
        });

        //Open forgot passowrd activity
        forgotP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(login.this,forgotPassword.class));
            }
        });

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,SignUp.class));
            }
        });





        //Login with Facebook
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // On success action
                        startActivity(new Intent(login.this,MainActivity.class));
                        Toast.makeText(login.this, "Hogya", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                       //On cancel action
                        Toast.makeText(login.this, "Cancle kr dia", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // On error action
                        Toast.makeText(login.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void SignInWithMail(String mail,String password){

        myAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(login.this, "LogIN", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(login.this,MainActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }


}
