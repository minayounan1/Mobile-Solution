package com.example.mobilesolutions.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CartItemList.class}, version = 2, exportSchema = false)
public abstract class ItemListDB extends RoomDatabase {
    public abstract ItemListDAO itemListDAO();
}
