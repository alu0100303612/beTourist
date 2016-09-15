package com.example.bianney.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bianney.myapplication.others.Monument;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.pager_item);
        this.listView = (ListView) findViewById(R.id.mainListView);
        List<Monument> monuments = MainApplication.getInstance().getArraySearch();
        for (int i = 0; i < monuments.size(); i++){
            Log.d("TAG", "david pesado: "+ monuments.get(i).getName());
        }
        this.listView.setAdapter(new CustomAdapter(this, monuments, 0));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {

                // Sets the visibility of the indeterminate progress bar in the
                // title
                Log.d("TAG", "HOLAA");
            }
        });
    }
}
