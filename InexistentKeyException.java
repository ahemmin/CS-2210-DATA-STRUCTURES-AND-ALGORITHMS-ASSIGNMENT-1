// Class deals with exception caused by trying to remove a record associated with a key that does not exist
public class InexistentKeyException extends Exception{
	
	public InexistentKeyException () {
		super("Sorry there is no data stored at that key");
	}
}
