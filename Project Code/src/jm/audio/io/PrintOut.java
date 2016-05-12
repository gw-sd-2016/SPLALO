package jm.audio.io;

import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import jm.JMC;
import jm.audio.AOException;
import jm.audio.AudioObject;

public final class PrintOut
  extends AudioObject
  implements JMC
{
  private int width;
  
  public PrintOut(AudioObject paramAudioObject)
  {
    this(paramAudioObject, 80);
  }
  
  public PrintOut(AudioObject paramAudioObject, int paramInt)
  {
    super(paramAudioObject, "[PrintOut]");
    this.width = paramInt;
    try
    {
      RandomAccessFile localRandomAccessFile = new RandomAccessFile("jmusic.tmp", "rw");
      try
      {
        localRandomAccessFile.close();
      }
      catch (IOException localIOException2) {}
    }
    catch (IOException localIOException1) {}
  }
  
  public void finalize() {}
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    for (int j = 0; j < i; j++)
    {
      float f = paramArrayOfFloat[j];
      String str = "";
      if (j % ((int)(this.sampleRate / 8000.0D) + 1) == 0)
      {
        int k = 0;
        for (int m = 0; m < (int)((f + 1.0D) * (this.width * 0.5D - 4.0D)); m++)
        {
          str = str + " ";
          k++;
        }
        str = str + "o";
        for (m = k; m < this.width - 4; m++) {
          str = str + " ";
        }
        f *= 1000.0F;
        str = str + (int)f / 1000.0D;
        System.out.println(str);
      }
    }
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\io\PrintOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */