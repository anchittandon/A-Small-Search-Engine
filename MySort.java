import java.util.*;

interface MySortInterface<Sortable>{
	public ArrayList<Sortable> sortThisList( Myset<Sortable> listOfSortableEntries);
	public void mergeSort(ArrayList<Sortable> whole);
}

public class MySort<Sortable extends Comparable<Sortable>> implements MySortInterface<Sortable>{

    public MySort(){

    }

    public ArrayList<Sortable> sortThisList( Myset<Sortable> listOfSortableEntries ) {
        ArrayList<Sortable> list = MySet2ArrayList(listOfSortableEntries);
        mergeSort(list);
        return list;
    
    }

    public ArrayList<Sortable> MySet2ArrayList(Myset<Sortable> listOfSortableEntries){
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
 
    public void merge(ArrayList<Sortable> left, ArrayList<Sortable> right, ArrayList<Sortable> whole) {
        int leftIndex = 0;
        int rightIndex = 0;
        int wholeIndex = 0;
 
        // As long as neither the left nor the right ArrayList has
        // been used up, keep taking the smaller of left.get(leftIndex)
        // or right.get(rightIndex) and adding it at both.get(bothIndex).
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
            // The left ArrayList has been use up...
            rest = right;
            restIndex = rightIndex;
        } else {
            // The right ArrayList has been used up...
            rest = left;
            restIndex = leftIndex;
        }
 
        // Copy the rest of whichever ArrayList (left or right) was not used up.
        for (int i=restIndex; i<rest.size(); i++) {
            whole.set(wholeIndex, rest.get(i));
            wholeIndex++;
        }
    }
 	public static void main(String[] args){
        ArrayList<Integer> array = new ArrayList<Integer>();
        // int[] arr = new int[]{1,45,67,21,12,3425,231,45,99,12,45,1,3251,564,23,1,4,12,67};
        int[] arr = new int[]{1,2,3,4,3,4,1,5,6,4,7,8,9};
        float[] arr1 = new float[]{(float)9.077,(float)4.207,(float)6.165};
        int len = arr.length;
        for(int i=0;i<len;i++){
            array.add(arr[i]);
        }
        MySort<Integer> mysort = new MySort<Integer>();
        mysort.mergeSort(array);
        for(int i=0;i<len;i++){
            System.out.print(array.get(i)+" ");
        }
        System.out.println("");
    }
}