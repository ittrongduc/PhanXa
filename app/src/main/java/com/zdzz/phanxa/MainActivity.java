package com.zdzz.phanxa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zdzz.phanxa.service.time.TimeService;
import com.zdzz.phanxa.service.time.impl.TimeServiceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int STORAGE_PERMISSION_CODE = 1;

    private final String APPLICATION_PATH = MainActivity.this.getFilesDir().getAbsolutePath();
    //Context
    private TextView textView;
    private Button buttonGetPath;

    //Service
    private TimeService timeService;

    //Model

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initService();
        initView();

        getStoragePermission();

        doJob();
    }

    private void doJob() {
        textView.setOnClickListener(this);
        buttonGetPath.setOnClickListener(this);

    }

    private void initService() {
        timeService = new TimeServiceImpl();
    }

    private void initView() {
        textView = findViewById(R.id.textView);
        buttonGetPath = findViewById(R.id.buttonGetPath);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.buttonGetPath){

            File filePath = new File(APPLICATION_PATH, "myfile.txt");

            try {

                FileWriter fileWriter = new FileWriter(filePath);
                fileWriter.append("Duc Nguyen");
                fileWriter.append("\n");
                fileWriter.append("zDzz");
                fileWriter.flush();
                fileWriter.close();

                Toast.makeText(this, "Created File", Toast.LENGTH_SHORT).show();

                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
                String temp = bufferedReader.readLine();
                while (temp != null){
                    Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();
                    temp = bufferedReader.readLine();
                }

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Error When Create File", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void requestStoragePermission() {

        if (ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This Permission is needed")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();

        }else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void getStoragePermission(){
        if (ContextCompat
                .checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

        }else {
            requestStoragePermission();
        }
    }
}
