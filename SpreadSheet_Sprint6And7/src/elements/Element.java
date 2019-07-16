package elements;

public abstract class Element 
{
	protected String getMargin(int size)
	{
		String stepSize = "    ";
		StringBuilder margin = new StringBuilder();

		for (int i = 1; i <= size; i++)
			margin.append(stepSize);

		return margin.toString();
		
	}
	public abstract String value();				// The result of what's in a cell or its contents
	
	public abstract String formula();			// The mathematical description of a cell's contents
	
	public abstract float data();				// The numerical value of a cell's contents
	
}
