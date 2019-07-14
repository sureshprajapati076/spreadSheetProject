package elements;

public class Director {
	private Spreadsheet spreadsheet;

	public Director(Spreadsheet sps) {
		spreadsheet = sps;
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
		writeInCellText(1, 1, "Airfare:");
		writeInCellNumber(1, 2, 485.15F);

		writeInCellText(1, 3, "");
		writeInCellText(1, 4, "What we pay to the airlines");

		writeInCellText(2, 1, "Taxi:");
		writeInCellNumber(2, 2, 118);
		writeInCellText(3, 1, "Rental Car:");
		writeInCellNumber(3, 2, 295.85F);
		writeInCellText(4, 1, "Hotel:");
		writeInCellNumber(4, 2, 431);
		writeInCellText(5, 1, "Meals:");
		writeInCellNumber(5, 2, 150);
		writeInCellText(5, 3, "");
		writeInCellText(5, 4, "This is all our meals");

		writeInCellText(7, 1, "Sub-total:");
		
		Add add = new Add();

		
		
		
		for(int i=1; i<=5; i++)
			add.append(new Reference(spreadsheet.cell(i, 2)));
		
		
		
		//add.append(new NumericalValue(1.0F));
		spreadsheet.cell(7, 2).setContent(add);
		cell(7,2).setContent(add);
	
		writeInCellText(8, 1, "Discount:");
		writeInCellNumber(8, 2, 0.15F);
		writeInCellText(9, 1, "Total:");
		
		
		Multiply multiply=new Multiply();
		multiply.append(new Reference(spreadsheet.cell(7, 2)));
		
		Subtract onePlusTaxFactor=new Subtract();
		multiply.append(onePlusTaxFactor);
		onePlusTaxFactor.append(new NumericalValue(1.0F));
		onePlusTaxFactor.append(new Reference(spreadsheet.cell(8, 2)));
		
		
		
		
		
		
		
		spreadsheet.cell(9, 2).setContent(multiply);
		
		

		writeInCellText(10, 1, "Partners: ");
		writeInCellNumber(10, 2, 4);
		writeInCellText(11, 1, "Months: ");
		writeInCellNumber(11, 2, 12);
		writeInCellText(12, 1, "Installments:");
		
		Divide division=new Divide();
		division.append(new Reference(spreadsheet.cell(9, 2)));
		division.append(new Reference(spreadsheet.cell(10, 2)));
		division.append(new Reference(spreadsheet.cell(11, 2)));
		
		spreadsheet.cell(12, 2).setContent(division);
		
		
		
		
		
		
		
		
		
	

	}

}
