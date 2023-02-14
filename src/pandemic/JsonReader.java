package pandemic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonReader {
	
	private HashMap<String,Integer> towns;
	private HashMap<String,List<String>> neighbors;
	private String filename;
	private FileReader reader;
	private JSONObject phonebook;
	
	public JsonReader(String filename){
		try {
			this.filename = filename;
			this.towns = new HashMap<String,Integer>();
			this.neighbors = new HashMap<String,List<String>>();
			this.reader = new FileReader(filename);
		    this.phonebook = new JSONObject(new JSONTokener(reader));
		}catch(FileNotFoundException e) {
			System.out.println("File Not Found");
		}
	    }
	/**
	 * Sets all the towns and their sector into an hashmap
	 * 
	 * @return Map with corresponding to each town with a value that is a Sector
	 */
	public void setTowns(){
		Iterator<String> entries = phonebook.keys();
		String entryKey = entries.next();
		JSONObject entry = phonebook.getJSONObject(entryKey);
		Iterator<String> datakeys = entry.keys();
		while (datakeys.hasNext()) {
			String data = datakeys.next();
			towns.put(data,entry.getInt(data));
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
	 * 
	 * @return a map with keys corresponding to each town with a value that is a list of neighbors.
	 */
	public void setNeighbors(){
    	
		
		Iterator<String> entries = phonebook.keys();
		entries.next();
	    String entryKey = entries.next();
	    JSONObject entry = phonebook.getJSONObject(entryKey);
	    
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
	public HashMap<String,List<String>> getNeighbors() {
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


