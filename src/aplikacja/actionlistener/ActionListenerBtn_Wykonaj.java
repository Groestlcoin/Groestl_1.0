package aplikacja.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import aplikacja.gui.Gui;
import aplikacja.implementation.CompressionFunction;
import aplikacja.implementation.Pad;
import aplikacja.implementation.ToByteArray;
import aplikacja.implementation.Utility;

public class ActionListenerBtn_Wykonaj implements ActionListener {

	private Gui gui;
	private String inputHEX;
	private byte[] hexByteArray;
	private Pad pad;
	private byte[][] tabOfMessageBlocks;

	public ActionListenerBtn_Wykonaj(Gui gui) {
		this.gui = gui;
	}

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent arg0) {

		inputHEX = gui.textField_Input.getText();
		// hex = Long.decode(inputHEX);

		ToByteArray toByte = new ToByteArray();
		try {

			hexByteArray = toByte.toByteArray(inputHEX);

		} catch (Exception e) {
			System.out.println("Podaj parzyst¹ liczbê znaków");
		}

		pad = new Pad(hexByteArray);
		pad.printPlainByteArray();
		pad.append1Bit();
		pad.printPlainByteArray1();
		pad.calculateW();
		pad.calculateP();
		pad.add64bitRepresentationOfP();

		byte[] paddedArrayOfBlocks = pad.getPaddedArrayOfBytes();
		int numberOfBlocks = pad.getP();

		this.tabOfMessageBlocks = new byte[numberOfBlocks][64];
		for (int i = 0, j = 0; i < paddedArrayOfBlocks.length; i++, j++) {
			if (j == 64) {
				j = 0;
			}
			int blockNumber = i / 64;
			this.tabOfMessageBlocks[blockNumber][j] = paddedArrayOfBlocks[i];
		}
		for (int i = 0; i < tabOfMessageBlocks.length; i++) {
			for (int j = 0; j < tabOfMessageBlocks[0].length; j++) {
				System.out.print(tabOfMessageBlocks[i][j] + " ");
			}
			System.out.println();
		}
		
		CompressionFunction co = new CompressionFunction(tabOfMessageBlocks[0], Utility.representation256);
		co.xorHM();

	}

}
