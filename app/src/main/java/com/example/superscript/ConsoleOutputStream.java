package com.example.superscript;

import android.widget.TextView;

import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleOutputStream extends PrintStream {

    private TextView outputTextView;

    public ConsoleOutputStream(OutputStream out) {
        super(out);
    }

    public void setOutputTextView(TextView outputTextView) {
        this.outputTextView = outputTextView;
    }

    @Override
    public void write(int b) {
        if (outputTextView != null) {
            outputTextView.append(String.valueOf((char) b));
        }
    }
}