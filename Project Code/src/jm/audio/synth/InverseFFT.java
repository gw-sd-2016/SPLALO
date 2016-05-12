package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.math.RealFloatFFT_Radix2;

public final class InverseFFT
  extends AudioObject
{
  public InverseFFT(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[InverseFFT]");
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    RealFloatFFT_Radix2 localRealFloatFFT_Radix2 = new RealFloatFFT_Radix2(i);
    localRealFloatFFT_Radix2.inverse(paramArrayOfFloat);
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\InverseFFT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */