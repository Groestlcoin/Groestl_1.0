package aplikacja.implementation;

import javax.xml.bind.DatatypeConverter;

public class ToByteArray {
	
	public static byte[] toByteArray(String s) {
	    return DatatypeConverter.parseHexBinary(s);
	}

}
