package edu.binghamton.project5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListChanged {
    private Button add, clear;
    private EditText input;
    private RecyclerView list;
    private TextView total;

    private ArrayList<String> arrayList;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;

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

        recyclerView = findViewById(R.id.list);
        adapter = new RecyclerAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.refreshDrawableState();

        calculateTotal();
        configureClearButton();
        configureAddButton();
    }

    public void configureAddButton() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
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
        Integer totalCount = 0;
        for (String i: arrayList) {
            totalCount += Integer.parseInt(i);
        }
        total.setText(String.valueOf(totalCount));
    }

    public void clear() {
        arrayList.clear();
        adapter.notifyDataSetChanged();
        total.setText("0");
    }

    public void add() {
        String userInput = input.getText().toString();
        arrayList.add(userInput);
        adapter.notifyDataSetChanged();
        calculateTotal();
        input.setText("");
    }
}
