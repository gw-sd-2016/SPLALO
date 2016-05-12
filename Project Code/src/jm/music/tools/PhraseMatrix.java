package jm.music.tools;

import java.io.PrintStream;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Phrase;

public final class PhraseMatrix
  implements JMC
{
  private AdaptiveMatrix pitchAM;
  private AdaptiveMatrix rhythmAM;
  private AdaptiveMatrix dynamicAM;
  private int pitchDepth;
  private int rhythmDepth;
  private int dynamicDepth;
  private Note[] notes;
  private final double[] rhythmMap = { 4.0D, 3.0D, 2.0D, 1.0D, 0.6666666666666666D, 1.5D, 0.5D, 0.75D, 0.3333333333333333D, 0.25D, 0.125D };
  
  public PhraseMatrix(Phrase paramPhrase, int paramInt)
  {
    this(paramPhrase, paramInt, paramInt, paramInt);
  }
  
  public PhraseMatrix(Phrase paramPhrase, int paramInt1, int paramInt2, int paramInt3)
  {
    this.pitchDepth = paramInt1;
    this.rhythmDepth = paramInt2;
    this.dynamicDepth = paramInt3;
    this.notes = paramPhrase.getNoteArray();
    calcPitch();
    calcRhythm();
    calcDynamic();
  }
  
  public void calcPitch()
  {
    int[] arrayOfInt = new int[this.notes.length];
    for (int i = 0; i < this.notes.length; i++) {
      arrayOfInt[i] = this.notes[i].getPitch();
    }
    this.pitchAM = new AdaptiveMatrix(arrayOfInt, this.pitchDepth, 127);
  }
  
  public void calcRhythm()
  {
    int[] arrayOfInt = new int[this.notes.length];
    for (int i = 0; i < this.notes.length; i++)
    {
      int j = 0;
      for (int k = 0; k < this.rhythmMap.length; k++) {
        if (this.notes[i].getRhythmValue() == this.rhythmMap[k])
        {
          j = 1;
          arrayOfInt[i] = k;
          break;
        }
      }
      if (j == 0)
      {
        System.err.print("[WARNING] PhraseMatrix only supports ");
        System.err.println("rhythm values supported in the JMC file");
      }
    }
    this.rhythmAM = new AdaptiveMatrix(arrayOfInt, this.rhythmDepth, this.rhythmMap.length);
  }
  
  public void calcDynamic()
  {
    int[] arrayOfInt = new int[this.notes.length];
    for (int i = 0; i < this.notes.length; i++) {
      arrayOfInt[i] = this.notes[i].getDynamic();
    }
    this.dynamicAM = new AdaptiveMatrix(arrayOfInt, this.dynamicDepth, 127);
  }
  
  public Phrase generate(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    return generate(paramBoolean1, paramBoolean2, paramBoolean3, this.notes.length);
  }
  
  public Phrase generate(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt)
  {
    int[] arrayOfInt1 = new int[this.pitchDepth];
    int[] arrayOfInt2 = new int[this.rhythmDepth];
    int[] arrayOfInt3 = new int[this.dynamicDepth];
    Note[] arrayOfNote = new Note[paramInt];
    for (int i = 0; i < paramInt; i++) {
      arrayOfNote[i] = new Note();
    }
    for (i = 0; i < this.pitchDepth; i++) {
      arrayOfInt1[i] = this.notes[i].getPitch();
    }
    for (i = 0; i < this.rhythmDepth; i++) {
      for (int j = 0; j < this.rhythmMap.length; j++) {
        if (this.notes[i].getRhythmValue() == this.rhythmMap[j])
        {
          arrayOfInt2[i] = j;
          break;
        }
      }
    }
    for (i = 0; i < this.dynamicDepth; i++) {
      arrayOfInt3[i] = this.notes[i].getDynamic();
    }
    int[] arrayOfInt4 = this.pitchAM.generate(paramInt, arrayOfInt1);
    int[] arrayOfInt5 = this.dynamicAM.generate(paramInt, arrayOfInt3);
    int[] arrayOfInt6 = this.rhythmAM.generate(paramInt, arrayOfInt2);
    int k;
    if (paramBoolean1) {
      for (k = 0; k < paramInt; k++) {
        arrayOfNote[k].setPitch(arrayOfInt4[k]);
      }
    }
    if (paramBoolean2) {
      for (k = 0; k < paramInt; k++)
      {
        arrayOfNote[k].setRhythmValue(this.rhythmMap[arrayOfInt6[k]]);
        arrayOfNote[k].setDuration(this.rhythmMap[arrayOfInt6[k]] * 0.9D);
      }
    }
    if (paramBoolean3) {
      for (k = 0; k < paramInt; k++) {
        arrayOfNote[k].setDynamic(arrayOfInt5[k]);
      }
    }
    Phrase localPhrase = new Phrase();
    localPhrase.addNoteList(arrayOfNote);
    return localPhrase;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\PhraseMatrix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */