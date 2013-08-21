package com.DevonaWard.theInfo;

public enum theData {

	Atlanta("Atlanta Hawks","Philips Arena","Southeast","ATL"),
	Boston("Boston Celtics", "TD Garden", "Atlantic","BOS"),
	Brooklyn("Brooklyn Nets", "Barclasy Center", "Atlantic","BKN"),
	Charlotte("Charlotte Bobcats","Time Warner Cable Arena","East","CHA");
	
	private final String full_name;
	private final String site_name;
	private final String division;
	private final String abbreviation;
	
	private theData(String full_name, String site_name, String division, String abbreviation){
		this.full_name = full_name;
		this.site_name = site_name;
		this.division = division;
		this.abbreviation = abbreviation;
	}
	
	public String setFullName(){
		return full_name;
	}
	
	public String setSiteName(){
		return site_name;
	}
	
	public String setDivision(){
		return division;
	}
	
	public String setAbbreviation(){
		return abbreviation;
	}
}
