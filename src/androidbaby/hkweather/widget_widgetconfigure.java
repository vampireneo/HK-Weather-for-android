package androidbaby.hkweather;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class widget_widgetconfigure extends Activity {

	int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	private Button choose;
	protected RadioGroup widget_update,widget_background;
	protected RadioButton widget_update1,widget_update2,widget_update3,widget_b1,widget_b2,widget_b3,widget_b4;
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		//透明介面
        // Have the system blur any windows behind this one.
        // See assets/res/any/layout/translucent_background.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        
        setContentView(R.layout.widgetconfigure);
        
          widget_update = (RadioGroup)findViewById(R.id.widget_update);
	      widget_update1 = (RadioButton)findViewById(R.id.widget_update1);
	      widget_update2 = (RadioButton)findViewById(R.id.widget_update2);
	      widget_update3 = (RadioButton)findViewById(R.id.widget_update3);
	      widget_update.setOnCheckedChangeListener(listener);
        
          widget_background = (RadioGroup)findViewById(R.id.widget_background);
	      widget_b1 = (RadioButton)findViewById(R.id.widget_b1);
	      widget_b2 = (RadioButton)findViewById(R.id.widget_b2);
	      widget_b3 = (RadioButton)findViewById(R.id.widget_b3);
	      widget_b4 = (RadioButton)findViewById(R.id.widget_b4);
	      widget_background.setOnCheckedChangeListener(listener2);
        
	      choose = (Button)findViewById(R.id.choose); 
	       
	      
	      	//
	      	// 尋找部件widget的id
	      	//
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();

				if (extras != null) {
					mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
				}
			//
			// 如果沒有給予部件widget的id即會取消
			//
				if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
					finish();
				}
	        	

			
			choose.setOnClickListener(new View.OnClickListener(){
	    		public void onClick(View v) {

	    			
	    			Context context = getApplicationContext();
	    			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    			//加入資訊(重開機後令widget開啟)
	    	    	Editor editor = prefs.edit();
	    	    	editor.putBoolean("widget", true);
	    	    	editor.commit();
					//開始使用Widget
	    			context = widget_widgetconfigure.this;
	    			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	    			try {
	   				
	    				widget_hkweather.onUpdate(context, appWidgetManager, mAppWidgetId);
	    				
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}

	    			
	    			// Make sure we pass back the original appWidgetId
	    			Intent resultValue = new Intent();
	    			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
	    			setResult(RESULT_OK, resultValue);
	    			finish();
	    		}
	    		
	        });
	        
	      
    }
    
    
    

    
    
    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			
			Context context = getApplicationContext();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			
				if (widget_update1.isChecked()){
					
					Editor editor = prefs.edit();
	            	editor.putString("widget_update", "900000");
	            	editor.commit();
					
				}else if(widget_update2.isChecked()){
					
					Editor editor = prefs.edit();
	            	editor.putString("widget_update", "1800000");
	            	editor.commit();
	            	
				}else{
					
					Editor editor = prefs.edit();
	            	editor.putString("widget_update", "3600000");
	            	editor.commit();	
					
				}
    
			
		}
	};
    
    
    
    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			
			
			Context context = getApplicationContext();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			
				if (widget_b1.isChecked()){
					
					Editor editor = prefs.edit();
	            	editor.putString("widget_background", "1");
	            	editor.commit();
					
				}else if(widget_b2.isChecked()){
					
					Editor editor = prefs.edit();
	            	editor.putString("widget_background", "2");
	            	editor.commit();
	            
	            	
				}else if(widget_b3.isChecked()){
					
					Editor editor = prefs.edit();
	            	editor.putString("widget_background", "3");
	            	editor.commit();	            	
	            	
				}else if(widget_b4.isChecked()){
					
					Editor editor = prefs.edit();
	            	editor.putString("widget_background", "4");
	            	editor.commit();	              	
	            	
	            	
				}else{
					
					Editor editor = prefs.edit();
	            	editor.putString("widget_background", "1");
	            	editor.commit();	
					
				}
    
			
		}
	}; 
    
    
    
    
    
    
    
    
    
    
}
