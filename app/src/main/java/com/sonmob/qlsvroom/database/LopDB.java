package com.sonmob.qlsvroom.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sonmob.qlsvroom.model.Lop;

@Database(entities = {Lop.class}, version = 1)
public abstract class LopDB extends RoomDatabase {
    private static final String DATABASE_NAME = "lop.db";
    private static LopDB instance;

    public static synchronized LopDB getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), LopDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract LopDAO lopDAO();

}
