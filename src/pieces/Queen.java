package pieces;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import mainPackage.Pieces;

//CLASE QUE CONTIENE LAS CARACTERISTICAS DE LA REINA
//METODOS COMENTADOS EN LA CLASE PADRE PIECES
public class Queen extends Pieces {

	private static int count1 = 0, count2 = 0;

	public Queen(Dimension dimension, int row, int col) {

		super(dimension, row, col);

	}

	
	// METODOS ABSTRACTOS

	@Override
	public void setImage() {

		figure = new ImageIcon("src/IMG/blackQueen.png").getImage();
		figure = figure.getScaledInstance(resWidth, resHeight, 0);
		labelFigure = new JLabel(new ImageIcon(figure));

	}

	@Override
	public void pieceMovement() {

		// MOVIMIENTOS DIAGONALES
		// MOVIMIENTO DIAGONAL IZQUIERDA SUPERIOR
		int count = 1;

		for (int i = (row - 1); i > -1; i--) {

			if (col - count < 0) {

				break;

			}
			if (board.getIsBoardOcuped(i, col - count) != null) {

				if (board.getIsBoardOcuped(i, col - count) != getTeam()) {

					board.setNextMov(i, col - count);
					count++;
					break;

				}
				count++;
				break;

			} else {

				board.setNextMov(i, col - count);
				count++;

			}
		}
		// MOVIMIENTO DIAGONAL DERECHO SUPERIOR
		count = 1;
		
		for (int i = (row - 1); i > -1; i--) {
			
			if (col + count > 7) {
				
				break;
				
			}
			if (board.getIsBoardOcuped(i, col + count) != null) {
				
				if (board.getIsBoardOcuped(i, col + count) != getTeam()) {
					
					board.setNextMov(i, col + count);
					count++;
					break;
					
				}
				count++;
				break;
				
			} else {
				
				board.setNextMov(i, col + count);
				count++;
				
			}
		}
		// MOVIMIENTO DIAGONAL IZQUIERDA INFERIOR
		count = 1;
		
		for (int i = (row + 1); i < 8; i++) {

			if (col - count < 0) {
				
				break;
				
			}
			if (board.getIsBoardOcuped(i, col - count) != null) {
				
				if (board.getIsBoardOcuped(i, col - count) != getTeam()) {
					
					board.setNextMov(i, col - count);
					count++;
					break;
					
				}
				count++;
				break;
				
			} else {
				
				board.setNextMov(i, col - count);
				count++;
				
			}
		}
		// MOVIMIENTO DIAGONAL DERECHO INFERIOR
		count = 1;
		
		for (int i = (row + 1); i < 8; i++) {
			
			if (col + count > 7) {
				
				break;
				
			}

			if (board.getIsBoardOcuped(i, col + count) != null) {
				
				if (board.getIsBoardOcuped(i, col + count) != getTeam()) {
					
					board.setNextMov(i, col + count);
					count++;
					break;
					
				}
				count++;
				break;
	
			} else {
				
				board.setNextMov(i, col + count);
				count++;
				
			}
		}
		// ..............................................................
		// MOVIMIENTOS HORIZONTALES Y VERTICALES

		// MOVIMIENTO VERTICAL

		for (int i = row - 1; i > -1; i--) {

			if (board.getIsBoardOcuped(i, col) != null) {
				
				if (board.getIsBoardOcuped(i, col) != getTeam()) {

					board.setNextMov(i, col);
					break;
					
				}
				break;
				
			} else {

				board.setNextMov(i, col);
				
			}
		}
		for (int i = row + 1; i < 8; i++) {

			if (board.getIsBoardOcuped(i, col) != null) {
				
				if (board.getIsBoardOcuped(i, col) != getTeam()) {

					board.setNextMov(i, col);
					break;
					
				}
				break;
				
			} else {

				board.setNextMov(i, col);
				
			}
		}

		// MOVIMIENTO HORIZONTAL
		for (int i = col + 1; i < 8; i++) {

			if (board.getIsBoardOcuped(row, i) != null) {

				if (board.getIsBoardOcuped(row, i) != getTeam()) {
					
					board.setNextMov(row, i);
					break;
					
				}
				break;
				
			} else if (board.getIsBoardOcuped(row, i) == null) {
				
				board.setNextMov(row, i);
				
			}
		}
		for (int i = col - 1; i > -1; i--) {
			
			if (board.getIsBoardOcuped(row, i) != null) {
				
				if (board.getIsBoardOcuped(row, i) != getTeam()) {
					
					board.setNextMov(row, i);
					break;
					
				}
				break;
			} else if (board.getIsBoardOcuped(row, i) == null) {
				
				board.setNextMov(row, i);
				
			}
		}
		board.check(false);
	}

	@Override
	public void newImage() {

			figure = new ImageIcon("src/IMG/whiteQueen.png").getImage();
			figure = figure.getScaledInstance(resWidth, resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));

	}

	@Override
	public void setGraveyardDimension() {

		if (getTeam() == "white") {
			
			setPosition(new Dimension(100 + (10 * count1), 100));
			count1++;
			
		} else {
			
			setPosition(new Dimension(1500 + (10 * count2), 100));
			count2++;
			
		}
	}
}
