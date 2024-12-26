package com.example.drink_order_system;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import com.example.drink_order_system.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;

    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private List<ImageModel> imageList = new ArrayList<>();
    private Handler handler = new Handler();
    private Runnable runnable;

    private RecyclerView recyclerView1;
    private ImageAdapter adapter1;
    private List<ImageModel> imageList1 = new ArrayList<>();
    private Handler handler1 = new Handler();
    private Runnable runnable1;

//    private RadioGroup rg_tab_bar;
//    private RadioButton rb_home;
//    private Fragment fg_home; //点单界面
//    private Fragment fg_order; //点单界面
//    private Fragment fg_shop; //购物车界面
//    private Fragment fg_bill; //查看历史订单界面
//    private Fragment fg_mine; //点单界面
//    private FragmentManager fManager;
//    private String userName;

    public HomeFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // 正确获取RecyclerView实例，这里假设布局文件中RecyclerView的id是recyclerView


        TextView username = root.findViewById(R.id.username);
        username.setText(People_Data.user_name);

        TextView textView5 = root.findViewById(R.id.textView5);
        textView5.setText(People_Data.money.toString());

        TextView textView6 = root.findViewById(R.id.textView6);
        textView6.setText(People_Data.pages.toString());


        recyclerView = root.findViewById(R.id.recyclerView);

        imageList.add(new ImageModel(R.drawable.home_tmzx2));
        imageList.add(new ImageModel(R.drawable.home_tmzx1));
        //imageList.add(new ImageModel(R.drawable.image3));

        adapter = new ImageAdapter(imageList);

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


        recyclerView1 = root.findViewById(R.id.recyclerView1);

        imageList1.add(new ImageModel(R.drawable.home_tmzx2));
        imageList1.add(new ImageModel(R.drawable.home_tmzx1));
        //imageList.add(new ImageModel(R.drawable.image3));

        adapter1 = new ImageAdapter(imageList1);

        // 设置布局管理器为线性布局管理器，实现水平方向滚动，使图片横向排列展示
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(layoutManager1);

        recyclerView1.setAdapter(adapter1);

        // 实现自动轮播，每3秒切换一次图片（可根据需求调整时间间隔）
        runnable1 = new Runnable() {
            @Override
            public void run() {
                int currentPosition = layoutManager1.findFirstVisibleItemPosition();
                int nextPosition = currentPosition < imageList1.size() - 1? currentPosition + 1 : 0;
                recyclerView1.smoothScrollToPosition(nextPosition);
                handler1.postDelayed(runnable1, 3000);
            }
        };
        handler1.postDelayed(runnable1, 3000);

        //到店自取的事件
//        ImageView inshop = root.findViewById(R.id.inshop);
//        inshop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //点击以后跳转到点单界面
//                FragmentManager fManager = null;
//                FragmentTransaction fTransaction = fManager.beginTransaction();
//                hideAllFragment(fTransaction);
//                int checkedId = R.id.rb_order;
//                switch (checkedId){
//                    case R.id.rb_order:
//                        if(fg_order==null)
//                        {
//                            fg_order = OrderFragment.newInstance(userName);
//                            fTransaction.add(R.id.ly_content,fg_order);
//                            fTransaction.show(fg_order);
//                        }
//                        else
//                            fTransaction.show(fg_order);
//                        break;
//                }
//                fTransaction.commit();
//            }
//        });
        return root;
    }

    //隐藏所有Fragment
//    private void hideAllFragment(FragmentTransaction fragmentTransaction){
//        if(fg_home!=null)fragmentTransaction.hide(fg_home);
//        if(fg_order!=null)fragmentTransaction.hide(fg_order);
//        if(fg_bill!=null)fragmentTransaction.hide(fg_bill);
//        if(fg_shop!=null)fragmentTransaction.hide(fg_shop);
//        if(fg_mine!=null)fragmentTransaction.hide(fg_mine);
//    }
}

