package jm.music.tools.ga;

import jm.music.data.Phrase;

public class SimpleParentSelector
  extends ParentSelector
{
  public SimpleParentSelector() {}
  
  public Phrase[] selectParents(Phrase[] paramArrayOfPhrase, double[] paramArrayOfDouble)
  {
    Phrase[] arrayOfPhrase = new Phrase[paramArrayOfPhrase.length];
    for (int i = 0; i < paramArrayOfPhrase.length; i++) {
      arrayOfPhrase[i] = paramArrayOfPhrase[i].copy();
    }
    return arrayOfPhrase;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\SimpleParentSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */