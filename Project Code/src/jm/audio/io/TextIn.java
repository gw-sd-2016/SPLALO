package jm.audio.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StreamTokenizer;
import jm.JMC;
import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;

public final class TextIn
  extends AudioObject
  implements JMC
{
  private boolean DEBUG_AUIn = false;
  private String fileName;
  private FileInputStream fis;
  private StreamTokenizer st;
  public boolean fin = false;
  
  public TextIn(Instrument paramInstrument, String paramString, int paramInt1, int paramInt2)
  {
    super(paramInstrument, paramInt1, "[TextIn]");
    this.fileName = paramString;
    try
    {
      this.fis = new FileInputStream(this.fileName);
    }
    catch (IOException localIOException)
    {
      System.out.println(localIOException);
    }
    this.channels = paramInt2;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = 0;
    int j = 1;
    float f = 0.0F;
    while (j != 0) {
      try
      {
        paramArrayOfFloat[(i++)] = this.fis.read();
        if (i >= paramArrayOfFloat.length) {
          j = 0;
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        System.exit(1);
      }
    }
    return i;
  }
  
  public void finalize()
  {
    if (this.fis != null) {
      try
      {
        this.fis.close();
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\io\TextIn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */