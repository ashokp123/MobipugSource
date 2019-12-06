package com.o3sa.mobipugapp.uicomponents;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.o3sa.mobipugapp.customerfragments.CustomerProductlist;
import com.o3sa.mobipugapp.customerfragments.ProductDetailsMain;
import com.o3sa.mobipugapp.customerfragments.SubCategoryList;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.customerfragments.ChangePassword;
import com.o3sa.mobipugapp.customerfragments.EditProfile;
import com.o3sa.mobipugapp.customerfragments.SendFeedback;
import com.o3sa.mobipugapp.fragments.EditOffers;
import com.o3sa.mobipugapp.fragments.EditProduct;
import com.o3sa.mobipugapp.fragments.GenerateBill;
import com.o3sa.mobipugapp.fragments.ManageCategoriesMain;
import com.o3sa.mobipugapp.fragments.ManageSubCategoriesMain;
import com.o3sa.mobipugapp.fragments.VRegistnOne;
import com.o3sa.mobipugapp.fragments.VendorProfile;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.fragments.ManageOffers;
import com.o3sa.mobipugapp.fragments.ManageOffersMain;
import com.o3sa.mobipugapp.fragments.ManageProducts;
import com.o3sa.mobipugapp.fragments.ManageProductsMain;
import com.o3sa.mobipugapp.fragments.VendorManageCategory;
import com.o3sa.mobipugapp.fragments.VendorManageSubCategory;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kiran on 22-09-2018.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    BasicComponents components;
    Activity activity;

    //subcategry
    ImageView sbcat_arrw_img;
    TextView sbcat_nms_txt;
    CircularImageView sbcat_prfle_img;
    LinearLayout itemmain_lay;
    //myorders
    TextView myodr_numbr_txt,myodr_num_txt,myodr_bokdate_txt,myodr_catgrynms_txt,myodr_price_txt,myodr_mangeodr_txt;
    ImageView myodr_catgrys_img;
    LinearLayout orderdetailsmain_lay,orderdetails_lay;
    //profile
    TextView prfle_catgrynms_txt;
    LinearLayout prfl_lay_one;

    LinearLayout hmpg_ctgries_lstitm_lay;
    ImageView hm_pg_ctgries_img;
    TextView hm_pg_ctgries_txt;

    LinearLayout hmpg_poplrkeywrds_lstitm_lay;
    ImageView hm_pg_poplrkeywrds_img;
    TextView hm_pg_poplrkeywrds_titl_txt,hm_pg_poplrkeywrds_dscrptn_txt;

    //offersliat
    ImageView ofrs_brnd_img,ofrs_mange_img;
    TextView ofrs_brnd_tx,ofrs_prmocde_tx,ofrs_prmocde1_tx,ofrs_strtdte_tx,ofrs_strtdte1_tx,ofrs_endate_tx,ofrs_endate1_tx,ofrs_discnt_tx,ofrs_mange_tx;
    LinearLayout ofers_mange_lay;

    //bill history
    TextView bill_bloods_txtvw,bill_mn_dt_yr_txtvw,bill_xray_txtvw,bill_mns_dts_yrs_txtvw;

    //Vendor home page
    ImageView hm_ctgries_img;
    TextView hm_ctgries_txt;
    LinearLayout vendorhp_lay;

    //Manage products
    TextView prdctname_tx,prdctcost_tx,prdctdiscntprice_tx,prdctdiscnt_tx,procount_tx,prdctmange_tx;
    RatingBar pro_rating;
    ImageView prdctbrnd_img,prdctmange_img;

    TextView cat_detls_name_tx,cat_detls_cost_tx,cat_detls_discntprice_tx,prostock_tx,cat_detls_discnt_tx,cat_detls_ratngcount_tx;
    RatingBar cat_detls__rating;
    ImageView cat_detls_brnd_img;
    LinearLayout catgry_lstitm_lay,prdctmange_lay;

    //Category Grd Details
    TextView cat_detls_grd_name_tx,gridprostock_tx,cat_detls_grd_cost_tx,cat_detls_grd_discntprice_tx,cat_detls_grd_discnt_tx,cat_detls_grd_ratngcount_tx;
    RatingBar cat_detls__grdrating;
    ImageView cat_detls_grd_brnd_img;
    LinearLayout ctgry_griditm_lay;
    ImageView cart_rv_prdct_img;
    TextView cart_rv_prdct_name_txt,cart_rv_prdct_type_txt;
    TextView cart_rv_no_of_prdcts_txt,cart_rv_price_of_prdct_txt;
    private PopupWindow mPopupWindow;


    CircularImageView cat_prfle_img;
    TextView cat_nms_txt;

    TextView catmnge_nms_txt,setstatus_txt;
    LinearLayout catsetstatus_lay;
    ImageView catmnge_prfle_img;
    //price
    LinearLayout price_rnge_layy;
    TextView price_rnge_txtt;
    //offr
    LinearLayout offr_rnge_layy;
    TextView offr_rnge_txtt;
    //Cstmrlandng
    TextView clandng_brndnme_tx,clandng_weight_tx,clandng_price_tx,clandng_dscunt_tx,clandng_strtdte_tx,
            clandng_strtshwdte_tx,clandng_enddte_tx,clandng_endshwdte_tx;
    ImageView clandng_brnd_img;
    TextView odr_pp_odrnum_txt,odr_pp_brndnme_txt,odr_pp_brndprce_txt;
    public ViewHolder(View convertView, String type,final Activity activity) {

        super(convertView);

        this.activity = activity;
        components=new BasicComponents(activity);
         if(type.equalsIgnoreCase("orderpopup")){
            odr_pp_odrnum_txt = (TextView) convertView.findViewById(R.id.odr_pp_odrnum_txt);
            odr_pp_brndnme_txt = (TextView) convertView.findViewById(R.id.odr_pp_brndnme_txt);
            odr_pp_brndprce_txt = (TextView) convertView.findViewById(R.id.odr_pp_brndprce_txt);
        }
        else if (type.equalsIgnoreCase("c_mangecategrylist")) {
            catmnge_prfle_img = (CircularImageView)convertView.findViewById(R.id.catmnge_prfle_img);
            catmnge_nms_txt = (TextView) convertView.findViewById(R.id.catmnge_nms_txt);
            setstatus_txt=(TextView) convertView.findViewById(R.id.setstatus_txt);
            catsetstatus_lay=(LinearLayout) convertView.findViewById(R.id.catsetstatus_lay);
        } else if (type.equalsIgnoreCase("c_mangesubcategrylist")) {
            catmnge_prfle_img = (CircularImageView)convertView.findViewById(R.id.catmnge_prfle_img);
            catmnge_nms_txt = (TextView) convertView.findViewById(R.id.catmnge_nms_txt);
            setstatus_txt=(TextView) convertView.findViewById(R.id.setstatus_txt);
            catsetstatus_lay=(LinearLayout) convertView.findViewById(R.id.catsetstatus_lay);
        } else if (type.equalsIgnoreCase("c_subcategrylist")) {
            cat_prfle_img = (CircularImageView)convertView.findViewById(R.id.cat_prfle_img);
            cat_nms_txt = (TextView) convertView.findViewById(R.id.cat_nms_txt);
            itemmain_lay=(LinearLayout) convertView.findViewById(R.id.itemmain_lay);
        } else if (type.equalsIgnoreCase("c_categrylist")) {
            cat_prfle_img = (CircularImageView)convertView.findViewById(R.id.cat_prfle_img);
            cat_nms_txt = (TextView) convertView.findViewById(R.id.cat_nms_txt);
            itemmain_lay=(LinearLayout) convertView.findViewById(R.id.itemmain_lay);
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

        }else if (type.equalsIgnoreCase("profile")) {
            prfle_catgrynms_txt = (TextView) convertView.findViewById(R.id.prfle_catgrynms_txt);
            prfl_lay_one = (LinearLayout) convertView.findViewById(R.id.prfl_lay_one);
        }else if (type.equalsIgnoreCase("hmpg_catgries")) {
            hmpg_ctgries_lstitm_lay = (LinearLayout) convertView.findViewById(R.id.hmpg_ctgries_lstitm_lay);
            hm_pg_ctgries_img = (ImageView)convertView.findViewById(R.id.hm_pg_ctgries_img);
            hm_pg_ctgries_txt = (TextView) convertView.findViewById(R.id.hm_pg_ctgries_txt);
        }else if (type.equalsIgnoreCase("hmpg_keywords")) {
            hmpg_poplrkeywrds_lstitm_lay = (LinearLayout) convertView.findViewById(R.id.hmpg_poplrkeywrds_lstitm_lay);
            hm_pg_poplrkeywrds_img = (ImageView)convertView.findViewById(R.id.hm_pg_poplrkeywrds_img);
            hm_pg_poplrkeywrds_titl_txt = (TextView) convertView.findViewById(R.id.hm_pg_poplrkeywrds_titl_txt);
            hm_pg_poplrkeywrds_dscrptn_txt = (TextView) convertView.findViewById(R.id.hm_pg_poplrkeywrds_dscrptn_txt);
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
        }else if (type.equalsIgnoreCase("BillHistory_one")) {
            bill_bloods_txtvw = (TextView) convertView.findViewById(R.id.bill__bloods_txtvw);
            bill_mn_dt_yr_txtvw = (TextView) convertView.findViewById(R.id.bill_mn_dt_yr_txtvw);
        }else if (type.equalsIgnoreCase("BillHistory_two")) {
            bill_xray_txtvw = (TextView) convertView.findViewById(R.id.bill_xray_txtvw);
            bill_mns_dts_yrs_txtvw = (TextView) convertView.findViewById(R.id.bill_mns_dts_yrs_txtvw);
        }else if (type.equalsIgnoreCase("vendor_homepage")) {
            hm_ctgries_img = (ImageView) convertView.findViewById(R.id.hm_ctgries_img);
            hm_ctgries_txt = (TextView) convertView.findViewById(R.id.hm_ctgries_txt);
            vendorhp_lay=(LinearLayout) convertView.findViewById(R.id.vendorhp_lay);
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
        }else if (type.equalsIgnoreCase("productslist")) {
            prdctbrnd_img = (ImageView)convertView.findViewById(R.id.prdctbrnd_img);
            prdctmange_img = (ImageView)convertView.findViewById(R.id.prdctmange_img);
            prdctname_tx = (TextView) convertView.findViewById(R.id.prdctname_tx);
            prdctcost_tx = (TextView) convertView.findViewById(R.id.prdctcost_tx);
            prdctdiscntprice_tx = (TextView) convertView.findViewById(R.id.prdctdiscntprice_tx);
            prdctdiscnt_tx = (TextView) convertView.findViewById(R.id.prdctdiscnt_tx);
            pro_rating = (RatingBar) convertView.findViewById(R.id.pro_rating);
            procount_tx = (TextView) convertView.findViewById(R.id.procount_tx);
            prdctmange_tx = (TextView) convertView.findViewById(R.id.prdctmange_tx);
        }
        else if (type.equalsIgnoreCase("catgrydetails")) {
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

        }else if(type.equalsIgnoreCase("cart_rv_ordr_rvw")){
            cart_rv_prdct_img = (ImageView)convertView.findViewById(R.id.cart_rv_prdct_img);
            cart_rv_prdct_name_txt = (TextView) convertView.findViewById(R.id.cart_rv_prdct_name_txt);
            cart_rv_prdct_type_txt = (TextView) convertView.findViewById(R.id.cart_rv_prdct_type_txt);
            cart_rv_no_of_prdcts_txt = (TextView) convertView.findViewById(R.id.cart_rv_no_of_prdcts_txt);
            cart_rv_price_of_prdct_txt = (TextView) convertView.findViewById(R.id.cart_rv_price_of_prdct_txt);
        }

        else if(type.equalsIgnoreCase("price_rnge_rvww")){
            price_rnge_layy = (LinearLayout) convertView.findViewById(R.id.price_rnge_layy);
            price_rnge_txtt = (TextView) convertView.findViewById(R.id.price_rnge_txtt);
        }else if(type.equalsIgnoreCase("offr_rnge_rvww")){
            offr_rnge_layy = (LinearLayout) convertView.findViewById(R.id.offr_rnge_layy);
            offr_rnge_txtt = (TextView) convertView.findViewById(R.id.offr_rnge_txtt);
        }

         else if(type.equalsIgnoreCase("clandng")){

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

    }

    public void assign_data(final ArrayList<DumpData> datalist, final int position, String formtype) { //ArrayList<HashMap<String, String>> datalist
        if (formtype.equalsIgnoreCase("orderpopup")) {
            components.CustomizeTextview(odr_pp_odrnum_txt, Constants.Small, R.color.sport_shoos_txt_clr, datalist.get(position).ordernumbers, Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(odr_pp_brndnme_txt, Constants.Small, R.color.sport_shoos_txt_clr, datalist.get(position).orderbrands, Constants.WrapCenterNormal+Constants.Roboto, new int[]{15,0,0,0});
            components.CustomizeTextview(odr_pp_brndprce_txt, Constants.Small, R.color.sport_shoos_txt_clr, datalist.get(position).orderbrandprice, Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,0});
        }
        else if (formtype.equalsIgnoreCase("c_subcategrylist")) {
            components.CustomizeTextview(cat_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).sbcat_nmslist,Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{10,0,0,0});
            itemmain_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // fragmentcalling(new ManageProductsMain());
                }
            });
        } else if (formtype.equalsIgnoreCase("c_categrylist")) {
            components.CustomizeTextview(cat_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).sbcat_nmslist,Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{10,0,0,0});
            itemmain_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // fragmentcalling(new ManageSubCategoriesMain());
                }
            });
        }

        else if (formtype.equalsIgnoreCase("clandng")) {
            components.CustomizeImageview(clandng_brnd_img, new int[]{110,110},datalist.get(position).clandngprdctlists, new int[]{0,0,0,0});
            components.CustomizeTextview(clandng_brndnme_tx, Constants.XXNormal,R.color.sport_shoos_txt_clr,"Noodles",Constants.WrapLeftBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(clandng_weight_tx, Constants.Normal,R.color.blue_color,"Rs.20/Grams",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,3,0,0});
            components.CustomizeTextview(clandng_price_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"6999,00",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(clandng_dscunt_tx, Constants.XSmall,R.color.serch_nrml_clr,"( -50% )",Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.CustomizeTextview(clandng_strtdte_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"Start Date:",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(clandng_strtshwdte_tx, Constants.XSmall,R.color.serch_nrml_clr,"10-11-2018",Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.CustomizeTextview(clandng_enddte_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"End Date:",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(clandng_endshwdte_tx, Constants.XSmall,R.color.serch_nrml_clr,"10-12-2019",Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});

        }

        if (formtype.equalsIgnoreCase("c_mangecategrylist")) {
            components.CustomizeTextview(catmnge_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).sbcat_nmslist,Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(setstatus_txt, Constants.XNormal,R.color.home_pg_clr,activity.getResources().getString(R.string.setstatus),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});

            catsetstatus_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  /*  LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                    int centerPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2;
*/
                   /* if (position > centerPosition) {
                        VendorManageCategory.c_categorylist.smoothScrollToPosition(position + 2);
                    } else if (position < centerPosition) {
                        VendorManageCategory.c_categorylist.smoothScrollToPosition(position - 1);
                    }*/

                    SetStatuspopup(catsetstatus_lay, datalist, position,"category");
                }
            });
        }


