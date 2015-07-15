package hichang.activity;

import hichang.Song.CMediaPlayer;
import hichang.Song.LocalBitmap;
import hichang.Song.ReadText;
import hichang.Song.Sentence;
import hichang.Song.Singer;
import hichang.Song.Song;
import hichang.Song.User;
import hichang.audio.AudRec;
import hichang.ourView.CurveAndLrc;
import hichang.ourView.CurveAndLrc.ModeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class HiSingActivity extends Activity {

	/**
	 * ������
	 */
	private SeekBar volumnSeekBar;
	/**
	 * �Ƿ���ڰ�������
	 */
	private boolean isComa;
	/**
	 * �Ƿ����ģʽ
	 */
	private boolean isKTV;
	/**
	 * ý������(��mediaVolumn_intת��)
	 */
	private int mediaVolume;
	private float mediaVolumn_float = 0.3f;
	/**
	 * ý������(��mediaVolumnת��)
	 */
	private int mediaVolumn_int;
	/**
	 * ��˷�����
	 */
	private int micVolumn;
	/**
	 * �ж�timer�Ƿ���
	 */
	private boolean isStart;
	/**
	 * ����ͼƬ
	 */
	Bitmap[] accompany;
	/**
	 * ����ͼƬ
	 */
	Bitmap[] original;
	/**
	 * ����ͼƬ
	 */
	Bitmap[] ktvMode;
	/**
	 * ����ͼƬ
	 */
	Bitmap[] professional;
	/**
	 * ����ͼƬ
	 */
	Bitmap volumnnote;
	/**
	 * ��Դ
	 */
	private Resources resources;
	/**
	 * ���ֲ�����
	 */
	private CMediaPlayer media;
	/**
	 * timer
	 */
	private Timer timer;
	/**
	 * handler
	 */
	private Handler handler;
	/**
	 * timerTask
	 */
	private TimerTask task1, task2, task3, task4;
	/**
	 * ���·�������ʾ����ImageView
	 */
	private ImageView funcRedImage, funcGreenImage, funcYellowImage,
			funcBlueImage;
	/**
	 * ԭ����ktv�Ƿ�����ʾ
	 */
	private ImageView funcFirstImage;
	/**
	 * ���ࡢרҵ�Ƿ�����ʾ
	 */
	private ImageView funcSecImage;
	/**
	 * ԭ���������ktvרҵ�в�б��
	 */
	private ImageView funcSprit;
	/**
	 * ����ͼ��
	 */
	private ImageView volumnIcon;
	/**
	 * ����ͼƬ
	 */
	private ImageView singerImage;
	/**
	 * ��������������߷�
	 */
	private TextView musicInfo;
	/**
	 * ���ݿ�Song����ʽӿ�
	 */
	Song song;
	/**
	 * ���ݿ�Singer����ʽӿ�
	 */
	Singer singer;
	/**
	 * ���ݿ�User����ʽӿ�
	 */
	User user;
	/**
	 * ��������Ϣ
	 */
	Song nowSong;
	/**
	 * �������Ժ����о��ӵ����
	 */
	ArrayList<Sentence> nowSongSentences;
	/**
	 * ��ʼ��Timer
	 */
	Timer startTimer;
	/**
	 * startTimer�Ƿ�ȡ����
	 */
	private boolean isStartTimerCancel;
	/**
	 * ����ʱ��Timer
	 */
	Timer disTimer;
	/**
	 * disTimer�Ƿ�ȡ����
	 */
	private boolean isDisTimerCancel;
	/**
	 * ������̵�Timer
	 */
	Timer songTimer, timeTimer;
	/**
	 * ģʽ�л�ʱ�ĵ���ʱ
	 */
	Timer startGreenTimer;
	/**
	 * startGreenTimer�Ƿ�ȡ����
	 */
	private boolean isStartGreenTimerCancel;
	/**
	 * �������׸�Ĳ��ŵ�ʱ��
	 */
	int nowSongTime;
	/**
	 * ���ڵĸ�ʣ���һ���ʣ��Լ���һ����
	 */
	Sentence nowSentence, nextSentence, lastSentence;
	/**
	 * ָʾ��һ���ʵ��α꣨��ArrayList�е�λ�ã�
	 */
	int sentenceFlag;
	/**
	 * ����ʱ���
	 */
	ImageView[] colorBalls = new ImageView[3];
	/**
	 * ����ʱʣ�µ�ʱ��
	 */
	double leftTimes;
	/**
	 * �������׸����ʱ��
	 */
	int nowSongLength;
	/**
	 * ʱ���
	 */
	final int TIMESTEP = 40;
	/**
	 * ��׼ʱ��
	 */
	int standTime;
	/**
	 * ʱ�����λ��
	 */
	int timeHandX;
	/**
	 * ���׸�ķ���
	 */
	int nowSongScore;
	/**
	 * ��ǰ��������߷�
	 */
	int highScore;
	/**
	 * ʱ����
	 */
	ImageView timeHand;
	/**
	 * �Ƿ�����ѡ��ı��
	 */
	boolean isNeedFinish = false;
	/**
	 * ����������Ŀ
	 */
	private int sentenceCount;
	/**
	 * �Ƿ�տ�ʼ����
	 */
	private boolean isStartNow;
	/**
	 * �Ƿ�տ�ʼ����֮ǰ����֮ǰ�Ͱ�����ɫ��
	 */
	private boolean isPressPre;

	private TextView textView;

	private CurveAndLrc curveLrc;

	private int curveW, curveH, curveX, curveY;
	private int lrcH, lrcW, lrcX, lrcY;
	private AudRec audRec;

	final static int MSG_TURN_COLORBARS = 100;
	final static int MSG_HIDE_YELLOWBALL = 101;
	final static int MSG_HIDE_REDBALL = 102;
	final static int MSG_HIDE_BLUEBALL = 103;
	final static int MSG_SHOW_SCORE = 104;
	final static int MSG_START_SONG = 200;
	final static int MSG_SHOW_TIME = 105;
	final static int MSG_START_SHOW_TIME=106;
	ImageView staffImage;
	String songTime;
	
	
	int []pids=new int[1];
	ActivityManager am;
	MemoryInfo outInfo;
	Timer timer1;
	TextView processInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sing);

		int pid=Process.myPid();
    	pids[0] = pid;
		am=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		outInfo=new MemoryInfo();
		processInfo=(TextView)findViewById(R.id.sinprocessinfo);
		timer1=new Timer();
		
		isStart = false;

		textView = (TextView)findViewById(R.id.sing_text_zcl);
		volumnSeekBar = (SeekBar) findViewById(R.id.sing_player_seekbar);
		musicInfo=(TextView)findViewById(R.id.showscore);
		singerImage=(ImageView)findViewById(R.id.sin_image);
		funcRedImage = (ImageView) findViewById(R.id.sing_function_red);
		funcGreenImage = (ImageView) findViewById(R.id.sing_function_green);
		funcBlueImage = (ImageView) findViewById(R.id.sing_function_blue);
		funcYellowImage = (ImageView) findViewById(R.id.sing_function_yellow);
		funcFirstImage = (ImageView) findViewById(R.id.sing_funcfirstimage);
		funcSecImage = (ImageView) findViewById(R.id.sing_funcsecimage);
		funcSprit = (ImageView) findViewById(R.id.sing_funcsprit);
		volumnIcon = (ImageView) findViewById(R.id.sing_icon);
		colorBalls[0] = (ImageView) findViewById(R.id.sing_count_blue);
		colorBalls[1] = (ImageView) findViewById(R.id.sing_count_red);
		colorBalls[2] = (ImageView) findViewById(R.id.sing_count_yellow);
		timeHand = (ImageView) findViewById(R.id.sing_time_hand);
		curveLrc = (CurveAndLrc) findViewById(R.id.sing_curveandlrc);
		staffImage=(ImageView)findViewById(R.id.sing_staff);
		
		resources = this.getResources();
		isPressPre = false;

		isComa = false;
		isKTV = false;
		mediaVolume = 3;
		mediaVolumn_int = 30;
		micVolumn = 30;
		accompany = new Bitmap[2];
		original = new Bitmap[2];
		ktvMode = new Bitmap[2];
		professional = new Bitmap[2];

		// ��ȡ�����洫���ĸ���ID
		Intent intent=getIntent();
		int songid=intent.getIntExtra("songId", 0);
		
		song = new Song(getBaseContext());
		singer =new Singer(getBaseContext());
		user=new User(getBaseContext());
		
		nowSong = song.findSongById(songid);
		int singerid=singer.querySingerByName(nowSong.getSinger1()).getiD();
		highScore=user.queryFirstScore(nowSong.getSongID());
		String songName=nowSong.getName();
		if(songName.charAt(0)>0&&songName.charAt(0)<128&&songName.length()>12)songName=songName.substring(0,12)+"...";
		else if((songName.charAt(0)<0||songName.charAt(0)>128)&&songName.length()>7)songName=songName.substring(0,7)+"...";
		String picPath="/sdcard/HiChang/Singer/"+singerid+"/"+singerid+"_p.png";
		singerImage.setImageBitmap(LocalBitmap.getLoacalBitmap(picPath));
		//musicInfo.setText(nowSong.getName()+"\n��߷�-"+highScore+"\n");
		
		initPic();
		
		handler = new Handler() {

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 10:
				{
					am.getMemoryInfo(outInfo);
					List<RunningAppProcessInfo>runApps=am.getRunningAppProcesses();
					Debug.MemoryInfo[]dmf=am.getProcessMemoryInfo(pids);
					processInfo.setText("��ǰ�ڴ�ռ��:"+dmf[0].dalvikPrivateDirty+"KB\n �����ڴ�:"+outInfo.availMem/1024+"KB\n");
					handler.sendEmptyMessage(500);
				}
				// �鳪��ԭ�����л����أ�ͼ������ֵ��л���
				case 1:
					funcFirstImage.setVisibility(ImageView.INVISIBLE);
					funcSecImage.setVisibility(ImageView.INVISIBLE);
					funcSprit.setVisibility(ImageView.INVISIBLE);
					break;
				// ģʽ֮����л����أ�ͼ������ֵ��л���
				case 2:
					funcFirstImage.setVisibility(ImageView.INVISIBLE);
					funcSecImage.setVisibility(ImageView.INVISIBLE);
					funcSprit.setVisibility(ImageView.INVISIBLE);
					break;
				// ����ͼ������
				case 3:
					volumnIcon.setVisibility(ImageView.INVISIBLE);
					volumnSeekBar.setVisibility(SeekBar.INVISIBLE);
					textView.setVisibility(TextView.INVISIBLE);
					break;
				// ����ʱС�����
				case MSG_TURN_COLORBARS:
					colorBalls[0].setVisibility(ImageView.VISIBLE);
					colorBalls[1].setVisibility(ImageView.VISIBLE);
					colorBalls[2].setVisibility(ImageView.VISIBLE);
					disTimer = new Timer();
					disTimer.schedule(new TimerTask() {
						int i = 0;
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (i == 0) {
								handler.sendEmptyMessage(MSG_HIDE_YELLOWBALL);
							} else if (i == 1) {
								handler.sendEmptyMessage(MSG_HIDE_REDBALL);
							} else if (i == 2) {
								handler.sendEmptyMessage(MSG_HIDE_BLUEBALL);
							} else {
								this.cancel();
								disTimer.cancel();
							}
							i++;
						}
					}, 0, 1000);
					break;
				// ����������С������
				case MSG_HIDE_YELLOWBALL:
					colorBalls[2].setVisibility(ImageView.INVISIBLE);
					break;
				// �����ڶ���С������
				case MSG_HIDE_REDBALL:
					colorBalls[1].setVisibility(ImageView.INVISIBLE);
					break;
				// ������һ��С������
				case MSG_HIDE_BLUEBALL:
					colorBalls[0].setVisibility(ImageView.INVISIBLE);
					break;
				// ���׸�����������ʾ
				case MSG_SHOW_SCORE:
					startShowScore();
					break;
				case MSG_START_SHOW_TIME:
					timeTimer=new Timer();
					timeTimer.schedule(new TimerTask() {						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							handler.sendEmptyMessage(MSG_SHOW_TIME);
						}
					}, 0 ,500);
					
					break;
				case MSG_SHOW_TIME:
					nowSongTime=media.CGetCurrentPosition();
					int time=nowSongTime/1000;
					int second=time%60;
					int minute=(time-second)/60;
					if(second<10){
						musicInfo.setText(nowSong.getName()+"\n��߷�-"+highScore+"\n"+minute+": "+"0"+second+"/"+songTime);
					} else {
						musicInfo.setText(nowSong.getName()+"\n��߷�-"+highScore+"\n"+minute+": "+second+"/"+songTime);
					}
					break;
				case MSG_START_SONG:
					media.CStart();
					songTimer=new Timer();
					songTimer.schedule(new TimerTask() {

						@Override
						public void run() {
							nowSongTime = media.CGetCurrentPosition();
							if (nowSongTime >= nowSongLength - 500
									|| nowSongTime >= lastSentence.StartTimeofThis
											+ lastSentence.LastTimeofThis
											+ 5000) {
								
								handler.sendEmptyMessage(MSG_SHOW_SCORE);
								songTimer.cancel();
							}
							if (sentenceFlag < sentenceCount) {
								if (!isStart && nowSongTime > nowSongSentences.get(0).StartTimeofThis - 3000) {
									handler.sendEmptyMessage(MSG_TURN_COLORBARS);
									isStart=true;
								}
								if (nowSongTime >= nowSongSentences
										.get(sentenceFlag).StartTimeofThis
										+ nowSongSentences.get(sentenceFlag).LastTimeofThis) {
									sentenceFlag++;
								}
							} 
							curveLrc.drawCurveAndLrc(nowSongTime-440,
									audRec.getNote(nowSongTime-440));
						}
					}, 40, 40);
					break;
				}
			}
		}; 
		
		timer1.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(10);
			}
		}, 500);
	}

	public void setKTV(boolean isKtv) {
		isKTV = isKtv;
		if (isKTV) {
			lrcH = (int) resources.getDimension(R.dimen.sing_ktv_lrc_height);
			lrcW = (int) resources.getDimension(R.dimen.sing_ktv_lrc_width);
			lrcX = (int) resources.getDimension(R.dimen.sing_ktv_lrc_x);
			lrcY = (int) resources.getDimension(R.dimen.sing_ktv_lrc_y);
			curveW = (int) resources.getDimension(R.dimen.sing_ktv_curve_width);
			curveH = (int) resources
					.getDimension(R.dimen.sing_ktv_curve_height);
			curveX = (int) resources.getDimension(R.dimen.sing_ktv_curve_x);
			curveY = (int) resources.getDimension(R.dimen.sing_ktv_curve_y);
			
			staffImage.setVisibility(View.VISIBLE);
			
		} else {
			lrcH = (int) resources
					.getDimension(R.dimen.sing_vocational_lrc_height);
			lrcW = (int) resources
					.getDimension(R.dimen.sing_vocational_lrc_width);
			lrcX = (int) resources.getDimension(R.dimen.sing_vocational_lrc_x);
			lrcY = (int) resources.getDimension(R.dimen.sing_vocational_lrc_y);
			curveW = (int) resources
					.getDimension(R.dimen.sing_vocational_curve_width);
			curveH = (int) resources
					.getDimension(R.dimen.sing_vocational_curve_height);
			curveX = (int) resources
					.getDimension(R.dimen.sing_vocational_curve_x);
			curveY = (int) resources
					.getDimension(R.dimen.sing_vocational_curve_y);
			staffImage.setVisibility(View.INVISIBLE);
		}
		curveLrc.setCurveXYWH(curveX, curveY, curveW, curveH);
		curveLrc.setLrcXYWH(lrcX, lrcY, lrcW, lrcH);
		curveLrc.setKTV(isKTV);
	}

	// ��ȡ��������Ϣ
	public void initSong() {
		ReadText nowText = new ReadText(nowSong.getSongLyricUrl());
		nowSongSentences = nowText.ReadData();

		lastSentence = nowSongSentences.get(nowSongSentences.size() - 1);
		sentenceCount = nowSongSentences.size();
		nowSongLength = media.CGetDuration();
		
		int sec=(nowSongLength/1000)%60;
		int min=(nowSongLength/1000-sec)/60;
		if(sec<10){
			songTime=min+":"+"0"+sec;
		} else{
			songTime=min+":"+sec;
		}
		handler.sendEmptyMessage(MSG_START_SHOW_TIME);
		sentenceFlag = 0;
		
		curveLrc.init(nowSongSentences, nowText.max, nowText.min,
				ModeType.MODE_SING);
		setKTV(isKTV);

		audRec = new AudRec(nowSongSentences, handler, media);
		audRec.init();
	}

	// ��ʼ��һ��ý�岥����
	private void initMediaPlayer() {
		// TODO Auto-generated method stub
		media = new CMediaPlayer();
		media.CSetDataSource(nowSong.getMusicPath(), nowSong.getAccomanimentPath());
		media.CPrepare();
	}

	// ��ʼ�������õ���ͼƬ
	public void initPic() {
		volumnnote = BitmapFactory.decodeResource(resources,
				R.drawable.musicalnote);
		// ����ģ���ͼƬ
		for (int i = 0; i < 2; i++) {
			ktvMode[i] = BitmapFactory.decodeResource(resources,
					R.drawable.ktv_selected + i);
			professional[i] = BitmapFactory.decodeResource(resources,
					R.drawable.professional_selected + i);
			original[i] = BitmapFactory.decodeResource(resources,
					R.drawable.original_selected + i);
			accompany[i] = BitmapFactory.decodeResource(resources,
					R.drawable.accompany_selected + i);
		}
	}

	// ��ʾ���׸�ķ���
	public void startShowScore() {
		nowSongScore = audRec.getTotalMark();
		//nowSongScore = 98;
		Intent intent = new Intent(HiSingActivity.this, HiScoreActivity.class);
		intent.putExtra("score", nowSongScore);
		intent.putExtra("songId", nowSong.getSongID());
		startActivityForResult(intent, 0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		// ����Ϊ���
		if (isStart == true) {
			task1.cancel();
			task1 = new TimerTask() {
				public void run() {
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				}
			};
			task2.cancel();
			task2 = new TimerTask() {
				public void run() {
					Message message = new Message();
					message.what = 2;
					handler.sendMessage(message);
				}
			};
			task3.cancel();
			task3 = new TimerTask() {
				public void run() {
					Message message = new Message();
					message.what = 3;
					handler.sendMessage(message);
				}
			};
		}
		isStart = true;
		
		// ����Ϊ���ؼ�
		if (keyCode == 4) {
			Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent();
			intent.putExtra("type", 0);
			intent.setClass(HiSingActivity.this, RemoteMusicActivity.class);
			startActivity(intent);
			this.finish();
		}
		// ����Ϊ��ɫ�������ԭ�����л�
		if (keyCode == 183) {
			textView.setVisibility(TextView.INVISIBLE);
			volumnIcon.setVisibility(ImageView.INVISIBLE);
			volumnSeekBar.setVisibility(SeekBar.INVISIBLE);
			funcFirstImage.setVisibility(ImageView.VISIBLE);
			funcSecImage.setVisibility(ImageView.VISIBLE);
			funcSprit.setVisibility(ImageView.VISIBLE);
			if (isComa == false) {
				Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
				isComa = true;
				funcFirstImage.setImageBitmap(accompany[0]);
				funcSecImage.setImageBitmap(original[1]);
				media.CSetAccompany();
			} else {
				Toast.makeText(this, "ԭ��", Toast.LENGTH_SHORT).show();
				isComa = false;
				funcFirstImage.setImageBitmap(accompany[1]);
				funcSecImage.setImageBitmap(original[0]);
				media.CSetOriginal();
			}
			timer.schedule(task1, 3000);

		}
		// ����Ϊ��ɫ��ģʽ���л�
		if (keyCode == 184) {
			textView.setVisibility(TextView.INVISIBLE);
			volumnIcon.setVisibility(ImageView.INVISIBLE);
			volumnSeekBar.setVisibility(SeekBar.INVISIBLE);
			funcFirstImage.setVisibility(ImageView.VISIBLE);
			funcSecImage.setVisibility(ImageView.VISIBLE);
			funcSprit.setVisibility(ImageView.VISIBLE);
			if (isKTV == true) {
				colorBalls[0].setX(400);
				colorBalls[0].setY(770);
				colorBalls[1].setX(460);
				colorBalls[1].setY(770);
				colorBalls[2].setX(520);
				colorBalls[2].setY(770);
				Toast.makeText(this, "רҵģʽ", Toast.LENGTH_SHORT).show();
				funcFirstImage.setImageBitmap(ktvMode[1]);
				funcSecImage.setImageBitmap(professional[0]);
			} else {
				colorBalls[0].setX(120);
				colorBalls[0].setY(420);
				colorBalls[1].setX(170);
				colorBalls[1].setY(420);
				colorBalls[2].setX(220);
				colorBalls[2].setY(420);
				Toast.makeText(this, "ktvģʽ", Toast.LENGTH_SHORT).show();
				funcFirstImage.setImageBitmap(ktvMode[0]);
				funcSecImage.setImageBitmap(professional[1]);
			}
			setKTV(!isKTV);
			timer.schedule(task2, 3000);
		}
		// ����Ϊ��ɫ����С����
		if (keyCode == 185) {
			funcFirstImage.setVisibility(ImageView.INVISIBLE);
			funcSecImage.setVisibility(ImageView.INVISIBLE);
			funcSprit.setVisibility(ImageView.INVISIBLE);
			volumnIcon.setImageBitmap(volumnnote);
			Toast.makeText(this, "��С������������", Toast.LENGTH_SHORT).show();
			mediaVolume = mediaVolume - 1;
			mediaVolumn_float = mediaVolumn_float - 0.1f;
			if (mediaVolume < 0) {
				mediaVolume = 0;
				mediaVolumn_float = 0;
			}
			media.CSetVolume(mediaVolumn_float);
			mediaVolumn_int = (int) (mediaVolume * 10);
			volumnSeekBar.setProgress(mediaVolumn_int);
			textView.setText("" + mediaVolume);
			volumnIcon.setVisibility(ImageView.VISIBLE);
			volumnSeekBar.setVisibility(SeekBar.VISIBLE);
			textView.setVisibility(TextView.VISIBLE);
			timer.schedule(task3, 5000);
		}
		// ����Ϊ��ɫ����������
		if (keyCode == 186) {
			funcFirstImage.setVisibility(ImageView.INVISIBLE);
			funcSecImage.setVisibility(ImageView.INVISIBLE);
			funcSprit.setVisibility(ImageView.INVISIBLE);
			volumnIcon.setImageBitmap(volumnnote);
			mediaVolume = mediaVolume + 1;
			mediaVolumn_float = mediaVolumn_float +0.1f;
			if (mediaVolume > 10) {
				mediaVolume = 10;
				mediaVolumn_float = 1;
			}
			media.CSetVolume(mediaVolumn_float);
			mediaVolumn_int = (int) (mediaVolume * 10);
			Toast.makeText(this, "����ý������", Toast.LENGTH_SHORT).show();
			volumnSeekBar.setProgress(mediaVolumn_int);
			textView.setText("" + mediaVolume);
			volumnIcon.setVisibility(ImageView.VISIBLE);
			volumnSeekBar.setVisibility(SeekBar.VISIBLE);
			textView.setVisibility(TextView.VISIBLE);
			timer.schedule(task3, 5000);
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (resultCode) {
		case 8:
			break;
		case 4:
			Intent intent=new Intent();
			intent.putExtra("type", 0);
			intent.setClass(HiSingActivity.this, RemoteMusicActivity.class);
			startActivity(intent);
			this.finish();
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		timeTimer.cancel();
		songTimer.cancel();
		disTimer.cancel();
		timer.cancel();
		timer1.cancel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub	
		audRec.free();
		audRec=null;
		media.CStop();
		media.CRelease();
		song=null;
		singer=null;
		user=null;
		nowSong=null;
		
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sentenceFlag=0;
		super.onStop();
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		song=null;
		singer=null;
		user=null;
		nowSong=null;
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		disTimer=new Timer();
		songTimer=new Timer();
		timer= new Timer();
		task1 = new TimerTask() {
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};
		task2 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = 2;
				handler.sendMessage(message);
			}
		};
		task3 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = 3;
				handler.sendMessage(message);
				isStart = false;
			}
		};
		task4 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = 4;
				handler.sendMessage(message);
			}
		};
		
		initMediaPlayer();
		initSong();
		audRec.start();
	}

}
