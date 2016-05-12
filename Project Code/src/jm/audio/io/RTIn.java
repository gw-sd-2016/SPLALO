package jm.audio.io;

import java.io.PrintStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.TargetDataLine;
import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;

public final class RTIn
  extends AudioObject
{
  public boolean finished = false;
  private int bufsize;
  private TargetDataLine dline;
  private boolean started = false;
  
  public RTIn(Instrument paramInstrument, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInstrument, paramInt1, "[RTIn]");
    this.sampleRate = paramInt1;
    this.channels = paramInt2;
    this.bufsize = paramInt3;
    init();
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    if (!this.started)
    {
      this.dline.start();
      this.started = true;
    }
    int i = 0;
    int j = 0;
    int k = paramArrayOfFloat.length * 2;
    byte[] arrayOfByte = new byte[k];
    this.dline.read(arrayOfByte, 0, k);
    while (i < paramArrayOfFloat.length)
    {
      int m = (short)((arrayOfByte[(j++)] << 8) + arrayOfByte[(j++)]);
      paramArrayOfFloat[i] = (m / 32767.0F);
      i++;
    }
    return i;
  }
  
  public void init()
  {
    AudioFormat localAudioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, this.sampleRate, 16, this.channels, this.channels * 2, this.sampleRate, true);
    DataLine.Info localInfo = new DataLine.Info(TargetDataLine.class, localAudioFormat);
    System.out.println("Setting for audio line: " + localInfo);
    if (!AudioSystem.isLineSupported(localInfo))
    {
      System.out.println(localInfo);
      System.err.println("JMF Line not supported ... exiting .. sothere");
      System.exit(1);
    }
    try
    {
      this.dline = ((TargetDataLine)AudioSystem.getLine(localInfo));
      this.dline.open(localAudioFormat, this.bufsize * 2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\io\RTIn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */