package androidbaby.hkweather;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class warn extends Activity implements Runnable {

	private ImageButton thebutton1,thebutton2,thebutton4,thebutton5,thebutton6,tc_pic,zoom_pic;
	private TextView alltext;
	Thread thread;
	ProgressDialog dialog;
	Bitmap bitmap,bitmap2;
	private String tc,zoom,strResult;
	private String warn0="";
	private String memo = "";
	private String now = "";
	
	 private static final String DESKTOP_USERAGENT =
         "Mozilla/5.0 (Macintosh; U; Intel Mac OS X; en)"
         + " AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2"
         + " Safari/525.20.1"; 
	 
	 
	
	
	
    protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
               
        //介面
        setContentView(R.layout.warn);
        
        thebutton1 = (ImageButton) findViewById(R.id.button_a);
		thebutton2 = (ImageButton) findViewById(R.id.button_b);		
		thebutton4 = (ImageButton) findViewById(R.id.button_d);
		thebutton5 = (ImageButton) findViewById(R.id.button_e);
		thebutton6 = (ImageButton) findViewById(R.id.button_f);
		tc_pic = (ImageButton) findViewById(R.id.tc_pic);
		zoom_pic = (ImageButton) findViewById(R.id.zoom_pic);
		
		alltext = (TextView) findViewById(R.id.alltext);		
		
		
		button();
		
		//dialog = new ProgressDialog(forecast.this);
		//dialog.setMessage(getResources().getText(R.string.loading).toString());
		//dialog.show();
		setProgressBarIndeterminateVisibility(true);
		
		thread = new Thread(warn.this);
		thread.start();
		

		
    }
	
	
	
	private void button() {
		// TODO Auto-generated method stub
		
        thebutton1.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(warn.this, main.class);
				startActivity(intent);
				warn.this.finish();
				
			}        	 
        });		
        
        thebutton2.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(warn.this, forecast.class);
				startActivity(intent);
				warn.this.finish();
				
			}        	 
        });		
        
        
        thebutton4.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(warn.this, twitter.class);
				startActivity(intent);
				warn.this.finish();
				
			}        	 
        });      
        
        thebutton5.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(warn.this, helpme.class);
				startActivity(intent);
				warn.this.finish();
				
			}        	 
        });
        
        
        thebutton6.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				warn0 = "";
				memo = "";

				thread = new Thread(warn.this);
				thread.start();
				setProgressBarIndeterminateVisibility(true);
				
			}        	 
        });	
        
        tc_pic.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub

				   String url_link = "http://www.weather.gov.hk/wxinfo/currwx/tc_posc.htm";
				   Uri uri = Uri.parse(url_link);
				   Intent it  = new Intent(Intent.ACTION_VIEW,uri);
				   startActivity(it);
				   warn.this.finish();
				
			}        	 
        });
        
        
        zoom_pic.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				   String url_link = "http://www.weather.gov.hk/wxinfo/currwx/tc_posc.htm";
				   Uri uri = Uri.parse(url_link);
				   Intent it  = new Intent(Intent.ACTION_VIEW,uri);
				   startActivity(it);
				   warn.this.finish();
				
			}        	 
        });
        
        
	}
	
	


	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		
		
		GregorianCalendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		int ampm = cal.get(GregorianCalendar.AM_PM);
		now = " " + pad(cal.get(Calendar.HOUR_OF_DAY)) + ":" + pad(cal.get(Calendar.MINUTE)) + am_pm(ampm) + " " + day + "/" + month + "/" + year ;			


		
		try {
			

	          HttpURLConnection urlConnection= null;
	          URL url=new URL("http://www.weather.gov.hk/textonly/warning/detailc.htm");
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
	          

	        	String begin0 = "<pre>";
	        	String end0 =  "</pre>";

	        	int sindex0 = strResult.indexOf(begin0) + begin0.length();
	        	int eindex0 = strResult.indexOf(end0,sindex0);

	        		
	        	if ( sindex0 != -1 && eindex0 != -1 ){	
	        			        		
	        		Pattern p = Pattern.compile(".+");
	        		Matcher m = p.matcher(strResult.substring(sindex0,eindex0));
	        	 	while (m.find()) {
	        	 		warn0 = m.group();
	        	 		memo = memo + warn0 ;
	        	 		
	        	 	}	
	        	}else{
	        		   memo = getResources().getText(R.string.nowarn).toString();
	        		
	        	}
	        	
			} catch (Exception e) {
			// TODO Auto-generated catch block
				Log.e("Hkweather warn","Error:" + e);
				errorResults.sendEmptyMessage(0);

			}
			
			
        	UpdateResults.sendEmptyMessage(0);			
			
        	
		try{

				String uri = "http://www.weather.gov.hk/wxinfo/currwx/tc_pos.htm";
				HttpGet httpRequest = new HttpGet(uri);
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
	        
				if(httpResponse.getStatusLine().getStatusCode() == 200){
	        		
					String strResult = EntityUtils.toString(httpResponse.getEntity());        
	        	
					//下載tc_ .png 圖
					String begin1 = "<p><img src='tc_";
					String end1 =  "' alt=";
					tc = "";
	        	
					int sindex1 = strResult.indexOf(begin1) + begin1.length();
					int eindex1 = strResult.indexOf(end1,sindex1);
	        		
					if ( sindex1 != -1 && eindex1 != -1 ){	
	        			        		
						Pattern p = Pattern.compile(".+");
						Matcher m = p.matcher(strResult.substring(sindex1,eindex1));
	        	
						while (m.find()) {
							tc = m.group();
						}
					}
	        	
	        	
	        		if ( tc !="" ){
	        			
	        			Log.d("Hkweather warn","tc = " + tc);
	        			
	        			URL imageUrl;
	        			imageUrl = new URL("http://www.weather.gov.hk/wxinfo/currwx/tc_" + tc );

	        			HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
	        			conn.connect();

	        			InputStream is = conn.getInputStream();
	        			bitmap = BitmapFactory.decodeStream(is);
	        			is.close();
					
	        			UpdateResults2.sendEmptyMessage(0);

	        		}
	        		
	        		
	        		//下載zoom_ .png 圖
		        	String begin2 = "<p><img src='zoom_";
		        	String end2 = "' alt=";
		        	zoom = "";
		        	
		        	int sindex2 = strResult.indexOf(begin2) + begin2.length();
		        	int eindex2 = strResult.indexOf(end2,sindex2);

		        	if ( sindex2 != -1 && eindex2 != -1 ){	
		        		
		        		Pattern p2 = Pattern.compile(".+");
		        		Matcher m2 = p2.matcher(strResult.substring(sindex2,eindex2));
		        	
		        	 	while (m2.find()) {
		        	 		zoom = m2.group();
		        	 	}
		        	}
		        	
		        	
	        		if ( zoom !="" ){
	            		
	        			URL imageUrl2;
	        			imageUrl2 = new URL("http://www.weather.gov.hk/wxinfo/currwx/zoom_" + zoom );

	        			HttpURLConnection conn2 = (HttpURLConnection)imageUrl2.openConnection();
	        			conn2.connect();

	        			InputStream is2 = conn2.getInputStream();
	        			bitmap2 = BitmapFactory.decodeStream(is2);
	        			is2.close();
	        			
		        		UpdateResults3.sendEmptyMessage(0);

	        		}	  
	        		

				}else{
				}


			} catch (Exception e) {
			// TODO Auto-generated catch block
				Log.e("Hkweather warn","Error:" + e);

			}
		
			UpdateResults4.sendEmptyMessage(0);
			

	}
	







//UI線程更新	
	Handler UpdateResults = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		alltext.setText(memo);		
		setTitle(getResources().getText(R.string.app_name).toString() + " (" + getResources().getText(R.string.updatetime).toString() + now + ")");


		}
	};
	

	Handler UpdateResults2 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		tc_pic.setImageBitmap(bitmap);


		}
	};
	
	
	Handler UpdateResults3 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		zoom_pic.setImageBitmap(bitmap2);

	
		}
	};
	
	
	Handler UpdateResults4 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
			setProgressBarIndeterminateVisibility(false);

		}
	};	
	
	
	
	Handler errorResults = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
			new AlertDialog.Builder(warn.this)
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
