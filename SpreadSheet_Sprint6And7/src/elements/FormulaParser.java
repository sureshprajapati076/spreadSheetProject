package elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FormulaParser {
	private static Spreadsheet spreadsheet;
	private static List<Float> operands = new ArrayList<>();
	private static List<NumericalOperator> operators = new ArrayList<>();

	public static void setSpreadSheet(Spreadsheet st) {
		spreadsheet = st;
	}

	public static float eval(String str) {

		System.out.println("----------------------------------------------------------------------------------------\n");

		System.out.println("Original Expression\n\n" + str);

		str = parseReferences(str);

		System.out.println("\nAfter Parsing References \n\n" + str);

		float result = parseFormula(str);

		System.out.println("\n\n\n\n\n----------------------------------------------------------------------------------------");
		return result;
	}


	private static float parseFormula(String expr) {


		boolean isFirstOperandNegative=false;
		if(expr.startsWith("-")) {
			isFirstOperandNegative=true;
			expr=expr.substring(1);
			
		}                               // for exprs like - -5 
		if(expr.startsWith("-")) {
			isFirstOperandNegative=false;
			expr=expr.substring(1);
			
		}
		

		operands = Arrays.asList(expr.split("[+*/\\-]+")).stream().map(x -> Float.valueOf(x)).collect(Collectors.toList());
		
		if(isFirstOperandNegative) operands.set(0, -operands.get(0));
		
		

		int operatorIndex = 0;
		for (int i = 0; i < expr.length(); i++) {
			if (expr.charAt(i) == '+') {
				operators.add(new Add());
				operatorIndex++;
				if (expr.charAt(i + 1) == '-') {
					operands.set(operatorIndex, -operands.get(operatorIndex));
					i++;
				}
			} else if (expr.charAt(i) == '-') {
				operators.add(new Subtract());
				operatorIndex++;
				if (expr.charAt(i + 1) == '-') {
					operands.set(operatorIndex, -operands.get(operatorIndex));
					i++;
				}
			} else if (expr.charAt(i) == '*') {
				operators.add(new Multiply());
				operatorIndex++;
				if (expr.charAt(i + 1) == '-') {
					operands.set(operatorIndex, -operands.get(operatorIndex));
					i++;
				}

			}

			else if (expr.charAt(i) == '/') {
				operators.add(new Divide());
				operatorIndex++;
				if (expr.charAt(i + 1) == '-') {
					operands.set(operatorIndex, -operands.get(operatorIndex));
					i++;
				}
			}
		}

		performDivisionFirst();

		performMultiplicationSecond();

		performAddAndSubtraction();

		return operands.get(0);
	}

	private static void doOperation(NumericalOperator operator) {
		int pos = operators.indexOf(operator);
		operator.append(new NumericalValue(operands.get(pos)));
		operator.append(new NumericalValue(operands.get(pos + 1)));

		float result = Float.parseFloat(operator.value());

		operands.set(pos, result);
		operands.remove(pos + 1);
		operators.remove(pos);

		displayExpression();

	}

	private static void performAddAndSubtraction() {

		System.out.println("\nAddition and Subtraction Operation ===========>");
		while (!operators.isEmpty())
			doOperation(operators.get(0));
	}

	private static void performMultiplicationSecond() {

		System.out.println("\nMultiplication Operation ===========>");

		for (int i = 0; i < operators.size(); i++) {
			NumericalOperator operator = operators.get(i);
			if (operator.getClass().getSimpleName().equals("Multiply")) {

				doOperation(operator);
				i--;

			}

		}

	}

	private static void performDivisionFirst() {
		
		
		

		System.out.println("\nDivision Operation ===========>");

		for (int i = 0; i < operators.size(); i++) {
			NumericalOperator operator = operators.get(i);
			if (operator.getClass().getSimpleName().equals("Divide")) {

				doOperation(operator);
				i--;
			}

		}

	}

	public static String parseReferences(String expr) {
		int posLeftParen, posRightParen, posComma;

		int row, col;
		while (expr.contains("[")) {

			posLeftParen = expr.indexOf("[");
			posRightParen = expr.indexOf("]");
			posComma = expr.indexOf(",");

			row = Integer.parseInt(expr.substring(posLeftParen + 1, posComma));

			col = Integer.parseInt(expr.substring(posComma + 1, posRightParen));

			String st1 = spreadsheet.cell(row, col).value();

			if (st1.isEmpty())
				st1 = "0";

			expr = expr.replace(expr.substring(posLeftParen, posRightParen + 1), st1);

		}

		return expr;
	}

	private static void displayExpression() {
		int i;
		System.out.print("\n\t\t");
		for (i = 0; i < operands.size() - 1; i++)
			System.out.print(String.format("%.2f%s", operands.get(i), operators.get(i).getSymbol()));
		System.out.print(String.format("%.2f", operands.get(i)));

		System.out.println();
	}

}
