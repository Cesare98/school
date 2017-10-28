/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public final class MyFrame extends JFrame implements ActionListener {

	int riga = 0, colonna = 0;
	private AdiacencyMatrix matrice;
	private static final String title = "Labirinto";
	private static final int width = 400, high = 600;
	JButton confirm, start, end;
	boolean modifica, inizio, fine;
	private Integer lung;
	private JButton[][] btn;
	private JPanel p, p1;

	public MyFrame(int n) {

		modifica = true;
		inizio = fine = false;
		lung = n;
		matrice = new AdiacencyMatrix(lung * lung);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, high);

		p = new JPanel();
		p1 = new JPanel();
		p.setBackground(Color.white);
		p1.setLayout(new GridLayout(3, 1));
		p.setLayout(new GridLayout(lung, lung));
		setLayout(new GridLayout(2, 1));
		btn = new JButton[n][n];

		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				btn[i][k] = new JButton();
				btn[i][k].setText("");
				btn[i][k].setBackground(Color.white);
				btn[i][k].addActionListener(this);
				p.add(btn[i][k]);
			}
		}

		for (int i = 0; i < n * n; i++) {
			if (i % n != 0) {
				matrice.addEdge(i, i - 1);
			}
			if (i % n != n - 1) {
				matrice.addEdge(i, i + 1);
			}
			if (i >= n) {
				matrice.addEdge(i, i - n);
			}
			if (i + n < n * n) {
				matrice.addEdge(i, i + n);
			}
		}

		confirm = new JButton("Conferma");
		start = new JButton("Inizio");
		end = new JButton("Fine");
		confirm.addActionListener(this);
		start.addActionListener(this);
		end.addActionListener(this);
		start.setVisible(false);
		end.setVisible(false);
		p1.add(confirm);
		p1.add(start);
		p1.add(end);
		add(p);
		add(p1);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object action = e.getSource();
		if (modifica) {
			for (int i = 0; i < lung; i++) {
				for (int j = 0; j < lung; j++) {
					if (action.equals(btn[i][j]) && btn[i][j].getBackground().equals(Color.white)) {
						btn[i][j].setBackground(Color.black);
						if (i > 0) {
							matrice.removeEdge(i * lung + j, (i - 1) * lung + j);
						}
						if (i < lung - 1) {
							matrice.removeEdge(i * lung + j, (i + 1) * lung + j);
						}
						if (j > 0) {
							matrice.removeEdge(i * lung + j, i * lung + (j - 1));
						}
						if (j < lung - 1) {
							matrice.removeEdge(i * lung + j, i * lung + (j + 1));
						}
					} else if (action.equals(btn[i][j]) && btn[i][j].getBackground().equals(Color.black)) {
						btn[i][j].setBackground(Color.white);
						if (i > 0 && btn[i - 1][j].getBackground().equals(Color.white)) {

							matrice.addEdge(i * lung + j, (i - 1) * lung + j);

						}
						if (i < lung - 1 && btn[i + 1][j].getBackground().equals(Color.white)) {

							matrice.addEdge(i * lung + j, (i + 1) * lung + j);

						}
						if (j > 0 && btn[i][j - 1].getBackground().equals(Color.white)) {

							matrice.addEdge(i * lung + j, i * lung + (j - 1));

						}
						if (j < lung - 1 && btn[i][j + 1].getBackground().equals(Color.white)) {

							matrice.addEdge(i * lung + j, i * lung + (j + 1));

						}
					}
				}
			}
		}

		if (action.equals(confirm)) {
			modifica = false;
			inizio = true;
			start.setVisible(true);
			confirm.setVisible(false);
			for (int i = 0; i < lung; i++) {
				for (int j = 0; j < lung; j++) {
					if (btn[i][j].getBackground().equals(Color.black)) {
						btn[i][j].setEnabled(false);
					}
				}
			}
		}

		if (inizio) {
			for (int l = 0; l < lung; l++) {
				for (int m = 0; m < lung; m++) {
					if (action.equals(btn[l][m])) {
						if (btn[l][m].getBackground().equals(Color.white)) {
							btn[l][m].setBackground(Color.yellow);
							riga = l;
							colonna = m;

						} else {
							btn[l][m].setBackground(Color.white);
						}
					}
				}
			}
		}

		if (action.equals(start)) {
			inizio = false;
			fine = true;
			start.setVisible(false);
			end.setVisible(true);
			for (int i = 0; i < lung; i++) {
				for (int j = 0; j < lung; j++) {
					if (btn[i][j].getBackground().equals(Color.yellow)) {
						btn[i][j].setEnabled(false);
					}
				}
			}
		}

		if (fine) {
			for (int i = 0; i < lung; i++) {
				for (int j = 0; j < lung; j++) {
					if (action.equals(btn[i][j])) {
						if (btn[i][j].getBackground().equals(Color.white)) {
							btn[i][j].setBackground(Color.red);
						} else {
							btn[i][j].setBackground(Color.white);
						}
					}
				}
			}
		}

		int rowEnd = 0;
		int columnEnd = 0;
		if (action.equals(end)) {
			int[] percorso = new int[50];
			fine = false;
			end.setVisible(false);
			for (int i = 0; i < lung; i++) {
				for (int j = 0; j < lung; j++) {
					if (btn[i][j].getBackground().equals(Color.red)) {
						btn[i][j].setEnabled(false);
						rowEnd = i;
						columnEnd = j;
					}
				}
			}

			int originVertice = (riga * lung) + colonna;
			int endVertice = (rowEnd * lung) + columnEnd;//vertice di destinazione
			percorso = matrice.BFSspanningTree(originVertice);

			for (int i = 0; i < percorso.length; i++) {
				System.out.println(percorso[i]);
			}

			//se la destionazione è nella prima riga
			if (endVertice < lung) {

				int fatherVertice = endVertice;//creazione ed assegnazione del nodo padre

				//se il vertice di destinazione è in basso si effettua il bfs dal vertice di arrivo    
				int columnToFound;
				int rowToFound;

				while (fatherVertice != originVertice) {

					rowToFound = fatherVertice % lung;
					columnToFound = fatherVertice / lung;

					if (fatherVertice == -1) {
						JOptionPane.showConfirmDialog(this, "Non esiste percorso", "Ouch!", JOptionPane.OK_CANCEL_OPTION);
						break;
					}

					//se la casella è avviabile cambia il colore
					if (btn[columnToFound][rowToFound].getBackground().equals(Color.WHITE)) {
						btn[columnToFound][rowToFound].setBackground(Color.CYAN);
					}

					fatherVertice = percorso[fatherVertice];
				}
			}

			//se il vertice di destinazione si trova dopo la prima riga
			if (endVertice > lung) {

				int fatherVertice = endVertice;//creazione ed assegnazione del nodo padre

				//se il vertice di destinazione è in basso si effettua il bfs dal vertice di arrivo    
				int columnToFound;
				int rowToFound;

				while (fatherVertice != originVertice) {

					rowToFound = fatherVertice % lung;
					columnToFound = fatherVertice / lung;

					if (fatherVertice == -1) {
						JOptionPane.showConfirmDialog(this, "Non esiste percorso", "Ouch!", JOptionPane.OK_CANCEL_OPTION);
						break;
					}

					//se la casella è avviabile cambia il colore
					if (btn[columnToFound][rowToFound].getBackground().equals(Color.WHITE)) {
						btn[columnToFound][rowToFound].setBackground(Color.CYAN);
					}

					fatherVertice = percorso[fatherVertice];
				}
			}
		}
	}
}
