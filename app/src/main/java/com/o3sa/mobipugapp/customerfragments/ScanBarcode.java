package com.o3sa.mobipugapp.customerfragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.storedobjects.DevicePermissions;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kiran on 06-04-2018.
 */

public class ScanBarcode extends Fragment {

    ImageView consignment_barcode;
    Context context;
    public static String scnned_image = "";
    public static String scnned_amount = "";

    //image upload
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;
    protected static final int SELECT_FILE = 1;
    private static final int CAPTURE_CAMERA = 2;
    private static final int PIC_CROP = 3;
    private Uri picUri;

    LinearLayout barcodescan_lay;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.scanbarcode,container,false);
        context=this.getActivity();

        initialization(rootView);

        return rootView;

    }

    public void initialization(View rootView){

        consignment_barcode=(ImageView) rootView.findViewById(R.id.consignment_barcode);
        barcodescan_lay=(LinearLayout) rootView.findViewById(R.id.barcodescan_lay);

        callScanActivity();

        barcodescan_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callScanActivity();
            }
        });

    }

    public void callScanActivity(){

        boolean result = DevicePermissions.checkPermission(getActivity());
        boolean result1 = DevicePermissions.checkPermission_storage(getActivity());
        boolean result2 = DevicePermissions.checkPermission_write(getActivity());

        if (result&&result1&&result2) {

            StoredObjects.pagetype="Scanbarcode";
            Intent intent = new Intent(getActivity(), ScanActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void onResume() {
        try {
            if(scnned_image.length()>0&&(!scnned_image.equalsIgnoreCase(null))){

                StoredObjects.LogMethod("scnned_consgnmtNum","scnned_consgnmtNum--"+scnned_image);

                String[] strings = scnned_image.split(":");
                scnned_amount = strings[0];
                scnned_image = strings[1];

                fragmentcalling(getActivity(),new ProceedToPay());

            }
        } catch (Exception e) {
        }
        super.onResume();
    }

    public void fragmentcalling(Activity activity, Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack("").commit();

    }

}





