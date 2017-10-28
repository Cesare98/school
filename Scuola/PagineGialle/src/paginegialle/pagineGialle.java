package paginegialle;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class pagineGialle
{

	static String cogn;
	static String nome;
	static int n = 100;
	//static String newLine = "\n";
	static int scelta = 0;
	static String r = new String();

	static long p = 0;

	public static void estraiNominativo(String s)
	{
		int pos1 = s.indexOf('\t');
		cogn = s.substring(0, pos1);
		String resto = s.substring(pos1 + 1);
		int pos2 = resto.indexOf('\t');
		nome = resto.substring(0, pos2);
	}

	public static void main(String[] args)
	{
		while (scelta != 3)
		{
			Scanner in = new Scanner(System.in);
			System.out.println("1) Ricrea/crea file");
			System.out.println("2) Metti in ordine alfabetico");
			System.out.println("3) Chiudi programma");
			System.out.print("La tua scelta: ");
			scelta = in.nextInt();

			if (scelta == 1)
			{
				try
				{
					RandomAccessFile newFile = new RandomAccessFile("ordinamento.txt", "rw");

					for (int i = 0; i < n - 1; i++)
					{
						r = r + "*";
					}
					r = r + '\n';

					String v[] =
					{
						"A-B", "C-D", "E-F", "G-H", "I-L", "K-J", "M-N",
						"O-P", "Q-R", "S-T", "V-W", "X-Y", "Z"
					};

					for (int i = 0; i < 13; i++)
					{
						try
						{

							for (int j = 0; j < 20; j++)
							{
								newFile.write(r.getBytes());
							}

						} catch (IOException ex)
						{
							Logger.getLogger(pagineGialle.class.getName()).log(Level.SEVERE, null, ex);
						}
					}

					p = 0;
					for (int i = 0; i < 13; i++)
					{
						try
						{
							newFile.seek(p);
							newFile.write(v[i].getBytes());
							p = p + 20 * n;

						} catch (IOException ex)
						{
							Logger.getLogger(pagineGialle.class.getName()).log(Level.SEVERE, null, ex);
						}

					}
				} catch (FileNotFoundException ex)
				{
					Logger.getLogger(pagineGialle.class.getName()).log(Level.SEVERE, null, ex);
				}

			}

			if (scelta == 2)
			{

				try
				{
					Scanner file = new Scanner(new FileReader("agenda_telefonica_100.txt"));
					RandomAccessFile newFile = new RandomAccessFile("ordinamento.txt", "rw");
					String riga = file.nextLine();
					boolean inserito = false;
					while (!inserito)
					{

						try
						{
							p = 0;
							newFile.seek(p);
							String rig = newFile.readLine();
							while (true)
							{
								if (riga.charAt(0) == rig.charAt(0) || riga.charAt(0) == rig.charAt(2))
								{
									break;
								}
								System.out.println(rig + "     " + riga);
								p += (20 * n);
								newFile.seek(p);
								rig = newFile.readLine();
							}

							while (rig.charAt(0) == '*' && newFile.readLine() != null
									&& newFile.readLine().charAt(0) != 'A'
									&& newFile.readLine().charAt(3) != 'B'
									&& newFile.readLine().charAt(0) != 'C'
									&& newFile.readLine().charAt(3) != 'D'
									&& newFile.readLine().charAt(0) != 'E'
									&& newFile.readLine().charAt(3) != 'F'
									&& newFile.readLine().charAt(0) != 'H'
									&& newFile.readLine().charAt(3) != 'I'
									&& newFile.readLine().charAt(0) != 'L'
									&& newFile.readLine().charAt(0) != 'K'
									&& newFile.readLine().charAt(3) != 'J'
									&& newFile.readLine().charAt(0) != 'M'
									&& newFile.readLine().charAt(3) != 'N'
									&& newFile.readLine().charAt(0) != 'O'
									&& newFile.readLine().charAt(3) != 'P'
									&& newFile.readLine().charAt(0) != 'Q'
									&& newFile.readLine().charAt(3) != 'R'
									&& newFile.readLine().charAt(0) != 'S'
									&& newFile.readLine().charAt(3) != 'T'
									&& newFile.readLine().charAt(0) != 'V'
									&& newFile.readLine().charAt(3) != 'W'
									&& newFile.readLine().charAt(0) != 'X'
									&& newFile.readLine().charAt(3) != 'Y'
									&& newFile.readLine().charAt(0) != 'Z')
							{
								rig = newFile.readLine();
							}

							newFile.write(riga.getBytes());
							if (file.hasNextLine())
							{
								riga = file.nextLine();
							}
							p = 0;
							newFile.seek(p);

						} catch (IOException ex)
						{
							Logger.getLogger(pagineGialle.class.getName()).log(Level.SEVERE, null, ex);
						}
						if (riga == null)
						{
							inserito = true;
						}
					}

				} catch (FileNotFoundException ex)
				{
					Logger.getLogger(pagineGialle.class.getName()).log(Level.SEVERE, null, ex);
				}

			}

		}
	}
}
