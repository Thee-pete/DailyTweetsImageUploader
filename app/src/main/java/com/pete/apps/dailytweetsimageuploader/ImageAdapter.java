package com.pete.apps.dailytweetsimageuploader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.internal.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Upload> mUploads;

    public ImageAdapter(Context context, ArrayList<Upload> uploads) {
        this.mContext = context;
        this.mUploads = uploads;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView;
        }
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, final int position) {

        CardView cardView = holder.cardView;
        TextView textViewName = cardView.findViewById(R.id.text_view_name);
        final ImageView imageView = cardView.findViewById(R.id.image_view_upload);


        textViewName.setText(mUploads.get(position).getmName());
        Picasso.with(mContext).load(mUploads.get(position).getmImageUri()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imageView);

        final ImageView shareBtn = cardView.findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Picasso.with(mContext).load(mUploads.get(position).getmImageUri()).into(new Target() {
                    @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("image/*");
                        i.setPackage("com.twitter.android");
                        i.putExtra(Intent.EXTRA_STREAM,getLocalBitmapUri(bitmap, mContext));
                        mContext.startActivity(Intent.createChooser(i, "Share Image"));
                    }
                    @Override public void onBitmapFailed(Drawable errorDrawable) { }
                    @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
                });

            }
            public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
                Uri bmpUri = null;
                try {
                    File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
                    FileOutputStream out = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.close();
                    bmpUri = Uri.fromFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bmpUri;

            }
        });

    }


    @Override
    public int getItemCount () {
        return mUploads.size();
    }
}
