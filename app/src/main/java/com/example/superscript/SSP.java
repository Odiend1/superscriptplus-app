package com.example.superscript;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class SSP {

  static HashMap<String, String> vars = new HashMap<String, String>();
  static Set<Character> numeric = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-');
  static Set<String> keywords = Set.of("true", "false", "nil");
  static TextView output;
  static Activity activity;

  static class Function {
    String function = "";
    ArrayList<String> args = new ArrayList<String>();
    String result;
  }

  public static boolean validData(String data) {
    //Removes line separator
    data = data.replace(System.lineSeparator(), "");

    boolean inString = false;
    boolean inFunction = false;

    int pNum = 0;
    for (int i = 0; i < data.length(); i++) {
      // Current character
      char inputChar = data.charAt(i);
      if (inputChar == '(' && !inString) {
        if (!inFunction) {
          inFunction = true;
          pNum = 0;
        }
        pNum++;
      } else if (inputChar == ')' && inFunction) {
        pNum--;
        if (pNum == 0) {
          inFunction = false;
        }
      } else if ((inputChar != ' ' && inputChar != ')') || inString) {
        // If quotations found, opens or closes string.
        if (inputChar == '"' && !inString) {
          inString = true;
        } else if (inputChar == '"' && inString) {
          inString = false;
        } else if (!inString && !numeric.contains(inputChar) && !inFunction) {
          return false;
        }
      }
      // If space in the middle of arguments and not in string, starts reading new
      // argument
      else if (inputChar == ' ' && !inFunction) {
        return true;
      } else if (inputChar == ')' && !inFunction) {
        return true;
      } else if (!(inputChar == ' ' && !inFunction && !inString) && !inString && !numeric.contains(inputChar)
          && !inFunction) {
        return false;
      }
    }
    return true;
  }

  public static ArrayList<Function> compile(String code) {
    // Removes line separator
    code = code.replace(System.lineSeparator(), "");

    // Variable for next expected character, used to find errors
    String expected = "(";

    // Variable for functions that are compiled and will be executed in execute()
    ArrayList<Function> compiled = new ArrayList<Function>();

    // Index of current function in ArrayList compiled
    int fnIndex = 0;

    // Whether or not read argument is currently part of a string or function
    boolean inString = false;
    boolean inFunction = false;
    boolean infix = false;

    // Arg that is being formed character by character
    String addedArg = "";

    // Adds initial function that is compiled
    compiled.add(new Function());

    // Number of nested parentheses/brackets
    int pNum = 0;
    int bNum = 0;

    // Prevents NullPointerException
    if (code == null)
      return compiled;

    // Iterates through every character of the code
    for (int i = 0; i < code.length(); i++) {
      // Current character
      char inputChar = code.charAt(i);

      while (!inString && inputChar == ' ' && (code.length() > 1 && i > 0 && code.length() > i + 1) ? code.charAt(i + 1) == ' ' : false) {
        i += 1;
        inputChar = ' ';
      }


      // If opening parentheses is detected, starts looking for function to read
      if (inputChar == '(' && expected == "(") {
        expected = "fn";
      }
      else if(expected == "(" && inputChar != ' ' && inputChar != ';'){
        String invalid = "";
        while(i < code.length() && code.charAt(i) != ' ' && code.charAt(i) != '(' && code.charAt(i) != ')'){
          invalid = invalid + code.charAt(i);
          i++;
        }
          output.setText(output.getText().toString() + "\n" + "Error: Invalid symbol " + invalid);
          return new ArrayList<Function>();
      }

      // If closing parentheses is detected moves on to next function for parsing
      else if (inputChar == ')' && expected == ")") {
        fnIndex++;
        expected = "(";
        compiled.add(new Function());
      }

      // If function is expected, reads it and adds it to Function object in list
      else if (expected == "fn") {
        if (inputChar != ' ' && inputChar != ')') {
          // Spaces separate function and arguments. If no space, adds current character
          // to function name.
          compiled.get(fnIndex).function = compiled.get(fnIndex).function + inputChar;
        } else if (inputChar == ' ') {
          // Next, arguments are expected
          expected = "args";
          addedArg = "";
        } else {
          fnIndex++;
          expected = "(";
          compiled.add(new Function());
        }
      }

      // If arguments are expected
      else if (expected == "args") {
        // If a space or closing parentheses is detected while not currently reading a
        // string, doesn't add character to arguments

        // Detecting nested functions
        if ((inputChar == '(' || inputChar == '{') && !inString) {
          if (!inFunction) {
            inFunction = true;
            pNum = 0;
          }
          if(inputChar == '{') {infix = true; addedArg += "(infix "; bNum++;}
          else addedArg += inputChar;
          pNum++;
        } else if ((inputChar == ')' || inputChar == '}') && inFunction && !inString) {
          pNum--;
          addedArg += ")";
          if (pNum == 0) {
            inFunction = false;
          }
          if(infix){
            bNum--;
            if(bNum == 0){
              infix = false;
            }
          }
          else if(inputChar == '}'){
            output.setText(output.getText().toString() + "\n" + "Error: Unexpected symbol }");
            return new ArrayList<Function>();
          }
        } else if (inputChar == ' ' && !inFunction && !inString) {
          compiled.get(fnIndex).args.add(addedArg);
          addedArg = "";
        }

        else if ((inputChar != ' ' && inputChar != ')') || inString) {
          // If quotations found, opens or closes string.
          if (inputChar == '"' && !inString) {
            inString = true;
            addedArg += inputChar;
          } else if (inputChar == '"' && inString) {
            inString = false;
            addedArg += inputChar;
          }

          // If no quotations detected, simply adds character to argument that is being
          // read
          else if (inString) {
            addedArg += inputChar;
          } else if (numeric.contains(inputChar) || inFunction) {
            addedArg += inputChar;
          } else {
            addedArg += inputChar;
          }
        }
        // If space in the middle of arguments and not in string, starts reading new
        // argument
        else if (inputChar == ' ' && !inFunction) {
          compiled.get(fnIndex).args.add(addedArg);
          addedArg = "";
        } else if (inputChar == ')' && !inFunction) {
          if (addedArg != "") compiled.get(fnIndex).args.add(addedArg);
          expected = "(";
          addedArg = "";
          fnIndex++;
          compiled.add(new Function());
        } else if (numeric.contains(inputChar) || inFunction) {
          addedArg += inputChar;
        } else {
          addedArg += inputChar;
        }
      }
    }

    if (expected != "(" || pNum > 0) {
      output.setText(output.getText().toString() + "\n" + "Error: Expected end of function");
      return new ArrayList<Function>();
    }
    else if(bNum > 0){
      output.setText(output.getText().toString() + "\n" + "Error: Expected end of infix expression");
    }

    if(compiled.get(compiled.size() - 1).function == ""){
      compiled.remove(compiled.size() - 1);
    }

    return compiled;
  }

  public static String operate(String operation, ArrayList<String> args){
    // Final number that will be returned
    float finalNum;

        finalNum = 0;
        if(operation.equals("*")) finalNum = 1;
        try{
          if(operation.equals("-") || operation.equals("/")) finalNum = Float.parseFloat(args.get(0));
        }
        catch(NumberFormatException e){
          output.setText(output.getText().toString() + "\n" + "Error: Function " + operation + " cannot convert non-numeric value to number");
        }

        if (args.size() > 0 && (operation.equals("-") || operation.equals("/")) ? args.size() > 1 : true) {
          for (int i = (operation.equals("+") || operation.equals("*")) ? 0 : 1; i < args.size(); i++) {
            try {
              if (Float.parseFloat(args.get(i)) == 0f && operation.equals("/")) {
              output.setText(output.getText().toString() + "\n" + "Error: Cannot divide by zero");
              return null;
            }
              switch(operation){
                case "+":
                  finalNum += Float.parseFloat(args.get(i));
                  break;
                case "-":
                  finalNum -= Float.parseFloat(args.get(i));
                  break;
                case "*":
                  finalNum *= Float.parseFloat(args.get(i));
                  break;
                case "/":
                  finalNum /= Float.parseFloat(args.get(i));
                  break;
              }
            } catch (NumberFormatException e) {
              output.setText(output.getText().toString() + "\n" + "Error: Function " + operation + " cannot convert non-numeric value to number");
              return null;
            }
          }
        } else {
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + operation + ".");
          return null;
        }
      
    return (String.valueOf(finalNum).endsWith(".0")
            ? String.valueOf(finalNum).substring(0, String.valueOf(finalNum).length() - 2)
            : String.valueOf(finalNum));
  }

  public static ArrayList<Function> execute(ArrayList<Function> execs){

    ArrayList<Function> results = new ArrayList<Function>();
    for (int execIndex = 0; execIndex < execs.size(); execIndex++) {
      Function exec = execs.get(execIndex);
      Function res = new Function();

      for (int i = 0; i < exec.args.size(); i++) {
        String arg = exec.args.get(i);
        if (((arg.startsWith("(") && arg.endsWith(")")) && ((exec.function.equals("if")) ? i == 0 : true))
            && !exec.function.equals("do") && !exec.function.equals("while") && !exec.function.equals("repeat")) {
          ArrayList<Function> nestRes = execute(compile(arg));
          if (nestRes.size() > 0)
            exec.args.set(i, nestRes.get(0).result);
        }
        if (arg.startsWith("\"") && arg.endsWith("\"")) {
          exec.args.set(i, arg.substring(1, arg.length() - 1));
        } else if (vars.containsKey(arg) && !exec.function.equals("=") && !exec.function.equals("while")) {
          exec.args.set(i, vars.get(arg));
        } else if (!exec.function.equals("=") && !exec.function.equals("while") && !validData(arg)
            && !keywords.contains(arg) && !exec.function.equals("infix")) {
          output.setText(output.getText().toString() + "\n" + "Error: Invalid symbol " + arg);
          return new ArrayList<Function>();
        }
      }

      // print function
      if (exec.function.equals("prn") || exec.function.equals("print")) {
        if (exec.args.size() == 1) {
          output.setText(output.getText().toString() + exec.args.get(0));
          output.setText(output.getText().toString() + "\n" + "");
          results.add(res);
        } else {
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // pr function
      else if (exec.function.equals("pr")) {
        if (exec.args.size() == 1) {
          output.setText(output.getText().toString() + exec.args.get(0));
          results.add(res);
        } else {
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // + function
      else if (exec.function.equals("+")) {
        res.result = operate(exec.function, exec.args);
        if(res.result == null) return new ArrayList<Function>();
        results.add(res);
      }

      // - function
      else if (exec.function.equals("-")) {
          res.result = operate(exec.function, exec.args);
        if(res.result == null) return new ArrayList<Function>();
        results.add(res);
      }

      // * function
      else if (exec.function.equals("*")) {
        res.result = operate(exec.function, exec.args);
        if(res.result == null) return new ArrayList<Function>();
        results.add(res);
      }

      // / function
      else if (exec.function.equals("/")) {
        res.result = operate(exec.function, exec.args);
        if(res.result == null) return new ArrayList<Function>();
        results.add(res);
      }

      // = function
      else if (exec.function.equals("=")) {
        if (exec.args.size() == 2) {
          if (!numeric.contains(exec.args.get(0).charAt(0)) && !keywords.contains(exec.args.get(0))) {
            if (vars.get(exec.args.get(0)) == null)
              vars.put(exec.args.get(0), exec.args.get(1));
            else
              vars.replace(exec.args.get(0), exec.args.get(1));
            results.add(res);
          } else {
            output.setText(output.getText().toString() + "\n" + "Error: Cannot assign value to symbol " + exec.args.get(0));
            return new ArrayList<Function>();
          }
        } else {
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          output.setText(output.getText().toString() + "\n" + exec.args.get(0));
          output.setText(output.getText().toString() + "\n" + exec.args.get(1));
          output.setText(output.getText().toString() + "\n" + exec.args.get(2));
          return new ArrayList<Function>();
        }
      }

      // exit function
      else if (exec.function.equals("exit")) {
        return new ArrayList<Function>();
      }

      // is function
      else if (exec.function.equals("is")) {
        if (exec.args.size() == 2) {
          if (exec.args.get(0).equals(exec.args.get(1))) {
            res.result = "true";
          } else {
            res.result = "false";
          }
          results.add(res);
        }
      }

      // if function
      else if (exec.function.equals("if")) {
        if (exec.args.size() == 2 || exec.args.size() == 3) {
          if (exec.args.get(0).equals("true")) {
            execute(compile(exec.args.get(1)));
          } else {
            if (exec.args.size() > 2)
              execute(compile(exec.args.get(2)));
          }
        }
      }

      // while function
      else if (exec.function.equals("while")) {
        if (exec.args.size() == 2) {
          while ((!vars.containsKey(exec.args.get(0))) ? ((!exec.args.get(0).equals("true") && !exec.args.get(0).equals("false")) ?execute(compile(exec.args.get(0))).get(0).result.equals("true") : exec.args.get(0).equals("true"))
                  : vars.get(exec.args.get(0)).equals("true")) {
            execute(compile(exec.args.get(1)));
          }
        }
      }

      // do function
      else if (exec.function.equals("do")) {
        exec.args.forEach((arg) -> {
          execute(compile(arg));
        });
      }

      // < function
      else if (exec.function.equals("<")) {
        if (exec.args.size() == 2) {
          if (Float.parseFloat(exec.args.get(0)) < Float.parseFloat(exec.args.get(1))) {
            res.result = "true";
          } else {
            res.result = "false";
          }
          results.add(res);
        }
      }

      // > function
      else if (exec.function.equals(">")) {
        if (exec.args.size() == 2) {
          if (Float.parseFloat(exec.args.get(0)) > Float.parseFloat(exec.args.get(1))) {
            res.result = "true";
          } else {
            res.result = "false";
          }
          results.add(res);
        }
      }

      // read function
      else if (exec.function.equals("read")) {
        if (exec.args.size() <= 1) {
          output.setText(output.getText().toString() + ((exec.args.size() == 1) ? exec.args.get(0) : ""));
          activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              AlertDialog.Builder builder = new AlertDialog.Builder(activity);
              builder.setTitle(((exec.args.size() == 1) ? exec.args.get(0) : ""));
              EditText inputEditText = new EditText(activity);
              builder.setView(inputEditText);
              builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  res.result = inputEditText.getText().toString();
                }
              });
              builder.setCancelable(false);
              builder.show();
            }
          });
          while(res.result == null){

          }
          output.setText(output.getText().toString() + res.result + "\n");
          results.add(res);
        } else {
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // repeat function
      else if (exec.function.equals("repeat")) {
        if (exec.args.size() == 2) {
          try {
            for (int i = 0; i < Float.parseFloat(exec.args.get(0)); i++) {
              execute(compile(exec.args.get(1)));
            }
          } catch (NumberFormatException e) {
            output.setText(output.getText().toString() + "\n" + "Error: Function " + exec.function + " cannot convert non-numeric value to number");
          }
        }
      }

      // concat function
      else if (exec.function.equals("concat")) {
        String finalStr = "";

        if (exec.args.size() > 0) {
          for (int i = 0; i < exec.args.size(); i++) {
            finalStr += exec.args.get(i);
          }
          res.result = finalStr;
          results.add(res);
        } else {
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // lower function
      else if (exec.function.equals("lower")) {
        if (exec.args.size() == 1) {
          res.result = exec.args.get(0).toLowerCase();
          results.add(res);
        } else {
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // upper function
      else if (exec.function.equals("upper")) {
        if (exec.args.size() == 1) {
          res.result = exec.args.get(0).toUpperCase();
          results.add(res);
        } else {
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // sleep function
      else if (exec.function.equals("sleep")) {
        if (exec.args.size() == 1) {
          try {
            Thread.sleep(Long.parseLong(exec.args.get(0)));
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          catch(NumberFormatException e){
            output.setText(output.getText().toString() + "\n" + "Error: Function " + exec.function + " cannot convert non-numeric value to number");
            return new ArrayList<Function>();
          }
        } else {
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // infix expressions
      else if (exec.function.equals("infix")){
        String ex = String.join("", exec.args);
        ex = ex.replaceAll("\\s","");
        for(int i = 0; i < ex.length(); i++){
          Character infixChar = ex.charAt(i);
          if(infixChar.equals('*') || infixChar.equals('/')){
            String num1 = "";
            String num2 = "";
            int j = i - 1;
            while(j >= 0 && numeric.contains(ex.charAt(j))){
              num1 += ex.charAt(j);
              j--;
            }
            num1 = new StringBuilder(num1).reverse().toString();
            int rStart = (j != 0) ? j + 1 : j;
            j = i + 1;
            while(j < ex.length() && numeric.contains(ex.charAt(j))){
              num2 += ex.charAt(j);
              j++;
            }
            int rEnd = j;
            if(operate(String.valueOf(infixChar), new ArrayList<String>(Arrays.asList(num1, num2))) == null) return new ArrayList<Function>();
            ex = ex.substring(0, rStart) + String.valueOf(operate(String.valueOf(infixChar), new ArrayList<String>(Arrays.asList(num1, num2)))) + ex.substring(rEnd);
            i = rStart;
          }
        }
        for(int i = 0; i < ex.length(); i++){
          Character infixChar = ex.charAt(i);
          if(infixChar.equals('+') || infixChar.equals('-')){
            String num1 = "";
            String num2 = "";
            int j = i - 1;
            while(j >= 0 && numeric.contains(ex.charAt(j))){
              num1 += ex.charAt(j);
              j--;
            }
            num1 = new StringBuilder(num1).reverse().toString();
            int rStart = (j != 0) ? j + 1 : j;
            j = i + 1;
            while(j < ex.length() && numeric.contains(ex.charAt(j))){
              num2 += ex.charAt(j);
              j++;
            }
            int rEnd = j;
            if(operate(String.valueOf(infixChar), new ArrayList<String>(Arrays.asList(num1, num2))) == null) return new ArrayList<Function>();
            ex = ex.substring(0, rStart) + String.valueOf(operate(String.valueOf(infixChar), new ArrayList<String>(Arrays.asList(num1, num2)))) + ex.substring(rEnd);
            i = rStart;
          }
        }
        try{
          Float.parseFloat(ex);
        }
        catch(NumberFormatException e){
          if(!(ex.equals("true") || ex.equals("false"))){
          output.setText(output.getText().toString() + "\n" + "Error: Infix expression cannot convert non-numeric value to number");
            return new ArrayList<Function>();
          }
        }
        res.result = ex;
        results.add(res);
      }

      // not function
      else if (exec.function.equals("not")){
        if(exec.args.size() == 1){
          if(exec.args.get(0).equals("true")){
            res.result = "false";
          }
          else if(exec.args.get(0).equals("false")){
            res.result = "true";
          }
          else{
            output.setText(output.getText().toString() + "\n" + "Error: Function " + exec.function + " cannot perform logical operation on non-boolean data type.");
            return new ArrayList<Function>();
          }
          results.add(res);
        }
        else{
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // and function
      else if (exec.function.equals("and")){
        if(exec.args.size() > 1){
          Boolean result = true;
          for(Integer i = 0; i < exec.args.size(); i++){
            if(exec.args.get(i).equals("true") || exec.args.get(i).equals("false")){
              result = result && Boolean.valueOf(exec.args.get(i));
            }
            else{
              output.setText(output.getText().toString() + "\n" + "Error: Function " + exec.function + " cannot perform logical operation on non-boolean data type.");
              return new ArrayList<Function>();
            }
          }
          res.result = String.valueOf(result);
          results.add(res);
        }
        else{
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // or function
      else if (exec.function.equals("or")){
        if(exec.args.size() > 1){
          Boolean result = false;
          for(Integer i = 0; i < exec.args.size(); i++){
            if(exec.args.get(i).equals("true") || exec.args.get(i).equals("false")){
              result = result || Boolean.valueOf(exec.args.get(i));
            }
            else{
              output.setText(output.getText().toString() + "\n" + "Error: Function " + exec.function + " cannot perform logical operation on non-boolean data type.");
              return new ArrayList<Function>();
            }
          }
          res.result = String.valueOf(result);
          results.add(res);
        }
        else{
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // rand function
      else if (exec.function.equals("rand")){
        if(exec.args.size() == 2){
          try{
            res.result = String.valueOf((int)(Math.random() * Float.parseFloat(exec.args.get(1)) + Float.parseFloat(exec.args.get(0))));
          }
          catch(NumberFormatException e){
            output.setText(output.getText().toString() + "\n" + "Error: Function " + exec.function + " cannot convert non-numeric value to number");
          }
          results.add(res);
        }
        else{
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // length function
      else if (exec.function.equals("length")){
        if(exec.args.size() == 1){
          res.result = String.valueOf(exec.args.get(0).length());
          results.add(res);
        }
      }

      // char_at function
      else if (exec.function.equals("char_at")){
        if(exec.args.size() == 2){
          try{
            res.result = String.valueOf(exec.args.get(0).charAt(Integer.parseInt(exec.args.get(1))));
          }
          catch(StringIndexOutOfBoundsException e){
            output.setText(output.getText().toString() + "\n" + "Error: String index out of bounds");
            return new ArrayList<Function>();
          }
          catch(NumberFormatException e){
            output.setText(output.getText().toString() + "\n" + "Error: Function " + exec.function + " cannot convert non-numeric value to number");
            return new ArrayList<Function>();
          }
          results.add(res);
        }
        else{
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      // substr function
      else if (exec.function.equals("substr")){
        if(exec.args.size() == 2 || exec.args.size() == 3){
          try{
            if(exec.args.size() == 2) res.result = exec.args.get(0).substring(Integer.parseInt(exec.args.get(1)));
            else res.result = exec.args.get(0).substring(Integer.parseInt(exec.args.get(1)), Integer.parseInt(exec.args.get(2)));
          }
          catch(StringIndexOutOfBoundsException e){
            output.setText(output.getText().toString() + "\n" + "Error: String index out of bounds");
            return new ArrayList<Function>();
          }
          catch(NumberFormatException e){
            output.setText(output.getText().toString() + "\n" + "Error: Function " + exec.function + " cannot convert non-numeric value to number");
            return new ArrayList<Function>();
          }
          results.add(res);
        }
        else{
          output.setText(output.getText().toString() + "\n" + "Error: Invalid number of arguments given for function " + exec.function + ".");
          return new ArrayList<Function>();
        }
      }

      else {
        output.setText(output.getText().toString() + "\n" + "Error: Undefined function " + exec.function);
        return new ArrayList<Function>();
      }

    }
    return results;
  }

  public static void main(String[] args) {
    /*Scanner scanner = new Scanner(System.in);
    output.setText(output.getText().toString() + "\n" + "");
    while (true) {
      // Create a Scanner object
      output.setText(output.getText().toString() + "> ");

      // Get inputted code segment
      String code = scanner.nextLine();
      if (code != "(exit)")
        execute(compile(code));
      else
        scanner.close();
    }*/
  }
}