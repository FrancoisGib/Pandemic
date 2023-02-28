package pandemic;

import java.io.FileNotFoundException;
import java.io.FileReader;
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
import org.json.JSONObject;
import org.json.JSONTokener;

/** The JsonReader class, it reads the data of a json file */
public abstract class JsonReader {
	
	/** The json file name */
	protected String filename;
	
	/** The file reader of the json file */
	protected FileReader reader;
	
	/** The phonebook of the reader */
	protected JSONObject phonebook;
	
	/**
	 * Builds a JsonReader to read a json file
	 * 
<<<<<<< HEAD
	 * @param filename The name of the file to read
=======
	 * @param filename, the name of the file to read
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
	 * @exception FileNotFoundException The exception if the specified file doesn't exist
	 */
	public JsonReader(String filename) throws FileNotFoundException {
		this.filename = filename;
		this.reader = new FileReader(filename);
		this.phonebook = new JSONObject(new JSONTokener(this.reader));
	}
}


