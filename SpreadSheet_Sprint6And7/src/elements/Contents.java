package elements;

public abstract class Contents extends Element
{
	public Contents(Cell cell)
	{
		if (cell != null)
			setCellOwner(cell);
	}
	
	public Contents()
	{
		
	}
	
	public void setCellOwner(Cell cl)
	{
		if (cl != null)
		{
			Associations.cellIsMadeOfContents.link(cl, this);				// Belongs to that Cell now
		}
	}

	public String getCoordinates()											// If belonging to a Cell, return its coordinates
	{
		String coordinates = new String();

		Cell cell = Associations.cellIsMadeOfContents.rightToLeft(this);
		if (cell != null)
			coordinates += cell.getCoordinates();

		return coordinates;
		
	}
	
	
   
	
}
