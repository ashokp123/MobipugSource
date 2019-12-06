package com.o3sa.mobipugapp.customerfragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
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
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.InputType;
import android.util.Log;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.o3sa.mobipugapp.uicomponents.CircularImageView;
import com.o3sa.mobipugapp.uicomponents.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by android_2 on 10/24/2018.
 */

public class EditProfile extends Fragment {

    EditText edtprf_nme_edtx,edtprf_mble_edtx,edtprf_emal_edtx;
    Button edtprf_save_btn;
    public ArrayList<HashMap<String, String>> profilelist;
    BasicComponents components;
    CircularImageView profile_img;
    String filename="";
    public static String fileName="";

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
    ImageView camerapic_img;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_profile,container,false);

        intialization(v);
        assigndata();
        servicecalling();
        return v;
    }

    public void intialization(View v) {
        components=new BasicComponents(getActivity());
        StoredObjects.pagetype="editprofile";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.editprofile),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        edtprf_nme_edtx = (EditText) v.findViewById(R.id.edtprf_nme_edtx);
        edtprf_mble_edtx = (EditText) v.findViewById(R.id.edtprf_mble_edtx);
        edtprf_emal_edtx = (EditText) v.findViewById(R.id.edtprf_emal_edtx);
        edtprf_save_btn = (Button) v.findViewById(R.id.edtprf_save_btn);
        profile_img=(CircularImageView) v.findViewById(R.id.profile_img);
        camerapic_img=(ImageView) v.findViewById(R.id.camerapic_img);

        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });

    }

    public void assigndata() {

        components.CustomizeButton(edtprf_save_btn, Constants.XXNormal, R.color.white, getActivity().getApplicationContext().getResources().getString(R.string.save), R.drawable.cntinue_btn_bg, Constants.MatchCenterNormal + Constants.SFUIText, new int[]{0, 45}, new int[]{0, 20, 0, 0});
        edtprf_mble_edtx.setInputType(InputType.TYPE_CLASS_NUMBER);
        components.CustomizeEditview(edtprf_nme_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.name),0,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edtprf_mble_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.mobile),0,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edtprf_emal_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.email),0,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{10,0,0,0});

    }
    private void servicecalling() {

        parsing_methods(0, StoredUrls.CustomerGetprofile_url,"customer_id="+StoredObjects.UserId,Constants.POSTMETHOD);

        edtprf_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               editservicecalling("No");
            }

        });

    }

    private void editservicecalling(String imageupload) {

        StoredObjects.hide_keyboard_activity(getActivity());

        String name = edtprf_nme_edtx.getText().toString().trim();
        String mobile = edtprf_mble_edtx.getText().toString().trim();
        String email = edtprf_emal_edtx.getText().toString().trim();

        if (StoredObjects.inputValidation(edtprf_nme_edtx, getActivity().getResources().getString(R.string.pleaseentername), getActivity())) {
            if (StoredObjects.inputValidation(edtprf_mble_edtx, getActivity().getResources().getString(R.string.pleaseentermobile), getActivity())) {
                if (StoredObjects.inputValidation(edtprf_emal_edtx, getActivity().getResources().getString(R.string.pleaseenteremail), getActivity())) {
                    if (StoredObjects.isValidEmail(email)) {
                        if(imageupload.equalsIgnoreCase("No")){
                            parsing_methods(1, StoredUrls.CustomerEditprofile_url,"customer_id="+StoredObjects.UserId+"&name="+name+"&mobile="+mobile+"&email="+email,
                                            Constants.POSTMETHOD);
                        }else{
                            parsing_methods(1, StoredUrls.CustomerEditprofile_url,"customer_id="+StoredObjects.UserId+"&name="+name+"&mobile="+mobile+"&email="+email+
                                            "&image="+filename,Constants.POSTMETHOD);
                        }

                    } else {
                        StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.invalidemail), getActivity());
                    }
                }
            }
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
                        if(val==0) {
                            try {
                                profilelist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                                assignprofiledata();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            StoredObjects.Services_list.get(val).countDown.cancel();
                        }else if(val==1){
                            try {
                                JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                                String status = jsonObject.getString("status");
                                if(status.equalsIgnoreCase("200")){
                                    StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.profileupdate_msg),getActivity());
                                }else{
                                    String error = jsonObject.getString("error");
                                    StoredObjects.ToastMethod(error,getActivity());
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            StoredObjects.Services_list.get(val).countDown.cancel();

                        }else {
                            try {
                                JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                                String status = jsonObject.getString("status");
                                if(status.equalsIgnoreCase("200")){
                                    filename = jsonObject.getString("file_name");
                                    editservicecalling("Yes");
                                }else{
                                    String error = jsonObject.getString("error");

                                    StoredObjects.ToastMethod(error,getActivity());
                                }
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

    private void assignprofiledata() {
        //{"status":200,"message":"success","results":[{"name":"test","":"test12@gmail.com","":"20020200202","image":"admin\/uploads\/1540617131_gridviewicon.png"}]}
        edtprf_nme_edtx.setText(profilelist.get(0).get("name"));
        edtprf_mble_edtx.setText(profilelist.get(0).get("phone"));
        edtprf_emal_edtx.setText(profilelist.get(0).get("email"));

        Glide.with(getActivity())
                .load(Uri.parse(StoredUrls.MainUrl+profilelist.get(0).get("image"))) // add your image url
                .transform(new StoredObjects.CircleTransform(getActivity())) // applying the image transformer
                //.placeholder(R.drawable.menu_profile)
                .into(profile_img);
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
            parsing_methods(2, StoredUrls.Uploadimage_url,realPath,Constants.IMAGEUPLOAD);


        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
    }

}
