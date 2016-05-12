package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Wavelet
  extends AudioObject
{
  public Wavelet(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[FGT]");
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    return paramArrayOfFloat.length;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Wavelet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */