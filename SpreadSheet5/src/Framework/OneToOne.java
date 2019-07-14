package Framework;

import java.util.ArrayList;
import java.util.List;

public class OneToOne<Left, Right> extends Association<Left, Right> 
{
	private CollectionOfOne<Left, Right> leftCollection;
	private CollectionOfOne<Right, Left> rightCollection;
	
	@Override
	protected void buildLeftCollection()
	{
		leftCollection = new CollectionOfOne<Left, Right>();
		setLeftCollection(leftCollection);								// Make it available to superclass as well, for easy access
	}

	@Override
	protected void buildRightCollection()
	{
		rightCollection = new CollectionOfOne<Right, Left>();
		setRightCollection(rightCollection);							// Make it available to super class as well, for easy access
	}	

/*	@Override
	protected String getClassName()
	{
		return this.getClass().getSimpleName();
	}*/

	public OneToOne(String leftName, String rightName, String leftRole, String rightRole) 
	{
		super(leftName, rightName, leftRole, rightRole);
		buildLeftCollection();
		buildRightCollection();
	}
	
	@Override
	public void link(Left left, Right right)							// Link "left" with "right", making sure the previous links they  
																		// ...previously had (if any) are erased since this is a ONE to ONE Association
	{
		if (left != null && right != null && leftCollection != null && rightCollection != null)
		{
			Right previousRight = leftToRight(left);					// See if left is already linked to a "Right*"
			if (previousRight != null)									// If it is...
				rightCollection.unlink(previousRight, left);			// ...tell that one it's no longer linked to left
			
			Left previousLeft = rightToLeft(right);						// See if right is already linked to a "Left*"
			if (previousLeft != null)									// If it is...
				leftCollection.unlink(previousLeft, right);				// ...tell that one it's no longer linked to "right"
			
			super.link(left, right);									// Link left to right and right to left, according to the traversal policies
		}
	}

	@Override
	public void unlinkLeft(Left left)									// Delete whatever link there might be from left object, if any
	{
		if (left != null)
		{
			Right right = leftToRight(left);
			if (right != null)											// If left is linked to a Right object
				disconnect(left, right);								// Unlink them
		}
	}

	
	@Override
	public List<Right> leftToRights(Left left)							// Returns a list of Right objects "left" is linked to: 0, or 1 such objects in this case
	{
		List<Right> rightObject = new ArrayList<Right>();

		if (left != null)
		{
			Right right = leftToRight(left);
			if (right != null)
				rightObject.add(right);
		}

		return rightObject;
	}

	@Override
	public	List<Left> rightToLefts(Right right)						// Returns a list of Left objects "right" is linked to: 0, or 1 such objects in this case
	{
		List<Left> leftObject = new ArrayList<Left>();

		if (right != null)
		{
			Left left = rightToLeft(right);
			if (left != null)
				leftObject.add(left);
		}

		return leftObject;
	}


	@Override
	public 	void unlinkRight(Right right)								// Delete whatever link there might be from right object, if any
	{
		if (right != null)
		{
			Left left = rightToLeft(right);
			if (left != null)											// If right is linked to a Left object
				disconnect(left, right);								// Unlink them
		}
	}

	@Override
	public Right leftToRight(Left left)									// Return the Right object left is linked to, NULL if none
	{
		Right ret = null;

		if (leftCollection != null)										// If left-to-right traversal available
			ret = leftCollection.isLinkedTo(left);						// Return what left is linked to

		return ret;
	}

	@Override
	public Left rightToLeft(Right right)								// Return the Left object right is linked to, NULL if none
	{
		Left ret = null;

		if (rightCollection != null)									// If right-to-left traversal available
			ret = rightCollection.isLinkedTo(right);					// Return what right is linked to

		return ret;
	}



}
