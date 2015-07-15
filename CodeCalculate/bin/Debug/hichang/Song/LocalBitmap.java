package hichang.Song;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LocalBitmap {

	/**
	 * ���ر���ͼƬ
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis); // /����ת��ΪBitmapͼƬ

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
