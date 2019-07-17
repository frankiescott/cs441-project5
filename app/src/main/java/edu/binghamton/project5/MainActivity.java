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
import android.support.design.widget.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListChanged {
    private String filename;

    private Button add, clear;
    private EditText input;
    private TextView total;

    private ArrayList<String> arrayList;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private boolean showedMessage = false;

    //this will be used to load values upon application startup
    ArrayList<Integer> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load data from file
        filename = getFilesDir().getPath() + "/data.txt";
        try {
            values = loadData(); //returns ArrayList
        } catch (IOException e) {
            e.printStackTrace();
        }

        //populate the ArrayList that will be linked to the adapter
        arrayList = new ArrayList<>();
        if (values.size() != 0) {
            for (Integer i : values) {
                arrayList.add(String.valueOf(i));
            }
        }

        //grab handlers for widgets and configure buttons
        initWidgets();

        //set up recycler view and it's adapter
        recyclerView = findViewById(R.id.list);
        adapter = new RecyclerAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //used for swipe to delete
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.refreshDrawableState();

        //will update total if data was loaded from file
        calculateTotal();
    }

    public void initWidgets() {
        add = findViewById(R.id.add);
        clear = findViewById(R.id.clear);
        input = findViewById(R.id.input);
        total = findViewById(R.id.total);

        configureClearButton();
        configureAddButton();

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showedMessage == false) {
                    Snackbar.make(findViewById(android.R.id.content), "Enter a positive integer", Snackbar.LENGTH_SHORT).show();
                    showedMessage = true;
                }
            }
        });
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
        input.setText("");
        Snackbar.make(findViewById(android.R.id.content), "Data cleared", Snackbar.LENGTH_SHORT).show();
        try {
            saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add() {
        String userInput = input.getText().toString();
        if (userInput.length() != 0) {
            arrayList.add(userInput);
            adapter.notifyDataSetChanged();
            calculateTotal();
            input.setText("");
            Snackbar.make(findViewById(android.R.id.content), "Added " + userInput + "!", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void showDelete(String data) {
        Snackbar.make(findViewById(android.R.id.content), "Removed " + data + "!", Snackbar.LENGTH_SHORT).show();
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
        fout.write(out.getBytes());
        fout.close();
    }

    public ArrayList<Integer> loadData() throws IOException {
        ArrayList<Integer> readValues = new ArrayList<>();
        File f = new File(filename);
        if (!f.exists()) {
            f.createNewFile();
            return readValues; //return empty arraylist
        }
        if (f.length() == 0) {
            return readValues; //return empty arraylist
        }

        FileInputStream fin = new FileInputStream(new File(filename));
        int c;
        String data = "";
        while((c = fin.read()) != -1) {
            data = data + Character.toString((char) c);
        }
        String[] splitData = data.split(" ");
        for (String i: splitData) {
            readValues.add(Integer.valueOf(i));
        }
        return readValues;
    }
}
