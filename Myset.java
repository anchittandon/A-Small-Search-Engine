interface MysetInterface<T>{
	public Boolean IsEmpty();
	public Boolean IsMember(T o);
	public void Insert(T o);
	public void Delete(T o) throws Exception;
	public Myset<T> Union(Myset<T> a);
	public Myset<T> Intersection(Myset<T> a);
}


public class Myset<T> implements MysetInterface<T>{

	MyLinkedList<T> myset;

	Myset(){
		myset = new MyLinkedList<T>();
	}
	public Boolean IsEmpty(){
		//Returns true if the set is empty
		return myset.isEmpty();
	}
	public Boolean IsMember(T o){
		//O(n)
		//Returns true if o is in the set, false otherwise.
		MyLinkedList<T>.Node itr= myset.getHead();
		while(itr!=null){
			//System.out.println(itr.getData());
			if(itr.getData().equals(o)){
				return true;
			}
			itr = itr.getNext();
		}
		return false;
	}
	public void Insert(T o){
		//O(n)
		// Inserts o into the set
		if(IsMember(o) == false){
			myset.add(o);
		}
	}
	public void Delete(T o) throws NotInSetException{
		//O(n)
		//Deletes o from the set, throws exception if o is not in the set.
		if(IsMember(o) == true){
			if(myset.head!=null && myset.head.getData() == o){
				myset.remove();
			}
			else{
				MyLinkedList<T>.Node prev = myset.head;
				MyLinkedList<T>.Node itr = myset.head.getNext();
				while(itr!=null){
					if(itr.getData().equals(o)){
						prev.next = itr.next;
					}
					prev = itr;
					itr = itr.getNext();
				}				
			}
		}
		else{
			throw new NotInSetException(o+" is not in the set.");
		}
	}

	public Myset<T> Union(Myset<T> a){
		//O(n^2)
		//Returns a set which is the union of the current set with the set a
		Myset<T> answer = new Myset<T>();
		MyLinkedList<T>.Node itr = myset.getHead();
		while(itr!=null){
			answer.Insert(itr.getData());
			itr= itr.getNext();
		}
		itr = a.myset.getHead();
		while(itr!=null){
			answer.Insert(itr.getData());
			itr = itr.getNext();
		}
		return answer;
	}
	public Myset<T> Intersection(Myset<T> a){
		//O(n^2)
		//Returns a set which is the intersection of the current set with the set a
		Myset<T> answer = new Myset<T>();
		MyLinkedList<T>.Node itr = myset.getHead();
		while( itr != null){
			if(a.IsMember(itr.getData()) == true){
				answer.Insert(itr.getData());
			}
			itr = itr.getNext();
		}
		return answer;
	}

	public MyLinkedList<T>.Node find(T a){
		MyLinkedList<T>.Node result = this.myset.find(a);
		return result;
	}
	public String toString(){
        return myset.toString();
    }
}	