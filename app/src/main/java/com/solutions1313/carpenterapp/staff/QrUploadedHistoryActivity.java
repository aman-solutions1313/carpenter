package com.solutions1313.carpenterapp.staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.solutions1313.carpenterapp.R;
import com.solutions1313.carpenterapp.adapter.QrUploadedHistoryListRecyclerAdapter;
import com.solutions1313.carpenterapp.adapter.ScannedHistoryListRecyclerAdapter;
import com.solutions1313.carpenterapp.carpenter.ScanHistoryActivity;
import com.solutions1313.carpenterapp.model.DemoModel;

import java.util.ArrayList;

public class QrUploadedHistoryActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_uploaded_history);
        //init
        context = QrUploadedHistoryActivity.this;
        recyclerView = findViewById(R.id.recyclerView);

        setHistory();
    }

    private void setHistory() {
        ArrayList<DemoModel> list = new ArrayList<>();
        list.add(new DemoModel("Product 1"));
        list.add(new DemoModel("Product 2"));
        list.add(new DemoModel("Product 3"));
        list.add(new DemoModel("Product 4"));
        list.add(new DemoModel("Product 5"));

        QrUploadedHistoryListRecyclerAdapter adapter = new QrUploadedHistoryListRecyclerAdapter(list, context);
        GridLayoutManager gridLayoutRecyclerview = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutRecyclerview);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}