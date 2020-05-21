/** @author 
 *  Abhilasha Devkar : acd170130
 *  SP10 : Tries
 **/


package acd170130;
//starter code for Tries

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This is Trie class which is used to 
 * perform various operations on the same.
 */
public class Trie<V> 
{
	/**
	 * This is Entry class equivalent to Node of Trie.
	 * @param value = value of Node
	 * @param child = hashmap(key = character, 
	 * 				  value = pointer to node containing that character) 
	 * 				  at Node
	 * @param depth = depth of Node
	 * @param EndOfWord = To indicate whether current node is end 
	 * 					  of any particular word.
	 */
    private class Entry 
    {
		V value;
		HashMap<Character,Entry> child;
		int depth;
		boolean EndOfWord;
		Entry(V value, int depth) 
		{
		    this.value = value;
		    child = new HashMap<>();
		    this.depth = depth;
		    EndOfWord = false;
		}
    }
    private Entry root;
    private int size;
    private int prefixCnt;

    /**
     * Constructor
     * @param root = New Node with default values.
     * @param size = Number of words in Trie. Setting it with 0.
     * @param prefixCnt = Prefix count.
     **/
    public Trie() 
    {
		root = new Entry(null, 0);
		size = 0;
		prefixCnt = 0;
    }

    
 // public methods

    /**
     * This method is used to add word in Trie.
     * @param  str: Word to be added. 
     * @param value : value of the word.
     * @return value of added word.
     */
    public V put(String str, V value) 
    {
    	Entry curr = root;
    	for (int i=0; i<str.length(); i++)
    	{
    		char ch = str.charAt(i);
    		Entry node = curr.child.get(ch);
    		if (node == null)
    		{    			
    			node = new Entry(null, curr.depth+1);
    			curr.child.put(ch, node);
    		}
    		curr = node;
    	}
    	if(!curr.EndOfWord)
    	{
    		curr.value = value;
        	curr.EndOfWord = true;
        	size++;
    	}
    	else
    	{
    		System.out.println("Word is already present.");
    	}    	    	
    	return curr.value;
    }

    /**
     * This method is used to get value associated with word.
     * @param  str: Word whose value is to be found. 
     * @return value of added word if present(Here, value is time at which word got added into Trie).
     *		   else null (if word is not present in Trie).
     */
    public V get(String str) 
    {
    	Entry curr = root;
    	for (int i=0; i<str.length(); i++)
    	{
    		char ch = str.charAt(i);
    		Entry node = curr.child.get(ch);
    		//If node does not exist for given character
    		if (node == null) 
    		{
    		    return null;	
    		}
    		curr = node;
    	}
    	//If word exists but it was not added individually into Trie
    	return curr.EndOfWord == true ? curr.value : null;
    }

    /**
     * This method is driver method to remove word from Trie.
     * @param  str: Word to be removed. 
     */
    public boolean remove(String str) 
    {
    	return (remove(root, str, 0));
    }
    
    /**
     * This method is helper method to remove word from Trie.
     * @param curr : Current node which is getting explored.
     * @param  str : Word to be removed. 
     * @param index : Index of word.
     * @return True if parent should remove the child mapping from its node.
     * 	       False otherwise.
     */
    private boolean remove(Entry curr, String str, int index)
    {
    	//If we have found the word
    	if (index == str.length())
    	{
    		if (!curr.EndOfWord)
    		{
    			System.out.println("String can not be removed as it was not inserted as a word.");
    			return false;
    		}
    		
    		curr.EndOfWord = false;
    		curr.value = null;
    		size--;
    		System.out.println("String removed.");
    		return curr.child.size() == 0;
    	}
    	
    	//If at intermediate position
    	char ch = str.charAt(index);
    	Entry node = curr.child.get(ch);
    	
    	//If node does not exist for given character
    	if (node == null) 
    	{
    		System.out.println("String can not be removed as it does not exist.");
    	    return false;	
    	}
    	
    	//shouldDeleteCurrNode becomes true if child node is empty and ready to be deleted.
    	boolean shouldDeleteCurrNode = remove(node, str, index+1);
    	if (shouldDeleteCurrNode)
    	{
    		//If Node does not indicate end of any word.
    		if(!curr.EndOfWord)
    		{
    			curr.child.remove(ch);
        		return curr.child.size() == 0;
    		}
    	}
    	return false;
    }
    
    
    /********************************/
    /**Iterator Mathods**/
    /********************************/
    
    
    /**
     * This method is used to add word in Trie.
     * @param  iter : Iterator associated with Word to be added. 
     * @param value : value of the word.
     * @return value of added word.
     */
    private V put(StringIterator iter, V value)
    {
    	Entry curr = root;
    	while(iter.hasNext())
    	{
    		char ch = iter.next();
    		Entry node = curr.child.get(ch);
    		if (node == null)
    		{    			
    			node = new Entry(null, curr.depth+1);
    			curr.child.put(ch, node);
    		}
    		curr = node;
    	}
    	if(!curr.EndOfWord)
    	{
    		curr.value = value;
        	curr.EndOfWord = true;
        	size++;
    	}
    	else
    	{
    		System.out.println("Word is already present.");
    	}
       	return curr.value;
    }

    
    /**
     * This method is used to get value associated with word.
     * @param  iter : Iterator associated with Word to be found. 
     * @return value of added word if present(Here, value is time at which word got added into Trie).
     *		   else null (if word is not present in Trie).
     */
    private V get(StringIterator iter)
    {
    	Entry curr = root;
    	while(iter.hasNext())
    	{
    		char ch = iter.next();
    		Entry node = curr.child.get(ch);
    		//If node does not exist for given character
    		if (node == null) 
    		{
    		    return null;	
    		}
    		curr = node;
    	}
    	//If word exists but it was not added individually into Trie
    	return curr.EndOfWord == true ? curr.value : null;
    }

    /**
     * This method is driver method to remove word from Trie.
     * @param iter : Iterator associated with Word to be removed. 
     */
    private Boolean remove(StringIterator iter) 
    {
    	return (remove(root, iter, 0));
    }
    
    /**
     * This method is helper method to remove word from Trie.
     * @param curr : Current node which is getting explored.
     * @param iter : Iterator associated with Word to be removed. 
     * @param index : Index of word.
     * @return True if parent should remove the child mapping from its node.
     * 	       False otherwise.
     */
    private boolean remove(Entry curr, StringIterator iter, int index)
    {
    	//If we have found the word
    	if (index == iter.arr.length)
    	{
    		if (!curr.EndOfWord)
    		{
    			System.out.println("String can not be removed as it was not inserted as a word.");
    			return false;
    		}
    		
    		curr.EndOfWord = false;
    		curr.value = null;
    		size--;
    		System.out.println("String removed.");
    		return curr.child.size() == 0;
    	}
    	
    	//If at intermediate position
    	char ch = iter.arr[index];
    	Entry node = curr.child.get(ch);
    	
    	//If node does not exist for given character
    	if (node == null) 
    	{
    		System.out.println("String can not be removed as it does not exist.");
    	    return false;	
    	}
    	
    	//shouldDeleteCurrNode becomes true if child node is empty and ready to be deleted.
    	boolean shouldDeleteCurrentNode = remove(node, iter, index+1);
    	if (shouldDeleteCurrentNode)
    	{
    		//If Node does not indicate end of any word.
    		if(!curr.EndOfWord)
    		{
    			curr.child.remove(ch);
        		return curr.child.size() == 0;
    		}
    	}
    	return false;
    }


    /**
     * This method is used to detect How many words 
     * in the dictionary start with this prefix.
     * @param prefix : Prefix. 
     */
    public int prefixCount(String prefix) 
    {
    	Entry curr = root;
    	prefixCnt = 0;
    	for (int i=0; i<prefix.length(); i++)
    	{
    		char ch = prefix.charAt(i);
    		Entry node = curr.child.get(ch);
    		//If node does not exist for given character
    		if (node == null) 
    		{
    		    return 0;	
    		}
    		curr = node;
    	}
    	count(curr);
    	return prefixCnt;
    }
    
    /**
     * This method is helper method to detect How many words 
     * in the dictionary start with this prefix.
     * @param curr : Current Node. 
     */
    private void count(Entry curr)
    {
    	//Increase Prefix count if any word is found.
    	if (curr.EndOfWord == true)
    		prefixCnt += 1;
    	
    	for (HashMap.Entry<Character, Entry> entry : curr.child.entrySet())
    	{
    		count(entry.getValue());
    	}
    }

    /**
     * This method is used to detect total words in Trie.
     * @return size. 
     */
    public int size()
    {
    	return size;
    }
    
    /**
     * Iterator Class to iterate over Word
     * */
    public static class StringIterator implements Iterator<Character>
    {
		char[] arr; 
		int index;
		public StringIterator(String s)
		{ 
			arr = s.toCharArray(); 
			index = 0;
		}
		public boolean hasNext()
		{ 
			return index < arr.length;
		}
		public Character next()
		{ 
			return arr[index++]; 
		}
		public void remove()
		{ 
			throw new java.lang.UnsupportedOperationException(); 
		}
    }

    //Each option when selected can be used until “End” is typed.
    public static void main(String[] args)
    {
    	Scanner in = new Scanner(System.in);

		Trie<Integer> trie = new Trie<>();
		
		int wordno = 0;
		
		System.out.println("Options");
		System.out.println("Each option when selected can be used until “End” is typed.");
		System.out.println("1. Insert string into the trie");
		System.out.println("2. Remove element from the the trie");
		System.out.println("3. Get value associated with the string");
		System.out.println("4. Get Prefix count of the string");
		whileloop:
			while(in.hasNext()) {
			    int option = in.nextInt();
			    switch(option) {
			    case 1:  
			    	while(in.hasNext()) 
					{
						
					    String str = in.next();
					    
					    if(str.equals("End")) 
					    { 
					    	break; 
					    }
					    StringIterator iter = new StringIterator(str);
					    wordno++;
					    //trie.put(iter, wordno);
					    trie.put(str, wordno);
				    }
			    	break;
			    case 2:  
			    	while(in.hasNext()) 
					{
					    String str = in.next();
					    if(str.equals("End")) 
					    { 
					    	break; 
					    }
					    StringIterator iter = new StringIterator(str);
					    //trie.remove(iter);
					    trie.remove(str);					    
					}
			    	break;
			    	
			    case 3:	
			    	while(in.hasNext()) 
					{
					    String str = in.next();
					    if(str.equals("End")) 
					    { 
					    	break; 
					    }
					    StringIterator iter = new StringIterator(str);
					    //Integer val = trie.get(iter);
					    Integer val = trie.get(str);
					    System.out.println(str + "\t" + val);
					    
					}
			    	break;
			    	
			    case 4:
			    	System.out.println("Enter the prefix");
			    	while(in.hasNext()) 
					{			
					    String str = in.next();
					    if(str.equals("End")) 
					    { 
					    	break; 
					    }		
					    Integer cnt = trie.prefixCount(str);
					    System.out.println("Number of entries with the prefix: "+cnt);
					    System.out.println("Enter the prefix");
					}
			    	break;

			    default:
			    	break whileloop;// Exit loop
			    }
		 }// end of while
			in.close();		
    }
}
