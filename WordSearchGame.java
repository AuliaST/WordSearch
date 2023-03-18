import java.util.*;

//Word Search
//File:	wordSearch.java
/* Purpose: This class creates a word search
* 			grid. The user is asked to enter 
* 			in words and the word grid will be 
* 			generated based on the words given
* 		
*/
public class wordSearchReal {
	private static int width;
	private static int length;
	private static int wordCount;
	private static  ArrayList<String> words;
	private static char [][] search; 
	private static char [][] solutions;
	static Scanner input = new Scanner(System.in);
	private static boolean created;

	// main method
	public static void  main  (String [] args){
		printIntro();
		created = false;
		menu();
	}// end of main method
	
	// method that prints out the 
	// intro for the game
	public static void printIntro() {
		System.out.println("Welcome to my word search generator!");
		System.out.println("This program will allow you to generate your own word search puzzle");
	}// end of method printIntro
	
	// method that displays the choices a
	// user has with this program
	public static void menu() {
		char choice = 'a';
		ArrayList<String> wordsActual = new ArrayList<String>();
		// while loop that will keep allowing
		// a user to make choices until they
		// decide to quit
		while(choice != 'q') {
			System.out.println("Please select an option:");
			System.out.println("g: create a new word search grid");
			System.out.println("p: print the current word search grid");
			System.out.println("s: show the solutions of the current grid");
			System.out.println("q: quit the game");
			choice = input.next().charAt(0);
			// switch case that branches out
			// depending on the user's choice
			switch(choice)
			{
			// choice to create a new wordsearch grid
			case 'g':
				wordsActual = createGrid();
				created = true;
				break;
			// choice to print the currently generated
			// grid
			case 'p':
				// if statement checking if a user has
				// created a grid before or not
				// if not then reminds a user to
				// create a grid first
				if(created == false) {
					System.out.println("Please create a search first");
				}
				// otherwise it will print out
				// the grid
				else {
					printGrid(search);
					// will print out the words that were 
					// able to be added to the wordsearch
					System.out.println("Words to find(that were able to be added):");
					// for loop printing every word added
					for(int i = 0;i<wordsActual.size();i++){
						// if statement to just print out the words
						// to search for in triplets
						if(i%3==2) {
							System.out.println(wordsActual.get(i));
						}
						// printing a new line on the third
						// printed word otherwise no printed new line
						else {
							System.out.print(wordsActual.get(i)+ " ");
						}// end of if/else
					}// end of for loop
					System.out.println();
				}// end of if/else
				
				break;
			// case to print out the solutions to the generated
			//  puzzle
			case 's':
				printGrid(solutions);
				break;
			// if  the word count is 0 and 
			// the user inputted something other 
			// than the given choices then will
			// have them enter their choice again
			default:
				if(choice!='q' && wordCount == 0) {
					System.out.println("Please input a correct choice");
				}// end of if
			}// end of switch case
			
		}// end of while loop
	}// end of method menu

	// method that creates the main grid
	// of the word search, getting input 
	// from the user and putting them in
	// random orientations in the grid
	// and returns the list of words
	// added into the grid
	public static ArrayList<String> createGrid() {
		String word;
		width  = 0;
		length = 0;
		wordCount = 0;
		words = new ArrayList<String>();
		
		ArrayList<String> wordsActual = new ArrayList<String>();
		System.out.println("Enter each word on it's own line");
		System.out.println("Enter done when you are finished adding words");
		System.out.println("Only single words are allowed, no punctuation");
		// while loop to keep grabbing 
		// words until the user enters
		// done as a word, meaning 
		// they are finished inputting words
		while (input.hasNextLine()){
		       word = input.next().toLowerCase(); 
		       // if statement checking if the user
		       // enters done
		      if(word.equals("done")){
		        break;
		      }// end of if
		      // initializing width to first
		      // word inputted 
		      if(width == 0) {
		    	  width = word.length();
		      }
		      // otherwise updates the width
		      // if there is a larger word inputted
		      else if (word.length()>width){
		    	  width = word.length();
		      }// end of if/else
		      // increments word count
		      // and adds to the list holding all
		      // entered words
		      wordCount++;
		      words.add(word);
		    }// end of while loop
		// updates the width to 
		// one and a half times the width
		// of the longest word
		// and changes length to the width
		// plus a quarter of the width
		width =(int) Math.ceil(width + width*.50);
		length = width + width/4;
		search = new char [width][length];
		solutions = new char [width][length];
		// this for loop is to run
		// for every word and will 
		// insert the entered words in
		// random orientations
		for(int i = 0;i<wordCount;i++) {
			int x = random(1,4);
			// switch case to choose between
			// one of four different directions
			switch (x) {
			// case for horizontal word insert
			// and each case checks
			// if the word was able to be put in
			// within 100 tries or not
			case 1:
				if(insertWordsHorizontally(words.get(i))) {
					wordsActual.add(words.get(i));
				}// end of if
				break;
			// case for insert word vertically
			case 2:
				if(insertWordsVertically(words.get(i))) {
					wordsActual.add(words.get(i));
				}// end of if
				break;
			// case for insert words diagonally left to right
			case 3:
				if(insertWordsDiagonally(words.get(i))) {
					wordsActual.add(words.get(i));
				}// end of if
				break;
			// case for insert words diagonally
			// right to left
			case 4:
				if(insertWordsDiagonallyReverse(words.get(i))) {
					wordsActual.add(words.get(i));
				}// end of if
				break;
			}// end of switch case
		}// end of for loop
		
		// functions to fill in
		// any remaining holes for the 
		// actual grid and the solutions
		// grid and returns the words 
		// added into the grid
		fillGrid();
		fillSolutions();
		return wordsActual;
	}// end of method createGrid
	
