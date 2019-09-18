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
 * ����˹��������ࣺ
 * ǰ�᣺������һ�����JPanel������Ƕ�봰��
 * ������Դ�һ�����ʣ���һ�����ܣ��Զ����ơ�
 * ��ʵ�ǵ�����JPanel���paint()
 * 
 * 
 * 
 * 
 * (1):
 * ���ؾ�̬��Դ
 */
public class Tetris extends JPanel {

	/** ���ԣ�����������ĸ񷽿� */
	private Tetromino currentOne = Tetromino.randomOne();
	/** ���ԣ���Ҫ������ĸ񷽿� */
	private Tetromino nextOne = Tetromino.randomOne();
	/** ���ԣ�ǽ��20�� 10�е� ��� ���Ϊ26 */
	private Cell[][] wall = new Cell[20][10];
	/** ͳ�Ʒ����� */
	int[] scores_pool = { 0, 1, 2, 5, 10 };
	private int totalScore = 0;
	private int totalLine = 0;

	/** ���������������䵱��Ϸ��״̬ */
	public static final int PLAYING = 0;
	public static final int PAUSE = 1;
	public static final int GAMEOVER = 2;
	/** ����һ�����ԣ��洢��Ϸ�ĵ�ǰ״̬ */
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
			 * getResource(String url) url:����ͼƬ��·�� ���λ����ͬ����
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
	 * ��дJPanel���е�paint(Graphics g) ����.
	 */
	public void paint(Graphics g) {
		// ���Ʊ���
		/*
		 * g������ g.drawImage(image,x,y,null) image:���Ƶ�ͼƬ x:��ʼ���Ƶĺ����� y:��ʼ���Ƶ�������
		 */
		g.drawImage(background, 0, 0, null);
		// ƽ��������
		g.translate(15, 15);
		// ����ǽ
		paintWall(g);
		// ��������������ĸ񷽿�
		paintCurrentOne(g);
		// ������һ����Ҫ������ĸ񷽿�
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
	 * ������һ����Ҫ������ĸ񷽿� ���Ƶ��������Ͻǵ���Ӧ����
	 * 
	 * @param g
	 */
	public void paintNextOne(Graphics g) {
		// ��ȡnextOne������ĸ�Ԫ��
		Cell[] cells = nextOne.cells;
		for (Cell c : cells) {
			// ��ȡÿһ��Ԫ�ص��кź��к�
			int row = c.getRow();
			int col = c.getCol();
			// �������������
			int x = col * CELL_SIZE + 260;
			int y = row * CELL_SIZE + 26;
			g.drawImage(c.getImage(), x, y, null);
		}
	}

	/**
	 * ��������������ĸ񷽿� ȡ�������Ԫ�� ����Ԫ�ص�ͼƬ�� ������x: ������y:
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
	 * ǽ��20�У�10�еı�� ��һ����ά���飬 Ӧ��ʹ��˫��ѭ�� ���������Ρ�
	 * 
	 * @param a
	 */
	public void paintWall(Graphics a) {
		// ���ѭ����������
		for (int i = 0; i < 20; i++) {
			// �ڴ�ѭ����������
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
	 * ��װ����Ϸ����Ҫ�߼�
	 */
	public void start() {

		game_state = PLAYING;
		// �������̼����¼�
		KeyListener l = new KeyAdapter() {
			/*
			 * KeyPressed() �Ǽ��̰�ť ����ȥ�����õķ���
			 */
			public void keyPressed(KeyEvent e) {
				// ��ȡһ�¼��ӵĴ���
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
		// �����Ӽ����¼�����
		this.addKeyListener(l);
		// ���������óɽ���
		this.requestFocus();

		while (true) {
			/**
			 * ���������е��ˣ������˯��״̬�� ˯��ʱ��Ϊ300���룬��λΪ���� 300����󣬻��Զ�ִ�к�������
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
					// ����һ��������ĸ񷽿鸳ֵ����������ı���
					if (!isGameOver()) {
						currentOne = nextOne;
						nextOne = Tetromino.randomOne();
					} else {
						game_state = GAMEOVER;
					}
				}
				repaint();
				/*
				 * ����֮��Ҫ���½��л��ƣ��Żῴ�������� λ�� repaint���� Ҳ��JPanel�����ṩ�� �˷����е�����paint����
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
	 * ��һ�У��ͽ�������������ķ��鶼Ҫ����ƽ��
	 */
	public void destroyLine() {
		// ͳ�������е�����
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
		// �ӷ�������ȡ�������������ܷ���
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
	 * һ������
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
	 * ʹ��left�������������Ϊ
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
	 * ʹ��down�������ĸ񷽿������
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
			 * ��ȡÿ��Ԫ�ص��кź��к� �жϣ� ֻҪ��һ��Ԫ�ص���һ�����з��� ����ֻҪ��һ��Ԫ�ص������һ�У� �Ͳ�����������
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
	 * ������������ʱ����Ҫ���ĸ񷽿飬Ƕ�뵽ǽ�� Ҳ���Ǵ洢����ά��������Ӧ��λ����
	 */
	public void landToWall() {
		Cell[] cells = currentOne.cells;
		for (Cell c : cells) {
			// ��ȡ���յ��кź��к�
			int row = c.getRow();
			int col = c.getCol();
			wall[row][col] = c;
		}
	}

	/** ������������ ��Ϸ��ʼ */
	public static void main(String[] args) {
		// 1:����һ�����ڶ���
		JFrame frame = new JFrame("��ƴ����˹");

		// ������Ϸ���棬�����
		Tetris panel = new Tetris();
		// �����Ƕ�봰��
		frame.add(panel);

		// 2:����Ϊ�ɼ�
		frame.setVisible(true);
		// 3:���ô��ڵĳߴ�
		frame.setSize(535, 580);
		// 4:���ô��ھ���
		frame.setLocationRelativeTo(null);
		// 5:���ô��ڹرգ���������ֹ
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ��Ϸ����Ҫ�߼���װ��start������
		panel.start();

	}

}
