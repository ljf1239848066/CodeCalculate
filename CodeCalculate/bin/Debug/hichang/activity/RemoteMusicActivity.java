package hichang.activity;

import hichang.Song.Singer;
import hichang.Song.Song;
import hichang.Song.LocalBitmap;
import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RemoteMusicActivity extends Activity{
	private ImageView[]heatimage=new ImageView[10];	//热度图标布局
	private ImageView[]singerimage=new ImageView[9];//歌星头像
	private ImageView[]singerbox=new ImageView[9];	//歌星背景框
	private ImageView[]number=new ImageView[10];	//序号
	private ImageView[]arrow=new ImageView[4];		//方向箭头
	private TextView[] songname=new TextView[10];	//歌曲名列表
	private TextView[] singername=new TextView[10];	//歌手名列表
	private TextView[] musicstar=new TextView[9];	//歌星列表
	private TextView leftText;						//左指示栏文本
	private TextView rightText;						//右指示栏文本
	private TextView titleText;						//标题文本
	private TextView pageText;						//页码
	private TextView search;                      	//搜索
	private LinearLayout searchframe;				//搜索框背景布局
	private ImageView keyBoard;                     //键盘
	private ImageView midSmallBack;					//中间小背景
	private ImageView searchBack;					//键盘背景
	private ImageView smallarrow;					//小箭头
	private ImageView oKkey;                        //OK键
	private AbsoluteLayout layout0;
	private AbsoluteLayout layout;
	
	private Animation showImage;    //显示数据动画
	private Handler handler;
	private Timer timer;
	
	int[] idsongname;
	int[] idsingername;
	int[] idsinger;
	int[] idheatimage;
	int[] idheat;
	int[] idimage;
	int[] idnumber;
	int[] idbox;
	int[] idpagepic;
	int[] idarrow;
	
	/*
	 * 标识当前页面状态
	 * flag=1:歌星点歌
	 * flag=2:排行榜点歌
	 * flag=3:歌曲点歌
	 */	
	private int flag=2;
	//页码
	private int page=1;
	//最大页码
	private int maxPage;
	//最后一页歌曲/歌手数
	private int lastPageCount;
	//当前页面歌曲或歌手数
	private int nowCount=10;
	//从歌星点歌界面跳转到歌曲点歌界面时歌星界面的页码
	private int prePage=1;
	//从歌星点歌界面跳转到歌曲点歌界面时输入框遗留的文本内容
	private String preText;
	//是否第一次进入该页面
	private boolean isFirstEnter=true;
	//从前面页面跳转过来的参数
	private int afferentParam=0;
	//传出的参数
	private String outflowParam;
	//按键
	int keyCode;
	/*
	 * OK键是否已点过
	 * flag=1:isOKDown=false时数字键用于数字输入搜索歌手
	 * 		  isOKDown=true时表示输入结束，进入选择歌手状态
	 * flag=2:isOKDown任意，但此时设置为false，以便进入歌手选歌或歌曲点歌时输入数字搜索
	 * flag=3:isOKDown=false时数字键用于数字输入搜索歌手
	 * 		  isOKDown=true时表示输入结束，进入选择歌曲状态
	 */
	boolean isOKDown=false;
	/*
	 * 是否是从歌星点歌界面跳转到歌曲点歌界面
	 * 若是，则返回键可跳转回歌星点歌界面，并且歌曲点歌界面显示的是所有包含某歌手的歌曲
	 * 否则，返回键不可跳回，且歌曲点歌界面显示的是所有歌曲（其中）10首
	 */
	boolean isFromSinger=false;
	//是否进入过歌曲点歌界面
	boolean isFromSong=false;
	//歌星点歌界面获取的歌手名
	private String cSingerName="";
	//字体样式实例
	Typeface typeFace;
	
	Song song;
	Singer singer;
	//数据库中歌曲表中歌曲总数
	int songcount;
	//数据库中歌手表中歌手总数
	int singercount;
	//歌曲清单
	List<Song> songList;
	//歌手清单,只需要歌手名信息
	List<Singer> singerList;
	
    /** Called when the activity is first created. */
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        //去掉Activity上面的状态栏 
        setContentView(R.layout.remotemusic);
        
        // 使用assets/fonts/目录下字体 
        typeFace = Typeface.createFromAsset(getAssets(),"fonts/fzzy.ttf");
        
        arrow[0]=(ImageView)findViewById(R.id.arrow_up);
        arrow[1]=(ImageView)findViewById(R.id.arrow_down);
        arrow[2]=(ImageView)findViewById(R.id.arrow_left);
        arrow[3]=(ImageView)findViewById(R.id.arrow_right);
        leftText=(TextView)findViewById(R.id.lefttext);
        rightText=(TextView)findViewById(R.id.righttext);
        titleText=(TextView)findViewById(R.id.tilte);
        pageText=(TextView)findViewById(R.id.page);
        search=(TextView)findViewById(R.id.search);
        searchframe=(LinearLayout)findViewById(R.id.searchframe);
        midSmallBack=(ImageView)findViewById(R.id.midsmallframe);
        keyBoard=(ImageView)findViewById(R.id.keyboard_num);
        searchBack=(ImageView)findViewById(R.id.searchback);
        oKkey=(ImageView)findViewById(R.id.keyboard_ok);
        smallarrow=(ImageView)findViewById(R.id.smallarrow);
        layout0=(AbsoluteLayout)findViewById(R.id.widget0);
        layout=(AbsoluteLayout)findViewById(R.id.layout);

        idsongname=new int[]{R.id.songname1,R.id.songname2,R.id.songname3,R.id.songname4,R.id.songname5,R.id.songname6,R.id.songname7,R.id.songname8,R.id.songname9,R.id.songname0};
        idsingername=new int[]{R.id.singername1,R.id.singername2,R.id.singername3,R.id.singername4,R.id.singername5,R.id.singername6,R.id.singername7,R.id.singername8,R.id.singername9,R.id.singername0};
        idsinger=new int[]{R.id.singer1,R.id.singer2,R.id.singer3,R.id.singer4,R.id.singer5,R.id.singer6,R.id.singer7,R.id.singer8,R.id.singer9};
        idheatimage=new int[]{R.id.heat1,R.id.heat2,R.id.heat3,R.id.heat4,R.id.heat5,R.id.heat6,R.id.heat7,R.id.heat8,R.id.heat9,R.id.heat0};
        idimage=new int[]{R.id.image1,R.id.image2,R.id.image3,R.id.image4,R.id.image5,R.id.image6,R.id.image7,R.id.image8,R.id.image9};
        idnumber=new int[]{R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9,R.id.button0};
        idbox=new int[]{R.id.box1,R.id.box2,R.id.box3,R.id.box4,R.id.box5,R.id.box6,R.id.box7,R.id.box8,R.id.box9};
        idheat=new int[]{R.drawable.heat3,R.drawable.heat2,R.drawable.heat1,R.drawable.heat0};
        idpagepic=new int[]{R.drawable.zero,R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine};
        idarrow=new int[]{R.drawable.arrow_up,R.drawable.arrow_down,R.drawable.arrow_left,R.drawable.arrow_right,R.drawable.upwhite,R.drawable.downwhite,R.drawable.leftwhite,R.drawable.rightwhite};
        
        for(int i=0;i<10;i++)
        {
        	songname[i]=(TextView)findViewById(idsongname[i]);
        	singername[i]=(TextView)findViewById(idsingername[i]);  	
        	heatimage[i]=(ImageView)findViewById(idheatimage[i]);
        	number[i]=(ImageView)findViewById(idnumber[i]);
        	if(i<9)
        	{
        		musicstar[i]=(TextView)findViewById(idsinger[i]);
        		singerimage[i]=(ImageView)findViewById(idimage[i]);
        		singerbox[i]=(ImageView)findViewById(idbox[i]);	
        	}
        }
        //更换字体
        leftText.setTypeface(typeFace);
        rightText.setTypeface(typeFace);
        titleText.setTypeface(typeFace);
        
        Intent intent=getIntent();
        afferentParam=intent.getIntExtra("type", -1);
        
        //实例化Song和Singer
        song=new Song(this.getBaseContext());
    	singer=new Singer(this.getBaseContext());
    	
    	showImage=AnimationUtils.loadAnimation(this, R.anim.showimage);
    	showImage.setFillAfter(true);
	
    	//初始化表单信息
    	initText();
        setListString(flag,1);	
        timer=new Timer();
        handler=new Handler()
        {
        	@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
        		if(msg.what==0){
        			arrow[0].setBackgroundResource(idarrow[msg.what]);
        		}else if(msg.what==1){
        			arrow[1].setBackgroundResource(idarrow[msg.what]);
        		}else if(msg.what==2){
        			arrow[2].setBackgroundResource(idarrow[msg.what]);
        		}else if(msg.what==3){
        			arrow[3].setBackgroundResource(idarrow[msg.what]);
        		}else if(msg.what==4){
        			arrow[0].setBackgroundResource(idarrow[msg.what]);
        			timer.schedule(new TimerTask() {
    					
    					@Override
    					public void run() {
    						// TODO Auto-generated method stub
    						handler.sendEmptyMessage(0);
    					}
    				}, 150);
        		}else if(msg.what==5){
        			arrow[1].setBackgroundResource(idarrow[msg.what]);
        			timer.schedule(new TimerTask() {
    					
    					@Override
    					public void run() {
    						// TODO Auto-generated method stub
    						handler.sendEmptyMessage(1);
    					}
    				}, 150);
        		}else if(msg.what==6){
        			arrow[2].setBackgroundResource(idarrow[msg.what]);
        			timer.schedule(new TimerTask() {
        				
        				@Override
        				public void run() {
        					// TODO Auto-generated method stub
        					handler.sendEmptyMessage(2);
        				}
        			}, 150);
        		}else if(msg.what==7){
        			arrow[3].setBackgroundResource(idarrow[msg.what]);
            		timer.schedule(new TimerTask() {
        				
        				@Override
        				public void run() {
        					// TODO Auto-generated method stub
        					handler.sendEmptyMessage(3);
        				}
        			}, 150);
        		}
        	}
        };
 
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event)
    {
    	switch(keyCode)
    	{
    		case KeyEvent.KEYCODE_DPAD_UP:
				handler.sendEmptyMessage(4);		
	    		if(page==1)return false;		
    			page--;
    			initText();
	    		setListString(flag,page);
    		break;
    		case KeyEvent.KEYCODE_DPAD_DOWN:
    			handler.sendEmptyMessage(5);
    			page++;
    			initText();
    			setListString(flag,page);
    		break;
    		case KeyEvent.KEYCODE_DPAD_LEFT:
			handler.sendEmptyMessage(6);
			isOKDown=false;
			oKkey.setImageResource(R.drawable.keyborad_okbutton2);
			smallarrow.setImageResource(R.drawable.right);
			searchBack.setImageResource(R.drawable.keyframe_bright);
			midSmallBack.setImageResource(R.drawable.midframe_dark);
			if (flag == 1) {
				flag = 3;
				search.setText("");
    			search.setEnabled(true);
    			isOKDown = false;
			} 
			else if (flag == 3)
			{
				flag--;
				isFromSinger=false;
			}
			else {
				flag--;
				search.setText("");
    			search.setEnabled(true);
				isOKDown = false;
			}
			page = 1;
			layout.startAnimation(showImage);
			initText();
			setListString(flag, page);
    		break;
    		case KeyEvent.KEYCODE_DPAD_RIGHT:
			handler.sendEmptyMessage(7);
			isOKDown=false;
			oKkey.setImageResource(R.drawable.keyborad_okbutton2);
			smallarrow.setImageResource(R.drawable.right);
			searchBack.setImageResource(R.drawable.keyframe_bright);
			midSmallBack.setImageResource(R.drawable.midframe_dark);
    		if(flag==3)
    		{
    			flag=1;
    			search.setText("");
    			search.setEnabled(true);
    			isFromSinger=false;
    		}
    		else if(flag==2)
    		{
    			flag++;
    			isOKDown=false;
    			search.setText("");
    			search.setEnabled(true);
    		}
    		else
    		{
    			flag++;
    		}
    		page=1;
    		layout.startAnimation(showImage);
    		initText();
    		setListString(flag,page);
    		break; 	
    		case KeyEvent.KEYCODE_BACK:
    		{
    			if(flag==3&&isFromSinger)
    			{
    				flag=1;
    				search.setText(preText);
    				initText();
    				setListString(flag,prePage);
    				page=prePage;
    				isFromSinger=false;
    			}
    			else if(!isFromSinger)
    			{
    				showImage.cancel();
    				Intent intent=new Intent();
    				intent.putExtra("pid", 2);
    				intent.setClass(RemoteMusicActivity.this, MainActivity.class);
    				startActivity(intent);
    				System.exit(0);
    				finish();
//    				this.onStop();
//    				Intent intent=new Intent();
//    				intent.setClass(RemoteMusicActivity.this, MainActivity.class);
//    				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//    				android.os.Process.killProcess(android.os.Process.myPid());
//    				startActivity(intent);
//    				onDestroy();
    			}
    		}
    		break;
    		case KeyEvent.KEYCODE_ENTER:  //OK键
    		{
    			if(flag!=2){
    				if(!isOKDown)
	    			{
	    				search.setEnabled(false);
	    				oKkey.setImageResource(R.drawable.keyboard_okbutton1);
	    				smallarrow.setImageResource(R.drawable.left);
	    				searchBack.setImageResource(R.drawable.keyframe_dark);
	    				midSmallBack.setImageResource(R.drawable.midframe_bright);
	    				isOKDown=true;
	    			}
	    			else if(isOKDown)
	    			{
	    				search.setEnabled(true);
	    				isOKDown=false;
	    				oKkey.setImageResource(R.drawable.keyborad_okbutton2);
	    				smallarrow.setImageResource(R.drawable.right);
	    				searchBack.setImageResource(R.drawable.keyframe_bright);
	    				midSmallBack.setImageResource(R.drawable.midframe_dark);
	    			}
    			}
    		}
    		break;
    		case KeyEvent.KEYCODE_0:
    		{
    			dealKeyDown(0);
    		}
    		break;
    		case KeyEvent.KEYCODE_1:
    		{
    			dealKeyDown(1);
    		}
    		break;
    		case KeyEvent.KEYCODE_2:
    		{
    			dealKeyDown(2);
    		}
    		break;
    		case KeyEvent.KEYCODE_3:
    		{
    			dealKeyDown(3);
    		}
    		break;
    		case KeyEvent.KEYCODE_4:
    		{
    			dealKeyDown(4);
    		}
    		break;
    		case KeyEvent.KEYCODE_5:
    		{
    			dealKeyDown(5);
    		}
    		break;
    		case KeyEvent.KEYCODE_6:
    		{
    			dealKeyDown(6);
    		}
    		break;
    		case KeyEvent.KEYCODE_7:
    		{
    			dealKeyDown(7);
    		}
    		break;
    		case KeyEvent.KEYCODE_8:
    		{
    			dealKeyDown(8);
    		}
    		break;
    		case KeyEvent.KEYCODE_9:
    		{
    			dealKeyDown(9);
    		}
    		break;
    		case 219:
    		{
    			if(!isOKDown){
    				String text="";
    				int length=search.getText().length();
    				if(length>0){
    					text=search.getText().toString().substring(0, length-1);
    				}
    				search.setText(text);
    				onSeTextChange();
    			}
    		}
    	default :
    		return false;
    	}
    	return true;
    }
  //标题栏、左右栏、图片
    public void initText()
    {
    	if(flag==1)
		{
    		searchframe.setVisibility(View.VISIBLE);
    		searchBack.setVisibility(View.VISIBLE);
    		keyBoard.setVisibility(View.VISIBLE);
    		oKkey.setVisibility(View.VISIBLE);
    		smallarrow.setVisibility(View.VISIBLE);
    		midSmallBack.setVisibility(View.VISIBLE);
			titleText.setText("首页>歌星点歌");
			leftText.setText("歌曲点歌");
			rightText.setText("排行榜点歌");
		}
		else if(flag==2)
		{
			searchBack.setVisibility(View.INVISIBLE);
			searchframe.setVisibility(View.INVISIBLE);
			keyBoard.setVisibility(View.INVISIBLE);
    		oKkey.setVisibility(View.INVISIBLE);
    		smallarrow.setVisibility(View.INVISIBLE);
    		midSmallBack.setVisibility(View.INVISIBLE);
			titleText.setText("首页>排行榜点歌");
			leftText.setText("歌星点歌");
			rightText.setText("歌曲点歌");	
			
		}
		else
		{
			searchBack.setVisibility(View.VISIBLE);
			searchframe.setVisibility(View.VISIBLE);
			keyBoard.setVisibility(View.VISIBLE);
    		oKkey.setVisibility(View.VISIBLE);
    		smallarrow.setVisibility(View.VISIBLE);
    		midSmallBack.setVisibility(View.VISIBLE);
    		if(isFromSinger)titleText.setText("首页>歌星点歌>歌曲点歌");
    		else titleText.setText("首页>歌曲点歌");
			leftText.setText("排行榜点歌");
			rightText.setText("歌星点歌");
		}
    	if(flag==1)
    	{
    		for(int i=0;i<10;i++)
    		{
    			number[i].setVisibility(View.INVISIBLE);
    			songname[i].setVisibility(View.INVISIBLE);
    			singername[i].setVisibility(View.INVISIBLE);
    			if(i<9)
    			{
    				musicstar[i].setVisibility(View.VISIBLE);
        			singerbox[i].setVisibility(View.VISIBLE);
    				singerimage[i].setVisibility(View.VISIBLE);  
    			}
    		}
    	}
    	else
    	{
    		for(int i=0;i<10;i++)
    		{
    			number[i].setVisibility(View.VISIBLE);
    			songname[i].setVisibility(View.VISIBLE);
    			singername[i].setVisibility(View.VISIBLE);
    			if(i<9)
    			{
    				musicstar[i].setVisibility(View.INVISIBLE);
    				singerbox[i].setVisibility(View.INVISIBLE);
    				singerimage[i].setVisibility(View.INVISIBLE);  
    			}
    		}
    	}
    	if(flag==2)
    	{
    		if(!isFirstEnter)
    		{
    			for(int i=0;i<10;i++)
        		{
        			heatimage[i].setVisibility(View.VISIBLE);
        			songname[i].setWidth(323);
    				singername[i].setWidth(184);
    				songname[i].setTextSize(30);
    				singername[i].setTextSize(30);
    				if(i<5)
    				{
    					songname[i].setX(378);
    					songname[i].setY(243+i*138);
    					singername[i].setX(762);
    					singername[i].setY(243+i*138);
    					number[i].setX(300);
    					number[i].setY(226+i*138);
    				}
    				else {
    					songname[i].setX(1046);
    					songname[i].setY(i*138-447);
    					singername[i].setX(1426);
    					singername[i].setY(i*138-447);
    					number[i].setX(966);
    					number[i].setY(i*138-464);
    				}
        		}
    		}
    		isFirstEnter=false;
    	}
    	else 
    	{
    		for(int i=0;i<10;i++)
    		{
    			heatimage[i].setVisibility(View.INVISIBLE);
    			songname[i].setWidth(180);
				songname[i].setTextSize(22);
				singername[i].setWidth(140);
				singername[i].setTextSize(22);
				if(i<5)
				{
					songname[i].setX(325);
					songname[i].setY(280+i*110);
					singername[i].setX(565);
					singername[i].setY(280+i*110);
					number[i].setX(250);
					number[i].setY(270+i*110);
				}
				else {
					songname[i].setX(830);
					songname[i].setY(i*110-270);
					singername[i].setX(1070);
					singername[i].setY(i*110-270);
					number[i].setX(750);
					number[i].setY(i*110-280);
				}
    		}
    	}
    }
    //设置显示
    public void setListString(int currentflag,int currentpage)
    {   
    	nowCount=10;
    	//设置最大页码数和最后一页的歌或歌手数目
    	if(currentflag==1)
    	{
    		String searchtext=search.getText().toString();
    		if(searchtext=="")singercount=singer.getSingerCount();
    		else singercount=singer.getSingerCountBySimpleName(searchtext);
        	if(singercount%9==0)
        	{
        		maxPage=singercount/9;
        		lastPageCount=9;
        	}
        	else
        	{
        		maxPage=singercount/9+1;
        		lastPageCount=singercount%9;
        	}
        	if(singercount==0)
        	{
        		maxPage=0;
        		nowCount=lastPageCount=0;
        	}
    	}
    	else if(currentflag==2)
    	{
    		songcount=song.getSongCount();
        	if(songcount%10==0)
        	{
        		maxPage=songcount/10;
        		lastPageCount=10;
        	}
        	else
        	{
        		maxPage=songcount/10+1;
        		lastPageCount=songcount%10;
        	}
        	if(songcount==0)
        	{
        		maxPage=0;
        		nowCount=lastPageCount=0;
        	}
    	}
    	else
    	{
    		String searchtext=search.getText().toString();
    		if(isFromSinger&&searchtext=="")songcount=song.getSongCountBySigner(cSingerName);
    		else if(isFromSinger&&searchtext!="")songcount=song.getSongCountBySgAndSn(cSingerName, searchtext);
    		else if(!isFromSinger&&searchtext!="")songcount=song.getSongCountBySimpleName(searchtext);
    		else songcount=song.getSongCount();
        	if(songcount%10==0)
        	{
        		maxPage=songcount/10;
        		lastPageCount=10;
        	}
        	else
        	{
        		maxPage=songcount/10+1;
        		lastPageCount=songcount%10;
        	}
        	if(songcount==0)
        	{
        		maxPage=0;
        		nowCount=lastPageCount=0;
        	}
    	}
    	//如果当前页码大于最大页码或小于1直接返回不做任何更改
    	if(currentpage>maxPage&&maxPage!=0)
    	{
    		page--;
    		return;
    	}
    	if(currentpage<1)
    	{
    		page++;
    		return;
    	}
    	if(currentpage>maxPage&&maxPage==0)
    	{
    		currentpage=page=1;
    	}
    	//显示歌手或者歌曲
    	if(currentpage==maxPage)
    	{
    		nowCount=lastPageCount;
    		if(flag==1)
    		{
    			for(int i=9;i>lastPageCount;i--)
        		{
        			//若当前页码为最后一页并且歌曲或歌手总数不为9的整数倍时，9个列表空间中无数据的控件隐藏
        			musicstar[i-1].setVisibility(View.INVISIBLE);
        		}
    		}
    		else if(flag==2)
    		{
        		for(int i=10;i>lastPageCount;i--)
        		{
        			//若当前页码为最后一页是且歌曲或歌手总数不为10的整数倍时，10个列表空间中无数据的控件隐藏
        			songname[i-1].setVisibility(View.INVISIBLE);
        			singername[i-1].setVisibility(View.INVISIBLE);
        			heatimage[i-1].setVisibility(View.INVISIBLE);
        		}
    		}
    		else 
    		{
    			for(int i=10;i>lastPageCount;i--)
        		{
        			//若当前页码为最后一页是且歌曲或歌手总数不为10的整数倍时，10个列表空间中无数据的控件隐藏
        			songname[i-1].setVisibility(View.INVISIBLE);
        			singername[i-1].setVisibility(View.INVISIBLE);
        		}
    		}
    	}
    	//从数据库拿出信息并进行显示
    	switch(currentflag)
    	{
    	case 1:
    	{	
    		singerList=null;
    		String searchtext=search.getText().toString();
    		if(searchtext!="")singerList=singer.queryNineSingerBySN(searchtext,currentpage);
    		else singerList=singer.queryNineSinger(currentpage);	
    		if(nowCount==10)nowCount=9;
    		String str="";
    		int i=0;
    		if(nowCount!=0)nowCount=singerList.size();
    		for(;i<nowCount;i++)
    		{
    			str=singerList.get(i).getName().trim();
    			if(str.charAt(0)>0&&str.charAt(0)<128&&str.length()>8)str=str.substring(0, 8);
    			else if(str.length()>4&&(str.charAt(0)<0||str.charAt(0)>128))str=str.substring(0, 4);
    			musicstar[i].setText(str);
    			musicstar[i].setVisibility(View.VISIBLE);
    			String id=singerList.get(i).getiD()+"";
    			String picPath="/sdcard/HiChang/Singer/"+id+"/"+id+"_p_r.png";
    			File imagefile=new File(picPath);
    			if(imagefile.exists()&&LocalBitmap.getLoacalBitmap(picPath)!=null)singerimage[i].setImageBitmap(LocalBitmap.getLoacalBitmap(picPath));
    			else singerimage[i].setImageResource(R.drawable.photo);
    		}
    		for(;i<9;i++)
    		{
    			//若当前页码为最后一页是且歌曲或歌手总数不为9的整数倍时，9个列表空间中无数据的控件隐藏
    			musicstar[i].setText("");
    			musicstar[i].setVisibility(View.INVISIBLE);
    			singerimage[i].setImageResource(R.drawable.photo);
    		}
    	}
    	break;
    	case 2:
    	{
    		songList=null;
    		songList=song.queryTenSongByTwoClicks(currentpage);
    		if(nowCount!=0)nowCount=songList.size();
    		String str="";
    		int i=0;
    		for(;i<nowCount;i++)
    		{
    			songname[i].setVisibility(View.VISIBLE);
    			str=songList.get(i).getName().trim();
    			if(str.charAt(0)>0&&str.charAt(0)<128&&str.length()>12)str=str.substring(0, 12)+"…";
    			else if(str.length()>7&&(str.length()>4&&(str.charAt(0)<0)||str.charAt(0)>128))str=str.substring(0, 7)+"…";
    			if(songList.get(i).getIsAvailable()==1)songname[i].setTextColor(Color.WHITE);
    			else songname[i].setTextColor(Color.GRAY);
    			songname[i].setText(str);
    			songname[i].setVisibility(View.VISIBLE);
    			str=songList.get(i).getSinger1().trim();
    			if(str.charAt(0)>0&&str.charAt(0)<128&&str.length()>8)str=str.substring(0, 8)+"…";
    			else if(str.length()>4&&(str.charAt(0)<0||str.charAt(0)>128))str=str.substring(0, 4)+"…";
    			if(songList.get(i).getIsAvailable()==1)singername[i].setTextColor(Color.WHITE);
    			else singername[i].setTextColor(Color.GRAY);
            	singername[i].setText(str);
            	singername[i].setVisibility(View.VISIBLE);
    		}
    		for(;i<10;i++)
    		{
    			songname[i].setText("");
    			singername[i].setText("");
    		}
    		if(currentpage==1)
    		{
        		for(i=0;i<3;i++)
        		{
        			heatimage[i].setBackgroundResource(idheat[i]);
        		}
    		}
    		else 
    		{
    			for(i=0;i<3;i++)
        		{ 
        			heatimage[i].setBackgroundResource(idheat[3]);
        		}
    		}
    	}
    	break;
    	case 3:
    	{
    		songList=null;
    		String searchtext=search.getText().toString();
    		if(isFromSinger&&searchtext=="")songList=song.findTenSongBySinger(cSingerName, currentpage);
    		else if(isFromSinger&&searchtext!="")songList=song.findTenSongBySgAndSn(cSingerName, searchtext, currentpage);
    		else if(!isFromSinger&&searchtext!="")songList=song.findTenSongBySimpleName(searchtext, currentpage);
    		else songList=song.findTenSong(currentpage);
    		if(nowCount!=0)nowCount=songList.size();
    		String str="";
    		int i=0;
    		for(i=0;i<nowCount;i++)
    		{
    			songname[i].setVisibility(View.VISIBLE);
    			str=songList.get(i).getName().trim();
    			if(str.charAt(0)>0&&str.charAt(0)<128&&str.length()>12)str=str.substring(0, 12)+"…";
    			else if(str.length()>7&&(str.charAt(0)<0||str.charAt(0)>128))str=str.substring(0, 7)+"…";
    			if(songList.get(i).getIsAvailable()==1)songname[i].setTextColor(Color.WHITE);
    			else songname[i].setTextColor(Color.GRAY);
    			songname[i].setText(str);
    			songname[i].setVisibility(View.VISIBLE);
    			if(isFromSinger)str=cSingerName;
    			else str=songList.get(i).getSinger1().trim();
    			if(str.charAt(0)>0&&str.charAt(0)<128&&str.length()>8)str=str.substring(0, 8)+"…";
    			else if(str.length()>4&&(str.charAt(0)<0||str.charAt(0)>128))str=str.substring(0, 4)+"…";
    			if(songList.get(i).getIsAvailable()==1)singername[i].setTextColor(Color.WHITE);
    			else singername[i].setTextColor(Color.GRAY);
            	singername[i].setText(str);
            	singername[i].setVisibility(View.VISIBLE);
    		}
    		for(;i<10;i++)
    		{
    			songname[i].setText("");
    			singername[i].setText("");
    		}
    	}
    	break;
    	}
    	String strPage=currentpage+"/"+maxPage;
    	pageText.setX(1700-strPage.length()*15);
    	pageText.setText(strPage);
    	
    }
    
    //榜单点歌、歌曲点歌
    public void remoteByList(int keycode)
    {
    	Song outputSong=new Song();
    	if(keycode>=0&&keycode<nowCount)
    	{
    		outputSong=songList.get(keycode);
    		if(outputSong.getIsAvailable()==0)
    		{
    			Toast.makeText(getApplicationContext(), "抱歉！该歌曲不存在！", Toast.LENGTH_LONG).show();
    			return;
    		}
    		Intent intent=new Intent();
        	intent.putExtra("songId", outputSong.getSongID());
        	Class nextclass;
        	if(afferentParam==0)nextclass=HiSingActivity.class;
        	else if(afferentParam==1)nextclass=PracticeActivity.class;
        	else nextclass=PartyActivity.class;
        	intent.setClass(RemoteMusicActivity.this, nextclass);
        	RemoteMusicActivity.this.startActivity(intent);
        	System.exit(0);
        	finish();
    	}
    	else return;
    }
    //歌星点歌界面进入歌曲点歌
    public void remoteMusicBySinger(int keycode)
    {
    	if(keycode<0||keycode+1>nowCount){
    		return;
    	}
    	prePage=page;
    	preText=search.getText().toString();
    	search.setText("");
    	page=1;
    	flag=3;
    	isFromSinger=true;
    	isOKDown=false;
		oKkey.setImageResource(R.drawable.keyborad_okbutton2);
		smallarrow.setImageResource(R.drawable.right);
		searchBack.setImageResource(R.drawable.keyframe_bright);
		midSmallBack.setImageResource(R.drawable.midframe_dark);
    	cSingerName=singerList.get(keycode).getName();
    	initText();
    	setListString(3, 1);
    }
    //处理数字按键事件
    public void dealKeyDown(int keycode)
    {
    	if(flag==1)
    	{
    		if(isOKDown)
    		{
    			remoteMusicBySinger(keycode-1);
    		} else {
    			String str=search.getText().toString();
        		search.setText(str+keycode+"");
        		onSeTextChange();
    		}
    	} 
    	else if(flag==2)
    	{
    		if(keycode==0)remoteByList(9);
			else remoteByList(keycode-1);
    	} 
    	else
    	{
    		if(isOKDown){
    			if(keycode==0)remoteByList(9);
    			else remoteByList(keycode-1);
    		} 
    		else
    		{
    			String str=search.getText().toString();
        		search.setText(str+keycode+"");
        		onSeTextChange();
    		}
    	}
    }
    public void onSeTextChange()
    {
    	page=1;
    	initText();
		setListString(flag, 1);
    }
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		heatimage=null;
		singerimage=null;
		singerbox=null;
		number=null;
		arrow=null;
		songname=null;
		singername=null;
		musicstar=null;
		leftText=null;
		rightText=null;
		pageText=null;						
		search=null;                    
		searchframe=null;				
		keyBoard=null;                   
		searchBack=null;					
		oKkey=null;                   
		layout0=null;
		layout=null;
		showImage=null;
		handler=null;
		timer=null;
		idsongname=null;
		idsingername=null;
		idsinger=null;
		idheatimage=null;
		idheat=null;
		idimage=null;
		idnumber=null;
		idbox=null;
		idpagepic=null;
		idarrow=null;
		preText=null;
		cSingerName=null;
		typeFace=null;		
		song=null;
		singer=null;
		songList=null;
		singerList=null;
		super.onDestroy();
	}
}