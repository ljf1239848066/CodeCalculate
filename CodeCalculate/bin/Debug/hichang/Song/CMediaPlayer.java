/*
- * .CMediaPlayer.java
 * class:CMediaPlayer
 */
package hichang.Song;

import java.io.IOException;

import android.media.MediaPlayer;
/**
 * CMediaPlayer
 * ����˵��:ģ���õ��ĳ���MediaPlayer�еķ��������з�װ���˴�����ʵ��˫�������Ĳ���
 */
public class CMediaPlayer extends MediaPlayer {
	/**
	 * position����:��¼��ǰ����λ�ã�int����
	 */
	private int position;
	/**
	 * mediaOrigianl����:MediaPlayer��ʵ�������ڲ���ԭ��
	 */
	public MediaPlayer mediaOriginal;
	/**
	 * mediaAccompany����:MediaPlayer��ʵ�������ڲ��Ű���
	 */
	private MediaPlayer mediaAccompany;
	/**
	 * pathOriginal:ԭ�������ļ�·��
	 */
	private String pathOriginal;
	/**
	 * pathAccompany:�����ļ�·��
	 */
	private String pathAccompany;
    /**
     * isAccompany:��¼��ǰ�Ƿ��ǲ��Ű��ֵ࣬Ϊtrue��ʱ���ʾ���Ű���
     */
	private boolean isAccompany;
	/**
	 * isOriginal:��ǰ�Ƿ񲥷ŵ�ԭ����true��ʾԭ���ڲ���
	 */
	private boolean isOriginal;
	/**
	 * volume:��ǰ����ֵ��float����
	 */
	private float volume;
	/**
	 * CMediaPlayer��Ĺ��캯������ɳ�ʼ���Ĺ���
	 * <p>��������:�ڴ˹��췽���У�����ԭ���Ͱ���Ĳ�������������������ʼֵΪ0.3</p>
	 */
	public CMediaPlayer(){
		mediaOriginal = new MediaPlayer();
		mediaAccompany = new MediaPlayer();
		isOriginal = false;
		isAccompany = false;
		volume = 0.30f;
	}
			
