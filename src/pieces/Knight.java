package pieces;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import mainPackage.Pieces;

// CLASE QUE CONTIENE LAS CARACTERISTICAS DEL CABALLO
// METODOS COMENTADOS EN LA CLASE PADRE PIECES
public class Knight extends Pieces {

	private static int count1 = 0, count2 = 0;

	public Knight(Dimension dimension, int row, int col) {

		super(dimension, row, col);

	}

	
	// METODOS ABSTRACTOS
	@Override
	public void setImage() {

			figure = new ImageIcon("src/IMG/blackKnight.png").getImage();
			figure = figure.getScaledInstance(resWidth, resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));
	
	}

	@Override
	public void pieceMovement() {
		// Arrays que van a guardar las posiciones a las que se puede mover el caballo
		String[] x = new String[4];
		String[] j = new String[4];

		// .....................................
		if (row == 0) {
			
			x[0] = "1";
			x[1] = "2";
			
		} else if (row == 7) {
			
			x[0] = "-1";
			x[1] = "-2";
			
		} else if (row == 1) {
			
			x[0] = "1";
			x[1] = "2";
			x[2] = "-1";
			
		} else if (row == 6) {
			
			x[0] = "-1";
			x[1] = "-2";
			x[2] = "1";
			
		}

		if (row > 1 && row < 6) {
			
			x[0] = "1";
			x[1] = "-1";
			x[2] = "2";
			x[3] = "-2";
			
		}
		// .....................................

		if (col == 0) {
			
			j[0] = "1";
			j[1] = "2";
			
		} else if (col == 7) {
			
			j[0] = "-1";
			j[1] = "-2";
			
		} else if (col == 1) {
			
			j[0] = "1";
			j[1] = "2";
			j[2] = "-1";
			
		} else if (col == 6) {
			
			j[0] = "-1";
			j[1] = "-2";
			j[2] = "1";
			
		}

		if (col > 1 && col < 6) {
			
			j[0] = "1";
			j[1] = "-1";
			j[2] = "2";
			j[3] = "-2";
			
		}

		// Bucle para activar las posiciones de los arrays
		for (int i = 0; i < 4; i++) {
			
			for (int z = 0; z < 4; z++) {
				
				if (x[i] != null && j[z] != null) {
					
					if (x[i] != j[z] && Integer.parseInt(x[i]) != -Integer.parseInt(j[z])) {
						
						if (board.getIsBoardOcuped(row + Integer.parseInt(x[i]),col + Integer.parseInt(j[z])) != getTeam()) {

							board.setNextMov(row + Integer.parseInt(x[i]), col + Integer.parseInt(j[z]));

						}
					}
				}
			}
		}
		board.check(false);
	}

	@Override
	public void newImage() {

			figure = new ImageIcon("src/IMG/whiteKnight.png").getImage();
			figure = figure.getScaledInstance(resWidth, resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));

	}

	@Override
	public void setGraveyardDimension() {

		if (getTeam() == "white") {
			
			setPosition(new Dimension(100 + (10 * count1), 700));
			count1++;
			
		} else {
			
			setPosition(new Dimension(1500 + (10 * count2), 700));
			count2++;
			
		}
	}
}
