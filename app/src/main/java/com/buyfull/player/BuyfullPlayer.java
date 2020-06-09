package com.buyfull.player;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

public class BuyfullPlayer {
    private AudioTrack  mTrack;
    public boolean setDataSource(byte[] wavFile){
        stop();
        release();
        if (wavFile == null || wavFile.length == 0){
            return false;
        }
        mTrack = new AudioTrack(AudioManager.STREAM_MUSIC,48000, AudioFormat.CHANNEL_OUT_MONO,AudioFormat.ENCODING_PCM_16BIT,wavFile.length,AudioTrack.MODE_STATIC);
        int writeResult = mTrack.write(wavFile,0,wavFile.length);
        if (writeResult == AudioTrack.ERROR_INVALID_OPERATION || writeResult == AudioTrack.ERROR_BAD_VALUE
                || writeResult == AudioTrack.ERROR_DEAD_OBJECT || writeResult == AudioTrack.ERROR) {
            //出异常情况
            release();
            return false;
        }

        mTrack.setLoopPoints( 48*1000, 48*2000,-1);

        if (mTrack.getState() == AudioTrack.STATE_INITIALIZED)
            return true;

        return false;
    }

    public void play(){
        if (mTrack != null && mTrack.getState() == AudioTrack.STATE_INITIALIZED && mTrack.getPlayState() != AudioTrack.PLAYSTATE_PLAYING){
            try{
                mTrack.play();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean isPlaying(){
        if (mTrack != null && mTrack.getState() == AudioTrack.STATE_INITIALIZED && mTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING){
            return true;
        }
        return false;
    }

    public void stop(){
        if (mTrack != null && mTrack.getState() == AudioTrack.STATE_INITIALIZED && mTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING){
            try {
                mTrack.stop();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void release(){
        if (mTrack != null && mTrack.getState() == AudioTrack.STATE_INITIALIZED){
            try {
                mTrack.release();
            }catch (Exception e){
                e.printStackTrace();
            }
            mTrack = null;
        }
    }

    public void setVolume(float volume){
        if (mTrack != null && mTrack.getState() == AudioTrack.STATE_INITIALIZED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                try {
                    mTrack.setVolume(volume);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                try {
                    mTrack.setStereoVolume(volume,volume);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }
}
