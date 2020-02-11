package pieces;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import mainPackage.Pieces;

// CLASE QUE CONTIENE LAS CARACTERISTICAS DEL ALFIL
// METODOS COMENTADOS EN LA CLASE PADRE PIECES
public class Bishop extends Pieces {

	private static int count1 = 0, count2 = 0;

	public Bishop(Dimension dimension, int row, int col) {

		super(dimension, row, col);

	}

	
	// METODOS ABSTRACTOS

	@Override
	public void setImage() {

		figure = new ImageIcon("src/IMG/blackBishop.png").getImage();
		figure = figure.getScaledInstance(resWidth, resHeight, 0);
		labelFigure = new JLabel(new ImageIcon(figure));

	}

	@Override
	public void pieceMovement() {

		int count = 1;
		// MOVIMIENTO DIAGONAL IZQUIERDA SUPERIOR
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
		// .....................................
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
		board.check(false);
	}

	@Override
	public void newImage() {
		
			figure = new ImageIcon("src/IMG/whiteBishop.png").getImage();
			figure = figure.getScaledInstance(resWidth, resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));

	}

	@Override
	public void setGraveyardDimension() {

		if (getTeam() == "white") {
			
			setPosition(new Dimension(100 + (10 * count1), 900));
			count1++;
			
		} else {
			
			setPosition(new Dimension(1500 + (10 * count2), 900));
			count2++;
			
		}
	}
}
