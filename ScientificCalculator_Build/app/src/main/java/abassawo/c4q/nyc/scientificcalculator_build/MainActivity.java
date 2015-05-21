
package abassawo.c4q.nyc.scientificcalculator_build;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Iterator;

import com.fathzer.soft.javaluator.Constant;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;

public class MainActivity extends Activity implements Serializable{
    private String expression, calcExpr;  //expression is for Human Display. calcExpr is for calculator to calculate.
    private TextView calcDisplay, answerDisplay;  //together, both displays show expression and answer below. (see xml)
    private Button equalsButton;
    DoubleEvaluator expressionEvaluator;
    private String delString;
    private double result;
    private String resultStr, ANS;
    private static final Operator factorial = new Operator("!", 2, Operator.Associativity.LEFT, 3); //symbol, operand count, associativity, and precedence)
    private static final Function SQRT = new Function("sqrt", 1);

//        @Override
//    protected void onSaveInstanceState(Bundle outState){
//            super.onSaveInstanceState(outState);
//            outState.putString("expressionStr", expression);
//            outState.putString("answer", ANS);
//            outState.putString("result Str", resultStr);
//    }


        @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parameters params = DoubleEvaluator.getDefaultParameters();
        params.add(SQRT);
        params.add(factorial);

        //final DoubleEvaluator expressionEvaluator = new DoubleEvaluator(params); //experimental

//        if (  (savedInstanceState != null)) {
//            //Restore value of members from saved state
//            mCurrentExpression = savedInstanceState.getString("expressionStr");
//            mCurrentANS = savedInstanceState.getString("answer");
//        }
//        else {
//            mCurrentExpression = "";
//            mCurrentANS = "";
//        }
//

        try {
            expressionEvaluator = new DoubleEvaluator(params) {
                @Override
                protected Double evaluate(Operator operator, Iterator<Double> operands, Object evaluationContext) {
                    double facResult = 1;
                    if (operator == factorial) {
                        double num = operands.next();
                        for (double i = num; i >= 1; i--) {
                            facResult = facResult * i;
                        }
                        return facResult;
                    } else {
                        return super.evaluate(operator, operands, evaluationContext);
                    }
                    //return super.evaluate(expression evaluationContext);

                }

                @Override
                public Double evaluate(Function function, Iterator arguments, Object evaluationContext) {
                    if (function == SQRT) {
                        // Implements the new function
                        return Math.sqrt((double) arguments.next());
                    } else {
                        // If it's another function, pass it to DoubleEvaluator
                        return super.evaluate(function, arguments, evaluationContext);
                    }
                }

            };
        }
        catch (IllegalArgumentException e){

        }

        final DecimalFormat doubleFormat = new DecimalFormat("0.00");

        expression = "";
        answerDisplay = (TextView) findViewById(R.id.tvAnswer);
        calcDisplay = (TextView) findViewById(R.id.tvExpression);
        calcDisplay.setMovementMethod(new ScrollingMovementMethod());
        equalsButton = (Button) findViewById(R.id.equalsButton);
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("expression", expression);

