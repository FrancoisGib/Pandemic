package pandemic.jsonreader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

/** The TownsJsonReader class, it reads the towns' datas of a json file */
public class TownsJsonReader extends JsonReader {
	/** The hashmap of towns in the json file */
	private HashMap<String,Integer> towns;
	
	/** The hashmap of neighbors in the json file */
	private HashMap<String, ArrayList<String>> neighbors;
	
	/**
	 * Builds a JsonReader who read a json file
	 * 
	 * @param filename The name of the file to read
	 * @exception FileNotFoundException The exception if the specified file doesn't exist
	 */
	public TownsJsonReader(String filename) throws FileNotFoundException{
		super(filename);
		this.towns = new HashMap<String,Integer>();
		this.neighbors = new HashMap<String, ArrayList<String>>();
	}
	
	/**
	 * Sets all the towns and their sector into an hashmap
	 */
	public void setTowns(){
		Iterator<String> entries = this.phonebook.keys();
		String entryKey = entries.next();
		JSONObject entry = this.phonebook.getJSONObject(entryKey);
		Iterator<String> datakeys = entry.keys();
		while (datakeys.hasNext()) {
			String data = datakeys.next();
			this.towns.put(data,entry.getInt(data));
		}
	}
	
	/**
	 * Give all towns name and sector into an hashmap
	 * 
	 * @return The hashmap with all towns name and sector
	 */
	public HashMap<String,Integer> getTowns() {
		return this.towns;
	}
	
	/**
	 * Sets all the towns neighbors into an hashmap
	 */
	public void setNeighbors(){
		Iterator<String> entries = this.phonebook.keys();
		entries.next();
	    String entryKey = entries.next();
	    JSONObject entry = this.phonebook.getJSONObject(entryKey);
	    Iterator<String> datakeys = entry.keys();
	    
	    while (datakeys.hasNext()) {
	    	String name = datakeys.next();
	    	ArrayList<String> list = new ArrayList<String>();
	    	JSONArray jsonArray = entry.getJSONArray(name);
	    	for(int i=0; i < jsonArray.length(); i++) {
	    		list.add((String) jsonArray.get(i));
	    	}
	    	this.neighbors.put(name, list);
	    }
	}
	
	/**
	 * Give all towns name and neighbors into an hashmap.
	 * 
	 * @return The hashmap with all towns name and neighbors.
	 */
	public HashMap<String, ArrayList<String>> getNeighbors() {
		return this.neighbors;
	}
	
	/**
	 * Give the json filename.
	 * 
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
}
