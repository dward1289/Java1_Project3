package com.DevonaWard.project2;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.DevonaWard.theInfo.webInfo;
import com.DevonaWard.theLayout.theLayout;
import com.DevonaWard.thelibrary.ConnectionError;
import com.DevonaWard.thelibrary.TestConnection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemSelectedListener;



public class MainActivity extends Activity {
	Context context;
	String[] teamCity;
	TextView resultView;
	Boolean _connected = false;
	TextView jsonView;
	TextView connectedView;
	ArrayList<String> teamAbbrList = new ArrayList<String>();
	ArrayList<String> teamConList = new ArrayList<String>();
	ArrayList<String> teamDivList = new ArrayList<String>();
	ArrayList<String> teamSiteList = new ArrayList<String>();
	Spinner viewSpinner;
	String teamAbbr;
	String teamCon;
	String teamDiv;
	String teamSite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//City_Array count
		int teamNum = getResources().getStringArray(R.array.city_array).length;
		context = this;
		
		//City Array
		teamCity = getResources().getStringArray(R.array.city_array);
		
		LinearLayout linearLayout = new LinearLayout(this);
		LinearLayout mainLayout = theLayout.layoutWithButton(this, "Submit");
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		linearLayout.setLayoutParams(layoutParams);
		
		//Button created from theData class
		Button mainButton = (Button) mainLayout.findViewById(1);
		mainButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*Get selected team info.*/
				int the = viewSpinner.getSelectedItemPosition();
				String abbr = teamAbbrList.get(the).toString();
				String con = teamConList.get(the).toString();  
				String div = teamDivList.get(the).toString();
				String site = teamSiteList.get(the).toString();
				
				jsonView.setText("Team Abbreviation: "+abbr+"\nTeam Conference: "+con+"\nTeam Division: "+div+"\nTeam Arena: "+site);
			}
			
		});

		//Make web request
		getTeam();
		
		connectedView = new TextView(context);
		
		//Detecting network connection
				_connected = TestConnection.getConnectionStatus(context);
				if(_connected){
					connectedView.setText("Network Connection: " + TestConnection.getConnectionType(context)+"\n");
				}
				else{
					connectedView.setText(""+ConnectionError.getConnectionType(context)+"\n");
				}


		//TextView created
		TextView txtView = new TextView(context);
		txtView.setText("Select from the list of "+ teamNum +" teams.");
		
		//Spinner adapter
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, teamCity);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				//Creating the spinner
				viewSpinner = new Spinner(context);
				viewSpinner.setAdapter(spinnerAdapter);
				layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				viewSpinner.setLayoutParams(layoutParams);
				//Spinner onClick
				viewSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
						Toast.makeText(context, "You selected the " + teamCity[position], Toast.LENGTH_LONG).show();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});

		//Displays JSON results		
	    jsonView = new TextView(context);
	        
		linearLayout.addView(txtView);
		linearLayout.addView(viewSpinner);
		linearLayout.addView(mainLayout);
		linearLayout.addView(connectedView);
		linearLayout.addView(jsonView);
		
		
		setContentView(linearLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Get URL
	private void getTeam(){
		String baseURL = "https://erikberg.com/nba/teams.json";
		URL finalURL;
		try{
			finalURL = new URL(baseURL);
			teamRequest TR = new teamRequest();
			TR.execute(finalURL);
		} catch (MalformedURLException e){
			Log.e("BAD URL", "MALFORMED URL");
			finalURL = null;
		}
	}
	
	//Get data from URL
	private class teamRequest extends AsyncTask<URL, Void, String>{
		@Override
		protected String doInBackground(URL... urls){
			String response = "";
			for(URL url: urls){
				response = webInfo.getURLStringResponse(url);
			}
			return response;
		}
		//Get data and add to arrays.
		@Override
		protected void onPostExecute(String result){
			try {
				JSONArray jsonArray = new JSONArray(result);
				
				int n = jsonArray.length();
				for(int i = 0;i<n; i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					
					teamAbbr= jsonObject.getString("abbreviation");
					teamCon = jsonObject.getString("conference");
					teamDiv = jsonObject.getString("division");
					teamSite= jsonObject.getString("site_name");
					teamAbbrList.add(teamAbbr);
					teamConList.add(teamCon);  
					teamDivList.add(teamDiv);
					teamSiteList.add(teamSite);
				}
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
	}
}






