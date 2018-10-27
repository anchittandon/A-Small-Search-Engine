interface PositionInterface{
	public PageEntry getPageEntry();
	public int getWordIndex();
}

public class Position implements PositionInterface, Comparable<Position> {

	/*
	 Represents a Tuple <page, word position>
	 */
	
	PageEntry page;
	int wordposition;

	public Position(PageEntry p, int wordIndex){
		page = p;
		this.wordposition = wordIndex;
	}

	public PageEntry getPageEntry(){
	// Returns the PageEntry field
		return page;
	}

	public int getWordIndex(){
	// Returns the position of the word from 
	// the beginning of document.
		return wordposition;
	}

	public boolean equals(Object obj){
	/*  For two Positions to be same,
		they should have:
		1. Same page
		2. Same position of word in page
	*/
		boolean condition1 = this.page.equals(((Position)obj).page);
		boolean condition2 = this.wordposition == ((Position)obj).wordposition;
        return  condition1 && condition2 ;
    }

    @Override
    public int compareTo(Position obj){
        return this.wordposition - obj.getWordIndex();
    } 

    public String toString(){
    	return page+" "+wordposition;
    }

}