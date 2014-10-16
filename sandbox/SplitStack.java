
import java.util.EmptyStackException;

/**
 * class SplitStack ************************* Creates a container for positive
 * integers sorting odd and even numbers into two different stacks
 * 
 * @author Michael Singleton
 *********************************************/
public class SplitStack {
	//TEST
	public static void main(String[] args) {
		SplitStack testStack = new SplitStack();//creates new SplitStack

		int sizeOddActualEmpty = testStack.sizeOdd();//checks the size of both stacks 
		int sizeEvenActualEmpty = testStack.sizeEven();

		boolean isEmptyOddActualEmpty = testStack.isEmptyOdd();//checks that both stacks are empty
		boolean isEmptyEvenActualEmpty = testStack.isEmptyEven();

		String pushActualNegative = null;
		try {
			testStack.push(-1);
		} catch (Exception e) {
			pushActualNegative = e.toString();//attempts to push a negative, saves the result
		}

		testStack.push(3);//pushes an even and an odd element
		testStack.push(2);

		String pushActualThreeAndTwo = java.util.Arrays.toString(testStack.array);

		int sizeOddActualOne = testStack.sizeOdd();//checks the new size of both stacks
		int sizeEvenActualOne = testStack.sizeEven();

		boolean isEmptyOddActualOne = testStack.isEmptyOdd();//checks that neither stack is empty
		boolean isEmptyEvenActualOne = testStack.isEmptyEven();

		int topOddActualOne = testStack.topOdd();//checks the values at both stacks
		int topEvenActualOne = testStack.topEven();

		int popOddActualOne = testStack.popOdd();//checks and removes the values at both stacks
		int popEvenActualOne = testStack.popEven();

		String topOddActualEmpty = null;
		String topEvenActualEmpty = null;
		try {
			testStack.topOdd();
		} catch (Exception e) {
			topOddActualEmpty = e.toString();//attempts to check the values at both empty stacks, saves result
		}
		try {
			testStack.topEven();
		} catch (Exception e) {
			topEvenActualEmpty = e.toString();
		}

		String popOddActualEmpty = null;
		String popEvenActualEmpty = null;
		try {
			testStack.popOdd();
		} catch (Exception e) {
			popOddActualEmpty = e.toString();//attempts to check and remove the values at both empty stacks, saves result
		}
		try {
			testStack.popEven();
		} catch (Exception e) {
			popEvenActualEmpty = e.toString();
		}

		for (int i = 1; i <= 100; i++) {
			testStack.push(2);//fills both stacks
			testStack.push(3);
		}

		String pushActualFull = null;
		try {
			testStack.push(5);
		} catch (Exception e) {
			pushActualFull = e.toString();//attempts to push a value onto full stacks, saves result
		}

		System.out.printf("sizeOdd for empty%nActual: %s%nExpected: 0%n%n", sizeOddActualEmpty);
		System.out.printf("sizeEven for empty%nActual: %s%nExpected: 0%n%n", sizeEvenActualEmpty);
		System.out.printf("sizeOdd for not empty%nActual: %s%nExpected: 1%n%n", sizeOddActualOne);
		System.out.printf("sizeEven for not empty%nActual: %s%nExpected: 1%n%n", sizeEvenActualOne);

		System.out.printf("isEmptyOdd for empty%nActual: %s%nExpected: true%n%n", isEmptyOddActualEmpty);
		System.out.printf("isEmptyEven for empty%nActual: %s%nExpected: true%n%n", isEmptyEvenActualEmpty);
		System.out.printf("isEmptyOdd for not empty%nActual: %s%nExpected: false%n%n", isEmptyOddActualOne);
		System.out.printf("isEmptyEven for not empty%nActual: %s%nExpected: false%n%n", isEmptyEvenActualOne);

		System.out.printf("push for a negative%nActual: %s%nExpected: java.lang.IllegalArgumentException: argument must be a positive integer%n%n", pushActualNegative);
		System.out.printf("push for full stacks%nActual: %s%nExpected: java.lang.ArrayIndexOutOfBoundsException: maximum size limit reached%n%n", pushActualFull);
		System.out.printf("push for 3, 2%nActual: %s%nExpected: [2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3]%n%n", pushActualThreeAndTwo);

		System.out.printf("topOdd for empty%nActual: %s%nExpected: java.util.EmptyStackException%n%n", topOddActualEmpty);
		System.out.printf("topEven for empty%nActual: %s%nExpected: java.util.EmptyStackException%n%n", topEvenActualEmpty);
		System.out.printf("topOdd for not empty%nActual: %s%nExpected: 3%n%n", topOddActualOne);
		System.out.printf("topEven for not empty%nActual: %s%nExpected: 2%n%n", topEvenActualOne);

		System.out.printf("popOdd for empty%nActual: %s%nExpected: java.util.EmptyStackException%n%n", popOddActualEmpty);
		System.out.printf("popEven for empty%nActual: %s%nExpected: java.util.EmptyStackException%n%n", popEvenActualEmpty);
		System.out.printf("popOdd for not empty%nActual: %s%nExpected: 3%n%n", popOddActualOne);
		System.out.printf("popEven for not empty%nActual: %s%nExpected: 2%n%n", popEvenActualOne);
	}
	private int[] array;
	private int headOdd = 199;

