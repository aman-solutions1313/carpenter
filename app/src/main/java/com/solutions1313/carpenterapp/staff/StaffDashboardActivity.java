package com.solutions1313.carpenterapp.staff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.solutions1313.carpenterapp.carpenter.CarpenterDashboardActivity;
import com.solutions1313.carpenterapp.carpenter.ScanHistoryActivity;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class StaffDashboardActivity extends AppCompatActivity {
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

    LinearLayout click_image_btn;
    private int CAMERA_PIC_REQUEST = 1;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);
        context = StaffDashboardActivity.this;
        searchBtn = findViewById(R.id.searchBtn);
        cartBtn = findViewById(R.id.cartBtn);
        click_image_btn = findViewById(R.id.click_image_btn);

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

        setProfileImageFromPref();

        emailTV.setText(email);
        nameTV.setText(user_name);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(StaffDashboardActivity.this, CartActivity.class);
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
                        Intent intent3 = new Intent(StaffDashboardActivity.this, QrUploadedHistoryActivity.class);
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
                                SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(StaffDashboardActivity.this);
                                SharedPreferences.Editor edit = wmbPreference.edit();
                                edit.putBoolean("LoggedIn", false);
                                edit.apply();
                                Intent intent1 = new Intent(StaffDashboardActivity.this, SplashScreenActivity.class);
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

        click_image_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap imageBitMap = (Bitmap) data.getExtras().get("data");
            if (imageBitMap != null) {
                ConfirmationDialog = new Dialog(context);
                ConfirmationDialog.setContentView(R.layout.dialog_qr_generated);
                ConfirmationDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ConfirmationDialog.setCancelable(false);
                LinearLayout cancelBtn = ConfirmationDialog.findViewById(R.id.cancelBtn);
                LinearLayout okBtn = ConfirmationDialog.findViewById(R.id.okBtn);
                ImageView clickedImage = ConfirmationDialog.findViewById(R.id.clickedImage);

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "QR Code Uploaded", Toast.LENGTH_SHORT).show();
                        ConfirmationDialog.dismiss();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ConfirmationDialog.dismiss();
                    }
                });

                clickedImage.setImageBitmap(imageBitMap);
                ConfirmationDialog.show();
            }
        }
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