else if (formtype.equalsIgnoreCase("c_mangesubcategrylist")) {
        components.CustomizeTextview(catmnge_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).sbcat_nmslist,Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(setstatus_txt, Constants.XNormal,R.color.home_pg_clr,activity.getResources().getString(R.string.setstatus),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});

        catsetstatus_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /*  LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

                VendorManageSubCategory.c_categorylist.smoothScrollToPosition(position);
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                int centerPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2;

               if (position > centerPosition) {
                    VendorManageSubCategory.c_categorylist.smoothScrollToPosition(position + 2);
                } else if (position < centerPosition) {
                    VendorManageSubCategory.c_categorylist.smoothScrollToPosition(position - 1);
                }*/

                SetStatuspopup(catsetstatus_lay, datalist, position,"sub-category");
            }
        });
    }






else if (formtype.equalsIgnoreCase("manageoffers")) {
            components.CustomizeImageview(ofrs_brnd_img, new int[]{110,110},datalist.get(position).ofrscatgryimglist, new int[]{0,0,0,0});
            components.CustomizeImageview(ofrs_mange_img, new int[]{12,12},R.drawable.edit_icon, new int[]{0,0,0,0});
            components.ApplyTint(ofrs_mange_img,R.color.white);
            components.CustomizeTextview(ofrs_brnd_tx, Constants.XXNormal,R.color.sport_shoos_txt_clr,datalist.get(position).ofrsbrndlst,Constants.WrapLeftBold+Constants.Roboto, new int[]{8,0,0,0});
            components.CustomizeTextview(ofrs_prmocde_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.prmocde),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_prmocde1_tx, Constants.XSmall,R.color.ofrs_listitem_clr,datalist.get(position).ofrsprmolst,Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_strtdte_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.strtdate),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_strtdte1_tx, Constants.XSmall,R.color.ofrs_listitem_clr,datalist.get(position).ofrstartlst,Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_endate_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.enddate),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_endate1_tx, Constants.XSmall,R.color.ofrs_listitem_clr,datalist.get(position).ofrsendlst,Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_discnt_tx, Constants.Medium,R.color.ofrs_listitem_clr,datalist.get(position).ofrsdscntlst,Constants.WrapLeftNormal+Constants.Roboto, new int[]{30,5,0,0});
            components.CustomizeTextview(ofrs_mange_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.manage),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,0});
            ofers_mange_lay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                   /*   LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

                    ManageOffers.ofrs_recycleviwe.smoothScrollToPosition(position);
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                    int centerPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2;

                  if (position > centerPosition) {
                        ManageOffers.ofrs_recycleviwe.smoothScrollToPosition(position + 2);
                    } else if (position < centerPosition) {
                        ManageOffers.ofrs_recycleviwe.smoothScrollToPosition(position - 1);
                    }*/

            Manageofferpopup(ofers_mange_lay, datalist, position);
        }
    });
}



