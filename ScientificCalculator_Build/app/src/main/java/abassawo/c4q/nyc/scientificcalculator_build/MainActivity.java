package abassawo.c4q.nyc.scientificcalculator_build;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.Iterator;


import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;

public class MainActivity extends Activity{
    private String expression;
    private String calcExpr;
    private Button equalsButton;
    private String delString;
    TextView calcDisplay;
    private double result;
    private String ANS;
    private TextView answerDisplay;
    String resultStr;
    DoubleEvaluator expressionEvaluator;
    private static final Function SQRT = new Function("sqrt", 1);
    private static final Operator FACTORIAL = new Operator("!", 2, Operator.Associativity.LEFT, 3);
    private static final Parameters params= DoubleEvaluator.getDefaultParameters(); // Gets the default DoubleEvaluator's parameters


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        params.add(SQRT); // add the new sqrt function to Javaluator's parameters
        params.add(FACTORIAL); // add the new factorial to Javaluator's params
        expressionEvaluator = new DoubleEvaluator(params){

            @Override
            protected Double evaluate(Operator operator, Iterator<Double> operands, Object evaluationContext) {
                double facResult = 1;
                if (operator == FACTORIAL){
                    double num = operands.next();
                    for (double i = num; i >= 1; i--){
                        facResult = facResult * i;
                    }
                    return facResult;
                }
                else {
                    return super.evaluate(operator, operands, evaluationContext );
                }
            }


            @Override
            protected Double evaluate(Function function, Iterator arguments, Object evaluationContext) {
                if (function == SQRT) {
                    // Implements the new function
                    return Math.sqrt((double) arguments.next());
                } else {
                    // If it's another function, pass it to DoubleEvaluator
                    return super.evaluate(function, arguments, evaluationContext);
                }
            }
        };

        final DecimalFormat doubleFormat = new DecimalFormat("0.00");

        expression = "";
        //calcExpr = expression;

        answerDisplay = (TextView) findViewById(R.id.tvAnswer);
        calcDisplay = (TextView) findViewById(R.id.tvExpression);
        calcDisplay.setMovementMethod(new ScrollingMovementMethod());
        equalsButton = (Button) findViewById(R.id.equalsButton);
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ANS="";
                    try {
                        if (expression.contains("!" )){
                            String factorialExp = expression.replace("!", "!1");
                            calcExpr = factorialExp;
                        } else if (expression.contains("%")){
                            calcExpr = expression.replace("%", "* .01");
                        } else {
                            calcExpr = expression;
                        }

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



                    } catch (IllegalArgumentException e) {
                        answerDisplay.setText("Err");
                    }
                    expression = String.valueOf(resultStr);
                    calcExpr = null;
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
        switch (v.getId()) {
            case R.id.delButton:
                delString = calcDisplay.getText().toString();
                int length = delString.length();
                if (length > 0) {
                    delString = delString.substring(0, length - 1);
                    expression = delString;
                } else {
                    expression = "";
                }
                calcDisplay.setText(expression);
                break;
            case R.id.cancelButton: //rename this to clearButton.
                calcExpr="";
                expression="";
                calcDisplay.setText("");
                answerDisplay.setText("");
                break;
            //Operations
            case R.id.minusButton:
                if (expression == "" && ANS != null){
                    expression += (ANS + "-");
                    calcDisplay.setText(expression);
                } else {
                    expression += ("" + "-");
                    calcDisplay.setText(expression);
                }
                break;
            case R.id.plusButton:
                if (expression == "" && ANS != null){
                    expression += (ANS + "+");
                    calcDisplay.setText(expression);
                } else {
                    expression += ("" + "+");
                    calcDisplay.setText(expression);
                }
                    break;

            case R.id.xButton:
                if (expression == "" && ANS != null){
                    expression += (ANS + "+");
                    calcDisplay.setText(expression);
                } else {
                    expression += ("" + '*');
                    calcDisplay.setText(expression);
                }
                break;
            case R.id.divButton:
                if (expression == "" && ANS != null){
                    expression += (ANS + "+");
                    calcDisplay.setText(expression);
                } else {
                    expression += ("" + '/');
                    calcDisplay.setText(expression);
                }
                break;
            case R.id.ansButton:
                if (ANS == null){
                    expression += "";
                    calcDisplay.setText("");
                } else if (expression.equals(ANS)){//works
                    calcDisplay.setText(expression);
                } else  {
                    expression += ANS;
                    calcDisplay.setText(expression);
                }
                break;

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
                case R.id.decimalButton:
                    expression += ("" + '.');
                    //calcDisplay.setText(expression);
                    break;
                case R.id.openParensButton:
                    expression += ("" + '(');
                    // calcDisplay.setText(expression);
                    break;
                case R.id.closeParensButton:
                    expression += ("" + ')');
                    // calcDisplay.setText(expression);
                    break;

                case R.id.piButton:
                    expression += ("pi");
                    break;
                case R.id.sinButton:
                    expression += ("sin(");
                    //calcDisplay.setText(expression);
                    break;
                case R.id.cosButton:
                    expression += ("cos(");
                    break;
                case R.id.tanButton:
                    expression += ("tan(");
                    break;
                case R.id.lnButton:
                    expression += ("ln(");
                    break;
                case R.id.exp_EXPButton:
                    expression += ("^");
                    break;
                case R.id.eButton:
                    expression += ("e");
                    break;
                case R.id.logButton:
                    expression += ("log");
                    break;
                case R.id.sqrtButton:
                    expression += ("sqrt");
                    break;
                case R.id.factorialButton:
                    expression += ("!");
                    break;

    }
            calcDisplay.setText(expression);
            answerDisplay.setText("");

    }





}

