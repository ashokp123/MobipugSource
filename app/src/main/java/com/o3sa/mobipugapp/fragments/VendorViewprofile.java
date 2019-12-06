package com.o3sa.mobipugapp.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kiran on 31-10-2018.
 */

public class VendorViewprofile extends Fragment {


    BasicComponents components;
    EditText vregn_oldpswd_edtx,vregn_newpswd_edtx,vregn_cnfmpswd_edtx;
    Button vregn_sbmt_btn;
    public ArrayList<HashMap<String, String>> profilelist,packageslist,categorieslist,subcategorieslist,attributeslist,subattributeslist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vendor_chngepaswrd_popup,container,false);

        intialization(v);
        assigndata();
        servicecalling();
        return v;
    }

    public void intialization(View v){
        components=new BasicComponents(getActivity());
        StoredObjects.pagetype="changepassword";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.changepswd),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});


        vregn_oldpswd_edtx=(EditText) v.findViewById(R.id.vregn_oldpswd_edtx);
        vregn_newpswd_edtx=(EditText) v.findViewById(R.id.vregn_newpswd_edtx);
        vregn_cnfmpswd_edtx=(EditText) v.findViewById(R.id.vregn_cnfmpswd_edtx);

        vregn_sbmt_btn =(Button) v.findViewById(R.id.vregn_sbmt_btn);

    }

    public void assigndata(){

        components.CustomizeEditview(vregn_oldpswd_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.oldpswd),R.drawable.shadoweffect, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{0,0,0,0});
        components.CustomizeEditview(vregn_newpswd_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.newpswd),R.drawable.shadoweffect, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{0,10,0,0});
        components.CustomizeEditview(vregn_cnfmpswd_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.confrmpassword),R.drawable.shadoweffect, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{0,10,0,0});
        components.SetPasswordHint(vregn_oldpswd_edtx);
        components.SetPasswordHint(vregn_newpswd_edtx);
        components.SetPasswordHint(vregn_cnfmpswd_edtx);
        components.CustomizeButton(vregn_sbmt_btn, Constants.XXNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.submit_),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,45}, new int[]{0,22,0,22});

    }

    private void servicecalling() {
        String full_name="";
        String fax="";
        String email="";
        String business_name="";
        String business_address="";
        String about_us="";
        String telephone="";
        String pincode="";
        String latitude="";
        String longitude="";
        String map_address="";
        String account_number="";

        String ifsc_code="";
        String website="";
        String id_proof="";
        String pan_card="";
        String package_type="";
        String category_id="";
        String sub_categories="";
        String main_category="";
        String business_images="";
        String business_background_images="";

        String parameters= "&full_name="+full_name+"&fax="+fax+"&email="+email+"&business_name="+business_name+"&business_address="+business_address+
                "&about_us="+about_us+"&telephone="+telephone+"&pincode="+pincode
                +"&latitude="+latitude+"&longitude="+longitude+"&map_address="+map_address+
                "&account_number="+account_number+"&ifsc_code="+ifsc_code+"&website="+website+
                "&id_proof="+id_proof+"&pan_card="+pan_card+"&package_type="+package_type+
                "&category_id="+category_id+"&sub_categories="+sub_categories+"&main_category="+main_category+
                "&business_images="+business_images+"&business_background_images="+business_background_images;
        parsing_methods(0, StoredUrls.VendorGetprofile_url,"vendor_id="+StoredObjects.UserId,Constants.POSTMETHOD);
        parsing_methods(1, StoredUrls.VendorEditprofile_url,"vendor_id="+StoredObjects.UserId+parameters,Constants.POSTMETHOD);
        parsing_methods(2, StoredUrls.GetPackages_url,"",Constants.POSTMETHOD);
        parsing_methods(3, StoredUrls.GetVendorCategories_url,"",Constants.POSTMETHOD);
        String parent_category_id="";
        parsing_methods(4, StoredUrls.GetVendorSubCategories_url,"parent_category_id="+parent_category_id,Constants.POSTMETHOD);
        parsing_methods(5, StoredUrls.GetAttributes_url,"",Constants.POSTMETHOD);
        String parent_attr_id="";
        parsing_methods(6, StoredUrls.GetSubAttributes_url,"parent_attr_id="+parent_attr_id,Constants.POSTMETHOD);


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

                                profilelist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                      /*      profilelist.get(0).get("full_name");
                            profilelist.get(0).get("fax");
                            profilelist.get(0).get("email");
                            profilelist.get(0).get("business_name");
                            profilelist.get(0).get("business_address");
                            profilelist.get(0).get("about_us");
                            profilelist.get(0).get("telephone");
                            profilelist.get(0).get("password");
                            profilelist.get(0).get("pincode");
                            profilelist.get(0).get("latitude");
                            profilelist.get(0).get("longitude");
                            profilelist.get(0).get("map_address");
                            profilelist.get(0).get("account_number");
                            profilelist.get(0).get("ifsc_code");
                            profilelist.get(0).get("website");
                            profilelist.get(0).get("id_proof");
                            profilelist.get(0).get("pan_card");
                            profilelist.get(0).get("package_type");
                            profilelist.get(0).get("category_id");
                            profilelist.get(0).get("sub_categories");
                            profilelist.get(0).get("pincode");
                            profilelist.get(0).get("main_category");
                            profilelist.get(0).get("business_images_count");
                            profilelist.get(0).get("business_images");
                            profilelist.get(0).get("business_background_images_count");
                            profilelist.get(0).get("business_background_images");*/

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else if(val==1){
                            try {
                                JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                                    String status = jsonObject.getString("status");
                                    if(status.equalsIgnoreCase("200")){

                                    }else{
                                        String error = jsonObject.getString("error");
                                        StoredObjects.ToastMethod(error,getActivity());
                                    }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else if(val==2){
                            //{"status":200,"message":"success","results_count":3,"results":[{"type_id":2,"type_name":"Rs.90\/month"},{"type_id":3,"type_name":"Rs.100\/month"}]}
                           /* try {
                                packageslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                                packageslist.get(position).get("type_id");
                                packageslist.get(position).get("type_name");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                        }else if(val==3){
                           // {"status":200,"message":"success","results_count":2,"results":[{"category_id":1,"category_name":"IT"},{"category_id":2,"category_name":"another"}]}
                            /*try {
                                categorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                                categorieslist.get(position).get("category_id");
                                categorieslist.get(position).get("category_name");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                        }else if(val==4){
                            // {"status":200,"message":"success","results_count":1,"results":[{"category_id":3,"category_name":"IT"}]}
                          /* try {
                               subcategorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                               subcategorieslist.get(position).get("category_id");
                               subcategorieslist.get(position).get("category_name");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                        }else if(val==5){
                            //{"status":200,"message":"success","results_count":3,"results":[{"attr_id":1,"attr_name":"Weight"},{"attr_id":3,"attr_name":"length"},{"attr_id":4,"attr_name":"dfghdfghdfg"}]}
                          /* try {
                               attributeslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                               attributeslist.get(position).get("attr_id");
                               attributeslist.get(position).get("attr_name");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                        }else if(val==6){
                            //{"status":200,"message":"success","results_count":2,"results":[{"attr_id":2,"attr_name":"Grams"},{"attr_id":5,"attr_name":"kghkhj"}]}
                         /* try {
                               subattributeslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                               subattributeslist.get(position).get("attr_id");
                               subattributeslist.get(position).get("attr_name");

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
