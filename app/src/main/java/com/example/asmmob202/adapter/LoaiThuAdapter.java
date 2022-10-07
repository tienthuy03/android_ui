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

public class LoaiThuAdapter extends RecyclerView.Adapter<LoaiThuAdapter.LoaiThuViewHolder> {
    private ArrayList<Loai> list;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;

    public LoaiThuAdapter(ArrayList<Loai> list, Context context, KhoanThuChiDAO khoanThuChiDAO) {
        this.list = list;
        this.context = context;
        this.khoanThuChiDAO = new KhoanThuChiDAO(context);
    }

    @NonNull
    @Override
    public LoaiThuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.loaithu_item, parent, false);
        LoaiThuViewHolder loaiThuViewHolder = new LoaiThuViewHolder(view);
        return loaiThuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiThuViewHolder holder, int position) {
        Loai loai = list.get(position);
        holder.txtTenLoai.setText(loai.getTenloai());
        holder.iveditLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogUpdateLoaiThu(loai);
            }
        });
        holder.ivdeleteLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (khoanThuChiDAO.xoaLoai(loai.getMaloai())) {
                    Toast.makeText(context, "Xoá Thành công", Toast.LENGTH_SHORT).show();
                    reLoadData();
                } else {
                    Toast.makeText(context, "Xoa Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LoaiThuViewHolder extends RecyclerView.ViewHolder {
        //khởi tạo các thuộc tính
        TextView txtTenLoai;
        CardView cvLoaiThu;
        ImageView iveditLoaiThu, ivdeleteLoaiThu;

        //Khởi tạo Constructor
        public LoaiThuViewHolder(View view) {
            super(view);
            //ánh xạ
            txtTenLoai = view.findViewById(R.id.txtTenLoaiChi);
            cvLoaiThu = view.findViewById(R.id.cvLoaiThu);
            iveditLoaiThu = view.findViewById(R.id.iveditLoaiThu);
            ivdeleteLoaiThu = view.findViewById(R.id.ivdeleteLoaiThu);
        }
    }
    public void ShowDialogUpdateLoaiThu(Loai loai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_sualoaithu, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);
        TextInputLayout edtTenLoaiThu = view.findViewById(R.id.edtTenLoaiThu);
        edtTenLoaiThu.getEditText().setText(loai.getTenloai());
        Button btnCapNhatLoaiThu = view.findViewById(R.id.btnCapNhatLoaiThu);
        Button btnHuyLoaiThu = view.findViewById(R.id.btnHuyLoaiThu);

        btnCapNhatLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenloai = edtTenLoaiThu.getEditText().getText().toString();
                loai.setTenloai(tenloai);
                if (khoanThuChiDAO.capnhatLoai(loai)) {
                    Toast.makeText(context, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                    reLoadData();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập Nhật Thất Bại", Toast.LENGTH_SHORT).show();
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

    private void reLoadData() {
        list.clear();
        list = khoanThuChiDAO.getdsLoai("thu");
        notifyDataSetChanged();
    }
}
