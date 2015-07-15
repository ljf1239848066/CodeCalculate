package hichang.Song;

import hichang.database.DataBase;
import hichang.database.SongDA;

import java.util.List;

import android.content.Context;

public class Song {
	/**
	 * ����ID
	 */
	private int songID;
    /**
     * ������
     */
    private  String name;
    /**
     * ����������ĸ��д
     */
    private String simpleName;
    /**
     * ������1
     */
    private String singer1;
    /**
     * ������2
     */
    private String singer2;
    /**
     * �����������
     */
    private int clicks;
    /**
     * �Ƿ����
     */
    private int isAvailable;
    /**
     * ԭ��·��
     */
    private String musicPath;
    /**
     * �鳪·��
     */
    private String accomanimentPath;
    /**
     * ���·��
     */
    private String songLyricUrl;

    //ԤԼ�ţ�δ��ԤԼʱΪ-1
    public int bookNum=-1;

    /**
     * �����ļ��е�·��
     */

    private final String PATH="/sdcard/HiChang/Songs/";
    /**
     * DataBase��������Song(Context context)���ݲ���
     */
	private DataBase databaseHelper;
	/**
	 * Song�Ĺ��캯��
	 */
	public Song()
	{	
	}
	/**
	 * Song�������ݿ����ʱ�Ĺ��캯��
	 * @param context activity�����Ļ���
	 */
	public Song(Context context) {
		databaseHelper=new DataBase(context);
		SongDA.connectDB(databaseHelper);
	}
	/*�����set��get�����������û��ȡHiSongʵ����ǰ����ֵ*/
	public int getSongID() {
		return songID;
	}
	public void setSongID(int songID) {
		this.songID = songID;
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
	public String getSinger1() {
		return singer1;
	}
	public void setSinger1(String singer1) {
		this.singer1 = singer1;
	}
	public String getSinger2() {
		return singer2;
	}
	public void setSinger2(String singer2) {
		this.singer2 = singer2;
	}
	public int getClicks() {
		return clicks;
	}
	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	public int getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getMusicPath() {
		return musicPath;
	}
	//����ÿ�׸��������ļ�����ID��ʾһ���ļ��У��ļ�����ID�����v���ı�ʾԭ���ļ����ӡ�i���ı�ʾ�����ļ���txt��ʽ���ļ��Ǹ�������ļ�
	public void setMusicPath(int songId) {
		this.musicPath = PATH+songId+"/"+songId+"_v.mp3";
	}
	public String getAccomanimentPath() {
		return accomanimentPath;
	}
	public void setAccomanimentPath(int songId) {
		this.accomanimentPath = PATH+songId+"/"+songId+"_i.mp3";
	}
	public String getSongLyricUrl() {
		return songLyricUrl;
	}
	public void setSongLyricUrl(int songId) {
		this.songLyricUrl = PATH+songId+"/"+songId+".txt";
	}
	/**
	 * ���캯��������HiSong����
	 * @param songID ����ID
	 * @param name  ������
	 * @param simpleName ����������ĸ��д
	 * @param singer1 ����1
	 * @param singer2 ����2
	 * @param clicks �������
	 * @param isAvailable �Ƿ����
	 * @param musicPath ԭ��·��
	 * @param accomanimentPath ����·��
	 * @param songLyricUrl ���·��
	 */
	public Song(int songID, String name, String simpleName, String singer1,
			String singer2, int clicks, int isAvailable, String musicPath,
			String accomanimentPath, String songLyricUrl) {
		super();
		this.songID = songID;
		this.name = name;
		this.simpleName = simpleName;
		this.singer1 = singer1;
		this.singer2 = singer2;
		this.clicks = clicks;
		this.isAvailable = isAvailable;
		this.musicPath = musicPath;
		this.accomanimentPath = accomanimentPath;
		this.songLyricUrl = songLyricUrl;
	}
	/**
	 * �������еĸ���
	 * @return ����Song�б�
	 */
	public List<Song> findAllSong()
	{
		return SongDA.findAllSong();
	}
	/**
	 * ��Ӹ���
	 * @param name            ������
	 * @param simplename      ����������ĸ
	 * @param singer1                              һ���ݳ���
	 * @param singer2                             �����ݳ���
	 * @param clicks         �������
	 * @param isAvailable    �Ƿ��ɴ˸����ļ�
	 */
	public void addSong(String name, String simplename, String singer1,String singer2,int clicks, int isAvailable)
	{
		SongDA.addSong(name, simplename, singer1, singer2, clicks, isAvailable);
	}
	/**
	 * ɾ������
	 * @param name ��������ͨ��������ɾ���ļ�
	 */
	public void deleteSong(String name)
	{
		SongDA.deleteSong(name);
	}	
	/**
	 * ������ȫ���������Ҹ��������ظ����б�
	 * @param subname  ����ȫ������
	 * @return         ���ظ����б�
	 */
	public List<Song> querySongBySubname(String subname)
	{
		return SongDA.querySongBySubname(subname);
	}		
	/**
	 * ����������д���Ҹ��������ظ����б�
	 * @param simplename  �������Ƽ�д
	 * @return           ���ظ����б�
	 */
	public List<Song> querySongBySimpleName(String simplename)
	{
		return SongDA.querySongBySimpleName(simplename);
	}	
	/**
	 * ��������������ߵ�˳�򷵻ظ����б�
	 * @return  �����б�
	 */
	public List<Song> querySongByClicks()
	{
		return SongDA.querySongByClicks();
	}	
	/**
	 * �����磨������չ����������������û��������֮�͵ĸߵ�˳�򷵻ظ����б�
	 * @return  �����б�
	 */
	public List<Song> querySongByTwoClicks()
	{
		return SongDA.querySongByTwoClicks();
	}	
	/**
	 * ���û���������ߵ�˳�򷵻ظ����б�
	 * @return  �����б�
	 */
	public List<Song> querySongByUserClicks()
	{
		return SongDA.querySongByUserClicks();
	}	
	/**
	 * ���ݸ��������Ҹ���ID
	 * @param name ������
	 * @return
	 */
	public List<Song> querySongBySingerName(String name)
	{
		return SongDA.querySongBySingerName(name);
	}		
	/**
	 * �Ӹ����б��м�����������д�ĸ������б�
	 * @param songlist �����б�
	 * @param simplename ��������д
	 * @return
	 */
	public List<Song> findSubSongList(List<Song> songlist,String simplename)
	{
		return SongDA.findSubSongList(songlist, simplename);
	}	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public int getSongCount()
	{
		return SongDA.getSongCount();
	}
	/**
	 * ��˳����ȡʮ�׸���
	 * @param page ҳ����
	 * @return
	 */
	public List<Song> findTenSong(int page)
	{
		return SongDA.findTenSong(page);
	}
	/**
	 * �ø�������д��������˳����ȡʮ�׸���
	 * @param simplename ��������д
	 * @param page ҳ����
	 * @return
	 */
	public List<Song> findTenSongBySimpleName(String simplename,int page)
	{
		return SongDA.findTenSongBySimpleName(simplename, page);
	}
	/**
	 * ����������������û��������֮�͵ĸߵ�˳�򷵻ظ����б�
	 * @param page ҳ����
	 * @return
	 */
	public List<Song> queryTenSongByTwoClicks(int page)
	{
		return SongDA.queryTenSongByTwoClicks(page);
	}	
	/**
	 * ��ȡ��ָ����������дƥ�䵽�ĸ�������
	 * @param simplename ��������д
	 * @return
	 */
	public int getSongCountBySimpleName(String simplename)
	{
		return SongDA.getSongCountBySimpleName(simplename);
	}
	/**
	 * ��ȡ��ָ����������дƥ�䵽�ĸ�����ҳ����ÿҳ10��)
	 * @param simplename ��������д
	 * @return
	 */
	public int getSongPageBySimpleName(String simplename)
	{
		return SongDA.getSongCountBySimpleName(simplename);
	}
	/**
	 * ��ȡ�������б����ҳ����ÿҳ10��)
	 * @return
	 */
	public int getSongPage()
	{
		return SongDA.getSongPage();
	}
	/**
	 * ���ݸ�������ȡ10�׸���
	 * @return
	 */
	public List<Song> findTenSongBySinger(String name,int page)
	{
		return SongDA.findTenSongBySinger(name,page);
	}
	/**
	 * ���ݸ�������ȡ�����ø��ֵĸ�������
	 * @return
	 */
	public int getSongCountBySigner(String name)
	{
		return SongDA.getSongCountBySigner(name);
	}
	/**
	 * ���ݸ������͸�������д������ظ�������
	 * @param singer ������
	 * @param simplename ��������д
	 * @param page ҳ��
	 */
	public int getSongCountBySgAndSn(String singer,String simplename)
	{
		return SongDA.getSongCountBySgAndSn(singer, simplename);
	}
	/**
	 * ���ݸ������͸�������д��ȡ10�׸���
	 */
	public  List<Song> findTenSongBySgAndSn(String singer,String simplename,int page)
	{
		return SongDA.findTenSongBySgAndSn(singer, simplename, page);
	}
	//���ݸ���ID��ȡ����
	public Song findSongById(int songid)
	{
		return SongDA.findSongById(songid);
	}
}
