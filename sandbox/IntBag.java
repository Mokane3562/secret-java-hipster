
import java.util.LinkedList;

/**
 * IntBag ************************************************************* A bag of
 * integer numbers represented using a singly linked list.
 * 
 * @author Michael Singleton
 ***********************************************************************/
public class IntBag {

	//TEST
	public static void main(String[] args) {
		int[] array = { 1, 2, 3, 2, 4, 5, 11 };
		IntBag bag = new IntBag(array);
		System.out.println("Actual:   " + bag); //test for toString(), IntBag(array[])
		System.out.println("Expected: {1,2,3,2,4,5,11,}\n");

		bag.addItem(11);
		System.out.println("A: " + bag); //test for addItem()
		System.out.println("E: {1,2,3,2,4,5,11,11,}\n");

		bag.removeItem(1);
		System.out.println("A: " + bag); //test for removeItem()
		System.out.println("E: {2,3,2,4,5,11,11,}\n");

		bag.removeAllItem(11); //test for removeAllItem()
		System.out.println("A: " + bag);
		System.out.println("E: {2,3,2,4,5,}\n");

		System.out.println("A: " + bag.getSize()); //test for getSize()
		System.out.println("E: 5\n");

		System.out.println("A: " + bag.countItem(2)); //test for countItem()
		System.out.println("E: 2\n");

		IntBag emptyBag = new IntBag();
		System.out.println((bag.isEmpty()) ? "A: true" : "A: false"); //test for isEmpty(), IntBag()
		System.out.println("E: false");
		System.out.println((emptyBag.isEmpty()) ? "A: true" : "A: false");
		System.out.println("E: true\n");

		System.out.println((bag.hasItem(7)) ? "A: true" : "A: false"); //test for hasItem()
		System.out.println("E: false");
		System.out.println((bag.hasItem(4)) ? "A: true" : "A: false");
		System.out.println("E: true");
	}

	private LinkedList<Integer> bag;

	/**
	 * IntBag Creates an empty bag object.
	 */
	public IntBag() {
		bag = new LinkedList<Integer>();
	}

	/**
	 * IntBag Creates a bag object with the items given as a parameter.
	 * 
	 * @param array
	 *            [] the items to be included in the bag.
	 */
	public IntBag(int array[]) {
		bag = new LinkedList<Integer>();
		for (int x : array) {
			bag.add(x);
		}
	}

	/**
	 * addItem Adds an item to the bag.
	 * 
	 * @param item
	 *            the item to be added to the bag.
	 */
	public void addItem(int item) {
		bag.add(item);
	}

	/**
	 * countItem Returns the number of occurrences of a specific item in the
	 * bag.
	 * 
	 * @param item
	 *            the item whos number of occurrences are to be counted.
	 * 
	 * @return the number of times the item occurred.
	 */
	public int countItem(int item) {
		int count = 0;
		for (int x : bag) {
			if (x == item) {
				count++;
			}
		}
		return count;
	}

	/**
	 * getSize Returns the number of elements in the bag.
	 * 
	 * @return the number of elements in the bag.
	 */
	public int getSize() {
		return bag.size();
	}

	/**
	 * hasItem Checks if the specified item is in the bag.
	 * 
	 * @param item
	 *            the item to be checked
	 * 
	 * @return if the item is in the bag.
	 */
	public boolean hasItem(int item) {
		return (bag.contains(item)) ? true : false;
	}

	/**
	 * isEmpty Checks if the bag is empty.
	 * 
	 * @return if the bag is empty.
	 */
	public boolean isEmpty() {
		return (bag.size() == 0) ? true : false;
	}

	/**
	 * removeAllItem Removes all occurrences of a specified item from the bag.
	 * 
	 * @param item
	 *            the item whos every occurrence is to be removed from the bag.
	 */
	public void removeAllItem(int item) {
		while(bag.contains(item) == true) {
			removeItem(item);
		}
	}

	/**
	 * removeItem Removes the first occurrence of a specific item from the bag.
	 * 
	 * @param item
	 *            the item to be removed from the bag.
	 * 
	 * @return returns true if the item was present in the list.
	 */
	public boolean removeItem(int item) {
		if (bag.contains(item) == true) {
			bag.remove(bag.indexOf(item));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * toString Returns a string representation of the bag object.
	 * 
	 * @return the string representation of the bag.
	 */
	@Override
	public String toString() {
		String string = "{";
		for (int x : bag) {
			string = string + x + ",";
		}
		string = string + "}";
		return string;
	}
}