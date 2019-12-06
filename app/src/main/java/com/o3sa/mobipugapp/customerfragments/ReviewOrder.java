package com.o3sa.mobipugapp.customerfragments;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kiran on 26-10-2018.
 */

public class ReviewOrder extends Fragment {

    BasicComponents components;

    RecyclerView cart_rvw_order_rvw;
    TextView cart_rv_ordr_txt,cart_rv_ordr_no_of_itms_txt,cart_rv_ordr_dte_txt;
    TextView cart_rv_ordr_date_time_txt,cart_rv_ordr_dlvry_dte_txt,cart_rv_ordr_dlvry_dte_txt1;
    TextView cart_rv_ordr_price_txt,cart_rv_ordr_dlvry_time_txt,cart_rv_ordr_dlvry_time_txt1,cart_rv_ordr_dlvry_addrss_txt,cart_rv_ordr_dlvry_addrss_txt1;
    ImageView cart_rv_ordr_dlvry_dte_img,cart_rv_ordr_dlvry_dte_img1,cart_rv_ordr_dlvry_time_img,cart_rv_ordr_dlvry_time_img1;
    LinearLayout cart_rv_ordr_dlvry_addrss_lay;
    Button proceed_btn;

    public ArrayList<HashMap<String, String>> profilelist;
    Database database;
    String quantity="";
    String sub_total="";
    String order_amount="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.review_order,container,false);

