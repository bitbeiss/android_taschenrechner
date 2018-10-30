package at.ac.fh_salzburg.czuzan.taschenrechner;

import android.util.Log;


//Main class for calculator (operations and data)
public class TR {
    private double operand1;
    private double operand2;
    private char operator;
    private double result;
    private boolean errorflag;

    public TR() {
        operand1 = 0;
        operand2 = 0;
        result = 0;
        operator = ' ';
        errorflag = false;
    }

    public void setOperand1(double tok1) {
        this.operand1 = tok1;
    }

    public void setOperand2(double tok2) {
        this.operand2 = tok2;
    }

    public void setOperator(char op) {
        this.operator = op;
    }

    private void plus() {
        this.result = this.operand1 + this.operand2;
    }

    private void minus() {
        this.result = this.operand1 - this.operand2;
    }

    private void mal() {
        this.result = this.operand1 * this.operand2;
    }

    private void div() {
        try {
            if (this.operand2 == 0) throw new IllegalArgumentException();
            this.result = this.operand1 / this.operand2;
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
                if (this.operand2 == 0) {
                    this.errorflag = true;
                    break;
                }
                Log.d("calc - Operator","%");
                div();
                break;

            default:
                Log.d("calc - Operator fail","No/no-valid operator! Value: ".concat(String.valueOf(this.operator)));
        }
    }

    public double getResult() {
        return this.result;
    }

    public void clear() {
        this.setOperand1(0);
        this.setOperand2(0);
        this.result = 0;
        this.setOperator(' ');
        errorflag = false;
    }
}