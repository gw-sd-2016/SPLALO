package jm.music.tools.ga;

import jm.music.data.Phrase;

public abstract class ParentSelector
  extends GAComponent
{
  public ParentSelector() {}
  
  public abstract Phrase[] selectParents(Phrase[] paramArrayOfPhrase, double[] paramArrayOfDouble);
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\ParentSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */