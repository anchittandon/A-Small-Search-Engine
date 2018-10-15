interface InvertedPageIndexInterface{
	public void addPage(PageEntry p);
	public Myset<PageEntry> getPagesWhichContainWord(String str);
}


public class InvertedPageIndex implements InvertedPageIndexInterface{

    /*
        Maps a word to its set of PageEntries using Hashing.
     */

	MyHashTable InvertedPageIndexHashTable;

	public InvertedPageIndex(){
        InvertedPageIndexHashTable = new MyHashTable();
    }

	public void addPage(PageEntry p){

    // Add a new page entry p to the inverted page index.
        
	    PageIndex pageIndex = p.getPageIndex();
    	MyLinkedList<WordEntry> words = pageIndex.getWordEntries();
		MyLinkedList<WordEntry>.Node itr = words.getHead();
        WordEntry word;

		while(itr != null){
            word = itr.getData();
            InvertedPageIndexHashTable.addPositionsForWord(word);
            itr = itr.getNext();
        }

	}

	public Myset<PageEntry> getPagesWhichContainWord(String str){
    
    // Returns a set of page-entries of webpages which contain the word.

		WordEntry word = InvertedPageIndexHashTable.FindWord(str);
        
        if(word == null){
            return null;
        }

        Position position;
        MyLinkedList<Position> positonList = word.getAllPositionsForThisWord();
        Myset<PageEntry> pageSet = new Myset<PageEntry>();
        MyLinkedList<Position>.Node itr = positonList.getHead();
        PageEntry page;
        
        while(itr != null){
            position = itr.getData();
            page = position.getPageEntry();
            pageSet.Insert(page);
            itr = itr.getNext();
        }

        return pageSet;
	}

}