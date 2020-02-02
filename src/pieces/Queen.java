package pieces;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import mainPackage.Pieces;

public class Queen extends Pieces {

	private static int count1 = 0, count2 = 0;

	public Queen(Dimension dimension, int row, int col) {

		setImage();
		setPosition(dimension);
		this.row = row;
		this.col = col;
	}

	@Override
	public void setImage() {

		try {

			figure = new ImageIcon("src/IMG/blackQueen.png").getImage();
			figure = figure.getScaledInstance(resWidth,resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));
		} catch (Exception f) {
			System.out.println("No se encuentra la imagen");
		}

	}

	@Override
	public void pieceMovement() {

		// MOVIMIENTOS DIAGONALES
		// Movimiento diagonal izquierda superior
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
		// Movimiento diagonal derecho superior
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
		// Movimiento diagonal izquierda inferior
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
		// Movimiento diagonal derecho superior
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
				System.out.println("el camino esta bloqueado " + row + " " + i);
				break;
			} else if (board.getIsBoardOcuped(row, i) == null) {
				board.setNextMov(row, i);
			}
		}
		System.out.println("Movimiento hacia la izquierda");
		for (int i = col - 1; i > -1; i--) {
			System.out.println(row + " " + i);
			if (board.getIsBoardOcuped(row, i) != null) {
				if (board.getIsBoardOcuped(row, i) != getTeam()) {
					board.setNextMov(row, i);
					break;
				}
				System.out.println("el camino esta bloqueado " + row + " " + i);
				break;
			} else if (board.getIsBoardOcuped(row, i) == null) {
				board.setNextMov(row, i);
			}
		}
		board.check(false);

	}

	@Override
	public void newImage() {

		try {

			figure = new ImageIcon("src/IMG/whiteQueen.png").getImage();
			figure = figure.getScaledInstance(resWidth,resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));
		} catch (Exception f) {
			System.out.println("No se encuentra la imagen");
		}

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
