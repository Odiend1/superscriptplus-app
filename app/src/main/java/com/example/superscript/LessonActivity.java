package com.example.superscript;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class LessonActivity extends AppCompatActivity {

    private int lessonNum;
    private int pageNum = 1;
    private int pages;
    private Button next;
    private Button back;
    private Button exit;
    private ScrollView lessonView;
    private LinearLayout lessonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNum++;
                loadLesson();
            }
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pageNum > 1) {
                    pageNum--;
                    loadLesson();
                }
            }
        });

        exit = (Button) findViewById(R.id.exit_lesson);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                exitLesson();
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            lessonNum = Integer.parseInt(extras.getString("lesson"));
        }

        loadLesson();
    }

    public void loadLesson(){
        lessonView = (ScrollView) findViewById(R.id.lesson_view);
        lessonLayout = (LinearLayout) findViewById(R.id.lesson_layout);
        lessonLayout.removeAllViews();

        if(lessonNum == 1){
            if(pageNum == 1){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l1_p1_body1));
                layout(body1);
                lessonLayout.addView(body1);
            }
            else if(pageNum == 2){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l1_p2_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(prn \"Hello world!\")");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);
            }
            else{
                exitLesson();
            }
        }
        else if(lessonNum == 2){
            if(pageNum == 1){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l2_p1_body1));
                layout(body1);
                lessonLayout.addView(body1);
            }
            else if(pageNum == 2){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l2_p2_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(prn \"Hello world!\")");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l2_p2_body2));
                layout(body2);
                lessonLayout.addView(body2);

                TextView code2 = new TextView(this);
                code2.setText("(prn 2.54)");
                code2.setBackgroundColor(getColor(R.color.background_grey));
                code2.setTextColor(getColor(R.color.lime));
                layout(code2);
                lessonLayout.addView(code2);

                TextView body3 = new TextView(this);
                body3.setText(getString(R.string.l2_p2_body3));
                layout(body3);
                lessonLayout.addView(body3);
            }
            else{
                exitLesson();
            }
        }
        else{
            exitLesson();
        }
    }

    public void layout(TextView textview){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(20, 20, 20, 20);
        textview.setPadding(56, ((!textview.getText().toString().trim().startsWith("(")) ? 24 : 0), 56, ((!textview.getText().toString().trim().startsWith("(")) ? 24 : 0));
    }

    public void exitLesson(){
            Context context = this;
            Intent intent = new Intent(this, MainActivity.class);
            context.startActivity(intent);
    }
}