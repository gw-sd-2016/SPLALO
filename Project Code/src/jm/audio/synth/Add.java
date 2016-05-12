package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Add
  extends AudioObject
{
  public Add(AudioObject[] paramArrayOfAudioObject)
  {
    super(paramArrayOfAudioObject, "[Add]");
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    float[][] arrayOfFloat = new float[this.inputs][];
    arrayOfFloat[0] = new float[paramArrayOfFloat.length];
    int i = this.previous[0].nextWork(arrayOfFloat[0]);
    for (int j = 1; j < this.inputs; j++)
    {
      arrayOfFloat[j] = new float[i];
      if (i != this.previous[j].nextWork(arrayOfFloat[j])) {
        throw new AOException(this.name, 0);
      }
    }
    for (j = 0; j < i; j++) {
      for (int k = 0; k < this.inputs; k++) {
        paramArrayOfFloat[j] += arrayOfFloat[k][j];
      }
    }
    return j;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Add.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */