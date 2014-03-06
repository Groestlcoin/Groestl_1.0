package aplikacja.implementation;

public class HashFunction {

	private byte[] hexbyteArray;
	private Pad pad;
	private int[][] tabOfMessageBlocks;

	public void calculateHash(String inputHEX) {
		System.out.println(this.getClass().getCanonicalName()+ ":\t" + inputHEX);
		try {
			hexbyteArray = ToByteArray.toByteArray(inputHEX);
		} catch (Exception e) {
			System.out.println("Podaj parzyst¹ liczbê znaków");
		}

		pad = new Pad(hexbyteArray);
		pad.printPlainByteArray();
		pad.append1Bit();
		pad.printPlainByteArray1();
		pad.calculateW();
		pad.calculateP();
		pad.add64bitRepresentationOfP();

		byte[] paddedArrayOfBlocks = pad.getPaddedArrayOfBytes();
		int numberOfBlocks = pad.getP();

		this.tabOfMessageBlocks = new int[numberOfBlocks][64];
		for (int i = 0, j = 0; i < paddedArrayOfBlocks.length; i++, j++) {
			if (j == 64) {
				j = 0;
			}
			int blockNumber = i / 64;
			this.tabOfMessageBlocks[blockNumber][j] = paddedArrayOfBlocks[i];
		}
		/*
		 * for (int i = 0; i < tabOfMessageBlocks.length; i++) { for (int j = 0;
		 * j < tabOfMessageBlocks[0].length; j++) {
		 * System.out.print(tabOfMessageBlocks[i][j] + " "); }
		 * System.out.println(); }
		 */

		new CompressionFunction(Utility.representation256, tabOfMessageBlocks[0]);

	}

}
