package com.tetris;

public class T extends Tetromino{
	/**
	 * �ṩ�����������г�ʼ��
	 * T�͵��ĸ񷽿��λ��
	 * */
	public T() {
		cells[0]=new Cell(0,4,Tetris.T);
		cells[1]=new Cell(0,3,Tetris.T);
		cells[2]=new Cell(0,5,Tetris.T);
		cells[3]=new Cell(1,4,Tetris.T);
		states = new State[4];
		states[0] = new State(0,0,0,-1,0,1,1,0);//s0
		states[1] = new State(0,0,-1,0,1,0,0,-1);//s1
		states[2] = new State(0,0,0,1,0,-1,-1,0);//s2
		states[3] = new State(0,0,1,0,-1,0,0,1);	//s3	
	}
}
