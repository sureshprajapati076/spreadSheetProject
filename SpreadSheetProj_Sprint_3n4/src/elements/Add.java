package elements;

import java.util.ArrayList;
import java.util.List;

public class Add extends NumericalOperator{
	List<Contents> addOperands = new ArrayList<>();  
	public Add() {
		
	}
	
	
	public void append(Contents c) {
		addOperands.add(c);
	}


	@Override
	public String value() {
		float value = 0;
		for(Contents r: addOperands) {
			value += Float.parseFloat(r.value());
		}
		
		
		return String.valueOf(value);
	}


	@Override
	public String formula() {
		String formula = "";
		for(Contents r: addOperands) {
			formula += r.formula()+ "+";
		}
		
		
		return "("+formula.substring(0, formula.length()-1)+")";
	}


	@Override
	public float data() {
		return 0.01F;
	}


	

}