	// method that just fills the rest
	// of the empty parts of the grid
	// with random letters
	public static void fillGrid() {
		// for loop to run length of the grid
		for (int i =0; i <length; i ++){
			// for loop to run width of the grid
		    for (int j =0; j< width; j++){
		    	// if to check if current
		    	// location is empty or not
		        if(search[j][i] == 0){
		        	// if yes then it fills with a random
		        	// lowercase letter
		        	char x = (char) random(97, 122); 
		        	search[j][i] = x;
		        }// end of if
		      }// end of for loop
		    }// end of for loop
	}// end of method fillGrid
	
	
	// method to fill in the solutions array
	// with - where there aren't any solutions
	public static void fillSolutions() {
		// for loop to run length of the grid
		for (int i =0; i <length; i ++){
			// for loop to run width of the grid
		    for (int j =0; j< width; j++){
		    	// if to check if current
		    	// location is empty or not
		        if(solutions[j][i] == 0){
		        	// if yes then it fills with -
					char x = '-'; 
					solutions[j][i] = x;
		        }// end of if
			}// end of for loop
		}// end of for loop
		
	}// end of fillSolutions
	
	// method to produce a random number
	public static int random(int low, int high){ 
	    Random generator = new Random();
	    return generator.nextInt(high-low+1) + low;
	  }// end of random
	
	// method to insert a word vertically
	// into the search grid
	public static boolean insertWordsHorizontally(String word) {
		int x;
		int y;
		int between;
		int stop = 0;
		between = length - word.length();
		x = random(0,width-1);
		y = random(0,between);
		// while loop to keep trying a random
		// coordinate 99 more times if 
		// the first random coordinate wasn't valid
		// utilizes check function to see
		// if coordinate is valid or not
		while(!checkHorizontally(x,y, word) && stop!=99) {
			x = random(0,width-1);
			y = random(0,between);
			stop++;
		}// end of while loop
		// if stop got to 99 
		// then it took 100 tries
		// and was unable to add the word
		// to the grid
		if(stop==99) {
			return false;
		}// end of if
		return true;
		
	}// end of insertWordsHorizontally
	
	// method to check for horizontal insert point
	// is valid
	public static boolean checkHorizontally(int x,int y, String word) {
		char [][] copy=new char [width][length];
		char [][] solutionsClone=new char [width][length];
		solutionsClone = copyArray(solutions);
		copy = copyArray(search);
		// for loop to see if placing
		// the letters in a copy of the array
		// is valid
		for(int i = 0;i<word.length();i++) {
			// if the spot isn't empty then
			// it is not a valid coordinate
			// and returns false
			if(copy[x][y]!=0) {
				solutionsClone = copyArray(solutions);
				copy = copyArray(search);
				return false;
			}// end of if
			// creates the array as it loops
			// if it succeeds then the solutions
			// and search grids will be updated
			// with the new inserted word
			copy[x][y] = word.charAt(i);
			solutionsClone[x][y] = word.charAt(i);
			y++;
		}
		solutions = copyArray(solutionsClone);
		search = copyArray(copy);
		// returns true meaning the word
		// was able to be added
		return true;
	}// end of checkHorizontally
	
	// each below method is the same as horizontal function
	// and horizontal check method, only differences is
	// which index of the 2d array gets incremented
	// or decremented depending on which direction it is being put in
	
	// method to insert word vertically
	// into the search grid, see insertWordsHorizontally
	// for in depth comments
	public static boolean insertWordsVertically(String word) {
		int x;
		int y;
		int between;
		int stop = 0;
		between = width - word.length();
		x = random(0,between);
		y = random(0,length-1);
		while(!checkVertically(x,y, word)&&stop!=99) {
			x = random(0,between);
			y = random(0,length-1);
			stop++;
		}
		
		if(stop==99) {
			return false;
		}
		return true;
		
	}// end of method insertWordsVertically
	
	// method to check if the word is able 
	// to be inserted vertically, see checkHorizontally
	// for in depth comments
	public static boolean checkVertically(int x,int y, String word) {
		char [][] copy=new char [width][length];
		char [][] solutionsClone=new char [width][length];
		solutionsClone = copyArray(solutions);
		copy = copyArray(search);
		for(int i = 0;i<word.length();i++) {
			if(copy[x][y]!=0) {
				solutionsClone = copyArray(solutions);
				copy = copyArray(search);
				return false;
			}
			copy[x][y] = word.charAt(i);
			solutionsClone[x][y] = word.charAt(i);
			x++;
		}
		solutions = copyArray(solutionsClone);
		search = copyArray(copy);
		return true;
	}// end of method checkVertically
	
	// method to insert word diagonally from left to right
	// into the search grid, see insertWordsHorizontally
	// for in depth comments
	public static boolean insertWordsDiagonally(String word) {
		int x;
		int y;
		int betweenx;
		int betweeny;
		int stop = 0;
		betweeny = length - word.length();
		betweenx = width - word.length();
		x = random(0,betweenx);
		y = random(0,betweeny);
		while(!checkDiagonally(x,y, word) && stop!= 99) {
			x = random(0,betweenx);
			y = random(0,betweeny);
			stop++;
		}
		
		if(stop==99) {
			return false;
		}
		return true;
		
	}// end of method insertWordsDiagonally
	
	// method to check if the word is able 
	// to be inserted diagonally
	// from left to right, see checkHorizontally
	// for in depth comments
	public static boolean checkDiagonally(int x,int y, String word) {
		char [][] copy=new char [width][length];
		char [][] solutionsClone=new char [width][length];
		solutionsClone = copyArray(solutions);
		copy = copyArray(search);
		for(int i = 0;i<word.length();i++) {
			if(copy[x][y]!=0) {
				solutionsClone = copyArray(solutions);
				copy = copyArray(search);
				return false;
			}
			copy[x][y] = word.charAt(i);
			solutionsClone[x][y] = word.charAt(i);
			y++;
			x++;
		}
		solutions = copyArray(solutionsClone);
		search = copyArray(copy);
		
		return true;
	}// end of method checkDiagonally
	
	// method to insert word diagonally from right to left
	// into the search grid, see insertWordsHorizontally
	// for in depth comments
	public static boolean insertWordsDiagonallyReverse(String word) {
		int x;
		int y;
		int betweenx;
		int betweeny;
		int stop = 0;
		
		betweenx = width - word.length()-1;
		x = random(0,betweenx);
		y = random(word.length()-1,length-1);
		while(!checkDiagonallyReverse(x,y, word) && stop!= 99) {
			x = random(0,betweenx);
			y = random(word.length()-1,length-1);
			stop++;
		}
		
		if(stop==99) {
			return false;
		}
		return true;	
	}// end of method insertWordsDiagonallyReverse
	
	// method to check if the word is able 
	// to be inserted diagonally
	// from right to left, see checkHorizontally
	// for in depth comments
	public static boolean checkDiagonallyReverse(int x,int y, String word) {
		char [][] copy=new char [width][length];
		char [][] solutionsClone=new char [width][length];
		solutionsClone = copyArray(solutions);
		copy = copyArray(search);
		for(int i = 0;i<word.length();i++) {
			if(copy[x][y]!=0) {
				solutionsClone = copyArray(solutions);
				copy = copyArray(search);
				return false;
			}
			copy[x][y] = word.charAt(i);
			solutionsClone[x][y] = word.charAt(i);
			y--;
			x++;
		}
		solutions = copyArray(solutionsClone);
		search = copyArray(copy);
		
		return true;
	}// end of method checkDiagonallyReverse
	
	// method to print the given 2d array
	public static void printGrid(char [][] toPrint) {
		// for loop to run the height of the 
		// given 2d array
		for(int i = 0;i <width;i++) {
			// method to run the length of the 
			// 2d array
			for(int j = 0; j<length;j++) {
				// prints out the value at the coordinate and a space
				System.out.print(toPrint[i][j]+ " ");
			}// end of for loop
			// prints new line 
			System.out.println();
		}// end of for loop
		
	}// end of printGrid
	
	// method to manually create a new 
	// 2d array that copies the values
	// from the given array
	public static char [][] copyArray(char[][] a){
		char [][] c = new char [width][length];
		// for loop to run the height of the 
		// 2d array
		for(int i = 0; i<width;i++) {
			// method to run the length of the 
			// 2d array
			for(int j = 0;j<length;j++) {
				c[i][j]=a[i][j];
			}// end of for loop
		}// end of for loop
		return c;
	}// end of method copyArray
}
