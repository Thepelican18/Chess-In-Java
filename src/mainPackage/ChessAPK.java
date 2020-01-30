package mainPackage;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ChessAPK {

	public static void main(String[] args) {
	
		new Frame();
		
		
	}

}

class Frame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
	public Frame() {
		
		try {
			setIconImage(new ImageIcon("src/IMG/chessIcon.png").getImage());
			}catch(Exception f) {
				System.out.println("No se ha podido cargar el icono");
			}
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		ChessBoard board = ChessBoard.getInstance();
		add(board);
		   
	}
	
	public static int resWidth() {
		
		return (int)resolution.getWidth();
	}
	public static int resHeight() {
		
		return (int)resolution.getHeight();
	}
	
}
