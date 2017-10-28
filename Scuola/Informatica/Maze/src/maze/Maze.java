package maze;

import java.util.Scanner;

/**
 *
 * @author davide.cesare
 */
public class Maze {
	
	public static void main(String[] args) {
		int n;
		System.out.println("Inserire la grandezza del labirinto");
		Scanner input = new Scanner(System.in);
		n=input.nextInt();
		AdiacencyMatrix matrix = new AdiacencyMatrix(n);
		MyFrame frame = new MyFrame(n);
	}
	
}
