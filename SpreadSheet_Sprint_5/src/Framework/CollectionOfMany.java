package Framework;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Vector;

public class CollectionOfMany<From, To> extends Collection<From, To>
{
	private Map<From, List<To>> collection = new HashMap<>();	// The mapping between "From" objects and maps of lists of "To" items, a fast-retrieval map

	private List<To> getItemsContainer(From from)				// Get a reference of the container dedicated to this "From" object. Create it if not found.
	{
		List<To> items = null;
		
		List<To> list = collection.get(from);					// Find the list of items for the "from" object, if we have one

		if (list != null)										// If we found that list...										
			items = list;										// ...return it
		else													// If there isn't one yet
		{
			collection.put(from, new Vector<To>());				// Create it
			items = collection.get(from);						// Return it, making sure it's really the one the map holds for the "from" argument
		}

		return items;
	}

	public String getMultiplicityName()
	{
		return multiplicities(Mult.ToZeroOrMore);
	}
	
	public void link(From from, To to)
	{
		if (from != null && to != null)
		{
			List<To> items = getItemsContainer(from);			// Get the items list for that "from" object
			if (items != null)									// Trust but verify
				if (!items.contains(to))						// If that link doesn't already exist...
					items.add(to);								// ...add it (no duplicates)
		}
	}

	public void unlink(From from, To to)						// Remove the link between "from" and "to", if any
	{
		if (from != null && to != null)							// Skip null pointers... Oh, surprise!
		{
			List<To> items = getItemsContainer(from);			// Get list of items "from" is linked to

			if (items != null)									// Trust but verify
				items.remove(to);								// Make sure the "from" object is no longer linked to the "to" object
		}
	}

	public 	void unlinkFrom(From from)							// Unlink "from" from whatever it may be linked to
	{
		if (from != null)										// Still skipping null pointers
		{
			List<To> items = getItemsContainer(from);			// Get the items list for that "from" object
			if (items != null)									// Trust but verify
				items.clear();									// Empty the list, entirely, so that "from" is not linked to anything anymore
		}
	}
	
	public List<To> isLinkedToCollection(From from)				// Returns a CLONE of the list of objects the "from" object is linked to--NOT a pointer to the actual list
	{
		List<To> list = new Vector<To>();						// An empty list to return if something goes wrong

		List<To> items = getItemsContainer(from);				// Get the list of items for the "from" object

		if (items != null)										// If we get one (and we always should)
			list.addAll(items);									// Make a clone of that list
		
		return list;											// Return a clone of that container, or just an empty list
	}
	
	public boolean isLinked(From from, To to)					// Returns true if "from" and "to" are linked, or false if not
	{
		boolean found = false;

		if (from != null && to != null)
		{
			List<To> items = getItemsContainer(from);			// Get the list of items for the "from" object
			if (items != null)									
				found = items.contains(to);						// See if that list contains the "to" objects
		}

		return found;
	}
	
	/*
	 * We've decided the following  methods are NOT needed within the context of our Associations Framework
	 * 
	public void unlinkAllLinksWithToObjects(To to);				 
	public void unlinkFirstFoundToBeLinkedTo(To *to);
	 */
}
