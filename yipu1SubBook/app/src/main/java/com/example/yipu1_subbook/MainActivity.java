
/*
 * Copyright (c) 2018 Yipu Chen, CMPUT301. University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of Code of Student Behavior
 *  at University of Alberta.
 *  You can find a copy of the lincense in this project. Otherwise, please contact yipu1@ualberta.ca
 *
 */

package com.example.yipu1_subbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static java.lang.Boolean.TRUE;

/**
 * This is the main activity of the application
 * It shows
 */
public class MainActivity extends Activity {
    private static final String FILENAME = "subscription_list.sav";

    private ListView historyList;
    private TextView totalText;
    private ArrayList<Subscription> subscriptionList = new ArrayList<>();
    private ArrayAdapter<Subscription> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        historyList = (ListView) findViewById(R.id.RecordList);
        totalText = (TextView) findViewById(R.id.textView_total);
        Button addButton = (Button) findViewById(R.id.button_add);
        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptionList);
        historyList.setAdapter(adapter);

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ShowDetailsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 CreateDialog createDialog = new CreateDialog(MainActivity.this, new CreateDialog.onDialogListener() {
                    @Override
                    public void onEnsure(String name, String date, String amount, String comment) {
                        Subscription subscription = new Subscription(name, date, amount, comment);
                        subscriptionList.add(subscription);
                        FileUtils.saveInFile(MainActivity.this,subscriptionList);
                        adapter.notifyDataSetChanged();
                        updateTotalAmount();
                    }
                });
                createDialog.show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        subscriptionList.clear();
        subscriptionList.addAll(FileUtils.<Subscription>loadFromFile(this));
        adapter.notifyDataSetChanged();
        updateTotalAmount();

    }

    private void updateTotalAmount() {
        Float total = 0f;
        for (int i = 0; i < subscriptionList.size(); i++) {
            total += (Float) subscriptionList.get(i).getAmount();
        }

        totalText.setText(String.format("Total amount: $%.2f", total));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy is called");
    }
}