package com.example.asmmob202.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmmob202.R;
import com.example.asmmob202.adapter.KhoanThuAdapter;
import com.example.asmmob202.dao.KhoanThuChiDAO;
import com.example.asmmob202.model.KhoanThuChi;
import com.example.asmmob202.model.Loai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class KhoanThuFragment extends Fragment {
    RecyclerView rcvKhoanThu;
   FloatingActionButton floatAddLoai;
   ArrayList<KhoanThuChi> list;
   KhoanThuChiDAO khoanThuChiDAO;
   KhoanThuAdapter khoanThuAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.khoanthu_fragment, container, false);
       //Giao diện
        rcvKhoanThu = view.findViewById(R.id.rcvKhoanThu);
        floatAddLoai = view.findViewById(R.id.fabAddLoai);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvKhoanThu.setLayoutManager(layoutManager);
        // data
        khoanThuChiDAO = new KhoanThuChiDAO(getContext());
         loadData();

        floatAddLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogThemKhoanThu();
            }
        });
        return view;
    }

    private ArrayList<HashMap<String, Object>> getListSpinner(){
        ArrayList<HashMap<String, Object>> listSpinner = new ArrayList<>();
        ArrayList<Loai> listLoai = khoanThuChiDAO.getdsLoai("thu");
        for (Loai loai : listLoai){
            HashMap<String , Object> hashMap = new HashMap<>();
            hashMap.put("maloai", loai.getMaloai());
            hashMap.put("tenloai", loai.getTenloai());
            listSpinner.add(hashMap);
        }
        return listSpinner;
    }

    public void ShowDialogThemKhoanThu(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_themkhoanthu, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);
        TextInputLayout edtTien = view.findViewById(R.id.edtTien);
        TextInputLayout edtGhiChu = view.findViewById(R.id.edtGhiChu);
        TextInputLayout edtNgayThu = view.findViewById(R.id.edtNgayThu);
        Spinner spnLoaiThu = view.findViewById(R.id.spnLoaiThu);
        Button btnThemKhoanThu = view.findViewById(R.id.btnThemKhoanThu);
        Button btnHuyKhoanThu = view.findViewById(R.id.btnHuyKhoanThu);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getListSpinner(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiThu.setAdapter(simpleAdapter);

        btnThemKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tien =edtTien.getEditText().getText().toString();
                String ghichu = edtGhiChu.getEditText().getText().toString();
                String ngaythu = edtNgayThu.getEditText().getText().toString();
               HashMap<String, Object> selected = (HashMap<String, Object>) spnLoaiThu.getSelectedItem();
               int maloai = (int) selected.get("maloai");
               KhoanThuChi khoanThuChi = new KhoanThuChi(Integer.parseInt(tien),ngaythu,ghichu,maloai);
                if(khoanThuChiDAO.themMoiKhoan(khoanThuChi)){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuyKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }

    public void loadData(){
        list = khoanThuChiDAO.getdsKhoanThuChi("thu");
        //adapter
        khoanThuAdapter = new KhoanThuAdapter(list, getContext(), khoanThuChiDAO, getListSpinner());
        rcvKhoanThu.setAdapter(khoanThuAdapter);
    }
}