	private int headEven = 0;

	/**
	 * SplitStack creates a new SplitStack object
	 */
	public SplitStack() {
		array = new int[200];
	}

	/**
	 * isEmptyEven checks if the even stack is empty
	 * 
	 * @return true if the even stack is empty
	 */
	public boolean isEmptyEven() {
		return (headEven == 0) ? true : false;
	}

	/**
	 * isEmptyOdd checks if the odd stack is empty
	 * 
	 * @return true if the odd stack is empty
	 */
	public boolean isEmptyOdd() {
		return (headOdd == 199) ? true : false;
	}

	/**
	 * popEven returns and removes the first element on the even stack
	 * 
	 * @return the first element on the even stack
	 */
	public int popEven() { //assuming isEmpty == false
		if (isEmptyEven()) {
			throw new EmptyStackException();
		}
		headEven--;
		int temp = array[headEven];
		array[headEven] = -1;
		return temp;
	}

	/**
	 * popOdd returns and removes the first element on the odd stack
	 * 
	 * @return the first element on the odd stack
	 */
	public int popOdd() { //assuming isEmpty == false
		if (isEmptyOdd()) {
			throw new EmptyStackException();
		}
		headOdd++;
		int temp = array[headOdd];
		array[headOdd] = -1;
		return temp;
	}

	/**
	 * push pushes an element onto the appropriate stack, depending on whether
	 * it's an odd number or an even number.
	 * 
	 * @param n
	 *            the element to be added to its respective stack
	 */
	public void push(int n) { //assuming n>=0, headOdd>=headEven
		if (n < 0) {
			throw new IllegalArgumentException("argument must be a positive integer");
		} else if (headOdd < headEven) {
			throw new ArrayIndexOutOfBoundsException("maximum size limit reached");
		} else if (n % 2 == 1) {
			array[headOdd] = n;
			headOdd--;
		} else {
			array[headEven] = n;
			headEven++;
		}
	}

	/**
	 * sizeEven returns the size of the even stack
	 * 
	 * @return the size of the even stack
	 */
	public int sizeEven() {
		return headEven;
	}

	/**
	 * sizeOdd returns the size of the odd stack
	 * 
	 * @return the size of the odd stack
	 */
	public int sizeOdd() {
		return 199 - headOdd;
	}

	/**
	 * topEven returns the value of the first element on the even stack
	 * 
	 * @return the first element on the even stack
	 */
	public int topEven() { //assuming isEmpty == false
		if (isEmptyEven()) {
			throw new EmptyStackException();
		}
		return array[headEven - 1];
	}

	/**
	 * topOdd returns the value of the first element on the odd stack
	 * 
	 * @return the first element on the odd stack
	 */
	public int topOdd() { //assuming isEmpty == false
		if (isEmptyOdd()) {
			throw new EmptyStackException();
		}
		return array[headOdd + 1];
	}
}