package sample;

public class PcThinkAnswer {
	private int direction;
	private int mapIndex;
	private int turnIndexH;
	private int turnIndexV;
	private boolean isAttack;
	private boolean isDefense;
	
	public int getDirection() { return direction; }
	public void setDirection(int direction) { this.direction = direction; }
	
	public int getMapIndex() { return mapIndex;}
	public void setMapIndex(int mapIndex) {this.mapIndex = mapIndex; }
	
	public int getTurnIndexH() { return turnIndexH; }
	public void setTurnIndexH(int turnIndexH) { this.turnIndexH = turnIndexH; }
	
	public int getTurnIndexV() { return turnIndexV; }
	public void setTurnIndexV(int turnIndexV) { this.turnIndexV = turnIndexV; }
	
	public boolean getIsAttack() { return isAttack; }
	public void setIsAttack(boolean attack) { isAttack = attack; }
	
	public boolean getIsDefense() { return isDefense; }
	public void setIsDefense(boolean defense) { isDefense = defense; }
	
}
