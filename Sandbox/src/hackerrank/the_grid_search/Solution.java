package hackerrank.the_grid_search;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Solution {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		Scanner in = new Scanner(System.in);
		int tcs = in.nextInt();
		boolean[] results = new boolean[tcs];
		for (int i = 0; i < tcs; i++) {
			String[] mainGrid = getMatrixFromInput(in);
			String[] subGrid = getMatrixFromInput(in);
			results[i] = isGridContained(mainGrid, subGrid);
		}
		for (boolean result:results) {
			if (result) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
	}

	private static boolean isGridContained(String[] mainGrid, String[] subGrid) {
		int mainGridRows = mainGrid.length;
		int subGridRows = subGrid.length;
		int subGrodColumns = subGrid[0].length();
		String subGridFirstRow = subGrid[0];

		for (int i = 0; i <= mainGridRows - subGridRows; i++) {
			int columnIndex = mainGrid[i].indexOf(subGridFirstRow);
			while (columnIndex != -1) {
				String[] gridToCompare = getSubgridFromGrid(mainGrid, subGridRows, subGrodColumns, i, columnIndex);
				if (areGridsSame(gridToCompare, subGrid)) {
					return true;
				}
				columnIndex = mainGrid[i].indexOf(subGridFirstRow, columnIndex + 1);
			}
		}
		return false;
	}

	private static boolean areGridsSame(String[] grid1, String[] grid2) {
		int gridRowsCount = grid1.length;

		for (int i = 0; i < gridRowsCount; i++) {
			if (!grid1[i].equals(grid2[i])) {
				return false;
			}
		}
		return true;
	}

	private static String[] getSubgridFromGrid(String[] mainGrid, int subGridRows, int subGrodColumns, int startRowIndex, int startColumnIndex) {
		String[] tmp = new String[subGridRows];
		for (int i = 0; i < subGridRows; i++) {
			tmp[i] = mainGrid[startRowIndex + i].substring(startColumnIndex, startColumnIndex + subGrodColumns);
		}
		return tmp;
	}

	private static String[] getMatrixFromInput(Scanner in) {
		int n = in.nextInt();
		int m = in.nextInt();
		String [] tmp = new String [n];
		for (int i = 0; i < n; i++) {
			tmp[i] = in.next();
		}
		return tmp;
	}

}
