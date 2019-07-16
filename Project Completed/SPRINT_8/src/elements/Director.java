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
		setCell(1, 1, "\"Airfare:\"");
		setCell(1, 2, "485.15F");

		setCell(1, 3, "");
		setCell(1, 4, "\"What we pay to the airlines\"");

		setCell(2, 1, "\"Taxi:\"");
		setCell(2, 2, "118");
		setCell(3, 1, "\"Rental Car:\"");
		setCell(3, 2, "295.85F");
		setCell(4, 1, "\"Hotel:\"");
		setCell(4, 2, "431");
		setCell(5, 1, "\"Meals:\"");
		setCell(5, 2, "150");
		setCell(5, 3, "");
		setCell(5, 4, "\"This is all our meals\"");

		setCell(7, 1, "\"Sub Total:\"");

		

		setCell(7, 2,"[1,2]+[2,2]+[3,2]+[4,2]+[5,2]");

		setCell(8, 1, "\"Discount:");
		setCell(8, 2, "0.15F");
		setCell(9, 1, "\"Total:");
		
		setCell(9,2,"[7,2]*(1-[8,2])");

	

		setCell(10, 1, "\"Partners: \"");
		setCell(10, 2, "4");
		setCell(11, 1, "\"Months:\" ");
		setCell(11, 2, "12");
		setCell(12, 1, "\"Installments:\"");

		setCell(12,2,"[9,2]/[10,2]/[11,2]");
		
		
		

	
		

		
		setCell(14, 1, "\"SampleExpr1\"");
		setCell(14, 2, "[4,2] * 4 / 0.5 + [7,7] + (25 + 0.23 - 50) * (([2,2] * (2 + 4 / [2,2]) - 15.25) - 2.3) * [4,2]");

	
		
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
