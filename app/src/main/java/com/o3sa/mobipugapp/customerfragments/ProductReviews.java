package com.o3sa.mobipugapp.customerfragments;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.database.Database;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.CircularImageView;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.CustomviewpagerNew;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kiran on 26-10-2018.
 */

public class ProductReviews extends Fragment {

    BasicComponents components;

    RatingBar prdct_rws_rating;
    TextView prdct_vw_dtls_txt,prdct_rw_rtng_txt,prdct_rw_rtng_txt1,prdct_ovrall_rws_txt,prdct_vw_dtls_cmnts_txt;
    TextView prdct_five_star_txt,prdct_no_of_five_star_txt,prdct_four_star_txt,prdct_no_of_four_star_txt,prdct_three_star_txt,prdct_no_of_three_star_txt,
            prdct_two_star_txt,prdct_no_of_two_star_txt,prdct_one_star_txt,prdct_no_of_one_star_txt;
    ProgressBar prdct_five_star_rtngbar,prdct_four_star_rtngbar,prdct_three_star_rtngbar,prdct_two_star_rtngbar,prdct_one_star_rtngbar;
    Button prdct_wrt_rvw_nw_btn;
    LinearLayout prdct_rvw_lay,noreviews_lay;
    CircularImageView rvw_prfl_img;

    public ArrayList<HashMap<String, String>> prdct_details_list;
    public ArrayList<HashMap<String, String>> reviews_list;



    String reviews_count = "";
    float rationgval=0f;
    int count=0;
    Database database;
    TextView nocartitems;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.productreviews,container,false);
        init(v);
        callingService();
        return v;
    }

    public void init(View b){

        database=new Database(getActivity());
        database.getAllDevice();
        components=new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.reviews),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
        StoredObjects.pagetype="productreviews";
        Sidemenu.updatemenu(StoredObjects.pagetype);

        prdct_vw_dtls_txt = (TextView)b.findViewById(R.id.prdct_vw_dtls_txt);
        prdct_rw_rtng_txt = (TextView)b.findViewById(R.id.prdct_rw_rtng_txt);
        prdct_rw_rtng_txt1 = (TextView)b.findViewById(R.id.prdct_rw_rtng_txt1);
        prdct_ovrall_rws_txt = (TextView)b.findViewById(R.id.prdct_ovrall_rws_txt);

        prdct_five_star_txt = (TextView)b.findViewById(R.id.prdct_five_star_txt);
        prdct_no_of_five_star_txt = (TextView)b.findViewById(R.id.prdct_no_of_five_star_txt);
        prdct_four_star_txt = (TextView)b.findViewById(R.id.prdct_four_star_txt);
        prdct_no_of_four_star_txt = (TextView) b.findViewById(R.id.prdct_no_of_four_star_txt);
        prdct_three_star_txt = (TextView)b.findViewById(R.id.prdct_three_star_txt);
        prdct_no_of_three_star_txt = (TextView)b.findViewById(R.id.prdct_no_of_three_star_txt);
        prdct_two_star_txt = (TextView)b.findViewById(R.id.prdct_two_star_txt);
        prdct_no_of_two_star_txt = (TextView)b.findViewById(R.id.prdct_no_of_two_star_txt);
        prdct_one_star_txt = (TextView)b.findViewById(R.id.prdct_one_star_txt);
        prdct_no_of_one_star_txt = (TextView)b.findViewById(R.id.prdct_no_of_one_star_txt);

        prdct_wrt_rvw_nw_btn = (Button)b.findViewById(R.id.prdct_wrt_rvw_nw_btn);
        prdct_rws_rating = (RatingBar)b.findViewById(R.id.prdct_rws_rating);

        prdct_five_star_rtngbar = (ProgressBar) b.findViewById(R.id.prdct_five_star_rtngbar);
        prdct_four_star_rtngbar = (ProgressBar)b.findViewById(R.id.prdct_four_star_rtngbar);
        prdct_three_star_rtngbar = (ProgressBar)b.findViewById(R.id.prdct_three_star_rtngbar);
        prdct_two_star_rtngbar = (ProgressBar)b.findViewById(R.id.prdct_two_star_rtngbar);
        prdct_one_star_rtngbar = (ProgressBar)b.findViewById(R.id.prdct_one_star_rtngbar);

        prdct_vw_dtls_cmnts_txt = (TextView)b.findViewById(R.id.prdct_vw_dtls_cmnts_txt);
        nocartitems=(TextView) b.findViewById(R.id.nocartitems);

        prdct_rvw_lay = (LinearLayout) b.findViewById(R.id.prdct_rvw_lay);
        noreviews_lay=(LinearLayout) b.findViewById(R.id.noreviews_lay);
        rvw_prfl_img = (CircularImageView)b.findViewById(R.id.rvw_prfl_img);

        setData();

        prdct_wrt_rvw_nw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddReviewPopup(getActivity());
            }
        });

    }

    private void callingService() {
        parsing_methods(0, StoredUrls.GetProductDetails_url,"customer_id="+StoredObjects.UserId+"&product_id="+StoredObjects.get_product_id, Constants.POSTMETHOD);
    }

    public void parsing_methods(final  int val,String url,String parameters,String parsing_type){

        WebServicesCalling webServicesCalling = new WebServicesCalling(getActivity());

        webServicesCalling.calling_webservices(url,parameters,val,parsing_type);

        StoredObjects.Services_list.get(val).countDown = new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                StoredObjects.LogMethod("Seconds remaining: "," Seconds remaining: " + millisUntilFinished / 100);

                if(StoredObjects.Services_list.get(val).Result != ""){
                    StoredObjects.LogMethod("Result",StoredObjects.Services_list.get(val).Result);
                    StoredObjects.LogMethod("Service_called",StoredObjects.Services_list.get(val).url);

                    if(val==0) {
                        try {
                            prdct_details_list = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                            if(!prdct_details_list.get(0).get("reviews").equalsIgnoreCase("[]")){
                                reviews_list = JsonParsing.GetInnerJsonData(prdct_details_list.get(0).get("reviews"));
                                if(reviews_list.size()==0){
                                    noreviews_lay.setVisibility(View.VISIBLE);
                                    prdct_rvw_lay.setVisibility(View.GONE);
                                }else{
                                    noreviews_lay.setVisibility(View.GONE);
                                    prdct_rvw_lay.setVisibility(View.VISIBLE);
                                    prdct_rvw_lay.removeAllViews();
                                    for(int i =0;i<reviews_list.size();i++){
                                        addlayout(prdct_rvw_lay,reviews_list,i);
                                    }
                                }
                                StoredObjects.LogMethod("reviews_list","reviews_list:------"+reviews_list.size());

                            }

                            AsignData();




                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);
                            e.printStackTrace();
                        }

                    }

                    StoredObjects.Services_list.get(val).countDown.cancel();

                }

            }

            public void onFinish() {
                StoredObjects.LogMethod("Finished ","Finished");
            }

        };

        StoredObjects.Services_list.get(val).countDown.start();

    }


    private void AddReviewPopup(Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customerreview);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView reviewtitle_tx = (TextView)dialog.findViewById(R.id.reviewtitle_tx);
        final TextView ratinggiven_txt = (TextView)dialog.findViewById(R.id.ratinggiven_txt);
        RatingBar rws_rating = (RatingBar)dialog.findViewById(R.id.rws_rating);
        final EditText customername_edtx=(EditText) dialog.findViewById(R.id.customername_edtx);

        final  EditText customermsg_edtx=(EditText) dialog.findViewById(R.id.customermsg_edtx);
        Button addreview_btn=(Button) dialog.findViewById(R.id.addreview_btn);
        ImageView cancelrvw_btn = (ImageView)dialog.findViewById(R.id.cancelrvw_btn);

        components.CustomizeTextview(reviewtitle_tx,Constants.Large,R.color.txt_clr,getActivity().getResources().getString(R.string.addrvw),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,10});
        components.CustomizeTextview(ratinggiven_txt,Constants.XNormal,R.color.txt_clr,getActivity().getResources().getString(R.string.ratinggiven),Constants.MatchCenterNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeEditview(customername_edtx, Constants.Normal,getActivity().getApplicationContext().getResources().getString(R.string.name),0,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeMultilineEditview(customermsg_edtx,Constants.Normal,getActivity().getApplicationContext().getResources().getString(R.string.writeareview),R.drawable.shadowwithoutradius,true,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,10},4);
        customermsg_edtx.setPadding(StoredObjects.pxFromDp(getActivity(),10),StoredObjects.pxFromDp(getActivity(),5),StoredObjects.pxFromDp(getActivity(),10),StoredObjects.pxFromDp(getActivity(),5));
        components.CustomizeButton(addreview_btn, Constants.XXNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.submit),R.drawable.list_bottom_bg,Constants.MatchCenterBold+Constants.SFUIText, new int[]{0,42}, new int[]{0,0,0,10});

        rws_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // TODO Auto-generated method stub
                ratinggiven_txt.setText(""+rating+" OUT OF 5");
                rationgval=rating;

            }
        });


        cancelrvw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        addreview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredObjects.hide_keyboard_activity(getActivity());
                String name=customername_edtx.getText().toString().trim();
                String descr=customermsg_edtx.getText().toString().trim();
                if (StoredObjects.inputValidation(customername_edtx, getActivity().getResources().getString(R.string.seclt_name),getActivity())) {

                    if (StoredObjects.inputValidation(customermsg_edtx, getActivity().getResources().getString(R.string.productrvw),getActivity())) {
                        if(rationgval!=0f){
                            dialog.dismiss();
                            parsing_methods(0, StoredUrls.AddReview_url,"customer_id="+StoredObjects.UserId+"&product_id="+StoredObjects.get_product_id+"&customer_name="+name+"&review_text="+descr+"&rating_star="+rationgval,Constants.POSTMETHOD);

                        }else{
                            StoredObjects.ToastMethod("Please give product rating.",getActivity());
                        }

                    }
                }
            }
        });

        dialog.show();

    }

    public void fragmentcalling( Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }

    public void addlayout(LinearLayout prdct_rvw_lay, ArrayList<HashMap<String, String>> reviews_list, int position){

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.prdct_vw_pager_lstitm,null);

        TextView prdct_good_at_txt = (TextView)v.findViewById(R.id.prdct_good_at_txt);
        TextView prdct_time_txt = (TextView)v.findViewById(R.id.prdct_time_txt);
        ImageView rvw_prfl_img = (ImageView) v.findViewById(R.id.rvw_prfl_img);

        TextView prdct_person_name_txt = (TextView)v.findViewById(R.id.prdct_person_name_txt);
        TextView prdct_descrptn_txt = (TextView)v.findViewById(R.id.prdct_descrptn_txt);

        Glide.with(getActivity())
                .load(Uri.parse(StoredUrls.MainUrl+reviews_list.get(position).get("customer_photo"))) // add your image url
                .centerCrop() // scale to fill the ImageView and crop any extra
                .fitCenter() // scale to fit entire image within ImageView
                .placeholder(R.drawable.imagenotfound)
                .into(rvw_prfl_img);

        components.CustomizeTextview(prdct_time_txt, Constants.Small,R.color.rvw_txt_clr,reviews_list.get(position).get("rating_period"),Constants.WrapRightNormal+Constants.Roboto, new int[]{12,18,0,0});
        components.CustomizeTextview(prdct_person_name_txt, Constants.XXNormal,R.color.txt_clr,reviews_list.get(0).get("customer_name"),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{8,0,12,3});
        components.CustomizeTextview(prdct_descrptn_txt, Constants.Normal,R.color.txt_clr,reviews_list.get(position).get("review_text"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{12,8,12,5});

        prdct_rvw_lay.addView(v);

    }

    Float number;

    private void AsignData(){

        String rvws_txt = prdct_details_list.get(0).get("rating");
        number = Float.parseFloat(rvws_txt);
        StoredObjects.LogMethod("number","number:---"+number);

        components.CustomizeTextview(prdct_rw_rtng_txt, Constants.XXNormal, R.color.sport_shoos_txt_clr, number+"", Constants.WrapLeftBold+Constants.Roboto, new int[]{15,0,0,0});
        prdct_rws_rating.setRating(number);

        reviews_count = prdct_details_list.get(0).get("reviews_count");
        if (reviews_count.equalsIgnoreCase("1")||reviews_count.equalsIgnoreCase("0")){
            components.CustomizeTextview(prdct_rw_rtng_txt1,Constants.Normal,R.color.nrml_txt_clr," of 5 ( "+prdct_details_list.get(0).get("reviews_count")+" review )",Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,6,0,0});
        }else {
            components.CustomizeTextview(prdct_rw_rtng_txt1,Constants.Normal,R.color.nrml_txt_clr," of 5 ( "+prdct_details_list.get(0).get("reviews_count")+" reviews )",Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,6,0,0});
        }

        components.CustomizeTextview(prdct_no_of_five_star_txt,Constants.Normal,R.color.nrml_txt_clr,prdct_details_list.get(0).get("five_star_rating"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,0,0,0});
        components.CustomizeTextview(prdct_no_of_four_star_txt,Constants.Normal,R.color.nrml_txt_clr,prdct_details_list.get(0).get("four_star_rating"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,0,0,0});
        components.CustomizeTextview(prdct_no_of_three_star_txt,Constants.Normal,R.color.nrml_txt_clr,prdct_details_list.get(0).get("three_star_rating"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,0,0,0});
        components.CustomizeTextview(prdct_no_of_two_star_txt,Constants.Normal,R.color.nrml_txt_clr,prdct_details_list.get(0).get("two_star_rating"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,0,0,0});
        components.CustomizeTextview(prdct_no_of_one_star_txt,Constants.Normal,R.color.nrml_txt_clr,prdct_details_list.get(0).get("one_star_rating"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,0,0,0});

        Double fivestar_val=0.0;
        Double fourstar_val=0.0;
        Double threestar_val=0.0;
        Double twostar_val=0.0;
        Double onestar_val=0.0;
        Double totalreview_val=0.0;
        int p_fivestar_val=0;
        int p_fourstar_val=0;
        int p_threestar_val=0;
        int p_twostar_val=0;
        int p_onestar_val=0;

        totalreview_val=Double.parseDouble(prdct_details_list.get(0).get("reviews_count"));

        fivestar_val=Double.parseDouble(prdct_details_list.get(0).get("five_star_rating"))*100;
        fourstar_val=Double.parseDouble(prdct_details_list.get(0).get("four_star_rating"))*100;
        threestar_val=Double.parseDouble(prdct_details_list.get(0).get("three_star_rating"))*100;
        twostar_val=Double.parseDouble(prdct_details_list.get(0).get("two_star_rating"))*100;
        onestar_val=Double.parseDouble(prdct_details_list.get(0).get("one_star_rating"))*100;
         p_fivestar_val=(int) Math.round(fivestar_val/totalreview_val);
         p_fourstar_val=(int) Math.round(fourstar_val/totalreview_val);
         p_threestar_val=(int) Math.round(threestar_val/totalreview_val);
         p_twostar_val=(int) Math.round(twostar_val/totalreview_val);
         p_onestar_val=(int) Math.round(onestar_val/totalreview_val);
        if(totalreview_val==0.0){
            prdct_five_star_rtngbar.setProgress(0);
            prdct_four_star_rtngbar.setProgress(0);
            prdct_three_star_rtngbar.setProgress(0);
            prdct_two_star_rtngbar.setProgress(0);
            prdct_one_star_rtngbar.setProgress(0);
        }else{
            if(fivestar_val==0.0){
                prdct_five_star_rtngbar.setProgress(0);
            }else{
                prdct_five_star_rtngbar.setProgress(p_fivestar_val);
            }
            if(fourstar_val==0.0){
                prdct_four_star_rtngbar.setProgress(0);
            }else{
                prdct_four_star_rtngbar.setProgress(p_fourstar_val);
            }
            if(threestar_val==0.0){
                prdct_three_star_rtngbar.setProgress(0);
            }else{
                prdct_three_star_rtngbar.setProgress(p_threestar_val);
            }
            if(twostar_val==0.0){
                prdct_two_star_rtngbar.setProgress(0);
            }else{
                prdct_two_star_rtngbar.setProgress(p_twostar_val);
            }
            if(onestar_val==0.0){
                prdct_one_star_rtngbar.setProgress(0);
            }else{
                prdct_one_star_rtngbar.setProgress(p_onestar_val);
            }

        }


    }

    private void setData() {

        components.CustomizeTextview(prdct_vw_dtls_txt,Constants.Large,R.color.txt_clr,getActivity().getResources().getString(R.string.rvws),Constants.WrapLeftNormal+Constants.Gibson, new int[]{12,12,0,0});

        components.CustomizeTextview(prdct_five_star_txt,Constants.Normal,R.color.nrml_txt_clr,getActivity().getResources().getString(R.string.five_s),Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,0,0,0});
        components.CustomizeTextview(prdct_four_star_txt,Constants.Normal,R.color.nrml_txt_clr,getActivity().getResources().getString(R.string.four_s),Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,0,0,0});
        components.CustomizeTextview(prdct_three_star_txt,Constants.Normal,R.color.nrml_txt_clr,getActivity().getResources().getString(R.string.three_s),Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,0,0,0});
        components.CustomizeTextview(prdct_two_star_txt,Constants.Normal,R.color.nrml_txt_clr,getActivity().getResources().getString(R.string.two_s),Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,0,0,0});
        components.CustomizeTextview(prdct_one_star_txt,Constants.Normal,R.color.nrml_txt_clr,getActivity().getResources().getString(R.string.one_s),Constants.WrapLeftNormal+Constants.Roboto, new int[]{3,0,0,0});
        components.CustomizeTextview(nocartitems, Constants.XXXNormal, R.color.black, getActivity().getResources().getString(R.string.noreviewsfound), Constants.WrapCenterBold+ Constants.Roboto, new int[]{10,10,10,10});

        components.CustomizeButton(prdct_wrt_rvw_nw_btn,Constants.Normal,R.color.blue_color,getActivity().getResources().getString(R.string.wrte_a),R.drawable.wrte_a_rvw_bg,Constants.MatchCenterNormal+Constants.Roboto, new int[]{0,47}, new int[]{15,0,15,0});

        components.CustomizeTextview(prdct_vw_dtls_cmnts_txt,Constants.Large,R.color.txt_clr,getActivity().getResources().getString(R.string.cmnts),Constants.WrapLeftNormal+Constants.Gibson, new int[]{12,0,0,10});

    }

}

