
import java.util.Arrays;
import java.util.LinkedList;

//NOTE: this doesn't compile due to missing classes (SumDriverLicense, PolyDriverLicense)
public class HashSumAndChain<K, V> {
	/**
	 * Entry - an object holding a value and a key used in ordering
	 */
	public class Entry {
		private K key; //key
		private V value; //value

		/**
		 * Creates an entry
		 * 
		 * @param key
		 *            the entry's key
		 * @param value
		 *            the value held by the entry
		 */
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Returns the key of the entry
		 * 
		 * @return the key of this entry
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns the value of the entry
		 * 
		 * @return the value of this entry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Standard toString method
		 * 
		 * @return a string representation of the object
		 */

		/*****************************************************/
		public V setValue(V value) {
			V toReturn = getValue();
			this.value = value;
			return toReturn;
		}

		/*****************************************************************/
		@Override
		public String toString() {
			return "(" + getKey() + ", " + getValue() + ")";
		}
	}
	public static void main(String[] args) {
		HashSumAndChain<SumDriverLicense, String> table1 = new HashSumAndChain<SumDriverLicense, String>();
		table1.put(new SumDriverLicense("H809123456"), "bob"); // hashSumChain inserts ::
		table1.put(new SumDriverLicense("L123456789"), "jim");
		table1.put(new SumDriverLicense("D098765432"), "lacy");
		table1.put(new SumDriverLicense("O456123987"), "agustus");
		System.out.println("> elements of table are:\n" + table1.array);
		System.out.println("> load factor: " + table1.loadFactor);

		HashSumAndChain<PolyDriverLicense, String> table2 = new HashSumAndChain<PolyDriverLicense, String>();
		table2.put(new PolyDriverLicense("H809123456"), "bob"); // hashPolyChain inserts ::
		table2.put(new PolyDriverLicense("L123456789"), "jim");
		table2.put(new PolyDriverLicense("D098765432"), "lacy");
		table2.put(new PolyDriverLicense("O456123987"), "agustus");
		System.out.println("> elements of table are:\n" + table2.array);
		System.out.println("> load factor: " + table2.loadFactor);
	}

	private final int INITIAL_TABLE_SIZE = 11;
	private final double MAX_LOAD_FACTOR = 0.75;
	private LinkedList<Entry>[] array;

	private double loadFactor;

	private double numEntries;

	public HashSumAndChain() {
		array = new LinkedList[INITIAL_TABLE_SIZE];
		for (int i = 0; i < INITIAL_TABLE_SIZE; i++) {
			array[i] = new LinkedList<Entry>();
		}
		loadFactor = 0.0;
		numEntries = 0.0;
	}

	public boolean containsKey(K key) {
		boolean result = false;
		for (LinkedList<Entry> list : array) {
			for (Entry e : list) {
				if (e.getKey() == key) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	private int getEntryIndex(K key) { // this method exists purely because parameter pass by reference does not
		int result = -1;
		for (LinkedList<Entry> list : array) {
			for (Entry e : list) {
				if (e.getKey() == key) {
					result = list.indexOf(e);
					break;
				}
			}
		}
		return result;
	}

	public V put(K key, V value) {
		V oldValue = null;
		int code = key.hashCode();
		int index = code % array.length;
		index = Math.abs(index);
		if (containsKey(key)) {
			int listIndex = getEntryIndex(key);
			oldValue = array[index].get(listIndex).getValue();
			array[index].set(listIndex, new Entry(key, value));
		} else {
			array[index].add(new Entry(key, value));
			numEntries++;
		}
		update();
		return oldValue;
	}

	public void rehash() {
		array = Arrays.copyOf(array, (2 * array.length) + 1);
	}

	private void update() {
		loadFactor = numEntries / array.length;
		if (loadFactor > MAX_LOAD_FACTOR) {
			rehash();
			loadFactor = numEntries / array.length;
		}
	}
}