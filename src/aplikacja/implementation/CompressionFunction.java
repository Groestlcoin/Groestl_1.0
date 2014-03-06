package aplikacja.implementation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CompressionFunction {

	private byte[][] pRoundConstant;
	private byte[][] qRoundConstant;
	private byte[][] him1;
	private byte[][] mi;
	private byte[][] xoredHM;
	private Map<String, Integer> indexMap;

	public CompressionFunction(byte[] h, byte[] m) {
		this.him1 = prepareArray(h);
		this.mi = prepareArray(m);

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

		pRoundConstant = new byte[8][8];
		qRoundConstant = new byte[8][8];

		for (int i = 0; i < pRoundConstant[0].length; i++) {
			pRoundConstant[0][i] = (byte) (0xFF & (i * 16));
		}

		for (int i = 0; i < qRoundConstant.length; i++) {
			Arrays.fill(qRoundConstant[i], (byte) (0xFF & 255));
		}

		for (int i = 0; i < qRoundConstant[0].length; i++) {
			qRoundConstant[7][i] = (byte) (0xFF & (255 - (i * 16)));
		}

		xorHM(this.him1, this.mi);
		pPermutations(xoredHM);
	}

	private byte[][] prepareArray(byte[] tab) {

		byte[][] preparedArray = new byte[8][8];
		for (int i = 0; i < tab.length; i++) {
			preparedArray[i % 8][i / 8] = tab[i];
		}

		return preparedArray;
	}

	public void xorHM(byte[][] him1, byte[][] mi) {

		/*
		 * System.out.println(); for (int i = 0; i < him1.length; i++) { for
		 * (int j = 0; j < him1[0].length; j++) { System.out.print(him1[i][j] +
		 * "\t"); } System.out.println(); }
		 * 
		 * System.out.println(); for (int i = 0; i < mi.length; i++) { for (int
		 * j = 0; j < mi[0].length; j++) { System.out.print(mi[i][j] + "\t"); }
		 * System.out.println(); }
		 */

		this.xoredHM = new byte[8][8];
		for (int i = 0; i < this.xoredHM.length; i++) {
			for (int j = 0; j < this.xoredHM[0].length; j++) {
				this.xoredHM[i][j] = (byte) (0xFF & (him1[i][j] ^ mi[i][j]));
			}
		}

		/*
		 * System.out.println(); for (int i = 0; i < xoredHM.length; i++) { for
		 * (int j = 0; j < xoredHM[0].length; j++) {
		 * System.out.print(xoredHM[i][j] + "\t"); } System.out.println(); }
		 */
	}

	public void pPermutations(byte[][] xoredHM) {
		byte[][] tempTab = new byte[8][8];
		System.out.println("1:");
		for (int k = 0; k < tempTab.length; k++) {
			System.out.println(Arrays.toString(tempTab[k]));
		}
		// for (int i = 0; i < 10; i++) {
		tempTab = addRoundConstantP(xoredHM, 0);
		System.out.println("2:");
		for (int k = 0; k < tempTab.length; k++) {
			System.out.println(Arrays.toString(tempTab[k]));
		}
		tempTab = subBytes(tempTab);
		// }
	}

	public void qPermutations(byte[][] m) {
		byte[][] tempTab = new byte[8][8];
		for (int i = 0; i < 10; i++) {
			tempTab = addRoundConstantQ(m, i);

		}
	}

	public byte[][] xorAllOutput(byte[][] h, byte[][] pOutput, byte[][] qOutput) {

		byte[][] output = new byte[8][8];
		for (int i = 0; i < h.length; i++) {
			for (int j = 0; j < h[0].length; j++) {
				output[i][j] = (byte) (h[i][j] ^ pOutput[i][j] ^ qOutput[i][j]);
			}
		}
		return output;
	}

	public byte[][] subBytes(byte[][] tab) {

		// System.out.println(Integer.toHexString(Utility.sBox[5][3] & 0xFF));

		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				String sTemp = Integer.toHexString(tab[i][j] & 0xFF);
				System.out.println(sTemp);
			}
		}

		return null;
	}

	public byte[][] mixBytes() {
		return null;
	}

	public byte[][] addRoundConstantP(byte[][] tab, int r) {

		for (int j = 0; j < tab[0].length; j++) {
			tab[0][j] = (byte)(0xFF &  (this.pRoundConstant[0][j] ^ r));
		}

		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				tab[i][j] = (byte)(0xFF &  (tab[i][j] ^ pRoundConstant[i][j]));
			}
		}

		return tab;
	}

	public byte[][] addRoundConstantQ(byte[][] tab, int r) {

		for (int j = 0; j < tab[0].length; j++) {
			tab[7][j] = (byte) (this.qRoundConstant[7][j] ^ r);
		}

		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				tab[i][j] = (byte) (tab[i][j] ^ qRoundConstant[i][j]);
			}
		}

		return tab;
	}

	public byte[][] shiftBytesP() {
		return null;
	}

	public byte[][] shiftBytesQ() {
		return null;
	}

}
