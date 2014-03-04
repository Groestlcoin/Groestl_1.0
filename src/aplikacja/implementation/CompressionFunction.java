package aplikacja.implementation;

public class CompressionFunction {

	public CompressionFunction(byte[] him1, byte[] mi) {
		this.him1 = prepareArray(him1);
		this.mi = prepareArray(mi);
	}

	private byte[][] prepareArray(byte[] tab) {

		byte[][] preparedArray = new byte[8][8];
		for (int i = 0; i < tab.length; i++) {
			preparedArray[i % 8][i / 8] = tab[i];
		}

		return preparedArray;
	}

	private byte[][] him1;
	private byte[][] mi;
	private byte[][] xoredHM;

	public void xorHM() {
		
		System.out.println();
		for (int i = 0; i < him1.length; i++) {
			for (int j = 0; j < him1[0].length; j++) {
				System.out.print(him1[i][j] + "\t");
			}
			System.out.println();
		}
		
		System.out.println();
		for (int i = 0; i < mi.length; i++) {
			for (int j = 0; j < mi[0].length; j++) {
				System.out.print(mi[i][j] + "\t");
			}
			System.out.println();
		}
		
		xoredHM = new byte[8][8];
		for (int i = 0; i < xoredHM.length; i++) {
			for (int j = 0; j < xoredHM[0].length; j++) {
				xoredHM[i][j] = (byte) (him1[i][j] ^ mi[i][j]);
			}
		}
		
		System.out.println();
		for (int i = 0; i < xoredHM.length; i++) {
			for (int j = 0; j < xoredHM[0].length; j++) {
				System.out.print(xoredHM[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public void P() {

	}

	public void Q() {

	}

	public void xorAllOutput() {

	}

	public byte[] subBytes() {
		return null;
	}

	public byte[] mixBytes() {
		return null;
	}

	public byte[] addRoundConstantP() {
		return null;
	}

	public byte[] addRoundConstantQ() {
		return null;
	}

	public byte[] shiftBytesP() {
		return null;
	}

	public byte[] shiftBytesQ() {
		return null;
	}

}
