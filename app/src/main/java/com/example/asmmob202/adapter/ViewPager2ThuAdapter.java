package com.example.asmmob202.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.asmmob202.fragment.KhoanThuFragment;
import com.example.asmmob202.fragment.LoaiThuFragment;

public class ViewPager2ThuAdapter extends FragmentStateAdapter {
    public ViewPager2ThuAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return new LoaiThuFragment();
        }else{
            return new KhoanThuFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
