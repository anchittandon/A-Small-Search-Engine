interface PageIndexInterface{
	void addPositionForWord(String str, Position p);
	MyLinkedList<WordEntry> getWordEntries();
}

public class PageIndex{

	Myset<WordEntry> currentWordEntryList;
	PageIndex()
    {
        currentWordEntryList = new Myset<WordEntry>();
    }
	void addPositionForWord(String str, Position p){
		WordEntry node;
		this.currentWordEntryList.Insert(node)

	}
	MyLinkedList<WordEntry> getWordEntries(){

	}
}