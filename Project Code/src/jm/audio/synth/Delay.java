package jm.audio.synth;

import java.util.Vector;
import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Delay
  extends AudioObject
{
  Vector storedSamples = new Vector();
  int sampleDelay = 0;
  int sampleCounter = 0;
  
  public Delay(AudioObject paramAudioObject, int paramInt)
  {
    super(paramAudioObject, "[Delay]");
    this.sampleDelay = paramInt;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    int j;
    if (this.sampleCounter >= this.sampleDelay) {
      for (j = 0; j < i; j++)
      {
        this.storedSamples.addElement(new Float(paramArrayOfFloat[j]));
        Float localFloat = (Float)this.storedSamples.elementAt(this.storedSamples.size() - 1);
        paramArrayOfFloat[j] = localFloat.floatValue();
        this.storedSamples.removeElementAt(this.storedSamples.size() - 1);
      }
    } else {
      for (j = 0; j < i; j++)
      {
        this.storedSamples.addElement(new Float(paramArrayOfFloat[j]));
        paramArrayOfFloat[j] = 0.0F;
      }
    }
    this.sampleCounter += 1;
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Delay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */