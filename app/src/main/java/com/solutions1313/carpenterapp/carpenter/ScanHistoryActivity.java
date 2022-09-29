package com.solutions1313.carpenterapp.carpenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solutions1313.carpenterapp.R;
import com.solutions1313.carpenterapp.adapter.ScannedHistoryListRecyclerAdapter;
import com.solutions1313.carpenterapp.adapter.WithdrawHistoryListRecyclerAdapter;
import com.solutions1313.carpenterapp.model.DemoModel;

import java.util.ArrayList;

public class ScanHistoryActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerViewScan, recyclerViewWithdraw;
    LinearLayout withdrawLayout, scanLayout, scanBtnTab, withdrawBtnTab;
    Button startFundBtn, donateNowBtn;
    TextView withdrawText, scanText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_history);
        //init
        context = ScanHistoryActivity.this;
        recyclerViewScan = findViewById(R.id.recyclerViewScan);
        recyclerViewWithdraw = findViewById(R.id.recyclerViewWithdraw);
        withdrawLayout = findViewById(R.id.withdrawLayout);
        scanLayout = findViewById(R.id.scanLayout);
        scanBtnTab = findViewById(R.id.scanBtnTab);
        withdrawBtnTab = findViewById(R.id.withdrawBtnTab);
        scanText = findViewById(R.id.scanText);
        withdrawText = findViewById(R.id.withdrawText);

        scanBtnTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                withdrawBtnTab.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                withdrawBtnTab.setBackground(context.getResources().getDrawable(R.drawable.transparent_btn_bg));
                scanBtnTab.setBackground(context.getResources().getDrawable(R.drawable.secondary_btn_bg));
//                scanBtnTab.setBackgroundColor(context.getResources().getColor(R.color.theme_color_secondary));
                withdrawLayout.setVisibility(View.GONE);
                scanLayout.setVisibility(View.VISIBLE);
                scanText.setTextColor(context.getResources().getColor(R.color.white));
                withdrawText.setTextColor(context.getResources().getColor(R.color.light_black));
            }
        });

        withdrawBtnTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanBtnTab.setBackground(context.getResources().getDrawable(R.drawable.transparent_btn_bg));
                withdrawBtnTab.setBackground(context.getResources().getDrawable(R.drawable.secondary_btn_bg));
                scanLayout.setVisibility(View.GONE);
                withdrawLayout.setVisibility(View.VISIBLE);
                withdrawText.setTextColor(context.getResources().getColor(R.color.white));
                scanText.setTextColor(context.getResources().getColor(R.color.light_black));
            }
        });
        setScanHistory();
        setWithdrawHistory();
    }

    private void setScanHistory() {
        ArrayList<DemoModel> list = new ArrayList<>();
        list.add(new DemoModel("Product 1"));
        list.add(new DemoModel("Product 2"));
        list.add(new DemoModel("Product 3"));
        list.add(new DemoModel("Product 4"));
        list.add(new DemoModel("Product 5"));

        ScannedHistoryListRecyclerAdapter adapter = new ScannedHistoryListRecyclerAdapter(list, context);
        GridLayoutManager gridLayoutRecyclerview = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewScan.setLayoutManager(gridLayoutRecyclerview);
        recyclerViewScan.setItemAnimator(new DefaultItemAnimator());
        recyclerViewScan.setAdapter(adapter);
    }

    private void setWithdrawHistory() {
        ArrayList<DemoModel> list = new ArrayList<>();
        list.add(new DemoModel("Product 1"));
        list.add(new DemoModel("Product 2"));
        list.add(new DemoModel("Product 3"));

        WithdrawHistoryListRecyclerAdapter adapter = new WithdrawHistoryListRecyclerAdapter(list, context);
        GridLayoutManager gridLayoutRecyclerview = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewWithdraw.setLayoutManager(gridLayoutRecyclerview);
        recyclerViewWithdraw.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWithdraw.setAdapter(adapter);
    }
}