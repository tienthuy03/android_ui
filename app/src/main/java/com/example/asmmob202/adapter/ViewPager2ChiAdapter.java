package com.example.asmmob202.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.asmmob202.fragment.KhoanChiFragment;
import com.example.asmmob202.fragment.LoaiChiFragment;

public class ViewPager2ChiAdapter extends FragmentStateAdapter {
    public ViewPager2ChiAdapter(@NonNull FragmentActivity fragmentActivity, LoaiChiFragment loaiChiFragment, KhoanChiFragment khoanChiFragment) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       if(position == 0){
           return  new LoaiChiFragment();

       }else {
           return new KhoanChiFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
