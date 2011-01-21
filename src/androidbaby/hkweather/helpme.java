package androidbaby.hkweather;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class helpme extends Activity {
	
	
	private ImageButton thebutton1,thebutton2,thebutton3,thebutton4,thebutton5,thebutton6;
	protected RadioGroup setting1;
	protected RadioButton update15,update30,update60,noupdate;
	private CheckBox notification_sound,notification_shake,notification_reshow,notification_error;
	private TextView last_service_update,last_widget_update;
	private int notificationsoundshowbox=0;
	

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		//³z©ú¤¶­±
        // Have the system blur any windows behind this one.
        // See assets/res/any/layout/translucent_background.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        
        setContentView(R.layout.helpme);
        setTitle(R.string.app_name);
        
		SharedPreferences cookies = PreferenceManager.getDefaultSharedPreferences(this);
        
		thebutton1 = (ImageButton) findViewById(R.id.button_a);		
		thebutton2 = (ImageButton) findViewById(R.id.button_b);
		thebutton3 = (ImageButton) findViewById(R.id.button_c);
		thebutton4 = (ImageButton) findViewById(R.id.button_d);
		thebutton5 = (ImageButton) findViewById(R.id.button_e);
		thebutton6 = (ImageButton) findViewById(R.id.button_f);    
		
		
		
		last_service_update = (TextView) findViewById(R.id.last_service_update);
		String notification_text_update = cookies.getString("notification_text_update","");
		last_service_update.setText(notification_text_update);
		
		
		last_widget_update = (TextView) findViewById(R.id.last_widget_update);
		String get_widget_text = cookies.getString("widget_test_update","");
		last_widget_update.setText(get_widget_text);
		
		

		
		setting1 = (RadioGroup)findViewById(R.id.setting1);
		noupdate = (RadioButton)findViewById(R.id.noupdate);		
		update15 = (RadioButton)findViewById(R.id.update15);
		update30 = (RadioButton)findViewById(R.id.update30);	
		update60 = (RadioButton)findViewById(R.id.update60);	

		int setting2buttonshow = cookies.getInt("notification_update",10800000);
		if (setting2buttonshow == 900000){
			update15.setChecked(true);
		}else if (setting2buttonshow == 1800000){
			update30.setChecked(true);
		}else if (setting2buttonshow == 3600000){
			update60.setChecked(true);
		}else{
			noupdate.setChecked(true);
		}
		
	    setting1.setOnCheckedChangeListener(listener1);
	    
	    
		
		notification_sound = (CheckBox)findViewById(R.id.notification_sound);
		
		
		notificationsoundshowbox = cookies.getInt("notification_sound",0);
		
		
		if (notificationsoundshowbox == 1){
			notification_sound.setChecked(true);
		}
		
		
		notification_sound.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
					if(notification_sound.isChecked()){
						
						Context context = getApplicationContext();
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						Editor editor = prefs.edit();
		            	editor.putInt("notification_sound", 1);
		            	editor.commit();
		            	
					}else{
						
						Context context = getApplicationContext();
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						Editor editor = prefs.edit();
		            	editor.putInt("notification_sound", 0);
		            	editor.commit();
		            	
					}
			}
		});
		
		notification_shake = (CheckBox)findViewById(R.id.notification_shake);
		int notification_shakeshowbox = cookies.getInt("notification_shake",0);
		if (notification_shakeshowbox == 1){
			notification_shake.setChecked(true);
		}
		notification_shake.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
					if(notification_shake.isChecked()){
						Context context = getApplicationContext();
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						Editor editor = prefs.edit();
		            	editor.putInt("notification_shake", 1);
		            	editor.commit();
					}else{
						Context context = getApplicationContext();
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						Editor editor = prefs.edit();
		            	editor.putInt("notification_shake", 0);
		            	editor.commit();
					}
			}
		});
		
		notification_reshow = (CheckBox)findViewById(R.id.notification_reshow);
		int notification_reshowbox = cookies.getInt("notification_reshow",0);
		if (notification_reshowbox == 1){
			notification_reshow.setChecked(true);
		}
		notification_reshow.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				
					if(notification_reshow.isChecked()){
						Context context = getApplicationContext();
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						Editor editor = prefs.edit();
		            	editor.putInt("notification_reshow", 1);
		            	editor.commit();
		            	
					}else{
						
						Context context = getApplicationContext();
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						Editor editor = prefs.edit();
		            	editor.putInt("notification_reshow", 0);
		            	editor.commit();
		            	
					}
			}
		});

		notification_error = (CheckBox)findViewById(R.id.notification_error);
		int notification_errorbox = cookies.getInt("notification_error",0);
		if (notification_errorbox == 1){
			notification_error.setChecked(true);
		}
		notification_error.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
					if(notification_error.isChecked()){
						Context context = getApplicationContext();
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						Editor editor = prefs.edit();
		            	editor.putInt("notification_error", 1);
		            	editor.commit();
					}else{
						Context context = getApplicationContext();
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						Editor editor = prefs.edit();
		            	editor.putInt("notification_error", 0);
		            	editor.commit();
					}
			}
		});
		
		
	    
        button();
        
        
    }

    
    
    
    
	private void button() {
		// TODO Auto-generated method stub
		
        thebutton1.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(helpme.this, main.class);
				startActivity(intent);
				helpme.this.finish();
				
			}        	 
        });			
        
        thebutton2.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(helpme.this, forecast.class);
				startActivity(intent);
				helpme.this.finish();
				
			}        	 
        });
        
        thebutton3.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(helpme.this, local.class);
				startActivity(intent);
				helpme.this.finish();
				
			}        	 
        });        
        
        
        thebutton4.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(helpme.this, twitter.class);
				startActivity(intent);
				helpme.this.finish();
				
			}        	 
        });	
        
	      

        
        
        
		
	}
	
	
	
	
	

	
	
    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			
			Context context = getApplicationContext();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			
				if (update15.isChecked()){
					
					
					Intent intentup = new Intent(context,notification_autorun.class);
					PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 2, intentup , PendingIntent.FLAG_NO_CREATE);
					
					AlarmManager am;
					am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
					am.cancel(alarmIntent);
					
					Editor editor = prefs.edit();
	            	editor.putInt("notification_update", 900000);
	            	editor.putInt("notification", 1);
	            	editor.commit();
	            	
	            	
		        	Intent i = new Intent(helpme.this,notification_stratservice.class);
		        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        	startService(i);
		        	
		        	
				}else if (update30.isChecked()){
					
					
					Intent intentup = new Intent(context,notification_autorun.class);
					PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 2, intentup , PendingIntent.FLAG_NO_CREATE);
					
					AlarmManager am;
					am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
					am.cancel(alarmIntent);
					
					Editor editor = prefs.edit();
	            	editor.putInt("notification_update", 1800000);
	            	editor.putInt("notification",1);
	            	editor.commit();
	            	
	            	
		        	Intent i = new Intent(helpme.this,notification_stratservice.class);
		        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        	startService(i);
	            	
	            	
				}else if (update60.isChecked()){
					
					
					Intent intentup = new Intent(context,notification_autorun.class);
					PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 2, intentup , PendingIntent.FLAG_NO_CREATE);
					
					AlarmManager am;
					am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
					am.cancel(alarmIntent);
	
					Editor editor = prefs.edit();
	            	editor.putInt("notification_update", 3600000);
	            	editor.putInt("notification", 1);
	            	editor.commit();
	            	
	            	Intent i = new Intent(helpme.this,notification_stratservice.class);
		        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        	startService(i);
	            	
	            	
	
				}else if (noupdate.isChecked()){
				
				
				
					Intent intentup = new Intent(context,notification_autorun.class);
					PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 2, intentup , PendingIntent.FLAG_NO_CREATE);
				
					AlarmManager am;
					am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
					am.cancel(alarmIntent);
				
				
					Editor editor = prefs.edit();
					editor.putInt("notification", 0);
					editor.putString("notification_text_update", "");
					editor.commit();
				

				
				}else{
					
					Editor editor = prefs.edit();
	            	editor.putInt("notification_update", 7200000);
	            	editor.putInt("notification", 0);
	            	editor.commit();
					
				}
			
			
		}
	};
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
