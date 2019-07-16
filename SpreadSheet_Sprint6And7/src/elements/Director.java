package elements;

public class Director {
	private Spreadsheet spreadsheet;

	public Director(Spreadsheet sps) {
		spreadsheet = sps;
		FormulaParser.setSpreadSheet(sps);
	}

	public Spreadsheet getSpreadsheet() {
		return spreadsheet;
	}

	public void writeInCellText(int r, int c, String text) {
		if (text != null)
			spreadsheet.cell(r, c).setContent(new Text(text));
	}

	public void writeInCellNumber(int r, int c, float number) {
		spreadsheet.cell(r, c).setContent(new NumericalValue(number));
	}

	public Cell cell(int row, int col) // Get a reference to cell at (row,col)
	{
		return spreadsheet.cell(row, col);
	}

	public String contents() {
		return spreadsheet.show();
	}

	public String describe() {
		return spreadsheet.describe();
	}

	public String examine() {
		return spreadsheet.examine();
	}

	public void buildSample() // Build sample data for development purpose
	{

		setCell(1, 1, "\"BusFare\"");
		setCell(1, 2, "150.50F");
		setCell(2, 1, "\"TrainFare\"");
		setCell(2, 2, "100+25");
		setCell(3, 1, "\"MealCost\"");
		setCell(3, 2, "[1,2]-156");
		setCell(4, 1, "\"SubTotal\"");
		setCell(4, 2, "[1,2]+[2,2]-[3,2]-280");
		
		setCell(6,1,"\"SampleExpr1\"");
		setCell(6,2,"-[3,2]/-2/0.5*-3-[3,2]+3-8--8+9+[3,2]");

		
		
	}

	public void setCell(int r, int c, String string) {
		
		

		if (string != null && string.startsWith("\""))
			spreadsheet.cell(r, c).setContent(new Text(string.substring(1, string.length() - 1)));
		else if (string.isEmpty())
			spreadsheet.cell(r, c).setContent(new Text(string));
		else if (string.matches(".*[\\[\\]+\\*/-].*")) {
			string=string.replaceAll("\\s+"	, "");
			System.out.println("Parsing for Cell ["+r+","+c+"]");
			spreadsheet.cell(r, c).setFormula(string);
			spreadsheet.cell(r, c).setContent(new NumericalValue(FormulaParser.eval(string)));

		} else
			spreadsheet.cell(r, c).setContent(new NumericalValue(Float.parseFloat(string)));

	}

	

}
