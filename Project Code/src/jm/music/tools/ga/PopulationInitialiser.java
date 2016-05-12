package jm.music.tools.ga;

import jm.music.data.Phrase;

public abstract class PopulationInitialiser
  extends GAComponent
{
  public PopulationInitialiser() {}
  
  public abstract Phrase[] initPopulation(Phrase paramPhrase, int paramInt);
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\PopulationInitialiser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */