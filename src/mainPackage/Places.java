package mainPackage;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Places implements MouseListener {
	protected JLabel place;
	private Image squarePlace;
	private int col,row;
	private ChessBoard board = ChessBoard.getInstance();
	private Dimension dimensionPlace;
	public Places(Dimension dimension) {
		try {
			place = new JLabel(new ImageIcon(squarePlace = ImageIO.read(new File("src/IMG/movPlace.png"))));
			}catch(IOException  f) {
				System.out.println("No se ha podido crear la imagen square");
			}
		dimensionPlace = dimension;
	}
	public JLabel getLabelPlace() {
		return place; 
	}
	public void setDimensionPlace(Dimension dimension) {
		dimensionPlace = dimension;
	}
	public Dimension getDimensionPlace() {
		return dimensionPlace;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(place)) {
			board.movPiece(dimensionPlace,row,col);
		}
	}
	public void setCoordPlace(int row, int col) {
		this.row =row;
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
