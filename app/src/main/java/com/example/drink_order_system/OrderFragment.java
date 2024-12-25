package com.example.drink_order_system;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderFragment extends Fragment {
    private ArrayList<Drinks> drinks_array = new ArrayList<Drinks>(); //可选的饮品列表
    private ArrayList<LeftBean> titles_array = new ArrayList<LeftBean>(); //饮品类别列表
    private RecyclerView right_listView; //右侧饮品列表
    private RecyclerView left_listView; //左侧类别列表
    private LinearLayoutManager right_llM;
    private TextView right_title;
    private SearchView searchView;

    private AlertDialog chooseDialog = null;
    private AlertDialog.Builder builder = null;
    private View view_choose;

    private Context mContext = this.getActivity();

    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance(String userName) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        System.out.println("Username in newInstance"+userName);
        args.putString("userName", userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        SearchView mSearch = (SearchView) view.findViewById(R.id.my_search);
        int id = mSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView text_search = (TextView) mSearch.findViewById(id);
        text_search.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        right_title = (TextView) view.findViewById(R.id.Top_drinkType);

        right_listView = (RecyclerView) view.findViewById(R.id.rec_right);
        left_listView = (RecyclerView) view.findViewById(R.id.rec_left);
        searchView = (SearchView) view.findViewById(R.id.my_search);
        builder = new AlertDialog.Builder(this.getActivity());
        view_choose = inflater.inflate(R.layout.dialogue_choose, null, false);
        builder.setView(view_choose);
        builder.setCancelable(false);
        chooseDialog = builder.create();

        view_choose.findViewById(R.id.button_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDialog.dismiss();
            }
        });

        view_choose.findViewById(R.id.button_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String size = "中杯";
                String temperature = "全冰";
                String sugar = "全糖";
                RadioGroup radiogroup = (RadioGroup) view_choose.findViewById(R.id.radioGroup_size);
                for (int i = 0; i < radiogroup.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) radiogroup.getChildAt(i);
                    if (rd.isChecked()) {
                        size = String.valueOf(rd.getText());
                    }
                }
                radiogroup = (RadioGroup) view_choose.findViewById(R.id.radioGroup_ice);
                for (int i = 0; i < radiogroup.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) radiogroup.getChildAt(i);
                    if (rd.isChecked()) {
                        temperature = String.valueOf(rd.getText());
                    }
                }
                radiogroup = (RadioGroup) view_choose.findViewById(R.id.radioGroup_sugar);
                for (int i = 0; i < radiogroup.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) radiogroup.getChildAt(i);
                    if (rd.isChecked()) {
                        sugar = String.valueOf(rd.getText());
                    }
                }
                TextView drinkName = view_choose.findViewById(R.id.choose_drinkName);
                //写买进购物车的逻辑
                System.out.println("drinkName:" + String.valueOf(drinkName.getText()).split("  #")[0]);
                Drinks drink = new Drinks(Integer.parseInt(String.valueOf(drinkName.getText()).split("  #")[1]));
                Flavor flavor = new Flavor(size, temperature, sugar);
                TextView numberTV = (TextView) view_choose.findViewById(R.id.textView_drinkNumber);
                int number = Integer.parseInt((String) numberTV.getText());
                Ordered_drinks od = new Ordered_drinks(drink, flavor, number);
                chooseDialog.dismiss();
            }
        });

        view_choose.findViewById(R.id.button_subtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView numberText = (TextView) view_choose.findViewById(R.id.textView_drinkNumber);
                int i = Integer.parseInt(String.valueOf(numberText.getText()));
                if (i > 1) {
                    i--;
                }
                numberText.setText(String.valueOf(i));
            }
        });

        view_choose.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView numberText = (TextView) view_choose.findViewById(R.id.textView_drinkNumber);
                int i = Integer.parseInt(String.valueOf(numberText.getText()));
                if (i < 100) {
                    i++;
                }
                numberText.setText(String.valueOf(i));
            }
        });

        initData();
        right_llM = new LinearLayoutManager(this.getActivity());
        right_listView.setLayoutManager(right_llM);
        Right_adapter rightAdapter = new Right_adapter(inflater, drinks_array);
        right_listView.setAdapter(rightAdapter);

        titles_array.get(0).setSelect(true);
        left_listView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        LeftAdapter leftAdapter = new LeftAdapter(titles_array);
        left_listView.setAdapter(leftAdapter);

        right_listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItemPosition = right_llM.findFirstVisibleItemPosition();
                leftAdapter.setCurrentPosition(firstItemPosition);
                if (leftAdapter.getCurrentTitle() != "") {
                    right_title.setText(leftAdapter.getCurrentTitle());
                }
            }
        });


        leftAdapter.setOnItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int rightPosition) {
                if (right_llM != null) {
                    right_llM.scrollToPositionWithOffset(rightPosition, 0);
                }
            }
        });

        rightAdapter.buttonSetOnClick(new Right_adapter.MyClickListener() {
            @Override
            public void onclick(View v, int position) {
                chooseDialog.show();
                if (view_choose != null) {
                    Drinks drink = drinks_array.get(position);
                    ImageView img = view_choose.findViewById(R.id.choose_drink_img);
                    img.setImageResource(drink.getImageResId());
                    TextView name = view_choose.findViewById(R.id.choose_drinkName);
                    name.setText(drink.get_name() + "  #" + (drink.get_number() + 1));
                    TextView intro = view_choose.findViewById(R.id.choose_drinkIntro);
                    intro.setText(drink.get_introduction());
                    TextView drink_number = view_choose.findViewById(R.id.textView_drinkNumber);
                    drink_number.setText("1");
                }
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryText) {
                for (int i = 0; i < drinks_array.size(); i++) {
                    if (drinks_array.get(i).get_name().contains(queryText)) {
                        if (right_llM != null) {
                            right_llM.scrollToPositionWithOffset(i, 0);
                            break;
                        }
                    }
                }
                return true;
            }
        });
        return view;
    }

    private void initData() {
        drinks_array.add(new Drinks("冰鲜柠檬水", " 蜜雪经典",
                4f, "大片柠檬看得见，现切现捣超新鲜", R.drawable.good_bxnms));
        drinks_array.add(new Drinks("茉莉奶绿", 6f, "好一朵美丽的茉莉花",
                R.drawable.good_mlnl));
        drinks_array.add(new Drinks("新鲜冰淇淋", 2f, "新鲜冰淇淋，一口就爱上",
                R.drawable.good_xxbjl));
        drinks_array.add(new Drinks("珍珠奶茶", 6f, "珍珠Q弹，奶茶经曲",
                R.drawable.good_zznc));
        drinks_array.add(new Drinks("蜜桃四季春", 7f, "蜜桃四季春，四季桃花运",
                R.drawable.good_mtsjc));

        drinks_array.add(new Drinks("珍珠奶茶", "醇香奶茶",
                6f, "珍珠Q弹，奶茶经曲", R.drawable.good_zznc));
        drinks_array.add(new Drinks("布丁奶茶",
                6f, "鸡蛋布丁更香浓", R.drawable.good_bdnc));
        drinks_array.add(new Drinks("红豆奶茶",
                6f, "沙甜小红豆，香醇有嚼头", R.drawable.good_hdnc));

        drinks_array.add(new Drinks("冰鲜柠檬水", "真鲜果茶",
                4f, "大片柠檬看得见，现切现捣超新鲜", R.drawable.good_bxnms));
        drinks_array.add(new Drinks("蜜桃四季春", 7f, "蜜桃四季春，四季桃花运",
                R.drawable.good_mtsjc));
        drinks_array.add(new Drinks("芋圆葡萄", 8f, "大颗葡萄肉，酸甜嚼不够",
                R.drawable.good_yypt));

        drinks_array.add(new Drinks("茉莉奶绿", "轻乳系列",
                18f, "好一朵美丽的茉莉花", R.drawable.good_mlnl));

        drinks_array.add(new Drinks("美式咖啡", "鲜萃咖啡",
                6f, "蜜雪咖啡，滴滴鲜萃", R.drawable.good_mskf));
        drinks_array.add(new Drinks("拿铁咖啡", 7f, "蜜雪咖啡，滴滴鲜萃",
                R.drawable.good_ntkf));

        drinks_array.add(new Drinks("新鲜冰淇淋", "新鲜冰淇淋",
                2f, "新鲜冰淇淋，一口就爱上", R.drawable.good_xxbjl));

        for (int i = 0; i < drinks_array.size(); i++) {
            Drinks temp = drinks_array.get(i);
            if (temp.get_type() != null) {
                titles_array.add(new LeftBean(i, temp.get_type()));
            }
        }
    }
}