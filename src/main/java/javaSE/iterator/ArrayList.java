package javaSE.iterator;

public class ArrayList implements Collection{
	Object[] objects = new Object[10];
	int index = 0;//当前数组已使用的下标
	public void add(Object o){
		if(objects.length <= index){
			int size = objects.length*2;
			Object[] newObject = new Object[size];
			System.arraycopy(objects, 0, newObject, 0, index);
			newObject[index] = o;
			index++;
			objects = newObject;

		}else{
			objects[index] = o;
			index++;
		}
	}
	public int getSize(){
		return index;
	}
	public Iterator iterator(){
		return new ArrayListIterator();

	}
	class ArrayListIterator implements Iterator{
		int currentIndex = 0;
		public boolean hasNext() {
			if(currentIndex >= index){
				return false;
			}else{
				return true;
			}
		}

		public Object next() {
			Object o = objects[currentIndex];
			currentIndex++;
			return o;
		}

	}
}
