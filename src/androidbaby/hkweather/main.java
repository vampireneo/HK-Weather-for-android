package androidbaby.hkweather;


import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;





public class main extends Activity implements Runnable {

	
	//private ProgressDialog d;

	private TextView temp_view,tempper_view,uv_view,ap_view;
	private ImageButton thebutton1,thebutton2,thebutton3,thebutton4,thebutton5,thebutton6;
	private ImageView sample_icon,warn1_icon,warn2_icon,warn3_icon,warn4_icon;
	
	RelativeLayout mainlayout;
	Drawable d;
	Bitmap bm;
	Thread thread;
	
	private LocationManager mLocationManager;
	private GeoPoint currentGeoPoint;
	private String strLocationPrivider = "";
	private Location myLocation = null;

	private String temp,tempper,uv,now,sampleicon,ap,warn1,warn2,warn3,warn4,localc;
	private int main_dialog;
	private Handler buttonHandler = new Handler();
	private String kingspark,wongchukhang,takwuling,taipo,shatin,laufaushan,tuenmun,tseungkwano,saikung,cheungchau,cheklapkok,tsingyi,shekkong,tsuenwan,hongkongpark,shaukeiwan,kowlooncity,happyvalley,wongtaisin,stanley,kwuntong,shamshuipo;

	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
			
			
			setContentView(R.layout.main);
			thebutton1 = (ImageButton) findViewById(R.id.button_a);
			mainlayout = (RelativeLayout)findViewById(R.id.mainlayout);			
			thebutton2 = (ImageButton) findViewById(R.id.button_b);
			thebutton3 = (ImageButton) findViewById(R.id.button_c);
			thebutton4 = (ImageButton) findViewById(R.id.button_d);
			thebutton5 = (ImageButton) findViewById(R.id.button_e);
			thebutton6 = (ImageButton) findViewById(R.id.button_f);

	         temp_view = (TextView) findViewById(R.id.temp);   
	         tempper_view = (TextView) findViewById(R.id.tempper); 
	         uv_view = (TextView) findViewById(R.id.uv);
	         ap_view = (TextView) findViewById(R.id.ap);
	         
	         sample_icon = (ImageView) findViewById(R.id.sample_icon);
	         warn1_icon = (ImageView) findViewById(R.id.warn1);
	         warn2_icon = (ImageView) findViewById(R.id.warn2);
	         warn3_icon = (ImageView) findViewById(R.id.warn3);
	         warn4_icon = (ImageView) findViewById(R.id.warn4);
	         
	         setTitle(getResources().getText(R.string.app_name).toString());
	         setProgressBarIndeterminateVisibility(true);
	         
	         

	         button();
			//d = new ProgressDialog(main.this);
			//d.setMessage("loading...");
			//d.show();
			thread = new Thread(main.this);
			thread.start();

