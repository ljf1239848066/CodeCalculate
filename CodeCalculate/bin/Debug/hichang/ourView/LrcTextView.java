package hichang.ourView;


import hichang.Song.Chapter;
import hichang.Song.Sentence;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class LrcTextView extends SurfaceView implements Callback {
   	

    
	// ע�ͣ������ⲿ����
	// drawText��������ʵģ���һ֡����������ʣ��������ļ�֡��ÿһ֡���䡣
	// clear �����������ؼ���գ��ڲ�������һ��֮�����

	SurfaceHolder sfh;
	/**
	 *  ��һ�仰�͵ڶ��仰
	 */
    public Sentence firstSentence, secSentence;
	/**
	 *  �����С
	 */
	int size = 50;
	/**
	 *  ��һ��Ļ���
	 */
	Paint paint;
	/**
	 * �ڶ���Ļ���
	 */
	Paint newPaint;
	/**
	 *  ��Ӱ
	 */
	LinearGradient shader;
	/**
	 *  ����
	 */
	Canvas canvas;
	/**
	 *  �ؼ��߶�
	 */
	public int height,width;
	/**
	 *  ��ǰ�ֵı�ʶ
	 */
	public int chapterFlag;
	/**
	 *  ��ǰ�ֺ���һ����
	 */
	Chapter nowChapter, nextChapter,lastChapter;
	/**
	 *  ��仰�е��ֵ���Ŀ
	 */
	int chapterSize;
	/**
	 *  ��Ӱ�Ľ�����
	 */
	public float nowEndPos,preEndPos;
	/**
	 *  ��ǰ�ֵĿ��
	 */
	float nowChapterLength;
    /**
     * ��ʼ����
     */
	float firstStartX,secStartX,firstStartY,secStartY;
	boolean isKTV;
	/**
	 * �Ƿ��ǵ�һ��
	 */
	boolean isFirst=false;
	public int songLength;
	
	public LrcTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		sfh = this.getHolder();
		sfh.addCallback(this);
		this.setZOrderOnTop(true);
		sfh.setFormat(PixelFormat.TRANSPARENT);

		size = 50;
		// ��һ��Ļ���
		paint = new Paint();
		// �ڶ���Ļ���
		newPaint = new Paint();
		paint.setTextSize(size);
		paint.setColor(Color.WHITE);
		newPaint.setColor(Color.WHITE);
		newPaint.setTextSize(size);
        
		firstSentence=new Sentence();
		secSentence=new Sentence();
	}
	

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
        getWH();
        isFirst=false;
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		getWH(); 
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	
	public void init(int songlength){
		this.songLength=songlength;
		isFirst=false;
	}
	
    //������Ӧʱ�̵ľ���
	public void DrawSentence(float nowtime) {
		setPaintShader(nowtime);
		paint.setShader(shader);
		if(!isKTV){
			canvas = sfh.lockCanvas(new Rect(0,0,width,height/2));
		} else {
			if(isFirst){
				canvas = sfh.lockCanvas(new Rect(0,0,width,height/2));
			}
			else{
				canvas = sfh.lockCanvas(new Rect(0,height/2,width,height)); 
			}
		}
		canvas.drawText(firstSentence.text, firstStartX, firstStartY, paint);
		sfh.unlockCanvasAndPost(canvas);
	}
	
    //���û���
	public void setPaintShader(float nowtime) {

		if (nowtime-firstSentence.StartTimeofThis >= nextChapter.ChpofStart) {
			nowChapter = nextChapter;
			nowChapterLength = paint.measureText(nowChapter.text);
			
            preEndPos = paint.measureText(firstSentence.text.substring(0,
    				chapterFlag));
			nowEndPos=preEndPos;
			
		    shader = new LinearGradient(firstStartX, 0, firstStartX+nowEndPos, 0, new int[] {
					Color.YELLOW, Color.WHITE }, new float[] { 0.999f, 1 },
					TileMode.CLAMP);
		
			chapterFlag++;
			if (chapterFlag < chapterSize) {
				nextChapter = firstSentence.anotherChapters.get(chapterFlag);
			}
			else{
				if(secSentence!=null){
					nextChapter = new Chapter(secSentence.StartTimeofThis, 0, 0, "");
				}
				else{
					nextChapter = new Chapter(songLength, 0, 0, "");
				}
			}
		}
		if(nowtime<=firstSentence.StartTimeofThis+nowChapter.ChpofStart+nowChapter.ChpofLast){
			if(nowtime<=firstSentence.StartTimeofThis+lastChapter.ChpofStart+lastChapter.ChpofLast){
				nowEndPos = (nowtime-firstSentence.StartTimeofThis-nowChapter.ChpofStart) / nowChapter.ChpofLast 
						* nowChapterLength+preEndPos;
			}
			shader = new LinearGradient(firstStartX, 0, firstStartX+nowEndPos, 0, new int[] {
					Color.YELLOW, Color.WHITE }, new float[] { 0.99f, 1 },
					TileMode.CLAMP);
		}
	}
    
	public void myDrawText(Sentence sentence1,Sentence sentence2, boolean isktv)
	{

		firstSentence = sentence1;
		secSentence = sentence2;
        
		chapterFlag = 0;
		chapterSize = firstSentence.anotherChapters.size();
		nowChapter =null;
		nextChapter = firstSentence.anotherChapters.get(0);
		lastChapter = firstSentence.anotherChapters.get(chapterSize-1);
		nowEndPos = 0;
		preEndPos=0;
		shader=null;
		paint.setShader(shader);
		
		isKTV=isktv;
		//רҵģʽ
		if(!isKTV){
			size=50;
			paint.setTextSize(size);
			newPaint.setTextSize(size);
			
			Clear();
			Clear();
			firstStartX=(width-newPaint.measureText(firstSentence.text))/2;
			firstStartY=height/2-5;
			// �����Ȼ����仰
			canvas = sfh.lockCanvas();
			canvas.drawText(firstSentence.text, firstStartX, firstStartY, newPaint);
			if (secSentence != null) {
				secStartX=(width-newPaint.measureText(secSentence.text))/2;
				secStartY=height-5;
				canvas.drawText(secSentence.text, secStartX, secStartY, newPaint);
			}
			sfh.unlockCanvasAndPost(canvas);
		}
		//KTVģʽ
		else if(isKTV){
			size=65;
			paint.setTextSize(size);
			newPaint.setTextSize(size);
			
			isFirst=!isFirst;
			if(isFirst){
				ClearSec();
				ClearSec();
				//��������ֳ�С��8�����Ҿ���
				if(firstSentence.text.length()>=8){
					firstStartX=0;
				}else {
					firstStartX=width/2-newPaint.measureText(firstSentence.text);
				}
				
				firstStartY=height / 2-8;
				// �����Ȼ����仰
				canvas = sfh.lockCanvas();
				canvas.drawText(firstSentence.text, firstStartX, firstStartY, newPaint);
				if (secSentence != null) {
					//��������ֳ�С��10���������
					if(secSentence.text.length()>=8){
						secStartX=width-newPaint.measureText(secSentence.text);
					} else {
						secStartX=width/2;
					}
					secStartY=height/2+size+8;
					canvas.drawText(secSentence.text, secStartX, secStartY, newPaint);
				}
				sfh.unlockCanvasAndPost(canvas);
			} else {
				ClearFirst();
				ClearFirst();

				// �����Ȼ����仰
				canvas = sfh.lockCanvas();
				if (secSentence != null) {
					//��������ֳ�С��10�����Ҿ���
					if(secSentence.text.length()>=8){
						secStartX=0;
					} else {
						secStartX=width/2-newPaint.measureText(secSentence.text);
					}
					secStartY = height / 2 - 8;
					canvas.drawText(secSentence.text, secStartX, secStartY,
							newPaint);
				}
				//��������ֳ�С��10���������
				if(firstSentence.text.length()>=8){
					firstStartX=width-newPaint.measureText(firstSentence.text);
				}else {
					firstStartX=width/2;
				}
				firstStartY = height / 2 + size + 8;
				canvas.drawText(firstSentence.text, firstStartX, firstStartY,
						newPaint);
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	// �������� 
	//
	public void Clear() {
		Canvas canvas = sfh.lockCanvas();
		canvas.drawColor(Color.TRANSPARENT,
				android.graphics.PorterDuff.Mode.CLEAR);
		sfh.unlockCanvasAndPost(canvas);
	}
	public void ClearFirst(){
		Canvas canvas=sfh.lockCanvas(new Rect(0,0,width,height/2));
		canvas.drawColor(Color.TRANSPARENT,
				android.graphics.PorterDuff.Mode.CLEAR);
		sfh.unlockCanvasAndPost(canvas);
	}
	public void ClearSec(){
		Canvas canvas=sfh.lockCanvas(new Rect(0,height/2,width,height));
		canvas.drawColor(Color.TRANSPARENT,
				android.graphics.PorterDuff.Mode.CLEAR);
		sfh.unlockCanvasAndPost(canvas);
	}
	
	public void getWH(){
		height=getHeight();
		width=getWidth();
	}
}
