import java.util.Random;
import java.util.Scanner;

public class Maze {
	private int row;
	private int col;
	Cell cell[];

	public Maze(int row, int col) {
		this.row = row;
		this.col = col;
		initMaze();
	}

	public void initMaze() {
		cell = new Cell[row * col];
		for (int i = 0; i < row * col; i++) {
			cell[i] = new Cell();
		}
		createMaze(new DisjSets(row * col));
	}

	public void createMaze(DisjSets disjSet) {
		Random randGen = new Random();
		while (disjSet.countRoot() > 1) {
			int cell1 = randGen.nextInt(row * col);
			int direction = randGen.nextInt(4); // randomly pick a direction.
			int cell2;
			
			if (direction == 0 && cell1 >= col) { // 0:up
				cell2 = cell1 - col;
				if (disjSet.find(cell1) != disjSet.find(cell2)) { // cell1 and cell2 does not have the same root
					cell[cell2].southWall = false; // break the wall between cell1 and cell2
					disjSet.union(disjSet.find(cell1), disjSet.find(cell2));
				}
			} else if (direction == 1 && cell1 % col != col - 1) { // 1:right
				cell2 = cell1 + 1;
				if (disjSet.find(cell1) != disjSet.find(cell2)) {
					cell[cell2].westWall = false;
					disjSet.union(disjSet.find(cell1), disjSet.find(cell2));
				}
			} else if (direction == 2 && cell1 < col * (row - 1)) { // 2:down
				cell2 = cell1 + col;
				if (disjSet.find(cell1) != disjSet.find(cell2)) {
					cell[cell1].southWall = false;
					disjSet.union(disjSet.find(cell1), disjSet.find(cell2));
				}
			} else if (direction == 3 && cell1 % col != 0) { // 3:left
				cell2 = cell1 - 1;
				if (disjSet.find(cell1) != disjSet.find(cell2)) {
					cell[cell1].westWall = false;
					disjSet.union(disjSet.find(cell1), disjSet.find(cell2));
				}
			}
		}

	}

	public void printMaze() {
		System.out.print(" ↓");
		for (int i = 1; i < col; i++) {
			System.out.print(" _");
		}
		System.out.println();
		for (int i = 0; i < row * col; i++) {
			if (cell[i].westWall) {
				System.out.print("|");
			}else {
				System.out.print(" ");
			}
			if (cell[i].southWall) {
				System.out.print("_");
			}else {
				System.out.print(" ");
			}
			if ((i + 1) % col == 0) {
				if (i == row * col - 1) {
					System.out.println("→");
				} else {
					System.out.print("|\n");
				}
			}
		}
	}

	private class Cell {
		private boolean southWall;
		private boolean westWall;
		public Cell() {
			southWall = true;
			westWall = true;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int row = 0;
		int col = 0;

		while (row <= 0) {
			System.out.print("please input number of rows:");
			if (sc.hasNextInt()) {
				row = sc.nextInt();
			} else {
				System.out.println("please enter a number greater than 0.");
				sc.next();
			}
		}

		while (col <= 0) {
			System.out.print("please input number of columns:");
			if (sc.hasNextInt()) {
				col = sc.nextInt();
			} else {
				System.out.println("please enter a number greater than 0.");
				sc.next();
			}
		}
		sc.close();

		Maze maze = new Maze(row, col);

		maze.printMaze();
	}
}
