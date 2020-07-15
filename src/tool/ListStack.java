package tool;

import shape.GShape;

public class ListStack {
	private Node top;
	
	private class Node{
		
		private GShape shape;
		private Node nextNode;
		
		Node(GShape shape){
			this.shape = shape;
			this.nextNode = null;
		}
		
	}
	public ListStack() {
		this.top = null;
		
	}
	public boolean isEmpty() {
		return (this.top == null);
	}
	
	public void push(GShape shape) {
		Node newNode = new Node(shape);
		newNode.nextNode = top;
		this.top = newNode;
	}
	
	public GShape peek() {
		if(isEmpty()) throw new ArrayIndexOutOfBoundsException();
		return this.top.shape;
	}
	
	public GShape pop() {
		
		GShape shape = peek();
		top = top.nextNode;
		return shape;
	}
}
