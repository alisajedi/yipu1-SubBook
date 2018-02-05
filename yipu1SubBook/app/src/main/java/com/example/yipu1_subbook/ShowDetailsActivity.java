
/*
 * Copyright (c) 2018 Yipu Chen, CMPUT301. University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of Code of Student Behavior
 *  at University of Alberta.
 *  You can find a copy of the lincense in this project. Otherwise, please contact yipu1@ualberta.ca
 *
 */

package com.example.yipu1_subbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

/**
 *
 * @author Yipu
 *
 * @version 1.0
 *
 * This activity shows details of a selected subscription.
 * Also included are three buttons: Delete, Update and Back.
 * By clicking the Back button. It jumps to the main activity without doing anything.
 * By clicking the Update button. An editing dialog shows and allows editing the selected
 * subscription.
 * By clicking the Delete button. An alert dialog shows to ask for deleteing confirmation.
 */
public class ShowDetailsActivity extends AppCompatActivity {
    private static final String FILENAME = "subscription_list.sav";
    private Subscription subscription;
    private TextView nameText;
    private TextView amountText;
    private TextView dateText;
    private TextView commentText;
    private ArrayList<Subscription> subscriptionList;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        final int position = (int) getIntent().getSerializableExtra("position");
        subscriptionList = FileUtils.loadFromFile(this);
        subscription = subscriptionList.get(position);

        nameText = (TextView) findViewById(R.id.editText_Name);
        dateText = (TextView) findViewById(R.id.editText_Date);
        amountText = (TextView) findViewById(R.id.editText_Amount);
        commentText = (TextView) findViewById(R.id.editText_Comment);

        updateText();

        Button updateButton = (Button) findViewById(R.id.button_update);
        Button cancelButton = (Button) findViewById(R.id.button_cancel);
        Button deleteButton = (Button) findViewById(R.id.button_delete);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditDialog dialog = new EditDialog(ShowDetailsActivity.this, subscription, new EditDialog.onDialogListener(){
            @Override
            public void onEnsure(String name, String date, String amount, String comment) {
                subscription.setName(name);
                subscription.setDate(date);
                subscription.setAmount(Float.valueOf(amount));
                subscription.setComment(comment);
                subscriptionList.add(subscription);
                FileUtils.saveInFile(ShowDetailsActivity.this,subscriptionList);
                updateText();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String name = nameText.getText().toString();
                String comment = commentText.getText().toString();
                String amount = amountText.getText().toString();
                String date = dateText.getText().toString();

                dialog.show();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent intent = new Intent(ShowDetailsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.setMessage("By clicking the DELETE button you will delete the selected record. Are you sure to delete?");
                alert.setTitle("Warnning!");
                alert.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        subscriptionList.remove(position);
                        FileUtils.saveInFile(ShowDetailsActivity.this,subscriptionList);
                        Intent intent = new Intent(ShowDetailsActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alert.show();
            }
        });
    }

    public void updateText() {
        nameText.setText(subscription.getName());
        dateText.setText(subscription.getDate());
        amountText.setText(String.valueOf(subscription.getAmount()));
        commentText.setText(subscription.getComment());
    }

    private boolean checkInput(String name, String dateString, String amountString, String comment) {
        if (name.length() == 0 || name.length() > 20 ||
                !dateString.matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$") ||
                !amountString.matches("[0-9.]+") || comment.length() > 30) {
            return false;
        } else {
            return true;
        }
    }
}
