/*
 * .Sentence.java
 * ��������:Sentence
 */
package hichang.Song;

import java.util.ArrayList;
import hichang.Song.Chapter;
/**
 * ����:HiSentence<br/>
 * �Ծ��ӵ����Խ��г�ʼ�����������ӵĿ�ʼʱ�䣬���ֵĳ���ʱ�䣬���������Լ����þ�ĵ÷�
 */
public class Sentence {
	/**
	 * ���ڼ�¼�þ仰��ÿ���ֵ�����,����ĸ�����ݿ��ܰ�����~������ΪҪ��ʾ����������
	 */
	public ArrayList<Chapter> mychapter=new ArrayList<Chapter>();
	/**
	 * �����ʾ����ĸ�����ݣ��˲��ֲ��������ļ��н��������ġ�~��
	 */
	public ArrayList<Chapter> anotherChapters=new ArrayList<Chapter>();
	/**
	 * ���ӵĿ�ʼʱ��
	 */
	public int StartTimeofThis;
	/**
	 * ���ӵĳ���ʱ��
	 */
	public int LastTimeofThis;
	/**
	 * ��ʼ�ַ���
	 */
	public String text="";
	/**
	 * ���ӵĵ÷�
	 */
	public int Score;
	/**
     * HiSentence�Ĺ��캯��
     * @param startTime���ӵĿ�ʼʱ�� 
     * @param lastTime���ӵĳ���ʱ��
     */
	public Sentence(int startTime,int lastTime){
		this.StartTimeofThis = startTime;
		this.LastTimeofThis = lastTime;
	}
	/**
     * �޲����Ĺ��캯��
     */
	public Sentence(){
		
	}
	/**
	 * ���ӵ��γɹ����е������
	 * @param start ������ڸþ����ʼʱ��
	 * @param last  �ֵĳ���ʱ��
	 * @param high  �ֵ�����
	 * @param text  ������
	 */
	public void AddChapter(int start,int last,int high,String text){
		Chapter aChapter=new Chapter(start, last, high, text);
		if(!text.equals("~")){
			this.text += text;
		}
		mychapter.add(aChapter);
		
	}
	/**
	 * ���ӵ��γɹ����е������,������~
	 * @param start ������ڸþ����ʼʱ��
	 * @param last  �ֵĳ���ʱ��
	 * @param high  �ֵ�����
	 * @param text  ������
	 */
	public void AddAnotherChapter(int start,int last,int high,String text){
		if(text.equals("~")){
			anotherChapters.get(anotherChapters.size()-1).ChpofLast+=last;
		} else {
			Chapter aChapter=new Chapter(start, last, high, text);
			anotherChapters.add(aChapter);
		}
		
	}
	/**
	 * ��ȡindex�������ֵ���Կ�ʼʱ��
	 * @param index ����
	 * @return ������Կ�ʼʱ��
	 */
	public int getChapterStart(int index){
		return mychapter.get(index).ChpofStart;
	}
	
	public int getChapterHigh(int index){
		return mychapter.get(index).ChpofHigh;
	}

	public int getChapterLast(int index){
		return mychapter.get(index).ChpofLast;
	}
	
	public int getChaptetCount(){
	    return mychapter.size();
	}
	
	public String getChapterText(int index){
		return mychapter.get(index).text;
	}


}
