package androidbaby.hkweather;

import java.net.URL;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class twitter extends Activity implements Runnable {

	
	private ImageButton thebutton1,thebutton2,thebutton3,thebutton4,thebutton5,thebutton6;
	public EditText edit;
	public Button add0,add4,add5,add6,add7,add8,add9,add10,ok;
	public String twitter,temp,tempper,uv,ap,notificationtext,day1,day2,day3,auto_text1,auto_text2,auto_text3;
	
	Thread thread;
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            
	        //����
	        setContentView(R.layout.twitter);
	        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  
	        
	        setTitle(getResources().getText(R.string.app_name4).toString());
	        
	        
	        edit = (EditText) findViewById(R.id.edit);
	        thebutton1 = (ImageButton) findViewById(R.id.button_a);
	        thebutton1 = (ImageButton) findViewById(R.id.button_a);
			thebutton2 = (ImageButton) findViewById(R.id.button_b);	
			thebutton3 = (ImageButton) findViewById(R.id.button_c);
			thebutton4 = (ImageButton) findViewById(R.id.button_d);
			thebutton5 = (ImageButton) findViewById(R.id.button_e);
			thebutton6 = (ImageButton) findViewById(R.id.button_f);
	        
	        
	        button();
	        
	        
	        setProgressBarIndeterminateVisibility(true);
			thread = new Thread(twitter.this);
			thread.start();
	}
	
	
	private void button() {
		// TODO Auto-generated method stub

		ok = (Button) findViewById(R.id.ok);
        add0 = (Button) findViewById(R.id.add0);		
   		add4 = (Button) findViewById(R.id.add4);	
        add5 = (Button) findViewById(R.id.add5);
        add6 = (Button) findViewById(R.id.add6);
   		add7 = (Button) findViewById(R.id.add7);	
   		add8 = (Button) findViewById(R.id.add8);
   		add9 = (Button) findViewById(R.id.add9);	
   		add10 = (Button) findViewById(R.id.add10);  		
 
   		
   		
		ok.setVisibility(View.INVISIBLE);
		add0.setVisibility(View.INVISIBLE);
		add4.setVisibility(View.INVISIBLE);
		add5.setVisibility(View.INVISIBLE);
		add6.setVisibility(View.INVISIBLE);
		add7.setVisibility(View.INVISIBLE);
		add8.setVisibility(View.INVISIBLE);
		add9.setVisibility(View.INVISIBLE);
		add10.setVisibility(View.INVISIBLE);

		
		
   		
   		
        thebutton1.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(twitter.this, main.class);
				startActivity(intent);
				twitter.this.finish();
				
			}        	 
        });	
		
		
        thebutton2.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(twitter.this, forecast.class);
				startActivity(intent);
				twitter.this.finish();
				
			}        	 
        });		
		

        thebutton3.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(twitter.this, local.class);
				startActivity(intent);
				twitter.this.finish();
				
			}        	 
        }); 
        
        
        
        thebutton5.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(twitter.this, helpme.class);
				startActivity(intent);
				twitter.this.finish();
				
			}        	 
        });
        
        
        thebutton6.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thread = new Thread(twitter.this);
				thread.start();
				thebutton4.setBackgroundResource(R.drawable.transparent);
				
				ok.setVisibility(View.INVISIBLE);
				add0.setVisibility(View.INVISIBLE);
				add4.setVisibility(View.INVISIBLE);
				add5.setVisibility(View.INVISIBLE);
				add6.setVisibility(View.INVISIBLE);
				add7.setVisibility(View.INVISIBLE);
				add8.setVisibility(View.INVISIBLE);
				add9.setVisibility(View.INVISIBLE);
				add10.setVisibility(View.INVISIBLE);

				
				setProgressBarIndeterminateVisibility(true);
				
			}        	 
        });
		
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		Vector<String> weathername = new Vector<String>();
		notificationtext = null;
        String warnt = null;
        String warnt2 = null;
        String warnt3 = null;  
        String warnt4 = null; 
		temp = "";
		tempper = "";
		uv = "";
		ap = "";
        
        
		try{
		

	        String uri = "http://www.weather.gov.hk/textonly/forecast/englishwx.htm";
	        HttpGet httpRequest = new HttpGet(uri);
	        HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
	        
	        		
	        	String strResult = EntityUtils.toString(httpResponse.getEntity());        
	        	
	        	//�ū�
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
	        	 * Pattern ����
	        	 * 
	        	 *  Pattern p = Pattern.compile("[A-Za-z]+");
	        	 *  Matcher m = p.matcher("Hello, Android!");
	        	 *  while (m.find()) {
	        	 *   System.out.println(m.group()); // prints "Hello" and "Android"
	        	 *    }
	        	 */

	        	
	        	//���
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
	        		        	
	        	
	        	//���
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
			
			

		        //�Ů�ìV����
		        String uri3 = "http://www.epd-asg.gov.hk/out/capi_rss.xml";
		        HttpGet httpRequest3 = new HttpGet(uri3);
		        HttpResponse httpResponse3 = new DefaultHttpClient().execute(httpRequest3);
		        
		        if(httpResponse3.getStatusLine().getStatusCode() == 200){
	        		
		        	String strResult3 = EntityUtils.toString(httpResponse3.getEntity(),"Big5");    
		        	        
		        	//�ϥ�
		        	String begin5 = "�{�ɪ��Ů�ìV����:";
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
			

			
		    
		    
    		//�ͮ�ĵ�i�r�y
	        String uri2 = "http://www.weather.gov.hk/textonly/warning/warn.htm";
	        HttpGet httpRequest2 = new HttpGet(uri2);
	        HttpResponse httpResponse2 = new DefaultHttpClient().execute(httpRequest2);


	        	String strResult2 = EntityUtils.toString(httpResponse2.getEntity());  
	        	String begin = "Warning Codes\n";
	        	String end =  "-->";
	        	
	        	int sindex = strResult2.indexOf(begin) + begin.length();
	        	int eindex = strResult2.indexOf(end,sindex);
	        	
	        	if ( sindex != -1 && eindex != -1 ){	
	        			        		
	        		Pattern p = Pattern.compile("[0-9]+");
	        		Matcher m = p.matcher(strResult2.substring(sindex,eindex));
	        	
	        	 	while (m.find()) {
	        	 		weathername.add(m.group());
	        		}
	        	 	
	        	 	
	        		for ( int i = 0; i < weathername.size(); i++) {
	        			
	        			System.out.println("Weather " + i + " = " + weathername.get(i));

	    	        	int warn_no = Integer.parseInt(weathername.get(i));
	    	        	
	    	        	if (i==0){
		    	        	switch (warn_no) {
	    	    			case 1: warnt = "�@���ٳƫH��"; break;
	    	    			case 2: warnt = "�T���j���H��"; break;
	    	    			case 3: warnt = "�K���F�_�P���μɭ��H��"; break;
	    	    			case 4: warnt = "�K����_�P���μɭ��H��"; break;
	    	    			case 5: warnt = "�K���F�n�P���μɭ��H��"; break;
	    	    			case 6: warnt = "�K����n�P���μɭ��H��"; break;
	    	    			case 7: warnt = "�E���P���μɭ��W�j�H��"; break;
	    	    			case 8: warnt = "�Q�������H��"; break;
	    	    			case 9: warnt = "����ɫBĵ�i�H��"; break;
	    	    			case 10: warnt = "����ɫBĵ�i�H��"; break;
	    	    			case 11: warnt = "�¦�ɫBĵ�i�H��"; break;
	    	    			case 12: warnt = "�p��ĵ�i"; break;
	    	    			case 13: warnt = "�s�ɥ_�������S�O���i"; break;
	    	    			case 14: warnt = "�s�d���mĵ�i"; break;
	    	    			case 15: warnt = "�j�P�u�ԭ��H��"; break;
	    	    			case 16: warnt = "����ĵ�i"; break;
	    	    			case 17: warnt = "������a�M�Iĵ�i"; break;
	    	    			case 18: warnt = "������a�M�Iĵ�i"; break;
	    	    			case 19: warnt = "�H�N�Ѯ�ĵ�i"; break;
	    	    			case 20: warnt = "�ż��Ѯ�ĵ�i"; break;
	    	    			case 21: warnt = "���Sĵ�i"; break;
	    	    			case 40: warnt = "null"; break;
	    	        		}

	    	        	}else if (i==2){
		    	        	switch (warn_no) {
	    	    			case 1: warnt2 = "�@���ٳƫH��"; break;
	    	    			case 2: warnt2 = "�T���j���H��"; break;
	    	    			case 3: warnt2 = "�K���F�_�P���μɭ��H��"; break;
	    	    			case 4: warnt2 = "�K����_�P���μɭ��H��"; break;
	    	    			case 5: warnt2 = "�K���F�n�P���μɭ��H��"; break;
	    	    			case 6: warnt2 = "�K����n�P���μɭ��H��"; break;
	    	    			case 7: warnt2 = "�E���P���μɭ��W�j�H��"; break;
	    	    			case 8: warnt2 = "�Q�������H��"; break;
	    	    			case 9: warnt2 = "����ɫBĵ�i�H��"; break;
	    	    			case 10: warnt2 = "����ɫBĵ�i�H��"; break;
	    	    			case 11: warnt2 = "�¦�ɫBĵ�i�H��"; break;
	    	    			case 12: warnt2 = "�p��ĵ�i"; break;
	    	    			case 13: warnt2 = "�s�ɥ_�������S�O���i"; break;
	    	    			case 14: warnt2 = "�s�d���mĵ�i"; break;
	    	    			case 15: warnt2 = "�j�P�u�ԭ��H��"; break;
	    	    			case 16: warnt2 = "����ĵ�i"; break;
	    	    			case 17: warnt2 = "������a�M�Iĵ�i"; break;
	    	    			case 18: warnt2 = "������a�M�Iĵ�i"; break;
	    	    			case 19: warnt2 = "�H�N�Ѯ�ĵ�i"; break;
	    	    			case 20: warnt2 = "�ż��Ѯ�ĵ�i"; break;
	    	    			case 21: warnt2 = "���Sĵ�i"; break;
	    	    			case 40: warnt2 = "null"; break;
	    	        	
	    	        		}

	    	        	}else if (i==3){
		    	        	switch (warn_no) {
	    	    			case 1: warnt3 = "�@���ٳƫH��"; break;
	    	    			case 2: warnt3 = "�T���j���H��"; break;
	    	    			case 3: warnt3 = "�K���F�_�P���μɭ��H��"; break;
	    	    			case 4: warnt3 = "�K����_�P���μɭ��H��"; break;
	    	    			case 5: warnt3 = "�K���F�n�P���μɭ��H��"; break;
	    	    			case 6: warnt3 = "�K����n�P���μɭ��H��"; break;
	    	    			case 7: warnt3 = "�E���P���μɭ��W�j�H��"; break;
	    	    			case 8: warnt3 = "�Q�������H��"; break;
	    	    			case 9: warnt3 = "����ɫBĵ�i�H��"; break;
	    	    			case 10: warnt3 = "����ɫBĵ�i�H��"; break;
	    	    			case 11: warnt3 = "�¦�ɫBĵ�i�H��"; break;
	    	    			case 12: warnt3 = "�p��ĵ�i"; break;
	    	    			case 13: warnt3 = "�s�ɥ_�������S�O���i"; break;
	    	    			case 14: warnt3 = "�s�d���mĵ�i"; break;
	    	    			case 15: warnt3 = "�j�P�u�ԭ��H��"; break;
	    	    			case 16: warnt3 = "����ĵ�i"; break;
	    	    			case 17: warnt3 = "������a�M�Iĵ�i"; break;
	    	    			case 18: warnt3 = "������a�M�Iĵ�i"; break;
	    	    			case 19: warnt3 = "�H�N�Ѯ�ĵ�i"; break;
	    	    			case 20: warnt3 = "�ż��Ѯ�ĵ�i"; break;
	    	    			case 21: warnt3 = "���Sĵ�i"; break;
	    	    			case 40: warnt3 = "null"; break;
	    	        	
	    	        		}

	    	        	}else{
		    	        	switch (warn_no) {
	    	    			case 1: warnt4 = "�@���ٳƫH��"; break;
	    	    			case 2: warnt4 = "�T���j���H��"; break;
	    	    			case 3: warnt4 = "�K���F�_�P���μɭ��H��"; break;
	    	    			case 4: warnt4 = "�K����_�P���μɭ��H��"; break;
	    	    			case 5: warnt4 = "�K���F�n�P���μɭ��H��"; break;
	    	    			case 6: warnt4 = "�K����n�P���μɭ��H��"; break;
	    	    			case 7: warnt4 = "�E���P���μɭ��W�j�H��"; break;
	    	    			case 8: warnt4 = "�Q�������H��"; break;
	    	    			case 9: warnt4 = "����ɫBĵ�i�H��"; break;
	    	    			case 10: warnt4 = "����ɫBĵ�i�H��"; break;
	    	    			case 11: warnt4 = "�¦�ɫBĵ�i�H��"; break;
	    	    			case 12: warnt4 = "�p��ĵ�i"; break;
	    	    			case 13: warnt4 = "�s�ɥ_�������S�O���i"; break;
	    	    			case 14: warnt4 = "�s�d���mĵ�i"; break;
	    	    			case 15: warnt4 = "�j�P�u�ԭ��H��"; break;
	    	    			case 16: warnt4 = "����ĵ�i"; break;
	    	    			case 17: warnt4 = "������a�M�Iĵ�i"; break;
	    	    			case 18: warnt4 = "������a�M�Iĵ�i"; break;
	    	    			case 19: warnt4 = "�H�N�Ѯ�ĵ�i"; break;
	    	    			case 20: warnt4 = "�ż��Ѯ�ĵ�i"; break;
	    	    			case 21: warnt4 = "���Sĵ�i"; break;
	    	    			case 40: warnt4 = "null"; break;
	    	        	
	    	        		}
	    	        	}	    	        	
	    	        	
	    	        	
	        		}
	        	}  
		    
            	if (warnt != null || warnt2 != null || warnt3 != null || warnt4 != null){
	               if (warnt == null){
	            	   warnt = "";
	               }
	               if (warnt2 == null){
	            	   warnt2 = "";
	               }
	               if (warnt3 == null){
	            	   warnt3 = "";
	               }
	               if (warnt4 == null){
	            	   warnt4 = "";
	               }
	               
	               	//�ͮ�ĵ�i��r
	               	notificationtext = warnt + " " + warnt2 + " " + warnt3 +  " " + warnt4 + " �{���ͮ�";
            	}
		    
		    
			UpdateResults.sendEmptyMessage(0);//UI�u�{�i�H��s    			
	    
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		errorResults.sendEmptyMessage(0);
	
	
		}
	}


	Handler errorResults = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
			new AlertDialog.Builder(twitter.this)
			.setTitle("")
			.setMessage(getResources().getText(R.string.errorlink).toString())
			.setPositiveButton
			(getResources().getText(R.string.ok).toString(),new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
				}
			}).show();
		}
	};	
	
	
	//UI�u�{��s	
	Handler UpdateResults = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
			
		
			if (notificationtext != null){
				add0.setText(" ... + [ " + notificationtext + " ]");
				add0.setVisibility(View.VISIBLE);
	            add0.setOnClickListener(new Button.OnClickListener(){
	        		public void onClick(View v) {
	        			// TODO Auto-generated method stub	
	        			edit.setText(edit.getText().toString() + notificationtext);
	        		}
	            });				
				
			}else{
				add0.setVisibility(View.INVISIBLE);
			}
		
			
			
            add4.setText(" ... + [ �̮a " + temp +" �� ]");
            add4.setOnClickListener(new Button.OnClickListener(){
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        			
        			edit.setText(edit.getText().toString() + " �̮a " + temp +" ��");
        			
        		}
            });
            
            add5.setText(" ... + [ ��� " + tempper +" % , ���~������ : " + uv + " ]");
            add5.setOnClickListener(new Button.OnClickListener(){
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        			
        			edit.setText(edit.getText().toString() + " ��� " + tempper +" % , ���~������ : " + uv );
        			
        		}
            });
            
            add6.setText(" ... + [ �Ů�ìV���� : " + ap + " ]");
            add6.setOnClickListener(new Button.OnClickListener(){
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        			
        			edit.setText(edit.getText().toString() + " �Ů�ìV���� : " + ap );
        			
        		}
            });
            
            add7.setText(" ... + [ �ᥪ�� , ��h���A =)]");
            add7.setOnClickListener(new Button.OnClickListener(){
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        			
        			edit.setText(edit.getText().toString() + " �ᥪ�� , ��h���A =)");
        			
        		}
            });
            
            add8.setText(" ... + [ ���B�� , �O�o�a�B�� ]");
            add8.setOnClickListener(new Button.OnClickListener(){
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        			
        			edit.setText(edit.getText().toString() + " ���B�� , �O�o�a�B��");
        			
        		}
            });       
            
            
            add9.setText(" ... + [ �}�Y��~�a���ݤ[�� ]");
            add9.setOnClickListener(new Button.OnClickListener(){
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        			
        			edit.setText(edit.getText().toString() + " �}�Y��~�a���ݤ[��");
        			
        		}
            });             
            
            add10.setText(" ... + [ �Ѯ��� , �n�n�O�@�ֽ��� ]");
            add10.setOnClickListener(new Button.OnClickListener(){
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        			
        			edit.setText(edit.getText().toString() + " �Ѯ��� , �n�n�O�@�ֽ���");
        			
        		}
            });  
            


            
       		//���ɵ��B�Ͳ{�b�Ѯ𱡪p
       		ok = (Button) findViewById(R.id.ok);	
            ok.setOnClickListener(new Button.OnClickListener(){
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        			
        			closekeyboard();
        			String success =  getResources().getText(R.string.twittertext0).toString() + edit.getText().toString();
        			
        	  	    Intent intent=new Intent(Intent.ACTION_SEND);
        	  	    
        	  	    intent.setType("text/plain");
        	  	    intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getText(R.string.twittertext2).toString());
        	  	    intent.putExtra(Intent.EXTRA_TEXT, success);
        	  	    Intent.createChooser(intent, getResources().getText(R.string.app_name).toString());
        	  	    
        	  	    startActivity(Intent.createChooser(intent, getTitle()));
        		}

            });
            
            ok.setVisibility(View.VISIBLE);
    		add4.setVisibility(View.VISIBLE);
    		add5.setVisibility(View.VISIBLE);
    		add6.setVisibility(View.VISIBLE);
    		add7.setVisibility(View.VISIBLE);
    		add8.setVisibility(View.VISIBLE);
    		add9.setVisibility(View.VISIBLE);
    		add10.setVisibility(View.VISIBLE);
           
            
			//edit ���Ц�m�̫�
			Editable etable = edit.getText();
			Selection.setSelection(etable, etable.length());
			
			
			//����Ū���i��
			thebutton4.setBackgroundResource(R.drawable.FDDA3E);
			setProgressBarIndeterminateVisibility(false);
		
		}
	};
	
	
	
	private void closekeyboard() {

		InputMethodManager m=(InputMethodManager) ok.getContext().getSystemService(INPUT_METHOD_SERVICE);

		m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 

	};
	
	
}
