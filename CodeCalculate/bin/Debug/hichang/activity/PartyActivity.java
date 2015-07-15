package hichang.activity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import hichang.Song.CMediaPlayer;
import hichang.Song.LocalBitmap;
import hichang.Song.ReadText;
import hichang.Song.Sentence;
import hichang.Song.Singer;
import hichang.Song.Song;
import hichang.Song.SongList;
import hichang.Song.User;
import hichang.audio.AudRec;
import hichang.ourView.CurveAndLrc;
import hichang.ourView.KeyEditText;
import hichang.ourView.LrcTextView;
import hichang.ourView.VoiceView;
import hichang.ourView.CurveAndLrc.ModeType;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PartyActivity extends Activity {

	// 音量条
	private SeekBar volumnSeekBar;
	// 是否调节伴奏音乐
	private boolean isComa;
	// 是否调节模式
	private boolean isKTV;

	// 媒体音量
	private float mediaVolume;
	private int mediaVolumn_int;
	// 麦克风音量
	private int micVolumn;
	// 判断timer是否开启
	private boolean isStart;
	// 功能图片
	Bitmap[] accompany;
	Bitmap[] original;
	Bitmap[] ktvMode;
	Bitmap[] professional;
	Bitmap volumnnote;
	// 资源
	private Resources resources;
	// 音樂播放器
	private CMediaPlayer media;
	// 原唱和伴奏的路径
	private String oriPath;
	private String accomPath;
	private ImageView funcRedImage, funcGreenImage, funcYellowImage,
			funcBlueImage;
	private ImageView funcFirstImage, funcSecImage, funcSprit, volumnIcon;
	private ImageView singerImage;
	private TextView musicInfo;
	private ListView partyLV;
	private ImageView menuFirstIV, menuSecIV, menuThirdIV;
	private Bitmap[] menuNum = new Bitmap[10];

	SongList nowSongList;
	int bookListPage = 1;
	int rankListPage = 1;
	int searchListPage = 1;
	AbsoluteLayout listLayout, keyBoardLayout;
	MyAdapter adapter;
	boolean isMenuShow = false;
	int whichList = 2;
	private Bitmap[] menuBook = new Bitmap[2];
	private Bitmap[] menuBooked = new Bitmap[2];
	private Bitmap[] menuRank = new Bitmap[2];
	Song song, nowSong;
	Singer singer;
	User user;
	VoiceView nowVoiceView;
	LrcTextView nowLrcTV;

	ArrayList<Sentence> nowSongSentences;
	Timer startTimer, disTimer, songTimer,timeTimer;
	int nowSongTime;
	Sentence nowSentence, nextSentence, lastSentence;
	int sentenceFlag;
	ImageView[] colorBalls = new ImageView[3];
	double leftTimes;
	int nowSongLength;
	final int TIMESTEP = 40;
	int standTime;
	int timeHandX;
	int nowSongScore;
	int songId;
	int highScore;
	int singerid;
	ImageView timeHand;
	Handler handler;

	boolean isPlay = true; // 是否在放歌
	TextView searchTV;
	boolean isOk=false;

	Animation lightAnim, darkAnim;
	ImageView menuLeftSelectFirst, menuLeftSelectSec, menuRightSelectFirst,
			menuRightSelectSec;

	private TextView textView;
	private TimerTask task;
	private Timer timer;
	private boolean isTimerCancel;

	CurveAndLrc curveLrc;
	AudRec audRec;
	private int curveW, curveH, curveX, curveY;
	private int lrcH, lrcW, lrcX, lrcY;

	final static int MSG_TURN_COLORBARS = 100;
	final static int MSG_HIDE_YELLOWBALL = 101;
	final static int MSG_HIDE_REDBALL = 102;
	final static int MSG_HIDE_BLUEBALL = 103;
	final static int MSG_SHOW_TIME = 105;
	final static int MSG_SHOW_SCORE = 104;
	final static int MSG_START_SONG = 200;

	String songTime;
	
	ImageView staffImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.party);

		timer = new Timer();
		songTimer = new Timer();
		disTimer = new Timer();

		isTimerCancel = false;

		singerImage = (ImageView) findViewById(R.id.party_image);
		musicInfo = (TextView) findViewById(R.id.party_musicinfo);
		textView = (TextView) findViewById(R.id.party_text_zcl);
		volumnSeekBar = (SeekBar) findViewById(R.id.party_player_seekbar);
		funcRedImage = (ImageView) findViewById(R.id.party_function_red);
		funcGreenImage = (ImageView) findViewById(R.id.party_function_green);
		funcBlueImage = (ImageView) findViewById(R.id.party_function_blue);
		funcYellowImage = (ImageView) findViewById(R.id.party_function_yellow);
		funcFirstImage = (ImageView) findViewById(R.id.party_funcfirstimage);
		funcSecImage = (ImageView) findViewById(R.id.party_funcsecimage);
		funcSprit = (ImageView) findViewById(R.id.party_funcsprit);
		volumnIcon = (ImageView) findViewById(R.id.party_volumntype);
		volumnSeekBar.setMax(100);
		partyLV = (ListView) findViewById(R.id.party_list);
		menuFirstIV = (ImageView) findViewById(R.id.party_menu_image1);
		menuSecIV = (ImageView) findViewById(R.id.party_menu_image2);
		menuThirdIV = (ImageView) findViewById(R.id.party_menu_image3);
		listLayout = (AbsoluteLayout) findViewById(R.id.party_list_layout);
		colorBalls[0] = (ImageView) findViewById(R.id.party_countblue);
		colorBalls[1] = (ImageView) findViewById(R.id.party_countred);
		colorBalls[2] = (ImageView) findViewById(R.id.party_countyellow);
		timeHand = (ImageView) findViewById(R.id.party_timeHand);
		searchTV = (TextView) findViewById(R.id.party_search_edit);
		keyBoardLayout = (AbsoluteLayout) findViewById(R.id.party_keyboard_layout);
		menuLeftSelectFirst = (ImageView) findViewById(R.id.party_left_first);
		menuLeftSelectSec = (ImageView) findViewById(R.id.party_left_sec);
		menuRightSelectFirst = (ImageView) findViewById(R.id.party_right_first);
		menuRightSelectSec = (ImageView) findViewById(R.id.party_right_sec);
		curveLrc = (CurveAndLrc) findViewById(R.id.party_curveandlrc);
		staffImage = (ImageView)findViewById(R.id.party_staff);
		resources = this.getResources();
		//searchTV.setKeyBoard(keyBoardLayout);
		partyLV.setFocusable(false);

		song = new Song(getBaseContext());
		singer = new Singer(getBaseContext());
		user = new User(getBaseContext());

		isComa = false;
		isKTV = false;
		mediaVolume = 0.3f;
		mediaVolumn_int = 30;
		micVolumn = 30;
		accompany = new Bitmap[2];
		original = new Bitmap[2];
		ktvMode = new Bitmap[2];
		professional = new Bitmap[2];

		task = new TimerTask() {
			public void run() {
				Message message = new Message();
				message.what = 3;
				handler.sendMessage(message);
			}
		};

		// 获取点歌界面传来的SongId
		Intent intent = getIntent();
		songId = intent.getIntExtra("songId", 0);
		nowSong = song.findSongById(songId);

		singerid = singer.querySingerByName(nowSong.getSinger1()).getiD();
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
		musicInfo.setText(nowSong.getName() + "\n最高分 - " + highScore + "\n");

		handler = new Handler() {

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					funcFirstImage.setVisibility(ImageView.INVISIBLE);
					funcSecImage.setVisibility(ImageView.INVISIBLE);
					funcSprit.setVisibility(ImageView.INVISIBLE);
					break;
				case 2:
					funcFirstImage.setVisibility(ImageView.INVISIBLE);
					funcSecImage.setVisibility(ImageView.INVISIBLE);
					funcSprit.setVisibility(ImageView.INVISIBLE);
					break;
				case 3:
					volumnIcon.setVisibility(ImageView.INVISIBLE);
					volumnSeekBar.setVisibility(SeekBar.INVISIBLE);
					textView.setVisibility(TextView.INVISIBLE);
					break;
				// 倒计时小球出现
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
								disTimer.cancel();
							}
							i++;
						}
					}, 0, 1000);
					break;
				// 倒数第三个小球隐藏
				case MSG_HIDE_YELLOWBALL:
					colorBalls[2].setVisibility(ImageView.INVISIBLE);
					break;
				// 倒数第二个小球隐藏
				case MSG_HIDE_REDBALL:
					colorBalls[1].setVisibility(ImageView.INVISIBLE);
					break;
				// 倒数第一个小球隐藏
				case MSG_HIDE_BLUEBALL:
					colorBalls[0].setVisibility(ImageView.INVISIBLE);
					break;
				case 13:
					menuLeftSelectFirst.startAnimation(lightAnim);
					menuLeftSelectSec.startAnimation(darkAnim);
					menuRightSelectFirst.startAnimation(lightAnim);
					menuLeftSelectSec.startAnimation(darkAnim);
					break;
				case MSG_SHOW_TIME:
					int time=nowSongTime/1000;
					int second=time%60;
					int minute=(time-second)/60;
					if(second<10){
						musicInfo.setText(nowSong.getName()+"\n最高分 - "+highScore+"\n"+minute+": "+"0"+second+"/"+songTime);
					} else {
						musicInfo.setText(nowSong.getName()+"\n最高分 - "+highScore+"\n"+minute+": "+second+"/"+songTime);
					}
					break;
				case MSG_START_SONG:
						media.CStart();
						songTimer=new Timer();
						songTimer.schedule(new TimerTask() {
							
						@Override

						public void run() {
								
								nowSongTime = media.CGetCurrentPosition();
								handler.sendEmptyMessage(MSG_SHOW_TIME);
								if (sentenceFlag < nowSongSentences.size()) {
									if (!isStart&&nowSongTime > nowSongSentences.get(0).StartTimeofThis - 3000) {
										handler.sendEmptyMessage(MSG_TURN_COLORBARS);
										isStart=true;
									}
									if (nowSongTime >= nowSongSentences
											.get(sentenceFlag).StartTimeofThis
											+ nowSongSentences.get(sentenceFlag).LastTimeofThis) {
										sentenceFlag++;
									}

							}
							curveLrc.drawCurveAndLrc(nowSongTime - 440,
									audRec.getNote(nowSongTime - 440));
						}
					}, 40, 40);
					
					break;
				}
			}
		};

		songTimer = new Timer();
		media = new CMediaPlayer();
		initPic();
		initMenu();
		startMenuSelectAnim();
		initMediaPlayer();
	}
	
	public void onTextChanged() {
		// TODO Auto-generated method stub
		adapter.setSongs((ArrayList<Song>) song
				.findTenSongBySimpleName(searchTV.getText().toString(),
						searchListPage));
		adapter.notifyDataSetChanged();
	}
	
	public void setKTV(boolean isKtv, boolean isMenu) {
		isKTV = isKtv;
		if (isKTV) {
			if (isMenu) {
				lrcH = (int) resources
						.getDimension(R.dimen.party_menu_ktv_lrc_height);
				lrcW = (int) resources
						.getDimension(R.dimen.party_menu_ktv_lrc_width);
				lrcX = (int) resources
						.getDimension(R.dimen.party_menu_ktv_lrc_x);
				lrcY = (int) resources
						.getDimension(R.dimen.party_menu_ktv_lrc_y);
				curveW = (int) resources
						.getDimension(R.dimen.party_menu_ktv_curve_width);
				curveH = (int) resources
						.getDimension(R.dimen.party_menu_ktv_curve_height);
				curveX = (int) resources
						.getDimension(R.dimen.party_menu_ktv_curve_x);
				curveY = (int) resources
						.getDimension(R.dimen.party_menu_ktv_curve_y);
			} else {
				lrcH = (int) resources
						.getDimension(R.dimen.party_ktv_lrc_height);
				lrcW = (int) resources
						.getDimension(R.dimen.party_ktv_lrc_width);
				lrcX = (int) resources.getDimension(R.dimen.party_ktv_lrc_x);
				lrcY = (int) resources.getDimension(R.dimen.party_ktv_lrc_y);
				curveW = (int) resources
						.getDimension(R.dimen.party_ktv_curve_width);
				curveH = (int) resources
						.getDimension(R.dimen.party_ktv_curve_height);
				curveX = (int) resources
						.getDimension(R.dimen.party_ktv_curve_x);
				curveY = (int) resources
						.getDimension(R.dimen.party_ktv_curve_y);
			}
		} else {
			if (isMenu) {
				lrcH = (int) resources
						.getDimension(R.dimen.party_menu_vocational_lrc_height);
				lrcW = (int) resources
						.getDimension(R.dimen.party_menu_vocational_lrc_width);
				lrcX = (int) resources
						.getDimension(R.dimen.party_menu_vocational_lrc_x);
				lrcY = (int) resources
						.getDimension(R.dimen.party_menu_vocational_lrc_y);
				curveW = (int) resources
						.getDimension(R.dimen.party_menu_vocational_curve_width);
				curveH = (int) resources
						.getDimension(R.dimen.party_menu_vocational_curve_height);
				curveX = (int) resources
						.getDimension(R.dimen.party_menu_vocational_curve_x);
				curveY = (int) resources
						.getDimension(R.dimen.party_menu_vocational_curve_y);
			} else {
				lrcH = (int) resources
						.getDimension(R.dimen.party_vocational_lrc_height);
				lrcW = (int) resources
						.getDimension(R.dimen.party_vocational_lrc_width);
				lrcX = (int) resources
						.getDimension(R.dimen.party_vocational_lrc_x);
				lrcY = (int) resources
						.getDimension(R.dimen.party_vocational_lrc_y);
				curveW = (int) resources
						.getDimension(R.dimen.party_vocational_curve_width);
				curveH = (int) resources
						.getDimension(R.dimen.party_vocational_curve_height);
				curveX = (int) resources
						.getDimension(R.dimen.party_vocational_curve_x);
				curveY = (int) resources
						.getDimension(R.dimen.party_vocational_curve_y);
			}
		}
		curveLrc.setCurveXYWH(curveX, curveY, curveW, curveH);
		curveLrc.setLrcXYWH(lrcX, lrcY, lrcW, lrcH);
		curveLrc.setKTV(isKTV);
	}

	public void initSong() {
		ReadText nowText = new ReadText(nowSong.getSongLyricUrl());
		nowSongSentences = nowText.ReadData();

		nowSentence = nowSongSentences.get(0);
		nextSentence = nowSongSentences.get(1);
		lastSentence = nowSongSentences.get(nowSongSentences.size() - 1);
		sentenceFlag = 0;
		nowSongTime=0;
		curveLrc.init(nowSongSentences, nowText.max, nowText.min,
				ModeType.MODE_PARTY);
		setKTV(isKTV, isMenuShow);

		audRec = new AudRec(nowSongSentences, handler, media);
		audRec.init();

		nowSongLength = media.CGetDuration();
		int sec=(nowSongLength/1000)%60;
		int min=(nowSongLength/1000-sec)/60;
		if(sec<10){
			songTime=min+":"+"0"+sec;
		} else{
			songTime=min+":"+sec;
		}
		// Toast.makeText(this, nowSong.name+"initSong",
		// Toast.LENGTH_SHORT).show();
	}

	public void play() {
		media.CReset();
		media.CSetDataSource(nowSong.getMusicPath(),
				nowSong.getAccomanimentPath());
		media.CPrepare();
		initSong();
		audRec.start();
		mediaVolume = media.CGetVolume();
	}

	public void initMediaPlayer() {
		// TODO Auto-generated method stub
		nowSongList = new SongList();

//		Song firstSong = new Song(getBaseContext());
//		firstSong = firstSong.findSongById(1236);
//		Song secSong = new Song(getBaseContext());
//		secSong = secSong.findSongById(842);
//		nowSongList.bookSong(firstSong);
//		nowSongList.bookSong(secSong);
		media.mediaOriginal.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				// audRec.free();
				songTimer.cancel();
				audRec.free();
				audRec = null;
				Toast.makeText(getBaseContext(), "next", Toast.LENGTH_SHORT)
						.show();
				nextSong();
			}
		});
		play();
	}

	public void startMenuSelectAnim() {
		lightAnim = AnimationUtils.loadAnimation(this, R.anim.twinkle_appear);
		lightAnim.setFillAfter(true);
		darkAnim = AnimationUtils.loadAnimation(this, R.anim.twinkle_disappear);
		darkAnim.setFillAfter(true);
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(13);
			}
		}, 0, 1000);
	}

	public void initPic() {
		volumnnote = BitmapFactory.decodeResource(resources,
				R.drawable.musicalnote);
		// 功能模块的图片
		for (int i = 0; i < 2; i++) {
			ktvMode[i] = BitmapFactory.decodeResource(resources,
					R.drawable.ktv_selected + i);
			professional[i] = BitmapFactory.decodeResource(resources,
					R.drawable.professional_selected + i);
			original[i] = BitmapFactory.decodeResource(resources,
					R.drawable.original_selected + i);
			accompany[i] = BitmapFactory.decodeResource(resources,
					R.drawable.accompany_selected + i);

			menuBook[i] = BitmapFactory.decodeResource(resources,
					R.drawable.party_book_selected + i);
			menuBooked[i] = BitmapFactory.decodeResource(resources,
					R.drawable.party_booked_selected + i);
			menuRank[i] = BitmapFactory.decodeResource(resources,
					R.drawable.party_rank_selected + i);
		}

		for (int i = 0; i < 10; i++) {
			menuNum[i] = BitmapFactory.decodeResource(resources,
					R.drawable.party_menu_button0 + i);
		}

	}

	public void initMenu() {
		adapter = new MyAdapter(
				(ArrayList<Song>) song.queryTenSongByTwoClicks(rankListPage),
				this);
		partyLV.setAdapter(adapter);
	}

	public void nextSong() {	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		curveLrc.clearTotal();
		isStart = false;
		nowSong = nowSongList.nextSong();

		if (nowSong == null) {
			Toast.makeText(this, "没歌了啊", Toast.LENGTH_SHORT);
			isPlay = false;
		} else {
			isPlay = true;
			Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
			singerid = singer.querySingerByName(nowSong.getSinger1()).getiD();
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
			play();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (isMenuShow) {
			switch (keyCode) {
			case 7:
				OnNumKeyDown(0);
				break;
			case 8:
				OnNumKeyDown(1);
				break;
			case 9:
				OnNumKeyDown(2);
				break;
			case 10:
				OnNumKeyDown(3);
				break;
			case 11:
				OnNumKeyDown(4);
				break;
			case 12:
				OnNumKeyDown(5);
				break;
			case 13:
				OnNumKeyDown(6);
				break;
			case 14:
				OnNumKeyDown(7);
				break;
			case 15:
				OnNumKeyDown(8);
				break;
			case 16:
				OnNumKeyDown(9);
				break;
			default:
				break;
			}
		} else {
			switch (keyCode) {
			case 8:

				break;

			default:
				break;
			}
		}
		if (keyCode == 66) {
			if (isMenuShow) {
				if (whichList == 3) {
					isOk = false;
				}
			} else {
				if (nowSongList.size() == 0) {
					media.CStop();
					Toast.makeText(this, "没歌了，快去点歌吧", Toast.LENGTH_SHORT);
				} else {
					disTimer.cancel();
					songTimer.cancel();
					audRec.free();
					audRec = null;
					nextSong();
				}
			}
		}
		if (keyCode == 219) {
			if (!isOk) {
				String text = "";
				int length = searchTV.getText().length();
				if (length > 0) {
					text = searchTV.getText().toString()
							.substring(0, length - 1);
				}
				searchTV.setText(text);
				onTextChanged();
			}
		}
		if (keyCode == 82) {
			if (isMenuShow) {	
				TranslateAnimation translateAnimation = new TranslateAnimation(
						0, 495, 0, 0);
				translateAnimation.setDuration(500);
				translateAnimation.setFillAfter(true);
				listLayout.startAnimation(translateAnimation);
				searchTV.startAnimation(translateAnimation);
				keyBoardLayout.startAnimation(translateAnimation);
				isMenuShow = false;
			} else {
				initMenu();
				listLayout.setVisibility(AbsoluteLayout.VISIBLE);
				TranslateAnimation translateAnimation = new TranslateAnimation(
						495, 0, 0, 0);
				translateAnimation.setDuration(500);
				translateAnimation.setFillAfter(true);
				listLayout.startAnimation(translateAnimation);
				searchTV.startAnimation(translateAnimation);
				keyBoardLayout.startAnimation(translateAnimation);
				isMenuShow = true;
			}
			setKTV(isKTV, isMenuShow);
		}
		// 按键为左键
		else if (keyCode == 21) {
			if (isMenuShow) {
				searchTV.setText("");
				isOk=false;
				switch (whichList) {
				case 1:
					bookListPage = 1;
					whichList = 3;
					searchTV.setVisibility(View.VISIBLE);
					menuFirstIV.setImageBitmap(menuRank[1]);
					menuSecIV.setImageBitmap(menuBook[0]);
					menuThirdIV.setImageBitmap(menuBooked[1]);
					adapter.setSongs((ArrayList<Song>) song
							.findTenSongBySimpleName("", searchListPage));
					break;
				case 2:
					rankListPage = 1;
					whichList = 1;
					searchTV.setVisibility(View.INVISIBLE);
					menuFirstIV.setImageBitmap(menuBook[1]);
					menuSecIV.setImageBitmap(menuBooked[0]);
					menuThirdIV.setImageBitmap(menuRank[1]);
					adapter.setSongs(nowSongList.getSongsByPage(bookListPage));
					break;
				case 3:
					searchListPage = 1;
					whichList = 2;
					searchTV.setVisibility(View.INVISIBLE);
					menuFirstIV.setImageBitmap(menuBooked[1]);
					menuSecIV.setImageBitmap(menuRank[0]);
					menuThirdIV.setImageBitmap(menuBook[1]);
					adapter.setSongs((ArrayList<Song>) song
							.queryTenSongByTwoClicks(rankListPage));
					break;
				default:
					break;
				}
				adapter.notifyDataSetChanged();
			}
		}

		// 按键为右键
		else if (keyCode == 22) {
			if (isMenuShow) {
				searchTV.setText("");
				isOk=false;
				switch (whichList) {
				case 1:
					bookListPage = 1;
					whichList = 2;
					isOk=true;
					searchTV.setVisibility(View.INVISIBLE);
					menuFirstIV.setImageBitmap(menuBooked[1]);
					menuSecIV.setImageBitmap(menuRank[0]);
					menuThirdIV.setImageBitmap(menuBook[1]);
					adapter.setSongs((ArrayList<Song>) song
							.queryTenSongByTwoClicks(rankListPage));
					break;
				case 2:
					rankListPage = 1;
					whichList = 3;
					isOk=false;
					searchTV.setVisibility(View.VISIBLE);
					menuFirstIV.setImageBitmap(menuRank[1]);
					menuSecIV.setImageBitmap(menuBook[0]);
					menuThirdIV.setImageBitmap(menuBooked[1]);
					adapter.setSongs((ArrayList<Song>) song
							.findTenSongBySimpleName("", searchListPage));
					break;
				case 3:
					searchListPage = 1;
					whichList = 1;
					isOk=false;
					searchTV.setVisibility(View.INVISIBLE);
					menuFirstIV.setImageBitmap(menuBook[1]);
					menuSecIV.setImageBitmap(menuBooked[0]);
					menuThirdIV.setImageBitmap(menuRank[1]);
					adapter.setSongs(nowSongList.getSongsByPage(bookListPage));
					break;
				default:
					break;
				}
				adapter.notifyDataSetChanged();
			}
		} else if (keyCode == 19) {
			if (isMenuShow) {
				switch (whichList) {
				case 1:
					bookListPage--;
					if (bookListPage <= 0) {
						bookListPage = nowSongList.getPageNum();
						adapter.setSongs(nowSongList
								.getSongsByPage(bookListPage));
					} else {
						adapter.setSongs(nowSongList
								.getSongsByPage(bookListPage));
					}
					break;
				case 2:
					rankListPage--;
					if (rankListPage <= 0) {
						rankListPage = song.getSongPage();
						adapter.setSongs((ArrayList<Song>) song
								.queryTenSongByTwoClicks(rankListPage));
					} else {
						adapter.setSongs((ArrayList<Song>) song
								.queryTenSongByTwoClicks(rankListPage));
					}
					break;
				case 3:
					searchListPage--;
					if (searchListPage <= 0) {
						searchListPage = song.getSongPageBySimpleName("");
						adapter.setSongs((ArrayList<Song>) song
								.findTenSongBySimpleName(searchTV.getText()
										.toString(), searchListPage));
					} else {
						adapter.setSongs((ArrayList<Song>) song
								.findTenSongBySimpleName(searchTV.getText()
										.toString(), searchListPage));
					}
					break;
				default:
					break;
				}
				adapter.notifyDataSetChanged();
			}
		} else if (keyCode == 20) {
			if (isMenuShow) {
				switch (whichList) {
				case 1:
					bookListPage++;
					ArrayList<Song> theBookedSongs = nowSongList
							.getSongsByPage(bookListPage);
					if (theBookedSongs == null) {
						bookListPage = 1;
						adapter.setSongs(nowSongList
								.getSongsByPage(bookListPage));
					} else {
						adapter.songs = theBookedSongs;
					}
					break;
				case 2:
					rankListPage++;
					ArrayList<Song> theRankSongs = (ArrayList<Song>) song
							.queryTenSongByTwoClicks(rankListPage);
					if (theRankSongs == null) {
						rankListPage = 1;
						adapter.setSongs((ArrayList<Song>) song
								.queryTenSongByTwoClicks(rankListPage));
					} else {
						adapter.songs = theRankSongs;
					}
					break;
				case 3:
					searchListPage++;
					ArrayList<Song> theBookSongs = (ArrayList<Song>) song
							.findTenSongBySimpleName(searchTV.getText()
									.toString(), searchListPage);
					if (theBookSongs == null) {
						searchListPage = 1;
						adapter.setSongs((ArrayList<Song>) song
								.findTenSongBySimpleName(searchTV.getText()
										.toString(), searchListPage));
					} else {
						adapter.songs = theBookSongs;
					}
					break;
				default:
					break;
				}
				adapter.notifyDataSetChanged();
			}
		}

		// 按键为返回键
		if (keyCode == 4) {
			Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent();
			intent.putExtra("type", 2);
			intent.setClass(PartyActivity.this, RemoteMusicActivity.class);
			startActivity(intent);
			this.finish();
		}
		// 按键为红色键
		if (keyCode == 183) {
			// StartNumTurnAnim(99);
			textView.setVisibility(TextView.INVISIBLE);
			volumnIcon.setVisibility(ImageView.INVISIBLE);
			volumnSeekBar.setVisibility(SeekBar.INVISIBLE);
			funcFirstImage.setVisibility(ImageView.VISIBLE);
			funcSecImage.setVisibility(ImageView.VISIBLE);
			funcSprit.setVisibility(ImageView.VISIBLE);
			if (isComa == false) {
				Toast.makeText(this, "伴奏", Toast.LENGTH_SHORT).show();
				isComa = true;
				funcFirstImage.setImageBitmap(accompany[0]);
				funcSecImage.setImageBitmap(original[1]);
				media.CSetAccompany();
			} else {
				Toast.makeText(this, "原唱", Toast.LENGTH_SHORT).show();
				isComa = false;
				funcFirstImage.setImageBitmap(accompany[1]);
				funcSecImage.setImageBitmap(original[0]);
				media.CSetOriginal();
			}
		}
		// 按键为绿色键
		if (keyCode == 184) {
			textView.setVisibility(TextView.INVISIBLE);
			volumnIcon.setVisibility(ImageView.INVISIBLE);
			volumnSeekBar.setVisibility(SeekBar.INVISIBLE);
			funcFirstImage.setVisibility(ImageView.VISIBLE);
			funcSecImage.setVisibility(ImageView.VISIBLE);
			funcSprit.setVisibility(ImageView.VISIBLE);
			if (isKTV == true) {
				Toast.makeText(this, "专业模式", Toast.LENGTH_SHORT).show();
				isKTV = false;
				funcFirstImage.setImageBitmap(ktvMode[1]);
				funcSecImage.setImageBitmap(professional[0]);
			} else {
				Toast.makeText(this, "ktv模式", Toast.LENGTH_SHORT).show();
				isKTV = true;
				funcFirstImage.setImageBitmap(ktvMode[0]);
				funcSecImage.setImageBitmap(professional[1]);
			}
			setKTV(isKTV, isMenuShow);
		}
		// 按键为黄色键
		if (keyCode == 185) {
			task.cancel();
			task = new TimerTask() {
				public void run() {
					Message message = new Message();
					message.what = 3;
					handler.sendMessage(message);
				}
			};
			Toast.makeText(this, "减小背景音的音量", Toast.LENGTH_SHORT).show();
			funcFirstImage.setVisibility(ImageView.INVISIBLE);
			funcSecImage.setVisibility(ImageView.INVISIBLE);
			funcSprit.setVisibility(ImageView.INVISIBLE);
			volumnIcon.setImageBitmap(volumnnote);
			mediaVolume = mediaVolume - 0.1f;
			if (mediaVolume < 0) {
				mediaVolume = 0;
			}
			media.CSetVolume(mediaVolume);
			mediaVolumn_int = (int) (mediaVolume * 100);
			volumnSeekBar.setProgress(mediaVolumn_int);
			textView.setText("" + mediaVolumn_int / 10);
			volumnIcon.setVisibility(ImageView.VISIBLE);
			volumnSeekBar.setVisibility(SeekBar.VISIBLE);
			textView.setVisibility(TextView.VISIBLE);
			timer.schedule(task, 5000);
		}
		// 按键为蓝色键
		if (keyCode == 186) {
			task.cancel();
			task = new TimerTask() {
				public void run() {
					Message message = new Message();
					message.what = 3;
					handler.sendMessage(message);
				}
			};
			Toast.makeText(this, "减小背景音的音量", Toast.LENGTH_SHORT).show();
			funcFirstImage.setVisibility(ImageView.INVISIBLE);
			funcSecImage.setVisibility(ImageView.INVISIBLE);
			funcSprit.setVisibility(ImageView.INVISIBLE);
			volumnIcon.setImageBitmap(volumnnote);
			mediaVolume = mediaVolume + 0.1f;
			if (mediaVolume > 1) {
				mediaVolume = 1;
			}
			media.CSetVolume(mediaVolume);
			mediaVolumn_int = (int) (mediaVolume * 100);
			volumnSeekBar.setProgress(mediaVolumn_int);
			textView.setText("" + mediaVolumn_int / 10);
			volumnIcon.setVisibility(ImageView.VISIBLE);
			volumnSeekBar.setVisibility(SeekBar.VISIBLE);
			textView.setVisibility(TextView.VISIBLE);
			timer.schedule(task, 5000);
		}
		return true;
	}

	public void OnNumKeyDown(int key) {
		if (whichList == 1) {
			if (adapter.songs.size() > key) {
				nowSongList.SetToFirst(bookListPage, key);
				adapter.setSongs(nowSongList.getSongsByPage(bookListPage));
			}
		} else if (whichList == 2) {
			if (adapter.songs.size() > key) {
				if (adapter.songs.get(key).getIsAvailable() == 1) {
					nowSongList.bookSong(adapter.songs.get(key));
				} else {
					Toast.makeText(this, "抱歉，该歌曲暂时不可用，请重新选歌",
							Toast.LENGTH_SHORT).show();
				}
			}
			if (!isPlay) {
				nextSong();
			}
		} else {
			String strSearch=searchTV.getText().toString();
			searchTV.setText(""+strSearch+key);
			onTextChanged();
			if (adapter.songs.size() > key) {
				if (adapter.songs.get(key).getIsAvailable() == 1) {
					nowSongList.bookSong(adapter.songs.get(key));
				} else {
					Toast.makeText(this, "抱歉，该歌曲暂时不可用，请重新选歌",
							Toast.LENGTH_SHORT).show();
				}
			}
			if (!isPlay) {
				nextSong();
			}
		}
		adapter.notifyDataSetChanged();
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
		media.CPause();
		songTimer.cancel();
		disTimer.cancel();
		timer.cancel();
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
		if (audRec != null) {
			audRec.free();
			audRec = null;
		}
		media.CRelease();
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class MyAdapter extends BaseAdapter {
		public ArrayList<Song> songs;
		private Context context;

		public MyAdapter(ArrayList<Song> songs, Context context) {
			this.songs = songs;
			this.context = context;
			if (this.songs == null) {
				this.songs = new ArrayList<Song>();
			}
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return 10;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public void setSongs(ArrayList<Song> songs) {
			this.songs = songs;
			if (this.songs == null) {
				this.songs = new ArrayList<Song>();
			}
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View item = LayoutInflater.from(context).inflate(
					R.layout.party_list_item, null);
			ImageView numImage = (ImageView) item
					.findViewById(R.id.party_menu_num);
			TextView songTV = (TextView) item
					.findViewById(R.id.party_menu_song);
			TextView singerTV = (TextView) item
					.findViewById(R.id.party_menu_singer);

			numImage.setImageBitmap(menuNum[position]);
			if (songs.size() <= position) {
				songTV.setText("");
				singerTV.setText("");
			} else {
				if (songs.get(position).getIsAvailable() == 1) {
					songTV.setTextColor(Color.WHITE);
					singerTV.setTextColor(Color.WHITE);
				}
				songs.get(position).bookNum = nowSongList.getBookedNum(songs
						.get(position).getSongID());
				if (songs.get(position).bookNum != -1) {
					songTV.setText(songs.get(position).getName() + "   预约"
							+ songs.get(position).bookNum);
				} else {
					songTV.setText(songs.get(position).getName());
				}
				singerTV.setText(songs.get(position).getSinger1());
			}
			return item;
		}
	}
}
