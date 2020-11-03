// Class deals with exception caused from trying to input a record into our dictionary that already exists in the dictionary
public class DuplicatedKeyException extends Exception {
	
	public DuplicatedKeyException() {
		super("There is already data stored at that key");
	}
}
