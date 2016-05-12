package jm.audio;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.SourceDataLine;
import jm.music.rt.RTLine;

public class RTMixer
  implements AudioChainListener
{
  private int totLines = 0;
  private int count = 0;
  private float[] sampleArray;
  private ByteArrayOutputStream bos;
  private DataOutputStream dos;
  private SourceDataLine dline;
  protected int sampleRate;
  protected int channels;
  public long currentTime = 0L;
  protected double controlRate;
  private double scorePosition = 0.0D;
  private RTLine[] rtlines;
  private int bufferSize;
  
  public RTMixer(RTLine[] paramArrayOfRTLine)
  {
    this(paramArrayOfRTLine, 0.1D);
  }
  
  public RTMixer(RTLine[] paramArrayOfRTLine, double paramDouble)
  {
    this.rtlines = paramArrayOfRTLine;
    this.sampleRate = paramArrayOfRTLine[0].getSampleRate();
    this.channels = paramArrayOfRTLine[0].getChannels();
    this.controlRate = paramDouble;
    for (this.bufferSize = ((int)(this.sampleRate * this.channels * this.controlRate)); this.bufferSize % 4 != 0; this.bufferSize = ((int)(this.sampleRate * this.channels * this.controlRate))) {
      this.controlRate += 0.001D;
    }
    for (int i = 0; i < paramArrayOfRTLine.length; i++)
    {
      this.totLines += this.rtlines[i].getNumLines();
      this.rtlines[i].setBufferSize(this.bufferSize);
      if (paramArrayOfRTLine[i].getSampleRate() != this.sampleRate)
      {
        System.err.println("jMusic RTMixer error: All instruments must have the same sample rate.");
        System.exit(0);
      }
      if (paramArrayOfRTLine[i].getChannels() != this.channels)
      {
        System.err.println("jMusic RTMixer error: All instruments must have the same number of channels.");
        System.exit(0);
      }
    }
    initJMFSound(this.bufferSize);
    this.bos = new ByteArrayOutputStream();
    this.dos = new DataOutputStream(this.bos);
  }
  
  public void addLines(RTLine[] paramArrayOfRTLine)
  {
    RTLine[] arrayOfRTLine = new RTLine[this.totLines + paramArrayOfRTLine.length];
    for (int i = 0; i < this.rtlines.length; i++) {
      arrayOfRTLine[i] = this.rtlines[i];
    }
    for (i = this.rtlines.length; i < arrayOfRTLine.length; i++)
    {
      if (this.rtlines[i].getSampleRate() != this.sampleRate)
      {
        System.err.println("jMusic RTMixer error: All instruments must have the same sample rate.");
        System.exit(0);
      }
      if (this.rtlines[i].getChannels() != this.channels)
      {
        System.err.println("jMusic RTMixer error: All instruments must have the same number of channels.");
        System.exit(0);
      }
      arrayOfRTLine[i] = paramArrayOfRTLine[(i - this.rtlines.length)];
      this.totLines += this.rtlines[i].getNumLines();
      arrayOfRTLine[i].setBufferSize(this.bufferSize);
    }
    this.rtlines = arrayOfRTLine;
  }
  
  public synchronized void controlChange(float[] paramArrayOfFloat, int paramInt, boolean paramBoolean)
  {
    for (int i = 0; i < paramInt; i++) {
      this.sampleArray[i] += paramArrayOfFloat[i];
    }
    if (++this.count == this.totLines)
    {
      this.scorePosition += this.controlRate;
      for (i = 0; i < this.rtlines.length; i++)
      {
        Instrument[] arrayOfInstrument = this.rtlines[i].getInstrument();
        for (int j = 0; j < arrayOfInstrument.length; j++) {
          arrayOfInstrument[j].release();
        }
      }
      this.count = 0;
      writeOutAudio(this.sampleArray.length);
    }
  }
  
  public void begin()
  {
    this.sampleArray = new float[this.bufferSize];
    for (int i = 0; i < this.rtlines.length; i++) {
      this.rtlines[i].start(this);
    }
  }
  
  public void pause()
  {
    for (int i = 0; i < this.rtlines.length; i++) {
      this.rtlines[i].pause();
    }
  }
  
  public void unPause()
  {
    for (int i = 0; i < this.rtlines.length; i++) {
      this.rtlines[i].unPause();
    }
  }
  
  public void stop()
  {
    for (int i = 0; i < this.rtlines.length; i++) {
      this.rtlines[i].stop();
    }
  }
  
  public void actionLines(Object paramObject, int paramInt)
  {
    for (int i = 0; i < this.rtlines.length; i++) {
      this.rtlines[i].externalAction(paramObject, paramInt);
    }
  }
  
  private void writeOutAudio(int paramInt)
  {
    this.bos.reset();
    for (int i = 0; i < paramInt; i++)
    {
      if (this.totLines > 1) {
        this.sampleArray[i] /= this.totLines * 0.75F;
      }
      try
      {
        this.dos.writeShort((short)(int)(this.sampleArray[i] * 32767.0F));
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
      this.sampleArray[i] = 0.0F;
    }
    i = this.dline.write(this.bos.toByteArray(), 0, this.bos.size());
    this.currentTime += paramInt;
  }
  
  private void initJMFSound(int paramInt)
  {
    AudioFormat localAudioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, this.sampleRate, 16, this.channels, this.channels * 2, this.sampleRate, true);
    DataLine.Info localInfo = new DataLine.Info(SourceDataLine.class, localAudioFormat);
    if (!AudioSystem.isLineSupported(localInfo))
    {
      System.out.println(localInfo);
      System.err.println("jMusic RTMixer error: JMF Line not supported. Real time audio must be 16 bit stereo ... exiting .. sorry :(");
      System.exit(1);
    }
    try
    {
      this.dline = ((SourceDataLine)AudioSystem.getLine(localInfo));
      this.dline.open(localAudioFormat, paramInt * 8);
      this.dline.start();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void finalize()
  {
    System.out.println("RTMixer finalizing...");
    try
    {
      this.dos.close();
      this.bos.close();
    }
    catch (IOException localIOException) {}
    this.dline.stop();
    this.dline.close();
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\RTMixer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */