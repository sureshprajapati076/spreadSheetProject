package Framework;
import java.util.List;

public abstract class Association<Left, Right> 
{

	public enum Multiplicity
	{
		ZeroOrOne, ZeroOrMore
	};
	
	private Collection<Left, Right> leftCollection = null;	// Left Collection, points to the collection built by the derived classes
	private Collection<Right, Left> rightCollection = null;	// Right Collection, points to the collection built by the derived classes
	
	private String leftToRightNameString = new String();	// The user's plain-English name of that Association, from left to right
	private String rightToLeftNameString = new String();	// The user's plain-English name of this Association, from right to left

	private String leftRoleName = new String();				// Role on the left side of the Association, if any
	private String rightRoleName = new String();			// Role on the right side of the Association, if any

	protected abstract void buildLeftCollection();			// Build Left collection, according to multiplicity requirements
	protected abstract void buildRightCollection();			// Build Right Collection, according to multiplicity requirement

	protected void setLeftToRightName(String nm) 
	{ 
		if (nm != null) 
		{
			leftToRightNameString = nm;
			leftToRightNameString += " ";
		}
	}
	
	protected void setRightToLeftName(String nm) 
	{ 
		if (nm != null) 
		{
			rightToLeftNameString = nm;
			rightToLeftNameString += " "; 
		}
	}

	protected void setLeftCollection(Collection<Left, Right> collection)		// Set the Left Collection build by the subclass, for this one to refer to
	{
		leftCollection = collection;
	}

	protected void setRightCollection(Collection<Right, Left> collection)		// Set the Right Collection build by the subclass, for this one to refer to
	{
		rightCollection = collection;
	}
	
	protected void buildCollections()											// Build the Collections according to multiplicity 							
	{
		buildLeftCollection();													// ...put a Collection on the left
		buildRightCollection();													// ...put a Collection on the right
	}


	protected void disconnect(Left left, Right right)							// Delete link between left and right. NOTE: this is assuming they really are linked to each other
	{
		if (left != null && right != null && leftCollection != null && rightCollection != null)
		{
			leftCollection.unlink(left, right);									// Unlink left from right
			rightCollection.unlink(right, left);								// Unlink right from left
		}
	}
	
	protected String getClassName()
	{
		return this.getClass().getSimpleName();
	}
	
	public Association(String leftName, String rightName, String leftRole, String rightRole)
	{
		setLeftToRightName(leftName != null ? leftName : "");
		setRightToLeftName(rightName != null ? rightName : "");

		leftRoleName = leftRole != null ? leftRole : "";
		leftRoleName += " ";
		rightRoleName = rightRole != null ? rightRole : ""; 
		rightRoleName += " ";
	}

	public String leftToRightName() { return leftToRightNameString; }
	public String rightToLeftName() { return rightToLeftNameString; }

	public String leftRole() { return leftRoleName; }
	public String rightRole() { return rightRoleName; }

	public String rightMultiplicityName() { return leftCollection != null ? leftCollection.getMultiplicityName() : ""; }
	public String leftMultiplicityName() { return rightCollection != null ? rightCollection.getMultiplicityName() : ""; }

	public String report()												// Association's self-describing method 
	{
		StringBuilder rep = new StringBuilder(); 

		if (leftCollection != null  && rightCollection != null)
		{
			rep.append(getClassName() + ":\n");
			rep.append("A ").append(leftRole()).append(leftToRightName()).append(rightMultiplicityName()).append(rightRole()).append("\n");
			rep.append("A ").append(rightRole()).append(rightToLeftName()).append(leftMultiplicityName()).append(leftRole()).append("\n");
		}

		return rep.toString();
	}
	
	public String toString()
	{
		return report();
	}
	
	public void link(Left left, Right right)							// Create links between left and right objects
	{
		if (left != null && right != null)								// Skip null references
		{
			leftCollection.link(left, right);							// Set link from left to right
			rightCollection.link(right, left);							// Set link from right to left
		}
	}
	
	public boolean isLinked(Left left, Right right)						// Returns true if "left" AND "right" are linked, false if not
	{
		boolean isLinked = false;										// Assume there is no link until the opposite has been established

		if (left != null && right != null && leftCollection != null && rightCollection != null)			
			isLinked =  leftCollection.isLinked(left, right) && rightCollection.isLinked(right, left);
				
		return isLinked;
	}

	public Right leftToRight(Left left) { return null; }				// Return the Left object right is linked to, or NULL if none, or the query makes no sense
	public abstract List<Right> leftToRights(Left left);				// Returns the list of 0, 1, or more Right objects "left" is linked to.

	public Left rightToLeft(Right right) { return null; }				// Return the Left object right is linked to, or NULL if none, or the query makes no sense
	public abstract List<Left> rightToLefts(Right right);				// Returns the list of 0, 1, or more Left objects "right" is linked to.

	public void unlink(Left left, Right right)							// Delete whatever link there might be between left and right, if any
	{
		if (left != null && right != null && leftCollection != null && rightCollection != null)			// Skip null references
		{
			if (leftCollection.isLinked(left, right))					// If "left" and "right" are indeed linked
				leftCollection.unlink(left, right);						// ...well, disconnect them

			if (rightCollection.isLinked(right, left))					// If "right" and "left" are indeed linked
				rightCollection.unlink(right, left);					// ...well, disconnect them
		}
	}

	public abstract void unlinkLeft(Left left);							// Delete whatever link(s) there might be from left object, if any

	public abstract void unlinkRight(Right right);				// Delete whatever link(s) there might be from right object, if any

}
