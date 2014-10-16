import java.util.Scanner;

public class PkmnRankCalc {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the base stat level of a pokemon. Enter an empty value to quit");
		while(in.hasNextInt()){
			int statValue = in.nextInt();
			statValue -= 300; 
			int remainder = statValue % 25; 
			statValue -= remainder;
			if (remainder > 12) statValue += 25;
			statValue = statValue * 4 / 100;
			System.out.println(Integer.toString(statValue, 2));
		}
		in.close();
	}

}
