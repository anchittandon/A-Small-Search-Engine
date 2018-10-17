# A-Small-Search-Engine
In this project, I have built the basic data structure underlying search engines: *an inverted index*. I have used this inverted index to answer some simple search queries.

## An inverted index for a set of webpages
Suppose we are given a set of webpages *W*. For our purposes, each webpage *w* ∈ *W* will be considered to be a sequence of words *w<sub>1</sub>, w<sub>2</sub>, . . . w<sub>k</sub>*. Another way of representing the webpage could be to maintain a list of words along with the position(s) of the words in the webpage. For example consider a web page with the following text:

<p align="center">Data structures is the study of structures for storing data.</p>

This can be represented as 

<p align="center">{ ( data : 1, 10 ), ( structures : 2, 7 ), ( study : 5 ), ( storing : 9 ) }.</p>

Note that the small connector words like “is”, “the”, “of”, “for” have not been stored. Words like this are referred to as *stop words* and are generally removed since they are very frequent and normally contain no information about the content of the webpage.

This representation of the webpage is similar to the index we see at the back of many books which tell us the page numbers where certain important terms used in the book may be found. In fact, we can refer to this as an *index* for the webpage. In mathematical notation we would say that given a webpage *w = w<sub>1</sub>, w<sub>2</sub>, . . . , w<sub>k</sub>*, the index of *w* is

<p align="center">{ ( <em>u</em> : <em>i</em><sub>1</sub>( <em>u</em> ), . . . <em>i</em><sub>l</sub>( <em>u</em> ) ) : <em>w</em><sub><em>i</em><sub><em>j</em></sub></sub>(<em>u</em>) = <em>u</em>, 1 ≤ <em>j</em> ≤ <em>l</em> }.</p>

An index is used to find the location of a particular string (word) in a specific document or webpage, but when we move to a *collection* of webpages, we need to first figure out which of the web pages contain the string. For this we store an *inverted index*. Let us try to define an inverted index formally. 

Let us suppose we are given a collection *C* of webpages. For each page *p* ∈ *C*, let us denote by *W*(*p*) the set of all words (excluding stop words) that occur in *p*.

An inverted index for *C* will contain an entry for each word *w* ∈ *W*(*C*). This entry will contain tuples of the form (*p*, *k*) to indicate that *w* occurs in the *k*th position of page *p* ∈ *C*. Using the notation that *p*[*k*] denotes the *k*th word of page *p*, we can say that the inverted index of *C* is defined as

<p align="center"><em>Inv</em>(<em>C</em>) = {(<em>w</em> : {(<em>p</em>, <em>k</em>) : <em>p</em> ∈ <em>C</em>, <em>p</em>[<em>k</em>] = <em>w</em>}) : <em>w</em> ∈ <em>W</em>(<em>C</em>)}.</p>

For example, consider the following (small) collection of documents.
<p align="center"><ol><li>Data structures is the study of structures for storing data.</li>
  
<li>Structural engineers collect data about structures.</li></ol></p>

The inverted index for this would be

{ ( data : { ( 1, 1 ), ( 1, 10 ), ( 2, 4 ) } ),
( structures: { ( 1, 2 ), ( 1, 7 ), ( 2, 6 ) } ),
( study : { ( 1, 5 ) } ),
( storing : { ( 1,9 ) } ),
( structural : { ( 2, 1 ) } ),
( engineers : { ( 2, 2 ) } ),
( collect : { ( 2, 3 ) } ) }



## The web search problem

The *web search problem* is defined as follows:

> Given a collection of webpages *C* and a sequence of words *q<sub>1</sub>* . . . *q<sub>k</sub>*, find the “most relevant” set of pages *p<sub>1</sub>*, *p<sub>2</sub>*, . . . *p* that contain as many of *q<sub>1</sub>* . . . *q<sub>k</sub>* as possible and return  them in the order of decreasing “relevance”.  

The question of how to measure the relevance of a webpage to a particular query is an involved question with no easy answers. However, for the purpose of this assignment we will work with a simple scoring function. 

## A scoring function for search term relevance

