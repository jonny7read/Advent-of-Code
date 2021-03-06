package year2016;

import java.awt.Point;
import java.util.ArrayList;

import utils.InputTask;

public class Task8 extends InputTask {

	private static final int	PIXEL_WIDTH		= 50;
	private static final int	PIXEL_HEIGHT	= 6;
	private boolean[][]			mPixels			= new boolean[PIXEL_WIDTH][PIXEL_HEIGHT];

	public static void main(String[] args) {
		new Task8();
	}

	public Task8() {
		ArrayList<String> input = getInput("2016Task8Input.txt", 5);

		processInput(input);

		int pixelsLeftOn = getNumberOfPixelsOn();

		System.out.println(pixelsLeftOn);
	}

	private int getNumberOfPixelsOn() {
		int count = 0;
		for (int x = 0; x < PIXEL_WIDTH; x++) {
			for (int y = 0; y < PIXEL_HEIGHT; y++) {
				if (mPixels[x][y]) {
					count++;
				}
			}
		}
		return count;
	}

	private void processInput(ArrayList<String> input) {
		System.out.println("Inital State:");

		System.out.println(input.size() + " lines of input");
		try {
			for (String command : input) {
				System.out.println(command);
				Thread.sleep(1000);

				processCommand(command);
				printState();
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void printState() {
		for (int y = 0; y < PIXEL_HEIGHT; y++) {
			for (int x = 0; x < PIXEL_WIDTH; x++) {
				System.out.print(mPixels[x][y] ? '#' : '.');
			}
			System.out.println();
		}
		System.out.println();
	}

	private void processCommand(String command) throws InterruptedException {
		if (command.startsWith("rect")) {
			int indexOfX = command.indexOf('x');
			int rectX = Integer.parseInt(command.substring(indexOfX - 1, indexOfX));
			int rectY = Integer.parseInt(command.substring(indexOfX + 1));

			fillRect(new Point(rectX, rectY));
		} else if (command.startsWith("rotate")) {
			String orientation = command.substring(7, 10); // row or col
			int lastSpace = command.lastIndexOf(' ');
			int steps = Integer.parseInt(command.substring(lastSpace + 1));
			int indexOfEquals = command.indexOf('=');
			int rowOrCol = Integer.parseInt(command.substring(indexOfEquals + 1, indexOfEquals + 2));

			if ("row".equals(orientation)) {
				shiftRow(rowOrCol, steps);
			} else {
				shiftCol(rowOrCol, steps);
			}
		} else {
			System.out.println("invalid action in command: " + command);
		}
	}

	private void fillRect(Point point) {
		for (int x = 0; x < point.x; x++) {
			for (int y = 0; y < point.y; y++) {
				mPixels[x][y] = true;
			}
		}
	}

	private void shiftRow(int row, int steps) {
		for (int x = 0; x < PIXEL_WIDTH; x++) {
			boolean stored;
			int nextX = x + steps % PIXEL_WIDTH;
			System.out.println(x + " " + nextX);

			stored = mPixels[nextX][row];
			mPixels[nextX][row] = mPixels[x][row];
			mPixels[x][row] = stored;
		}
	}

	private void flipPixel(int x, int y) {
		mPixels[x][y] = !mPixels[x][y];
	}

	private void shiftCol(int col, int steps) {
		for (int i = 0; i < steps; i++) {
			mPixels[col][PIXEL_HEIGHT - 1] = mPixels[col][0];
			for (int x = 1; x < PIXEL_HEIGHT - 1; x++) {
				mPixels[col][x + 1] = mPixels[col][x];
			}
		}
	}
}
