package com.stwalkerster.android.apps.instaclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class SignUp extends AppCompatActivity {
Button EmailAuth,PhoneAuth;

//To Load a fragment
    private boolean loadFarg(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragments,fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EmailAuth = findViewById(R.id.EmailAuth);
        PhoneAuth = findViewById(R.id.PhoneAuth);

        EmailAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuth.setTextColor(getApplication().getResources().getColor(R.color.grey));
                EmailAuth.setTextColor(getApplication().getResources().getColor(R.color.black));
                PhoneAuth.setBackgroundResource(R.drawable.onoffboder);
                EmailAuth.setBackgroundResource(R.drawable.buttonboder);


                Fragment fragment = null;
                fragment = new EmailAuth();
                loadFarg(fragment);
            }
        });


        PhoneAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuth.setTextColor(getApplication().getResources().getColor(R.color.black));
                EmailAuth.setTextColor(getApplication().getResources().getColor(R.color.grey));
                PhoneAuth.setBackgroundResource(R.drawable.buttonboder);
                EmailAuth.setBackgroundResource(R.drawable.onoffboder);
                Fragment fragment = null;
                fragment = new phoneAuth();
                loadFarg(fragment);

            }
        });



        loadFarg(new phoneAuth());
    }
}
