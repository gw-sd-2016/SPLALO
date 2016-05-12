package jm.music.tools.ga;

import jm.music.data.Phrase;

public abstract class Recombiner
  extends GAComponent
{
  public Recombiner() {}
  
  public abstract Phrase[] recombine(Phrase[] paramArrayOfPhrase, double[] paramArrayOfDouble, double paramDouble, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\Recombiner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */