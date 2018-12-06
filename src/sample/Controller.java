package sample;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Random;

public class Controller {
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private Label youText;
	
	@FXML
	private ImageView crown;
	
	@FXML
	private Label youLabel;
	
	@FXML
	private Label pcLabel;
	
	@FXML
	private Pane symbolPane;
	
	@FXML
	private ImageView line;
	
	@FXML
	private ImageView diagLine;
	
	@FXML
	private Label pcText;
	
	@FXML
	private Label textInfo;
	
	@FXML
	private ImageView grid;
	
	@FXML
	private ImageView box_1;
	
	@FXML
	private ImageView box_2;
	
	@FXML
	private ImageView box_3;
	
	@FXML
	private ImageView box_4;
	
	@FXML
	private ImageView box_5;
	
	@FXML
	private ImageView box_6;
	
	@FXML
	private ImageView box_7;
	
	@FXML
	private ImageView box_8;
	
	@FXML
	private ImageView box_9;
	
	@FXML
	private Label ease;
	
	@FXML
	private Label medium;
	
	@FXML
	private Label hard;
	
	@FXML
	private ImageView easeBox;
	
	@FXML
	private ImageView mediumBox;
	
	@FXML
	private ImageView hardBox;
	
	@FXML
	private ImageView difficultyCheckPoint;
	
	@FXML
	private Pane oCheckBoxPane;
	
	@FXML
	private ImageView oCheckBox;
	
	@FXML
	private ImageView oCheckBox_o;
	
	@FXML
	private Pane xCheckBoxPane;
	
	@FXML
	private ImageView xCheckBox;
	
	@FXML
	private ImageView xCheckBox_g;
	
	@FXML
	private ImageView arrow;
	
	@FXML
	private ImageView startBtn;
	
	private String currentSymbol;
	private final String X_SYMBOL = "X";
	private final String O_SYMBOL = "O";
	private final String EMPTY = "-";
	
	private String currentDifficalty;
	private final String EASE = "ease";
	private final String MEDIUM = "medium";
	private final String HARD = "hard";
	
	private String state;
	private final String GAMEPLAY = "gameplay";
	private final String IN_MENU = "menu";
	
	private String whoesTurn;
	private final String YOU = "you";
	private final String PC = "pc";
	
	private boolean youIsTurned;
	private boolean pcIsTurned;
	
	private int youPoints = 0;
	private int pcPoints = 0;
	
	private ImageView[] btnOfPlace = new ImageView[9];
	private boolean[] isBusyPlase = new boolean[9];
	private String[][] gameField = {
		{ "-", "-", "-"},
		{ "-", "-", "-"},
		{ "-", "-", "-"}};
	private int emptyPlaces;
	
	private ColorAdjust colorAdjustReset = new ColorAdjust(
			0, 0, 0, 0);
	private ColorAdjust colorAdjustLight = new ColorAdjust(
			0, 0, 0.25, 0);
	
	private final float winMap[][] = { // x, y, rotate
			{116.5f, -17, 90},
			{116.5f,  48, 90},
			{116.5f, 111, 90},
			
			{50,  46, 0},
			{115, 46, 0},
			{180, 46, 0},
			
			{   23,     48,  0},
			{22.5f,  50.5f, 90}
	};
	
	
	
	private void init() {
		btnOfPlace[0] = box_1;
		btnOfPlace[1] = box_2;
		btnOfPlace[2] = box_3;
		btnOfPlace[3] = box_4;
		btnOfPlace[4] = box_5;
		btnOfPlace[5] = box_6;
		btnOfPlace[6] = box_7;
		btnOfPlace[7] = box_8;
		btnOfPlace[8] = box_9;
		
		pcText.setText(Integer.toString(pcPoints));
		youText.setText(Integer.toString(youPoints));
		
		crown.setVisible(false);
	}
	
	private void reset() {
		for (int i = 0; i < gameField.length; i++) {
			for (int j = 0; j < gameField[0].length; j++) {
				gameField[i][j] = EMPTY;
			}
		}
		
		for (int i = 0; i < isBusyPlase.length; i++) {
			isBusyPlase[i] = false;
		}
		
		emptyPlaces = 9;
		
		diagLine.setVisible(false);
		line.setVisible(false);
		
		pcIsTurned = false;
		youIsTurned = false;
		
		
	}
	
	@FXML
	public void initialize() {
		init();
		reset();
		
		setSymbol(O_SYMBOL);
		setDifficalty(HARD);
		setState(IN_MENU);
	}
	
	/*-----------------------------------------------------------
	*                       game play
	* -----------------------------------------------------------*/
	
	private void Turn_YOU(MouseEvent e) {
		for (int i = 0; i < btnOfPlace.length; i++) {
			int h = i % 3;
			int v = (i - (i % 3)) / 3;
			
			if (whoesTurn.equals(YOU)
					&& !youIsTurned
					&& e.getTarget() == btnOfPlace[i]
					&& gameField[v][h].equals(EMPTY)) {
				
				gameField[v][h] = currentSymbol;
				isBusyPlase[i] = true;
				emptyPlaces--;
				
				addSymbol((int) btnOfPlace[i].getLayoutX(),
						(int) btnOfPlace[i].getLayoutY());
				
				btnOfPlace[i].setCursor(Cursor.DEFAULT);
				btnOfPlace[i].setEffect(null);
				
				youIsTurned = true;
				checkWin();
			}
		}
	}
	
	private void Turn_PC() {
		if (whoesTurn.equals(PC) && !pcIsTurned) {
			
			switch (currentDifficalty) {
				case EASE:
					pcRandomTurn();
					break;
				
				case MEDIUM:
					double chance = Math.random() * 100;
					if (chance > 50) pcRandomTurn();
					else pcAITurn();
					break;
					
				case HARD:
					pcAITurn();
					break;
			}
			
			pcIsTurned = true;
			checkWin();
		}
	}
	
	private void pcRandomTurn() {
		int random, h, v;
		do {
			random = (int) (Math.random() * 9);
			h = random % 3;
			v = (random - (random % 3)) / 3;
			
		} while (!gameField[v][h].equals(EMPTY));
		
		gameField[v][h] = currentSymbol.equals(X_SYMBOL) ? O_SYMBOL:X_SYMBOL;
		isBusyPlase[random] = true;
		emptyPlaces --;
		
		addSymbol((int) btnOfPlace[random].getLayoutX(),
				(int) btnOfPlace[random].getLayoutY());
		
		btnOfPlace[random].setCursor(Cursor.DEFAULT);
		btnOfPlace[random].setEffect(null);
	}
	
	private void pcAITurn() {
		boolean isAttack = false;
		boolean isDefense = false;
		int turnIndexH = 0;
		int turnIndexV = 0;
		
		//isDefense
		if (gameField[0][0].equals(currentSymbol))
		
		
//		System.out.println("hard turn");
		pcRandomTurn();
	}
	
	private void checkWin() {
		boolean isWin = false;
		String winSymbol = "";
		int winMapIndex = 0;
		
		// how winLine?
		if (!gameField[0][0].equals(EMPTY)
				&& gameField[0][0].equals(gameField[0][1])
				&& gameField[0][0].equals(gameField[0][2])) {
			winSymbol = gameField[0][0];
			winMapIndex = 1;
			isWin = true;
		} else if (!gameField[1][0].equals(EMPTY)
				&& gameField[1][0].equals(gameField[1][1])
				&& gameField[1][0].equals(gameField[1][2])) {
			winSymbol = gameField[1][0];
			winMapIndex = 2;
			isWin = true;
		} else if (!gameField[2][0].equals(EMPTY)
				&& gameField[2][0].equals(gameField[2][1])
				&& gameField[2][0].equals(gameField[2][2])) {
			winSymbol = gameField[2][0];
			winMapIndex = 3;
			isWin = true;
		}
		else if (!gameField[0][0].equals(EMPTY)
				&& gameField[0][0].equals(gameField[1][0])
				&& gameField[0][0].equals(gameField[2][0])) {
			winSymbol = gameField[0][0];
			winMapIndex = 4;
			isWin = true;
		} else if (!gameField[0][1].equals(EMPTY)
				&& gameField[0][1].equals(gameField[1][1])
				&& gameField[0][1].equals(gameField[2][1])) {
			winSymbol = gameField[0][1];
			winMapIndex = 5;
			isWin = true;
		} else if (!gameField[0][2].equals(EMPTY)
				&& gameField[0][2].equals(gameField[1][2])
				&& gameField[0][2].equals(gameField[2][2])) {
			winSymbol = gameField[0][2];
			winMapIndex = 6;
			isWin = true;
		}
		else if (!gameField[0][0].equals(EMPTY)
				&& gameField[0][0].equals(gameField[1][1])
				&& gameField[0][0].equals(gameField[2][2])) {
			winSymbol = gameField[0][0];
			winMapIndex = 7;
			isWin = true;
		} else if (!gameField[0][2].equals(EMPTY)
				&& gameField[0][2].equals(gameField[1][1])
				&& gameField[0][2].equals(gameField[2][0])) {
			winSymbol = gameField[0][2];
			winMapIndex = 8;
			isWin = true;
		}
		
		if (isWin) {
			showLine(winMapIndex);
			setPointsToWinner();
			
			if (winSymbol.equals(currentSymbol)) {
				textInfo.setText("YOU win!");
			} else {
				textInfo.setText("PC win!");
			}
			
			PauseTransition pause = new PauseTransition(Duration.millis(1500));
			pause.setOnFinished( e -> setState(IN_MENU) );
			pause.play();
		} else {
			switchWhoesTurn();
		}
	}
	
	private void showLine(int winMapIndex) {
		winMapIndex --;
		if (winMapIndex < 6) {
			line.setVisible(true);
			line.setLayoutX(winMap[winMapIndex][0]);
			line.setLayoutY(winMap[winMapIndex][1]);
			line.setRotate(winMap[winMapIndex][2]);
		} else {
			diagLine.setVisible(true);
			diagLine.setLayoutX(winMap[winMapIndex][0]);
			diagLine.setLayoutY(winMap[winMapIndex][1]);
			diagLine.setRotate(winMap[winMapIndex][2]);
		}
	}
	
	private void addSymbol(int x, int y) {
		if (whoesTurn.equals(YOU)) {
			if (currentSymbol.equals(O_SYMBOL)) addImg_O(x, y);
			else addImg_X(x, y);
		} else if (whoesTurn.equals(PC)) {
			if (currentSymbol.equals(O_SYMBOL)) addImg_X(x, y);
			else addImg_O(x, y);
		}
	}
	
	private void addImg_X(int x, int y) {
		Image img = new Image("sample/res/x.png");
		ImageView imgWrap = new ImageView(img);
		imgWrap.setX(x + 4);
		imgWrap.setY(y + 4);
		symbolPane.getChildren().add(imgWrap);
	}
	
	private void addImg_O(int x, int y) {
		Image img = new Image("sample/res/o.png");
		ImageView imgWrap = new ImageView(img);
		imgWrap.setX(x + 2);
		imgWrap.setY(y + 2);
		symbolPane.getChildren().add(imgWrap);
	}
	
	private void clearGameField() {
		symbolPane.getChildren().clear();
	}
	
	
	/*-----------------------------------------------------------
	*                       GAME states navigation
	* -----------------------------------------------------------*/
	
