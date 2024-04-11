package com.example.cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Recept.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table Recipes(foodname TEXT primary key, ingredients TEXT, instructions TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop table if exists Recipes");
    }

    public Boolean insertuserdata(String foodname, String ingredients, String instructions) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("foodname", foodname);
        contentValues.put("ingredients", ingredients);
        contentValues.put("instructions", instructions);
        long result = DB.insert("Recipes", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateuserdata(String foodname, String ingredients, String instructions) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ingredients", ingredients);
        contentValues.put("instructions", instructions);
        Cursor cursor = DB.rawQuery("Select * from Recipes where name = ?", new String[]{foodname});
        if (cursor.getCount() > 0) {

            long result = DB.update("Recipes", contentValues, "foodname=?", new String[]{foodname});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Boolean deletedata(String foodname) {
        SQLiteDatabase DB = this.getWritableDatabase();


        Cursor cursor = DB.rawQuery("Select * from Recipes where name = ?", new String[]{foodname});
        if (cursor.getCount() > 0) {

            long result = DB.delete("Recipes", "foodname=?", new String[]{foodname});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();


        Cursor cursor = DB.rawQuery("Select * from Recipes", null);
        return cursor;
    }
}