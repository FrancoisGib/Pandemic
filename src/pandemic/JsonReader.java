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
	private HashMap<String, Integer> towns;
	private HashMap<String, List<String>> neighbors;
	private String filename;
	private FileReader reader;
	private JSONObject phonebook;

	public JsonReader(String filename) throws FileNotFoundException {
		this.filename = filename;
		this.towns = new HashMap<String, Integer>();
		this.neighbors = new HashMap<String, List<String>>();
		this.reader = new FileReader(filename);
		this.phonebook = new JSONObject(new JSONTokener(reader));
	}

	/**
	 * @return Map with corresponding to each town with a value that is a Sector
	 */
	public void setTowns() {
		Iterator<String> entries = phonebook.keys();
		while (entries.hasNext()) {
			String entryKey = entries.next();
			JSONObject entry = phonebook.getJSONObject(entryKey);
			Iterator<String> datakeys = entry.keys();
			while (datakeys.hasNext()) {
				if (entryKey.equals("cities")) {
					towns.put(datakeys.next(), entry.getInt(datakeys.next()));
				}
			}
		}
	}

	public HashMap<String, Integer> getTowns() {
		return this.towns;
	}

	/**
	 * @return a map with keys corresponding to each town with a value that is a
	 *         list of neighbors
	 */
	public void setNeighbors() {
		Iterator<String> entries = phonebook.keys();
		while (entries.hasNext()) {
			String entryKey = entries.next();
			JSONObject entry = phonebook.getJSONObject(entryKey);
			Iterator<String> datakeys = entry.keys();
			while (datakeys.hasNext()) {
				if (entryKey.equals("neighbors")) {
					JSONArray x = entry.getJSONArray(datakeys.next());
					List<String> list = new ArrayList<String>();
					for (int i = 0; i < x.length(); i++) {
						String value = (String) x.get(i);
						list.add(value);
					}
					neighbors.put(datakeys.next(), list);
				}
			}
		}
	}

	public HashMap<String, List<String>> getNeighbors() {
		return this.neighbors;
	}


	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
}
