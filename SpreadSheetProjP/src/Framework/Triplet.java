package Framework;

@SuppressWarnings("hiding")

public class Triplet<Class, Left, Right>
{
	public Class linkObject;
	public Left left;
	public Right right;
	public Triplet(Class lo, Left lft, Right rgt) { linkObject = lo; left = lft; right = rgt; }
	public boolean hasNoNulls() { return linkObject != null && left != null && right != null; }
	public boolean nulls() { return !hasNoNulls(); }
}
