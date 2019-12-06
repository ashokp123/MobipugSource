package com.o3sa.mobipugapp.fragments;

import android.Manifest;
import android.app.Activity;
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
import android.support.v4.app.FragmentManager;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.servicesparsing.InterNetChecker;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.storedobjects.DevicePermissions;
import com.o3sa.mobipugapp.storedobjects.ImageUpload;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.CircularImageView;
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

import static android.view.Gravity.TOP;

/**
 * Created by android_2 on 11/5/2018.
 */

public class EditVendorRegistnTwo extends Fragment {

    TextView edt_vreg_busnme_tx,edt_vreg_bus_imgupld_tx,edt_vreg_bus_bg_imgupld_tx,edt_vreg_slct_catgry_tx,edt_vreg_slct_subcatgry_tx,edt_vreg_abutus_tx;

    EditText edt_vreg_busnme_edtx,edt_vreg_bus_imgupld_edtx,edt_vreg_bus_bg_imgupld_edtx,edt_vreg_slct_catgry_edtx,edt_vreg_slct_subcatgry_edtx,edt_vreg_abutus_edtx;

    Button edt_vreg_cntnue_buttn;
    ImageView business_img,business_bg_img;

    LinearLayout vndr_ctgry_lay,vndr_sub_ctgry_lay;
    private PopupWindow category_popup,sub_category_popup;
    public static String category_id = "",sub_category_id = "";
    public ArrayList<HashMap<String, String>> catgry_array,sub_ctgry_array;
    LinearLayout businss_img_lay,businss_bg_img_lay;
    String image_type = "";

    public static String vndr_business_img = "",vndr_business_bg_img = "",vndr_business_name = "",vndr_ctgry = "",vndr_sub_ctgry = "",vndr_abt_us = "";

    //image upload
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;
    protected static final int SELECT_FILE = 1;
    private static final int CAPTURE_CAMERA = 2;
    private static final int PIC_CROP = 3;
    private Bitmap myImg;
    private Uri picUri;

    String filename="";
    public static String fileName="";
    String business_type_image = "",business_bg_type_image = "";

