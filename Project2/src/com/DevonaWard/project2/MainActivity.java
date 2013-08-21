package com.DevonaWard.project2;

import java.net.MalformedURLException;
import java.net.URL;

import com.DevonaWard.theInfo.theJSON;
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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import android.widget.TextView;



import android.widget.LinearLayout;


public class MainActivity extends Activity {
	Context context;
	String[] teamCity;
	RadioGroup cityOptions;
	TextView resultView;
	TextView infoView;
	Boolean _connected = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//City_Array count
		int teamNum = 4;
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
				/*This will get the ID of the selected radio button,
				 * and display the data from theJSON in the resultView.
				 */
				int id = cityOptions.getCheckedRadioButtonId();
				RadioButton selectedRadio = (RadioButton)findViewById(id);
				String selected = selectedRadio.getText().toString();
				resultView.setText(theJSON.readJSON(selected));
				
				getTeam(selected);
				
			}
			
		});

		//Detecting network connection
				_connected = TestConnection.getConnectionStatus(context);
				if(_connected){
					Log.i("Network Connection: ",TestConnection.getConnectionType(context));
				}
				else{
					Log.i("",ConnectionError.getConnectionType(context));
				}


		//TextView created
		TextView txtView = new TextView(context);
		txtView.setText("Select from the list of "+ teamNum +" cities.");
		
		//Populates radio buttons using teamCity.	
		cityOptions = theLayout.getOpions(this, teamCity);
		
		//Displays JSON results
		resultView = new TextView(context);
		
		//Displays a cool from a condition
		infoView = new TextView(context);
				
		linearLayout.addView(txtView);
		linearLayout.addView(cityOptions);
		linearLayout.addView(mainLayout);
		linearLayout.addView(resultView);
		linearLayout.addView(infoView);

		
		setContentView(linearLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Get URL
	private void getTeam(String team){
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
		//Show data in JSON format in Log
		@Override
		protected void onPostExecute(String result){
			Log.i("URL RESPONSE", result);
		}
	}
}






