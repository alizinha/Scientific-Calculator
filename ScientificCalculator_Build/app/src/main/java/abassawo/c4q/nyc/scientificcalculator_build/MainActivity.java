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
import java.io.IOException;

//import java.text.DecimalFormat;




import com.fathzer.soft.javaluator.DoubleEvaluator;

public class MainActivity extends Activity{

    private String expression;
    TextView calcDisplay;
    private double result;
    String resultStr;
    String answer;
    boolean clickable;
    DoubleEvaluator expressionEvaluator;

    //TODO : Implement functionality that clears text after err.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button equalsButton = (Button) findViewById(R.id.equalsButton);
        //final DecimalFormat doubleFormat = new DecimalFormat("0.00");
        expression = "";
        calcDisplay = (TextView) findViewById(R.id.tvResult);
        final TextView answerDisplay = (TextView) findViewById(R.id.tvAnswer);
        expressionEvaluator = new DoubleEvaluator();
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        result = expressionEvaluator.evaluate(expression);
                        resultStr = String.valueOf(result);
                        Log.d("test string", "resultStr");
                        if (resultStr.endsWith(".0")) {
                            answer = resultStr.substring(0, resultStr.indexOf('.')); //Integer cases!
                            answerDisplay.setText(answer);
                            // after calculating,the next onclick of a digit should clear screen content for added convenience.
//                            reset();
                        } else {
                            answerDisplay.setText("" + result); //perhaps format this for maximum # of digits after decimal place. "0.000"
                            // clear content for new calculations, unless an operation sign is selected. see comment above.
                        }
                    } catch (IllegalArgumentException e){
                        answerDisplay.setText("Err");
                    }
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
            case R.id.decimalButton:
                expression += ("" + '.');
                calcDisplay.append("" + '.');
                break;
            case R.id.openParensButton:
                expression += ("" + '(');
                calcDisplay.append("" + '(');
                break;
            case R.id.closeParensButton:
                expression += ("" + ')');
                calcDisplay.append("" + ')');
                break;
            case R.id.cancelButton:
                expression = "";
                calcDisplay.setText("");
                break;
            case R.id.zeroButton:
                expression += ("" + 0);
                calcDisplay.append("" + 0);
                break;
            case R.id.oneButton:
                expression += ("" + 1);
                calcDisplay.append("" + 1);
                break;
            case R.id.twoButton:
                expression += ("" + 2);
                calcDisplay.append("" + 2);
                break;
            case R.id.threeButton:
                expression += ("" + 3);
                calcDisplay.append("" + 3);
                break;
            case R.id.fourButton:
                expression += ("" + 4);
                calcDisplay.append("" + 4);
                break;
            case R.id.fiveButton:
                expression += ("" + 5);
                calcDisplay.append("" + 5);
                break;
            case R.id.sixButton:
                expression += ("" + 6);
                calcDisplay.append("" + 6);
                break;
            case R.id.sevenButton:
                expression += ("" + 7);
                calcDisplay.append("" + 7);
                break;
            case R.id.eightButton:
                expression += ("" + 8);
                calcDisplay.append("" + 8);
                break;
            case R.id.nineButton:
                expression += ("" + 9);
                calcDisplay.append("" + 9);
                break;
            case R.id.minusButton:
                expression += ("" + " -");
                calcDisplay.append("" + "- ");
//                reset();
                break;

            case R.id.plusButton:                  //OPERATIONS
                Button plusButton = (Button) findViewById(R.id.plusButton);
                //plusButton.setClickable(false);
                expression += ("" + "+ ");
                calcDisplay.append("" + "+ ");
//                reset();
                break;

            case R.id.xButton:
                Button xButton = (Button) findViewById(R.id.xButton);
                // xButton.setClickable(fal
                // se);
                expression += ("" + '*');
                calcDisplay.append("" + "* ");
//                reset();
                break;
            case R.id.divButton:
                Button divButton = (Button) findViewById(R.id.divButton);
                //divButton.setClickable(false);
                expression += ("" + '/');
                calcDisplay.append("" + "÷ ");
//                reset();
                break;
            case R.id.piButton:
                Button piButton = (Button) findViewById(R.id.piButton);
                expression += ("" + 3.14159 );
                calcDisplay.append("pi");
            case R.id.sinButton:
                Button sinButton = (Button) findViewById(R.id.sinButton);
                expression += ("sin");
                calcDisplay.append("sin");
            case R.id.cosButton:
                Button cosButton = (Button) findViewById(R.id.cosButton);
                expression += ("cos");
                calcDisplay.append("cos");
        }
    }
//    public void reset() {
//        clickable = true;
//    }
}
