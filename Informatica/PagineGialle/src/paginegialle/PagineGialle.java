/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paginegialle;

import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide.cesare
 */
public class PagineGialle
{

	/**
	 * @param args the command line arguments
	 */
	static String cogn;
	static String nome;
	static long p = 0;

	static public void estraiNominativi(String s)
	{
		int pos1 = s.indexOf('\t');
		cogn = s.substring(0, pos1);
		String resto = s.substring(pos1 + 1);
		int pos2 = resto.indexOf('\t');
		nome = resto.substring(0, pos2);
	}

	public static void main(String[] args)
	{

		try (Scanner file = new Scanner(new FileReader("agenda_telefonica.txt")))
		{
			RandomAccessFile output = new RandomAccessFile("Pagine.txt", "rw");
			String sb = new String();
			for (int i = 0; i < 99; i++)
			{
				sb = sb + '*';
			}

			sb = '\n' + sb;
			for (int i = 0; i < 12; i++)
			{
				if (i == 0)
				{
					output.writeBytes("A-B");
				}
				if (i == 1)
				{
					output.writeBytes("C-D");
				}
				if (i == 2)
				{
					output.writeBytes("E-F");
				}
				if (i == 3)
				{
					output.writeBytes("G-H");
				}
				if (i == 4)
				{
					output.writeBytes("I-L");
				}
				if (i == 5)
				{
					output.writeBytes("K-M");
				}
				if (i == 6)
				{
					output.writeBytes("N-O");
				}
				if (i == 7)
				{
					output.writeBytes("P-Q");
				}
				if (i == 8)
				{
					output.writeBytes("R-S");
				}
				if (i == 9)
				{
					output.writeBytes("T-V");
				}
				if (i == 10)
				{
					output.writeBytes("W-X");
				}
				if (i == 11)
				{
					output.writeBytes("Y-Z");
				}

				for (int k = 0; k < 20; k++)
				{
					output.write(sb.getBytes());
				}
				output.writeBytes("\n");

			}
			output.seek(0);

			while (file.hasNextLine())
			{
				String riga = file.nextLine();
				String rig = output.readLine();
				while (!(riga.charAt(0) != rig.charAt(0) || riga.charAt(0) != rig.charAt(2)))
				{	p = p + 20 * 100;
					output.seek(p);
					rig = output.readLine();
				}
				do
				{
					rig = output.readLine();
				} while (rig.charAt(0) == '*');
				output.writeBytes(riga);
				riga = file.nextLine();
				p = 0;
				output.seek(p);
			}
			p = p + 20 * 100;
			output.seek(p);

		} catch (IOException ex)
		{
			Logger.getLogger(PagineGialle.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
