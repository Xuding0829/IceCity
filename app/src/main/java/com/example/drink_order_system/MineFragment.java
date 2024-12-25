package com.example.drink_order_system;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drink_order_system.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends Fragment {

    private FragmentHomeBinding binding;

    private RecyclerView recyclerView;
    private ImageAdapter2 adapter;
    private List<ImageModel> imageList = new ArrayList<>();
    private Handler handler = new Handler();
    private Runnable runnable;

    public MineFragment(){

    }

    public static MineFragment newInstance(String userName) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        System.out.println("Username in newInstance"+userName);
        args.putString("userName", userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_mine, container, false);


        TextView minename = root.findViewById(R.id.minename);
        minename.setText(People_Data.user_name);

        TextView money = root.findViewById(R.id.money);
        money.setText(People_Data.money.toString());

        TextView pages = root.findViewById(R.id.pages);
        pages.setText(People_Data.pages.toString());


        recyclerView = root.findViewById(R.id.recyclerView);

        imageList.add(new ImageModel(R.drawable.mine_dj1));
        imageList.add(new ImageModel(R.drawable.mine_dj2));
        imageList.add(new ImageModel(R.drawable.mine_dj3));
        imageList.add(new ImageModel(R.drawable.mine_dj4));

        adapter = new ImageAdapter2(imageList);

        // 设置布局管理器为线性布局管理器，实现水平方向滚动，使图片横向排列展示
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        // 实现自动轮播，每3秒切换一次图片（可根据需求调整时间间隔）
        runnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition = layoutManager.findFirstVisibleItemPosition();
                int nextPosition = currentPosition < imageList.size() - 1? currentPosition + 1 : 0;
                recyclerView.smoothScrollToPosition(nextPosition);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);

        ImageView duihuanma = root.findViewById(R.id.duihuanma);
        duihuanma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击以后跳转
                Intent intent = new Intent(getActivity(), DuihuanmaActivity.class);
                startActivity(intent);
            }
        });

        ImageView xuewangfuliqun = root.findViewById(R.id.xuewangfuliqun);
        xuewangfuliqun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "此功能还未开放", Toast.LENGTH_SHORT).show();
            }
        });


        ImageView wentifankui  = root.findViewById(R.id.wentifankui);
        wentifankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击以后跳转
                Intent intent = new Intent(getActivity(), WentifankuiActivity.class);
                startActivity(intent);
            }
        });

        ImageView jiamengzixun  = root.findViewById(R.id.jiamengzixun);
        jiamengzixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "此功能还未开放", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView guanyuwomen  = root.findViewById(R.id.guanyuwomen);
        guanyuwomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击以后跳转
                Intent intent = new Intent(getActivity(), guanyuwomenActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
