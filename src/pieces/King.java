package pieces;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import mainPackage.Pieces;

public class King extends Pieces {
	private boolean castling = true;
	
	public King(Dimension dimension,int row,int col) {
		
		setImage();
		setPosition(dimension);
		alive = true;
		this.row = row;
		this.col = col;
	}

	@Override
	public void setImage() {
		
		try {
			figure = ImageIO.read(new File("src/IMG/blackKing.png"));
			labelFigure = new JLabel(new ImageIcon(figure));
		} catch (IOException f) {
			System.out.println("No se encuentra la imagen");
		}
	}

	@Override
	public void pieceMovement() {
		
		
		if(castling) {
			
				board.castling(getTeam(),getRow(),getCol(),getPosition());
				if(castling == false) {
				return;
				}
		}
		
		//.........................
		
		String[] x = new String[3];
		String[] y = new String[3];
		
		//-----------
		if (row == 0) {
			x[0] = "1";
			x[1] = "0";
		} else if (row == 7) {
			x[0] = "-1";
			x[1] = "0";
		} 
		//........
		if (row > 0 && row < 7) {
			x[0] = "1";
			x[1] = "-1";
			x[2] = "0";
		}
		//..........
		
		if (col == 0) {
			y[0] = "1";
			y[1] = "0";
		} else if (col == 7) {
			y[0] = "-1";
			y[1] = "0";
		}
		//................
		if (col > 0 && col < 7) {
			y[0] = "1";
			y[1] = "-1";
			y[2] = "0";
			
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (x[i] != null && y[j] != null) {
					
						
						if (board.getIsBoardOcuped(row + Integer.parseInt(x[i]), col + Integer.parseInt(y[j])) != getTeam()) {

							board.setNextMov(row + Integer.parseInt(x[i]), col + Integer.parseInt(y[j]));

						}
				}
			}
		}
	}

	@Override
	public void newImage() {
		
		try {
			figure = ImageIO.read(new File("src/IMG/whiteKing.png"));
			labelFigure = new JLabel(new ImageIcon(figure));
		} catch (IOException f) {
			System.out.println("No se encuentra la imagen");
		}
	}

	@Override
	public void setGraveyardDimension() {
		
		
	}

	public void castlingDone() {
		castling = false;
	}
}
