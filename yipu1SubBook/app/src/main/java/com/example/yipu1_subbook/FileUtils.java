
/*
 * Copyright (c) 2018 Yipu Chen, CMPUT301. University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of Code of Student Behavior
 *  at University of Alberta.
 *  You can find a copy of the lincense in this project. Otherwise, please contact yipu1@ualberta.ca
 *
 */

package com.example.yipu1_subbook;

import android.content.Context;

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
 * Represents a file handler.
 *
 * @author Yipu
 *
 * @version 1.0
 *
 * This class handle file saving and loading.
 */
public class FileUtils {
    private static final String FILENAME = "subscription_list.sav";

    /**
     * Save all the subscriptions into file
     *
     * @param list The subscription list contains all the subscriptions currently.
     */
    public static void saveInFile(Context context,ArrayList<Subscription> list){
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(list, out);
            out.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Loads all subscription from file and put them in a Arraylist.
     * Return the list or a new list if File is not found.
     *
     * @return a list of subscriptions loads from file.
     */
    public static ArrayList<Subscription> loadFromFile(Context context){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //2018-02-02
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            return gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            return new ArrayList<Subscription>();
        }
    }

}