    BasicComponents components;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_vendor_registn_two,container,false);

        intialization(v);

        if(StoredObjects.vendorprofilearray.size()>=10){
            //setImages(StoredObjects.vendorprofilearray);
            business_img.setVisibility(View.VISIBLE);
            business_bg_img.setVisibility(View.VISIBLE);
        }else{
            assigndata();
            business_img.setVisibility(View.VISIBLE);
            business_bg_img.setVisibility(View.VISIBLE);
        }
        servicecalling();
        return v;
    }

    public void intialization(View v) {

        components = new BasicComponents(getActivity());

        edt_vreg_busnme_edtx = (EditText) v.findViewById(R.id.edt_vreg_busnme_edtx);
        edt_vreg_bus_imgupld_edtx = (EditText) v.findViewById(R.id.edt_vreg_bus_imgupld_edtx);
        edt_vreg_bus_bg_imgupld_edtx = (EditText) v.findViewById(R.id.edt_vreg_bus_bg_imgupld_edtx);
        edt_vreg_slct_catgry_edtx = (EditText) v.findViewById(R.id.edt_vreg_slct_catgry_edtx);
        edt_vreg_slct_subcatgry_edtx = (EditText) v.findViewById(R.id.edt_vreg_slct_subcatgry_edtx);
        edt_vreg_abutus_edtx = (EditText) v.findViewById(R.id.edt_vreg_abutus_edtx);

        edt_vreg_busnme_tx = (TextView)v.findViewById(R.id.edt_vreg_busnme_tx);
        edt_vreg_bus_imgupld_tx = (TextView)v.findViewById(R.id.edt_vreg_bus_imgupld_tx);
        edt_vreg_bus_bg_imgupld_tx = (TextView)v.findViewById(R.id.edt_vreg_bus_bg_imgupld_tx);
        edt_vreg_slct_catgry_tx = (TextView)v.findViewById(R.id.edt_vreg_slct_catgry_tx);
        edt_vreg_slct_subcatgry_tx = (TextView)v.findViewById(R.id.edt_vreg_slct_subcatgry_tx);
        edt_vreg_abutus_tx = (TextView)v.findViewById(R.id.edt_vreg_abutus_tx);

        edt_vreg_cntnue_buttn = (Button) v.findViewById(R.id.edt_vreg_cntnue_buttn);

        business_img = (ImageView)v.findViewById(R.id.business_img);
        business_bg_img = (ImageView)v.findViewById(R.id.business_bg_img);

        businss_img_lay = (LinearLayout)v.findViewById(R.id.businss_img_lay);
        businss_bg_img_lay = (LinearLayout)v.findViewById(R.id.businss_bg_img_lay);

        vndr_ctgry_lay = (LinearLayout)v.findViewById(R.id.vndr_ctgry_lay);
        vndr_sub_ctgry_lay = (LinearLayout)v.findViewById(R.id.vndr_sub_ctgry_lay);


        businss_img_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_type="business_img_type";
                uploadimage();
            }
        });

        businss_bg_img_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_type="business_bg_img_type";
                uploadimage();
            }
        });


        vndr_ctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryPopup(edt_vreg_slct_catgry_edtx,vndr_ctgry_lay);
            }
        });
        vndr_sub_ctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubCategoryPopup(edt_vreg_slct_subcatgry_edtx,vndr_sub_ctgry_lay);
            }
        });


        edt_vreg_cntnue_buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StoredObjects.hide_keyboard_activity(getActivity());

                vndr_business_name = edt_vreg_busnme_edtx.getText().toString().trim();
                vndr_business_img = edt_vreg_bus_imgupld_edtx.getText().toString().trim();
                vndr_business_bg_img = edt_vreg_bus_bg_imgupld_edtx.getText().toString().trim();
                vndr_ctgry = edt_vreg_slct_catgry_edtx.getText().toString().trim();
                vndr_sub_ctgry = edt_vreg_slct_subcatgry_edtx.getText().toString().trim();
                vndr_abt_us = edt_vreg_abutus_edtx.getText().toString().trim();

                if (StoredObjects.inputValidation(edt_vreg_busnme_edtx, getActivity().getResources().getString(R.string.pls_ntr_bsnesname), getActivity())) {
                    if (StoredObjects.inputValidation(edt_vreg_bus_imgupld_edtx, getActivity().getResources().getString(R.string.pls_slct_business_img), getActivity())) {
                        if (StoredObjects.inputValidation(edt_vreg_bus_bg_imgupld_edtx, getActivity().getResources().getString(R.string.pls_slct_business_bg_img), getActivity())) {
                            if (StoredObjects.inputValidation(edt_vreg_slct_catgry_edtx, getActivity().getResources().getString(R.string.pls_ntr_ctgry), getActivity())) {
                                if (StoredObjects.inputValidation(edt_vreg_slct_subcatgry_edtx, getActivity().getResources().getString(R.string.pls_ntr_sub_ctgry), getActivity())) {
                                    if (StoredObjects.inputValidation(edt_vreg_abutus_edtx, getActivity().getResources().getString(R.string.pls_ntr_abt_us), getActivity())) {

                                        if (vndr_business_name.length() > 0 && (!vndr_business_name.equalsIgnoreCase(null)) && vndr_business_img.length() > 0 && (!vndr_business_img.equalsIgnoreCase(null))
                                                && vndr_business_bg_img.length() > 0 && (!vndr_business_bg_img.equalsIgnoreCase(null)) && vndr_ctgry.length() > 0 && (!vndr_ctgry.equalsIgnoreCase(null))
                                                && vndr_sub_ctgry.length() > 0 && (!vndr_sub_ctgry.equalsIgnoreCase(null)) && vndr_abt_us.length() > 0 && (!vndr_abt_us.equalsIgnoreCase(null))) {

                                            StoredObjects.vendorprofilearray.add(vndr_business_name);//9
                                            StoredObjects.vendorprofilearray.add(vndr_business_img);//10
                                            StoredObjects.vendorprofilearray.add(vndr_business_bg_img);//11
                                            StoredObjects.vendorprofilearray.add(vndr_ctgry);//12
                                            StoredObjects.vendorprofilearray.add(vndr_sub_ctgry);//13
                                            StoredObjects.vendorprofilearray.add(vndr_abt_us);//14

                                            StoredObjects.vendorprofilearray.add(business_type_image);//15
                                            StoredObjects.vendorprofilearray.add(business_bg_type_image);//16

                                            fragmentcalling(new EditVendorRegistnThree());

                                        } else {
                                            Toast.makeText(getActivity(), "Please select all fields", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.invalidata), getActivity());

                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void servicecalling() {
        StoredObjects.UserId="1";

        parsing_methods(0, StoredUrls.GetVendorCategories_url,"",Constants.POSTMETHOD);

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

                    if (val==0){
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){
                                catgry_array = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                            }else{
                                String error = jsonObject.getString("error");
                                StoredObjects.ToastMethod(error,getActivity());
                            }
                        }catch (JSONException e){
                        }
                    }else if(val==1) {
                        try {
                            JSONObject jsonObject = new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if (status.equalsIgnoreCase("200")) {
                                sub_ctgry_array = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                            } else {
                                String error = jsonObject.getString("error");
                                StoredObjects.ToastMethod(error, getActivity());
                            }
                        } catch (JSONException e) {
                        }
                    }else{
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){

                                if(image_type.equalsIgnoreCase("business_img_type")){
                                    business_type_image = jsonObject.getString("file_name");
                                    setUplodedImage(business_type_image);
                                }else{
                                    business_bg_type_image = jsonObject.getString("file_name");
                                    setUplodedImage(business_bg_type_image);
                                }

                            }else{
                                String error = jsonObject.getString("error");
                                StoredObjects.ToastMethod(error,getActivity());
                            }

                        }catch (JSONException e){

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

    private void uploadimage(){

        try {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);

            } else {

                Imagepickingpopup(getActivity());
            }
        } catch (Exception e) {
        }

    }

    private void Imagepickingpopup(final Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pictureselectionpopup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout takepic_lay = (LinearLayout)dialog.findViewById(R.id.takepic_lay);
        LinearLayout pickglry_lay = (LinearLayout)dialog.findViewById(R.id.pickglry_lay);
        LinearLayout cancel_lay = (LinearLayout)dialog.findViewById(R.id.cancel_lay);

        ImageView dismiss_btn = (ImageView)dialog.findViewById(R.id.dismiss_btn);

        dismiss_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        cancel_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        takepic_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = DevicePermissions.checkPermission(activity);
                boolean result1 = DevicePermissions.checkPermission_storage(activity);
                boolean result2 = DevicePermissions.checkPermission_write(activity);

                if (result&&result1&&result2) {
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
                    dialog.dismiss();
                }
            }
        });

        pickglry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");
                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");
                Intent chooserIntent = Intent.createChooser(getIntent, "Pick Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
                startActivityForResult(chooserIntent, SELECT_FILE);

                dialog.dismiss();
            }
        });

        dialog.show();

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

                    if (image_type.equalsIgnoreCase("business_img_type")){
                        //business_img.setImageBitmap(myImg);
                        business_img.setVisibility(View.VISIBLE);
                        bitmapToUriConverter(myImg);
                        edt_vreg_bus_imgupld_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("id_proof"));
                        dialog_show.dismiss();
                    }else{
                        //business_bg_img.setImageBitmap(myImg);
                        business_bg_img.setVisibility(View.VISIBLE);
                        bitmapToUriConverter(myImg);
                        edt_vreg_bus_bg_imgupld_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("pan_card"));
                        dialog_show.dismiss();
                    }

                    //bitmapToUriConverter(myImg);
                    //dialog_show.dismiss();
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
            File file = new File(getActivity().getFilesDir(), "ProfileImages" + new Random().nextInt() + ".png");

            FileOutputStream out;
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion > Build.VERSION_CODES.M) {
                out = getActivity().openFileOutput(file.getName(), Context.MODE_PRIVATE);
            }else{
                out = getActivity().openFileOutput(file.getName(),
                        Context.MODE_WORLD_READABLE);
            }

            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            //get absolute path
            String realPath = file.getAbsolutePath();

            StoredObjects.LogMethod("Image_Path","Image_Path:-----"+realPath);
            parsing_methods(2, StoredUrls.Uploadimage_url, realPath, Constants.IMAGEUPLOAD);

        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
    }




    public void assigndata() {

        components.CustomizeTextview(edt_vreg_busnme_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.businessname), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_bus_imgupld_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.businesspic), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_bus_bg_imgupld_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.businessbgpic), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_slct_catgry_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.categorytype), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_slct_subcatgry_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.secltsub_catgry), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_abutus_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.aboutus), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});

        components.CustomizeEditview(edt_vreg_busnme_edtx, Constants.Medium,  VendorProfile.vndr_profilelist.get(0).get("business_name"),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edt_vreg_bus_imgupld_edtx, Constants.Medium,  getActivity().getApplicationContext().getResources().getString(R.string.businesspic),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edt_vreg_bus_bg_imgupld_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.businessbgpic),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edt_vreg_slct_catgry_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.categorytype),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edt_vreg_slct_subcatgry_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.secltsub_catgry),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});

        components.CustomizeMultilineEditview(edt_vreg_abutus_edtx,Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.aboutus),R.drawable.shadowwithoutradius,true,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,10},3);
        edt_vreg_abutus_edtx.setGravity(Gravity.LEFT|TOP);
        edt_vreg_abutus_edtx.setPadding(20,5,20,5);

        components.CustomizeButton(edt_vreg_cntnue_buttn, Constants.Normal, R.color.white, getActivity().getApplicationContext().getResources().getString(R.string.cntinue), R.drawable.cntinue_btn_bg, Constants.MatchCenterNormal + Constants.Gibson, new int[]{0, 45}, new int[]{0,10,0,30});

        edt_vreg_busnme_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("business_name"));
        edt_vreg_bus_imgupld_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("business_images"));
        edt_vreg_bus_bg_imgupld_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("business_background_images"));
        edt_vreg_slct_catgry_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("category_id"));
        edt_vreg_slct_subcatgry_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("sub_categories"));
        edt_vreg_abutus_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("about_us"));

        edt_vreg_slct_catgry_edtx.setText(category_type);
        edt_vreg_slct_subcatgry_edtx.setText(sub_category_type);

        Glide.with(getActivity())
                .load(Uri.parse(StoredUrls.MainUrl+"/admin/uploads/"+VendorProfile.vndr_profilelist.get(0).get("business_images")))
                .transform(new StoredObjects.CircleTransform(getActivity()))
                .placeholder(R.drawable.noresultsfound)
                .into(business_img);

        Glide.with(getActivity())
                .load(Uri.parse(StoredUrls.MainUrl+"/admin/uploads/"+VendorProfile.vndr_profilelist.get(0).get("business_background_images")))
                .transform(new StoredObjects.CircleTransform(getActivity()))
                .placeholder(R.drawable.noresultsfound)
                .into(business_bg_img);

    }

    private void CategoryPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        category_popup=new PopupWindow(mView, width-60, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        category_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< catgry_array.size(); i++){
            addCategoryLay(additems_lay,catgry_array,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        category_popup.setBackgroundDrawable(new BitmapDrawable());
        category_popup.showAsDropDown(adpro_slctctgry_lay, 0, 0, Gravity.CENTER);

    }

    public static String category_type = "";
    public static String sub_category_type = "";

    public void addCategoryLay(LinearLayout layout, final ArrayList<HashMap<String, String>> catgry_array, final int position, final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText(catgry_array.get(position).get("category_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_id=catgry_array.get(position).get("category_id");
                name_txt.setText(catgry_array.get(position).get("category_name"));

                category_type = name_txt.getText().toString().trim();

                parsing_methods(1, StoredUrls.GetVendorSubCategories_url,"parent_category_id="+category_id,Constants.POSTMETHOD);

                category_popup.dismiss();
            }
        });

        layout.addView(v);

    }

    private void SubCategoryPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        sub_category_popup=new PopupWindow(mView, width-60, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        sub_category_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< sub_ctgry_array.size(); i++){
            addSubCategoryLay(additems_lay,sub_ctgry_array,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        sub_category_popup.setBackgroundDrawable(new BitmapDrawable());
        sub_category_popup.showAsDropDown(adpro_slctctgry_lay, 0, 0, Gravity.CENTER);

    }

    public void addSubCategoryLay(LinearLayout layout, final ArrayList<HashMap<String, String>> sub_ctgry_array, final int position,final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView  filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( sub_ctgry_array.get(position).get("category_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sub_category_id = sub_ctgry_array.get(position).get("category_id");
                name_txt.setText( sub_ctgry_array.get(position).get("category_name"));

                sub_category_type = name_txt.getText().toString().trim();

                sub_category_popup.dismiss();
            }
        });

        layout.addView(v);

    }

    public void setUplodedImage(String type_image){

        if(type_image.equalsIgnoreCase("business_img_type")){
            Glide.with(getActivity())
                    .load(Uri.parse(StoredUrls.MainUrl+"/admin/uploads/"+business_type_image))//+VendorProfile.vndr_profilelist.get(0).get("image")
                    .transform(new StoredObjects.CircleTransform(getActivity()))
                    .placeholder(R.drawable.noresultsfound)
                    .into(business_img);
        }else {
            Glide.with(getActivity())
                    .load(Uri.parse(StoredUrls.MainUrl+"/admin/uploads/"+business_bg_type_image))//+VendorProfile.vndr_profilelist.get(0).get("image")
                    .transform(new StoredObjects.CircleTransform(getActivity()))
                    .placeholder(R.drawable.noresultsfound)
                    .into(business_bg_img);
        }

    }


    public void fragmentcalling(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }



}
