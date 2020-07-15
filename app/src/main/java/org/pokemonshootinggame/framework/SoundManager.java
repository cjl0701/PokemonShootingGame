package org.pokemonshootinggame.framework;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

//SoundManager를 통한 사운드 기반
//SingleTon 패턴 적용하여 어디서나 접근 가능하도록 작성
public class SoundManager {
    private static SoundManager s_instance;

    private SoundPool m_soundPool; //여러 사운드 동시 출력에 문제없고, 게임 제작에 적합한 SoundPool
    private HashMap m_soundPoolMap; //사운드의 빠른 탐색을 위한 해시맵
    private AudioManager m_audioManager; //사운드 관리를 위한 오디오 매니저
    private Context m_activity; //애플리케이션의 컨텍스트 값

    private SoundManager() {
        super();
    }

    public static SoundManager getInstance() {
        if (s_instance == null) s_instance = new SoundManager();
        return s_instance;
    }

    public void init(Context context) {
        //멤버 변수 생성과 초기화
        // SoundPool soundPool = new SoundPool (최대 스트림 개수, 오디오 스트림 타입, 샘플링 품질);
        m_soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        m_soundPoolMap = new HashMap();
        m_audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        m_activity = context;
    }

    public void addSound(int index, int soundID) {
        //사운드 리소스 로드: int sound = soundPool.load(콘텍스트, 리소스 아이디, 우선권);
        int id = m_soundPool.load(m_activity, soundID, 1); //사운드를 로드
        m_soundPoolMap.put(index, id); //해시맵에 아이디 값을 받아온 인덱스 저장장
    }

    //사운드 재생
    public void play(int index) {
        float streamVolume = m_audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume / m_audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //soundPool.play(실행할 사운드ID, 좌측 볼륨, 우측 볼륨, 재생 우선순위, 반복 여부, 속도);
        m_soundPool.play((Integer) m_soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
    }

    //사운드 반복 재생
    public void playLooped(int index) {
        float streamVolume = m_audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume / m_audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        m_soundPool.play((Integer) m_soundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
    }
}