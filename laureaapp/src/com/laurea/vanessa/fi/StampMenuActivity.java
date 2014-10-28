/* 
 * Created by Nghi Le Vinh
 * 25/06/2014
 */

package com.laurea.vanessa.fi;


import com.example.test.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
	 
	public class StampMenuActivity extends Activity implements OnClickListener {
		//UI Elements
		private Button addBtn,removeBtn,resetBtn;
		private TextView resultTextView;
		private ImageView s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
		private ImageView imgView[]={s1,s2,s3,s4,s5,s6,s7,s8,s9,s10};
		private int imgViewLayout[]={R.id.s1,R.id.s2,R.id.s3,R.id.s4,R.id.s5,
							R.id.s6,R.id.s7,R.id.s8,R.id.s9,R.id.s10};
		int opacityImgid = R.drawable.stamp_icon_opacity50;
		int Imgid = R.drawable.stamp_icon;
		int freeImgid = R.drawable.stamp_icon_free;
		
		//Functional Elements
		private static final String INTERNAL_RESULT_NUMBER="internalResultNumber";
		private static final String EXTERNAL_RESULT_NUMBER="externalResultNumber"; //read only
		SharedPreferences internal;
		SharedPreferences external;
		SharedPreferences.Editor internalEditor;
		SharedPreferences.Editor externalEditor;
		private int result;
		
		@Override	
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.stamp_menu_activity);
			addBtn = (Button)findViewById(R.id.add);
			removeBtn = (Button)findViewById(R.id.substract);
			resetBtn= (Button)findViewById(R.id.reset);
			resultTextView = (TextView) findViewById(R.id.textView1);
			addBtn.setOnClickListener(this);
			removeBtn.setOnClickListener(this);
			resetBtn.setOnClickListener(this);
			initImageViews();
			internal = getPreferences(MODE_PRIVATE);
			displayText();
			displayStamps();
	    }	
	    
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
		    case R.id.substract:
		    	refreshDisplay("-");
		    	break;	
		    case R.id.add:
		    	refreshDisplay("+");
		        break;
		    case R.id.reset:
		    	refreshDisplay("reset");
		        break;
		    }	
		}
		
		
		/*
		 *  Custom Functions	
		 */
	    private void refreshDisplay(String operation){ //refreshDisplay
	    	getResultFromOperation(operation);
	    	savePreferences();
			displayText();
			displayStamps();
	    }
	    
	    private void initImageViews(){
	    	for(int i=0;i<10;i++){
				imgView[i]=(ImageView) findViewById(imgViewLayout[i]);
			}
	    }
	    
	    public void displayStamps(){
	    	//the number of result equals to the number of faded imageViews
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
	    
	    public void displayText(){
	    	String staffNoticeString="";
	    	result=internal.getInt(INTERNAL_RESULT_NUMBER, -1);
			if(result==-1){
				staffNoticeString="Click reset to activate the stamp coupons";
			}else{
		    	staffNoticeString = "Coffee bought: "+Integer.toString(result);
				if(result==9){
					staffNoticeString+=". 1 for free.";
				}
			}
			resultTextView.setText(staffNoticeString);
	    }
	    
	    public void savePreferences(){
	    	internalEditor = internal.edit();
	    	internalEditor.putInt(INTERNAL_RESULT_NUMBER, result);
			internalEditor.commit();
			
			//save into file
			external = getSharedPreferences("mytho", MODE_PRIVATE);
			externalEditor = external.edit();
			String encryptedResult=SecurityHelper.encryptNumberOfStampIcon(result);
			externalEditor.putString(EXTERNAL_RESULT_NUMBER, encryptedResult);
			externalEditor.commit();
	    }

		private void getResultFromOperation(String operation) {
			//get current result
	    	result=internal.getInt(INTERNAL_RESULT_NUMBER, 0);
	    	
	    	//calculate result based on operation
	    	if (operation=="+"){
	    		if(result==0){
	    			result=0;
	    		}else if(result==-1){
	    			result=9; //the staff mistakenly press "-" so if they press "+" again they regain 1 coupon items again
	    		}else{
	    			result--;
	    		}
	    	}
	    	
	    	if (operation=="-"){
	    		if(result==9 || result==-1){
	    			result=-1;
	    		}else{
	    			result++;
	    		}
	    	}
	    	
	    	if (operation=="reset"){
	    		result=0;
	    	}
		}

		
	}
