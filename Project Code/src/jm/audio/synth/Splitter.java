package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Splitter
  extends AudioObject
{
  float[] buf = null;
  int count = 0;
  int outputs = 0;
  
  public Splitter(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[Volume]");
  }
  
  public void build()
  {
    this.outputs = this.next.length;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    if (this.count == 0)
    {
      this.buf = new float[paramArrayOfFloat.length];
      this.previous[0].nextWork(this.buf);
    }
    if (++this.count == this.outputs) {
      this.count = 0;
    }
    for (int i = 0; i < this.buf.length; i++) {
      paramArrayOfFloat[i] = this.buf[i];
    }
    return this.buf.length;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Splitter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */