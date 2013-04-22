package aplikacja.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.xml.bind.DatatypeConverter;

import aplikacja.gui.Gui;
import aplikacja.implementation.Pad;
import aplikacja.implementation.ToByteArray;

public class ActionListenerBtn_Wykonaj implements ActionListener {

	private Gui gui;
	private String inputHEX;
	private long hex;
	private byte[] hexByteArray;
	private Pad pad;

	public ActionListenerBtn_Wykonaj(Gui gui) {
		this.gui = gui;
	}

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		inputHEX = gui.textField_Input.getText();
//		hex = Long.decode(inputHEX);
		
		ToByteArray toByte = new ToByteArray();
		try{
			
			hexByteArray = toByte.toByteArray(inputHEX);

		}catch(Exception e){
			System.out.println("Podaj parzyst¹ liczbê znaków");
		}
		
		pad = new Pad(hexByteArray);
		pad.printPlainByteArray();
		pad.append1Bit();
		pad.printPlainByteArray1();
		pad.CalculateW();
		pad.CalculateZ();
		
	}


}
