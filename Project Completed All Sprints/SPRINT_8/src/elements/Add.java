package elements;

public class Add extends NumericalOperator {

	@Override
	public String value() {
		float value = 0;
		for (Contents r : operands) {
			value += Float.parseFloat(r.value());
		}

		return String.valueOf(value);
	}

	@Override
	public String formula() {
		String formula = "";
		for (Contents r : operands) {
			formula += r.formula() + "+";
		}

		return "(" + formula.substring(0, formula.length() - 1) + ")";
	}

	@Override
	public float data() {
		return 0.01F;
	}

	public String getSymbol() {
		return " + ";
	}

}
