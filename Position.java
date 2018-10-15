interface PositionInterface{
	public PageEntry getPageEntry();
	public int getWordIndex();
}

public class Position implements PositionInterface {
	
	PageEntry page;
	int wordposition;

	public Position(PageEntry p, int wordIndex){
		page = p;
		this.wordposition = wordIndex;
	}

	public PageEntry getPageEntry(){
		return page;
	}

	public int getWordIndex(){
		return wordposition;
	}

	public boolean equals(Object obj){
        return this.page.equals(((Position)obj).page) && this.wordposition==((Position)obj).wordposition;
    }
    
}