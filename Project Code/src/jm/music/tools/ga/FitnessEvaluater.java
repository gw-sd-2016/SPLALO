package jm.music.tools.ga;

import jm.music.data.Phrase;

public abstract class FitnessEvaluater
  extends GAComponent
{
  public FitnessEvaluater() {}
  
  public abstract double[] evaluate(Phrase[] paramArrayOfPhrase);
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\FitnessEvaluater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */