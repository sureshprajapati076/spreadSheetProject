package Framework;

import java.util.ArrayList;
import java.util.List;

public class AssociationClass<Center, Left, Right>
{
	Association<Left, Center> leftAssociation;
	Association<Right, Center> rightAssociation;

	Association.Multiplicity leftMultiplicity;
	Association.Multiplicity rightMultiplicity;

	String leftToRightNameString;
	String rightToLeftNameString;
	String centerClassNameString;

	/*
			All Associations will form a branch that goes from an end (Left or Right), towards the center (Center)
	*/

	Association<Left, Center> createLeftAssociation(Association.Multiplicity mult, String role)
	{
		Association<Left, Center> association = null;

		if (mult == Association.Multiplicity.ZeroOrOne)
			association = new OneToOne<Left, Center>("", "", role,"");
		else if (mult == Association.Multiplicity.ZeroOrMore)
			association = new OneToMany<Left, Center>("", "", role,"");

		return association;
	}

	Association<Right, Center> createRightAssociation(Association.Multiplicity mult, String role)
	{
		Association<Right, Center> association = null;

		if (mult == Association.Multiplicity.ZeroOrOne)
			association = new OneToOne<Right, Center>("", "", role,"");
		else if (mult == Association.Multiplicity.ZeroOrMore)
			association = new OneToMany<Right, Center>("", "", role,"");

		return association;
	}

	void createAssociations(String leftRole, String rightRole)
	{
		leftAssociation = createLeftAssociation(rightMultiplicity, leftRole);		// Left Association will go from Left towards the Class
		rightAssociation = createRightAssociation(leftMultiplicity, rightRole);		// Right Association will go from Right towards the Class
	}

	protected String getClassName()
	{
		return this.getClass().getSimpleName();
	}

	public AssociationClass(String lftName, String rghtName, Association.Multiplicity leftMult, Association.Multiplicity rightMult, 
						String leftRole, String rightRole, String centerClassName)
	{
		leftMultiplicity = leftMult;
		rightMultiplicity = rightMult;
		
		leftToRightNameString = lftName + " ";
		rightToLeftNameString = rghtName + " ";
		
		centerClassNameString = centerClassName;
		
		createAssociations(leftRole, rightRole);
	}

	public String leftToRightName() { return leftToRightNameString; }

	public String rightToLeftName() { return rightToLeftNameString; }

	public String report()
	{
		StringBuilder descr = new StringBuilder();

		if (leftAssociation != null && rightAssociation != null)
		{
			descr.append(getClassName() + ": \n");
			descr.append("A " + leftAssociation.leftRole() + leftToRightName() + leftAssociation.rightMultiplicityName() + rightAssociation.leftRole() + "according to a " + centerClassNameString + "\n");
			descr.append("A " + rightAssociation.leftRole() + rightToLeftName() + rightAssociation.rightMultiplicityName() + leftAssociation.leftRole() + "according to a " + centerClassNameString + "\n");
		}

		return descr.toString();
	}

	public String toString() { return report(); }

	public void link(Center obj, Left lft, Right rgt)
	{
		if (obj != null && lft != null && rgt != null)
		{
			if (leftMultiplicity == Association.Multiplicity.ZeroOrOne)		// If Right object can be linked to only one link object 
				unlinkRight(rgt);									// ...disconnect it first, both sides of the link object

			if (rightMultiplicity == Association.Multiplicity.ZeroOrOne)	// If Left object can be linked to only one link object
				unlinkLeft(lft);										// ...disconnect it first, both sides of the link object
			
			leftAssociation.link(lft, obj);
			rightAssociation.link(rgt, obj);
		}
	}

	public void unlink(Center obj, Left lft, Right rgt)
	{
		if (obj != null && lft != null && rgt != null)
		{
			leftAssociation.unlink(lft, obj);
			rightAssociation.unlink(rgt, obj);
		}
	}

	public void unlink(Triplet<Center, Left, Right> triplet)
	{
		unlink(triplet.linkObject, triplet.left, triplet.right);
	}

	public List<Triplet<Center, Left, Right>> fromLeft(Left left)				// Returns a collection of all the link objects "left" is linked to
	{
		List<Triplet<Center, Left, Right>> triplets = new ArrayList<>();
		Triplet<Center, Left, Right> triplet = null;

		if (left != null)
			for (Center linkObject : leftAssociation.leftToRights(left))
			{
				triplet = new Triplet<>(linkObject, left, rightAssociation.rightToLeft(linkObject));
				triplets.add(triplet);
			}

		return triplets;
	}

	public List<Triplet<Center, Left, Right>> fromRight(Right right)			// Returns a collection of all the link objects "right" is linked to
	{
		List<Triplet<Center, Left, Right>> triplets = new ArrayList<>();
		Triplet<Center, Left, Right> triplet = null;

		if (right != null)
			for (Center linkObject : rightAssociation.leftToRights(right))
			{
				triplet = new Triplet<>(linkObject, leftAssociation.rightToLeft(linkObject), right);
				triplets.add(triplet);
			}

		return triplets;
	}

	public Triplet<Center, Left, Right> firstFromLeft(Left left)				// Returns the first link object "left" is linked to
	{
		Triplet<Center, Left, Right> triplet = null;											// The Triplet we'll return (initialized to null)

		if (left != null)
		{
			List<Triplet<Center, Left, Right>> triplets = fromLeft(left);
			if (triplets.size() >= 1)
				triplet = triplets.get(0);
		}

		return triplet;
	}

	public Triplet<Center, Left, Right> firstFromRight(Right right)				// Returns the first link object "right" is linked to
	{
		Triplet<Center, Left, Right> triplet = null;											// The Triplet we'll return (initialized to null)

		if (right != null)
		{
			List<Triplet<Center, Left, Right>> triplets = fromRight(right);
			if (triplets.size() >= 1)
				triplet = triplets.get(0);
		}

		return triplet;
	}

	public Right centerToRight(Center obj)						// Returns the Right object the link object is linked to (or null if there are many!)
	{
		Right right = null;

		if (obj != null)
			right = rightAssociation.rightToLeft(obj);

		return right;
	}

	public Left centerToLeft(Center obj)						// Returns the Left object the link object is linked to (or null if there are many!)
	{
		Left left = null;

		if (obj != null)
			left = leftAssociation.rightToLeft(obj);

		return left;
	}

	public void unlink(Center obj)							// Unlink the link object from Left and Right 
	{
		if (obj != null)
		{
			leftAssociation.unlinkRight(obj);
			rightAssociation.unlinkRight(obj);
		}
	}


	public void unlinkLeft(Left left)
	{
		if (left != null)
			for (Triplet<Center, Left, Right> triplet : fromLeft(left))
				unlink(triplet);
	}

	void unlinkRight(Right right)
	{
		if (right != null)
			for (Triplet<Center, Left, Right> triplet : fromRight(right))
				unlink(triplet);
	}
	
	public List<Center> leftToCenters(Left left)
	{
		return leftAssociation.leftToRights(left);
	}

	public List<Center> rightToCenters(Right right)
	{
		return rightAssociation.leftToRights(right);
	}
	
}
