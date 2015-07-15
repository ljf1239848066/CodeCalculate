package hichang.database;

import hichang.Song.Song;
import hichang.database.DataBase;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * SongDA<br/>
 * �����ݿ���ʵ���
 */
public class SongDA {
	private static DataBase databaseHelper;
	private static SQLiteDatabase db;
	public SongDA() {   	
	}	
	public static void connectDB(DataBase nowDB){
		databaseHelper=nowDB;
	}
	///���������ɾ���
		/**
		 * ��Ӹ���
		 * @param name   ������
		 * @param simplename ����ÿ��������ĸ
		 * @param singerOne һ���ݳ��ߵ�
		 * @param singerTwo �����ݳ��ߵ�����
		 * @param clicks    �������
		 * @param isAvailable �Ƿ����
		 */
	public static void addSong(String name, String simplename, String singer1,String singer2,int clicks, int isAvailable)
	{
		db = databaseHelper.getWritableDatabase(); 
		db.beginTransaction();
		try 
		{
			db.execSQL("insert into SONG(Name,SimpleName,Singer1,Singer2,Clicks,IsAvailable) values(?,?,?,?,?,?)", 
					new Object[] { name, simplename,singer1,singer2,isAvailable});
			db.setTransactionSuccessful();
		}
		catch (Exception e)
		{ }
		db.endTransaction();
	}				
		
