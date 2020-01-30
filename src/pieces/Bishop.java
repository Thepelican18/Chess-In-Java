package pieces;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import mainPackage.Pieces;

public class Bishop extends Pieces {
	private static int count1 = 0,count2 = 0;
	
	public Bishop(Dimension dimension,int row,int col) {
			
			setImage();
			setPosition(dimension);
			this.row = row;
			this.col = col;
		}
	
	
	@Override
	public void setImage() {
		
		try {

			figure = new ImageIcon("src/IMG/blackBishop.png").getImage();
			figure = figure.getScaledInstance(resWidth,resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));
		} catch (Exception f) {
			System.out.println("No se encuentra la imagen");
		}
	}

	@Override
	public void pieceMovement() {
		
		//Movimiento diagonal izquierda superior
		int count = 1;
		for(int i = (row-1) ;i > -1 ;i--) {
		
			if(col - count < 0) {
				break;
			}
				if(board.getIsBoardOcuped(i, col- count) != null) {
			if(board.getIsBoardOcuped(i, col- count) != getTeam()) {
				board.setNextMov(i,col- count);
				count++;
				break;
			}
			count++;
			break;
				}else {
					board.setNextMov(i,col - count);
					count++;
				}
		}
		//Movimiento diagonal derecho superior
				count = 1;
				for(int i = (row-1) ;i > -1 ;i--) {
					if(col + count > 7) {
						break;
					}
					
					
						if(board.getIsBoardOcuped(i, col + count) != null) {
					if(board.getIsBoardOcuped(i, col + count) != getTeam()) {
						board.setNextMov(i,col + count);
						count++;
						break;
					}
					count++;
					break;
						}else {
							board.setNextMov(i,col + count);
							count++;
						}
				}
				//.....................................
				//Movimiento diagonal izquierda inferior
				 count = 1;
				for(int i = (row+1) ;i < 8 ;i++) {
				
					if(col - count < 0) {
						break;
					}
						if(board.getIsBoardOcuped(i, col- count) != null) {
					if(board.getIsBoardOcuped(i, col- count) != getTeam()) {
						board.setNextMov(i,col- count);
						count++;
						break;
					}
					count++;
					break;
						}else {
							board.setNextMov(i,col - count);
							count++;
						}
				}
				//Movimiento diagonal derecho superior
						count = 1;
						for(int i = (row+1) ;i < 8 ;i++) {
							if(col + count > 7) {
								break;
							}
							
								if(board.getIsBoardOcuped(i, col + count) != null) {
							if(board.getIsBoardOcuped(i, col + count) != getTeam()) {
								board.setNextMov(i,col + count);
								count++;
								break;
							}
							count++;
							break;
								}else {
									board.setNextMov(i,col + count);
									count++;
								}
						}
		
		
		
	}

	@Override
	public void newImage() {
		// TODO Auto-generated method stub
		try {

			figure = new ImageIcon("src/IMG/whiteBishop.png").getImage();
			figure = figure.getScaledInstance(resWidth,resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));
		} catch (Exception f) {
			System.out.println("No se encuentra la imagen");
		}
	}

	@Override
	public void setGraveyardDimension() {
		
		if(getTeam() == "white") {
			setPosition(new Dimension(100 + (10 * count1),900));
			count1++;
		}else {
			setPosition(new Dimension(1500+ (10 * count2),900));
			count2++;
		}
	}

}
