package com.solutions1313.carpenterapp.carpenter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.solutions1313.carpenterapp.R;
import com.solutions1313.carpenterapp.SplashScreenActivity;
import com.solutions1313.carpenterapp.staff.StaffLoginActivity;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CarpenterDashboardActivity extends AppCompatActivity {
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    //    private BottomNavigationView bottomNavigationView;
//    private ViewPager viewPager;
    MenuItem prevMenuItem;
    private Context context;
    Dialog ConfirmationDialog;

    private Bitmap bits;
    public static final int pick_image = 1;
    CircleImageView profileImageView;
    Uri imageUri;
    public Bitmap bitmap = null;
    ImageView searchBtn, cartBtn;
    String user_id = "", user_name = "", email = "";

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    boolean isLoggedIn = false;


    ImageView scan_icon;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    TextView withdrawBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpenter_dashboard);
        context = CarpenterDashboardActivity.this;
        searchBtn = findViewById(R.id.searchBtn);
        withdrawBtn = findViewById(R.id.withdrawBtn);
        cartBtn = findViewById(R.id.cartBtn);
        scan_icon = findViewById(R.id.scan_icon);

        SharedPreferences getShared = getSharedPreferences("user_id", MODE_PRIVATE);
        user_id = getShared.getString("Id", "");
        email = getShared.getString("email", "NA");
        user_name = getShared.getString("user_name", "NA");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isLoggedIn = extras.getBoolean("isLoggedIn");
        }
        Log.d("msg", "#######user id :   " + user_id);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mToggle.getDrawerArrowDrawable().setColor(getColor(R.color.light_black));
        } else {
            mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.light_black));
        }
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mToggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        }
        navigationView = findViewById(R.id.nav_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View headerLayout = navigationView.getHeaderView(0);
        final TextView nameTV = headerLayout.findViewById(R.id.name);
        final TextView emailTV = headerLayout.findViewById(R.id.email);
        profileImageView = headerLayout.findViewById(R.id.user_image);


        initData();
        setProfileImageFromPref();

        emailTV.setText(email);
        nameTV.setText(user_name);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CarpenterDashboardActivity.this, CartActivity.class);
//                startActivity(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {

                    case R.id.nav_home:
                        break;

                    case R.id.nav_history:
                        Intent intent3 = new Intent(CarpenterDashboardActivity.this, ScanHistoryActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_logout:

                        ConfirmationDialog = new Dialog(context);
                        ConfirmationDialog.setContentView(R.layout.dialog_automation_confirmation);
                        ConfirmationDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        ConfirmationDialog.setCancelable(false);
                        LinearLayout okBtn, cancelBtn;
                        TextView textTitle;

                        cancelBtn = ConfirmationDialog.findViewById(R.id.cancelBtn);
                        okBtn = ConfirmationDialog.findViewById(R.id.okBtn);
                        textTitle = ConfirmationDialog.findViewById(R.id.textTitle);
                        textTitle.setText("Are you sure you want to Logout?");
                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ConfirmationDialog.dismiss();
                                SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(CarpenterDashboardActivity.this);
                                SharedPreferences.Editor edit = wmbPreference.edit();
                                edit.putBoolean("LoggedIn", false);
                                edit.apply();
                                Intent intent1 = new Intent(CarpenterDashboardActivity.this, SplashScreenActivity.class);
                                startActivity(intent1);
                                finish();

                            }
                        });
                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ConfirmationDialog.dismiss();
                            }
                        });
                        ConfirmationDialog.show();

                        break;
                }
                drawerLayout.closeDrawer(Gravity.LEFT);

                return true;
            }
        });

    }

    private void initData() {
        scan_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                        Toast.makeText(context, "Please allow camera permission!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(context, ScanActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
         } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(CarpenterDashboardActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }

        withdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    private void setProfileImageFromPref() {

        bits = null;
        SharedPreferences getShared = getSharedPreferences("user_id", MODE_PRIVATE);
        String profile = getShared.getString("profile_image", "");

        if (profile != "") {
            byte[] decode = Base64.decode(profile.getBytes(), 1);
            bits = BitmapFactory.decodeByteArray(decode, 0, decode.length);
            profileImageView.setImageBitmap(bits);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}