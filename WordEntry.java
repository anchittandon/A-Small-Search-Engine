interface WordEntryInterface{
	WordEntry(String word);
	void addPosition(Position position);
	void addPositions(MyLinkedList<Position> positions);
	MyLinkedList<Position> getAllPositionsForThisWord();
	float getTermFrequency(String word);
}

public class WordEntry{
	String word;
	Myset<Position> positions;
	WordEntry(String word){
		this.word = word;
		this.positions = new Myset<Position>();
	}

	void addPosition(Position position){
		this.positions.Insert(position);
	} 
	void addPositions(MyLinkedList<Position> ps){
		this.positions.Union(ps);
	}

	MyLinkedList<Position> getAllPositionsForThisWord(){
		return positions.myset;
	}

	float getTermFrequency(String word){

	}
}