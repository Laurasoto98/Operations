package lab07;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;


public class StringSplitter {
	private Queue<Character> characters;
	private String token;
	
	public static final String SPECIAL_CHARACTER = "()+-*/^";
	
	public StringSplitter(String line){
		characters = new LinkedList<Character>();
		for (int i = 0; i < line.length(); i++){
			characters.add(line.charAt(i));
		}
		findNextToken();
	}
	
	// post: returns true if there is another token
	public boolean hasNext(){
		return token != null;
	}
	
	//pre: there is another token to return (throws NoSuchElementException if not)
	//post: returns and consumes the next token
	public String next(){
		checkToken();
		String result = token;
		findNextToken();
		return result;
	}
	
	//pre: there is another token to return (throws NoSuchElementException if not)
	//post: returns the next toek without consuming it
	public String peek(){
		checkToken();
		return token;
	}
	
	//post: finds the next token, if any
	private void findNextToken(){
		while (!characters.isEmpty() && Character.isWhitespace(characters.peek())){
			characters.remove();
		}
		if (characters.isEmpty()){
			token = null;
		}else{
			token = "" + characters.remove();
			if (!SPECIAL_CHARACTER.contains(token)){
				boolean done = false;
				while (!characters.isEmpty() && !done){
					char ch = characters.peek();
					if (Character.isWhitespace(ch) || SPECIAL_CHARACTER.indexOf(ch) >= 0){
						done = true;
					}else{
						token = token + characters.remove();
					}
				}
			}
		}
	}
	
	//post: throws an exception if there is no token left
	private void checkToken(){
		if (!hasNext()){
			throw new NoSuchElementException();
		}
	}
}
