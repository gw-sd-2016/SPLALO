package jm.music.tools.ga;

import jm.music.data.Phrase;

public class BestNSurvivorSelector
  extends SurvivorSelector
{
  public BestNSurvivorSelector() {}
  
  public Phrase[] selectSurvivors(Phrase[] paramArrayOfPhrase1, double[] paramArrayOfDouble1, Phrase[] paramArrayOfPhrase2, double[] paramArrayOfDouble2)
  {
    Phrase[] arrayOfPhrase = new Phrase[paramArrayOfPhrase1.length];
    double[] arrayOfDouble = new double[paramArrayOfDouble1.length + paramArrayOfDouble2.length];
    for (int i = 0; i < paramArrayOfDouble1.length; i++) {
      arrayOfDouble[i] = paramArrayOfDouble1[i];
    }
    for (i = paramArrayOfDouble1.length; i < arrayOfDouble.length; i++) {
      arrayOfDouble[i] = paramArrayOfDouble2[(i - paramArrayOfDouble1.length)];
    }
    int[] arrayOfInt = new int[arrayOfPhrase.length];
    boolean[] arrayOfBoolean = new boolean[arrayOfDouble.length];
    for (int m = 0; m < arrayOfInt.length; m++)
    {
      int j = arrayOfDouble.length - 1;
      for (int n = arrayOfDouble.length - 1; n >= 0; n--) {
        if (arrayOfBoolean[n] == 0) {
          j = n;
        }
      }
      for (n = 0; n < arrayOfDouble.length; n++)
      {
        int k = 1;
        if (arrayOfDouble[n] > arrayOfDouble[j])
        {
          for (int i1 = 0; i1 < m; i1++) {
            if (n == arrayOfInt[i1]) {
              k = 0;
            }
          }
          if (k == 1) {
            j = n;
          }
        }
      }
      arrayOfInt[m] = j;
      arrayOfBoolean[j] = true;
    }
    for (m = 0; m < arrayOfInt.length; m++) {
      if (arrayOfInt[m] < paramArrayOfPhrase1.length) {
        arrayOfPhrase[m] = paramArrayOfPhrase1[arrayOfInt[m]];
      } else {
        arrayOfPhrase[m] = paramArrayOfPhrase2[(arrayOfInt[m] - paramArrayOfPhrase1.length)];
      }
    }
    return arrayOfPhrase;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\BestNSurvivorSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */