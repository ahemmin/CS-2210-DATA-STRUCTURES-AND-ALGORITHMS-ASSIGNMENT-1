// Class defines the board and the objects on it and uses them to determine the state of the game

public class Evaluate {
	private char[][] gameBoard;
	private int tilesNeeded;
	private int boardRows;
	private int boardColumns;
	private int win_likelihood;
	private int M = 9991;
	
// Constructor initializes instance variables and creates an empty game board (indicated by the g's)
	
public Evaluate(int boardRows, int boardColumns, int tilesNeeded, int maxLevels) {
	this.tilesNeeded = tilesNeeded;
	win_likelihood = maxLevels;
	this.boardRows = boardRows;
	this.boardColumns = boardColumns;
	gameBoard = new char[boardRows][boardColumns];
	for(int i = 0; i < this.boardRows; i++) {
		for(int j = 0; j < this.boardColumns; j++) {
			gameBoard[i][j] = 'g';
		}
	}
}

// Method creates an object of type Dictionary of the user's specified size

public Dictionary createDictionary() {
	return new Dictionary(M);
}

// Method checks the object of type dictionary to see if a game using that game board has already been played before

public Data repeatedConfig(Dictionary dict) {
	String empty_string = "";
	for(int i = 0; i < this.boardRows; i++) {
		for(int j = 0; j < this.boardColumns; j++) {
			empty_string = empty_string + gameBoard[i][j];
		}
	}
	return dict.get(empty_string);	
}

// Method puts new game boards into the object of type Dictionary if they do not already exist in the Dictionary object

public void insertConfig(Dictionary dict, int score, int level) {
	String empty_string = "";
	for(int i = 0; i < this.boardRows; i++) {
		for(int j = 0; j < this.boardColumns; j++) {
			empty_string = empty_string + gameBoard[i][j];
		}
	}
	Data record = new Data(empty_string,score,level);
	
	// Catches if the record already exists in the Dictionary object
	
	try {
		dict.put(record);
	}
	catch (DuplicatedKeyException e) {
		
	}
	}

// Method stores and user specified symbol into a user specified location in the game board

public void storePlay(int row, int col, char symbol) {
	gameBoard[row][col] = symbol;
}

// Method checks to see if a user specified position on the game board is currently empty

public boolean squareIsEmpty(int row, int col) {
	boolean indicator1 = false;
	if (gameBoard[row][col] == 'g') {
		indicator1 = true;
	}
	return indicator1;
}

// Method checks to see if a user specified position on the game board is currently containing one of the computer's tiles

public boolean tileOfComputer(int row, int col) {
	boolean indicator2 = false;
	if (gameBoard[row][col] == 'o') {
		indicator2 = true;
	}
	return indicator2;
}

// Method checks to see if a user specified position on the game board is currently containing one of the human's tiles

public boolean tileOfHuman(int row, int col) {
	boolean indicator3 = false;
	if (gameBoard[row][col] == 'b') {
		indicator3 = true;
	}
	return indicator3;
}

// Method determines if game has been won or not by checking if there exists a sequence of tiles large enough to win the game

public boolean wins(char symbol) {
	
	// Determines if there exists and winning sequence of symbols
	
	if (checkRow(this.tilesNeeded)== symbol) {
		return true;
	}
	if (checkCol(this.tilesNeeded)== symbol) {
		return true;
	}
	if (checkDiagonalsToRight(this.tilesNeeded)== symbol) {
		return true;
	}
	if (checkDiagonalsToLeft(this.tilesNeeded)== symbol) {
		return true;
	}
	return false;
}

// Method determines if the game is drawn by checking that all the positions on the game board are occupied by player tiles

public boolean isDraw() {
	boolean indicator4 = true;
	for(int i = 0; i < this.boardRows; i++) {
		for(int j = 0; j < this.boardColumns; j++) {
			
			// Determines whether there are no longer any open positions on the game board left
			
			if(gameBoard[i][j] == 'g') {
				indicator4 = false;
			}			
		}
	}
	return indicator4;
}

// Method evaluates the current state of the board (if someone has won, its a draw or the game is still ongoing)

public int evalBoard() {
	
	// Determines if computer wins
	
	if (wins('o')) {
		return 3;
	}
	
	// Determines if human wins
	
	if (wins('b')) {
		return 0;
	}
	
	// Determines if the game is drawn since neither player obtained a sufficient sequence of symbols to win the game
	
	if (isDraw()) {
		return 2;		
	}
	
	// Shows that the game is still in progress
	
		return 1;
}

// Helper method checks all rows to see if there exists a sequence of the same symbol long enough to match the required number to win the game

private char checkRow(int tilesNeeded) {
	char symbol;
	int counter = 0;
	int k;
	for(int i = 0; i < this.boardRows; i++) {
		for(int j = 0; j < this.boardColumns; j++) {
			symbol = gameBoard[i][j];
			counter = 1;
			k = 0;
			
			//Iterates through row to determine if there is a sufficient number of identical symbols to win the game
			
			while ((j+1+k) < this.boardColumns && symbol == gameBoard[i][j+1+k]) {
				counter++;
				k++;
			}
			// Returns symbol if indeed there is a sufficient number of symbols to win game
			
			if (counter == tilesNeeded) {
				return symbol;
			}
			}
			}
	return 'f';
}

// Helper method checks all columns to see if there exists a sequence of the same symbol long enough to match the required number to win the game

private char checkCol(int tilesNeeded) {
	char symbol;
	int counter = 0;
	for(int i = 0; i < this.boardRows; i++) {
		for(int j = 0; j < this.boardColumns; j++) {
			symbol = gameBoard[i][j];
			counter = 1;
			int k = 0;
			
			//Iterates through column to determine if there is a sufficient number of identical symbols to win the game
			
			while ((i+1+k) < this.boardRows && symbol == gameBoard[i+1+k][j]) {
				counter++;
				k++;
			}
			// Returns symbol if indeed there is a sufficient number of symbols to win game
			if (counter == tilesNeeded) {
				return symbol;
			}
			}
			}
	return 'f';
}

// Helper method checks all diagonals that go down and to the right to see if there exists a sequence of the same symbol long enough to match the required number to win the game

private char checkDiagonalsToRight(int tilesNeeded) {
	char symbol;
	int counter = 0;
	for(int i = 0; i < this.boardRows; i++) {
		for(int j = 0; j < this.boardColumns; j++) {
			symbol = gameBoard[i][j];
			counter = 1;
			int k = 0;
			
			//Iterates through down and right diagonals to determine if there is a sufficient number of identical symbols to win the game
			
			while ((i+1+k) < this.boardRows && (j+1+k) < this.boardColumns && symbol == gameBoard[i+1+k][j+1+k]) {
				counter++;
				k++;
			}
			
			// Returns symbol if indeed there is a sufficient number of symbols to win game
			
			if (counter == tilesNeeded) {
				return symbol;
			}
			}
			}
	return 'f';
}

//Helper method checks all diagonals that go down and to the left to see if there exists a sequence of the same symbol long enough to match the required number to win the game

private char checkDiagonalsToLeft(int tilesNeeded) {
	char symbol;
	int counter = 0;
	for(int i = 0; i < this.boardRows; i++) {
		for(int j = 0; j < this.boardColumns; j++) {
			symbol = gameBoard[i][j];
			counter = 1;
			int k = 0;
			
			//Iterates through down and left diagonals to determine if there is a sufficient number of identical symbols to win the game
			
			while ((i+1+k) < this.boardRows && (j-1-k) > 0 && symbol == gameBoard[i+1+k][j-1-k]) {
				counter++;
				k++;
			}
			
			// Returns symbol if indeed there is a sufficient number of symbols to win game
			
			if (counter == tilesNeeded) {
				return symbol;
			}
			}
			}
	return 'f';
}
}
