package hichang.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class HelpActivity extends Activity{

	private ImageView help_fpoint,help_spoint,help_tpoint;
	private ImageView help_border;
	private Handler handler;
	private Gallery g;
	private Timer timer;
	Animation help_reduceAnimation,help_anifloat;
	public static Bitmap[] mThumbIds;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
        help_anifloat = AnimationUtils.loadAnimation(this, R.anim.help_float);
        help_border = (ImageView)findViewById(R.id.help_float);
        g = (Gallery) findViewById(R.id.help_gallery);
        help_reduceAnimation = AnimationUtils.loadAnimation(this, R.anim.help_reduce);
        help_reduceAnimation.setFillAfter(true);
        help_fpoint = (ImageView)findViewById(R.id.help_fpoint);
        help_spoint = (ImageView)findViewById(R.id.help_spoint);
        help_tpoint = (ImageView)findViewById(R.id.help_tpoint);
		final ImageAdapter a = new ImageAdapter(this);
		timer = new Timer();
		mThumbIds = new Bitmap[3];
		mThumbIds[0] = BitmapFactory.decodeResource(getResources(), R.drawable.help_first);
		mThumbIds[1] = BitmapFactory.decodeResource(getResources(), R.drawable.help_second);
		mThumbIds[2] = BitmapFactory.decodeResource(getResources(), R.drawable.help_third);
        handler = new Handler()
        {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what == 3) 
				{
					try {
						g.setAdapter(a);
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(HelpActivity.this, e.toString(), 1000).show();
					}
				}
				if(msg.what == 1)
				{
					help_border = null;
					help_fpoint = null;
					help_spoint = null;
					help_tpoint = null;
					g = null;
					Intent intent = new Intent();
					intent.setClass(HelpActivity.this, MainActivity.class);
					intent.putExtra("help", 1);
					startActivity(intent);
					HelpActivity.this.finish();
				}
			}
        	
        };
		help_border.startAnimation(help_anifloat);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(3);
			}
		}, 1200);
		g.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg2 == 0)
				{
					help_spoint.setVisibility(ImageView.INVISIBLE);
					help_fpoint.setVisibility(ImageView.VISIBLE);
				}
				else if(arg2 == 1)
				{
					help_fpoint.setVisibility(ImageView.INVISIBLE);
					help_tpoint.setVisibility(ImageView.INVISIBLE);
					help_spoint.setVisibility(ImageView.VISIBLE);
				}
				else if(arg2 == 2)
				{
					help_spoint.setVisibility(ImageView.INVISIBLE);
					help_tpoint.setVisibility(ImageView.VISIBLE);
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			mThumbIds[0].recycle();
			mThumbIds[1].recycle();
			mThumbIds[2].recycle();
			Toast.makeText(this, "·µ»Ø", 1000).show();
			g.destroyDrawingCache();
			g.setVisibility(Gallery.INVISIBLE);
			help_fpoint.setVisibility(ImageView.INVISIBLE);
			help_spoint.setVisibility(ImageView.INVISIBLE);
			help_tpoint.setVisibility(ImageView.INVISIBLE);
			help_border.startAnimation(help_reduceAnimation);
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler.sendEmptyMessage(1);
				}
			}, 1200);
		}
		return true;
	}

	public static class ImageAdapter extends BaseAdapter {
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	ImageView imageView;
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new Gallery.LayoutParams(1910,1065));
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(18, 18, 18, 18);
                imageView.setImageBitmap(mThumbIds[position]);
                return imageView;
        }

        private Context mContext;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer.cancel();
		help_anifloat.cancel();
		help_reduceAnimation.cancel();
	}
	
}
