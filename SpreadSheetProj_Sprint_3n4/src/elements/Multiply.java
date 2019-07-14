package elements;

import java.util.ArrayList;
import java.util.List;

public class Multiply extends NumericalOperator{
	List<Contents> multiplyOperands = new ArrayList<>();
	public Multiply() {
		
	}
	public void append(Contents c) {
		multiplyOperands.add(c);
	}
	
	
	
	
	
	
	
	
	@Override
	public String value() {
		float value = 1.0F;
		for(Contents r: multiplyOperands) {
			value *= Float.parseFloat(r.value());
		}
		
		//return value+"";
		return String.valueOf(value);
	}
	@Override
	public String formula() {
		String formula = "";
		for(Contents r: multiplyOperands) {
			formula += r.formula()+ "*";
		}
		
		//return value+"";
		return "("+formula.substring(0, formula.length()-1)+")";
	}
	@Override
	public float data() {
		// TODO Auto-generated method stub
		return 0;
	}

}
