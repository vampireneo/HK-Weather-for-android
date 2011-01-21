package androidbaby.hkweather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class notification extends Service{


	public static NotificationManager mNM;
	private static final int notify = 1;
	private Handler notificationHandler = new Handler();
	static SimpleDateFormat df_time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	private String notification,notification_update;
	private int notification_sound,notification_shake,notification_error;
	private int notification_int_update;

	
	
	public void onStart(Intent intent,int stratId){


		Vector<String> weathername = new Vector<String>();
		
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
    	try{
    		
    		//Ū����T
    		Context context = getApplicationContext();
    		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    		Editor editor = prefs.edit();
    		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());	
    		String allsystemTime = df_time.format(date);
    		editor.putString("notification_text_update", "Last Service updated:" + allsystemTime);
    		editor.commit();

    		
    		
    		//notificationHandler.removeCallbacks(mTasks);
    		notification_error = prefs.getInt("notification_error",0);
    		notification_sound = prefs.getInt("notification_sound",0);
    		notification_shake = prefs.getInt("notification_shake",0);

            String warnt = null;
            String warnt2 = null;
            String warnt3 = null;  
            String warnt4 = null; 
            
    		
    		//�ͮ�ĵ�i�r�y
	        String uri2 = "http://www.weather.gov.hk/textonly/warning/warn.htm";
	        HttpGet httpRequest2 = new HttpGet(uri2);
	        HttpResponse httpResponse2 = new DefaultHttpClient().execute(httpRequest2);


	        	String strResult2 = EntityUtils.toString(httpResponse2.getEntity());  
	        	String begin = "Warning Codes\n";
	        	String end =  "-->";
	        	
	        	int sindex = strResult2.indexOf(begin) + begin.length();
	        	int eindex = strResult2.indexOf(end,sindex);
	        	
	        	if ( sindex != -1 && eindex != -1 ){	
	        			        		
	        		Pattern p = Pattern.compile("[0-9]+");
	        		Matcher m = p.matcher(strResult2.substring(sindex,eindex));
	        	
	        	 	while (m.find()) {
	        	 		weathername.add(m.group());
	        		}
	        	 	
	        	 	
	        		for ( int i = 0; i < weathername.size(); i++) {
	        			
	        			System.out.println("Weather " + i + " = " + weathername.get(i));

	    	        	int warn_no = Integer.parseInt(weathername.get(i));
	    	        	
	    	        	if (i==0){
		    	        	switch (warn_no) {
	    	    			case 1: warnt = "�@���ٳƫH��"; break;
	    	    			case 2: warnt = "�T���j���H��"; break;
	    	    			case 3: warnt = "�K���F�_�P���μɭ��H��"; break;
	    	    			case 4: warnt = "�K����_�P���μɭ��H��"; break;
	    	    			case 5: warnt = "�K���F�n�P���μɭ��H��"; break;
	    	    			case 6: warnt = "�K����n�P���μɭ��H��"; break;
	    	    			case 7: warnt = "�E���P���μɭ��W�j�H��"; break;
	    	    			case 8: warnt = "�Q�������H��"; break;
	    	    			case 9: warnt = "����ɫBĵ�i�H��"; break;
	    	    			case 10: warnt = "����ɫBĵ�i�H��"; break;
	    	    			case 11: warnt = "�¦�ɫBĵ�i�H��"; break;
	    	    			case 12: warnt = "�p��ĵ�i"; break;
	    	    			case 13: warnt = "�s�ɥ_�������S�O���i"; break;
	    	    			case 14: warnt = "�s�d���mĵ�i"; break;
	    	    			case 15: warnt = "�j�P�u�ԭ��H��"; break;
	    	    			case 16: warnt = "����ĵ�i"; break;
	    	    			case 17: warnt = "������a�M�Iĵ�i"; break;
	    	    			case 18: warnt = "������a�M�Iĵ�i"; break;
	    	    			case 19: warnt = "�H�N�Ѯ�ĵ�i"; break;
	    	    			case 20: warnt = "�ż��Ѯ�ĵ�i"; break;
	    	    			case 21: warnt = "���Sĵ�i"; break;
	    	    			case 40: warnt = "null"; break;
	    	        		}

	    	        	}else if (i==2){
		    	        	switch (warn_no) {
	    	    			case 1: warnt2 = "�@���ٳƫH��"; break;
	    	    			case 2: warnt2 = "�T���j���H��"; break;
	    	    			case 3: warnt2 = "�K���F�_�P���μɭ��H��"; break;
	    	    			case 4: warnt2 = "�K����_�P���μɭ��H��"; break;
	    	    			case 5: warnt2 = "�K���F�n�P���μɭ��H��"; break;
	    	    			case 6: warnt2 = "�K����n�P���μɭ��H��"; break;
	    	    			case 7: warnt2 = "�E���P���μɭ��W�j�H��"; break;
	    	    			case 8: warnt2 = "�Q�������H��"; break;
	    	    			case 9: warnt2 = "����ɫBĵ�i�H��"; break;
	    	    			case 10: warnt2 = "����ɫBĵ�i�H��"; break;
	    	    			case 11: warnt2 = "�¦�ɫBĵ�i�H��"; break;
	    	    			case 12: warnt2 = "�p��ĵ�i"; break;
	    	    			case 13: warnt2 = "�s�ɥ_�������S�O���i"; break;
	    	    			case 14: warnt2 = "�s�d���mĵ�i"; break;
	    	    			case 15: warnt2 = "�j�P�u�ԭ��H��"; break;
	    	    			case 16: warnt2 = "����ĵ�i"; break;
	    	    			case 17: warnt2 = "������a�M�Iĵ�i"; break;
	    	    			case 18: warnt2 = "������a�M�Iĵ�i"; break;
	    	    			case 19: warnt2 = "�H�N�Ѯ�ĵ�i"; break;
	    	    			case 20: warnt2 = "�ż��Ѯ�ĵ�i"; break;
	    	    			case 21: warnt2 = "���Sĵ�i"; break;
	    	    			case 40: warnt2 = "null"; break;
	    	        	
	    	        		}

	    	        	}else if (i==3){
		    	        	switch (warn_no) {
	    	    			case 1: warnt3 = "�@���ٳƫH��"; break;
	    	    			case 2: warnt3 = "�T���j���H��"; break;
	    	    			case 3: warnt3 = "�K���F�_�P���μɭ��H��"; break;
	    	    			case 4: warnt3 = "�K����_�P���μɭ��H��"; break;
	    	    			case 5: warnt3 = "�K���F�n�P���μɭ��H��"; break;
	    	    			case 6: warnt3 = "�K����n�P���μɭ��H��"; break;
	    	    			case 7: warnt3 = "�E���P���μɭ��W�j�H��"; break;
	    	    			case 8: warnt3 = "�Q�������H��"; break;
	    	    			case 9: warnt3 = "����ɫBĵ�i�H��"; break;
	    	    			case 10: warnt3 = "����ɫBĵ�i�H��"; break;
	    	    			case 11: warnt3 = "�¦�ɫBĵ�i�H��"; break;
	    	    			case 12: warnt3 = "�p��ĵ�i"; break;
	    	    			case 13: warnt3 = "�s�ɥ_�������S�O���i"; break;
	    	    			case 14: warnt3 = "�s�d���mĵ�i"; break;
	    	    			case 15: warnt3 = "�j�P�u�ԭ��H��"; break;
	    	    			case 16: warnt3 = "����ĵ�i"; break;
	    	    			case 17: warnt3 = "������a�M�Iĵ�i"; break;
	    	    			case 18: warnt3 = "������a�M�Iĵ�i"; break;
	    	    			case 19: warnt3 = "�H�N�Ѯ�ĵ�i"; break;
	    	    			case 20: warnt3 = "�ż��Ѯ�ĵ�i"; break;
	    	    			case 21: warnt3 = "���Sĵ�i"; break;
	    	    			case 40: warnt3 = "null"; break;
	    	        	
	    	        		}

	    	        	}else{
		    	        	switch (warn_no) {
	    	    			case 1: warnt4 = "�@���ٳƫH��"; break;
	    	    			case 2: warnt4 = "�T���j���H��"; break;
	    	    			case 3: warnt4 = "�K���F�_�P���μɭ��H��"; break;
	    	    			case 4: warnt4 = "�K����_�P���μɭ��H��"; break;
	    	    			case 5: warnt4 = "�K���F�n�P���μɭ��H��"; break;
	    	    			case 6: warnt4 = "�K����n�P���μɭ��H��"; break;
	    	    			case 7: warnt4 = "�E���P���μɭ��W�j�H��"; break;
	    	    			case 8: warnt4 = "�Q�������H��"; break;
	    	    			case 9: warnt4 = "����ɫBĵ�i�H��"; break;
	    	    			case 10: warnt4 = "����ɫBĵ�i�H��"; break;
	    	    			case 11: warnt4 = "�¦�ɫBĵ�i�H��"; break;
	    	    			case 12: warnt4 = "�p��ĵ�i"; break;
	    	    			case 13: warnt4 = "�s�ɥ_�������S�O���i"; break;
	    	    			case 14: warnt4 = "�s�d���mĵ�i"; break;
	    	    			case 15: warnt4 = "�j�P�u�ԭ��H��"; break;
	    	    			case 16: warnt4 = "����ĵ�i"; break;
	    	    			case 17: warnt4 = "������a�M�Iĵ�i"; break;
	    	    			case 18: warnt4 = "������a�M�Iĵ�i"; break;
	    	    			case 19: warnt4 = "�H�N�Ѯ�ĵ�i"; break;
	    	    			case 20: warnt4 = "�ż��Ѯ�ĵ�i"; break;
	    	    			case 21: warnt4 = "���Sĵ�i"; break;
	    	    			case 40: warnt4 = "null"; break;
	    	        	
	    	        		}
	    	        	}	    	        	
	    	        	
	    	        	
	        		}
	        	}
    		
    		


            if (warnt != null || warnt2 != null || warnt3 != null || warnt4 != null){

	               if (warnt == null){
	            	   warnt = "";
	               }
	               if (warnt2 == null){
	            	   warnt2 = "";
	               }
	               if (warnt3 == null){
	            	   warnt3 = "";
	               }
	               if (warnt4 == null){
	            	   warnt4 = "";
	               }
            	
	               	//�ͮ�ĵ�i��r
	               	notification = warnt + " " + warnt2 + " " + warnt3 +  " " + warnt4 + " �{���ͮ�";
	               	
	                String savetext = warnt + warnt2 + warnt3 + warnt4;
	               	String loadtext = prefs.getString("notification_text", "");                      	
	               	String notification_text_update = prefs.getString("notification_text_update", ""); 
	               	
	               	
	               	//�ۦP��ĵ�i�|����	
	               	if (loadtext.equals(savetext)){

	               	}else{
	               		Editor editor3 = prefs.edit();
	                	editor3.putString("notification_text",savetext);
	                	editor3.commit();
	                	shownotification(notification,notification_sound,notification_shake); 
	               	}
	               	
	               	Editor editor2 = prefs.edit();
	               	editor2.putString("notification_text_update", notification_text_update + "\nNotification update updated: " + allsystemTime + "\n[" + savetext + "]>>[" + loadtext +"]");
	            	editor2.commit();
	               	 	
	               	
	               	
            }else{
        	   	//�S���ͮ�ĵ�i��

            	String loadtext = prefs.getString("notification_text", "");
         	   
	               	//�ͮ�ĵ�i���_���h		    	            	   
	               	if (loadtext != "" ){
	               		
	    	          	String notification = "�Ҧ��ͮ�ĵ�i�w�g����";
	    	          	shownotification(notification,notification_sound,notification_shake);
	    	          	Editor editor3 = prefs.edit();		    	          	
	                	editor3.putString("notification_text", "");
	                	editor3.commit();
	    	          	
	    	          	
	               	}
	               	
	               	Editor editor2 = prefs.edit();
	            	editor2.putString("notification_text_update", "Notification update updated: " + allsystemTime + " [" + loadtext + "]");
	            	editor2.commit();
	            	
            }      	
	                
     
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block

			
	  		//��s����  
			Context context = getApplicationContext();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			Editor editor = prefs.edit();
			
			java.sql.Date date2 = new java.sql.Date(System.currentTimeMillis());	
			String allsystemTime2 = df_time.format(date2);
        	editor.putString("notification_text_update","Last Service " + allsystemTime2 + " Error:" + e);
        	editor.commit();
			
			if (notification_error == 1){
				notification = "��s���� ";
	        	shownotification(notification,notification_sound,notification_shake);	
			}
			
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			
			
	  		//��s����  
			Context context = getApplicationContext();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			Editor editor = prefs.edit();
			
			java.sql.Date date2 = new java.sql.Date(System.currentTimeMillis());	
			String allsystemTime2 = df_time.format(date2);
        	editor.putString("notification_text_update","Last Service " + allsystemTime2 + " Error:" + e);
        	editor.commit();
			
			if (notification_error == 1){
				notification = "��s���� ";
	        	shownotification(notification,notification_sound,notification_shake);	
			}

		
    	} catch (Exception e) {
    	
    	  		//��s����  
    			Context context = getApplicationContext();
    			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    			Editor editor = prefs.edit();
    			
    			java.sql.Date date2 = new java.sql.Date(System.currentTimeMillis());	
    			String allsystemTime2 = df_time.format(date2);
            	editor.putString("notification_text_update","Last Service " + allsystemTime2 + " Error:" + e);
            	editor.commit();
    			
    			if (notification_error == 1){
    				notification = "��s���� ";
    	        	shownotification(notification,notification_sound,notification_shake);	
    			}
    	        
    			
    	}
    	

    	
    	
	}


		  /* show notification*/	
		  public void shownotification(String notification,int notification_sound,int notification_shake){

		      PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, shownotification.class), 0);
		        /* �إ߳]�w�����Ѽ� */ 	      
			    Notification myNote = new Notification();
		        /* �]�w��ܪ�icon */	      
			    myNote.icon=R.drawable.icon;
		        /* �]�w��ܪ���r�T�� */
			    myNote.tickerText = notification;
		        /* �o�X�w�]�n�� */
			    if (notification_sound == 1){
			    	myNote.defaults = Notification.DEFAULT_SOUND;
			    }
			    /* �]�w�_�� */
			    if (notification_shake == 1){
			    	myNote.vibrate = (new long[]{0,500,200,600});
			    }
		        /* �]�w�d�������Ѽ� */
		      	myNote.setLatestEventInfo(this, "����Ѯ�", notification, contentIntent);
		        /* �e�XNotification */
		      	mNM.notify(notify, myNote);      	
		  }	
		
	
			
			@Override
			public void onDestroy() {
				/* �����A�� */
				//notificationHandler.removeCallbacks(mTasks);
				super.onDestroy();
			}
			
			
			
			@Override
			public IBinder onBind(Intent arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			
			
			
			

			

}
