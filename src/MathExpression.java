import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MathExpression {
    private StringBuilder expression;

    MathExpression() {
        expression = new StringBuilder();
    }

    void append(String text) {
        expression.append(text);
    }

    void clear() {
        expression.delete(0, expression.length());
    }

    void set(String text) {
        clear();
        expression.append(text);
    }

    void deleteLast() {
        try {
            expression.delete(expression.length() - 1, expression.length());
        } catch (StringIndexOutOfBoundsException e) {}
    }

    void evaluate() {
        Expression exp = new ExpressionBuilder(expression.toString()).build();
        double result = exp.evaluate();
        if (result == Math.floor(result)) {
            set(Long.toString(Math.round(result)));
        } else {
            set(Double.toString(result));
        }
    }

    public String toString() {
        return expression.toString();
    }
}
