package jm.music.tools.ga;

import jm.music.data.Phrase;

public class ElitismSurvivorSelector
  extends SurvivorSelector
{
  private static final int ELITISM_CONSTANT = 2;
  
  public ElitismSurvivorSelector() {}
  
  public Phrase[] selectSurvivors(Phrase[] paramArrayOfPhrase1, double[] paramArrayOfDouble1, Phrase[] paramArrayOfPhrase2, double[] paramArrayOfDouble2)
  {
    Phrase[] arrayOfPhrase = new Phrase[paramArrayOfPhrase1.length];
    int[] arrayOfInt = new int[2];
    int i = -1;
    boolean[] arrayOfBoolean = new boolean[paramArrayOfDouble1.length];
    for (int k = 0; k < arrayOfInt.length; k++)
    {
      i = paramArrayOfDouble1.length - 1;
      for (int m = paramArrayOfDouble1.length - 1; m >= 0; m--) {
        if (arrayOfBoolean[m] == 0) {
          i = m;
        }
      }
      for (m = 0; m < paramArrayOfDouble1.length - 1; m++)
      {
        int j = 1;
        if (paramArrayOfDouble1[m] > paramArrayOfDouble1[i])
        {
          for (int n = 0; n < k; n++) {
            if (m == arrayOfInt[n]) {
              j = 0;
            }
          }
          if (j == 1) {
            i = m;
          }
        }
        arrayOfInt[k] = i;
        arrayOfBoolean[i] = true;
      }
    }
    for (k = 0; k < arrayOfInt.length; k++) {
      arrayOfPhrase[k] = paramArrayOfPhrase1[arrayOfInt[k]];
    }
    for (k = 0; k < arrayOfPhrase.length - 2; k++) {
      arrayOfPhrase[(k + 2)] = paramArrayOfPhrase2[k];
    }
    return arrayOfPhrase;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\ElitismSurvivorSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */