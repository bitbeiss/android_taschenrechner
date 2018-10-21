package at.ac.fh_salzburg.czuzan.taschenrechner;
import android.util.Log;

import android.view.Display;

import static java.lang.Float.parseFloat;

public class Tokenizer {
    /*
        Tokenizer reads what the display currently holds and tries to
        parse it into float and char. Thereafter decides if a valid token
        (operator, float number etc.) has been encountered and how to proceed.
     */

    private String token;
    private int state;     //state of the tokenizer (1 to 3; 3 --> complete)
    private float tok1;    //token 1
    private float tok2;    //token 2
    private String ctok;   //currently token
    private char op;       //operator

    Tokenizer() {
        this.tok1 = 0;
        this.tok2 = 0;
        this.op = ' ';
        this.state = 1;
        this.ctok = "";
    }

    public void reset() {
        this.setToken1(0);
        this.setToken2(0);
        this.setOperator(' ');
        this.setState(1);
        this.setCurrentToken("");
        Log.d("Reset","Tokenizer was reset.");
    }

    public void setCurrentToken(String current_token) {
        this.ctok = current_token;
    }

    public int getState() {
        return this.state;
    }

    private void setState(int stat) {
        this.state = stat;
    }

    public float getToken1() {
        return this.tok1;
    }

    public float getToken2() {
        return this.tok2;
    }

    private void setToken1(float tok) {
        this.tok1 = tok;
    }

    private void setToken2(float tok) {
        this.tok2 = tok;
    }

    private void setOperator(char operator) {
        this.op = operator;
    }

    public char getOperator() {
        return this.op;
    }

    // Custom method to test if a string is empty or contains just blanks
    private static boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().equals(""));
    }

    // Custom method mimicing C# tryParse behaviour
    public boolean tryParseFloat(String value) {

        if(isNullOrEmpty(value)) {
            Log.d("Fail - isNullOrEmpty","tryParseFloat");
            return false;
        }

        try {
            parseFloat(value);
            Log.d("Success","tryParseFloat was true");
            return true;
        }
        catch (NumberFormatException e) {
            Log.d("Fail - catch","tryParseFloat");
            return false;
        }
    }


    public boolean tryParseChar(String value) {

        if(isNullOrEmpty(value)) {
            return false;
        }

        try {
            value.charAt(0);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public void mytokenize() {
        Log.d("Exe","mytokenize");
        // See if the current token evaluates to a float type
        if (tryParseFloat(this.ctok)) {

            // we don't have an operator yet
            if(this.state == 1){
                this.setToken1(parseFloat(this.ctok));
                Log.d("Set","Token1 was set.");
            }

            // we already have an Operator
            if(this.state == 2) {
                this.setToken2(parseFloat(this.ctok));
                Log.d("Set","Token2 was set.");
                this.setState(3);
                Log.d("State", String.valueOf(getState()));
            }
        }

        // See if the current token evaluates to a char type
        if (tryParseChar(this.ctok)) {
            char tmp = ctok.charAt(0);
            // ...AND is an operator
            if ((tmp == '%') || (tmp == 'x') || (tmp == '+') || (tmp == '-')) {
                // Operator is valid: switch from entry of token 1 to token 2
                this.setOperator(tmp);
                Log.d("Operator set",String.valueOf(tmp));
                this.setState(2);
                Log.d("State", String.valueOf(getState()));
            }
        }
    }
}