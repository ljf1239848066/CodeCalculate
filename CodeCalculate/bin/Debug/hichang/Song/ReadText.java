/*
 * .ReadText.java
 * �����ࣺReadText
 */
package hichang.Song;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.*;
import java.io.FileReader;

/**
 * ������ReadText<br/>
 * ��ȡ����ļ������ļ����н���
 */
public class ReadText {
	/**
	 * ��ȡ����ļ���Ҫ���ص����о���
	 */
	private ArrayList<Sentence> allSentences = null;
	/**
	 * ��������ļ���·��
	 */
	private String url;
	/**
	 * ���׸��г��ֵĸ�����ߵ���Сֵ
	 */
	public int min;
	/**
	 * �ø��г��ֵĸ�����ߵ����ֵ
	 */
	public int max;

	/**
	 * ��Ĺ��캯��
	 * 
	 * @param url
	 *            ��������ļ���·��
	 */
	public ReadText(String url) {
		// TODO Auto-generated constructor stub
		this.url = url;
		max = 0;
		min = 200;
	}

	/**
	 * ��ȡ��������ļ�������
	 * 
	 * @return ����HiSentence�ļ���
	 */
	public ArrayList<Sentence> ReadData() {
		allSentences = new ArrayList<Sentence>();
		try {
			FileReader reader = new FileReader(url);

			BufferedReader br = new BufferedReader(reader);// ���ļ�
			String rowString;

			while ((rowString = br.readLine()) != null)// ÿһ�ж�һ��
			{
				allSentences.add(anaylise(rowString));//
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return allSentences;// ����һ�仰
	}

	/**
	 * ��ȡ��ʵ������ļ����ҽ��н���
	 * 
	 * @param in
	 * @return
	 */
	public ArrayList<Sentence> ReadData(InputStream in) {
		allSentences = new ArrayList<Sentence>();
		try {
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(inr);// ���ļ�
			String rowString;

			while ((rowString = br.readLine()) != null)// ÿһ�ж�һ��
			{
				allSentences.add(anaylise(rowString));//
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return allSentences;// ����һ�仰
	}

	/**
	 * �������ļ��ж�ȡ������
	 * 
	 * @param sentence
	 *            ��ȡ��Ҫ������������
	 * @return Sentence����
	 */
	private Sentence anaylise(String sentence)// �������ļ��ж�ȡ������
	{
		Sentence mysentence = null;
		String aString;

		Pattern pattern = Pattern.compile("\\[([^\\]]*)]");// ������ʽƥ��þ��ʱ��ͳ���
		Matcher matcher = pattern.matcher(sentence);

		while (matcher.find()) {
			aString = matcher.group(0);

			String[] strs = aString.split(",");
			String starttime = strs[0].replaceAll("[\\[]", "");
			String lasttime = strs[1].replaceAll("[\\]]", "");
			mysentence = new Sentence(Integer.parseInt(starttime),
					Integer.parseInt(lasttime));
			anayliseChapter(mysentence, sentence); // ����ÿ����
		}
		return mysentence;

	}

	// /<parameter=sentence>������
	// /<parameter=string>ÿ������ɵ��ַ���
	private void anayliseChapter(Sentence sentence, String mysString) {
		int ChapofStart = 0;
		int Chapoflast = 0;
		int ChapofHigh = 0;
		String text = "";

		//
		Pattern pattern = Pattern.compile("<\\d*,\\d*,\\d*>[^<]");
		Matcher matcher = pattern.matcher(mysString);
		while (matcher.find()) {
			String aString = matcher.group(0);
			String mystring = aString.replace("<", ",").replace(">", ",");
			String[] strs = mystring.split(",");
			for (int j = 0; j < strs.length; j++) {
				switch (j) {
				case 0:
					ChapofStart = Integer.parseInt(strs[j + 1]);// ��ʾʱ��
					break;
				case 1:
					Chapoflast = Integer.parseInt(strs[j + 1]);// ����ʱ��
					break;
				case 2:
					ChapofHigh = Integer.parseInt(strs[j + 1]);// ����
					max(ChapofHigh);
					min(ChapofHigh);// ����ÿ�׸��������ߺ���С����
					break;
				case 3:
					text = strs[j + 1];// ��
					break;
				default:
					break;
				}
			}

			sentence.AddChapter(ChapofStart, Chapoflast, ChapofHigh, text); // �Ѹ�����ӵ�sentece��
			sentence.AddAnotherChapter(ChapofStart, Chapoflast, ChapofHigh,
						text);
			
		}
	}

	/**
	 * ����ÿ�׸���������
	 * 
	 * @param high
	 */
	private void max(int high) {
		if (high > max) {
			max = high;
		}
	}

	/**
	 * ����ÿ�׸����С����
	 * 
	 * @param high
	 */
	private void min(int high) {
		if (high < min) {
			min = high;
		}
	}

}
