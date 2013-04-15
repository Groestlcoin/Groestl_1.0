package aplikacja.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.xml.bind.DatatypeConverter;

import aplikacja.gui.Gui;
import aplikacja.implementation.Pad;

public class ActionListenerBtn_Wykonaj implements ActionListener {

	private Gui gui;
	private String inputHEX;
	private long hex;
	private byte[] hexByteArray;
	private Pad pad;

	public ActionListenerBtn_Wykonaj(Gui gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		inputHEX = gui.textField_Input.getText();
//		hex = Long.decode(inputHEX);
		
		try{
			
			hexByteArray = toByteArray(inputHEX);

		}catch(Exception e){
			System.out.println("Podaj parzyst¹ liczbê znaków");
		}
		
		pad = new Pad(hexByteArray);
		
	}

	public static byte[] toByteArray(String s) {
	    return DatatypeConverter.parseHexBinary(s);
	}

}
