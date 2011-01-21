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
	
	
	
	
	//��s���Widget
	public void onEnabled(Context context){

		Intent i = new Intent(context,widget_stratservice.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(i);
		
		
	}
	

	
    //�����H��
	  public void onReceive(Context context,Intent intent){
		  
		  	  
		  //��shkweather Widget���s���O
	      if(intent.getAction().equals("hkweather.update.SendAction")){

	    	  Toast.makeText(context, "hkweather widget update..." , Toast.LENGTH_SHORT).show();
	    	  widget_stratservice.autoupdate(context,intent);
	    	  
		      }
	      
	      
	      //����Widget
	      if(intent.getAction().equals("android.appwidget.action.APPWIDGET_DELETED")){
	    	  	onDeleted(context, intent);
		      }


	      //�̫�@��widget����
	      if(intent.getAction().equals("android.appwidget.action.APPWIDGET_DISABLED")){
		      	onDisabled(context);
		      }	      
	      
	      //��swidget
	      if(intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")){
	    	  	onEnabled(context);
	      }	 


	      
	      
	  }	
	
	
	
	
	
	
	
	
	//�@��widget����
	public void onDeleted(Context context,Intent intent) {
		Log.e("HKweather widget","onDeleted");	
		
		widget_stratservice.remove(context, intent);
		
		//����s�}������JServices
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    	Editor editor = prefs.edit();
    	editor.putBoolean("widget", false);
    	editor.putString("widget_test_update", "");
    	editor.commit();	
    	    	
    	context.stopService(new Intent(context,widget_stratservice.class));


	}
	

	//�̫�@��widget����
	public void onDisabled(Context context,Intent intent) {
		Log.e("HKweather widget","onDisabled");	

		widget_stratservice.remove(context, intent);
		
		//����s�}������JServices
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    	Editor editor = prefs.edit();
    	editor.putBoolean("widget", false);
    	editor.putString("widget_test_update", "");
    	editor.commit();	
    	    	
    	context.stopService(new Intent(context,widget_stratservice.class));

    	
		
	}	
	

	
	
	
	
	
	

	
	


      



		
	
	
	
}
    