        init(v);
        servicecalling();
        GetDataBaseData();
        return v;

    }

    private void servicecalling() {
        parsing_methods(1, StoredUrls.CustomerGetprofile_url,"customer_id="+StoredObjects.UserId,Constants.POSTMETHOD);

    }

    public void init(View b){
        StoredObjects.pagetype="revieworder";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components = new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.revieworder),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
        database=new Database(getActivity());
        database.getAllDevice();
        cart_rvw_order_rvw = (RecyclerView)b.findViewById(R.id.cart_rvw_order_rvw);

        cart_rv_ordr_txt = (TextView)b.findViewById(R.id.cart_rv_ordr_txt);
        cart_rv_ordr_no_of_itms_txt = (TextView)b.findViewById(R.id.cart_rv_ordr_no_of_itms_txt);
        cart_rv_ordr_dte_txt = (TextView)b.findViewById(R.id.cart_rv_ordr_dte_txt);
        cart_rv_ordr_date_time_txt = (TextView)b.findViewById(R.id.cart_rv_ordr_date_time_txt);
        cart_rv_ordr_dlvry_dte_txt = (TextView)b.findViewById(R.id.cart_rv_ordr_dlvry_dte_txt);
        cart_rv_ordr_dlvry_dte_txt1 = (TextView)b.findViewById(R.id.cart_rv_ordr_dlvry_dte_txt1);
        cart_rv_ordr_dlvry_time_txt = (TextView)b.findViewById(R.id.cart_rv_ordr_dlvry_time_txt);
        cart_rv_ordr_dlvry_time_txt1 = (TextView)b.findViewById(R.id.cart_rv_ordr_dlvry_time_txt1);

        cart_rv_ordr_dlvry_addrss_txt = (TextView)b.findViewById(R.id.cart_rv_ordr_dlvry_addrss_txt);
        cart_rv_ordr_dlvry_addrss_txt1 = (TextView)b.findViewById(R.id.cart_rv_ordr_dlvry_addrss_txt1);

        cart_rv_ordr_dlvry_dte_img = (ImageView) b.findViewById(R.id.cart_rv_ordr_dlvry_dte_img);
        cart_rv_ordr_dlvry_dte_img1 = (ImageView)b.findViewById(R.id.cart_rv_ordr_dlvry_dte_img1);
        cart_rv_ordr_dlvry_time_img = (ImageView)b.findViewById(R.id.cart_rv_ordr_dlvry_time_img);
        cart_rv_ordr_dlvry_time_img1 = (ImageView)b.findViewById(R.id.cart_rv_ordr_dlvry_time_img1);
        cart_rv_ordr_price_txt = (TextView) b.findViewById(R.id.cart_rv_ordr_price_txt);
        cart_rv_ordr_dlvry_addrss_lay = (LinearLayout) b.findViewById(R.id.cart_rv_ordr_dlvry_addrss_lay);
        proceed_btn=(Button) b.findViewById(R.id.proceed_btn);

        setData();

        proceed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activatepopup(getActivity());
            }
        });

    }

    public void GetDataBaseData(){

        StoredObjects.mycart_array.clear();
        StoredObjects.mycart_array = database.GetMyCartData(StoredObjects.UserId);

         quantity="";
         sub_total="";
         order_amount="";

         for(int i=0;i<StoredObjects.mycart_array.size();i++){
             quantity=quantity+StoredObjects.mycart_array.get(i).cart_proqty;
             sub_total=sub_total+StoredObjects.mycart_array.get(i).cart_proprice;
             order_amount=order_amount+StoredObjects.mycart_array.get(i).cart_proprice;
         }

        CustomRecyclerview recyclerview = new CustomRecyclerview(getActivity());
        recyclerview.Assigndatatorecyleview(cart_rvw_order_rvw, StoredObjects.mycart_array,"cart_rv_ordr_rvw",
                Constants.Listview,0, Constants.ver_orientation,R.layout.cart_rv_ordr_lstitm);



    }

    private void Activatepopup(final Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activatepopup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        TextView dialog_alert_txt=(TextView) dialog.findViewById(R.id.dialog_alert_txt);
        Button logout_cncl_txt=(Button) dialog.findViewById(R.id.logout_cncl_txt);
        Button logout_ok_txt=(Button) dialog.findViewById(R.id.logout_ok_txt);

        components.CustomizeTextview(dialog_alert_txt,Constants.XNormal,R.color.sport_shoos_txt_clr,activity.getApplicationContext().getResources().getString(R.string.confirmtxt),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});

        components.CustomizeButton(logout_cncl_txt, Constants.Medium,R.color.black,activity.getApplicationContext().getResources().getString(R.string.cncl),R.drawable.cncl_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,40}, new int[]{0,20,0,30});
        components.CustomizeButton(logout_ok_txt, Constants.Medium,R.color.white,activity.getApplicationContext().getResources().getString(R.string.ok),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,40}, new int[]{0,20,0,30});

        logout_cncl_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        logout_ok_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                String name=profilelist.get(0).get("name");
                String email=profilelist.get(0).get("email");
                String phone=profilelist.get(0).get("phone");

                JSONArray ItemsArray = new JSONArray();
                JSONObject jsonObject = null;
                for (int i= 0;i<StoredObjects.mycart_array.size();i++) {
                    try {

                        //[{"product_id":"1","attr_id":"1","price":"12","quantity":"1"}
                            String[] parts=StoredObjects.mycart_array.get(i).cart_attrid.split(",");
                            jsonObject = new JSONObject();
                            jsonObject.put("product_id", StoredObjects.mycart_array.get(i).cart_proid);
                            jsonObject.put("attr_id", parts[1]);
                            jsonObject.put("price", StoredObjects.mycart_array.get(i).cart_proprice);
                            jsonObject.put("quantity", StoredObjects.mycart_array.get(i).cart_proqty);
                           ItemsArray.put(jsonObject);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                parsing_methods(0, StoredUrls.ConfirmOrder_url,"customer_name="+name+"&email="+email+"&phone="+phone+"&customer_id="+StoredObjects.UserId+"&quantity="+quantity+"&sub_total="+sub_total+"&order_amount="+order_amount+"&items="+ItemsArray.toString(),Constants.POSTMETHOD);


            }
        });

        dialog.show();

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
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){

                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.orderplacedmsg),getActivity());
                            }else{
                                String error = jsonObject.getString("error");

                                StoredObjects.ToastMethod(error,getActivity());
                            }
                        }catch (JSONException e){

                        }
                        StoredObjects.Services_list.get(val).countDown.cancel();


                    }else if(val==1) {
                        try {

                            profilelist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        StoredObjects.Services_list.get(val).countDown.cancel();
                    }


                }

            }

            public void onFinish() {
                StoredObjects.LogMethod("Finished ","Finished");
            }
        };

        StoredObjects.Services_list.get(val).countDown.start();

    }
    private void setData(){

        components.CustomizeImageview(cart_rv_ordr_dlvry_dte_img, new int[]{10,10}, R.drawable.up_and_down, new int[]{0,0,0,0});
        components.CustomizeImageview(cart_rv_ordr_dlvry_time_img, new int[]{10,10}, R.drawable.up_and_down, new int[]{0,0,0,0});

        components.CustomizeTextview(cart_rv_ordr_txt, Constants.XXNormal,R.color.rvw_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.rv_ordr),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(cart_rv_ordr_dte_txt, Constants.XNormal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.day),Constants.WrapLeftBold+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(cart_rv_ordr_no_of_itms_txt, Constants.Medium,R.color.rvw_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.two_i),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});

        components.CustomizeTextview(cart_rv_ordr_date_time_txt, Constants.XNormal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.dlvry_d),Constants.WrapLeftBold+Constants.Roboto, new int[]{0,8,0,0});
        components.CustomizeTextview(cart_rv_ordr_dlvry_dte_txt, Constants.XNormal,R.color.rvw_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.dlvry_dt),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,4,0,0});
        components.CustomizeTextview(cart_rv_ordr_dlvry_dte_txt1, Constants.Medium,R.color.txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.selct),Constants.WrapLeftNormal+Constants.Roboto, new int[]{12,7,7,7});

        components.CustomizeTextview(cart_rv_ordr_dlvry_time_txt, Constants.XNormal,R.color.rvw_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.dlvry_t),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,13,0,0});
        components.CustomizeTextview(cart_rv_ordr_dlvry_time_txt1, Constants.Medium,R.color.txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.selct_t),Constants.WrapLeftNormal+Constants.Roboto, new int[]{12,7,7,7});

        components.CustomizeTextview(cart_rv_ordr_dlvry_addrss_txt, Constants.XNormal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.dlvry_adrs),Constants.WrapLeftBold+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(cart_rv_ordr_dlvry_addrss_txt1, Constants.XNormal,R.color.rvw_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.dlvry_adrs),Constants.WrapLeftNormal+Constants.Roboto, new int[]{12,10,0,0});

        components.CustomizeButton(proceed_btn,Constants.XNormal,R.color.white,getActivity().getResources().getString(R.string.proceedtopay), R.drawable.addtocart_bg, Constants.MatchCenterNormal+Constants.SFUIText, new int[]{0,50}, new int[]{10,10,10,5});
        components.CustomizeTextview(cart_rv_ordr_price_txt, Constants.Medium,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.pric),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,0,0,0});


    }


}

