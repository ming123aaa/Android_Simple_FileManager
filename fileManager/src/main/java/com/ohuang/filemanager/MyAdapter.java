package com.ohuang.filemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import androidx.recyclerview.widget.RecyclerView;

import com.ohuang.filemanager.share.Share2;
import com.ohuang.filemanager.share.ShareContentType;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Activity context;
    File[] filesAndFolders;

    public MyAdapter(Activity context, File[] filesAndFolders) {
        this.context = context;
        this.filesAndFolders = filesAndFolders;
    }

    public void setFilesAndFolders(File[] filesAndFolders) {
        this.filesAndFolders = filesAndFolders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        File selectedFile = filesAndFolders[position];
        holder.textView.setText(selectedFile.getName());

        if(selectedFile.exists()) {
            if (selectedFile.isDirectory()) {
                holder.imageView.setImageResource(R.drawable.ic_baseline_folder_24);
            } else {
                holder.imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
            }
        }else {
            holder.imageView.setImageResource(R.drawable.ic_baseline_delete_64px);
            holder.textView.setText(selectedFile.getName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedFile.exists()){
                    Toast.makeText(context.getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (selectedFile.isDirectory()) {
                    Intent intent = new Intent(context, FileListActivity.class);
                    String path = selectedFile.getAbsolutePath();
                    intent.putExtra("path", path);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    //open thte file
                    try {

                        new Share2.Builder(context).setContentType(ShareContentType.FILE)
                                .setShareFileUri(FileManagerContentProvider.getUriForFile(context, selectedFile.getAbsolutePath())).build().shareBySystem();
                    } catch (Exception e) {
                        Toast.makeText(context.getApplicationContext(), "share file fail", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenu().add("DELETE");


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("DELETE")) {
                            boolean deleted = FileUtils.delete(selectedFile);
                            if (deleted) {
                                Toast.makeText(context.getApplicationContext(), "已删除 ", Toast.LENGTH_SHORT).show();
//                                v.setVisibility(View.GONE);
                                notifyDataSetChanged();
                            }
                        }

                        return true;
                    }
                });

                popupMenu.show();
                return true;
            }
        });


    }

    public static String getMimeType(String filePath) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
    }

    @Override
    public int getItemCount() {
        return filesAndFolders.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.file_name_text_view);
            imageView = itemView.findViewById(R.id.icon_view);
        }
    }
}
