package aplikacja.implementation;

public class Pad {

	private byte[] hexByteArray;
	
	private int w;

	private byte[] hexByteArrayAppended;

	private int z;

	public Pad(byte[] hexByteArray) {
		this.hexByteArray = hexByteArray;
	}

	public void printPlainByteArray() {
		for (int i = 0; i < hexByteArray.length; i++) {
			System.out.print(hexByteArray[i] + " ");
		}
		System.out.println();
		System.out.println(this.getClass().getCanonicalName()
				+ "\thexByteArray.length: " + hexByteArray.length);
	}

	public void append1Bit() {
		ToByteArray toByte = new ToByteArray();

		byte[] tempByteArray = new byte[this.hexByteArray.length + 1];
		for (int i = 0; i < this.hexByteArray.length; i++) {
			tempByteArray[i] = this.hexByteArray[i];
		}
		tempByteArray[tempByteArray.length - 1] = toByte.toByteArray("80")[0];
		this.hexByteArrayAppended = tempByteArray;
	}
	
	public void printPlainByteArray1() {
		for (int i = 0; i < hexByteArrayAppended.length; i++) {
			System.out.print(hexByteArrayAppended[i] + " ");
		}
		System.out.println();
		System.out.println(this.getClass().getCanonicalName()
				+ "\thexByteArrayAppended.length: " + hexByteArrayAppended.length);
	}
	
	public void CalculateW(){
		this.w = (0 - this.hexByteArray.length * 8) + (447);
		System.out.println("w: " + w);
	}
	
	public void CalculateZ(){
//		System.out.println(this.hexByteArray.length + " " + this.w);
		this.z = ((this.hexByteArray.length * 8) + this.w + 65)/512;
		System.out.println("z: " + z);
	}
	
	

}
