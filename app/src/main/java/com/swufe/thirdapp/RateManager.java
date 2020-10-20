package com.swufe.thirdapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RateManager {
    private DBhelper dBhelper;
    private String TB_NAME;

    public  RateManager(Context context){
        dBhelper = new DBhelper(context);
        TB_NAME = DBhelper.TB_NAME;
    }

    public void add(RateItem rateItem){
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", rateItem.getName());
        values.put("rate", rateItem.getRate());

        db.insert(TB_NAME, null, values);
        db.close();
    }

    public void addAll(List<RateItem>list_item){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        for(RateItem item : list_item){
            ContentValues values = new ContentValues();
            values.put("name", item.getName());
            values.put("rate", item.getRate());
            db.insert(TB_NAME, null, values);
        }
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        db.delete(TB_NAME, "id=", new String[]{""+id});
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        db.delete(TB_NAME, null, null);
        db.close();
    }

    public RateItem findById(int id){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        Cursor cursor = db.query(TB_NAME, null,"ID=?", new String[]{String.valueOf(id)},null,null, null);

        RateItem item = new RateItem();
        if(cursor!=null && cursor.moveToFirst()){
            item = new RateItem();
            item.setID(cursor.getInt(cursor.getColumnIndex("id")));
            item.setName(cursor.getString(cursor.getColumnIndex("name")));
            item.setRate(cursor.getString(cursor.getColumnIndex("rate")));
            cursor.close();
        }
        db.close();
        return item;
    }

    public RateItem findByName(String name){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        Cursor cursor = db.query(TB_NAME, null,"name=?", new String[]{String.valueOf(name)},null,null, null);

        RateItem item = new RateItem();
        if(cursor!=null && cursor.moveToFirst()){
            item = new RateItem();
            item.setID(cursor.getInt(cursor.getColumnIndex("id")));
            item.setName(cursor.getString(cursor.getColumnIndex("name")));
            item.setRate(cursor.getString(cursor.getColumnIndex("rate")));
            cursor.close();
        }
        db.close();
        return item;
    }

    public List<RateItem> listAll(){
        List<RateItem> list = new ArrayList<>();
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        Cursor cursor = db.query(TB_NAME, null,null, null,null,null, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                RateItem item = new RateItem();
                item.setID(cursor.getInt(cursor.getColumnIndex("id")));
                item.setName(cursor.getString(cursor.getColumnIndex("name")));
                item.setRate(cursor.getString(cursor.getColumnIndex("rate")));
                list.add(item);
            }
            cursor.close();
        }
        db.close();
        return list;
    }

    public void update(RateItem item){
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("rate", item.getRate());
        db.update(TB_NAME, values, "name=?", new String[]{item.getName()});
        db.close();
    }
}
