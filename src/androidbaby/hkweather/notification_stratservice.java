package androidbaby.hkweather;


import java.text.SimpleDateFormat;
import java.util.Calendar;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;





public class notification_stratservice extends Service{

	public static NotificationManager mNM;
	static SimpleDateFormat df_time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private int notification_int_update;

	
	
	public void onStart(Intent intent,int stratId){
		
		
		//Log.e("hkweather2", "notification_stratservice");
				
		//讀取資訊
		Context context = getApplicationContext();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		//Editor editor = prefs.edit();

    	//Log.d("androidbaby.hkweather","notification update");
    	
		//建立記錄更新時間
		//java.sql.Date date = new java.sql.Date(System.currentTimeMillis());	
		//String allsystemTime = df_time.format(date);
    	//editor.putString("notification_text_update", "Service Strat Time:" + allsystemTime);
    	//editor.commit();
    	
    	//讀取更新時間
		notification_int_update = prefs.getInt("notification_update",10800000);

    	
		//利用AlarmManager下次更新
		Intent intentup = new Intent(notification_stratservice.this,notification_autorun.class);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 1, intentup , 0);
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.add(Calendar.SECOND, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), notification_int_update , alarmIntent);
		
		
		
		
		Intent i = new Intent(context,notification.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(i);

		
	}

	
	

	

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
	
	
}
