package androidbaby.hkweather;

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
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

public class widget_stratservice extends Service{
	
	
	
	RemoteViews updateviews;
	ComponentName mAppWidgetId;
	AppWidgetManager appWidgetManager;
	PendingIntent pendingIntent;
	public static String temp,tempper, uv, sampleicon, warn1, warn2, warn3, warn4,localc;
	static SimpleDateFormat df_time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	static int setwarn;
	
	public void onStart(Intent intent,int stratId){
				
		//讀取資訊
		Context context = getApplicationContext();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String widget_update = prefs.getString("widget_update","10800000");
		int updatetime = Integer.parseInt(widget_update);
		String widget_b = prefs.getString("widget_background","3");
		int background = Integer.parseInt(widget_b);
		
		
		//利用AlarmManager下次更新
		Intent intentup = new Intent(widget_stratservice.this,widget_autorun.class);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 1, intentup , 0);
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.add(Calendar.SECOND, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), updatetime , alarmIntent);
		

		
		//取得weatherwidget 資訊(widget ID , widget Name)
		//如果用於自身中
		//RemoteViews updateViews = buildUpdate(this);
		//否則用
		updateviews = new RemoteViews(this.getPackageName(),R.layout.weatherwidget);
		if(background==1){
			updateviews = new RemoteViews(this.getPackageName(),R.layout.widget1);
		}else if (background==2){
			updateviews = new RemoteViews(this.getPackageName(),R.layout.widget2);
		}else if (background==3){
			updateviews = new RemoteViews(this.getPackageName(),R.layout.widget3);
		}else if (background==4){
			updateviews = new RemoteViews(this.getPackageName(),R.layout.widget4);			
		}else{
			updateviews = new RemoteViews(this.getPackageName(),R.layout.widget1);
	 
		}
		

		ComponentName thisWidget = new ComponentName(this, widget_hkweather.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		//把資訊填寫到變數中
		appWidgetManager = manager;
		mAppWidgetId = thisWidget;
			
		
        //Widget更新
        Intent intent1 = new Intent(this, main.class);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0, intent1, 0);
        updateviews.setOnClickPendingIntent(R.id.sample_icon, pendingIntent1);   
		 	
        //Widget更新
        Intent intent2 = new Intent("hkweather.update.SendAction");
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 0, intent2, 0);
        updateviews.setOnClickPendingIntent(R.id.update, pendingIntent2);   
        

        //更新
		appWidgetManager.updateAppWidget(mAppWidgetId, updateviews);
		autoupdate(context, intent);
		
	}



	
	
	
	//自動更新
	public static void autoupdate(final Context context,Intent intent){
		
			Vector<String> weatherno = new Vector<String>();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			
		try {
			
			//讀取資訊
			String widget_b = prefs.getString("widget_background","3");
			int background = Integer.parseInt(widget_b);
			//建立記錄更新時間
			java.sql.Date date = new java.sql.Date(System.currentTimeMillis());	
			String allsystemTime = df_time.format(date);
			Editor editor = prefs.edit();
        	editor.putString("widget_test_update", "Last widget update on strat :" + allsystemTime);
        	editor.commit();
        	
        	
        	//選擇Layout
			RemoteViews updateviews = new RemoteViews(context.getPackageName(),R.layout.weatherwidget);
			if(background==1){
				updateviews = new RemoteViews(context.getPackageName(),R.layout.widget1);
			}else if (background==2){
				updateviews = new RemoteViews(context.getPackageName(),R.layout.widget2);
			}else if (background==3){
				updateviews = new RemoteViews(context.getPackageName(),R.layout.widget3);
			}else if (background==4){
				updateviews = new RemoteViews(context.getPackageName(),R.layout.widget4);			
			}else{
				updateviews = new RemoteViews(context.getPackageName(),R.layout.widget1);
		 
			}
			
			
			//取得自 widget_hkweather Widget 
			ComponentName thisWidget = new ComponentName(context, widget_hkweather.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(context);

	        //進入頁面
	        Intent intent1 = new Intent(context, main.class);
	        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent1, 0);
	        updateviews.setOnClickPendingIntent(R.id.sample_icon, pendingIntent1);   
			 	
	        //Widget更新
	        Intent intent2 = new Intent("hkweather.update.SendAction");
	        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent2, 0);
	        updateviews.setOnClickPendingIntent(R.id.update, pendingIntent2);   
			

	        String uri = "http://www.weather.gov.hk/textonly/forecast/englishwx.htm";
	        HttpGet httpRequest = new HttpGet(uri);
	        HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
	        
	        if(httpResponse.getStatusLine().getStatusCode() == 200){
	        		
	        	String strResult = EntityUtils.toString(httpResponse.getEntity());        
	        	
	        	//溫度
	        	String begin1 = "Air Temperature : ";
	        	String end1 =  " degrees Celsius";

	        	int sindex1 = strResult.indexOf(begin1) + begin1.length();
	        	int eindex1 = strResult.indexOf(end1,sindex1);
	        		
	        	if ( sindex1 != -1 && eindex1 != -1 ){	
	        			        		
	        		Pattern p = Pattern.compile("[0-9]+");
	        		Matcher m = p.matcher(strResult.substring(sindex1,eindex1));
	        	
	        	 	while (m.find()) {
	        	 		temp = m.group();
	        	 	}
	        	}
	        	
	        	/*
	        	 * Pattern 應用
	        	 * 
	        	 *  Pattern p = Pattern.compile("[A-Za-z]+");
	        	 *  Matcher m = p.matcher("Hello, Android!");
	        	 *  while (m.find()) {
	        	 *   System.out.println(m.group()); // prints "Hello" and "Android"
	        	 *    }
	        	 */

	        	
	        	//濕度
	        	String begin2 = "Relative Humidity : ";
	        	String end2 = " per cent";
	        	
	        	int sindex2 = strResult.indexOf(begin2) + begin2.length();
	        	int eindex2 = strResult.indexOf(end2,sindex2);

	        	if ( sindex2 != -1 && eindex2 != -1 ){	
	        		
	        		Pattern p2 = Pattern.compile("[0-9]+");
	        		Matcher m2 = p2.matcher(strResult.substring(sindex2,eindex2));
	        	
	        	 	while (m2.find()) {
	        	 		tempper = m2.group();
	        	 	}
	        	}
	        	
	        	
	        	//濕度
	        	uv = "0";
	        	
	        	String begin3 = "During the past hour the mean UV Index recorded at King's Park : ";
	        	String end3 = "Intensity of UV";
	        	
	        	int sindex3 = strResult.indexOf(begin3) + begin3.length();
	        	int eindex3 = strResult.indexOf(end3,sindex3);

	        	if ( sindex3 != -1 && eindex3 != -1 ){	
	        		
	        		Pattern p3 = Pattern.compile(".+");
	        		Matcher m3 = p3.matcher(strResult.substring(sindex3,eindex3));
	        	
	        	 	while (m3.find()) {
	        	 		uv = m3.group();
	        	 	}
	        	}
	        	
	        	
			
        	//圖示
        	String begin4 = "Weather Cartoon : No. ";
        	String end4 = " -";
        	
        	int sindex4 = strResult.indexOf(begin4) + begin4.length();
        	int eindex4 = strResult.indexOf(end4,sindex4);

        	if ( sindex4 != -1 && eindex4 != -1 ){	
        		
        		Pattern p4 = Pattern.compile("[0-9]+");
        		Matcher m4 = p4.matcher(strResult.substring(sindex4,eindex4));
        	
        	 	while (m4.find()) {
        	 		sampleicon = m4.group();
        	 	}
        	}
	        }
        	
        	
	        //警告圖示
	        String uri2 = "http://www.weather.gov.hk/textonly/warning/warn.htm";
	        HttpGet httpRequest2 = new HttpGet(uri2);
	        HttpResponse httpResponse2 = new DefaultHttpClient().execute(httpRequest2);
	        
	        if(httpResponse2.getStatusLine().getStatusCode() == 200){

	        	updateviews.setImageViewResource( R.id.warn1 , R.drawable.a40);
	        	updateviews.setImageViewResource( R.id.warn2 , R.drawable.a40);
	        	updateviews.setImageViewResource( R.id.warn3 , R.drawable.a40);
	        	updateviews.setImageViewResource( R.id.warn4 , R.drawable.a40);
	        	
	        	
	        	
	        	String strResult2 = EntityUtils.toString(httpResponse2.getEntity());  
	        	String begin = "Warning Codes\n";
	        	String end =  "\n-->";
	        	
	        	int sindex = strResult2.indexOf(begin) + begin.length();
	        	int eindex = strResult2.indexOf(end,sindex);
	        	
	        	if ( sindex != -1 && eindex != -1 ){	
	        			        		
	        		Pattern p = Pattern.compile("[0-9]+");
	        		Matcher m = p.matcher(strResult2.substring(sindex,eindex));
	        	
	        	 	while (m.find()) {
	        	 		weatherno.add(m.group());
	        		}
	        	 	
	        	 	
	        		for ( int i = 0; i < weatherno.size(); i++) {
	        			
	        			//System.out.println("Weather " + i + " = " + weatherno.get(i));

	    	        	int warn_no = Integer.parseInt(weatherno.get(i));
	    	        	
	    	        	if (i==0){
	    	        		setwarn = R.id.warn1;

	    	        	}else if (i==1){
	    	        		setwarn = R.id.warn2;

	    	        	}else if (i==2){
	    	        		setwarn = R.id.warn3;

	    	        	}else{
	    	        		setwarn = R.id.warn4;

	    	        	}	    	        	
	    	        	
	    	        	switch (warn_no) {
	    	        		case 1:	updateviews.setImageViewResource( setwarn , R.drawable.a1); break;
	    	        		case 2:	updateviews.setImageViewResource( setwarn , R.drawable.a2); break;
	    	        		case 3:	updateviews.setImageViewResource( setwarn , R.drawable.a3); break;
	    	        		case 4:	updateviews.setImageViewResource( setwarn , R.drawable.a4); break;
	    	        		case 5:	updateviews.setImageViewResource( setwarn , R.drawable.a5); break;
	    	        		case 6:	updateviews.setImageViewResource( setwarn , R.drawable.a6); break;
	    	        		case 7:	updateviews.setImageViewResource( setwarn , R.drawable.a7); break;
	    	        		case 8:	updateviews.setImageViewResource( setwarn , R.drawable.a8); break;
	    	        		case 9:	updateviews.setImageViewResource( setwarn , R.drawable.a9); break;
	    	        		case 10:updateviews.setImageViewResource( setwarn , R.drawable.a10); break;
	    	        		case 11:updateviews.setImageViewResource( setwarn , R.drawable.a11); break;
	    	        		case 12:updateviews.setImageViewResource( setwarn , R.drawable.a12); break;
	    	        		case 13:updateviews.setImageViewResource( setwarn , R.drawable.a13); break;
	    	        		case 14:updateviews.setImageViewResource( setwarn , R.drawable.a14); break;
	    	        		case 15:updateviews.setImageViewResource( setwarn , R.drawable.a15); break;
	    	        		case 16:updateviews.setImageViewResource( setwarn , R.drawable.a16); break;
	    	        		case 17:updateviews.setImageViewResource( setwarn , R.drawable.a17); break;
	    	        		case 18:updateviews.setImageViewResource( setwarn , R.drawable.a18); break;
	    	        		case 19:updateviews.setImageViewResource( setwarn , R.drawable.a19); break;
	    	        		case 20:updateviews.setImageViewResource( setwarn , R.drawable.a20); break;
	    	        		case 21:updateviews.setImageViewResource( setwarn , R.drawable.a21); break;
	    	        		case 40:updateviews.setImageViewResource( setwarn , R.drawable.a40); break;

	    	        		}
	        			}
	        	}
			
			
        	editor.putString("widget_test_update", "Last widget update strat download : " + allsystemTime);
        	editor.commit();
        	

			updateviews.setTextViewText(R.id.temp, temp.toString() + "°C ");
			updateviews.setTextViewText(R.id.tempper, tempper.toString() + "% ");
			updateviews.setTextViewText(R.id.uv, uv.toString() +"UV");
			
			manager.updateAppWidget(thisWidget, updateviews);	
			
			
			int icon_no = Integer.parseInt(sampleicon);
			switch (icon_no) {
			case 40 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.transparent); break;
			case 41 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.transparent); break;

			case 50 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c1); break;
			case 51 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c2); break;
			case 52 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c2); break;
			case 53 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c3); break;
			case 54 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c4); break;

			case 60 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c5); break;
			case 61 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c5); break;
			case 62 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c6); break;
			case 63 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c7); break;
			case 64 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c8); break;
			case 65 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c9); break;

			case 70 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c12); break;
			case 71 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c12); break;
			case 72 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c12); break;
			case 73 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c10); break;
			case 74 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c12); break;
			case 75 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c12); break;
			case 76 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c11); break;
			case 77 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c12); break;

			case 80 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c13); break;
			case 81 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c14); break;
			case 82 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c14); break;
			case 83 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c14); break;
			case 84 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c14); break;
			case 85 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c14); break;

			case 90 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c1); break;
			case 91 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c2); break;
			case 92 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c15); break;
			case 93 : updateviews.setImageViewResource(R.id.sample_icon, R.drawable.c15); break;
			
			}
			
			
	     }
			manager.updateAppWidget(thisWidget, updateviews);		
        	editor.putString("widget_test_update", "Last widget updated: " + allsystemTime);
        	editor.commit();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block		
				Log.e("androidbaby widget","Error :" + e);
				
				java.sql.Date date = new java.sql.Date(System.currentTimeMillis());	
				String allsystemTime = df_time.format(date);
				Editor editor = prefs.edit();
	        	editor.putString("widget_test_update", "Error widget update: " + allsystemTime + "/nError Code :" + e);
	        	editor.commit();				
		}
		
	}
	
	
	

	
	
	
	//移除
	public static void remove(Context context,Intent intent){
		
		Intent intentup = new Intent(context,widget_autorun.class);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 2, intentup , PendingIntent.FLAG_NO_CREATE);
		
		AlarmManager am;
		am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
		am.cancel(alarmIntent);
		
		Log.e("Hkweather widget","AlarmManager remove");
		
	}




		@Override
		public IBinder onBind(Intent arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	
	
	
	
	
}
