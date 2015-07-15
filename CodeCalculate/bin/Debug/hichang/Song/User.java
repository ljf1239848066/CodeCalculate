package hichang.Song;

import hichang.database.DataBase;
import hichang.database.UserDA;

import java.util.List;

import android.content.Context;

/**
 * ����User��<br/>
 * ����һ��User��PD�࣬����ṹ�ĵڶ��㣬Ҳ������DA��Ľ���
 */
public class User {
	/**
	 * �û�ID
	 */
	private int iD;
	/**
	 * ¼����
	 */
	private String recordName;
	/**
	 * ��һ������
	 */
	private String firstName;
	/**
	 * �ڶ�������
	 */
	private String secondName;
	/**
	 * ����������
	 */
	private String thirdName;
	/**
	 * ��һ���÷�
	 */
	private int firstScore;
	/**
	 * �ڶ����÷�
	 */
	private int secondScore;
	/**
	 * �������÷�
	 */
	private int thirdScore;
	/**
	 * �û��������
	 */
	private int clicks;
	/**
	 * ����ID
	 */
	private int songID;
	/**
	 * DataBase�������������ݿ�Ľ�����
	 */
	private DataBase databaseHelper;
	//һϵ����HiUser������ص�setter��getter����
	public int getID() {
		return iD;
	}
	public void setID(int iD) {
		this.iD = iD;
	}
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getThirdName() {
		return thirdName;
	}
	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}
	public int getFirstScore() {
		return firstScore;
	}
	public void setFirstScore(int firstScore) {
		this.firstScore = firstScore;
	}
	public int getSecondScore() {
		return secondScore;
	}
	public void setSecondScore(int secondScore) {
		this.secondScore = secondScore;
	}
	public int getThirdScore() {
		return thirdScore;
	}
	public void setThirdScore(int thirdScore) {
		this.thirdScore = thirdScore;
	}
	public int getClicks() {
		return clicks;
	}
	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	public int getSongID() {
		return songID;
	}
	public void setSongID(int songID) {
		this.songID = songID;
	}
	/**
	 * HiUser�Ĺ��췽��
	 */
	public User()
	{
		
	}
	/**
	 * HiUser�Ĺ��췽���������������ݿ�
	 * @param context �豸������
	 */
	public User(Context context)
	{
		databaseHelper=new DataBase(context);
		UserDA.connectDB(databaseHelper);
	}
	/**
	 * ����û�ʹ�ü�¼1-¼������,ֻ��¼���������ID
	 * @param recordname ¼����
	 * @param songid     ����id
	 */
	public void addRecord(String recordname,int songid)
	{
		UserDA.addRecord(recordname, songid);
	}
	/**
	 * �޸ĵ�һ���÷ּ�¼
	 * @param id  �õ��˵÷ֵ��û�ID
	 * @param name �õ��˵÷��û���
	 * @param score ����
	 */
	public void alterFirst(int id,String name,int score)
	{
		UserDA.alterFirst(id, name, score);
	}	
	/**
	 * �޸ĵڶ����÷ּ�¼
	 * @param id �õ��˵÷ֵ��û�ID
	 * @param name �õ��˵÷��û���
	 * @param score ����
	 */
	public void alterSecond(int id,String name,int score)
	{
		UserDA.alterSecond(id, name, score);
	}	
	/**
	 * �޸ĵ������÷ּ�¼
	 * @param id �õ��˵÷ֵ��û�ID
	 * @param name �õ��˵÷��û���
	 * @param score ����
	 */
	public void alterThird(int id,String name,int score)
	{
		UserDA.alterThird(id, name, score);
	}	
	/**
	 * �޸ĵ������(���������+1),�޸Ĳ�����¼���ļ�¼������ʼ��¼
	 * @param songid ����ID
	 */
	public void alterClicks(int songid)
	{
		UserDA.alterClicks(songid);
	}	
	/**
	 * ���ݸ���ID��ȡ�ø�������߷�
	 */
	public int queryFirstScore(int songid)
	{
		return UserDA.queryFirstScore(songid);
	}
	/**
	 * ��ȡָ������ID��ǰ�����ݳ�������
	 * @param songid  ����ID
	 * @return ����ǰ�������ֵ�����
	 */
	public String[] queryThreeName(int songid)
	{
		String[] name=UserDA.queryThreeName(songid);
		return name;
	}	
	/**
	 * ��ȡָ������ID��ǰ�����÷�
	 * @param songid  ����ID
	 * @return   ����ǰ�����÷ֵ�����
	 */
	public int[] queryThreeScore(int songid)
	{
		int[] score=UserDA.queryThreeScore(songid);
		return score;
	}	
	/**
	 * ���ݸ���ID��ȡ¼����
	 * @param songid  ����ID
	 * @return
	 */
	public String queryRecordName(int songid)
	{
		return UserDA.queryRecordName(songid);
	}
	/**
	 * ����¼������ȡ����ID
	 * @param recordname ¼����
	 * @return
	 */
	public int querySongIdByRecordName(String recordname)
	{
		return UserDA.querySongIdByRecordName(recordname);
	}	
	/**
	 * ͨ������ID����User
	 * @param songid ����ID
	 * @return
	 */
	public User queryUserBySongId(int songid)
	{
		return UserDA.queryUserBySongId(songid);
	}	
}
