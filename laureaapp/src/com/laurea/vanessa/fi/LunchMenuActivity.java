/* 
 * Created by Elena Mihaleva
 * 31/03/2014
 */
package com.laurea.vanessa.fi;


import android.annotation.SuppressLint;
//Indicates that Lint should ignore the specified warnings for the annotated element.
import android.content.Intent;
//  the Activity class takes care of creating a window for you in which you can place your UI 
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
// A mapping from String values to various Parcelable types.
import android.webkit.WebView;
// This class is the basis upon which you can roll your own web browser or simply display some online content within your Activity.
import com.example.test.R;

public class LunchMenuActivity extends ActionBarActivity {
	
	// passing the information into the Intent's constructor

	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		/* Calling the super.onCreate() method , 
		 *  tells the VM to run the code in addition to the 
		 * existing code in the onCreate() of the parent class.
		 */	
		super.onCreate(savedInstanceState);
		
		//setting your layout from activity_webvie.xml file
		
		setContentView(R.layout.lunch);
		  // enable the home button
	   // android.app.ActionBar actionBar = getActionBar();
	   // actionBar.setHomeButtonEnabled(true);
	   // actionBar.setDisplayHomeAsUpEnabled(true);
		
		/* creating an object webView from class WebView 
		 *  to which is assigned the id webView of the object 
		 *   created in the xml file */
		
		WebView webView = (WebView)findViewById(R.id.webView1);
		
		// Setting the initial scale for this WebView.
		webView.setInitialScale(1);
		 // Enabling JavaScript
		webView.getSettings().setJavaScriptEnabled(true);
		// Enabling Overview mode
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);// enable wide view port
		
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);// used to hide strange scroll bar.
		webView.setScrollbarFadingEnabled(false);//disable the scrollbar
		
		// the object webView is loading the url
		webView.loadUrl("http://www.jamix.fi/ruokalistat/app?anro=97090"); 
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu); //action bar
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    	case android.R.id.home:
	        Intent intent = new Intent(this, MainActivity.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(intent);
	        finish();
	        return true;
	        case R.id.lunch:
		    	this.startActivity(new Intent(this, LunchMenuActivity.class));
		    	finish();
	            return true;
	        case R.id.coffee:
	        	this.startActivity(new Intent(this, BeverageInfoActivity.class));
	        	finish();
	            return true;
	        case R.id.contact:
	        	this.startActivity(new Intent (this, ContactInfoActivity.class));
	        	finish();
	            return true;	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
}

