package com.tetris;

import java.util.Arrays;

/**
 *  �ĸ񷽿�
 *  ����:
 *    --cells ----�ĸ����� 
 *  ��Ϊ:
 *  	moveLeft()
 *  	moveRight()
 *  	softDrop()
 */
public class Tetromino {
	protected Cell[] cells = new Cell[4];
	/**��ת״̬���ԣ�״̬�������������ʽ���д洢*/
	protected State[] states;
	/**����һ���������䵱��ת�����ļ�����*/
	private int count = 100000;
	
	
	/**
	 * �ĸ񷽿������ƶ�
	 * ʵ���ϣ�����ÿ�����������ƶ�
	 * */
	public void moveLeft() {
		for(int i=0;i<cells.length;i++) {
			Cell cell = cells[i];
			cell.left();
		}
	}
	/**�ĸ񷽿������ƶ�*/
	public void moveRight() {
		for(Cell c:cells) {
			c.right();
		}
	}
	/**�ĸ񷽿������ƶ�*/
	public void softDrop() {
		for(Cell c:cells) {
			c.drop();
		}
	}
	@Override
	public String toString() {
		return "[" +Arrays.toString(cells) + "]";
	}
	/**�������һ���ĸ񷽿�*/
	public static Tetromino randomOne() {
		Tetromino  t = null;
		int num = (int)(Math.random()*7);
		switch(num) {
		case 0:t = new O();break;
		case 1:t = new T();break;
		case 2:t = new I();break;
		case 3:t = new J();break;
		case 4:t = new L();break;
		case 5:t = new S();break;
		case 6:t = new Z();break;
		}
		return t;
	}
	/**
	 * ˳ʱ�룬������ת�ĸ񷽿�
	 */
	
	public void rotateRight() {
		//��ת��һ�Σ�����������1
		count++;//100001
		State s = states[count%states.length];
		//��Ҫ��ȡ����кź��к�
		Cell c = cells[0];
		int row = c.getRow();
		int col = c.getCol();
		cells[1].setRow(row+s.row1);
		cells[1].setCol(col+s.col1);
		cells[2].setRow(row+s.row2);
		cells[2].setCol(col+s.col2);
		cells[3].setRow(row+s.row3);
		cells[3].setCol(col+s.col3);
		
		
		
	}
	/**
	 * ��ʱ�룬������ת�ĸ񷽿�
	 */
	public void rotateLeft() {
		count--;//100001
		State s = states[count%states.length];
		//��Ҫ��ȡ����кź��к�
		Cell c = cells[0];
		int row = c.getRow();
		int col = c.getCol();
		cells[1].setRow(row+s.row1);
		cells[1].setCol(col+s.col1);
		cells[2].setRow(row+s.row2);
		cells[2].setCol(col+s.col2);
		cells[3].setRow(row+s.row3);
		cells[3].setCol(col+s.col3);
	}
	/**
	 * �����ڲ��ࣺState,����
	 * ��װÿ����ת������������������������
	 * ����(�кţ��к�)
	 */
	public class State{
		/**
		 * ��ư˸����ԣ��ֱ�洢�ĸ񷽿�Ԫ�ص����
		 * λ��
		 */
		int row0,col0,row1,col1,row2,col2,row3,col3;
		public State() {}
		public State(int row0, int col0, int row1, int col1, int row2, int col2, int row3, int col3) {
			super();
			this.row0 = row0;
			this.col0 = col0;
			this.row1 = row1;
			this.col1 = col1;
			this.row2 = row2;
			this.col2 = col2;
			this.row3 = row3;
			this.col3 = col3;
		}
		public int getRow0() {
			return row0;
		}
		public void setRow0(int row0) {
			this.row0 = row0;
		}
		public int getCol0() {
			return col0;
		}
		public void setCol0(int col0) {
			this.col0 = col0;
		}
		public int getRow1() {
			return row1;
		}
		public void setRow1(int row1) {
			this.row1 = row1;
		}
		public int getCol1() {
			return col1;
		}
		public void setCol1(int col1) {
			this.col1 = col1;
		}
		public int getRow2() {
			return row2;
		}
		public void setRow2(int row2) {
			this.row2 = row2;
		}
		public int getCol2() {
			return col2;
		}
		public void setCol2(int col2) {
			this.col2 = col2;
		}
		public int getRow3() {
			return row3;
		}
		public void setRow3(int row3) {
			this.row3 = row3;
		}
		public int getCol3() {
			return col3;
		}
		public void setCol3(int col3) {
			this.col3 = col3;
		}
		@Override
		public String toString() {
			return "State [row0=" + row0 + ", col0=" + col0 + ", row1=" + row1 + ", col1=" + col1 + ", row2=" + row2
					+ ", col2=" + col2 + ", row3=" + row3 + ", col3=" + col3 + "]";
		}
		
	
	}
}










