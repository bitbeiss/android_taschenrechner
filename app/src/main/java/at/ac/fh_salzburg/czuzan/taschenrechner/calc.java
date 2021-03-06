package at.ac.fh_salzburg.czuzan.taschenrechner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.SystemClock;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static android.view.View.*;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;


public class calc extends AppCompatActivity {

    final Tokenizer myTokenizer = new Tokenizer();
    final TR myTR = new TR();
    private TextView display;
    private TextView txthistory;


    public void myTR_calc()
    {
        myTR.calc();
    }

    public void resetAll()
    {
        myTokenizer.reset();
        myTR.clear();
        display.setText("0");
        txthistory.setText("");
    }

    public void historySet()
    {
        if((myTokenizer.getToken2() > 0.0) || (myTokenizer.getState() > 2))
            txthistory.setText(String.valueOf(myTokenizer.getToken1()) + " " + String.valueOf(myTokenizer.getOperator()) + " " + String.valueOf(myTokenizer.getToken2()));
        else
            txthistory.setText(String.valueOf(myTokenizer.getToken1()) + " " +String.valueOf(myTokenizer.getOperator()));

    }

    public void displayZeroCheck()
    {
        String displayText = display.getText().toString();
        if(displayText.substring(0,1).equals("0") && (displayText.length() > 1))
        {
            if(!displayText.substring(1,2).equals("."))
                displayText = displayText.substring(1,displayText.length());
        }
        else if(displayText.substring(0,1).equals("-")&& (displayText.length() > 1))
        {
            if(displayText.substring(1,2).equals("0") && (displayText.length() > 2))
            {
                if(!displayText.substring(2,3).equals("."))
                    displayText = displayText.substring(0,1) + displayText.substring(2,displayText.length());
            }
            else if(displayText.substring(1,2).equals("."))
                displayText = displayText.substring(0,1) + "0" + displayText.substring(1, displayText.length());
        }
        else if(displayText.substring(0,1).equals("."))
        {
            displayText = "0" + displayText;
        }
        display.setText(displayText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        View btn0 = findViewById(R.id.btn0);
        View btn1 = findViewById(R.id.btn1);
        View btn2 = findViewById(R.id.btn2);
        View btn3 = findViewById(R.id.btn3);
        View btn4 = findViewById(R.id.btn4);
        View btn5 = findViewById(R.id.btn5);
        View btn6 = findViewById(R.id.btn6);
        View btn7 = findViewById(R.id.btn7);
        View btn8 = findViewById(R.id.btn8);
        View btn9 = findViewById(R.id.btn9);

        View btn_plus = findViewById(R.id.btn_plus);
        View btn_minus = findViewById(R.id.btn_minus);
        View btn_mal = findViewById(R.id.btn_mal);
        View btn_division = findViewById(R.id.btn_division);
        View btn_corr = findViewById(R.id.btn_corr);
        final View btn_equal = findViewById(R.id.btn_equal);
        View btn_comma = findViewById(R.id.btn_comma);
        View btn_sign = findViewById(R.id.btn_sign);
        View btn_recall = findViewById(R.id.btn_recall);
        View btn_store = findViewById(R.id.btn_store);
        View btn_squareRoot = findViewById(R.id.btn_squareRoot);

        List<View> digit_btn_list = Arrays.asList(      btn0, btn1, btn2, btn3,
                                                        btn4, btn5, btn6, btn7, btn8, btn9,
                                                        btn_comma);
        List<View> op_ctrl_btn_list = Arrays.asList(    btn_plus, btn_minus, btn_mal,
                                                        btn_equal, btn_corr, btn_division,
                                                        btn_sign, btn_recall, btn_store, btn_squareRoot);



        display = findViewById(R.id.display);
        txthistory = findViewById(R.id.txtHistory);

        final Iterator<View> d_iterator = digit_btn_list.iterator();
        final Iterator<View> oc_iterator = op_ctrl_btn_list.iterator();



        // Install the onClick Listerners for control- and operator buttons.
        // Keyboard logic for other than digits
        while(oc_iterator.hasNext()) {
            View tmp = oc_iterator.next();
            tmp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(android.view.View v) {

                    if(myTR.isErrorflag())
                        resetAll();


                    Button btn = (Button) v;
                    String ch = btn.getText().toString();
                    String current_display_text = display.getText().toString();

                    if((ch.contentEquals("+")
                            || ch.contentEquals("-")
                            || ch.contentEquals("x")
                            || ch.contentEquals("%"))
                            && (myTokenizer.getState() == 3))
                        btn_equal.performClick();

                    if(ch.equals("+/-")) {
                        if(!(myTokenizer.getState() == 2))
                        {
                            if (display.getText().toString().substring(0,1).equals("-")) {
                                display.setText(display.getText().toString().substring(1));
                            }
                            else{
                                display.setText(String.valueOf('-').concat(display.getText().toString()));
                            }
                        }
                        else
                            display.setText(String.valueOf('-').concat("0"));
                        myTokenizer.setCurrentToken(display.getText().toString());
                        myTokenizer.mytokenize();
                    }

                    if(ch.equals("+") ) {
                        display.setText("+");
                        myTokenizer.setCurrentToken("+");
                        myTokenizer.mytokenize();
                        // display.setText("");
                    }

                    if(ch.toString().equals("-") ) {
                        display.setText("-");
                        myTokenizer.setCurrentToken("-");
                        myTokenizer.mytokenize();
                        // display.setText("");
                    }

                    if(ch.equals("x") ) {
                        display.setText("x");
                        myTokenizer.setCurrentToken("x");
                        myTokenizer.mytokenize();
                        // display.setText("");
                    }

                    if(ch.equals("%") ) {
                        display.setText("%");
                        myTokenizer.setCurrentToken("%");
                        myTokenizer.mytokenize();
                        // display.setText("");
                    }

                    if(ch.equals("sqrt()") ) {

                        if (myTokenizer.getState() == 2)
                            return;
                        else if (myTokenizer.getState() == 1)
                        {
                            if((myTokenizer.getToken1() >= 0))
                            {
                                myTokenizer.setToken1(Math.sqrt(myTokenizer.getToken1()));
                                display.setText(String.valueOf(myTokenizer.getToken1()));
                            }
                            else
                            {
                                myTR.setErrorflag(true);
                                display.setText("Error: complex number");
                                //display.setText(Double.toString((myTokenizer.getToken1())));
                                return;
                            }
                        }
                        else if (myTokenizer.getState() == 3)
                        {
                            if((myTokenizer.getToken2() >= 0))
                            {
                                myTokenizer.setToken2(Math.sqrt(myTokenizer.getToken2()));
                                display.setText(String.valueOf(myTokenizer.getToken2()));
                            }
                            else
                            {
                                myTR.setErrorflag(true);
                                display.setText("Error: complex number");
                                return;
                            }
                        }
                    }


                    if(ch.equals("=") ) {
                        if (myTokenizer.getState() == 3) {
                            myTR.setOperand1(myTokenizer.getToken1());
                            myTR.setOperand2(myTokenizer.getToken2());
                            myTR.setOperator(myTokenizer.getOperator());
                            myTR.calc();

                            if(myTR.isErrorflag()) {
                                display.setText("divide by 0");
                                myTR.setLastUsedButton(btn.getText().toString());
                                return;
                            }

                            display.setText(String.valueOf(myTR.roundResult()));

                            myTR.setOperand1(myTR.getResult()); // Continue using result as operand1
                            myTokenizer.trcontinue(); // Continue instead of reset using result as operand1
                            myTokenizer.setToken1(myTR.getResult());

                        }
                    }

                    // clear/reset the calculator
                    if (ch.equals("CE/C"))
                        resetAll();

                    if(ch.equals("STO")) {
                        btn_equal.performClick();
                        if(myTokenizer.tryParseDouble(display.getText().toString()))
                            myTR.setStoredValue(parseDouble(display.getText().toString()));
                    }

                    if(ch.equals("RCL")) {

                        display.setText(String.valueOf(myTR.getStoredValue()));
                        myTokenizer.setCurrentToken(String.valueOf(myTR.getStoredValue()));
                        myTokenizer.mytokenize();
                    }

                    historySet();
                    displayZeroCheck();

                    if(!(ch.equals("RCL") || ch.equals("STO")))
                        myTR.setLastUsedButton(btn.getText().toString());
                 }
            });
        }

