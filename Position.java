interface PositionInterface{
	Position(PageEntry p, int wordIndex);
	PageEntry getPageEntry();
	int getWordIndex();
}

public class Position implements PositionInterface {
	
	PageEntry page;
	int wordposition;

	Position(PageEntry p, int wordIndex){
		page = p;
		this.wordposition = wordIndex;
	}

	PageEntry getPageEntry(){
		return page;
	}

	int getWordIndex(){
		return wordposition;
	}
}