package javaSE.factory;

public class Test {
	public static void main(String[] args) {
		Factory factory = new ShipFactory();
		Vehicle v =  factory.create();
		v.run();
	}
}
