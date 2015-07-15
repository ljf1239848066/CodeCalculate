package hichang.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import hichang.Song.*;
import hichang.audio.AudRec;
import hichang.ourView.CurveAndLrc;
import hichang.ourView.CurveAndLrc.ModeType;
import hichang.ourView.LrcTextView;
import hichang.ourView.VoiceView;
import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.provider.MediaStore.Audio.Media;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PracticeActivity extends Activity {

	// ���ݿ�Song����ʽӿ�
	private Song song;
	// ���ݿ�Singer����ʽӿ�
	private Singer singer;
	// ���ݿ�User����ʽӿ�
	private User user;
	// ��ǰ���Ÿ���
	private Song nowSong;
	// ������ĸ�����Ϣ
	private ReadText nowSongText;
	// ������ĸ�ʾ�����
	private ArrayList<Sentence> nowSongSentence;
	// ��������ʱ�������룩
	private int nowSongLength;
	// ��ǰ���ŵ��ĺ�����
	private int nowSongTime;
	// ��ǰ���ŵ��ľ��ӵ��α�
	private int sentenceFlag = 0;
	// ��ǰ���ŵ��ľ��Ӻ���һ��,���һ��
	private Sentence nowSentence, nextSentence, lastSentence;
	// ��ǰ��ľ�����Ŀ
	private int sentenceSize;
	// ʹ��timer�ؼ����������������
	private Timer songtimer;
	// ��ʲ���
	private LinearLayout mLayout;
	// ��ʹ�������
	private ScrollView sView;
	// �����ʾ����
	private ListView listView;
	// listView�еļ����ؼ�
	private TextView tView1;
	private TextView tView2;
	// �������е�һ������
	private View view;
	// �Զ����List������
	private MyAdapter adapter;
	// ��ʴ�ŵĵط�
	public static int select_item = -1;// ���ڲ��ŵ�ĳ����
	// ������λ��
	public static int mposition = -367;
	// �жϵ���Ĳ��������ǲ������
	public static int a = 0;
	// ��� ������
	public VoiceView voiceView;
	// ���������
	private LrcTextView lrcView;
	// �ܶ���ʱ��
	private ImageView timeHand;
	// �Ƿ��ڵ�����˷�����
	// �Ƿ�����������
	private boolean isMic = false;
	// �Ƿ��ڵ���ý������
	private boolean isMedia = false;
	// �Ƿ���ڰ�������
	private boolean isComa = false;
	// �Ƿ����ģʽ
	private boolean isKTV = true;
	// ��Ϣ����
	private Handler handler;
	// ý������
	private int mediaVolume = 3;
	private int mediaVolumn_int;
	private float mediaVolumn_float = 0.3f;
	// ��˷�����
	private int micVolumn = 30;
	// ��ǰ��������߷�
	int highScore;
	// ����ͼƬ
	Bitmap[] smallWhiteNums = new Bitmap[10];
	Bitmap[] whiteNums = new Bitmap[10];
	Bitmap[] redNums = new Bitmap[10];

	// ����ͼƬ
	Bitmap[] evaluates = new Bitmap[4];
	// ����ģ���ͼƬ
	Bitmap[] accompany = new Bitmap[2];
	Bitmap[] original = new Bitmap[2];
	Bitmap[] ktvMode = new Bitmap[2];
	Bitmap[] professional = new Bitmap[2];
	Bitmap[] volumns = new Bitmap[2];
	// ���ǵ�ͼƬ
	Bitmap darkBigStar, lightBigStar, darkSmallStar, lightSmallStar;
	// ��Դ
	private Resources resources;
	// ����
	private Animation numTurnFirstAnim;
	private Animation numTurnSecAnim;
	private Animation starFirstAnim;
	private Animation starSecAnim;
	private Animation evaluateFirstAnim;
	private Animation evaluateSecAnim;
	private Animation jumpFirstAnim;
	private Animation jumpSecAnim;
	private TranslateAnimation timeAnim;
	// ������ImageView
	private ImageView leftImage, rightImage;
	private ImageView singerImage;
	private TextView musicInfo, volumnTextView;
	private int numImageHeight, numImageWidth;
	private Bitmap leftBm, rightBm, changeLeftBm, changeRightBm;
	private Bitmap evaluateBm, changeEvaBm;
	private int whichevaluate;
	// ���ǵ�ImageView
	private ImageView starImage;
	// ÿ�������ʮλ�͸�λ����
	private int leftScoreNum, rightScoreNum;
	// ���·�������ʾ����ImageView
	private ImageView funcRedImage, funcGreenImage, funcYellowImage,
			funcBlueImage, funcFirstImage, funcSecImage, funcSprit;
	private AbsoluteLayout funcLayout;
	// ��������ͼ��
	private SeekBar volumnSeekBar;
	private ImageView volumnImage;
	// ����ImageView
	private ImageView evaluateImage;
	// ����ʱͼ��
	private ImageView countBlueImage, countRedImage, countYellowImage;
	// �Ƿ��Ǹտ�ʼ����
	private boolean isStart = false;
	// �Ƿ���ѡ��ʼ����
	private boolean isSelectStart = true;
	// ����ʱ��ʱ��
	private double leftTimes;
	// ����ʱ,ʱ��,������timer
	private Timer disTimer, handTimer, scoreTimer, senTimer, timeTimer;
	// timertask
	private Timer buttonTimer;
	TimerTask iconTask1, iconTask2, iconTask3;
	TimerTask songTimerTask;
	// ������
	// private MediaPlayer mediaPlayer2,mediaPlayer3;
	private CMediaPlayer nowMediaPlayer = new CMediaPlayer();
	// ��Ƶ����
	private AudRec audRec;
	// ʱ��Ĳ���
	final int TIMESTEP = 40;
	// ��ǰ����ı�׼ʱ��
	int standTime;
	// ʱ�������
	int timeHandX;

	SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
	int soundId;

	TextView text1;
	int[] pids = new int[1];
	ActivityManager am;
	MemoryInfo outInfo;

	CurveAndLrc curveLrc;
	private int curveW, curveH, curveX, curveY;
	private int lrcH, lrcW, lrcX, lrcY;

	private TimerTask timerTask, songTask;

	final static int MSG_SHOW_TIME = 155;
	final static int MSG_TURN_COLORBARS = 150;
	final static int MSG_HIDE_YELLOWBALL = 151;
	final static int MSG_HIDE_REDBALL = 152;
	final static int MSG_HIDE_BLUEBALL = 153;
	final static int MSG_SHOW_SCORE = 154;
	final static int MSG_START_SONG = 200;
	final static int MSG_RESTART_SONG = 201;
	final static int MSG_START_SHOW_TIME = 156;

	String songTime;
	ImageView staffImage;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.practice);

		volumnTextView = (TextView) findViewById(R.id.volumn_textview);
		singerImage = (ImageView) findViewById(R.id.pra_sin_image);
		musicInfo = (TextView) findViewById(R.id.pra_showscore);
		leftImage = (ImageView) findViewById(R.id.num_left);
		rightImage = (ImageView) findViewById(R.id.num_right);
		funcRedImage = (ImageView) findViewById(R.id.function_red);
		funcGreenImage = (ImageView) findViewById(R.id.function_green);
		funcBlueImage = (ImageView) findViewById(R.id.function_blue);
		funcYellowImage = (ImageView) findViewById(R.id.function_yellow);
		funcFirstImage = (ImageView) findViewById(R.id.funcfirstimage);
		funcSecImage = (ImageView) findViewById(R.id.funcsecimage);
		funcSprit = (ImageView) findViewById(R.id.funcsprit);
		funcLayout = (AbsoluteLayout) findViewById(R.id.funclayout);
		evaluateImage = (ImageView) findViewById(R.id.evaluteimage);
		volumnSeekBar = (SeekBar) findViewById(R.id.player_seekbar);
		volumnImage = (ImageView) findViewById(R.id.volumntype);
		countBlueImage = (ImageView) findViewById(R.id.countblue);
		countRedImage = (ImageView) findViewById(R.id.countred);
		countYellowImage = (ImageView) findViewById(R.id.countyellow);
		sView = (ScrollView) this.findViewById(R.id.ScrollView);
		listView = (ListView) findViewById(R.id.listview_list);
		mLayout = (LinearLayout) this.findViewById(R.id.LinearLayout);
		timeHand = (ImageView) findViewById(R.id.timeHand);
		starImage = (ImageView) findViewById(R.id.starimage);
		listView.setFocusable(false);
		sView.setFocusable(false);
		volumnSeekBar.setFocusable(false);
		curveLrc = (CurveAndLrc) findViewById(R.id.practice_curveandlrc);
		staffImage = (ImageView) findViewById(R.id.practice_staff);

		resources = this.getResources();
		// �����ļ�·��
		// ��ȡ�����洫���ĸ���ID
		Intent intent = getIntent();
		int songid = intent.getIntExtra("songId", 0);

		song = new Song(getBaseContext());
		singer = new Singer(getBaseContext());
		user = new User(getBaseContext());

		nowSong = song.findSongById(songid);
		int singerid = singer.querySingerByName(nowSong.getSinger1()).getiD();
		highScore = user.queryFirstScore(nowSong.getSongID());
		String songName = nowSong.getName();
		if (songName.charAt(0) > 0 && songName.charAt(0) < 128
				&& songName.length() > 12)
			songName = songName.substring(0, 12) + "...";
		else if ((songName.charAt(0) < 0 || songName.charAt(0) > 128)
				&& songName.length() > 7)
			songName = songName.substring(0, 7) + "...";
		String picPath = "/sdcard/HiChang/Singer/" + singerid + "/" + singerid
				+ "_p.png";
		singerImage.setImageBitmap(LocalBitmap.getLoacalBitmap(picPath));
		// musicInfo.setText(nowSong.getName()+"\n��߷�-"+highScore+"\n");

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 4) {
					funcFirstImage.setVisibility(ImageView.INVISIBLE);
					funcSecImage.setVisibility(ImageView.INVISIBLE);
					funcSprit.setVisibility(ImageView.INVISIBLE);
				}
				if (msg.what == 5) {
					funcFirstImage.setVisibility(ImageView.INVISIBLE);
					funcSecImage.setVisibility(ImageView.INVISIBLE);
					funcSprit.setVisibility(ImageView.INVISIBLE);
				}
				if (msg.what == 6) {
					volumnImage.setVisibility(ImageView.INVISIBLE);
					volumnSeekBar.setVisibility(SeekBar.INVISIBLE);
					volumnTextView.setVisibility(TextView.INVISIBLE);
				}
				if (msg.what == 123) {
					ScrollToNext(adapter);
				} else if (msg.what == 2) {
					ScrollToPast(adapter);
				} else if (msg.what == 100) {

					leftImage.setImageBitmap(changeLeftBm);
					rightImage.setImageBitmap(changeRightBm);
					evaluateImage.setImageBitmap(changeEvaBm);
				} else if (msg.what == 101) {
					leftImage.setImageBitmap(leftBm);
					rightImage.setImageBitmap(rightBm);
					evaluateImage.setImageBitmap(evaluateBm);
				} else if (msg.what == 107) {
					starImage.setImageBitmap(lightBigStar);
					starImage.startAnimation(starFirstAnim);
				} else if (msg.what == 108) {
					starImage.startAnimation(starSecAnim);
				} else if (msg.what == 109) {
					starImage.setImageBitmap(darkBigStar);
				} else if (msg.what == 115) {

					am.getMemoryInfo(outInfo);
					List<RunningAppProcessInfo> runApps = am
							.getRunningAppProcesses();
					Debug.MemoryInfo[] dmf = am.getProcessMemoryInfo(pids);
					text1.setText("��ǰactivityռ���ڴ�" + dmf[0].dalvikPrivateDirty
							+ "KB" + "�����ڴ�" + outInfo.availMem / 1024 + "KB "
							+ audRec.getCurrentReadindex() + " "
							+ audRec.getCurrentRecordIndex() + " "
							+ audRec.getCurrentRealNote() + " "
							+ audRec.getProcessTime() + " "
							+ audRec.getCurrentMark() + " "
							+ curveLrc.firstSenStartX + " "
							+ curveLrc.pointFlag + " "
							+ curveLrc.pointsTime[0].length);
				} else if (msg.what == MSG_START_SONG) {
					nowMediaPlayer.CStart();
					songtimer = new Timer();
					songtimer.schedule(songTask, 40, 40);
				} else if (msg.what == MSG_TURN_COLORBARS) {
					countBlueImage.setVisibility(ImageView.VISIBLE);
					countRedImage.setVisibility(ImageView.VISIBLE);
					countYellowImage.setVisibility(ImageView.VISIBLE);
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
								disTimer.cancel();
							}
							i++;
						}
					}, 0, 1000);
				} else if (msg.what == MSG_HIDE_YELLOWBALL) {
					countYellowImage.setVisibility(View.INVISIBLE);
				} else if (msg.what == MSG_HIDE_REDBALL) {
					countRedImage.setVisibility(View.INVISIBLE);
				} else if (msg.what == MSG_HIDE_BLUEBALL) {
					countBlueImage.setVisibility(View.INVISIBLE);
				} else if (msg.what == MSG_RESTART_SONG) {
					countBlueImage.setVisibility(View.INVISIBLE);
					nowMediaPlayer.CReStart();
					songtimer = new Timer();
					audRec.setRecording(true);
					songtimer.schedule(songTask, 40, 40);
				} else if (msg.what == MSG_SHOW_TIME) {
					nowSongTime = nowMediaPlayer.CGetCurrentPosition();
					int time = nowSongTime / 1000;
					int second = time % 60;
					int minute = (time - second) / 60;
					if (second < 10) {
						musicInfo.setText(nowSong.getName() + "\n��߷�-"
								+ highScore + "\n" + minute + ":" + "0"
								+ second + "/" + songTime);
					} else {
						musicInfo.setText(nowSong.getName() + "\n��߷�-"
								+ highScore + "\n" + minute + ":" + second
								+ "/" + songTime);
					}
				} else if (msg.what == MSG_START_SHOW_TIME) {
					timeTimer = new Timer();
					timeTimer.schedule(new TimerTask() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							handler.sendEmptyMessage(MSG_SHOW_TIME);
						}
					}, 0, 500);
				}
			}
		};

		// ���ֳ�ʼ��
		InitSong();
		InitPic();
		InitAnim();
		initTask();

		songtimer = new Timer();
		disTimer = new Timer();
		scoreTimer = new Timer();
		senTimer = new Timer();
		buttonTimer = new Timer();
		timeTimer = new Timer();
		// ����һ�����Բ���
		listView.scrollTo(0, mposition);
		// ����һ��ScrollView����
		adapter = new MyAdapter(nowSongSentence, getBaseContext());// �������ĳ�ʼ��������ǰһ���Ǹ�ʵ�List����һ����������
		listView.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listView);

		// int myPid = Process.myPid();
		// pids[0] = myPid;
		// text1 = (TextView) findViewById(R.id.text);
		// Timer timer = new Timer();
		// am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		// text1.setTextSize(15);
		// outInfo = new MemoryInfo();
		//
		// timer.schedule(new TimerTask() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// handler.sendEmptyMessage(115);
		// }
		// }, 0, 500);

		audRec.start();
	}

	public void initTask() {
		timerTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(MSG_RESTART_SONG);
			}
		};

		songTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				nowSongTime = nowMediaPlayer.CGetCurrentPosition();
				if (nowSongTime >= nowSongSentence.get(sentenceSize - 1).StartTimeofThis
						+ nowSongSentence.get(sentenceSize - 1).LastTimeofThis
						+ 440) {
					curveLrc.clearTotal();
					curveLrc.clearTotal();
					songtimer.cancel();
				}
				if (sentenceFlag < sentenceSize) {
					if (!isStart
							&& nowSongTime >= nowSongSentence.get(0).StartTimeofThis - 3000) {
						handler.sendEmptyMessage(MSG_TURN_COLORBARS);
						isStart = true;
					}
					if (nowSongTime >= nowSongSentence.get(sentenceFlag).StartTimeofThis
							+ nowSongSentence.get(sentenceFlag).LastTimeofThis) {
						nowSongSentence.get(sentenceFlag).Score = audRec
								.getMark(sentenceFlag);
						handler.sendEmptyMessage(123);
						StartNumTurnAnim(audRec.getMark(sentenceFlag));
						sentenceFlag++;
					}
				}
				curveLrc.drawCurveAndLrc(nowSongTime - 440,
						audRec.getNote(nowSongTime - 440));
			}
		};
		iconTask1 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(4);
			}
		};
		iconTask2 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(5);
			}
		};
		iconTask3 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(6);
			}
		};
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// ����Ϊ���
		if (keyCode == 21) {
			PreThreeSen();
		}
		// ����Ϊ�Ҽ�
		else if (keyCode == 22) {
			NextThreeSen();
		}
		// ����Ϊ�ϼ�
		else if (keyCode == 19) {
			PreSen();
		}
		// ����Ϊ�¼�
		else if (keyCode == 20) {
			NextSen();
		}
		// ����Ϊ���ؼ�
		else if (keyCode == 4) {
			Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.putExtra("type", 1);
			intent.setClass(PracticeActivity.this, RemoteMusicActivity.class);
			startActivity(intent);
			this.finish();
		}
		// ����Ϊ��ɫ��
		else if (keyCode == 183) {
			RedPress();
		}
		// ����Ϊ��ɫ��
		else if (keyCode == 184) {
			GreenPress();
		}
		// ����Ϊ��ɫ��
		else if (keyCode == 185) {
			YellowPress();
		}
		// ����Ϊ��ɫ��
		else if (keyCode == 186) {
			BluePress();
		}
		return true;
	}

	// ��ʼ��������Ϣ
	public void InitSong() {
		nowSongText = new ReadText(nowSong.getSongLyricUrl());
		// ��ȡ��sdcard�е�.txt
		nowSongSentence = nowSongText.ReadData();

		// //��ȡ��raw�е�.txt
		// InputStream in=resources.openRawResource(R.raw.a681);
		// nowSongSentence=nowSongText.ReadData(in);

		nowMediaPlayer.CSetDataSource(nowSong.getMusicPath(),
				nowSong.getAccomanimentPath());
		nowMediaPlayer.CPrepare();
		nowSongLength = nowMediaPlayer.CGetDuration();

		curveLrc.init(nowSongSentence, nowSongText.max, nowSongText.min,
				ModeType.MODE_PRACTICE);
		setKTV();

		// ��ʼ����ľ��ӵ�һЩ��Ϣ
		sentenceSize = nowSongSentence.size();
		nowSentence = nowSongSentence.get(0);
		nextSentence = nowSongSentence.get(1);
		sentenceFlag = 0;
		lastSentence = nowSongSentence.get(sentenceSize - 1);

		// ���Ի���Ƶ����
		audRec = new AudRec(nowSongSentence, handler, nowMediaPlayer);
		audRec.init();

		soundId = soundPool.load(this, R.raw.a, 1);

		nowSongLength = nowMediaPlayer.CGetDuration();
		int sec = (nowSongLength / 1000) % 60;
		int min = (nowSongLength / 1000 - sec) / 60;
		if (sec < 10) {
			songTime = min + ":" + "0" + sec;
		} else {
			songTime = min + ":" + sec;
		}
		handler.sendEmptyMessage(MSG_START_SHOW_TIME);
		handler.sendEmptyMessage(123);
	}

	public void setKTV() {
		isKTV = !isKTV;
		if (isKTV) {
			lrcH = (int) resources
					.getDimension(R.dimen.practice_ktv_lrc_height);
			lrcW = (int) resources.getDimension(R.dimen.practice_ktv_lrc_width);
			lrcX = (int) resources.getDimension(R.dimen.practice_ktv_lrc_x);
			lrcY = (int) resources.getDimension(R.dimen.practice_ktv_lrc_y);
			curveW = (int) resources
					.getDimension(R.dimen.practice_ktv_curve_width);
			curveH = (int) resources
					.getDimension(R.dimen.practice_ktv_curve_height);
			curveX = (int) resources.getDimension(R.dimen.practice_ktv_curve_x);
			curveY = (int) resources.getDimension(R.dimen.practice_ktv_curve_y);
			staffImage.setVisibility(View.VISIBLE);

		} else {
			lrcH = (int) resources
					.getDimension(R.dimen.practice_vocational_lrc_height);
			lrcW = (int) resources
					.getDimension(R.dimen.practice_vocational_lrc_width);
			lrcX = (int) resources
					.getDimension(R.dimen.practice_vocational_lrc_x);
			lrcY = (int) resources
					.getDimension(R.dimen.practice_vocational_lrc_y);
			curveW = (int) resources
					.getDimension(R.dimen.practice_vocational_curve_width);
			curveH = (int) resources
					.getDimension(R.dimen.practice_vocational_curve_height);
			curveX = (int) resources
					.getDimension(R.dimen.practice_vocational_curve_x);
			curveY = (int) resources
					.getDimension(R.dimen.practice_vocational_curve_y);
			staffImage.setVisibility(View.INVISIBLE);
		}
		curveLrc.setCurveXYWH(curveX, curveY, curveW, curveH);
		curveLrc.setLrcXYWH(lrcX, lrcY, lrcW, lrcH);
		curveLrc.setKTV(isKTV);
	}

	// ����ͼƬ��Դ
	public void InitPic() {
		// ������ԴͼƬ
		for (int i = 0; i < 10; i++) {
			whiteNums[i] = BitmapFactory.decodeResource(resources,
					R.drawable.num0 + i);
			redNums[i] = BitmapFactory.decodeResource(resources,
					R.drawable.red0 + i);
			smallWhiteNums[i] = BitmapFactory.decodeResource(resources,
					R.drawable.small0 + i);
		}
		// ������ԴͼƬ
		for (int i = 0; i < 4; i++) {
			evaluates[i] = BitmapFactory.decodeResource(resources,
					R.drawable.evaluate0 + i);
		}

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
			volumns[i] = BitmapFactory.decodeResource(resources,
					R.drawable.volumn_media + i);
		}

		darkBigStar = BitmapFactory.decodeResource(resources,
				R.drawable.dark_bigstar);
		lightBigStar = BitmapFactory.decodeResource(resources,
				R.drawable.bigstar);
		darkSmallStar = BitmapFactory.decodeResource(resources,
				R.drawable.dark_smallstar);
		lightSmallStar = BitmapFactory.decodeResource(resources,
				R.drawable.smallstar);
		numImageHeight = whiteNums[0].getHeight();
		numImageWidth = whiteNums[0].getWidth();
		leftBm = ((BitmapDrawable) leftImage.getDrawable()).getBitmap();
		rightBm = ((BitmapDrawable) rightImage.getDrawable()).getBitmap();
		evaluateBm = ((BitmapDrawable) evaluateImage.getDrawable()).getBitmap();

	}

	// ��ʼ������
	public void InitAnim() {
		// ���ַ�ת
		numTurnFirstAnim = AnimationUtils.loadAnimation(this,
				R.anim.numturnfirst);
		numTurnSecAnim = AnimationUtils.loadAnimation(this, R.anim.numturnsec);
		starFirstAnim = AnimationUtils.loadAnimation(this, R.anim.starfirst);
		starSecAnim = AnimationUtils.loadAnimation(this, R.anim.starsec);
		evaluateFirstAnim = AnimationUtils.loadAnimation(this,
				R.anim.evaluatefirst);
		evaluateSecAnim = AnimationUtils
				.loadAnimation(this, R.anim.evaluatesec);

		evaluateFirstAnim.setFillAfter(true);
		evaluateSecAnim.setFillAfter(true);
	}

	// û�仰��ʼʱ����һ��ķ�����ʾ������sentenceScoreΪ��һ��ķ���
	public void StartNumTurnAnim(int sentenceScore) {
		StartStarAnim();
		rightScoreNum = sentenceScore % 10;
		leftScoreNum = (sentenceScore - rightScoreNum) / 10;

		if (sentenceScore >= 90) {
			whichevaluate = 0;
		} else if (sentenceScore >= 80) {
			whichevaluate = 1;
		} else if (sentenceScore >= 70) {
			whichevaluate = 2;
		} else {
			whichevaluate = 3;
		}
		scoreTimer = new Timer();
		scoreTimer.schedule(new TimerTask() {
			int height = numImageHeight / 10;
			int eHeight = evaluateImage.getHeight() / 10;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				changeLeftBm = createBitmap(leftBm, whiteNums[leftScoreNum],
						numImageWidth, numImageHeight, height);
				changeRightBm = createBitmap(rightBm, whiteNums[rightScoreNum],
						numImageWidth, numImageHeight, height);
				changeEvaBm = createBitmap(evaluateBm,
						evaluates[whichevaluate], evaluateImage.getWidth(),
						evaluateImage.getHeight(), eHeight);
				handler.sendEmptyMessage(100);
				height += height;
				eHeight += eHeight;
				if (height >= numImageHeight) {
					leftBm = whiteNums[leftScoreNum];
					rightBm = whiteNums[rightScoreNum];
					evaluateBm = evaluates[whichevaluate];
					handler.sendEmptyMessage(101);
					this.cancel();
					scoreTimer.cancel();
				}
			}
		}, 0, 150);
	}

	/**
	 * ͼƬ�ϳ�
	 * 
	 * @param bitmap
	 * @return
	 */
	private Bitmap createBitmap(Bitmap src1, Bitmap src2, int width,
			int height, int divHeight) {

		Bitmap newSrc1 = Bitmap.createBitmap(src1, 0, divHeight, width, height
				- divHeight);
		Bitmap newSrc2 = Bitmap.createBitmap(src2, 0, 0, width, divHeight);

		Bitmap newb = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas cv = new Canvas(newb);

		cv.drawBitmap(newSrc1, 0, 0, null);

		cv.drawBitmap(newSrc2, 0, height - divHeight, null);

		cv.save(Canvas.ALL_SAVE_FLAG);// ����
		// store
		cv.restore();// �洢
		return newb;
	}

	// ���ǵĶ���
	public void StartStarAnim() {
		handler.sendEmptyMessage(107);
		Timer starTimer = new Timer();
		starTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(108);
			}
		}, starFirstAnim.getDuration());

		starTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(109);
			}
		}, starFirstAnim.getDuration() + starSecAnim.getDuration());
	}

	// ���۵Ķ���
	public void StartEvaluateAnim(int sentenceScore) {
		if (sentenceScore >= 90) {
			handler.sendEmptyMessage(102);
		} else if (sentenceScore >= 80) {
			handler.sendEmptyMessage(103);
		} else if (sentenceScore >= 70) {
			handler.sendEmptyMessage(104);
		} else {
			handler.sendEmptyMessage(105);
		}
		Timer evaluateTimer = new Timer();
		evaluateTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(106);
			}
		}, 1500);
	}

	// �����;��幦����ʾ�黬���Ķ���
	public void StartJumpAnim() {

	}

	public void NextSen() {
		senTimer.cancel();
		songtimer.cancel();
		initTask();
		if (sentenceFlag == sentenceSize) {
			nowMediaPlayer.CPause();
			curveLrc.clearTotal();
			return;
		} else if (sentenceFlag == sentenceSize - 1) {
			sentenceFlag = sentenceSize;
			handler.sendEmptyMessage(123);
			nowMediaPlayer.CPause();
			curveLrc.clearTotal();
			return;
		} else {
			sentenceFlag++;
			handler.sendEmptyMessage(123);
		}
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		curveLrc.setToSen(sentenceFlag);
		curveLrc.drawCurveAndLrc(
				nowSongSentence.get(sentenceFlag).StartTimeofThis, new int[0]);

		countBlueImage.setVisibility(View.VISIBLE);

		nowMediaPlayer
				.CSeekTo(nowSongSentence.get(sentenceFlag).StartTimeofThis);
		nowMediaPlayer.CPause();

		audRec.setRecording(false);
		audRec.setStartTime(nowSongSentence.get(sentenceFlag).StartTimeofThis);

		senTimer = new Timer();
		senTimer.schedule(timerTask, 1000);
	}

	public void PreSen() {
		senTimer.cancel();
		songtimer.cancel();
		initTask();

		if (sentenceFlag <= 0) {
			sentenceFlag = 0;
		} else {
			sentenceFlag--;
			handler.sendEmptyMessage(2);
		}

		curveLrc.clearColor(sentenceFlag);
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		curveLrc.setToSen(sentenceFlag);
		curveLrc.drawCurveAndLrc(
				nowSongSentence.get(sentenceFlag).StartTimeofThis, new int[0]);

		countBlueImage.setVisibility(View.VISIBLE);
		nowMediaPlayer
				.CSeekTo(nowSongSentence.get(sentenceFlag).StartTimeofThis);
		nowMediaPlayer.CPause();

		audRec.setRecording(false);
		audRec.setStartTime(nowSongSentence.get(sentenceFlag).StartTimeofThis);

		senTimer = new Timer();
		senTimer.schedule(timerTask, 1000);
	}

	public void NextThreeSen() {
		senTimer.cancel();
		songtimer.cancel();
		initTask();
		if (sentenceFlag == sentenceSize) {
			nowMediaPlayer.CPause();
			curveLrc.clearTotal();
			return;
		} else if (sentenceFlag >= sentenceSize - 3) {
			for (int i = 0; i < sentenceSize - 1; i++) {
				handler.sendEmptyMessage(123);
			}
			sentenceFlag = sentenceSize;
			nowMediaPlayer.CPause();
			curveLrc.clearTotal();
			return;
		} else {
			sentenceFlag += 3;
			handler.sendEmptyMessage(123);
			handler.sendEmptyMessage(123);
			handler.sendEmptyMessage(123);
		}

		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		curveLrc.setToSen(sentenceFlag);
		curveLrc.drawCurveAndLrc(
				nowSongSentence.get(sentenceFlag).StartTimeofThis, new int[0]);

		countBlueImage.setVisibility(View.VISIBLE);

		nowMediaPlayer
				.CSeekTo(nowSongSentence.get(sentenceFlag).StartTimeofThis);
		nowMediaPlayer.CPause();

		audRec.setRecording(false);
		audRec.setStartTime(nowSongSentence.get(sentenceFlag).StartTimeofThis);

		senTimer = new Timer();
		senTimer.schedule(timerTask, 1000);
	}

	public void PreThreeSen() {
		senTimer.cancel();
		songtimer.cancel();
		initTask();
		if (sentenceFlag <= 2) {
			for (int i = sentenceFlag; i > 0; i--) {
				handler.sendEmptyMessage(2);
			}
			sentenceFlag = 0;
		} else {
			sentenceFlag -= 3;
			handler.sendEmptyMessage(2);
			handler.sendEmptyMessage(2);
			handler.sendEmptyMessage(2);
		}

		curveLrc.clearColor(sentenceFlag);
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		curveLrc.setToSen(sentenceFlag);
		curveLrc.drawCurveAndLrc(
				nowSongSentence.get(sentenceFlag).StartTimeofThis, new int[0]);

		countBlueImage.setVisibility(View.VISIBLE);

		nowMediaPlayer
				.CSeekTo(nowSongSentence.get(sentenceFlag).StartTimeofThis);
		nowMediaPlayer.CPause();

		audRec.setRecording(false);
		audRec.setStartTime(nowSongSentence.get(sentenceFlag).StartTimeofThis);

		senTimer = new Timer();
		senTimer.schedule(timerTask, 1000);
	}

	public void RedPress() {
		iconTask1.cancel();
		iconTask1 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(4);
			}
		};
		volumnTextView.setVisibility(TextView.INVISIBLE);
		volumnImage.setVisibility(ImageView.INVISIBLE);
		volumnSeekBar.setVisibility(SeekBar.INVISIBLE);
		funcFirstImage.setVisibility(ImageView.VISIBLE);
		funcSecImage.setVisibility(ImageView.VISIBLE);
		funcSprit.setVisibility(ImageView.VISIBLE);
		if (isComa == false) {
			Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
			isComa = true;
			funcFirstImage.setImageBitmap(accompany[0]);
			funcSecImage.setImageBitmap(original[1]);
			nowMediaPlayer.CSetAccompany();
		} else {
			Toast.makeText(this, "ԭ��", Toast.LENGTH_SHORT).show();
			isComa = false;
			funcFirstImage.setImageBitmap(accompany[1]);
			funcSecImage.setImageBitmap(original[0]);
			nowMediaPlayer.CSetOriginal();
		}
		buttonTimer.schedule(iconTask1, 3000);
	}

	public void GreenPress() {
		iconTask2.cancel();
		iconTask2 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(5);
			}
		};
		volumnTextView.setVisibility(TextView.INVISIBLE);
		volumnImage.setVisibility(ImageView.INVISIBLE);
		volumnSeekBar.setVisibility(SeekBar.INVISIBLE);
		funcFirstImage.setVisibility(ImageView.VISIBLE);
		funcSecImage.setVisibility(ImageView.VISIBLE);
		funcSprit.setVisibility(ImageView.VISIBLE);
		if (isKTV == true) {
			countBlueImage.setX(610);
			countBlueImage.setY(815);
			countRedImage.setX(660);
			countRedImage.setY(815);
			countYellowImage.setX(710);
			countYellowImage.setY(815);
			Toast.makeText(this, "רҵģʽ", Toast.LENGTH_SHORT).show();
			funcFirstImage.setImageBitmap(ktvMode[1]);
			funcSecImage.setImageBitmap(professional[0]);

		} else {
			countBlueImage.setX(120);
			countBlueImage.setY(420);
			countRedImage.setX(170);
			countRedImage.setY(420);
			countYellowImage.setX(220);
			countYellowImage.setY(420);
			Toast.makeText(this, "ktvģʽ", Toast.LENGTH_SHORT).show();
			// isKTV = true;
			funcFirstImage.setImageBitmap(ktvMode[0]);
			funcSecImage.setImageBitmap(professional[1]);

		}
		setKTV();
		buttonTimer.schedule(iconTask2, 3000);
	}

	public void YellowPress() {
		iconTask3.cancel();
		iconTask3 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(6);
			}
		};
		funcFirstImage.setVisibility(ImageView.INVISIBLE);
		funcSecImage.setVisibility(ImageView.INVISIBLE);
		funcSprit.setVisibility(ImageView.INVISIBLE);
		volumnImage.setImageBitmap(volumns[0]);
		Toast.makeText(this, "��С������������", Toast.LENGTH_SHORT).show();
		mediaVolume = mediaVolume - 1;
		mediaVolumn_float = mediaVolumn_float - 0.1f;
		if (mediaVolume < 0) {
			mediaVolume = 0;
			mediaVolumn_float = 0;
		}
		nowMediaPlayer.CSetVolume(mediaVolumn_float);
		mediaVolumn_int = (int) (mediaVolume * 10);
		volumnSeekBar.setProgress(mediaVolumn_int);
		volumnTextView.setText("" + mediaVolume);
		volumnImage.setVisibility(ImageView.VISIBLE);
		volumnSeekBar.setVisibility(SeekBar.VISIBLE);
		volumnTextView.setVisibility(TextView.VISIBLE);
		buttonTimer.schedule(iconTask3, 3000);
	}

	public void BluePress() {
		iconTask3.cancel();
		iconTask3 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(6);
			}
		};
		funcFirstImage.setVisibility(ImageView.INVISIBLE);
		funcSecImage.setVisibility(ImageView.INVISIBLE);
		funcSprit.setVisibility(ImageView.INVISIBLE);
		volumnImage.setImageBitmap(volumns[0]);
		mediaVolume = mediaVolume + 1;
		mediaVolumn_float = mediaVolumn_float + 0.1f;
		if (mediaVolume > 10) {
			mediaVolume = 10;
			mediaVolumn_float = 1;
		}
		nowMediaPlayer.CSetVolume(mediaVolumn_float);
		mediaVolumn_int = (int) (mediaVolume * 10);
		Toast.makeText(this, "����ý������", Toast.LENGTH_SHORT).show();
		volumnSeekBar.setProgress(mediaVolumn_int);
		volumnTextView.setText("" + mediaVolume);
		volumnImage.setVisibility(ImageView.VISIBLE);
		volumnSeekBar.setVisibility(SeekBar.VISIBLE);
		volumnTextView.setVisibility(TextView.VISIBLE);
		buttonTimer.schedule(iconTask3, 3000);
	}

	// ��ȡlistview�ĸߺͿ������ҵģ���̫���
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		params.height += 5;// if without this statement,the listview will be a
							// little short
		listView.setLayoutParams(params);
	}

	// ������һ��������Ҳ���Զ����������
	public void ScrollToNext(MyAdapter myAdapter) {
		select_item = select_item + 1;
		if (select_item > sentenceSize) {
			select_item = sentenceSize;
		} else {
			mposition = mposition + 78;
		}
		listView.scrollTo(0, mposition);

		myAdapter.notifyDataSetChanged();
	}

	// ��ǰ�������������Զ����������
	public void ScrollToPast(MyAdapter myAdapter) {

		select_item = select_item - 1;
		if (select_item < 0) {
			mposition = -367 + (sentenceSize - 1) * 83;
			select_item = sentenceSize - 1;
		} else {
			mposition = mposition - 78;
		}
		listView.scrollTo(0, mposition);
		myAdapter.notifyDataSetChanged();

	}

	class ViewHolder {
		TextView lyricTV;
		ImageView leftScoreImage, rightScoreImage, smallStarImage;
	}

	// �Զ���������
	class MyAdapter extends BaseAdapter {
		List<Sentence> list;
		Context con;
		int smallLeftScore, smallRightScore;
		Bitmap smallLeftChangeBm, smallRightChangeBm, smallLeftBm,
				smallRightBm;
		Timer smallScoreTimer, smallStarTimer;
		int smallImageHeight, smallImageWidth;
		ViewHolder holder;
		Handler listHandler;

		public MyAdapter(ArrayList<Sentence> list, Context con) {
			super();
			this.list = list;
			this.con = con;
			smallLeftBm = smallWhiteNums[0];
			smallRightBm = smallWhiteNums[0];
			smallImageHeight = smallWhiteNums[0].getHeight();
			smallImageWidth = smallWhiteNums[0].getWidth();
			listHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					if (msg.what == 100) {
						holder.leftScoreImage.setImageBitmap(smallLeftChangeBm);
						holder.rightScoreImage
								.setImageBitmap(smallRightChangeBm);
					} else if (msg.what == 101) {
						holder.leftScoreImage.setImageBitmap(smallLeftBm);
						holder.rightScoreImage.setImageBitmap(smallRightBm);
					} else if (msg.what == 102) {
						holder.smallStarImage.startAnimation(starSecAnim);
					} else if (msg.what == 103) {
						holder.smallStarImage.setImageBitmap(darkSmallStar);
					} else if (msg.what == 104) {
						holder.smallStarImage.setImageBitmap(lightSmallStar);
						holder.smallStarImage.startAnimation(starFirstAnim);
					}
				}
			};
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = LayoutInflater.from(con).inflate(
						R.layout.lyric_item, null);
				holder = new ViewHolder();
				holder.lyricTV = (TextView) convertView
						.findViewById(R.id.lyric_tv);
				holder.leftScoreImage = (ImageView) convertView
						.findViewById(R.id.leftscoreimage);
				holder.rightScoreImage = (ImageView) convertView
						.findViewById(R.id.rightscoreimage);
				holder.smallStarImage = (ImageView) convertView
						.findViewById(R.id.smallstarimage);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (list.get(position).text.length() > 12) {
				holder.lyricTV.setText(list.get(position).text.subSequence(0,
						12) + "...");
			} else {
				holder.lyricTV.setText(list.get(position).text);
			}
			holder.lyricTV.setTextSize(20);

			smallRightScore = list.get(position).Score % 10;
			smallLeftScore = (list.get(position).Score - smallRightScore) / 10;

			if (select_item == position) {
				holder.lyricTV.setTextColor(Color.RED);
				holder.leftScoreImage.setImageBitmap(redNums[smallLeftScore]);
				holder.rightScoreImage.setImageBitmap(redNums[smallRightScore]);
			} else {
				holder.lyricTV.setTextColor(Color.WHITE);
				holder.leftScoreImage
						.setImageBitmap(smallWhiteNums[smallLeftScore]);
				holder.rightScoreImage
						.setImageBitmap(smallWhiteNums[smallRightScore]);
			}
			return convertView;

		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		nowMediaPlayer.CPause();
		songtimer.cancel();
		disTimer.cancel();
		senTimer.cancel();
		buttonTimer.cancel();
		timeTimer.cancel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		nowMediaPlayer.release();
		audRec.free();
		audRec = null;
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
