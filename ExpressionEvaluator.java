import java.util.Stack;

public class ExpressionEvaluator {

    // Método para verificar si un carácter es un operador
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Método para obtener la precedencia de un operador
    public static int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    // Método para convertir la expresión infija a notación postfija
    public static String infixToPostfix(String expression) {
        String result = "";
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Si es un número, lo añadimos al resultado
            if (Character.isDigit(c)) {
                result += c;
            }
            // Si es un paréntesis abierto, lo añadimos a la pila
            else if (c == '(') {
                stack.push(c);
            }
            // Si es un paréntesis cerrado, vaciamos hasta encontrar el paréntesis abierto
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result += stack.pop();
                }
                stack.pop();
            }
            // Si es un operador
            else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    result += stack.pop();
                }
                stack.push(c);
            }
        }

        // Vaciamos la pila al final
        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }

    // Método para evaluar una expresión postfija
    public static double evaluatePostfix(String expression) {
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Si es un número, lo añadimos a la pila
            if (Character.isDigit(c)) {
                stack.push((double)(c - '0'));
            }
            // Si es un operador, aplicamos la operación
            else {
                double b = stack.pop();
                double a = stack.pop();
                switch (c) {
                    case '+':
                        stack.push(a + b);
                        break;
                    case '-':
                        stack.push(a - b);
                        break;
                    case '*':
                        stack.push(a * b);
                        break;
                    case '/':
                        stack.push(a / b);
                        break;
                    case '^':
                        stack.push(Math.pow(a, b));
                        break;
                }
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        String expression = "3+4*2/(1-5)^2^3";
        String postfix = infixToPostfix(expression);
        System.out.println("Expresión Postfija: " + postfix);
        double result = evaluatePostfix(postfix);
        System.out.println("Resultado: " + result);
    }
}
