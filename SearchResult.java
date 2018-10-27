interface SearchResultInterface{
    public PageEntry getPageEntry();
    public float getRelevance();
    public int compareTo(SearchResult obj);
}

public class SearchResult implements Comparable<SearchResult>,SearchResultInterface{

    private PageEntry page;
    private float relevance;

    public SearchResult(PageEntry p, float r){
        page = p;
        relevance = r;
    }

    public PageEntry getPageEntry(){
    // Returns the page of the Search Result
        return page;
    }

    public float getRelevance(){
    // Returns the relevance of the result
        return relevance;
    }

    public int compareTo(SearchResult other){
    // Gives the ordering between the current object and the "other" Object
    	if( relevance > other.getRelevance() ){
    		return 1;
    	}
    	else if( relevance < other.getRelevance() ){
    		return -1;
    	}
        else{
        	return 0;
		}
    }

    public String toString(){
    // Represents the data of the search result
    	String result = page.toString();//+" "+relevance;
        return result;
    }
}