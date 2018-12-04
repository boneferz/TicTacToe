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
import javafx.util.Duration;

import java.util.Random;

public class Controller {
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private Label youText;
	
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
	
	private boolean youIsTurned = false;
	private boolean pcIsTurned = false;
	
	private int youPoints = 0;
	private int pcPoints = 0;
	
	private ImageView[] btnOfPlace = new ImageView[9];
	private boolean[] isBusyPlase = new boolean[9];
	private String[][] gameField = {
			{ "-", "-", "-"},
			{ "-", "-", "-"},
			{ "-", "-", "-"}
	};
	private int emptyPlaces;
	
	private ColorAdjust colorAdjustReset = new ColorAdjust(
			0, 0, 0, 0);
	private ColorAdjust colorAdjustLight = new ColorAdjust(
			0, 0, 0.25, 0);
	
	
	private void init() {
		for (int i = 0; i < gameField.length; i++) {
			for (int j = 0; j < gameField[0].length; j++) {
				gameField[i][j] = EMPTY;
			}
		}
		
		for (int i = 0; i < isBusyPlase.length; i++) {
			isBusyPlase[i] = false;
		}
		
		emptyPlaces = 9;
		
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

//		setState(IN_MENU);
		setSymbol(O_SYMBOL);
		setDifficalty(EASE);
		
		setState(GAMEPLAY);
	}
	
	@FXML
	public void initialize() {
		
		init();
		
	}
	
	/*-----------------------------------------------------------
	*                       game play
	* -----------------------------------------------------------*/
	
	private void play() {
		switchFirsturn();
	}
	
	private void PC_turn() {
		if (whoesTurn.equals(PC) && !pcIsTurned) {
			
			int random, h, v;
			do {
				random = (int) (Math.random() * 9);
				h = random % 3;
				v = (random - (random % 3)) / 3;
			
			} while (!gameField[v][h].equals(EMPTY));
			
			
			gameField[v][h] = X_SYMBOL;
			isBusyPlase[random] = true;
			emptyPlaces --;
			traceGameField();
			
			btnOfPlace[random].setCursor(Cursor.DEFAULT);
			btnOfPlace[random].setEffect(null);
			
			addImg_X((int) btnOfPlace[random].getLayoutX(),
					(int) btnOfPlace[random].getLayoutY());
			
			pcIsTurned = true;
			switchWhoesTurn();
		}
	}
	
	private void addImg_X(int x, int y) {
		Image img = new Image("sample/res/x.png");
		ImageView imgWrap = new ImageView(img);
		imgWrap.setX(x + 3);
		imgWrap.setY(y + 3);
		rootPane.getChildren().add(imgWrap);
	}
	
	private void addImg_O(int x, int y) {
		Image img = new Image("sample/res/o.png");
		ImageView imgWrap = new ImageView(img);
		imgWrap.setX(x + 3);
		imgWrap.setY(y + 3);
		rootPane.getChildren().add(imgWrap);
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
				textInfo.setText("your symbol " + O_SYMBOL);
			} else if (e.getTarget() == xCheckBox) {
				setSymbol(X_SYMBOL);
				textInfo.setText("your symbol " + X_SYMBOL);
			}
			// difficulty
			if (e.getSource() == ease || e.getTarget() == easeBox) {
				animation(easeBox, 1);
				setDifficalty(EASE);
				textInfo.setText("difficalty " + EASE);
			} else if (e.getSource() == medium || e.getTarget() == mediumBox) {
				animation(mediumBox, 1);
				setDifficalty(MEDIUM);
				textInfo.setText("difficalty " + MEDIUM);
			} else if (e.getSource() == hard || e.getTarget() == hardBox) {
				animation(hardBox, 1);
				setDifficalty(HARD);
				textInfo.setText("difficalty " + HARD);
			}
			// start
			if (e.getTarget() == startBtn) {
				setState(GAMEPLAY);
				textInfo.setText(" set: gameplay");
				animation(startBtn, 1);
			}
		}
		else if (state.equals(GAMEPLAY)) {
			
			if (e.getTarget() == startBtn) {
				setState(IN_MENU);
				textInfo.setText("set: menu");
				animation(startBtn, 1);
			}
			
			for (int i = 0; i < btnOfPlace.length; i++) {
				int h = i % 3;
				int v = (i - (i % 3)) / 3;
				
				if (whoesTurn.equals(YOU)
						&& !youIsTurned
						&& e.getTarget() == btnOfPlace[i]
						&& gameField[v][h].equals(EMPTY)) {
					
					// add symbol
					addImg_O(
							(int) ((Node) e.getSource()).getLayoutX(),
							(int) ((Node) e.getSource()).getLayoutY()
					);
					
					gameField[v][h] = currentSymbol;
					isBusyPlase[i] = true;
					emptyPlaces --;
					traceGameField();
					
					btnOfPlace[i].setCursor(Cursor.DEFAULT);
					btnOfPlace[i].setEffect(null);
					
					youIsTurned = true;
					switchWhoesTurn();
				}
			}
		}
	}
	
	/*-----------------------------------------------------------
	*                       SETTERS
	* -----------------------------------------------------------*/
	
	private void setState(String state) {
		this.state = state;
		textInfo.setText(state);
		
		if (state.equals(GAMEPLAY)) {
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
			
			play(); // <--
		}
		
		if (state.equals(IN_MENU)) {
			grid.setOpacity(0.25);
			startBtn.setOpacity(1);
			
			for (int i = 0; i < btnOfPlace.length; i++) {
				btnOfPlace[i].setOpacity(0.25);
				btnOfPlace[i].setCursor(Cursor.DEFAULT);
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
			textInfo.setText("The End!");
			return;
		}
		
		if (pcIsTurned) {
			pcIsTurned = false;
			setWhoesTurn(YOU);
		} else if (youIsTurned) {
			youIsTurned = false;
			setWhoesTurn(PC);
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
//						btnOfPlace[i].setEffect(null);
						btnOfPlace[i].setCursor(Cursor.DEFAULT);
					}
				}
				
				int delay = (int) (Math.random() * 1200) + 300;
				PauseTransition pause = new PauseTransition(Duration.millis(delay));
				pause.setOnFinished( e -> {
					PC_turn();
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
				if (gameField[v][h].equals(EMPTY)
						&& e.getTarget() == btnOfPlace[i]) {
					
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
				
				if (gameField[v][h].equals(EMPTY)
						&& e.getTarget() == btnOfPlace[i]) {
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
