package com.ohuang.filemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;

public class FileManager {
    
    public static void jump(Activity activity,String path){
        Intent intent = new Intent(activity, FileListActivity.class);
        intent.putExtra("path",path);
        activity.startActivity(intent);
    }

    public static void jump(Activity activity){
        Intent intent = new Intent(activity, FileListActivity.class);
        intent.putExtra("path", activity.getFilesDir().getParent());
        activity.startActivity(intent);
    }
}
