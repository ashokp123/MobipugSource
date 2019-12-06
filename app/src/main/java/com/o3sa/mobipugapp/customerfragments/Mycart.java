package com.o3sa.mobipugapp.customerfragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.database.Database;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.RobotTextView;

import java.util.ArrayList;

/**
 * Created by Kiran on 24-10-2018.
 */

public class Mycart extends Fragment {

    BasicComponents components;

    LinearLayout my_cart_lay,nocartlay;
    Button cartproceed_btn;
    TextView nocartitems;
    Database database;
    LinearLayout top_lay,bottom_lay;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mycartlist,container,false);

        init(v);
        AssignData();
        GetDataBaseData();
        return v;
    }

    public void init(View b){
        database=new Database(getActivity());
        database.getAllDevice();
        components=new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.mycart),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
        StoredObjects.pagetype="mycart";
        Sidemenu.updatemenu(StoredObjects.pagetype);

        my_cart_lay=(LinearLayout) b.findViewById(R.id.my_cart_lay);
        cartproceed_btn=(Button) b.findViewById(R.id.cartproceed_btn);
        nocartitems=(TextView) b.findViewById(R.id.nocartitems);
        nocartlay=(LinearLayout) b.findViewById(R.id.nocartlay);
        top_lay=(LinearLayout) b.findViewById(R.id.top_lay);
        bottom_lay=(LinearLayout) b.findViewById(R.id.bottom_lay);

        cartproceed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentcalling(getActivity(),new ReviewOrder());
            }
        });


        /*
        * http://demos.customerwebdemo.com/mobipug/app/create_order.php?customer_name=test124&email=test12@gmail.com&phone=20020200212&customer_id=1&quantity=3&sub_total=60&order_amount=60&items=[{"product_id":"1","attr_id":"1","price":"20","quantity":"3"}]*/

    }

    public void GetDataBaseData(){

        StoredObjects.mycart_array.clear();
        StoredObjects.mycart_array = database.GetMyCartData(StoredObjects.UserId);
        if(StoredObjects.mycart_array.size()==0){
            bottom_lay.setVisibility(View.GONE);
            top_lay.setVisibility(View.GONE);
            nocartlay.setVisibility(View.VISIBLE);
        }else{
            nocartlay.setVisibility(View.GONE);
            bottom_lay.setVisibility(View.VISIBLE);
            top_lay.setVisibility(View.VISIBLE);
        }

        my_cart_lay.removeAllViews();
        for (int i=0;i<StoredObjects.mycart_array.size();i++){
            addlayout(my_cart_lay,StoredObjects.mycart_array,i);
        }

    }
    public void addlayout(LinearLayout layout, final ArrayList<DumpData> my_cart_dinein_lst, final int position){

        View v	=LayoutInflater.from(getActivity()).inflate(R.layout.mycart_listitem, null);
        TextView cart_catgrynms_txt = (TextView)v.findViewById(R.id.cart_catgrynms_txt);
        TextView cart_price_txt = (TextView)v.findViewById(R.id.cart_price_txt);
        final RobotTextView cart_count_txt = (RobotTextView)v.findViewById(R.id.cart_count_txt);
        ImageView cart_plus_img=(ImageView)v.findViewById(R.id.cart_plus_img);
        ImageView cart_minus_img=(ImageView)v.findViewById(R.id.cart_minus_img);
        ImageView cart_catgrys_img=(ImageView)v.findViewById(R.id.cart_catgrys_img);
        ImageView itemdelete_img=(ImageView)v.findViewById(R.id.itemdelete_img);
        cart_count_txt.setText(my_cart_dinein_lst.get(position).cart_proqty);
        components.CustomizeTextview(cart_catgrynms_txt,Constants.XNormal,R.color.black,my_cart_dinein_lst.get(position).cart_proname,Constants.WrapLeftNormal+Constants.Gibson, new int[]{0,0,0,0});
        //pro_attri+","+pro_attriid
        String[] parts=my_cart_dinein_lst.get(position).cart_attrid.split(",");
        components.CustomizeTextview(cart_price_txt,Constants.Normal,R.color.cam_txt_clt,"Rs."+my_cart_dinein_lst.get(position).cart_proprice+"/"+parts[0],Constants.WrapLeftNormal+Constants.Gibson, new int[]{0,0,0,0});

        Glide.with(getActivity())
                .load(Uri.parse(StoredUrls.MainUrl+my_cart_dinein_lst.get(position).cart_proimg)) // add your image url
                .centerCrop() // scale to fill the ImageView and crop any extra
                .fitCenter() // scale to fit entire image within ImageView
                .placeholder(R.drawable.imagenotfound)
                .into(cart_catgrys_img);

        itemdelete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.Delete_MyCart_Items(StoredObjects.UserId,my_cart_dinein_lst.get(position).cart_proid);

                GetDataBaseData();
            }
        });

        cart_plus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int val=0;

                String quantity= cart_count_txt.getText().toString();
                val=Integer.parseInt(quantity)+1;
                IncreamentbyOne(my_cart_dinein_lst, my_cart_dinein_lst.get(position),quantity);
                database.Update_Mycart_Items(StoredObjects.UserId,my_cart_dinein_lst.get(position).cart_proid,val+"");
                GetDataBaseData();
            }
        });

        cart_minus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int val=0;

                String quantity= cart_count_txt.getText().toString();

                val=Integer.parseInt(my_cart_dinein_lst.get(position).cart_proqty);

                if(val>1){
                    DecreamentbyOne(my_cart_dinein_lst, my_cart_dinein_lst.get(position),quantity);
                    database.Update_Mycart_Items(StoredObjects.UserId,my_cart_dinein_lst.get(position).cart_proid,(val-1)+"");
                    GetDataBaseData();
                }else{
                    Toast.makeText(getActivity(), "Minimum quantity should be 1",Toast.LENGTH_LONG).show();
                }

            }
        });

        layout.addView(v);

    }

    public void IncreamentbyOne(ArrayList<DumpData> productlist, DumpData dumpData, String value) {
        int i = productlist.indexOf(dumpData);

        if (i == -1) {
            throw new IndexOutOfBoundsException();
        }

        int val=0;
        DumpData dumpData_update = new DumpData();
        dumpData_update.cart_customerid = dumpData.cart_customerid;
        dumpData_update.cart_proid = dumpData.cart_proid;
        dumpData_update.cart_proname= dumpData.cart_proname;
        dumpData_update.cart_proimg = dumpData.cart_proimg;
        dumpData_update.cart_proprice = dumpData.cart_proprice;
        dumpData_update.cart_attrid = dumpData.cart_attrid;
        val=Integer.parseInt(dumpData.cart_proqty)+1;
        dumpData_update.cart_proqty=val+"";

        productlist.remove(dumpData);
        productlist.add(i, dumpData_update);

        my_cart_lay.removeAllViews();
        for (int j=0;j<productlist.size();j++){
            addlayout(my_cart_lay,productlist,j);

        }

    }

    public void DecreamentbyOne(ArrayList<DumpData> productlist, DumpData dumpData, String value) {
        int i = productlist.indexOf(dumpData);

        if (i == -1) {
            throw new IndexOutOfBoundsException();
        }

        int val=0;

        DumpData dumpData_update = new DumpData();
        dumpData_update.cart_customerid = dumpData.cart_customerid;
        dumpData_update.cart_proid = dumpData.cart_proid;
        dumpData_update.cart_proname= dumpData.cart_proname;
        dumpData_update.cart_proimg = dumpData.cart_proimg;
        dumpData_update.cart_proprice = dumpData.cart_proprice;
        dumpData_update.cart_attrid = dumpData.cart_attrid;
        val=Integer.parseInt(dumpData.cart_proqty)-1;
        dumpData_update.cart_proqty=val+"";

        productlist.remove(dumpData);
        productlist.add(i, dumpData_update);

        my_cart_lay.removeAllViews();
        for (int j=0;j<productlist.size();j++){
            addlayout(my_cart_lay,productlist,j);

        }

    }

    public void fragmentcalling(Activity activity, Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }
    public void AssignData(){
        components.CustomizeTextview(nocartitems, Constants.XXXNormal, R.color.black, getActivity().getResources().getString(R.string.no_cartitemsfound), Constants.WrapCenterBold+ Constants.Roboto, new int[]{10,10,10,10});

        components.CustomizeButton(cartproceed_btn,Constants.XNormal,R.color.white,getActivity().getResources().getString(R.string.savendcontinue), R.drawable.addtocart_bg, Constants.MatchCenterNormal+Constants.SFUIText, new int[]{0,50}, new int[]{10,0,10,7});

    }


}

