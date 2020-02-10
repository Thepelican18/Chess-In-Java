package mainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import pieces.Pawn;
import pieces.Queen;
import pieces.Rock;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;

public class ChessBoard extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Graphics2D g2D;

	private Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
	private Rectangle2D[][] paintBoard = new Rectangle2D[8][8];
	private static final Dimension board[][] = new Dimension[8][8];
	private static String[][] isBoardOcuped = new String[8][8];
	private double x, y;
	private String turn = "black", team, checkString = "";
	private int countPos = 0, count = 0, index;
	// ................................
	private Pieces[] figures = new Pieces[16];
	private Pawn[] pawn = new Pawn[16];
	private Rock[] rock = new Rock[4];
	private Knight[] knight = new Knight[4];
	private Bishop[] bishop = new Bishop[4];
	private Queen[] queen = new Queen[2];
	private King[] king = new King[2];
	// ................................
	private boolean draw = true, focus = false, check = false;
	private Places place[] = new Places[33];
	private static ChessBoard chessBoard = null;
	int timeSec = 0, timeMin = 0;

	// ................................
	public static ChessBoard getInstance() {

		if (chessBoard == null) {
			chessBoard = new ChessBoard();
		}
		return chessBoard;
	}

	// ................................
	private ChessBoard() {

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		setLayout(null);
		g2D = (Graphics2D) g;
		
		//COLOR DE FONDO
		setBackground(Color.GRAY.darker().darker());

		// COLOR PRIMARIO
		g2D.drawImage(new ImageIcon("src/IMG/backgroundBrown.png").getImage(), (Frame.resWidth() * 443) / 1920,
				(Frame.resHeight() * 15) / 1080, null);
		
		// COLOR SECUNDARIO

		g2D.drawImage(new ImageIcon("src/IMG/backgroundWhite.png").getImage(), Frame.resWidth() * 480 / 1920,
				(Frame.resHeight() * 50) / 1080, null);
		
		//DIBUJAR EL TURNO
		g2D.setPaint(Color.RED);
		g2D.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		if (turn == "black") {
			g2D.drawString("Turno negras", 30, 50);

		} else {
			g2D.drawString("Turno blancas", 30, 50);
		}

		//DIBUJAR EL TIEMPO
		g2D.setFont(new Font("TimesRoman", Font.PLAIN, 45));
		g2D.drawString(Integer.toString(timeMin) + ":" + Integer.toString(timeSec), 1600, 50);

		paintBoard();
		// DIBUJADO QUE INSTANCIA LAS PIEZAS
		if (draw) {
			drawBoard();
			Timer timer = new Timer(1000, this);// Temporizador que hace que se actualice cada segundo
			timer.start();// comienzo del temporizar
			draw = false;
		}

		//DIBUJA "JAQUE"
		g2D.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		g2D.drawString(checkString, 200, 50);
		
		reDrawBoard();//Redibujar las piezas

	}
	
	//METODO QUE CREA LAS DIMENSIONES DE CADA CUADRADO Y COLOCA LAS RESPECTIVAS IMAGENES
	public void paintBoard() {

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				y = (((Frame.resHeight() * 960) / 1080) / 8) * i;
				x = ((Frame.resWidth() / 2) / 8) * j;

				board[i][j] = new Dimension((int) (Frame.resWidth() / 4) + (int) x,
						(int) (((Frame.resHeight() * 75) / 1080) / 2) + (int) y);
				if ((i + j) % 2 == 0) {

					g2D.drawImage(new ImageIcon("src/IMG/backgroundBlack.png").getImage(),
							(int) (Frame.resWidth() / 4) + (int) x, (int) (Frame.resHeight() * 50 / 1080) + (int) y,
							null);
				}
			}
		}
	}

	public void drawBoard() {
		System.out.println("Creando..");
		count = 0;
		index = 6;
		team = "black";
		// Coloca 32 JLabels fuera del tablero para en un futuro usarlos como posibles
		// movimientos de la pieza
		for (int i = 0; i < 32; i++) {

			place[i] = new Places(new Dimension(-1000, -1000));
			add(place[i].getLabelPlace());
			place[i].getLabelPlace().addMouseListener(place[i]);
		}
		place[32] = new Places(-1000, -1000);
		add(place[32].getLabelPlace());

		// Dibujar peones
		for (int i = 0; i < 16; i++) {

			if (i == 8) {
				index = 1;
				count = 0;
				team = "white";
			}

			pawn[i] = new Pawn(board[index][count], index, count);
			pawn[i].setTeam(team);

			if (team == "white") {
				pawn[i].newImage();
			}

			isBoardOcuped[index][count] = team;
			count++;
			add(pawn[i].getLabel());
			pawn[i].getLabel().addMouseListener(pawn[i]);
		}
		index = 7;
		count = 0;
		team = "black";
		// Dibujar torres
		for (int i = 0; i < 4; i++) {
			if (i == 2) {
				index = 0;
				count = 0;
				team = "white";
			}
			rock[i] = new Rock(board[index][count], index, count);
			knight[i] = new Knight(board[index][count], index, count);
			rock[i].setTeam(team);
			if (team == "white") {
				rock[i].newImage();
			}
			isBoardOcuped[index][count] = team;
			count += 7;
			add(rock[i].getLabel());
			rock[i].getLabel().addMouseListener(rock[i]);

		}
		index = 7;
		count = 1;
		team = "black";
		// Dibujar Caballos
		for (int i = 0; i < 4; i++) {
			if (i == 2) {
				index = 0;
				count = 1;
				team = "white";
			}
			knight[i] = new Knight(board[index][count], index, count);
			knight[i].setTeam(team);
			if (team == "white") {
				knight[i].newImage();
			}
			isBoardOcuped[index][count] = team;
			count += 5;
			add(knight[i].getLabel());
			knight[i].getLabel().addMouseListener(knight[i]);

		}
		index = 7;
		count = 2;
		team = "black";
		// Dibujar alfiles
		for (int i = 0; i < 4; i++) {
			if (i == 2) {
				index = 0;
				count = 2;
				team = "white";
			}
			bishop[i] = new Bishop(board[index][count], index, count);
			bishop[i].setTeam(team);
			if (team == "white") {
				bishop[i].newImage();
			}
			isBoardOcuped[index][count] = team;
			count += 3;
			add(bishop[i].getLabel());
			bishop[i].getLabel().addMouseListener(bishop[i]);

		}
		index = 7;
		count = 3;
		team = "black";
		// Dibujar Reinas
		for (int i = 0; i < 2; i++) {

			if (i == 1) {
				index = 0;
				team = "white";
			}
			queen[i] = new Queen(board[index][count], index, count);
			queen[i].setTeam(team);

			king[i] = new King(board[index][count + 1], index, count + 1);
			king[i].setTeam(team);
			if (team == "white") {
				queen[i].newImage();
				king[i].newImage();
			}
			isBoardOcuped[index][count] = team;
			isBoardOcuped[index][count + 1] = team;
			add(queen[i].getLabel());
			queen[i].getLabel().addMouseListener(queen[i]);

			add(king[i].getLabel());
			king[i].getLabel().addMouseListener(king[i]);

		}
		count = 0;
	}

	public void reDrawBoard() {
		for (int i = 0; i < 16; i++) {

			pawn[i].getLabel().setBounds((int) pawn[i].getPosition().getWidth(),
					(int) pawn[i].getPosition().getHeight(), (int) (resolution.getWidth() * 120) / 1920,
					(int) (resolution.getHeight() * 120) / 1080);
			if (figures[i] != null) {
				figures[i].getLabel().setBounds((int) figures[i].getPosition().getWidth(),
						(int) figures[i].getPosition().getHeight(), (int) (resolution.getWidth() * 120) / 1920,
						(int) (resolution.getHeight() * 120) / 1080);
			}

		}

		for (int i = 0; i < 4; i++) {

			rock[i].getLabel().setBounds((int) rock[i].getPosition().getWidth(),
					(int) rock[i].getPosition().getHeight(), (int) (resolution.getWidth() * 120) / 1920,
					(int) (resolution.getHeight() * 120) / 1080);
			knight[i].getLabel().setBounds((int) knight[i].getPosition().getWidth(),
					(int) knight[i].getPosition().getHeight(), (int) (resolution.getWidth() * 120) / 1920,
					(int) (resolution.getHeight() * 120) / 1080);
			bishop[i].getLabel().setBounds((int) bishop[i].getPosition().getWidth(),
					(int) bishop[i].getPosition().getHeight(), (int) (resolution.getWidth() * 120) / 1920,
					(int) (resolution.getHeight() * 120) / 1080);
		}
		for (int i = 0; i < 2; i++) {

			queen[i].getLabel().setBounds((int) queen[i].getPosition().getWidth(),
					(int) queen[i].getPosition().getHeight(), (int) (resolution.getWidth() * 120) / 1920,
					(int) (resolution.getHeight() * 120) / 1080);
			king[i].getLabel().setBounds((int) king[i].getPosition().getWidth(),
					(int) king[i].getPosition().getHeight(), (int) (resolution.getWidth() * 120) / 1920,
					(int) (resolution.getHeight() * 120) / 1080);
		}
	}

	// Metodo para obtener la dimension especifica del tablero
	public static Dimension getBoard(int row, int col) {

		return board[row][col];
	}

	public String getIsBoardOcuped(int row, int col) {
		return isBoardOcuped[row][col];
	}

	public void nextTurn() {
		if (turn == "black") {
			turn = "white";
		} else {
			turn = "black";
		}

	}

	public String getTurn() {
		return turn;
	}

	public void isFocus(boolean bool) {
		if (bool == false) {
			for (int i = 0; i < 32; i++) {

				place[i].setDimensionPlace(new Dimension(-1000, -1000));
				place[i].getLabelPlace().setBounds((int) place[i].getDimensionPlace().getWidth() + 5,
						(int) place[i].getDimensionPlace().getHeight() + 17, 110, 110);
				countPos = 0;

				repaint();
			}

		} else {
			focus = bool;
		}
	}

	public void setNextMov(int i, int j) {
		// CREA LAS CASILLAS A LAS QUE SE PUEDE MOVER ESA PIEZA

		if (!check) {
			place[countPos].setDimensionPlace(board[i][j]);
			place[countPos].setCoordPlace(i, j);
			place[countPos].getLabelPlace().setBounds(
					(int) place[countPos].getDimensionPlace().getWidth() + (Frame.resWidth() * 3 / 1080),
					(int) place[countPos].getDimensionPlace().getHeight() + (Frame.resHeight() * 35 / 1920),
					Frame.resWidth() * 110 / 1920, Frame.resHeight() * 110 / 1080);
			countPos++;
		} else {
			Dimension dim1;
			if (team == "black") {
				dim1 = king[1].getPosition();
			} else {
				dim1 = king[0].getPosition();
			}
			Dimension dim2 = new Dimension(board[i][j]);

			if ((dim1.getHeight() == dim2.getHeight() && dim1.getWidth() == dim2.getWidth())) {

				place[32].setDimensionPlace(dim1);
				place[32].setCoordPlace(i, j);
				place[32].getLabelPlace().setBounds(
						(int) place[32].getDimensionPlace().getWidth() + (Frame.resWidth() * 3 / 1080),
						(int) place[32].getDimensionPlace().getHeight() + (Frame.resHeight() * 35 / 1920),
						Frame.resWidth() * 110 / 1920, Frame.resHeight() * 110 / 1080);

				checkString = "JAQUE";

			}
		}

	}

	public void isCheck(int row, int col, int index, Pieces[] figure) {

		check = true;
		team = figure[index].getTeam();
		System.out.println(figure);
		figure[index].pieceMovement();

	}

	public void movPiece(Dimension dimension, int row, int col) {
		isFocus(false);
		place[32].setDimensionPlace(new Dimension(-1000, -1000));
		place[32].getLabelPlace().setBounds((int) place[32].getDimensionPlace().getWidth() + 5,
				(int) place[32].getDimensionPlace().getHeight() + 17, 110, 110);
		checkString = "";
		// COMPRUEBA SI LAS PIEZAS SE PUEDEN MATAR O MOVER
		for (int i = 0; i < 16; i++) {
			// COMPRUEBA SI TIENE QUE MATAR A UN PEON CORONADO
			if (figures[i] != null) {
				if (figures[i].getPosition().equals(board[row][col])) {

					figures[i].setGraveyardDimension();
					figures[i].setCoord(-1, -1);
					figures[i].getLabel().removeMouseListener(figures[i]);
					;
				}
				// COMPRUEBA SI TIENE QUE MOVER A UN PEON CORONADO
				if (figures[i].getLabel().equals(Queen.piece)) {

					figures[i].setPosition(dimension);
					isBoardOcuped[figures[i].getRow()][figures[i].getCol()] = null;
					figures[i].setCoord(row, col);
					isBoardOcuped[figures[i].getRow()][figures[i].getCol()] = figures[i].getTeam();
					isCheck(figures[i].getRow(), figures[i].getCol(), i, figures);
				}

			}
			// TORRE--------------------------------------------------------TORRE
			if (i < rock.length) {
				// COMPRUEBA SI TIENE QUE MATAR A UNA TORRE
				if (rock[i].getPosition().equals(board[row][col])) {

					rock[i].setGraveyardDimension();
					rock[i].setCoord(-1, -1);
					rock[i].getLabel().removeMouseListener(rock[i]);
					rock[i].castlingOut();

				}
				// MUEVE LA TORRE QUE SE HABIA SELECCIONADO ANTES
				if (rock[i].getLabel().equals(Rock.piece)) {

					rock[i].setPosition(dimension);
					isBoardOcuped[rock[i].getRow()][rock[i].getCol()] = null;
					rock[i].setCoord(row, col);
					isBoardOcuped[rock[i].getRow()][rock[i].getCol()] = rock[i].getTeam();
					rock[i].castlingOut();
					isCheck(rock[i].getRow(), rock[i].getCol(), i, rock);
				}
			}
			// CABALLO--------------------------------------------------------CABALLO
			if (i < knight.length) {
				// COMPRUEBA SI TIENE QUE MATAR A UN CABALLO
				if (knight[i].getPosition().equals(board[row][col])) {

					knight[i].setGraveyardDimension();
					knight[i].setCoord(-1, -1);
					knight[i].getLabel().removeMouseListener(knight[i]);
					;
				}
				// MUEVE EL CABALLO QUE SE HABIA SELECCIONADO ANTES
				if (knight[i].getLabel().equals(Knight.piece)) {

					knight[i].setPosition(dimension);
					isBoardOcuped[knight[i].getRow()][knight[i].getCol()] = null;
					knight[i].setCoord(row, col);
					isBoardOcuped[knight[i].getRow()][knight[i].getCol()] = knight[i].getTeam();
					isCheck(knight[i].getRow(), knight[i].getCol(), i, knight);
				}
			}
			// ALFIL--------------------------------------------------------ALFIL
			if (i < bishop.length) {
				// COMPRUEBA SI TIENE QUE MATAR A UN ALFIL
				if (bishop[i].getPosition().equals(board[row][col])) {

					bishop[i].setGraveyardDimension();
					bishop[i].setCoord(-1, -1);
					bishop[i].getLabel().removeMouseListener(bishop[i]);
					;
				}
				// MUEVE EL ALFIL QUE SE HABIA SELECCIONADO ANTES
				if (bishop[i].getLabel().equals(Bishop.piece)) {

					bishop[i].setPosition(dimension);
					isBoardOcuped[bishop[i].getRow()][bishop[i].getCol()] = null;
					bishop[i].setCoord(row, col);
					isBoardOcuped[bishop[i].getRow()][bishop[i].getCol()] = bishop[i].getTeam();
					isCheck(bishop[i].getRow(), bishop[i].getCol(), i, bishop);
				}
			}
			// REINA--------------------------------------------------------REINA
			if (i < queen.length) {
				// COMPRUEBA SI TIENE QUE MATAR A UNA REINA
				if (queen[i].getPosition().equals(board[row][col])) {

					queen[i].setGraveyardDimension();
					queen[i].setCoord(-1, -1);
					queen[i].getLabel().removeMouseListener(queen[i]);
					;
				}
				// MUEVE LA REINA QUE SE HABIA SELECCIONADO ANTES
				if (queen[i].getLabel().equals(Queen.piece)) {

					queen[i].setPosition(dimension);
					isBoardOcuped[queen[i].getRow()][queen[i].getCol()] = null;
					queen[i].setCoord(row, col);
					isBoardOcuped[queen[i].getRow()][queen[i].getCol()] = queen[i].getTeam();
					isCheck(queen[i].getRow(), queen[i].getCol(), i, queen);
				}
			}
			if (i < king.length) {
				// COMPRUEBA SI MUERE EL REY
				if (king[i].getPosition().equals(board[row][col])) {

					System.exit(0);
				}
				// MUEVE EL REY QUE SE HABIA SELECCIONADO ANTES
				if (king[i].getLabel().equals(King.piece)) {

					king[i].setPosition(dimension);
					isBoardOcuped[king[i].getRow()][king[i].getCol()] = null;
					king[i].setCoord(row, col);
					isBoardOcuped[king[i].getRow()][king[i].getCol()] = king[i].getTeam();
					king[i].castlingDone();
				}
			}

			// COMPRUEBA SI TIENE QUE MATAR A UN PEON
			if (pawn[i].getPosition().equals(board[row][col])) {

				pawn[i].setGraveyardDimension();
				pawn[i].setCoord(-1, -1);
				pawn[i].getLabel().removeMouseListener(pawn[i]);
				;
			}
			// MUEVE EL PEON QUE SE HABIA SELECCIONADO ANTES
			if (pawn[i].getLabel().equals(Pawn.piece)) {

				pawn[i].setPosition(dimension);
				isBoardOcuped[pawn[i].getRow()][pawn[i].getCol()] = null;
				pawn[i].setCoord(row, col);
				isBoardOcuped[pawn[i].getRow()][pawn[i].getCol()] = pawn[i].getTeam();
				pawn[i].firstMovementDone();
				isCheck(pawn[i].getRow(), pawn[i].getCol(), i, pawn);

				if (pawn[i].getRow() == 7 || pawn[i].getRow() == 0) {

					int option = JOptionPane.showOptionDialog(null, "En que ficha se va a transformar este peon: ",
							"Opciones ", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
							new Object[] { "Reina", "Torre", "Caballo", "Alfil" }, null);
					pawn[i].setPosition(new Dimension(-10000, -10000));
					if (option == 0) {
						createPiece(new Queen(board[pawn[i].getRow()][pawn[i].getCol()], pawn[i].getRow(),
								pawn[i].getCol()), pawn[i].getTeam());
					} else if (option == 1) {
						createPiece(
								new Rock(board[pawn[i].getRow()][pawn[i].getCol()], pawn[i].getRow(), pawn[i].getCol()),
								pawn[i].getTeam());
					} else if (option == 2) {
						createPiece(new Knight(board[pawn[i].getRow()][pawn[i].getCol()], pawn[i].getRow(),
								pawn[i].getCol()), pawn[i].getTeam());
					} else if (option == 3) {
						createPiece(new Bishop(board[pawn[i].getRow()][pawn[i].getCol()], pawn[i].getRow(),
								pawn[i].getCol()), pawn[i].getTeam());
					}
				}

			}
		}
		nextTurn();
		repaint();
	}

	public void createPiece(Pieces figure, String team) {

		figures[count] = figure;

		if (team == "white") {
			figures[count].newImage();
		}

		figures[count].getLabel().addMouseListener(figures[count]);
		figures[count].setTeam(team);
		add(figures[count].getLabel());
		count++;

	}

	public void castling(String team, int row, int col, Dimension dimension) {
		// HACE EL ENROQUE CORTO
		int num, num2;
		if (team == "white") {
			num = 3;
			num2 = 2;
		} else {
			num = 1;
			num2 = 0;
		}

		if (getIsBoardOcuped(row, col + 1) == null && getIsBoardOcuped(row, col + 2) == null
				&& rock[num].getCastling()) {

			int option = JOptionPane.showOptionDialog(null, "¿Desea hacer enroque?", "Opciones ",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
					new Object[] { "Enrocar", "Cancelar" }, null);
			if (option == 0) {
				if (getIsBoardOcuped(row, col - 1) == null && getIsBoardOcuped(row, col - 2) == null
						&& getIsBoardOcuped(row, col - 3) == null && rock[num2].getCastling()) {
					option = JOptionPane.showOptionDialog(null, "¿Enroque corto o largo?", "Opciones ",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
							new Object[] { "Enroque corto", "Enroque largo" }, null);
				}
				if (option == 0) {
					if (team == "white") {
						king[1].setPosition(rock[3].getPosition());
						rock[3].setPosition(dimension);
						king[1].setCoord(0, 7);
						rock[3].setCoord(0, 4);
						king[1].castlingDone();
					} else {
						king[0].setPosition(rock[1].getPosition());
						rock[1].setPosition(dimension);
						king[0].setCoord(7, 7);
						rock[1].setCoord(7, 4);
						king[0].castlingDone();
					}
				}
				if (option == 1) {
					if (team == "white") {
						king[1].setPosition(rock[2].getPosition());
						rock[2].setPosition(dimension);
						king[1].setCoord(0, 0);
						rock[2].setCoord(0, 4);
						king[1].castlingDone();
					} else {
						king[0].setPosition(rock[0].getPosition());
						rock[0].setPosition(dimension);
						king[0].setCoord(7, 0);
						rock[0].setCoord(7, 4);
						king[0].castlingDone();
					}
				}
				nextTurn();
				repaint();
			}

		} // HACE EL ENROQUE LARGO
		else if (getIsBoardOcuped(row, col - 1) == null && getIsBoardOcuped(row, col - 2) == null
				&& getIsBoardOcuped(row, col - 3) == null && rock[num2].getCastling()) {

			int option = JOptionPane.showOptionDialog(null, "¿Desea hacer enroque?", "Opciones ",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
					new Object[] { "Enrocar", "Cancelar" }, null);
			if (option == 0) {
				if (team == "white") {
					king[1].setPosition(rock[2].getPosition());
					rock[2].setPosition(dimension);
					king[1].setCoord(0, 0);
					rock[2].setCoord(0, 4);
					king[1].castlingDone();
				} else {
					king[0].setPosition(rock[0].getPosition());
					rock[0].setPosition(dimension);
					king[0].setCoord(7, 0);
					rock[0].setCoord(7, 4);
					king[0].castlingDone();
				}
				nextTurn();
				repaint();
			}

		}

	}

	public void check(boolean bool) {

		check = bool;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timeSec++;
		repaint();
		if (timeSec == 60) {
			timeMin++;
			timeSec = 0;
		}

	}
}
