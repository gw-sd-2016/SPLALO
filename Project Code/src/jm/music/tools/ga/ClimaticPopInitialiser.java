package jm.music.tools.ga;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import jm.music.data.Note;
import jm.music.data.Phrase;
import jm.music.tools.PhraseAnalysis;

public class ClimaticPopInitialiser
  extends PopulationInitialiser
{
  protected static String label = "Climatic Population Initialiser";
  public final int TONIC = 60;
  public static final int MIN_POPULATION_SIZE = 2;
  public static final int MAX_POPULATION_SIZE = 100;
  public static final int DEFAULT_POPULATION_SIZE = 50;
  public static final double CLIMAX_AVERAGE = 0.523D;
  public static final double CLIMAX_ST_DEV = 0.261D;
  protected Panel panel;
  protected int populationSize;
  protected Label populationLabel;
  protected boolean modifyAll = false;
  
  public ClimaticPopInitialiser()
  {
    this(50);
  }
  
  public ClimaticPopInitialiser(int paramInt)
  {
    this.populationSize = paramInt;
    this.panel = new Panel();
    this.panel.setLayout(new FlowLayout(0, 0, 0));
    this.populationLabel = new Label(Integer.toString(this.populationSize));
    this.panel.add(new Label("Population Size", 2));
    this.panel.add(new Scrollbar(0, this.populationSize, 1, 2, 100) {});
    this.panel.add(this.populationLabel);
  }
  
  public Phrase[] initPopulation(Phrase paramPhrase, int paramInt)
  {
    Phrase localPhrase = completeFinalBeat(paramPhrase, paramInt);
    int i;
    if (this.modifyAll) {
      i = 0;
    } else {
      i = localPhrase.size();
    }
    double[][] arrayOfDouble = generateBeatRhythmArray(localPhrase, paramInt);
    int[] arrayOfInt = generateIntervalArray(localPhrase);
    Phrase[] arrayOfPhrase = new Phrase[this.populationSize];
    for (int j = 0; j < this.populationSize; j++)
    {
      arrayOfPhrase[j] = localPhrase.copy();
      int m = 0;
      Note localNote;
      int k;
      int i2;
      if (isClimaxAccepted(localPhrase, paramInt))
      {
        m = findClimax(localPhrase);
        localNote = new Note(60, paramInt);
        k = 7 * paramInt;
      }
      else
      {
        n = 127;
        for (i1 = 0; i1 < localPhrase.size(); i1++)
        {
          i2 = localPhrase.getNote(i1).getPitch();
          if ((i2 != Integer.MIN_VALUE) && (i2 < n)) {
            n = i2;
          }
        }
        localNote = generateClimax(n);
        m = localNote.getPitch();
        k = 4 * paramInt;
      }
      int n = -1;
      for (int i1 = 0; i1 < localPhrase.size(); i1++)
      {
        i2 = localPhrase.getNote(i1).getPitch();
        if (i2 != Integer.MIN_VALUE)
        {
          n = i2 - 12;
          break;
        }
      }
      if (n < 53) {
        n = 53;
      }
      extend(arrayOfPhrase[j], localNote, k, arrayOfDouble, arrayOfInt, m, paramInt, n);
      addAppropriateTarget(arrayOfPhrase[j], localNote);
      if (arrayOfPhrase[j].getEndTime() != 8 * paramInt)
      {
        localNote = new Note(60, paramInt);
        k = 7 * paramInt;
        extend(arrayOfPhrase[j], localNote, k, arrayOfDouble, arrayOfInt, m, paramInt, n);
        i1 = arrayOfPhrase[j].size() - 1;
        for (i2 = arrayOfPhrase[j].getNote(i1).getPitch(); i2 == Integer.MIN_VALUE; i2 = arrayOfPhrase[j].getNote(--i1).getPitch()) {}
        int i3 = localNote.getPitch();
        if (i2 < i3)
        {
          if (i3 - i2 > 6) {
            localNote.setPitch(i3 - 12);
          }
        }
        else if ((i2 > i3) && (i2 - i3 > 6)) {
          localNote.setPitch(i3 + 12);
        }
        arrayOfPhrase[j].addNote(localNote);
      }
      cleanMelody(arrayOfPhrase[j], i);
    }
    return arrayOfPhrase;
  }
  
  private Phrase completeFinalBeat(Phrase paramPhrase, int paramInt)
  {
    Phrase localPhrase = paramPhrase.copy();
    double d1 = localPhrase.getEndTime();
    double d2 = Math.ceil(d1) - d1;
    if (d2 > 0.0D)
    {
      int[] arrayOfInt = generateIntervalArray(paramPhrase);
      int i = localPhrase.size() - 1;
      for (int j = Integer.MIN_VALUE; j == Integer.MIN_VALUE; j = localPhrase.getNote(i--).getPitch()) {}
      j += arrayOfInt[((int)(Math.random() * arrayOfInt.length))];
      if (!isScale(j)) {
        if (Math.random() < 0.5D) {
          j++;
        } else {
          j--;
        }
      }
      localPhrase.addNote(new Note(j, d2));
    }
    return localPhrase;
  }
  
  private double[][] generateBeatRhythmArray(Phrase paramPhrase, int paramInt)
  {
    double[][] arrayOfDouble1 = new double[(int)paramPhrase.getEndTime() * paramInt][];
    int i = 0;
    int j = 0;
    double d1 = 0.0D;
    while (j < paramPhrase.size())
    {
      int k = j;
      double d2 = d1;
      int m = j;
      double d3 = d1;
      double[] arrayOfDouble3 = new double[paramPhrase.size()];
      int n = 0;
      Note localNote = paramPhrase.getNote(m++);
      double d4 = localNote.getRhythmValue();
      arrayOfDouble3[(n++)] = d4;
      if (localNote.getPitch() == Integer.MIN_VALUE) {
        arrayOfDouble3[(n - 1)] *= -1.0D;
      }
      for (d3 += d4; d3 != Math.ceil(d3); d3 += d4)
      {
        localNote = paramPhrase.getNote(m++);
        d4 = localNote.getRhythmValue();
        arrayOfDouble3[(n++)] = d4;
        if (localNote.getPitch() == Integer.MIN_VALUE) {
          arrayOfDouble3[(n - 1)] *= -1.0D;
        }
      }
      double[] arrayOfDouble4 = new double[n];
      System.arraycopy(arrayOfDouble3, 0, arrayOfDouble4, 0, n);
      arrayOfDouble1[(i++)] = arrayOfDouble4;
      j = m;
      d1 = d3;
      while ((d3 < d2 + paramInt) && (m < paramPhrase.size()))
      {
        localNote = paramPhrase.getNote(m++);
        d4 = localNote.getRhythmValue();
        arrayOfDouble3[(n++)] = d4;
        if (localNote.getPitch() == Integer.MIN_VALUE) {
          arrayOfDouble3[(n - 1)] *= -1.0D;
        }
        for (d3 += d4; d3 != Math.ceil(d3); d3 += d4)
        {
          localNote = paramPhrase.getNote(m++);
          d4 = localNote.getRhythmValue();
          arrayOfDouble3[(n++)] = d4;
          if (localNote.getPitch() == Integer.MIN_VALUE) {
            arrayOfDouble3[(n - 1)] *= -1.0D;
          }
        }
        if (d3 <= d2 + paramInt)
        {
          arrayOfDouble4 = new double[n];
          System.arraycopy(arrayOfDouble3, 0, arrayOfDouble4, 0, n);
          arrayOfDouble1[(i++)] = arrayOfDouble4;
        }
      }
    }
    double[][] arrayOfDouble2 = new double[i][];
    System.arraycopy(arrayOfDouble1, 0, arrayOfDouble2, 0, i);
    return arrayOfDouble2;
  }
  
  private int[] generateIntervalArray(Phrase paramPhrase)
  {
    int[] arrayOfInt1 = new int[0];
    try
    {
      arrayOfInt1 = PhraseAnalysis.pitchIntervals(paramPhrase);
    }
    catch (ArrayStoreException localArrayStoreException)
    {
      System.exit(0);
    }
    int[] arrayOfInt2 = new int[arrayOfInt1.length * 2];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, arrayOfInt1.length);
    for (int i = 0; i < arrayOfInt1.length; i++)
    {
      if (arrayOfInt2[i] > 127) {
        arrayOfInt2[i] -= 255;
      }
      arrayOfInt2[(arrayOfInt1.length + i)] = (0 - arrayOfInt2[i]);
    }
    return arrayOfInt2;
  }
  
  private boolean isClimaxAccepted(Phrase paramPhrase, int paramInt)
  {
    int i = 0;
    int j = 0;
    double d1 = 0.0D;
    double d2 = 0.0D;
    for (int k = 0; k < paramPhrase.size(); k++)
    {
      int m = paramPhrase.getNote(k).getPitch();
      if (m != Integer.MIN_VALUE) {
        if (m > i)
        {
          i = m;
          d2 = d1;
          j = 0;
        }
        else if (m == i)
        {
          j++;
          d2 = d1;
        }
      }
      d1 += paramPhrase.getNote(k).getRhythmValue();
    }
    if ((d2 < 8 * paramInt * 0.262D) || (d2 > 8 * paramInt * 0.784D)) {
      return false;
    }
    return (i > paramPhrase.getNote(0).getPitch() + 12) && (j <= 1);
  }
  
  private int findClimax(Phrase paramPhrase)
  {
    int i = 0;
    for (int j = 0; j < paramPhrase.size(); j++)
    {
      int k = paramPhrase.getNote(j).getPitch();
      if ((k != Integer.MIN_VALUE) && (k > i)) {
        i = k;
      }
    }
    return i;
  }
  
  private Note generateClimax(int paramInt)
  {
    int i = 0;
    for (int j = paramInt + 13; i == 0; j++) {
      if ((j % 12 == 0) || (j % 12 == 7)) {
        i = j;
      }
    }
    while (i > 88)
    {
      if ((j % 12 == 0) || (j % 12 == 7)) {
        i = j;
      }
      j--;
    }
    return new Note(i, 1.0D);
  }
  
  private void extend(Phrase paramPhrase, Note paramNote, int paramInt1, double[][] paramArrayOfDouble, int[] paramArrayOfInt, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = (int)paramPhrase.getEndTime(); i < paramInt1; i = (int)paramPhrase.getEndTime())
    {
      int j;
      int k;
      int m;
      if (i == 2 * paramInt3)
      {
        j = paramPhrase.size() - 1;
        for (k = paramPhrase.getNote(j).getPitch(); k == Integer.MIN_VALUE; k = paramPhrase.getNote(--j).getPitch()) {}
        if ((k % 12 != 0) && (k % 12 != 7))
        {
          for (m = k + 1; (m % 12 != 0) && (m % 12 != 7); m++) {}
          for (int n = k - 1; (n % 12 != 0) && (n % 12 != 7); n--) {}
          if (m > paramInt2) {
            k = n;
          } else if (n < paramInt4) {
            k = m;
          } else if (m - k > k - n) {
            k = n;
          } else if (k - n > m - k) {
            k = m;
          } else {
            k = m;
          }
        }
        paramPhrase.addNote(new Note(k, 2.0D));
      }
      else
      {
        j = paramInt1;
        k = 0;
        for (m = 0; (m < 30) && (i + j > (i / paramInt3 + 1) * paramInt3); m++)
        {
          k = (int)(Math.random() * paramArrayOfDouble.length);
          double d = 0.0D;
          for (int i2 = 0; i2 < paramArrayOfDouble[k].length; i2++) {
            d += (paramArrayOfDouble[k][i2] < 0.0D ? 0.0D - paramArrayOfDouble[k][i2] : paramArrayOfDouble[k][i2]);
          }
          j = (int)d;
        }
        if (m != 30) {
          for (int i1 = 0; i1 < paramArrayOfDouble[k].length; i1++) {
            addNote(paramPhrase, paramNote, paramInt1, paramArrayOfDouble[k][i1], paramArrayOfInt, paramInt2, paramInt4);
          }
        } else {
          addNote(paramPhrase, paramNote, paramInt1, (i / paramInt3 + 1) * paramInt3 - i, paramArrayOfInt, paramInt2, paramInt4);
        }
      }
    }
  }
  
  private void addAppropriateTarget(Phrase paramPhrase, Note paramNote)
  {
    Note localNote = paramNote.copy();
    int i = paramPhrase.size();
    int j;
    do
    {
      j = paramPhrase.getNote(--i).getPitch();
    } while (j == Integer.MIN_VALUE);
    int k = paramNote.getPitch();
    if (j + 7 < k)
    {
      do
      {
        k -= 12;
      } while (k - 12 > j);
      localNote.setPitch(k);
    }
    paramPhrase.addNote(localNote);
  }
  
  private void addNote(Phrase paramPhrase, Note paramNote, int paramInt1, double paramDouble, int[] paramArrayOfInt, int paramInt2, int paramInt3)
  {
    if (paramDouble < 0.0D)
    {
      paramPhrase.addNote(new Note(Integer.MIN_VALUE, 0.0D - paramDouble));
    }
    else
    {
      int i = paramPhrase.size() - 1;
      for (int j = paramPhrase.getNote(i).getPitch(); j == Integer.MIN_VALUE; j = paramPhrase.getNote(--i).getPitch()) {}
      double d1 = (paramNote.getPitch() - j) / (paramInt1 - paramPhrase.getEndTime());
      int k = paramArrayOfInt[((int)(Math.random() * paramArrayOfInt.length))];
      int m = paramNote.getPitch() - j;
      double d2 = k / m;
      if ((d2 < 0.0D) && (Math.random() < 2.5D / (paramInt1 - paramPhrase.getEndTime()))) {
        k = 0 - k;
      }
      double d3 = m / (paramInt1 - paramPhrase.getEndTime());
      double d4 = d3 / d1;
      if ((d4 >= 2.0D) || (d4 <= 0.5D)) {
        k /= 2;
      }
      int n = j + k;
      if ((n >= paramInt2) || (n < paramInt3)) {
        n = j - k;
      }
      if ((n >= paramInt2) || (n < paramInt3)) {
        n = j - k / 2;
      }
      if ((n >= paramInt2) || (n < paramInt3)) {
        n = j - k / 4;
      }
      paramPhrase.addNote(new Note(n, paramDouble));
    }
  }
  
  private void cleanMelody(Phrase paramPhrase, int paramInt)
  {
    for (int i = paramInt; i < paramPhrase.size(); i++)
    {
      int j = paramPhrase.getNote(i).getPitch();
      if ((j != Integer.MIN_VALUE) && (!isScale(j))) {
        if (Math.random() < 0.5D) {
          paramPhrase.getNote(i).setPitch(j + 1);
        } else {
          paramPhrase.getNote(i).setPitch(j - 1);
        }
      }
    }
  }
  
  private boolean isScale(int paramInt)
  {
    for (int i = 0; i < PhraseAnalysis.MAJOR_SCALE.length; i++) {
      if (paramInt % 12 == PhraseAnalysis.MAJOR_SCALE[i]) {
        return true;
      }
    }
    return false;
  }
  
  public Panel getPanel()
  {
    return this.panel;
  }
  
  public String getLabel()
  {
    return label;
  }
  
  public void setModifyAll(boolean paramBoolean)
  {
    this.modifyAll = paramBoolean;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\ClimaticPopInitialiser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */