package androidbaby.hkweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class widget_autorun extends BroadcastReceiver{
	
	

	
	  @Override 
	    public void onReceive( Context context, Intent intent )
	    {
		  

		  widget_stratservice.autoupdate(context, intent);
		  

	    }



}