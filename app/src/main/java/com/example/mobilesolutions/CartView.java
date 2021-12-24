package com.example.mobilesolutions;



import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.room.Room;

import com.example.mobilesolutions.databinding.ActivityCartViewBinding;
import com.example.mobilesolutions.db.CartItemList;
import com.example.mobilesolutions.db.ItemListDB;




public class CartView extends AppCompatActivity {


    private ItemListDB itemListDB;
    private ActivityCartViewBinding binding;


    private SensorManager mSensorMgr;
    private float accelerationVal, accelerationLast, shake;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.cartRecycleView.setLayoutManager(new LinearLayoutManager(CartView.this));
        itemListDB = Room.databaseBuilder(
                this, ItemListDB.class, "CartList_DB").fallbackToDestructiveMigration().build();
        binding.addButton.setOnClickListener(view -> addItem());
        binding.logoutButton.setOnClickListener(view -> logout());
        mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorMgr.registerListener(sel, mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        itemListDB.itemListDAO().getAllItems().observe(this, cartItemListList -> binding.cartRecycleView.setAdapter(new ViewAdapter(cartItemListList)));
    }

    public void logout() {
        Toast.makeText(CartView.this, "Goodbye!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,MainActivity.class));
    }

    private final SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            accelerationLast = accelerationVal;
            accelerationVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = accelerationVal - accelerationLast;
            shake = shake * 0.9f + delta;
            if (shake > 8) {
                new Thread(() -> {
                    itemListDB.itemListDAO().clearDatabase();
                }).start();
                Toast.makeText(CartView.this, "Clear Cart.", Toast.LENGTH_LONG).show();
            }


        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    public void addItem() {
        new Thread(() -> {
            CartItemList sli = new CartItemList();
            sli.setItemName(binding.addItemName.getText().toString());
            sli.setStoreName(binding.addStoreName.getText().toString());
            itemListDB.itemListDAO().insertNewItem(sli);
        }).start();

    }


}