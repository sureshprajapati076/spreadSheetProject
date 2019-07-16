package elements;

public class Subtract extends NumericalOperator {

	@Override
	public String value() {
		float value = Float.parseFloat(operands.get(0).value());

		for (int i = 1; i < operands.size(); i++) {
			value -= Float.parseFloat(operands.get(i).value());
		}

		return String.valueOf(value);
	}

	@Override
	public String formula() {
		String formula = "";
		for (Contents r : operands) {
			formula += r.formula() + "-";
		}

		return "(" + formula.substring(0, formula.length() - 1) + ")";
	}

	@Override
	public float data() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getSymbol() {
		return " - ";
	}

}
