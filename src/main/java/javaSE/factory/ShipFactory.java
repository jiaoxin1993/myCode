package javaSE.factory;

public class ShipFactory implements Factory{

	public Vehicle create() {
		Vehicle v = new Ship();
		return v;
	}

}
