package aplikacja.implementation;

import java.util.Arrays;

public class HashFunction {

	private byte[] hexbyteArray;
	private Pad pad;
	private int[][] tabOfMessageBlocks;
	private CompressionFunction comp;

	public void calculateHash(String inputHEX) {
		System.out.println(this.getClass().getCanonicalName() + ":\t" + inputHEX);
		try {
			hexbyteArray = ToByteArray.toByteArray(inputHEX);
		} catch (Exception e) {
			System.out.println("Podaj parzyst¹ liczbê znaków");
		}

		pad = new Pad(hexbyteArray);
		// pad.printPlainByteArray();
		pad.append1Bit();
		// pad.printPlainByteArray1();
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

		comp = new CompressionFunction(Utility.representation256, tabOfMessageBlocks[0]);

	}

	public String getHash() {

		// String hash = new String(comp.getBlockState());
		String hash = toByteString(comp.getBlockState());

		return hash;
	}

	private String toByteString(byte[] blockState) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < blockState.length; i++) {
			sb.append(blockState[i] & 0xFF);
		}

		return sb.toString();
	}

}