	private void start() {
		textInfo.setText("gameplay");
		
		grid.setOpacity(1);
		startBtn.setOpacity(0.75);
		
		for (int i = 0; i < btnOfPlace.length; i++) {
			btnOfPlace[i].setOpacity(1);
			btnOfPlace[i].setCursor(Cursor.HAND);
		}
		
		ease.setCursor(Cursor.DEFAULT);
		easeBox.setCursor(Cursor.DEFAULT);
		medium.setCursor(Cursor.DEFAULT);
		mediumBox.setCursor(Cursor.DEFAULT);
		hard.setCursor(Cursor.DEFAULT);
		hardBox.setCursor(Cursor.DEFAULT);
		
		xCheckBox.setCursor(Cursor.DEFAULT);
		oCheckBox.setCursor(Cursor.DEFAULT);
		
		switch (currentSymbol) {
			case X_SYMBOL:
				xCheckBox.setOpacity(1);
				xCheckBox_g.setOpacity(1);
				oCheckBox.setOpacity(0.45);
				oCheckBox_o.setOpacity(0.45);
				break;
			
			case O_SYMBOL:
				oCheckBox.setOpacity(1);
				oCheckBox_o.setOpacity(1);
				xCheckBox.setOpacity(0.25);
				xCheckBox_g.setOpacity(0.25);
				break;
		}
		
		switch (currentDifficalty) {
			case EASE:
				ease.setOpacity(1);
				easeBox.setOpacity(1);
				
				medium.setOpacity(0.25);
				mediumBox.setOpacity(0.25);
				hard.setOpacity(0.25);
				hardBox.setOpacity(0.25);
				break;
			case MEDIUM:
				medium.setOpacity(1);
				mediumBox.setOpacity(1);
				
				ease.setOpacity(0.25);
				easeBox.setOpacity(0.25);
				hard.setOpacity(0.25);
				hardBox.setOpacity(0.25);
				break;
			case HARD:
				hard.setOpacity(1);
				hardBox.setOpacity(1);
				
				ease.setOpacity(0.25);
				easeBox.setOpacity(0.25);
				medium.setOpacity(0.25);
				mediumBox.setOpacity(0.25);
				break;
		}
		
		switchFirsturn(); // <--
	}
	
	private void exit() {
		textInfo.setText("menu");
		
		reset();
		
		grid.setOpacity(0.25);
		startBtn.setOpacity(1);
		
		for (int i = 0; i < btnOfPlace.length; i++) {
			btnOfPlace[i].setOpacity(0.25);
			btnOfPlace[i].setCursor(Cursor.DEFAULT);
			btnOfPlace[i].setEffect(colorAdjustReset);
		}
		
		ease.setCursor(Cursor.HAND);
		easeBox.setCursor(Cursor.HAND);
		medium.setCursor(Cursor.HAND);
		mediumBox.setCursor(Cursor.HAND);
		hard.setCursor(Cursor.HAND);
		hardBox.setCursor(Cursor.HAND);
		
		xCheckBox.setCursor(Cursor.HAND);
		oCheckBox.setCursor(Cursor.HAND);
		
		oCheckBox.setOpacity(1);
		oCheckBox_o.setOpacity(1);
		xCheckBox.setOpacity(1);
		xCheckBox_g.setOpacity(1);
		
		ease.setOpacity(1);
		easeBox.setOpacity(1);
		medium.setOpacity(1);
		mediumBox.setOpacity(1);
		hard.setOpacity(1);
		hardBox.setOpacity(1);
		
		line.setVisible(false);
		diagLine.setVisible(false);
		
		// clear gameScreen
		clearGameField();
	}
	
	/*-----------------------------------------------------------
	*                       Mouse listeners
	* -----------------------------------------------------------*/
	
	@FXML
	private void clickListener(MouseEvent e) {
		if (state.equals(IN_MENU)) {
			// symbol
			if (e.getTarget() == oCheckBox) {
				setSymbol(O_SYMBOL);
				textInfo.setText("your symbol - " + O_SYMBOL);
			} else if (e.getTarget() == xCheckBox) {
				setSymbol(X_SYMBOL);
				textInfo.setText("your symbol - " + X_SYMBOL);
			}
			// difficulty
			if (e.getSource() == ease || e.getTarget() == easeBox) {
				animation(easeBox, 1);
				setDifficalty(EASE);
				textInfo.setText("difficalty - " + EASE);
			} else if (e.getSource() == medium || e.getTarget() == mediumBox) {
				animation(mediumBox, 1);
				setDifficalty(MEDIUM);
				textInfo.setText("difficalty - " + MEDIUM);
			} else if (e.getSource() == hard || e.getTarget() == hardBox) {
				animation(hardBox, 1);
				setDifficalty(HARD);
				textInfo.setText("difficalty - " + HARD);
			}
			// start
			if (e.getTarget() == startBtn) {
				animation(startBtn, 1);
				
				setState(GAMEPLAY); // <--
			}
		}
		else if (state.equals(GAMEPLAY)) {
			
			if (e.getTarget() == startBtn) {
				animation(startBtn, 1);
				
				// reset
				setState(IN_MENU); // <--
			}
			
			Turn_YOU(e);
			
		}
}
	
