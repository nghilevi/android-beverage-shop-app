/* 
 * Created by Nghi Le Vinh
 * 25/06/2014
 */

package com.laurea.vanessa.fi;


import group.pals.android.lib.ui.lockpattern.LockPatternActivity;
import group.pals.android.lib.ui.lockpattern.prefs.SecurityPrefs;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.test.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	//UI Elements
	private ImageButton stampButton;
	private ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9,i10;
	private ImageView imgView[]={i1,i2,i3,i4,i5,i6,i7,i8,i9,i10};
	private int imgViewLayout[]={R.id.m1,R.id.m2,R.id.m3,R.id.m4,R.id.m5,
			R.id.m6,R.id.m7,R.id.m8,R.id.m9,R.id.m10};
	private TextView messageTextView;
	int opacityImgid = R.drawable.stamp_icon_opacity50;
	int Imgid = R.drawable.stamp_icon;
	int freeImgid = R.drawable.stamp_icon_free;
	
	//Functional Elements
	String predefinedPattern = "c8c0b24a15dc8bbfd411427973574695230458f0"; //Lock Pattern
	private static final int REQ_CREATE_PATTERN = 1;
	private static final int REQ_ENTER_PATTERN = 2;
	private static final String EXTERNAL_RESULT_NUMBER="externalResultNumber";
	private SharedPreferences external;
	private int result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		stampButton = (ImageButton)findViewById(R.id.stampButton);
		stampButton.setOnClickListener(this);	
		messageTextView=(TextView)findViewById(R.id.messageTextView);
		initImageViews();
		refreshDisplay(imgView,imgViewLayout);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refreshDisplay(imgView,imgViewLayout); //When the user come back from other activities, esp. StampMenuActivity
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
	        case R.id.lunch:
		    	this.startActivity(new Intent(this, LunchMenuActivity.class));
	            return true;
	        case R.id.coffee:
	        	this.startActivity(new Intent(this, BeverageInfoActivity.class));
	            return true;
	        case R.id.contact:
	        	this.startActivity(new Intent (this, ContactInfoActivity.class));
	            return true;	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onClick(View v) {
		//Open barcode scanner
		switch(v.getId()) {
	    case R.id.stampButton:
	    	IntentIntegrator integrator = new IntentIntegrator(this);              
			integrator.initiateScan();
	        break;
	    }
	}
		
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) { 
	
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		checkQRCode(scanResult);

		switch (requestCode) {
		    case REQ_CREATE_PATTERN: { //Create pattern code is used in development mode. Important! Do not remove!
		        if (resultCode == RESULT_OK) {
		            char[] pattern = data.getCharArrayExtra(LockPatternActivity.EXTRA_PATTERN); //Use toString to display char array in plain text and do a reverse process
		            SecurityPrefs.setAutoSavePattern(this, true);
		            startActivity(new Intent (this, StampMenuActivity.class)); //Start StampMenuActivity
		        }
		        break;
		    }
		    case REQ_ENTER_PATTERN: {
		        switch (resultCode) {
		        case LockPatternActivity.RESULT_OK:
		        	startActivity(new Intent (this, StampMenuActivity.class));// The user passed. Start StampMenuActivity
		            break;
		        case LockPatternActivity.RESULT_CANCELED:
		        	Toast.makeText(getApplicationContext(), "Cancel Authentication Process",Toast.LENGTH_SHORT).show(); // The user cancelled/click back the task
		            break;
		        case LockPatternActivity.RESULT_FAILED:
		        	Toast.makeText(getApplicationContext(), "You have failed many times!",Toast.LENGTH_SHORT).show(); // The user failed to enter the pattern
		            break;
		        case LockPatternActivity.RESULT_FORGOT_PATTERN: 
		        	Toast.makeText(getApplicationContext(), "Do you forget the pattern?",Toast.LENGTH_SHORT).show(); // The user forgot the pattern and invoked your recovery Activity.
		            break;
		        }
		        /*
		         * In any case, there's always a key EXTRA_RETRY_COUNT, which holds
		         * the number of tries that the user did.
		         */
		        int retryCount = data.getIntExtra(
		                LockPatternActivity.EXTRA_RETRY_COUNT, 0);
		        break;
		    }// REQ_ENTER_PATTERN
	    }//Switch
		//finishActivity(requestCode);
	}//onActivityResult

	/*
	 *  Custom Functions	
	 */
	private void checkQRCode(IntentResult scanResult) {
		if (scanResult != null) {	
			String scanContent=scanResult.getContents();
			if(scanContent!=null){	
				if(scanContent.length()<27){ //Check if scanContent.length()<27
					Toast.makeText(getApplicationContext(), "Your QR Code is invalid!",Toast.LENGTH_SHORT).show();
				}else{	
					boolean result =SecurityHelper.decryptQRCode(scanContent);
					if(result){
						openLockPattern();
					}else{
						Toast.makeText(getApplicationContext(), "Your QR Code is invalid!",Toast.LENGTH_SHORT).show();
					}
				}//scanContent.length()<27
			}//scanContent !=null
		}//scanResult !=null
	}
	
	private void refreshDisplay(ImageView imgView[],int imgViewLayout[]){
		getResultFromExternalFile();
		displayText();
		displayStamps();
	}
	
	//assign imageViews
	private void initImageViews(){
    	for(int i=0;i<10;i++){
			imgView[i]=(ImageView) findViewById(imgViewLayout[i]);
		}
    }
	

	public void getResultFromExternalFile(){
		external = getSharedPreferences("mytho",MODE_PRIVATE); 
		String resultString = external.getString(EXTERNAL_RESULT_NUMBER, "-1");//-1 means the user just download the app
		result=SecurityHelper.decryptNumberOfStampIcon(resultString);
	}
	
	public void displayText(){
		String messageString="";
		if(result==-1){
			messageString="Welcome! Please let BarLaurea staff activate coffee stamp for you!";
		}else{
			messageString="You bought "+result+" cups.";
			if(result==9){
				messageString+=" You get 1 for free!";
			}else{
				messageString+=" Buy "+(9-result)+" more to get 1 for free!";
			}
		}
		messageTextView.setText(messageString);
	}
	
	//display stamps icon based on the result variable
	public void displayStamps(){
		if(result==-1){
			for(int i=0;i<10;i++){
	    		imgView[i].setImageResource(opacityImgid);
			}
		}else{
	    	for(int i=0;i<9;i++){
	    		imgView[i].setImageResource(Imgid);
			}
	    	imgView[9].setImageResource(freeImgid);
	    	for(int i=0;i<=result-1;i++){
				imgView[i].setImageResource(opacityImgid);
			}
		}
	}

	
	public void openLockPattern(){
			//char[] savedPattern = SecurityPrefs.getPattern(this); //important!
			char[] savedPattern =predefinedPattern.toCharArray(); //instead of using the savedPattern above, we use a predefined pattern here
			if(savedPattern!=null){
				//Enter Pattern Mode
				Intent enterPatternIntent = new Intent(LockPatternActivity.ACTION_COMPARE_PATTERN, null,
				        this, LockPatternActivity.class);
				enterPatternIntent.putExtra(LockPatternActivity.EXTRA_PATTERN, savedPattern);
				startActivityForResult(enterPatternIntent, REQ_ENTER_PATTERN);
			}else{
				//Create Pattern Mode. important! -- DO NOT REMOVE THIS! -----
				Intent createNewPatternIntent = new Intent(LockPatternActivity.ACTION_CREATE_PATTERN, null,
				        this, LockPatternActivity.class);
				startActivityForResult(createNewPatternIntent, REQ_CREATE_PATTERN);
			}
	}
}

