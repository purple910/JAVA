package com.tetris;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * 俄罗斯方块的主类：
 * 前提：必须是一块面板JPanel，可以嵌入窗口
 * 面板上自带一个画笔，有一个功能：自动绘制。
 * 其实是调用了JPanel里的paint()
 * 
 * 
 * 
 * 
 * (1):
 * 加载静态资源
 */
public class Tetris extends JPanel {

	/** 属性：正在下落的四格方块 */
	private Tetromino currentOne = Tetromino.randomOne();
	/** 属性：将要下落的四格方块 */
	private Tetromino nextOne = Tetromino.randomOne();
	/** 属性：墙，20行 10列的 表格 宽度为26 */
	private Cell[][] wall = new Cell[20][10];
	/** 统计分数： */
	int[] scores_pool = { 0, 1, 2, 5, 10 };
	private int totalScore = 0;
	private int totalLine = 0;

	/** 定义三个常量：充当游戏的状态 */
	public static final int PLAYING = 0;
	public static final int PAUSE = 1;
	public static final int GAMEOVER = 2;
	/** 定义一个属性，存储游戏的当前状态 */
	private int game_state;

	String[] showState = { "P[pause]", "C[continue]", "Enter[replay]" };

	private static final int CELL_SIZE = 26;
	public static BufferedImage T;
	public static BufferedImage I;
	public static BufferedImage O;
	public static BufferedImage J;
	public static BufferedImage L;
	public static BufferedImage S;
	public static BufferedImage Z;
	public static BufferedImage background;
	public static BufferedImage game_over;
	static {
		try {
			/*
			 * getResource(String url) url:加载图片的路径 相对位置是同包下
			 */
			T = ImageIO.read(Tetris.class.getResource("T.png"));
			O = ImageIO.read(Tetris.class.getResource("O.png"));
			I = ImageIO.read(Tetris.class.getResource("I.png"));
			J = ImageIO.read(Tetris.class.getResource("J.png"));
			L = ImageIO.read(Tetris.class.getResource("L.png"));
			S = ImageIO.read(Tetris.class.getResource("S.png"));
			Z = ImageIO.read(Tetris.class.getResource("Z.png"));
			background = ImageIO.read(Tetris.class.getResource("tetris.png"));
			game_over = ImageIO.read(Tetris.class.getResource("game-over.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重写JPanel类中的paint(Graphics g) 方法.
	 */
	public void paint(Graphics g) {
		// 绘制背景
		/*
		 * g：画笔 g.drawImage(image,x,y,null) image:绘制的图片 x:开始绘制的横坐标 y:开始绘制的纵坐标
		 */
		g.drawImage(background, 0, 0, null);
		// 平移坐标轴
		g.translate(15, 15);
		// 绘制墙
		paintWall(g);
		// 绘制正在下落的四格方块
		paintCurrentOne(g);
		// 绘制下一个将要下落的四格方块
		paintNextOne(g);

		paintScore(g);
		paintState(g);
	}

	private void paintState(Graphics g) {
		if (game_state == GAMEOVER) {
			g.drawImage(game_over, 0, 0, null);
			g.drawString(showState[GAMEOVER], 285, 265);
		}
		if (game_state == PLAYING) {
			g.drawString(showState[PLAYING], 285, 265);
		}
		if (game_state == PAUSE) {
			g.drawString(showState[PAUSE], 285, 265);
		}

	}

	public void paintScore(Graphics g) {
		g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 26));
		g.drawString("SCORES:" + totalScore, 285, 165);
		g.drawString("LINES:" + totalLine, 285, 215);
	}

	/**
	 * 绘制下一个将要下落的四格方块 绘制到面板的右上角的相应区域
	 * 
	 * @param g
	 */
	public void paintNextOne(Graphics g) {
		// 获取nextOne对象的四个元素
		Cell[] cells = nextOne.cells;
		for (Cell c : cells) {
			// 获取每一个元素的行号和列号
			int row = c.getRow();
			int col = c.getCol();
			// 横坐标和纵坐标
			int x = col * CELL_SIZE + 260;
			int y = row * CELL_SIZE + 26;
			g.drawImage(c.getImage(), x, y, null);
		}
	}

	/**
	 * 绘制正在下落的四格方块 取出数组的元素 绘制元素的图片， 横坐标x: 纵坐标y:
	 * 
	 */
	public void paintCurrentOne(Graphics g) {
		Cell[] cells = currentOne.cells;
		for (Cell c : cells) {
			int x = c.getCol() * CELL_SIZE;
			int y = c.getRow() * CELL_SIZE;
			g.drawImage(c.getImage(), x, y, null);
		}
	}

	/**
	 * 墙是20行，10列的表格 是一个二维数组， 应该使用双层循环 绘制正方形。
	 * 
	 * @param a
	 */
	public void paintWall(Graphics a) {
		// 外层循环控制行数
		for (int i = 0; i < 20; i++) {
			// 内存循环控制列数
			for (int j = 0; j < 10; j++) {
				int x = j * CELL_SIZE;
				int y = i * CELL_SIZE;
				Cell cell = wall[i][j];
				if (cell == null) {
					a.drawRect(x, y, CELL_SIZE, CELL_SIZE);
				} else {
					a.drawImage(cell.getImage(), x, y, null);
				}
			}
		}
	}

	/**
	 * 封装了游戏的主要逻辑
	 */
	public void start() {

		game_state = PLAYING;
		// 开启键盘监听事件
		KeyListener l = new KeyAdapter() {
			/*
			 * KeyPressed() 是键盘按钮 按下去所调用的方法
			 */
			public void keyPressed(KeyEvent e) {
				// 获取一下键子的代号
				int code = e.getKeyCode();

				if (code == KeyEvent.VK_P) {
					if (game_state == PLAYING) {
						game_state = PAUSE;
					}

				}
				if (code == KeyEvent.VK_C) {
					if (game_state == PAUSE) {
						game_state = PLAYING;
					}
				}
				if (code == KeyEvent.VK_ENTER) {
					game_state = PLAYING;
					wall = new Cell[20][10];
					currentOne = Tetromino.randomOne();
					nextOne = Tetromino.randomOne();
					totalScore = 0;
					totalLine = 0;
				}

				switch (code) {
				case KeyEvent.VK_DOWN:
					softDropAction();
					break;
				case KeyEvent.VK_LEFT:
					moveLeftAction();
					break;
				case KeyEvent.VK_RIGHT:
					moveRightAction();
					break;
				case KeyEvent.VK_UP:
					rotateRightAction();
					break;
				case KeyEvent.VK_SPACE:
					handDropAction();
					break;
				}
				repaint();
			}

		};
		// 面板添加监听事件对象
		this.addKeyListener(l);
		// 面板对象设置成焦点
		this.requestFocus();

		while (true) {
			/**
			 * 当程序运行到此，会进入睡眠状态， 睡眠时间为300毫秒，单位为毫秒 300毫秒后，会自动执行后续代码
			 */
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (game_state == PLAYING) {
				if (canDrop()) {
					currentOne.softDrop();
				} else {
					landToWall();
					destroyLine();
					// 将下一个下落的四格方块赋值给正在下落的变量
					if (!isGameOver()) {
						currentOne = nextOne;
						nextOne = Tetromino.randomOne();
					} else {
						game_state = GAMEOVER;
					}
				}
				repaint();
				/*
				 * 下落之后，要重新进行绘制，才会看到下落后的 位置 repaint方法 也是JPanel类中提供的 此方法中调用了paint方法
				 */
			}
		}
	}

	public boolean isGameOver() {
		Cell[] cells = nextOne.cells;
		for (Cell c : cells) {
			int row = c.getRow();
			int col = c.getCol();
			if (wall[row][col] != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 满一行，就进行消除，上面的方块都要向下平移
	 */
	public void destroyLine() {
		// 统计销毁行的行数
		int lines = 0;

		Cell[] cells = currentOne.cells;
		for (Cell c : cells) {
			int row = c.getRow();
			while (row < 20) {
				if (isFullLine(row)) {
					lines++;
					wall[row] = new Cell[10];
					for (int i = row; i > 0; i--) {
						System.arraycopy(wall[i - 1], 0, wall[i], 0, 10);
					}
					wall[0] = new Cell[10];
				}
				row++;
			}
		}
		// 从分数池中取出分数，加入总分数
		totalScore += scores_pool[lines];
		totalLine += lines;

	}

	public boolean isFullLine(int row) {
		Cell[] line = wall[row];
		for (Cell c : line) {
			if (c == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 一键到底
	 */
	public void handDropAction() {
		for (;;) {
			if (canDrop()) {
				currentOne.softDrop();
			} else {
				break;
			}
		}
		landToWall();
		destroyLine();
		if (!isGameOver()) {
			currentOne = nextOne;
			nextOne = Tetromino.randomOne();
		} else {
			game_state = GAMEOVER;
		}
	}

	public void rotateRightAction() {
		currentOne.rotateRight();
		if (outOfBounds() || coincide()) {
			currentOne.rotateLeft();
		}
	}

	protected void moveRightAction() {
		currentOne.moveRight();
		if (outOfBounds() || coincide()) {
			currentOne.moveLeft();
		}

	}

	/**
	 * 使用left键控制向左的行为
	 */
	protected void moveLeftAction() {
		currentOne.moveLeft();
		if (outOfBounds() || coincide()) {
			currentOne.moveRight();
		}
	}

	public boolean outOfBounds() {
		Cell[] cells = currentOne.cells;
		for (Cell c : cells) {
			int col = c.getCol();
			int row = c.getRow();
			if (col < 0 || col > 9 || row > 19 || row < 0) {
				return true;
			}
		}
		return false;

	}

	public boolean coincide() {
		Cell[] cells = currentOne.cells;
		for (Cell c : cells) {
			int row = c.getRow();
			int col = c.getCol();
			if (wall[row][col] != null) {
				return true;
			}
		}
		return false;
	}

	/*
	 * 使用down键控制四格方块的下落
	 */
	public void softDropAction() {
		if (canDrop()) {
			currentOne.softDrop();
		} else {
			landToWall();
			destroyLine();
			currentOne = nextOne;
			nextOne = Tetromino.randomOne();
		}
	}

	public boolean canDrop() {
		Cell[] cells = currentOne.cells;
		/*
		 * 
		 */
		for (Cell c : cells) {
			/*
			 * 获取每个元素的行号和列号 判断： 只要有一个元素的下一行上有方块 或者只要有一个元素到达最后一行， 就不能再下落了
			 */
			int row = c.getRow();
			int col = c.getCol();

			if (row == 19) {
				return false;
			}
			if (wall[row + 1][col] != null) {
				return false;
			}
		}
		return true;
	}

	/*
	 * 当不能再下落时，需要将四格方块，嵌入到墙中 也就是存储到二维数组中相应的位置上
	 */
	public void landToWall() {
		Cell[] cells = currentOne.cells;
		for (Cell c : cells) {
			// 获取最终的行号和列号
			int row = c.getRow();
			int col = c.getCol();
			wall[row][col] = c;
		}
	}

	/** 启动程序的入口 游戏开始 */
	public static void main(String[] args) {
		// 1:创建一个窗口对象
		JFrame frame = new JFrame("火拼俄罗斯");

		// 创建游戏界面，即面板
		Tetris panel = new Tetris();
		// 将面板嵌入窗口
		frame.add(panel);

		// 2:设置为可见
		frame.setVisible(true);
		// 3:设置窗口的尺寸
		frame.setSize(535, 580);
		// 4:设置窗口居中
		frame.setLocationRelativeTo(null);
		// 5:设置窗口关闭，即程序终止
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 游戏的主要逻辑封装在start方法中
		panel.start();

	}

}