	/*-----------------------------------------------------------
	*                       SETTERS
	* -----------------------------------------------------------*/
	
	private void setState(String state) {
		this.state = state;
		
		switch (state) {
			case GAMEPLAY:
				start();
				break;
				
			case IN_MENU:
				exit();
				break;
		}
	}
	
	private void switchFirsturn() {
		if (currentSymbol.equals(X_SYMBOL))
			setWhoesTurn(YOU);
		else if (currentSymbol.equals(O_SYMBOL))
			setWhoesTurn(PC);
	}
	
	private void switchWhoesTurn() {
		if (emptyPlaces < 1) {
			textInfo.setText("Draw!");
			
			PauseTransition pause = new PauseTransition(Duration.millis(1500));
			pause.setOnFinished( e -> setState(IN_MENU) );
			pause.play();
			
			System.out.println("Draw -- next!");
		} else {
			if (pcIsTurned) {
				pcIsTurned = false;
				setWhoesTurn(YOU);
			} else if (youIsTurned) {
				youIsTurned = false;
				setWhoesTurn(PC);
			}
		}
	}
	
	private void setWhoesTurn(String who) {
		whoesTurn = who;
		
		switch (who) {
			case YOU:
				textInfo.setText("you turn");
				
				for (int i = 0; i < isBusyPlase.length; i++) {
					if (!isBusyPlase[i]) {
						btnOfPlace[i].setCursor(Cursor.HAND);
						colorAdjustLight.setBrightness(0.25);
					}
				}
				break;
			case PC:
				textInfo.setText("wait ...");
				
				for (int i = 0; i < isBusyPlase.length; i++) {
					if (!isBusyPlase[i]) {
						colorAdjustLight.setBrightness(0);
						btnOfPlace[i].setCursor(Cursor.DEFAULT);
					}
				}
				
//				int delay = (int) (Math.random() * 1200) + 300;
				PauseTransition pause = new PauseTransition(Duration.millis(200));
				pause.setOnFinished( e -> {
					Turn_PC();
				});
				pause.play();
				
				break;
		}
	}
	
	private void setSymbol(String symbol) {
		currentSymbol = symbol;
		
		switch (symbol) {
			case X_SYMBOL:
				whoesTurn = YOU;
				arrow.setX(xCheckBoxPane.getLayoutX() - 40);
				break;
			case O_SYMBOL:
				whoesTurn = PC;
				arrow.setX(oCheckBoxPane.getLayoutX() - 40);
				break;
		}
	}
	
	private void setDifficalty(String difficalty) {
		currentDifficalty = difficalty;
		
		switch (difficalty) {
			case EASE:
				difficultyCheckPoint.setY(0);
				break;
			case MEDIUM:
				difficultyCheckPoint.setY(25);
				break;
			case HARD:
				difficultyCheckPoint.setY(50);
				break;
		}
	}
	
	private void setPointsToWinner() {
		
		// animation FX
		
		switch (whoesTurn) {
			case YOU:
				youPoints ++;
				youText.setText(String.valueOf(youPoints));
				break;
				
			case PC:
				pcPoints ++;
				pcText.setText(String.valueOf(pcPoints));
				break;
		}
		
		if (youPoints == pcPoints) {
			youText.setLayoutY(3);
			youLabel.setLayoutY(3);
			
			youText.setOpacity(1);
			youText.setFont(Font.font(youText.getFont().getFamily(), 20));
			youLabel.setOpacity(1);
			youLabel.setFont(Font.font(youText.getFont().getFamily(), 20));
			
			pcText.setOpacity(1);
			pcText.setFont(Font.font(youText.getFont().getFamily(), 20));
			pcLabel.setOpacity(1);
			pcLabel.setFont(Font.font(youText.getFont().getFamily(), 20));
			
			crown.setVisible(false);
		} else if (youPoints > pcPoints) {
			youText.setLayoutY(-6);
			youLabel.setLayoutY(-6);
			
			youText.setOpacity(1);
			youText.setFont(Font.font(youText.getFont().getFamily(), 30));
			youLabel.setOpacity(1);
			youLabel.setFont(Font.font(youText.getFont().getFamily(), 30));
			
			pcText.setOpacity(0.5);
			pcText.setFont(Font.font(youText.getFont().getFamily(), 20));
			pcLabel.setOpacity(0.5);
			pcLabel.setFont(Font.font(youText.getFont().getFamily(), 20));
			
			crown.setVisible(true);
			crown.setLayoutX(51);
			crown.setLayoutY(257);
		} else if (youPoints < pcPoints) {
			youText.setLayoutY(3);
			youLabel.setLayoutY(3);
			
			youText.setOpacity(0.5);
			youText.setFont(Font.font(youText.getFont().getFamily(), 20));
			youLabel.setOpacity(0.5);
			youLabel.setFont(Font.font(youText.getFont().getFamily(), 20));
			
			pcText.setOpacity(1);
			pcText.setFont(Font.font(youText.getFont().getFamily(), 30));
			pcLabel.setOpacity(1);
			pcLabel.setFont(Font.font(youText.getFont().getFamily(), 30));
			
			crown.setVisible(true);
			crown.setLayoutX(66);
			crown.setLayoutY(285);
		}
	}
	
