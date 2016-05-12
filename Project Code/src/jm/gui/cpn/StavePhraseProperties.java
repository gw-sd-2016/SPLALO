package jm.gui.cpn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public class StavePhraseProperties
  extends Properties
{
  private static String KEY_SIGNATURE = "STAVE_KEY";
  private static String STAVE_TYPE = "STAVE_TYPE";
  private static String STAVE_TITLE = "STAVE_TITLE";
  private static String STAVE_METRE = "STAVE_METRE";
  private static String PHRASE_NUMERATOR = "PHRASE_NUM";
  private static String PHRASE_DENOMINATOR = "PHRASE_DEN";
  private static String PHRASE_TEMPO = "PHRASE_TEMPO";
  private static String PHRASE_TITLE = "PHRASE_TITLE";
  private static String PHRASE_INSTRUMENT = "PHRASE_INSTRUMENT";
  private static String LAST_NOTE_RHYTHM = "LAST_NOTE_RHYTHM";
  private static String LAST_NOTE_DUR = "LAST_NOTE_DUR";
  private static String FINAL_REST_RHYTHM = "FINAL_REST_RHYTHM";
  private static String FINAL_REST_DUR = "FINAL_REST_DUR";
  private static String OTHER_NOTES_TOTAL_RHYTHM = "OTHER_NOTES_TOTAL_RHYTHM";
  private static String OTHER_NOTES_TOTAL_DUR = "OTHER_NOTES_TOTAL_DUR";
  private static String GRAND_STAVE = "GRAND_STAVE";
  private static String TREBLE_STAVE = "TREBLE_STAVE";
  private static String BASS_STAVE = "BASS_STAVE";
  private static String PIANO_STAVE = "PIANO_STAVE";
  private static String FILE_NAME_SUFFIX = "pj";
  
  public StavePhraseProperties(String paramString)
    throws FileNotFoundException, IOException
  {
    FileInputStream localFileInputStream = new FileInputStream(paramString + FILE_NAME_SUFFIX);
    load(localFileInputStream);
  }
  
  public StavePhraseProperties(Stave paramStave, Phrase paramPhrase)
  {
    System.out.println("1");
    setSavedProperty(KEY_SIGNATURE, paramStave.getKeySignature());
    System.out.println("2");
    setSavedProperty(STAVE_TYPE, getStaveType(paramStave));
    System.out.println("3");
    setSavedProperty(STAVE_TITLE, paramStave.getTitle());
    System.out.println("4");
    setSavedProperty(STAVE_METRE, paramStave.getMetre());
    System.out.println("5");
    setSavedProperty(PHRASE_NUMERATOR, paramPhrase.getNumerator());
    System.out.println("6");
    setSavedProperty(PHRASE_DENOMINATOR, paramPhrase.getDenominator());
    System.out.println("7");
    setSavedProperty(PHRASE_INSTRUMENT, paramPhrase.getInstrument());
    System.out.println("8");
    setSavedProperty(PHRASE_TEMPO, paramPhrase.getTempo());
    System.out.println("9");
    setSavedProperty(PHRASE_TITLE, paramPhrase.getTitle());
    int i = findLastNonRest(paramPhrase);
    System.out.println("10");
    if (i >= 0)
    {
      setSavedProperty(LAST_NOTE_RHYTHM, paramPhrase.getNote(i).getRhythmValue());
      setSavedProperty(LAST_NOTE_DUR, paramPhrase.getNote(i).getDuration());
    }
    else
    {
      setSavedProperty(LAST_NOTE_RHYTHM, 0.0D);
      setSavedProperty(LAST_NOTE_DUR, 0.0D);
    }
    setSavedProperty(FINAL_REST_RHYTHM, getFinalRestRhythm(paramPhrase));
    setSavedProperty(FINAL_REST_DUR, getFinalRestDuration(paramPhrase));
    setSavedProperty(OTHER_NOTES_TOTAL_RHYTHM, getOtherNotesTotalRhythm(paramPhrase));
    setSavedProperty(OTHER_NOTES_TOTAL_DUR, getOtherNotesTotalDuration(paramPhrase));
  }
  
  private void setSavedProperty(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      paramString1 = "";
    }
    if (paramString2 == null) {
      paramString2 = "";
    }
    setProperty(paramString1, paramString2);
  }
  
  private void setSavedProperty(String paramString, int paramInt)
  {
    setSavedProperty(paramString, Integer.toString(paramInt));
  }
  
  private void setSavedProperty(String paramString, double paramDouble)
  {
    setSavedProperty(paramString, Double.toString(paramDouble));
  }
  
  private String getStaveType(Stave paramStave)
  {
    if ((paramStave instanceof TrebleStave)) {
      return TREBLE_STAVE;
    }
    if ((paramStave instanceof GrandStave)) {
      return GRAND_STAVE;
    }
    if ((paramStave instanceof BassStave)) {
      return BASS_STAVE;
    }
    if ((paramStave instanceof PianoStave)) {
      return PIANO_STAVE;
    }
    return GRAND_STAVE;
  }
  
  public void writeToFile(String paramString)
    throws FileNotFoundException
  {
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString + FILE_NAME_SUFFIX);
      store(localFileOutputStream, "Stave and Phrase Properties for " + paramString);
    }
    catch (IOException localIOException)
    {
      System.out.println("Error Writing MIDI Properties File " + paramString + " " + localIOException.getMessage());
    }
  }
  
  public void updateStave(Stave paramStave)
  {
    paramStave.setKeySignature(new Integer(getProperty(KEY_SIGNATURE)).intValue());
    paramStave.setTitle(getProperty(STAVE_TITLE));
    paramStave.setMetre(new Double(getProperty(STAVE_METRE)).doubleValue());
  }
  
  public void updatePhrase(Phrase paramPhrase)
  {
    paramPhrase.setNumerator(new Integer(getProperty(PHRASE_NUMERATOR)).intValue());
    paramPhrase.setDenominator(new Integer(getProperty(PHRASE_DENOMINATOR)).intValue());
    paramPhrase.setTitle(getProperty(PHRASE_TITLE));
    paramPhrase.setTempo(new Double(getProperty(PHRASE_TEMPO)).doubleValue());
    try
    {
      paramPhrase.setInstrument(new Integer(getProperty(PHRASE_INSTRUMENT)).intValue());
    }
    catch (Throwable localThrowable)
    {
      paramPhrase.setInstrument(0);
    }
    int i = findLastNonRest(paramPhrase);
    if (i >= 0)
    {
      paramPhrase.getNote(i).setRhythmValue(new Double(getProperty(LAST_NOTE_RHYTHM)).doubleValue());
      paramPhrase.getNote(i).setDuration(new Double(getProperty(LAST_NOTE_DUR)).doubleValue());
    }
    if (new Double(getProperty(FINAL_REST_RHYTHM)).doubleValue() > 1.0E-5D) {
      adjustFinalRestRhythm(paramPhrase, new Double(getProperty(FINAL_REST_RHYTHM)).doubleValue(), new Double(getProperty(FINAL_REST_DUR)).doubleValue());
    }
    adjustOtherNotesTotalRhythm(paramPhrase, new Double(getProperty(OTHER_NOTES_TOTAL_RHYTHM)).doubleValue());
    adjustOtherNotesTotalDuration(paramPhrase, new Double(getProperty(OTHER_NOTES_TOTAL_DUR)).doubleValue());
    Score localScore = new Score();
    Part localPart = new Part();
    localScore.addPart(localPart);
    localPart.addPhrase(paramPhrase);
  }
  
  public boolean isTrebleStave()
  {
    return getProperty(STAVE_TYPE).equals(TREBLE_STAVE);
  }
  
  public boolean isBassStave()
  {
    return getProperty(STAVE_TYPE).equals(BASS_STAVE);
  }
  
  public boolean isGrandStave()
  {
    return getProperty(STAVE_TYPE).equals(GRAND_STAVE);
  }
  
  public boolean isPianoStave()
  {
    return getProperty(STAVE_TYPE).equals(PIANO_STAVE);
  }
  
  private static int findLastNonRest(Phrase paramPhrase)
  {
    for (int i = paramPhrase.size() - 1; (i >= 0) && (paramPhrase.getNote(i).getPitch() == Integer.MIN_VALUE); i--) {}
    return i;
  }
  
  private static void adjustFinalRestRhythm(Phrase paramPhrase, double paramDouble1, double paramDouble2)
  {
    double d = getFinalRestRhythm(paramPhrase);
    if (paramDouble1 - d > 0.001D)
    {
      Note localNote = new Note();
      localNote.setFrequency(-2.147483648E9D);
      localNote.setRhythmValue(paramDouble1 - d);
      localNote.setDuration((paramDouble1 - d) * (paramDouble2 / paramDouble1));
      paramPhrase.addNote(localNote);
    }
  }
  
  private static void adjustOtherNotesTotalRhythm(Phrase paramPhrase, double paramDouble)
  {
    double d1 = getOtherNotesTotalRhythm(paramPhrase);
    if (d1 > 0.0D)
    {
      double d2 = paramDouble / d1;
      int i = findLastNonRest(paramPhrase);
      for (int j = 0; j < i; j++) {
        paramPhrase.getNote(j).setRhythmValue(paramPhrase.getNote(j).getRhythmValue() * d2);
      }
    }
  }
  
  private static void adjustOtherNotesTotalDuration(Phrase paramPhrase, double paramDouble)
  {
    double d1 = getOtherNotesTotalDuration(paramPhrase);
    if (d1 > 0.0D)
    {
      double d2 = paramDouble / d1;
      int i = findLastNonRest(paramPhrase);
      for (int j = 0; j < i; j++) {
        if (paramPhrase.getNote(j).getPitch() != Integer.MIN_VALUE) {
          paramPhrase.getNote(j).setDuration(paramPhrase.getNote(j).getDuration() * d2);
        } else {
          paramPhrase.getNote(j).setDuration(paramPhrase.getNote(j).getRhythmValue());
        }
      }
    }
  }
  
  private static double getFinalRestRhythm(Phrase paramPhrase)
  {
    double d = 0.0D;
    int j = paramPhrase.size();
    for (int i = findLastNonRest(paramPhrase) + 1; i < j; i++) {
      d += paramPhrase.getNote(i).getRhythmValue();
    }
    return d;
  }
  
  private static double getFinalRestDuration(Phrase paramPhrase)
  {
    double d = 0.0D;
    int j = paramPhrase.size();
    for (int i = findLastNonRest(paramPhrase) + 1; i < j; i++) {
      d += paramPhrase.getNote(i).getDuration();
    }
    return d;
  }
  
  private static double getOtherNotesTotalRhythm(Phrase paramPhrase)
  {
    double d = 0.0D;
    int i = 0;
    int j = findLastNonRest(paramPhrase);
    while (i < j)
    {
      d += paramPhrase.getNote(i).getRhythmValue();
      i++;
    }
    return d;
  }
  
  private static double getOtherNotesTotalDuration(Phrase paramPhrase)
  {
    double d = 0.0D;
    int i = 0;
    int j = findLastNonRest(paramPhrase);
    while (i < j)
    {
      if (paramPhrase.getNote(i).getPitch() != Integer.MIN_VALUE) {
        d += paramPhrase.getNote(i).getDuration();
      }
      i++;
    }
    return d;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\StavePhraseProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */