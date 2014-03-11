package aplikacja.implementation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CompressionFunction {

	private int[][] pRoundConstant;
	private int[][] qRoundConstant;
	private int[][] him1;
	private int[][] mi;
	private int[][] xoredHM;
	private Map<String, Integer> indexMap;

	public CompressionFunction(byte[] representation256, int[] tabOfMessageBlocks) {
		this.him1 = prepareArray(representation256);
		this.mi = prepareArray(tabOfMessageBlocks);

		indexMap = new HashMap<String, Integer>();
		indexMap.put("0", 0);
		indexMap.put("1", 1);
		indexMap.put("2", 2);
		indexMap.put("3", 3);
		indexMap.put("4", 4);
		indexMap.put("5", 5);
		indexMap.put("6", 6);
		indexMap.put("7", 7);
		indexMap.put("8", 8);
		indexMap.put("9", 9);
		indexMap.put("A", 10);
		indexMap.put("B", 11);
		indexMap.put("C", 12);
		indexMap.put("D", 13);
		indexMap.put("E", 14);
		indexMap.put("F", 15);

		pRoundConstant = new int[8][8];
		qRoundConstant = new int[8][8];

		for (int i = 0; i < pRoundConstant[0].length; i++) {
			pRoundConstant[0][i] = (int) (0xFF & (i * 16));
		}

		for (int i = 0; i < qRoundConstant.length; i++) {
			Arrays.fill(qRoundConstant[i], (int) (0xFF & 255));
		}

		for (int i = 0; i < qRoundConstant[0].length; i++) {
			qRoundConstant[7][i] = (int) (0xFF & (255 - (i * 16)));
		}

		xorHM(this.him1, this.mi);
		pPermutations(xoredHM);
	}

	private int[][] prepareArray(int[] tab) {

		int[][] preparedArray = new int[8][8];
		for (int i = 0; i < tab.length; i++) {
			preparedArray[i % 8][i / 8] = tab[i];
		}

		return preparedArray;
	}

	private int[][] prepareArray(byte[] tab) {

		int[][] preparedArray = new int[8][8];
		for (int i = 0; i < tab.length; i++) {
			preparedArray[i % 8][i / 8] = tab[i];
		}

		return preparedArray;
	}

	public void xorHM(int[][] him1, int[][] mi) {

		/*
		 * System.out.println(); for (int i = 0; i < him1.length; i++) { for
		 * (int j = 0; j < him1[0].length; j++) { System.out.print(him1[i][j] +
		 * "\t"); } System.out.println(); }
		 * 
		 * System.out.println(); for (int i = 0; i < mi.length; i++) { for (int
		 * j = 0; j < mi[0].length; j++) { System.out.print(mi[i][j] + "\t"); }
		 * System.out.println(); }
		 */

		this.xoredHM = new int[8][8];
		for (int i = 0; i < this.xoredHM.length; i++) {
			for (int j = 0; j < this.xoredHM[0].length; j++) {
				this.xoredHM[i][j] = (int) (0xFF & (him1[i][j] ^ mi[i][j]));
			}
		}

		/*
		 * System.out.println(); for (int i = 0; i < xoredHM.length; i++) { for
		 * (int j = 0; j < xoredHM[0].length; j++) {
		 * System.out.print(xoredHM[i][j] + "\t"); } System.out.println(); }
		 */
	}

	public void pPermutations(int[][] xoredHM) {
		int[][] tempTab = new int[8][8];
		// for (int i = 0; i < 10; i++) {
		tempTab = addRoundConstantP(xoredHM, 0);
		System.out.println();
		for (int k = 0; k < 8; k++) {
			for (int l = 0; l < 8; l++) {
				System.out.print(Integer.toHexString(tempTab[k][l] & 0xFF) + " ");
			}
			System.out.println();
		}

		tempTab = subBytes(tempTab);
		System.out.println();
		for (int k = 0; k < 8; k++) {
			for (int l = 0; l < 8; l++) {
				System.out.print(Integer.toHexString(tempTab[k][l] & 0xFF) + " ");
			}
			System.out.println();
		}
		tempTab = shiftBytesP(tempTab);
		System.out.println();
		for (int k = 0; k < 8; k++) {
//			System.out.println(Arrays.toString(tempTab[k]));
			for (int l = 0; l < 8; l++) {
				System.out.print((tempTab[k][l] & 0xFF) + " ");
//				System.out.print(Integer.toHexString(tempTab[k][l] & 0xFF) + " ");
			}
			System.out.println();
		}
		tempTab = mixBytes(tempTab);

		// }
	}

	public void qPermutations(int[][] m) {
		int[][] tempTab = new int[8][8];
		for (int i = 0; i < 10; i++) {
			tempTab = addRoundConstantQ(m, i);

		}
	}

	public int[][] xorAllOutput(int[][] h, int[][] pOutput, int[][] qOutput) {

		int[][] output = new int[8][8];
		for (int i = 0; i < h.length; i++) {
			for (int j = 0; j < h[0].length; j++) {
				output[i][j] = (int) (h[i][j] ^ pOutput[i][j] ^ qOutput[i][j]);
			}
		}
		return output;
	}

	public int[][] subBytes(int[][] tab) {

		// System.out.println(Integer.toHexString(Utility.sBox[5][3] & 0xFF));

		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				String sTemp = String.format("%02X", (tab[i][j] & 0xFF)).toUpperCase();
				String fPart = sTemp.substring(0, 1);
				String sPart = sTemp.substring(1);

				int indexI = indexMap.get(fPart);
				int indexJ = indexMap.get(sPart);

				tab[i][j] = Utility.sBox[indexI][indexJ];
			}
		}

		return tab;
	}

	public int[][] mixBytes(int[][] tempTab) {

		return null;
	}

	public int[][] addRoundConstantP(int[][] tab, int r) {

		int[][] tempPConstTab = pRoundConstant.clone();

		for (int j = 0; j < tempPConstTab[0].length; j++) {
			int con = this.pRoundConstant[0][j];
			int xor = con ^ r;
			tempPConstTab[0][j] = xor;
		}

		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				tab[i][j] = (0xFF & (tempPConstTab[i][j] ^ tab[i][j]));
			}
		}

		return tab;
	}

	public int[][] addRoundConstantQ(int[][] tab, int r) {

		int[][] tempPConstTab = qRoundConstant.clone();

		for (int j = 0; j < tempPConstTab[0].length; j++) {
			int con = this.qRoundConstant[0][j];
			int xor = con ^ r;
			tempPConstTab[0][j] = xor;
		}

		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				tab[i][j] = (0xFF & (tempPConstTab[i][j] ^ tab[i][j]));
			}
		}

		return tab;
	}

	public int[][] shiftBytesP(int[][] tab) {

		int[] c = { 0, 1, 2, 3, 4, 5, 6, 7 };
		int[][] tempTab = new int[8][8];
		for (int i = 0; i < tempTab.length; i++) {
			for (int j = 0; j < tempTab[0].length; j++) {
				int r = j - c[i];
				if (r < 0) {
					tempTab[i][8 + r] = tab[i][j];
				} else {
					tempTab[i][r] = tab[i][j];
				}
			}
		}

		return tempTab;
	}

	public int[][] shiftBytesQ(int[][] tab) {
		int[] c = { 1, 3, 5, 7, 0, 2, 4, 6 };
		int[][] tempTab = new int[8][8];
		for (int i = 0; i < tempTab.length; i++) {
			for (int j = 0; j < tempTab[0].length; j++) {
				int r = j - c[i];
				if (r < 0) {
					tempTab[i][8 + r] = tab[i][j];
				} else {
					tempTab[i][r] = tab[i][j];
				}
			}
		}

		return tempTab;
	}

}
