
/*
 * Copyright (c) 2018 Yipu Chen, CMPUT301. University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of Code of Student Behavior
 *  at University of Alberta.
 *  You can find a copy of the lincense in this project. Otherwise, please contact yipu1@ualberta.ca
 *
 */

package com.example.yipu1_subbook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a subscription.
 *
 * @author Yipu
 *
 * @version 1.0
 */

public class Subscription implements Serializable {
    private String name;        //up to 20 characters
    private String date;        //format yyyy-mm-dd
    private Float amount;       //non-negative
    private String comment;     //may be blank, up to 30 characters

    Subscription(String name, String date, String amount, String comment) {
        this.name = name;
        this.date = date;
        this.amount = Float.valueOf(amount);
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Float getAmount() {
        return amount;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    //Returns a string containing subscription name, date and amount.
    public String toString() {
        //return String.format("%-20s | %-15s | %-10.2f | %-30s", name, date, amount, comment);
        return (name + "\n" + date + "\n$" + amount);
    }
}