package com.DevonaWard.theLayout;


import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


public class theLayout {

	
	//Linear Layout
	public static LinearLayout layoutWithButton(Context context, String buttonTxt){
		LinearLayout linearLayout = new LinearLayout(context);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		linearLayout.setLayoutParams(layoutParams);
				
		//Main button created
		Button button = new Button(context);
		button.setText(buttonTxt);
		button.setId(1);

		linearLayout.addView(button);

		return linearLayout;
	}

}
