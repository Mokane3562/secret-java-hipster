

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Priority Queue - an ordered queue
 * 
 * @author Michael Singleton
 */
public class PQueue {
	/**
	 * Entry - an object holding a value and a key used in ordering
	 */
	public static class Entry {
		private Integer k; //key
		private Object v; //value

		/**
		 * Creates an entry
		 * 
		 * @param k
		 *            the entry's key
		 * @param v
		 *            the value held by the entry
		 */
		public Entry(Integer k, Object v) {
			this.k = k;
			this.v = v;
		}

		/**
		 * Returns the key of the entry
		 * 
		 * @return the key of this entry
		 */
		public Integer getKey() {
			return k;
		}

		/**
		 * Returns the value of the entry
		 * 
		 * @return the value of this entry
		 */
		public Object getValue() {
			return v;
		}

		/**
		 * Standard toString method
		 * 
		 * @return a string representation of the object
		 */
		@Override
		public String toString() {
			return "(" + getKey() + ", " + getValue() + ")";
		}
	}

	public static void main(String args[]) {
		/*
		 * //Testing public static void main(String[] args){
		 * System.out.printf("\ Testing for Assignment #5%n\ %n\ 1 - public
		 * PQueue()%n\ 2 - public int size()%n\ 3 - public boolean isEmpty()%n\
		 * 4 - public Object min()%n\ 5 - public Entry insert(Double k, Object
		 * v)%n\ 6 - public Object removeMin()%n\ 7 - public Object[]
		 * PQueueSort(Object[])%n\ Nested Classes:%n\ 7 - public Entry (Double
		 * k, Object v)%n\ 8 - public Double getKey()%n\ 9 - public Object
		 * getValue()%n\ ");
		 */

		PQueue pq = new PQueue();
		pq.insert(1, "Jake");
		pq.insert(4, new Rectangle(50, 50, 50, 50));
		pq.insert(3, 'd');
		pq.insert(2, 5.0);
		pq.insert(21313545, 0x1DF4);

		//System.out.println(pq.min());

		Object[] array = new Object[5];

		for (int i = 0; i < array.length; i++) {
			array[i] = pq.removeMin();
		}

		for (Object e : array) {
			System.out.println(e);
		}
	}

	private ArrayList<Entry> pQueue;

	/**
	 * Creates an empty priority queue
	 */
	public PQueue() {
		pQueue = new ArrayList<Entry>();
	}

	/**
	 * Creates and inserts an entry object into the priority queue
	 * 
	 * @param k
	 *            the key associated with the entry
	 * @param v
	 *            the value of the entry
	 * @return the entry object that was inserted into the priority queue
	 */
	public Entry insert(Integer k, Object v) {
		Entry newEntry = new Entry(k, v);
		pQueue.add(newEntry);
		return newEntry;
	}

	/**
	 * Checks to see if the priority queue is empty
	 * 
	 * @return true if the priority queue is empty
	 */
	public boolean isEmpty() {
		return (pQueue.size() == 0) ? true : false; //if pQueue size is 0 return true, else return false
	}

	/**
	 * Returns the value of the smallest element in the priority queue
	 * 
	 * @return the lowest ordered object in the priority queue
	 */
	public Object min() {
		int min = 0;
		for (int i = 1; i < pQueue.size(); i++) {
			if (pQueue.get(i).getKey().compareTo(pQueue.get(min).getKey()) < 0) //if the key of pQueue[index] is less than the key of pQueue[minimum so far]
				min = i;
		}
		return pQueue.get(min).getValue();
	}

	/**
	 * Removes and returns the value of the smallest element in the priority
	 * queue
	 * 
	 * @return the lowest ordered object in the priority queue
	 */
	public Object removeMin() {
		int min = 0;
		for (int i = 1; i < pQueue.size(); i++) {
			if (pQueue.get(i).getKey().compareTo(pQueue.get(min).getKey()) < 0) //if the key of pQueue[index] is less than the key of pQueue[minimum so far]
				min = i;
		}
		Object temp = pQueue.get(min).getValue();
		pQueue.remove(min); //note: this line is the only difference between this method and the min() method 
		return temp;
	}

	/**
	 * Returns the size of the priority queue
	 * 
	 * @return the number of elements in the priority queue
	 */
	public int size() {
		return pQueue.size();
	}
}