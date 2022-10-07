package com.example.asmmob202.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmmob202.R;
import com.example.asmmob202.adapter.LoaiChiAdapter;
import com.example.asmmob202.dao.KhoanThuChiDAO;
import com.example.asmmob202.model.Loai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LoaiChiFragment extends Fragment {
    RecyclerView rcvLoaiChi;
    KhoanThuChiDAO khoanThuChiDAO;
    ArrayList<Loai> list;
    LoaiChiAdapter loaiChiAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loaichi_fragment, container, false);
        //ánh xạ giao diện

        rcvLoaiChi = view.findViewById(R.id.rcvLoaiChi);
        FloatingActionButton fabThemLoaiChi = view.findViewById(R.id.fabThemLoaiChi);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvLoaiChi.setLayoutManager(layoutManager);

        //data
        khoanThuChiDAO = new KhoanThuChiDAO(getContext());
       loadData();


        fabThemLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogThemLoaiChi();
            }
        });
        return view;

    }

    public  void loadData(){
        list = khoanThuChiDAO.getdsLoai("chi");
        //xét adapter
        loaiChiAdapter = new LoaiChiAdapter(list, getContext(), khoanThuChiDAO );
        rcvLoaiChi.setAdapter(loaiChiAdapter);
    }

   public  void ShowDialogThemLoaiChi(){
       AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
       LayoutInflater layoutInflater = getLayoutInflater();
       View view = layoutInflater.inflate(R.layout.dialog_themloaichi, null);
       builder.setView(view);
       AlertDialog alertDialog = builder.create();
       alertDialog.show();
       TextInputLayout edtTenLoaiChi = view.findViewById(R.id.edtTenLoaiChi);
       Button btnThemLoaiChi = view.findViewById(R.id.btnThemLoaiChi);
       Button btnHuyLoaiChi = view.findViewById(R.id.btnHuyLoaiChi);

       btnThemLoaiChi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String tenloaichi = edtTenLoaiChi.getEditText().getText().toString();
               Loai loaiChiThem = new Loai(tenloaichi,"chi");
             if(khoanThuChiDAO.ThemLoai(loaiChiThem)){
                 Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                 loadData();
                 alertDialog.dismiss();
             }else {
                 Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
             }
           }
       });
        btnHuyLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

   }

}
