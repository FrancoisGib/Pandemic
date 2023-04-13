package pandemic.jsonreader;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
	 * @param filename The name of the file to read
	 * @exception FileNotFoundException The exception if the specified file doesn't exist
	 */
	public JsonReader(String filename) throws FileNotFoundException {
		this.filename = filename;
		this.reader = new FileReader(filename);
		this.phonebook = new JSONObject(new JSONTokener(this.reader));
	}
}


