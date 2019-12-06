package com.o3sa.mobipugapp.customerfragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.database.Database;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.CustomRecyclerview;
import com.o3sa.mobipugapp.uicomponents.Customviewpager;
import com.o3sa.mobipugapp.uicomponents.CustomviewpagerNew;
import com.o3sa.mobipugapp.uicomponents.ViewpagerAdapter;
import com.o3sa.mobipugapp.uicomponents.ViewpagerAdapterNew;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//Created by android-4 on 10/22/2018.


public class ProductDetails extends Fragment {

    BasicComponents components;

    ViewPager prdct_viewpager;
    LinearLayout viewPagerCountDots;
    ViewpagerAdapterNew viewpagerAdapter;

    TextView prdct_titl_txt,prdct_price_txt,prdct_actual_price_txt,prdct_dscnt_txt,prdct_ratng_txt;
    RatingBar prdct_rating;
    ImageView prdct_share_img,prdct_like_img;
    TextView prdct_dtls_txt,prdct_dtls_txt1,prdct_addtocart_txt;
    Button prdct_vw_dtls_btn,addtocart_btn;

    public ArrayList<HashMap<String, String>> prdct_details_list;

    ArrayList<HashMap<String, String>> images_list,reviews_list,offers_list,attributes_list;

    String isfavourite = "";
    String pro_name = "",pro_price="0.00",pro_image="",pro_attri="",pro_attriid="",prostatus="";
    ImageView noproductimg;

