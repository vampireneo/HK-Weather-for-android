package androidbaby.hkweather;

import java.text.SimpleDateFormat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class notification_autorun extends BroadcastReceiver{
	
	
	
	static SimpleDateFormat df_time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	
	
	  @Override 
	    public void onReceive( Context context, Intent intent )
	    { 
		  
			//Log.e("hkweather2", "notification_autorun");


				//Åª¨ú¸ê°T
				//SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
				//Editor editor = prefs.edit();
				
				//java.sql.Date date = new java.sql.Date(System.currentTimeMillis());	
				//String allsystemTime = df_time.format(date);
		    	//editor.putString("notification_text_update", "Last Service updated:" + allsystemTime);
		    	//editor.commit();
		    	
		    	
				Intent i = new Intent(context,notification.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startService(i);

		  
	    }



}