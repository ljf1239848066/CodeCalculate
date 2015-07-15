package hichang.ourView;

import java.util.ArrayList;
import java.util.Currency;

import hichang.Song.Chapter;
import hichang.Song.Sentence;
import hichang.Song.Song;
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
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class CurveAndLrc extends SurfaceView implements android.view.SurfaceHolder.Callback {

	private SurfaceHolder sfh;

	private ArrayList<Sentence> songSen;// �����ľ�����

	private int pitchMax, pitchMin;// ��������ߺ��������

	public int sentenceFlag;// ��ǰ���ӵ��α�

	public int chapterFlag;// ��ǰ�ֵ��α�

	public int pointFlag;


	private int sentencesCount;// ��ǰ���ӵ���Ŀ

	private int curveX, curveY, curveW, curveH; // ���ߵ����Ͻ�,x,y����,�Լ���������ķ�Χ

	private int lrcX, lrcY, lrcW, lrcH; // ��ʵ����Ͻǣ�x,y���꣬�Լ���������ķ�Χ

	private boolean isKTV;

	private ModeType modeType;// ����ģʽ������ģʽ�����߾ۻ�ģʽ

	private boolean isFirstToSing;

	private int textSize, curveTextSize;// ����Ĵ�С

	public float firstSenStartX, firstSenStartY, secSenStartX, secSenStartY;// ��������һ��͵ڶ��ֵ���ʼ����

	public static final int MSG_SHOW_COLORBALLS = 1001;
	
	public static final int MSG_SHOW_TIMEHAND=1002;

	public static final int MSG_HIDE_TIMEHAND=1003;
	
	private Sentence firstSen, secSen;

	private final int MAGIN_TO_HALF_OF_HEIGHT = 10; // ������������м��С�ξ���

	private final int MAGIN_TO_CURVE = 5; 

	private final int DISTANCE_LRC_LEAD = 8;//�����Ⱦʱ�����ȵľ���
	
	private final int LINE_WIDTH=3;
	
	public float timeDensity;// ��λʱ�������������еĳ���

	private double[] chapRectStartX, chapRectEndX, chapRectStartY, chapRectEndY;// ��ǰ���ÿ���ֵ�Բ�Ǿ��ε���ʼ����

	private double[] chapTextLocaX, chapTextLocaY;// ��ǰ���ÿ���������������ڵ�����

	private float pitchHeight;// ��λ���������������еĸ߶�

	private float rectHeight;

	private float radiusX, radiusY;// Բ�Ǿ��ε�x,y���뾶

	private float[][][] pointsPos;

	private int[][][] pointsColor;

	public int[][][] pointsTime; // ÿ�������Խ���ʱ��

	private int[][] lrcChapPointNum;

	private Paint  lrcOriPaint,lrcChangePaint,curveOriPaint,curveLrcPaint,curveChangePaint,wrongPaint, linePaint ,clearPaint;
	
	private ArrayList<ArrayList<ArrayList<Integer>>> wrongPoints;

	private ArrayList<ArrayList<ArrayList<Integer>>> wrongPointResults;

	private ArrayList<ArrayList<Double>>  wrongPointLeft,wrongPointRight,wrongPointTop;
	
	private Shader[] shaders;
	
	private long songEndTime;

	private boolean isStart;
	
	private boolean isNext;
	
	private boolean isEnd;
	
	private boolean isChange;
	
	public CurveAndLrc(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		sfh = this.getHolder();
		sfh.addCallback(this);
		this.setZOrderOnTop(true);
		sfh.setFormat(PixelFormat.TRANSLUCENT);

		setKTV(false);
		
		lrcOriPaint = new Paint();
		lrcOriPaint.setColor(Color.WHITE);
		lrcOriPaint.setTextSize(textSize);
		
		lrcChangePaint = new Paint();
		lrcChangePaint.setTextSize(textSize);
		lrcChangePaint.setColor(Color.YELLOW);
		
		curveOriPaint = new Paint();
		curveLrcPaint = new Paint();
		curveOriPaint.setColor(Color.YELLOW);
		curveLrcPaint.setColor(Color.WHITE);
		curveLrcPaint.setTextSize(curveTextSize);
		
		curveChangePaint = new Paint();
		curveChangePaint.setColor(Color.YELLOW);
		wrongPaint = new Paint();
		wrongPaint.setColor(Color.rgb(255,127,0));
		
		linePaint=new Paint();
		linePaint.setColor(Color.rgb(255, 105, 180));
		clearPaint=new Paint();
		clearPaint.setColor(Color.TRANSPARENT);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * ��ʼ��
	 * 
	 * @param songSen
	 *            ��ǰ�����ľ�����
	 * @param pitchMax
	 *            ��ǰ�������������
	 * @param pitchMin
	 *            ��ǰ�������������
	 * @param handler
	 *            ������Ϣ
	 * @param type
	 *            �����������߾ۻ�ģʽ
	 */
	public void init(ArrayList<Sentence> songSen, int pitchMax, int pitchMin, ModeType type) {
		this.songSen = songSen;
		this.pitchMax = pitchMax;
		this.pitchMin = pitchMin;
		this.modeType = type;

		pointFlag = 0;
		chapterFlag = 0;
		sentenceFlag = 0;
		sentencesCount = this.songSen.size();
		
		isFirstToSing = true;
		isStart=false;
		isNext=false;
		isEnd=false;
		isChange=false;
		
		
		pointsColor=new int[sentencesCount][][];
		pointsPos=new float[sentencesCount][][];
		pointsTime=new int[sentencesCount][][];
		lrcChapPointNum=new int[sentencesCount][];
		wrongPoints = new ArrayList<ArrayList<ArrayList<Integer>>>();
		wrongPointResults = new ArrayList<ArrayList<ArrayList<Integer>>>();
		wrongPointLeft = new ArrayList<ArrayList<Double>>();
		wrongPointRight = new ArrayList<ArrayList<Double>>();
		wrongPointTop= new ArrayList<ArrayList<Double>>();
		sentenceFlag=0;
		initAllSentence();
	}

	/**
	 * ��������������λ�úʹ�С��Ϣ
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setCurveXYWH(int x, int y, int width, int height) {
		curveH = height;
		curveW = width;
		curveX = x;
		curveY = y;

		pitchHeight = curveH / (pitchMax - pitchMin + 1 + 8);
	}

	/**
	 * �������������λ�úʹ�С��Ϣ
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setLrcXYWH(int x, int y, int width, int height) {
		lrcH = height;
		lrcW = width;
		lrcX = x;
		lrcY = y;
	}
	/**
	 * ���û�����Ҫ���ŵľ���
	 * @param currentSen 
	 */
	public void setToSen(int currentSen){
		sentenceFlag=currentSen;
		chapterFlag=0;
		pointFlag=0;
		isChange=true;
		isEnd=false;
	}
	
	/**
	 * ���ݵ�ǰʱ����Ƶ�ǰ�����ߺ͸�������
	 * @param curentTime �������ŵĵ�ǰʱ��
	 * @param results Ҫ���ĵ㼯������
	 */
	public void drawCurveAndLrc(long currentTime, int[] results) {


		// �ж��Ƿ�ʼ��ͼ
		if(!isStart){
			if(currentTime>=songSen.get(0).StartTimeofThis-3000){
				isStart=true;
				initSentence(sentenceFlag);	
				Canvas canvas=sfh.lockCanvas();					
				drawOriLrc(canvas);
				drawOriCurve(canvas);
				sfh.unlockCanvasAndPost(canvas);
			}
		} 
		
		if (!isStart||isEnd) {
			return;
		}
		
		if(isChange){
			isChange = false ;
			Canvas canvas=sfh.lockCanvas();
			initSentence(sentenceFlag);
			clearSurf(canvas);
			drawOriLrc(canvas);
			drawOriCurve(canvas);
			sfh.unlockCanvasAndPost(canvas);
		}
		
		for (int i = 0; i < results.length; i++) {
			
			long curTime = currentTime - (results.length - i - 1) * 40;
			long curPointTime = firstSen.StartTimeofThis
					+ pointsTime[sentenceFlag][chapterFlag][pointFlag];

			if (curTime >= curPointTime) {
				if (pointFlag == 0
						&& pointsTime[sentenceFlag][chapterFlag][0] == 40) {
					setColor(sentenceFlag, chapterFlag, 0, results[i]);
				} else if (pointFlag == 1) {
					if (pointsPos[sentenceFlag][chapterFlag][0] < 40) {
						setColor(sentenceFlag, chapterFlag, 0, results[i]);
					}
					setColor(sentenceFlag, chapterFlag, 1, results[i]);
				} else {
					setColor(sentenceFlag, chapterFlag, pointFlag, results[i]);
				}
				pointFlag++;
			}

			if (pointFlag == pointsTime[sentenceFlag][chapterFlag].length) {
				chapterFlag++;
				pointFlag = 0;
			}
			
			if(chapterFlag == pointsTime[sentenceFlag].length){
				isNext=true;
				sentenceFlag++;
				chapterFlag = 0;	
				if(sentenceFlag == sentencesCount){
					break;
				}
				initSentence(sentenceFlag);
			}
		}
		
		Canvas canvas = sfh.lockCanvas();
		clearSurf(canvas);
		if(isNext){
			isNext=false;
			if(sentenceFlag<sentencesCount){
				drawOriCurve(canvas);
				drawOriLrc(canvas);
			} else {
				isEnd=true;
				clearSurf(canvas);
				sfh.unlockCanvasAndPost(canvas);
				return;
			}
		}
		drawLrcChange(sentenceFlag, chapterFlag , pointFlag , canvas);
		drawCurveChange(sentenceFlag, chapterFlag , pointFlag ,
				canvas);
		drawWrongPoint(canvas, sentenceFlag);
		drawLine(canvas, currentTime);
		sfh.unlockCanvasAndPost(canvas);
	}
	
	public void initSentence(int currentSen){
		firstSen=songSen.get(sentenceFlag);
		initWrongPoints(sentenceFlag);
		shaders=null;
		shaders=new Shader[firstSen.getChaptetCount()];
	}
	
	/**
	 * ������Ļ
	 * @param canvas
	 */
	public void clearSurf(Canvas canvas){
		//canvas.drawRect(preLeft, curveY, preLeft+LINE_WIDTH, curveY+curveH, clearPaint);
		canvas.drawColor(Color.TRANSPARENT,
				android.graphics.PorterDuff.Mode.CLEAR);
	}
	/**
	 * ��ʱ����
	 * @param canvas
	 * @param currentTime ��ǰʱ��
 	 */
	public void drawLine(Canvas canvas, long currentTime){
		int relaTime=(int) (currentTime-firstSen.StartTimeofThis);
		if(relaTime<firstSen.getChapterStart(0)){
			return;
		}
		float lineLeft=relaTime*timeDensity+curveX;
		canvas.drawRect(lineLeft, curveY, lineLeft+LINE_WIDTH, curveY+curveH, linePaint);
		
	}
	
	/**
	 * ����ǰ�ľ��ӵ�ԭ�䣨û�п�ʼ��Ⱦ�ľ��ӣ�
	 */
	public void drawOriLrc(Canvas canvas) {
		
		if (sentenceFlag < sentencesCount - 1) {
			secSen = songSen.get(sentenceFlag + 1);
		} else {
			secSen = null;
		}
		
		if (modeType == ModeType.MODE_PRACTICE && !isKTV) {
			firstSenStartX = lrcX + (lrcW
					- lrcOriPaint.measureText(firstSen.text))/2;
			firstSenStartY = lrcY + lrcH / 2 - MAGIN_TO_HALF_OF_HEIGHT;
			canvas.drawText(firstSen.text, firstSenStartX, firstSenStartY,
					lrcOriPaint);
			if (secSen != null) {
				secSenStartX = lrcX
						+ (lrcW - lrcOriPaint.measureText(secSen.text)) / 2;
				secSenStartY = lrcY +lrcH/ 2 + textSize + MAGIN_TO_HALF_OF_HEIGHT;
				canvas.drawText(secSen.text, secSenStartX, secSenStartY,
						lrcOriPaint);
			}
		} else {
			if (isFirstToSing) {
				isFirstToSing = false;
				// ��������ֳ�С��10�����Ҿ���
				if (firstSen.text.length() >= 10) {
					firstSenStartX = lrcX;
				} else {
					firstSenStartX = lrcX + lrcW / 2
							- lrcOriPaint.measureText(firstSen.text);
				}
				firstSenStartY = lrcY + lrcH / 2 - MAGIN_TO_HALF_OF_HEIGHT;
				canvas.drawText(firstSen.text, firstSenStartX, firstSenStartY,
						lrcOriPaint);

				if (secSen != null) {
					if (secSen.text.length() >= 10) {
						secSenStartX = lrcX + lrcW
								- lrcOriPaint.measureText(secSen.text);
					} else {
						secSenStartX = lrcX + lrcW / 2;
					}
					secSenStartY = lrcY + lrcH / 2 + textSize
							+ MAGIN_TO_HALF_OF_HEIGHT;
					canvas.drawText(secSen.text, secSenStartX, secSenStartY,
							lrcOriPaint);
				}

			} else {
				isFirstToSing = true;
				if (firstSen.text.length() >= 10) {
					firstSenStartX = lrcX + lrcW
							- lrcOriPaint.measureText(firstSen.text);
				} else {
					firstSenStartX = lrcX + lrcW / 2;
				}
				firstSenStartY = lrcY + lrcH / 2 + textSize
						+ MAGIN_TO_HALF_OF_HEIGHT;
				canvas.drawText(firstSen.text, firstSenStartX, firstSenStartY,
						lrcOriPaint);

				if (secSen != null) {
					if (secSen.text.length() >= 10) {
						secSenStartX = lrcX;
					} else {
						secSenStartX = lrcX + lrcW / 2
								- lrcOriPaint.measureText(secSen.text);
					}
					secSenStartY = lrcY + lrcH / 2 - MAGIN_TO_HALF_OF_HEIGHT;
					canvas.drawText(secSen.text, secSenStartX, secSenStartY,
							lrcOriPaint);
				}
			}
		}
	}

	/**
	 * ����ǰ�ľ��ӵı仯�еľ��ӣ���ʼ��Ⱦ�ľ��ӣ�
	 * 
	 * @param currentTime
	 *            �������ŵ��ĵ�ǰʱ��
	 */
	public void drawLrcChange(int currentSen, int currentChapter, int currentPoint,Canvas canvas) {
		
		Shader lrcChangeShader = setPaintShader(currentSen, currentChapter,currentPoint);	
		lrcChangePaint.setShader(lrcChangeShader);	
		canvas.drawText(firstSen.text, firstSenStartX, firstSenStartY,
				lrcChangePaint);
		if(secSen!=null){
			canvas.drawText(secSen.text, secSenStartX, secSenStartY, lrcOriPaint);
		}
	}

	/**
	 * ������Ⱦ
	 * @param currentChapter ��ǰ��
	 * @param currentPoint ��ǰ��
	 * @return
	 */
	public Shader setPaintShader(int currentSen, int currentChapter, int currentPoint) {
	
		Shader shader = null;
		float currentEndPosX;

		if (lrcChapPointNum[currentSen][currentChapter] == 0) {
			return setPaintShader(currentSen, currentChapter-1, lrcChapPointNum[currentSen][currentChapter-1]);
//			currentChapter -= 1;
//			currentPoint += pointsTime[currentSen][currentChapter].length;
//			setPaintShader(currentSen, currentChapter, currentPoint);
		} else {
			int preChapNum=currentChapter;
			for(int i=0;i<currentChapter;i++){
				if(lrcChapPointNum[currentSen][i]==0){
					preChapNum--;
				}
			}
			
			currentEndPosX = lrcChangePaint.measureText(firstSen.text.substring(0,
					preChapNum))
					+ lrcChangePaint.measureText(firstSen.getChapterText(currentChapter))
					* (float)currentPoint / lrcChapPointNum[currentSen][currentChapter]+DISTANCE_LRC_LEAD;
			
			shader = new LinearGradient(0, 0, firstSenStartX+currentEndPosX, 0,
					new int[] { Color.YELLOW, Color.WHITE }, new float[] {
							0.99f, 1 }, TileMode.CLAMP);
		}
		return shader;
	}
	/**
	 * ����ǰ���ӵ�ԭ���ߣ�û�п�ʼ����ľ��ӣ�
	 */
	public void drawOriCurve(Canvas canvas) {
		getChaptersLocation();
		
		for (int i = 0; i < firstSen.getChaptetCount(); i++) {
			int rectStartX=(int)(chapRectStartX[i]*curveW+curveX);
			int rectEndX=(int)(chapRectEndX[i]*curveW+curveX);
			int rectStartY=(int)(chapRectStartY[i]*curveH+curveY);
			int rectEndY=(int) (rectStartY+rectHeight);
			int textX=(int)(chapTextLocaX[i]*curveW+curveX);
			int textY=(int)(rectEndY+curveTextSize+MAGIN_TO_CURVE);
			canvas.drawRoundRect(new RectF(rectStartX,
					rectStartY, rectEndX,
					rectEndY), radiusX, radiusY, curveOriPaint);
			canvas.drawText(firstSen.getChapterText(i), textX,
					textY, curveLrcPaint);
		}
	}
	/**
	 * ����ǰ���ӵı仯���ߣ���ʼ���㣩
	 * 
	 * @param currentChapter
	 * @param currentPoint
	 * @param result
	 */
	public void drawCurveChange(int currentSen, int currentChapter, int currentPoint,Canvas canvas) {
		
		int curRectStartX=(int)(chapRectStartX[currentChapter]*curveW+curveX);
		int curRectEndX=(int)(chapRectEndX[currentChapter]*curveW+curveX);	
		shaders[currentChapter] = new LinearGradient(
				curRectStartX, 0,
				curRectEndX, 0,
				pointsColor[currentSen][currentChapter], pointsPos[currentSen][currentChapter],
				TileMode.CLAMP);
		
		int rectStartX0=(int)(chapRectStartX[0]*curveW+curveX);
		int rectEndX0=(int)(chapRectEndX[0]*curveW+curveX);
		int rectStartY0=(int)(chapRectStartY[0]*curveH+curveY);
		int rectEndY0=(int) (rectStartY0+rectHeight);
		int textX0=(int)(chapTextLocaX[0]*curveW+curveX);
		int textY0=(int)(rectEndY0+curveTextSize+MAGIN_TO_CURVE);
		
		canvas.drawRoundRect(new RectF(rectStartX0,
				rectStartY0, rectEndX0,
				rectEndY0), radiusX, radiusY, curveOriPaint);
		canvas.drawText(firstSen.getChapterText(0), textX0,
				textY0, curveLrcPaint);
		for (int i = 0; i <firstSen.getChaptetCount(); i++) {
			curveChangePaint.setShader(shaders[i]);
			int rectStartX=(int)(chapRectStartX[i]*curveW+curveX);
			int rectEndX=(int)(chapRectEndX[i]*curveW+curveX);
			int rectStartY=(int)(chapRectStartY[i]*curveH+curveY);
			int rectEndY=(int) (rectStartY+rectHeight);
			int textX=(int)(chapTextLocaX[i]*curveW+curveX);
			int textY=(int)(rectEndY+curveTextSize+MAGIN_TO_CURVE);
			canvas.drawRoundRect(new RectF(rectStartX,
					rectStartY, rectEndX,
					rectEndY), radiusX, radiusY, curveChangePaint);
			canvas.drawText(firstSen.getChapterText(i), textX,
					textY, curveLrcPaint);
		}
	}
	
	public void clearColor(int fromSen){
		for(int i=fromSen;i<songSen.size();i++){
			for(int k=0;k<pointsColor[i].length;k++){
				for(int j=0;j<pointsColor[i][k].length;j++){
					pointsColor[i][k][j]=Color.YELLOW;
				}
			}
		}
	}
	
	
	public void drawWrongPoint(Canvas canvas, int currentSen){
		for(int i=0;i<songSen.get(currentSen).getChaptetCount();i++){
			for(int j=0;j<wrongPointLeft.get(i).size();j++){
				int left=(int)(wrongPointLeft.get(i).get(j).doubleValue()*curveW)+curveX;
				int right=(int)(wrongPointRight.get(i).get(j).doubleValue()*curveW)+curveX;
				int top=(int)(wrongPointTop.get(i).get(j).doubleValue()*curveH)+curveY;
				int bottom=(int) (top+rectHeight);
				canvas.drawRect(new Rect(left, top, right, bottom), wrongPaint);
			}
		}
	}
	
	public void initWrongPoints(int currentSen){
		wrongPointLeft.clear();
		wrongPointRight.clear();
		wrongPointTop.clear();
		
		for(int i=0;i<songSen.get(currentSen).getChaptetCount();i++){
			wrongPointLeft.add(new ArrayList<Double>());
			wrongPointRight.add(new ArrayList<Double>());
			wrongPointTop.add(new ArrayList<Double>());
			wrongPoints.get(currentSen).get(i).clear();
			wrongPointResults.get(currentSen).get(i).clear();
		}
		
	}
	/**
	 * ��ʼ����ǰ���������������Ҫ���ĵ��λ�ú���ɫ
	 * @param sentenceFlag ���ӵ��α�
	 */
	public void initAllSentence() {
		for (int sentenceFlag = 0; sentenceFlag < songSen.size(); sentenceFlag++) {
			Sentence firstSen = songSen.get(sentenceFlag);
			int chapNum = firstSen.getChaptetCount();

			pointsColor[sentenceFlag] = new int[chapNum][];
			pointsPos[sentenceFlag] = new float[chapNum][];
			pointsTime[sentenceFlag] = new int[chapNum][];
			lrcChapPointNum[sentenceFlag] = new int[chapNum];
			wrongPoints.add(new ArrayList<ArrayList<Integer>>());
			wrongPointResults.add(new ArrayList<ArrayList<Integer>>());
			for (int i = 0; i < chapNum; i++) {
				wrongPoints.get(sentenceFlag).add(new ArrayList<Integer>());
				wrongPointResults.get(sentenceFlag).add(
						new ArrayList<Integer>());

				int firstPointTime = 40 - (firstSen.StartTimeofThis + firstSen
						.getChapterStart(i)) % 40;
				int chapPointNum;
				if (firstPointTime == 0) {
					if ((firstSen.getChapterLast(i) - firstPointTime) % 40 == 0) {
						chapPointNum = (firstSen.getChapterLast(i)) / 40;
					} else {
						chapPointNum = (firstSen.getChapterLast(i)) / 40 + 1;
					}
				} else {
					if ((firstSen.getChapterLast(i) - firstPointTime) % 40 == 0) {
						chapPointNum = (firstSen.getChapterLast(i) - firstPointTime) / 40 + 1;
					} else {
						chapPointNum = (firstSen.getChapterLast(i) - firstPointTime) / 40 + 2;
					}
				}

				pointsPos[sentenceFlag][i] = new float[chapPointNum * 2];
				pointsColor[sentenceFlag][i] = new int[chapPointNum * 2];
				pointsTime[sentenceFlag][i] = new int[chapPointNum];
				if (firstPointTime == 0) {
					pointsTime[sentenceFlag][i][0] = firstSen
							.getChapterStart(i) + 40;
					pointsPos[sentenceFlag][i][0] = 1.0f / firstSen
							.getChapterLast(i) * 40;
					pointsPos[sentenceFlag][i][1] = pointsPos[sentenceFlag][i][0];

				} else {
					pointsTime[sentenceFlag][i][0] = firstSen
							.getChapterStart(i) + firstPointTime;
					pointsPos[sentenceFlag][i][0] = 1.0f
							/ firstSen.getChapterLast(i) * firstPointTime;
					pointsPos[sentenceFlag][i][1] = pointsPos[sentenceFlag][i][0];
				}
				for (int j = 2; j < 2 * chapPointNum; j += 2) {
					pointsTime[sentenceFlag][i][j / 2] = pointsTime[sentenceFlag][i][j / 2 - 1] + 40;
					pointsPos[sentenceFlag][i][j] = 1.0f
							/ firstSen.getChapterLast(i) * 40
							+ pointsPos[sentenceFlag][i][j - 1];
					pointsPos[sentenceFlag][i][j + 1] = pointsPos[sentenceFlag][i][j];
				}
				pointsPos[sentenceFlag][i][2 * chapPointNum - 1] = 1;
				pointsPos[sentenceFlag][i][2 * chapPointNum - 2] = 1;
				pointsTime[sentenceFlag][i][chapPointNum - 1] = firstSen
						.getChapterStart(i) + firstSen.getChapterLast(i);

				for (int j = 0; j < 2 * chapPointNum; j += 2) {
					pointsColor[sentenceFlag][i][j] = Color.YELLOW;
					pointsColor[sentenceFlag][i][j + 1] = Color.YELLOW;
				}
			}

			// �������ÿ���ֵĵ���,"~"�ĵ����ӵ�ǰ��һ������
			for (int i = chapNum - 1; i >= 0; i--) {
				lrcChapPointNum[sentenceFlag][i] += pointsTime[sentenceFlag][i].length;
				if (firstSen.getChapterText(i).equals("~")) {
//					lrcChapPointNum[sentenceFlag][i - 1] = lrcChapPointNum[sentenceFlag][i];
					lrcChapPointNum[sentenceFlag][i] = 0;
				}
			}
		}
	}

	/**
	 * ����Ӧ��������ɫ
	 * 
	 * @param currentChapter
	 * @param currentPoint
	 * @param result
	 */
	public void setColor(int currentSen, int currentChapter, int currentPoint, int result) {
		if(result==10){
			return;
		}
		else if(result==0){
			if (currentPoint == 0) {
				pointsColor[currentSen][currentChapter][0] = Color.GREEN;
			} else {
				pointsColor[currentSen][currentChapter][2 * currentPoint - 1] = Color.GREEN;
				pointsColor[currentSen][currentChapter][2 * currentPoint] = Color.GREEN;
			}
		} else {
			
			int rectStartX=(int)(chapRectStartX[currentChapter]*curveW+curveX);
			int rectStartY=(int)(chapRectStartY[currentChapter]*curveH+curveY);
			
			double top =  (rectStartY-curveY - result
					* pitchHeight)/(double)curveH;
			double right =  (rectStartX-curveX
					+  timeDensity*pointsPos[currentSen][currentChapter][2 * currentPoint]
					* firstSen.getChapterLast(currentChapter))/(double)curveW;
			double left;
			if (currentPoint == 0) {
				left =  (rectStartX-curveX)/(double)curveW;
			} else {
				left = (rectStartX-curveX+timeDensity*pointsPos[currentSen][currentChapter][2 * (currentPoint - 1)]
						* firstSen.getChapterLast(currentChapter))/(double)curveW;
			}
			
			wrongPoints.get(currentSen).get(currentChapter).add(currentPoint);
			wrongPointResults.get(currentSen).get(currentChapter).add(result);
			
			wrongPointTop.get(currentChapter).add(top);
			wrongPointLeft.get(currentChapter).add(left);
			wrongPointRight.get(currentChapter).add(right);
		}
		
	}

	/**
	 * �õ���������ÿ���ֵ�Բ�Ǿ��κ��ֵ�λ��
	 */
	public void getChaptersLocation() {
		chapRectStartX = null;
		chapRectStartY = null;
		chapRectEndX = null;
		chapRectEndY = null;
		chapTextLocaX = null;
		chapTextLocaY = null;
		chapRectStartX = new double[firstSen.getChaptetCount()];
		chapRectStartY = new double[firstSen.getChaptetCount()];
		chapRectEndX = new double[firstSen.getChaptetCount()];
		//chapRectEndY = new double[firstSen.getChaptetCount()];
		chapTextLocaX = new double[firstSen.getChaptetCount()];
		//chapTextLocaY = new double[firstSen.getChaptetCount()];

		timeDensity = (float) curveW
				/ firstSen.LastTimeofThis;
		for (int k = 0; k < chapRectStartX.length; k++) {
			chapRectStartX[k] = timeDensity * firstSen.getChapterStart(k)/(double)curveW;
					
			chapRectEndX[k] = chapRectStartX[k] + (timeDensity
					* firstSen.getChapterLast(k))/ (double)curveW;
			chapRectStartY[k] = (curveH - pitchHeight
					* (firstSen.getChapterHigh(k) - pitchMin + 1 + 4)) /(double)curveH;
			//chapRectEndY[k] = chapRectStartY[k] + rectHeight;
			chapTextLocaX[k] = chapRectStartX[k]
					+ (chapRectEndX[k] - chapRectStartX[k]) / 2 - curveTextSize / (double)(2*curveW);
//			if (chapRectEndY[k] + curveTextSize + MAGIN_TO_CURVE >= curveY + curveH) {
//				chapTextLocaY[k] = chapRectStartY[k] - MAGIN_TO_CURVE/(double)curveH;
//			} else {
//				chapTextLocaY[k] = chapRectStartY[k] + (curveTextSize + MAGIN_TO_CURVE)/(double)curveH;
//			}
		}
	}

	/**
	 * �����Ƿ���KTVģʽ
	 * 
	 * @param isKtv
	 *            �Ƿ���KTV
	 */
	public void setKTV(boolean isKtv) {
		if(this.isKTV!=isKtv||this.modeType==ModeType.MODE_PARTY&&isStart){
			isChange=true;
			this.isKTV = isKtv;
		}
		if (isKTV) {
			radiusX = 1.5f;
			radiusY = 1.5f;
			rectHeight = 3;
			curveTextSize=10;
			textSize=70;
		} else {
			radiusX = 15;
			radiusY = 15;
			rectHeight = 30;
			if(modeType!=ModeType.MODE_PRACTICE){
				textSize=70; 
			} else {
				textSize=50;
			}
			curveTextSize=30;
		}
		if(firstSen!=null){
			timeDensity = (float) curveW
					/ firstSen.LastTimeofThis;
		}
	}
	
	public boolean isKTV() {
		return this.isKTV;
	}
	
	
	/**
	 * ����
	 */
	public void clearTotal(){
		Canvas canvas = sfh.lockCanvas();
		canvas.drawColor(Color.TRANSPARENT,
				android.graphics.PorterDuff.Mode.CLEAR);
		sfh.unlockCanvasAndPost(canvas);
	}
	/**
	 * ģʽ��ö��
	 * @author qin
	 *
	 */
	public enum ModeType {
		MODE_PRACTICE, MODE_SING, MODE_PARTY
	}
}
