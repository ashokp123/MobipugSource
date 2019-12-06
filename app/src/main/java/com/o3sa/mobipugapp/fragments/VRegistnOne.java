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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.o3sa.mobipugapp.R;
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
 * Created by android_2 on 11/13/2018.
 */

public class VRegistnOne extends Fragment {

    EditText vreg_idprf_edtxt,vreg_fulnme_edtxt,vreg_acntnmbr_edtxt,vreg_ifsc_edtxt,vreg_pakgetype_edtxt;
    ImageView vreg_pakgetype_imgg;
    TextView vreg_bnkdetls_txtt;
    Button vreg_cntnue_butnn;
    LinearLayout vreg_idprf_lay,vreg_pakgetype_lay;

    BasicComponents components;

    private PopupWindow package_popup;

    public static ArrayList<HashMap<String, String>> vndr_profilelist1;

    ListPopupWindow pckg_type_popup;
    ArrayAdapter<String> pckg_type_adapter;
    public ArrayList<HashMap<String, String>> packages_array;
    public ArrayList<String> pckg_type_list;

    //
    String realPath;
    String filename="";
    public static String fileName="";

    String package_id="";

    //image upload
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;
    protected static final int SELECT_FILE = 1;
    private static final int CAPTURE_CAMERA = 2;
    private static final int PIC_CROP = 3;
    private Bitmap myImg;
    private Uri picUri;

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

    String image_type="";
    String idproof_image="",pancard_image="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.v_registn_one,container,false);

