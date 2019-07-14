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
		
		Add add = new Add();

		add.append(new NumericalValue(12.5F));
		
		for(int i=1; i<=5; i++)
			add.append(new Reference(spreadsheet.cell(i, 2)));
		
	//	spreadsheet.cell(7, 2).setContent(add);
		
		add.append(new NumericalValue(12.5F));
		cell(7,2).setContent(add);

		setCell(8, 1, "\"Discount:"); 
		setCell(8, 2, "0.15F");
		setCell(9, 1, "\"Total:");
		
		
		Multiply multiply=new Multiply();
		multiply.append(new Reference(spreadsheet.cell(7, 2)));
		
		Subtract onePlusTaxFactor=new Subtract();
		multiply.append(onePlusTaxFactor);
		onePlusTaxFactor.append(new NumericalValue(1.0F));
		onePlusTaxFactor.append(new Reference(spreadsheet.cell(8, 2)));
	

		spreadsheet.cell(9, 2).setContent(multiply);
		
	
		setCell(10, 1, "\"Partners: \"");
		setCell(10, 2, "4");
		setCell(11, 1, "\"Months:\" ");
		setCell(11, 2, "12");
		setCell(12, 1, "\"Installments:\"");
		
		Divide division=new Divide();
		division.append(new Reference(spreadsheet.cell(9, 2)));
		division.append(new Reference(spreadsheet.cell(10, 2)));
		division.append(new Reference(spreadsheet.cell(11, 2)));
		
		spreadsheet.cell(12, 2).setContent(division);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		writeInCellText(1, 1, "Airfare:");
//		writeInCellNumber(1, 2, 485.15F);
//
//		writeInCellText(1, 3, "");
//		writeInCellText(1, 4, "What we pay to the airlines");
//
//		writeInCellText(2, 1, "Taxi:");
//		writeInCellNumber(2, 2, 118);
//		writeInCellText(3, 1, "Rental Car:");
//		writeInCellNumber(3, 2, 295.85F);
//		writeInCellText(4, 1, "Hotel:");
//		writeInCellNumber(4, 2, 431);
//		writeInCellText(5, 1, "Meals:");
//		writeInCellNumber(5, 2, 150);
//		writeInCellText(5, 3, "");
//		writeInCellText(5, 4, "This is all our meals");
//
//		writeInCellText(7, 1, "Sub-total:");
//		
//		Add add = new Add();
//
//		
//		
//		for(int i=1; i<=5; i++)
//			add.append(new Reference(spreadsheet.cell(i, 2)));
//		
//		spreadsheet.cell(7, 2).setContent(add);
//		cell(7,2).setContent(add);
//
//		writeInCellText(8, 1, "Discount:"); 
//		writeInCellNumber(8, 2, 0.15F);
//		writeInCellText(9, 1, "Total:");
//		
//		
//		Multiply multiply=new Multiply();
//		multiply.append(new Reference(spreadsheet.cell(7, 2)));
//		
//		Subtract onePlusTaxFactor=new Subtract();
//		multiply.append(onePlusTaxFactor);
//		onePlusTaxFactor.append(new NumericalValue(1.0F));
//		onePlusTaxFactor.append(new Reference(spreadsheet.cell(8, 2)));
//		
//	
//		spreadsheet.cell(9, 2).setContent(multiply);
//		
//	
//		writeInCellText(10, 1, "Partners: ");
//		writeInCellNumber(10, 2, 4);
//		writeInCellText(11, 1, "Months: ");
//		writeInCellNumber(11, 2, 12);
//		writeInCellText(12, 1, "Installments:");
//		
//		Divide division=new Divide();
//		division.append(new Reference(spreadsheet.cell(9, 2)));
//		division.append(new Reference(spreadsheet.cell(10, 2)));
//		division.append(new Reference(spreadsheet.cell(11, 2)));
//		
//		spreadsheet.cell(12, 2).setContent(division);
		
	
	}

	private void setCell(int r, int c, String string) {
		
		
			if (string != null && string.startsWith("\""))
				spreadsheet.cell(r, c).setContent(new Text(string.substring(1, string.length()-1)));
			else if(string.isEmpty())
				spreadsheet.cell(r, c).setContent(new Text(string));
			else
				spreadsheet.cell(r, c).setContent(new NumericalValue(Float.parseFloat(string)));
			
			
			
			
			
		}
		
	

}
