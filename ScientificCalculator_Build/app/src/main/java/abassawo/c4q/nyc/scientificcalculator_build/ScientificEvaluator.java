package abassawo.c4q.nyc.scientificcalculator_build;

import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.AbstractVariableSet;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Operator;
import java.util.Iterator;
import com.fathzer.soft.javaluator.Parameters;


/**
 * Created by c4q-Abass on 5/20/15.
 */
public class ScientificEvaluator extends AbstractEvaluator<Double>{

    /** The negate unary operator.*/
//    public final static Operator NEGATE = new Operator("!", 1, Operator.Associativity.RIGHT, 3);
//    /** The logical AND operator.*/
//    private static final Operator AND = new Operator("&&", 2, Operator.Associativity.LEFT, 2);


    private static final Operator factorial = new Operator("!1", 2, Operator.Associativity.LEFT, 3);
    private static final Function SQRT = new Function("sqrt", 1);




    /** The logical OR operator.*/
    public final static Operator OR = new Operator("||", 2, Operator.Associativity.LEFT, 1);

    //private static Parameters params= DoubleEvaluator.getDefaultParameters(); // Gets the default DoubleEvaluator's parameters
    Parameters params = new Parameters();
//    params.add(DoubleEvaluator.PLUS);
//    params.add(DoubleEvaluator.MINUS);
//    params.add(DoubleEvaluator.MULTIPLY);
//    params.add(DoubleEvaluator.DIVIDE);
//    params.add(DoubleEvaluator.NEGATE);


    @Override
    protected Double toValue(String s, Object o) {
        return null;
    }

    public ScientificEvaluator(Parameters params){

        super(params);

    }







}
