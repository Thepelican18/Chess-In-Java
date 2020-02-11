package mainPackage;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * @author ThePelican18
 */
public class ChessAPK {

	public static void main(String[] args) {
	
		new Frame();
			
	}
}
//MARCO PRINCIPAL
class Frame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Frame() {
	    setIconImage(new ImageIcon("src/IMG/chessIcon.png").getImage());
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		ChessBoard board = ChessBoard.getInstance();
		add(board);//Añade el JPanel
		   
	}
	
	//METODO QUE DEVUELVE EL ANCHO DE LA PANTALLA
	public static int resWidth() {
		
		return (int)resolution.getWidth();
		
	}
	
	//METODO QUE DEVUELVE EL ALTO DE LA PANTALLA
	public static int resHeight() {
		
		return (int)resolution.getHeight();
	}
}
