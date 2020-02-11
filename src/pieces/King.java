package pieces;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import mainPackage.Pieces;

//CLASE QUE CONTIENE LAS CARACTERISTICAS DEL REY
//METODOS COMENTADOS EN LA CLASE PADRE PIECES
public class King extends Pieces {
	
	private boolean castling = true;//Variable para comprobar si puede hacer enroque
	
	public King(Dimension dimension,int row,int col) {
		
		super(dimension,row,col);
		
	}

	
	//METODOS ABSTRACTOS

	@Override
	public void setImage() {

			figure = new ImageIcon("src/IMG/blackKing.png").getImage();
			figure = figure.getScaledInstance(resWidth,resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));
		
	}

	@Override
	public void pieceMovement() {
		
		// COMPRUEBA SI PUEDE HACER ENROQUE
		if(castling) {
			
				board.castling(getTeam(),getRow(),getCol(),getPosition());
				
				// Para que cuando enroque no le permita hacer un movimiento extra
				if(castling == false) {
					
				return;
				
				}
				
		}
		
		//Array de posibles movimientos para el rey
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
		
		// MOVIMIENTO DEL REY
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
		
			figure = new ImageIcon("src/IMG/whiteKing.png").getImage();
			figure = figure.getScaledInstance(resWidth,resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));
		
	}

	@Override
	public void setGraveyardDimension() {}//El rey no tiene cementerio
	
	//METODOS PROPIOS
	
	//METODO QUE DA POR FINALIZADO EL ENROQUE
	public void castlingDone() {
		
		castling = false;
		
	}
}
