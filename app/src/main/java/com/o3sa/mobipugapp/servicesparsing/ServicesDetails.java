package com.o3sa.mobipugapp.servicesparsing;

import android.os.CountDownTimer;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ServicesDetails {

    public String url = "";
    public List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(20);
    public String Result = "";
    public CountDownTimer countDown;
    public String parsing_type="";
    public String imagepath="";
}