One of the simplest scoring function is term frequency-inverse document frequency. It is used to measure how important a word *w* is to a webpage *p*. It is a product of two factors i.e. term frequency and inverse document frequency. Given a word *w* and a webpage *p*, the relevance score is defined as

<p align="center">relevance<em><sub>w</sub></em>(<em>p</em>) = <em>tf<sub>w</sub></em>(<em>p</em>) x <em>idf<sub>w</sub></em>(<em>p</em>)</p>

*Term Frequency*: It is the total number of occurrence of a word *w* in a webpage *p*, denoted by *f<sub>w</sub>*(*p*). It is normalized by the total number of words in webpage *p*, denoted by |*W*(*p*)|. It is defined as

<p align="center"><em>tf<sub>w</sub></em>(<em>p</em>) = <em>f<sub>w</sub></em>(<em>p</em>)/ |<em>W</em>(<em>p</em>)| </p>

*Inverse document frequency*: It is the logarithm of the total number of webpages, denoted by *N* divide by the logarithm of the number of webpages *n<sub>w</sub>*(*p*) that contain the word *w*. It is defined as

<p align="center"><em>idf<sub>w</sub></em>(<em>p</em>) = log(<em>N</em>/<em>n<sub>w</sub></em>(<em>p</em>)) </p>

So, if we are given a search query that has a single term, say *w*, to return the webpages in order of relevance we have to first extract the entry corresponding to *w* from *Inv*(*C*) and then calculate the relevance of each page and return the pages in decreasing order of relevance.

## Compound searches

In this assignment we will answer three kinds of search queries: AND queries, OR queries and phrase queries. We now describe these three along with their scoring methodology.

• **OR queries**: Given a search query <em>q<sub>1</sub> . . . q<sub>k</sub></em>, any page that contains any of the words *q<sub>1</sub>* to *q<sub>k</sub>* is a valid answer. The relevance score of a page *p* is computed as

<p align="center"> relevance<em><sub>q<sub>1</sub>...q<sub>k</sub></sub></em> (<em>p</em>) = &sum; relevance<sub><em>q<sub>i</sub></em></sub> (<em>p</em>), </p>

and pages are returned in decreasing order of relevance. Note that if some *q<sub>i</sub>* does not occur in page *p* the relevance<sub><em>q<sub>i</sub></em></sub>(<em>p</em>) = 0.

• ***AND** queries*: Given a search query <em>q<sub>1</sub> . . . q<sub>k</sub></em>, any page that contains all of the words *q<sub>1</sub>* to *q<sub>k</sub>* is a valid answer. The relevance score of a page *p* is computed as

<p align="center"> relevance<em><sub>q<sub>1</sub>...q<sub>k</sub></sub></em> (<em>p</em>) = &sum; relevance<sub><em>q<sub>i</sub></em></sub> (<em>p</em>), </p>

and pages are returned in decreasing order of relevance.

• ***Phrase** queries*: Given a search query <em>q<sub>1</sub> . . . q<sub>k</sub></em>, any page that contains *q<sub>1</sub>* in position *l*, *q<sub>2</sub>* in position *l*+1 and so on till *q<sub>k</sub>* in position *l*+*k*−1 is said to contain the phrase <em>q<sub>1</sub> . . . q<sub>k</sub></em> at the position . Suppose in a webpage *p* having |*W*(*p*)| words, the phrase <em>q<sub>1</sub> . . . q<sub>k</sub></em> occurs *m* times then the relevance score of page *p* for this phrase is

<p align="center"> relevance<em><sub>q<sub>1</sub>...q<sub>k</sub></sub></em> (<em>p</em>) = <em>tf<sub>q<sub>1</sub>...q<sub>k</sub></sub></em>(<em>p</em>) x <em>idf<sub>q<sub>1</sub>...q<sub>k</sub></sub></em>(<em>p</em>) = {<em>m</em> /(|<em>W</em>(<em>p</em>)| − (<em>k</em> − 1) x <em>m</em> )} x log (<em>N</em>/<em>n<sub>w</sub></em>(<em>p</em>)) </p>
