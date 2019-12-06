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
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.customfonts.CustomEditTextGibson;
import com.o3sa.mobipugapp.servicesparsing.InterNetChecker;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.DevicePermissions;
import com.o3sa.mobipugapp.storedobjects.ImageUpload;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.RobotTextView;

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
 * Created by Kiran on 27-10-2018.
 */

public class VendorAddSubCategory extends Fragment {

    EditText categoryname_edtx,subcategoryname_edtx;

    Button submit_btn;
    String category_type="";
    LinearLayout adct_slctctgry_lay;
    CustomEditTextGibson addctctgry_edtx;

    private PopupWindow category_popup;

    BasicComponents components;
    public ArrayList<HashMap<String, String>> categorieslist;
    String category_id="";
    String c_filename="",sc_filename="";
    String cat_type="";
    public static String fileName="";
    //image upload
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;
    protected static final int SELECT_FILE = 1;
    private static final int CAPTURE_CAMERA = 2;
    private static final int PIC_CROP = 3;
    private Bitmap myImg;
    private Uri picUri;
    LinearLayout imageview_lay,s_gallery_lay,s_camera_lay,addcatimg_lay;
    TextView adprdctimage_tx,adprdctimgpick_tx,camera_tx,gallery_tx;
    LinearLayout gallery_lay,camera_lay;
    TextView subctimgpick_tx;
    ImageView subcatuploaded_img,catimguploaded_img;



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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.addsubcategory,container,false);

        components=new BasicComponents(getActivity());
        intialization(v);
        assigndata();
        servicecalling();

        return v;
    }

    private void servicecalling() {
        parsing_methods(0, StoredUrls.GetProductCategories_url,"",Constants.POSTMETHOD);

    }

    public void intialization(View v){
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.addsubctgry),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
        StoredObjects.pagetype="v_addsubcategory";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        submit_btn = (Button)v.findViewById(R.id.submit_btn);
        categoryname_edtx =(EditText)v.findViewById(R.id.categoryname_edtx);
        subcategoryname_edtx =(EditText)v.findViewById(R.id.subcategoryname_edtx);
        adct_slctctgry_lay=(LinearLayout) v.findViewById(R.id.adct_slctctgry_lay) ;
        addctctgry_edtx=(CustomEditTextGibson) v.findViewById(R.id.addctctgry_edtx);
        adprdctimage_tx= (TextView)v.findViewById(R.id.adprdctimage_tx);
        adprdctimgpick_tx =(TextView)v.findViewById(R.id.adprdctimgpick_tx);
        camera_lay=(LinearLayout) v.findViewById(R.id.camera_lay);
        gallery_lay=(LinearLayout) v.findViewById(R.id.gallery_lay);
        camera_tx =(TextView)v.findViewById(R.id.camera_tx);
        gallery_tx =(TextView)v.findViewById(R.id.gallery_tx);
        imageview_lay=(LinearLayout) v.findViewById(R.id.imageview_lay);
        s_camera_lay=(LinearLayout) v.findViewById(R.id.s_camera_lay);
        s_gallery_lay=(LinearLayout) v.findViewById(R.id.s_gallery_lay);

        addcatimg_lay=(LinearLayout) v.findViewById(R.id.addcatimg_lay);
        subctimgpick_tx=(TextView) v.findViewById(R.id.subctimgpick_tx);
        catimguploaded_img=(ImageView) v.findViewById(R.id.catimguploaded_img);
        subcatuploaded_img=(ImageView) v.findViewById(R.id.subcatuploaded_img);
        imageview_lay.setVisibility(View.GONE);
        catimguploaded_img.setVisibility(View.GONE);
        subcatuploaded_img.setVisibility(View.GONE);

        addctctgry_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFilterPopup(addctctgry_edtx,adct_slctctgry_lay);
            }
        });
        adct_slctctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFilterPopup(addctctgry_edtx,adct_slctctgry_lay);
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldcategoryname=addctctgry_edtx.getText().toString().trim();
                String categoryname=categoryname_edtx.getText().toString().trim();
                String subcategoryname=subcategoryname_edtx.getText().toString().trim();
                if(category_type.equalsIgnoreCase("old")&&categoryname.length()==0){
                    if(StoredObjects.inputValidation(addctctgry_edtx,getActivity().getResources().getString(R.string.sel_category),getActivity())){
                        if(StoredObjects.inputValidation(subcategoryname_edtx,getActivity().getResources().getString(R.string.plsentersubcategory),getActivity())){
                            if(sc_filename.equalsIgnoreCase("")){
                                StoredObjects.ToastMethod("Please select Sub Category Image",getActivity());
                            }else{
                                parsing_methods(1, StoredUrls.AddSubCategory_url,"old_category_id="+category_id+"&sub_category_name="+subcategoryname+"&sub_cat_image="+sc_filename,Constants.POSTMETHOD);

                            }
                        }
                    }
                }else{
                    if(StoredObjects.inputValidation(categoryname_edtx,getActivity().getResources().getString(R.string.plsentercategory),getActivity())){
                        if(StoredObjects.inputValidation(subcategoryname_edtx,getActivity().getResources().getString(R.string.plsentersubcategory),getActivity())){
                            if(c_filename.equalsIgnoreCase("")){
                                StoredObjects.ToastMethod("Please select Category Image",getActivity());
                            }else{
                                if(sc_filename.equalsIgnoreCase("")){
                                    StoredObjects.ToastMethod("Please select Sub Category Image",getActivity());
                                }else{
                                    parsing_methods(2, StoredUrls.AddSubCategory_url,"category_name="+categoryname+"&cat_image="+c_filename+"&sub_category_name="+subcategoryname+"&sub_cat_image="+sc_filename,Constants.POSTMETHOD);

                                }
                            }
                        }
                    }
                }


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

                        Imagepickingpopup(getActivity(),"0","subcat");
                    }
                } catch (Exception e) {
                }
            }
        });

        gallery_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imagepickingpopup(getActivity(),"1","subcat");
            }
        });
        s_camera_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);

                    } else {

                        Imagepickingpopup(getActivity(),"0","cat");
                    }
                } catch (Exception e) {
                }
            }
        });

        s_gallery_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imagepickingpopup(getActivity(),"1","cat");
            }
        });
    }

    public void assigndata(){
        components.CustomizeButton(submit_btn, Constants.XXNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.submit),R.drawable.list_bottom_bg,Constants.MatchCenterBold+Constants.Gibson, new int[]{0,42}, new int[]{0,20,0,30});
        components.CustomizeEditview(categoryname_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.categoryname),R.drawable.shadoweffect,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,0});
        components.CustomizeEditview(subcategoryname_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.subcategoryname),R.drawable.shadoweffect,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,10,0,0});
        components.CustomizeTextview(adprdctimage_tx,Constants.Normal,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.catsubcatimage),Constants.WrapLeftNormal+Constants.Gibson, new int[]{10,10,0,10});
        components.CustomizeTextview(adprdctimgpick_tx,Constants.Small,R.color.black,getActivity().getApplicationContext().getResources().getString(R.string.product_pick),Constants.WrapLeftNormal+Constants.Gibson, new int[]{15,0,0,0});

        components.CustomizeTextview(subctimgpick_tx,Constants.Small,R.color.black,getActivity().getApplicationContext().getResources().getString(R.string.product_pick),Constants.WrapLeftNormal+Constants.Gibson, new int[]{15,0,0,0});


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

                    if(val==0){
                        try {
                            categorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        StoredObjects.Services_list.get(val).countDown.cancel();

                    }else if(val==1) {
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){

                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.addsubcategorymsg),getActivity());
                            }else{
                                String error = jsonObject.getString("error");

                                StoredObjects.ToastMethod(error,getActivity());
                            }
                        }catch (JSONException e){

                        }
                        StoredObjects.Services_list.get(val).countDown.cancel();


                    }else  if(val==2) {
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){

                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.addsubcategorymsg),getActivity());
                            }else{
                                String error = jsonObject.getString("error");

                                StoredObjects.ToastMethod(error,getActivity());
                            }
                        }catch (JSONException e){

                        }
                        StoredObjects.Services_list.get(val).countDown.cancel();
                    }else if(val==3){
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){
                                imageview_lay.setVisibility(View.VISIBLE);
                                if(cat_type.equalsIgnoreCase("cat")){
                                    c_filename = jsonObject.getString("file_name");
                                    catimguploaded_img.setVisibility(View.VISIBLE);
                                    catimguploaded_img.setImageBitmap(myImg);
                                }else{
                                    sc_filename = jsonObject.getString("file_name");
                                    subcatuploaded_img.setVisibility(View.VISIBLE);
                                    subcatuploaded_img.setImageBitmap(myImg);
                                }

                            }else{
                                String error = jsonObject.getString("error");

                                StoredObjects.ToastMethod(error,getActivity());
                            }
                            StoredObjects.Services_list.get(val).countDown.cancel();
                        }catch (JSONException e){

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

    private void CategoryFilterPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        category_popup=new PopupWindow(mView, width-90, LinearLayout.LayoutParams.WRAP_CONTENT, true);
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
        RobotTextView filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("category_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_type="old";
                category_id=filter_array.get(position).get("category_id");
                name_txt.setText( filter_array.get(position).get("category_name"));
                parsing_methods(3, StoredUrls.GetProductSubCategories_url,"parent_category_id="+category_id,Constants.POSTMETHOD);
                addcatimg_lay.setVisibility(View.GONE);
                category_popup.dismiss();
            }
        });

        layout.addView(v);

    }


    private void Imagepickingpopup(FragmentActivity activity, String type,String cattype) {
        cat_type=cattype;
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
            parsing_methods(3, StoredUrls.Uploadimage_url,realPath,Constants.IMAGEUPLOAD);


        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
    }

}



