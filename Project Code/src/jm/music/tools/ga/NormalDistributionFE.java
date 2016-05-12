package jm.music.tools.ga;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.PrintStream;
import jm.music.data.Phrase;
import jm.music.tools.NoteListException;
import jm.music.tools.PhraseAnalysis;
import jm.music.tools.QuantisationException;

public class NormalDistributionFE
  extends FitnessEvaluater
{
  protected static String label = "Normal Distribution Fitness Evaluater";
  protected Panel panel = new Panel();
  private double[] weighting = { 1.0D, 1.0D, 1.0D, 0.1D, 1.0D, 1.0D, 0.5D, 0.5D, 0.5D, 1.0D, 0.5D, 0.5D, 1.0D, 0.5D, 1.0D, 0.1D, 1.0D, 1.0D, 1.0D, 0.5D, 0.1D, 0.1D, 0.1D };
  protected Label F1Label;
  protected Label F2Label;
  protected Label F3Label;
  protected Label F4Label;
  protected Label F5Label;
  protected Label F6Label;
  protected Label F7Label;
  protected Label F8Label;
  protected Label F9Label;
  protected Label F10Label;
  protected Label F11Label;
  protected Label F12Label;
  protected Label F13Label;
  protected Label F14Label;
  protected Label F15Label;
  protected Label F16Label;
  protected Label F17Label;
  protected Label F18Label;
  protected Label F19Label;
  protected Label F20Label;
  protected Label F21Label;
  protected Label F22Label;
  protected Label F23Label;
  private double[] mean = { 0.307D, 0.308D, 0.021D, 0.669D, 0.021D, 0.079D, 0.652D, 0.545D, 0.383D, 0.13D, 0.562D, 0.411D, 0.495D, 0.601D, 0.013D, 0.252D, 0.066D, 0.183D, 0.112D, 0.538D, 0.439D, 0.523D, 0.346D };
  private double[] standardDeviation = { 0.115D, 0.129D, 0.038D, 0.318D, 0.044D, 0.137D, 0.148D, 0.166D, 0.211D, 0.13D, 0.21D, 0.139D, 0.059D, 0.218D, 0.047D, 0.399D, 0.105D, 0.146D, 0.125D, 0.227D, 0.246D, 0.261D, 0.275D };
  public static final double duration = 0.25D;
  public static final int tonic = 60;
  public static final int[] scale = PhraseAnalysis.MAJOR_SCALE;
  
  public NormalDistributionFE()
  {
    this.panel.setLayout(new GridLayout(23, 3));
    this.F1Label = new Label(Integer.toString((int)(this.weighting[0] * 100.0D)));
    this.panel.add(new Label("Note Density", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[0] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F1Label);
    this.F2Label = new Label(Integer.toString((int)(this.weighting[1] * 100.0D)));
    this.panel.add(new Label("Pitch Variety", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[1] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F2Label);
    this.F3Label = new Label(Integer.toString((int)(this.weighting[1] * 100.0D)));
    this.panel.add(new Label("Rhythmic Variety", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[2] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F3Label);
    this.F4Label = new Label(Integer.toString((int)(this.weighting[3] * 100.0D)));
    this.panel.add(new Label("Climax Strength", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[3] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F4Label);
    this.F5Label = new Label(Integer.toString((int)(this.weighting[4] * 100.0D)));
    this.panel.add(new Label("Rest Density", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[4] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F5Label);
    this.F6Label = new Label(Integer.toString((int)(this.weighting[5] * 100.0D)));
    this.panel.add(new Label("Tonal Deviation", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[5] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F6Label);
    this.F7Label = new Label(Integer.toString((int)(this.weighting[6] * 100.0D)));
    this.panel.add(new Label("Key Centeredness", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[6] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F7Label);
    this.F8Label = new Label(Integer.toString((int)(this.weighting[7] * 100.0D)));
    this.panel.add(new Label("Pitch Range", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[7] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F8Label);
    this.F9Label = new Label(Integer.toString((int)(this.weighting[8] * 100.0D)));
    this.panel.add(new Label("Rhythm Range", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[8] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F9Label);
    this.F10Label = new Label(Integer.toString((int)(this.weighting[9] * 100.0D)));
    this.panel.add(new Label("Repeated Pitch Density", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[9] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F10Label);
    this.F11Label = new Label(Integer.toString((int)(this.weighting[10] * 100.0D)));
    this.panel.add(new Label("Repeated Rhythm Density", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[10] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F11Label);
    this.F12Label = new Label(Integer.toString((int)(this.weighting[11] * 100.0D)));
    this.panel.add(new Label("Melodic Direction Stability", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[11] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F12Label);
    this.F13Label = new Label(Integer.toString((int)(this.weighting[12] * 100.0D)));
    this.panel.add(new Label("Overall Pitch Direction", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[12] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F13Label);
    this.F14Label = new Label(Integer.toString((int)(this.weighting[13] * 100.0D)));
    this.panel.add(new Label("Pitch Movement", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[13] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F14Label);
    this.F15Label = new Label(Integer.toString((int)(this.weighting[14] * 100.0D)));
    this.panel.add(new Label("Dissonance", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[14] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F15Label);
    this.F16Label = new Label(Integer.toString((int)(this.weighting[15] * 100.0D)));
    this.panel.add(new Label("Leap Compensation", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[15] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F16Label);
    this.F17Label = new Label(Integer.toString((int)(this.weighting[16] * 100.0D)));
    this.panel.add(new Label("Syncopation", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[16] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F17Label);
    this.F18Label = new Label(Integer.toString((int)(this.weighting[17] * 100.0D)));
    this.panel.add(new Label("Repeated Pitch Patterns of 3", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[17] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F18Label);
    this.F19Label = new Label(Integer.toString((int)(this.weighting[18] * 100.0D)));
    this.panel.add(new Label("Repeated Pitch Patterns of 4", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[18] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F19Label);
    this.F20Label = new Label(Integer.toString((int)(this.weighting[19] * 100.0D)));
    this.panel.add(new Label("Repeated Rhythm Patterns of 3", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[19] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F20Label);
    this.F21Label = new Label(Integer.toString((int)(this.weighting[20] * 100.0D)));
    this.panel.add(new Label("Repeated Rhythm Patterns of 4", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[20] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F21Label);
    this.F22Label = new Label(Integer.toString((int)(this.weighting[21] * 100.0D)));
    this.panel.add(new Label("Climax Position", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[21] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F22Label);
    this.F23Label = new Label(Integer.toString((int)(this.weighting[22] * 100.0D)));
    this.panel.add(new Label("Climax Tonality", 2));
    this.panel.add(new Scrollbar(0, (int)(this.weighting[22] * 100.0D), 1, 0, 100) {});
    this.panel.add(this.F23Label);
  }
  
  public double[] evaluate(Phrase[] paramArrayOfPhrase)
  {
    double[] arrayOfDouble = new double[paramArrayOfPhrase.length];
    for (int i = 0; i < paramArrayOfPhrase.length; i++)
    {
      double d = 0.0D;
      for (int j = 0; j < this.mean.length; j++) {
        d += calculateFitness(getValue2(j, paramArrayOfPhrase[i]), this.mean[j], this.standardDeviation[j], this.weighting[j]);
      }
      arrayOfDouble[i] = (1.0D / (d / (this.weighting.length - 1) + 1.0D));
    }
    return arrayOfDouble;
  }
  
  private double calculateFitness(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    return Math.abs((paramDouble1 - paramDouble2) / paramDouble3) * paramDouble4;
  }
  
  private double getValue2(int paramInt, Phrase paramPhrase)
  {
    try
    {
      switch (paramInt)
      {
      case 0: 
      default: 
        return PhraseAnalysis.noteDensity(paramPhrase, 0.25D);
      case 1: 
        return PhraseAnalysis.pitchVariety(paramPhrase);
      case 2: 
        return PhraseAnalysis.rhythmicVariety(paramPhrase);
      case 3: 
        return PhraseAnalysis.climaxStrength(paramPhrase);
      case 4: 
        return PhraseAnalysis.restDensity(paramPhrase, 0.25D);
      case 5: 
        return PhraseAnalysis.tonalDeviation(paramPhrase, 0.25D, 60, scale);
      case 6: 
        return PhraseAnalysis.keyCenteredness(paramPhrase, 0.25D, 60);
      case 7: 
        return PhraseAnalysis.pitchRangePerSpan(paramPhrase);
      case 8: 
        return PhraseAnalysis.rhythmRangePerSpan(paramPhrase);
      case 9: 
        return PhraseAnalysis.repeatedPitchDensity(paramPhrase);
      case 10: 
        return PhraseAnalysis.repeatedRhythmicValueDensity(paramPhrase);
      case 11: 
        return PhraseAnalysis.melodicDirectionStability(paramPhrase);
      case 12: 
        return PhraseAnalysis.overallPitchDirection(paramPhrase);
      case 13: 
        return PhraseAnalysis.movementByStep(paramPhrase, 60, scale);
      case 14: 
        return PhraseAnalysis.dissonance(paramPhrase);
      case 15: 
        return PhraseAnalysis.leapCompensation(paramPhrase);
      case 16: 
        return PhraseAnalysis.syncopation(paramPhrase);
      case 17: 
        return PhraseAnalysis.repeatedPitchPatterns(paramPhrase, 3);
      case 18: 
        return PhraseAnalysis.repeatedPitchPatterns(paramPhrase, 4);
      case 19: 
        return PhraseAnalysis.repeatedRhythmPatterns(paramPhrase, 3);
      case 20: 
        return PhraseAnalysis.repeatedRhythmPatterns(paramPhrase, 4);
      case 21: 
        return PhraseAnalysis.climaxPosition(paramPhrase);
      }
      return PhraseAnalysis.climaxTonality(paramPhrase, 60, scale);
    }
    catch (NoteListException localNoteListException)
    {
      localNoteListException.printStackTrace();
      System.err.println(localNoteListException);
      System.exit(-1);
    }
    catch (QuantisationException localQuantisationException)
    {
      localQuantisationException.printStackTrace();
      System.err.println(localQuantisationException);
      System.exit(-1);
    }
    return 0.0D;
  }
  
  public Panel getPanel()
  {
    return this.panel;
  }
  
  public String getLabel()
  {
    return label;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\NormalDistributionFE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */