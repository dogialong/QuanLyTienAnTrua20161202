package com.training.cst.quanlytienantrua.UserInterface.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.training.cst.quanlytienantrua.DataManager.Object.Account;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.Helper.Contants;
import com.training.cst.quanlytienantrua.R;

import java.util.regex.Matcher;

public class ActivityLogin extends AppCompatActivity {
    static ActivityLogin mActivityLogin;
    private Button btnSignin, btnSignup, btnSigninInSignup;
    private EditText etUsernameSignin, etPasswordSignin;
    private EditText etUsernameSignup, etPasswordSignup, etRePasswordSingup;
    private DatabaseUser mDatabaseUser;
    public static final String TAG = "ActivityLogin";
    boolean doubleBackToExitPressedOnce = false;

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
                } else if (usernamesignin.equals("") && passworssignin.equals("")
                        || mDatabaseUser.checkAccount(
                        mDatabaseUser.COLUMN_USERNAME + "=?"
                        , new String[]{usernamesignin})>0 && passworssignin.equals("")) {
                    etUsernameSignin.setText("");
                    etPasswordSignin.setText("");
                    Toast.makeText(ActivityLogin.this, R.string.missing_info, Toast.LENGTH_SHORT).show();
                } else if (mDatabaseUser.getRegisterPass(passworssignin) == 1){
                    etUsernameSignin.setText("");
                    etPasswordSignin.setText("");
                    Toast.makeText(ActivityLogin.this, R.string.user_wrong, Toast.LENGTH_SHORT).show();
                } else if (!mDatabaseUser.getRegister(usernamesignin).equals(passworssignin))  {
                    etUsernameSignin.setText("");
                    etPasswordSignin.setText("");
                    Toast.makeText(ActivityLogin.this, R.string.pass_incorrect, Toast.LENGTH_SHORT).show();
                } else {
                    etUsernameSignin.setText("");
                    etPasswordSignin.setText("");
                    Toast.makeText(ActivityLogin.this, R.string.error_create_person, Toast.LENGTH_SHORT).show();
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
                        Matcher matcher = Contants.PATTERN.matcher(username);
                        Matcher matcher1 = Contants.PATTERN.matcher(password);
                        Matcher matcher2 = Contants.PATTERN.matcher(repassword);
                        final int countUser = mDatabaseUser.checkAccount(mDatabaseUser.COLUMN_USERNAME + "= ?", new String[]{username});
                        if (countUser < 1 && matcher.matches()
                                && matcher1.matches() && matcher2.matches()
                                && password.equals(repassword)) {
                            mDatabaseUser.insertAccount(new Account(username, password, repassword));
                            Toast.makeText(ActivityLogin.this, "Create Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (username.equals("") || password.equals("") || repassword.equals("")) {
                            reset();
                            Toast.makeText(getApplicationContext(), R.string.missing_info, Toast.LENGTH_SHORT).show();
                        } else if (username.contains("") || password.contains("")){
                            reset();
                            Toast.makeText(getApplicationContext(), R.string.account_has_space, Toast.LENGTH_SHORT).show();
                        }  else if (!password.equals(repassword)) {
                           reset();
                            Toast.makeText(getApplicationContext(), R.string.not_same, Toast.LENGTH_SHORT).show();
                        } else {
                            reset();
                            Toast.makeText(ActivityLogin.this, R.string.other_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
    private void reset() {
        etPasswordSignup.setText("");
        etRePasswordSingup.setText("");
        etUsernameSignup.setText("");
    }

    public static ActivityLogin newInstance() {
        return mActivityLogin;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
