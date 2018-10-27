import java.util.*;
import java.io.*;

interface SearchEngineInterface{
	public void performAction(String actionMessage);
}

public class SearchEngine implements SearchEngineInterface{

    /*
        An interface to the search engine.
    */
   
	private Myset<String> excludedPlurals;
	private Myset<PageEntry> pageSet;
	public static InvertedPageIndex wordHash;
    private Myset<String> connectorWordSet;
   
	public SearchEngine(){

        this.connectorWordSet = new Myset<String>();
        this.excludedPlurals = new Myset<String>();
        this.pageSet = new Myset<PageEntry> ();
        this.wordHash = new InvertedPageIndex();

        // Connector words to be ignored
        this.connectorWordSet.Insert("whose");
		this.connectorWordSet.Insert("will");
		this.connectorWordSet.Insert("does");
		this.connectorWordSet.Insert("or");
		this.connectorWordSet.Insert("are");
		this.connectorWordSet.Insert("this");
		this.connectorWordSet.Insert("the");
		this.connectorWordSet.Insert("and");
		this.connectorWordSet.Insert("was");
		this.connectorWordSet.Insert("for");
		this.connectorWordSet.Insert("they");
		this.connectorWordSet.Insert("a");
		this.connectorWordSet.Insert("of");
		this.connectorWordSet.Insert("is");
		this.connectorWordSet.Insert("these");
		this.connectorWordSet.Insert("an");

        // Plural words to be merged with the singular entries.
		this.excludedPlurals.Insert("stacks");
		this.excludedPlurals.Insert("structures");
		this.excludedPlurals.Insert("applications");

	}

    public static InvertedPageIndex getInvertedPageIndex(){
        return wordHash;
    }

	private PageEntry parsePage(String pageName) throws Exception {

    // Parses the page with name pageName into a PageEntry

        PageEntry page = new PageEntry(pageName);
        PageIndex index = page.getPageIndex();
        BufferedReader br = null;
        String input;

        try{

            br = new BufferedReader( new FileReader("./webpages/"+pageName));
            int wordPosition = 1;
            while((input = br.readLine())!=null){

                input = input.toLowerCase();
                String[] words = input.split("\\s++|\\{|}|<|>|\\(|\\)|\\.|,|;|'|\"|\\?|#|!|-|:");
                int size = words.length;

                for(int itr=0;itr<size;itr++){

                	String currentWord = words[itr];
                    if(!currentWord.equals("")){
                        if(this.excludedPlurals.IsMember(currentWord)){
                        	currentWord = currentWord.substring(0,currentWord.length()-1);
                        }
                        if(!connectorWordSet.IsMember(currentWord)){
                            index.addPositionForWord(currentWord, new Position(page,wordPosition));
                        }
                        wordPosition++;
                    }
                }

            }
            page.setWordCount(wordPosition-1);

        }

        catch(IOException e){
            throw new Exception("Error - \""+pageName+"\" not found");
        }

        finally{

            try{

                if(br != null){
                    br.close();
                }

            }

            catch(IOException e){
                e.printStackTrace();
            }

        }

        return page;
    }

    private Myset<SearchResult> extractPages(Myset<PageEntry> pages, String[] strings, boolean isPhrase){
        MyLinkedList<PageEntry> pageList = pages.myset;
        MyLinkedList<PageEntry>.Node itr = pageList.getHead();
        Myset<SearchResult> results = new Myset<SearchResult>();
        float rel;
        while(itr != null){
            rel = itr.getData().getRelevanceOfPage(strings,isPhrase);
            try {
                results.Insert(new SearchResult(itr.getData(),rel));
            }
            catch (Exception e){}
            itr = itr.getNext();
        }
        return results;
    }

    private ArrayList<SearchResult> getSearchResultsFromPages(Myset<PageEntry> pages, String[] strings, boolean isPhrase){
        Myset<SearchResult> answer = extractPages(pages,strings,isPhrase);
        MySort<SearchResult> mysort = new MySort<SearchResult>();
        return mysort.sortThisList(answer);
    }

    private void performActionAddPage(String pageName) throws Exception{

    /*
        Adds webpage pageName to the search engine database. 
        The contents of the webpage are stored in a file named 
        pageName in the webpages folder.
    */
   
    	pageName = pageName.toLowerCase();
    	PageEntry page = parsePage(pageName);
    	pageSet.Insert(page);
    	wordHash.addPage(page);
    	
        System.out.println("Added page - \""+page+"\"");
    }

    private String processWord(String word){
        word = word.toLowerCase();
        if(this.excludedPlurals.IsMember(word)){
            word = word.substring(0,word.length()-1);
        }
        return word;
    }

    private void performActionQueryFindPagesWhichContainWord(String word){

    /*
        Prints the name of the webpages which contain the word. 
        The list of webpage names are comma separated. 
        If the word is not found in any webpage, then prints 
        "No webpage contains word"
    */
   
    	word = processWord(word);
    	Myset<PageEntry> pagesWhichContainWordSet = wordHash.getPagesWhichContainWord(word);

    	if(pagesWhichContainWordSet == null){
            System.out.println("No webpage contains word \""+word+"\"");
        }

        else{
            System.out.print("\""+word+"\" is present in pages - \"");
            String[] strings = new String[1];
            strings[0] = word;
            printSortedPages(pagesWhichContainWordSet,strings);
        }

    }

    private void performActionQueryFindPositionsOfWordInAPage(String word, String pageName) throws Exception{

    /*
        Prints the word indices where the word is found in the document with name pageName.
        The word indices should be separated by a comma. 
        If the word is not found in the webpage , then prints "Webpage pageName does not contain word x". 
        If the webpage is not added in database, then prints "No webpage pageName found".
    */

    	word = processWord(word);
    	pageName = pageName.toLowerCase();
        Myset<PageEntry> pagesOfWord = wordHash.getPagesWhichContainWord(word);
        MyLinkedList<PageEntry>.Node pageNode;
        PageEntry page;

        try{
            pageNode = pagesOfWord.find(new PageEntry(pageName));
        }
        catch(NullPointerException e){
            throw new Exception("Webpage \""+pageName+"\" does not contain word \""+word+"\"");
        }

        try{
            page = pageNode.getData();
        }
        catch(NullPointerException e){
            throw new Exception("No webpage \""+pageName+"\" found");
        }

        try{

            PageIndex pageIndexOfPage = page.getPageIndex();
            MyLinkedList<WordEntry> wordEntriesOfPage = pageIndexOfPage.getWordEntries();
            WordEntry targetWord =  wordEntriesOfPage.find(new WordEntry(word)).getData();
            MyLinkedList<Position> positions = targetWord.getAllPositionsForThisWord();
            MyLinkedList<Position>.Node itr = positions.getHead();
            String indexString = "";
            
            while(itr != null){
                //System.out.println(itr.getData().getPageEntry());
                if(itr.getData().getPageEntry().equals(page)){
                    indexString = (", "+itr.getData().getWordIndex()) + indexString;
                }
                itr = itr.getNext();
            }

            if(indexString.equals("")){
                System.out.println("Webpage \""+pageName+"\" does not contain word \""+word+"\"");
            }
            else{
                System.out.print("Positions of \""+word+"\" in page \""+pageName+"\" are ");
                System.out.println(indexString.substring(2,indexString.length()));
            }

        }

        catch(NullPointerException e){
            System.out.println("Webpage "+pageName+" does not contain word "+word);
        }
    }

    private String[] alignInput(String str[]){
        String[] strings = new String[str.length-1];
        int i=0;
        while(i<str.length-1){
            str[i+1] = processWord(str[i+1]);
            strings[i] = str[i+1];
            i++;
        }
        return strings;
    }

    private void printSortedPages(Myset<PageEntry> pages, String strings[]){
        if(pages == null){
            System.out.println("No Such Pages");
        }
        else{
            ArrayList<SearchResult> sortedPages = getSearchResultsFromPages(pages,strings,false);
            for (int i = 0; i < sortedPages.size(); i++) {
                System.out.print(sortedPages.get(i)+" ");
            }
            System.out.println("");
        }
    }

    private void performActionQueryFindPagesWhichContainAllWords(String str[]){

    /*
        Prints the name of the webpages which contain all the words given in str. 
        The words are separated by a space.
    */  
        
        String[] strings = alignInput(str);
        Myset<PageEntry> pages = wordHash.getPagesWhichContainAllWord(strings);
        printSortedPages(pages,strings);
    }

    private void performActionQueryFindPagesWhichContainAnyOfTheseWords(String str[]){

    /*
        Print the name of the webpages which contain at least one word from this
        set str.
    */
        String[] strings = alignInput(str);
        Myset<PageEntry> pages = wordHash.getPagesWhichContainAnyWord(strings);
        printSortedPages(pages,strings);
    }

    private void performActionQueryFindPagesWhichContainPhrase(String str[]){
    
    /*
        Print the name of the webpages which contain the phrase str.
    */
        String[] strings = alignInput(str);
        Myset<PageEntry> pages = wordHash.getPagesWhichContainPhrase(strings);
        printSortedPages(pages,strings);    
    } 

	public void performAction(String actionMessage){

    // Takes an action as a string. 

		String delims = "[ ]+";
		String[] tokens = actionMessage.split(delims);

        for(String x:tokens){
            System.out.print(x+" ");
        }
        System.out.print(": ");

        try{

			switch(tokens[0]){

	            case "addPage":
	            	performActionAddPage(tokens[1]);
	                break;

	             case "queryFindPagesWhichContainWord":
	             	performActionQueryFindPagesWhichContainWord(tokens[1]);
	                break;

	            case "queryFindPositionsOfWordInAPage":
	            	performActionQueryFindPositionsOfWordInAPage(tokens[1],tokens[2]);
	                break;

                case "queryFindPagesWhichContainAllWords":
                    performActionQueryFindPagesWhichContainAllWords(tokens);
                    break;

                case "queryFindPagesWhichContainAnyOfTheseWords":
                    performActionQueryFindPagesWhichContainAnyOfTheseWords(tokens);
                    break;

                case "queryFindPagesWhichContainPhrase":
                    performActionQueryFindPagesWhichContainPhrase(tokens);
                    break;

	        }

		}

		catch (Exception e){
			System.out.println(e.getMessage());
		}
        System.out.println("");
        
	}
}