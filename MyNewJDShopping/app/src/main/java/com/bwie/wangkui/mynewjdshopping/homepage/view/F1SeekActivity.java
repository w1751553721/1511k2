package com.bwie.wangkui.mynewjdshopping.homepage.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.wangkui.mynewjdshopping.GoodsSeek.DataViewActivity;
import com.bwie.wangkui.mynewjdshopping.MainActivity;
import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.Utils;
import com.bwie.wangkui.mynewjdshopping.homepage.SqDatabase.SqDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class F1SeekActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.img1)
    ImageButton img1;

    ListView list;
    private SqDao dao;

    private FlowLayout mFlowLayout;
    private LayoutInflater mInflater;
    private String[] mVals = new String[]{"苹果8", "精灵3", "无人机玩具", "奶酪",
            "老爹鞋", "轻薄本", "杨氏橙", "工程机",
            "飞利浦牙刷", "金士顿内存"};//数据模拟，实际应从网络获取此数据

    /************
     * 以上为流式标签相关
     ************/
    public static final String EXTRA_KEY_TYPE = "extra_key_type";
    public static final String EXTRA_KEY_KEYWORD = "extra_key_keyword";
    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";
    private SharedPreferences mPref;//使用SharedPreferences记录搜索历史
    private String mType;
    private EditText input;
    private Button btn_search;
    private List<String> mHistoryKeywords;//记录文本
    private ArrayAdapter<String> mArrAdapter;//搜索历史适配器
    private LinearLayout mSearchHistoryLl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f1_seek);
        ButterKnife.inject(this);
        //先查询数据库
        dao = new SqDao(this);
        initFlowView();
        initHistoryView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initFlowView() {
        mInflater = LayoutInflater.from(this);
        mFlowLayout = (FlowLayout) findViewById(R.id.flowlayout);
        initData();
    }

    /**
     * 将数据放入流式布局
     */
    private void initData() {
        for (int i = 0; i < mVals.length; i++) {
            TextView tv = (TextView) mInflater.inflate(
                    R.layout.search_label_tv, mFlowLayout, false);
            tv.setText(mVals[i]);
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mFlowLayout.addView(tv);
        }
    }

    /************
     * 以上为流式标签相关
     ************/

    private void initHistoryView() {
        input = (EditText) findViewById(R.id.edit);
        btn_search = (Button) findViewById(R.id.seek);
        btn_search.setOnClickListener(this);
        img1.setOnClickListener(this);
        mPref = getSharedPreferences("input", Activity.MODE_PRIVATE);
        mType = getIntent().getStringExtra(EXTRA_KEY_TYPE);
        String keyword = getIntent().getStringExtra(EXTRA_KEY_KEYWORD);
        if (!TextUtils.isEmpty(keyword)) {
            input.setText(keyword);
        }
        mHistoryKeywords = new ArrayList<String>();
        input = (EditText) findViewById(R.id.edit);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    if (mHistoryKeywords.size() > 0) {
                        mSearchHistoryLl.setVisibility(View.VISIBLE);
                    } else {
                        mSearchHistoryLl.setVisibility(View.GONE);
                    }
                } else {
                    mSearchHistoryLl.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initSearchHistory();
    }

    /**
     * 初始化搜索历史记录
     */
    public void initSearchHistory() {
        mSearchHistoryLl = (LinearLayout) findViewById(R.id.search_history_ll);
        ListView listView = (ListView) findViewById(R.id.search_history_lv);
        findViewById(R.id.clear_history_btn).setOnClickListener(this);
        String history = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        if (!TextUtils.isEmpty(history)) {
            List<String> list = new ArrayList<String>();
            for (Object o : history.split(",")) {
                list.add((String) o);
            }
            mHistoryKeywords = list;
        }
        if (mHistoryKeywords.size() > 0) {
            mSearchHistoryLl.setVisibility(View.VISIBLE);
        } else {
            mSearchHistoryLl.setVisibility(View.GONE);
        }
        mArrAdapter = new ArrayAdapter<String>(this, R.layout.item_search_history, mHistoryKeywords);
        listView.setAdapter(mArrAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                input.setText(mHistoryKeywords.get(i));
                mSearchHistoryLl.setVisibility(View.GONE);
            }
        });
        mArrAdapter.notifyDataSetChanged();
    }

    /**
     * 储存搜索历史
     */
    public void save() {
        String text = input.getText().toString();
        String oldText = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        Log.e("tag", "" + oldText);
        Log.e("Tag", "" + text);
        Log.e("Tag", "" + oldText.contains(text));
        if (!TextUtils.isEmpty(text) && !(oldText.contains(text))) {
            if (mHistoryKeywords.size() > 5) {
                //最多保存条数
                return;
            }
            SharedPreferences.Editor editor = mPref.edit();
            editor.putString(KEY_SEARCH_HISTORY_KEYWORD, text + "," + oldText);
            editor.commit();
            mHistoryKeywords.add(0, text);
        }
        mArrAdapter.notifyDataSetChanged();
    }

    /**
     * 清除历史纪录
     */
    public void cleanHistory() {
        mPref = getSharedPreferences("input", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();
        editor.remove(KEY_SEARCH_HISTORY_KEYWORD).commit();
        mHistoryKeywords.clear();
        mArrAdapter.notifyDataSetChanged();
        mSearchHistoryLl.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seek:
                String keywords = input.getText().toString();
                if (!TextUtils.isEmpty(keywords)) {
                    save();
                    Intent intent = new Intent(F1SeekActivity.this, DataViewActivity.class);
                    intent.putExtra("seek", keywords);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(F1SeekActivity.this, "请输入搜索内容", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.clear_history_btn:
                cleanHistory();
                break;
                case R.id.img1:
                  finish();
                break;
            default:
                break;
        }

    }

}
