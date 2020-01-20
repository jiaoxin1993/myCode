package javaSE.iterator;

public class Node {
	private Object data;
	private Node nextNode;
	public Node getNextNode() {
		return nextNode;
	}
	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
	public Node(Object data,Node node) {
		this.setData(data);
		nextNode = node;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