			/*
			 * 	    
			 * Thread t = new Thread() {
		       public void run() {
		                doSomething(); //主線程
		        		loadedHandler.post(UpdateResults); //UI線程可以更新
		        		close_d_handler.sendEmptyMessage(0);
		            }
		        };
		        t.start();
			 * 
			 */
			Context context = getApplicationContext();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			main_dialog = prefs.getInt("main_dialog", 1);
			if( main_dialog < 3){	
					show_teaching();
			}		
			
			
			
	}
	

	private void show_teaching() {
		
        LayoutInflater factory = LayoutInflater.from(main.this);
        View myView = factory.inflate(R.layout.alert_main_dialog,null);
	 	AlertDialog dialog = new AlertDialog.Builder(main.this).create();
	 	dialog.setView(myView);
	 	dialog.setIcon(R.drawable.icon_teaching);
	 	dialog.setTitle(getResources().getText(R.string.alert_1).toString());
	 	
	 	dialog.setButton(getResources().getText(R.string.alert_2).toString(), 
		new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				
						Context context = getApplicationContext();
						SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
    	            	Editor editor = data.edit();
    		 			editor.putInt("main_dialog",3);	
    			    	editor.commit();

					}
			 	}			 	
			 	);
	 		 	
		dialog.setButton2(getResources().getText(R.string.alert_3).toString(),
		new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				

					}
			 	}			 	
		);
		dialog.show();
		

}
	
	
	        
	private void button() {
		// TODO Auto-generated method stub

		warn1_icon.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(main.this, warn.class);
				startActivity(intent);
				main.this.finish();
				
			}        	 
        });			
		warn2_icon.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(main.this, warn.class);
				startActivity(intent);
				main.this.finish();
				
			}        	 
        });	
		warn3_icon.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(main.this, warn.class);
				startActivity(intent);
				main.this.finish();
				
			}        	 
        });	
		warn4_icon.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(main.this, warn.class);
				startActivity(intent);
				main.this.finish();
				
			}        	 
        });	
		
		
        thebutton2.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(main.this, forecast.class);
				startActivity(intent);
				main.this.finish();
				
			}        	 
        });		
		
        thebutton3.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(main.this, local.class);
				startActivity(intent);
				main.this.finish();
				
			}        	 
        }); 
        
        
        thebutton4.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(main.this, twitter.class);
				startActivity(intent);
				main.this.finish();
				
			}        	 
        });        
        
        thebutton5.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(main.this, helpme.class);
				startActivity(intent);
				main.this.finish();
				
			}        	 
        });
        
        thebutton6.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thread = new Thread(main.this);
				thread.start();
				thebutton1.setBackgroundResource(R.drawable.transparent);
				setProgressBarIndeterminateVisibility(true);
				
			}        	 
        });
        
        
	}




	public void run(){

		try {
			
			
	        String uri = "http://www.weather.gov.hk/textonly/forecast/englishwx.htm";
	        HttpGet httpRequest = new HttpGet(uri);
	        HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
	        
	        if(httpResponse.getStatusLine().getStatusCode() == 200){
	        		
	        	String strResult = EntityUtils.toString(httpResponse.getEntity());        
	        	
	        	//溫度
	        	String begin1 = "Air Temperature : ";
	        	String end1 =  " degrees Celsius";

	        	int sindex1 = strResult.indexOf(begin1) + begin1.length();
	        	int eindex1 = strResult.indexOf(end1,sindex1);
	        		
	        	if ( sindex1 != -1 && eindex1 != -1 ){	
	        			        		
	        		Pattern p = Pattern.compile("[0-9]+");
	        		Matcher m = p.matcher(strResult.substring(sindex1,eindex1));
	        	
	        	 	while (m.find()) {
	        	 		temp = m.group();
	        	 	}
	        	}
	        	

	        	
	        	
	        	/*
	        	 * Pattern 應用
	        	 * 
	        	 *  Pattern p = Pattern.compile("[A-Za-z]+");
	        	 *  Matcher m = p.matcher("Hello, Android!");
	        	 *  while (m.find()) {
	        	 *   System.out.println(m.group()); // prints "Hello" and "Android"
	        	 *    }
	        	 */

	        	
	        	//濕度
	        	String begin2 = "Relative Humidity : ";
	        	String end2 = " per cent";
	        	
	        	int sindex2 = strResult.indexOf(begin2) + begin2.length();
	        	int eindex2 = strResult.indexOf(end2,sindex2);

	        	if ( sindex2 != -1 && eindex2 != -1 ){	
	        		
	        		Pattern p2 = Pattern.compile("[0-9]+");
	        		Matcher m2 = p2.matcher(strResult.substring(sindex2,eindex2));
	        	
	        	 	while (m2.find()) {
	        	 		tempper = m2.group();
	        	 	}
	        	}
	        		        	
	        	
	        	//濕度
	        	uv = "0";
	        	
	        	String begin3 = "During the past hour the mean UV Index recorded at King's Park : ";
	        	String end3 = "Intensity of UV";
	        	
	        	int sindex3 = strResult.indexOf(begin3) + begin3.length();
	        	int eindex3 = strResult.indexOf(end3,sindex3);

	        	if ( sindex3 != -1 && eindex3 != -1 ){	
	        		
	        		Pattern p3 = Pattern.compile(".+");
	        		Matcher m3 = p3.matcher(strResult.substring(sindex3,eindex3));
	        	
	        	 	while (m3.find()) {
	        	 		uv = m3.group();
	        	 	}
	        	}
	        	

        	//圖示
        	String begin4 = "Weather Cartoon : No. ";
        	String end4 = " -";
        	
        	int sindex4 = strResult.indexOf(begin4) + begin4.length();
        	int eindex4 = strResult.indexOf(end4,sindex4);

        	if ( sindex4 != -1 && eindex4 != -1 ){	
        		
        		Pattern p4 = Pattern.compile("[0-9]+");
        		Matcher m4 = p4.matcher(strResult.substring(sindex4,eindex4));
        	
        	 	while (m4.find()) {
        	 		sampleicon = m4.group();
        	 	}
        	}
	        }
        	
        	
	        //警告圖示
	        String uri2 = "http://www.weather.gov.hk/textonly/warning/warn.htm";
	        HttpGet httpRequest2 = new HttpGet(uri2);
	        HttpResponse httpResponse2 = new DefaultHttpClient().execute(httpRequest2);
	        
	        warn1 = "40";
	        warn2 = "40";
	        warn3 = "40";
	        warn4 = "40";
	        	
	        if(httpResponse2.getStatusLine().getStatusCode() == 200){
	        	
	        	Vector<String> weatherno = new Vector<String>();

	        	String strResult2 = EntityUtils.toString(httpResponse2.getEntity());  
	        	String begin = "Warning Codes\n";
	        	String end =  "\n-->";
	        	
	        	int sindex = strResult2.indexOf(begin) + begin.length();
	        	int eindex = strResult2.indexOf(end,sindex);
	        	
	        	if ( sindex != -1 && eindex != -1 ){	
	        			        		
	        		Pattern p = Pattern.compile("[0-9]+");
	        		Matcher m = p.matcher(strResult2.substring(sindex,eindex));
	        	
	        	 	while (m.find()) {
	        	 		weatherno.add(m.group());
	        		}

	        		for ( int i = 0; i < weatherno.size(); i++) {
	        			
	        			//System.out.println("Weather " + i + " = " + weatherno.get(i));

	    	        	int warn_no = Integer.parseInt(weatherno.get(i));
	    	        	
	    	        	if (i==0){
	    	        		warn1 = ""+warn_no;

	    	        	}else if (i==1){
	    	        		warn2 = ""+warn_no;

	    	        	}else if (i==2){
	    	        		warn3 = ""+warn_no;

	    	        	}else{
	    	        		warn4 = ""+warn_no;

	    	        	}	    	        	
	        		}
	        	}
	        }
	        
	        
	        //空氣污染指數
	        String uri3 = "http://www.epd-asg.gov.hk/out/capi_rss.xml";
	        HttpGet httpRequest3 = new HttpGet(uri3);
	        HttpResponse httpResponse3 = new DefaultHttpClient().execute(httpRequest3);
	        
	        if(httpResponse3.getStatusLine().getStatusCode() == 200){
        		
	        	String strResult3 = EntityUtils.toString(httpResponse3.getEntity(),"Big5");    
	        	        
	        	//圖示
	        	String begin5 = "現時的空氣污染指數:";
	        	String end5 = "</title>";
	        	
	        	int sindex5 = strResult3.indexOf(begin5) + begin5.length();
	        	int eindex5 = strResult3.indexOf(end5,sindex5);

	        	if ( sindex5 != -1 && eindex5 != -1 ){	
	        		
	        		Pattern p5 = Pattern.compile(".+");
	        		Matcher m5 = p5.matcher(strResult3.substring(sindex5,eindex5));
	        	
	        	 	while (m5.find()) {
	        	 		ap = m5.group();
	        	 	}
	        	}
		    }
	        	
	        
			GregorianCalendar cal = new GregorianCalendar();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH)+1;
			int year = cal.get(Calendar.YEAR);
			int ampm = cal.get(GregorianCalendar.AM_PM);
			now = " " + pad(cal.get(Calendar.HOUR_OF_DAY)) + ":" + pad(cal.get(Calendar.MINUTE)) + am_pm(ampm) + " " + day + "/" + month + "/" + year ;
		    
			

			UpdateResults.sendEmptyMessage(0);//UI線程可以更新    			
		    
    			
			} catch (Exception e) {
			// TODO Auto-generated catch block
				
				errorResults.sendEmptyMessage(0);
			
			
			}
	}

	
	
	
	
	Handler errorResults = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
			new AlertDialog.Builder(main.this)
			.setTitle("")
			.setMessage(getResources().getText(R.string.errorlink).toString())
			.setPositiveButton
			(getResources().getText(R.string.ok).toString(),new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
				}
			}).show();
		
		}
	};


	
	//UI線程更新	
	Handler UpdateResults = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
				temp_view.setText(temp.toString() + "°C ");
				tempper_view.setText(tempper.toString() + "% ");
				uv_view.setText(uv.toString() + "UV");
				ap_view.setText( getResources().getText(R.string.air).toString() + ap.toString());
				
		
				
				int which = Integer.parseInt(sampleicon);
				sample_icon_change(which);

				int warn1_no = Integer.parseInt(warn1);
				warn_icon_change1(warn1_no);
				
				int warn2_no = Integer.parseInt(warn2);
				warn_icon_change2(warn2_no);
				
				int warn3_no = Integer.parseInt(warn3);
				warn_icon_change3(warn3_no);
				
				int warn4_no = Integer.parseInt(warn4);
				warn_icon_change4(warn4_no);
				
				
				setTitle(getResources().getText(R.string.app_name).toString() + " (" + getResources().getText(R.string.updatetime).toString() + now + ")");
				

				
				//關閉讀取進度
				setProgressBarIndeterminateVisibility(false);
				//d.dismiss();
				
				
				
			}


			
		};
	


		
		
	Thread location_thread = new Thread() {
	public void run() {
			try{
				mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				myLocation = agpsLocationPrivider(mLocationManager);
				if(myLocation != null)
					{
						mLocationManager.requestLocationUpdates(strLocationPrivider, 10000, 20, mLocationListener);
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
			} 
			
		
		}
	};	
		

//觸控事件		
public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX ,float velocityY) {
// 參數解釋：
// e1：第1個ACTION_DOWN MotionEvent
// e2：最後一個ACTION_MOVE MotionEvent
// velocityX：X軸上的移動速度，像素/秒
// velocityY：Y軸上的移動速度，像素/秒

// 觸發條件 ：
// X軸的坐標位移大於FLING_MIN_DISTANCE，且移動速度大於FLING_MIN_VELOCITY個像素/秒

			if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 1000) {
				// Fling left
			Toast.makeText(this, "Fling Left", Toast.LENGTH_SHORT).show();
			} else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 1000) {
			// Fling right
			Toast.makeText(this, "Fling Right", Toast.LENGTH_SHORT).show();
			}

	return false;

}
	
	
	
	
	
	  public Location agpsLocationPrivider(LocationManager lm)
	  {
	    Location retLocation = null;
	    try
	    {
	      Criteria mCriteria = new Criteria();
	      mCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
	      mCriteria.setAltitudeRequired(false);
	      mCriteria.setBearingRequired(false);
	      mCriteria.setCostAllowed(true);
	      mCriteria.setPowerRequirement(Criteria.POWER_LOW);
	      strLocationPrivider = lm.getBestProvider(mCriteria, true);
	      retLocation = lm.getLastKnownLocation(strLocationPrivider);

	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    return retLocation;
	  }
	  

		//更新監聽器
	  public final LocationListener mLocationListener = new LocationListener()
	  {
	    @Override
	    //當手機收到位置變更時，將location傳入取得地理座標 
	    public void onLocationChanged(Location location)
	    {	      
	    	currentGeoPoint = getGeoByLocation(location);
	    	getAddressbyGeoPoint(currentGeoPoint);
	    }
	    
	    
	    @Override
	    //當Provider已離開服務範圍時 
	    public void onProviderDisabled(String provider)
	    {

	    }
	    
	    @Override
	    public void onProviderEnabled(String provider)
	    {

	    }
	    
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras)
	    {
	
	      
	    }
	  }; 
	  
	  //轉換地理座標為標準格式
	  private GeoPoint getGeoByLocation(Location location)
	  {
	    GeoPoint gp = null;
	    try
	    {
	      //當Location存在
	      if (location != null)
	      {
	        double geoLatitude = location.getLatitude()*1E6;
	        double geoLongitude = location.getLongitude()*1E6;
	        gp = new GeoPoint((int) geoLatitude, (int) geoLongitude);
	      }
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return gp;
	  }
	  
	  
	  //從座標經緯度取得地址
	  public void getAddressbyGeoPoint(GeoPoint gp)
	  {

	    try
	    {
	    	//當GeoPoint不等於null
	    	if (gp != null)
	      	{
	        //建立Geocoder物件
	        Geocoder gc = new Geocoder(main.this, Locale.getDefault());
	        
	        //取出地理座標經緯度
	        double geoLatitude = (int)gp.getLatitudeE6()/1E6;
	        double geoLongitude = (int)gp.getLongitudeE6()/1E6;
	        
	        double a=(double)geoLatitude;		
	        double b=(double)geoLongitude;
	        
		    //旺角 mk	
	        if(22.31612 < a && a < 22.32320 && 114.16590 < b && b < 114.17715){	
	        	
	        	
	        }
	        //尖沙咀 tst
	        else if(22.29055 < a && a < 22.30105 && 114.16600 < b && b < 114.17765){
	        	
	        	
	        }
	        //九龍
	        else if(22.29810 < a && a < 22.30830 && 114.15100 < b && b < 114.16600){
	        	
	        	
	        }
	        //銅鑼灣
	        else if(22.27540 < a && a < 22.28567 && 114.17665 < b && b < 114.18808){
	        	
	        	;}
	        //灣仔
	        else if(22.27315 < a && a < 22.28722 && 114.16735 < b && b < 114.17665){
	        	
	        	
	        }
	        //上環
	        else if(22.27300 < a && a < 22.29100 && 114.14560 < b && b < 114.15310){
	        	
	        	
	        }     		
	        //中環
	        else if(22.27300 < a && a < 22.28805 && 114.15310 < b && b < 114.16250){
	        	
	        	
	        }   		
	        //金鐘
	        else if(22.27300 < a && a < 22.28805 && 114.16250 < b && b < 114.17665){
	        	
	        	
	        }
	        //上水
	        else if(22.48040 < a && a < 22.51495 && 114.11890 < b && b < 114.13300){
	        	
	        	
	        }
	        //羅湖
	        else if(22.51830 < a && a < 22.53988 && 114.09850 < b && b < 114.13335){
	        	
	        	
	        }
	        //屯門
	        else if(22.35400 < a && a < 22.43500 && 113.89500 < b && b < 113.99000){
	        	
	        	
	        }
	        //天水圍
	        else if(22.43500 < a && a < 22.50750 && 113.99000 < b && b < 114.01450){
	        	
	        	
	        }
	        //元朗
	        else if(22.43400 < a && a < 22.50750 && 114.01450 < b && b < 114.05250){
	        	
	        	
	        }
	        //錦上路
	        else if(22.40400 < a && a < 22.49900 && 114.05200 < b && b < 114.08100){
	        	
	        	
	        }
	        //深井/大欖
	        else if(22.35400 < a && a < 22.39500 && 113.99000 < b && b < 114.07400){
	        	
	        	
	        } 
	        //青衣
	        else if(22.32450 < a && a < 22.36550 && 114.07600 < b && b < 114.11000){
	        	
	        	
	        }
	        //荃灣
	        else if(22.36550 < a && a < 22.38000 && 114.10500 < b && b < 114.12200){
	        	
	        	
	        }
	        //大窩口
	        else if(22.36550 < a && a < 22.38000 && 114.12200 < b && b < 114.12970){
	        	
	        	
	        }
	        //葵涌
	        else if(22.36200 < a && a < 22.38600 && 114.12970 < b && b < 114.14430){
	        	
	        	
	        } 
	        //葵芳
	        else if(22.35080 < a && a < 22.36200 && 114.12120 < b && b < 114.14430){
	        	
	        	
	        }
	        //荔景
	        else if(22.32600 < a && a < 22.35085 && 114.11875 < b && b < 114.13380){
	        	
	        	
	        }	
	        //美孚
	        else if(22.32600 < a && a < 22.35085 && 114.13380 < b && b < 114.14470){
	        	
	        	
	        }      		
	        //荔枝角
	        else if(22.32600 < a && a < 22.35150 && 114.14470 < b && b < 114.15030){
	        	
	        	
	        }
	        //長沙灣
	        else if(22.33300 < a && a < 22.35150 && 114.15030 < b && b < 114.15920){
	        	
	        	
	        }
	        //深水步
	        else if(22.32630 < a && a < 22.33350 && 114.15920 < b && b < 114.16670){
	        	
	        	
	        }
	        //南昌
	        else if(22.32600 < a && a < 22.33300 && 114.15035 < b && b < 114.15975){
	        	
	        	
	        }
	        //奧運
	        else if(22.31620 < a && a < 22.32290 && 114.15447 < b && b < 114.16160){
	        	
	        	
	        }
	        //大角咀
	        else if(22.31620 < a && a < 22.32290 && 114.16160 < b && b < 114.16593){
	        	
	        	
	        }
	        //太子
	        else if(22.31620 < a && a < 22.32290 && 114.16160 < b && b < 114.16593){
	        	
	        	
	        }     		
	        //油麻地
	        else if(22.31620 < a && a < 22.32290 && 114.16160 < b && b < 114.16593){
	        	
	        	
	        } 
	        //炮台山
	        else if(22.28560 < a && a < 22.29470 && 114.18705 < b && b < 114.19680){
	        	
	        	
	        }  
	        //紅磡
	        else if(22.29600 < a && a < 22.30950 && 114.17765 < b && b < 114.19890){
	        	
	        	
	        }  
	        //何文田,土瓜灣
	        else if(22.30950 < a && a < 22.32550 && 114.17715 < b && b < 114.19460){
	        	
	        	
	        }  
	        //九龍城
	        else if(22.32720 < a && a < 22.32950 && 114.17876 < b && b < 114.19743){
	        	
	        	
	        }     		
	        //石硤尾
	        else if(22.32720 < a && a < 22.34780 && 114.16670 < b && b < 114.17353){
	        	
	        	
	        }      		
	        //九龍塘
	        else if(22.32720 < a && a < 22.34780 && 114.17353 < b && b < 114.17876){
	        	
	        	
	        }  
	        //樂富
	        else if(22.33295 < a && a < 22.34780 && 114.17876 < b && b < 114.19070){
	        	
	        	
	        }  
	        //黃大仙
	        else if(22.33295 < a && a < 22.35363 && 114.19070 < b && b < 114.19743){
	        	
	        	
	        }  
	        //鑽石山
	        else if(22.33295 < a && a < 22.34546 && 114.19743 < b && b < 114.20477){
	        	
	        	
	        }  
	        //彩虹
	        else if(22.32700 < a && a < 22.35470 && 114.20477 < b && b < 114.21833){
	        	
	        	
	        }  
	        //九龍灣
	        else if(22.31750 < a && a < 22.32700 && 114.20477 < b && b < 114.21833){
	        	
	        	
	        }  
	        //牛頭角
	        else if(22.31302 < a && a < 22.31750 && 114.20541 < b && b < 114.22301){
	        	
	        	
	        }  
	        //觀塘1
	        else if(22.31302 < a && a < 22.31750 && 114.22301 < b && b < 114.23039){
	        	
	        	
	        }  
	        //觀塘2
	        else if(22.30679 < a && a < 22.31302 && 114.21245 < b && b < 114.23039){
	        	
	        	
	        }  
	        //藍田
	        else if(22.30298 < a && a < 22.31243 && 114.23086 < b && b < 114.24420){
	        	
	        	
	        }  
	        //油塘
	        else if(22.28756 < a && a < 22.30298 && 114.23172 < b && b < 114.24595){
	        	
	        	
	        }  
	        //馬灣
	        else if(22.34068 < a && a < 22.35870 && 114.05240 < b && b < 114.06780){
	        	
	        	
	        }       		
	        //東涌/赤蠟角
	        else if(22.27620 < a && a < 22.32450 && 113.8910 < b && b < 113.95650){
	        	
	        	
	        }      		
	        //將軍澳
	        else if(22.30298 < a && a < 22.33036 && 114.24480 < b && b < 114.27764){
	        	
	        	
	        }          		
	        //大圍
	        else if(22.35750 < a && a < 22.37960 && 114.16280 < b && b < 114.18870){
	        	
	        	
	        } 
	        //沙田
	        else if(22.35750 < a && a < 22.39076 && 114.18870 < b && b < 114.22000){
	        	
	        	
	        } 
	        //火炭
	        else if(22.39076 < a && a < 22.40822 && 114.18540 < b && b < 114.20130){
	        	
	        	
	        } 
	        //大學
	        else if(22.40822 < a && a < 22.43544 && 114.20130 < b && b < 114.21640){
	        	
	        	
	        } 
	        //馬鞍山
	        else if(22.40917 < a && a < 22.43631 && 114.21640 < b && b < 114.25090){
	        	
	        	
	        } 
	        //大埔
	        else if(22.43948 < a && a < 22.46748 && 114.14770 < b && b < 114.19450){
	        	
	        	
	        } 
	        //粉嶺
	        else if(22.48040 < a && a < 22.51495 && 114.13300 < b && b < 114.15559){
	        	
	        	
	        } 
	        //西貢
	        else if(22.34000 < a && a < 22.40330 && 114.24680 < b && b < 114.30700){
	        	
	        	
	        } 
	        //香港仔/鴨利洲
	        else if(22.22800 < a && a < 22.24830 && 114.14345 < b && b < 114.17790){
	        	
	        	
	        }   
	        //薄扶林
	        else if(22.24830 < a && a < 22.27295 && 114.11525 < b && b < 114.14280){
	        	
	        	} 
	        //跑馬地
	        else if(22.263190 < a && a < 22.27540 && 114.17620 < b && b < 114.19740){
	        	
	        	
	        } 
	        //山頂
	        else if(22.26330 < a && a < 22.27135 && 114.14305 < b && b < 114.16615){
	        	
	        	
	        }     		
	        //北角
	        else if(22.28130 < a && a < 22.29440 && 114.19690 < b && b < 114.20610){
	        	
	        	
	        } 
	        //側魚涌
	        else if(22.27260 < a && a < 22.29440 && 114.20610 < b && b < 114.21310){
	        	
	        	
	        } 
	        //太古
	        else if(22.27260 < a && a < 22.29115 && 114.21310 < b && b < 114.21920){
	        	
	        	
	        } 
	        //西灣河
	        else if(22.27260 < a && a < 22.28500 && 114.21920 < b && b < 114.22450){
	        	
	        	
	        }       		
	        //筲箕灣
	        else if(22.27260 < a && a < 22.28500 && 114.22450 < b && b < 114.23195){
	        	
	        	
	        } 
	        //杏花村
	        else if(22.27260 < a && a < 22.28500 && 114.23195 < b && b < 114.24760){
	        	
	        	
	        } 
	        //柴灣
	        else if(22.25440 < a && a < 22.27260 && 114.22770 < b && b < 114.22725){
	        	
	        	
	        } 
	        //石澳
	        else if(22.20520 < a && a < 22.25440 && 114.22770 < b && b < 114.26300){
	        	
	        	
	        } 
	        //赤柱
	        else if(22.19290 < a && a < 22.23280 && 114.19280 < b && b < 114.22725){
	        	
	        	
	        } 
	        //西環
	        else if(22.27300 < a && a < 22.29100 && 114.11590 < b && b < 114.14560){
	        	
	        	
	        } 
	        //天后
	        else if(22.27558 < a && a < 22.28560 && 114.18808 < b && b < 114.19722){
	        	
	        	
	        } 	
	        //落馬洲,皇崗口岸
	        else if(22.49970 < a && a < 22.53100 && 114.05530 < b && b < 114.08965){
	        	
	        	
	        }
	        //迪士尼
	        else if(22.30614 < a && a < 22.32766 && 114.02850 < b && b < 114.05500){
	        	
	        }

	        

	      	}else{
	      		
	      	}
	        	
	      }catch(Exception e)
		    {
		      e.printStackTrace();
		    }
	
	  }
	    
	  

	  
	  
		//改變天氣圖示
		public int sample_icon_change(int which0) {
			
			switch (which0) {
  		case 50:	
  			bm = BitmapFactory.decodeResource(getResources(),R.drawable.b50); 
  			sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a50);
  			mainlayout.setBackgroundDrawable(d);
  		break;
  		case 51:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b51); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a51);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 52:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b52); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a52);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 53:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b53); 
	    		sample_icon.setImageBitmap(bm);
  			d = getResources().getDrawable(R.drawable.a53);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 54:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b54); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a54);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 60:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b60); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a60);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 61:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b61); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a61);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 62:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b62); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a62);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 63:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b63); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a63);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 64:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b64); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a64);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 65:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b65); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a65);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 70:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b70); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a70);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 71:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b71); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a71);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 72:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b72); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a72);
  			mainlayout.setBackgroundDrawable(d); 
	    		break;
  		case 73:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b73); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a73);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 74:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b74); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a74);
  			mainlayout.setBackgroundDrawable(d); 
	    		break;
  		case 75:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b75); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a75);
  			mainlayout.setBackgroundDrawable(d); 
	    		break;
  		case 76:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b76); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a76);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 77:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b77); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a77);
  			mainlayout.setBackgroundDrawable(d); 
	    		break;
  		case 80:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b80); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a80);
  			mainlayout.setBackgroundDrawable(d); 
	    		break;
  		case 81:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b81); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a81);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 82:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b82); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a82);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 83:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b83); 
	    		sample_icon.setImageBitmap(bm);
  			d = getResources().getDrawable(R.drawable.a83);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 84:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b84); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a84);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 85:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b85); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a85);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 90:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b90); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a90);
  			mainlayout.setBackgroundDrawable(d); 
	    		break;
  		case 91:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b91); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a91);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 92:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b92); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a92);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
  		case 93:	
	    		bm = BitmapFactory.decodeResource(getResources(),R.drawable.b93); 
	    		sample_icon.setImageBitmap(bm); 
  			d = getResources().getDrawable(R.drawable.a93);
  			mainlayout.setBackgroundDrawable(d);
	    		break;
			}
	         return which0;
	    }
		
		//改變警告圖示1
		public int warn_icon_change1(int which1) {
			switch (which1) {
  		case 1:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a1);  warn1_icon.setImageBitmap(bm);  break;
  		case 2:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a2);  warn1_icon.setImageBitmap(bm);  break;
  		case 3:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a3);  warn1_icon.setImageBitmap(bm);  break;
  		case 4:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a4);  warn1_icon.setImageBitmap(bm);  break;
  		case 5:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a5);  warn1_icon.setImageBitmap(bm);  break;
  		case 6:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a6);  warn1_icon.setImageBitmap(bm);  break;
  		case 7:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a7);  warn1_icon.setImageBitmap(bm);  break;
  		case 8:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a8);  warn1_icon.setImageBitmap(bm);  break;
  		case 9:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a9);  warn1_icon.setImageBitmap(bm);  break;
  		case 10:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a10);  warn1_icon.setImageBitmap(bm);  break;
  		case 11:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a11);  warn1_icon.setImageBitmap(bm);  break;
  		case 12:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a12);  warn1_icon.setImageBitmap(bm);  break;
  		case 13:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a13);  warn1_icon.setImageBitmap(bm);  break;
  		case 14:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a14);  warn1_icon.setImageBitmap(bm);  break;
  		case 15:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a15);  warn1_icon.setImageBitmap(bm);  break;
  		case 16:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a16);  warn1_icon.setImageBitmap(bm);  break;
  		case 17:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a17);  warn1_icon.setImageBitmap(bm);  break;
  		case 18:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a18);  warn1_icon.setImageBitmap(bm);  break;
  		case 19:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a19);  warn1_icon.setImageBitmap(bm);  break;
  		case 20:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a20);  warn1_icon.setImageBitmap(bm);  break;
  		case 21:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a21);  warn1_icon.setImageBitmap(bm);  break;
  		case 40:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a40);  warn1_icon.setImageBitmap(bm);  break;
			}
  		return which1;
	    }  			

		//改變警告圖示2
		public int warn_icon_change2(int which2) {
			switch (which2) {
  		case 1:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a1);  warn2_icon.setImageBitmap(bm);  break;
  		case 2:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a2);  warn2_icon.setImageBitmap(bm);  break;
  		case 3:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a3);  warn2_icon.setImageBitmap(bm);  break;
  		case 4:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a4);  warn2_icon.setImageBitmap(bm);  break;
  		case 5:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a5);  warn2_icon.setImageBitmap(bm);  break;
  		case 6:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a6);  warn2_icon.setImageBitmap(bm);  break;
  		case 7:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a7);  warn2_icon.setImageBitmap(bm);  break;
  		case 8:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a8);  warn2_icon.setImageBitmap(bm);  break;
  		case 9:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a9);  warn2_icon.setImageBitmap(bm);  break;
  		case 10:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a10);  warn2_icon.setImageBitmap(bm);  break;
  		case 11:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a11);  warn2_icon.setImageBitmap(bm);  break;
  		case 12:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a12);  warn2_icon.setImageBitmap(bm);  break;
  		case 13:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a13);  warn2_icon.setImageBitmap(bm);  break;
  		case 14:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a14);  warn2_icon.setImageBitmap(bm);  break;
  		case 15:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a15);  warn2_icon.setImageBitmap(bm);  break;
  		case 16:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a16);  warn2_icon.setImageBitmap(bm);  break;
  		case 17:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a17);  warn2_icon.setImageBitmap(bm);  break;
  		case 18:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a18);  warn2_icon.setImageBitmap(bm);  break;
  		case 19:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a19);  warn2_icon.setImageBitmap(bm);  break;
  		case 20:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a20);  warn2_icon.setImageBitmap(bm);  break;
  		case 21:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a21);  warn2_icon.setImageBitmap(bm);  break;
  		case 40:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a40);  warn2_icon.setImageBitmap(bm);  break;
			} 
  		return which2;
	    } 
		
		//改變警告圖示3
		public int warn_icon_change3(int which3) {
			switch (which3) {
  		case 1:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a1);  warn3_icon.setImageBitmap(bm);  break;
  		case 2:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a2);  warn3_icon.setImageBitmap(bm);  break;
  		case 3:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a3);  warn3_icon.setImageBitmap(bm);  break;
  		case 4:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a4);  warn3_icon.setImageBitmap(bm);  break;
  		case 5:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a5);  warn3_icon.setImageBitmap(bm);  break;
  		case 6:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a6);  warn3_icon.setImageBitmap(bm);  break;
  		case 7:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a7);  warn3_icon.setImageBitmap(bm);  break;
  		case 8:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a8);  warn3_icon.setImageBitmap(bm);  break;
  		case 9:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a9);  warn3_icon.setImageBitmap(bm);  break;
  		case 10:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a10);  warn3_icon.setImageBitmap(bm);  break;
  		case 11:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a11);  warn3_icon.setImageBitmap(bm);  break;
  		case 12:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a12);  warn3_icon.setImageBitmap(bm);  break;
  		case 13:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a13);  warn3_icon.setImageBitmap(bm);  break;
  		case 14:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a14);  warn3_icon.setImageBitmap(bm);  break;
  		case 15:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a15);  warn3_icon.setImageBitmap(bm);  break;
  		case 16:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a16);  warn3_icon.setImageBitmap(bm);  break;
  		case 17:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a17);  warn3_icon.setImageBitmap(bm);  break;
  		case 18:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a18);  warn3_icon.setImageBitmap(bm);  break;
  		case 19:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a19);  warn3_icon.setImageBitmap(bm);  break;
  		case 20:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a20);  warn3_icon.setImageBitmap(bm);  break;
  		case 21:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a21);  warn3_icon.setImageBitmap(bm);  break;
  		case 40:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a40);  warn3_icon.setImageBitmap(bm);  break;
			} 
  		return which3;
	    } 

		//改變警告圖示4
		public int warn_icon_change4(int which4) {
			switch (which4) {
  		case 1:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a1);  warn4_icon.setImageBitmap(bm);  break;
  		case 2:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a2);  warn4_icon.setImageBitmap(bm);  break;
  		case 3:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a3);  warn4_icon.setImageBitmap(bm);  break;
  		case 4:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a4);  warn4_icon.setImageBitmap(bm);  break;
  		case 5:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a5);  warn4_icon.setImageBitmap(bm);  break;
  		case 6:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a6);  warn4_icon.setImageBitmap(bm);  break;
  		case 7:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a7);  warn4_icon.setImageBitmap(bm);  break;
  		case 8:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a8);  warn4_icon.setImageBitmap(bm);  break;
  		case 9:	bm = BitmapFactory.decodeResource(getResources(),R.drawable.a9);  warn4_icon.setImageBitmap(bm);  break;
  		case 10:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a10);  warn4_icon.setImageBitmap(bm);  break;
  		case 11:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a11);  warn4_icon.setImageBitmap(bm);  break;
  		case 12:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a12);  warn4_icon.setImageBitmap(bm);  break;
  		case 13:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a13);  warn4_icon.setImageBitmap(bm);  break;
  		case 14:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a14);  warn4_icon.setImageBitmap(bm);  break;
  		case 15:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a15);  warn4_icon.setImageBitmap(bm);  break;
  		case 16:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a16);  warn4_icon.setImageBitmap(bm);  break;
  		case 17:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a17);  warn4_icon.setImageBitmap(bm);  break;
  		case 18:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a18);  warn4_icon.setImageBitmap(bm);  break;
  		case 19:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a19);  warn4_icon.setImageBitmap(bm);  break;
  		case 20:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a20);  warn4_icon.setImageBitmap(bm);  break;
  		case 21:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a21);  warn4_icon.setImageBitmap(bm);  break;
  		case 40:bm = BitmapFactory.decodeResource(getResources(),R.drawable.a40);  warn4_icon.setImageBitmap(bm);  break;
			} 
  		return which4;
	    }			  
	  
	  
		
		
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
