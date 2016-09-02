package challenge.waterquality;

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
	private String mainbase = "http://waterservices.usgs.gov/nwis/dv/";
	private String format = "?format=rdb";
	private String state = null;
	private String site = null;
	private String huc = null;
	private String county = null;
	private String bbox = null;
	
	//Set Site Attribute - Major Filter
	public void addSite(String[] siteNumber){
		try{
			String sitestring=null;
			//If only one site
			if(siteNumber.length==1){
				sitestring = "&site="+siteNumber[0];
			//Else, multiple sites
			}else{
				sitestring = "&sites=";
				for(int i=0;i<siteNumber.length;i++){
					sitestring+=siteNumber[i]+',';
				}
				//Remove last remaining comma
				sitestring = sitestring.substring(0, sitestring.length()-1);
			}
			//Remove last comma
			this.site = sitestring;	
		}catch(NullPointerException e){
			this.site = null;
		}	
	}
	
	//Set state attribute - Major Filter
	public void addState(String stateId){
		try{
			String statestring = "&stateCd="+stateId;
			this.state = statestring;	
		}catch(NullPointerException e){
			this.state=null;
		}
	}
	
	//Set HUC Code - Major Filter
	public void addHuc(Integer majorHUC, Integer minorHUC){
		try{
			String hucstring = "&huc="+Integer.toString(majorHUC)+","+Integer.toString(minorHUC);
			this.huc = hucstring;
		}catch(NullPointerException e){
			this.huc=null;
		}
	}
	
	//Set Lat/Long Input - Major Filter
	public void addBbox(Double[] latlong){
		try{
			//Reformat Strings to either 1 or 0 decimal places
			String newwest = new DecimalFormat("#.#").format(latlong[0]);
			String neweast = new DecimalFormat("#.#").format(latlong[1]);
			String newsouth = new DecimalFormat("#,#").format(latlong[2]);
			String newnorth = new DecimalFormat("#,#").format(latlong[3]);
			
			String bboxstring = "&bBox="+newwest+','+newsouth+','
					+neweast+','+newnorth;
			this.bbox = bboxstring;	
		}catch(NullPointerException e){
			this.bbox=null;
		}
	}
	
	public void addCounty(Integer county){
		try{
			String countystring = "&countyCd="+Integer.toString(county);
			this.county = countystring;
		}catch(NullPointerException e){
			this.county = null;
		}
	}	
	public void addFormat(String type){
		try{
			if(type.equals("xml")){
				String formatstring = "?format=waterml";
				this.format = formatstring;
			}else{
				String formatstring = "?format=rdb";
				this.format = formatstring;
			}
		}catch(NullPointerException e){
			this.format = "?format=rdb";
		}
	}
	
	public String getPath(){
		String outString = this.mainbase+this.format;
		outString = easyAppend(outString,this.state);
		outString = easyAppend(outString,this.county);
		outString = easyAppend(outString,this.site);
		outString = easyAppend(outString,this.huc);
		outString = easyAppend(outString,this.bbox);
		return outString;
	}
	
	private String easyAppend(String input1, String input2){
		String outstring=null;
		if(input2!=null){
			outstring = input1+input2; 
		}else{
			outstring = input1;
		}
		return outstring;
	}
}