else if (formtype.equalsIgnoreCase("manageproducts")) {
                    components.CustomizeImageview(prdctbrnd_img, new int[]{110,110},datalist.get(position).ofrscatgryimglist, new int[]{0,0,0,0});
                    components.CustomizeImageview(prdctmange_img, new int[]{12,12},R.drawable.edit_icon, new int[]{0,0,0,0});
                    components.ApplyTint(prdctmange_img,R.color.white);
                    components.CustomizeTextview(prdctname_tx, Constants.XXNormal,R.color.sport_shoos_txt_clr,"Sport Shoes",Constants.WrapLeftBold+Constants.Roboto, new int[]{0,0,0,0});
                    components.CustomizeTextview(prdctcost_tx, Constants.Normal,R.color.blue_color,"$1,500",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,3,0,0});
                    components.CustomizeTextview(prdctdiscntprice_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"6999,00",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
                    components.CustomizeTextview(prdctdiscnt_tx, Constants.XSmall,R.color.serch_nrml_clr,"( -50% )",Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});
                    components.CustomizeTextview(procount_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"(40)",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
                    components.CustomizeTextview(prdctmange_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.manage),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,0});

                    prdctmange_lay.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {

                          /*  LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

                            ManageProducts.ofrs_recycleviwe.smoothScrollToPosition(position);
                            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                            int centerPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2;

                            if (position > centerPosition) {
                            ManageProducts.ofrs_recycleviwe.smoothScrollToPosition(position + 2);
                            } else if (position < centerPosition) {
                            ManageProducts.ofrs_recycleviwe.smoothScrollToPosition(position - 1);
                            }*/

                             Manageproductspopup(prdctmange_lay, datalist, position);
                }
                });


        }else if (formtype.equalsIgnoreCase("categry")) {
            components.CustomizeImageviewBackground(sbcat_arrw_img, new int[]{14,22}, R.drawable.backblueicon, new int[]{0,0,15,0});
            components.CustomizeTextview(sbcat_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).sbcat_nmslist,Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{10,0,0,0});
            itemmain_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentcalling(new SubCategoryList());
                }
            });
        }else  if (formtype.equalsIgnoreCase("subcategry")) {
            components.CustomizeImageviewBackground(sbcat_arrw_img, new int[]{14,22}, R.drawable.backblueicon, new int[]{0,0,15,0});
            components.CustomizeTextview(sbcat_nms_txt, Constants.Large,R.color.thik_black,datalist.get(position).sbcat_nmslist,Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{10,0,0,0});
            itemmain_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentcalling(new CustomerProductlist());
                }
            });
        }else if (formtype.equalsIgnoreCase("myorders")) {
            components.CustomizeImageviewBackground(myodr_catgrys_img, new int[]{85,85},R.drawable.restarunt_sammpleimg, new int[]{10,0,0,0});
            components.CustomizeTextview(myodr_numbr_txt, Constants.Normal,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.no),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{10,0,0,0});
            components.CustomizeTextview(myodr_num_txt, Constants.Normal,R.color.blue_color,datalist.get(position).myodrsnums,Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{5,0,0,0});
            components.CustomizeTextview(myodr_bokdate_txt, Constants.Small,R.color.sport_shoos_txt_clr,datalist.get(position).myodrsdates,Constants.WrapRightNormal+Constants.Roboto, new int[]{0,0,10,0});
            components.CustomizeTextview(myodr_catgrynms_txt, Constants.XXNormal,R.color.sport_shoos_txt_clr,datalist.get(position).myodrscatgynme,Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(myodr_price_txt, Constants.Normal,R.color.blue_color,datalist.get(position).myodrscatgryprce,Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{0,8,0,0});
            components.CustomizeTextview(myodr_mangeodr_txt, Constants.XXNormal,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.order_details),Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{0,14,0,14});
            myodr_mangeodr_txt.setFocusable(false);
            myodr_mangeodr_txt.setClickable(false);
            orderdetails_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailsPopup(activity);
                }
            });
            orderdetailsmain_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailsPopup(activity);
                }
            });

        }else if (formtype.equalsIgnoreCase("profile")) {
            components.CustomizeWithBgTextview(prfle_catgrynms_txt,Constants.XXNormal,R.color.prfl_txt_clr,datalist.get(position).prflecatgrylist,R.drawable.prfl_white_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
            prfle_catgrynms_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(StoredObjects.UserType.equalsIgnoreCase("Vendor")) {
                        if(position==1){
                            fragmentcalling(new ChangePassword());
                        }else  if(position==0){
                            fragmentcalling(new VendorProfile());
                        }else  if(position==2){
                            fragmentcalling(new SendFeedback());
                        }else  if(position==3){
                            fragmentcalling(new VRegistnOne());
                        }else  if(position==4){
                            Logoutpopup(activity);
                        }
                    }else {
                        if(position==1){
                            fragmentcalling(new ChangePassword());
                        }else  if(position==0){

                            fragmentcalling(new EditProfile());
                        }else  if(position==2){
                           fragmentcalling(new SendFeedback());
                        }else  if(position==4){
                            Logoutpopup(activity);
                        }
                    }

                }
            });

            if (position==3){
                prfl_lay_one.setVisibility(View.VISIBLE);
            }else{
                prfl_lay_one.setVisibility(View.GONE);
            }

        }else if (formtype.equalsIgnoreCase("hmpg_catgries")) {
            components.CustomizeTextview(hm_pg_ctgries_txt, Constants.XNormal, R.color.serch_nrml_clr, datalist.get(position).hmpg_ctgries_names, Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,7,0,0});
            components.CustomizeImageview(hm_pg_ctgries_img, new int[]{80,80},datalist.get(position).hmpg_ctgries_imgs, new int[]{0,0,0,0});
            hmpg_ctgries_lstitm_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentcalling(new ProductDetailsMain());
                }
            });
        }else if (formtype.equalsIgnoreCase("hmpg_keywords")) {
            components.CustomizeTextview(hm_pg_poplrkeywrds_titl_txt, Constants.XNormal, R.color.ctgries_clr, datalist.get(position).hmpg_keywords_names, Constants.MatchCenterNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeImageview(hm_pg_poplrkeywrds_img, new int[]{50,50},datalist.get(position).hmpg_keywords_imgs, new int[]{0,0,0,0});
            components.CustomizeTextview(hm_pg_poplrkeywrds_dscrptn_txt, Constants.Medium, R.color.serch_nrml_clr, datalist.get(position).hmpg_keywords_dscrptn, Constants.MatchCenterNormal+Constants.Roboto, new int[]{0,3,0,0});
        }else if (formtype.equalsIgnoreCase("offerslist")) {
            components.CustomizeImageview(ofrs_brnd_img, new int[]{110,110},datalist.get(position).ofrscatgryimglist, new int[]{0,0,0,0});
            components.CustomizeImageview(ofrs_mange_img, new int[]{12,12},R.drawable.edit_icon, new int[]{0,0,0,0});
            components.ApplyTint(ofrs_mange_img,R.color.white);
            components.CustomizeTextview(ofrs_brnd_tx, Constants.XXNormal,R.color.sport_shoos_txt_clr,datalist.get(position).ofrsbrndlst,Constants.WrapLeftBold+Constants.Roboto, new int[]{8,0,0,0});
            components.CustomizeTextview(ofrs_prmocde_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.prmocde),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_prmocde1_tx, Constants.XSmall,R.color.ofrs_listitem_clr,datalist.get(position).ofrsprmolst,Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_strtdte_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.strtdate),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_strtdte1_tx, Constants.XSmall,R.color.ofrs_listitem_clr,datalist.get(position).ofrstartlst,Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_endate_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.enddate),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_endate1_tx, Constants.XSmall,R.color.ofrs_listitem_clr,datalist.get(position).ofrsendlst,Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(ofrs_discnt_tx, Constants.Medium,R.color.ofrs_listitem_clr,datalist.get(position).ofrsdscntlst,Constants.WrapLeftNormal+Constants.Roboto, new int[]{30,5,0,0});
            components.CustomizeTextview(ofrs_mange_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.manage),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,0});
        }else if(formtype.equalsIgnoreCase("BillHistory_one")) {
            components.CustomizeTextview(bill_bloods_txtvw, Constants.Medium,R.color.light_ash_two,datalist.get(position).bill_blood_txtvw,Constants.WrapCenterBold+Constants.Roboto, new int[]{15,13,0,13});
            components.CustomizeTextview(bill_mn_dt_yr_txtvw, Constants.Small,R.color.light_ash_three,datalist.get(position).bill_dt_mn_one,Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,13,15,13});
        }else if(formtype.equalsIgnoreCase("BillHistory_two")) {
            components.CustomizeTextview(bill_xray_txtvw, Constants.Normal,R.color.light_ash_two,datalist.get(position).bill_xray_txtvw,Constants.WrapCenterBold+Constants.Roboto, new int[]{15,13,0,13});
            components.CustomizeTextview(bill_mns_dts_yrs_txtvw, Constants.Small,R.color.light_ash_three,datalist.get(position).bill_dt_mn_two,Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,13,15,13});
        }else if(formtype.equalsIgnoreCase("vendor_homepage")){
            components.CustomizeTextview(hm_ctgries_txt, Constants.Medium,R.color.light_ash_three,datalist.get(position).hmpg_ctgries_names,Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,10});

            if(position==0){
                components.CustomizeImageview(hm_ctgries_img, new int[]{35,40},R.drawable.offers, new int[]{0,10,0,10});
            }else if(position==1){
                components.CustomizeImageview(hm_ctgries_img, new int[]{35,40},R.drawable.product, new int[]{0,10,0,10});
            } else if(position==2){
                components.CustomizeImageview(hm_ctgries_img, new int[]{35,40},R.drawable.product, new int[]{0,10,0,10});
            }else{
                components.CustomizeImageview(hm_ctgries_img, new int[]{35,40},R.drawable.generate_bills, new int[]{0,10,0,10});

            }
            components.ApplyTint(hm_ctgries_img,R.color.blue_color);
            vendorhp_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0){
                        fragmentcalling(new ManageOffersMain());
                    }else if(position==1){
                        fragmentcalling(new ManageProductsMain());
                    } else if(position==2){
                        fragmentcalling(new ManageCategoriesMain());
                    }else{
                        fragmentcalling(new GenerateBill());
                    }

                }
            });
        }

        else if (formtype.equalsIgnoreCase("productslist")) {
            components.CustomizeImageview(prdctbrnd_img, new int[]{110,110},datalist.get(position).ofrscatgryimglist, new int[]{0,0,0,0});
            components.CustomizeImageview(prdctmange_img, new int[]{12,12},R.drawable.edit_icon, new int[]{0,0,0,0});
            components.ApplyTint(prdctmange_img,R.color.white);
            components.CustomizeTextview(prdctname_tx, Constants.XXNormal,R.color.sport_shoos_txt_clr,"Sport Shoes",Constants.WrapLeftBold+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(prdctcost_tx, Constants.Normal,R.color.blue_color,"$1,500",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,3,0,0});
            components.CustomizeTextview(prdctdiscntprice_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"6999,00",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(prdctdiscnt_tx, Constants.XSmall,R.color.serch_nrml_clr,"( -50% )",Constants.WrapLeftNormal+Constants.Roboto, new int[]{5,0,0,0});
            components.CustomizeTextview(procount_tx, Constants.XSmall,R.color.ofrs_listitem_clr,"(40)",Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(prdctmange_tx, Constants.XSmall,R.color.serch_nrml_clr,activity.getResources().getString(R.string.manage),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,0});
        }
        else if (formtype.equalsIgnoreCase("catgrydetails")) {
            //components.CustomizeImageviewBackground(cat_detls_brnd_img, new int[]{110, 110}, datalist.get(position).catgrydetlsimglist, new int[]{0,0, 0, 0});
            components.CustomizeTextview(cat_detls_name_tx, Constants.Large, R.color.sport_shoos_txt_clr, "Sport Shoes", Constants.WrapLeftSemiBold + Constants.Roboto, new int[]{10, 7, 0, 0});
            components.CustomizeTextview(cat_detls_cost_tx, Constants.Normal, R.color.blue_color, "$1,500", Constants.WrapLeftSemiBold  + Constants.Roboto, new int[]{10, 5, 0, 0});
            components.CustomizeTextview(cat_detls_discntprice_tx, Constants.XSmall, R.color.ofrs_listitem_clr, "6999,00", Constants.WrapLeftNormal + Constants.Roboto, new int[]{10, 5, 0, 0});
            components.CustomizeTextview(cat_detls_discnt_tx, Constants.XSmall, R.color.ofrs_listitem_clr, "( -50% )", Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});
            components.CustomizeTextview(cat_detls_ratngcount_tx, Constants.XSmall, R.color.ofrs_listitem_clr, "(40)", Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 0, 0, 0});

           // components.CustomizeWithBgTextview(prostock_tx, Constants.XXSmall, R.color.white, "Out of Stock",R.drawable.transprant_bg, Constants.WrapLeftNormal + Constants.Roboto, new int[]{5,5, 0, 0});

            catgry_lstitm_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentcalling(new ProductDetailsMain());
                }
            });

        } else if (formtype.equalsIgnoreCase("catgrygrddetails")) {
           // components.CustomizeImageviewBackground(cat_detls_grd_brnd_img, new int[]{Constants.matchParent, 160}, datalist.get(position).catgrydetlsgrdimglist, new int[]{0, 0, 0, 0});
            components.CustomizeTextview(cat_detls_grd_name_tx, Constants.Large, R.color.sport_shoos_txt_clr, "Sport Shoes", Constants.WrapLeftSemiBold + Constants.Roboto, new int[]{5, 0, 0, 0});
            components.CustomizeTextview(cat_detls_grd_cost_tx, Constants.Normal, R.color.blue_color, "$1,500", Constants.WrapLeftSemiBold + Constants.Roboto, new int[]{5, 2, 0, 0});
            components.CustomizeTextview(cat_detls_grd_discntprice_tx, Constants.XSmall, R.color.ofrs_listitem_clr, "6999,00", Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});
            components.CustomizeTextview(cat_detls_grd_discnt_tx, Constants.XSmall, R.color.ofrs_listitem_clr, "( -50% )", Constants.WrapLeftNormal + Constants.Roboto, new int[]{5, 0, 0, 0});
            components.CustomizeTextview(cat_detls_grd_ratngcount_tx, Constants.XSmall, R.color.ofrs_listitem_clr, "(40)", Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 0, 0, 0});

            ctgry_griditm_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentcalling(new ProductDetailsMain());
                }
            });

        }else if (formtype.equalsIgnoreCase("cart_rv_ordr_rvw")) {

            Glide.with(activity)
                    .load(Uri.parse(StoredUrls.MainUrl+datalist.get(position).cart_proimg)) // add your image url
                    // .transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                    .placeholder(R.drawable.imagenotfound)
                    .into(cart_rv_prdct_img);
            components.CustomizeTextview(cart_rv_prdct_name_txt, Constants.XXNormal, R.color.nrml_txt_clr, datalist.get(position).cart_proname, Constants.WrapLeftNormal + Constants.Roboto, new int[]{0, 0, 0, 0});
            components.CustomizeTextview(cart_rv_prdct_type_txt, Constants.XXNormal, R.color.sport_shoos_txt_clr,datalist.get(position).cart_proname, Constants.WrapLeftNormal + Constants.Roboto, new int[]{0,0,0,0});
            components.CustomizeTextview(cart_rv_no_of_prdcts_txt, Constants.Medium, R.color.rvw_txt_clr,  datalist.get(position).cart_proqty, Constants.WrapCenterNormal + Constants.Roboto, new int[]{0,0,0,0});
            //String[] attributes=datalist.get(position).cart_attrid.split(",");
            components.CustomizeTextview(cart_rv_price_of_prdct_txt, Constants.Medium, R.color.rvw_txt_clr,"Rs." +datalist.get(position).cart_proprice, Constants.WrapRightNormal + Constants.SFUIText, new int[]{0,0,0,0});
        }else if (formtype.equalsIgnoreCase("price_rnge_rvww")) {

            components.CustomizeTextview(price_rnge_txtt, Constants.XSmall, R.color.nrml_txt_clr, datalist.get(position).price, Constants.WrapCenterNormal + Constants.Roboto, new int[]{20,10,20,10});

            if(datalist.get(position).layout_highlight.equalsIgnoreCase("Yes")){
                price_rnge_layy.setBackground(activity.getResources().getDrawable(R.drawable.lay_bg));
            }else{
                price_rnge_layy.setBackground(activity.getResources().getDrawable(R.drawable.lstitm_bg));
            }

            price_rnge_layy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdatePriceRngeLayout(datalist,datalist.get(position),"Yes",position);
                }
            });

        }else if (formtype.equalsIgnoreCase("offr_rnge_rvww")) {
            components.CustomizeTextview(offr_rnge_txtt, Constants.XSmall, R.color.nrml_txt_clr, datalist.get(position).price, Constants.WrapCenterNormal + Constants.Roboto, new int[]{20,10,20,10});

            if(datalist.get(position).layout_highlight.equalsIgnoreCase("Yes")){
                offr_rnge_layy.setBackground(activity.getResources().getDrawable(R.drawable.lay_bg));
            }else{
                offr_rnge_layy.setBackground(activity.getResources().getDrawable(R.drawable.lstitm_bg));
            }

            offr_rnge_layy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateOffrRngeLayout(datalist,datalist.get(position),"Yes",position);
                }
            });

        }


    }

    public void fragmentcalling(Fragment fragment) {

        FragmentManager fragmentManager = ((FragmentActivity)activity).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack("").commit();

    }

    public void UpdatePriceRngeLayout(ArrayList<DumpData> updatelist, DumpData dumpData, String value, int position) {
        ArrayList<DumpData> datalist=new ArrayList<>();
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
        for (int k=0;k<updatelist.size();k++){
            DumpData data=new DumpData();
            data.price = updatelist.get(k).price;
            if(position==k){
                data.layout_highlight="Yes";
            }else{
                data.layout_highlight="No";
            }
            datalist.add(data);
        }

        CustomRecyclerview recyclerview = new CustomRecyclerview(activity);
        recyclerview.Assigndatatorecyleview(CustomerProductlist.price_rnge_rvw, datalist,"price_rnge_rvww", Constants.Gridview,4,
                Constants.ver_orientation,R.layout.fltr_price_rnge_lstitm);

    }

    public void UpdateOffrRngeLayout(ArrayList<DumpData> updatelist1, DumpData dumpData, String value, int position) {
        ArrayList<DumpData> datalist=new ArrayList<>();
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
        for (int k=0;k<updatelist1.size();k++){
            DumpData data=new DumpData();
            data.price = updatelist1.get(k).price;
            if(position==k){
                data.layout_highlight="Yes";
            }else{
                data.layout_highlight="No";
            }
            datalist.add(data);
        }

        CustomRecyclerview recyclerview1 = new CustomRecyclerview(activity);
        recyclerview1.Assigndatatorecyleview(CustomerProductlist.offr_rnge_rvw, datalist,"offr_rnge_rvww", Constants.Gridview,4,
                Constants.ver_orientation,R.layout.offr_rnge_lstitm);

    }



    private void Manageofferpopup(LinearLayout viewpager_lay, ArrayList<DumpData> datalist, int position){

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

            mPopupWindow.showAtLocation(viewpager_lay, Gravity.CENTER, 70, 70);

        }else if(position==datalist.size()-1){
            up_arw_lay.setVisibility(View.GONE);
            down_arw_lay.setVisibility(View.VISIBLE);

            mPopupWindow.showAtLocation(viewpager_lay, Gravity.BOTTOM, 210, 210);

        }else{
            up_arw_lay.setVisibility(View.VISIBLE);
            down_arw_lay.setVisibility(View.GONE);
        }*/

        components.CustomizeImageview(mange_uparow_img, new int[]{45,35},R.drawable.up_arroww_, new int[]{0,0,5,-10});
        //components.ApplyTint(mange_uparow_img,R.color.blue_color);
        components.CustomizeImageview(mange_dwnarow_img, new int[]{45,35},R.drawable.down_arroww, new int[]{0,-10,5,0});
        components.CustomizeImageview(mange_actv_img, new int[]{20,20},R.drawable.deleteimage, new int[]{10,8,3,8});
        components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.actve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
        components.ApplyTint(mange_actv_img,R.color.white);
        components.CustomizeImageview(mange_inactv_img, new int[]{20,20},R.drawable.deleteimage, new int[]{10,8,3,8});
        components.CustomizeTextview(mange_inactv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.inactve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
        components.ApplyTint(mange_inactv_img,R.color.white);
        components.CustomizeImageview(mange_mange_img, new int[]{20,20},R.drawable.deleteimage, new int[]{10,8,3,8});
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
                String id="";
                String status="";
                Activatepopup(activity,"ofr",id,status);
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
                fragmentcalling(new EditOffers());
                mPopupWindow.dismiss();
            }
        });

        mange_delt_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // DisplayCnclPopup(activity,"offers");
                String id="";
                Deletepopup(activity,"ofr",id);
                mPopupWindow.dismiss();
            }
        });

    }



            private void Manageproductspopup(LinearLayout viewpager_lay, ArrayList<DumpData> datalist, int position){

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

                    components.CustomizeImageview(mange_uparow_img, new int[]{45,35},R.drawable.up_arroww_, new int[]{0,0,5,-10});
                //components.ApplyTint(mange_uparow_img,R.color.blue_color);
                    components.CustomizeImageview(mange_dwnarow_img, new int[]{45,35},R.drawable.down_arroww, new int[]{0,-10,5,0});
                    components.CustomizeImageview(mange_actv_img, new int[]{20,20},R.drawable.deleteiconrounded, new int[]{10,8,3,8});
                    components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.actve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
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
                        String id="";
                        String status="";
                        Activatepopup(activity,"pro",id,status);
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
                        String id="";
                        Deletepopup(activity,"pro",id);
                        mPopupWindow.dismiss();
                    }
                });

            }



                private void SetStatuspopup(LinearLayout viewpager_lay, ArrayList<DumpData> datalist, int position, final String type){

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
                            /*if (position>centerPosition){
                            up_arw_lay.setVisibility(View.GONE);
                            down_arw_lay.setVisibility(View.VISIBLE);

                            mPopupWindow.showAtLocation(viewpager_lay, Gravity.CENTER, 150, 150);

                            }else if(position==datalist.size()-1){
                            up_arw_lay.setVisibility(View.GONE);
                            down_arw_lay.setVisibility(View.VISIBLE);

                            mPopupWindow.showAtLocation(viewpager_lay, Gravity.BOTTOM, 190, 190);

                            }else{
                            up_arw_lay.setVisibility(View.VISIBLE);
                            down_arw_lay.setVisibility(View.GONE);
                            }*/


                            components.CustomizeImageview(mange_uparow_img, new int[]{45,35},R.drawable.up_arroww_, new int[]{0,0,5,-10});
                            //components.ApplyTint(mange_uparow_img,R.color.blue_color);
                            components.CustomizeImageview(mange_dwnarow_img, new int[]{45,35},R.drawable.down_arroww, new int[]{0,-10,5,0});
                            components.CustomizeImageview(mange_actv_img, new int[]{20,20},R.drawable.deleteiconrounded, new int[]{10,8,3,8});
                            components.CustomizeTextview(mange_actv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.actve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
                            components.ApplyTint(mange_actv_img,R.color.white);
                            components.CustomizeImageview(mange_inactv_img, new int[]{20,20},R.drawable.deleteiconrounded, new int[]{10,8,3,8});
                            components.CustomizeTextview(mange_inactv_tx, Constants.Normal,R.color.white,activity.getResources().getString(R.string.inactve),Constants.WrapCenterNormal+Constants.Roboto, new int[]{5,0,0,0});
                            components.ApplyTint(mange_inactv_img,R.color.white);
                            components.CustomizeImageview(mange_mange_img, new int[]{20,20},R.drawable.deleteiconrounded, new int[]{10,8,3,8});
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
                                String id="";
                                String status="";
                                Activatepopup(activity,"category",id,status);

                            }else{
                                String id="";
                                String status="";
                                Activatepopup(activity,"sub_category",id,status);

                            }
                                mPopupWindow.dismiss();
                                }
                       });



                    mange_mange_lay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                               if(type.equalsIgnoreCase("category")){
                                   String id="";
                                   Deletepopup(activity,"category",id);

                               }else{
                                   String id="";
                                   Deletepopup(activity,"sub_category",id);

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

    private void OrderDetailsPopup(final Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_details_popup);
        dialog.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        String[] odr_detls_nos  = {"1","2","1","1","1","1","1"};
        String[]  odr_detls_brnds = {"Nike shoses","Nike T-shirt","L&V Jean","Thuong dihh shoes","Goi mi tom","Comdam package","Apple smart watch"};
        String[] odr_detls_brndprces  = {"$1200.00","$840.22","$4200.00","$110.00","$1.00","$12.00","$1200.00"};

        RecyclerView odr_pp_listview = (RecyclerView)dialog.findViewById(R.id.odr_pp_listview);

        StoredObjects.order_popup_arraylist.clear();

        for (int i = 0;i<odr_detls_nos.length;i++){
            DumpData dumpData = new DumpData();
            dumpData.ordernumbers = odr_detls_nos[i];
            dumpData.orderbrands = odr_detls_brnds[i];
            dumpData.orderbrandprice = odr_detls_brndprces[i];

            StoredObjects.order_popup_arraylist.add(dumpData);
        }

        CustomRecyclerview recyclerview = new CustomRecyclerview(activity);
        recyclerview.Assigndatatorecyleview(odr_pp_listview,StoredObjects.order_popup_arraylist,"orderpopup", Constants.Listview,0, Constants.ver_orientation,R.layout.order_details_popup_listitems);



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
        components.CustomizeTextview(odr_pp_odrid_txt, Constants.XXNormal,R.color.blue_color,activity.getResources().getString(R.string.ordernum),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(odr_pp_odrdate_txt, Constants.Medium,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.orderdate),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(odr_pp_fee_txt, Constants.Medium,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.fee),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,15,0});
        components.CustomizeTextview(odr_pp_feeprce_txt, Constants.Medium,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.feeprce),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(odr_pp_vat_txt, Constants.Medium,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.vat_tax),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,15,0});
        components.CustomizeTextview(odr_pp_vatprce_txt, Constants.Medium,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.vatprce),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(odr_pp_totl_txt, Constants.Normal,R.color.sport_shoos_txt_clr,activity.getResources().getString(R.string.grnd_total),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,15,0,0});
        components.CustomizeTextview(odr_pp_totlprce_txt, Constants.XXNormal,R.color.blue_color,activity.getResources().getString(R.string.totalprce),Constants.WrapCenterSemiBold+Constants.Roboto, new int[]{0,0,0,20});

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

    private void Deletepopup(final Activity activity,final String type,String id) {

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
               /* if(type.equalsIgnoreCase("ofr")){
                    parsing_methods(0, StoredUrls.DeleteOffer_url,"id="+id,Constants.POSTMETHOD,"ofr");
                }else if(type.equalsIgnoreCase("pro")){
                    parsing_methods(0, StoredUrls.DeleteProduct_url,"id="+id,Constants.POSTMETHOD,"pro");
                }else if(type.equalsIgnoreCase("category")){
                    parsing_methods(0, StoredUrls.DeleteCategory_url,"id="+id,Constants.POSTMETHOD,"category");
                }else{
                    parsing_methods(0, StoredUrls.DeleteSubCategory_url,"id="+id,Constants.POSTMETHOD,"sub_category");
                }
*/
            }
        });

        dialog.show();

    }
    private void Activatepopup(final Activity activity,final String type,final String id,final String status) {

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
            components.CustomizeTextview(dialog_alert_txt,Constants.XNormal,R.color.sport_shoos_txt_clr,activity.getApplicationContext().getResources().getString(R.string.deleteproducttxt),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});

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
              /*  if(type.equalsIgnoreCase("ofr")){
                    parsing_methods(1, StoredUrls.UpdateOfferstatus_url,"id="+id+"&status="+status,Constants.POSTMETHOD,"ofr"+"@"+status);
                }else if(type.equalsIgnoreCase("pro")){
                    parsing_methods(1, StoredUrls.UpdateProductstatus_url,"id="+id+"&status="+status,Constants.POSTMETHOD,"pro"+"@"+status);
                }else if(type.equalsIgnoreCase("category")){
                    parsing_methods(1, StoredUrls.UpdateCategorystatus_url,"id="+id+"&status="+status,Constants.POSTMETHOD,"category"+"@"+status);
                }else{
                    parsing_methods(1, StoredUrls.UpdateSubctgrystatus_url,"id="+id+"&status="+status,Constants.POSTMETHOD,"sub_category"+"@"+status);
                }
*/

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
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.activatedproducttxt),activity);
                                    }else{
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.inactivatedproducttxt),activity);
                                    }
                                }else if(parts[0].equalsIgnoreCase("ofr")){
                                    if(parts[1].equalsIgnoreCase("Inactive")){
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.activatedofrtxt),activity);
                                    }else{
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.inactivatedofrtxt),activity);
                                    }

                                }else if(parts[0].equalsIgnoreCase("category")){
                                    if(parts[1].equalsIgnoreCase("Inactive")){
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.activatedctgrytxt),activity);
                                    }else{
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.inactivatedctgrytxt),activity);
                                    }

                                }else{
                                    if(parts[1].equalsIgnoreCase("Inactive")){
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.activatedsubctgrytxt),activity);
                                    }else{
                                        StoredObjects.ToastMethod(activity.getResources().getString(R.string.inactivatedsubctgrytxt),activity);
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
                        // {"status":200,"message":"success","results_count":2,"results":[{"category_id":1,"category_name":"IT"},{"category_id":2,"category_name":"another"}]}
                      /*  try {
                            categorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
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