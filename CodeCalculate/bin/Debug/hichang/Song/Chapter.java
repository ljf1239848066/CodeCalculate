/*
 * .Chapter.java
 * Ϊ�����һ�仰�е��ֽ������࣬����ÿ���ֵ�������Ϣ
 */
package hichang.Song;
/**
 * ����:Character
 * ������ֵ�������Ϣ�����������ʵʱ�䡢�ֵĳ���ʱ�䡢�ֵ������Լ��ֵ�����
 */
public class Chapter{
	/**
     * ������������ڵ�ǰ�þ仰����ʼʱ��
     */ 
	 public int ChpofStart;
	 /**
	  * ����ֵĳ���ʱ��
	  */
	 public int ChpofLast;
	 /**
	  * �ֵ�����
	  */
	 public int ChpofHigh;
	 /**
	  * �ֵ�����
	  */
	 public String text;
	 /**
	  * ��Ĺ��캯��
	  * @param start ����Ըþ�Ŀ�ʼʱ��
	  * @param last  �ֵĳ���ʱ��
	  * @param high     �ֵ�����
	  * @param text      �ֵ�����
	  */
	 public Chapter(int start,int last,int high,String text){
		 this.ChpofStart=start;
		 this.ChpofLast=last;
		 this.ChpofHigh=high;
		 this.text=text;
	 }
}