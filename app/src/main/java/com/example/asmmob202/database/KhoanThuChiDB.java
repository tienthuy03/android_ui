package com.example.asmmob202.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KhoanThuChiDB extends SQLiteOpenHelper {
    public KhoanThuChiDB(Context context){
        super(context,"KhoanThuChiDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlLoai = "CREATE TABLE LOAI (maloai integer primary key autoincrement, tenloai text, trangthai text)";
        sqLiteDatabase.execSQL(sqlLoai);

        String sqlKhoan = "CREATE TABLE KHOANTHUCHI(makhoan integer primary key autoincrement,tien integer, ngay text, ghichu text, maloai integer)";
        sqLiteDatabase.execSQL(sqlKhoan);

        //data mau
        String insLoai = "INSERT INTO LOAI VALUES(1,'tiền lương','thu'),(2,'tiền trúng số','thu'),(3, 'tiền xăng','chi')";
        sqLiteDatabase.execSQL(insLoai);

        String insKhoanThuChi = "INSERT INTO KHOANTHUCHI VALUES(1,1000,'2022-07-25','may mắn',2),(2,10000, '2022-07-25','luong tháng 8',1),(3,15000,' 2022-07-25','nhieu hon',3)";
        sqLiteDatabase.execSQL(insKhoanThuChi);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i != i1){
            String dLoai = "DROP TABLE IF EXISTS LOAI";
            sqLiteDatabase.execSQL(dLoai);
            String dKhoanThuChi = "DROP TABLE IF EXISTS KHOANTHUCHI";
            sqLiteDatabase.execSQL(dKhoanThuChi);
            onCreate(sqLiteDatabase);
        }
    }
}
