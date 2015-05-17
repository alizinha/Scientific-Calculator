package abassawo.c4q.nyc.scientificcalculator_build;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;




import com.fathzer.soft.javaluator.DoubleEvaluator;

public class MainActivity extends Activity{

    private String expression;
    TextView calcDisplay;
    private double result;
    private double answer;
    private TextView answerDisplay;
    String resultStr;
    DoubleEvaluator expressionEvaluator;

    //TODO : Implement functionality that clears text after err.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Button equalsButton = (Button) findViewById(R.id.equalsButton);
        final DecimalFormat doubleFormat = new DecimalFormat("0.00");
        expression = "";

        calcDisplay = (TextView) findViewById(R.id.tvExpression);
        answerDisplay = (TextView) findViewById(R.id.tvAnswer);
        expressionEvaluator = new DoubleEvaluator();
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        result = expressionEvaluator.evaluate(expression);
                        resultStr = doubleFormat.format(result); //avoid excessive decimals

                        resultStr = String.valueOf(result);
                        Log.d("test string", "resultStr");
                        if (resultStr.endsWith(".0")) {
                            resultStr = resultStr.substring(0, resultStr.indexOf('.')); //Integer cases!
                            answerDisplay.setText(" = " + resultStr);
                        } else {
                            answerDisplay.setText("" + resultStr); //perhaps format this for maximum # of digits after decimal place. "0.000"
                            // clear content for new calculations, unless an operation sign is selected. see comment above.
                        }

                    } catch (IllegalArgumentException e) {
                        answerDisplay.setText("Err");
                    }
                expression = String.valueOf(resultStr); ///


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

    public void onButtonClick(View v) {
            switch (v.getId()) {
                case R.id.delButton:
                    if (expression.length() >= 1) //or use scanner
                    expression = expression.replace(expression.charAt(expression.length() - 1), ' ');
                   // calcDisplay.setText(expression);
                    break;
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
                case R.id.cancelButton:
                    expression = "";
                    //calcDisplay.setText(expression);
                    answerDisplay.setText("");
                    break;
                case R.id.ansButton:
                    expression += resultStr;
                    Log.d(" ans button working?", expression);
                case R.id.zeroButton:
                    expression += ("" + 0);
                  //  calcDisplay.setText(expression);
                    break;
                case R.id.oneButton:
                    expression += ("" + 1);
                  //  calcDisplay.setText(expression);
                    break;
                case R.id.twoButton:
                    expression += ("" + 2);
                   // calcDisplay.setText(expression);
                    break;
                case R.id.threeButton:
                    expression += ("" + 3);
                    //calcDisplay.append("" + 3);
                    break;
                case R.id.fourButton:
                    expression += ("" + 4);
                   // calcDisplay.setText(expression);
                    break;
                case R.id.fiveButton:
                    expression += ("" + 5);
                    //calcDisplay.setText(expression);
                    break;
                case R.id.sixButton:
                    expression += ("" + 6);
                    //calcDisplay.setText(expression);
                    break;
                case R.id.sevenButton:
                    expression += ("" + 7);
                    //calcDisplay.setText(expression);
                    break;
                case R.id.eightButton:
                    expression += ("" + 8);
                    //calcDisplay.setText(expression);
                    break;
                case R.id.nineButton:
                    expression += ("" + 9);
                    //calcDisplay.setText(expression);
                    break;
                case R.id.minusButton:
                    expression += ("" + " -");
                    //calcDisplay.setText(expression);
                    break;
                case R.id.plusButton:
                    expression += ("" + "+ ");
                   // calcDisplay.setText(expression);
                    break;
                case R.id.xButton:
                    expression += ("" + '*');
                   // calcDisplay.setText(expression);
                    break;
                case R.id.divButton:
                    expression += ("" + '/');
                    break;
                case R.id.piButton:
                    expression += ("" + 3.14159);
                    //calcDisplay.setText(expression);
                    break;
                case R.id.sinButton:
//                    Button sinButton = ()
                    expression += ("sin");
                    //calcDisplay.setText(expression);
                    break;
                case R.id.cosButton:
                    expression += ("cos");
                    //calcDisplay.setText(expression);
                    break;
                case R.id.tanButton:
                    expression+=("tan");
                    //calcDisplay.setText(expression);
                    break;

            }

        calcDisplay.setText(expression);
    }
}
