package com.o3sa.mobipugapp.uicomponents;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.activities.LandingPage;
import com.o3sa.mobipugapp.customerfragments.ChangePassword;
import com.o3sa.mobipugapp.customerfragments.CustomerProductlist;
import com.o3sa.mobipugapp.customerfragments.EditProfile;
import com.o3sa.mobipugapp.customerfragments.ProductDetailsMain;
import com.o3sa.mobipugapp.customerfragments.SendFeedback;
import com.o3sa.mobipugapp.customerfragments.SubCategoryList;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.fragments.EditOffers;
import com.o3sa.mobipugapp.fragments.EditProduct;
import com.o3sa.mobipugapp.fragments.ManageCategoriesMain;
import com.o3sa.mobipugapp.fragments.ManageOffersMain;
import com.o3sa.mobipugapp.fragments.ManageProductsMain;
import com.o3sa.mobipugapp.fragments.ManageSubCategoriesMain;
import com.o3sa.mobipugapp.fragments.VendorProfile;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.o3sa.mobipugapp.uicomponents.CustomRecyclerview.hashMapRecycleviewadapter;

/**
 * Created by Kiran on 02-11-2018.
 */

public class HashmapViewHolder extends RecyclerView.ViewHolder

    {

        BasicComponents components;
        Activity activity;
        //Manage products
        TextView prdctname_tx, prdctcost_tx, prdctdiscntprice_tx, prdctdiscnt_tx, procount_tx, prdctmange_tx;
        RatingBar pro_rating;
        ImageView prdctbrnd_img, prdctmange_img;
        //offersliat
        ImageView ofrs_brnd_img,ofrs_mange_img;
        TextView ofrs_brnd_tx,ofrs_prmocde_tx,ofrs_prmocde1_tx,ofrs_strtdte_tx,ofrs_strtdte1_tx,ofrs_endate_tx,ofrs_endate1_tx,ofrs_discnt_tx,ofrs_mange_tx;
        LinearLayout ofers_mange_lay,itemmain_lay;

        TextView cat_detls_name_tx,cat_detls_cost_tx,cat_detls_discntprice_tx,prostock_tx,cat_detls_discnt_tx,cat_detls_ratngcount_tx;
        RatingBar cat_detls__rating;
        ImageView cat_detls_brnd_img;
        LinearLayout catgry_lstitm_lay,prdctmange_lay,mproducts_lay;

        CircularImageView cat_prfle_img;
        TextView cat_nms_txt,sbcat_nms_txt;

        CircularImageView sbcat_prfle_img;
        ImageView sbcat_arrw_img;

        ImageView cat_detls_grd_brnd_img;
        TextView cat_detls_grd_name_tx,cat_detls_grd_cost_tx,cat_detls_grd_discntprice_tx,cat_detls_grd_discnt_tx,cat_detls_grd_ratngcount_tx,gridprostock_tx;
        RatingBar cat_detls__grdrating;
        LinearLayout ctgry_griditm_lay;

        //myorders
        TextView myodr_numbr_txt,myodr_num_txt,myodr_bokdate_txt,myodr_catgrynms_txt,myodr_price_txt,myodr_mangeodr_txt;
        ImageView myodr_catgrys_img;
        LinearLayout orderdetailsmain_lay,orderdetails_lay;

        private PopupWindow mPopupWindow;
        ArrayList<HashMap<String, String>> imageslist=new ArrayList<>();
        ArrayList<HashMap<String, String>> attrbutslist=new ArrayList<>();
        ArrayList<HashMap<String, String>> orderitemslist=new ArrayList<>();
        ArrayList<HashMap<String, String>> offerslist=new ArrayList<>();

        TextView catmnge_nms_txt,setstatus_txt;
        LinearLayout catsetstatus_lay;
        ImageView catmnge_prfle_img;
        ImageView wishlistadded_img,wlpro_img;
        TextView wl_procost_tx,wl_proname_tx,wl_prostatus_tx;
        LinearLayout offers_bglay,moffers_bglay,products_lay;

        //Cstmrlandng
        TextView clandng_brndnme_tx,clandng_weight_tx,clandng_price_tx,clandng_dscunt_tx,clandng_strtdte_tx,
                clandng_strtshwdte_tx,clandng_enddte_tx,clandng_endshwdte_tx;
        ImageView clandng_brnd_img;
        TextView odr_pp_odrnum_txt,odr_pp_brndnme_txt,odr_pp_brndprce_txt;
        public HashmapViewHolder(View convertView, String type, final Activity activity){

        super(convertView);

        this.activity = activity;
        components = new BasicComponents(activity);
         if(type.equalsIgnoreCase("clandng")){

                clandng_brnd_img = (ImageView)convertView.findViewById(R.id.clandng_brnd_img);
                clandng_brndnme_tx = (TextView) convertView.findViewById(R.id.clandng_brndnme_tx);
                clandng_weight_tx = (TextView) convertView.findViewById(R.id.clandng_weight_tx);
                clandng_price_tx = (TextView) convertView.findViewById(R.id.clandng_price_tx);
                clandng_dscunt_tx = (TextView) convertView.findViewById(R.id.clandng_dscunt_tx);
                clandng_strtdte_tx = (TextView) convertView.findViewById(R.id.clandng_strtdte_tx);
                clandng_strtshwdte_tx = (TextView) convertView.findViewById(R.id.clandng_strtshwdte_tx);
                clandng_enddte_tx = (TextView) convertView.findViewById(R.id.clandng_enddte_tx);
                clandng_endshwdte_tx = (TextView) convertView.findViewById(R.id.clandng_endshwdte_tx);

            }
            else if (type.equalsIgnoreCase("productslist")) {
            prdctbrnd_img = (ImageView) convertView.findViewById(R.id.prdctbrnd_img);
            prdctmange_img = (ImageView) convertView.findViewById(R.id.prdctmange_img);
            prdctname_tx = (TextView) convertView.findViewById(R.id.prdctname_tx);
            prdctcost_tx = (TextView) convertView.findViewById(R.id.prdctcost_tx);
            prdctdiscntprice_tx = (TextView) convertView.findViewById(R.id.prdctdiscntprice_tx);
            prdctdiscnt_tx = (TextView) convertView.findViewById(R.id.prdctdiscnt_tx);
            pro_rating = (RatingBar) convertView.findViewById(R.id.pro_rating);
            procount_tx = (TextView) convertView.findViewById(R.id.procount_tx);
            prdctmange_tx = (TextView) convertView.findViewById(R.id.prdctmange_tx);
            products_lay=(LinearLayout) convertView.findViewById(R.id.products_lay);
        }else if (type.equalsIgnoreCase("offerslist")) {
            ofrs_brnd_img = (ImageView)convertView.findViewById(R.id.ofrs_brnd_img);
            ofrs_mange_img = (ImageView)convertView.findViewById(R.id.ofrs_mange_img);
            ofrs_brnd_tx = (TextView) convertView.findViewById(R.id.ofrs_brnd_tx);
            ofrs_prmocde_tx = (TextView) convertView.findViewById(R.id.ofrs_prmocde_tx);
            ofrs_prmocde1_tx = (TextView) convertView.findViewById(R.id.ofrs_prmocde1_tx);
            ofrs_strtdte_tx = (TextView) convertView.findViewById(R.id.ofrs_strtdte_tx);
            ofrs_strtdte1_tx = (TextView) convertView.findViewById(R.id.ofrs_strtdte1_tx);
            ofrs_endate_tx = (TextView) convertView.findViewById(R.id.ofrs_endate_tx);
            ofrs_endate1_tx = (TextView) convertView.findViewById(R.id.ofrs_endate1_tx);
            ofrs_discnt_tx = (TextView) convertView.findViewById(R.id.ofrs_discnt_tx);
            ofrs_mange_tx = (TextView) convertView.findViewById(R.id.ofrs_mange_tx);
            offers_bglay=(LinearLayout) convertView.findViewById(R.id.offers_bglay);
        }else if (type.equalsIgnoreCase("manageoffers")) {
            ofrs_brnd_img = (ImageView)convertView.findViewById(R.id.ofrs_brnd_img);
            ofrs_mange_img = (ImageView)convertView.findViewById(R.id.ofrs_mange_img);
            ofrs_brnd_tx = (TextView) convertView.findViewById(R.id.ofrs_brnd_tx);
            ofrs_prmocde_tx = (TextView) convertView.findViewById(R.id.ofrs_prmocde_tx);
            ofrs_prmocde1_tx = (TextView) convertView.findViewById(R.id.ofrs_prmocde1_tx);
            ofrs_strtdte_tx = (TextView) convertView.findViewById(R.id.ofrs_strtdte_tx);
            ofrs_strtdte1_tx = (TextView) convertView.findViewById(R.id.ofrs_strtdte1_tx);
            ofrs_endate_tx = (TextView) convertView.findViewById(R.id.ofrs_endate_tx);
            ofrs_endate1_tx = (TextView) convertView.findViewById(R.id.ofrs_endate1_tx);
            ofrs_discnt_tx = (TextView) convertView.findViewById(R.id.ofrs_discnt_tx);
            ofrs_mange_tx = (TextView) convertView.findViewById(R.id.ofrs_mange_tx);
            ofers_mange_lay=(LinearLayout) convertView.findViewById(R.id.ofers_mange_lay);
            moffers_bglay=(LinearLayout) convertView.findViewById(R.id.moffers_bglay);
        }else if (type.equalsIgnoreCase("manageproducts")) {
            prdctbrnd_img = (ImageView)convertView.findViewById(R.id.prdctbrnd_img);
            prdctmange_img = (ImageView)convertView.findViewById(R.id.prdctmange_img);
            prdctname_tx = (TextView) convertView.findViewById(R.id.prdctname_tx);
            prdctcost_tx = (TextView) convertView.findViewById(R.id.prdctcost_tx);
            prdctdiscntprice_tx = (TextView) convertView.findViewById(R.id.prdctdiscntprice_tx);
            prdctdiscnt_tx = (TextView) convertView.findViewById(R.id.prdctdiscnt_tx);
            pro_rating = (RatingBar) convertView.findViewById(R.id.pro_rating);
            procount_tx = (TextView) convertView.findViewById(R.id.procount_tx);
            prdctmange_tx = (TextView) convertView.findViewById(R.id.prdctmange_tx);
            prdctmange_lay=(LinearLayout) convertView.findViewById(R.id.prdctmange_lay);
            mproducts_lay=(LinearLayout) convertView.findViewById(R.id.mproducts_lay);
        } else if (type.equalsIgnoreCase("c_categrylist")) {
            cat_prfle_img = (CircularImageView)convertView.findViewById(R.id.cat_prfle_img);
            cat_nms_txt = (TextView) convertView.findViewById(R.id.cat_nms_txt);
            itemmain_lay=(LinearLayout) convertView.findViewById(R.id.itemmain_lay);
        } else if (type.equalsIgnoreCase("c_mangecategrylist")) {
            catmnge_prfle_img = (CircularImageView)convertView.findViewById(R.id.catmnge_prfle_img);
            catmnge_nms_txt = (TextView) convertView.findViewById(R.id.catmnge_nms_txt);
            setstatus_txt=(TextView) convertView.findViewById(R.id.setstatus_txt);
            catsetstatus_lay=(LinearLayout) convertView.findViewById(R.id.catsetstatus_lay);
            itemmain_lay=(LinearLayout) convertView.findViewById(R.id.itemmain_lay);

        } else if (type.equalsIgnoreCase("c_mangesubcategrylist")) {
            catmnge_prfle_img = (CircularImageView)convertView.findViewById(R.id.catmnge_prfle_img);
            catmnge_nms_txt = (TextView) convertView.findViewById(R.id.catmnge_nms_txt);
            setstatus_txt=(TextView) convertView.findViewById(R.id.setstatus_txt);
            catsetstatus_lay=(LinearLayout) convertView.findViewById(R.id.catsetstatus_lay);
            itemmain_lay=(LinearLayout) convertView.findViewById(R.id.itemmain_lay);
        } else if (type.equalsIgnoreCase("c_subcategrylist")) {
            cat_prfle_img = (CircularImageView)convertView.findViewById(R.id.cat_prfle_img);
            cat_nms_txt = (TextView) convertView.findViewById(R.id.cat_nms_txt);
            itemmain_lay=(LinearLayout) convertView.findViewById(R.id.itemmain_lay);
        }else if (type.equalsIgnoreCase("catgrydetails")) {
            cat_detls_brnd_img = (ImageView) convertView.findViewById(R.id.cat_detls_brnd_img);
            cat_detls_name_tx = (TextView) convertView.findViewById(R.id.cat_detls_name_tx);
            cat_detls_cost_tx = (TextView) convertView.findViewById(R.id.cat_detls_cost_tx);
            cat_detls_discntprice_tx = (TextView) convertView.findViewById(R.id.cat_detls_discntprice_tx);
            cat_detls_discnt_tx = (TextView) convertView.findViewById(R.id.cat_detls_discnt_tx);
            cat_detls__rating = (RatingBar) convertView.findViewById(R.id.cat_detls__rating);
            cat_detls_ratngcount_tx = (TextView) convertView.findViewById(R.id.cat_detls_ratngcount_tx);
            catgry_lstitm_lay = (LinearLayout)convertView.findViewById(R.id.catgry_lstitm_lay);
            prostock_tx=(TextView) convertView.findViewById(R.id.prostock_tx);
        }else if(type.equalsIgnoreCase("catgrygrddetails")){
            cat_detls_grd_brnd_img = (ImageView)convertView.findViewById(R.id.prdetls_grd_brnd_img);
            cat_detls_grd_name_tx = (TextView) convertView.findViewById(R.id.cat_detls_grd_name_tx);
            cat_detls_grd_cost_tx = (TextView) convertView.findViewById(R.id.cat_detls_grd_cost_tx);
            cat_detls_grd_discntprice_tx = (TextView) convertView.findViewById(R.id.cat_detls_grd_discntprice_tx);
            cat_detls_grd_discnt_tx = (TextView) convertView.findViewById(R.id.cat_detls_grd_discnt_tx);
            cat_detls__grdrating = (RatingBar) convertView.findViewById(R.id.cat_detls__grdrating);
            cat_detls_grd_ratngcount_tx = (TextView) convertView.findViewById(R.id.cat_detls_grd_ratngcount_tx);
            ctgry_griditm_lay = (LinearLayout)convertView.findViewById(R.id.ctgry_griditm_lay);

            gridprostock_tx=(TextView) convertView.findViewById(R.id.gridprostock_tx);
        }else if (type.equalsIgnoreCase("categry")) {
            sbcat_prfle_img = (CircularImageView)convertView.findViewById(R.id.sbcat_prfle_img);
            sbcat_arrw_img = (ImageView)convertView.findViewById(R.id.sbcat_arrw_img);
            sbcat_nms_txt = (TextView) convertView.findViewById(R.id.sbcat_nms_txt);
            itemmain_lay=(LinearLayout) convertView.findViewById(R.id.itemmain_lay);
        }else   if (type.equalsIgnoreCase("subcategry")) {
            sbcat_prfle_img = (CircularImageView)convertView.findViewById(R.id.sbcat_prfle_img);
            sbcat_arrw_img = (ImageView)convertView.findViewById(R.id.sbcat_arrw_img);
            sbcat_nms_txt = (TextView) convertView.findViewById(R.id.sbcat_nms_txt);
            itemmain_lay=(LinearLayout) convertView.findViewById(R.id.itemmain_lay);
        }else if (type.equalsIgnoreCase("myorders")) {
            myodr_catgrys_img = (ImageView)convertView.findViewById(R.id.myodr_catgrys_img);
            myodr_numbr_txt = (TextView) convertView.findViewById(R.id.myodr_numbr_txt);
            myodr_num_txt = (TextView) convertView.findViewById(R.id.myodr_num_txt);
            myodr_bokdate_txt = (TextView) convertView.findViewById(R.id.myodr_bokdate_txt);
            myodr_catgrynms_txt = (TextView) convertView.findViewById(R.id.myodr_catgrynms_txt);
            myodr_price_txt = (TextView) convertView.findViewById(R.id.myodr_price_txt);
            myodr_mangeodr_txt = (TextView) convertView.findViewById(R.id.myodr_mangeodr_txt);
            orderdetailsmain_lay=(LinearLayout) convertView.findViewById(R.id.orderdetailsmain_lay);
            orderdetails_lay=(LinearLayout) convertView.findViewById(R.id.orderdetails_lay);

        } else if(type.equalsIgnoreCase("orderpopup")){
            odr_pp_odrnum_txt = (TextView) convertView.findViewById(R.id.odr_pp_odrnum_txt);
            odr_pp_brndnme_txt = (TextView) convertView.findViewById(R.id.odr_pp_brndnme_txt);
            odr_pp_brndprce_txt = (TextView) convertView.findViewById(R.id.odr_pp_brndprce_txt);
        }
        else if(type.equalsIgnoreCase("wishlist")){
             wlpro_img = (ImageView) convertView.findViewById(R.id.wlpro_img);
            wishlistadded_img = (ImageView) convertView.findViewById(R.id.wishlistadded_img);
            wl_procost_tx = (TextView) convertView.findViewById(R.id.wl_procost_tx);
            wl_proname_tx = (TextView) convertView.findViewById(R.id.wl_proname_tx);
            wl_prostatus_tx = (TextView) convertView.findViewById(R.id.wl_prostatus_tx);
        }


    }

    public void assign_data(final ArrayList<HashMap<String, String>> datalist, final int position, String formtype) { //ArrayList<HashMap<String, String>> datalist

           if (formtype.equalsIgnoreCase("clandng")) {
            components.CustomizeTextview(clandng_brndnme_tx, Constants.XXNormal,R.color.sport_shoos_txt_clr,datalist.get(position).get("product_name"),Constants.WrapLeftBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(clandng_strtdte_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"Start Date : ",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(clandng_strtshwdte_tx, Constants.XSmall,R.color.serch_nrml_clr,datalist.get(position).get("start_date"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.CustomizeTextview(clandng_enddte_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"End Date : ",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(clandng_endshwdte_tx, Constants.XSmall,R.color.serch_nrml_clr,datalist.get(position).get("end_date"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});

                   Glide.with(activity)
                           .load(Uri.parse(StoredUrls.MainUrl+datalist.get(position).get("image"))) // add your image url
                           .centerCrop() // scale to fill the ImageView and crop any extra
                           .fitCenter() // scale to fit entire image within ImageView
                           .placeholder(R.drawable.imagenotfound)
                           .into(clandng_brnd_img);

               if(!datalist.get(position).get("attributes").equalsIgnoreCase("[]")){
                   try {
                       attrbutslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("attributes"));

                   }catch (Exception e){

                   }
               }

               Double discount=0.0;
               Double discountval=0.0;
               if(attrbutslist.size()>0){
                   if(datalist.get(position).get("discount_type").equalsIgnoreCase("Fixed Amount")){
                       discount= calculatediscount(attrbutslist.get(0).get("price"),datalist.get(position).get("amount"),"fixed");
                       components.CustomizeTextview(clandng_dscunt_tx, Constants.Medium,R.color.serch_nrml_clr," "+discount+("% OFF"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,5,0,0});
                       discountval=Double.parseDouble(attrbutslist.get(0).get("price"))-Double.parseDouble(datalist.get(position).get("amount"));
                       components.CustomizeTextview(clandng_price_tx, Constants.Medium,R.color.ofrs_listitem_clr,"Rs."+String.format("%.0f",discountval),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});

                   }else{
                       components.CustomizeTextview(clandng_dscunt_tx, Constants.Medium,R.color.serch_nrml_clr," "+datalist.get(position).get("amount")+"% OFF",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,5,0,0});
                       discount= calculatediscount(attrbutslist.get(0).get("price"),datalist.get(position).get("amount"),"per");

                       components.CustomizeTextview(clandng_price_tx, Constants.Medium,R.color.ofrs_listitem_clr,"Rs."+String.format("%.0f",discount),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});

                   }
                   components.CustomizeTextview(clandng_weight_tx, Constants.Normal,R.color.blue_color,"Rs."+attrbutslist.get(0).get("price")+"/"+attrbutslist.get(0).get("name")+" "+attrbutslist.get(0).get("sub_attr_name"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,3,0,0});

               }else{
                   components.CustomizeTextview(clandng_dscunt_tx, Constants.Medium,R.color.ofrs_listitem_clr,"",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,5,0,0});
                   components.CustomizeTextview(clandng_weight_tx, Constants.Normal,R.color.blue_color,"Rs.0.00",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,3,0,0});
                   components.CustomizeTextview(clandng_price_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"Rs.0.00",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});

               }

        }
        else if(formtype.equalsIgnoreCase("wishlist")){


                wishlistadded_img.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       parsing_methods(2, StoredUrls.RemovefromWishlist_url,"customer_id="+StoredObjects.UserId+"&product_id="+datalist.get(position).get("product_id")+"&wishlist_id="+datalist.get(position).get("wishlist_id"),Constants.POSTMETHOD,"");

                   }
               });

           if(!datalist.get(position).get("images").equalsIgnoreCase("[]")){
               try {
                   imageslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("images"));

               }catch (Exception e){

               }
           }
           if(!datalist.get(position).get("attributes").equalsIgnoreCase("[]")){
               try {
                   attrbutslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("attributes"));

               }catch (Exception e){

               }
           }


           if(attrbutslist.size()>0){
               components.CustomizeTextview(wl_procost_tx, Constants.Normal, R.color.blue_color, "Rs."+attrbutslist.get(0).get("price")+"/"+attrbutslist.get(0).get("sub_attr_name"), Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 3, 0, 0});
           }else{
               components.CustomizeTextview(wl_procost_tx, Constants.Normal, R.color.blue_color, "Rs.0.00", Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 3, 0, 0});
           }

           if(imageslist.size()>0){
               Glide.with(activity)
                       .load(Uri.parse(StoredUrls.MainUrl+imageslist.get(0).get("image"))) // add your image url
                       // .transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                       .placeholder(R.drawable.imagenotfound)
                       .into(wlpro_img);
           }else{
               wlpro_img.setBackgroundResource(R.drawable.imagenotfound);
           }

           components.CustomizeTextview(wl_proname_tx, Constants.XXNormal, R.color.sport_shoos_txt_clr, datalist.get(position).get("product_name"), Constants.WrapLeftBold + Constants.Roboto, new int[]{0, 0, 0, 0});

           if( datalist.get(position).get("stock_status").equalsIgnoreCase("Out of Stock")){
               wl_prostatus_tx.setVisibility(View.VISIBLE);
           }else{
               wl_prostatus_tx.setVisibility(View.GONE);
           }


       }else if (formtype.equalsIgnoreCase("productslist")) {

           if(datalist.get(position).get("status").equalsIgnoreCase("Active")){
               products_lay.setBackgroundColor(activity.getResources().getColor(R.color.white));
           }else{
               products_lay.setBackgroundColor(activity.getResources().getColor(R.color.ofrs_vwe_clr));
           }

           if(!datalist.get(position).get("images").equalsIgnoreCase("[]")){
               try {
                   imageslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("images"));

               }catch (Exception e){

               }
           }
           if(!datalist.get(position).get("attributes").equalsIgnoreCase("[]")){
               try {
                   attrbutslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("attributes"));

               }catch (Exception e){

               }
           }
           float val=Float.parseFloat(datalist.get(position).get("rating"));
           pro_rating.setRating(Float.parseFloat(String.format("%.1f",val)));
               components.CustomizeTextview(procount_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"("+datalist.get(position).get("reviews_count")+")",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
               if(attrbutslist.size()>0){
               components.CustomizeTextview(prdctcost_tx, Constants.Normal, R.color.blue_color, "Rs."+attrbutslist.get(0).get("price")+"/"+attrbutslist.get(0).get("sub_attr_name"), Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 3, 0, 0});
           }else{
               components.CustomizeTextview(prdctcost_tx, Constants.Normal, R.color.blue_color, "Rs.0.00", Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 3, 0, 0});
           }



           if(imageslist.size()>0){
               Glide.with(activity)
                       .load(Uri.parse(StoredUrls.MainUrl+imageslist.get(0).get("image"))) // add your image url
                       .centerCrop() // scale to fill the ImageView and crop any extra
                       .fitCenter() // scale to fit entire image within ImageView
                       .placeholder(R.drawable.imagenotfound)
                       .into(prdctbrnd_img);
           }else{
               prdctbrnd_img.setBackgroundResource(R.drawable.imagenotfound);
           }

           if(!datalist.get(position).get("offers").equalsIgnoreCase("[]")){
               try {
                   offerslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("offers"));

               }catch (Exception e){

               }
           }
           if(offerslist.size()>0){
               prdctdiscnt_tx.setVisibility(View.VISIBLE);
               prdctdiscntprice_tx.setVisibility(View.VISIBLE);
               Double discount=0.0;
               Double discountval=0.0;


               if(attrbutslist.size()>0){
                   if(offerslist.get(0).get("discount_type").equalsIgnoreCase("Fixed Amount")){
                       discountval=Double.parseDouble(attrbutslist.get(0).get("price"))-Double.parseDouble(offerslist.get(0).get("amount"));

                       discount= calculatediscount(attrbutslist.get(0).get("price"),offerslist.get(0).get("amount"),"fixed");

                       components.CustomizeTextview(prdctdiscntprice_tx, Constants.XSmall,R.color.ofrs_listitem_clr,String.format("%.0f",discountval),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
                       components.CustomizeTextview(prdctdiscnt_tx, Constants.XSmall,R.color.serch_nrml_clr," (-"+discount+"%)",Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});

                   }else{
                       discount= calculatediscount(attrbutslist.get(0).get("price"),offerslist.get(0).get("amount"),"per");
                       components.CustomizeTextview(prdctdiscntprice_tx, Constants.XSmall,R.color.ofrs_listitem_clr,String.format("%.0f",discount),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
                       components.CustomizeTextview(prdctdiscnt_tx, Constants.XSmall,R.color.serch_nrml_clr," (-"+offerslist.get(0).get("amount")+"%)",Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});

                   }
               }else{
                   prdctdiscnt_tx.setVisibility(View.GONE);
                   prdctdiscntprice_tx.setVisibility(View.GONE);
               }

           }else{
               prdctdiscnt_tx.setVisibility(View.GONE);
               prdctdiscntprice_tx.setVisibility(View.GONE);
           }
            components.CustomizeImageview(prdctmange_img, new int[]{12, 12}, R.drawable.edit_icon, new int[]{0, 0, 0, 0});
            components.ApplyTint(prdctmange_img, R.color.white);
            components.CustomizeTextview(prdctname_tx, Constants.XXNormal, R.color.sport_shoos_txt_clr, datalist.get(position).get("product_name"), Constants.WrapLeftBold + Constants.Roboto, new int[]{0, 0, 0, 0});
              components.CustomizeTextview(prdctmange_tx, Constants.XSmall, R.color.serch_nrml_clr, activity.getResources().getString(R.string.manage), Constants.WrapCenterNormal + Constants.Roboto, new int[]{0, 0, 0, 0});

        }else if (formtype.equalsIgnoreCase("catgrydetails")) {

           if(!datalist.get(position).get("images").equalsIgnoreCase("[]")){
               try {
                   imageslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("images"));

               }catch (Exception e){

               }
           }
           if(!datalist.get(position).get("attributes").equalsIgnoreCase("[]")){
               try {
                   attrbutslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("attributes"));

               }catch (Exception e){

               }
           }

           if(attrbutslist.size()>0){
               components.CustomizeTextview(cat_detls_cost_tx, Constants.Normal, R.color.blue_color, "Rs."+attrbutslist.get(0).get("price")+"/"+attrbutslist.get(0).get("name")+" "+attrbutslist.get(0).get("sub_attr_name"), Constants.WrapLeftSemiBold + Constants.Roboto, new int[]{5, 2, 0, 0});
           }else{
               components.CustomizeTextview(cat_detls_cost_tx, Constants.Normal, R.color.blue_color, "Rs.0.00", Constants.WrapLeftSemiBold + Constants.Roboto, new int[]{5, 2, 0, 0});
           }

           if(imageslist.size()>0){
               Glide.with(activity)
                       .load(Uri.parse(StoredUrls.MainUrl+imageslist.get(0).get("image")))
                       .placeholder(R.drawable.imagenotfound)
                       .centerCrop() // scale to fill the ImageView and crop any extra
                       .fitCenter() // scale to fit entire image within ImageView
                       .into(cat_detls_brnd_img);

           }else{
               cat_detls_brnd_img.setBackgroundResource(R.drawable.imagenotfound);
           }
           if(datalist.get(position).get("stock_status").equalsIgnoreCase("Available")){
               prostock_tx.setVisibility(View.GONE);
           }else{
               prostock_tx.setVisibility(View.VISIBLE);

           }
           components.CustomizeTextviewFrameLay(prostock_tx, Constants.XXXXSmall, R.color.white, datalist.get(position).get("stock_status"), Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 0, 0, 0});
           components.CustomizeTextview(cat_detls_name_tx, Constants.Large, R.color.sport_shoos_txt_clr, datalist.get(position).get("product_name"), Constants.WrapLeftSemiBold + Constants.Roboto, new int[]{5, 0, 0, 0});

           catgry_lstitm_lay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   StoredObjects.get_product_id = datalist.get(position).get("product_id");
                   fragmentcalling(new ProductDetailsMain());
               }
           });

               float val=Float.parseFloat(datalist.get(position).get("rating"));
               cat_detls__rating.setRating(Float.parseFloat(String.format("%.1f",val)));
               components.CustomizeTextview(cat_detls_ratngcount_tx, Constants.XSmall, R.color.ofrs_listitem_clr, "("+datalist.get(position).get("reviews_count")+")", Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 0, 0, 0});

               if(!datalist.get(position).get("offers").equalsIgnoreCase("[]")){
                   try {
                       offerslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("offers"));

                   }catch (Exception e){

                   }
               }
               if(offerslist.size()>0){
                   cat_detls_discnt_tx.setVisibility(View.VISIBLE);
                   cat_detls_discntprice_tx.setVisibility(View.VISIBLE);
                   Double discount=0.0;
                   Double discountval=0.0;


                   if(attrbutslist.size()>0){
                       if(offerslist.get(0).get("discount_type").equalsIgnoreCase("Fixed Amount")){
                           discountval=Double.parseDouble(attrbutslist.get(0).get("price"))-Double.parseDouble(offerslist.get(0).get("amount"));
                           discount= calculatediscount(attrbutslist.get(0).get("price"),offerslist.get(0).get("amount"),"fixed");
                           components.CustomizeTextview(cat_detls_discntprice_tx, Constants.XSmall, R.color.ofrs_listitem_clr, String.format("%.0f",discountval), Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});
                           components.CustomizeTextview(cat_detls_discnt_tx, Constants.XSmall, R.color.ofrs_listitem_clr, " (-"+discount+"%)", Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});

                       }else{
                           discount= calculatediscount(attrbutslist.get(0).get("price"),offerslist.get(0).get("amount"),"per");
                           components.CustomizeTextview(cat_detls_discntprice_tx, Constants.XSmall, R.color.ofrs_listitem_clr,String.format("%.0f",discount), Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});
                           components.CustomizeTextview(cat_detls_discnt_tx, Constants.XSmall, R.color.ofrs_listitem_clr, " (-"+offerslist.get(0).get("amount")+"%)", Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});


                       }
                   }else{
                       cat_detls_discnt_tx.setVisibility(View.GONE);
                       cat_detls_discntprice_tx.setVisibility(View.GONE);
                   }

               }else{
                   cat_detls_discnt_tx.setVisibility(View.GONE);
                   cat_detls_discntprice_tx.setVisibility(View.GONE);
               }

           }else if (formtype.equalsIgnoreCase("catgrygrddetails")) {

           if(!datalist.get(position).get("images").equalsIgnoreCase("[]")){
               try {
                   imageslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("images"));
               }catch (Exception e){
               }
           }
           if(!datalist.get(position).get("attributes").equalsIgnoreCase("[]")){
               try {
                   attrbutslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("attributes"));
               }catch (Exception e){
               }
           }

           if(attrbutslist.size()>0){
               components.CustomizeTextview(cat_detls_grd_cost_tx, Constants.Normal, R.color.blue_color, "Rs."+attrbutslist.get(0).get("price")+"/"+attrbutslist.get(0).get("sub_attr_name"), Constants.WrapLeftSemiBold + Constants.Roboto, new int[]{5, 2, 0, 0});
           }else{
               components.CustomizeTextview(cat_detls_grd_cost_tx, Constants.Normal, R.color.blue_color, "Rs.0.00", Constants.WrapLeftSemiBold + Constants.Roboto, new int[]{5, 2, 0, 0});
           }

           if(imageslist.size()>0){
               Glide.with(activity)
                       .load(Uri.parse(StoredUrls.MainUrl+imageslist.get(0).get("image")))
                       .placeholder(R.drawable.imagenotfound)
                       .centerCrop() // scale to fill the ImageView and crop any extra
                       .fitCenter()  // scale to fit entire image within ImageView
                       .into(cat_detls_grd_brnd_img);
           }else{
               cat_detls_grd_brnd_img.setBackgroundResource(R.drawable.imagenotfound);
           }


               if(datalist.get(position).get("stock_status").equalsIgnoreCase("Available")){
                   gridprostock_tx.setVisibility(View.GONE);
               }else{
                   gridprostock_tx.setVisibility(View.VISIBLE);

               }
           components.CustomizeTextviewFrameLay(gridprostock_tx, Constants.XXXXSmall, R.color.white, datalist.get(position).get("stock_status"), Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 0, 0, 0});
           components.CustomizeTextview(cat_detls_grd_name_tx, Constants.Large, R.color.sport_shoos_txt_clr, datalist.get(position).get("product_name"), Constants.WrapLeftSemiBold + Constants.Roboto, new int[]{5, 0, 0, 0});


               float val=Float.parseFloat(datalist.get(position).get("rating"));
               cat_detls__grdrating.setRating(Float.parseFloat(String.format("%.1f",val)));
               components.CustomizeTextview(cat_detls_grd_ratngcount_tx, Constants.XSmall, R.color.ofrs_listitem_clr, "("+datalist.get(position).get("reviews_count")+")", Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 0, 0, 0});

               if(!datalist.get(position).get("offers").equalsIgnoreCase("[]")){
                   try {
                       offerslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("offers"));

                   }catch (Exception e){

                   }
               }
               if(offerslist.size()>0){
                   cat_detls_grd_discnt_tx.setVisibility(View.VISIBLE);
                   cat_detls_grd_discntprice_tx.setVisibility(View.VISIBLE);
                   Double discount=0.0;
                   Double discountval=0.0;


                   if(attrbutslist.size()>0){
                       if(offerslist.get(0).get("discount_type").equalsIgnoreCase("Fixed Amount")){
                           discountval=Double.parseDouble(attrbutslist.get(0).get("price"))-Double.parseDouble(offerslist.get(0).get("amount"));
                           discount= calculatediscount(attrbutslist.get(0).get("price"),offerslist.get(0).get("amount"),"fixed");
                           components.CustomizeTextview(cat_detls_grd_discntprice_tx, Constants.XSmall, R.color.ofrs_listitem_clr, String.format("%.0f",discountval), Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});
                           components.CustomizeTextview(cat_detls_grd_discnt_tx, Constants.XSmall, R.color.ofrs_listitem_clr, " (-"+discount+"%)", Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});

                       }else{
                           discount= calculatediscount(attrbutslist.get(0).get("price"),offerslist.get(0).get("amount"),"per");
                           components.CustomizeTextview(cat_detls_grd_discntprice_tx, Constants.XSmall, R.color.ofrs_listitem_clr,String.format("%.0f",discount), Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});
                           components.CustomizeTextview(cat_detls_grd_discnt_tx, Constants.XSmall, R.color.ofrs_listitem_clr, " (-"+offerslist.get(0).get("amount")+"%)", Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});


                       }
                   }else{
                       cat_detls_grd_discnt_tx.setVisibility(View.GONE);
                       cat_detls_grd_discntprice_tx.setVisibility(View.GONE);
                   }

               }else{
                   cat_detls_grd_discnt_tx.setVisibility(View.GONE);
                   cat_detls_grd_discntprice_tx.setVisibility(View.GONE);
               }

           ctgry_griditm_lay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   StoredObjects.get_product_id = datalist.get(position).get("product_id");

                   fragmentcalling(new ProductDetailsMain());
               }
           });

       }else if (formtype.equalsIgnoreCase("offerslist")) {

           StoredObjects.LogMethod("value","valuesss:::"+datalist.get(position).get("product_name"));
           components.CustomizeImageview(ofrs_mange_img, new int[]{12,12},R.drawable.edit_icon, new int[]{0,0,0,0});
           components.ApplyTint(ofrs_mange_img,R.color.white);
           if(datalist.get(position).get("status").equalsIgnoreCase("Active")){
               offers_bglay.setBackgroundColor(activity.getResources().getColor(R.color.white));
           }else{
               offers_bglay.setBackgroundColor(activity.getResources().getColor(R.color.ofrs_vwe_clr));
           }
           try{
               Glide.with(activity)
                       .load(Uri.parse(StoredUrls.MainUrl+datalist.get(position).get("image"))) // add your image url
                       .placeholder(R.drawable.imagenotfound)
                       .centerCrop() // scale to fill the ImageView and crop any extra
                       .fitCenter() // scale to fit entire image within ImageView
                       .into(ofrs_brnd_img);

           }catch (Exception e){

           }

           if(!datalist.get(position).get("attributes").equalsIgnoreCase("[]")){
               try {
                   attrbutslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("attributes"));

               }catch (Exception e){

               }
           }
               Double discount=0.0;
               if(attrbutslist.size()>0){
                   if(datalist.get(position).get("discount_type").equalsIgnoreCase("Fixed Amount")){
                       discount= calculatediscount(attrbutslist.get(0).get("price"),datalist.get(position).get("amount"),"fixed");
                       components.CustomizeTextview(ofrs_discnt_tx, Constants.Medium,R.color.ofrs_listitem_clr,discount+("% OFF"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,5,0,0});

                   }else{
                       components.CustomizeTextview(ofrs_discnt_tx, Constants.Medium,R.color.ofrs_listitem_clr,datalist.get(position).get("amount")+"% OFF",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,5,0,0});

                   }
               }else{
                   components.CustomizeTextview(ofrs_discnt_tx, Constants.Medium,R.color.ofrs_listitem_clr,"% OFF",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,5,0,0});

               }

           //{"status":200,"message":"success","results_count":1,"results":[{"":"test20","offer_type":"Public","discount_type":"Fixed Amount","amount":0,"start_date":"05-11-2018","end_date":"25-11-2018","product_id":1,"status":"Active","image":"admin\/uploads\/1541143447_shopimgtwo.jpg"}]}
           components.CustomizeTextview(ofrs_brnd_tx, Constants.XXNormal,R.color.sport_shoos_txt_clr,datalist.get(position).get("product_name"),Constants.WrapLeftBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_strtdte_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.strtdate),Constants.WrapRightNormal+Constants.Roboto, new int[]{0,0,0,0});
           components.CustomizeTextview(ofrs_strtdte1_tx, Constants.XSmall,R.color.ofrs_listitem_clr,datalist.get(position).get("start_date"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
           components.CustomizeTextview(ofrs_endate_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.enddate),Constants.WrapRightNormal+Constants.Roboto, new int[]{0,0,0,0});
           components.CustomizeTextview(ofrs_endate1_tx, Constants.XSmall,R.color.ofrs_listitem_clr,datalist.get(position).get("end_date"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
           components.CustomizeTextview(ofrs_mange_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.manage),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,0});

       }

       else if (formtype.equalsIgnoreCase("manageoffers")) {
           components.CustomizeImageview(ofrs_mange_img, new int[]{12,12},R.drawable.edit_icon, new int[]{0,0,0,0});
           components.ApplyTint(ofrs_mange_img,R.color.white);

           if(datalist.get(position).get("status").equalsIgnoreCase("Active")){
               moffers_bglay.setBackgroundColor(activity.getResources().getColor(R.color.white));
           }else{
               moffers_bglay.setBackgroundColor(activity.getResources().getColor(R.color.ofrs_vwe_clr));
           }

           try{
               Glide.with(activity)
                       .load(Uri.parse(StoredUrls.MainUrl+datalist.get(position).get("image"))) // add your image url
                       .placeholder(R.drawable.imagenotfound)
                       .centerCrop() // scale to fill the ImageView and crop any extra
                       .fitCenter() // scale to fit entire image within ImageView
                       .into(ofrs_brnd_img);

           }catch (Exception e){

           }

           if(!datalist.get(position).get("attributes").equalsIgnoreCase("[]")){
               try {
                   attrbutslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("attributes"));

               }catch (Exception e){

               }
           }
           Double discount=0.0;
           if(attrbutslist.size()>0){
               if(datalist.get(position).get("discount_type").equalsIgnoreCase("Fixed Amount")){
                   discount= calculatediscount(attrbutslist.get(0).get("price"),datalist.get(position).get("amount"),"fixed");
                   components.CustomizeTextview(ofrs_discnt_tx, Constants.Medium,R.color.ofrs_listitem_clr,discount+("% OFF"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,5,0,0});

               }else{
                   components.CustomizeTextview(ofrs_discnt_tx, Constants.Medium,R.color.ofrs_listitem_clr,datalist.get(position).get("amount")+"% OFF",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,5,0,0});

               }
           }else{
               components.CustomizeTextview(ofrs_discnt_tx, Constants.Medium,R.color.ofrs_listitem_clr,"% OFF",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,5,0,0});

           }
           //{"status":200,"message":"success","results_count":1,"results":[{"":"test20","offer_type":"Public","discount_type":"Fixed Amount","amount":0,"start_date":"05-11-2018","end_date":"25-11-2018","product_id":1,"status":"Active","image":"admin\/uploads\/1541143447_shopimgtwo.jpg"}]}
           components.CustomizeTextview(ofrs_brnd_tx, Constants.XXNormal,R.color.sport_shoos_txt_clr,datalist.get(position).get("product_name"),Constants.WrapLeftBold+Constants.Roboto, new int[]{0,0,0,0});
           components.CustomizeTextview(ofrs_strtdte_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.strtdate),Constants.WrapRightNormal+Constants.Roboto, new int[]{0,0,0,0});
           components.CustomizeTextview(ofrs_strtdte1_tx, Constants.XSmall,R.color.ofrs_listitem_clr,datalist.get(position).get("start_date"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
           components.CustomizeTextview(ofrs_endate_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.enddate),Constants.WrapRightNormal+Constants.Roboto, new int[]{0,0,0,0});
           components.CustomizeTextview(ofrs_endate1_tx, Constants.XSmall,R.color.ofrs_listitem_clr,datalist.get(position).get("end_date"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
           components.CustomizeTextview(ofrs_mange_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.manage),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,0});

           ofers_mange_lay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {


                   Manageofferpopup(ofers_mange_lay, datalist, position);
               }
           });
       }else if (formtype.equalsIgnoreCase("manageproducts")) {

           if(!datalist.get(position).get("images").equalsIgnoreCase("[]")){
               try {
                   imageslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("images"));

               }catch (Exception e){

               }
           }
           if(!datalist.get(position).get("attributes").equalsIgnoreCase("[]")){
               try {
                   attrbutslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("attributes"));

               }catch (Exception e){

               }
           }
           float val=Float.parseFloat(datalist.get(position).get("rating"));
           pro_rating.setRating(Float.parseFloat(String.format("%.1f",val)));

               components.CustomizeTextview(procount_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"("+datalist.get(position).get("reviews_count")+")",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});

           if(attrbutslist.size()>0){
               components.CustomizeTextview(prdctcost_tx, Constants.Normal, R.color.blue_color, "Rs."+attrbutslist.get(0).get("price")+"/"+attrbutslist.get(0).get("sub_attr_name"), Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 3, 0, 0});
           }else{
               components.CustomizeTextview(prdctcost_tx, Constants.Normal, R.color.blue_color, "Rs.0.00", Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 3, 0, 0});
           }



           if(imageslist.size()>0){
               Glide.with(activity)
                       .load(Uri.parse(StoredUrls.MainUrl+imageslist.get(0).get("image"))) // add your image url
                       .centerCrop() // scale to fill the ImageView and crop any extra
                       .fitCenter() // scale to fit entire image within ImageView
                       .placeholder(R.drawable.imagenotfound)
                       .into(prdctbrnd_img);
           }else{
               prdctbrnd_img.setBackgroundResource(R.drawable.imagenotfound);
           }

           if(!datalist.get(position).get("offers").equalsIgnoreCase("[]")){
               try {
                   offerslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("offers"));

               }catch (Exception e){

               }
           }
           if(offerslist.size()>0){
               prdctdiscnt_tx.setVisibility(View.VISIBLE);
               prdctdiscntprice_tx.setVisibility(View.VISIBLE);
               Double discount=0.0;
               Double discountval=0.0;



               if(attrbutslist.size()>0){
                   if(offerslist.get(0).get("discount_type").equalsIgnoreCase("Fixed Amount")){
                       discountval=Double.parseDouble(attrbutslist.get(0).get("price"))-Double.parseDouble(offerslist.get(0).get("amount"));

                       discount= calculatediscount(attrbutslist.get(0).get("price"),offerslist.get(0).get("amount"),"fixed");

                       components.CustomizeTextview(prdctdiscntprice_tx, Constants.XSmall,R.color.ofrs_listitem_clr,String.format("%.0f",discountval),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
                       components.CustomizeTextview(prdctdiscnt_tx, Constants.XSmall,R.color.serch_nrml_clr," (-"+discount+"%)",Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});

                   }else{
                       discount= calculatediscount(attrbutslist.get(0).get("price"),offerslist.get(0).get("amount"),"per");
                       components.CustomizeTextview(prdctdiscntprice_tx, Constants.XSmall,R.color.ofrs_listitem_clr,String.format("%.0f",discount),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
                       components.CustomizeTextview(prdctdiscnt_tx, Constants.XSmall,R.color.serch_nrml_clr," (-"+offerslist.get(0).get("amount")+"%)",Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});

                   }
               }else{
                   prdctdiscnt_tx.setVisibility(View.GONE);
                   prdctdiscntprice_tx.setVisibility(View.GONE);
               }


           }else{
               prdctdiscnt_tx.setVisibility(View.GONE);
               prdctdiscntprice_tx.setVisibility(View.GONE);
           }
           components.CustomizeImageview(prdctmange_img, new int[]{12, 12}, R.drawable.edit_icon, new int[]{0, 0, 0, 0});
           components.ApplyTint(prdctmange_img, R.color.white);
           components.CustomizeTextview(prdctname_tx, Constants.XXNormal, R.color.sport_shoos_txt_clr, datalist.get(position).get("product_name"), Constants.WrapLeftBold + Constants.Roboto, new int[]{0, 0, 0, 0});
            components.CustomizeTextview(prdctmange_tx, Constants.XSmall, R.color.serch_nrml_clr, activity.getResources().getString(R.string.manage), Constants.WrapCenterNormal + Constants.Roboto, new int[]{0, 0, 0, 0});
           prdctmange_lay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Manageproductspopup(prdctmange_lay, datalist, position);
               }
           });


           if(datalist.get(position).get("status").equalsIgnoreCase("Active")){
               mproducts_lay.setBackgroundColor(activity.getResources().getColor(R.color.white));
           }else{
               mproducts_lay.setBackgroundColor(activity.getResources().getColor(R.color.ofrs_vwe_clr));
           }

       }else if (formtype.equalsIgnoreCase("c_categrylist")) {
           components.CustomizeTextview(cat_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).get("category_name"),Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{10,0,0,0});
           itemmain_lay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    StoredObjects.CategoryId=datalist.get(position).get("category_id");
                    fragmentcalling(new ManageSubCategoriesMain());
               }
           });

           Glide.with(activity)
                   .load(Uri.parse(StoredUrls.MainUrl+datalist.get(position).get("image"))) // add your image url
                    .transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                   .placeholder(R.drawable.imagenotfound)
                   .into(cat_prfle_img);


       }

        if (formtype.equalsIgnoreCase("c_mangecategrylist")) {
            components.CustomizeTextview(catmnge_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).get("category_name"),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(setstatus_txt, Constants.XNormal,R.color.home_pg_clr,activity.getResources().getString(R.string.setstatus),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});

            catsetstatus_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    SetStatuspopup(catsetstatus_lay, datalist, position,"category");
                }
            });

            Glide.with(activity)
                    .load(Uri.parse(StoredUrls.MainUrl+datalist.get(position).get("image"))) // add your image url
                     .transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                    .placeholder(R.drawable.imagenotfound)
                    .into(catmnge_prfle_img);

            itemmain_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StoredObjects.CategoryId=datalist.get(position).get("category_id");
                    fragmentcalling(new ManageSubCategoriesMain());
                }
            });

        }else if (formtype.equalsIgnoreCase("c_mangesubcategrylist")) {
            components.CustomizeTextview(catmnge_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).get("category_name"),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(setstatus_txt, Constants.XNormal,R.color.home_pg_clr,activity.getResources().getString(R.string.setstatus),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});

            catsetstatus_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetStatuspopup(catsetstatus_lay, datalist, position,"sub-category");
                }
            });
            Glide.with(activity)
                    .load(Uri.parse(StoredUrls.MainUrl+datalist.get(position).get("image"))) // add your image url
                    .transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                    .placeholder(R.drawable.imagenotfound)
                    .into(catmnge_prfle_img);

        } else if (formtype.equalsIgnoreCase("c_subcategrylist")) {
            components.CustomizeTextview(cat_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).get("category_name"),Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{10,0,0,0});
            Glide.with(activity)
                    .load(Uri.parse(StoredUrls.MainUrl+datalist.get(position).get("image"))) // add your image url
                    .transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                    .placeholder(R.drawable.imagenotfound)
                    .into(cat_prfle_img);

        }else if (formtype.equalsIgnoreCase("categry")) {

            try{
                Glide.with(activity)
                        .load(Uri.parse(StoredUrls.MainUrl+datalist.get(position).get("image"))) // add your image url
                        //.transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                        .placeholder(R.drawable.imagenotfound)
                        .into(sbcat_prfle_img);
            }catch (Exception e){

            }

            components.CustomizeImageviewBackground(sbcat_arrw_img, new int[]{14,22}, R.drawable.backblueicon, new int[]{0,0,15,0});
            components.CustomizeTextview(sbcat_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).get("category_name"),Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{10,0,0,0});

            itemmain_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String category_id = datalist.get(position).get("category_id");

                    fragmentcallingNew(new SubCategoryList(),category_id);
                }
            });
        }else  if (formtype.equalsIgnoreCase("subcategry")) {

            try{
                Glide.with(activity)
                        .load(Uri.parse(StoredUrls.MainUrl+datalist.get(position).get("image"))) // add your image url
                        //.transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                        .placeholder(R.drawable.imagenotfound)
                        .into(sbcat_prfle_img);
            }catch (Exception e){

            }

            components.CustomizeImageviewBackground(sbcat_arrw_img, new int[]{14,22}, R.drawable.backblueicon, new int[]{0,0,15,0});
            components.CustomizeTextview(sbcat_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).get("category_name"),Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{10,0,0,0});
            itemmain_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentcalling(new CustomerProductlist());
                }
            });
        }

        else if (formtype.equalsIgnoreCase("myorders")) {
            components.CustomizeImageviewBackground(myodr_catgrys_img, new int[]{85,85},R.drawable.package_n, new int[]{10,0,0,0});
            components.CustomizeTextview(myodr_numbr_txt, Constants.Normal,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.no),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{10,0,0,0});
            components.CustomizeTextview(myodr_num_txt, Constants.Normal,R.color.blue_color,"#"+datalist.get(position).get("order_id"),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{5,0,0,0});
            components.CustomizeTextview(myodr_bokdate_txt, Constants.Small,R.color.sport_shoos_txt_clr,"Booked on "+datalist.get(position).get("createddate"),Constants.WrapRightNormal+Constants.Roboto, new int[]{0,0,10,0});
            components.CustomizeTextview(myodr_catgrynms_txt, Constants.XXNormal,R.color.sport_shoos_txt_clr,"Order Placed",Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(myodr_price_txt, Constants.Normal,R.color.blue_color,"Rs."+datalist.get(position).get("order_amount"),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{0,8,0,0});
            components.CustomizeTextview(myodr_mangeodr_txt, Constants.XXNormal,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.order_details),Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{0,14,0,14});
            myodr_mangeodr_txt.setFocusable(false);
            myodr_mangeodr_txt.setClickable(false);
            orderdetails_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        orderitemslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("items"));
                    }catch (Exception e){

                    }
                    OrderDetailsPopup(activity,orderitemslist,datalist,position);
                }
            });
            orderdetailsmain_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        orderitemslist=JsonParsing.GetInnerJsonData(datalist.get(position).get("items"));
                    }catch (Exception e){

                    }

                    OrderDetailsPopup(activity,orderitemslist,datalist,position);
                }
            });

        }else if (formtype.equalsIgnoreCase("orderpopup")) {
            components.CustomizeTextview(odr_pp_odrnum_txt, Constants.Small, R.color.sport_shoos_txt_clr,(position+1)+"."+ datalist.get(position).get("product_name"), Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(odr_pp_brndnme_txt, Constants.Small, R.color.sport_shoos_txt_clr, datalist.get(position).get("quantity"), Constants.WrapCenterNormal+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(odr_pp_brndprce_txt, Constants.Small, R.color.sport_shoos_txt_clr,"Rs."+ datalist.get(position).get("attribute_price"), Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,0});
        }

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

        public void fragmentcalling(Fragment fragment) {

        FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack("").commit();

    }

        public void fragmentcallingNew(Fragment fragment, String category_id){

            FragmentManager fragmentManager = ((FragmentActivity)activity).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.addToBackStack("");
            Bundle bundle = new Bundle();
            bundle.putString("category_id",category_id);
            fragment.setArguments(bundle);
            fragmentTransaction.commit();

        }

        public void updateProductJsonData(ArrayList<HashMap<String, String>> list, HashMap<String, String> HashMapdata, int postion,String val) {

            int i = list.indexOf(HashMapdata);

            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            HashMap<String, String> dumpData_update = new HashMap<String, String>();
            dumpData_update.put("product_id", HashMapdata.get("product_id"));
            dumpData_update.put("product_name", HashMapdata.get("product_name"));
            dumpData_update.put("category_id", HashMapdata.get("category_id"));
            dumpData_update.put("sub_category_id", HashMapdata.get("sub_category_id"));
            dumpData_update.put("category_name", HashMapdata.get("category_name"));
            dumpData_update.put("stock_status", HashMapdata.get("stock_status"));
            dumpData_update.put("sub_cat_name", HashMapdata.get("sub_cat_name"));
            dumpData_update.put("attributes_count", HashMapdata.get("attributes_count"));
            dumpData_update.put("attributes", HashMapdata.get("attributes"));
            dumpData_update.put("images_count", HashMapdata.get("images_count"));
            dumpData_update.put("images", HashMapdata.get("images"));
            dumpData_update.put("rating", HashMapdata.get("rating"));

            dumpData_update.put("reviews_count", HashMapdata.get("reviews_count"));
            dumpData_update.put("reviews", HashMapdata.get("reviews"));
            dumpData_update.put("one_star_rating", HashMapdata.get("one_star_rating"));
            dumpData_update.put("two_star_rating", HashMapdata.get("two_star_rating"));

            dumpData_update.put("three_star_rating", HashMapdata.get("three_star_rating"));
            dumpData_update.put("four_star_rating", HashMapdata.get("four_star_rating"));
            dumpData_update.put("five_star_rating", HashMapdata.get("five_star_rating"));
            dumpData_update.put("offers_count", HashMapdata.get("offers_count"));
            dumpData_update.put("offers", HashMapdata.get("offers"));
            dumpData_update.put("status", val);
            list.remove(HashMapdata);
            list.add(postion,dumpData_update);
            hashMapRecycleviewadapter.notifyDataSetChanged();


        }
        public void updateOffersData(ArrayList<HashMap<String, String>> list, HashMap<String, String> HashMapdata, int postion,String val) {

            int i = list.indexOf(HashMapdata);

            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            HashMap<String, String> dumpData_update = new HashMap<String, String>();

                dumpData_update.put("offer_id", HashMapdata.get("offer_id"));
                dumpData_update.put("product_name", HashMapdata.get("product_name"));
                dumpData_update.put("promo_code", HashMapdata.get("promo_code"));
                dumpData_update.put("offer_type", HashMapdata.get("offer_type"));
                dumpData_update.put("discount_type", HashMapdata.get("discount_type"));

                dumpData_update.put("amount", HashMapdata.get("amount"));
                dumpData_update.put("start_date", HashMapdata.get("start_date"));
                dumpData_update.put("end_date", HashMapdata.get("end_date"));
                dumpData_update.put("product_id",HashMapdata.get("product_id"));
                 dumpData_update.put("status", val);
                dumpData_update.put("image",HashMapdata.get("image"));
            dumpData_update.put("lat",HashMapdata.get("lat"));
            dumpData_update.put("lng",HashMapdata.get("lng"));
            dumpData_update.put("attributes_count",HashMapdata.get("attributes_count"));
            dumpData_update.put("attributes",HashMapdata.get("attributes"));

            list.remove(HashMapdata);
            list.add(postion,dumpData_update);
            hashMapRecycleviewadapter.notifyDataSetChanged();


        }
    public void UpdatePriceRngeLayout(ArrayList<DumpData> updatelist, DumpData dumpData, String value, int position) {
        ArrayList<DumpData> datalist = new ArrayList<>();
        int i = updatelist.indexOf(dumpData);

        if (i == -1) {
            throw new IndexOutOfBoundsException();
        }

        DumpData dumpData_update = new DumpData();

        dumpData_update.price = dumpData.price;
        dumpData_update.layout_highlight = value;

        updatelist.remove(dumpData);
        updatelist.add(i, dumpData_update);

        datalist.clear();
        for (int k = 0; k < updatelist.size(); k++) {
            DumpData data = new DumpData();
            data.price = updatelist.get(k).price;
            if (position == k) {
                data.layout_highlight = "Yes";
            } else {
                data.layout_highlight = "No";
            }
            datalist.add(data);
        }

        CustomRecyclerview recyclerview = new CustomRecyclerview(activity);
        recyclerview.Assigndatatorecyleview(CustomerProductlist.price_rnge_rvw, datalist, "price_rnge_rvww", Constants.Gridview, 4,
                Constants.ver_orientation, R.layout.fltr_price_rnge_lstitm);

    }

    public void UpdateOffrRngeLayout(ArrayList<DumpData> updatelist1, DumpData dumpData, String value, int position) {
        ArrayList<DumpData> datalist = new ArrayList<>();
        int i = updatelist1.indexOf(dumpData);

        if (i == -1) {
            throw new IndexOutOfBoundsException();
        }

        DumpData dumpData_update1 = new DumpData();

        dumpData_update1.price = dumpData.price;
        dumpData_update1.layout_highlight = value;
        updatelist1.remove(dumpData);
        updatelist1.add(i, dumpData_update1);

        datalist.clear();
        for (int k = 0; k < updatelist1.size(); k++) {
            DumpData data = new DumpData();
            data.price = updatelist1.get(k).price;
            if (position == k) {
                data.layout_highlight = "Yes";
            } else {
                data.layout_highlight = "No";
            }
            datalist.add(data);
        }

        CustomRecyclerview recyclerview1 = new CustomRecyclerview(activity);
        recyclerview1.Assigndatatorecyleview(CustomerProductlist.offr_rnge_rvw, datalist, "offr_rnge_rvww", Constants.Gridview, 4,
                Constants.ver_orientation, R.layout.offr_rnge_lstitm);

    }



        private void Manageofferpopup(LinearLayout viewpager_lay,final  ArrayList<HashMap<String, String>> datalist,final int position){

            LayoutInflater mLayoutInflater=LayoutInflater.from(activity);

            View mView=mLayoutInflater.inflate(R.layout.manage_ofr_popup, null);

            mPopupWindow=new PopupWindow(mView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setContentView(mView);

            LinearLayout mnage_ofrs_lst_lay = (LinearLayout)mView.findViewById(R.id.mnage_ofrs_lst_lay);
            LinearLayout mange_actv_lay = (LinearLayout)mView.findViewById(R.id.mange_actv_lay);
            ImageView mange_actv_img = (ImageView)mView.findViewById(R.id.mange_actv_img);
            TextView mange_actv_tx = (TextView)mView.findViewById(R.id.mange_actv_tx);

            LinearLayout mange_inactv_lay = (LinearLayout)mView.findViewById(R.id.mange_inactv_lay);
            ImageView mange_inactv_img = (ImageView)mView.findViewById(R.id.mange_inactv_img);
            TextView mange_inactv_tx = (TextView)mView.findViewById(R.id.mange_inactv_tx);

            LinearLayout mange_mange_lay = (LinearLayout)mView.findViewById(R.id.mange_mange_lay);
            ImageView mange_mange_img = (ImageView)mView.findViewById(R.id.mange_mange_img);
            TextView mange_mange_tx = (TextView)mView.findViewById(R.id.mange_mange_tx);

            LinearLayout mange_delt_lay = (LinearLayout)mView.findViewById(R.id.mange_delt_lay);
            ImageView mange_delt_img = (ImageView)mView.findViewById(R.id.mange_delt_img);
            TextView mange_delt_tx = (TextView)mView.findViewById(R.id.mange_delt_tx);

            LinearLayout down_arw_lay = (LinearLayout)mView.findViewById(R.id.down_arw_lay);
            LinearLayout up_arw_lay = (LinearLayout)mView.findViewById(R.id.up_arw_lay);

            ImageView mange_uparow_img = (ImageView)mView.findViewById(R.id.mange_uparow_img);
            ImageView mange_dwnarow_img = (ImageView)mView.findViewById(R.id.mange_dwnarow_img);
            up_arw_lay.setVisibility(View.VISIBLE);
            down_arw_lay.setVisibility(View.GONE);


            if(datalist.get(position).get("status").equalsIgnoreCase("Active")){
                components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.actve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});

            }else{
                components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.inactve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});

            }
            components.CustomizeImageview(mange_uparow_img, new int[]{55,40},R.drawable.up_arroww_, new int[]{0,0,5,-10});
            //components.ApplyTint(mange_uparow_img,R.color.blue_color);
            components.CustomizeImageview(mange_dwnarow_img, new int[]{45,35},R.drawable.down_arroww, new int[]{0,-10,5,0});
            components.CustomizeImageview(mange_actv_img, new int[]{20,20},R.drawable.deleteimage, new int[]{10,8,3,8});
          //  components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.actve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.ApplyTint(mange_actv_img,R.color.white);
            components.CustomizeImageview(mange_inactv_img, new int[]{20,20},R.drawable.deleteimage, new int[]{10,8,3,8});
            components.CustomizeTextview(mange_inactv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.inactve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.ApplyTint(mange_inactv_img,R.color.white);
            components.CustomizeImageview(mange_mange_img, new int[]{20,20},R.drawable.deleteimage, new int[]{10,8,3,8});
            components.CustomizeTextview(mange_mange_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.manage),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.ApplyTint(mange_mange_img,R.color.white);
            components.CustomizeImageview(mange_delt_img, new int[]{20,20},R.drawable.deleteimage, new int[]{10,8,3,8});
            components.CustomizeTextview(mange_delt_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.delete),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.ApplyTint(mange_delt_img,R.color.white);
            Drawable d = new ColorDrawable(Color.WHITE);

            d.setAlpha(200);

            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

            mPopupWindow.showAsDropDown(viewpager_lay,0,0, Gravity.CENTER);

            //getWindow().setBackgroundDrawable(d);

            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    Drawable d = new ColorDrawable(Color.WHITE);
                    //getWindow().setBackgroundDrawable(d);
                }
            });

            mange_actv_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=datalist.get(position).get("offer_id");
                    String status="";
                    if(datalist.get(position).get("status").equalsIgnoreCase("Active")){
                        status="Inactive";
                    }else{
                        status="Active";
                    }
                    updateOffersData(datalist,datalist.get(position),position,status);
                    String product_id =datalist.get(position).get("product_id");

                    mPopupWindow.dismiss();
                    Activatepopup(activity,"ofr",id,status,StoredObjects.UserId,product_id);

                }
            });


            mange_mange_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StoredObjects.OfferId=datalist.get(position).get("offer_id");
                    StoredObjects.offerslist.clear();
                    StoredObjects.offerslist.add(datalist.get(position));
                    StoredObjects.position=position;
                    fragmentcalling(new EditOffers());

                    mPopupWindow.dismiss();
                }
            });

            mange_delt_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // DisplayCnclPopup(activity,"offers");
                    String id=datalist.get(position).get("offer_id");
                    String product_id=datalist.get(position).get("product_id");
                    Deletepopup(activity,"ofr",id,StoredObjects.UserId,product_id);
                    mPopupWindow.dismiss();
                }
            });

        }



        private void Manageproductspopup(LinearLayout viewpager_lay,final ArrayList<HashMap<String, String>> datalist, final int position){

            LayoutInflater mLayoutInflater=LayoutInflater.from(activity);

            View mView=mLayoutInflater.inflate(R.layout.manage_ofr_popup, null);

            mPopupWindow=new PopupWindow(mView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setContentView(mView);


            LinearLayout mnage_ofrs_lst_lay = (LinearLayout)mView.findViewById(R.id.mnage_ofrs_lst_lay);
            LinearLayout mange_actv_lay = (LinearLayout)mView.findViewById(R.id.mange_actv_lay);
            ImageView mange_actv_img = (ImageView)mView.findViewById(R.id.mange_actv_img);
            TextView mange_actv_tx = (TextView)mView.findViewById(R.id.mange_actv_tx);

            LinearLayout mange_inactv_lay = (LinearLayout)mView.findViewById(R.id.mange_inactv_lay);
            ImageView mange_inactv_img = (ImageView)mView.findViewById(R.id.mange_inactv_img);
            TextView mange_inactv_tx = (TextView)mView.findViewById(R.id.mange_inactv_tx);

            LinearLayout mange_mange_lay = (LinearLayout)mView.findViewById(R.id.mange_mange_lay);
            ImageView mange_mange_img = (ImageView)mView.findViewById(R.id.mange_mange_img);
            TextView mange_mange_tx = (TextView)mView.findViewById(R.id.mange_mange_tx);

            LinearLayout mange_delt_lay = (LinearLayout)mView.findViewById(R.id.mange_delt_lay);
            ImageView mange_delt_img = (ImageView)mView.findViewById(R.id.mange_delt_img);
            TextView mange_delt_tx = (TextView)mView.findViewById(R.id.mange_delt_tx);

            LinearLayout down_arw_lay = (LinearLayout)mView.findViewById(R.id.down_arw_lay);
            LinearLayout up_arw_lay = (LinearLayout)mView.findViewById(R.id.up_arw_lay);

            ImageView mange_uparow_img = (ImageView)mView.findViewById(R.id.mange_uparow_img);
            ImageView mange_dwnarow_img = (ImageView)mView.findViewById(R.id.mange_dwnarow_img);
            up_arw_lay.setVisibility(View.VISIBLE);
            down_arw_lay.setVisibility(View.GONE);
                   /* if (position>centerPosition){
                        up_arw_lay.setVisibility(View.GONE);
                        down_arw_lay.setVisibility(View.VISIBLE);

                        mPopupWindow.showAtLocation(viewpager_lay, Gravity.BOTTOM, 210, 210);

                    }else{
                    up_arw_lay.setVisibility(View.VISIBLE);
                    down_arw_lay.setVisibility(View.GONE);
                }
*/
            if(datalist.get(position).get("status").equalsIgnoreCase("Active")){
                components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.actve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});

            }else{
                components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.inactve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});

            }
            components.CustomizeImageview(mange_uparow_img, new int[]{45,35},R.drawable.up_arroww_, new int[]{0,0,5,-10});
            //components.ApplyTint(mange_uparow_img,R.color.blue_color);
            components.CustomizeImageview(mange_dwnarow_img, new int[]{45,35},R.drawable.down_arroww, new int[]{0,-10,5,0});
            components.CustomizeImageview(mange_actv_img, new int[]{20,20},R.drawable.deleteiconrounded, new int[]{10,8,3,8});
           // components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.actve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.ApplyTint(mange_actv_img,R.color.white);
            components.CustomizeImageview(mange_inactv_img, new int[]{20,20},R.drawable.deleteiconrounded, new int[]{10,8,3,8});
            components.CustomizeTextview(mange_inactv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.inactve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.ApplyTint(mange_inactv_img,R.color.white);
            components.CustomizeImageview(mange_mange_img, new int[]{20,20},R.drawable.deleteiconrounded, new int[]{10,8,3,8});
            components.CustomizeTextview(mange_mange_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.manage),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.ApplyTint(mange_mange_img,R.color.white);
            components.CustomizeImageview(mange_delt_img, new int[]{20,20},R.drawable.deleteiconrounded, new int[]{10,8,3,8});
            components.CustomizeTextview(mange_delt_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.delete),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.ApplyTint(mange_delt_img,R.color.white);
            Drawable d = new ColorDrawable(Color.WHITE);

            d.setAlpha(200);

            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.showAsDropDown(viewpager_lay,0,0, Gravity.CENTER);

            //getWindow().setBackgroundDrawable(d);

            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    Drawable d = new ColorDrawable(Color.WHITE);
                    //getWindow().setBackgroundDrawable(d);
                }
            });


            mange_actv_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=datalist.get(position).get("product_id");
                    String status="";
                    if(datalist.get(position).get("status").equalsIgnoreCase("Active")){
                        status="Inactive";
                    }else{
                        status="Active";
                    }

                    updateProductJsonData(datalist,datalist.get(position),position,status);

                    Activatepopup(activity,"pro",id,status,StoredObjects.UserId,"");
                    mPopupWindow.dismiss();
                }
            });

            mange_inactv_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });

            mange_mange_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentcalling(new EditProduct());
                    mPopupWindow.dismiss();
                }
            });

            mange_delt_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // DisplayCnclPopup(activity,"products");
                    String id=datalist.get(position).get("product_id");
                    Deletepopup(activity,"pro",id,StoredObjects.UserId,"");
                    mPopupWindow.dismiss();
                }
            });

        }



        private void SetStatuspopup(LinearLayout viewpager_lay,final  ArrayList<HashMap<String, String>> datalist, final int position, final String type){

            LayoutInflater mLayoutInflater=LayoutInflater.from(activity);

            View mView=mLayoutInflater.inflate(R.layout.setstatuspopup, null);

            mPopupWindow=new PopupWindow(mView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setContentView(mView);

            LinearLayout mnage_status_lst_lay = (LinearLayout)mView.findViewById(R.id.mnage_status_lst_lay);
            LinearLayout mange_actv_lay = (LinearLayout)mView.findViewById(R.id.mange_actv_lay);
            ImageView mange_actv_img = (ImageView)mView.findViewById(R.id.mange_actv_img);
            TextView mange_actv_tx = (TextView)mView.findViewById(R.id.mange_actv_tx);

            LinearLayout mange_inactv_lay = (LinearLayout)mView.findViewById(R.id.mange_inactv_lay);
            ImageView mange_inactv_img = (ImageView)mView.findViewById(R.id.mange_inactv_img);
            TextView mange_inactv_tx = (TextView)mView.findViewById(R.id.mange_inactv_tx);

            LinearLayout mange_mange_lay = (LinearLayout)mView.findViewById(R.id.mange_mange_lay);
            ImageView mange_mange_img = (ImageView)mView.findViewById(R.id.mange_mange_img);
            TextView mange_mange_tx = (TextView)mView.findViewById(R.id.mange_mange_tx);


            LinearLayout down_arw_lay = (LinearLayout)mView.findViewById(R.id.down_arw_lay);
            LinearLayout up_arw_lay = (LinearLayout)mView.findViewById(R.id.up_arw_lay);

            ImageView mange_uparow_img = (ImageView)mView.findViewById(R.id.mange_uparow_img);
            ImageView mange_dwnarow_img = (ImageView)mView.findViewById(R.id.mange_dwnarow_img);
            up_arw_lay.setVisibility(View.VISIBLE);
            down_arw_lay.setVisibility(View.GONE);

            if(datalist.get(position).get("status").equalsIgnoreCase("Active")){
                components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.actve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});

            }else{
                components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.inactve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});

            }

            components.CustomizeImageview(mange_uparow_img, new int[]{45,35},R.drawable.up_arroww_, new int[]{0,0,5,-10});
            //components.ApplyTint(mange_uparow_img,R.color.blue_color);
            components.CustomizeImageview(mange_dwnarow_img, new int[]{45,35},R.drawable.down_arroww, new int[]{0,-10,5,0});
            components.CustomizeImageview(mange_actv_img, new int[]{20,20},R.drawable.deleteimage, new int[]{10,8,3,8});
           // components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.actve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.ApplyTint(mange_actv_img,R.color.white);
            components.CustomizeImageview(mange_inactv_img, new int[]{20,20},R.drawable.deleteimage, new int[]{10,8,3,8});
            components.CustomizeTextview(mange_inactv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.inactve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.ApplyTint(mange_inactv_img,R.color.white);
            components.CustomizeImageview(mange_mange_img, new int[]{20,20},R.drawable.deleteimage, new int[]{10,8,3,8});
            components.CustomizeTextview(mange_mange_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.delete),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.ApplyTint(mange_mange_img,R.color.white);

            Drawable d = new ColorDrawable(Color.WHITE);

            d.setAlpha(200);

            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

            mPopupWindow.showAsDropDown(viewpager_lay,0,0, Gravity.CENTER);

            //getWindow().setBackgroundDrawable(d);

            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    Drawable d = new ColorDrawable(Color.WHITE);
                    //getWindow().setBackgroundDrawable(d);
                }
            });


            mange_actv_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(type.equalsIgnoreCase("category")){
                        String id=datalist.get(position).get("category_id");
                        String status="";
                        if(datalist.get(position).get("status").equalsIgnoreCase("Active")){
                            status="Inactive";
                        }else{
                            status="Active";
                        }
                        Activatepopup(activity,"category",id,status,StoredObjects.UserId,"");

                    }else{
                        String id=datalist.get(position).get("category_id");
                        String status="";
                        if(datalist.get(position).get("status").equalsIgnoreCase("Active")){
                            status="Inactive";
                        }else{
                            status="Active";
                        }
                        Activatepopup(activity,"sub_category",id,status,StoredObjects.UserId,"");

                    }
                    mPopupWindow.dismiss();
                }
            });



            mange_mange_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(type.equalsIgnoreCase("category")){
                        String id=datalist.get(position).get("category_id");
                        Deletepopup(activity,"category",id,StoredObjects.UserId,"");

                    }else{
                        String id="";
                        Deletepopup(activity,"sub_category",id,StoredObjects.UserId,"");

                    }
                    mPopupWindow.dismiss();
                }
            });

        }

        private void DisplaySuccessPopup(Activity activity) {

            final Dialog dialog = new Dialog(activity);
            dialog.getWindow();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.success_popup);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            ImageView success_img = (ImageView)dialog.findViewById(R.id.success_img);

            RobotTextView success_popup_dscrptn_txt=(RobotTextView) dialog.findViewById(R.id.success_popup_dscrptn_txt);
            RobotTextView success_popup_cncl_txt=(RobotTextView) dialog.findViewById(R.id.success_popup_cncl_txt);
            RobotTextView success_popup_ok_txt=(RobotTextView) dialog.findViewById(R.id.success_popup_ok_txt);


            success_popup_cncl_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            success_popup_ok_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }



