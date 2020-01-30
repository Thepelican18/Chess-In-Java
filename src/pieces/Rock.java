package pieces;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import mainPackage.Pieces;

public class Rock extends Pieces {
	private static int count1 = 0,count2 = 0;
	private boolean castling = true;
	
	public Rock(Dimension dimension,int row, int col) {
		setImage();
		setPosition(dimension);
		this.row = row;
		this.col = col;
	}


	@Override
	public void setImage() {
		
		try {

			figure = new ImageIcon("src/IMG/blackRock.png").getImage();
			figure = figure.getScaledInstance(resWidth,resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));
		} catch (Exception f) {
			System.out.println("No se encuentra la imagen");
		}
		
	}

	@Override
	public void pieceMovement() {
		
		//MOVIMIENTO VERTICAL 
		
			
			for (int i = row - 1; i > -1; i--) {

				if(board.getIsBoardOcuped(i, col) != null) {
					if(board.getIsBoardOcuped(i, col) != getTeam()) {
						
						board.setNextMov(i,col);
						break;
					}
					
					break;
				} else{
					
					board.setNextMov(i,col);
				}
			
		}
			for (int i = row + 1; i < 8; i++) {

				if(board.getIsBoardOcuped(i, col) != null) {
					if(board.getIsBoardOcuped(i, col) != getTeam()) {
						
						board.setNextMov(i,col);
						break;
					}
					break;
				} else {
					
					board.setNextMov(i,col);
				}
			}
		
		//MOVIMIENTO HORIZONTAL
		for(int i = col+1;i < 8 ;i++) {
		
			
			if(board.getIsBoardOcuped(row, i) != null) {
				
				if(board.getIsBoardOcuped(row, i) != getTeam()) {
					board.setNextMov(row,i);
					break;
				}
				System.out.println("el camino esta bloqueado " + row + " " + i);
				break;
			}else if(board.getIsBoardOcuped(row, i) == null) {
				board.setNextMov(row,i);
			}
		}
		System.out.println("Movimiento hacia la izquierda");
		for(int i = col-1;i > -1 ;i--) {
			System.out.println(row +" "+ i);
			if(board.getIsBoardOcuped(row, i) != null) {
				if(board.getIsBoardOcuped(row, i) != getTeam()) {
					board.setNextMov(row,i);
					break;
				}
				System.out.println("el camino esta bloqueado " + row + " " + i);
				break;
			}else if(board.getIsBoardOcuped(row, i) == null) {
				board.setNextMov(row,i);
			}
		}
	}

	@Override
	public void newImage() {
		try {

			figure = new ImageIcon("src/IMG/whiteRock.png").getImage();
			figure = figure.getScaledInstance(resWidth,resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));
		} catch (Exception f) {
			System.out.println("No se encuentra la imagen");
		}
	}
	@Override
	public void setGraveyardDimension() {
		if(getTeam() == "white") {
			setPosition(new Dimension(100 + (10 * count1),500));
			count1++;
		}else {
			setPosition(new Dimension(1500+ (10 * count2),500));
			count2++;
		}
	}
	public void castlingOut() {
		castling = false;
	}
	public boolean getCastling() {
		return castling;
	}

}
