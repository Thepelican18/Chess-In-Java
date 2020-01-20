package mainPackage;



import javax.swing.JFrame;

public class Frame extends JFrame{

	private ChessBoard board = null;
	
	public Frame() {
				
		ChessBoard board = ChessBoard.getInstance();
		add(board);
		   
	}
	
	
}
