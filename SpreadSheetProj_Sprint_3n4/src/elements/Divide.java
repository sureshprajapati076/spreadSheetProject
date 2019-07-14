package elements;

import java.util.ArrayList;
import java.util.List;

public class Divide extends NumericalOperator{
	List<Contents> divisionOperands = new ArrayList<>();
	public Divide() {
		
	}
	public void append(Contents c) {
		divisionOperands.add(c);
	}
	
	
	
	
	
	
	
	
	@Override
	public String value() {
		float value = Float.parseFloat(divisionOperands.get(0).value());

		for(int i=1;i<divisionOperands.size();i++) {
			value /= Float.parseFloat(divisionOperands.get(i).value());
		}
		
		
		return String.valueOf(value);
	}
	@Override
	public String formula() {
		String formula = "";
		for(Contents r: divisionOperands) {
			formula += r.formula()+ "/";
		}
		
		
		return "("+formula.substring(0, formula.length()-1)+")";
	}
	@Override
	public float data() {
		// TODO Auto-generated method stub
		return 0;
	}

}
