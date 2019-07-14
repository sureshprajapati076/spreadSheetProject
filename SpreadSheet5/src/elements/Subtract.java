package elements;

import java.util.ArrayList;
import java.util.List;

public class Subtract extends NumericalOperator{
	List<Contents> subtractOperands = new ArrayList<>();
	public Subtract() {
		
	}
	public void append(Contents c) {
		subtractOperands.add(c);
	}
	
	
	@Override
	public String value() {
		float value = Float.parseFloat(subtractOperands.get(0).value());

		for(int i=1;i<subtractOperands.size();i++) {
			value -= Float.parseFloat(subtractOperands.get(i).value());
		}
		
		
		return String.valueOf(value);
	}
	@Override
	public String formula() {
		String formula = "";
		for(Contents r: subtractOperands) {
			formula += r.formula()+ "-";
		}
		
		
		return "("+formula.substring(0, formula.length()-1)+")";
	}
	@Override
	public float data() {
		// TODO Auto-generated method stub
		return 0;
	}

}
