
package javaSE.iterator;

public class Test {
	public static void main(String[] args) {
		Collection c = new ArrayList();
		for (int i = 0; i < 15; i++) {
			c.add(new Dog());
		}
		
		System.out.println(c.getSize());
		Iterator iterator = c.iterator();
		while(iterator.hasNext()){
			 Dog g = (Dog)iterator.next();
			 System.out.println(g.id);
		}
	}
}	
