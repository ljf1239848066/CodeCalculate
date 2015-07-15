package hichang.database;

import hichang.Song.Singer;
import hichang.database.DataBase;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Singer<br/>
 * �����ݿ���ʵ����ݷ�����
 */
public class SingerDA {
	/**
	 * HiDataBase����
	 */
	private static DataBase databaseHelper;
	/**
	 * �����ݿ�����Ķ���
	 */
	private static SQLiteDatabase db;
	/**
	 * SingerDA�Ĺ��캯��
	 */
	public SingerDA() {   	
	}
	/**
	 * �������ݿ�
	 * @param nowDB DataBase�������
	 */
	public static void connectDB(DataBase nowDB){
		databaseHelper=nowDB;
	}
	///���ֱ����ɾ���
		/**
		 * ��Ӹ���
		 * @param name ������
		 * @param simplename ����������ĸ
		 * @param gender   �����Ա�
		 */
	public static void addSinger(String name,String simplename,String gender)
	{
		db=databaseHelper.getWritableDatabase();
		db.beginTransaction();
		try
		{
			db.execSQL("insert into TableSinger(Name,SimpleName,Gender) values(?,?,?)", 
					new Object[] { name, simplename,gender});
			db.setTransactionSuccessful();
		} 
		catch (Exception e) 
		{ } 
		db.endTransaction();
	}		
	/**
	 * �����������Ҹ�����Ϣ
	 */
	public static Singer querySingerByName(String name)
	{
		db=databaseHelper.getReadableDatabase();
		Singer singer = new Singer();
		Cursor result=db.rawQuery("select * from SINGER where Name like '"+name+"' order by Name", null);
		result.moveToFirst();
		if(!result.isAfterLast())
		{
			singer.setiD((result.getInt(0)));
			singer.setName((result.getString(1)));
			singer.setSimpleName(result.getString(2));
			singer.setGender((result.getString(3)));
		}
		if(result!=null)result.close();
		db.close();
		result=null;
		return singer;
	}
	/**
	 * ������ȫ���������Ҹ���,���ظ����б�
	 * @param subName ����ȫ������
	 * @return ���ظ����б�
	 */
	public static List<Singer> querySingerBySubName(String subName)
	{
		db=databaseHelper.getReadableDatabase();
		List<Singer> data = new ArrayList<Singer>();
		Cursor result=db.rawQuery("select * from SINGER where Name like '"+subName+"%' order by Name", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			Singer singer = new Singer();
			singer.setiD((result.getInt(0)));
			singer.setName((result.getString(1)));
			singer.setSimpleName(result.getString(2));
			singer.setGender((result.getString(3)));
			data.add(singer);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}	
	/**
	 * ����������д���Ҹ��֣����ظ����б�
	 * @param simplename ��������д
	 * @return �����б�
	 */
	public static List<Singer> querySingerBySimpleName(String simplename)
	{
		List<Singer> data = new ArrayList<Singer>();
		db=databaseHelper.getReadableDatabase();
		Cursor result=db.rawQuery("select * from SINGER where SimpleName like '"+simplename+"' order by Name desc", null);
		result.moveToFirst(); 
		while(!result.isAfterLast())
		{
			Singer singer = new Singer();
			singer.setiD((result.getInt(0)));
			singer.setName((result.getString(1)));
			singer.setSimpleName(result.getString(2));
			singer.setGender((result.getString(3)));
			data.add(singer);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}		
	/**
	 * �����Ա𷵻ظ����б�
	 * @param gender �����Ա�
	 * @return �����б�
	 */
	public static List<Singer> querySingerByGender(String gender)
	{
		db=databaseHelper.getReadableDatabase();
		List<Singer> data=new ArrayList<Singer>();
		Cursor result=db.rawQuery("select * from SINGER where Gender like ? order by Name", new String[]{gender});
		result.moveToFirst();
		while(!result.isAfterLast()){
			Singer singer = new Singer();
			singer.setiD((result.getInt(0)));
			singer.setName((result.getString(1)));
			singer.setSimpleName(result.getString(2));
			singer.setGender((result.getString(3)));
			data.add(singer);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;//δ�ҵ����ؿ�
		return data;
	}		
	/**
	 * �������и���
	 * @return �������и���
	 */
	public static List<Singer> queryAllSinger()
	{
		db=databaseHelper.getReadableDatabase();
		List<Singer> data=new ArrayList<Singer>();
		Cursor result=db.rawQuery("select * from SINGER order by Name", null);
		result.moveToFirst();
		while(!result.isAfterLast()){
			Singer singer = new Singer();
			singer.setiD((result.getInt(0)));
			singer.setName((result.getString(1)));
			singer.setSimpleName(result.getString(2));
			singer.setGender((result.getString(3)));
			data.add(singer);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;//δ�ҵ����ؿ�
		return data;
	}	
	/**
	 * ��������д���ظ����б�����б�
	 * @param singerlist ����ԭʼ�б�
	 * @param simplename ��������д
	 * @return �����б�����б�
	 */
	public static List<Singer> querySubSingerList(List<Singer> singerlist,String simplename)
	{
		List<Singer> data=new ArrayList<Singer>();
		if(singerlist.isEmpty())return null;
		for(int i=0;i<singerlist.size();i++)
		{
			if(singerlist.get(i).getSimpleName().indexOf(simplename)==0)
				data.add(singerlist.get(i));
		}
		return data;
	}
	/**
	 * ��ȡ��������
	 * @return
	 */
	public static int getSingerCount()
	{
		int count=0;
		db=databaseHelper.getReadableDatabase();
		Cursor result=db.rawQuery("SELECT count(ID) from SINGER", null);
		result.moveToFirst();
		if(result.isNull(0))count= 0;
		else count=result.getInt(0);
		if(result!=null)result.close();
		db.close();
		return count;
	}
	/**
	 * ��˳����ȡ�Ÿ�������Ϣ
	 * @param page ҳ����
	 * @return
	 */
	public static List<String> findNineSinger(int page)
	{
		db=databaseHelper.getReadableDatabase();
		List<String> data = new ArrayList<String>();
		Cursor result;
		result=db.rawQuery("select Name from SINGER where ID not in (select ID from SINGER limit "+page+"*9-9)  limit 9", null);
		result.moveToFirst();
		while(!result.isAfterLast()){
			data.add(result.getString(0));
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}	
	/**
	 * ��˳����ȡ�Ÿ�������Ϣ
	 * @param page ҳ����
	 * @return
	 */
	public static List<Singer> queryNineSinger(int page)
	{
		db=databaseHelper.getReadableDatabase();
		List<Singer> data=new ArrayList<Singer>();
		Cursor result;
		result=db.rawQuery("select * from SINGER where ID not in (select ID from SINGER limit "+page+"*9-9)  limit 9", null);
		result.moveToFirst();
		while(!result.isAfterLast()){
			Singer singer = new Singer();
			singer.setiD((result.getInt(0)));
			singer.setName((result.getString(1)));
			singer.setSimpleName(result.getString(2));
			singer.setGender((result.getString(3)));
			data.add(singer);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}	
	/**
	 * ���ݸ�������д��ȡ��������
	 * @return
	 */
	public static int getSingerCountBySimpleName(String simplename)
	{
		int count=0;
		db=databaseHelper.getReadableDatabase();
		Cursor result=db.rawQuery("SELECT count(ID) from SINGER where SimpleName like '"+simplename+"%'", null);
		result.moveToFirst();
		if(result.isNull(0))count= 0;
		else count=result.getInt(0);
		if(result!=null)result.close();
		db.close();
		return count;
	}
	/**
	 * ����������д��ȡ�Ÿ�������
	 * @param simplename ��������д
	 * @param page ҳ����
	 * @return
	 */
	public static List<String> findNineSingerBySN(String simplename,int page)
	{
		db=databaseHelper.getReadableDatabase();
		List<String> data = new ArrayList<String>();
		Cursor result;
		result=db.rawQuery("select Name from SINGER where SimpleName like '"+simplename+"%' and ID not in (select ID from SINGER where SimpleName like '"+simplename+"%' limit "+page+"*9-9)  limit 9", null);
		result.moveToFirst();
		while(!result.isAfterLast()){
			data.add(result.getString(0));
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}
	/**
	 * ����������д��ȡ�Ÿ�����
	 * @param simplename ��������д
	 * @param page ҳ����
	 * @return
	 */
	public static List<Singer> queryNineSingerBySN(String simplename,int page)
	{
		db=databaseHelper.getReadableDatabase();
		List<Singer> data = new ArrayList<Singer>();
		Cursor result;
		result=db.rawQuery("select * from SINGER where SimpleName like '"+simplename+"%' and ID not in (select ID from SINGER where SimpleName like '"+simplename+"%' limit "+page+"*9-9)  limit 9", null);
		result.moveToFirst();
		while(!result.isAfterLast()){
			Singer singer = new Singer();
			singer.setiD((result.getInt(0)));
			singer.setName((result.getString(1)));
			singer.setSimpleName(result.getString(2));
			singer.setGender((result.getString(3)));
			data.add(singer);
			result.moveToNext();
		}
		if(result!=null)result.close();
		db.close();
		if(data.isEmpty())return null;
		return data;
	}
}
