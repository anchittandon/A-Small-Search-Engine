interface MyLinkedListInterface<T>{
	public int getSize()
	public Boolean isEmpty()
	public void add(T data)
	public T remove()
	public String toString()
}

public class MyLinkedList <T> implements MyLinkedListInterface<T> {
	/*
	 *Implements Linked List with Node class
	 *remove() removes the first element
	 *add() inserts from the front of the linked list
	*/
	class Node {
		T data;
		Node next;
		Node(T d){
			this.data = d;
			this.next = null;
		}
		public T getData(){
			return data;
		}
		public Node getNext(){
			return next;
		}
		public void setNext(Node n){
			next = n; 
		}
	}

	Node head;
	int size;

	MyLinkedList(){
		head = null;
		size = 0;
	}

	public int getSize(){
		return size;
	}

	public Boolean isEmpty(){
		return size==0;
	}

	public void add(T data)	{
		//Add the element at the head of the Linked List
		Node nextNode = new Node(data);
		nextNode.setNext(head);
		head = nextNode;
		size+=1;
	}

	public T remove(){
		// Removes the element at the head of the Linked List
		if(this.isEmpty()){
			return null;
		}
		T returnNode = head.getData();
		head = head.getNext();
		size--;
		return returnNode;
	}

	public String toString(){
        String linkedListString= "";
        String dataString = "";
        Node itr = head;
        while(itr != null){
            dataString = itr.data.toString();
            if(dataString.equals("") == false){
                linkedListString = dataString+", "+linkedListString;
            }
            itr = itr.next;
        }
        if(linkedListString.equals("") == true){
            return linkedListString;
        }
        else{
            return linkedListString.substring(0,linkedListString.length()-2);
        }
    }
}