        // Install the onClick Listeners for all digit- and the comma Button.
        // "Keyboard Logic"
        while(d_iterator.hasNext()) {
            View tmp = d_iterator.next();
            tmp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(android.view.View v) {

                    if(myTR.isErrorflag() || myTR.getLastUsedButton().contentEquals("="))
                        resetAll();

                    Button btn = (Button) v;
                    String ch = btn.getText().toString();
                    String current_display_text = display.getText().toString();

                    // case: the last thing seen was an Operator
                    // (and now we see a digit again)
                    // the case covers a given operand1, operator and still-to-enter
                    // operand2 (delete display and continue to enter numbers)
                    // if (myTokenizer.getState()==2 || myTokenizer.getState()==3) {
                    if (myTokenizer.getState()==2) {
                        current_display_text="";
                        display.setText("");
                    }

                    // case: 0 is displayed (nothing entered yet; replace zero with
                    // current character/number entered on keyboard.
                    if (display.getText().toString().equals("0")||(myTokenizer.getState()==2)) {
                        if(ch.equals(".")){
                            current_display_text="0";
                            current_display_text = current_display_text.concat(ch);
                            display.setText(current_display_text);
                        }
                        else {
                            display.setText(ch);
                            current_display_text = current_display_text.concat(ch);
                            display.setText(current_display_text);
                        }
                        myTokenizer.setCurrentToken(display.getText().toString());
                        myTokenizer.mytokenize();

                    }
                    // case: comma encountered; append comma and continue appending numbers
                    // tokenizer to be done only with additional digit entered (omitted here)
                    else if (ch.equals(".") || ch.equals(",")) {
                        if(!display.getText().toString().contains("."))
                            display.setText(current_display_text.concat("."));
                    }
                    // case: continue to append numbers entered on keyboard
                    // (try to) tokenize thereafter
                    else {
                        display.setText(current_display_text.concat(ch));
                        myTokenizer.setCurrentToken(display.getText().toString());
                        myTokenizer.mytokenize();
                    }

                    historySet();
                    displayZeroCheck();

                    myTR.setLastUsedButton(btn.getText().toString());
                }
            });
        }
    }
}
