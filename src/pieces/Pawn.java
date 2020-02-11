package pieces;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import mainPackage.Pieces;

// CLASE QUE CONTIENE LAS CARACTERISTICAS DEL PEON
// METODOS COMENTADOS EN LA CLASE PADRE PIECES
public class Pawn extends Pieces {
	
	private boolean isFirstMovement = true;// Movimiento doble el primer desplazamiento
	private int count1 = 0,count2 = 0;// Variables para los cementerios

	public Pawn(Dimension dimension,int row,int col) {
		
		super(dimension,row,col);// Constructor padre
		
	}

	// METODOS ABSTRACTOS
	@Override
	public void setImage() {

			figure = new ImageIcon("src/IMG/blackPawn.png").getImage();
			figure = figure.getScaledInstance(resWidth,resHeight, 0);
			labelFigure = new JLabel(new ImageIcon(figure));
			
	}
	
	@Override
	public void newImage() {
		
			figure = new ImageIcon("src/IMG/whitePawn.png").getImage();
			figure = figure.getScaledInstance(resWidth,resHeight ,0);
			labelFigure = new JLabel(new ImageIcon(figure));
			
	}
	
	@Override
	public void pieceMovement() {
		
					int i = row;
					int j = col;
					
					// COMPRUEBA SI EL PEON SE PUEDE SALIR DEL LIMITE
					if(teamNum == 1 && i == 0) {
						
						System.out.println("entrado");
						board.check(false);
						return;
						
					}else if(teamNum == -1 && i == 7) {
						
						board.check(false);
						return;
						
					}
					
					
					// COMPRUEBA SI EL PEON PUEDE MATAR
					if(j==0) {
						
						if(board.getIsBoardOcuped(i - (1*teamNum),j+1) != null && board.getIsBoardOcuped(i - (1*teamNum),j+1) != getTeam()){
							
							board.setNextMov(i - (1*teamNum),j+1);
							
						}
					}else if(j==7) {
						
						if(board.getIsBoardOcuped(i - (1*teamNum),j-1) != null && board.getIsBoardOcuped(i - (1*teamNum),j-1) != getTeam()){
							
							board.setNextMov(i - (1*teamNum),j-1);
							
						}
						
					}else {
						
						if(board.getIsBoardOcuped(i - (1*teamNum),j+1) != null && board.getIsBoardOcuped(i - (1*teamNum),j-1) != null && board.getIsBoardOcuped(i - (1*teamNum),j+1) != getTeam() && board.getIsBoardOcuped(i - (1*teamNum),j-1) != getTeam()) {
							
							board.setNextMov(i - (1*teamNum),j-1);
							board.setNextMov(i - (1*teamNum),j+1);
							
						}else if(board.getIsBoardOcuped(i - (1*teamNum),j-1) != null && board.getIsBoardOcuped(i - (1*teamNum),j-1) != getTeam()) {
							
							board.setNextMov(i - (1*teamNum),j-1);
							
						}else if(board.getIsBoardOcuped(i - (1*teamNum),j+1) != null && board.getIsBoardOcuped(i - (1*teamNum),j+1) != getTeam()) {
							
							board.setNextMov(i - (1*teamNum),j+1);
							
						}
							
						}
					// COMPRUEBA LAS CASILLAS A LAS QUE SE PUEDE MOVER EL PEON
					if(isFirstMovement) {
						
						if (board.getIsBoardOcuped(i - (1*teamNum),j) == null && board.getIsBoardOcuped(i - (2*teamNum),j) == null) {

							board.setNextMov(i - (1*teamNum),j);
							board.setNextMov(i - (2*teamNum),j);

						}else if(board.getIsBoardOcuped(i - (1*teamNum),j) == null) {
							
							board.setNextMov(i - (1*teamNum),j);
							
						}
					}else {
					
						if (board.getIsBoardOcuped(i - (1*teamNum),j) == null) {
	
							board.setNextMov(i - (1*teamNum),j);
							
						}
					}
					board.check(false);
				}
	
	@Override
	public void setGraveyardDimension() {
		
		if(getTeam() == "white") {
			
			setPosition(new Dimension(100 + (10 * count1),300));
			count1++;
			
		}else {
			
			setPosition(new Dimension(1500+ (10 * count2),300));
			count2++;
			
		}
	}
	
	// METODOS PROPIOS DE LA CLASE PEON
	
	// METODO QUE DA POR FINALIZADA LA VENTAJA DEL PRIMER MOVIMIENTO
		public void firstMovementDone(){
			
			isFirstMovement = false;
			
		}
}
