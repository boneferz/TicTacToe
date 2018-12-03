package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

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
	
	private int youPoints;
	private int pcPoints;
	
	private ImageView[] btnPlaceArr = new ImageView[9];
	private String[][] gameField = {
			{ "-", "-", "-"},
			{ "-", "-", "-"},
			{ "-", "-", "-"}
	};
	
	
	private void init() {
		btnPlaceArr[0] = box_1;
		btnPlaceArr[1] = box_2;
		btnPlaceArr[2] = box_3;
		btnPlaceArr[3] = box_4;
		btnPlaceArr[4] = box_5;
		btnPlaceArr[5] = box_6;
		btnPlaceArr[6] = box_7;
		btnPlaceArr[7] = box_8;
		btnPlaceArr[8] = box_9;
		
		youPoints = 0;
		pcPoints = 0;
		pcText.setText(Integer.toString(pcPoints));
		youText.setText(Integer.toString(youPoints));
		
		whoesTurn = YOU;
		
		setSymbol(O_SYMBOL);
		setDifficalty(EASE);
//		setState(IN_MENU);
		setState(GAMEPLAY);
	}
	
	@FXML
	public void initialize() {
		
		init();
		
	}
	
	void gameplay() {
		System.out.println(whoesTurn);
		if (whoesTurn.equals(YOU)) {
			textInfo.setText("you turn, put a symbol");
		} else if (whoesTurn.equals(PC)) {
			textInfo.setText("pc turn, wait ...");
		}
	}
	
	void addImg(int x, int y) {
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
			
			System.out.println(e.getSource());
			System.out.println(e.getTarget());
			System.out.println("");
			
			if (whoesTurn.equals(YOU) || !youIsTurned) {
				youIsTurned = true;
			}
			
			if (e.getTarget() == box_1) {
				gameField[0][0] = currentSymbol;
//				youPoints ++;
//				youText.setText(Integer.toString(youPoints));
//				addImg((int) box_1.getLayoutX(), (int) box_1.getLayoutY());
//				System.out.println("box 1");
				box_1.setEffect(new ColorAdjust(0, 0, 0.45, 0));
			} else if (e.getTarget() == box_2) {
				gameField[0][1] = currentSymbol;
//				pcPoints ++;
//				pcText.setText(Integer.toString(pcPoints));
//				addImg((int) box_2.getLayoutX(), (int) box_2.getLayoutY());
				box_2.setEffect(new ColorAdjust(0, 0, 0.45, 0));
			} else if (e.getTarget() == box_3) {
				gameField[0][2] = currentSymbol;
				box_3.setEffect(new ColorAdjust(0, 0, 0.45, 0));
			} else if (e.getTarget() == box_4) {
				gameField[1][0] = currentSymbol;
				box_4.setEffect(new ColorAdjust(0, 0, 0.45, 0));
			} else if (e.getTarget() == box_5) {
				gameField[1][1] = currentSymbol;
				box_5.setEffect(new ColorAdjust(0, 0, 0.45, 0));
			} else if (e.getTarget() == box_6) {
				gameField[1][2] = currentSymbol;
				box_6.setEffect(new ColorAdjust(0, 0, 0.45, 0));
			} else if (e.getTarget() == box_7) {
				gameField[2][0] = currentSymbol;
				box_7.setEffect(new ColorAdjust(0, 0, 0.45, 0));
			} else if (e.getTarget() == box_8) {
				gameField[2][1] = currentSymbol;
				box_8.setEffect(new ColorAdjust(0, 0, 0.45, 0));
			} else if (e.getTarget() == box_9) {
				gameField[2][2] = currentSymbol;
				box_9.setEffect(new ColorAdjust(0, 0, 0.45, 0));
			}
			
			addImg(
					(int) ((Node) e.getSource()).getLayoutX(),
					(int) ((Node) e.getSource()).getLayoutY()
			);
			
			pcIsTurned = false;
			whoesTurn = PC;
			// setTurn
			gameplay();
			
			traceGameField();
			
		}
	}
	
	void traceGameField() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(gameField[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	
	/*-----------------------------------------------------------
	*                       SETTERS
	* -----------------------------------------------------------*/
	
	private void setState(String state) {
		this.state = state;
		textInfo.setText(state);
		
		if (state.equals(GAMEPLAY)) {
			gameplay(); // <--
			
			grid.setOpacity(1);
			startBtn.setOpacity(0.75);
			
			for (int i = 0; i < btnPlaceArr.length; i++) {
				btnPlaceArr[i].setOpacity(1);
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
		}
		
		if (state.equals(IN_MENU)) {
			grid.setOpacity(0.25);
			startBtn.setOpacity(1);
			
			for (int i = 0; i < btnPlaceArr.length; i++) {
				btnPlaceArr[i].setOpacity(0.25);
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
	
	private void setSymbol(String symbol) {
		currentSymbol = symbol;
		
		switch (symbol) {
			case X_SYMBOL:
				arrow.setX(xCheckBoxPane.getLayoutX() - 40);
				break;
			case O_SYMBOL:
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
		}
		
		if (state.equals(GAMEPLAY)) {
			if (e.getSource() == box_1) {
				box_1.setEffect(new ColorAdjust(0, 0, 0.25, 0));
			} else if (e.getSource() == box_2) {
				box_2.setEffect(new ColorAdjust(0, 0, 0.25, 0));
			} else if (e.getSource() == box_3) {
				box_3.setEffect(new ColorAdjust(0, 0, 0.25, 0));
			} else if (e.getSource() == box_4) {
				box_4.setEffect(new ColorAdjust(0, 0, 0.25, 0));
			} else if (e.getSource() == box_5) {
				box_5.setEffect(new ColorAdjust(0, 0, 0.25, 0));
			} else if (e.getSource() == box_6) {
				box_6.setEffect(new ColorAdjust(0, 0, 0.25, 0));
			} else if (e.getSource() == box_7) {
				box_7.setEffect(new ColorAdjust(0, 0, 0.25, 0));
			} else if (e.getSource() == box_8) {
				box_8.setEffect(new ColorAdjust(0, 0, 0.25, 0));
			} else if (e.getSource() == box_9) {
				box_9.setEffect(new ColorAdjust(0, 0, 0.25, 0));
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
			if (e.getSource() == box_1) {
				box_1.setEffect(new ColorAdjust(0, 0, 0, 0));
			} else if (e.getSource() == box_2) {
				box_2.setEffect(new ColorAdjust(0, 0, 0, 0));
			} else if (e.getSource() == box_3) {
				box_3.setEffect(new ColorAdjust(0, 0, 0, 0));
			} else if (e.getSource() == box_4) {
				box_4.setEffect(new ColorAdjust(0, 0, 0, 0));
			} else if (e.getSource() == box_5) {
				box_5.setEffect(new ColorAdjust(0, 0, 0, 0));
			} else if (e.getSource() == box_6) {
				box_6.setEffect(new ColorAdjust(0, 0, 0, 0));
			} else if (e.getSource() == box_7) {
				box_7.setEffect(new ColorAdjust(0, 0, 0, 0));
			} else if (e.getSource() == box_8) {
				box_8.setEffect(new ColorAdjust(0, 0, 0, 0));
			} else if (e.getSource() == box_9) {
				box_9.setEffect(new ColorAdjust(0, 0, 0, 0));
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
}
