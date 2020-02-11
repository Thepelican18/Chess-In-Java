package mainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
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
	private Image whiteSquare,blackSquare,brownSquare;//Fondos del tablero
	private final Dimension board[][] = new Dimension[8][8];//Contiene las dimensiones de cada casilla del tablero
	private String[][] isBoardOcuped = new String[8][8];//Contiene si las dimensiones estan ocupadas por algo
	private int x, y;//Variables para las dimensiones de cada casilla
	private String turn = "black", team, checkString = "",time = "00:00";//Variables para dibujar en pantalla
	private int countPos = 0, col = 0, row, timeSec = 1, timeMin = 0,count = 0;
	// ................................
	private Pieces[] figures = new Pieces[16];//Array de figuras
	private Pawn[] pawn = new Pawn[16];//Array de peones
	private Rock[] rock = new Rock[4];//Array de torres
	private Knight[] knight = new Knight[4];//Array de caballos
	private Bishop[] bishop = new Bishop[4];//Array de alfiles
	private Queen[] queen = new Queen[2];//Array de reinas
	private King[] king = new King[2];//Array de reyes
	// ................................
	private boolean draw = true, needCheck = false,resolution = false;
	private Places place[] = new Places[33];//Array de casillas de posibles movimientos
	private static ChessBoard chessBoard = null;

	// ................................
	private ChessBoard() {}
	
	// METODO QUE DIBUJA LOS GRAFICOS
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		setLayout(null);
		g2D = (Graphics2D) g;
		
		if(!resolution) {
			
			setResImageSize();//si aun no se ha adaptado la resolucion de las imagenes se llama a este metodo
			
		}
		// COLOR DE FONDO
		setBackground(Color.GRAY.darker().darker());

		// COLOR BORDE DEL TABLERO
		
		g2D.drawImage(new ImageIcon(brownSquare).getImage(),  (Frame.resWidth() * 443) / 1920,  (Frame.resHeight() * 15) / 1080, null);
		
		// DIBUJA CASILLAS BLANCAS
		
		g2D.drawImage(new ImageIcon(whiteSquare).getImage(), Frame.resWidth() * 480 / 1920,(Frame.resHeight() * 50) / 1080, null);
		
		// DIBUJA CASILLAS NEGRAS
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				y = (((Frame.resHeight() * 960) / 1080) / 8) * i;
				x = ((Frame.resWidth() / 2) / 8) * j;
				if ((i + j) % 2 == 0) {
					g2D.drawImage(new ImageIcon(blackSquare).getImage(), (Frame.resWidth() / 4) + x, (Frame.resHeight() * 50 / 1080) + y,null);
				}
			}
		}

		// DIBUJAR EL TURNO DEL EQUIPO CORRESPONDIENTE
		g2D.setPaint(Color.RED);
		g2D.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		if (turn == "black") {
			g2D.drawString("Turno negras", 30, 50);

		} else {
			g2D.drawString("Turno blancas", 30, 50);
		}

		// DIBUJAR EL TIEMPO
		g2D.setFont(new Font("TimesRoman", Font.PLAIN, 45));
		g2D.drawString(time, 1700, 50);

		
		// DIBUJADO QUE INSTANCIA LAS PIEZAS Y CREA LAS DIMENSIONES
		if (draw) {
			
			dimensionBoard();//Crea las dimensiones
			drawBoard();//instancia las piezas en el tablero
			Timer timer = new Timer(1000, this);// Temporizador que hace que se actualice cada segundo
			timer.start();// comienzo del temporizar
			draw = false;
		}

		// DIBUJA "JAQUE"
		g2D.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		g2D.drawString(checkString, 200, 50);
		
		reDrawBoard();// Redibujar las piezas en las nuevas posiciones

	}
	
	// METODO QUE ADAPTA LAS IMAGENES A LA RESOLUCION DE PANTALLA
	public void setResImageSize() {
		
		resolution = true;
		brownSquare = new ImageIcon("src/IMG/backgroundBrown.png").getImage();
		brownSquare = brownSquare.getScaledInstance((Frame.resWidth() * 1030) / 1920, (Frame.resHeight() * 1030) / 1080, 0);
		whiteSquare = new ImageIcon("src/IMG/backgroundWhite.png").getImage();
		whiteSquare = whiteSquare.getScaledInstance((Frame.resWidth() * 960) / 1920,(Frame.resHeight() * 960) / 1080, 0);
		blackSquare = new ImageIcon("src/IMG/backgroundBlack.png").getImage();
		blackSquare = blackSquare.getScaledInstance((Frame.resWidth() * 120) / 1920,(Frame.resHeight() * 120) / 1080, 0);
		
	}
	
	// METODO QUE CREA LAS DIMENSIONES DE CADA CUADRADO;
	public void dimensionBoard() {

		for (int i = 0; i < 8; i++) {
			
			for (int j = 0; j < 8; j++) {
				
				y = (((Frame.resHeight() * 960) / 1080) / 8) * i;
				x = ((Frame.resWidth() / 2) / 8) * j;
				board[i][j] = new Dimension( (Frame.resWidth() / 4) +  x, (((Frame.resHeight() * 75) / 1080) / 2) + y);
				
			}
		}
	}

	// METODO QUE INSTANCIA TODAS LOS JLABELS EN UNA POSICION DEFAULT
	public void drawBoard() {
		
		
		// Coloca 32 JLabels fuera del tablero para en un futuro usarlos como posibles
		// movimientos de la pieza
		for (int i = 0; i < 32; i++) {

			place[i] = new Places(new Dimension(-1000, -1000));
			add(place[i].getLabelPlace());
			place[i].getLabelPlace().addMouseListener(place[i]);
			
		}
		// JLabel del jaque
		place[32] = new Places(-1000, -1000);
		add(place[32].getLabelPlace());

		// Dibujar peones
		col = 0;// Columna por defecto
		row = 6;// Fila por defecto
		team = "black";// Equipo por defecto 
		
		for (int i = 0; i < 16; i++) {

			if (i == 8) {// Cambia a dibujar los del otro equipo
				row = 1;
				col = 0;
				team = "white";
			}

			pawn[i] = new Pawn(board[row][col], row, col);// Crea la pieza dandole unas dimensiones y una fila y columna
			pawn[i].setTeam(team);// Le da un equipo a la pieza

			if (team == "white") {
				
				pawn[i].newImage();// Cambia la imagen de pieza al equipo contrario
				
			}

			isBoardOcuped[row][col] = team;// Ocupa la posicion
			col++;
			add(pawn[i].getLabel());// Añade la etiqueta al JPanel
			pawn[i].getLabel().addMouseListener(pawn[i]);// Añade un listener
			
		}
		// Dibujar torres
		row = 7;
		col = 0;
		team = "black";
		
		for (int i = 0; i < 4; i++) {
			
			if (i == 2) {
				
				row = 0;
				col = 0;
				team = "white";
				
			}
			
			rock[i] = new Rock(board[row][col], row, col);
			knight[i] = new Knight(board[row][col], row, col);
			rock[i].setTeam(team);
			
			if (team == "white") {
				
				rock[i].newImage();
				
			}
			
			isBoardOcuped[row][col] = team;
			col += 7;
			add(rock[i].getLabel());
			rock[i].getLabel().addMouseListener(rock[i]);

		}
		// Dibujar Caballos
		row = 7;
		col = 1;
		team = "black";
		
		for (int i = 0; i < 4; i++) {
			if (i == 2) {
				
				row = 0;
				col = 1;
				team = "white";
				
			}
			
			knight[i] = new Knight(board[row][col], row, col);
			knight[i].setTeam(team);
			
			if (team == "white") {
				
				knight[i].newImage();
				
			}
			
			isBoardOcuped[row][col] = team;
			col += 5;
			add(knight[i].getLabel());
			knight[i].getLabel().addMouseListener(knight[i]);

		}
		// Dibujar alfiles
		row = 7;
		col = 2;
		team = "black";
		
		for (int i = 0; i < 4; i++) {
			
			if (i == 2) {
				
				row = 0;
				col = 2;
				team = "white";
				
			}
			
			bishop[i] = new Bishop(board[row][col], row, col);
			bishop[i].setTeam(team);
			
			if (team == "white") {
				
				bishop[i].newImage();
				
			}
			
			isBoardOcuped[row][col] = team;
			col += 3;
			add(bishop[i].getLabel());
			bishop[i].getLabel().addMouseListener(bishop[i]);

		}
		// Dibujar Reinas y Reyes
		row = 7;
		col = 3;
		team = "black";
		
		for (int i = 0; i < 2; i++) {

			if (i == 1) {
				
				row = 0;
				team = "white";
				
			}
			
			queen[i] = new Queen(board[row][col], row, col);
			queen[i].setTeam(team);
			king[i] = new King(board[row][col + 1], row, col + 1);
			king[i].setTeam(team);
			
			if (team == "white") {
				
				queen[i].newImage();
				king[i].newImage();
				
			}
			
			isBoardOcuped[row][col] = team;
			isBoardOcuped[row][col + 1] = team;
			add(queen[i].getLabel());
			queen[i].getLabel().addMouseListener(queen[i]);
			add(king[i].getLabel());
			king[i].getLabel().addMouseListener(king[i]);

		}
	}

	// METODO QUE DIBUJA LOS JLABELS EN LA DIMENSION ESPECIFICADA
	public void reDrawBoard() {
		
		// La pieza se dibujara en la dimension que tenga en sus variables
		// dimensiones de peon y figura(figura es un peon coronado)
		for (int i = 0; i < 16; i++) {

			pawn[i].getLabel().setBounds((int) pawn[i].getPosition().getWidth(),(int) pawn[i].getPosition().getHeight(), (Frame.resWidth() * 120) / 1920, (Frame.resHeight() * 120) / 1080);
			
			if (figures[i] != null) {
				
				figures[i].getLabel().setBounds((int) figures[i].getPosition().getWidth(),(int) figures[i].getPosition().getHeight(), (Frame.resWidth() * 120) / 1920, (Frame.resHeight() * 120) / 1080);
				
			}
			// dimensiones de torre,alfil y caballo
			if(i < 4) {
				
				rock[i].getLabel().setBounds((int) rock[i].getPosition().getWidth(),(int) rock[i].getPosition().getHeight(), (Frame.resWidth() * 120) / 1920, (Frame.resHeight() * 120) / 1080);
				knight[i].getLabel().setBounds((int) knight[i].getPosition().getWidth(),(int) knight[i].getPosition().getHeight(), (Frame.resWidth() * 120) / 1920, (Frame.resHeight() * 120) / 1080);
				bishop[i].getLabel().setBounds((int) bishop[i].getPosition().getWidth(),(int) bishop[i].getPosition().getHeight(), (Frame.resWidth() * 120) / 1920, (Frame.resHeight() * 120) / 1080);
				
			}
			// dimensiones de reina y rey
			if(i<2) {
				
				queen[i].getLabel().setBounds((int) queen[i].getPosition().getWidth(),(int) queen[i].getPosition().getHeight(), (Frame.resWidth() * 120) / 1920, (Frame.resHeight() * 120) / 1080);
				king[i].getLabel().setBounds((int) king[i].getPosition().getWidth(),(int) king[i].getPosition().getHeight(), (Frame.resWidth() * 120) / 1920, (Frame.resHeight() * 120) / 1080);
			
			}
		}
	}

	// METODO QUE CAMBIA EL TURNO
	public void nextTurn() {
		
		if (turn == "black") {
			
			turn = "white";
			
		} else {
			
			turn = "black";
			
		}
		
	}

	// METODO QUE ES LLAMADO CUANDO SE TERMINA EL FOCUS DEL CLICK EN UNA PIEZA
	public void notIsFocus() {
		
			for (int i = 0; i < 32; i++) {

				place[i].setDimensionPlace(new Dimension(-1000, -1000));//mueve las casillas de posibles movimientos fuera del escenario
				place[i].getLabelPlace().setBounds((int) place[i].getDimensionPlace().getWidth() + 5,(int) place[i].getDimensionPlace().getHeight() + 17, 110, 110);
				countPos = 0;//se reinicia el indice de la casilla de posibles movimientos
				
			}
			repaint();	
	}

	// METODO QUE CREA LAS CASILLAS A LAS QUE SE PUEDE MOVER ESA PIEZA
	public void setNextMov(int row, int col) {
		
		if (!needCheck) {
			
			place[countPos].setDimensionPlace(board[row][col]);//Nueva dimension de la casilla
			place[countPos].setCoordPlace(row, col);//Nueva fila y columna de la casilla
			//Coloca las casillas de posibles lugares en la fila y columna especificada
			place[countPos].getLabelPlace().setBounds((int) place[countPos].getDimensionPlace().getWidth() + (Frame.resWidth() * 3 / 1080),(int) place[countPos].getDimensionPlace().getHeight() + (Frame.resHeight() * 35 / 1920),Frame.resWidth() * 110 / 1920, Frame.resHeight() * 110 / 1080);
			countPos++;
			
		} else {
			
			Dimension dim1;
			
			if (team == "black") {
				
				dim1 = king[1].getPosition();
				
			} else {
				
				dim1 = king[0].getPosition();
				
			}
			
			Dimension dim2 = new Dimension(board[row][col]);
			
			//Comprueba si esa pieza puede matar al rey en el siguiente turno
			if ((dim1.getHeight() == dim2.getHeight() && dim1.getWidth() == dim2.getWidth())) {

				place[32].setDimensionPlace(dim1);
				place[32].setCoordPlace(row, col);
				place[32].getLabelPlace().setBounds((int) place[32].getDimensionPlace().getWidth() + (Frame.resWidth() * 3 / 1080),(int) place[32].getDimensionPlace().getHeight() + (Frame.resHeight() * 35 / 1920),Frame.resWidth() * 110 / 1920, Frame.resHeight() * 110 / 1080);
				checkString = "JAQUE";
				
			}
		}

	}

	// METODO PARA VOLVER A CALCULAR LOS MOVIMIENTOS DE LA PIEZA EN EL SIGUIENTE TURNO
	public void checkNextMove(int row, int col, int index, Pieces[] figure) {

		needCheck = true;
		team = figure[index].getTeam();
		figure[index].pieceMovement();

	}

	// METODO QUE MUEVE LA PIEZA CORRESPONDIENTE A LA CASILLA QUE SE HAYA HECHO CLICK Y COMPRUEBA SI TIENE QUE MATAR A ESA PIEZA
	public void movPiece(Dimension dimension, int row, int col) {
		
		notIsFocus();// Se termina el focus de esa pieza
		// Mueve la casilla jaque
		place[32].setDimensionPlace(new Dimension(-1000, -1000));
		place[32].getLabelPlace().setBounds((int) place[32].getDimensionPlace().getWidth() + 5,(int) place[32].getDimensionPlace().getHeight() + 17, 110, 110);
		checkString = "";
		
		// COMPRUEBA SI LAS PIEZAS SE PUEDEN MATAR O MOVER
		for (int i = 0; i < 16; i++) {
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
					checkNextMove(rock[i].getRow(), rock[i].getCol(), i, rock);
					
				}
			}
			// CABALLO--------------------------------------------------------CABALLO
			if (i < knight.length) {
				// COMPRUEBA SI TIENE QUE MATAR A UN CABALLO
				if (knight[i].getPosition().equals(board[row][col])) {

					knight[i].setGraveyardDimension();
					knight[i].setCoord(-1, -1);
					knight[i].getLabel().removeMouseListener(knight[i]);
					
				}
				// MUEVE EL CABALLO QUE SE HABIA SELECCIONADO ANTES
				if (knight[i].getLabel().equals(Knight.piece)) {

					knight[i].setPosition(dimension);
					isBoardOcuped[knight[i].getRow()][knight[i].getCol()] = null;
					knight[i].setCoord(row, col);
					isBoardOcuped[knight[i].getRow()][knight[i].getCol()] = knight[i].getTeam();
					checkNextMove(knight[i].getRow(), knight[i].getCol(), i, knight);
					
				}
			}
			// ALFIL--------------------------------------------------------ALFIL
			if (i < bishop.length) {
				// COMPRUEBA SI TIENE QUE MATAR A UN ALFIL
				if (bishop[i].getPosition().equals(board[row][col])) {

					bishop[i].setGraveyardDimension();
					bishop[i].setCoord(-1, -1);
					bishop[i].getLabel().removeMouseListener(bishop[i]);
					
				}
				// MUEVE EL ALFIL QUE SE HABIA SELECCIONADO ANTES
				if (bishop[i].getLabel().equals(Bishop.piece)) {

					bishop[i].setPosition(dimension);
					isBoardOcuped[bishop[i].getRow()][bishop[i].getCol()] = null;
					bishop[i].setCoord(row, col);
					isBoardOcuped[bishop[i].getRow()][bishop[i].getCol()] = bishop[i].getTeam();
					checkNextMove(bishop[i].getRow(), bishop[i].getCol(), i, bishop);
					
				}
			}
			// REINA--------------------------------------------------------REINA
			if (i < queen.length) {
				// COMPRUEBA SI TIENE QUE MATAR A UNA REINA
				if (queen[i].getPosition().equals(board[row][col])) {

					queen[i].setGraveyardDimension();
					queen[i].setCoord(-1, -1);
					queen[i].getLabel().removeMouseListener(queen[i]);
					
				}
				// MUEVE LA REINA QUE SE HABIA SELECCIONADO ANTES
				if (queen[i].getLabel().equals(Queen.piece)) {

					queen[i].setPosition(dimension);
					isBoardOcuped[queen[i].getRow()][queen[i].getCol()] = null;
					queen[i].setCoord(row, col);
					isBoardOcuped[queen[i].getRow()][queen[i].getCol()] = queen[i].getTeam();
					checkNextMove(queen[i].getRow(), queen[i].getCol(), i, queen);
					
				}
			}
			// REY--------------------------------------------------------REY
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
			// PEONES--------------------------------------------------------PEONES
			// COMPRUEBA SI TIENE QUE MATAR A UN PEON CORONADO
			if (figures[i] != null) {
							
				if (figures[i].getPosition().equals(board[row][col])) {

					figures[i].setGraveyardDimension();
					figures[i].setCoord(-1, -1);
					figures[i].getLabel().removeMouseListener(figures[i]);
								
				}
				// COMPRUEBA SI TIENE QUE MOVER A UN PEON CORONADO
				if (figures[i].getLabel().equals(Queen.piece)) {

					figures[i].setPosition(dimension);
					isBoardOcuped[figures[i].getRow()][figures[i].getCol()] = null;
					figures[i].setCoord(row, col);
					isBoardOcuped[figures[i].getRow()][figures[i].getCol()] = figures[i].getTeam();
					checkNextMove(figures[i].getRow(), figures[i].getCol(), i, figures);
								
				}
			}
			// COMPRUEBA SI TIENE QUE MATAR A UN PEON
			if (pawn[i].getPosition().equals(board[row][col])) {

				pawn[i].setGraveyardDimension();
				pawn[i].setCoord(-1, -1);
				pawn[i].getLabel().removeMouseListener(pawn[i]);
				
			}
			// MUEVE EL PEON QUE SE HABIA SELECCIONADO ANTES
			if (pawn[i].getLabel().equals(Pawn.piece)) {

				pawn[i].setPosition(dimension);
				isBoardOcuped[pawn[i].getRow()][pawn[i].getCol()] = null;
				pawn[i].setCoord(row, col);
				isBoardOcuped[pawn[i].getRow()][pawn[i].getCol()] = pawn[i].getTeam();
				pawn[i].firstMovementDone();
				checkNextMove(pawn[i].getRow(), pawn[i].getCol(), i, pawn);

				// SI PUEDE CORONARSE SE TRANSFORMARA EN LA PIEZA ELEGIDA
				if (pawn[i].getRow() == 7 || pawn[i].getRow() == 0) {

					int option = JOptionPane.showOptionDialog(null, "En que ficha se va a transformar este peon: ","Opciones ", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,new Object[] { "Reina", "Torre", "Caballo", "Alfil" }, null);
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

	// METODO QUE CREA UNA PIEZA
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

	// METODO QUE COMPRUEBA SI SE PUEDE HACER ENROQUE
	public void castling(String team, int row, int col, Dimension dimension) {
		// HACE EL ENROQUE CORTO
		int num, num2;
		// Establece las posiciones en las que se puede hacer el enroque segun el equipo
		if (team == "white") {
			
			num = 3;
			num2 = 2;
			
		} else {
			
			num = 1;
			num2 = 0;
			
		}

		if (getIsBoardOcuped(row, col + 1) == null && getIsBoardOcuped(row, col + 2) == null && rock[num].getCastling()) {

			int option = JOptionPane.showOptionDialog(null, "¿Desea hacer enroque?", "Opciones ",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Enrocar", "Cancelar" }, null);
			
			if (option == 0) {
				
				if (getIsBoardOcuped(row, col - 1) == null && getIsBoardOcuped(row, col - 2) == null && getIsBoardOcuped(row, col - 3) == null && rock[num2].getCastling()) {
					
					option = JOptionPane.showOptionDialog(null, "¿Enroque corto o largo?", "Opciones ",	JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,new Object[] { "Enroque corto", "Enroque largo" }, null);
				
				}
				//Intercambio de posiciones
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
		else if (getIsBoardOcuped(row, col - 1) == null && getIsBoardOcuped(row, col - 2) == null && getIsBoardOcuped(row, col - 3) == null && rock[num2].getCastling()) {

			int option = JOptionPane.showOptionDialog(null, "¿Desea hacer enroque?", "Opciones ", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Enrocar", "Cancelar" }, null);
			
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

	// METODO QUE ES LLAMADO DESDE UNA PIEZA PARA VOLVER A COMPROBAR SUS MOVIMIENTOS
	public void check(boolean bool) {

		needCheck = bool;
	}
	
	// METODO QUE INDICA EL TIEMPO TOTAL DE JUEGO
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//No se contara mas de 60 minutos
		if(timeMin == 60) {
			
			return;
			
		}
		time = "";
	
		try {
			
			Date formatter = new SimpleDateFormat("mm:ss").parse(Integer.toString(timeMin) + ":" + Integer.toString(timeSec));//Le da formato a la fecha
			
			for(int i = 14;i<19;i++) {
				
			time +=formatter.toString().charAt(i);//Corto el resto del formato por defecto para que aparezca 00:00
			
			}
			
		} catch (ParseException f) {

			System.out.println("No se ha podido dar el formato de fecha");
		} 
		//contador de segundos y minutos
		timeSec++;
		repaint();
		if (timeSec == 60) {
			
			timeMin++;
			timeSec = 0;
			
		}

	}
	
	
	// GETTERS-----------------GETTERS
	
	// METODO QUE DEVUELVE LA DIMENSION DE LA CASILLA ESPECIFICADA EN FILA Y COLUMNA
	public Dimension getBoard(int row, int col) {

		return getInstance().board[row][col];
			
	}
	
	// METODO QUE DEVUELVE SI ESTA OCUPADA LA DIMENSION Y POR QUE EQUIPO
	public String getIsBoardOcuped(int row, int col) {
			
		return isBoardOcuped[row][col];
			
	}
		
	// METODO QUE DEVUELVE DE QUE EQUIPO ES EL TURNO
	public String getTurn() {
			
		return turn;
			
	}

	// METODO PARA ACCEDER A CHESSBOARD SIN INSTANCIAR
	public static ChessBoard getInstance() {

		if (chessBoard == null) {
			
			chessBoard = new ChessBoard();
			
		}
		return chessBoard;
	}
}
