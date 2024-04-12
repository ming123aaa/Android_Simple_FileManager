package com.example.easytutofilemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.ohuang.filemanager.FileListActivity;
import com.ohuang.filemanager.FileManager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton storageBtn = findViewById(R.id.storage_btn);
        MaterialButton local_btn = findViewById(R.id.local_btn);
        try {
            FileUtils.writeText(getFilesDir()+"/time.txt",""+System.currentTimeMillis());
        } catch (IOException e) {

        }

        storageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    //permission allowed

                    String path = Environment.getExternalStorageDirectory().getPath();
                    FileManager.jump(MainActivity.this,path);
                }else{
                    //permission not allowed
                    requestPermission();

                }
            }
        });
        local_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManager.jump(MainActivity.this);

            }
        });

    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else
            return false;
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"Storage permission is requires,please allow from settings",Toast.LENGTH_SHORT).show();
        }else
        ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
    }
}