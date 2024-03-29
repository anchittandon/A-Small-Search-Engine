interface WordEntryInterface{
	public void addPosition(Position position);
	public void addPositions(MyLinkedList<Position> positions);
	public MyLinkedList<Position> getAllPositionsForThisWord();
	public float getTermFrequency(String word);
}

public class WordEntry{

	/*
		Stores the list of word indices 
		where the word is present in the document(s).
	*/

	private String word;
	private Myset<Position> positions;
	private AVL<Position> AVLpositions;

	public WordEntry(String word){
		this.word = word;
		this.positions = new Myset<Position>();
		this.AVLpositions = new AVL<Position>();
	}

	public String getWord(){
		return this.word;
	}

	public void addPosition(Position position){
	// Add a position entry for word.
		this.positions.Insert(position);
		this.AVLpositions.setRoot(this.AVLpositions.insert(AVLpositions.getRoot(),position));
	} 

	public void addPositions(MyLinkedList<Position> ps){
	// Add multiple position entries for word.
		Myset<Position> newpositions = new Myset<Position>();
		newpositions.myset = ps;
		this.positions = this.positions.Union(newpositions);

		MyLinkedList<Position>.Node itr = ps.getHead();
		while(itr!=null){
		this.AVLpositions.setRoot(this.AVLpositions.insert(AVLpositions.getRoot(),itr.getData()));
			itr = itr.getNext();
		}
	}

	public MyLinkedList<Position> getAllPositionsForThisWord(){
	// Returns a linked list of all position entries for the word.
		return positions.myset;
	}

	public AVL<Position> getPositions(){
        return AVLpositions;
    }

	public float getTermFrequency(String word){
	// Return the term frequency of the word in a webpage.
		MyLinkedList<Position>.Node itr = this.getAllPositionsForThisWord().getHead();
		float totalCount = 0;
		float numberThis = 0;
		PageEntry page = null;
		while(itr!=null){
			if(itr.getData().getPageEntry().getPageName() == word){
				numberThis = numberThis + 1.0f;
				page = itr.getData().getPageEntry();
			}
			itr = itr.getNext();
		}
		if(page == null){
			return 0.0f;
		}
		totalCount = page.getWordCount();
        // System.out.println(page+" "+this.getWord()+" "+totalCount+" "+numberThis+" "+"totalCount and numberthis");
		return numberThis/totalCount;	
}

	public boolean equals(Object obj){
	// Two WordEntry Objects are equal if they are
	// entries of same word
        return this.word.equals(((WordEntry)obj).word);
    }

    public String toString(){
        return word;
    } 
}