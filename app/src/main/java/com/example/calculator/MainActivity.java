package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView solution, result;
    MaterialButton ButtonC, ButtonOpen,ButtonClose;
    MaterialButton ButtonDivide,ButtonMultiply, ButtonAdd, ButtonSub,ButtonEquals;
    MaterialButton Button1,Button2,Button3, Button4,Button5,Button6, Button7,Button8,Button9,Button0;
    MaterialButton ButtonDot,ButtonAc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.resultTest);
        solution = findViewById(R.id.solutionText);

        assignId(ButtonC,R.id.C_button);
        assignId(ButtonAc,R.id.AC_button);
        assignId(ButtonOpen,R.id.open_bracket_button);
        assignId(ButtonClose,R.id.closed_bracket_button);
        assignId(ButtonDivide,R.id.divide_button);
        assignId(ButtonMultiply,R.id.multiply_button);
        assignId(ButtonAdd,R.id.plus_button);
        assignId(ButtonSub,R.id.minus_button);
        assignId(ButtonEquals,R.id.equals_button);
        assignId(Button1,R.id.one_button);
        assignId(Button2,R.id.two_button);
        assignId(Button3,R.id.three_button);
        assignId(Button4,R.id.four_button);
        assignId(Button5,R.id.five_button);
        assignId(Button6,R.id.six_button);
        assignId(Button7,R.id.seven_button);
        assignId(Button8,R.id.eight_button);
        assignId(Button9,R.id.nine_button);
        assignId(Button0,R.id.zero_button);
        assignId(ButtonDot,R.id.dot_button);

    }

    void assignId(MaterialButton btn,int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    String dataToCalculate;


    @Override
    public void onClick(View v) {
        ArrayList<String> symbols = new ArrayList<>();
        symbols.add("+");
        symbols.add("-");
        symbols.add("*");
        symbols.add("/");
       MaterialButton button = (MaterialButton) v;
       String ButtonText = button.getText().toString();
       String dataToCalculate = result.getText().toString();

       if(ButtonText.equals("AC")){
           result.setText("");
           solution.setText("0");
           return;
       }
       else if(ButtonText.equals("C")){
           if(dataToCalculate.length()==0){
               dataToCalculate="";
               solution.setText("0");
           }else {
               dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
           }
       }
       else if(symbols.contains(ButtonText) && symbols.contains(dataToCalculate.charAt(dataToCalculate.length()-1)))
       {
           dataToCalculate= dataToCalculate.substring(0,dataToCalculate.length()-1)+ButtonText;
       }else if(!ButtonText.equals("=")){
           dataToCalculate = dataToCalculate + ButtonText;
       }
       result.setText(dataToCalculate);

       String finalResult = getResult(dataToCalculate);

       if(!finalResult.equals("Err")){
           solution.setText(finalResult);
       }
    }

    String getResult(String data)
    {
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);

            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith((".0"))){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch(Exception e){
            return "Err";
        }
    }
}