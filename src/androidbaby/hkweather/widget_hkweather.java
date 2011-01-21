package androidbaby.hkweather;


import java.text.SimpleDateFormat;
import java.util.Timer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;

import android.content.Context;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.Toast;




public class widget_hkweather extends AppWidgetProvider {

	static Timer timer,timer2;
	static String s="3600000";
	static int ctime;
	public ImageButton update;
	static SimpleDateFormat df_hour = new SimpleDateFormat("mm");
	
	int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	
	
	
	public static void onUpdate(Context context,AppWidgetManager appWidgetManager, int mAppWidgetId) {
		// TODO Auto-generated method stub
		

		Intent i = new Intent(context,widget_stratservice.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(i);
	
	}
	
	
	
	
	//更新整個Widget
	public void onEnabled(Context context){

		Intent i = new Intent(context,widget_stratservice.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(i);
		
		
	}
	

	
    //接收信息
	  public void onReceive(Context context,Intent intent){
		  
		  	  
		  //更新hkweather Widget按鈕指令
	      if(intent.getAction().equals("hkweather.update.SendAction")){

	    	  Toast.makeText(context, "hkweather widget update..." , Toast.LENGTH_SHORT).show();
	    	  widget_stratservice.autoupdate(context,intent);
	    	  
		      }
	      
	      
	      //移除Widget
	      if(intent.getAction().equals("android.appwidget.action.APPWIDGET_DELETED")){
	    	  	onDeleted(context, intent);
		      }


	      //最後一個widget移除
	      if(intent.getAction().equals("android.appwidget.action.APPWIDGET_DISABLED")){
		      	onDisabled(context);
		      }	      
	      
	      //更新widget
	      if(intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")){
	    	  	onEnabled(context);
	      }	 


	      
	      
	  }	
	
	
	
	
	
	
	
	
	//一個widget移除
	public void onDeleted(Context context,Intent intent) {
		Log.e("HKweather widget","onDeleted");	
		
		widget_stratservice.remove(context, intent);
		
		//停止重新開機後載入Services
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    	Editor editor = prefs.edit();
    	editor.putBoolean("widget", false);
    	editor.putString("widget_test_update", "");
    	editor.commit();	
    	    	
    	context.stopService(new Intent(context,widget_stratservice.class));


	}
	

	//最後一個widget移除
	public void onDisabled(Context context,Intent intent) {
		Log.e("HKweather widget","onDisabled");	

		widget_stratservice.remove(context, intent);
		
		//停止重新開機後載入Services
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    	Editor editor = prefs.edit();
    	editor.putBoolean("widget", false);
    	editor.putString("widget_test_update", "");
    	editor.commit();	
    	    	
    	context.stopService(new Intent(context,widget_stratservice.class));

    	
		
	}	
	

	
	
	
	
	
	

	
	


      



		
	
	
	
}
    
