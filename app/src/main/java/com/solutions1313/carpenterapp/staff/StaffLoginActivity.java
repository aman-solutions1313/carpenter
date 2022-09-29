package com.solutions1313.carpenterapp.staff;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.solutions1313.carpenterapp.R;
import com.solutions1313.carpenterapp.SplashScreenActivity;
import com.solutions1313.carpenterapp.carpenter.CarpenterDashboardActivity;
import com.solutions1313.carpenterapp.carpenter.CarpenterLoginActivity;

public class StaffLoginActivity extends AppCompatActivity {

    Context context;
    TextView header_title, dateView;
    LinearLayout loginLayout, registerLayout, loginBtn, switchToRegisterBtn, registerBtn, switchToLoginBtn, healderLayout;
    EditText loginPhoneET, loginPasswordET;
    EditText registerFullnameET, registerEmailET, registerPhoneET, registerPasswordET;
    RadioButton bankRadio, paytmRadio;
    private int year, month, day;
    private Calendar calendar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);
        //init
        context = StaffLoginActivity.this;
        header_title = findViewById(R.id.header_title);
        loginLayout = findViewById(R.id.loginLayout);
        registerLayout = findViewById(R.id.registerLayout);
        loginBtn = findViewById(R.id.loginBtn);
        switchToRegisterBtn = findViewById(R.id.switchToRegisterBtn);
        registerBtn = findViewById(R.id.registerBtn);
        switchToLoginBtn = findViewById(R.id.switchToLoginBtn);
        loginPhoneET = findViewById(R.id.loginPhoneET);
        loginPasswordET = findViewById(R.id.loginPasswordET);
        registerFullnameET = findViewById(R.id.registerFullnameET);
        registerEmailET = findViewById(R.id.registerEmailET);
        registerPhoneET = findViewById(R.id.registerPhoneET);
        registerPasswordET = findViewById(R.id.registerPasswordET);

        switchToRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                loginLayout.setVisibility(View.GONE);
                registerLayout.setVisibility(View.VISIBLE);
                header_title.setText("Register");
            }
        });
        switchToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                loginLayout.setVisibility(View.VISIBLE);
                registerLayout.setVisibility(View.GONE);
                header_title.setText("Login");
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StaffLoginActivity.this, StaffDashboardActivity.class);
                intent.putExtra("isLoggedIn", true);
                startActivity(intent);
                finish();
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog ConfirmationDialog;
                ConfirmationDialog = new Dialog(context);
                ConfirmationDialog.setContentView(R.layout.dialog_automation_confirmation);
                ConfirmationDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ConfirmationDialog.setCancelable(false);
                LinearLayout okBtn, cancelBtn;
                TextView textTitle;

                cancelBtn = ConfirmationDialog.findViewById(R.id.cancelBtn);
                okBtn = ConfirmationDialog.findViewById(R.id.okBtn);
                textTitle = ConfirmationDialog.findViewById(R.id.textTitle);
                textTitle.setText("You have been registered successfully\nPlease wait for admin approval.");
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginLayout.setVisibility(View.VISIBLE);
                        registerLayout.setVisibility(View.GONE);
                        header_title.setText("Login");
                        ConfirmationDialog.dismiss();
                    }
                });
                cancelBtn.setVisibility(View.INVISIBLE);
                ConfirmationDialog.show();
            }
        });


    }
}