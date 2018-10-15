interface MyHashTableInterface{
	public void addPositionsForWord(WordEntry w);
}

public class MyHashTable implements MyHashTableInterface{

	/*
		Implements the hashtable used by the InvertedPageIndex. 
		Maps a word to its word-entry.
		The implementation of hashtable supports chaining.
	 */

	int MAX_SIZE = 100003;
	MyLinkedList<WordEntry>[] hashtable;

	@SuppressWarnings("unchecked")
	public MyHashTable(){
		hashtable = new MyLinkedList[MAX_SIZE];
	}

	private int getHashIndex(String str){
		
	/*
		A hash function which maps a string to the index 
		of its word-entry in the hashtable. 
	*/

		long index = 0;
		int multiplier = 1;
		int strLength = str.length();
		int val;

		for(int i=0;i<strLength;i++){

			if(Character.isLetter(str.charAt(i))){

				val = ((int)str.charAt(i) - 'a')+1;
				index += (val*multiplier)%MAX_SIZE;

				index%=MAX_SIZE;
				multiplier*=27;
				multiplier%=MAX_SIZE;

			}

		}

		return Math.toIntExact(index);

	}

	public void addPositionsForWord(WordEntry w){

	/*
		Adds an entry to the hashtable: stringName(w) − > positionList(w). 
		If no wordentry exists, then it creates a new word entry. 
		If a wordentry exists, then it merges w with the existing word-entry.
	*/

		int index = getHashIndex(w.toString());

		if (hashtable[index] == null){
            hashtable[index] = new MyLinkedList<WordEntry>();
            hashtable[index].add(w);
        }

        else{

            try{
            	MyLinkedList<WordEntry>.Node node = hashtable[index].find(w);
                WordEntry word = node.getData();
                word.addPositions(w.getAllPositionsForThisWord());
            }

            catch(NullPointerException e){
                hashtable[index].add(w);
            }

        }

	}

	public WordEntry FindWord(String str){

	/*
		Searches for the the word in the hashtable.
		Returns the corresponding WordEntry if found.
		Otherwise returns null.
	*/

		int index = getHashIndex(str);

		if(hashtable[index] != null){

            try{
            	MyLinkedList<WordEntry>.Node node = hashtable[index].find(new WordEntry(str));
                WordEntry word = node.getData();
                return word;
            }

            catch(NullPointerException e){
            }

        }

        return null;
	}

}