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


public class forecast extends Activity implements Runnable {

	private ImageButton thebutton1,thebutton2,thebutton3,thebutton4,thebutton5,thebutton6;
	private TextView local_time,local_view,local_view2;
	Thread thread;
	ProgressDialog dialog;
	
	private String strResult,strResult2,local0,localc,localc2,now;
	private String localtime="";
	private String localall="";
	private String localall2="";
	
	 private static final String DESKTOP_USERAGENT =
         "Mozilla/5.0 (Macintosh; U; Intel Mac OS X; en)"
         + " AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2"
         + " Safari/525.20.1"; 
	
	
	 
	 
	 
    protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
               
        //介面
        setContentView(R.layout.forecast);
        

        
        thebutton1 = (ImageButton) findViewById(R.id.button_a);
		thebutton2 = (ImageButton) findViewById(R.id.button_b);
		thebutton3 = (ImageButton) findViewById(R.id.button_c);
		thebutton4 = (ImageButton) findViewById(R.id.button_d);
		thebutton5 = (ImageButton) findViewById(R.id.button_e);
		thebutton6 = (ImageButton) findViewById(R.id.button_f);
        
		local_time = (TextView) findViewById(R.id.localtime);
		local_view = (TextView) findViewById(R.id.local);
		local_view2 = (TextView) findViewById(R.id.local2);
		
		button();
		
		//dialog = new ProgressDialog(forecast.this);
		//dialog.setMessage(getResources().getText(R.string.loading).toString());
		//dialog.show();
		setProgressBarIndeterminateVisibility(true);
		
		thread = new Thread(forecast.this);
		thread.start();
		
		
    }
	
	
	
	private void button() {
		// TODO Auto-generated method stub
		
        thebutton1.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(forecast.this, main.class);
				startActivity(intent);
				forecast.this.finish();
				
			}        	 
        });		
        
        thebutton3.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(forecast.this, local.class);
				startActivity(intent);
				forecast.this.finish();
				
			}        	 
        }); 
        
        
        thebutton4.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(forecast.this, twitter.class);
				startActivity(intent);
				forecast.this.finish();
				
			}        	 
        });      
        
        thebutton5.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(forecast.this, helpme.class);
				startActivity(intent);
				forecast.this.finish();
				
			}        	 
        });
        
        
        thebutton6.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				localtime="";
				localall="";
				localall2="";
				
				local_time.setText("");	
				local_view.setText("");
				local_view2.setText("");	
				
				thread = new Thread(forecast.this);
				thread.start();
				thebutton2.setBackgroundResource(R.drawable.theback1);
				setProgressBarIndeterminateVisibility(true);
				
			}        	 
        });	
        
	}
	
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			
			
//本 港 地 區 天 氣 預 報
	          HttpURLConnection urlConnection= null;
	          URL url=new URL("http://www.weather.gov.hk/textonly/forecast/localc.htm");
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
	          

	        	String begin0 = "<i>";
	        	String end0 =  "</i>";

	        	int sindex0 = strResult.indexOf(begin0) + begin0.length();
	        	int eindex0 = strResult.indexOf(end0,sindex0);

	        		
	        	if ( sindex0 != -1 && eindex0 != -1 ){	
	        			        		
	        		Pattern p = Pattern.compile(".+");
	        		Matcher m = p.matcher(strResult.substring(sindex0,eindex0));
	        		
	        		
	        	 	while (m.find()) {
	        	 		local0 = m.group();
	        	 		localtime = localtime + local0 ;
	        	 		
	        	 	}
	        	 	
	        	 	
	        	}
	          
	          
	        	String begin1 = "<pre>";
	        	String end1 =  "</pre>";

	        	int sindex1 = strResult.indexOf(begin1) + begin1.length();
	        	int eindex1 = strResult.indexOf(end1,sindex1);

	        		
	        	if ( sindex1 != -1 && eindex1 != -1 ){	
	        			        		
	        		Pattern p = Pattern.compile(".+");
	        		Matcher m = p.matcher(strResult.substring(sindex1,eindex1));
	        		
	        		
	        	 	while (m.find()) {
	        	 		localc = m.group();
	        	 		localall = localall + localc ;
	        	 		
	        	 	}
	        	 	
	        	 	
	        	}
	        	
	        	
	        	
	        	
	        	
	        	
	        	
//七 天 天 氣 預 報

		          HttpURLConnection urlConnection2= null;
		          URL url2=new URL("http://www.weather.gov.hk/textonly/forecast/ndayc.htm");
		          urlConnection2=(HttpURLConnection)url2.openConnection();
		          urlConnection2.setRequestMethod("GET");
		          urlConnection2.setDoOutput(true);
		          urlConnection2.setDoInput(true);
		          urlConnection2.setRequestProperty("User-Agent",DESKTOP_USERAGENT);
		          urlConnection2.setRequestProperty("Content-type","text/html; charset=BIG5-HKSCS");
		          urlConnection2.connect();
		          InputStream htmlBODY2 = urlConnection2.getInputStream();
		          
		          if(htmlBODY2!=null)
		          {
		            int leng2 =0;
		            byte[] Data2 = new byte[100];
		            byte[] totalData2 = new byte[0];
		            int totalLeg2 =0;

		            do
		            {
		              leng2 = htmlBODY2.read(Data2);
		              if(leng2>0)
		              {
		                totalLeg2 += leng2;
		                byte[] temp2 = new byte[totalLeg2];
		                
		                System.arraycopy(totalData2, 0, temp2, 0, totalData2.length);
		                System.arraycopy(Data2, 0, temp2, totalData2.length, leng2);
		                totalData2 = temp2;
		                
		              }
		            }while(leng2>0);

		           		strResult2 = new String(totalData2, "BIG5");
		          }
		          

		          	
		        	String begin2 = "<pre>";
		        	String end2 =  "七 天 天 氣 預 報 插 圖";

		        	int sindex2 = strResult2.indexOf(begin2) + begin2.length();
		        	int eindex2 = strResult2.indexOf(end2,sindex2);
		        		
		
		        		
		        	if ( sindex2 != -1 && eindex2 != -1 ){	
		        			        		
		        		Pattern p2 = Pattern.compile(".+");
		        		Matcher m2 = p2.matcher(strResult2.substring(sindex2,eindex2));
		        		
		        		
		        	 	while (m2.find()) {
		        	 		localc2 = m2.group();
		        	 		localall2 = localall2 + localc2 + "\n\n";
		        	 		
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
		
		local_time.setText(localtime + " :");
		local_view.setText(localall);
		local_view2.setText(localall2);		
		setTitle(getResources().getText(R.string.app_name).toString() + " (" + getResources().getText(R.string.updatetime).toString() + now + ")");
		
		thebutton2.setBackgroundResource(R.drawable.transparent);	
		setProgressBarIndeterminateVisibility(false);

		}
	};
	
	
	
	Handler errorResults = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
			new AlertDialog.Builder(forecast.this)
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
