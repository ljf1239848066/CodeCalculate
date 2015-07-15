package hichang.Song;

import hichang.Song.Singer;
import hichang.database.DataBase;
import hichang.database.SingerDA;

import java.util.List;

import android.content.Context;

/**
 * Singer<br/>
 * ����Singer
 */
public class Singer {
	/**
	 * ����ID
	 */
    public int iD;
    /**
     * ������
     */
    public String name;
    /**
     * ����������ĸ��д
     */
    public String simpleName;
    /**
     * �����Ա�
     */
    public String gender;
    /**
     * ����ID
     */
    public String songID;
    /**
     * HiDataBase�������������ݷ����ཻ��
     */
	private DataBase databaseHelper;
	/**
	 * ���캯��
	 */
	public Singer()
	{	
	}
	/**
	 * ���캯�����������ݷ����ཻ��
	 * @param context
	 */
	public Singer(Context context) {
		databaseHelper=new DataBase(context);
		SingerDA.connectDB(databaseHelper);
	}
	// Singer�����Ե�getter��setter
	public int getiD() {
		return iD;
	}
	public void setiD(int iD) {
		this.iD = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * ��Ӹ���
	 * @param name  ������
	 * @param simplename ���ֵ���������ĸ��д
	 * @param gender �����Ա�
	 */
	public void addSinger(String name,String simplename,String gender)
	{
		SingerDA.addSinger(name, simplename, gender);
	}	
	/**
	 * �����������Ҹ�����Ϣ
	 */
	public static Singer querySingerByName(String name)
	{
		return SingerDA.querySingerByName(name);
	}
	/**
	 * ������ȫ���������Ҹ���,���ظ����б�
	 * @param subName  ���ֲ���ȫ��
	 * @return  �����б�
	 */
	public List<Singer> querySingerBySubName(String subName)
	{
		return SingerDA.querySingerBySubName(subName);
	}	
	/**
	 * ����������д���Ҹ��֣����ظ����б�
	 * @param simplename  ��������д
	 * @return  �����б�
	 */
	public List<Singer> querySingerBySimpleName(String simplename)
	{
		return SingerDA.querySingerBySimpleName(simplename);
	}		
	/**
	 * �����Ա𷵻ظ������б�
	 * @param gender �����Ա�
	 * @return �����б�
	 */
	public List<Singer> querySingerByGender(String gender)
	{
		return SingerDA.querySingerByGender(gender);
	}
	/**
	 * �������и���
	 * @return �����б�
	 */
	public List<Singer> queryAllSinger()
	{
		return SingerDA.queryAllSinger();
	}	
	/**
	 * ��������д���ظ����б�����б�
	 * @param singerlist ����ԭʼ�б�
	 * @param simplename ��������д
	 * @return �����б�����б�
	 */
	public List<Singer> querySubSingerList(List<Singer> singerlist,String simplename)
	{
		return SingerDA.querySubSingerList(singerlist, simplename);
	}	
	/**
	 * ��ȡ��������
	 * @return ��������
	 */
	public int getSingerCount()
	{
		return SingerDA.getSingerCount();
	}
	/**
	 * ��˳����ȡ�Ÿ�������Ϣ
	 * @param page ҳ����
	 * @return
	 */
	public List<Singer> queryNineSinger(int page)
	{
		return SingerDA.queryNineSinger(page);
	}
	/**
	 * ��˳����ȡ�Ÿ�������Ϣ
	 * @param page ҳ���� 
	 * @return ������Ϣ
	 */
	public List<String> findNineSinger(int page)
	{
		return SingerDA.findNineSinger(page);
	}
	/**
	 * ���ݸ�������д��ȡ��������
	 * @return
	 */
	public int getSingerCountBySimpleName(String simplename)
	{
		return SingerDA.getSingerCountBySimpleName(simplename);
	}
	/**
	 * ����������д��ȡ�Ÿ�������
	 * @param simplename ��������д
	 * @param page ҳ����
	 * @return
	 */
	public List<String> findNineSingerBySN(String simplename,int page)
	{
		return SingerDA.findNineSingerBySN(simplename, page);
	}
	/**
	 * ����������д��ȡ�Ÿ�����
	 * @param simplename ��������д
	 * @param page ҳ����
	 * @return
	 */
	public List<Singer> queryNineSingerBySN(String simplename,int page)
	{
		return SingerDA.queryNineSingerBySN(simplename, page);
	}
}
