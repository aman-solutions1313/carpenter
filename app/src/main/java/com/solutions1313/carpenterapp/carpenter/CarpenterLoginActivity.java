package com.solutions1313.carpenterapp.carpenter;

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
import com.solutions1313.carpenterapp.staff.StaffDashboardActivity;
import com.solutions1313.carpenterapp.staff.StaffLoginActivity;

public class CarpenterLoginActivity extends AppCompatActivity {

//    Context context;
//    TextView header_title;
//    LinearLayout loginLayout, registerLayout, loginBtn, switchToRegisterBtn, registerBtn, switchToLoginBtn;
//    EditText loginPhoneET, loginPasswordET;
//    EditText registerFullnameET, registerEmailET, registerPhoneET, registerPasswordET;



    Context context;
    TextView header_title, dateView;
    LinearLayout loginLayout, registerLayout, loginBtn, switchToRegisterBtn, registerBtn, switchToLoginBtn, healderLayout, loginAsStaffBtn;
    EditText loginPhoneET, loginPasswordET;
    EditText registerFullnameET, registerEmailET, registerPhoneET, registerPasswordET;
    RadioButton bankRadio, paytmRadio;
    private int year, month, day;
    private Calendar calendar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpenter_login);
        //init
        context = CarpenterLoginActivity.this;
        header_title = findViewById(R.id.header_title);
        loginAsStaffBtn = findViewById(R.id.loginAsStaffBtn);
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
        healderLayout = findViewById(R.id.healderLayout);
        bankRadio = findViewById(R.id.bankRadio);
        dateView = findViewById(R.id.registerDOB);
        paytmRadio = findViewById(R.id.paytmRadio);
        LinearLayout.LayoutParams paramHeader1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.7f
        );
        LinearLayout.LayoutParams paramHeader4 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                4.0f
        );
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        switchToRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                loginLayout.setVisibility(View.GONE);
                registerLayout.setVisibility(View.VISIBLE);
                header_title.setText("Register");
                healderLayout.setLayoutParams(paramHeader4);
            }
        });
        switchToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                loginLayout.setVisibility(View.VISIBLE);
                registerLayout.setVisibility(View.GONE);
                header_title.setText("Login");
                healderLayout.setLayoutParams(paramHeader1);
            }
        });

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
                Toast.makeText(getApplicationContext(), "Select Date",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        loginAsStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CarpenterLoginActivity.this, StaffLoginActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        bankRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytmRadio.setChecked(false);
                bankRadio.setChecked(true);
            }
        });

        paytmRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankRadio.setChecked(false);
                paytmRadio.setChecked(true);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CarpenterLoginActivity.this, CarpenterDashboardActivity.class);
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

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}