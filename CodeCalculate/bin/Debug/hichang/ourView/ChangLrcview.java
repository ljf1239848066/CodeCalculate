package hichang.ourView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import hichang.Song.*;  

public class ChangLrcview extends SurfaceView implements Callback {
	/*
	 * �������ĸ����ʾ
	 * ����mydrawtext�����Զ������
	 * drawtex����ֻ������ǰ��� �����
	 */
	
	SurfaceHolder sfh;
	float time=0;

	public ChangLrcview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		sfh=this.getHolder();
		sfh.addCallback(this);
		this.setZOrderOnTop(true);
		sfh.setFormat(PixelFormat.TRANSPARENT);
	}
	
	public ChangLrcview(Context context,AttributeSet attrs){
		super(context,attrs);
		sfh=this.getHolder();
		sfh.addCallback(this);
		this.setZOrderOnTop(true);
		sfh.setFormat(PixelFormat.TRANSPARENT);
	}
	
	public ChangLrcview(Context context){
		super(context);
		sfh=this.getHolder();
		sfh.addCallback(this);
		this.setZOrderOnTop(true);
		sfh.setFormat(PixelFormat.TRANSPARENT);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unused")
	public void drawText(Sentence sentence1,Sentence sentence2,int interval)
	{
        int size = 30;
	    Paint paint = new Paint();
		paint.setTextSize(size);//���û��ʴ�С��Ĭ��Ϊ30
		Paint newpaint = new Paint();
		newpaint.setTextSize(size);
		newpaint.setColor(Color.WHITE);
		int wordWidth = (int)paint.measureText(sentence1.text);
		while(wordWidth >= getWidth())//������峤�ȴ��������ؼ����ȣ���С���ʴ�С
		{
			size -=5;
			paint.setTextSize(size);
			wordWidth = (int)paint.measureText(sentence1.text);
		}
		
		setPaintShader(paint,sentence1,interval,wordWidth);//���û��ʵ���Ӱ
		int x = 0;//�������忿���
		int y = getHeight()/2;//�������ϻ�
		int x1 = getWidth()-(int)newpaint.measureText(sentence2.text);//�ڶ��仰����ʼλ��
		if(sentence2!=null)
		{
			Canvas canvas = sfh.lockCanvas();
			canvas.drawText(sentence1.text, x, y, paint);
			canvas.drawText(sentence2.text, x1, y+40, newpaint);//�ѵڶ����ֻ��ڵ�һ�����·�40���ص�λ��
			sfh.unlockCanvasAndPost(canvas);
		}
		else{
			Canvas canvas = sfh.lockCanvas();
			canvas.drawText(sentence1.text, x, y, paint);
			sfh.unlockCanvasAndPost(canvas);
		}
	}
	
	private void setPaintShader(Paint paint,Sentence sentence,float interval,float wordwidth){
		float density;//ÿ����Ӧ��ǰ��������
		density= wordwidth/sentence.LastTimeofThis;
		
		LinearGradient shader = new LinearGradient(0, //��ʼλ��=����ľ�����ʼλ��
				                        getHeight()/20, time+1, getHeight()/20,
				                        //���û��ʵ��յ���Ӱλ��
				                        new int[]{Color.BLUE,Color.WHITE}, new float[]{0.99f,1},
				                        TileMode.CLAMP);
		time=time+interval*density;//���ӳ���
		paint.setShader(shader);
	}
	class MyThread implements Runnable{
//		�������߳����Զ���ͼ
		private Sentence sentence1;
		private Sentence sentence2;
		private int interval;
		private int runtime=0;
			public MyThread(Sentence sentence1,Sentence sentence2,int interval)
			{
				this.sentence1 = sentence1;
				this.sentence2 = sentence2;
				this.interval = interval;
			}
			public void run() {
				// TODO Auto-generated method stub
				Clear();
				while(runtime <= sentence1.LastTimeofThis)
				{
					drawText(sentence1,sentence2, interval);
				
				   try {
					Thread.sleep(interval);
					runtime+=interval;
				    }  catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
			
		}
	
	public void myDrawText(Sentence sentence1,Sentence sentence2,int interval){
		Clear();
		Clear();//�����������������˫������
		MyThread thread = new MyThread(sentence1,sentence2,interval);
		Thread td = new Thread(thread);
		td.start();
	}
	
	public void Clear(){
		Canvas canvas = sfh.lockCanvas();
		canvas.drawColor(Color.TRANSPARENT,android.graphics.PorterDuff.Mode.CLEAR);
		sfh.unlockCanvasAndPost(canvas);
		time=0;
	}

}
