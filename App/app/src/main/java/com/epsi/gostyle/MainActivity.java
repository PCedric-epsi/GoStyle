package com.epsi.gostyle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.epsi.gostyle.rest.CodeUtils;

public class MainActivity extends AppCompatActivity {

    private Button btScan;
    private Button btCode;
    private static final int PERMISSION_REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Items de l'Activity
        btScan = findViewById(R.id.btScan);
        btCode = findViewById(R.id.btCode);

        requestCamera();
    }

    /**
     * request camera access
     */
    private void requestCamera(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            startCamera();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     * get user permission response results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (!(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickScan(View view){
        newScan();
    }

    public void onClickCode(View view){
        newCode();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 25, 0, "Scanner");
        menu.add(0, 26, 0, "Codes");
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @param item
     * @return
     * handles item selection
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 25:
                newScan();
                break;
            case 26:
                newCode();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * launch new activity
     */
    private void newCode() {
        Intent intent = new Intent(this, CodeActivity.class);
        startActivity(intent);
    }

    /**
     * launch new activity
     */
    private void newScan() {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }
}