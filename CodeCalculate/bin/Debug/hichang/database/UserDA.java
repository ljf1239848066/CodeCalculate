package hichang.database;

import hichang.Song.User;
import hichang.database.DataBase;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * UserDA<br/>
 * User��DA�࣬�����ݿ�ֱ�ӽ���
 */
public class UserDA {
	private static DataBase databaseHelper;
	private static SQLiteDatabase db;
	/**
	 * HiUserDA �Ĺ��캯��
	 */
	public UserDA()
	{
		
	}
	/**
	 * ��������
	 * @param databaseHelper �ӵ��÷����ݵ�HiDataBase����
	 */
	public static void connectDB(DataBase nowDB)
	{
		databaseHelper=nowDB;
	}
	// HiUser�����ɾ���
	 
	/**
	 * ����û�ʹ�ü�¼1-¼������,ֻ��¼���������ID
	 * @param recordname ¼����
	 * @param songid ����ID
	 */	
	public static void addRecord(String recordname,int songid)
	{
		db = databaseHelper.getWritableDatabase(); 
		db.beginTransaction();
		try 
		{
			db.execSQL("insert into USER(RecordName,SongID) values(?,?)", 
					new Object[] { recordname, songid});
			db.setTransactionSuccessful();
		}
		catch (Exception e)
		{ }
		db.endTransaction();
	}
	/**
	 * �޸ĵ�һ����������Ϣ
	 * @param id ����ID
	 * @param name �û���
	 * @param score ����
	 */
	public static void alterFirst(int id,String name,int score)
	{
		db.close();
		db = databaseHelper.getWritableDatabase(); 
		db.execSQL("update USER set FirstName='"+name+"',FirstScore="+score+" where ID="+id);
		db.close();
	}	
	/**
	 * �޸ĵڶ����÷ּ�¼
	 * @param id ����ID
	 * @param name �û���
	 * @param score ����
	 */
	public static void alterSecond(int id,String name,int score)
	{

		db = databaseHelper.getWritableDatabase(); 
		db.execSQL("update USER set SecondName='"+name+"',SecondScore="+score+" where ID="+id);
		db.close();
	}	
	/**
	 * �޸ĵ������÷ּ�¼
	 * @param id ����ID
	 * @param name �û���
	 * @param score ����
	 */
	public static void alterThird(int id,String name,int score)
	{
		db = databaseHelper.getWritableDatabase(); 
		db.execSQL("update USER set ThirdName='"+name+"',ThirdScore="+score+" where ID="+id);
		db.close();
	}	
	/**
	 * �޸ĵ������(���������+1),�޸Ĳ�����¼���ļ�¼������ʼ��¼
	 * @param songid ����ID
	 */
	public static void alterClicks(int songid)
	{
		db=databaseHelper.getWritableDatabase();
		db.execSQL("update USER set Clicks=Clicks+1 where SongID="+songid+" and RecordName is null");
		db.close();
	}
	/**
	 * ��ȡָ������ID��ǰ�����ݳ�������
	 * @param songid ������
	 * @return  ָ������ID��ǰ�����ݳ�������
	 */
	public static String[] queryThreeName(int songid)
	{
		db=databaseHelper.getReadableDatabase();
		String[] name=new String[3];
		Cursor result=db.rawQuery("select FirstName,SecondName,ThirdName from USER where SongID="+songid+" and RecordName is null", null);
		result.moveToFirst();
		if(result.isNull(0))return null;//��һ������Ϊ����ζ��δ���ҵ���˸�����δ�ݳ���
		else name[0]=result.getString(0);
		if(!result.isNull(1))name[1]=result.getString(1);
		else name[1]="";
		if(!result.isNull(2))name[2]=result.getString(2);
		else name[2]="";
		result.close();
		db.close();
		return name;
	}
	/**
	 * ���ݸ���ID��ȡ�ø�������߷�
	 */
	public static int queryFirstScore(int songid)
	{
		db=databaseHelper.getReadableDatabase();
		int firstscore=0;
		Cursor result=db.rawQuery("select FirstScore from USER where SongID="+songid+" and RecordName is null", null);
		result.moveToFirst();
		firstscore=result.getInt(0);
		result.close();
		db.close();
		return firstscore;
	}
	/**
	 * ��ȡָ������ID��ǰ�����÷�
	 * @param songid ����ID
	 * @return ָ������ID��ǰ�����÷�
	 */
	public static int[] queryThreeScore(int songid)
	{
		db=databaseHelper.getReadableDatabase();
		int[] score=new int[3];
		Cursor result=db.rawQuery("select FirstScore,SecondScore,ThirdScore from USER where SongID="+songid+" and RecordName is null", null);
		result.moveToFirst();
		//FirstScore,SecondScore,ThirdScore ��Ĭ��ֵΪ0�������ж��Ƿ�Ϊ��
		score[0]=result.getInt(0);
		score[1]=result.getInt(1);
		score[2]=result.getInt(2);
		result.close();
		db.close();
		return score;
	}	
	/**
	 * ���ݸ���ID��ȡ¼����
	 * @param songid ����ID
	 * @return ¼����
	 */
	public static String queryRecordName(int songid)
	{
		db=databaseHelper.getReadableDatabase();
		Cursor result=db.rawQuery("select RecordName from USER where SongID="+songid+" and RecordName is not null", null);
		result.moveToFirst();
		if(result.isNull(0))return null;
		return result.getString(0);
	}
	/**
	 * ����¼������ȡ����ID
	 * @param recordname ¼����
	 * @return ����ID
	 */ 
	public static int querySongIdByRecordName(String recordname)
	{
		db=databaseHelper.getReadableDatabase();
		Cursor result=db.rawQuery("select SongID from USER where RecordName='?'", new String[]{recordname});
		result.moveToFirst();
		if(result.isNull(0))return 0;
		return result.getInt(0);
	}
	/**
	 * ͨ������ID���س������׸��User�б�
	 * @param songid ����ID
	 * @return  �������׸��User�б�
	 */
	public static User queryUserBySongId(int songid)
	{
		db=databaseHelper.getReadableDatabase();
		User user=new User();
		Cursor result=db.rawQuery("select * from USER where SongID="+songid+" order by ID", null);
		result.moveToFirst();
		if(!result.isAfterLast())
		{
			user.setID(result.getInt(0));
			if(!result.isNull(1))user.setRecordName(result.getString(1));
			else user.setRecordName("");
			if(!result.isNull(2))user.setFirstName(result.getString(2));
			else user.setFirstName("");
			user.setFirstScore(result.getInt(3));
			if(!result.isNull(4))user.setSecondName(result.getString(4));
			else user.setSecondName("");
			user.setSecondScore(result.getInt(5));
			if(!result.isNull(6))user.setThirdName(result.getString(6));
			else user.setThirdName("");
			user.setThirdScore(result.getInt(7));
			user.setClicks(result.getInt(8));
			user.setSongID(result.getInt(9));
		}
		result.close();
		db.close();
		return user;
	}
}
