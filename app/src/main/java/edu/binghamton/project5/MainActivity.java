package edu.binghamton.project5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Button add, clear;
    private EditText input;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add);
        clear = findViewById(R.id.clear);
        input = findViewById(R.id.input);
        list = findViewById(R.id.list);
    }
}
