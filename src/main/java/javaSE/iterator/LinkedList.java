package javaSE.iterator;

public class LinkedList implements Collection{
	int size = 0;
	Node head = null;
	Node end = null;
	public void add(Object o){
		Node node = new Node(o,null);
		if(head==null){
			head = node;
			end = node;
		}else{
			end.setNextNode(node);
			end = node;
		}
		size++;
		
	}
	public int getSize(){
		return size;
	}
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return new LinkedListIterator();
	}
	class LinkedListIterator implements Iterator{
		int currentIndex = 0;
		public boolean hasNext() {
			if(currentIndex >= size){
				return false;
			}else{
				return true;
			}
		}

		public Object next() {
			Node node = head;
			for (int i = 0; i < currentIndex; i++) {
				Node n = node.getNextNode();
				node = n;
			}
			currentIndex++;
			return node.getData();
		}
		
	}
}
