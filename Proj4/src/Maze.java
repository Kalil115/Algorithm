import java.util.Scanner;

public class Maze {
	private int row;
	private int col;
	Cell cell[][] = {};

	public Maze(int row, int col) {
		this.row = row;
		this.col = col;
		initMaze();
	}

	public void initMaze() {
		cell = new Cell[row][col];
		int count = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				cell[i][j] = new Cell();
				cell[i][j].index = count;
				cell[i][j].southWall = true;
				cell[i][j].westWall = true;
				count++;
			}
		}
	}

	public void printMaze() {
		for (int i = -1; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (i == -1) {
					if (j == 0) {
						System.out.print("  ");
						continue;
					}
					System.out.print(" _");
					continue;
				}
				if (cell[i][j].westWall)
					System.out.print("|");
				if (cell[i][j].southWall)
					System.out.print("_");
			}
			if (i != -1 && i < row - 1) {
				System.out.print("|\n");
			} else {
				System.out.print("\n");
			}

		}
	}

	private class Cell {
		private int index;
		private boolean southWall;
		private boolean westWall;

	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int row = 5;
		int col = 5;

//		while (row <= 0) {
//			System.out.print("please input number of rows:");
//			if (sc.hasNextInt()) {
//				row = sc.nextInt();
//			} else {
//				System.out.println("please enter a number greater than 0.");
//				sc.next();
//			}
//		}
//
//		while (col <= 0) {
//			System.out.print("please input number of columns:");
//			if (sc.hasNextInt()) {
//				col = sc.nextInt();
//			} else {
//				System.out.println("please enter a number greater than 0.");
//				sc.next();
//			}
//		}

		sc.close();

		Maze maze = new Maze(row, col);

		maze.printMaze();
	}
}
