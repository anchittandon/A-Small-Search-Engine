import java.util.*;

interface MySortInterface<Sortable>{
	public ArrayList<Sortable> sortThisList( Myset<Sortable> listOfSortableEntries);
	public void mergeSort(ArrayList<Sortable> whole);
}

public class MySort<Sortable extends Comparable<Sortable>> implements MySortInterface<Sortable>{

    //  To sort a list of Comparable objects

    public MySort(){
    // Empty Constructor
    }

    public ArrayList<Sortable> sortThisList( Myset<Sortable> listOfSortableEntries ) {
    // Given a set of Sortable objects, this method returns a sorted list of objects.
        ArrayList<Sortable> list = MySet2ArrayList(listOfSortableEntries);
        mergeSort(list);
        return list;
    
    }

    public ArrayList<Sortable> MySet2ArrayList(Myset<Sortable> listOfSortableEntries){
    // Shifts elements from Myset to an ArrayList
    	MyLinkedList<Sortable> entrySet = listOfSortableEntries.myset;
        MyLinkedList<Sortable>.Node itr = entrySet.getHead();
        ArrayList <Sortable> answer = new ArrayList <Sortable>();
        while(itr != null){
            answer.add(itr.getData());
            itr = itr.getNext();
        }
        return answer;
    }

    public void mergeSort(ArrayList<Sortable> whole) {
    // Merge sort 
        ArrayList<Sortable> left = new ArrayList<Sortable>();
        ArrayList<Sortable> right = new ArrayList<Sortable>();
        int center;
 
        if (whole.size() == 1) {    
            return ;
        } 
        else {
            center = whole.size()/2;
            // copy the left half of whole into the left.
            for (int i=0; i<center; i++) {
                    left.add(whole.get(i));
            }
 
            //copy the right half of whole into the new arraylist.
            for (int i=center; i<whole.size(); i++) {
                    right.add(whole.get(i));
            }
 
            // Sort the left and right halves of the arraylist.
            mergeSort(left);
            mergeSort(right);
 
            // Merge the results back together.
            merge(left, right, whole);
        }
        return ;
    }
 
    private void merge(ArrayList<Sortable> left, ArrayList<Sortable> right, ArrayList<Sortable> whole) {
    // Merge function    
        int leftIndex = 0;
        int rightIndex = 0;
        int wholeIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if ( (left.get(leftIndex).compareTo(right.get(rightIndex))) > 0) {
                whole.set(wholeIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                whole.set(wholeIndex, right.get(rightIndex));
                rightIndex++;
            }
            wholeIndex++;
        }
 
        ArrayList<Sortable> rest;
        int restIndex;
        if (leftIndex >= left.size()) {
            rest = right;
            restIndex = rightIndex;
        } 
        else {
            rest = left;
            restIndex = leftIndex;
        }
        for (int i=restIndex; i<rest.size(); i++) {
            whole.set(wholeIndex, rest.get(i));
            wholeIndex++;
        }
    }

}