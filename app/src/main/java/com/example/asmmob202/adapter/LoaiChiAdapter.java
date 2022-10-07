package com.example.asmmob202.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmmob202.R;
import com.example.asmmob202.dao.KhoanThuChiDAO;
import com.example.asmmob202.model.Loai;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LoaiChiAdapter extends RecyclerView.Adapter<LoaiChiAdapter.LoaiChiViewHolder> {
    private ArrayList<Loai> listLoaiChi;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;

    public LoaiChiAdapter(ArrayList<Loai> listLoaiChi, Context context, KhoanThuChiDAO khoanThuChiDAO) {
        this.listLoaiChi = listLoaiChi;
        this.context = context;
        this.khoanThuChiDAO =  new KhoanThuChiDAO(context);
    }


    @NonNull
    @Override
    public LoaiChiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.loaichi_item, parent, false);
        LoaiChiViewHolder loaiChiViewHolder = new LoaiChiViewHolder(view);
        return loaiChiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiChiViewHolder holder, int position) {
        Loai loai = listLoaiChi.get(position);
        holder.txtTenLoaiChi.setText(loai.getTenloai());
        holder.iveditLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogSuaLoaiChi(loai);
            }
        });
        holder.ivdeleteLoaichi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(khoanThuChiDAO.xoaLoai(loai.getMaloai())){
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                }else {
                    Toast.makeText(context, "Xoá Thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listLoaiChi.size();
    }

    public class LoaiChiViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenLoaiChi;
        CardView cvLoaiChi;
        ImageView  iveditLoaiChi, ivdeleteLoaichi;

        public LoaiChiViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenLoaiChi = itemView.findViewById(R.id.txtTenLoaiChi);
            cvLoaiChi = itemView.findViewById(R.id.cvLoaiChi);
            iveditLoaiChi = itemView.findViewById(R.id.iveditLoaiChi);
            ivdeleteLoaichi = itemView.findViewById(R.id.ivdeleteLoaiChi);
        }
    }
    public void ShowDialogSuaLoaiChi(Loai loai){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_sualoaichi, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextInputLayout edtTenLoaiChi = view.findViewById(R.id.edtTenLoaiChi);
        edtTenLoaiChi.getEditText().setText(loai.getTenloai());
        Button btnSuaLoaiChi = view.findViewById(R.id.btnSuaLoaiChi);
        Button btnHuyLoaiChi = view.findViewById(R.id.btnHuyLoaiChi);

        btnSuaLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String tenloai = edtTenLoaiChi.getEditText().getText().toString();
              loai.setTenloai(tenloai);
              if(khoanThuChiDAO.ThemLoai(loai)){
                  Toast.makeText(context, "Cập Nhật thành công", Toast.LENGTH_SHORT).show();
                  reloadData();
                  alertDialog.dismiss();
              }else {
                  Toast.makeText(context, "Cập Nhật thất bại", Toast.LENGTH_SHORT).show();
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
    private  void reloadData(){
        listLoaiChi.clear();
        listLoaiChi = khoanThuChiDAO.getdsLoai("chi");
        notifyDataSetChanged();
    }

}
