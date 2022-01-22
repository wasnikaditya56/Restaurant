package com.aditya.restaurant;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.StringTokenizer;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity
{
    TextView txtNotAccount, txtSignupForgetPassword, tvAppVersion;     // For creating account
    //TextView txtForgetPass;     // For retrieving password
    Button btnLogin, btnSignUp;            // Button for Login
    EditText etUsername, etPassword;
    String version = "", Email, deviceImageFlag, EMail, Password, email, password;
    CheckBox chkShowPassword;
    DBController db;
    String[] str2;


    //--------- To Save Value In SharedPreference -----------
    public static final String PREF_NAME = "PEOPLE_PREFERENCES";
    SharedPreferences myprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db=new DBController(this);

        //--------- To Save Value In SharedPreference -----------
        try
        {
            SharedPreferences shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            Email = (shared.getString("email", ""));

            System.out.println("Email::: "+Email);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_logsignin);
        etUsername = findViewById(R.id.edt_logusername);
        etPassword = findViewById(R.id.edt_logpassword);
       // txtSignupForgetPassword = findViewById(R.id.txt_signup_forget_password);




     /*   chkShowPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (etPassword.getTransformationMethod().getClass().getSimpleName() .equals("PasswordTransformationMethod"))
                {
                    etPassword.setTransformationMethod(new SingleLineTransformationMethod());
                }
                else
                {
                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
                etPassword.setSelection(etPassword.getText().length());
            }
        });*/


        initViewComponents();
       // forgetPasswordMethod();
    }

   /* private void forgetPasswordMethod()
    {
        txtSignupForgetPassword = findViewById(R.id.txt_signup_forget_password);

        txtSignupForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
    }*/

    private void initViewComponents()
    {
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                 email = etUsername.getText().toString().trim();
                 password = etPassword.getText().toString().trim();


                if(email.isEmpty() || email.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Please enter email ", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty() || password.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Please enter password ", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    try
                    {

                        String myData = db.getUserCredential();

                        System.out.println("myData::: "+myData);

                        String[] myLocalData = myData.split("#:#:#");
                        deviceImageFlag = myLocalData[0];
                        String deviceImageFlagCount2 = myLocalData[1];
                        int deviceImageFlagCount = Integer.parseInt(deviceImageFlagCount2);

                        System.out.println("deviceImageFlag::: "+deviceImageFlag);
                        System.out.println("deviceImageFlagCount::: "+deviceImageFlagCount);

                        for (int i = 1; i <= deviceImageFlagCount; i++)
                        {
                            //--------To split data-------------
                            str2 = deviceImageFlag.split("\n");

                            StringTokenizer stringtokenizer2 = new StringTokenizer(str2[i], ">");
                            while (stringtokenizer2.hasMoreElements())
                            {
                                EMail = stringtokenizer2.nextElement().toString();
                                Password = stringtokenizer2.nextElement().toString();
                                System.out.println("EMail:::: "+EMail);
                                System.out.println("Passwod:::: "+Password);

                            }
                        }

                        if((EMail.equalsIgnoreCase(email)) && (Password.equalsIgnoreCase(password)))
                        {
                            Toast.makeText(LoginActivity.this, " Login Successfull", Toast.LENGTH_LONG).show();
                            //  Intent ii=new Intent(LoginActivity.this,MainActivity.class);
                            Intent ii=new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(ii);
                            sharedPref();
                            clear();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Please Do Sign up First !!", Toast.LENGTH_LONG).show();
                        }

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(LoginActivity.this, "Please Do Sign up First !!", Toast.LENGTH_LONG).show();
                    }

                }



            }
        });
    }

    //For Clear All Fields
    public void clear()
    {
        etUsername.setText("");
        etPassword.setText("");
    }

    public void sharedPref()
    {
        SharedPreferences sp=getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("logged", true); // set it to false when the user is logged out
        editor.commit();
    }


}
