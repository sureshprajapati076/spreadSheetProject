package elements;

public class NumericalValue extends Contents {
	
	private float value;

	@Override
	public String value() {
		// TODO Auto-generated method stub
		return value+"";
	}

	@Override
	public String formula() {
		return value();
	}

	@Override
	public float data() {
		return value;
	}

	public NumericalValue(float number) {
		this.value = number;
	}
}