    Database database;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product,container,false);

        init(v);
        callingService();
        return v;
    }

    public void init(View b){

        attributes_list = new ArrayList<>();
        images_list = new ArrayList<>();
        reviews_list = new ArrayList<>();
        offers_list = new ArrayList<>();

        database=new Database(getActivity());
        database.getAllDevice();
        components=new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.product_details),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        StoredObjects.pagetype="productdetails";
        Sidemenu.updatemenu(StoredObjects.pagetype);

        prdct_titl_txt = (TextView) b.findViewById(R.id.prdct_titl_txt);
        prdct_price_txt = (TextView)b.findViewById(R.id.prdct_price_txt);
        prdct_actual_price_txt = (TextView)b.findViewById(R.id.prdct_actual_price_txt);
        prdct_dscnt_txt = (TextView)b.findViewById(R.id.prdct_dscnt_txt);
        prdct_ratng_txt = (TextView)b.findViewById(R.id.prdct_ratng_txt);

        prdct_dtls_txt = (TextView)b.findViewById(R.id.prdct_dtls_txt);
        prdct_dtls_txt1 = (TextView)b.findViewById(R.id.prdct_dtls_txt1);

        prdct_vw_dtls_btn = (Button) b.findViewById(R.id.prdct_vw_dtls_btn);
        addtocart_btn = (Button)b.findViewById(R.id.addtocart_btn);

        prdct_rating = (RatingBar) b.findViewById(R.id.prdct_rating);

        prdct_share_img = (ImageView)b.findViewById(R.id.prdct_share_img);
        prdct_like_img = (ImageView)b.findViewById(R.id.prdct_like_img);
        prdct_viewpager = (ViewPager)b.findViewById(R.id.prdct_viewpager);
        viewPagerCountDots = (LinearLayout)b.findViewById(R.id.viewPagerCountDots);
        prdct_addtocart_txt=(TextView) b.findViewById(R.id.prdct_addtocart_txt);
        noproductimg=(ImageView) b.findViewById(R.id.noproductimg);

        setData();

        addtocart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoredObjects.mycart_array.clear();
                StoredObjects.mycart_array = database.GetMyCartData(StoredObjects.UserId);

                if(prostatus.equalsIgnoreCase("Available")){
                    boolean checkitemexist = database.checkproductfromcart(StoredObjects.UserId, StoredObjects.get_product_id);
                    if (checkitemexist == true) {
                        StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.addedtocart),getActivity());
                    }else{
                        StoredObjects.redirecpage="productspage";
                        database.MyCart_insert(StoredObjects.UserId, StoredObjects.get_product_id,  pro_name, "1",pro_image, pro_price,pro_attri+","+pro_attriid);
                        fragmentcalling(new OrdersMain());
                    }

                }else{
                    StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.productunavlble),getActivity());
                }


            }
        });

        prdct_share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "Mobipug";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Mobipug");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, name +" Please refer to the given link "+StoredObjects.Playstorelink);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        prdct_like_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isfavourite.equalsIgnoreCase("Yes")){
                    parsing_methods(2, StoredUrls.RemovefromWishlist_url,"customer_id="+StoredObjects.UserId+"&product_id="+StoredObjects.get_product_id,Constants.POSTMETHOD);//+"&wishlist_id="+StoredObjects.get_product_id
                }else{
                    parsing_methods(1, StoredUrls.AddtoWishlist_url,"customer_id="+StoredObjects.UserId+"&product_id="+StoredObjects.get_product_id, Constants.POSTMETHOD);
                }
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



                            AssignData();

                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);

                            e.printStackTrace();
                        }

                    }else if(val==1){
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){
                                prdct_like_img.setBackgroundResource(R.drawable.filled_heart);
                                isfavourite="Yes";

                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.wishlist_msg),getActivity());
                            }else{
                                String error = jsonObject.getString("error");
                                StoredObjects.ToastMethod(error,getActivity());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else if(val==2){
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){
                                prdct_like_img.setBackgroundResource(R.drawable.heart);
                                isfavourite="No";

                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.remove_wishlist_msg),getActivity());
                            }else{
                                String error = jsonObject.getString("error");
                                StoredObjects.ToastMethod(error,getActivity());
                            }
                        } catch (JSONException e) {
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

    public void AssignData(){

        try {
            if(!prdct_details_list.get(0).get("attributes").equalsIgnoreCase("[]")){
                attributes_list = JsonParsing.GetInnerJsonData(prdct_details_list.get(0).get("attributes"));
                if(attributes_list.size()==0){
                    components.CustomizeTextview(prdct_price_txt,Constants.XNormal,R.color.blue_color,"Rs.0.00",Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{12,0,0,0});
                    pro_attriid="0";
                    pro_attri="0";
                }else{
                    pro_attriid=attributes_list.get(0).get("main_attr_id");
                    pro_attri=attributes_list.get(0).get("sub_attr_name");
                    components.CustomizeTextview(prdct_price_txt,Constants.XNormal,R.color.blue_color,"Rs."+attributes_list.get(0).get("price")+" / "+attributes_list.get(0).get("sub_attr_name"),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{12,0,0,0});
                }
            }else{
                components.CustomizeTextview(prdct_price_txt,Constants.XNormal,R.color.blue_color,"Rs.0.00",Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{12,0,0,0});

            }
            if(!prdct_details_list.get(0).get("images").equalsIgnoreCase("[]")){
                noproductimg.setVisibility(View.GONE);
                prdct_viewpager.setVisibility(View.VISIBLE);
                viewPagerCountDots.setVisibility(View.VISIBLE);
                images_list = JsonParsing.GetInnerJsonData(prdct_details_list.get(0).get("images"));
                pro_image=images_list.get(0).get("image");
                CustomviewpagerNew customviewpager = new CustomviewpagerNew();
                customviewpager.CustomviewpagerNew(getActivity(),prdct_viewpager,viewpagerAdapter,images_list,viewPagerCountDots,
                        "prdct_viewpager", R.layout.vw_pager_listitm_new);
            }else{
                noproductimg.setVisibility(View.VISIBLE);
                prdct_viewpager.setVisibility(View.GONE);
                viewPagerCountDots.setVisibility(View.GONE);
                pro_image="";

            }

            if(!prdct_details_list.get(0).get("offers").equalsIgnoreCase("[]")){
                offers_list = JsonParsing.GetInnerJsonData(prdct_details_list.get(0).get("offers"));

                if(offers_list.size()>0){
                    prdct_dscnt_txt.setVisibility(View.VISIBLE);
                    prdct_actual_price_txt.setVisibility(View.VISIBLE);
                    Double discount=0.0;
                    Double discountval=0.0;


                    if(attributes_list.size()>0){
                        if(offers_list.get(0).get("discount_type").equalsIgnoreCase("Fixed Amount")){
                            discountval=Double.parseDouble(attributes_list.get(0).get("price"))-Double.parseDouble(offers_list.get(0).get("amount"));
                            discount= calculatediscount(attributes_list.get(0).get("price"),offers_list.get(0).get("amount"),"fixed");
                            components.CustomizeTextview(prdct_actual_price_txt, Constants.Normal, R.color.rvw_txt_clr,"Rs."+ String.format("%.0f",discountval), Constants.WrapLeftNormal + Constants.Roboto, new int[]{12, 0, 0, 0});
                            components.CustomizeTextview(prdct_dscnt_txt, Constants.Normal, R.color.ofrs_listitem_clr, " (-"+discount+"%)", Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});
                            pro_price=String.format("%.0f",discountval);
                        }else{
                            discount= calculatediscount(attributes_list.get(0).get("price"),offers_list.get(0).get("amount"),"per");
                            components.CustomizeTextview(prdct_actual_price_txt, Constants.Normal, R.color.rvw_txt_clr,"Rs."+String.format("%.0f",discount), Constants.WrapLeftNormal + Constants.Roboto, new int[]{12, 0, 0, 0});
                            components.CustomizeTextview(prdct_dscnt_txt, Constants.Normal, R.color.ofrs_listitem_clr, " (-"+offers_list.get(0).get("amount")+"%)", Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});
                            pro_price=String.format("%.0f",discount);

                        }
                    }else{
                        prdct_dscnt_txt.setVisibility(View.GONE);
                        prdct_actual_price_txt.setVisibility(View.GONE);

                    }

                }else{
                    prdct_dscnt_txt.setVisibility(View.GONE);
                    prdct_actual_price_txt.setVisibility(View.GONE);
                }
            }else{
                prdct_dscnt_txt.setVisibility(View.GONE);
                prdct_actual_price_txt.setVisibility(View.GONE);
                if(attributes_list.size()>0){
                    pro_price=attributes_list.get(0).get("price");
                }

            }
             pro_name = prdct_details_list.get(0).get("product_name");

        }catch (Exception e){

        }
        components.CustomizeTextview(prdct_titl_txt, Constants.XXXNormal,R.color.sport_shoos_txt_clr,prdct_details_list.get(0).get("product_name"),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{12,12,10,0});
        isfavourite = prdct_details_list.get(0).get("is_favourite");

        if(isfavourite.equalsIgnoreCase("Yes")){
            prdct_like_img.setBackgroundResource(R.drawable.filled_heart);
        }else{
            prdct_like_img.setBackgroundResource(R.drawable.heart);
        }

        components.CustomizeTextview(prdct_ratng_txt,Constants.Normal,R.color.txt_clr, "("+prdct_details_list.get(0).get("reviews_count")+")",Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});
        prdct_rating.setRating(Float.parseFloat(prdct_details_list.get(0).get("rating")));
        components.CustomizeTextview(prdct_dtls_txt1,Constants.Normal,R.color.txt_clr,prdct_details_list.get(0).get("product_description"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{12,0,0,10});

        prostatus=prdct_details_list.get(0).get("stock_status");

    }
    public  Double calculatediscount(String totalval, String offerval, String type) {

        Double discount_val=0.0;
        Double total_val=0.0;
        Double per_val=0.0;
        Double discountedval=0.0;
        if(type.equalsIgnoreCase("fixed")){
            total_val=Double.parseDouble(totalval);
            discount_val=Double.parseDouble(offerval);
            discountedval=total_val-discount_val;
            per_val=(discountedval/total_val)*100;
        }else{
            total_val=Double.parseDouble(totalval);
            discount_val=Double.parseDouble(offerval);
            discountedval=total_val-total_val*0.01*discount_val;
            per_val=discountedval;
        }

        return per_val;
    }
    private void setData() {

        components.CustomizeWithBgTextview(prdct_addtocart_txt, Constants.XXXNormal,R.color.white,getActivity().getResources().getString(R.string.addtocart),R.drawable.addtocart_bg,Constants.WrapCenterNormal+Constants.Gibson, new int[]{10,5,0,5});
        components.CustomizeTextview(prdct_dtls_txt,Constants.Large,R.color.txt_clr,getActivity().getResources().getString(R.string.prdct),Constants.WrapLeftNormal+Constants.Roboto, new int[]{12,12,0,0});
        components.CustomizeImageview(prdct_share_img, new int[]{23,23}, R.drawable.share, new int[]{0,0,0,0});
        components.CustomizeButton(prdct_vw_dtls_btn, Constants.XXNormal,R.color.txt_clr,getActivity().getResources().getString(R.string.vw_detls),R.drawable.button_bg,Constants.MatchCenterNormal+Constants.Roboto, new int[]{0,50}, new int[]{0,0,0,0});
        components.CustomizeButton(addtocart_btn,Constants.XNormal,R.color.white,getActivity().getResources().getString(R.string.addtocart), R.drawable.addtocart_bg, Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,50}, new int[]{10,0,10,10});

    }

    public void fragmentcalling( Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }


}
