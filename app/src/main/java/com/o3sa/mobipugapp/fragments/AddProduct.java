package com.o3sa.mobipugapp.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.servicesparsing.InterNetChecker;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.storedobjects.DevicePermissions;
import com.o3sa.mobipugapp.storedobjects.ImageUpload;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.RobotTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Kiran on 21-10-2018.
 */

public class AddProduct extends Fragment {

    EditText addprdctname_edtx,addprdctdescr_edtx,prdctctgry_edtx,prdctsubctgry_edtx;//adprdctprice_edtx
    TextView addprdctdescr_tx,adprdctimage_tx,adprdctimgpick_tx,camera_tx,gallery_tx,stock_tx;//adprdctdetls_tx
    RadioButton avalblepro_rbtn,notavalblepro_rbtn;
    Button addproduct_btn;
    //ImageView addmsmnt_btn;
    private PopupWindow attributes_popup,subatrbts_popup,category_popup,subcategory_popup;

    BasicComponents components;
    LinearLayout main_msrmnt_lay,addpikedimgs_lay;//addmeasurmntlay
    public ArrayList<HashMap<String, String>> attributeslist,subattributeslist,categorieslist,subcategorieslist;

    LinearLayout adpro_slctctgry_lay,adpro_slctsubctgry_lay;
    String parent_id="",category_id="",subcategory_id="",images_array="";
    String product_status="";
    LinearLayout gallery_lay,camera_lay;


    String filename="";
    public static String fileName="";
    //image upload
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;
    protected static final int SELECT_FILE = 1;
    private static final int CAPTURE_CAMERA = 2;
    private static final int PIC_CROP = 3;
    private Bitmap myImg;
    private Uri picUri;

