package com.example.davide.flatdice;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

/**
 * Created by davide on 20/02/18.
 */

public class Face2 extends Fragment {

    private View v;
    private View tv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Random r = new Random();
        int number = r.nextInt(6)+1;

        switch (number) {
            case 1:
                v = inflater.inflate(R.layout.first_face, container, false);
                tv = v.findViewById(R.id.text_view1);
                break;
            case 2:
                v = inflater.inflate(R.layout.second_face, container, false);
                tv = v.findViewById(R.id.text_view2);
                break;
            case 3:
                v = inflater.inflate(R.layout.third_face, container, false);
                tv = v.findViewById(R.id.text_view3);
                break;
            case 4:
                v = inflater.inflate(R.layout.fourth_face, container, false);;
                tv = v.findViewById(R.id.text_view4);
                break;
            case 5:
                v=inflater.inflate(R.layout.five_face, container, false);;
                tv = v.findViewById(R.id.text_view5);
                break;
            case 6:
                v=inflater.inflate(R.layout.six_face, container, false);;
                tv = v.findViewById(R.id.text_view6);
                break;
        }
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        tv.setBackgroundColor(color);

        // Inflate the layout for this fragment
        return v;
    }
}
