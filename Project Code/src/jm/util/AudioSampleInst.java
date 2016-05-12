package jm.util;

import jm.audio.Instrument;
import jm.audio.io.SampleIn;

public final class AudioSampleInst
  extends Instrument
{
  private String fileName;
  
  public AudioSampleInst(String paramString)
  {
    this.fileName = paramString;
  }
  
  public void createChain()
  {
    SampleIn localSampleIn = new SampleIn(this, this.fileName, true, true);
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\util\AudioSampleInst.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */