package challenge.waterquality;

import java.util.HashMap;
import java.util.Map;

import challenge.waterquality.DVObject;

public class DVMap {
	//Private Variables for Class - currKey should change to most recent value
	private String currKey = null;
	private Map<String,DVObject> hmap = new HashMap<String,DVObject>();
	
	//Initialize New DV Map
	public void addEntry(String newTime){
		//If Time Already Exists (Unlikely) Avoid Collision
		if(hmap.containsKey(newTime)){
			this.currKey = newTime;
		//Else Initialize New DV Object for it
		}else{
			DVObject newDVObject = new DVObject();
			this.currKey = newTime;
			this.hmap.put(newTime, newDVObject);
		}
	}
	
	//Add Daily Value For
	public void addDV(String dailyValue){
		//Get Current DV Object and Update
		DVObject currObject = this.hmap.get(this.currKey);
		currObject.setDV(dailyValue);
		this.hmap.put(this.currKey, currObject);
	}
	
	//Get HashMap
	public Map<String,DVObject> getMap(){
		return this.hmap;
	}
}