//

        private void DisplayCnclPopup(Activity activity,String type) {

            final Dialog dialog = new Dialog(activity);
            dialog.getWindow();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.cncl_popup);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            ImageView cncl_img = (ImageView)dialog.findViewById(R.id.cncl_img);

            RobotTextView cncl_popup_dscrptn_txt=(RobotTextView) dialog.findViewById(R.id.cncl_popup_dscrptn_txt);
            RobotTextView cncl_popup_cncl_txt=(RobotTextView) dialog.findViewById(R.id.cncl_popup_cncl_txt);
            RobotTextView cncl_popup_ok_txt=(RobotTextView) dialog.findViewById(R.id.cncl_popup_ok_txt);

            if(type.equalsIgnoreCase("offers")){
                cncl_popup_dscrptn_txt.setText(activity.getResources().getString(R.string.deleteofrtxt));
            }else {
                cncl_popup_dscrptn_txt.setText(activity.getResources().getString(R.string.deleteofrtxt));
            }
            cncl_popup_cncl_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            cncl_popup_ok_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }

        private void OrderDetailsPopup(final Activity activity, ArrayList<HashMap<String, String>> orderitemslist,ArrayList<HashMap<String, String>> mainlist ,final int pos) {

            final Dialog dialog = new Dialog(activity);
            dialog.getWindow();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.order_details_popup);
            dialog.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            RecyclerView odr_pp_listview = (RecyclerView)dialog.findViewById(R.id.odr_pp_listview);
            TextView odr_pp_odr_txt=(TextView) dialog.findViewById(R.id.odr_pp_odr_txt);
            TextView odr_pp_odrid_txt=(TextView) dialog.findViewById(R.id.odr_pp_odrid_txt);
            TextView odr_pp_odrdate_txt=(TextView) dialog.findViewById(R.id.odr_pp_odrdate_txt);

            TextView odr_pp_fee_txt=(TextView) dialog.findViewById(R.id.odr_pp_fee_txt);
            TextView odr_pp_feeprce_txt=(TextView) dialog.findViewById(R.id.odr_pp_feeprce_txt);
            TextView odr_pp_vat_txt=(TextView) dialog.findViewById(R.id.odr_pp_vat_txt);
            TextView odr_pp_vatprce_txt=(TextView) dialog.findViewById(R.id.odr_pp_vatprce_txt);
            TextView odr_pp_totl_txt=(TextView) dialog.findViewById(R.id.odr_pp_totl_txt);
            TextView odr_pp_totlprce_txt=(TextView) dialog.findViewById(R.id.odr_pp_totlprce_txt);
            ImageView dismiss_btn=(ImageView) dialog.findViewById(R.id.dismiss_btn);
            ImageView odr_pp_odr_img = (ImageView)dialog.findViewById(R.id.odr_pp_odr_img);
            LinearLayout dismiss_lay=(LinearLayout) dialog.findViewById(R.id.dismiss_lay);
            dismiss_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dismiss_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            components.CustomizeImageview(odr_pp_odr_img, new int[]{60,60},R.drawable.ordersuccess, new int[]{0,0,5,0});

            components.CustomizeTextview(odr_pp_odr_txt, Constants.Normal,R.color.ordr_detls_txt_clr,activity.getResources().getString(R.string.order),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(odr_pp_odrid_txt, Constants.XXNormal,R.color.blue_color,"Order ID : #"+mainlist.get(pos).get("order_id"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(odr_pp_odrdate_txt, Constants.Medium,R.color.sport_shoos_txt_clr,mainlist.get(pos).get("createddate"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(odr_pp_fee_txt, Constants.Medium,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.fee),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,15,0});
            components.CustomizeTextview(odr_pp_feeprce_txt, Constants.Medium,R.color.sport_shoos_txt_clr,"Rs."+mainlist.get(pos).get("order_amount"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(odr_pp_vat_txt, Constants.Medium,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.vat_tax),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,15,0});
            components.CustomizeTextview(odr_pp_vatprce_txt, Constants.Medium,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.vatprce),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(odr_pp_totl_txt, Constants.Normal,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.grnd_total),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,15,0,0});
            components.CustomizeTextview(odr_pp_totlprce_txt, Constants.XXNormal,R.color.blue_color,"Rs."+mainlist.get(pos).get("order_amount"),Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{0,0,0,20});

            CustomRecyclerview recyclerview = new CustomRecyclerview(activity);
            recyclerview.Assigndatatorecyleviewhashmap(odr_pp_listview,orderitemslist,"orderpopup", Constants.Listview,0, Constants.ver_orientation,R.layout.order_details_popup_listitems);


            dialog.show();

        }
        private void DisplayPrdctLstDialog(Activity activity) {

            final Dialog dialog = new Dialog(activity);
            dialog.getWindow();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.prdct_detls_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            TextView prdct_dtls_txt = (TextView)dialog.findViewById(R.id.prdct_dtls_txt);

            LinearLayout prdct_dtls_cncl_lay = (LinearLayout)dialog.findViewById(R.id.prdct_dtls_cncl_lay);
            ImageView prdct_dtls_cncl_img = (ImageView)dialog.findViewById(R.id.prdct_dtls_cncl_img);
            TextView prdct_dtls_titl_txt = (TextView)dialog.findViewById(R.id.prdct_dtls_titl_txt);

            TextView prdct_dtls_catgry_txt = (TextView)dialog.findViewById(R.id.prdct_dtls_catgry_txt);
            TextView prdct_dtls_catgry_txtt = (TextView)dialog.findViewById(R.id.prdct_dtls_catgry_txtt);
            TextView prdct_dtls_catgry_txt1 = (TextView)dialog.findViewById(R.id.prdct_dtls_catgry_txt1);

            TextView prdct_dtls_sub_catgry_txt = (TextView)dialog.findViewById(R.id.prdct_dtls_sub_catgry_txt);
            TextView prdct_dtls_sub_catgry_txtt = (TextView)dialog.findViewById(R.id.prdct_dtls_sub_catgry_txtt);
            TextView prdct_dtls_sub_catgry_txt1 = (TextView)dialog.findViewById(R.id.prdct_dtls_sub_catgry_txt1);

            TextView prdct_dtls_price_txt = (TextView)dialog.findViewById(R.id.prdct_dtls_price_txt);
            TextView prdct_dtls_price_txtt = (TextView)dialog.findViewById(R.id.prdct_dtls_price_txtt);
            TextView prdct_dtls_price_txt1 = (TextView)dialog.findViewById(R.id.prdct_dtls_price_txt1);

            TextView prdct_dtls_dscnt_txt = (TextView)dialog.findViewById(R.id.prdct_dtls_dscnt_txt);
            TextView prdct_dtls_dscnt_txtt = (TextView)dialog.findViewById(R.id.prdct_dtls_dscnt_txtt);
            TextView prdct_dtls_dscnt_txt1 = (TextView)dialog.findViewById(R.id.prdct_dtls_dscnt_txt1);

            RatingBar prdct_dtls_rating_bar = (RatingBar)dialog.findViewById(R.id.prdct_dtls_rating_bar);
            TextView prdct_dtls_rating_txt = (TextView)dialog.findViewById(R.id.prdct_dtls_rating_txt);

            components.CustomizeTextview(prdct_dtls_txt, Constants.Normal,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.product_details),Constants.WrapCenterBold+Constants.Roboto, new int[]{50,0,0,0});

            components.CustomizeImageview(prdct_dtls_cncl_img, new int[]{12,12},R.drawable.cancel_img, new int[]{0,0,25,0});

            components.CustomizeTextview(prdct_dtls_titl_txt, Constants.Normal,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.product_name),Constants.WrapLeftBold+Constants.Roboto, new int[]{35,0,0,0});

            components.CustomizeTextview(prdct_dtls_catgry_txt, Constants.XSmall,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.ctgry),Constants.WrapLeftBold+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(prdct_dtls_catgry_txtt, Constants.XSmall,R.color.sport_shoos_txt_clr,":",Constants.WrapCenterBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(prdct_dtls_catgry_txt1, Constants.XSmall,R.color.thik_grey,activity.getResources().getString(R.string.ctgry),Constants.WrapLeftBold+Constants.Roboto, new int[]{5,0,0,0});

            components.CustomizeTextview(prdct_dtls_sub_catgry_txt, Constants.XSmall,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.sub_ctgry),Constants.WrapLeftBold+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(prdct_dtls_sub_catgry_txtt, Constants.XSmall,R.color.sport_shoos_txt_clr,":",Constants.WrapCenterBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(prdct_dtls_sub_catgry_txt1, Constants.XSmall,R.color.thik_grey,activity.getResources().getString(R.string.sub_ctgry),Constants.WrapLeftBold+Constants.Roboto, new int[]{5,0,0,0});

            components.CustomizeTextview(prdct_dtls_price_txt, Constants.XSmall,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.price),Constants.WrapLeftBold+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(prdct_dtls_price_txtt, Constants.XSmall,R.color.sport_shoos_txt_clr,":",Constants.WrapCenterBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(prdct_dtls_price_txt1, Constants.XSmall,R.color.thik_grey,activity.getResources().getString(R.string.price),Constants.WrapLeftBold+Constants.Roboto, new int[]{5,0,0,0});

            components.CustomizeTextview(prdct_dtls_dscnt_txt, Constants.XSmall,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.dscunt),Constants.WrapLeftBold+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(prdct_dtls_dscnt_txtt, Constants.XSmall,R.color.sport_shoos_txt_clr,":",Constants.WrapCenterBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(prdct_dtls_dscnt_txt1, Constants.XSmall,R.color.thik_grey,activity.getResources().getString(R.string.dscunt),Constants.WrapLeftBold+Constants.Roboto, new int[]{5,0,0,0});

            components.CustomizeTextview(prdct_dtls_rating_txt, Constants.Small,R.color.thik_grey,activity.getResources().getString(R.string.dscunt),Constants.WrapLeftBold+Constants.Roboto, new int[]{0,-3,0,0});

            prdct_dtls_cncl_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }





        private void DisplayOffrsDialog(Activity activity) {

            final Dialog dialog = new Dialog(activity);
            dialog.getWindow();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.offr_dtls_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            TextView offr_dtls_txt = (TextView)dialog.findViewById(R.id.offr_dtls_txt);

            LinearLayout offr_dtls_cncl_lay = (LinearLayout)dialog.findViewById(R.id.offr_dtls_cncl_lay);
            ImageView offr_dtls_cncl_img = (ImageView)dialog.findViewById(R.id.offr_dtls_cncl_img);
            TextView offr_dtls_titl_txt = (TextView)dialog.findViewById(R.id.offr_dtls_titl_txt);

            TextView offr_dtls_catgry_txt = (TextView)dialog.findViewById(R.id.offr_dtls_catgry_txt);
            TextView offr_dtls_catgry_txtt = (TextView)dialog.findViewById(R.id.offr_dtls_catgry_txtt);
            TextView offr_dtls_catgry_txt1 = (TextView)dialog.findViewById(R.id.offr_dtls_catgry_txt1);

            TextView offr_dtls_sub_catgry_txt = (TextView)dialog.findViewById(R.id.offr_dtls_sub_catgry_txt);
            TextView offr_dtls_sub_catgry_txtt = (TextView)dialog.findViewById(R.id.offr_dtls_sub_catgry_txtt);
            TextView offr_dtls_sub_catgry_txt1 = (TextView)dialog.findViewById(R.id.offr_dtls_sub_catgry_txt1);

            TextView offr_dtls_promocode_txt = (TextView)dialog.findViewById(R.id.offr_dtls_promocode_txt);
            TextView offr_dtls_promocode_txtt = (TextView)dialog.findViewById(R.id.offr_dtls_promocode_txtt);
            TextView offr_dtls_promocode_txt1 = (TextView)dialog.findViewById(R.id.offr_dtls_promocode_txt1);

            TextView offr_dtls_strt_dte_txt = (TextView)dialog.findViewById(R.id.offr_dtls_strt_dte_txt);
            TextView offr_dtls_strt_dte_txtt = (TextView)dialog.findViewById(R.id.offr_dtls_strt_dte_txtt);
            TextView offr_dtls_strt_dte_txt1 = (TextView)dialog.findViewById(R.id.offr_dtls_strt_dte_txt1);

            TextView offr_dtls_nd_dte_txt = (TextView)dialog.findViewById(R.id.offr_dtls_nd_dte_txt);
            TextView offr_dtls_nd_dte_txtt = (TextView)dialog.findViewById(R.id.offr_dtls_nd_dte_txtt);
            TextView offr_dtls_nd_dte_txt1 = (TextView)dialog.findViewById(R.id.offr_dtls_nd_dte_txt1);

            TextView offr_dtls_dscnt_txt = (TextView)dialog.findViewById(R.id.offr_dtls_dscnt_txt);
            TextView offr_dtls_dscnt_txtt = (TextView)dialog.findViewById(R.id.offr_dtls_dscnt_txtt);
            TextView offr_dtls_dscnt_txt1 = (TextView)dialog.findViewById(R.id.offr_dtls_dscnt_txt1);

            components.CustomizeTextview(offr_dtls_txt, Constants.Normal,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.of_five),Constants.WrapCenterBold+Constants.Roboto, new int[]{50,0,0,0});
            components.CustomizeImageview(offr_dtls_cncl_img, new int[]{12,12},R.drawable.cancel_img, new int[]{0,0,25,0});
            components.CustomizeTextview(offr_dtls_titl_txt, Constants.Normal,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.ofr_details),Constants.WrapLeftBold+Constants.Roboto, new int[]{35,0,0,0});
            components.CustomizeTextview(offr_dtls_catgry_txt, Constants.XSmall,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.ctgry),Constants.WrapLeftBold+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(offr_dtls_catgry_txtt, Constants.XSmall,R.color.sport_shoos_txt_clr,":",Constants.WrapCenterBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(offr_dtls_catgry_txt1, Constants.XSmall,R.color.thik_grey,activity.getResources().getString(R.string.ctgry),Constants.WrapLeftBold+Constants.Roboto, new int[]{5,0,0,0});
            components.CustomizeTextview(offr_dtls_sub_catgry_txt, Constants.XSmall,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.sub_ctgry),Constants.WrapLeftBold+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(offr_dtls_sub_catgry_txtt, Constants.XSmall,R.color.sport_shoos_txt_clr,":",Constants.WrapCenterBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(offr_dtls_sub_catgry_txt1, Constants.XSmall,R.color.thik_grey,activity.getResources().getString(R.string.sub_ctgry),Constants.WrapLeftBold+Constants.Roboto, new int[]{5,0,0,0});
            components.CustomizeTextview(offr_dtls_promocode_txt, Constants.XSmall,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.promo_code),Constants.WrapLeftBold+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(offr_dtls_promocode_txtt, Constants.XSmall,R.color.sport_shoos_txt_clr,":",Constants.WrapCenterBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(offr_dtls_promocode_txt1, Constants.XSmall,R.color.thik_grey,activity.getResources().getString(R.string.promo_code),Constants.WrapLeftBold+Constants.Roboto, new int[]{5,0,0,0});
            components.CustomizeTextview(offr_dtls_strt_dte_txt, Constants.XSmall,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.start_date),Constants.WrapLeftBold+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(offr_dtls_strt_dte_txtt, Constants.XSmall,R.color.sport_shoos_txt_clr,":",Constants.WrapCenterBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(offr_dtls_strt_dte_txt1, Constants.XSmall,R.color.thik_grey,activity.getResources().getString(R.string.start_date),Constants.WrapLeftBold+Constants.Roboto, new int[]{5,0,0,0});
            components.CustomizeTextview(offr_dtls_nd_dte_txt, Constants.XSmall,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.end_date),Constants.WrapLeftBold+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(offr_dtls_nd_dte_txtt, Constants.XSmall,R.color.sport_shoos_txt_clr,":",Constants.WrapCenterBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(offr_dtls_nd_dte_txt1, Constants.XSmall,R.color.thik_grey,activity.getResources().getString(R.string.end_date),Constants.WrapLeftBold+Constants.Roboto, new int[]{5,0,0,0});
            components.CustomizeTextview(offr_dtls_dscnt_txt, Constants.XSmall,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.dscunt),Constants.WrapLeftBold+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(offr_dtls_dscnt_txtt, Constants.XSmall,R.color.sport_shoos_txt_clr,":",Constants.WrapCenterBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(offr_dtls_dscnt_txt1, Constants.XSmall,R.color.thik_grey,activity.getResources().getString(R.string.dscunt),Constants.WrapLeftBold+Constants.Roboto, new int[]{5,0,0,0});

            offr_dtls_cncl_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }



        private void Logoutpopup(final Activity activity) {

            final Dialog dialog = new Dialog(activity);
            dialog.getWindow();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.logout_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            TextView dialog_alert_txt=(TextView) dialog.findViewById(R.id.dialog_alert_txt);
            Button logout_cncl_txt=(Button) dialog.findViewById(R.id.logout_cncl_txt);
            Button logout_ok_txt=(Button) dialog.findViewById(R.id.logout_ok_txt);

            components.CustomizeTextview(dialog_alert_txt,Constants.XNormal,R.color.sport_shoos_txt_clr,activity.getApplicationContext().getResources().getString(R.string.logout_txt),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
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
                    activity.finish();
                    activity.startActivity(new Intent(activity,LandingPage.class));

                }
            });

            dialog.show();

        }

        private void Deletepopup(final Activity activity,final String type,final String id,final String vendor_id,final String product_id) {

            final Dialog dialog = new Dialog(activity);
            dialog.getWindow();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.deletepopupnew);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            TextView dialog_alert_txt=(TextView) dialog.findViewById(R.id.dialog_alert_txt);
            Button logout_cncl_txt=(Button) dialog.findViewById(R.id.logout_cncl_txt);
            Button logout_ok_txt=(Button) dialog.findViewById(R.id.logout_ok_txt);

            if(type.equalsIgnoreCase("ofr")){
                components.CustomizeTextview(dialog_alert_txt,Constants.XNormal,R.color.sport_shoos_txt_clr,activity.getApplicationContext().getResources().getString(R.string.deleteofrtxt),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
            }else if(type.equalsIgnoreCase("pro")){
                components.CustomizeTextview(dialog_alert_txt,Constants.XNormal,R.color.sport_shoos_txt_clr,activity.getApplicationContext().getResources().getString(R.string.deleteproducttxt),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
            }else if(type.equalsIgnoreCase("category")){
                components.CustomizeTextview(dialog_alert_txt,Constants.XNormal,R.color.sport_shoos_txt_clr,activity.getApplicationContext().getResources().getString(R.string.deletectgrytxt),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
            }else{
                components.CustomizeTextview(dialog_alert_txt,Constants.XNormal,R.color.sport_shoos_txt_clr,activity.getApplicationContext().getResources().getString(R.string.deletesubctgrytxt),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});

            }
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
                if(type.equalsIgnoreCase("ofr")){
                    parsing_methods(0, StoredUrls.DeleteOffer_url,"offer_id="+id+"&vendor_id="+vendor_id+"&product_id="+product_id,Constants.POSTMETHOD,"ofr");
                }else if(type.equalsIgnoreCase("pro")){
                    parsing_methods(0, StoredUrls.DeleteProduct_url,"product_id="+id+"&vendor_id="+vendor_id,Constants.POSTMETHOD,"pro");
                }else if(type.equalsIgnoreCase("category")){
                    parsing_methods(0, StoredUrls.DeleteCategory_url,"category_id="+id+"&vendor_id="+vendor_id,Constants.POSTMETHOD,"category");
                }else{
                    parsing_methods(0, StoredUrls.DeleteSubCategory_url,"category_id="+id+"&vendor_id="+vendor_id,Constants.POSTMETHOD,"sub_category");
                }
                }
            });

            dialog.show();

        }
        private void Activatepopup(final Activity activity,final String type,final String id,final String status,final String vendor_id,final String product_id) {

            final Dialog dialog = new Dialog(activity);
            dialog.getWindow();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.activatepopup);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            TextView dialog_alert_txt=(TextView) dialog.findViewById(R.id.dialog_alert_txt);
            Button logout_cncl_txt=(Button) dialog.findViewById(R.id.logout_cncl_txt);
            Button logout_ok_txt=(Button) dialog.findViewById(R.id.logout_ok_txt);

            if(type.equalsIgnoreCase("ofr")){
                components.CustomizeTextview(dialog_alert_txt,Constants.XNormal,R.color.sport_shoos_txt_clr,activity.getApplicationContext().getResources().getString(R.string.activateofrtxt),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});

            }else  if(type.equalsIgnoreCase("pro")){
                components.CustomizeTextview(dialog_alert_txt,Constants.XNormal,R.color.sport_shoos_txt_clr,activity.getApplicationContext().getResources().getString(R.string.activateproducttxt),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});

            }else  if(type.equalsIgnoreCase("category")){
                components.CustomizeTextview(dialog_alert_txt,Constants.XNormal,R.color.sport_shoos_txt_clr,activity.getApplicationContext().getResources().getString(R.string.activatectgrytxt),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});

            }else{
                components.CustomizeTextview(dialog_alert_txt,Constants.XNormal,R.color.sport_shoos_txt_clr,activity.getApplicationContext().getResources().getString(R.string.activatesubctgrytxt),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});

            }
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
                if(type.equalsIgnoreCase("ofr")){
                    parsing_methods(1, StoredUrls.UpdateOfferstatus_url,"offer_id="+id+"&status="+status+"&vendor_id="+vendor_id+"&product_id="+product_id,Constants.POSTMETHOD,"ofr"+"@"+status);
                }else if(type.equalsIgnoreCase("pro")){
                    parsing_methods(1, StoredUrls.UpdateProductstatus_url,"product_id="+id+"&status="+status+"&vendor_id="+vendor_id,Constants.POSTMETHOD,"pro"+"@"+status);
                }else if(type.equalsIgnoreCase("category")){
                    parsing_methods(1, StoredUrls.UpdateCategorystatus_url,"category_id="+id+"&status="+status+"&vendor_id="+vendor_id,Constants.POSTMETHOD,"category"+"@"+status);
                }else{
                    parsing_methods(1, StoredUrls.UpdateSubctgrystatus_url,"category_id="+id+"&status="+status+"&vendor_id="+vendor_id,Constants.POSTMETHOD,"sub_category"+"@"+status);
                }

                }
            });

            dialog.show();

        }

        public void parsing_methods(final  int val, String url, String parameters, String parsing_type, final String type){
            WebServicesCalling webServicesCalling = new WebServicesCalling(activity);

            webServicesCalling.calling_webservices(url,parameters,val,parsing_type);

            StoredObjects.Services_list.get(val).countDown = new CountDownTimer(60 * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    StoredObjects.LogMethod("Seconds remaining: "," Seconds remaining: " + millisUntilFinished / 100);


                    if(StoredObjects.Services_list.get(val).Result != ""){
                        StoredObjects.LogMethod("Result",StoredObjects.Services_list.get(val).Result);
                        StoredObjects.LogMethod("Service_called",StoredObjects.Services_list.get(val).url);

                        if(val==0) {
                            //{"status":200,"message":"success","results_count":3,"results":[{"attr_id":1,"attr_name":"Weight"},{"attr_id":3,"attr_name":"length"},{"attr_id":4,"attr_name":"dfghdfghdfg"}]}
                            try {
                                JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                                String status = jsonObject.getString("status");
                                if(status.equalsIgnoreCase("200")){
                                    if(type.equalsIgnoreCase("pro")){
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.deletedproducttxt),activity);
                                    }else if(type.equalsIgnoreCase("ofr")){
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.deletedofrtxt),activity);


                                    }else if(type.equalsIgnoreCase("category")){
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.deletedctgrytxt),activity);

                                    }else{
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.deletedsubctgrytxt),activity);

                                    }
                                }else{
                                    String error = jsonObject.getString("error");

                                    StoredObjects.ToastMethod(error,activity);
                                }
                            } catch (JSONException e) {
                                StoredObjects.LogMethod("Service_called","Execption"+e);

                                e.printStackTrace();
                            }

                        }else if(val==1){
                            //{"status":200,"message":"success","results_count":2,"results":[{"attr_id":2,"attr_name":"Grams"},{"attr_id":5,"attr_name":"kghkhj"}]}
                            try {
                                JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                                String status = jsonObject.getString("status");
                                if(status.equalsIgnoreCase("200")){
                                    String[] parts=type.split("@");
                                    if(parts[0].equalsIgnoreCase("pro")){
                                        if(parts[1].equalsIgnoreCase("Inactive")){
                                            StoredObjects.ToastMethod(activity.getResources().getString(R.string.inactivatedproducttxt),activity);

                                        }else{
                                            StoredObjects.ToastMethod(activity.getResources().getString(R.string.activatedproducttxt),activity);

                                        }
                                    }else if(parts[0].equalsIgnoreCase("ofr")){
                                        if(parts[1].equalsIgnoreCase("Inactive")){
                                            StoredObjects.ToastMethod(activity.getResources().getString(R.string.inactivatedofrtxt),activity);

                                        }else{
                                            StoredObjects.ToastMethod(activity.getResources().getString(R.string.activatedofrtxt),activity);

                                        }

                                    }else if(parts[0].equalsIgnoreCase("category")){
                                        if(parts[1].equalsIgnoreCase("Inactive")){
                                            StoredObjects.ToastMethod(activity.getResources().getString(R.string.inactivatedctgrytxt),activity);

                                        }else{
                                            StoredObjects.ToastMethod(activity.getResources().getString(R.string.activatedctgrytxt),activity);

                                        }

                                    }else{
                                        if(parts[1].equalsIgnoreCase("Inactive")){
                                            StoredObjects.ToastMethod(activity.getResources().getString(R.string.inactivatedsubctgrytxt),activity);

                                        }else{
                                            StoredObjects.ToastMethod(activity.getResources().getString(R.string.activatedsubctgrytxt),activity);

                                        }

                                    }
                                }else{
                                    String error = jsonObject.getString("error");

                                    StoredObjects.ToastMethod(error,activity);
                                }
                            } catch (JSONException e) {
                                StoredObjects.LogMethod("Service_called","Execption"+e);

                                e.printStackTrace();
                            }
                        }else if(val==2){
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){
                                StoredObjects.ToastMethod(activity.getResources().getString(R.string.removefromwishlist),activity);

                            }else{
                                String error = jsonObject.getString("error");

                                StoredObjects.ToastMethod(error,activity);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        }else if(val==3){
                            // {"status":200,"message":"success","results_count":1,"results":[{"category_id":3,"category_name":"IT"}]}
                     /*   try {
                            subcategorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
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

}
