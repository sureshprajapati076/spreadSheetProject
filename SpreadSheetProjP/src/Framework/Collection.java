package Framework;

public abstract class Collection<From, To>
{
protected 	
	enum Mult
	{
		ToZeroOrOne,
		ToZeroOrMore
	};
	
	static String multiplicities(Mult i)
	{
		if (i == Mult.ToZeroOrOne)
			return new String("zero or one ");
		else if (i == Mult.ToZeroOrMore)
			return new String("zero or more ");
		else 
			return new String("?");
	}
	
	public abstract String getMultiplicityName();

	public abstract void link(From from, To to);			// Creates a unique link from "from" to "to"
	public abstract boolean isLinked(From from, To to);		// Returns true if "from" and "to" are linked, or false if not
	public abstract void unlink(From from, To to);			// Remove the link between "from" and "to", if any
	public abstract void unlinkFrom(From from);				// Unlink "from" with whatever it was linked with, if any
}
