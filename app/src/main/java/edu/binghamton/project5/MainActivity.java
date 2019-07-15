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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListChanged {
    private String filename;

    private Button add, clear;
    private EditText input;
    private RecyclerView list;
    private TextView total;

    private ArrayList<String> arrayList;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;

    //this will be used to load values upon application startup but afterwards use the ArrayList
    ArrayList<Integer> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filename = getFilesDir().getPath() + "/data.txt";
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        add = findViewById(R.id.add);
        clear = findViewById(R.id.clear);
        input = findViewById(R.id.input);
        total = findViewById(R.id.total);

        arrayList = new ArrayList<>();
        for (Integer i: array) {
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

        try {
            saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        arrayList.clear();
        adapter.notifyDataSetChanged();
        total.setText("0");
        try {
            saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add() {
        String userInput = input.getText().toString();
        arrayList.add(userInput);
        adapter.notifyDataSetChanged();
        calculateTotal();
        input.setText("");
    }

    public void saveData() throws IOException {
        File f = new File(filename);
        if (!f.exists()) {
            f.createNewFile();
        }

        FileOutputStream fout = openFileOutput("data.txt", MODE_PRIVATE);
        String out = "";
        for (String i: arrayList) {
            out = out + i + " ";
        }
        System.out.println("Saving Data: " + out);
        fout.write(out.getBytes());
        fout.close();
    }

    public void loadData() throws IOException {
        File f = new File(filename);
        if (!f.exists()) {
            f.createNewFile();
            return;
        }
        if (f.length() == 0) {
            return;
        }

        FileInputStream fin = new FileInputStream(new File(filename));
        int c;
        String data = "";
        while((c = fin.read()) != -1) {
            data = data + Character.toString((char) c);
        }
        String[] splitData = data.split(" ");
        for (String i: splitData) {
            array.add(Integer.valueOf(i));
        }
    }
}
