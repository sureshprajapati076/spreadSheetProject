package elements;

public class Application
{

	public static void main(String[] args) 
	{
	
		Spreadsheet spreadsheet = new Spreadsheet(15);		// Create the spreadsheet we'll work on, with a column width to display
		Director director = new Director(spreadsheet);		// Give it to the facade
		
		director.buildSample();								// Build sample cells, for testing
		
		
		
		
		System.out.println(director.contents());			// Quick view of contents
		//System.out.println(director.examine());				// Quick view of formulas
		
	
		
		
	}

}

