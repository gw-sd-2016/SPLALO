package jm.music.tools.ga;

import java.awt.Choice;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import jm.music.data.Note;
import jm.music.data.Phrase;
import jm.music.tools.PhraseAnalysis;

public class ComplexMutater
  extends Mutater
{
  private int[] MUTATE_PERCENTAGE = { 0, 40, 1, 40, 60 };
  private static final int SEMITONES_PER_OCTAVE = 12;
  private static final int TONIC = 60;
  protected static String label = "Mutater";
  protected Panel panel = new Panel();
  protected Choice choice;
  protected Scrollbar scrollbar;
  protected Label mutateLabel;
  protected boolean modifyAll = false;
  
  public ComplexMutater()
  {
    GridBagLayout localGridBagLayout = new GridBagLayout();
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    this.panel.setLayout(localGridBagLayout);
    this.mutateLabel = new Label(Integer.toString(this.MUTATE_PERCENTAGE[0]));
    this.scrollbar = new Scrollbar(0, this.MUTATE_PERCENTAGE[0], 1, 0, 100);
    this.choice = new Choice();
    this.choice.add("Random pitch change");
    this.choice.add("Bar sequence mutations");
    this.choice.add("Split and merge");
    this.choice.add("Step interpolation");
    this.choice.add("Tonal Pauses");
    this.choice.addItemListener(new ItemListener()
    {
      public void itemStateChanged(ItemEvent paramAnonymousItemEvent)
      {
        ComplexMutater.this.mutateLabel.setText(Integer.toString(ComplexMutater.this.MUTATE_PERCENTAGE[ComplexMutater.this.choice.getSelectedIndex()]));
        ComplexMutater.this.scrollbar.setValue(ComplexMutater.this.MUTATE_PERCENTAGE[ComplexMutater.this.choice.getSelectedIndex()]);
      }
    });
    this.scrollbar.addAdjustmentListener(new AdjustmentListener()
    {
      public void adjustmentValueChanged(AdjustmentEvent paramAnonymousAdjustmentEvent)
      {
        ComplexMutater.this.MUTATE_PERCENTAGE[ComplexMutater.this.choice.getSelectedIndex()] = ComplexMutater.this.scrollbar.getValue();
        ComplexMutater.this.mutateLabel.setText(Integer.toString(ComplexMutater.this.scrollbar.getValue()));
        ComplexMutater.this.mutateLabel.repaint();
      }
    });
    localGridBagConstraints.gridy = -1;
    localGridBagConstraints.gridwidth = 2;
    localGridBagLayout.setConstraints(this.choice, localGridBagConstraints);
    this.panel.add(this.choice);
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.fill = 2;
    localGridBagLayout.setConstraints(this.scrollbar, localGridBagConstraints);
    this.panel.add(this.scrollbar);
    localGridBagConstraints.gridx = -1;
    localGridBagConstraints.fill = 0;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagLayout.setConstraints(this.mutateLabel, localGridBagConstraints);
    this.panel.add(this.mutateLabel);
  }
  
  public Phrase[] mutate(Phrase[] paramArrayOfPhrase, double paramDouble, int paramInt1, int paramInt2)
  {
    double[] arrayOfDouble1 = new double[paramArrayOfPhrase.length];
    for (int i = 0; i < paramArrayOfPhrase.length; i++) {
      arrayOfDouble1[i] = paramArrayOfPhrase[i].getEndTime();
    }
    for (i = 0; i < paramArrayOfPhrase.length; i++)
    {
      Phrase localPhrase = paramArrayOfPhrase[i];
      if (this.modifyAll)
      {
        paramInt1 = 0;
        paramDouble = 0.0D;
      }
      int j = localPhrase.size() - paramInt1;
      double d1 = j * this.MUTATE_PERCENTAGE[0] / 100.0D;
      int k = 0;
      if (d1 < 1.0D)
      {
        if (Math.random() < d1) {
          k = 1;
        } else {
          k = 0;
        }
      }
      else {
        k = (int)Math.floor(d1);
      }
      for (int m = 0; m < k; m++)
      {
        int n = (int)(Math.random() * j);
        mutate(localPhrase.getNote(paramInt1 + n));
      }
      int i4;
      Object localObject2;
      int i10;
      if (Math.random() < this.MUTATE_PERCENTAGE[1] / 100.0D)
      {
        m = 0;
        d2 = 0.0D;
        for (int i1 = 0; i1 < localPhrase.size(); i1++) {
          d2 += localPhrase.getNote(i1).getRhythmValue();
        }
        int[] arrayOfInt1 = new int[(int)d2];
        localObject1 = new int[(int)d2];
        i3 = 0;
        d2 = 0.0D;
        for (i4 = 0; i4 < localPhrase.size(); i4++)
        {
          if (d2 / paramInt2 == Math.floor(d2 / paramInt2))
          {
            arrayOfInt1[i3] = i4;
            localObject1[(i3++)] = ((int)(d2 * paramInt2));
          }
          d2 += localPhrase.getNote(i4).getRhythmValue();
        }
        i4 = 0;
        localObject2 = new int[i3];
        if (i3 > 0) {
          for (i5 = 1; i5 < i3; i5++) {
            if (localObject1[i5] == localObject1[(i5 - 1)] + 1) {
              localObject2[(i4++)] = arrayOfInt1[(i5 - 1)];
            }
          }
        }
        if (i4 > 0)
        {
          for (i5 = 0; i5 < (int)(paramDouble / paramInt2) - 1; i5 = (int)(Math.random() * i4)) {}
          int i6 = (int)(Math.random() * 2.0D + 1.0D);
          int i7;
          switch (i6)
          {
          case 1: 
            i7 = 0;
            if (Math.random() < 0.5D) {
              i7 = 2;
            } else {
              i7 = -2;
            }
            d2 = 0.0D;
            i3 = localObject2[i5];
          }
          while (d2 < paramInt2)
          {
            shiftPitch(localPhrase.getNote(i3), i7);
            d2 += localPhrase.getNote(i3++).getRhythmValue();
            continue;
            i3 = localObject2[i5];
            for (d2 = 0.0D; d2 < paramInt2; d2 += localPhrase.getNote(i3++).getRhythmValue()) {}
            int i8 = i3 - localObject2[i5];
            i3 = localObject2[i5];
            if (i8 > 0)
            {
              arrayOfInt3 = new int[i8];
              double[] arrayOfDouble2 = new double[i8];
              for (i10 = 0; i10 < i8; i10++)
              {
                arrayOfInt3[i10] = localPhrase.getNote(i10 + i3).getPitch();
                arrayOfDouble2[i10] = localPhrase.getNote(i10 + i3).getRhythmValue();
              }
              for (i10 = 0; i10 < i8; i10++)
              {
                localPhrase.getNote(i10 + i3).setPitch(arrayOfInt3[(i8 - i10 - 1)]);
                localPhrase.getNote(i10 + i3).setRhythmValue(arrayOfDouble2[(i8 - i10 - 1)]);
              }
            }
          }
        }
      }
      m = localPhrase.size() - paramInt1;
      double d2 = m * this.MUTATE_PERCENTAGE[2] / 100.0D;
      int i2 = 0;
      if (d2 < 1.0D)
      {
        if (Math.random() < d2) {
          i2 = 1;
        } else {
          i2 = 0;
        }
      }
      else {
        i2 = (int)Math.floor(d2);
      }
      Object localObject1 = (Vector)localPhrase.getNoteList().clone();
      for (int i3 = 0; i3 < i2; i3++)
      {
        i4 = (int)(Math.random() * (m - 1));
        localObject2 = (Note)((Vector)localObject1).elementAt(paramInt1 + i4);
        i5 = ((Note)localObject2).getPitch();
        d4 = ((Note)localObject2).getRhythmValue();
        if ((d4 >= 1.0D) && (d4 % 1.0D == 0.0D) && (d4 * 2.0D == Math.ceil(d4 * 2.0D)))
        {
          ((Vector)localObject1).removeElementAt(paramInt1 + i4);
          ((Vector)localObject1).insertElementAt(new Note(i5, d4 / 2.0D), paramInt1 + i4);
          ((Vector)localObject1).insertElementAt(new Note(i5, d4 / 2.0D), paramInt1 + i4);
          m++;
        }
        else
        {
          double d5 = d4 + ((Note)((Vector)localObject1).elementAt(paramInt1 + i4 + 1)).getRhythmValue();
          if (d5 <= 2.0D)
          {
            ((Vector)localObject1).removeElementAt(paramInt1 + i4);
            ((Vector)localObject1).removeElementAt(paramInt1 + i4);
            ((Vector)localObject1).insertElementAt(new Note(i5, d5), paramInt1 + i4);
            m--;
          }
        }
      }
      localPhrase.addNoteList((Vector)localObject1, false);
      localObject1 = (Vector)localPhrase.getNoteList().clone();
      int i5 = Integer.MIN_VALUE;
      double d4 = 0.0D;
      for (int[] arrayOfInt2 = paramInt1; (arrayOfInt2 < ((Vector)localObject1).size()) && (i5 == Integer.MIN_VALUE); arrayOfInt2++)
      {
        i5 = ((Note)((Vector)localObject1).elementAt(arrayOfInt2)).getPitch();
        d4 = ((Note)((Vector)localObject1).elementAt(arrayOfInt2)).getRhythmValue();
      }
      for (int[] arrayOfInt3 = arrayOfInt2; arrayOfInt3 < ((Vector)localObject1).size(); arrayOfInt3++)
      {
        i3 = ((Note)((Vector)localObject1).elementAt(arrayOfInt3)).getPitch();
        double d3 = ((Note)((Vector)localObject1).elementAt(arrayOfInt3)).getRhythmValue();
        if (i3 != Integer.MIN_VALUE)
        {
          int i9 = i3 - i5;
          if (((Math.abs(i9) == 4) || (Math.abs(i9) == 3)) && (Math.random() < this.MUTATE_PERCENTAGE[3] / 100.0D))
          {
            i10 = 0;
            if (i9 > 0)
            {
              i10 = i3 - 1;
              if (!isScale(i10)) {
                i10--;
              }
            }
            else
            {
              i10 = i3 + 1;
              if (!isScale(i10)) {
                i10++;
              }
            }
            if (d3 > d4)
            {
              if ((d3 >= 0.5D) && ((int)Math.ceil(d3 * 2.0D) == (int)(d3 * 2.0D)))
              {
                ((Vector)localObject1).removeElementAt(arrayOfInt3);
                ((Vector)localObject1).insertElementAt(new Note(i3, d3 / 2.0D), arrayOfInt3);
                ((Vector)localObject1).insertElementAt(new Note(i10, d3 / 2.0D), arrayOfInt3);
                arrayOfInt3++;
              }
            }
            else if ((d4 >= 0.5D) && ((int)Math.ceil(d4 * 2.0D) == (int)(d4 * 2.0D)))
            {
              ((Vector)localObject1).removeElementAt(arrayOfInt3 - 1);
              ((Vector)localObject1).insertElementAt(new Note(i10, d4 / 2.0D), arrayOfInt3 - 1);
              ((Vector)localObject1).insertElementAt(new Note(i5, d4 / 2.0D), arrayOfInt3 - 1);
              arrayOfInt3++;
            }
          }
          i5 = i3;
          d4 = d3;
        }
      }
      localPhrase.addNoteList((Vector)localObject1, false);
      localPhrase.addNoteList(applyTonalPausesMutation(localPhrase, paramDouble, paramInt1, paramInt2), false);
      double d6 = 0.0D;
      for (int i11 = paramInt1; i11 < localPhrase.size(); i11++)
      {
        int i12 = localPhrase.getNote(i11).getPitch();
        double d7 = localPhrase.getNote(i11).getRhythmValue();
        if ((i12 != Integer.MIN_VALUE) && (!isScale(i12))) {
          if ((int)Math.ceil(d6 / 2.0D) == (int)(d6 / 2.0D))
          {
            if (Math.random() < d7) {
              if (Math.random() < 0.5D) {
                localPhrase.getNote(i11).setPitch(i12 + 1);
              } else {
                localPhrase.getNote(i11).setPitch(i12 - 1);
              }
            }
          }
          else if (Math.random() < d7 / 2.0D) {
            if (Math.random() < 0.5D) {
              localPhrase.getNote(i11).setPitch(i12 + 1);
            } else {
              localPhrase.getNote(i11).setPitch(i12 - 1);
            }
          }
        }
        d6 += d7;
      }
    }
    return paramArrayOfPhrase;
  }
  
  private void mutate(Note paramNote)
  {
    int i = (int)(10.0D / (Math.random() * 6.0D + 2.0D));
    if (Math.random() < 0.5D) {
      shiftPitch(paramNote, i);
    } else {
      shiftPitch(paramNote, 0 - i);
    }
  }
  
  private Vector applyTonalPausesMutation(Phrase paramPhrase, double paramDouble, int paramInt1, int paramInt2)
  {
    Vector localVector = (Vector)paramPhrase.getNoteList().clone();
    double d1 = paramDouble;
    int i = 0;
    for (int j = paramInt1; j < paramPhrase.size() - 1; j++)
    {
      int k = paramPhrase.getNote(j).getPitch();
      int m = pitchToDegree(k, 60);
      double d2 = paramPhrase.getNote(j).getRhythmValue() + paramPhrase.getNote(j + 1).getRhythmValue();
      if ((d1 / paramInt2 == Math.ceil(d1 / paramInt2)) && ((m == 0) || (m == 7)) && (Math.random() < 2.0D / d2 * (this.MUTATE_PERCENTAGE[4] / 100.0D)))
      {
        localVector.removeElementAt(j - i);
        localVector.removeElementAt(j - i);
        localVector.insertElementAt(new Note(k, d2), j - i);
        d1 += paramPhrase.getNote(j).getRhythmValue();
        j++;
        i++;
      }
      d1 += paramPhrase.getNote(j).getRhythmValue();
    }
    return localVector;
  }
  
  private void shiftPitch(Note paramNote, int paramInt)
  {
    paramNote.setPitch(paramNote.getPitch() + paramInt);
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
  
  private static int pitchToDegree(int paramInt1, int paramInt2)
  {
    paramInt1 -= paramInt2;
    if (paramInt1 < 0) {
      paramInt1 += (-paramInt1 / 12 + 1) * 12;
    }
    return paramInt1 % 12;
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


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\ComplexMutater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */