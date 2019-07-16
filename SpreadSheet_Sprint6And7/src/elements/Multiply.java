package elements;

public class Multiply extends NumericalOperator{
	
	
	
	@Override
	public String value() {
		float value = 1.0F;
		for(Contents r: operands) {
			value *= Float.parseFloat(r.value());
		}
		
		
		return String.valueOf(value);
	}
	@Override
	public String formula() {
		String formula = "";
		for(Contents r: operands) {
			formula += r.formula()+ "*";
		}
		
		
		return "("+formula.substring(0, formula.length()-1)+")";
	}
	@Override
	public float data() {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getSymbol() {
		return " * ";
	}


}
