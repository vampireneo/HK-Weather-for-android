package androidbaby.hkweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/* ��user�I��Notification�d�����ɡA�|���檺Activity */ 
public class shownotification extends Activity {
	  @Override 
	  protected void onCreate(Bundle savedInstanceState)
	  {  
	    super.onCreate(savedInstanceState); 
		Intent intent = new Intent();
		intent.setClass(this, warn.class);
		startActivity(intent);
		shownotification.this.finish();
	  } 
}