    ArrayList<String> imagesarray=new ArrayList<>();
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_CALENDAR:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String temp_image_path="";
                    Long tsLong = System.currentTimeMillis() / 1000;
                    String pic_name = tsLong.toString();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File dir = new File(Environment.getExternalStorageDirectory(), StoredObjects.picuri_path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File photo = new File(dir, pic_name+".jpg");


                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                    } else {
                        Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() +".fileprovider", photo);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                    }

                    startActivityForResult(intent, CAPTURE_CAMERA);
                    temp_image_path = photo.getAbsolutePath();
                    File imageFile = new File(temp_image_path);
                    picUri = Uri.fromFile(imageFile);
                } else {
                }



                break;
        }
    }
    LinearLayout proimgs_lay;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.addproduct,container,false);

        intialization(v);
        assigndata();
        servicecalling();

        return v;
    }



    public void intialization(View v){
        StoredObjects.pagetype="addproduct";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components=new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.add_product),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        imagesarray.clear();

        addprdctdescr_tx =(TextView) v.findViewById(R.id.addprdctdescr_tx);
        adprdctimage_tx= (TextView)v.findViewById(R.id.adprdctimage_tx);
        adprdctimgpick_tx =(TextView)v.findViewById(R.id.adprdctimgpick_tx);
        camera_tx =(TextView)v.findViewById(R.id.camera_tx);
        gallery_tx =(TextView)v.findViewById(R.id.gallery_tx);
        stock_tx =(TextView)v.findViewById(R.id.stock_tx);
        avalblepro_rbtn = (RadioButton)v.findViewById(R.id.avalblepro_rbtn);
        notavalblepro_rbtn = (RadioButton)v.findViewById(R.id.notavalblepro_rbtn);
        addproduct_btn = (Button)v.findViewById(R.id.addproduct_btn);
        addprdctname_edtx =(EditText)v.findViewById(R.id.addprdctname_edtx);
        addprdctdescr_edtx =(EditText)v.findViewById(R.id.addprdctdescr_edtx);
        prdctctgry_edtx =(EditText)v.findViewById(R.id.prdctctgry_edtx);
        prdctsubctgry_edtx=(EditText)v.findViewById(R.id.prdctsubctgry_edtx);
        main_msrmnt_lay=(LinearLayout) v.findViewById(R.id.main_msrmnt_lay);

        addpikedimgs_lay=(LinearLayout) v.findViewById(R.id.addpikedimgs_lay);
        adpro_slctsubctgry_lay=(LinearLayout) v.findViewById(R.id.adpro_slctsubctgry_lay);
        adpro_slctctgry_lay=(LinearLayout) v.findViewById(R.id.adpro_slctctgry_lay);
        camera_lay=(LinearLayout) v.findViewById(R.id.camera_lay);
        gallery_lay=(LinearLayout) v.findViewById(R.id.gallery_lay);
        proimgs_lay=(LinearLayout) v.findViewById(R.id.proimgs_lay);
        avalblepro_rbtn.setChecked(true);
        notavalblepro_rbtn.setChecked(false);
        proimgs_lay.setVisibility(View.GONE);
        product_status="Available";
        avalblepro_rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(avalblepro_rbtn.isChecked())
                {
                    product_status="Available";
                    avalblepro_rbtn.setChecked(true);
                    notavalblepro_rbtn.setChecked(false);
                }
            }
        });

        notavalblepro_rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notavalblepro_rbtn.isChecked())
                {
                    product_status="Out of Stock";
                    notavalblepro_rbtn.setChecked(true);
                    avalblepro_rbtn.setChecked(false);
                }
            }
        });
        prdctctgry_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              CategoryFilterPopup(prdctctgry_edtx,adpro_slctctgry_lay);
            }
        });
        adpro_slctctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFilterPopup(prdctctgry_edtx,adpro_slctctgry_lay);
            }
        });
        prdctsubctgry_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubCategoryFilterPopup(prdctsubctgry_edtx,adpro_slctsubctgry_lay);
            }
        });


        adpro_slctsubctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              SubCategoryFilterPopup(prdctsubctgry_edtx,adpro_slctsubctgry_lay);
            }
        });

        camera_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);

                    } else {

                        Imagepickingpopup(getActivity(),"0");
                    }
                } catch (Exception e) {
                }
            }
        });

        gallery_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imagepickingpopup(getActivity(),"1");
            }
        });


        addproduct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoredObjects.hide_keyboard_activity(getActivity());
                String proname=addprdctname_edtx.getText().toString();
                String prodescr=addprdctdescr_edtx.getText().toString();
                images_array="";
                if(imagesarray.size()>0){
                    for(int k=0;k<imagesarray.size();k++){
                        images_array=images_array+imagesarray.get(k);
                    }
                }else{

                }
                String imagesuploaded=images_array.substring(0,images_array.length()-1);



                JSONArray AttributesArray = new JSONArray();
                JSONObject jsonObject = null;
                for (int i= 0;i<StoredObjects.attributesarray.size();i++) {
                    try {
                        for(int k=0;k<StoredObjects.attributesarray.get(i).subattribts_array.size();k++){
                            jsonObject = new JSONObject();
                            jsonObject.put("id", StoredObjects.attributesarray.get(i).id);
                            jsonObject.put("main_attr_id", StoredObjects.attributesarray.get(i).subattribts_array.get(k).sa_attribte);
                            jsonObject.put("sub_attr_id", StoredObjects.attributesarray.get(i).subattribts_array.get(k).sa_subattribte);
                            jsonObject.put("name", StoredObjects.attributesarray.get(i).subattribts_array.get(k).sa_val);
                            jsonObject.put("price", StoredObjects.attributesarray.get(i).price);
                            AttributesArray.put(jsonObject);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (StoredObjects.inputValidation(prdctctgry_edtx, getActivity().getResources().getString(R.string.seclt_catgry),getActivity())) {
                    if (StoredObjects.inputValidation(prdctsubctgry_edtx, getActivity().getResources().getString(R.string.seclt_sub_catgry),getActivity())) {
                        if (StoredObjects.inputValidation(addprdctname_edtx, getActivity().getResources().getString(R.string.enterproname),getActivity())) {
                            if (StoredObjects.inputValidation(addprdctdescr_edtx, getActivity().getResources().getString(R.string.enterprodescr),getActivity())) {
                                         parsing_methods(5, StoredUrls.AddProduct_url,"vendor_id="+StoredObjects.UserId+"&stock_status="+product_status+"&category_id="+category_id+"&sub_category_id="+subcategory_id+"&product_description="+prodescr+"&product_name="+proname+"&attributes="+AttributesArray.toString()+"&product_images="+imagesuploaded,Constants.POSTMETHOD);
                                        }                                    }


                    }
                }
            }

        });
    }

    private void Imagepickingpopup(FragmentActivity activity,String type) {
        if(type.equalsIgnoreCase("0")){
            boolean result1 = DevicePermissions.checkPermission_storage(activity);
            boolean result = DevicePermissions.checkPermission(activity);
            boolean result2 = DevicePermissions.checkPermission_write(activity);

            if (result&&result1&&result2) {
                String temp_image_path = "";
                Long tsLong = System.currentTimeMillis() / 1000;
                String pic_name = tsLong.toString();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File dir = new File(Environment.getExternalStorageDirectory(), StoredObjects.picuri_path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File photo = new File(dir, pic_name + ".jpg");


                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                } else {
                    Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".fileprovider", photo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                }

                startActivityForResult(intent, CAPTURE_CAMERA);
                temp_image_path = photo.getAbsolutePath();
                File imageFile = new File(temp_image_path);
                picUri = Uri.fromFile(imageFile);
            }
        }else{
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            Intent chooserIntent = Intent.createChooser(getIntent, "Pick Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
            startActivityForResult(chooserIntent, SELECT_FILE);
        }


    }

    public void assigndata(){

       // components.CustomizeEditview(prdctctgry_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.sel_category),0,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,0});
        //components.CustomizeEditview(prdctsubctgry_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.sel_subcategory),0,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,0});

        components.CustomizeTextview(addprdctdescr_tx, Constants.Medium,R.color.thik_grey,getActivity().getApplicationContext().getResources().getString(R.string.product_descr),Constants.WrapLeftNormal+Constants.Gibson, new int[]{10,10,0,0});
        components.CustomizeTextview(adprdctimage_tx,Constants.Normal,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.product_imgs),Constants.WrapLeftNormal+Constants.Gibson, new int[]{10,10,0,10});
        components.CustomizeTextview(adprdctimgpick_tx,Constants.Small,R.color.black,getActivity().getApplicationContext().getResources().getString(R.string.product_pick),Constants.WrapLeftNormal+Constants.Gibson, new int[]{15,0,0,0});
        components.CustomizeTextview(stock_tx,Constants.Normal,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.stock),Constants.WrapLeftNormal+Constants.Gibson, new int[]{10,10,10,0});
        components.CustomizeButton(addproduct_btn, Constants.XNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.save),R.drawable.list_bottom_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,50}, new int[]{0,20,0,20});
        components.CustomizeEditview(addprdctname_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.product_name),R.drawable.shadoweffect,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,10,0,0});
        components.CustomizeMultilineEditview(addprdctdescr_edtx,Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.product_descrempty),R.drawable.shadoweffect,true,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,10,0,0},4);

        main_msrmnt_lay.removeAllViews();

        StoredObjects.attributesarray.clear();

        for(int i=0;i<1;i++){
            DumpData data=new DumpData();
            data.id="";
            data.main_attr_id="";
            data.sub_attr_id="";
            data.name="";
            data.price="";
            data.first_time="Yes";
            data.subattribts_array=getOptionsdata("Yes");
            StoredObjects.attributesarray.add(data);
        }

        for (int i = 0; i <StoredObjects.attributesarray.size(); i++) {
            addlayout(main_msrmnt_lay,StoredObjects.attributesarray,i);
        }

    }

    public void addsublayout(final LinearLayout layout, final ArrayList<DumpData> datalist, final int pos){
        View v	=LayoutInflater.from(getActivity()).inflate(R.layout.addmeasrmnt_item, null);
        final EditText mesrmntname_edt=(EditText) v.findViewById(R.id.mesrmntname_edt);
        final EditText mesrmntval_edt=(EditText) v.findViewById(R.id.mesrmntval_edt);
        final EditText mesrmntunit_edt=(EditText) v.findViewById(R.id.mesrmntunit_edt);
        ImageView addmoremsmnt_btn=(ImageView) v.findViewById(R.id.addmoremsmnt_btn);
        ImageView removemoremsmnt_btn=(ImageView) v.findViewById(R.id.removemoremsmnt_btn);

        if(datalist.get(pos).sa_firsttime.equalsIgnoreCase("Yes")){
            removemoremsmnt_btn.setVisibility(View.GONE);
            addmoremsmnt_btn.setVisibility(View.VISIBLE);
        }else{
            addmoremsmnt_btn.setVisibility(View.GONE);
            removemoremsmnt_btn.setVisibility(View.VISIBLE);
        }
        mesrmntval_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String name = mesrmntval_edt.getText().toString().trim();
                if (!hasFocus) {
                    if(name!=null&&name.length()>0){
                        Subdatalistupdated(datalist,datalist.get(pos),name,"name");
                    }
                }else{


                }
            }
        });
        removemoremsmnt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeViewAt(pos);
                subupdatedremove(datalist,pos,layout);



            }
        });
        mesrmntname_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterPopup(mesrmntname_edt,datalist,datalist.get(pos));
            }
        });
        mesrmntunit_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subattributeslist.size()==0){
                    StoredObjects.ToastMethod("No units found",getActivity());
                }else{
                    SubFilterPopup(mesrmntunit_edt,datalist,datalist.get(pos));
                }

            }
        });


        addmoremsmnt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subupdatedbyOne(datalist);
                for (int i =datalist.size()-1; i <datalist.size(); i++) {
                    addsublayout(layout,datalist,i);
                }
            }
        });
        layout.addView(v);
    }
    public void addlayout(final LinearLayout layout, final ArrayList<DumpData> datalist,final int pos){
        View v	=LayoutInflater.from(getActivity()).inflate(R.layout.main_msrmnt_listitem, null);
        final EditText adprdctprice_edtx=(EditText) v.findViewById(R.id.adprdctprice_edtx);
        TextView adprdctdetls_tx=(TextView) v.findViewById(R.id.adprdctdetls_tx);
        ImageView addmsmnt_btn=(ImageView) v.findViewById(R.id.addmsmnt_btn);
        ImageView removemsmnt_btn=(ImageView) v.findViewById(R.id.removemsmnt_btn);
        final LinearLayout addmeasurmntlay=(LinearLayout) v.findViewById(R.id.addmeasurmntlay);
        components.CustomizeEditview(adprdctprice_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.amount),R.drawable.shadoweffect,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{10,10,10,10});
        components.CustomizeTextview(adprdctdetls_tx,Constants.Normal,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.product_details),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
        adprdctprice_edtx.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        if(datalist.get(pos).first_time.equalsIgnoreCase("Yes")){
            removemsmnt_btn.setVisibility(View.GONE);
            addmsmnt_btn.setVisibility(View.VISIBLE);
        }else{
            addmsmnt_btn.setVisibility(View.GONE);
            removemsmnt_btn.setVisibility(View.VISIBLE);
        }
        removemsmnt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeViewAt(pos);
                removemainlay(datalist,pos,layout);
            }
        });
        addmeasurmntlay.removeAllViews();
        for (int i = 0; i <1; i++) {
            addsublayout(addmeasurmntlay,StoredObjects.attributesarray.get(pos).subattribts_array,i);
        }
        addmsmnt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<1;i++){
                    DumpData data=new DumpData();
                    data.id="";
                    data.price="";
                    data.first_time="No";
                    data.subattribts_array=getOptionsdata("Yes");
                    StoredObjects.attributesarray.add(data);
                }

                for (int i = StoredObjects.attributesarray.size()-1; i <StoredObjects.attributesarray.size(); i++) {
                    addlayout(main_msrmnt_lay,StoredObjects.attributesarray,i);
                }
            }
        });

       // ts_notes_edt.setImeOptions(EditorInfo.IME_ACTION_DONE);

       /* ts_notes_edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    ts_notes_edt.clearFocus();
                }
                return false;
            }
        });*/
        adprdctprice_edtx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String amount = adprdctprice_edtx.getText().toString().trim();
                if (!hasFocus) {
                    if(amount!=null&&amount.length()>0){
                        DataupdatedbyOne(datalist, datalist.get(pos),amount);

                    }
                }else{


                }
            }
        });
        layout.addView(v);
    }

    private void removemainlay(ArrayList<DumpData> datalist, int pos, LinearLayout layout) {
        datalist.remove(pos);
        layout.removeAllViews();
        for (int i = 0; i <datalist.size(); i++) {
            addlayout(layout,datalist,i);
        }
    }

    public static ArrayList<DumpData> getOptionsdata(String type) {

        ArrayList<DumpData> arrayList = new ArrayList<>();
        arrayList.clear();

            for (int i = 0; i <1; i++) {
                DumpData dumpclass = new DumpData();

                dumpclass.sa_subattribte = "";
                dumpclass.sa_val= "";
                dumpclass.sa_firsttime=type;
                dumpclass.sa_attribte="";
                arrayList.add(dumpclass);

            }

        return arrayList;
    }
    public void DataupdatedbyOne(ArrayList<DumpData> productlist, DumpData dumpData, String amount) {
        int i = productlist.indexOf(dumpData);

        if (i == -1) {
            throw new IndexOutOfBoundsException();
        }

        DumpData dumpData_update = new DumpData();
        dumpData_update.id = dumpData.id;
        dumpData_update.price = amount;
        dumpData_update.first_time = dumpData.first_time;
        dumpData_update.subattribts_array=dumpData.subattribts_array;
        productlist.remove(dumpData);
        productlist.add(i, dumpData_update);

    }

    public void subupdatedbyOne(ArrayList<DumpData> productlist) {

        DumpData dumpData_update = new DumpData();
        dumpData_update.sa_subattribte ="";
        dumpData_update.sa_val = "";
        dumpData_update.sa_firsttime= "No";
        dumpData_update.sa_attribte = "";
        productlist.add(productlist.size(), dumpData_update);

    }
    public void subupdatedremove(ArrayList<DumpData> productlist,int pos,LinearLayout layout) {

        productlist.remove(pos);
        layout.removeAllViews();
        for (int i =0; i <productlist.size(); i++) {
            addsublayout(layout,productlist,i);
        }

    }
    public void Subdatalistupdated(ArrayList<DumpData> productlist, DumpData dumpData, String value,String type) {
        int i = productlist.indexOf(dumpData);

        if (i == -1) {
            throw new IndexOutOfBoundsException();
        }

        DumpData dumpData_update = new DumpData();
        if(type.equalsIgnoreCase("sub")){
            dumpData_update.sa_subattribte =value;
        }else{
            dumpData_update.sa_subattribte =dumpData.sa_subattribte;
        }
        if(type.equalsIgnoreCase("main")){
            dumpData_update.sa_attribte =value;
        }else{
            dumpData_update.sa_attribte =dumpData.sa_attribte;
        }
        if(type.equalsIgnoreCase("name")){
            dumpData_update.sa_val =value;
        }else{
            dumpData_update.sa_val = dumpData.sa_val;
        }

        dumpData_update.sa_firsttime= dumpData.sa_firsttime;
        productlist.remove(dumpData);
        productlist.add(i, dumpData_update);



    }

    public void addimageslayout(LinearLayout layout,Bitmap image){
        View v	=LayoutInflater.from(getActivity()).inflate(R.layout.addimages_item, null);
        ImageView addpicked_img=(ImageView) v.findViewById(R.id.addpicked_img);

        addpicked_img.setImageBitmap(image);
       // components.CustomizeImageview(addpicked_img, new int[]{100,100}, R.drawable.restarunt_sammpleimg, new int[]{0,0,0,0});
      /*  Glide.with(getActivity())
                .load(Uri.parse(StoredUrls.MainUrl+image)) // add your image url
                //.transform(new StoredObjects.CircleTransform(getActivity())) // applying the image transformer
                .placeholder(R.drawable.menu_profile)
                .override(100,100)
                .fitCenter()
                .into(addpicked_img);*/
        layout.addView(v);
    }
    private void servicecalling() {
       parsing_methods(0, StoredUrls.GetAttributes_url,"",Constants.POSTMETHOD);



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
                        //{"status":200,"message":"success","results_count":3,"results":[{"attr_id":1,"attr_name":"Weight"},{"attr_id":3,"attr_name":"length"},{"attr_id":4,"attr_name":"dfghdfghdfg"}]}
                           try {
                               attributeslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                               StoredObjects.Services_list.get(val).countDown.cancel();
                               parsing_methods(1, StoredUrls.GetProductCategories_url,"vendor_id="+StoredObjects.UserId,Constants.POSTMETHOD);

                           } catch (JSONException e) {
                               StoredObjects.LogMethod("Service_called","Execption"+e);

                               e.printStackTrace();
                            }


                    }else if(val==2){
                        //{"status":200,"message":"success","results_count":2,"results":[{"attr_id":2,"attr_name":"Grams"},{"attr_id":5,"attr_name":"kghkhj"}]}
                          try {
                               subattributeslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);


                            } catch (JSONException e) {
                              StoredObjects.LogMethod("Service_called","Execption"+e);
                                e.printStackTrace();
                            }
                        StoredObjects.Services_list.get(val).countDown.cancel();
                    }else if(val==1){
                        // {"status":200,"message":"success","results_count":2,"results":[{"category_id":1,"category_name":"IT"},{"category_id":2,"category_name":"another"}]}
                            try {
                                categorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        StoredObjects.Services_list.get(val).countDown.cancel();
                    }else if(val==3){
                        // {"status":200,"message":"success","results_count":1,"results":[{"category_id":3,"category_name":"IT"}]}
                           try {
                               subcategorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        StoredObjects.Services_list.get(val).countDown.cancel();
                    }else if(val==4){
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){
                                filename = jsonObject.getString("file_name");
                                proimgs_lay.setVisibility(View.VISIBLE);
                                imagesarray.add(filename+",");

                                if(addpikedimgs_lay.getChildCount()==0){
                                    addpikedimgs_lay.removeAllViews();
                                }
                                addimageslayout(addpikedimgs_lay,myImg);
                            }else{
                                String error = jsonObject.getString("error");

                                StoredObjects.ToastMethod(error,getActivity());
                            }
                            StoredObjects.Services_list.get(val).countDown.cancel();
                        }catch (JSONException e){

                        }
                    }else if(val==5){
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){

                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.productadded),getActivity());
                                fragmentcalling(new ManageProductsMain());
                            }else{
                                String error = jsonObject.getString("error");

                                StoredObjects.ToastMethod(error,getActivity());
                            }
                            StoredObjects.Services_list.get(val).countDown.cancel();
                        }catch (JSONException e){

                        }
                    }



                }

            }

            public void onFinish() {
                StoredObjects.LogMethod("Finished ","Finished");
            }
        };

        StoredObjects.Services_list.get(val).countDown.start();


    }
    public  void fragmentcalling(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();

    }
    private void CategoryFilterPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        category_popup=new PopupWindow(mView, width-50, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        category_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< categorieslist.size(); i++){
            addcategoryfilter(additems_lay,categorieslist,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        category_popup.setBackgroundDrawable(new BitmapDrawable());
        category_popup.showAsDropDown(adpro_slctctgry_lay, 0, 0, Gravity.CENTER);


    }


    public void addcategoryfilter(LinearLayout layout, final ArrayList<HashMap<String, String>> filter_array, final int position,final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView  filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("category_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_id=filter_array.get(position).get("category_id");
                name_txt.setText( filter_array.get(position).get("category_name"));
                parsing_methods(3, StoredUrls.GetProductSubCategories_url,"vendor_id="+StoredObjects.UserId+"&parent_category_id="+category_id,Constants.POSTMETHOD);

                category_popup.dismiss();
            }
        });

        layout.addView(v);

    }
    private void FilterPopup(EditText name_txt, ArrayList<DumpData> datalist, DumpData dumpData){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.attributespopup, null);
        attributes_popup=new PopupWindow(mView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        attributes_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< attributeslist.size(); i++){
            addlayoutfilter(additems_lay,attributeslist,i,name_txt,datalist,dumpData);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        attributes_popup.setBackgroundDrawable(new BitmapDrawable());
        attributes_popup.showAsDropDown(name_txt, 0, 0, Gravity.CENTER);


    }


    public void addlayoutfilter(LinearLayout layout, final ArrayList<HashMap<String, String>> filter_array, final int position, final EditText name_txt, final ArrayList<DumpData> datalist, final DumpData dumpData){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView  filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("attr_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 parent_id=filter_array.get(position).get("attr_id");
                name_txt.setText( filter_array.get(position).get("attr_name"));
                Subdatalistupdated(datalist,dumpData,parent_id,"main");
                parsing_methods(2, StoredUrls.GetSubAttributes_url,"parent_attr_id="+parent_id,Constants.POSTMETHOD);

                attributes_popup.dismiss();
                     }
        });

        layout.addView(v);

    }
    private void SubFilterPopup(EditText name_txt, ArrayList<DumpData> datalist, DumpData dumpData){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.attributespopup, null);
        subatrbts_popup=new PopupWindow(mView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        subatrbts_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< subattributeslist.size(); i++){
            addlayoutsubfilter(additems_lay,subattributeslist,i,name_txt,datalist,dumpData);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        subatrbts_popup.setBackgroundDrawable(new BitmapDrawable());
        subatrbts_popup.showAsDropDown(name_txt, 0, 0, Gravity.CENTER);


    }

    public void addlayoutsubfilter(LinearLayout layout, final ArrayList<HashMap<String, String>> filter_array, final int position, final EditText name_txt,final ArrayList<DumpData> datalist,final DumpData dumpData){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout  filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView   filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("attr_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parent_id=filter_array.get(position).get("attr_id");
                name_txt.setText( filter_array.get(position).get("attr_name"));
                Subdatalistupdated(datalist,dumpData,parent_id,"sub");
                subatrbts_popup.dismiss();
            }
        });

        layout.addView(v);

    }

    private void SubCategoryFilterPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        subatrbts_popup=new PopupWindow(mView, width-50, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        subatrbts_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< subcategorieslist.size(); i++){
            addsubcategoryfilter(additems_lay,subcategorieslist,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        subatrbts_popup.setBackgroundDrawable(new BitmapDrawable());
        subatrbts_popup.showAsDropDown(adpro_slctctgry_lay, 0, 0, Gravity.CENTER);


    }


    public void addsubcategoryfilter(LinearLayout layout, final ArrayList<HashMap<String, String>> filter_array, final int position,final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView  filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("category_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subcategory_id=filter_array.get(position).get("category_id");
                name_txt.setText( filter_array.get(position).get("category_name"));

                subatrbts_popup.dismiss();
            }
        });

        layout.addView(v);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            //user is returning from capturing an image using the camera
            if (requestCode == CAPTURE_CAMERA) {

                String deviceMan = Build.MANUFACTURER;
                if (Build.VERSION.SDK_INT >Build.VERSION_CODES.N||deviceMan.contains("HUAWEI")||deviceMan.contains("motorola")|| deviceMan.equalsIgnoreCase("motorola")||deviceMan.contains("Meizu")||deviceMan.contains("meizu")||deviceMan.equalsIgnoreCase("mi") || deviceMan.equalsIgnoreCase("Mi") || deviceMan.equalsIgnoreCase("MI")||deviceMan.equalsIgnoreCase("LG nexus 5x")||deviceMan.equalsIgnoreCase("ZTE")||deviceMan.contains("LG")||deviceMan.contains("nexus")||deviceMan.equalsIgnoreCase("LG nexus 5")||deviceMan.equalsIgnoreCase("LG Nexus 5X")|| deviceMan.equalsIgnoreCase("VIVO")||deviceMan.equalsIgnoreCase("LG Nexus 5")||deviceMan.equalsIgnoreCase("oppo")||deviceMan.equalsIgnoreCase("OPPO")||deviceMan.equalsIgnoreCase("Oppo")||deviceMan.equalsIgnoreCase("ZTE Blade V6")||deviceMan.equalsIgnoreCase("zte blade v6")||deviceMan.equalsIgnoreCase("Zte blade v6")||deviceMan.equalsIgnoreCase("ZTE V6")||deviceMan.equalsIgnoreCase("zte v6")||deviceMan.equalsIgnoreCase("Zte v6")) {//||deviceMan.equalsIgnoreCase("YU")

                    try {
                        final String docFilePath =  ImageUpload.getFileNameByUri(getActivity(), picUri);
                        Bitmap myBitmap = BitmapFactory.decodeFile(docFilePath);
                        f_new = ImageUpload.createNewFile("CROP_");
                        try {
                            f_new.createNewFile();
                        } catch (IOException ex) {
                            Log.e("io", ex.getMessage());
                        }
                        Photo_SHowDialog(getActivity(),f_new,docFilePath,myBitmap);

                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                } else {
                    Uri uri = picUri;
                    //carry out the crop operation
                    try {
                        performCrop(picUri);
                        Log.d("picUri", uri.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            } else if (requestCode == SELECT_FILE) {
                String deviceMan = Build.MANUFACTURER;
                picUri = data.getData();
                String url = data.getData().toString();
                if (url.startsWith("content://com.google.android.apps.photos.content")){
                    String tempPath = ImageUpload.getPath(picUri, getActivity());
                    try {
                        InputStream is = getActivity().getContentResolver().openInputStream(picUri);
                        if (is != null) {
                            Bitmap pictureBitmap = BitmapFactory.decodeStream(is);
                            f_new = ImageUpload.createNewFile("CROP_");
                            try {
                                f_new.createNewFile();
                            } catch (IOException ex) {
                                Log.e("io", ex.getMessage());
                            }
                            Photo_SHowDialog(getActivity(),f_new,tempPath,pictureBitmap);
                        }
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{
                    if (Build.VERSION.SDK_INT >Build.VERSION_CODES.N||deviceMan.contains("HUAWEI")||deviceMan.contains("motorola")|| deviceMan.equalsIgnoreCase("motorola")||deviceMan.contains("Meizu")|| deviceMan.equalsIgnoreCase("VIVO")||deviceMan.contains("meizu")||deviceMan.equalsIgnoreCase("mi") || deviceMan.equalsIgnoreCase("Mi") || deviceMan.equalsIgnoreCase("MI")||deviceMan.equalsIgnoreCase("LG nexus 5x")||deviceMan.equalsIgnoreCase("ZTE")||deviceMan.contains("LG")||deviceMan.contains("nexus")||deviceMan.equalsIgnoreCase("LG nexus 5")||deviceMan.equalsIgnoreCase("LG Nexus 5X")||deviceMan.equalsIgnoreCase("LG Nexus 5")||deviceMan.equalsIgnoreCase("oppo")||deviceMan.equalsIgnoreCase("OPPO")||deviceMan.equalsIgnoreCase("Oppo")||deviceMan.equalsIgnoreCase("ZTE Blade V6")||deviceMan.equalsIgnoreCase("zte blade v6")||deviceMan.equalsIgnoreCase("Zte blade v6")||deviceMan.equalsIgnoreCase("ZTE V6")||deviceMan.equalsIgnoreCase("zte v6")||deviceMan.equalsIgnoreCase("Zte v6")) {//||deviceMan.equalsIgnoreCase("YU")
                        try {
                            Bitmap myBitmap=null;
                            picUri = data.getData();
                            final String docFilePath = ImageUpload.getFileNameByUri(getActivity(), picUri);
                            myBitmap = BitmapFactory.decodeFile(docFilePath);
                            try {
                                f_new = ImageUpload.createNewFile("CROP_");
                                try {
                                    f_new.createNewFile();
                                } catch (IOException ex) {
                                    Log.e("io", ex.getMessage());
                                }
                                Photo_SHowDialog(getActivity(),f_new,docFilePath,myBitmap);
                            } catch (Exception e1) {
                                e1.printStackTrace();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        picUri = data.getData();
                        try {
                            performCrop(picUri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            //user is returning from cropping the image
            else if (requestCode == PIC_CROP) {
                //get the returned data
                try {
                    Bitmap myBitmap = BitmapFactory.decodeFile(mCropImagedUri.getPath());
                    Photo_SHowDialog(getActivity(),f_new,mCropImagedUri.getPath(),myBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private void performCrop(Uri picUri) {
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("scale", true);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 500);
            cropIntent.putExtra("outputY", 500);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            // startActivityForResult(cropIntent, PIC_CROP);

            f_new = ImageUpload.createNewFile("CROP_");
            try {
                f_new.createNewFile();
            } catch (IOException ex) {
                Log.e("io", ex.getMessage());
            }

            mCropImagedUri = Uri.fromFile(f_new);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImagedUri);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);

        }

        //respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

    }



    private Uri mCropImagedUri;
    File f_new;

    /**
     * Crop the image
     *
     * @return returns <tt>true</tt> if crop supports by the device,otherwise false
     */



    Dialog dialog_show;
    public void Photo_SHowDialog(final Context context, final File captured_file, final String path, final Bitmap bitmap){
        dialog_show = new Dialog(context);
        dialog_show.getWindow();

        dialog_show.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_show.setContentView(R.layout.photoshow_popup);
        dialog_show.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog_show.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView captured_image = (ImageView) dialog_show.findViewById(R.id.captured_image);
        Button cancel_btn = (Button) dialog_show.findViewById(R.id.cancel_btn);
        Button saveandcontinue__btn = (Button) dialog_show.findViewById(R.id.saveandcontinue__btn);
        String fileNameSegments[] = path.split("/");
        fileName = fileNameSegments[fileNameSegments.length - 1];
        myImg = Bitmap.createBitmap(ImageUpload.getResizedBitmap(ImageUpload.getUnRotatedImage(path, BitmapFactory.decodeFile(path)), 300));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        myImg.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        captured_image.setImageBitmap(myImg);
        saveandcontinue__btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InterNetChecker.isNetworkAvailable(getActivity())) {
                    bitmapToUriConverter(myImg);
                    dialog_show.dismiss();
                }else{
                    Toast toast = Toast.makeText(context, "Please Check Internet Connection.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_show.dismiss();

            }
        });
        dialog_show.show();
    }

    public void bitmapToUriConverter(Bitmap mBitmap) {
        try {
            File file = new File(getActivity().getFilesDir(), "ProfileImages"
                    + new Random().nextInt() + ".png");

            FileOutputStream out;
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion > Build.VERSION_CODES.M) {
                out = getActivity().openFileOutput(file.getName(),
                        Context.MODE_PRIVATE);
            }else{
                out = getActivity().openFileOutput(file.getName(),
                        Context.MODE_WORLD_READABLE);
            }

            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            //get absolute path
            String realPath = file.getAbsolutePath();
            parsing_methods(4, StoredUrls.Uploadimage_url,realPath,Constants.IMAGEUPLOAD);


        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
    }
}

