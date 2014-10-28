/*Created by Elena Mihaleva
 * 31/03/2014
 * Updated: 01.07.2014
*/

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

import com.example.test.R;


public class ContactInfoActivity extends ActionBarActivity{
	String[] name; 
	String[] position;
	String[] phone;
	String[] address;
	ListView list;
	

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		/* Calling the super.onCreate() method , 
		 *  tells the VM to run the code in addition to the 
		 * existing code in the onCreate() of the parent class.
		 */		
		super.onCreate(savedInstanceState);
				
		//setting your layout from activity_webview.xml file

		setContentView(R.layout.contact_info_activity);
		
		 // enable the home button
	   // android.app.ActionBar actionBar = getActionBar();
	  //  actionBar.setHomeButtonEnabled(true);
	  // actionBar.setDisplayHomeAsUpEnabled(true);

		//calling the resources

		Resources res = getResources();
		//assigning the  arrays from strings.xml to the variables name,position,phone and address

		name = res.getStringArray(R.array.contacts);
		position = res.getStringArray(R.array.position);
		phone = res.getStringArray(R.array.phone);
		address = res.getStringArray(R.array.address);
		
		//initializing the images array

		int[] images = {R.drawable.miia_vakkuri, R.drawable.ilari_paananen};
		
		//Finding the listView by id
		list= (ListView)findViewById(R.id.listView2);
		// calling and setting the ListAdapter

		ContactAdapter adapter = new ContactAdapter(this, name, images, position,phone, address);
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

class ContactAdapter extends ArrayAdapter<String> {
	Context context;
	int[] images;
	String[] arrayName;
	String[] arrayPosition;
	String[] arrayPhone;
	String[] arrayAddress;
	
		ContactAdapter(Context c, String[] names, int images[], String[] pos, String[] phone, String[] address) {
		
		super(c, R.layout.single_row,R.id.textView1,names);
		this.context=c;
		this.images=images;
		this.arrayName=names;
		this.arrayPosition=pos;
		this.arrayPhone=phone;
		this.arrayAddress=address;
	}
	
	@SuppressLint("ViewHolder")
	@Override
	
	// Displaying the data at the specified position in the data set by inflate it from an XML layout file.

	public View getView(int position, View convertView, ViewGroup parent){
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.single_row_1, parent, false);
		ImageView myImage = (ImageView) row.findViewById(R.id.imageView1);
		TextView myName = (TextView) row.findViewById(R.id.textView1);
		TextView myPosition = (TextView) row.findViewById(R.id.textView2);
		TextView myPhone = (TextView)row.findViewById(R.id.textView3);
		TextView myAddress = (TextView)row.findViewById(R.id.textView4);

		myImage.setImageResource(images[position]);
		myName.setText(arrayName[position]);
		myPosition.setText(arrayPosition[position]);
		myPhone.setText(arrayPhone[position]);
		myAddress.setText(arrayAddress[position]);
		return row;
	}
}