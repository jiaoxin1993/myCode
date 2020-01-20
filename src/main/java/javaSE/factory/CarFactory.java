package javaSE.factory;

public class CarFactory implements Factory{

	public Vehicle create() {
		Vehicle v = new Car();
		return v;
	}

}
