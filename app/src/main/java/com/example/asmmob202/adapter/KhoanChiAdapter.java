package com.example.asmmob202.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmmob202.R;
import com.example.asmmob202.dao.KhoanThuChiDAO;
import com.example.asmmob202.model.KhoanThuChi;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class KhoanChiAdapter extends RecyclerView.Adapter<KhoanChiAdapter.KhoanChiViewHolder> {
    private ArrayList<KhoanThuChi> listkhoanthuchi;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;
    private ArrayList<HashMap<String, Object>> listSpinner;

    public KhoanChiAdapter(ArrayList<KhoanThuChi> listkhoanthuchi, Context context, KhoanThuChiDAO khoanThuChiDAO, ArrayList<HashMap<String, Object>> listSpinner) {
        this.listkhoanthuchi = listkhoanthuchi;
        this.context = context;
        this.khoanThuChiDAO = khoanThuChiDAO;
        this.listSpinner = listSpinner;
    }


    @NonNull
    @Override
    public KhoanChiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.khoanchi_item, parent, false);
        KhoanChiViewHolder khoanThuChiViewHolder = new KhoanChiViewHolder(view);
        return khoanThuChiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KhoanChiViewHolder holder, int position) {
        KhoanThuChi khoanThuChi = listkhoanthuchi.get(position);
        holder.txtTenKhoanChi.setText(khoanThuChi.getTenloai());
        holder.txtTienKhoanChi.setText(String.valueOf(khoanThuChi.getTien()));
        holder.txtNgayChi.setText(khoanThuChi.getNgay());
        holder.txtGhiChu.setText(khoanThuChi.getGhichu());

        holder. iveditKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogSuaKhoanThuChi(khoanThuChi);
            }
        });
        holder.ivdeleteKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(khoanThuChiDAO.xoaKhoan(khoanThuChi.getMakhoan())){
                   Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                    reloadData();
               }else {
                   Toast.makeText(context, "Xoa Thất Bại", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listkhoanthuchi.size();
    }

    public static class KhoanChiViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenKhoanChi, txtTienKhoanChi, txtGhiChu, txtNgayChi;
        ImageView iveditKhoanChi, ivdeleteKhoanChi;
        CardView cvKhoanChi;

        public KhoanChiViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenKhoanChi = itemView.findViewById(R.id.txtTenKhoanChi);
            txtTienKhoanChi = itemView.findViewById(R.id.txtTienKhoanChi);
            txtNgayChi = itemView.findViewById(R.id.txtngay);
            txtGhiChu = itemView.findViewById(R.id.txtGhiChu);
            cvKhoanChi = itemView.findViewById(R.id.cvKhoanChi);
            iveditKhoanChi = itemView.findViewById(R.id.iveditKhoanChi);
            ivdeleteKhoanChi = itemView.findViewById(R.id.ivdeleteKhoanChi);
        }
    }
    public void ShowDialogSuaKhoanThuChi(KhoanThuChi khoanThuChi){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_suakhoanchi, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Spinner spnLoaiChi = view.findViewById(R.id.spnLoaiChi);
        TextInputLayout edtTienKhoanChi = view.findViewById(R.id.edtTienKhoanChi);
        TextInputLayout edtGhiChu = view.findViewById(R.id.edtGhiChu);
        Button btnCapnhat = view.findViewById(R.id.btnUpdateKhoanChi);
        Button btnHuy = view.findViewById(R.id.btnHuyKhoanChi);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listSpinner,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
               new int[]{android.R.id.text1}
        );
            spnLoaiChi.setAdapter(simpleAdapter);

            int index = 0;
            int position = -1;
            for (HashMap<String, Object> item : listSpinner){
                if((int) item.get("maloai") == khoanThuChi.getMaloai()){
                    position = index;
                }
                index++;
            }
            spnLoaiChi.setSelection(position);
            edtTienKhoanChi.getEditText().setText(String.valueOf(khoanThuChi.getTien()));
            edtGhiChu.getEditText().setText(khoanThuChi.getGhichu());

        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tien = edtTienKhoanChi.getEditText().getText().toString();
                String ghichu = edtGhiChu.getEditText().getText().toString();
                HashMap<String, Object> selected = (HashMap<String, Object>) spnLoaiChi.getSelectedItem();
                int maloai = (int) selected.get("maloai");
                khoanThuChi.setMaloai(maloai);
                khoanThuChi.setTien(Integer.parseInt(tien));
                khoanThuChi.setGhichu(ghichu);
                if(khoanThuChiDAO.capNhatKhoan(khoanThuChi)){
                    Toast.makeText(context, "Cập Nhật Thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(context, "Cập Nhật Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            alertDialog.cancel();
            }
        });
    }
        private void reloadData(){
        listkhoanthuchi.clear();
        listkhoanthuchi = khoanThuChiDAO.getdsKhoanThuChi("chi");
        notifyDataSetChanged();
        }

}
