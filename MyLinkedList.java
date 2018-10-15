interface MyLinkedListInterface<T>{
	public int getSize();
	public Boolean isEmpty();
	public void add(T data);
	public T remove();
	public String toString();
}

public class MyLinkedList <T> implements MyLinkedListInterface<T> {
	/*
	 Implements Linked List with Node class
	 - remove() removes the first element
	 - add() inserts from the front of the linked list
	 - find() searches for the node containing a data mentioned in searchquery
	*/
	class Node {
		/*
			Implements a Node of MyLinkedList
			- getData() returns the data stored
			- getNext() returns the reference of next Node in the MyLinkedList
			- setNext() sets the reference of next Node in the MyLinkedList
		*/
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

	public MyLinkedList(){
		head = null;
		size = 0;
	}

	public int getSize(){
		return size;
	}

	public Node getHead(){
		return head;
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

	public Node find(T obj){
		// Searched for the Node containing data
		// Returns Node of the found object
		// If not found, returns null
        Node itr = getHead();
        while(itr != null && !obj.equals(itr.getData())){
            itr = itr.getNext();
        }
        return itr;
    }

	public String toString(){
		// For printing out the elements of MyLinkedList
        String linkedListString= "";
        String dataString = "";
        Node itr = getHead();
        while(itr != null){
            dataString = itr.getData().toString();
            if(dataString.equals("") == false){
                linkedListString = linkedListString+", "+dataString;
            }
            itr = itr.getData();
        }
        if(linkedListString.equals("") == true){
            return linkedListString;
        }
        else{
            return linkedListString.substring(2,linkedListString.length());
        }
    }
}