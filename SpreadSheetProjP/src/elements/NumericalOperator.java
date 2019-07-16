package elements;

import java.util.ArrayList;
import java.util.List;

public abstract class NumericalOperator extends Contents {
	List<Contents> operands = new ArrayList<>();

	
	
	public void append(Contents c) {
		operands.add(c);
		
	}
	public abstract String getSymbol();

}
