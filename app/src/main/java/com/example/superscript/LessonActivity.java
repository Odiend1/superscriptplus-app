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

import org.w3c.dom.Text;

public class LessonActivity extends AppCompatActivity {

    private int lessonNum;
    private int pageNum = 1;
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
        lessonView = findViewById(R.id.lesson_view);
        lessonLayout = findViewById(R.id.lesson_layout);
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
        else if(lessonNum == 3){
            if(pageNum == 1){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l3_p1_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(+ 1 1 1)");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l3_p1_body2));
                layout(body2);
                lessonLayout.addView(body2);

                TextView code2 = new TextView(this);
                code2.setText("(- 6 5)");
                code2.setBackgroundColor(getColor(R.color.background_grey));
                code2.setTextColor(getColor(R.color.lime));
                layout(code2);
                lessonLayout.addView(code2);

                TextView body3 = new TextView(this);
                body3.setText(getString(R.string.l3_p1_body3));
                layout(body3);
                lessonLayout.addView(body3);

                TextView code3 = new TextView(this);
                code3.setText("(* 4 3)");
                code3.setBackgroundColor(getColor(R.color.background_grey));
                code3.setTextColor(getColor(R.color.lime));
                layout(code3);
                lessonLayout.addView(code3);

                TextView body4 = new TextView(this);
                body4.setText(getString(R.string.l3_p1_body4));
                layout(body4);
                lessonLayout.addView(body4);

                TextView code4 = new TextView(this);
                code4.setText("(/ 1 2)");
                code4.setBackgroundColor(getColor(R.color.background_grey));
                code4.setTextColor(getColor(R.color.lime));
                layout(code4);
                lessonLayout.addView(code4);
            }
            else if(pageNum == 2){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l3_p2_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(prn (+ 4 1))");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l3_p2_body2));
                layout(body2);
                lessonLayout.addView(body2);
            }
            else{
                exitLesson();
            }
        }
        else if(lessonNum == 4){
            if(pageNum == 1){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l4_p1_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(= magic_number 42)");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);
            }
            else if(pageNum == 2){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l4_p2_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(prn magic_number)");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l4_p2_body2));
                layout(body2);
                lessonLayout.addView(body2);

                TextView code2 = new TextView(this);
                code2.setText("(prn (+ magic_number 3))\n(prn magic_number)");
                code2.setBackgroundColor(getColor(R.color.background_grey));
                code2.setTextColor(getColor(R.color.lime));
                layout(code2);
                lessonLayout.addView(code2);

                TextView body3 = new TextView(this);
                body3.setText(getString(R.string.l4_p2_body3));
                layout(body3);
                lessonLayout.addView(body3);

                TextView code3 = new TextView(this);
                code3.setText("(= magic_number (+ magic_number 1))\n(prn magic_number)");
                code3.setBackgroundColor(getColor(R.color.background_grey));
                code3.setTextColor(getColor(R.color.lime));
                layout(code3);
                lessonLayout.addView(code3);

                TextView body4 = new TextView(this);
                body4.setText(getString(R.string.l4_p2_body4));
                layout(body4);
                lessonLayout.addView(body4);
            }
            else{
                exitLesson();
            }
        }
        else if(lessonNum == 5){
            if(pageNum == 1){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l5_p1_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(= input (read)) (prn input)");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l5_p1_body2));
                layout(body2);
                lessonLayout.addView(body2);

                TextView code2 = new TextView(this);
                code2.setText("(= name (read “What is your name?”)) (pr “Hello, “) (pr name) (prn “!”)");
                code2.setBackgroundColor(getColor(R.color.background_grey));
                code2.setTextColor(getColor(R.color.lime));
                layout(code2);
                lessonLayout.addView(code2);

                TextView body3 = new TextView(this);
                body3.setText(getString(R.string.l5_p1_body3));
                layout(body3);
                lessonLayout.addView(body3);

                TextView code3 = new TextView(this);
                code3.setText("(prn “Welcome to the program!”)\n(sleep 3000)\n(= name (read “What is your name?”)) (pr “Hello, “) (pr name) (prn “!”)");
                code3.setBackgroundColor(getColor(R.color.background_grey));
                code3.setTextColor(getColor(R.color.lime));
                layout(code3);
                lessonLayout.addView(code3);
            }
            else if(pageNum == 2){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l5_p2_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(= num1 (read “Enter one number to add.”)) (= num2 (read “Enter another number to add.”))(pr “The sum of those numbers is: ”) (prn (+ num1 num2))");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l5_p2_body2));
                layout(body2);
                lessonLayout.addView(body2);

                TextView code2 = new TextView(this);
                code2.setText("(= faces (read \"How many faces should the dice have? \"))\n(pr \"The dice rolled... \") (sleep 500) (prn (rand 1 faces))\n");
                code2.setBackgroundColor(getColor(R.color.background_grey));
                code2.setTextColor(getColor(R.color.lime));
                layout(code2);
                lessonLayout.addView(code2);

                TextView body3 = new TextView(this);
                body3.setText(getString(R.string.l5_p2_body3));
                layout(body3);
                lessonLayout.addView(body3);
            }
            else{
                exitLesson();
            }
        }
        else if(lessonNum == 6){
            if(pageNum == 1){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l6_p1_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(is 1 1)");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l6_p1_body2));
                layout(body2);
                lessonLayout.addView(body2);

                TextView code2 = new TextView(this);
                code2.setText("(is 1 “Hi!”)");
                code2.setBackgroundColor(getColor(R.color.background_grey));
                code2.setTextColor(getColor(R.color.lime));
                layout(code2);
                lessonLayout.addView(code2);

                TextView body3 = new TextView(this);
                body3.setText(getString(R.string.l6_p1_body3));
                layout(body3);
                lessonLayout.addView(body3);

                TextView code3 = new TextView(this);
                code3.setText("(= number 3)\n(if (is number 3) (prn “Equal!”) (prn “Not equal.”))");
                code3.setBackgroundColor(getColor(R.color.background_grey));
                code3.setTextColor(getColor(R.color.lime));
                layout(code3);
                lessonLayout.addView(code3);

                TextView body4 = new TextView(this);
                body4.setText(getString(R.string.l6_p1_body4));
                layout(body4);
                lessonLayout.addView(body4);

                TextView code4 = new TextView(this);
                code4.setText("(= frown “:(”)\n(if (is frown “:(“) (prn “Why are you frowning?”))");
                code4.setBackgroundColor(getColor(R.color.background_grey));
                code4.setTextColor(getColor(R.color.lime));
                layout(code4);
                lessonLayout.addView(code4);
            }
            else if(pageNum == 2){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l6_p2_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(= num1 100)\n(= num2 3)\n(if (> num1 num2) (prn “The first number is bigger!”) (prn “The second number is bigger!”))");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l6_p2_body2));
                layout(body2);
                lessonLayout.addView(body2);

                TextView code2 = new TextView(this);
                code2.setText("(= num1 100)\n(= num2 3)\n(if (< num1 num2) (prn “The first number is smaller!”) (prn “The second number is smaller!”))");
                code2.setBackgroundColor(getColor(R.color.background_grey));
                code2.setTextColor(getColor(R.color.lime));
                layout(code2);
                lessonLayout.addView(code2);

                TextView body3 = new TextView(this);
                body3.setText(getString(R.string.l6_p2_body3));
                layout(body3);
                lessonLayout.addView(body3);

                TextView code3 = new TextView(this);
                code3.setText("(= num1 100)\n(= num2 3)\n(if (< num2 num1) (prn “The first number is smaller!”) (prn “The second number is smaller!”))");
                code3.setBackgroundColor(getColor(R.color.background_grey));
                code3.setTextColor(getColor(R.color.lime));
                layout(code3);
                lessonLayout.addView(code3);
            }
            else if(pageNum == 3){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l6_p3_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(= score 0)\n(if (> score 100) \n(do\n\t\t(pr “You win! Score: ”)\n\t\t(prn score)\n\t)\n)\n");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l6_p3_body2));
                layout(body2);
                lessonLayout.addView(body2);

                TextView code2 = new TextView(this);
                code2.setText("(= score 0)\n(if (> score 100) \n(do\n\t\t(pr “You win! Score: ”)\n\t\t(prn score)\n\t)\n(if (< score 0) \n(prn “Game over…”)\n)\n)\n");
                code2.setBackgroundColor(getColor(R.color.background_grey));
                code2.setTextColor(getColor(R.color.lime));
                layout(code2);
                lessonLayout.addView(code2);

                TextView body3 = new TextView(this);
                body3.setText(getString(R.string.l6_p3_body3));
                layout(body3);
                lessonLayout.addView(body3);

                TextView code3 = new TextView(this);
                code3.setText("(= score 0)\n(if (> score 100) \n(do\n\t\t(pr “You win! Score: ”)\n\t\t(prn score)\n\t)\n(if (< score 0) \n(prn “Game over…”)\n(do (pr “Score: “) (prn score))\n)\n)\n");
                code3.setBackgroundColor(getColor(R.color.background_grey));
                code3.setTextColor(getColor(R.color.lime));
                layout(code3);
                lessonLayout.addView(code3);

                TextView body4 = new TextView(this);
                body4.setText(getString(R.string.l6_p3_body4));
                layout(body4);
                lessonLayout.addView(body4);
            }
            else{
                exitLesson();
            }
        }
        else if(lessonNum == 7){
            if(pageNum == 1){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l7_p1_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(if (not (is 5 7)) (prn “5 is not 7!”))");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l7_p1_body2));
                layout(body2);
                lessonLayout.addView(body2);

                TextView code2 = new TextView(this);
                code2.setText("(and (is 5 5) (> 6 3))\n(and (< 3 4) (is (+ 6 3) 9) true)\n\n(and (is 1 1) (is 4 3))\n(and (true true false true))");
                code2.setBackgroundColor(getColor(R.color.background_grey));
                code2.setTextColor(getColor(R.color.lime));
                layout(code2);
                lessonLayout.addView(code2);

                TextView body3 = new TextView(this);
                body3.setText(getString(R.string.l7_p1_body3));
                layout(body3);
                lessonLayout.addView(body3);

                TextView code3 = new TextView(this);
                code3.setText("(or (is 6 5) (is 0 0))\n(or false false true)\n\n(or (is 6 5) (is 0 1))\n(or false false)");
                code3.setBackgroundColor(getColor(R.color.background_grey));
                code3.setTextColor(getColor(R.color.lime));
                layout(code3);
                lessonLayout.addView(code3);

                TextView body4 = new TextView(this);
                body4.setText(getString(R.string.l7_p1_body4));
                layout(body4);
                lessonLayout.addView(body4);
            }
            else{
                exitLesson();
            }
        }
        else if(lessonNum == 8){
            if(pageNum == 1){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l8_p1_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(= hello_world “Hello world!”) (pr (char_at hello_world 0)) (prn (char_at hello_world 1))");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l8_p1_body2));
                layout(body2);
                lessonLayout.addView(body2);

                TextView code2 = new TextView(this);
                code2.setText("(prn (substr “Hello world!” 6 11))");
                code2.setBackgroundColor(getColor(R.color.background_grey));
                code2.setTextColor(getColor(R.color.lime));
                layout(code2);
                lessonLayout.addView(code2);

                TextView body3 = new TextView(this);
                body3.setText(getString(R.string.l8_p1_body3));
                layout(body3);
                lessonLayout.addView(body3);

                TextView code3 = new TextView(this);
                code3.setText("(= text (read “Enter some text”)) (= text (substring 0 (length text)))");
                code3.setBackgroundColor(getColor(R.color.background_grey));
                code3.setTextColor(getColor(R.color.lime));
                layout(code3);
                lessonLayout.addView(code3);
            }
            else if(pageNum == 2){
                TextView body1 = new TextView(this);
                body1.setText(getString(R.string.l8_p2_body1));
                layout(body1);
                lessonLayout.addView(body1);

                TextView code1 = new TextView(this);
                code1.setText("(if (is (lower (read “Yes or no?”)) “yes”) (prn “You said yes!”))");
                code1.setBackgroundColor(getColor(R.color.background_grey));
                code1.setTextColor(getColor(R.color.lime));
                layout(code1);
                lessonLayout.addView(code1);

                TextView body2 = new TextView(this);
                body2.setText(getString(R.string.l8_p2_body2));
                layout(body2);
                lessonLayout.addView(body2);

                TextView code2 = new TextView(this);
                code2.setText("(= text “Enter some text”) (= times “Would you like to print this text repeated two or three times?”)\n(if (or (is times 2) (is (lower times) “two”)) \n(prn (concat text text))\n(if (or (is times 3) (is (lower times) “three”))\n\t(prn (concat text text text))\n(prn “Invalid number of repetitions”)\n))\n");
                code2.setBackgroundColor(getColor(R.color.background_grey));
                code2.setTextColor(getColor(R.color.lime));
                layout(code2);
                lessonLayout.addView(code2);

                TextView body3 = new TextView(this);
                body3.setText(getString(R.string.l8_p2_body3));
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