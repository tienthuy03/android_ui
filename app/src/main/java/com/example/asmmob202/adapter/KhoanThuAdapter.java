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

public class KhoanThuAdapter extends RecyclerView.Adapter<KhoanThuAdapter.KhoanThuChiViewHolder> {
    private ArrayList<KhoanThuChi> listkhoanthuchi;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;
    private ArrayList<HashMap<String, Object>> listSpinner;

    public KhoanThuAdapter(ArrayList<KhoanThuChi> listkhoanthuchi, Context context, KhoanThuChiDAO khoanThuChiDAO, ArrayList<HashMap<String, Object>> listSpinner) {
        this.listkhoanthuchi = listkhoanthuchi;
        this.context = context;
        this.khoanThuChiDAO = khoanThuChiDAO;
        this.listSpinner = listSpinner;
    }

    @NonNull
    @Override
    public KhoanThuChiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.khoanthu_item, parent, false);
        KhoanThuChiViewHolder khoanThuChiViewHolder = new KhoanThuChiViewHolder(view);
        return khoanThuChiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KhoanThuChiViewHolder holder, int position) {
        KhoanThuChi khoanThuChi = listkhoanthuchi.get(position);
        holder.txtTenKhoanThu.setText(khoanThuChi.getTenloai());
        holder.txtTienKhoanThu.setText(String.valueOf(khoanThuChi.getTien()));
        holder.txtNgayThu.setText(khoanThuChi.getNgay());
        holder.txtGhiChuKhoanThu.setText(khoanThuChi.getGhichu());

        holder. iveditKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogSuaKhoanThuChi(khoanThuChi);
            }
        });
        holder.ivdeleteKhoanThu.setOnClickListener(new View.OnClickListener() {
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

    public class KhoanThuChiViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenKhoanThu, txtTienKhoanThu, txtGhiChuKhoanThu, txtNgayThu;
        ImageView iveditKhoanThu, ivdeleteKhoanThu;
        CardView cvKhoanThu;

        public KhoanThuChiViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenKhoanThu = itemView.findViewById(R.id.txtTenKhoanThu);
            txtTienKhoanThu = itemView.findViewById(R.id.txtTienKhoanThu);
            txtNgayThu = itemView.findViewById(R.id.txtNgayThu);
            txtGhiChuKhoanThu = itemView.findViewById(R.id.txtGhichuKhoanThu);
            cvKhoanThu = itemView.findViewById(R.id.cvKhoanThu);
            iveditKhoanThu = itemView.findViewById(R.id.iveditKhoanThu);
            ivdeleteKhoanThu = itemView.findViewById(R.id.ivdeleteKhoanThu);
        }
    }
    public void ShowDialogSuaKhoanThuChi(KhoanThuChi khoanThuChi){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_suakhoanthu, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Spinner spnLoaiThu = view.findViewById(R.id.spnLoaiThu);
        TextInputLayout edtTien = view.findViewById(R.id.edtTien);
        TextInputLayout edtGhichu = view.findViewById(R.id.edtGhiChu);
        Button btnCapnhat = view.findViewById(R.id.btnUpdateKhoanThu);
        Button btnHuy = view.findViewById(R.id.btnHuyKhoanThu);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listSpinner,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
               new int[]{android.R.id.text1}
        );
            spnLoaiThu.setAdapter(simpleAdapter);

            int index = 0;
            int position = -1;
            for (HashMap<String, Object> item : listSpinner){
                if((int) item.get("maloai") == khoanThuChi.getMaloai()){
                    position = index;
                }
                index++;
            }
            spnLoaiThu.setSelection(position);
            edtTien.getEditText().setText(String.valueOf(khoanThuChi.getTien()));
            edtGhichu.getEditText().setText(khoanThuChi.getGhichu());
        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tien = edtTien.getEditText().getText().toString();
                String ghichu = edtGhichu.getEditText().getText().toString();
                HashMap<String, Object> selected = (HashMap<String, Object>) spnLoaiThu.getSelectedItem();
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
        listkhoanthuchi = khoanThuChiDAO.getdsKhoanThuChi("thu");
        notifyDataSetChanged();
        }
}
