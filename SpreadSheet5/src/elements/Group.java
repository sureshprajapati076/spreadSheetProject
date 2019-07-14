package elements;

import java.util.ArrayList;
import java.util.List;

public class Group extends NumericalOperator{
	private List<NumericalOperator> groupOperators = new ArrayList<NumericalOperator>();
	

	
	
	public void add(NumericalOperator numericalOperator) {
		groupOperators.add(numericalOperator);
	}




	@Override
	public String value() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public String formula() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public float data() {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
