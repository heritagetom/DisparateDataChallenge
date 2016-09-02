package challenge.waterquality;

import java.util.List;

public class DVObject {
	private Double dailyValue = null;
	private String siteName = null;
	private String DVUnit = null;
	private List<Double> location = null;
	
	public void setDV(String newDailyValue){
		this.dailyValue = Double.parseDouble(newDailyValue);	
	}
	
	public Double getDV(){
		return this.dailyValue;
	}
	
	public void setSiteName(String newSiteName){
		this.siteName = newSiteName;
	}
	
	public String getSiteName(){
		return this.siteName;
	}
	
	public void setDVUnit(String newDVunit){
		this.DVUnit = newDVunit;
	}
	
	public String getDVUnit(){
		return this.DVUnit;
	}
	
	public void setLocation(String lat, String lon){
		this.location.add(Double.parseDouble(lat));
		this.location.add(Double.parseDouble(lon));
	}
	
	public List<Double> getLocation(){
		return this.location;
	}

}
