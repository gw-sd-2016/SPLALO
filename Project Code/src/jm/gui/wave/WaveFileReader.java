package jm.gui.wave;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import jm.JMC;

public class WaveFileReader
  implements JMC
{
  private File file;
  private AudioFileFormat fileFormat;
  private AudioFormat format;
  private int bits;
  private String fileType;
  private boolean cache;
  private long duration;
  private InputStream is;
  private boolean wholeFile = false;
  private boolean bigEndian;
  private int channels;
  private int sampleRate;
  
  public WaveFileReader(String paramString)
  {
    try
    {
      this.file = new File(paramString);
      this.fileFormat = AudioSystem.getAudioFileFormat(this.file);
      this.format = this.fileFormat.getFormat();
      this.bigEndian = this.format.isBigEndian();
      this.channels = this.format.getChannels();
      this.sampleRate = ((int)this.format.getSampleRate());
      this.duration = (this.fileFormat.getFrameLength() * this.channels);
      this.bits = (this.format.getSampleSizeInBits() / 8);
      this.fileType = this.fileFormat.toString();
      this.is = AudioSystem.getAudioInputStream(this.file);
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
  
  public float[] getSamples(int paramInt1, int paramInt2)
  {
    float[] arrayOfFloat = new float[paramInt1];
    try
    {
      this.is = AudioSystem.getAudioInputStream(this.file);
      this.is.read(new byte[paramInt2 * this.bits]);
      byte[] arrayOfByte1 = new byte[this.bits * paramInt1];
      this.is.read(arrayOfByte1);
      int i = 0;
      int j = 0;
      while (i < paramInt1)
      {
        byte[] arrayOfByte2 = new byte[this.bits];
        for (int k = 0; k < this.bits; k++) {
          arrayOfByte2[k] = arrayOfByte1[(j++)];
        }
        arrayOfFloat[i] = getFloat(arrayOfByte2);
        i++;
      }
    }
    catch (UnsupportedAudioFileException localUnsupportedAudioFileException)
    {
      System.out.println("jMusic WaveFileReader error: This file format is not supported.");
      System.exit(0);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return arrayOfFloat;
  }
  
  public int getWaveSize()
  {
    return (int)(this.duration / this.channels);
  }
  
  public int getNumOfBytes()
  {
    return (int)(this.duration * this.bits);
  }
  
  public int getBits()
  {
    return this.bits;
  }
  
  public int getChannels()
  {
    return this.channels;
  }
  
  public int getSampleRate()
  {
    return this.sampleRate;
  }
  
  public int getBitResolution()
  {
    int i = -1;
    switch (this.bits)
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
    switch (this.bits)
    {
    case 1: 
      if (i > 127)
      {
        i = (i ^ 0xFFFFFFFF) + 1;
        i &= 0x7F;
        i = (i ^ 0xFFFFFFFF) + 1;
      }
      f = i / 127.0F;
      break;
    case 2: 
      if (i > 32767)
      {
        i = (i ^ 0xFFFFFFFF) + 1;
        i &= 0x7FFF;
        i = (i ^ 0xFFFFFFFF) + 1;
      }
      f = i / 32767.0F;
      break;
    case 3: 
      if (i > 8388607)
      {
        i = (i ^ 0xFFFFFFFF) + 1;
        i &= 0x7FFFFF;
        i = (i ^ 0xFFFFFFFF) + 1;
      }
      f = i / 8388608.0F;
      break;
    case 4: 
      f = i / 2.14748365E9F;
      break;
    default: 
      System.err.println("Format not accepted");
    }
    return f;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\wave\WaveFileReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */