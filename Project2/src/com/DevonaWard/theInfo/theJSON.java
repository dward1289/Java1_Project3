package com.DevonaWard.theInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class theJSON {

	public static JSONObject buildJSON() {

		// Teams JSONObject
		JSONObject teamsObject = new JSONObject();

		try {
			// Query JSONObject
			JSONObject queryObject = new JSONObject();

			// Team objects in query

			for (theData team : theData.values()) {

				// Team full name object
				JSONObject fullNameObjects = new JSONObject();

				// Add teams to object
				fullNameObjects.put("full_name", team.setFullName());
				fullNameObjects.put("site_name", team.setSiteName());
				fullNameObjects.put("division", team.setDivision());
				fullNameObjects.put("abbreviation", team.setAbbreviation());

				queryObject.put(team.name().toString(), fullNameObjects);

			}

			// Add query to teams
			teamsObject.put("query", queryObject);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teamsObject;
	}

	public static String readJSON(String selected) {

		String results,team,site,divisions,abrevi;
		
		JSONObject object = buildJSON();
		
		try {
			team = object.getJSONObject("query").getJSONObject(selected).getString("full_name");
			site = object.getJSONObject("query").getJSONObject(selected).getString("site_name");
			divisions = object.getJSONObject("query").getJSONObject(selected).getString("division");
			abrevi = object.getJSONObject("query").getJSONObject(selected).getString("abbreviation");
		results = "Team: " + team + "\r\n"
				+"Arena: " + site + "\r\n"
				+"Division: " + divisions + "\r\n"
				+"Abbreviation: " + abrevi + "\r\n";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			results = e.toString();
		}
		
		return results;
	}

}
