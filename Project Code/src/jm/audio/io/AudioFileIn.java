package jm.audio.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioFileIn
{
  private String fileName;
  private File file;
  private AudioFileFormat fileFormat;
  private AudioFormat format;
  private boolean bigEndian;
  private int channels;
  private int sampleRate;
  private long duration;
  private int sampleSize;
  private AudioInputStream is;
  private float[] sampleData;
  private boolean audioFileSpecified = true;
  
  public AudioFileIn(String paramString)
  {
    this.fileName = paramString;
    try
    {
      this.file = new File(paramString);
      this.fileFormat = AudioSystem.getAudioFileFormat(this.file);
      this.format = this.fileFormat.getFormat();
      this.bigEndian = this.format.isBigEndian();
      this.channels = this.format.getChannels();
      this.sampleRate = ((int)this.format.getSampleRate());
      this.duration = (this.fileFormat.getFrameLength() * this.channels);
      this.sampleSize = (this.format.getSampleSizeInBits() / 8);
    }
    catch (UnsupportedAudioFileException localUnsupportedAudioFileException)
    {
      System.err.println("jMusic AudioFileIn warning: '" + paramString + "' may not be an audio file.");
      System.err.println("Reading it in as raw data...");
      this.audioFileSpecified = false;
      this.channels = 1;
      this.sampleSize = 1;
      this.sampleRate = 0;
    }
    catch (IOException localIOException)
    {
      System.err.println("jMusic AudioFileIn error: Cannot read the specified file: " + paramString);
      System.err.println("Most likely the file does not exist at this location. Exiting...");
      System.exit(0);
    }
  }
  
  private void readFile()
  {
    Object localObject;
    if (this.audioFileSpecified)
    {
      try
      {
        this.is = AudioSystem.getAudioInputStream(this.file);
        byte[] arrayOfByte1 = new byte[(int)this.duration * this.sampleSize];
        this.is.read(arrayOfByte1);
        this.is.close();
        localObject = new ByteArrayInputStream(arrayOfByte1);
        this.sampleData = new float[(int)this.duration];
        byte[] arrayOfByte2 = new byte[this.sampleSize];
        for (int k = 0; k < this.duration; k++) {
          if (((ByteArrayInputStream)localObject).read(arrayOfByte2) == -1) {
            System.out.println("Ran out of samples to read");
          } else {
            this.sampleData[k] = getFloat(arrayOfByte2);
          }
        }
        ((ByteArrayInputStream)localObject).close();
      }
      catch (UnsupportedAudioFileException localUnsupportedAudioFileException) {}catch (IOException localIOException1)
      {
        localIOException1.printStackTrace();
      }
    }
    else
    {
      Vector localVector = new Vector();
      try
      {
        localObject = new FileInputStream(this.fileName);
        for (j = ((FileInputStream)localObject).read(); j != -1; j = ((FileInputStream)localObject).read()) {
          localVector.addElement(new Float(j / 255.0F));
        }
        ((FileInputStream)localObject).close();
      }
      catch (IOException localIOException2)
      {
        localIOException2.printStackTrace();
        System.exit(1);
      }
      int i = localVector.size();
      this.sampleData = new float[i];
      for (int j = 0; j < i; j++) {
        this.sampleData[j] = ((Float)localVector.elementAt(j)).floatValue();
      }
    }
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
  
  public float[] getSampleData()
  {
    readFile();
    return this.sampleData;
  }
  
  public int getChannels()
  {
    return this.channels;
  }
  
  public String getFileType()
  {
    if (this.audioFileSpecified) {
      return this.fileFormat.toString();
    }
    return new String("Non-audio");
  }
  
  public int getSampleRate()
  {
    return this.sampleRate;
  }
  
  public int getSampleBitDepth()
  {
    return this.sampleSize * 8;
  }
  
  public String getEncoding()
  {
    return this.format.getEncoding().toString();
  }
  
  public int getDuration()
  {
    return (int)this.duration;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\io\AudioFileIn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */