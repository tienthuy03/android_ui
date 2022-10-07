package com.example.asmmob202.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asmmob202.R;
import com.example.asmmob202.adapter.ViewPager2ChiAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChiFrgment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chi_fragment, container, false);
        TabLayout tabLayoutChi = view.findViewById(R.id.TabLayoutChi);
        ViewPager2 viewPager2Chi = view.findViewById(R.id.ViewPagerChi);
        ViewPager2ChiAdapter adapter = new ViewPager2ChiAdapter(getActivity(), new LoaiChiFragment() , new KhoanChiFragment());
        viewPager2Chi.setAdapter(adapter);

        new TabLayoutMediator(tabLayoutChi, viewPager2Chi, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("Loại Chi");
                }else {
                    tab.setText("Khoản Chi");
                }
            }
        }).attach();

        return view;
    }
}
