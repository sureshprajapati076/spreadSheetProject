package Framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TernaryAssociationClass<Center, Left, Top, Right>
{
	/*
	* All Associations will form a branch that goes from an end (LEFT), towards the center (RIGHT), where the Center is
	* All multiplicity will be one-to-many from each branch, inward
	*/
	
	// Using OneToMany "hard" objects here. Not planning to implement different multiplicity cases
	private OneToMany<Left, Center> leftAssociation;					
	private OneToMany<Top, Center> topAssociation;
	private OneToMany<Right, Center> rightAssociation;
	
	// Java only! Set of all the Ternary Links... why not?
	private List<Center> linkObjectList = new Vector<Center>();
	
	private String classNameString;
	private String leftNameString;
	private String topNameString;
	private String rightNameString;
	
	private String leftRoleString;
	private String topRoleString;
	private String rightRoleString;

	protected String getClassName() { return this.getClass().getSimpleName(); }

	private void populateQuadrupletList(List<Quadruplet<Center, Left, Top, Right>> instances, List<Center> centers)
	{
		for (Center center : centers)
		{
			Quadruplet<Center, Left, Top, Right> quad = fromCenter(center);
			if (quad != null && quad.hasNoNulls())
				instances.add(quad);
		}
	}


	public TernaryAssociationClass(String clssName, String leftName, String topName, String rightName, String lftRole, String tpRole, String rghtRole) 
	{
		classNameString = clssName;
		leftNameString = leftName;
		topNameString = topName;
		rightNameString = rightName;
		
		leftRoleString = lftRole;
		topRoleString = tpRole;
		rightRoleString = rghtRole;
		
		leftAssociation = new OneToMany<>(lftRole);
		topAssociation = new OneToMany<>(tpRole);
		rightAssociation = new OneToMany<>(rghtRole);
	}

	public String report()
	{
		StringBuilder descr = new StringBuilder();

		descr.append(getClassName() + "\n");
		descr.append("A " + classNameString + " links together: \n");
		descr.append("\t1. a " + leftNameString + " as the " + leftRoleString + "\n");
		descr.append("\t2. a " + topNameString + " as the " + topRoleString + "\n");
		descr.append("\t3. a " + rightNameString + " as the " + rightRoleString + "\n");
		descr.append("\taccording to a: " + classNameString + "\n");

		return descr.toString();
	}

	public String toString() { return report(); }

	
	public String className() { return classNameString; }
	public String leftRole() { return leftRoleString; }
	public String topRole() { return topRoleString; }
	public String rightRole() { return rightRoleString; }


	public void link(Center obj, Left left, Top top, Right right)
	{
		if (obj != null && left != null && top != null && right != null)
		{
			//Make sure that link object is not linked to anything else, to respect multiplicity
			unlink(obj);

			// Simply link the three branches: left, top, and right, to the "center": obj
			leftAssociation.link(left, obj);
			topAssociation.link(top, obj);
			rightAssociation.link(right, obj);
			linkObjectList.add(obj);				// Java only!
		}
	}

	public void unlink(Center obj)
	{
		if (obj != null)
		{
			leftAssociation.unlinkRight(obj);
			topAssociation.unlinkRight(obj);
			rightAssociation.unlinkRight(obj);
			
			linkObjectList.remove(obj);				// Java only!
		}
	}
	
	public List<Quadruplet<Center, Left, Top, Right>> links()		// Java only!
	{
		List<Quadruplet<Center, Left, Top, Right>> list = new ArrayList<>();
		
		populateQuadrupletList(list, linkObjectList);
		
		return list;
	}

	public void unlinkLeft(Left left)
	{
		if (left != null)
			for (Quadruplet<Center, Left, Top, Right> quad : fromLeft(left))
				unlink(quad.center);
	}

	public void unlinkTop(Top top)
	{
		if (top != null)
			for (Quadruplet<Center, Left, Top, Right> quad : fromTop(top))
				unlink(quad.center);
	}

	public void unlinkRight(Right right)
	{
		if (right != null)
			for (Quadruplet<Center, Left, Top, Right> quad : fromRight(right))
				unlink(quad.center);
	}

	public Quadruplet<Center, Left, Top, Right> fromCenter(Center center)
	{
		Quadruplet<Center, Left, Top, Right> quadruplet = new Quadruplet<>(null, null, null, null);

		if (center != null)
		{
			quadruplet.center = center;
			quadruplet.left = leftAssociation.rightToLeft(quadruplet.center);
			quadruplet.top = topAssociation.rightToLeft(quadruplet.center);
			quadruplet.right = rightAssociation.rightToLeft(quadruplet.center);
		}

		return quadruplet;
	}

	public List<Quadruplet<Center, Left, Top, Right>> fromLeft(Left left)
	{
		List<Quadruplet<Center, Left, Top, Right>> instances = new ArrayList<Quadruplet<Center, Left, Top, Right>>();

		if (left != null)
		{
			List<Center> centers = leftAssociation.leftToRights(left);
			populateQuadrupletList(instances, centers);
		}
		return instances;
	}

	public List<Quadruplet<Center, Left, Top, Right>> fromTop(Top top)
	{
		List<Quadruplet<Center, Left, Top, Right>> instances = new ArrayList<Quadruplet<Center, Left, Top, Right>>();

		if (top != null)
		{
			List<Center> centers = topAssociation.leftToRights(top);
			populateQuadrupletList(instances, centers);
		}
		return instances;
	}

	public List<Quadruplet<Center, Left, Top, Right>> fromRight(Right right)
	{
		List<Quadruplet<Center, Left, Top, Right>> instances = new ArrayList<Quadruplet<Center, Left, Top, Right>>();

		if (right != null)
		{
			List<Center> centers = rightAssociation.leftToRights(right);
			populateQuadrupletList(instances, centers);
		}
		return instances;
	}
	
	public List<Center> leftToCenters(Left left)
	{
		return leftAssociation.leftToRights(left);
	}

	public List<Center> rightToCenters(Right right)
	{
		return rightAssociation.leftToRights(right);
	}

	public List<Center> topToCenters(Top top)
	{
		return topAssociation.leftToRights(top);
	}

}