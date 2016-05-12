package jm.audio.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioFileOut
{
  private String fileName;
  private File file;
  private AudioFileFormat.Type fileType = AudioFileFormat.Type.AU;
  private AudioFormat format;
  private boolean bigEndian;
  private int channels;
  private int sampleRate;
  private long duration;
  private int sampleSize;
  private AudioInputStream ais;
  private float[] sampleData;
  
  public AudioFileOut(float[] paramArrayOfFloat, String paramString)
  {
    this(paramArrayOfFloat, paramString, 1, 44100, 16);
  }
  
  public AudioFileOut(float[] paramArrayOfFloat, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    this.sampleData = paramArrayOfFloat;
    this.duration = paramArrayOfFloat.length;
    this.fileName = paramString;
    this.channels = paramInt1;
    this.sampleRate = paramInt2;
    this.sampleSize = (paramInt3 / 8);
    if (paramString.endsWith(".au"))
    {
      this.fileType = AudioFileFormat.Type.AU;
      this.bigEndian = true;
    }
    else if (paramString.endsWith(".wav"))
    {
      this.fileType = AudioFileFormat.Type.WAVE;
      this.bigEndian = false;
    }
    else if ((paramString.endsWith(".aif")) || (paramString.endsWith(".aiff")))
    {
      this.fileType = AudioFileFormat.Type.AIFF;
      this.bigEndian = true;
    }
    else
    {
      paramString = paramString + ".au";
      this.bigEndian = true;
    }
    this.file = new File(this.fileName);
    byte[] arrayOfByte = new byte[paramArrayOfFloat.length * this.sampleSize];
    for (int i = 0; i < paramArrayOfFloat.length; i++)
    {
      int j = -1;
      switch (this.sampleSize)
      {
      case 1: 
        arrayOfByte[i] = new Float(paramArrayOfFloat[i] * 127.0F).byteValue();
        break;
      case 2: 
        int k = new Float(paramArrayOfFloat[i] * 32767.0F).shortValue();
        if (this.bigEndian)
        {
          arrayOfByte[(i * 2)] = ((byte)((k & 0xFF00) >> 8));
          arrayOfByte[(i * 2 + 1)] = ((byte)(k & 0xFF));
        }
        else
        {
          arrayOfByte[(i * 2)] = ((byte)(k & 0xFF));
          arrayOfByte[(i * 2 + 1)] = ((byte)((k & 0xFF00) >> 8));
        }
        break;
      case 3: 
        j = new Float(paramArrayOfFloat[i] * 8388608.0F).intValue();
        if (this.bigEndian)
        {
          arrayOfByte[(i * 3)] = ((byte)((j & 0xFF0000) >> 16));
          arrayOfByte[(i * 3 + 1)] = ((byte)((j & 0xFF00) >> 8));
          arrayOfByte[(i * 3 + 2)] = ((byte)(j & 0xFF));
        }
        else
        {
          arrayOfByte[(i * 3)] = ((byte)(j & 0xFF));
          arrayOfByte[(i * 3 + 1)] = ((byte)((j & 0xFF00) >> 8));
          arrayOfByte[(i * 3 + 2)] = ((byte)((j & 0xFF0000) >> 16));
        }
        break;
      case 4: 
        j = new Float(paramArrayOfFloat[i] * 2.14748365E9F).intValue();
        if (this.bigEndian)
        {
          arrayOfByte[(i * 4)] = ((byte)((j & 0xFF000000) >> 24));
          arrayOfByte[(i * 4 + 1)] = ((byte)((j & 0xFF0000) >> 16));
          arrayOfByte[(i * 4 + 2)] = ((byte)((j & 0xFF00) >> 8));
          arrayOfByte[(i * 4 + 3)] = ((byte)(j & 0xFF));
        }
        else
        {
          arrayOfByte[(i * 4)] = ((byte)(j & 0xFF));
          arrayOfByte[(i * 4 + 1)] = ((byte)((j & 0xFF00) >> 8));
          arrayOfByte[(i * 4 + 2)] = ((byte)((j & 0xFF0000) >> 16));
          arrayOfByte[(i * 4 + 3)] = ((byte)((j & 0xFF000000) >> 24));
        }
        break;
      default: 
        System.err.println("jMusic AudioFileOut error: " + paramInt3 + " bit audio output file format not supported, sorry :(");
        System.exit(0);
      }
    }
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
    this.format = new AudioFormat(this.sampleRate, paramInt3, this.channels, true, this.bigEndian);
    AudioInputStream localAudioInputStream = new AudioInputStream(localByteArrayInputStream, this.format, this.duration / this.channels);
    try
    {
      AudioSystem.write(localAudioInputStream, this.fileType, this.file);
    }
    catch (IOException localIOException)
    {
      System.out.println("error writing audio file.");
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\io\AudioFileOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */