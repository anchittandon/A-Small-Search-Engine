interface PageIndexInterface{
	public void addPositionForWord(String str, Position p);
	public MyLinkedList<WordEntry> getWordEntries();
}

public class PageIndex{

	/*
		Stores one word-entry for each unique word in the document
	 */

	Myset<WordEntry> currentWordEntrySet;

	public PageIndex(){
        currentWordEntrySet = new Myset<WordEntry>();
    }

	public void addPositionForWord(String str, Position p){
	//  Add position p to the word-entry of str. 
	//  If a word entry for str is already present in
	//	the page index, then add p to the word entry. 
	//	Otherwise, create a new word-entry for str with 
	//	just one position entry p.

		WordEntry word = new WordEntry(str);

		if (currentWordEntrySet.IsMember(word)){
			MyLinkedList<WordEntry>.Node currentNode = currentWordEntrySet.find(word);
			currentNode.getData().addPosition(p);
		}

		else{
			word.addPosition(p);
			currentWordEntrySet.Insert(word);
		}

	}

	public MyLinkedList<WordEntry> getWordEntries(){
	//	Return a list of all word entries stored in the page index.
		return currentWordEntrySet.myset;
	}
}