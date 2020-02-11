package mainPackage;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

// CLASE PARA LAS CASILLAS QUE PERMITEN MOVERSE A UNA FICHA
public class Places implements MouseListener {

	protected JLabel place;
	private Image squarePlace, checkPlaceIMG;
	private int col, row;
	private ChessBoard board = ChessBoard.getInstance();
	private Dimension dimensionPlace;

	public Places(Dimension dimension) {

		squarePlace = new ImageIcon("src/IMG/movPlace.png").getImage();
		squarePlace = squarePlace.getScaledInstance(Frame.resWidth() * 120 / 1920, Frame.resHeight() * 120 / 1080, 0);
		place = new JLabel(new ImageIcon(squarePlace));
		dimensionPlace = dimension;

	}

	// CONSTRUCTOR PARA LA CASILLA DEL JAQUE
	public Places(int row, int col) {

		checkPlaceIMG = new ImageIcon("src/IMG/checkPlace.png").getImage();
		checkPlaceIMG = checkPlaceIMG.getScaledInstance(Frame.resWidth() * 120 / 1920, Frame.resHeight() * 120 / 1080, 0);
		place = new JLabel(new ImageIcon(checkPlaceIMG));
		dimensionPlace = new Dimension(row, col);

	}
	
	// METODO QUE ESTABLECE UNA DIMENSION PARA LA CASILLA
	public void setDimensionPlace(Dimension dimension) {
		
		dimensionPlace = dimension;
		
	}
	

	// METODO QUE ESTABLECE UNA FILA Y UNA COLUMNA PARA LA CASILLA
	public void setCoordPlace(int row, int col) {
		
		this.row = row;
		this.col = col;
		
	}
	
	// GETTERS-----------------GETTERS
	
	// METODO QUE DEVUELVE LA ETIQUETA DE LA CASILLA
	public JLabel getLabelPlace() {
			
			return place;
			
		}
	
	// METODO QUE DEVUELVE LA DIMENSION DE LA CASILLA
	public Dimension getDimensionPlace() {
			
			return dimensionPlace;
			
		}
	
	// METODO QUE DEVUELVE LA FILA DE LA CASILLA
	public int getRow() {
		
		return row;
	}

	// METODO QUE DEVUELVE LA COLUMNA DE LA CASILLA
	public int getCol() {
		
		return col;
		
	}
	
	// DETECTA CUANDO SE HACE CLICK EN UNA CASILLA
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getSource().equals(place)) {
			
			board.movPiece(dimensionPlace, row, col);
			
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