	/**
	 * ɾ������
	 * @param name ������
	 */
	public static void deleteSong(String name)
	{
		db = databaseHelper.getWritableDatabase(); 
		db.execSQL("delete from SONG where Name=?", new Object[]{name});
		db.close();
	}			
	/**
	 * ������ȫ���������Ҹ��������ظ����б�
	 * @param subname ����ȫ������
	 * @return  �����б�
	 */
	public static List<Song> querySongBySubname(String subname)
	{
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("select * from SONG where Name like '"+subname+"%' order by Name", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}		
	/**
	 * ����������д���Ҹ��������ظ����б�
	 * @param simplename �������ļ�д
	 * @return �����б�
	 */
	public static List<Song> querySongBySimpleName(String simplename)
	{
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("select * from SONG where SimpleName like '"+simplename+"%' order by Name DESC", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}			
	/**
	 * ��������������ߵ�˳�򷵻ظ����б�
	 * @return �����б�
	 */
	public static List<Song> querySongByClicks()
	{
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("select * from SONG order by Clicks DESC", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}	
	/**
	 * ����������������û��������֮�͵ĸߵ�˳�򷵻ظ����б�
	 * @return �����б�
	 */
	public static List<Song> querySongByTwoClicks()
	{
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("SELECT SONG.SongID,SONG.Name,SONG.SimpleName,SONG.Singer1,SONG.Singer2,SONG.Clicks+USER.Clicks Clicks,SONG.IsAvailable FROM SONG,USER WHERE USER.RecordName IS NULL AND SONG.SongID=USER.SongID ORDER BY SONG.Clicks+USER.Clicks DESC", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}
	/**
	 * ���û���������ߵ�˳�򷵻ظ����б�
	 * @return �����б�
	 */
	public static List<Song> querySongByUserClicks()
	{
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("SELECT SONG.SongID,SONG.Name,SONG.SimpleName,SONG.Singer1,SONG.Singer2,USER.Clicks,SONG.IsAvailable FROM SONG,USER WHERE SONG.SongID=USER.SongID AND USER.RecordName ISNULL ORDER BY USER.Clicks DESC", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}			
	/**
	 * ���ݸ��������Ҹ���ID
	 * @param name ������
	 * @return �����б�
	 */
	public static List<Song> querySongBySingerName(String name)
	{
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("select * from SONG where Singer1 like '"+name+"' or Singer2 like '"+name+"%' or Singer2 like '%"+name+"'", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}		
	/**
	 * �������и���
	 * @return �����б�
	 */
	public static List<Song> findAllSong()
	{
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("select * from SONG order by Clicks desc", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}
	/**
	 * �ӵ�ǰ�����б��м�����������д�ĸ������б�
	 * @param songlist   ��ǰlist
	 * @param simplename ������д
	 * @return
	 */
	public static List<Song> findSubSongList(List<Song> songlist,String simplename)
	{
		List<Song> data=new ArrayList<Song>();
		if(songlist.isEmpty())return null;
		for(int i=0;i<songlist.size();i++)
		{
			if(songlist.get(i).getSimpleName().indexOf(simplename)==0)
				data.add(songlist.get(i));
		}
		return data;
	}
	/**
	 * ��ȡ��������
	 * @return
	 */
	public static int getSongCount()
	{
		int count=0;
		db=databaseHelper.getReadableDatabase();
		Cursor result=db.rawQuery("SELECT count(SongID) from SONG", null);
		result.moveToFirst();
		if(result.isNull(0))count= 0;
		else count=result.getInt(0);
		if(result!=null)result.close();
		db.close();
		return count;
	}
	/**
	 * ��˳����ȡʮ�׸���
	 * @param page ҳ����
	 * @return
	 */
	public static List<Song> findTenSong(int page)
	{
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("select * from SONG where SongID not in (select SongID from SONG limit "
				+page+"*10-10) limit 10", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}
	/**
	 * �ø�������д��������˳����ȡʮ�׸���
	 * @param simplename  ��������д
	 * @param page ҳ����
	 * @return
	 */
	public static List<Song> findTenSongBySimpleName(String simplename,int page)
	{
		if(page<1)return null;
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("select * from SONG where SimpleName like '"+simplename+"%' and " +
				"SongID not in (select SongID from SONG where SimpleName like '"+simplename+"%' " +
						"limit "+page+"*10-10) limit 10", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}
	/**
	 * ����������������û��������֮�͵ĸߵ�˳�򷵻ظ����б�
	 * @param page ҳ����
	 * @return
	 */
	public static List<Song> queryTenSongByTwoClicks(int page)
	{
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("SELECT SONG.SongID,SONG.Name,SONG.SimpleName,SONG.Singer1,SONG.Singer2," +
				"SONG.Clicks+USER.Clicks Clicks,SONG.IsAvailable FROM SONG,USER WHERE USER.RecordName IS NULL " +
				"AND SONG.SongID=USER.SongID AND SONG.SongID NOT IN (SELECT SONG.SongID FROM SONG,USER WHERE "+
				"USER.RecordName IS NULL AND SONG.SongID=USER.SongID LIMIT "+page+"*10-10 )"+
				"ORDER BY Clicks DESC LIMIT 10", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}		
	/**
	 * ��ȡ��ָ����������дƥ�䵽�ĸ�������
	 * @param simplename ��������д
	 * @return
	 */
	public static int getSongCountBySimpleName(String simplename)
	{
		int count=0;
		db=databaseHelper.getReadableDatabase();
		Cursor result=db.rawQuery("SELECT count(SongID) from SONG where SimpleName like '"+simplename+"%'", null);
		result.moveToFirst();
		if(result.isNull(0))count= 0;
		else count=result.getInt(0);
		if(result!=null)result.close();
		db.close();
		return count;
	}
	/**
	 * ��ȡ��ָ����������дƥ�䵽�ĸ�����ҳ����ÿҳ10��)
	 * @param simplename ��������д
	 * @return
	 */
	public static int getSongPageBySimpleName(String simplename)
	{
		int count=getSongCountBySimpleName(simplename);
		if(count==0)return 0;
		int page=(count%10==0)?(count/10):(count/10+1);
		return page;
	}
	
	/**
	 * ��ȡ�������б����ҳ����ÿҳ10��)
	 * @return
	 */
	public static int getSongPage()
	{
		int count=getSongCount();
		if(count==0)return 0;
		int page=(count%10==0)?(count/10):(count/10+1);
		return page;
	}
	/**
	 * ���ݸ�������ȡ10�׸���
	 * @return
	 */
	public static List<Song> findTenSongBySinger(String name,int page)
	{
		if(page<0)return null;
		if(page>getSongCountBySigner(name)*10)return null;
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("SELECT * FROM SONG WHERE Singer1 LIKE '"+name+"%' AND SongID NOT IN (SELECT SongID FROM SONG WHERE Singer1 LIKE '"+name+"%' OR Singer2 LIKE '"+name+"%' OR Singer2 LIKE '%"+name+"' LIMIT "+page+"*10-10) OR Singer2 like '%"+name+"' OR Singer2 LIKE '%,"+name+"' LIMIT 10 ", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}
	/**
	 * ���ݸ�������ȡ�����ø��ֵĸ�������
	 * @return
	 */
	public static int getSongCountBySigner(String name)
	{
		int count=0;
		db=databaseHelper.getReadableDatabase();
		Cursor result=db.rawQuery("SELECT count(SongID) from SONG where Singer1 like '"+name+"%' or Singer2 like '"+name+"%' or Singer2 like '%"+name+"'", null);
		result.moveToFirst();
		if(result.isNull(0))count= 0;
		else count=result.getInt(0);
		if(result!=null)result.close();
		db.close();
		return count;
	}
	/**
	 * ���ݸ������͸�������д������ظ�������
	 * @param singer ������
	 * @param simplename ��������д
	 * @param page ҳ��
	 */
	public static int getSongCountBySgAndSn(String singer,String simplename)
	{
		int count=0;
		db=databaseHelper.getReadableDatabase();
		Cursor result=db.rawQuery("SELECT count(SongID) from SONG where SimpleName like '"+simplename+"%'and SongID in (select SongID where Singer1 like '"+singer+"%' or Singer2 like '"+singer+"%' or Singer2 like '%"+singer+"')", null);
		result.moveToFirst();
		if(result.isNull(0))count= 0;
		else count=result.getInt(0);
		if(result!=null)result.close();
		db.close();
		return count;
	}
	
	/**
	 * ���ݸ������͸�������д��ȡ10�׸���
	 */
	public static List<Song> findTenSongBySgAndSn(String singer,String simplename,int page)
	{
		if(page<0)return null;
		if(page>getSongCountBySgAndSn(singer,simplename)*10)return null;
		db=databaseHelper.getReadableDatabase();
		List<Song> data = new ArrayList<Song>();
		Cursor result=db.rawQuery("SELECT * FROM SONG WHERE SimpleName LIKE '"+simplename+"%' " +
				"AND SongID NOT IN(SELECT SongID FROM SONG WHERE SimpleName LIKE '"+simplename+"%' " +
				"AND SongID IN (SELECT SongID FROM SONG WHERE Singer1 LIKE '"+singer+"%' OR Singer2 LIKE '"+singer+"%' " +
				"OR Singer2 LIKE '%"+singer+"') LIMIT "+page+"*10-10)AND SongID IN (" +
				"SELECT SongID FROM SONG WHERE  Singer1 LIKE '"+singer+"%' OR Singer2 LIKE '"+singer+"%' " +
				"OR Singer2 LIKE '%"+singer+"') LIMIT 10", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Song song = new Song();
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
			data.add(song);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}
	//���ݸ���ID��ȡ����
	public static Song findSongById(int songid)
	{
		Song song=new Song();
		db=databaseHelper.getReadableDatabase();
		Cursor result=db.rawQuery("select * from SONG where SongID ="+songid, null);
		result.moveToFirst();
		if(!result.isNull(0))
		{
			song.setSongID(result.getInt(0));
			song.setName(result.getString(1));
			song.setSimpleName(result.getString(2));
			song.setSinger1(result.getString(3));
			if(!result.isNull(4))song.setSinger2(result.getString(4));
			song.setClicks(result.getInt(5));
			song.setIsAvailable(result.getInt(6));
			song.setMusicPath(song.getSongID());
			song.setAccomanimentPath(song.getSongID());
			song.setSongLyricUrl(song.getSongID());
		}
		if(result!=null)result.close();
		else song=null;
		return song;
	}
}
