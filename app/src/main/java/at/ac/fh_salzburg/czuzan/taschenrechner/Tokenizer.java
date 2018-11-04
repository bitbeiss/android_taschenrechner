package at.ac.fh_salzburg.czuzan.taschenrechner;
import android.util.Log;

import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;

public class Tokenizer {
    /*
        Tokenizer reads what the display currently holds and tries to
        parse it into float and char. Thereafter decides if a valid token
        (operator, float number etc.) has been encountered and how to proceed.
     */

    private String token;
    private int state;     //state of the tokenizer (1 to 3; 3 --> complete)
    private double tok1;    //token 1
    private double tok2;    //token 2
    private String tokstr1;
    private String tokstr2;
    private String ctok;   //currently token
    private char op;       //operator

    Tokenizer() {
        this.tok1 = 0;
        this.tok2 = 0;
        this.tokstr1 = "";
        this.tokstr2 = "";
        this.op = ' ';
        this.state = 0;    //state: 0 (no token1 & 2, no op), 1 (token1), 2 (token1,op), 3 (token1,op,token2)
        this.ctok = "";
    }

    public void reset() {
        this.setToken1(0);
        this.setToken2(0);
        this.tokstr1 = "";
        this.tokstr2 = "";
        this.setOperator(' ');
        this.setState(0);
        this.setCurrentToken("");
        Log.d("Reset","Tokenizer was reset.");
    }

    public void trcontinue(){
        this.setToken2(0);
        this.tokstr2 = "";
        this.setOperator(' ');
        this.setState(1);
        this.setCurrentToken("");
        Log.d("Reset","Tokenizer was set to continue.");
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

    public double getToken1() {
        return this.tok1;
    }

    public double getToken2() {
        return this.tok2;
    }

    public void setTokstr1(String tokstr1) {
        this.tokstr1 = tokstr1;
    }

    public void setToken1(double tok) {
        this.tok1 = tok;
    }

    public void setToken2(double tok) {
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

    // Custom method mimicing C# tryParse behaviour
    public boolean tryParseDouble(String value) {

        if(isNullOrEmpty(value)) {
            Log.d("Fail - isNullOrEmpty","tryParseDouble");
            return false;
        }

        try {
            parseDouble(value);
            Log.d("Success","tryParseDouble was true");
            return true;
        }
        catch (NumberFormatException e) {
            Log.d("Fail - catch","tryParseDouble");
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
        // See if the current token evaluates to a Double type
        // "token" is read and evaluated from display!

        if (tryParseDouble(this.tokstr1.concat(this.ctok))) {

            // we don't have an operator yet
            if(this.state == 0 || this.state == 1) {
                this.setToken1(Double.valueOf(this.ctok));
                Log.d("Set", "Token1 was set to: ".concat(ctok));
                this.setState(1);
                Log.d("State", String.valueOf(getState()));
                this.ctok ="";
                }


                // we already have an Operator
            if(this.state == 2 || this.state == 3) {
                this.setToken2(Double.valueOf(this.ctok));
                Log.d("Set", "Token2 was set to ".concat(ctok));
                this.setState(3);
                Log.d("State", String.valueOf(getState()));
                this.ctok ="";
                }

        }

        // See if the current token evaluates to a char type
        if (tryParseChar(this.ctok)) {
            char tmp = ctok.charAt(0);
            if(this.state > 0)
            {
                // ...AND is an operator
                if (((tmp == '%') || (tmp == 'x') || (tmp == '+') || (tmp == '-'))) {
                    // Operator is valid: switch from entry of token 1 to token 2
                    this.setOperator(tmp);
                    Log.d("Operator set", String.valueOf(tmp));
                    this.setState(2);
                    Log.d("State", String.valueOf(getState()));
                    this.ctok = "";
                }
            }
        }
    }
}