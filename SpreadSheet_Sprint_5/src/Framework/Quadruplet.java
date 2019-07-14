package Framework;

/* 
* Simple structure to hold a pointer to each of the four objects of a Ternary Association Class instance, a ternary link object
*/

public class Quadruplet<Center, Left, Top, Right>										
{
	public Center center;			// Pointer to the actual object of a ternary link object
	public Left left;				// Pointer to the left object of a ternary link object
	public Top top;					// Pointer to the top object of a ternary link object
	public Right right;				// Pointer to the right object of a ternary link object
	
	Quadruplet(Center cent, Left lft, Top tp, Right rgt) { center = cent; left = lft; top = tp; right = rgt; }
	
	// Method to find out whether a Quadruplet has any null pointers. In normal operations, all pointers are supposed to be NOT null
	boolean hasNoNulls() { return center != null && left  != null && top != null && right != null; }
	public boolean nulls() { return !hasNoNulls(); }
}