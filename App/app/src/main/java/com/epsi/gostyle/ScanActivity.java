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

    //Items de l'Activity
    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);
    }

    //Utilisation de la caméra pour detecter le QR code
    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScanActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    txtBarcodeValue.post(new Runnable() {

                        @Override
                        public void run() {

                            intentData = barcodes.valueAt(0).displayValue;
                            System.out.println("test2" + intentData);

                            if(intentData.startsWith("GoStyle_")) {

                                btnAction.setClickable(true);
                                btnAction.setText("GET THE CODE");
                                data = intentData.split("GoStyle_")[1];
                            }

                            else {
                                dataReq = "";
                                btnAction.setClickable(false);
                                btnAction.setText("ANALYZING QR CODE");

                                if(currentToast == null) {
                                    currentToast = Toast.makeText(getApplicationContext(), "This QR CODE isn't a GoStyle code", Toast.LENGTH_SHORT);
                                }

                                currentToast.setText("This QR CODE isn't a GoStyle code");
                                currentToast.setDuration(Toast.LENGTH_SHORT);
                                currentToast.show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    public void onClickScanning(View view){

        if (intentData.length() > 0) {

            checkCode(data);
        }
    }

    private void checkCode(String code){

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    CodeBean currentCode = CodeUtils.getCode(code);

                    if(!CodeActivity.isExist(data)){

                        CodeActivity.codeBeanList.add(new CodeBean(data, currentCode.getValue()));
                        showToast("Qr Code saved");
                    } else {

                        showToast("This Qr Code is already saved");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

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