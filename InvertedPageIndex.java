interface InvertedPageIndexInterface{
	public void addPage(PageEntry p);
	public Myset<PageEntry> getPagesWhichContainWord(String str);
    public Myset<PageEntry> getPagesWhichContainAllWord(String query[]);
    public Myset<PageEntry> getPagesWhichContainAnyWord(String query[]);
    Myset<PageEntry>getPagesWhichContainPhrase(String str[]);
    public float getInverseDocumentFrequency(String word);
}


public class InvertedPageIndex implements InvertedPageIndexInterface{

    /*
        Maps a word to its set of PageEntries using Hashing.
    */
    private int countPages;
	private MyHashTable InvertedPageIndexHashTable;

	public InvertedPageIndex(){
        InvertedPageIndexHashTable = new MyHashTable();
        countPages = 0;
    }

	public void addPage(PageEntry p){

    // Add a new page entry p to the inverted page index.
        
	    PageIndex pageIndex = p.getPageIndex();
    	MyLinkedList<WordEntry> words = pageIndex.getWordEntries();
		MyLinkedList<WordEntry>.Node itr = words.getHead();
        WordEntry word;
        countPages++;
		
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

    public Myset<PageEntry> getPagesWhichContainAllWord(String query[]){
        int querySize = query.length;
        if(querySize >= 1){
            Myset<PageEntry> targetPages = getPagesWhichContainWord(query[0]);
            if(targetPages!=null){        
                for (int i=1;i<querySize;i++){
                    Myset<PageEntry> newPages = getPagesWhichContainWord(query[i]);
                    if(newPages!=null){
                        targetPages = targetPages.Intersection(newPages);
                    }
                    else{
                        return null;
                    }
                    if(targetPages.IsEmpty()){
                        return null;
                    }
                }
            }
            else{
                return null;
            }
            if(!targetPages.IsEmpty()){
                return targetPages;
            }
        }
        return null;
    }

    public Myset<PageEntry> getPagesWhichContainAnyWord(String query[]){
        int querySize = query.length;
        if(querySize >= 1){
            int queryIndex  = 0;
            boolean initialised = false;
            Myset<PageEntry> targetPages = new Myset<PageEntry>();
            while(queryIndex<querySize){
                if(!initialised){
                    targetPages = getPagesWhichContainWord(query[queryIndex]);
                    if(targetPages!=null){
                        initialised = true;
                    }
                }
                else{
                    Myset<PageEntry> newPages = getPagesWhichContainWord(query[queryIndex]);
                    if(newPages!=null){
                        targetPages = targetPages.Union(newPages);
                    }
                }
                queryIndex++;
            }
            if(!targetPages.IsEmpty()){
                return targetPages;
            }
        }
        return null;
    }


    public Myset<PageEntry> getPagesWhichContainPhrase(String str[]){
        Myset<PageEntry> targetPages = getPagesWhichContainAllWord(str);
        if(targetPages != null){
            MyLinkedList<PageEntry> pages = targetPages.myset;
            MyLinkedList<PageEntry>.Node itr = pages.getHead();
            Myset<PageEntry> validPages = new Myset<PageEntry>();
            while(itr != null){
                if(itr.getData().getPositionsOfPhrase(str) != null){
                    validPages.Insert(itr.getData());
                }
                itr = itr.getNext();
            }
            if(!validPages.IsEmpty()){
                return validPages;
            }
        }
        return null;
    }

    public float getInverseDocumentFrequency(String word){
    // Return the inverse document frequency of the word in a webpage.
        int numberThis = getPagesWhichContainWord(word).myset.getSize();
        if(numberThis == countPages){
            return 1.0f;
        }
        float nw = getPagesWhichContainWord(word).myset.getSize();
        float naturalLog = (float)Math.log(countPages/nw);
        return naturalLog;
    }
}