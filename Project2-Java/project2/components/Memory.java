package project2.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Memory {

	private HashMap<String, List<String>> cells = new HashMap<String, List<String>>();

	public Memory() {
		init();
	}

	private void init() {
		//
	}

	public void load(Collection<RDFDocument> docs) {
		for (RDFDocument doc : docs) {
			for (RDFFact fact : doc.getFacts()) {
				store(fact.getSubject()+":"+fact.getPredicate(), fact.getObject());
			}
		}
	}

	public void loadDelta(Collection<RDFDocument> docs) {
		for (RDFDocument doc : docs) {
			for (RDFFact fact : doc.getFacts()) {
				store(fact.getSubject()+":"+fact.getPredicate(), fact.getObject());
			}
		}
	}

	public void store(String key, String value) {
		if (value == null || value.length() == 0) return;
		
		List<String> values = (List<String>) cells.get(key);
		if (values == null) {
			values = new ArrayList<String>();
		}
		if (!values.contains(value)) {
			values.add(value);
		}
		cells.put(key, values);
	}

	public List<String> recall(String key) {
		List<String> values = cells.get(key);
		if (values == null) {
			values = new ArrayList<String>();
			cells.put(key, values);
		}
		return values;
	}

}
