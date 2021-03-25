package EPSI.mspr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btScan;
    private Button btCode;
    private static int PERMISSION_REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btScan = findViewById(R.id.btScan);
        btCode = findViewById(R.id.btCode);

        btScan.setOnClickListener(this);
        btCode.setOnClickListener(this);

        requestCamera();
    }



    private void requestCamera(){
        System.out.println("TESTE" + ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA));
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            System.out.println("test");
            startCamera();
        } else {
            System.out.println("TEST" + Manifest.permission.CAMERA);
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                System.out.println("TEST" + PERMISSION_REQUEST_CAMERA);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        System.out.println(grantResults.length + " ------- " + grantResults[0]);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                System.out.println("test");
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera(){

    }

    @Override
    public void onClick(View v) {
        if (v == btScan){
            newScan();
        }else if (v == btCode){
            newCode();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 25, 0, "Scanner");
        menu.add(0, 26, 0, "Codes");
        return super.onCreateOptionsMenu(menu);
    }

    /* Handles item selections */
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

    private void newCode() {
        Intent intent = new Intent(this, CodeActivity.class);
        startActivity(intent);
    }

    private void newScan() {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
        //finish();
    }
}