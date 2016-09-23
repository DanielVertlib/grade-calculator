package com.example.daniel.habits;

import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Daniel on 30/08/2016.
 */
public class MarkCalculations {

    public ArrayList<Double> CalculateMarks(ArrayList<Double> worthArray, ArrayList<Double> markArray, String cutoff)
    {
        ArrayList<Double> results = new ArrayList<Double>();
        Double cutoffWanted = Double.parseDouble(cutoff);
        Double examWorth = 100.0;
        Double itemWorth = 0.0;
        Double itemMark = 0.0;
        Double currentMark;

        for(int i=0;i<worthArray.size();i++)
        {
            examWorth -= worthArray.get(i);
            itemWorth += worthArray.get(i);
            itemMark += ((worthArray.get(i)/100.0) * (markArray.get(i)/100.0) * 100.0);
        }

        currentMark = (itemMark/itemWorth) * 100.0;

        Double finalCutoff = ((cutoffWanted/100) - (currentMark/100) * (itemWorth/100)) / (examWorth/100) * 100;
        results.add(currentMark);
        results.add(finalCutoff);

        return results;
    }


}
