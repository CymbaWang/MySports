package com.example.mysports.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mysports.adapter.GuanzhuAdapter;
import com.example.mysports.R;
import com.example.mysports.pojo.Guanzhu;

import java.util.ArrayList;
import java.util.List;

public class GuanzhuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanzhu);

        ListView listView1=(ListView)findViewById(R.id.guanzhu_list);
        final List<Guanzhu> guanzhuList=new ArrayList<Guanzhu>();
        guanzhuList.add(new Guanzhu(R.drawable.liweisi,R.drawable.nan,R.drawable.diandiandian,"Mafiaboy","我的世界黑暗之中的光芒！"));
        GuanzhuAdapter guanzhuAdapter=new GuanzhuAdapter(getApplicationContext(),guanzhuList);
        listView1.setAdapter(guanzhuAdapter);

        ImageButton fanhui_button=(ImageButton)findViewById(R.id.fanhui_button);
        fanhui_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
