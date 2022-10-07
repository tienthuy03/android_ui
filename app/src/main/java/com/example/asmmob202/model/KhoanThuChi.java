package com.example.asmmob202.model;


public class KhoanThuChi {
    private  int makhoan;
    private int tien;
    private String ngay;
    private String ghichu;
    private int maloai;
    private String tenloai;


    public KhoanThuChi(int makhoan, int tien,String ngay, String ghichu, int maloai, String tenloai) {
        this.makhoan = makhoan;
        this.tien = tien;
        this.ngay = ngay;
        this.ghichu = ghichu;
        this.maloai = maloai;
        this.tenloai = tenloai;
    }

    public KhoanThuChi(int tien, String ngay, String ghichu, int maloai) {
        this.tien = tien;
        this.ngay = ngay;
        this.ghichu = ghichu;
        this.maloai = maloai;
    }

    public int getMakhoan() {
        return makhoan;
    }

    public void setMakhoan(int makhoan) {
        this.makhoan = makhoan;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
