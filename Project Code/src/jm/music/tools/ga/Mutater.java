package jm.music.tools.ga;

import jm.music.data.Phrase;

public abstract class Mutater
  extends GAComponent
{
  public Mutater() {}
  
  public abstract Phrase[] mutate(Phrase[] paramArrayOfPhrase, double paramDouble, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\Mutater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */