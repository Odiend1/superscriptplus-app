package com.example.superscript;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public HomeFragment() {
        // Required empty public constructor
    }


    private Button lesson1;

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

        return view;
    }

    public void openLesson(int lessonNum){
        Intent intent = new Intent(getActivity(), LessonActivity.class);
        intent.putExtra("lesson", String.valueOf(lessonNum));
        startActivity(intent);
    }
}