        intialization(v);
        assigndata();
        servicecalling();
        return v;
    }

    public void intialization(View v) {
        components = new BasicComponents(getActivity());
        StoredObjects.pagetype="completeprofile";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,"Complete Profile",Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        vreg_idprf_edtxt =(EditText)v.findViewById(R.id.vreg_idprf_edtxt);
        vreg_fulnme_edtxt =(EditText)v.findViewById(R.id.vreg_fulnme_edtxt);
        vreg_acntnmbr_edtxt =(EditText)v.findViewById(R.id.vreg_acntnmbr_edtxt);
        vreg_ifsc_edtxt =(EditText)v.findViewById(R.id.vreg_ifsc_edtxt);
        vreg_pakgetype_edtxt =(EditText)v.findViewById(R.id.vreg_pakgetype_edtxt);

        vreg_pakgetype_imgg = (ImageView)v.findViewById(R.id.vreg_pakgetype_imgg);

        vreg_bnkdetls_txtt = (TextView)v.findViewById(R.id.vreg_bnkdetls_txtt);

        vreg_cntnue_butnn=(Button) v.findViewById(R.id.vreg_cntnue_butnn);

        vreg_idprf_lay = (LinearLayout)v.findViewById(R.id.vreg_idprf_lay);
        vreg_pakgetype_lay = (LinearLayout)v.findViewById(R.id.vreg_pakgetype_lay);

        vreg_idprf_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_type="idproof_img";
                uploadimage();
            }
        });


        vreg_pakgetype_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackagePopup(vreg_pakgetype_edtxt,vreg_pakgetype_lay);
            }
        });

    }

    private void servicecalling() {
        StoredObjects.UserId="1";
        parsing_methods(1, StoredUrls.GetPackages_url,"",Constants.POSTMETHOD);

        vreg_cntnue_butnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editservicecalling("No");
            }
        });

    }

    private void editservicecalling(String imageupload) {

        StoredObjects.hide_keyboard_activity(getActivity());

        StoredObjects.vndr_idproof = vreg_idprf_edtxt.getText().toString().trim();
        StoredObjects.vndr_fullname = vreg_fulnme_edtxt.getText().toString().trim();
        StoredObjects.vndr_ac = vreg_acntnmbr_edtxt.getText().toString().trim();
        StoredObjects.vndr_ifsc = vreg_ifsc_edtxt.getText().toString().trim();
        StoredObjects.vndr_pckg_type = vreg_pakgetype_edtxt.getText().toString().trim();

        if (StoredObjects.inputValidation(vreg_idprf_edtxt, getActivity().getResources().getString(R.string.pls_ntr_adharno), getActivity())) {
            if (StoredObjects.inputValidation(vreg_fulnme_edtxt, getActivity().getResources().getString(R.string.pls_ntr_fullname), getActivity())) {
                if (StoredObjects.inputValidation(vreg_acntnmbr_edtxt, getActivity().getResources().getString(R.string.pls_ntr_ac), getActivity())) {
                    if (StoredObjects.inputValidation(vreg_ifsc_edtxt, getActivity().getResources().getString(R.string.pls_ntr_ifsc_code), getActivity())) {
                        if (StoredObjects.inputValidation(vreg_pakgetype_edtxt, getActivity().getResources().getString(R.string.pls_ntr_pckg_type), getActivity())) {

/*                            if (StoredObjects.vndr_idproof.length() > 0 && (!StoredObjects.vndr_idproof.equalsIgnoreCase(null)) && StoredObjects.vndr_fullname.length() > 0 && (!StoredObjects.vndr_fullname.equalsIgnoreCase(null)) && StoredObjects.vndr_ac.length() > 0 && (!StoredObjects.vndr_ac.equalsIgnoreCase(null))
                                    && StoredObjects.vndr_ifsc.length() > 0 && (!StoredObjects.vndr_ifsc.equalsIgnoreCase(null)) && StoredObjects.vndr_pckg_type.length() > 0 && (!StoredObjects.vndr_pckg_type.equalsIgnoreCase(null))) {

                                fragmentcalling(new VRegistnTwo());

                            } else {
                                Toast.makeText(getActivity(), "Please select all fields", Toast.LENGTH_SHORT).show();
                            }*/

                            fragmentcalling(new VRegistnTwo());

                        } else {
                            StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.invalidata), getActivity());

                        }

                    }

                }

            }
        }
    }

    private void uploadimage() {

        try {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},1);
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
    Dialog dialog_show;
    public void Photo_SHowDialog(final Context context, final File captured_file, final String path, final Bitmap bitmap){
        dialog_show = new Dialog(context);
        dialog_show.getWindow();

        dialog_show.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_show.setContentView(R.layout.photoshow_popup);
        dialog_show.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog_show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
            realPath = file.getAbsolutePath();
            parsing_methods(0, StoredUrls.Uploadimage_url,realPath,Constants.IMAGEUPLOAD);

        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
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
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){

                                if(image_type.equalsIgnoreCase("idproof_img")){
                                    idproof_image = jsonObject.getString("file_name");
                                    vreg_idprf_edtxt.setText(realPath);
                                    StoredObjects.v_reg_idproof_img1 = idproof_image;
                                    //StoredObjects.LogMethod("image_path","image_path--->"+realPath);

                                }
                            }else{
                                String error = jsonObject.getString("error");
                                StoredObjects.ToastMethod(error,getActivity());
                            }
                        }catch (JSONException e){

                        }

                    }else if(val==1){

                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){
                                packages_array = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
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

    private void PackagePopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        package_popup=new PopupWindow(mView, width-60, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        package_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);

        additems_lay.removeAllViews();
        for (int i = 0; i< packages_array.size(); i++){
            addPackageLay(additems_lay,packages_array,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        package_popup.setBackgroundDrawable(new BitmapDrawable());
        package_popup.showAsDropDown(adpro_slctctgry_lay, 0, 0, Gravity.CENTER);

    }

    public void addPackageLay(LinearLayout layout, final ArrayList<HashMap<String, String>> filter_array, final int position,final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("type_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                package_id=filter_array.get(position).get("type_id");
                name_txt.setText( filter_array.get(position).get("type_name"));

                package_popup.dismiss();
            }
        });

        layout.addView(v);

    }


    private void assignvndrprofiledata() {

        vreg_fulnme_edtxt.setText(vndr_profilelist1.get(0).get("full_name"));
        vreg_acntnmbr_edtxt.setText(vndr_profilelist1.get(0).get("account_number"));
        vreg_ifsc_edtxt.setText(vndr_profilelist1.get(0).get("ifsc_code"));
        vreg_pakgetype_edtxt.setText(vndr_profilelist1.get(0).get("package_type"));

        vreg_acntnmbr_edtxt.setInputType(InputType.TYPE_CLASS_NUMBER);

    }

    public void assigndata() {

        components.CustomizeEditview(vreg_idprf_edtxt, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.aadhar_mndtry),0,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(vreg_fulnme_edtxt, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.fullname_mndtry),0,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(vreg_acntnmbr_edtxt, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.accntNum_mndtry),0,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{10,0,0,0});
        vreg_acntnmbr_edtxt.setInputType(InputType.TYPE_CLASS_NUMBER);
        components.CustomizeEditview(vreg_ifsc_edtxt, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.ifcs_code_mndtry),0,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(vreg_pakgetype_edtxt, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.packagetype),0,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{10,0,0,0});


        components.CustomizeTextview(vreg_bnkdetls_txtt, Constants.XNormal,R.color.black,getActivity().getApplicationContext().getResources().getString(R.string.bnkdetails),Constants.WrapLeftNormal+Constants.Gibson, new int[]{0,0,0,10});

        components.CustomizeButton(vreg_cntnue_butnn, Constants.Normal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.cntinue),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,45}, new int[]{0,0,0,10});

    }

    public void fragmentcalling(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }

}
