/* 
 * Created by Nghi Le Vinh
 * 25/06/2014
 */

package com.laurea.vanessa.fi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.util.Log;
import android.widget.Toast;

public class SecurityHelper {
	static char charArray[]={'a','b','c','d','e','f','g','h','k','l'};
	
	public static boolean decryptQRCode(String scanContent){
		//Start decrypting
		String decryptedPart1=	scanContent.charAt(4)+""+
						scanContent.charAt(9)+""+
						scanContent.charAt(14)+""+
						scanContent.charAt(19);
		String decryptedPart2=scanContent.substring(20,28);
		//String yyyyMMdd=new SimpleDateFormat("yyyyMMdd").format(new Date());

		//Check if decrypt QR code sucessfully
		if ((decryptedPart1.equals("RIKI"))) {
			return true;
		}else{
			return false; 
		}//END Check if decrypt QR code sucessfully
	}
	
	public static int decryptNumberOfStampIcon(String encryptedString){
		int result=0;
		if(!encryptedString.equals("0")){
			String resultString= (new StringBuilder(encryptedString).reverse().toString()).charAt(1)+"";
			result=new String(charArray).indexOf(resultString);
		}
		if(encryptedString.equals("-1")){//-1 means the first time the user install this app
			result=-1;
		}
		return result;
	}
	
	public static String encryptNumberOfStampIcon(int result){
		String encryptedString="-1";
		if(result!=-1){//-1 means the first time the user install this app
			int randomNum = 0 + (int)(Math.random()*9); //make a random integer number as salt with min=0, max=9
			encryptedString= UUID.randomUUID().toString()+charArray[result]+randomNum;
		}
		return encryptedString;
	}
}
