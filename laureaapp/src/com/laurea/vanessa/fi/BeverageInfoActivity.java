/*Created by Elena Mihaleva
 * Updated: 01.07.2014*/

package com.laurea.vanessa.fi;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import  com.example.test.R;

public  class BeverageInfoActivity  extends ActionBarActivity  {

	String[] titles; 
	String[] descriptions;
	String[] prices;
	ListView list;
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.beverage_info_activity);
		
		  // enable the home button
	    //android.app.ActionBar actionBar = getActionBar();
	   // actionBar.setHomeButtonEnabled(true);
	   // actionBar.setDisplayHomeAsUpEnabled(true);

		
		//calling the resources
		Resources res = getResources();
		//assigning the  arrays from strings.xml to the variables titles,descriptions,prices
		titles = res.getStringArray(R.array.titles);
		descriptions = res.getStringArray(R.array.descriptions);
		prices = res.getStringArray(R.array.menuPrice);
		
		//initializing the images array
		int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};
		
		//Finding the listView by id

		list= (ListView)findViewById(R.id.listView1);
		// calling and setting the ListAdapter
		ListAdapter adapter = new ListAdapter(this, titles, images, descriptions,prices);
		list.setAdapter(adapter);
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


//ListAdapter class convert the data to Text/Image view

class ListAdapter extends ArrayAdapter<String> {
	Context context;
	int[] images;
	String[] arrayTitles;
	String[] arrayDescription;
	String[] arrayPrices;
	
	//creating the constructor 
		ListAdapter(Context c, String[] titles, int images[], String[] des, String[] price) {
		
		super(c, R.layout.single_row,R.id.textView1,titles);
		this.context=c;
		this.images=images;
		this.arrayTitles=titles;
		this.arrayDescription=des;
		this.arrayPrices=price;
	}
	
	@SuppressLint("ViewHolder")
	@Override
	
	// Displaying the data at the specified position in the data set by inflate it from an XML layout file.
	public View getView(int position, View convertView, ViewGroup parent){
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.single_row, parent, false);
		ImageView myImage = (ImageView) row.findViewById(R.id.imageView1);
		TextView myTitle = (TextView) row.findViewById(R.id.textView1);
		TextView myDescription = (TextView) row.findViewById(R.id.textView2);
		TextView myPrice = (TextView)row.findViewById(R.id.textView3);
		
		myImage.setImageResource(images[position]);
		myTitle.setText(arrayTitles[position]);
		myDescription.setText(arrayDescription[position]);
		myPrice.setText(arrayPrices[position]);
		return row;
	}
}