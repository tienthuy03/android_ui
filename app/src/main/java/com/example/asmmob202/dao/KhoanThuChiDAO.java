package com.example.asmmob202.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmmob202.database.KhoanThuChiDB;
import com.example.asmmob202.model.KhoanThuChi;
import com.example.asmmob202.model.Loai;

import java.util.ArrayList;

public class KhoanThuChiDAO{

    KhoanThuChiDB khoanThuChiDB;
    //KhoanThuChi khoanThuChi;
    public KhoanThuChiDAO(Context context){
        khoanThuChiDB = new KhoanThuChiDB(context);
    }
    // 1.LẤY DANH SÁCH LOAI THU/CHI
    public ArrayList<Loai> getdsLoai(String loai){
        ArrayList<Loai> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAI",null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                String trangthai = cursor.getString(2);
                if(trangthai.equals(loai)){
                    list.add(new Loai(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
                }
            }while (cursor.moveToNext());
        }
        return list;
    }
    // 2.THÊM MỚI LOẠI THU/CHI
    public boolean ThemLoai(Loai loai){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai",loai.getTenloai());
        contentValues.put("trangthai",loai.getTrangthai());
        long check = sqLiteDatabase.insert("LOAI", null, contentValues);
        if(check == -1){
            return false;
        }
        return true;
    }
    //3.CẬP NHẬT LOẠI THU/CHI
    public boolean capnhatLoai(Loai loai){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maloai", loai.getMaloai());
        contentValues.put("tenloai",loai.getTenloai());
        contentValues.put("trangthai",loai.getTrangthai());
        long check = sqLiteDatabase.update("LOAI",contentValues,"maloai = ?", new String[]{String.valueOf(loai.getMaloai())});
        if(check == -1){
            return false;
        }
        return true;
    }

    // 4. XOÁ LOẠI THU/CHI
    public boolean xoaLoai(int maloai){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        long check = sqLiteDatabase.delete("LOAI","maloai = ?",new String[]{String.valueOf(maloai)});
        if(check == -1){
            return  false;
        }
        return true;
    }
    /*----------Các hàm thực hiện của KHOẢN THU/ CHI---------*/
    /*
     * Câu truy vấn để lấy tên loại trong bảng loại
     * LOAI (maloai, tenloai, trangthai)
     * KHOANTHUCHI (makhoan, tien, maloai)
     * ==> makhoan, tien, maloai, tenloai
     *
     * select k.makhoan, k.tien, k.maloai, l,tenloai
     * from LOAI l, KHOANTHUCHI k
     * where l.maloai = k.maloai and l.trangthai ="thu/chi"
     *
     * */
    //1.LẤY DANH SÁCH
    public ArrayList<KhoanThuChi> getdsKhoanThuChi(String loai) {
        ArrayList<KhoanThuChi> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select k.makhoan,k.tien, k.ngay, k.ghichu, k.maloai, l.tenloai from LOAI l, KHOANTHUCHI k where l.maloai = k.maloai and l.trangthai = ? ", new String[]{loai});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new KhoanThuChi(cursor.getInt(0), cursor.getInt(1),cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5)));

            }while (cursor.moveToNext());
        }
        return list;
    }

    //2.THÊM MỚI KHOẢN THU/CHI
    public boolean themMoiKhoan(KhoanThuChi khoanThuChi){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tien",khoanThuChi.getTien());
        contentValues.put("ngay", khoanThuChi.getNgay());
        contentValues.put("ghichu", khoanThuChi.getGhichu());
        contentValues.put("maloai", khoanThuChi.getMaloai());
        long check = sqLiteDatabase.insert("KHOANTHUCHI",null,contentValues);
        if(check == -1){
            return false;
        }else {
            return true;
        }
    }
    //3.CẬP NHẬT KHOẢN THU/CHI
    public boolean capNhatKhoan(KhoanThuChi khoanThuChi){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("makhoan", khoanThuChi.getMakhoan());
        contentValues.put("tien", khoanThuChi.getTien());
        contentValues.put("ngay", khoanThuChi.getNgay());
        contentValues.put("ghichu", khoanThuChi.getGhichu());
        contentValues.put("maloai",khoanThuChi.getMaloai());
        long check = sqLiteDatabase.update("KHOANTHUCHI",contentValues,"makhoan = ?", new String[]{String.valueOf(khoanThuChi.getMakhoan())});
        if(check ==-1){
            return  false;
        }else {
            return true;
        }

    }
    //4.XOÁ KHOẢN THU/CHI
    public boolean xoaKhoan(int makhoan){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        long check = sqLiteDatabase.delete("KHOANTHUCHI","makhoan = ?", new String[]{String.valueOf(makhoan)});
        if(check == -1){
            return  false;
        }else {
            return true;
        }
    }
    public float[] getThongTinThuChi() {
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getReadableDatabase();
        int thu = 0, chi = 0;
        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'thu')
        Cursor cursorThu = sqLiteDatabase.rawQuery("select sum(tien) from KHOANTHUCHI where maloai in (select maloai from loai where trangthai = 'thu') ", null);
        if (cursorThu.getCount() != 0) {
            cursorThu.moveToFirst();
            thu = cursorThu.getInt(0);
        }
        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'chi')
        Cursor cursorChi = sqLiteDatabase.rawQuery("select sum(tien) from KHOANTHUCHI where maloai in (select maloai from loai where trangthai = 'chi') ", null);
        if (cursorChi.getCount() != 0) {
            cursorChi.moveToFirst();
            chi = cursorChi.getInt(0);
        }
        float[] ketQua = new float[]{thu, chi};
        return ketQua;
    }

}