	/**
	 * ��ʼ���������������ļ�·��
	 * <p>�Խ����Ĳ��������г�ʼ���������ļ�·��</p>
	 * @param pathOriginal ԭ�����ļ�·��
	 * @param pathAccompany ������ļ�·��
	 */
	public void CSetDataSource(String pathOriginal,String pathAccompany){
		this.pathOriginal = pathOriginal;
		this.pathAccompany = pathAccompany;
		//׼��·��
		try {
			mediaOriginal.reset();
			mediaOriginal.setDataSource(this.pathOriginal);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			mediaAccompany.reset();
			mediaAccompany.setDataSource(this.pathAccompany);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �Բ���������׼������
	 * <p>���չٷ��ṩ��MediaPlayer�ṩ��MediaPlayer�������ڽ����޸ĵ�ͬ������</p>
	 */
	public void CPrepare(){
		try {
			mediaOriginal.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		try {
			mediaAccompany.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
			
	/**
	 * ��ʼ����
	 * <p>�˷�������ǰ��ǵ����Ⱥ����CMediaPlayer��CSetDataSource��CPrepare��������ʼ������Ĭ���ǲ��Ű���</p>
	 * 
	 */
	public void CStart() {
			mediaAccompany.start();
			mediaAccompany.setVolume(volume,volume);
			//���Ű��ཫisAccompany����Ϊtrue����ʾ��ǰ���ŵ��ǰ���
			isAccompany = true;
			//������˫������ʵ�֣���ʱ��ԭ���Ĳ�������������
			mediaOriginal.setVolume(0,0);
			mediaOriginal.start();
	}
	
	/**
	 * ����ԭ��
	 * �������ϲ��е��ô˷�����ʵ���л���ԭ��
	 */
	public void CSetOriginal(){
        //��ԭ��������������
		mediaOriginal.setVolume(volume,volume);
		//isOriginal����Ϊtrue����ʾ��ǰ���ŵ���ԭ��
		isOriginal = true;
		//�������������
		mediaAccompany.setVolume(0,0);
	    //isAccompany����Ϊfalse����ʾ��ǰû�в��Ű���
		isAccompany = false;
	}
	
	/**
	 * ���ð���
	 * �������ϲ��е��ô˷�����ʵ���л�������
	 */
	public void CSetAccompany(){
		//�˷����ı�д����ͬCSetOriginal()
		mediaAccompany.setVolume(volume,volume);
		isAccompany = true;
		mediaOriginal.setVolume(0,0);
		isOriginal = false;
	}
	
	/**
	 * ����ѭ������
	 * �������ϲ��е��ô˷�����ʵ���л���ѭ������ģʽ
	 */
	public void CSetLooping(){
		mediaAccompany.setLooping(true);
		mediaOriginal.setLooping(true);
	}
	/**
	 * ���ò�ѭ������
	 * �������ϲ��е��ô˷�����ʵ���л���ѭ���ǲ���ģʽ
	 */
	public void CSetNotLooping(){
		mediaAccompany.setLooping(false);
		mediaOriginal.setLooping(false);
	}
	/**
	 * ��ȡ��ǰ�Ƿ���ѭ������ģʽ
	 * @return true��ʾ��ǰΪѭ������ģʽ��false��ʾ��ѭ��ģʽ
	 */
	public boolean CGetIsLooping(){
		return (mediaAccompany.isLooping()&&mediaOriginal.isLooping());
	}
	
	/**
	 * ��ȡ��ǰ�����ĳ��ȣ�ʱ�䵥λΪms
	 * @return ���׸��ʱ��
	 */
	public int CGetDuration(){
		return mediaAccompany.getDuration();
	}
	
	/**
	 * ��ȡ��ǰ���ŵ�λ��
	 * @return ��ǰ�������ŵ�λ��
	 */
	public int CGetCurrentPosition(){
		position =  mediaAccompany.getCurrentPosition();
		return position;
	}
	
	/**
	 * ���ø����Ĳ���λ��
	 * @param pos ������Ҫ�������ŵ�λ��
	 */
	public void CSeekTo(int pos){
		mediaAccompany.seekTo(pos);
		mediaOriginal.seekTo(pos);
	}
	
	/**
	 *����ý������ 
	 * @param vol��Ҫ���õ�����ֵ
	 */
	public void CSetVolume(float vol){
		this.volume = vol;
		if((!isAccompany)&&(!isOriginal)){
			mediaAccompany.setVolume(volume,volume);
			mediaOriginal.setVolume(volume,volume);
		}
		if((!isAccompany)&&(isOriginal)){
			mediaOriginal.setVolume(volume,volume);
		}
		if((!isOriginal)&&(isAccompany)){
			mediaAccompany.setVolume(volume,volume);
		}
	}
	
	public float CGetVolume(){
		return volume;
	}
	
	/**
	 * ��ͣ����
	 */
	public void CPause(){
		mediaAccompany.pause();
		mediaOriginal.pause();
	}
	/**
	 * ��ͣ���������,pause�󲥷�
	 */
	public void CReStart(){
		mediaAccompany.start();
		mediaOriginal.start();
	}
	/**
	 * stop����,stop��Ҫ�ǵ�����·��CSetDataSource��Ȼ�����prepare()
	 * CStart()*/
	public void CStop(){
		mediaAccompany.stop();
		mediaOriginal.stop();
	}
	/**
	 * CMediaPlayer����ʱһ���ǵ�release
	 */
	public void CRelease(){
		mediaAccompany.release();
		mediaOriginal.release();
	}
	
	/**
	 * CMediaPlayer��CRelease���֮���ٴ�ʹ����ҪCReset
	 */
	public void CReset(){
		mediaAccompany.reset();
		mediaOriginal.reset();
	}
}
