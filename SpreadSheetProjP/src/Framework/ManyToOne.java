package Framework;

import java.util.ArrayList;
import java.util.List;

public class ManyToOne<Left, Right> extends Association<Left, Right> 
{
	protected CollectionOfOne<Left, Right> leftCollection = new CollectionOfOne<>();
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

	public ManyToOne(String leftName, String rightName, String leftRole, String rightRole) 
	{
		super(leftName, rightName, leftRole, rightRole);
		buildCollections();
	}
	
	@Override
	public void link(Left left, Right right)		
	{ 
		if (left != null && right != null )							// Skip all null pointers, shockingly
		{
			Right previousRight = leftCollection.isLinkedTo(left);	// Locate the Right object "left" may be already linked to
			if (previousRight != null)								// If there is one...
				unlink(left, previousRight);						// ...unlink it since "left" cannot be connected to more than one Right object
			super.link(left, right);								// Now that old links are removed we can add this new one without violating the multiplicity policy
		}
	}
	
	@Override
	public void unlinkLeft(Left left)						// Delete the single link there might be from left object, if any, and on either sides
	{
		if (left != null)
		{
			Right right = leftCollection.isLinkedTo(left);			// Find out what Right object "left" may be linked to
			if (right != null)										// If it is linked to a Right object
				unlink(left, right);								// ...unlink them the usual way
		}
	}

	@Override
	public void unlinkRight(Right right)	
	{ 
		if (right != null)															// This is the easy case: we'll get all the objects "right" is linked to
		{
			List<Left> leftItems = rightCollection.isLinkedToCollection(right);		// Get Collection of all the Right objects "left" is linked to
			for (Left left : leftItems)												// For each such "right"item...
				disconnect(left, right);											// ...simply disconnect them
		}
	}

	@Override
	public Right leftToRight(Left left)
	{
		Right right = null;

		if (left != null)
			right = leftCollection.isLinkedTo(left);

		return right;
	}

	@Override
	public List<Left> rightToLefts(Right right)							// Returns a the Left objects "right" is linked to
	{
		List<Left> rightObjects = new ArrayList<>();								// Return an empty List, as the worst-case scenario

		if (right != null)
			rightObjects = rightCollection.isLinkedToCollection(right);				// The rightCollection has the answer for us

		return rightObjects;
	}

	@Override
	public List<Right> leftToRights(Left left)		// Returns a list of Right objects "left" is linked to: 0, or 1 such objects in this case
	{
		List<Right> listWithOneRightObject = new ArrayList<>();	// List to hold such results

		if (left != null)
		{
			Right right = leftToRight(left);				// By definition, there will be at most one Right object linked to "left"
			if (right != null)									// If there is one...
				listWithOneRightObject.add(right);				// ...add it to the list
		}

		return listWithOneRightObject;
	}




}
