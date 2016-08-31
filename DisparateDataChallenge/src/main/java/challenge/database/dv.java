package challenge.database;

import java.text.DecimalFormat;

/* 
 * Jacob A. Tutmaher
 * Booz | Allen | Hamilton
 * Aug. 31 2016
 * 
 * This class catalogues attributes 
 * for NGA's Water Quality Database - SubDatabase: Daily Values (dv)
 * See https://www.challenge.gov/challenge/disparate-data-challenge/
 * for more information.
 */
public class dv {
	//These class variable should not change
	private String mainbase = "http://waterservices.usgs.gov/nwis/dv/";
	private String format = "?format=rdb";
	
	//Set base attribute - Major Filter
	public String database(){
		return this.mainbase+this.format;
	}
	
	//Set site attribute - Add Complication for multiple sites - Major Filter
	public String addSite(Integer siteNumber){
		try{
			String sitestring = "&site="+Integer.toString(siteNumber)+'/';
			return sitestring;	
		}catch(NullPointerException e){
			return "";
		}
		
	}
	
	//Set state attribute - Major Filter
	public String addState(String stateId){
		try{
			String statestring = "&stateCd="+stateId+'/';
			return statestring;	
		}catch(NullPointerException e){
			return "";
		}
	}
	
	//Set HUC Code - Major Filter
	public String  addHuc(Integer majorHUC, Integer minorHUC){
		try{
			String hucstring = "&huc="+Integer.toString(majorHUC)+","+Integer.toString(minorHUC)+'/';
			return hucstring;
		}catch(NullPointerException e){
			return "";
		}
	}
	
	//Set Lat/Long Input
	public String addBbox(Double west, Double south, Double east, Double north){
		//Reformat Strings to either 1 or 0 decimal places
		String newwest = new DecimalFormat("#.#").format(west);
		String neweast = new DecimalFormat("#.#").format(east);
		String newsouth = new DecimalFormat("#,#").format(south);
		String newnorth = new DecimalFormat("#,#").format(north);
		
		String bboxstring = "&bBox="+newwest+','+newsouth+','+neweast+','+newnorth+'/';
		return bboxstring;
	}
	
	public String addCounty(Integer county){
		try{
			String countystring = "&countyCd="+Integer.toString(county);
			return countystring;
		}catch(NullPointerException e){
			return "";
		}
	}
}
