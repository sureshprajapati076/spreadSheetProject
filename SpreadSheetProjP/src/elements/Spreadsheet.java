package elements;

public class Spreadsheet
{
	private static final int size = 15;				// The spreadsheet is a size x size square of cells
	private static Cell nullCell = new Cell();		// A fake cell we return when coordinates are out-of-bound

	private Cell[][] cells = new Cell[size][size];	// The fixed array of cells, each dimension from 1 to size, including size
	
	private void setCellsCoordinates()
	{
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
			{
				cells[row][col] = new Cell();
				cells[row][col].setCoordinates(row + 1, col + 1);
			}
	}
	private int columnWidth;
	
	private Boolean withinRange(int n) { return n > 0 && n <= size; }
	
	private String pad(String cellValue, int pads)	// Put cell's contents in string and pad with blanks to reach the desired length
	{
		StringBuffer contents = new StringBuffer();

		contents.append(cellValue);
		for (int length = contents.length(); length < pads; length++)
			contents.append(" ");

		return contents.toString();
		
	}

	public Spreadsheet(int width)
	{
		columnWidth = width;
		setCellsCoordinates();		
	}
	
	public Cell cell(int r, int c)					// Access the cell at coordinates r and c, if valid
	{
		if (withinRange(r) && withinRange(c))
			return cells[r-1][c-1];
		else
			return nullCell;		
	}
	
	public Cell getCell(int r, int c)				// Access he cell at coordinates r and c, if valid
	{
		return cell(r, c);
	}
	
	public String show()							// Returns a string that reflects the spreadsheet's contents, in a simple format
	{
		StringBuffer contents = new StringBuffer();

		for (int row = 1; row <= size; row++)
		{
			for (int col = 1; col <= size; col++)	// Show all the cells' contents in that row
				contents.append(pad(cell(row, col).value(), columnWidth));

			contents.append("\n");					// Go next line for next row
		}

		return contents.toString();
		
	}
	
	public String describe()						// Returns a string that reflects the spreadsheet's contents and formulas
	{
		StringBuilder contents = new StringBuilder();

		for (int row = 1; row <= size; row++)
		{
			for (int col = 1; col <= size; col++)	// Show all the cells' contents in that row
				contents.append(pad(cell(row, col).formula(), columnWidth));

			contents.append('\n');					// Go next line for next row
		}

		return contents.toString();
	}
	
	public String examine()									// Returns a textual list describing the contents of each cell
	{
		StringBuilder examination = new StringBuilder();

		for (int row = 1; row <= size; row++)
		{
			for (int col = 1; col <= size; col++)			// Examine all cells in that row
				if (!cell(row, col).isEmpty() && !cell(row,col).getContent().value().isEmpty()) {
					
					examination.append(cell(row, col).examine());
					
				}
		}

		return examination.toString();
		
	}

}
