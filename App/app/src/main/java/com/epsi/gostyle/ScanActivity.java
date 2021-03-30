package com.epsi.gostyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.epsi.gostyle.bean.CodeBean;
import com.epsi.gostyle.rest.CodeUtils;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private Button btnAction;
    private String intentData = "";
    private String dataReq = "";
    private Toast currentToast;
    private String data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        initViews();
    }

    /**
     * @param view -> unused (due to not having multiple buttons)
     * Process actions on click
     */
    public void onClickScanning(View view){

        if (intentData.length() > 0) {
            checkCode(data);
        }
    }

    /**
     * Init Activity items
     */
    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);
    }

    /**
     * Use Camera to detect QR CODES
     */
    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        /**
         * Instantiate a barcode detector, with its parameters
         */
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        /**
         *  Instantiate the camera, with its parameters
         */
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        /**
         * Receive information about changes that occur in the surface
         */
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            /**
             * @param holder
             * called when surface is created
             */
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    // check permissions
                    if (ActivityCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /**
             * @param holder -> current surface in use
             * @param format -> surface format
             * @param width -> surface width
             * @param height -> surface height
             *
             * called when surface is changed
             */
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            /**
             * @param holder -> current surface in use (right before destruction)
             * Turn of camera when preview stops (to save resources)
             */
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) { cameraSource.stop(); }
        });

        /**
         * Starts barcode detector
         */
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            /**
             * @param detections -> list of all detected QR CODES
             * get all detected QR CODES
             */
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    txtBarcodeValue.post(new Runnable() {

                        /**
                         * Executes when a QR CODE is found
                         */
                        @Override
                        public void run() {

                            /**
                             * get the first QR CODE of the list, (@index: 0) being the last one detected
                             */
                            try{
                                System.out.println(barcodes.valueAt(10).displayValue);
                            }
                         catch (Exception e) {
                            e.printStackTrace();
                        }

                            intentData = barcodes.valueAt(0).displayValue;

                            // vérification du qrcode détecté
                            if(intentData.startsWith("GoStyle_")) {
                                btnAction.setClickable(true);
                                btnAction.setText("GET THE CODE");
                                data = intentData.split("GoStyle_")[1];
                            }

                            else {
                                dataReq = "";
                                btnAction.setClickable(false);
                                btnAction.setText("ANALYZING QR CODE");

                                showToast("This QR CODE isn't a GoStyle code");
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * When activity is paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("test");
        cameraSource.release();
    }

    /**
     * when activity is resumed
     */
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("test2");
        initialiseDetectorsAndSources();
    }


    /**
     * @param code -> code from last (approved) QR CODE detected
     */
    private void checkCode(String code){

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    CodeBean currentCode = CodeUtils.getCode(code);

                    if(!CodeActivity.isExist(data)){
                        CodeActivity.codeBeanList.add(new CodeBean(data, currentCode.getValue()));
                        showToast("QR CODE saved");
                    } else {
                        showToast("This QR CODE is already saved");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    /**
     * update current Toast to refresh it and not having to wait for each Toast to print
     * @param message -> message to print in the Toast
     */
    private void showToast(String message) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(currentToast == null) {
                    currentToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                }

                currentToast.setText(message);
                currentToast.setDuration(Toast.LENGTH_SHORT);
                currentToast.show();
            }
        });
    }
}