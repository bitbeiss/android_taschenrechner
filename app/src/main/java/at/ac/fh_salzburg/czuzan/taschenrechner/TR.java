package at.ac.fh_salzburg.czuzan.taschenrechner;

import android.util.Log;


//Main class for calculator (operations and data)
public class TR {
    private float token1;
    private float token2;
    private char operator;
    private float result;
    private boolean errorflag;

    public TR() {
        token1 = 0;
        token2 = 0;
        result = 0;
        operator = ' ';
        errorflag = false;
    }

    public void setToken1(float tok1) {
        this.token1 = tok1;
    }

    public void setToken2(float tok2) {
        this.token2 = tok2;
    }

    public void setOperator(char op) {
        this.operator = op;
    }

    private void plus() {
        this.result = this.token1 + this.token2;
    }

    private void minus() {
        this.result = this.token1 - this.token2;
    }

    private void mal() {
        this.result = this.token1 * this.token2;
    }

    private void div() {
        try {
            if (this.token2 == 0) throw new IllegalArgumentException();
            this.result = this.token1 / this.token2;
        }
        catch (IllegalArgumentException e) {
            this.result = 0;
            this.errorflag = true;
        }
    }

    // Public Function to call the calculators functions.
    // (Result Depending on the operator data.)
    public void calc() {

        switch (this.operator) {
            case '+' :
                Log.d("calc - Operator","+");
                plus();
                break;

            case '-' :
                Log.d("calc - Operator","-");
                minus();
                break;

            case 'x' :
                Log.d("calc - Operator","x");
                mal();
                break;

            case '%' :
                Log.d("calc - Operator","%");
                div();
                break;

            default:
                Log.d("calc - Operator fail","No/no-valid operator! Value: ".concat(String.valueOf(this.operator)));
        }
    }

    public float getResult() {
        return this.result;
    }

    public void clear() {
        this.setToken1(0);
        this.setToken2(0);
        this.result = 0;
        this.setOperator(' ');
        errorflag = false;
    }
}