	/*-----------------------------------------------------------
	*                       ANIMATION
	* -----------------------------------------------------------*/
	
	@FXML
	private void downListener(MouseEvent e) {
		if (e.getTarget() == startBtn) {
			animation(startBtn, 0.98);
		}
	}
	
	@FXML
	private void hoverListener(MouseEvent e) {
		
		if (state.equals(IN_MENU)) {
			// symbol
			if (e.getTarget() == xCheckBox) {
				animation(xCheckBox, 1.07);
			} else if (e.getTarget() == oCheckBox) {
				animation(oCheckBox, 1.07);
			}
			// difficulty
			if (e.getSource() == ease || e.getTarget() == easeBox) {
				animation(easeBox, 1.07);
			} else if (e.getSource() == medium || e.getTarget() == mediumBox) {
				animation(mediumBox, 1.07);
			} else if (e.getSource() == hard || e.getTarget() == hardBox) {
				animation(hardBox, 1.07);
			}
		} else if (state.equals(GAMEPLAY)) {
			
			for (int i = 0; i < btnOfPlace.length; i++) {
				int h = i % 3;
				int v = (i - (i % 3)) / 3;
				
				if (gameField[v][h].equals(EMPTY) && e.getTarget() == btnOfPlace[i]) {
					btnOfPlace[i].setEffect(colorAdjustLight);
				}
			}
		}
	}
	
	@FXML
	private void overListener(MouseEvent e) {
		if (e.getSource() == startBtn) {
			animation(startBtn, 1);
		}
		
		if (state.equals(IN_MENU)) {
			// symbol
			if (e.getTarget() == xCheckBox) {
				animation(xCheckBox, 1);
			} else if (e.getTarget() == oCheckBox) {
				animation(oCheckBox, 1);
			}
			// difficulty
			if (e.getSource() == ease || e.getTarget() == easeBox) {
				animation(easeBox, 1);
			} else if (e.getSource() == medium || e.getTarget() == mediumBox) {
				animation(mediumBox, 1);
			} else if (e.getSource() == hard || e.getTarget() == hardBox) {
				animation(hardBox, 1);
			}
		}
		
		if (state.equals(GAMEPLAY)) {
			
			for (int i = 0; i < btnOfPlace.length; i++) {
				int h = i % 3;
				int v = (i - (i % 3)) / 3;
				
				if (gameField[v][h].equals(EMPTY) && e.getTarget() == btnOfPlace[i]) {
					btnOfPlace[i].setEffect(colorAdjustReset);
				}
			}
		}
	}
	
	private void animation(Object ob, double distance) {
		KeyValue keyValueXscale = new KeyValue(((Node) ob).scaleXProperty(), distance);
		KeyValue keyValueYscale = new KeyValue(((Node) ob).scaleYProperty(), distance);
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(30), keyValueXscale, keyValueYscale);
		
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().addAll(keyFrame);
		timeline.play();
	}
	
	/*-----------------------------------------------------------
	*                       UTILS
	* -----------------------------------------------------------*/
	
	private void traceGameField() {
		System.out.println("");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(gameField[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
}
