package com.example.mobilesolutions.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ItemListDAO {
    @Insert
    public void insertNewItem(CartItemList sli);
    @Query("SELECT * FROM CartItems")
    public LiveData<List<CartItemList>> getAllItems();
    @Query("DELETE FROM CartItems")
    public void clearDatabase();
}
