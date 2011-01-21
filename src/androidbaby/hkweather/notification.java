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
    		
    		//讀取資訊
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
            
    		
    		//生效警告字句
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
	    	    			case 1: warnt = "一號戒備信號"; break;
	    	    			case 2: warnt = "三號強風信號"; break;
	    	    			case 3: warnt = "八號東北烈風或暴風信號"; break;
	    	    			case 4: warnt = "八號西北烈風或暴風信號"; break;
	    	    			case 5: warnt = "八號東南烈風或暴風信號"; break;
	    	    			case 6: warnt = "八號西南烈風或暴風信號"; break;
	    	    			case 7: warnt = "九號烈風或暴風增強信號"; break;
	    	    			case 8: warnt = "十號颶風信號"; break;
	    	    			case 9: warnt = "黃色暴雨警告信號"; break;
	    	    			case 10: warnt = "紅色暴雨警告信號"; break;
	    	    			case 11: warnt = "黑色暴雨警告信號"; break;
	    	    			case 12: warnt = "雷暴警告"; break;
	    	    			case 13: warnt = "新界北部水浸特別報告"; break;
	    	    			case 14: warnt = "山泥傾瀉警告"; break;
	    	    			case 15: warnt = "強烈季候風信號"; break;
	    	    			case 16: warnt = "霜凍警告"; break;
	    	    			case 17: warnt = "黃色火災危險警告"; break;
	    	    			case 18: warnt = "紅色火災危險警告"; break;
	    	    			case 19: warnt = "寒冷天氣警告"; break;
	    	    			case 20: warnt = "酷熱天氣警告"; break;
	    	    			case 21: warnt = "海嘯警告"; break;
	    	    			case 40: warnt = "null"; break;
	    	        		}

	    	        	}else if (i==2){
		    	        	switch (warn_no) {
	    	    			case 1: warnt2 = "一號戒備信號"; break;
	    	    			case 2: warnt2 = "三號強風信號"; break;
	    	    			case 3: warnt2 = "八號東北烈風或暴風信號"; break;
	    	    			case 4: warnt2 = "八號西北烈風或暴風信號"; break;
	    	    			case 5: warnt2 = "八號東南烈風或暴風信號"; break;
	    	    			case 6: warnt2 = "八號西南烈風或暴風信號"; break;
	    	    			case 7: warnt2 = "九號烈風或暴風增強信號"; break;
	    	    			case 8: warnt2 = "十號颶風信號"; break;
	    	    			case 9: warnt2 = "黃色暴雨警告信號"; break;
	    	    			case 10: warnt2 = "紅色暴雨警告信號"; break;
	    	    			case 11: warnt2 = "黑色暴雨警告信號"; break;
	    	    			case 12: warnt2 = "雷暴警告"; break;
	    	    			case 13: warnt2 = "新界北部水浸特別報告"; break;
	    	    			case 14: warnt2 = "山泥傾瀉警告"; break;
	    	    			case 15: warnt2 = "強烈季候風信號"; break;
	    	    			case 16: warnt2 = "霜凍警告"; break;
	    	    			case 17: warnt2 = "黃色火災危險警告"; break;
	    	    			case 18: warnt2 = "紅色火災危險警告"; break;
	    	    			case 19: warnt2 = "寒冷天氣警告"; break;
	    	    			case 20: warnt2 = "酷熱天氣警告"; break;
	    	    			case 21: warnt2 = "海嘯警告"; break;
	    	    			case 40: warnt2 = "null"; break;
	    	        	
	    	        		}

	    	        	}else if (i==3){
		    	        	switch (warn_no) {
	    	    			case 1: warnt3 = "一號戒備信號"; break;
	    	    			case 2: warnt3 = "三號強風信號"; break;
	    	    			case 3: warnt3 = "八號東北烈風或暴風信號"; break;
	    	    			case 4: warnt3 = "八號西北烈風或暴風信號"; break;
	    	    			case 5: warnt3 = "八號東南烈風或暴風信號"; break;
	    	    			case 6: warnt3 = "八號西南烈風或暴風信號"; break;
	    	    			case 7: warnt3 = "九號烈風或暴風增強信號"; break;
	    	    			case 8: warnt3 = "十號颶風信號"; break;
	    	    			case 9: warnt3 = "黃色暴雨警告信號"; break;
	    	    			case 10: warnt3 = "紅色暴雨警告信號"; break;
	    	    			case 11: warnt3 = "黑色暴雨警告信號"; break;
	    	    			case 12: warnt3 = "雷暴警告"; break;
	    	    			case 13: warnt3 = "新界北部水浸特別報告"; break;
	    	    			case 14: warnt3 = "山泥傾瀉警告"; break;
	    	    			case 15: warnt3 = "強烈季候風信號"; break;
	    	    			case 16: warnt3 = "霜凍警告"; break;
	    	    			case 17: warnt3 = "黃色火災危險警告"; break;
	    	    			case 18: warnt3 = "紅色火災危險警告"; break;
	    	    			case 19: warnt3 = "寒冷天氣警告"; break;
	    	    			case 20: warnt3 = "酷熱天氣警告"; break;
	    	    			case 21: warnt3 = "海嘯警告"; break;
	    	    			case 40: warnt3 = "null"; break;
	    	        	
	    	        		}

	    	        	}else{
		    	        	switch (warn_no) {
	    	    			case 1: warnt4 = "一號戒備信號"; break;
	    	    			case 2: warnt4 = "三號強風信號"; break;
	    	    			case 3: warnt4 = "八號東北烈風或暴風信號"; break;
	    	    			case 4: warnt4 = "八號西北烈風或暴風信號"; break;
	    	    			case 5: warnt4 = "八號東南烈風或暴風信號"; break;
	    	    			case 6: warnt4 = "八號西南烈風或暴風信號"; break;
	    	    			case 7: warnt4 = "九號烈風或暴風增強信號"; break;
	    	    			case 8: warnt4 = "十號颶風信號"; break;
	    	    			case 9: warnt4 = "黃色暴雨警告信號"; break;
	    	    			case 10: warnt4 = "紅色暴雨警告信號"; break;
	    	    			case 11: warnt4 = "黑色暴雨警告信號"; break;
	    	    			case 12: warnt4 = "雷暴警告"; break;
	    	    			case 13: warnt4 = "新界北部水浸特別報告"; break;
	    	    			case 14: warnt4 = "山泥傾瀉警告"; break;
	    	    			case 15: warnt4 = "強烈季候風信號"; break;
	    	    			case 16: warnt4 = "霜凍警告"; break;
	    	    			case 17: warnt4 = "黃色火災危險警告"; break;
	    	    			case 18: warnt4 = "紅色火災危險警告"; break;
	    	    			case 19: warnt4 = "寒冷天氣警告"; break;
	    	    			case 20: warnt4 = "酷熱天氣警告"; break;
	    	    			case 21: warnt4 = "海嘯警告"; break;
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
            	
	               	//生效警告文字
	               	notification = warnt + " " + warnt2 + " " + warnt3 +  " " + warnt4 + " 現正生效";
	               	
	                String savetext = warnt + warnt2 + warnt3 + warnt4;
	               	String loadtext = prefs.getString("notification_text", "");                      	
	               	String notification_text_update = prefs.getString("notification_text_update", ""); 
	               	
	               	
	               	//相同的警告會取消	
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
        	   	//沒有生效警告時

            	String loadtext = prefs.getString("notification_text", "");
         	   
	               	//生效警告有否除去		    	            	   
	               	if (loadtext != "" ){
	               		
	    	          	String notification = "所有生效警告已經取消";
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

			
	  		//更新失敗  
			Context context = getApplicationContext();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			Editor editor = prefs.edit();
			
			java.sql.Date date2 = new java.sql.Date(System.currentTimeMillis());	
			String allsystemTime2 = df_time.format(date2);
        	editor.putString("notification_text_update","Last Service " + allsystemTime2 + " Error:" + e);
        	editor.commit();
			
			if (notification_error == 1){
				notification = "更新失敗 ";
	        	shownotification(notification,notification_sound,notification_shake);	
			}
			
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			
			
	  		//更新失敗  
			Context context = getApplicationContext();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			Editor editor = prefs.edit();
			
			java.sql.Date date2 = new java.sql.Date(System.currentTimeMillis());	
			String allsystemTime2 = df_time.format(date2);
        	editor.putString("notification_text_update","Last Service " + allsystemTime2 + " Error:" + e);
        	editor.commit();
			
			if (notification_error == 1){
				notification = "更新失敗 ";
	        	shownotification(notification,notification_sound,notification_shake);	
			}

		
    	} catch (Exception e) {
    	
    	  		//更新失敗  
    			Context context = getApplicationContext();
    			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    			Editor editor = prefs.edit();
    			
    			java.sql.Date date2 = new java.sql.Date(System.currentTimeMillis());	
    			String allsystemTime2 = df_time.format(date2);
            	editor.putString("notification_text_update","Last Service " + allsystemTime2 + " Error:" + e);
            	editor.commit();
    			
    			if (notification_error == 1){
    				notification = "更新失敗 ";
    	        	shownotification(notification,notification_sound,notification_shake);	
    			}
    	        
    			
    	}
    	

    	
    	
	}


		  /* show notification*/	
		  public void shownotification(String notification,int notification_sound,int notification_shake){

		      PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, shownotification.class), 0);
		        /* 建立設定相關參數 */ 	      
			    Notification myNote = new Notification();
		        /* 設定顯示的icon */	      
			    myNote.icon=R.drawable.icon;
		        /* 設定顯示的文字訊息 */
			    myNote.tickerText = notification;
		        /* 發出預設聲音 */
			    if (notification_sound == 1){
			    	myNote.defaults = Notification.DEFAULT_SOUND;
			    }
			    /* 設定震動 */
			    if (notification_shake == 1){
			    	myNote.vibrate = (new long[]{0,500,200,600});
			    }
		        /* 設定留言條的參數 */
		      	myNote.setLatestEventInfo(this, "香港天氣", notification, contentIntent);
		        /* 送出Notification */
		      	mNM.notify(notify, myNote);      	
		  }	
		
	
			
			@Override
			public void onDestroy() {
				/* 關閉服務 */
				//notificationHandler.removeCallbacks(mTasks);
				super.onDestroy();
			}
			
			
			
			@Override
			public IBinder onBind(Intent arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			
			
			
			

			

}
