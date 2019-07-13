package edu.binghamton.project5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button add, clear;
    private EditText input;
    private ListView list;
    private TextView total;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    //this will be used to load values upon application startup but afterwards use the ArrayList
    int[] array = {1, 2, 3, 4, 5, 6, 7, 8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add);
        clear = findViewById(R.id.clear);
        input = findViewById(R.id.input);
        list = findViewById(R.id.list);
        total = findViewById(R.id.total);

        arrayList = new ArrayList<String>();
        for (int i: array) {
            arrayList.add(String.valueOf(i));
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        list.setAdapter(adapter);

        calculateTotal();
        configureClearButton();
    }

    public void configureClearButton() {
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
    }

    public void calculateTotal() {
        int totalCount = 0;
        for (int i: array) {
            totalCount += i;
        }
        total.setText(String.valueOf(totalCount));
    }

    public void clear() {
        arrayList.clear();
        adapter.notifyDataSetChanged();
        total.setText("0");
    }
}
