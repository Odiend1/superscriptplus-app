package com.example.superscript.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.superscript.LessonActivity;
import com.example.superscript.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public HomeFragment() {
        // Required empty public constructor
    }


    private Button lesson1;
    private Button lesson2;
    private Button lesson3;
    private Button lesson4;
    private Button lesson5;
    private Button lesson6;
    private Button lesson7;
    private Button lesson8;
    private Button lesson9;
    private Button lesson10;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout lessonListLayout = view.findViewById(R.id.lesson_list_layout);
        lesson1 = lessonListLayout.findViewById(R.id.lesson1);
        lesson1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLesson(1);
            }
        });
        lesson2 = lessonListLayout.findViewById(R.id.lesson2);
        lesson2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLesson(2);
            }
        });
        lesson3 = lessonListLayout.findViewById(R.id.lesson3);
        lesson3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLesson(3);
            }
        });
        lesson4 = lessonListLayout.findViewById(R.id.lesson4);
        lesson4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLesson(4);
            }
        });
        lesson5 = lessonListLayout.findViewById(R.id.lesson5);
        lesson5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLesson(5);
            }
        });
        lesson6 = lessonListLayout.findViewById(R.id.lesson6);
        lesson6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLesson(6);
            }
        });
        lesson7 = lessonListLayout.findViewById(R.id.lesson7);
        lesson7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLesson(7);
            }
        });
        lesson8 = lessonListLayout.findViewById(R.id.lesson8);
        lesson8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLesson(8);
            }
        });
        lesson9 = lessonListLayout.findViewById(R.id.lesson9);
        lesson9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLesson(9);
            }
        });
        lesson10 = lessonListLayout.findViewById(R.id.lesson10);
        lesson10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLesson(10);
            }
        });

        return view;
    }

    public void openLesson(int lessonNum){
        System.out.println("Lesson: " + String.valueOf(lessonNum));
        Intent intent = new Intent(getActivity(), LessonActivity.class);
        intent.putExtra("lesson", String.valueOf(lessonNum));
        startActivity(intent);
    }
}