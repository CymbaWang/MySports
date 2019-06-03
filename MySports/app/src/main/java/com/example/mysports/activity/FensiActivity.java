package com.example.mysports.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mysports.R;
import com.example.mysports.pojo.Fensi;
import com.example.mysports.adapter.FensiAdapter;

import java.util.ArrayList;
import java.util.List;

public class FensiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fensi);

        ListView listView1=(ListView)findViewById(R.id.fensi_list);
        final List<Fensi> fensiList=new ArrayList<Fensi>();
        fensiList.add(new Fensi(R.drawable.liweisi,R.drawable.nan,R.drawable.guanzhu,"Mafiaboy","我的世界黑暗之中的光芒！"));
        fensiList.add(new Fensi(R.drawable.songzhongji,R.drawable.nv,R.drawable.yiguanzhu,"小熊熊","玩闹一时爽，计划火葬场(-_-)"));
        FensiAdapter fensiAdapter=new FensiAdapter(this.getApplicationContext(),fensiList);
        listView1.setAdapter(fensiAdapter);

        ImageButton fanhui_button=(ImageButton)findViewById(R.id.fanhui_button);
        fanhui_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
