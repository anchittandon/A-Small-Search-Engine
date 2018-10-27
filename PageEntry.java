import java.util.*;
interface PageEntryInterface{
	public PageIndex getPageIndex();
	public String getPageName();
    public int getWordCount();
    public void setWordCount(int n);
    public float getRelevanceOfPage(String str[], boolean doTheseWordsRepresentAPhrase);
    public int[] getPositionsOfPhrase(String phrase[]);
}

public class PageEntry implements PageEntryInterface{

	/*
		Stores the information related to a webpage.	 
	 */
	
	private String pageName;
	private PageIndex pageIndex;
    private int wordCount;

	public PageEntry(String pageName){
		this.pageName = pageName;
        this.pageIndex = new PageIndex();
	}

	public PageIndex getPageIndex(){
	// Returns the page index of this web-page.
		return this.pageIndex;
	}

    public int getWordCount(){
        return wordCount;
    }

    public void setWordCount(int n){
        wordCount = n;
    }

	public String getPageName(){
	// Returns the page name of this web-page.
		return this.pageName;
	}

	public boolean equals(Object obj){
	// Two PageEntry Objects are equal if they have the same page name.
        return this.pageName.equals(((PageEntry)obj).getPageName());
    }

    public String toString(){
        return pageName;
    }

    public float getRelevanceOfPage(String str[], boolean doTheseWordsRepresentAPhrase){
        int numWords = str.length;
        float relevance = 0;
        if(doTheseWordsRepresentAPhrase){
            int[] positions = getPositionsOfPhrase(str);
            PageIndex pageIndexOfPage = this.getPageIndex();
            MyLinkedList<WordEntry> wordEntriesOfPage = pageIndexOfPage.getWordEntries();
            WordEntry targetWord =  wordEntriesOfPage.find(new WordEntry(str[0])).getData();            
            MyLinkedList<Position>.Node itr = targetWord.getAllPositionsForThisWord().getHead();
            float numberThis = 0;
            float totalcount = this.getWordCount();
            float inverseDocumentFrequency = SearchEngine.getInvertedPageIndex().getInverseDocumentFrequency(targetWord.getWord());
            MyLinkedList<WordEntry>.Node iter = wordEntriesOfPage.getHead();
            while(iter!=null){
                if(iter.getData().getWord() == targetWord.getWord()){
                    numberThis = numberThis + 1.0f;
                }
                iter = iter.getNext();
            }
            relevance = (numberThis/(totalcount - (numWords-1)*numberThis ))*inverseDocumentFrequency;
        }
        else{
            MyLinkedList<WordEntry> words = pageIndex.getWordEntries();
            
            if(numWords == 1){
                PageIndex pageIndexOfPage = this.getPageIndex();
                MyLinkedList<WordEntry> wordEntriesOfPage = pageIndexOfPage.getWordEntries();
                WordEntry targetWord =  wordEntriesOfPage.find(new WordEntry(str[0])).getData();
              

                float termFrequency = targetWord.getTermFrequency(getPageName());
                float inverseDocumentFrequency = SearchEngine.getInvertedPageIndex().getInverseDocumentFrequency(targetWord.getWord());
                
                // System.out.println(this+" "+targetWord.getWord()+" "+termFrequency+" "+inverseDocumentFrequency);
                relevance = termFrequency*inverseDocumentFrequency;
                //  System.out.println(targetWord.getWord()+" "+termFrequency+" "+inverseDocumentFrequency+" "+relevance);
                return relevance;
            }
            for(int i=0;i<numWords;i++){
                try{
                    MyLinkedList<Position>.Node itr = words.find(new WordEntry(str[i])).data.getAllPositionsForThisWord().getHead();
                    String[] str1 = new String[1];
                    while(itr != null){
                        str1[0] = str[i];
                        relevance += getRelevanceOfPage(str1,false);
                        itr = itr.getNext();
                    }
                }
                catch(NullPointerException e){}
            }
        }
        return relevance;
    }

    @SuppressWarnings("unchecked")
    public int[] getPositionsOfPhrase(String phrase[]){

        Myset<Integer> answer = new Myset<Integer>();
        boolean isPhrase = true;
        int numWords = phrase.length;
        MyLinkedList<WordEntry> words = pageIndex.getWordEntries();
        AVL<Position>[] phraseWords = new AVL[numWords-1];
        WordEntry firstword =  words.find(new WordEntry(phrase[0])).getData();
        MyLinkedList<Position> firstWordPos = firstword.getAllPositionsForThisWord();
        MyLinkedList<Position>.Node iter = firstWordPos.getHead();
        for(int i=1;i<numWords;i++){
            WordEntry targetWord = words.find(new WordEntry(phrase[i])).getData();
            phraseWords[i-1] = targetWord.getPositions();
        }
        while(iter != null){
            isPhrase = true;
            int current_pos = iter.getData().getWordIndex();
            for(int i=1;i<numWords;i++){
                Position newPos = new Position(null,current_pos+i);
                if(phraseWords[i-1].search(newPos) == null){
                    isPhrase = false;
                    break;
                }
            }
            if(isPhrase){
                answer.Insert(iter.getData().getWordIndex());
            }
            iter = iter.getNext();
        }
        if(!answer.IsEmpty()){
            MyLinkedList<Integer> list = answer.myset;
            int[] ret = new int[list.getSize()];
            MyLinkedList<Integer>.Node itr = list.getHead();
            for(int i = 0 ; i < list.getSize() ; i++ ){
                ret[i] = itr.getData();
            }
            return ret;
        }
        else{
            return null;
        }
    }

}