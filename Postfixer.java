package lab07;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

public class Postfixer {

	public static double InfixEvaluator(String line) {

		StringSplitter data = new StringSplitter(line);
		Stack<String> operators = new Stack<String>();
		Stack<Double> operands = new Stack<Double>();

		while (data.hasNext()) {

			String token = data.next();

			if (!isOperator(token)) {

				double tok = Double.parseDouble(token);
				operands.push(tok);

			} else {
				if (token.equals("(")) {
					operators.push(token);
				} else if (token.equals(")")) {

					while (!operators.peek().equals("(")) {

						operands.push(evaluate(operands.pop(), operands.pop(), operators.pop()));

					}

					operators.pop();

				} else {

					while (!operators.isEmpty() && comparePrecedence(operators.peek(), token)) {

						operands.push(evaluate(operands.pop(), operands.pop(), operators.pop()));

					}

					operators.push(token);

				}
			}
		}

		while (!operators.isEmpty()) {

			operands.push(evaluate(operands.pop(), operands.pop(), operators.pop()));

		}

		return operands.pop();
	}

	public static boolean comparePrecedence(String token1, String token2) {
		return (tokenPrecedence(token1) >= tokenPrecedence(token2));
	}

	public static boolean isOperator(String token) {

		return (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^")
				|| token.equals("(") || token.equals(")"));

	}

	public static int tokenPrecedence(String token) {

		if (token.equals("(")) {
			return 1;
		} else if (token.equals("+") || token.equals("-")) {
			return 2;
		} else if (token.equals("*") || token.equals("/")) {
			return 3;
		} else if (token.equals("^")) {
			return 4;
		} else {
			return -1;
		}
	}

	public static double evaluate(double operan1, double operan2, String operator) {
		double num = 0;
		if (operator.equals("+")) {
			num = operan1 + operan2;
		} else if (operator.equals("-")) {
			num = operan2 - operan1;
		} else if (operator.equals("*")) {
			num = operan1 * operan2;
		} else if (operator.equals("/")) {
			num = operan2 / operan1;
		} else if (operator.equals("^")) {
			num = Math.pow(operan1, operan2);
		}
		return num;
	}

	public static String PostfixConvertor(String line) {

		String answ = "";
		StringSplitter data = new StringSplitter(line);
		Stack<String> operators = new Stack<String>();

		while (data.hasNext()) {

			String token = data.next();

			if (!isOperator(token)) {

				answ += token;

			} else {
				if (token.equals("(")) {
					operators.push(token);
				} else if (token.equals(")")) {
					while (!operators.peek().equals("(")) {
						if (!operators.peek().equals("(") || !operators.peek().equals(")")) {
							answ += operators.pop();
						} else {
							operators.pop();
						}
					}
					operators.pop();
				} else {
					if (tokenPrecedence(token) < tokenPrecedence(operators.peek())) {
						answ += operators.pop();
						operators.push(token);
					} else {
						operators.push(token);
					}
				}
			}
		}

		return answ;

	}

	public static void main(String[] args) {

		if (InfixEvaluator("10 + 2") != 12)
			System.err.println("test1 failed --> your answer should have been 12");

		if (InfixEvaluator("10 - 2") != 8)
			System.err.println("test2 failed --> your answer should have been 8");

		if (InfixEvaluator("100 * 2 + 12") != 212)
			System.err.println("test3 failed --> your answer should have been 212");

		if (InfixEvaluator("100 * ( 2 + 12 )") != 1400)
			System.err.println("test4 failed --> your answer should have been 1400");

		if (InfixEvaluator("100 * ( 2 + 12 ) / 14") != 100)
			System.err.println("test5 failed --> your answer should have been 100");

		System.out.println("Testing Done!!!");

		if (!PostfixConvertor(new String("(4+5)")).equals("45+"))
			System.err.println("test1 failed --> should have been 45+");

		if (!PostfixConvertor(new String("((4+5)*6)")).equals("45+6*"))
			System.err.println("test2 failed --> should have been 45+6*");

		if (!PostfixConvertor(new String("((4+((5*6)/7))-8)")).equals("456*7/+8-"))
			System.err.println("test3 failed --> should have been 456*7/+8-");

		if (!PostfixConvertor(new String("((4+5*(6-7))/8)")).equals("4567-*+8/"))
			System.err.println("test4 failed --> should have been 4567-*+8/");

		if (!PostfixConvertor(new String("(9+(8*7-(6/5^4)*3)*2)")).equals("987*654^/3*-2*+"))
			System.err.println("test5 failed --> should have been 987*654^/3*-2*+");

		System.out.println("Testing Done!!!");

	}

}
