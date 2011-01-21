package androidbaby.hkweather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class broadcastReceiver extends BroadcastReceiver{

	private int reshow = 0;
	private int notification = 0;
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		//
		//�}���ƥ�
        if(intent.getAction().equals( Intent.ACTION_BOOT_COMPLETED )){

    		notification = prefs.getInt("notification",0);
    		reshow = prefs.getInt("notification_reshow",0); 
        	
        //	
        //	�q��
    	//
        		if (notification == 1){
        			
        			if (reshow == 1){
        				Editor editor = prefs.edit();
        				editor.putString("notification_text", "");
        				editor.commit();
						        			
						}
        			
        			Intent i = new Intent(context,notification_stratservice.class);
        			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        			context.startService(i);
				
        		}else{

        			//context.stopService(new Intent(context,notification.class));
        			
        		}
        		
        		
        		
        		
        		//
        		//
        		//	Widget�}��
        		Boolean widget = prefs.getBoolean("widget",false); 
        		
        		if (widget == true){
        			
        			Intent k = new Intent(context,widget_stratservice.class);
        			k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        			context.startService(k);
        			
        		}else{
						//context.stopService(new Intent(context,widget_stratservice.class));    				
        		}
        	
        	
        }
		
		
        
        
        
        
        
	}	
}
