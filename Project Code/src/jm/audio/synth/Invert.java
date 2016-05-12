package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Invert
  extends AudioObject
{
  public Invert(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[Invert]");
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    for (int j = 0; j < i; j++) {
      paramArrayOfFloat[j] *= -1.0F;
    }
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Invert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */