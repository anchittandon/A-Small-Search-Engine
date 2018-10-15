interface PageEntryInterface{
	public PageIndex getPageIndex();
}

public class PageEntry implements PageEntryInterface{

	/*
		Stores the information related to a webpage.	 
	 */
	
	private String pageName;
	private PageIndex pageIndex;

	public PageEntry(String pageName){
		this.pageName = pageName;
        this.pageIndex = new PageIndex();
	}

	public PageIndex getPageIndex(){
	// Returns the page index of this web-page.
		return this.pageIndex;
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

}