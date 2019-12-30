//package com.example.ebuy;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//@Database(entities = Product.class, version = 1)
//public abstract class ProductDataBase extends RoomDatabase {
//
//    private static ProductDataBase instance;
//
//    public abstract ProductDao productDao();
//
//    public static synchronized ProductDataBase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(), ProductDataBase.class, "product_database" )
//                    .fallbackToDestructiveMigration()
//                    .build();
//        }
//        return instance;
//    }
//}