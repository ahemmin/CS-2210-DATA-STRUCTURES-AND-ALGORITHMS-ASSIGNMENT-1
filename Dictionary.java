import java.util.LinkedList;

// Class outlines the characteristics of objects of type Dictionary including what operations can be performed on them

public class Dictionary implements DictionaryADT {
	private int x = 37;
	private int M;
	private int number_of_records = 0;
	private LinkedList<Data>[] table;

// Constructor of class initializes our hash table of user specified size with null (creating an empty hash table)
	
public Dictionary(int size) {
	table = new LinkedList[size];
	for (int i = 0; i < size ; i++) {
		table[i] = null;
	}
	M = size;
}

// Method puts objects of type Data into our linked list as a specified value determined by the key of the Data object utilizing our hash function polynomialHashFunction() and specifies if a collision has occurred

public int put(Data record) throws DuplicatedKeyException {
	LinkedList<Data> linked_list_at_location_in_table;
	int location_in_table = polynomialHashFunction(record.getKey(),x,M);
	
	// Actions to be performed if the position in the hash table determined by the hash function is currently empty
	
	if (table[location_in_table] == null) {   
		linked_list_at_location_in_table = new LinkedList<Data>();
		linked_list_at_location_in_table.addLast(record);
		table[location_in_table] = linked_list_at_location_in_table;
		number_of_records ++;	
		return 0;
	}
	
	// Actions to be performed if the position in the hash table determined by the hash function already stores an object of type Data with the exact same key
	
	if (get(record.getKey()) != null) {
		throw new DuplicatedKeyException();
	}
	
	// Actions to be performed if the position in the table determined by the hash function is not empty but also doesn't already store an object with the same key as the object you want to put in the list
	
	table[location_in_table].addLast(record);
	number_of_records ++;
	return 1;	
	}

// Method removes the Data object containing the user's specified key

public void remove(String key) throws InexistentKeyException {
	int location_of_data2;
	int position_in_linked_list = 0;
	LinkedList<Data> linked_list_at_location_in_table2;
	int indicator2 = 0;
	
	location_of_data2 = polynomialHashFunction(key, x, M);
	
	// Actions to be performed if the position in the hash table determined by the hash function is empty or there exists no object of type Data that stores the user's specified key of the object they want to remove
	
	if (table[location_of_data2] == null | get(key) == null) {
		throw new InexistentKeyException();
	}
	// Actions to be performed if there is an object that exists in the hash table that has that key associated with it
	
	Data current_data = table[location_of_data2].get(position_in_linked_list);
	while (!current_data.getKey().equals(key)) {
		position_in_linked_list++;
		current_data = table[location_of_data2].get(position_in_linked_list);
	}
	table[location_of_data2].remove(position_in_linked_list);
}

// Method returns the Data object associated with the user's desired key

public Data get(String key) {
	int location_in_table2;
	LinkedList<Data> linked_list_at_location_in_table3;
	location_in_table2 = polynomialHashFunction(key, x, M);
	linked_list_at_location_in_table3 = table[location_in_table2];
	
	// Actions to be performed if position in the hash table determined by the hash function is empty
	
	if (linked_list_at_location_in_table3 == null) {
		return null;
	}
	for (int i = 0; i < linked_list_at_location_in_table3.size(); i++) {
		
		// Actions to be performed if there does exist an object of type Data associated with the user's specified key
		
		if (linked_list_at_location_in_table3.get(i).getKey().equals(key)) {
			return linked_list_at_location_in_table3.get(i);
		}
	}
	return null;		
}

// Method returns the number of records currently stored in the hash table

public int numDataItems() {
	return number_of_records;	
}

// Helper method converts key into integer value and then calculates the position where any given key should be stored in the hash table using a polynomial function

private int polynomialHashFunction(String str, int x, int M) {
	int integer_equivalent;
	int length_of_string = str.length();
	integer_equivalent = (int)str.charAt(length_of_string-1);
	for(int i = length_of_string - 2; i >= 0; i--) {
		integer_equivalent = (integer_equivalent*x + (int)str.charAt(i)) % M;
	}
	return integer_equivalent;
}
}

