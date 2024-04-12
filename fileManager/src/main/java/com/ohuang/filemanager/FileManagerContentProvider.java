package com.ohuang.filemanager;

import android.content.ContentProvider;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

public class FileManagerContentProvider extends FileProvider {

    public static Uri getUriForFile(Context context, String path) {
        File file = new File(path);
        String packageName = context.getPackageName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //这里要注意FileProvider一定要在AndroidManifest中去声明哦
            return FileProvider.getUriForFile(context, packageName + ".FileManagerContentProvider", file);
        } else {
            return Uri.fromFile(file);
        }
    }
}
