package jm.music.tools.ga;

import jm.music.data.Note;
import jm.music.data.Phrase;

public class OnePointCrossover
  extends Recombiner
{
  private static final int ROUNDS = 10;
  private static final int ELITISM_CONSTANT = 2;
  
  public OnePointCrossover() {}
  
  public Phrase[] recombine(Phrase[] paramArrayOfPhrase, double[] paramArrayOfDouble, double paramDouble, int paramInt1, int paramInt2)
  {
    Phrase[] arrayOfPhrase = paramArrayOfPhrase.length - 2 >= 0 ? new Phrase[paramArrayOfPhrase.length - 2] : new Phrase[0];
    int i;
    int j;
    int k;
    int m;
    int n;
    int i1;
    if (arrayOfPhrase.length > 1) {
      for (i = 1; i < arrayOfPhrase.length; i += 2)
      {
        j = selectTournamentVictor(paramArrayOfDouble, -1);
        k = selectTournamentVictor(paramArrayOfDouble, j);
        m = (int)paramArrayOfPhrase[j].getEndTime() / paramInt2;
        n = (int)paramArrayOfPhrase[k].getEndTime() / paramInt2;
        i1 = m > n ? n : m;
        for (int i2 = 0; i2 < (int)(paramDouble / paramInt2) + 1; i2 = (int)(Math.random() * i1)) {}
        arrayOfPhrase[(i - 1)] = crossover(i2, paramArrayOfPhrase[j], paramArrayOfPhrase[k], true, paramInt2);
        arrayOfPhrase[i] = crossover(i2, paramArrayOfPhrase[j], paramArrayOfPhrase[k], false, paramInt2);
      }
    }
    if ((int)(arrayOfPhrase.length / 2.0D) != Math.round(arrayOfPhrase.length / 2.0D))
    {
      i = selectTournamentVictor(paramArrayOfDouble, -1);
      j = selectTournamentVictor(paramArrayOfDouble, i);
      k = (int)paramArrayOfPhrase[i].getEndTime() / paramInt2;
      m = (int)paramArrayOfPhrase[j].getEndTime() / paramInt2;
      n = k > m ? m : k;
      i1 = (int)(Math.random() * n);
      arrayOfPhrase[(arrayOfPhrase.length - 1)] = crossover(i1, paramArrayOfPhrase[i], paramArrayOfPhrase[j], true, paramInt2);
    }
    return arrayOfPhrase;
  }
  
  private int selectTournamentVictor(double[] paramArrayOfDouble, int paramInt)
  {
    for (int i = paramInt; i == paramInt; i = (int)(Math.random() * paramArrayOfDouble.length)) {}
    for (int j = 0; j < 10; j++)
    {
      for (int k = i; (k == i) || (k == paramInt); k = (int)(Math.random() * paramArrayOfDouble.length)) {}
      if (paramArrayOfDouble[k] > paramArrayOfDouble[i]) {
        return k;
      }
    }
    return i;
  }
  
  private Phrase crossover(int paramInt1, Phrase paramPhrase1, Phrase paramPhrase2, boolean paramBoolean, int paramInt2)
  {
    Phrase localPhrase1 = new Phrase();
    Phrase localPhrase2 = paramBoolean ? paramPhrase2 : paramPhrase1;
    int i = 0;
    while (localPhrase1.getEndTime() + localPhrase2.getNote(i).getRhythmValue() < paramInt1 * paramInt2) {
      localPhrase1.addNote(localPhrase2.getNote(i++).copy());
    }
    double d1 = paramInt1 * paramInt2 - localPhrase1.getEndTime();
    localPhrase1.addNote(new Note(localPhrase2.getNote(i).getPitch(), paramInt1 * paramInt2 - localPhrase1.getEndTime()));
    i = -1;
    localPhrase2 = paramBoolean ? paramPhrase1 : paramPhrase2;
    for (double d2 = 0.0D; d2 <= paramInt1 * paramInt2; d2 += localPhrase2.getNote(i).getRhythmValue()) {
      i++;
    }
    localPhrase1.addNote(new Note(localPhrase2.getNote(i++).getPitch(), d2 - paramInt1 * paramInt2));
    while (i < localPhrase2.size()) {
      localPhrase1.addNote(localPhrase2.getNote(i++));
    }
    return localPhrase1;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\OnePointCrossover.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */