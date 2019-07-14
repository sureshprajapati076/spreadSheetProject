package Framework;

import java.util.ArrayList;
import java.util.List;

public class OneToMany<Left, Right> extends Association<Left, Right> 
{
	protected CollectionOfMany<Left, Right> leftCollection = new CollectionOfMany<Left, Right>();		// We build it right away, as this is possible in Java
	protected CollectionOfOne<Right, Left> rightCollection = new CollectionOfOne<Right, Left>();		// We build it right away, as this is possible in Java
	
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
	
	public OneToMany(String leftName, String rightName, String leftRole, String rightRole) 
	{
		super(leftName, rightName, leftRole, rightRole);
		buildCollections();
	}

	public OneToMany(String leftName) 
	{
		this(leftName, "", "", "");
		buildCollections();
	}

	@Override
	public void link(Left left, Right right)								// Link "left" with "right", making sure "right" will be linked to NO MORE than one Left object
	{																			
		if (left != null && right != null )									// Skip all NULL pointers, shockingly
		{
			Left previousLeft = rightCollection.isLinkedTo(right);			// Locate the Left object "right" may be already linked to
			
			if (previousLeft != null)										// If there is one...
				unlink(previousLeft, right);								// ...unlink it

			super.link(left, right);										// Now that old links are removed we can add this new one without violating the multiplicity policy
		}
	}

	@Override
	public 	void unlinkLeft(Left left)										// Delete whatever link(s) there might be from left object, if any. 
	{																				
		if (left != null )															// This is the easy case: we'll get all the objects "left" is linked to
		{
			List<Right> rightItems = leftCollection.isLinkedToCollection(left);		// Get Collection of all the Right objects "left" is linked to
			for (Right right : rightItems)											// For each such "right"item...
				disconnect(left, right);											// ...simply disconnect them
		}
	}


	@Override
	public void unlinkRight(Right right)										// Delete the single link there might be from right object, if any, and on either sides
	{
		if (right != null)
		{
			Left left = rightCollection.isLinkedTo(right);							// Find out what "right" may be linked to
			if (left != null)														// If it is linked to a Left object...
				unlink(left, right);												// ...unlink them the usual way
		}
	}
	
	@Override
	public Left rightToLeft(Right right)											// Return the Left object right is linked to, or NULL if none
	{
		Left left = null;

		if (right != null)															// Just testing pointers
			left = rightCollection.isLinkedTo(right);								// Right collection has that answer for us

		return left;
	}
	
	@Override
	public List<Right> leftToRights(Left left)							// Returns a list of all the Right objects "left" is linked to
	{
		List<Right> rightObjectsList = new ArrayList<Right>();						// Making sure something will be returned. An empty list is better than a null reference

		if (left != null)															// Shall I say it? Skipping null pointers...
			rightObjectsList = leftCollection.isLinkedToCollection(left);			// ...return the left collection's list (in fact, a clone of that list)

		return rightObjectsList;													// So, we'll return a clone of the original list--built by the leftCollection
	}

	@Override
	public List<Left> rightToLefts(Right right) 							// We know there will be only one Left object... just keeping our interface general
	{
		List<Left> listWithOneLeftObject = new ArrayList<Left>();

		if (right != null)
		{
			Left left = rightToLeft(right);
			if (left != null)
				listWithOneLeftObject.add(left);
		}

		return listWithOneLeftObject;
	}


}
