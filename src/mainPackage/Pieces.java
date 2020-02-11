package mainPackage;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

//CLASE PADRE ABSTRACTA QUE CONTIENE LAS CARACTERISTICAS DE LAS PIEZAS
public abstract class Pieces implements MouseListener {

	protected boolean brk = false;
	protected Dimension pos;
	protected static Object piece;
	private String team;
	protected Image figure;
	protected int resWidth = Frame.resWidth() * 120 / 1920, resHeight = Frame.resHeight() * 120 / 1080, teamNum, row,col;
	protected JLabel labelFigure;
	protected ChessBoard board = ChessBoard.getInstance();

	protected Pieces(Dimension dimension,int row,int col) {
		
		setImage();
		setPosition(dimension);
		this.row = row;
		this.col = col;
		
	}

	// METODOS ABSTRACTOS--------------
	
	public abstract void setImage();// Obliga a establecer una imagen por cada figura

	public abstract void pieceMovement();// Obliga a establecer un movimiento por cada figura

	public abstract void newImage();// Obliga a establecer la imagen del equipo contrario por cada figura

	public abstract void setGraveyardDimension();// Obliga a establecer un cementerio por cada figura

	
	// SETTERS-----------------SETTERS

	// METODO QUE LE DA UNA DIMENSION A LA PIEZA
	public void setPosition(Dimension dimension) {

		pos = dimension;

	}

	// METODO QUE LE DA UN EQUIPO A LA PIEZA Y SEGUN EL EQUIPO LE DA UN NUMERO
	public void setTeam(String color) {

		if (color.equals("black")) {

			teamNum = 1;

		} else if (color.equals("white")) {

			teamNum = -1;

		}
		team = color;
	}

	// METODO QUE ESTABLECE UNA FILA Y UNA COLUMNA A LA PIEZA
	public void setCoord(int row, int col) {

		this.row = row;
		this.col = col;

	}

	
	// GETTERS-----------------GETTERS

	// METODO QUE DEVUELVE LA DIMENSION DE LA PIEZA
	public Dimension getPosition() {

		return pos;

	}

	// METODO QUE DEVUELVE LA ETIQUETA DE LA PIEZA
	public JLabel getLabel() {

		return labelFigure;

	}

	// METODO QUE DEVUELVE EL EQUIPO
	public String getTeam() {

		return team;

	}

	// METODO QUE DEVUELVE LA FILA DE LA PIEZA
	public int getRow() {

		return row;

	}

	// METODO QUE DEVUELVE LA COLUMNA DE LA PIEZA
	public int getCol() {

		return col;

	}

	// DETECTA CUANDO SE HACE CLICK EN UNA PIEZA
	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource().equals(labelFigure) && getTeam() == board.getTurn()) {

			piece = labelFigure;
			board.notIsFocus();
			pieceMovement();

		}
	}

	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
