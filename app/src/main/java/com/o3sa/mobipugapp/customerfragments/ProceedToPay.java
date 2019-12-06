package com.o3sa.mobipugapp.customerfragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by android-4 on 11/13/2018.
 */

public class ProceedToPay extends Fragment {

    BasicComponents components;

    TextView pay_amnt_txt;
    ImageView scanned_img;
    Button prceed_to_pay_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.proceed_to_pay,container,false);

        components = new BasicComponents(getActivity());

        init(v);
        return v;
    }

    public void init(View b){

        pay_amnt_txt = (TextView)b.findViewById(R.id.pay_amnt_txt);
        scanned_img = (ImageView)b.findViewById(R.id.scanned_img);
        prceed_to_pay_btn = (Button)b.findViewById(R.id.prceed_to_pay_btn);

        setData();

    }

    private void setData(){

        components.CustomizeTextview(pay_amnt_txt, Constants.Medium, R.color.black, "Amount : "+ScanBarcode.scnned_amount+" /-", Constants.WrapCenterBold+ Constants.Roboto, new int[]{10,10,10,10});
        components.CustomizeButton(prceed_to_pay_btn,Constants.XNormal,R.color.white,getActivity().getResources().getString(R.string.proceedtopay), R.drawable.addtocart_bg, Constants.MatchCenterNormal+Constants.SFUIText, new int[]{0,50}, new int[]{10,0,10,7});

            Glide.with(getActivity())
                    .load(Uri.parse(StoredUrls.MainUrl+ScanBarcode.scnned_image)) // add your image url
                    //.transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                    //.override(200, 160) //to override image size.
                    .centerCrop() // scale to fill the ImageView and crop any extra
                    .fitCenter()  // scale to fit entire image within ImageView
                    .placeholder(R.drawable.imagenotfound)
                    .into(scanned_img);

    }

    public void fragmentcalling(Activity activity, Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }

}
