package androidbaby.hkweather;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class local extends Activity implements Runnable {

	private ImageButton thebutton1,thebutton2,thebutton3,thebutton4,thebutton5,thebutton6;
	private TextView local_view,topic_view,day1_view,day2_view,day3_view,day4_view,day5_view,day6_view,day7_view;
	Thread thread;
	ProgressDialog dialog;
	
	private String strResult,localc,now,wday,wday2,wday3,wday4,wday5,day1,day2,day3,day4,day5,day6,day7;
	private String localall="";
	
	
	 private static final String DESKTOP_USERAGENT =
         "Mozilla/5.0 (Macintosh; U; Intel Mac OS X; en)"
         + " AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2"
         + " Safari/525.20.1"; 
	
	
    protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
               
        //介面
        setContentView(R.layout.local);
        

        
        thebutton1 = (ImageButton) findViewById(R.id.button_a);
		thebutton2 = (ImageButton) findViewById(R.id.button_b);
		thebutton3 = (ImageButton) findViewById(R.id.button_c);
		thebutton4 = (ImageButton) findViewById(R.id.button_d);
		thebutton5 = (ImageButton) findViewById(R.id.button_e);
		thebutton6 = (ImageButton) findViewById(R.id.button_f);
        
		local_view = (TextView) findViewById(R.id.local);
	
		
		button();
		
		//dialog = new ProgressDialog(forecast.this);
		//dialog.setMessage(getResources().getText(R.string.loading).toString());
		//dialog.show();
		setProgressBarIndeterminateVisibility(true);
		
		thread = new Thread(local.this);
		thread.start();
		
		
    }
	
	
	
	private void button() {
		// TODO Auto-generated method stub
		
        thebutton1.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(local.this, main.class);
				startActivity(intent);
				local.this.finish();
				
			}        	 
        });		
        
		
        thebutton2.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(local.this, forecast.class);
				startActivity(intent);
				local.this.finish();
				
			}        	 
        });
        
  
        thebutton4.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(local.this, twitter.class);
				startActivity(intent);
				local.this.finish();
				
			}        	 
        });      
        
        thebutton5.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(local.this, helpme.class);
				startActivity(intent);
				local.this.finish();
				
			}        	 
        });
        
        
        thebutton6.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				localall="";
				local_view.setText("");	
				
				thread = new Thread(local.this);
				thread.start();
				thebutton3.setBackgroundResource(R.drawable.theback1);
				setProgressBarIndeterminateVisibility(true);
				
			}        	 
        });
        
	}
	
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			
	          HttpURLConnection urlConnection= null;
	          URL url=new URL("http://www.weather.gov.hk/textonly/forecast/chinesewx.htm");
	          urlConnection=(HttpURLConnection)url.openConnection();
	          urlConnection.setRequestMethod("GET");
	          urlConnection.setDoOutput(true);
	          urlConnection.setDoInput(true);
	          urlConnection.setRequestProperty("User-Agent",DESKTOP_USERAGENT);
	          urlConnection.setRequestProperty("Content-type","text/html; charset=BIG5-HKSCS");
	          urlConnection.connect();
	          InputStream htmlBODY = urlConnection.getInputStream();
	          
	          if(htmlBODY!=null)
	          {
	            int leng =0;
	            byte[] Data = new byte[100];
	            byte[] totalData = new byte[0];
	            int totalLeg =0;

	            do
	            {
	              leng = htmlBODY.read(Data);
	              if(leng>0)
	              {
	                totalLeg += leng;
	                byte[] temp = new byte[totalLeg];
	                System.arraycopy(totalData, 0, temp, 0, totalData.length);
	                System.arraycopy(Data, 0, temp, totalData.length, leng);
	                totalData = temp;
	              }
	            }while(leng>0);

	           		strResult = new String(totalData, "BIG5");
	          }
			
			
	          //System.out.println(strResult);
	          	
	          	
	        	String begin1 = "本 港 其 他 地 區 的 氣 溫 ：";
	        	String end1 =  "</pre>";

	        	int sindex1 = strResult.indexOf(begin1) + begin1.length();
	        	int eindex1 = strResult.indexOf(end1,sindex1);
	        		
	        	if ( sindex1 != -1 && eindex1 != -1 ){	
	        			        		
	        		Pattern p = Pattern.compile(".+");
	        		Matcher m = p.matcher(strResult.substring(sindex1,eindex1));
	        	
	        	 	while (m.find()) {
	        	 		localc = m.group();
	        	 		localall = localall + localc + "\n" ;
	        	 	}
	        	}
	        	
	        	

			
			    
				GregorianCalendar cal = new GregorianCalendar();
				int day = cal.get(Calendar.DATE);
				int month = cal.get(Calendar.MONTH)+1;
				int year = cal.get(Calendar.YEAR);
				int ampm = cal.get(GregorianCalendar.AM_PM);
				now = " " + pad(cal.get(Calendar.HOUR_OF_DAY)) + ":" + pad(cal.get(Calendar.MINUTE)) + am_pm(ampm) + " " + day + "/" + month + "/" + year ;
			    
		          
				
				
			    UpdateResults.sendEmptyMessage(0);//UI線程可以更新    			
			    
			    
			    
		    /*
			URL url = new URL("http://www.androidbaby.com/createdata/hkweather3/forecast.php");

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			forecastsax forecastdata = new forecastsax();
			xr.setContentHandler(forecastdata);
			xr.parse(new InputSource(url.openStream()));		
			
			forecastdataset forecastdataset = forecastdata.getParsedData();

			now = forecastdataset.nowtoString();
			
		    localc = forecastdataset.localctoString()+"\n\n";

		    wday = forecastdataset.wdaytoString();
		    wday2 = forecastdataset.wday2toString();
		    wday3 = forecastdataset.wday3toString();
		    wday4 = forecastdataset.wday4toString();
		    wday5 = forecastdataset.wday5toString();
		    
		    day1 = forecastdataset.day1dtoString() +"\n"+ forecastdataset.day1toString() +"\n"+ forecastdataset.day1ttoString() + forecastdataset.day1ptoString()+"\n\n";
		    day2 = forecastdataset.day2dtoString() +"\n"+ forecastdataset.day2toString() +"\n"+ forecastdataset.day2ttoString() + forecastdataset.day2ptoString()+"\n\n";
		    day3 = forecastdataset.day3dtoString() +"\n"+ forecastdataset.day3toString() +"\n"+ forecastdataset.day3ttoString() + forecastdataset.day3ptoString()+"\n\n";		    
		    day4 = forecastdataset.day4dtoString() +"\n"+ forecastdataset.day4toString() +"\n"+ forecastdataset.day4ttoString() + forecastdataset.day4ptoString()+"\n\n";
		    day5 = forecastdataset.day5dtoString() +"\n"+ forecastdataset.day5toString() +"\n"+ forecastdataset.day5ttoString() + forecastdataset.day5ptoString()+"\n\n";
		    day6 = forecastdataset.day6dtoString() +"\n"+ forecastdataset.day6toString() +"\n"+ forecastdataset.day6ttoString() + forecastdataset.day6ptoString()+"\n\n";
		    day7 = forecastdataset.day7dtoString() +"\n"+ forecastdataset.day7toString() +"\n"+ forecastdataset.day7ttoString() + forecastdataset.day7ptoString()+"\n\n";
		    */

		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
				
				errorResults.sendEmptyMessage(0);
			
			
		}
			

	}
	


	//UI線程更新	
	Handler UpdateResults = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		
		local_view.setText(localall);	
		setTitle(getResources().getText(R.string.app_name).toString() + " (" + getResources().getText(R.string.updatetime).toString() + now + ")");

		
		thebutton3.setBackgroundResource(R.drawable.transparent);	
		setProgressBarIndeterminateVisibility(false);

		
		}
	};
	
	
	
	Handler errorResults = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
			new AlertDialog.Builder(local.this)
			.setTitle("")
			.setMessage(getResources().getText(R.string.errorlink).toString())
			.setPositiveButton
			(getResources().getText(R.string.ok).toString(),new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
				}
			}).show();
		
		}
	};	
	
	
	
	
	//時間為個位時在時間前補0
	private static String pad(int c) {
	  if (c >= 10)
	   return String.valueOf(c);
	  else
	   return "0" + String.valueOf(c);
	} 
	
	
	//時間判斷為上午下午
	private static String am_pm(int c) {
	  if (c == 0)
	   return "AM";
	  else
	   return "PM";
	} 	  
  
	
	
	
	
	
	
}
