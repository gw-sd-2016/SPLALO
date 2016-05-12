package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;

public final class NoteBuffer
  extends AudioObject
{
  private float[] noteBuffer;
  private boolean flag = true;
  private int noteBufferPosition;
  
  public NoteBuffer(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[Volume]");
  }
  
  public void build()
  {
    this.noteBuffer = new float[this.numOfSamples];
    this.noteBufferPosition = 0;
    this.flag = true;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    if (this.flag)
    {
      i = this.previous[0].nextWork(this.noteBuffer);
      this.flag = false;
    }
    int i = 0;
    int j = this.noteBufferPosition + paramArrayOfFloat.length < this.numOfSamples ? this.numOfSamples - this.noteBufferPosition : paramArrayOfFloat.length;
    while (i < j)
    {
      paramArrayOfFloat[i] = this.noteBuffer[(this.noteBufferPosition++)];
      i++;
    }
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\NoteBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */