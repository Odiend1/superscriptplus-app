package com.example.superscript;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ConsoleInputStream extends InputStream {

    private Scanner inputScanner;

    public void setInputScanner(Scanner inputScanner) {
        this.inputScanner = inputScanner;
    }

    @Override
    public int read() throws IOException {
        if (inputScanner != null) {
            String input = inputScanner.nextLine();
            byte[] bytes = input.getBytes();
            return bytes.length > 0 ? bytes[0] : -1;
        } else {
            return -1;
        }
    }

    // override other read methods if needed
}

