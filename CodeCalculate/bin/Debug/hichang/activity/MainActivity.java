package hichang.activity;

import hichang.Song.ImageAdapter;
import hichang.database.DataBase;
import hichang.ourView.GalleryFlow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static final String DB_NAME="HICHANG.db";
	public static final int VERSION=1;
	public DataBase databaseHelper=new DataBase(this,DB_NAME, null, VERSION);
	public boolean isSDCardExist=true;
	public boolean userFolderExist=true;
	
	Handler handler;
	ProgressDialog progressdialog;
	private AbsoluteLayout layout;
	GalleryFlow gallery;  
	ImageAdapter adapter;
	int[] images;

	int []pids=new int[1];
	ActivityManager am;
	MemoryInfo outInfo;
	TextView processInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try {
			databaseHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		layout=(AbsoluteLayout)findViewById(R.id.widget0);
		handler=new Handler(){
        	@Override
        	public void handleMessage(Message msg)
        	{
        		if(msg.what==0)
        		{
        			layout.setVisibility(View.INVISIBLE);
        			progressdialog=ProgressDialog.show(MainActivity.this, "��ȴ�...", "    ��һ��ʹ��\n���ڳ�ʼ������...");
        		}
        		else if(msg.what==1)
        		{
        			progressdialog.cancel();
        			layout.setVisibility(View.VISIBLE);
        		}
        	}
        };
        new Thread(){
            @Override
            public void run() {
                String settingpath="/sdcard/HiChang/hichang.ini";
            	File fileDirs=new File(settingpath);
            	if(!fileDirs.exists())
            	{
            		handler.sendEmptyMessage(0); 
                	try {
        				databaseHelper.copyFile("/sdcard/HiChang/","","hichang.ini");
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
                	createUserFolder();
    				copyMusic();
    				handler.sendEmptyMessage(1); 
            	}
            }
        }.start();
		int[] images = {
				R.drawable.main_practice, R.drawable.main_sing, R.drawable.main_party,
				R.drawable.main_help,
				R.drawable.main_practice, R.drawable.main_sing ,R.drawable.main_party,
				R.drawable.main_help};
		Intent intent1 = getIntent();
		int help = intent1.getIntExtra("help", -1);
		gallery = (GalleryFlow) findViewById(R.id.gallery);
		adapter = new ImageAdapter(this);
		adapter.setImages(images,600,600);
		gallery.setAdapter(adapter);
		gallery.setSpacing(-50);
		if(help == 1)
		{
			gallery.setSelection(Integer.MAX_VALUE);
		}
		else {
			gallery.setSelection(Integer.MAX_VALUE/2 - 2);
		}	
	}   

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==66){
	    	Intent intent=new Intent();
	    	int i=(int)gallery.getSelectedItemId()%4;
	    	
	    	if(i==0)
	    	{
	    		intent.putExtra("type", 1);
	    		intent.setClass(MainActivity.this, RemoteMusicActivity.class);
	    	}
	    	else if(i==1)
	    	{
	    		intent.putExtra("type", 0);
	    		intent.setClass(MainActivity.this, RemoteMusicActivity.class);
	    		this.finish();
	    	} 
	    	else if(i==2)
	    	{
	    		intent.putExtra("type", 2);
	    		intent.setClass(MainActivity.this, RemoteMusicActivity.class);
	    		this.finish();
	    	}  
	    	else if(i==3)
	    	{
	    		intent.setClass(MainActivity.this, HelpActivity.class);
	    		gallery.destroyDrawingCache();
	    		this.finish();
	    	}
	    	startActivity(intent);    	
		}	
		else if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}
	/*
	 * ���û�SDCard����Ӧ���ļ���
	 */
	public void createUserFolder()
	{
		if(!databaseHelper.checkSDCard())
		{
			isSDCardExist=false;
			userFolderExist=false;
			return;
		}
		else
		{
			isSDCardExist=true;
			if(!databaseHelper.createUserFolder())
			{
				userFolderExist=false;
			}
			else userFolderExist=true;
		}
	}	
	public void copyMusic()
	{
		//���ø���id�����ڿ�������(ԭ�����鳪�����);
		String[] songid;
		//���ø���id�����ڿ���ͼƬ
		String[] singerid;
		//�û��ļ���·��
		String SONGPATH="/sdcard/HiChang/Songs/";
		String PICTUREPATH="/sdcard/HiChang/Singer/";
        songid=new String[]{"153","408","476","686","842","1236","1353","1757","1823"};
        singerid=new String[]{"1","3","5","8","9","12","17","31","644"};
		if(isSDCardExist)
		{
			try {
				//����ԭ������
				for(int i=0;i<songid.length;i++)
				{
					File file=new File(SONGPATH+songid[i]+"_v.mp3");
					if(!file.exists())
					{
						databaseHelper.copyFile(SONGPATH+songid[i]+"/","music/", songid[i]+"_v.mp3");
					}	
				}
				for(int i=0;i<songid.length;i++)
				{
					File file=new File(SONGPATH+songid[i]+"_i.mp3");
					if(!file.exists())
						databaseHelper.copyFile(SONGPATH+songid[i]+"/","music/",songid[i]+"_i.mp3");
				}
				for(int i=0;i<songid.length;i++)
				{
					File file=new File(SONGPATH+songid[i]+".txt");
					if(!file.exists())
						databaseHelper.copyFile(SONGPATH+songid[i]+"/","music/", songid[i]+".txt");
				}
				for(int i=0;i<singerid.length;i++)
				{
					File file=new File(PICTUREPATH+singerid[i]+"_p.png");
					if(!file.exists())
						databaseHelper.copyFile(PICTUREPATH+singerid[i]+"/","picture/", singerid[i]+"_p.png");
				}
				for(int i=0;i<singerid.length;i++)
				{
					File file=new File(PICTUREPATH+singerid[i]+"_p_r.png");
					if(!file.exists())
						databaseHelper.copyFile(PICTUREPATH+singerid[i]+"/","picture/", singerid[i]+"_p_r.png");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.gc();
	}	
}
