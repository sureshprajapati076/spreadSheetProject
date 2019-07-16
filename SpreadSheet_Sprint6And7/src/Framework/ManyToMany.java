package Framework;

import java.util.ArrayList;
import java.util.List;

public class ManyToMany<Left, Right> extends Association<Left, Right> 
{
	protected CollectionOfMany<Left, Right> leftCollection = new CollectionOfMany<>();
	protected CollectionOfMany<Right, Left> rightCollection = new CollectionOfMany<>();

	@Override
	protected void buildLeftCollection()
	{
		setLeftCollection(leftCollection);
	}

	@Override
	protected void buildRightCollection()
	{
		setRightCollection(rightCollection);
	}

	public ManyToMany(String leftName, String rightName, String leftRole, String rightRole) 
	{
		super(leftName, rightName, leftRole, rightRole);
		buildCollections();															// Build either sides' collections according to the multiplicities
	}
	
	@Override
	public void unlink(Left left, Right right)
	{
		if (left != null && right != null)
		{
			leftCollection.unlink(left, right);
			rightCollection.unlink(right, left);
		}
	}

	@Override
	public void unlinkLeft(Left left)										// Delete all the links there might be from left object, if any, and on either sides
	{
		if (left != null)															// Get all the Right objects "left" is linked to
		{
			List<Right> rightItems = leftCollection.isLinkedToCollection(left);		// Get Collection of all the Right objects "left" is linked to
			for (Right right : rightItems)											// For each such "right"item...
				disconnect(left, right);											// ...simply disconnect them, on each side
		}
	}

	@Override
	public void unlinkRight(Right right)
	{
		if (right != null)															// Delete all links to/from "right"
		{
			List<Left> leftItems = rightCollection.isLinkedToCollection(right);		// Get Collection of all the Right objects "left" is linked to
			for (Left left : leftItems)												// For each such "right"item...
				disconnect(left, right);											// ...simply disconnect them
		}
	}

	@Override
	public List<Right> leftToRights(Left left)
	{
		List<Right> rightObjectsList = new ArrayList<>();							// Will return an empty List, in the worst-case scenario

		if (left != null)															// Shall I say it? Skipping null pointers...
			rightObjectsList = leftCollection.isLinkedToCollection(left);			// The leftCollection has the answer for us

		return rightObjectsList;													
	}

	@Override
	public List<Left> rightToLefts(Right right)
	{
		List<Left> rightObjects = new ArrayList<>();								// Will return an empty List, in the worst-case scenario

		if (right != null)
			rightObjects = rightCollection.isLinkedToCollection(right);				// The rightCollection has the answer for us

		return rightObjects;
	}

}
