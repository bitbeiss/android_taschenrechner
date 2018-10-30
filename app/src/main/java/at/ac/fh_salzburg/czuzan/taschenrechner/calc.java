package at.ac.fh_salzburg.czuzan.taschenrechner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static android.view.View.*;


public class calc extends AppCompatActivity {

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
        View btn_equal = findViewById(R.id.btn_equal);
        View btn_comma = findViewById(R.id.btn_comma);
        View btn_sign = findViewById(R.id.btn_sign);

        List<View> digit_btn_list = Arrays.asList(      btn0, btn1, btn2, btn3,
                                                        btn4, btn5, btn6, btn7, btn8, btn9,
                                                        btn_comma);
        List<View> op_ctrl_btn_list = Arrays.asList(    btn_plus, btn_minus, btn_mal,
                                                        btn_equal, btn_corr, btn_division,
                                                        btn_sign);

        final Tokenizer myTokenizer = new Tokenizer();
        final TR myTR = new TR();

        final Iterator<View> d_iterator = digit_btn_list.iterator();
        final Iterator<View> oc_iterator = op_ctrl_btn_list.iterator();

        // Install the onClick Listerners for control- and operator buttons.
        // Keyboard logic for other than digits
        while(oc_iterator.hasNext()) {
            View tmp = oc_iterator.next();
            tmp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    TextView display = findViewById(R.id.display);
                    Button btn = (Button) v;
                    String ch = btn.getText().toString();
                    String current_display_text = display.getText().toString();

                    if(ch.equals("+/-")) {
                        if (display.getText().toString().substring(0,1).equals("-")) {
                            display.setText(display.getText().toString().substring(1));
                        }
                        else{
                            display.setText(String.valueOf('-').concat(display.getText().toString()));
                        }
                        myTokenizer.setCurrentToken(display.getText().toString());
                        myTokenizer.mytokenize();
                    }

                    if(ch.equals("+") ) {
                        display.setText("+");
                        myTokenizer.setCurrentToken("+");
                        myTokenizer.mytokenize();
                        display.setText("");
                    }

                    if(ch.toString().equals("-") ) {
                        display.setText("-");
                        myTokenizer.setCurrentToken("-");
                        myTokenizer.mytokenize();
                        display.setText("");
                    }

                    if(ch.equals("x") ) {
                        display.setText("x");
                        myTokenizer.setCurrentToken("x");
                        myTokenizer.mytokenize();
                        display.setText("");
                    }

                    if(ch.equals("%") ) {
                        display.setText("%");
                        myTokenizer.setCurrentToken("%");
                        myTokenizer.mytokenize();
                        display.setText("");
                    }

                    if(ch.equals("=") ) {
                        if (myTokenizer.getState() == 3) {
                            myTR.setOperand1(myTokenizer.getToken1());
                            myTR.setOperand2(myTokenizer.getToken2());
                            myTR.setOperator(myTokenizer.getOperator());
                            myTR.calc();

                            if ((int) myTR.digits == 0) {
                                display.setText(String.format("%.0f", myTR.getResult()));
                            }
                            else if ((int) myTR.digits == 1) {
                                display.setText(String.format("%.1f", myTR.getResult()));
                            }
                            else if ((int) myTR.digits == 2) {
                                display.setText(String.format("%.2f", myTR.getResult()));
                            }
                            else if ((int) myTR.digits == 3) {
                                display.setText(String.format("%.3f", myTR.getResult()));
                            }
                            else if ((int) myTR.digits == 4) {
                                display.setText(String.format("%.4f", myTR.getResult()));
                            }
                            else if ((int) myTR.digits == 5) {
                                display.setText(String.format("%.5f", myTR.getResult()));
                            }
                            else if ((int) myTR.digits == 6) {
                                display.setText(String.format("%.6f", myTR.getResult()));
                            }
                            else if ((int) myTR.digits == 7) {
                                display.setText(String.format("%.7f", myTR.getResult()));
                            }
                            else if ((int) myTR.digits == 8) {
                                display.setText(String.format("%.8f", myTR.getResult()));
                            }
                            else display.setText(String.format("%.8f", myTR.getResult()));
                            myTR.setOperand1(myTR.getResult()); // Continue using result as operand1
                            myTokenizer.trcontinue(); // Continue instead of reset using result as operand1
                            myTokenizer.setToken1(myTR.getResult());
                            //myTokenizer.reset();  // reset Tokenizer state
                        }
                    }

                    // clear/reset the calculator
                    if (ch.equals("CE/C")) {
                        myTokenizer.reset();
                        myTR.clear();
                        display.setText("0");
                    }
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
                    TextView display = findViewById(R.id.display);
                    Button btn = (Button) v;
                    String ch = btn.getText().toString();
                    String current_display_text = display.getText().toString();
                    // case: 0 is displayed (nothing entered yet; replace zero with
                    // current character/number entered on keyboard.
                    if (display.getText().toString().equals("0")) {
                        display.setText(ch);
                        myTokenizer.setCurrentToken(display.getText().toString());
                        myTokenizer.mytokenize();

                    }
                    // case: comma encountered; append comma and continue appending numbers
                    // tokenizer to be done only with additional digit entered (omitted here)
                    else if (ch.equals(".") || ch.equals(",")) {
                        display.setText(current_display_text.concat("."));
                    }
                    // case: continue to append numbers entered on keyboard
                    // (try to) tokenize thereafter
                    else {
                        display.setText(current_display_text.concat(ch));
                        myTokenizer.setCurrentToken(display.getText().toString());
                        myTokenizer.mytokenize();
                    }
                }
            });
        }
    }
}
