package com.training.cst.quanlytienantrua.UserInterface.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.training.cst.quanlytienantrua.DataManager.Object.Account;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.R;

public class ActivityLogin extends AppCompatActivity {
    static ActivityLogin mActivityLogin;
    private Button btnSignin, btnSignup, btnSigninInSignup;
    private EditText etUsernameSignin, etPasswordSignin;
    private EditText etUsernameSignup, etPasswordSignup, etRePasswordSingup;
    private DatabaseUser mDatabaseUser;
    public static final String TAG = "ActivityLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mActivityLogin = this;
        btnSignin = (Button) findViewById(R.id.activity_login_btn_signin);
        btnSignup = (Button) findViewById(R.id.activity_login_btn_signup);
        btnSigninInSignup = (Button) findViewById(R.id.activity_login__signup_btn_signin);
        etUsernameSignin = (EditText) findViewById(R.id.activity_login_ed_name);
        etPasswordSignin = (EditText) findViewById(R.id.activity_login_ed_pass);
        mDatabaseUser = new DatabaseUser(getApplicationContext());
        // Thuc hien dang nhap
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernamesignin = etUsernameSignin.getText().toString();
                String passworssignin = etPasswordSignin.getText().toString();
                if (mDatabaseUser.getRegister(usernamesignin).equals(passworssignin)) {
                    Toast.makeText(ActivityLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ActivityLogin.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    etUsernameSignin.setText("");
                    etPasswordSignin.setText("");
                    Toast.makeText(ActivityLogin.this, "Account k hop le", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Thuc hien dang ki account
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_login_signup);
                btnSigninInSignup = (Button) findViewById(R.id.activity_login__signup_btn_signin);
                etUsernameSignup = (EditText) findViewById(R.id.activity_login_signup_ed_name);
                etPasswordSignup = (EditText) findViewById(R.id.activity_login_signup_ed_pass);
                etRePasswordSingup = (EditText) findViewById(R.id.activity_login_signup_ed_repass);

                btnSigninInSignup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String username = etUsernameSignup.getText().toString();
                        final String password = etPasswordSignup.getText().toString();
                        final String repassword = etRePasswordSingup.getText().toString();
                        final int countUser = mDatabaseUser.checkAccount(mDatabaseUser.COLUMN_USERNAME + "= ?", new String[]{username});
                        if (countUser < 1) {
                            etPasswordSignup.setText("");
                            etRePasswordSingup.setText("");
                            etUsernameSignup.setText("");
                            mDatabaseUser.insertAccount(new Account(username, password, repassword));
                            Toast.makeText(ActivityLogin.this, "Create Successful", Toast.LENGTH_SHORT).show();
                            setContentView(R.layout.activity_login);
                            newInstance().recreate();

                        } else {
                            etPasswordSignup.setText("");
                            etRePasswordSingup.setText("");
                            etUsernameSignup.setText("");
                            Toast.makeText(ActivityLogin.this, "Account exits", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public static ActivityLogin newInstance() {
        return mActivityLogin;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }
}
