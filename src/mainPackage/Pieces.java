package mainPackage;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Pieces implements MouseListener{

	protected boolean alive = false,brk = false;
	protected Dimension pos;
	protected static Object piece;
	private String team;
	protected Image figure;
	protected int resWidth=Frame.resWidth()*120/1920;
	protected int resHeight=Frame.resHeight()*120/1080;
	protected JLabel labelFigure;
	protected ChessBoard board = ChessBoard.getInstance();
	protected int teamNum,row,col;
	
	
	protected Pieces(){
		
		
	}
	
	public abstract void setImage();
	public abstract void pieceMovement();
	public abstract void newImage();
	public abstract void setGraveyardDimension();
	
	public void setPosition(Dimension dimension) {
		pos = dimension;
	}
	public Dimension getPosition() {
		return pos;
	}
	public boolean itsalive() {
		return alive;
	}
	public Image getImage() {
		
		return figure;
	}
	public JLabel getLabel() {
		return labelFigure;
	}
	public void setTeam(String color) {
		if(color.equals("black")) {
			teamNum = 1;
		}else if(color.equals("white")) {
			teamNum = -1;
		}
		team = color;
	}
	public String getTeam() {
		return team;
	}
	public void setCoord(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getSource().equals(labelFigure) && getTeam() == board.getTurn()) {
			piece = labelFigure;
			board.isFocus(false);
			pieceMovement();
			

		}

	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