                try {
                    if (expression.contains("!")   ){
                        String factorialExp = expression.replace("!", "!1 ");
                        calcExpr = factorialExp;
                    } else
                    if (expression.contains("%")){
                        calcExpr = expression.replace("%", "* .01");  //calculate percent.)
                    } else {
                        calcExpr = expression;
                    }//can also create certain distinction.
                    result = expressionEvaluator.evaluate(calcExpr); //calculate stuff
                    Log.d("expr to be calculated", calcExpr);

                    resultStr = doubleFormat.format(result); //avoid excessive decimals
                    resultStr = String.valueOf(result);

                    Log.d("test string", "resultStr");
                    if (resultStr.endsWith(".0")) {
                        resultStr = resultStr.substring(0, resultStr.indexOf('.')); //Integer cases!
                        answerDisplay.setText("=" + resultStr); //exper

                    } else {
                        answerDisplay.setText("" + resultStr); //perhaps format this for maximum # of digits after decimal place. "0.000"
                        // clear content for new calculations, unless an operation sign is selected. see comment above.
                    }
                    Log.d("calc Expr", calcExpr);

                } catch (IllegalArgumentException e) {
                    answerDisplay.setText("err");  //experimental.
                }
                catch (NullPointerException e){
                    answerDisplay.setText("err");
                }
                expression = String.valueOf(resultStr);
                //calcExpr = "";
                expression = "";
                //returned to null. this way, you can still use a checker.
                // if calcexpr is null, when you do onNumber button clear calcdisplay before adding button
                ANS = resultStr;
            }
        });

     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onOperationClick(View v){
        delString = calcDisplay.getText().toString();
        switch (v.getId()) {
            case R.id.delButton:
                ImageButton backspace = (ImageButton) findViewById(R.id.delButton);
               // if (backspace!= null) {
                    if (delString.length() > 0) {
                        delString = delString.substring(0, delString.length() - 1);
                        expression = delString;
                    } else {
                        expression = "";
                        backspace.setClickable(false); //stop clicking! no more text!
                    }
               // }
                calcDisplay.setText(expression);
                break;
            case R.id.cancelButton: //rename this to clearButton.
                Button clearButton = (Button) findViewById(R.id.cancelButton);
               // if (clearButton != null) {
                    calcExpr = "";
                    expression = "";
                    calcDisplay.setText("");
                    answerDisplay.setText("");
                    if (delString.length() == 0) {
                        clearButton.setClickable(false);
                    }
                // }

                break;
            //Operations
            case R.id.minusButton:
                //Button minusButton = (Button) findViewById(R.id.minusButton);
               // if (minusButton != null) {
                    if (expression == "" && ANS != null) {
                        expression += (ANS + "-");
                        calcDisplay.setText(expression);
                    } else {
                        expression += ("" + "-");
                        calcDisplay.setText(expression);
                    }
               // }
                break;
            case R.id.plusButton:
               // Button plusButton = (Button) findViewById(R.id.plusButton);
               // if (plusButton != null) {
                    if (expression == "" && ANS != null) {
                        expression += (ANS + "+");
                        calcDisplay.setText(expression);
                    } else {
                        expression += ("" + "+");
                        calcDisplay.setText(expression);
                    }
               // }
                    break;

            case R.id.xButton:
                //Button xButton = (Button) findViewById(R.id.xButton);
                //if (xButton != null) {
                    if (expression == "" && ANS != null) {
                        expression += (ANS + "+");
                        calcDisplay.setText(expression);
                    } else {
                        expression += ("" + '*');
                        calcDisplay.setText(expression);
                    }
                //}
                break;
            case R.id.divButton:
                //Button divButton = (Button) findViewById(R.id.divButton);
                //if (divButton != null) {
                    if (expression == "" && ANS != null) {
                        expression += (ANS + "+");
                        calcDisplay.setText(expression);
                    } else {
                        expression += ("" + '/');
                        calcDisplay.setText(expression);
                    }
                //}
                break;
            case R.id.ansButton:
                //Button answerButton = (Button) findViewById(R.id.ansButton);
               // if (answerButton != null) { //IF WE ARE IN LANDSCAPE MODE
                    if (ANS == null) {
                        expression += "";
                        calcDisplay.setText("");
                    } else if (expression.equals(ANS)) {//works
                        calcDisplay.setText(expression);
                    } else {
                        expression += ANS;
                        calcDisplay.setText(expression);
                    }
                    break;
                //}

              }
                answerDisplay.setText("");
        }

      public void onNumbersClicked(View v) {


//
        switch (v.getId()) {
            case R.id.zeroButton:
                expression += ("" + 0);
                break;
            case R.id.oneButton:
                expression += ("" + 1);
                break;
            case R.id.twoButton:
                expression += ("" + 2);
                break;
            case R.id.threeButton:
                expression += ("" + 3);
                break;
            case R.id.fourButton:
                expression += ("" + 4);
                break;
            case R.id.fiveButton:
                expression += ("" + 5);
                break;
            case R.id.sixButton:
                expression += ("" + 6);
                break;
            case R.id.sevenButton:
                expression += ("" + 7);
                break;
            case R.id.eightButton:
                expression += ("" + 8);
                break;
            case R.id.nineButton:
                expression += ("" + 9);
                break;
        }
        calcDisplay.setText(expression);
          answerDisplay.setText("");
    }

    public void onButtonClick(View v) {
            switch (v.getId()) {
                case R.id.squareButton:
                    expression += "^2";
                    if (expression.contains("^2.")){
                        expression = expression.replace("^2.","^2 ");
                    }
                case R.id.decimalButton:
                    Button decimalButton = (Button) findViewById(R.id.decimalButton);
                    //if (decimalButton != null) {
                        expression += ("" + '.');
                    //}
                    //calcDisplay.setText(expression);
                    break;
                case R.id.openParensButton:
                    Button openParensButton = (Button) findViewById(R.id.openParensButton);
                    //if (openParensButton != null) {
                        expression += ("" + '(');
                        // calcDisplay.setText(expression);
                    //}
                    break;
                case R.id.closeParensButton:
                    Button closeParensButton = (Button) findViewById(R.id.closeParensButton);
                    //if (closeParensButton != null) {
                        expression += ("" + ')');
                        // calcDisplay.setText(expression);
                    //}
                    break;

                case R.id.piButton:
                    Button piButton = (Button) findViewById(R.id.piButton);
                    //if (piButton != null) {
                        expression += ("pi");
                    //}
                    break;
                case R.id.sinButton:
                    Button sinButton = (Button) findViewById(R.id.sinButton);
                    //if (sinButton != null) {
                        expression += ("sin(");
                    //}
                    //calcDisplay.setText(expression);
                    break;
                case R.id.cosButton:
                    Button cosButton = (Button) findViewById(R.id.cosButton);
                    //if (cosButton != null){
                        expression += ("cos(");
                    //}

                    break;
                case R.id.tanButton:
                    Button tanButton  = (Button) findViewById(R.id.tanButton);
                    //if (tanButton != null) {
                        expression += ("tan(");
                    //}
                    break;
                case R.id.lnButton:
                    Button lnButton = (Button) findViewById(R.id.lnButton);
                    //if (lnButton != null) {
                        expression += ("ln(");
                    //}
                    break;
                case R.id.exp_EXPButton:
                    Button expButton = (Button) findViewById(R.id.exp_EXPButton);
                    //if (expButton != null) {
                        expression += ("^");
                    //}
                    break;
                case R.id.eButton:
                    Button eButton = (Button) findViewById(R.id.eButton);
                    //if (eButton != null){
                    expression += ("e");
                  // }
                    break;
                case R.id.logButton:
                    Button logButton = (Button) findViewById(R.id.logButton);
                    //if (logButton != null) {
                        expression += ("log(");
                    //}
                    break;
                case R.id.sqrtButton:
                    Button sqrtButton = (Button) findViewById(R.id.sqrtButton);
                    //if (sqrtButton != null) {
                        expression += ("sqrt(");
                    //}
                    break;
                case R.id.factorialButton:
                    Button facButton = (Button) findViewById(R.id.factorialButton);
                   // if (facButton != null) {
                        expression += ("!");
                    //}
                    break;
                case R.id.pctSignButton:
                    Button pctButton = (Button) findViewById(R.id.pctSignButton);
                    //if (pctButton != null) {
                        expression += ("%");
                    //}
                    break;
                case R.id.invButton:
                    Button invButton = (Button) findViewById(R.id.invButton);
                    //if (invButton != null) {
                        expression += ("1/");
                    //}
                    break;

    }
            calcDisplay.setText(expression);
            answerDisplay.setText("");

    }

    public void onToggleClicked(View v){
        // radian to degree
    }
}

