package jm.music.tools;

import jm.music.data.Note;
import jm.music.data.Phrase;

public final class ChordAnalysis
{
  public static final int[] RATINGS = { 1, 4, 4, 3, 2, 5, 7 };
  
  private ChordAnalysis() {}
  
  public static Possible[] getChords(Phrase paramPhrase, double paramDouble, int paramInt, int[] paramArrayOfInt)
  {
    int[][] arrayOfInt = new int[paramArrayOfInt.length][3];
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      arrayOfInt[i][0] = paramArrayOfInt[i];
      arrayOfInt[i][1] = paramArrayOfInt[((i + 2) % paramArrayOfInt.length)];
      arrayOfInt[i][2] = paramArrayOfInt[((i + 4) % paramArrayOfInt.length)];
    }
    double d1 = paramPhrase.getStartTime();
    if (d1 < 0.0D) {
      d1 = 0.0D;
    }
    double d2 = 0.0D;
    int j = 0;
    double d3 = paramPhrase.getEndTime();
    if (d3 == 0.0D) {
      return new Possible[0];
    }
    Note localNote1 = new Note();
    Note localNote2 = new Note();
    int k = paramPhrase.size();
    Possible[] arrayOfPossible = new Possible[(int)Math.ceil(d3 / paramDouble)];
    int m = 0;
    for (m = 0; m < arrayOfPossible.length; m++)
    {
      if (d2 == m * paramDouble) {
        localNote1 = paramPhrase.getNote(j);
      } else {
        localNote1 = null;
      }
      while (d2 < (m + 0.5D) * paramDouble)
      {
        d2 += paramPhrase.getNote(j).getRhythmValue();
        j++;
        if (j >= k)
        {
          localNote2 = null;
          break label328;
        }
      }
      if (d2 == (m + 0.5D) * paramDouble) {
        localNote2 = paramPhrase.getNote(j);
      } else {
        localNote2 = null;
      }
      while (d2 < (m + 1) * paramDouble)
      {
        d2 += paramPhrase.getNote(j).getRhythmValue();
        j++;
        if (j >= k) {
          break label328;
        }
      }
      arrayOfPossible[m] = firstPass(localNote1, localNote2, paramInt, paramArrayOfInt, arrayOfInt);
    }
    label328:
    arrayOfPossible[m] = firstPass(localNote1, localNote2, paramInt, paramArrayOfInt, arrayOfInt);
    return arrayOfPossible;
  }
  
  public static int[] getFirstPassChords(Phrase paramPhrase, double paramDouble, int paramInt, int[] paramArrayOfInt)
  {
    Possible[] arrayOfPossible = getChords(paramPhrase, paramDouble, paramInt, paramArrayOfInt);
    int[] arrayOfInt = new int[arrayOfPossible.length];
    for (int i = 0; i < arrayOfPossible.length; i++) {
      if (arrayOfPossible[i] != null) {
        arrayOfInt[i] = arrayOfPossible[i].getBestChord();
      } else {
        arrayOfInt[i] = 7;
      }
    }
    return arrayOfInt;
  }
  
  public static int[] getSecondPassChords(Phrase paramPhrase, double paramDouble, int paramInt, int[] paramArrayOfInt)
  {
    Possible[] arrayOfPossible = getChords(paramPhrase, paramDouble, paramInt, paramArrayOfInt);
    int[] arrayOfInt = new int[arrayOfPossible.length];
    int i = arrayOfPossible.length - 1;
    if (i < 0) {
      return new int[0];
    }
    while (arrayOfPossible[i] == null)
    {
      arrayOfInt[i] = 7;
      i--;
      if (i < 0) {
        return arrayOfInt;
      }
    }
    arrayOfInt[i] = arrayOfPossible[i].getBestChord();
    int j = arrayOfInt[i];
    i--;
    while (i > 0)
    {
      while (arrayOfPossible[i] == null)
      {
        arrayOfInt[i] = 7;
        i--;
        if (i < 1) {
          break label184;
        }
      }
      int k = (j + 4) % paramArrayOfInt.length;
      if (acceptableChange(arrayOfPossible[i].chords, k, arrayOfPossible[i].getBestChord())) {
        arrayOfInt[i] = k;
      } else {
        arrayOfInt[i] = arrayOfPossible[i].getBestChord();
      }
      j = arrayOfInt[i];
      i--;
    }
    label184:
    if (arrayOfPossible[0] == null) {
      arrayOfInt[0] = 7;
    } else {
      arrayOfInt[0] = arrayOfPossible[0].getBestChord();
    }
    return arrayOfInt;
  }
  
  private static boolean acceptableChange(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      if ((paramArrayOfInt[i] == paramInt1) && (RATINGS[paramArrayOfInt[i]] <= 2 + RATINGS[paramInt2])) {
        return true;
      }
    }
    return false;
  }
  
  private static Possible firstPass(Note paramNote1, Note paramNote2, int paramInt, int[] paramArrayOfInt, int[][] paramArrayOfInt1)
  {
    if (isBad(paramNote1, paramInt, paramArrayOfInt))
    {
      if (isBad(paramNote2, paramInt, paramArrayOfInt)) {
        return null;
      }
      return firstPassChords(paramNote2, paramInt, paramArrayOfInt, paramArrayOfInt1);
    }
    if (isBad(paramNote2, paramInt, paramArrayOfInt)) {
      return firstPassChords(paramNote1, paramInt, paramArrayOfInt, paramArrayOfInt1);
    }
    if (PhraseAnalysis.pitchToDegree(paramNote1.getPitch(), paramInt) == PhraseAnalysis.pitchToDegree(paramNote2.getPitch(), paramInt)) {
      return firstPassChords(paramNote1, paramInt, paramArrayOfInt, paramArrayOfInt1);
    }
    return firstPassChords(paramNote1, paramNote2, paramInt, paramArrayOfInt, paramArrayOfInt1);
  }
  
  private static boolean isBad(Note paramNote, int paramInt, int[] paramArrayOfInt)
  {
    if (paramNote == null) {
      return true;
    }
    if (paramNote.getPitch() == Integer.MIN_VALUE) {
      return true;
    }
    return !PhraseAnalysis.isScale(paramNote, paramInt, paramArrayOfInt);
  }
  
  private static Possible firstPassChords(Note paramNote, int paramInt, int[] paramArrayOfInt, int[][] paramArrayOfInt1)
  {
    Possible localPossible = new Possible(new int[3]);
    int i = 0;
    int j = PhraseAnalysis.pitchToDegree(paramNote.getPitch(), paramInt);
    for (int k = 0; k < paramArrayOfInt1.length; k++) {
      if (isInTriad(j, paramArrayOfInt1[k])) {
        localPossible.chords[(i++)] = k;
      }
    }
    return localPossible;
  }
  
  private static Possible firstPassChords(Note paramNote1, Note paramNote2, int paramInt, int[] paramArrayOfInt, int[][] paramArrayOfInt1)
  {
    Possible localPossible1 = firstPassChords(paramNote1, paramInt, paramArrayOfInt, paramArrayOfInt1);
    Possible localPossible2 = firstPassChords(paramNote2, paramInt, paramArrayOfInt, paramArrayOfInt1);
    Possible localPossible3 = findCommonChords(localPossible1.chords, localPossible2.chords);
    return localPossible3 == null ? localPossible1 : localPossible3;
  }
  
  private static boolean isInTriad(int paramInt, int[] paramArrayOfInt)
  {
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      if (paramArrayOfInt[i] == paramInt) {
        return true;
      }
    }
    return false;
  }
  
  private static Possible findCommonChords(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    Possible localPossible = new Possible(new int[2]);
    int i = 0;
    for (int j = 0; j < paramArrayOfInt1.length; j++) {
      for (int k = 0; k < paramArrayOfInt2.length; k++) {
        if (paramArrayOfInt1[j] == paramArrayOfInt2[k]) {
          localPossible.chords[(i++)] = paramArrayOfInt1[j];
        }
      }
    }
    if (i == 0) {
      return null;
    }
    if (i == 2) {
      return localPossible;
    }
    if (i == 1)
    {
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = localPossible.chords[0];
      return new Possible(arrayOfInt);
    }
    throw new Error("Unexpected value for index");
  }
  
  private static class Possible
  {
    int[] chords = null;
    
    Possible() {}
    
    Possible(int[] paramArrayOfInt)
    {
      this.chords = paramArrayOfInt;
    }
    
    int getBestChord()
    {
      if (this.chords == null) {
        return -1;
      }
      int i = 6;
      for (int j = 0; j < this.chords.length; j++) {
        if (ChordAnalysis.RATINGS[this.chords[j]] < ChordAnalysis.RATINGS[i]) {
          i = this.chords[j];
        }
      }
      return i;
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ChordAnalysis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */