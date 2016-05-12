package jm.audio.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import jm.JMC;
import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;

public final class SampleOut
  extends AudioObject
  implements JMC
{
  private boolean sync;
  public static float max = 0.0F;
  public static int numofchan;
  public static int samprate;
  private RandomAccessFile raf;
  private int position = 0;
  private int count;
  private ByteArrayOutputStream bos = new ByteArrayOutputStream();
  private DataOutputStream dos = new DataOutputStream(this.bos);
  private boolean DEBUG_AU = false;
  private String fileName;
  private int size = 0;
  
  public SampleOut(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[SampleOut]");
    this.sync = true;
    this.fileName = "jmusic.tmp";
    try
    {
      this.raf = new RandomAccessFile(this.fileName, "rw");
    }
    catch (IOException localIOException)
    {
      System.out.println(localIOException);
    }
  }
  
  public SampleOut(AudioObject paramAudioObject, String paramString)
  {
    super(paramAudioObject, "[SampleOut]");
    this.sync = true;
    this.fileName = paramString;
    try
    {
      this.raf = new RandomAccessFile(this.fileName, "rw");
    }
    catch (IOException localIOException)
    {
      System.out.println(localIOException);
    }
  }
  
  public SampleOut(AudioObject paramAudioObject, String paramString, boolean paramBoolean)
  {
    super(paramAudioObject, "[SampleOut]");
    this.sync = paramBoolean;
    this.fileName = paramString;
    try
    {
      this.raf = new RandomAccessFile(this.fileName, "rw");
    }
    catch (IOException localIOException)
    {
      System.out.println(localIOException);
    }
  }
  
  public SampleOut(AudioObject paramAudioObject, String paramString, int paramInt, boolean paramBoolean)
  {
    super(paramAudioObject, "[SampleOut]");
    this.sync = paramBoolean;
    this.position = paramInt;
    this.fileName = paramString;
    try
    {
      this.raf = new RandomAccessFile(this.fileName, "rw");
    }
    catch (IOException localIOException)
    {
      System.out.println(localIOException);
    }
  }
  
  public void finalize() {}
  
  public void build()
  {
    if (this.sync) {
      try
      {
        this.raf.getFD().sync();
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
    this.position = ((int)(this.currentNoteStartTime * this.sampleRate) * 4 * this.channels);
    if (this.position < 0) {
      this.position = 0;
    }
    samprate = this.sampleRate;
    numofchan = this.channels;
    this.finished = false;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    if (this.inst.iterations < 0) {
      this.finished = true;
    }
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    for (int j = 0; j < i; j++)
    {
      float f = paramArrayOfFloat[j];
      if (max < Math.abs(f)) {
        max = Math.abs(f);
      }
      try
      {
        this.dos.writeFloat(f);
      }
      catch (IOException localIOException)
      {
        throw new AOException(this.name, localIOException.toString());
      }
    }
    write(i);
    return i;
  }
  
  private void write(int paramInt)
  {
    int i = paramInt;
    ByteArrayInputStream localByteArrayInputStream1 = null;
    DataInputStream localDataInputStream1 = null;
    ByteArrayInputStream localByteArrayInputStream2 = null;
    DataInputStream localDataInputStream2 = null;
    int j = i * 4;
    try
    {
      this.raf.seek(this.position);
      float f = this.raf.readFloat();
      this.raf.seek(this.position);
      byte[] arrayOfByte = new byte[j];
      int k = this.raf.read(arrayOfByte);
      localByteArrayInputStream1 = new ByteArrayInputStream(arrayOfByte);
      localDataInputStream1 = new DataInputStream(localByteArrayInputStream1);
      localByteArrayInputStream2 = new ByteArrayInputStream(this.bos.toByteArray());
      localDataInputStream2 = new DataInputStream(localByteArrayInputStream2);
      this.bos.reset();
      float[] arrayOfFloat = new float[i];
      for (int m = 0; m < i; m++)
      {
        arrayOfFloat[m] = (localDataInputStream1.readFloat() + localDataInputStream2.readFloat());
        if (max < Math.abs(arrayOfFloat[m])) {
          max = Math.abs(arrayOfFloat[m]);
        }
        this.dos.writeFloat(arrayOfFloat[m]);
      }
      this.dos.flush();
      this.raf.seek(this.position);
      this.raf.write(this.bos.toByteArray());
      this.bos.reset();
      this.position += j;
      localDataInputStream1.close();
      localByteArrayInputStream1.close();
      localDataInputStream2.close();
      localByteArrayInputStream2.close();
    }
    catch (EOFException localEOFException)
    {
      try
      {
        this.raf.seek(this.position);
        this.raf.write(this.bos.toByteArray());
        this.bos.reset();
        this.position += j;
      }
      catch (IOException localIOException2)
      {
        localIOException2.printStackTrace();
      }
    }
    catch (IOException localIOException1)
    {
      localIOException1.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\io\SampleOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */