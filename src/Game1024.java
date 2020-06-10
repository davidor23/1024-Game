	import java.util.Scanner;
	import java.util.Random;
	
		public class Game1024 {
			// Instance of Random, used for spawning random tile
			public static Random r = new Random();
			// To keep track of score, declared outside of main() as scope required to be accessed by move()
			public static int score = 0;
			// Keeps track of highest tile reached, declared outside of main() as scope required to be accessed by move()
			public static int highest = 1;
			
			public static void main(String[] args) {
				// Instance of Scanner, used to take in board size and move directions (WASD)	
				Scanner input = new Scanner(System.in);
				// Keeps input of board size
				int boardSize = 0;
				// Flag for when board size or direction is correct
				boolean validEntry = false;
				
				System.out.print("1024 Game");
				
				 while(!validEntry) {
					 System.out.print("\nPlease enter the board size (4-8)\n");
					 // Try catch to detect if integer, if not then try again
					 try {
						 boardSize = input.nextInt();
					 }
					 catch (Exception e) {
						 System.out.print("Board Size read error. Please try again.");
						 input.next();
						 continue;
					 }
					 // If integer in range it's accepted
					 if(boardSize >= 4 && boardSize <= 8) {
						 System.out.print("Thank you\n\n");
						 validEntry = true;
					 } else {
						 System.out.print("Board size out of bounds. Please try again.");
					 }
					 
				 }
				 
				 // Array to store tile values
				 int [][] tileValues = new int[boardSize][boardSize];
				 // Target to hit for game win
				 int target = 1024;
				 // Flags for win and game over
				 boolean gameWon = false;
				 boolean gameOver = false;
				 // Char to store user input, converted to string giving direction
				 char move = '0';
				 String direction = "";
				 
				 // Setting up game
				 tileValues = addRandomTile(tileValues);
				 tileValues = addRandomTile(tileValues);

				 // Game will continue until no valid move
				 while(!gameOver) {
					 printBoard(tileValues);
					 if(!gameWon) {
						 if(highest >= target) {
							 gameWon = true;
							 System.out.print("\nCongrats! You've Won! But How High Can You Go?\n");
						 }
					 }
					 // Test for loss
					 if(isGameOver(tileValues)) {
						 gameOver = true;
						 continue;
					 }
					 // Reuse flag for direction input
					 validEntry = false;
					 while(!validEntry) {
						 System.out.print("Please enter a direction to move board (WASD)...\nScore:" + score +" Highest:" + highest + "\n");
						 try {
							 // Tries to get the first character of String of input (does not allow more than one to be read)
							 move = input.next(".").charAt(0);
						 } catch (Exception e) {
							 // Similar to boardsize catch statement but printBoard() is not executed at beginning of this loop, must be called manually
							 System.out.print("\nDirection read error. Please try again\n");
							 input.next();
							 printBoard(tileValues);
							 continue;
						 }
						 switch (move) {
						 // If valid WASD detected switch case is entered and travels down until instruction is present, loads direction String
						 case 'w':
						 case 'W':{
							 direction = "up";
							 break;
						 }
						 case 'a':
						 case 'A':{
							 direction = "left";
							 break;
						 }
						 case 's':
						 case 'S':{
							 direction = "down";
							 break;
						 }
						 case 'd':
						 case 'D':{
							 direction = "right";
							 break;
						 }
						 default:{
							 // Similar to above, printBoard() won't be called by loop, called manually
							 System.out.print("\nDirection input not valid. Please try again.\n");
							 printBoard(tileValues);
							 continue;
							 }
						 }
						 // If move is valid exit loop, otherwise print board and ask again
						 if(validMove(tileValues,direction)) {
							 validEntry = true;
						 } else {
							 System.out.print("\nInvalid move. Please try again.\n");
							 printBoard(tileValues);
						 }
					 }
					 System.out.print("\nValid input detected\n");
					 // Once input validated call method to move in given direction
					 switch (direction) {
					 case "left": 
					 case "right": 
					 case "up": 
					 case "down": {
						 tileValues = move(tileValues, direction);
						 break;
					 }
					 default: System.out.print("\nError while reading given valid input.\n");
					 }
					 // Once move is complete random tile is added
					 tileValues = addRandomTile(tileValues);
				 }
				 
				 input.close();
				 
				 // Prints message depending on whether target reached
				 System.out.print("\nGame Over\n");
				 if(gameWon) {
					 System.out.print("\n\nCongrats! You reached the goal!\n\n" );
				 } else {
					 System.out.print("\n\nAh well, better look next time !\n\n");
				 }
					 

			}
			
			// Method for printing grid with values from array
			static void printBoard(int [][] boardArray) {
				// Format used to print line with array entries (- means print from left, % and 4 means reserve 4 spaces for entry, s means print as String)
				String centerAlignFormat = " %-4s  |";
				System.out.print("+");
				// Printing top line
				for(int i = 0; i < boardArray.length ; i ++) {
					System.out.print("-------+");
				}
				//For loop that'll repeat for each row of table
				for(int i = 0; i < boardArray.length; i++) {
					// Prints column lines
					System.out.print("\n|");
					for(int j = 0; j < boardArray.length; j++) {
						System.out.print("       |");
					}
					//Prints column lines and array entries for that row
					System.out.print("\n|");
					for(int j = 0; j < boardArray.length; j++) {
						// If array input = 0, cell is empty, otherwise the value in array is printed, note that centerAlignFormat is called upon 
						if(boardArray[i][j] == 0) {
							System.out.format(centerAlignFormat, " ");
						} else {
							System.out.format(centerAlignFormat, boardArray[i][j]);
						}
					}
					//Prints column lines
					System.out.print("\n|");
					for(int j = 0; j < boardArray.length; j++) {
						System.out.print("       |");
					}
					//Prints bottom line
					System.out.print("\n+");
					for(int j =0; j < boardArray.length; j++) {
						System.out.print("-------+");
					}
				}
				System.out.print("\n");
			}
			
			// Method to add a 1 to a random empty tile on board
			static int [][] addRandomTile(int[][] boardArray){
				int count  = 0;
				// Amount of empty spaces counted, if no empty spaces no change takes place
				for(int i = 0; i < boardArray.length; i++) {
					for(int j = 0; j < boardArray.length; j++) {
						if(boardArray[i][j] == 0) {
							count++;
						}
					}
				}
				if(count == 0) {
					return boardArray;
				}
				// Random number chosen between 0 and count (number of spaces with nothing in them)
				int randomTile = r.nextInt(count);
				// count reused as tracking number, this is increased until it matches random number generated above
				count = -1;
				//Label for breaking out of both for loops later
				outerloop:
				for(int i = 0; i < boardArray.length; i++) {
					for(int j = 0; j < boardArray.length; j++) {
						// If space is empty, tracker (count) is increased, if the track hits the random number at this point, 1 is put there
						if(boardArray[i][j] == 0) {
							count++;
						}
						if(count == randomTile) {
							boardArray[i][j] = 1;
							// Once a one is placed, program breaks from both loops to save time (hence outerloop label)
							break outerloop;
							
						}
					}
				}
				return boardArray;
			}
			
			//Method that returns whether the board can slide left
			static boolean canMoveLeft(int [][] boardArray) {
				for(int i = 0; i < boardArray.length; i++) {
					for(int j =0; j < boardArray.length -1; j++) {
						//Array searched for either 0 followed by a number, or two matching numbers side by side
						if( (boardArray[i][j] == 0 && boardArray[i][j+1] != 0) || ((boardArray[i][j] != 0) && (boardArray[i][j] == boardArray[i][j+1]))) {
							return true;
						}
					}
				}
				return false;
			}
			
			// Method that returns whether the board can slide right
			static boolean canMoveRight(int [][] boardArray) {
				for(int i = 0; i < boardArray.length; i++) {
					for(int j = 1; j < boardArray.length; j++) {
						// Array searched for either a number followed by a 0, or two matching numbers side by side
						if( (boardArray[i][j] == 0 && boardArray[i][j-1] != 0) || ((boardArray[i][j] != 0) && (boardArray[i][j] == boardArray[i][j-1]))) {
							return true;
						}
					}
				}
				return false;
			}
			
			//Method that returns whether the board can slide up
			static boolean canMoveUp(int[][] boardArray) {
				for(int j = 0; j < boardArray.length; j++) {
					for(int i = 0; i < boardArray.length - 1; i++) {
						// Array searched for either a 0 above a number, or two matching numbers above/below each other
						if( (boardArray[i][j] == 0 && boardArray[i+1][j] != 0) || ((boardArray[i][j] != 0) && (boardArray[i][j] == boardArray[i+1][j]))) {
							return true;
						}
					}
				}
				return false;
			}
			
			//Method that returns whether the board can slide down
			static boolean canMoveDown(int [][] boardArray) {
				for(int j = 0; j < boardArray.length; j++) {
					for(int i = 1; i < boardArray.length; i++) {
						// Array searched for either a 0 below a number, or two matching numbers above/below each other
						if( (boardArray[i][j] == 0 && boardArray[i-1][j] != 0) || ((boardArray[i][j] != 0) && (boardArray[i][j] == boardArray[i-1][j]))) {
							return true;
						}
					}
				}
				return false;
			}
			
			static boolean isGameOver(int [][] boardArray) {
				// If board cannot be slid in any direction, no valid move is available and game is over
				if(!canMoveLeft(boardArray) && !canMoveRight(boardArray) && !canMoveUp(boardArray) && !canMoveDown(boardArray)) {
					return true;
				}
				return false;
			}
			
			 //Method to check if move is possible on given board
			static boolean validMove(int [][] boardArray, String d) {
				switch (d) {
				//For each direction the method canMove(array,direction) is called, if valid return true, otherwise return false
				case "left":{
					if(canMoveLeft(boardArray)) {
						return true;
					}
					return false;
					}
				case "right":{
					if(canMoveRight(boardArray)) {
						return true;
					}
					return false;
					}
				case "up":{
					if(canMoveUp(boardArray)) {
						return true;
					}
					return false;
					}
				case "down":{
					if(canMoveDown(boardArray)) {
						return true;
					}
					return false;
					}
				default:{
					// This shouldn't be reached given String d in proper format, informs user of error in this case
					System.out.print("\nInvalid direction string detected.\n");
					return false;
					}
				}
			}
			
			// Method that shifts all entries in array in a given direction
			static int [][] shift(int [][] boardArray, String d) {
				int destination = 0;
				if(d == "up") {
					// Searches each column of array, destination tracker reset each time
					for(int j = 0; j < boardArray.length; j++) {
						destination = 0;
						for(int i = 0; i < boardArray.length; i++) {
							// If non zero detected current value moved to tracker location
							if(boardArray[i][j] != 0) {
								boardArray[destination][j] = boardArray[i][j];
								// If the tracker and current weren't in the same spot, delete the value of current slot, as its already been moved
								if(destination != i) {
									boardArray[i][j] = 0;
								}
								// Non zero meant that current slot filled our tracker slot, move our tracker slot to next position to fill
								destination++;
							}
							
						}
					}
					return boardArray;
				}
				// Same as shift up except rows count from bottom up (boardArray.length - 1) to zero
				if(d == "down") {
					for(int j = 0; j < boardArray.length; j++) {
						destination = 0;
						for(int i = boardArray.length - 1; i > -1; i--) {
							if(boardArray[i][j] != 0) {
								boardArray[boardArray.length -1 -destination][j] = boardArray[i][j];
								if(boardArray.length - 1 -destination != i) {
									boardArray[i][j] = 0;
								}
								destination++;
							}
							
						}
					}
					return boardArray;
					
				}
				// Similar to shift up/down but the rows are cycled through (i) and each element in row shifted (j)
				if(d == "left") {
					for(int i = 0; i < boardArray.length; i++) {
						destination = 0;
						for(int j = 0; j < boardArray.length; j++) {
							if(boardArray[i][j] != 0) {
								boardArray[i][destination] = boardArray[i][j];
								if(destination != j) {
									boardArray[i][j] = 0;
								}
								destination++;
							}
							
						}
					}
					return boardArray;
				}
				// Same as shift left but column is counted from right (boardArray - 1) to zero
				if(d == "right") {
					for(int i = 0; i < boardArray.length; i++) {
						destination = 0;
						for(int j = boardArray.length - 1; j > -1; j--) {
							if(boardArray[i][j] != 0) {
								boardArray[i][boardArray.length -1 -destination] = boardArray[i][j];
								if(boardArray.length - 1 -destination != j) {
									boardArray[i][j] = 0;
								}
								destination++;
							}
							
						}
					}
					return boardArray;
				}
				System.out.print("Invalid direction passed to shift method.");
				return boardArray;
			}
			
			// Method that shifts array entries in given direction, performs merges, then shifts again to close spaces from merging
			static int [][] move(int [][] boardArray, String d){
				boardArray = shift(boardArray, d);
				switch (d) {
				case "up":{
					for(int j = 0; j < boardArray.length; j++) {
						for(int i = 0; i < boardArray.length - 1; i++) {
							if(boardArray[i][j] == boardArray[i+1][j]) {
								boardArray[i][j] += (boardArray[i+1][j]);
								score += boardArray[i][j];
								if(boardArray[i][j] > highest) {
									highest = boardArray[i][j];
								}
								boardArray[i+1][j] = 0;
							}
						}
					}
					break;
				}
				case "down":{
					for(int j = 0; j < boardArray.length; j++) {
						for(int i = boardArray.length - 1; i > 0; i--) {
							if(boardArray[i][j] == boardArray[i-1][j]) {
								boardArray[i][j] += (boardArray[i-1][j]);
								score += boardArray[i][j];
								if(boardArray[i][j] > highest) {
									highest = boardArray[i][j];
								}
								boardArray[i-1][j] = 0;
							}
						}
					}
					break;
				}
				case "left":{
					for(int i = 0; i < boardArray.length; i++) {
						for(int j = 0; j < boardArray.length-1; j++) {
							if(boardArray[i][j] == boardArray[i][j+1]) {
								boardArray[i][j] += (boardArray[i][j+1]);
								score += boardArray[i][j];
								if(boardArray[i][j] > highest) {
									highest = boardArray[i][j];
								}
								boardArray[i][j+1] = 0;
							}
						}
					}
					break;
				}
				case "right":{
					for(int i = 0; i < boardArray.length; i++) {
						for(int j = boardArray.length -1; j > 0; j--) {
							if(boardArray[i][j] == boardArray[i][j-1]) {
								boardArray[i][j] += (boardArray[i][j-1]);
								score += boardArray[i][j];
								if(boardArray[i][j] > highest) {
									highest = boardArray[i][j];
								}
								boardArray[i][j-1] = 0;
							}
						}
					}
					break;
				}
				default: System.out.print("\nMove function direction error.\n");
				}
				boardArray = shift(boardArray,d);
				return boardArray;
				
			}

		}
