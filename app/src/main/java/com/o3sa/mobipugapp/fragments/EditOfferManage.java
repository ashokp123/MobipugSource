package com.o3sa.mobipugapp.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

import java.util.Calendar;

/**
 * Created by android_2 on 10/17/2018.
 */

public class EditOfferManage extends Fragment {

   public static TextView mange_adofr_tx,mange_ofrtype_tx,mange_publc_tx,mange_private_tx,mange_strtdate_tx,mange_dsplydate_tx,mange_enddate_tx,
            mange_dsplyenddate_tx,mange_ofrdscrptn_tx;

    LinearLayout strt_dte_lay,end_dte_lay;

    EditText mange_prmocd_edtx,mange_dscunt_edtx,mange_ofrdscrptn_edtx;

    TextView mange_ofrtype_edtx;
    Button mange_save_btn;
    ImageView mange_ofrtype_img,mange_dsplydate_img,mange_dsplyendate_img;
    LinearLayout mange_ofrtype_lay,mange_prmocd_lay,mange_dscunt_lay;
    RadioButton mange_radiobutn,mange_radiobutnone;

    BasicComponents components;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.editoffer_manage,container,false);
        intialization(v);
        assigndata();
        return v;
    }

    public void intialization(View v){
        components=new BasicComponents(getActivity());
        StoredObjects.pagetype="editoffer";
        Sidemenu.updatemenu(StoredObjects.pagetype);

        mange_ofrtype_lay = (LinearLayout)v.findViewById(R.id.mange_ofrtype_lay);
        mange_prmocd_lay = (LinearLayout)v.findViewById(R.id.mange_prmocd_lay);
        mange_dscunt_lay = (LinearLayout)v.findViewById(R.id.mange_dscunt_lay);

        mange_ofrtype_edtx =(TextView) v.findViewById(R.id.mange_ofrtype_edtx);
        mange_prmocd_edtx= (EditText)v.findViewById(R.id.mange_prmocd_edtx);
        mange_dscunt_edtx =(EditText)v.findViewById(R.id.mange_dscunt_edtx);
        mange_ofrdscrptn_edtx =(EditText)v.findViewById(R.id.mange_ofrdscrptn_edtx);

        mange_adofr_tx = (TextView)v.findViewById(R.id.mange_adofr_tx);
        mange_ofrtype_tx = (TextView)v.findViewById(R.id.mange_ofrtype_tx);
        mange_publc_tx = (TextView)v.findViewById(R.id.mange_publc_tx);
        mange_private_tx =(TextView)v.findViewById(R.id.mange_private_tx);
        mange_strtdate_tx =(TextView)v.findViewById(R.id.mange_strtdate_tx);
        mange_dsplydate_tx =(TextView)v.findViewById(R.id.mange_dsplydate_tx);
        mange_enddate_tx =(TextView)v.findViewById(R.id.mange_enddate_tx);
        mange_dsplyenddate_tx =(TextView)v.findViewById(R.id.mange_dsplyenddate_tx);
        mange_ofrdscrptn_tx =(TextView)v.findViewById(R.id.mange_ofrdscrptn_tx);

        mange_ofrtype_img = (ImageView)v.findViewById(R.id.mange_ofrtype_img);
        mange_dsplydate_img = (ImageView)v.findViewById(R.id.mange_dsplydate_img);
        mange_dsplyendate_img = (ImageView)v.findViewById(R.id.mange_dsplyendate_img);

        mange_radiobutn = (RadioButton)v.findViewById(R.id.mange_radiobutn);
        mange_radiobutnone = (RadioButton)v.findViewById(R.id.mange_radiobutnone);

        mange_save_btn = (Button)v.findViewById(R.id.mange_save_btn);

        strt_dte_lay = v.findViewById(R.id.strt_dte_lay);
        end_dte_lay = v.findViewById(R.id.end_dte_lay);

        mange_radiobutn.setChecked(true);
        mange_radiobutnone.setChecked(false);

        mange_radiobutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mange_radiobutn.isChecked())
                {
                    mange_radiobutn.setChecked(true);
                    mange_radiobutnone.setChecked(false);
                }
            }
        });

        mange_radiobutnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mange_radiobutnone.isChecked())
                {
                    mange_radiobutnone.setChecked(true);
                    mange_radiobutn.setChecked(false);
                }
            }
        });
    }
    public void assigndata(){

        components.CustomizeTextview(mange_ofrtype_edtx,Constants.Small,R.color.serch_nrml_clr,getActivity().getApplicationContext().getResources().getString(R.string.ofrtype),Constants.WrapCenterNormal+Constants.Roboto, new int[]{12,12,0,12});

        components.CustomizeEditview(mange_prmocd_edtx,Constants.Small,getActivity().getApplicationContext().getResources().getString(R.string.promo_code),0,true,Constants.MatchCenterBold+Constants.Roboto, new int[]{12,10,0,10});
        components.CustomizeEditview(mange_dscunt_edtx,Constants.Small,getActivity().getApplicationContext().getResources().getString(R.string.dscunt),0,true,Constants.MatchCenterBold+Constants.Roboto, new int[]{12,10,0,10});
        components.CustomizeMultilineEditview(mange_ofrdscrptn_edtx,Constants.Normal,getActivity().getApplicationContext().getResources().getString(R.string.ofr_dscrptnn),R.drawable.select_ctgry_bg,true,false,Constants.MatchLeftBold+Constants.Roboto, new int[]{0,10,0,0},8);

        mange_ofrdscrptn_edtx.setGravity(Gravity.TOP|Gravity.LEFT);
        mange_ofrdscrptn_edtx.setPadding(20, 10, 20, 0);

        components.CustomizeTextview(mange_adofr_tx,Constants.Medium,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.ad_ofr),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,25,0,0});
        components.CustomizeTextview(mange_ofrtype_tx,Constants.Medium,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.ofrtype),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,20,0,0});
        components.CustomizeTextview(mange_publc_tx,Constants.Small,R.color.serch_nrml_clr,getActivity().getApplicationContext().getResources().getString(R.string.publc),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(mange_private_tx,Constants.Normal,R.color.nrml_grey,getActivity().getApplicationContext().getResources().getString(R.string.prvate),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,1,7,0});
        components.CustomizeTextview(mange_strtdate_tx,Constants.Small,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.start_date),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,12,0,12});
        components.CustomizeTextview(mange_dsplydate_tx,Constants.Small,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.disply_strtdate),Constants.WrapCenterNormal+Constants.Roboto, new int[]{10,12,0,12});
        components.CustomizeTextview(mange_enddate_tx,Constants.Small,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.end_date),Constants.WrapCenterNormal+Constants.Roboto, new int[]{12,12,0,12});
        components.CustomizeTextview(mange_dsplyenddate_tx,Constants.Small,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.disply_enddate),Constants.WrapCenterNormal+Constants.Roboto, new int[]{10,12,0,12});
        components.CustomizeTextview(mange_ofrdscrptn_tx,Constants.Small,R.color.nrml_grey,getActivity().getApplicationContext().getResources().getString(R.string.ofr_dscrptn),Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,10,0,3});


        components.CustomizeImageview(mange_ofrtype_img, new int[]{15,15}, R.drawable.ic_launcher, new int[]{0,0,0,0});
        components.CustomizeImageview(mange_dsplydate_img, new int[]{20,20}, R.drawable.ic_launcher, new int[]{7,0,0,0});
        components.CustomizeImageview(mange_dsplyendate_img, new int[]{20,20}, R.drawable.ic_launcher, new int[]{7,0,0,0});

        components.CustomizeButton(mange_save_btn, Constants.XXNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.save),R.drawable.cntinue_btn_bg,Constants.MatchCenterBold+Constants.Roboto, new int[]{0,42}, new int[]{0,20,0,30});


        strt_dte_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePickerDialog(getActivity());
            }
        });

        end_dte_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePickerDialogg(getActivity());
            }
        });

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            int MotnhVal=month+1;

            String monthval;

            if (MotnhVal<10){
                monthval="0"+MotnhVal+"/"+day+"/"+year;
            }else {
                monthval=+MotnhVal+"/"+day+"/"+year;
            }
            mange_dsplydate_tx.setText(monthval);
        }
    }

    public void ShowDatePickerDialog(Activity activity) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");
    }



    public static class DatePickerFragmentt extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            int MotnhVal=month+1;

            String monthval;

            if (MotnhVal<10){
                monthval="0"+MotnhVal+"/"+day+"/"+year;
            }else {
                monthval=+MotnhVal+"/"+day+"/"+year;
            }
            mange_dsplyenddate_tx.setText(monthval);
        }
    }

    public void ShowDatePickerDialogg(Activity activity) {
        DialogFragment newFragment = new DatePickerFragmentt();
        newFragment.show(getFragmentManager(),"datePicker");
    }

}
