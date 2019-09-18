package com.tetris;

public class O extends Tetromino {
	/**
	 * 提供构造器，进行初始化
	 * O型的四格方块的位置
	 * */
	public O() {
		cells[0]=new Cell(0,4,Tetris.O);
		cells[1]=new Cell(0,5,Tetris.O);
		cells[2]=new Cell(1,4,Tetris.O);
		cells[3]=new Cell(1,5,Tetris.O);
		states = new State[] { new State(0, 0, 0, 1, 1, 0, 1, 1)};
	}
}
