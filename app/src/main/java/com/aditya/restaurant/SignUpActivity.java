package com.aditya.restaurant;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class SignUpActivity extends AppCompatActivity
{
    EditText etUsername, etEmail, etPassword, etMobile;        // Enter Username
    CheckBox chkSignupShowPassword;
    Button btnSignUp;           // Sending data to Cognito for registering new user

    String userName, email, password, mobile;

    DBController db;
    //--------- To Save Value In SharedPreference -----------
    public static final String PREF_NAME = "PEOPLE_PREFERENCES";
    SharedPreferences myprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db=new DBController(this);
       // chkSignupShowPassword = findViewById(R.id.chk_signup_show_password_icon);

        etEmail = findViewById(R.id.edt_email);
        etUsername = findViewById(R.id.edt_username);
        etPassword = findViewById(R.id.edt_password);
        etMobile = findViewById(R.id.edt_mobilenumber);

        btnSignUp = findViewById(R.id.btn_signup);


       /* chkSignupShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean value) {
                if (value)
                {
                    // Show Password
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    // etRepeatPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    // Hide Password
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //  etRepeatPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });*/
        initViewComponents();
    }

    private void initViewComponents()
    {
        btnSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {

                    String MobilePattern = "[0-9]{10}";
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                    email     =  etEmail.getText().toString().trim();
                    userName =  etUsername.getText().toString().trim();
                    mobile    =  etMobile.getText().toString().trim();
                    password  =  etPassword.getText().toString().trim();


                    // check if any of the fields are vaccant
                    if(userName.equals("") || userName== null)
                    {
                        Toast.makeText(getApplicationContext(), "Please Enter userName", Toast.LENGTH_LONG).show();
                    }
                    else if(!email.matches(emailPattern))
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter Valid Email Address",Toast.LENGTH_SHORT).show();

                    }

                    else if(!mobile.matches(MobilePattern))
                    {
                        Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();

                    }
                    else if(password.equals("") )
                    {
                        Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_LONG).show();
                    }

                    else
                    {
                        signUpPost();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void signUpPost()
    {

        db.insertUserCredential(email, userName, mobile, password);

        myprefs=getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor=myprefs.edit();
        prefsEditor.putString("email", email);
        prefsEditor.putString("password", password);
        prefsEditor.putString("UserName", userName);
        prefsEditor.putString("Mobile", mobile);
        prefsEditor.commit();

        Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        clear();

    }

    //For Clear All Fields
    public void clear()
    {
        etUsername.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etMobile.setText("");
    }

}
