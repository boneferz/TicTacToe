package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
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
	private final String X_SYMBOL = "x symbol";
	private final String O_SYMBOL = "o symbol";
	
	private String currentDifficalty;
	private final String EASE = "ease";
	private final String MEDIUM = "medium";
	private final String HARD = "hard";
	
	private String state;
	private final String GAMEPLAY = "gameplay";
	private final String IN_MENU = "menu";
	
	private int youPoints;
	private int pcPoints;
	private String[][] gameField = {
			{ "-", "-", "-"},
			{ "-", "-", "-"},
			{ "-", "-", "-"}
	};
	
	private ImageView[] btnPlaceArr = new ImageView[9];
	
	{
		btnPlaceArr[0] = box_1;
		btnPlaceArr[1] = box_2;
		btnPlaceArr[2] = box_3;
		btnPlaceArr[3] = box_4;
		btnPlaceArr[4] = box_5;
		btnPlaceArr[5] = box_6;
		btnPlaceArr[6] = box_7;
		btnPlaceArr[7] = box_8;
		btnPlaceArr[8] = box_9;
	}
	
	private void init() {
		setState(IN_MENU);
		
		youPoints = 0;
		pcPoints = 0;
		
		setSymbol(X_SYMBOL);
		setDifficalty(EASE);
		
		
		
		pcText.setText(Integer.toString(pcPoints));
		youText.setText(Integer.toString(youPoints));
		
		grid.setOpacity(0.25);
		
		
		
		for (int i = 0; i < btnPlaceArr.length; i++) {
			btnPlaceArr[i].setOpacity(0.5);
		}
		
		setState(GAMEPLAY);
	}
	
	@FXML
	public void initialize() {
		
		init();
		
	}
	
	void addImg(int x, int y) {
		Image img = new Image("sample/res/o.png");
		ImageView imgWrap = new ImageView(img);
		imgWrap.setX(x);
		imgWrap.setY(y);
		rootPane.getChildren().add(imgWrap);
	}
	
	/*-----------------------------------------------------------
	*                       Mouse listeners
	* -----------------------------------------------------------*/
	
	@FXML
	private void clickListener(MouseEvent e) {
		// change SYMBOL
		if (e.getTarget() == oCheckBox) {
			setSymbol(O_SYMBOL);
			textInfo.setText("your symbol " + O_SYMBOL);
		} else if (e.getTarget() == xCheckBox) {
			setSymbol(X_SYMBOL);
			textInfo.setText("your symbol " + X_SYMBOL);
		}
		
		// change DIFFICALTY
		if (e.getSource() == ease || e.getTarget() == easeBox) {
			setDifficalty(EASE);
			textInfo.setText("difficalty " + EASE);
		} else if (e.getSource() == medium || e.getTarget() == mediumBox) {
			setDifficalty(MEDIUM);
			textInfo.setText("difficalty " + MEDIUM);
		} else if (e.getSource() == hard || e.getTarget() == hardBox) {
			setDifficalty(HARD);
			textInfo.setText("difficalty " + HARD);
		}
		
		if (e.getTarget() == startBtn) {
			textInfo.setText("step: PC");
			animation(startBtn, 1);
		}
		
		System.out.println(e.getX());
		addImg((int) e.getX() - 30, (int) e.getY() - 30);
	}
	
	
	/*-----------------------------------------------------------
	*                       SETTERS
	* -----------------------------------------------------------*/
	
	private void setState(String state) {
		this.state = state;
		
		switch (state) {
			case GAMEPLAY:
				switch (currentSymbol) {
					case X_SYMBOL:
						xCheckBox.setOpacity(1);
						xCheckBox_g.setOpacity(1);
						
						oCheckBox.setOpacity(0.5);
						oCheckBox_o.setOpacity(0.5);
						break;
					case O_SYMBOL:
						oCheckBox.setOpacity(1);
						oCheckBox_o.setOpacity(1);
						
						xCheckBox.setOpacity(0.5);
						xCheckBox_g.setOpacity(0.5);
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
				break;
				
			case IN_MENU:
				switch (currentSymbol) {
					case X_SYMBOL:
						xCheckBox.setOpacity(1);
						xCheckBox_g.setOpacity(1);
						oCheckBox.setOpacity(1);
						oCheckBox_o.setOpacity(1);
						break;
					case O_SYMBOL:
						oCheckBox.setOpacity(1);
						oCheckBox_o.setOpacity(1);
						xCheckBox.setOpacity(1);
						xCheckBox_g.setOpacity(1);
						break;
				}
				
				switch (currentDifficalty) {
					case EASE:
						ease.setOpacity(1);
						easeBox.setOpacity(1);
						medium.setOpacity(1);
						mediumBox.setOpacity(1);
						hard.setOpacity(1);
						hardBox.setOpacity(1);
						break;
					case MEDIUM:
						medium.setOpacity(1);
						mediumBox.setOpacity(1);
						ease.setOpacity(1);
						easeBox.setOpacity(1);
						hard.setOpacity(1);
						hardBox.setOpacity(1);
						break;
					case HARD:
						hard.setOpacity(1);
						hardBox.setOpacity(1);
						ease.setOpacity(1);
						easeBox.setOpacity(1);
						medium.setOpacity(1);
						mediumBox.setOpacity(1);
						break;
				}
				break;
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
		if (e.getTarget() == xCheckBox) {
			animation(xCheckBox, 1.07);
		} else if (e.getTarget() == oCheckBox) {
			animation(oCheckBox, 1.07);
		}
		
		if (e.getSource() == ease || e.getTarget() == easeBox) {
			animation(easeBox, 1.07);
		} else if (e.getSource() == medium || e.getTarget() == mediumBox) {
			animation(mediumBox, 1.07);
		} else if (e.getSource() == hard || e.getTarget() == hardBox) {
			animation(hardBox, 1.07);
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
		if (e.getTarget() == xCheckBox) {
			animation(xCheckBox, 1);
		} else if (e.getTarget() == oCheckBox) {
			animation(oCheckBox, 1);
		}
		
		if (e.getSource() == ease || e.getTarget() == easeBox) {
			animation(easeBox, 1);
		} else if (e.getSource() == medium || e.getTarget() == mediumBox) {
			animation(mediumBox, 1);
		} else if (e.getSource() == hard || e.getTarget() == hardBox) {
			animation(hardBox, 1);
		}
		
		if (e.getSource() == startBtn) {
			animation(startBtn, 1);
		}
		
		if (state.equals(GAMEPLAY)) {
			System.out.println(state);
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
