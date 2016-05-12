package jm.audio.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import jm.JMC;
import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;
import jm.music.data.Note;

public class SampleIn
  extends AudioObject
  implements JMC
{
  private File file;
  private AudioFileFormat fileFormat;
  private AudioFormat format;
  private int sampleSize;
  private String fileType;
  private boolean cache;
  private long duration;
  private InputStream is;
  private boolean wholeFile = false;
  private int loop;
  private int loopCount;
  private int loopStart;
  private int loopEnd;
  private int streamPosition;
  private boolean bigEndian;
  
  public SampleIn(Instrument paramInstrument, String paramString)
  {
    this(paramInstrument, paramString, false);
  }
  
  public SampleIn(Instrument paramInstrument, String paramString, boolean paramBoolean)
  {
    this(paramInstrument, paramString, paramBoolean, false);
  }
  
  public SampleIn(Instrument paramInstrument, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    this(paramInstrument, paramString, paramBoolean1, paramBoolean2, 0);
  }
  
  public SampleIn(Instrument paramInstrument, String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    this(paramInstrument, paramString, paramBoolean1, paramBoolean2, 0, 0, 0);
  }
  
  public SampleIn(Instrument paramInstrument, String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInstrument, 0, "[SampleIn]");
    try
    {
      this.file = new File(paramString);
      this.cache = paramBoolean1;
      this.wholeFile = paramBoolean2;
      this.loop = paramInt1;
      this.loopStart = paramInt2;
      this.loopEnd = paramInt3;
      if (this.loop == -1) {
        this.loop = Integer.MAX_VALUE;
      }
      this.fileFormat = AudioSystem.getAudioFileFormat(this.file);
      this.format = this.fileFormat.getFormat();
      this.bigEndian = this.format.isBigEndian();
      this.channels = this.format.getChannels();
      this.sampleRate = ((int)this.format.getSampleRate());
      this.duration = (this.fileFormat.getFrameLength() * this.channels);
      this.sampleSize = (this.format.getSampleSizeInBits() / 8);
      this.fileType = this.fileFormat.toString();
      this.is = AudioSystem.getAudioInputStream(this.file);
      if (this.cache)
      {
        byte[] arrayOfByte = new byte[(int)this.duration * this.sampleSize];
        this.is.read(arrayOfByte);
        this.is.close();
        this.is = new ByteArrayInputStream(arrayOfByte);
      }
    }
    catch (UnsupportedAudioFileException localUnsupportedAudioFileException) {}catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public void finalize()
  {
    try
    {
      this.is.close();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public void build()
  {
    if (!this.wholeFile) {
      this.duration = ((this.currentNote.getDuration() * this.sampleRate * this.channels));
    }
    this.loopCount = this.loop;
    reset(0);
  }
  
  public void reset(int paramInt)
  {
    this.streamPosition = 0;
    try
    {
      if (this.cache)
      {
        this.is.reset();
      }
      else
      {
        this.is = AudioSystem.getAudioInputStream(this.file);
        if (paramInt > 0) {
          this.is.read(new byte[paramInt * this.sampleSize * this.channels]);
        }
      }
    }
    catch (UnsupportedAudioFileException localUnsupportedAudioFileException)
    {
      System.out.println("jMusic SampleIn error: This file format is not supported.");
      System.exit(0);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    this.finished = false;
    byte[] arrayOfByte1 = new byte[this.sampleSize * this.channels];
    byte[] arrayOfByte2 = new byte[this.sampleSize];
    int i = 0;
    while (i < paramArrayOfFloat.length - this.channels)
    {
      try
      {
        if (this.is.read(arrayOfByte1) == -1) {
          this.finished = true;
        } else {
          for (int j = 0; j < this.channels; j++)
          {
            for (int k = 0; k < this.sampleSize; k++) {
              arrayOfByte2[k] = arrayOfByte1[(k + j * this.sampleSize)];
            }
            paramArrayOfFloat[(i + j)] = getFloat(arrayOfByte2);
            if ((++this.streamPosition == this.loopStart) && (this.loop > 0))
            {
              this.is.mark(this.loopStart);
            }
            else if ((this.streamPosition == this.loopEnd) && (this.loop > 0) && (--this.loopCount >= 1))
            {
              reset(this.loopStart);
              this.streamPosition = this.loopStart;
            }
            if (this.streamPosition >= this.duration) {
              this.finished = true;
            }
          }
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
      i += this.channels;
    }
    return paramArrayOfFloat.length;
  }
  
  public void setWholeFile(boolean paramBoolean)
  {
    this.wholeFile = paramBoolean;
  }
  
  public int getWaveSize()
  {
    return (int)(this.duration / this.channels);
  }
  
  public int getNumOfBytes()
  {
    return (int)(this.duration * this.sampleSize);
  }
  
  public int getBits()
  {
    return this.sampleSize;
  }
  
  public int getSampleSize()
  {
    return this.sampleSize;
  }
  
  public int getBitResolution()
  {
    int i = -1;
    switch (this.sampleSize)
    {
    case 1: 
      i = 8;
      break;
    case 2: 
      i = 16;
      break;
    case 3: 
      i = 24;
      break;
    case 4: 
      i = 32;
    }
    return i;
  }
  
  private float getFloat(byte[] paramArrayOfByte)
  {
    float f = 0.0F;
    int i = 0;
    int j = paramArrayOfByte.length;
    int k = 0;
    while (k < paramArrayOfByte.length)
    {
      i |= (paramArrayOfByte[k] & 0xFF) << (this.bigEndian ? j : k + 1) * 8 - 8;
      k++;
      j--;
    }
    switch (this.sampleSize)
    {
    case 1: 
      if (i > 127)
      {
        i ^= 0xFFFFFFFF;
        i &= 0x7F;
        i = (i ^ 0xFFFFFFFF) + 1;
      }
      f = i / 127.0F;
      break;
    case 2: 
      if (i > 32767)
      {
        i ^= 0xFFFFFFFF;
        i &= 0x7FFF;
        i = (i ^ 0xFFFFFFFF) + 1;
      }
      f = i / 32767.0F;
      break;
    case 3: 
      if (i > 8388607)
      {
        i ^= 0xFFFFFFFF;
        i &= 0x7FFFFF;
        i = (i ^ 0xFFFFFFFF) + 1;
      }
      f = i / 8388608.0F;
      break;
    case 4: 
      f = (float)(i / 2.147483647E9D);
      break;
    default: 
      System.err.println("Format not accepted");
    }
    return f;
  }
  
  public void setLoopStart(int paramInt)
  {
    this.loopStart = paramInt;
  }
  
  public void setLoopEnd(int paramInt)
  {
    this.loopEnd = paramInt;
  }
  
  public void setLoop(int paramInt)
  {
    this.loop = paramInt;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\io\SampleIn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */