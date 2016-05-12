package jm.music.tools.ga;

import jm.music.data.Phrase;

public abstract class SurvivorSelector
  extends GAComponent
{
  public SurvivorSelector() {}
  
  public abstract Phrase[] selectSurvivors(Phrase[] paramArrayOfPhrase1, double[] paramArrayOfDouble1, Phrase[] paramArrayOfPhrase2, double[] paramArrayOfDouble2);
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\SurvivorSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */