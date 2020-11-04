package com.swufe.thirdapp.avatar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Avatar {
    private  final String url = Environment.getExternalStorageDirectory()+"/avatars/";
    private String name = null;
    ImageView imageView = null;

    public Avatar(){
        this.name = "";
    }

    public Avatar(String name){
        this.name = name + ".png";
    }

    public Avatar(ImageView imageView){
        this.imageView = imageView;
    }

    public void setName(String name){
        this.name = name + ".png";
    }

    public String getName(){
        return this.name;
    }

    public String getUrl(){
        return this.url;
    }

    public void saveImage(Bitmap bmp){
        File Dir = new File(url);
        if (!Dir.exists()) {
            Dir.mkdir();
        }
        File file = new File(Dir, this.name);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(){
        if (name != null) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(url + name);
                return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } finally {
                if(fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fis = null;
                }
            }
        } else {
            return null;
        }
    }

    public BitmapDrawable getDrawable(){
        if (name != null) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(url + name);
                return new BitmapDrawable(BitmapFactory.decodeStream(fis));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } finally {
                if(fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fis = null;
                }
            }
        } else {
            return null;
        }
    }
}
