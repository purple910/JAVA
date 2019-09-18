package com.tetris;


public class I extends Tetromino {
	/**
	 * 提供构造器，进行初始化
	 * T型的四格方块的位置
	 * */
	public I() {
		cells[0]=new Cell(0,4,Tetris.I);
		cells[1]=new Cell(0,3,Tetris.I);
		cells[2]=new Cell(0,5,Tetris.I);
		cells[3]=new Cell(0,6,Tetris.I);
		states = new State[] { 
				new State(0, 0, 0, -1, 0, 1, 0, 2),
				new State(0, 0, -1, 0, 1, 0, 2, 0)};
	}
}
