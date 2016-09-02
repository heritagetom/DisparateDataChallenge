package challenge.humanitarian;

public class Ebola {
	private String country = null;
	private Integer confirmedCases = null;
	private Integer confirmedLast21 = null;
	private Integer confirmedDeaths = null;
	private Integer probableCases = null;
	private Integer probableLast21 = null;
	private Integer probableDeaths = null;
	private Integer suspectedCases = null;
	private Integer suspectedLast21 = null;
	private Integer suspectedDeaths = null;
	
	public void setCountry(String newcountry){
		this.country = newcountry;
	}
	
	public String getCountry(){
		return this.country;
	}
	
	public void setConfirmedCases(String newConfirmedCases){
		Integer intConfirmedCases = Integer.parseInt(newConfirmedCases);
		this.confirmedCases = intConfirmedCases;
	}
	
	public Integer getConfirmedCases(){
		return this.confirmedCases;
	}
	
	public void setConfirmedLast21(String newConfirmed21){
		Integer intConfirmed21 = Integer.parseInt(newConfirmed21);
		this.confirmedLast21 = intConfirmed21;
	}
	
	public Integer getConfimredLast21(){
		return this.confirmedLast21;
	}
	
	public void setConfirmedDeaths(String newConfirmedDeaths){
		Integer intConfirmedDeaths = Integer.parseInt(newConfirmedDeaths);
		this.confirmedDeaths = intConfirmedDeaths;
	}
	
	public Integer getConfirmedDeaths(){
		return this.confirmedDeaths;
	}
	
	public void setProbableCases(String newProbableCases){
		Integer intProbableCases = Integer.parseInt(newProbableCases);
		this.probableCases = intProbableCases;
	}
	
	public Integer getProbableCases(){
		return this.probableCases;
	}
	
	public void setProbableLast21(String newProbable21){
		Integer intProbable21 = Integer.parseInt(newProbable21);
		this.probableLast21 = intProbable21;
	}
	
	public Integer getProbableLast21(){
		return this.probableLast21;
	}
	
	public void setProbableDeaths(String newProbableDeaths){
		Integer intProbableDeaths = Integer.parseInt(newProbableDeaths);
		this.probableDeaths = intProbableDeaths;
	}
	
	public Integer getProbableDeaths(){
		return this.getProbableDeaths();
	}
	
	

}
