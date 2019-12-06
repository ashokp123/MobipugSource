package com.o3sa.mobipugapp.customerfragments;

import android.app.Activity;
import android.os.Bundle;
import com.google.zxing.Result;
import com.o3sa.mobipugapp.customerfragments.ScanBarcode;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends Activity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {

         if(StoredObjects.pagetype.equalsIgnoreCase("Scanbarcode")){
            ScanBarcode.scnned_image = rawResult.getText();

            StoredObjects.LogMethod("scnned_consgnmtNum","scnned_consgnmtNum:-----"+ScanBarcode.scnned_image);

        }

        onBackPressed();

        //If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);

    }

}