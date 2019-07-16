package Framework;
import java.util.Map;
import java.util.HashMap;

public class CollectionOfOne<From, To> extends Collection<From, To>
{
	private Map<From, To> collection = new HashMap<From, To>();
	
	public String getMultiplicityName()
	{
		return multiplicities(Mult.ToZeroOrOne);
	}
	
	public void link(From from, To to)
	{
		if (from != null && to != null)
				collection.put(from, to);
	}
	
	public To isLinkedTo(From from)
	{
		To ret = null;
		
		if (from != null)
			ret = collection.get(from);			// Will return null if "from" object is not mapped
		
		return ret;
	}
	
	public boolean isLinked(From from, To to)
	{
		boolean result = false;
		
		if (from != null && to != null)
			if (to == isLinkedTo(from))
				result = true;
		
		return result;
	}
	
	public void unlink(From from, To to)
	{
		if (from != null && to != null)
			if (isLinked(from, to))
				collection.remove(from);
	}
	
	public void unlinkFrom(From from)
	{
		if (from != null)
			collection.remove(from);
	}
}
