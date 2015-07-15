package hichang.ourView;

import hichang.Song.Chapter;
import hichang.Song.Sentence;

import java.util.ArrayList;

import android.R.bool;
import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

/*
 * �ڵ���DrawLrc��Sentence sentcet��֮ǰ
 * һ��Ҫ����init������ʼ��������ߺ���С����
 * ��������
 * 
 * 
 * �����������¼�������֮�󣬵���drawChange����
 */
public class VoiceView extends SurfaceView implements
		android.view.SurfaceHolder.Callback {
	SurfaceHolder sfh;
	public float density = 0; // ��¼ÿ�仰��ͼ���ܶ� һ���ֵ������ʼλ��
	private Sentence sentence;// ��ǰ��Ҫ��ľ��ӣ�
	private int max;// ���׸���������
	private int min;// ���׸����С����

	Canvas canvas;
	Paint paint, textPaint, pointPaint, wrongPaint, linePaint;
	public float width;
	public float height;
	float[] screenlocation;
	public float rectHeight;
    public float pitchHeight;
	
	int nowPointColors[];
	float nowPointPos[];

	int nowChapterFlag;
	float nowHigh;
	int rectLeft, rectRight;
	LinearGradient shader;
	Chapter nowChapter, nextChapter, lastChapter;
	/**
	 * nowPointNum:����Ҫ�����ֵĵ���
	 */
	int nowPoint;
	/**
	 * nowPoint:���ڻ����ĵ���
	 */
	int nowPointNum;
	/**
	 * ����ֵĵ��λ�úͳ�ʼ��ɫ׼������û��
	 */
	boolean isPrepared = false;
	public boolean isKTV = false;
	/**
	 * ��ǰ�ֳ���ĵ�ĵ㼯
	 */
    ArrayList<Float> wrongPointX=new ArrayList<Float>(); 
    ArrayList<Integer> wrongResult=new ArrayList<Integer>();
    
    int preLineX=0;
	public VoiceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		sfh = this.getHolder();
		sfh.addCallback(this);
		this.setZOrderOnTop(true);
		sfh.setFormat(PixelFormat.TRANSLUCENT);

		paint = new Paint();
		paint.setColor(Color.YELLOW);// ����ÿ�仰����ɫ
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2.5f);
		textPaint = new Paint();
		textPaint.setColor(Color.WHITE);// �����������ɫ
		textPaint.setTextSize(40);// ��������Ĵ�С
		pointPaint = new Paint();
		wrongPaint = new Paint();
		wrongPaint.setColor(Color.rgb(255, 69, 0));
		linePaint=new Paint();
		linePaint.setColor(Color.DKGRAY);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		getWH();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		getWH();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	// ��ÿ�仰������
	// sentence�ǵ�ǰҪ���ľ���
	public void DrawLrc(Sentence sentence, boolean isktv) {

		getWH();
		isKTV = isktv;
		getWH();
		
		Clear(isktv);
		Clear(isktv);

		// ��������clear ���˫����
		this.sentence = sentence;
		isPrepared = false;
		nowChapterFlag = 0;
		nextChapter = sentence.mychapter.get(0);
		lastChapter = sentence.mychapter.get(sentence.mychapter.size() - 1);
		canvas = sfh.lockCanvas();
		screenlocation = new float[sentence.getChaptetCount()];
		screenLocation(screenlocation, sentence);

		if (isKTV) {
			DrawWhiteLines(canvas);

			RectF rectF;
			float high;
			for (int i = 0; i < sentence.getChaptetCount(); i++) {
				high = chapterHigh(sentence.getChapterHigh(i));
				rectF = new RectF((int) screenlocation[i], (int)high,
						(int) (screenlocation[i] + sentence.getChapterLast(i)
								* density), (int)high + rectHeight);
				// ��¼��Ҫ����Բ�Ǿ���
				canvas.drawRoundRect(rectF, 8, 8, paint);
			} // ���������������·���λ��
			sfh.unlockCanvasAndPost(canvas);
			// ����һ�������ĸ��

		} else {
			RectF rectF;
			float high;
			for (int i = 0; i < sentence.getChaptetCount(); i++) {
				high = chapterHigh(sentence.getChapterHigh(i));
				rectF = new RectF((int) screenlocation[i], (int)high,
						(int) (screenlocation[i] + sentence.getChapterLast(i)
								* density), (int)high + rectHeight);

				// ��¼��Ҫ����Բ�Ǿ���
				canvas.drawRoundRect(rectF, 8, 8, paint);
				canvas.drawText(sentence.getChapterText(i),
						(int) screenlocation[i] + sentence.getChapterLast(i)
								* density / 4,// ����������Ӧ�������λ��
						high + 100, textPaint);
			} // ���������������·���λ��
			sfh.unlockCanvasAndPost(canvas);
			// ����һ�������ĸ��
		}
	}

	private void screenLocation(float[] location, Sentence sentence) {

		density = (width -20)/ sentence.LastTimeofThis;// ͼ�������/ʱ�䣨ÿ�����߶������أ�
		for (int k = 0; k < sentence.getChaptetCount(); k++) {
			location[k] = density * sentence.getChapterStart(k)+10;
		}
	}// ����ÿ������ͼ���ϵ���ʼλ��

	private float chapterHigh(int thishigh) {

		return height - pitchHeight * (thishigh - min + 1 + 4);// ���߳���ÿ�ݵĸ߶�
	}// ����ÿ���ֵ�����Ļ�ϵ���ȷ�߶�

	// ��ʼ�����׸��������ߺ���С����
	public void init(int max, int min) {
		this.max = max;
		this.min = min;
		// this.handImage=hand;
	}

	public void Clear(boolean isktv) {
		canvas = sfh.lockCanvas();
		canvas.drawColor(Color.TRANSPARENT,
				android.graphics.PorterDuff.Mode.CLEAR);
		if (isktv) {
			DrawWhiteLines(canvas);
		}
		sfh.unlockCanvasAndPost(canvas);
	}// ����
	
	public void ClearTotal() {
		canvas = sfh.lockCanvas();
		canvas.drawColor(Color.TRANSPARENT,
				android.graphics.PorterDuff.Mode.CLEAR);
	
		sfh.unlockCanvasAndPost(canvas);
	}// ����
	
	// ����ɫ����
	public void DrawWhiteLines(Canvas canvas) {
		float[] pts = new float[10];
		pts[0] = height / 11;
		canvas.drawLine(0, pts[0], width, pts[0], linePaint);
		for (int i = 1; i < 10; i++) {
			pts[i] = pts[i - 1] + height / 11;
			canvas.drawLine(0, pts[i], width, pts[i], linePaint);
		}
	}

	public int drawPoints(int nowTime, int result) {
		if (nowTime < sentence.StartTimeofThis + lastChapter.ChpofStart + lastChapter.ChpofLast + 40)
        {
			if (isPrepared) 
			{
				if ((nowTime >= (nowChapter.ChpofStart + sentence.StartTimeofThis))&&(nowTime < (nowChapter.ChpofStart + nowChapter.ChpofLast + sentence.StartTimeofThis + 40))) 
				{
					Log.i("3", nowTime - nowChapter.ChpofStart
							- sentence.StartTimeofThis + "");

					if (nowTime > nowChapter.ChpofStart + nowChapter.ChpofLast + sentence.StartTimeofThis) 
					{
						isPrepared = false;
					}
					setColor(result);

					shader = new LinearGradient(rectLeft, 0, rectRight, 0,nowPointColors, nowPointPos, TileMode.CLAMP);
					pointPaint.setShader(shader);
					canvas = sfh.lockCanvas(new Rect(rectLeft, 0, rectRight,(int) height));
					if (!isKTV)
					{
						canvas.drawText(nowChapter.text,rectLeft + sentence.getChapterLast(nowChapterFlag - 1)* density / 4,nowHigh + 100, textPaint);
					}
					else 
					{
						DrawWhiteLines(canvas);
					}
					
					// ������˸���ػ���ǰ��Բ�Ǿ��κ͵�ǰ��
					canvas.drawRoundRect(new RectF(rectLeft, (int) nowHigh,rectRight, (int) (nowHigh + rectHeight)), 8,8, paint);
					

					// ���Բ�Ǿ���
					canvas.drawRoundRect(new RectF(rectLeft, (int) nowHigh,	rectRight, (int) (nowHigh + rectHeight)), 8, 8,pointPaint);

					for (int i = 0; i < wrongPointX.size(); i++)
					{
						canvas.drawRect(
								new Rect(
										(int) (rectLeft + (rectRight - rectLeft)
												* (wrongPointX.get(i) - 1.0f / nowChapter.ChpofLast * 40)),
										(int) (nowHigh + wrongResult.get(i)
												* pitchHeight),
										(int) (rectLeft + (rectRight - rectLeft)
												* wrongPointX.get(i)),
										(int) (nowHigh + pitchHeight + wrongResult
												.get(i) * pitchHeight)),
								wrongPaint);
					}
					sfh.unlockCanvasAndPost(canvas);
				}
			}
			if (nowTime >= nextChapter.ChpofStart + sentence.StartTimeofThis)
			{
				nowChapter = nextChapter;
				rectLeft = (int) screenlocation[nowChapterFlag];
				rectRight = (int) (rectLeft + nowChapter.ChpofLast * density);
				nowHigh = chapterHigh(nowChapter.ChpofHigh);
				wrongPointX.clear();
				wrongResult.clear();
				initPosAndColor(nowChapter.ChpofLast, nowTime - sentence.StartTimeofThis - nowChapter.ChpofStart,nowTime);
				nowChapterFlag++;
				if (nowChapterFlag == sentence.mychapter.size()) 
				{
					nextChapter = new Chapter(500000, 0, 0, "");
				}
				else
				{
					nextChapter = sentence.mychapter.get(nowChapterFlag);
				}
				setColor(result);

				shader = new LinearGradient(rectLeft, 0, rectRight, 0,
						nowPointColors, nowPointPos, TileMode.CLAMP);
				pointPaint.setShader(shader);

				canvas = sfh.lockCanvas(new Rect(rectLeft, 0, rectRight,(int) height));

				if (!isKTV) 
				{
					canvas.drawText(
							nowChapter.text,
							rectLeft
									+ sentence
											.getChapterLast(nowChapterFlag - 1)
									* density / 4,// ����������Ӧ�������λ��
							nowHigh + 100, textPaint);
				}
				else
				{
					DrawWhiteLines(canvas);
				}
				// ������˸���ػ���ǰ��Բ�Ǿ��κ͵�ǰ��
				canvas.drawRoundRect(new RectF(rectLeft, (int) nowHigh,
						rectRight, (int) (nowHigh + rectHeight)), 8, 8,
						paint);

				// ���Բ�Ǿ���
				canvas.drawRoundRect(new RectF(rectLeft, (int) nowHigh,
						rectRight, (int) (nowHigh + rectHeight)), 8, 8,
						pointPaint);

				for (int i = 0; i < wrongPointX.size(); i++) 
				{
					canvas.drawRect(
							new Rect(
									(int) (rectLeft + (rectRight - rectLeft)
											* (wrongPointX.get(i) - 1.0f / nowChapter.ChpofLast * 40)),
									(int) (nowHigh + wrongResult.get(i)
											* pitchHeight),
									(int) (rectLeft + (rectRight - rectLeft)
											* wrongPointX.get(i)),
									(int) (nowHigh + pitchHeight + wrongResult
											.get(i) * pitchHeight)),
							wrongPaint);
				}
				sfh.unlockCanvasAndPost(canvas);
				isPrepared = true;
			}
		}
        preLineX=(int)((float)(nowTime-sentence.StartTimeofThis)/sentence.LastTimeofThis*width);
        return preLineX;
	}
	/**
	 * ��ʼ��ÿ���ֵ��λ����Ϣ����ɫ
	 * @param nowChapterLastTime ��ǰ�ֵĳ���ʱ��
	 * @param firstPosTime   Ŀǰ�ֵĿ�ʼʱ��
	 * @param nowTime  Ŀǰ���ŵ�ʱ��
	 */
	public void initPosAndColor(int nowChapterLastTime, int firstPosTime,
			int nowTime) {
		Log.i("1", firstPosTime + "");
		Log.i("2", nowChapterLastTime + "");

		nowPointPos = null;
		nowPointColors = null;
		nowPoint = 0;
		if (nowChapterLastTime % 40 == 0) {
			nowPointNum = nowChapterLastTime / 40 + 1;
		} else {
			nowPointNum = nowChapterLastTime / 40 + 2;
		}
		nowPointPos = new float[2 * nowPointNum];
		nowPointPos[0] = 1.0f / nowChapterLastTime * firstPosTime;
		nowPointPos[1] = nowPointPos[0];
		for (int i = 2; i < nowPointPos.length; i += 2) {
			nowPointPos[i] = nowPointPos[i - 1] + 1.0f / nowChapterLastTime
					* 40;
			nowPointPos[i + 1] = nowPointPos[i];
		}
		if (nowPointPos[nowPointPos.length - 1] > 1) {
			nowPointPos[nowPointPos.length - 2] = 1;
			nowPointPos[nowPointPos.length - 1] = 1;
		}
		nowPointColors = new int[2 * nowPointNum];
		for (int i = 0; i < nowPointColors.length; i++) {
			nowPointColors[i] = Color.TRANSPARENT;
		}
	}

	public void setColor(int result) {
		nowPoint++;
		if (result == 0)
		{
			if (nowPoint == 1)
			{
				nowPointColors[0] = Color.rgb(34, 139, 34);
			}
			else if (nowPoint != 0)
			{
				nowPointColors[(nowPoint - 1) * 2] = Color.rgb(34, 139, 34);
				nowPointColors[(nowPoint - 1) * 2 - 1] = Color.rgb(34, 139, 34);
		    } 
		} 
		else 
		{
			wrongPointX.add((Float)nowPointPos[2*nowPoint-1]);
			wrongResult.add((Integer)result);
		}
	}

	public void getWH() {
		width = getWidth();
		height = getHeight();
        
		pitchHeight = height / (max - min + 9);
	    if(isKTV){
	    	rectHeight=3;
	    	paint.setStyle(Style.FILL);
	    }
	    else{
	    	rectHeight=30;
	    	paint.setStyle(Style.STROKE);
	    }
	}
}
