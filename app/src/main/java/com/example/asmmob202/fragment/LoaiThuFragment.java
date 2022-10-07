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
import com.example.asmmob202.adapter.LoaiThuAdapter;
import com.example.asmmob202.dao.KhoanThuChiDAO;
import com.example.asmmob202.model.Loai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LoaiThuFragment extends Fragment {
    RecyclerView rcvLoaiThu;
    KhoanThuChiDAO khoanThuChiDAO;
    ArrayList<Loai> list;
    LoaiThuAdapter loaiThuAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loaithu_fragment, container, false);
        //ánh xạ giao diện

        rcvLoaiThu = view.findViewById(R.id.rcvLoaiThu);
        FloatingActionButton fabThemLoaiChi = view.findViewById(R.id.fabAddLoai);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvLoaiThu.setLayoutManager(layoutManager);

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
        list = khoanThuChiDAO.getdsLoai("thu");
        //xét adapter
        loaiThuAdapter = new LoaiThuAdapter(list, getContext(), khoanThuChiDAO );
        rcvLoaiThu.setAdapter(loaiThuAdapter);
    }

   public  void ShowDialogThemLoaiChi(){
       AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
       LayoutInflater layoutInflater = getLayoutInflater();
       View view = layoutInflater.inflate(R.layout.dialog_themloaithu, null);
       builder.setView(view);
       AlertDialog alertDialog = builder.create();
       alertDialog.show();
       TextInputLayout edtTenLoaiThu = view.findViewById(R.id.edtTenLoaiThu);
       Button btnThemLoaiThu = view.findViewById(R.id.btnThemLoaiThu);
       Button btnHuyLoaiThu = view.findViewById(R.id.btnHuyLoaiThu);

       btnThemLoaiThu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String tenloaichi = edtTenLoaiThu.getEditText().getText().toString();
               Loai loaiChiThem = new Loai(tenloaichi,"thu");
             if(khoanThuChiDAO.ThemLoai(loaiChiThem)){
                 Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                 loadData();
                 alertDialog.dismiss();
             }else {
                 Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
             }
           }
       });
        btnHuyLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

   }


    }

