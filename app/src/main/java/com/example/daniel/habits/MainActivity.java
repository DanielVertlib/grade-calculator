package com.example.daniel.habits;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> taskArray;
    ArrayList<Double> worthArray;
    ArrayList<Double> markArray;
    ArrayList<String> itemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_window);
        InitialWindow();
    }

    private void InitialWindow()
    {
        Button start = (Button) findViewById(R.id.btnStart);

        start.setOnClickListener (new View.OnClickListener()
        {
            public void onClick(View v)
            {
                setContentView(R.layout.activity_main);
                StartMain();
            }
        });
    }

    private void StartMain()
    {
        ListView taskList = (ListView) findViewById(R.id.listTasks);
        taskArray = new ArrayList<>();
        worthArray = new ArrayList<>();
        markArray = new ArrayList<>();
        itemArray = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskArray);
        taskList.setAdapter(adapter);

        Button add = (Button) findViewById(R.id.btnAdd);
        Button calc = (Button) findViewById(R.id.btnCalc);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCourseItem();
            }
        });

        calc.setOnClickListener (new View.OnClickListener()
        {
            public void onClick(View v)
            {
                setContentView(R.layout.finalgrade_window);
                CalculateMark();
            }
        });
    }

    private void CalculateMark()
    {
        DecimalFormat df = new DecimalFormat("00.0");
        df.setRoundingMode(RoundingMode.DOWN);
        //TextView cutoff = (TextView) findViewById(R.id.txtCutoff);
        //String cutoffText = cutoff.getText().toString();
        MarkCalculations obj = new MarkCalculations();
        ArrayList<Double> results = obj.CalculateMarks(worthArray, markArray, "50");

        TextView current = (TextView) findViewById(R.id.txtCurrent);
        TextView needed = (TextView) findViewById(R.id.txtNeeded);
        String currentMark = String.format("%s%%", df.format(results.get(0)));
        String neededMark = String.format("%s%%", df.format(results.get(1)));

        current.setText(currentMark);
        needed.setText(neededMark);

        Button redo = (Button) findViewById(R.id.btnRedo);

        redo.setOnClickListener (new View.OnClickListener()
        {
            public void onClick(View v)
            {
                setContentView(R.layout.welcome_window);
                InitialWindow();
            }
        });
    }

    private void AddCourseItem()
    {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.addcourse_window, null);
        AlertDialog.Builder addWindow = new AlertDialog.Builder(this);

        // this is set the view from XML inside AlertDialog
        addWindow.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        addWindow.setCancelable(false);

        // Set up the buttons
        addWindow.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                EditText item = (EditText) alertLayout.findViewById(R.id.courseItem);
                EditText worth = (EditText) alertLayout.findViewById(R.id.markWorth);
                EditText mark = (EditText) alertLayout.findViewById(R.id.yourMark);

                String finalItem = item.getText().toString() + ":  " + worth.getText().toString() + "% out of " + mark.getText().toString() + "%";


                itemArray.add(item.getText().toString());
                worthArray.add(Double.parseDouble(worth.getText().toString()));
                markArray.add(Double.parseDouble(mark.getText().toString()));

                taskArray.add(finalItem);
                adapter.notifyDataSetChanged();
            }
        });
        addWindow.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = addWindow.create();
        addWindow.show();
    }
}
