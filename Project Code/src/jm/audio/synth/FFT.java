package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;
import jm.audio.math.RealFloatFFT_Radix2;

public final class FFT
  extends AudioObject
{
  public FFT(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[FFT]");
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    RealFloatFFT_Radix2 localRealFloatFFT_Radix2 = null;
    localRealFloatFFT_Radix2 = new RealFloatFFT_Radix2(this.inst.getBufSize());
    localRealFloatFFT_Radix2.transform(paramArrayOfFloat);
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\FFT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */