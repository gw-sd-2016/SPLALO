package jm.music.tools;

import java.util.Hashtable;
import jm.music.data.Note;
import jm.music.data.Phrase;

public final class PhraseAnalysis
{
  public static final String[] featureNames = { "01 - Pitch Variety", "02 - Pitch Range", "03 - Key Centeredness", "04 - Tonal Deviation", "05 - Dissonance", "06 - Overall Pitch Direction", "07 - Melodic Direction Stability", "08 - Pitch Movement By Tonal Step", "09 - Leap Compensation", "10 - Climax Strength", "11 - Climax Position", "12 - Climax Tonality", "13 - Note Density", "14 - Rest Density", "15 - Rhythmic Variety", "16 - Rhythmic Range", "17 - Syncopation", "18 - Repeated Pitch Density", "19 - Repeated Rhythmic Value Density", "20 - Repeated Pitch Patterns Of Three", "21 - Repeated Pitch Patterns Of Four", "22 - Repeated Rhythm Patterns Of Three", "23 - Repeated Rhythm Patterns Of Four" };
  public static final String NOTELIST_EXCEPTION_STRING = "NoteListException";
  public static final String QUANTISATION_EXCEPTION_STRING = "QuantisationException";
  public static final double NOTELIST_EXCEPTION_CONSTANT = -1.0D;
  public static final double QUANTISATION_EXCEPTION_CONSTANT = -2.0D;
  public static final int FEATURE_COUNT = 23;
  public static final int MAX_PITCH_RANGE = 24;
  public static final double MAX_RHYTHM_RANGE = 16.0D;
  public static final int[] MAJOR_SCALE = { 0, 2, 4, 5, 7, 9, 11 };
  public static final int[] MINOR_SCALE = { 0, 2, 3, 5, 7, 8, 10 };
  public static final int[] PRIMARY_NOTES = { 0, 7 };
  public static final int BIG_JUMP_INTERVAL = 8;
  public static final int MAX_DISTINCT_RHYTHMS = 16;
  public static final int INTERVAL_WITH_REST = 255;
  private static final int[] GOOD_INTERVALS = { 0, 1, 2, 3, 4, 5, 7, 8, 9, 12 };
  private static final int[] BAD_INTERVALS = { 6, 11 };
  private static final int SEMITONES_PER_OCTAVE = 12;
  
  private PhraseAnalysis() {}
  
  public static String[] getAllStatisticsAsStrings(Phrase paramPhrase, double paramDouble, int paramInt, int[] paramArrayOfInt)
  {
    return getAllStatisticsAsStrings(paramPhrase.getNoteArray(), paramDouble, paramInt, paramArrayOfInt);
  }
  
  public static String[] getAllStatisticsAsStrings(Note[] paramArrayOfNote, double paramDouble, int paramInt, int[] paramArrayOfInt)
  {
    String[] arrayOfString = new String[23];
    try
    {
      arrayOfString[0] = Double.toString(pitchVariety(paramArrayOfNote));
    }
    catch (NoteListException localNoteListException1)
    {
      arrayOfString[0] = "NoteListException";
    }
    try
    {
      arrayOfString[1] = Double.toString(pitchRangePerSpan(paramArrayOfNote));
    }
    catch (NoteListException localNoteListException2)
    {
      arrayOfString[1] = "NoteListException";
    }
    try
    {
      arrayOfString[2] = Double.toString(keyCenteredness(paramArrayOfNote, paramDouble, paramInt));
    }
    catch (NoteListException localNoteListException3)
    {
      arrayOfString[2] = "NoteListException";
    }
    catch (QuantisationException localQuantisationException1)
    {
      arrayOfString[2] = "QuantisationException";
    }
    try
    {
      arrayOfString[3] = Double.toString(tonalDeviation(paramArrayOfNote, paramDouble, paramInt, paramArrayOfInt));
    }
    catch (NoteListException localNoteListException4)
    {
      arrayOfString[3] = "NoteListException";
    }
    catch (QuantisationException localQuantisationException2)
    {
      arrayOfString[3] = "QuantisationException";
    }
    arrayOfString[4] = Double.toString(dissonance(paramArrayOfNote));
    arrayOfString[5] = Double.toString(overallPitchDirection(paramArrayOfNote));
    try
    {
      arrayOfString[6] = Double.toString(melodicDirectionStability(paramArrayOfNote));
    }
    catch (NoteListException localNoteListException5)
    {
      arrayOfString[6] = "NoteListException";
    }
    try
    {
      arrayOfString[7] = Double.toString(movementByStep(paramArrayOfNote, paramInt, paramArrayOfInt));
    }
    catch (NoteListException localNoteListException6)
    {
      arrayOfString[7] = "NoteListException";
    }
    arrayOfString[8] = Double.toString(leapCompensation(paramArrayOfNote));
    try
    {
      arrayOfString[9] = Double.toString(climaxStrength(paramArrayOfNote));
    }
    catch (NoteListException localNoteListException7)
    {
      arrayOfString[9] = "NoteListException";
    }
    try
    {
      arrayOfString[10] = Double.toString(climaxPosition(paramArrayOfNote));
    }
    catch (NoteListException localNoteListException8)
    {
      arrayOfString[10] = "NoteListException";
    }
    try
    {
      arrayOfString[11] = Double.toString(climaxTonality(paramArrayOfNote, paramInt, paramArrayOfInt));
    }
    catch (NoteListException localNoteListException9)
    {
      arrayOfString[11] = "NoteListException";
    }
    try
    {
      arrayOfString[12] = Double.toString(noteDensity(paramArrayOfNote, paramDouble));
    }
    catch (NoteListException localNoteListException10)
    {
      arrayOfString[12] = "NoteListException";
    }
    catch (QuantisationException localQuantisationException3)
    {
      arrayOfString[12] = "QuantisationException";
    }
    try
    {
      arrayOfString[13] = Double.toString(noteDensity(paramArrayOfNote, paramDouble));
    }
    catch (NoteListException localNoteListException11)
    {
      arrayOfString[13] = "NoteListException";
    }
    catch (QuantisationException localQuantisationException4)
    {
      arrayOfString[13] = "QuantisationException";
    }
    arrayOfString[14] = Double.toString(rhythmicVariety(paramArrayOfNote));
    try
    {
      arrayOfString[15] = Double.toString(rhythmRangePerSpan(paramArrayOfNote));
    }
    catch (NoteListException localNoteListException12)
    {
      arrayOfString[15] = "NoteListException";
    }
    arrayOfString[16] = Double.toString(syncopation(paramArrayOfNote));
    try
    {
      arrayOfString[17] = Double.toString(repeatedPitchDensity(paramArrayOfNote));
    }
    catch (NoteListException localNoteListException13)
    {
      arrayOfString[17] = "NoteListException";
    }
    try
    {
      arrayOfString[18] = Double.toString(repeatedRhythmicValueDensity(paramArrayOfNote));
    }
    catch (NoteListException localNoteListException14)
    {
      arrayOfString[18] = "NoteListException";
    }
    try
    {
      arrayOfString[19] = Double.toString(repeatedPitchPatterns(paramArrayOfNote, 3));
    }
    catch (NoteListException localNoteListException15)
    {
      arrayOfString[19] = "NoteListException";
    }
    try
    {
      arrayOfString[20] = Double.toString(repeatedPitchPatterns(paramArrayOfNote, 4));
    }
    catch (NoteListException localNoteListException16)
    {
      arrayOfString[20] = "NoteListException";
    }
    try
    {
      arrayOfString[21] = Double.toString(repeatedRhythmPatterns(paramArrayOfNote, 3));
    }
    catch (NoteListException localNoteListException17)
    {
      arrayOfString[21] = "NoteListException";
    }
    try
    {
      arrayOfString[22] = Double.toString(repeatedRhythmPatterns(paramArrayOfNote, 4));
    }
    catch (NoteListException localNoteListException18)
    {
      arrayOfString[22] = "NoteListException";
    }
    return arrayOfString;
  }
  
  public static double[] getAllStatisticsAsDoubles(Phrase paramPhrase, double paramDouble, int paramInt, int[] paramArrayOfInt)
  {
    return getAllStatisticsAsDoubles(paramPhrase.getNoteArray(), paramDouble, paramInt, paramArrayOfInt);
  }
  
  public static double[] getAllStatisticsAsDoubles(Note[] paramArrayOfNote, double paramDouble, int paramInt, int[] paramArrayOfInt)
  {
    double[] arrayOfDouble = new double[23];
    try
    {
      arrayOfDouble[0] = pitchVariety(paramArrayOfNote);
    }
    catch (NoteListException localNoteListException1)
    {
      arrayOfDouble[0] = -1.0D;
    }
    try
    {
      arrayOfDouble[1] = pitchRangePerSpan(paramArrayOfNote);
    }
    catch (NoteListException localNoteListException2)
    {
      arrayOfDouble[1] = -1.0D;
    }
    try
    {
      arrayOfDouble[2] = keyCenteredness(paramArrayOfNote, paramDouble, paramInt);
    }
    catch (NoteListException localNoteListException3)
    {
      arrayOfDouble[2] = -1.0D;
    }
    catch (QuantisationException localQuantisationException1)
    {
      arrayOfDouble[2] = -2.0D;
    }
    try
    {
      arrayOfDouble[3] = tonalDeviation(paramArrayOfNote, paramDouble, paramInt, paramArrayOfInt);
    }
    catch (NoteListException localNoteListException4)
    {
      arrayOfDouble[3] = -1.0D;
    }
    catch (QuantisationException localQuantisationException2)
    {
      arrayOfDouble[3] = -2.0D;
    }
    arrayOfDouble[4] = dissonance(paramArrayOfNote);
    arrayOfDouble[5] = overallPitchDirection(paramArrayOfNote);
    try
    {
      arrayOfDouble[6] = melodicDirectionStability(paramArrayOfNote);
    }
    catch (NoteListException localNoteListException5)
    {
      arrayOfDouble[6] = -1.0D;
    }
    try
    {
      arrayOfDouble[7] = movementByStep(paramArrayOfNote, paramInt, paramArrayOfInt);
    }
    catch (NoteListException localNoteListException6)
    {
      arrayOfDouble[7] = -1.0D;
    }
    arrayOfDouble[8] = leapCompensation(paramArrayOfNote);
    try
    {
      arrayOfDouble[9] = climaxStrength(paramArrayOfNote);
    }
    catch (NoteListException localNoteListException7)
    {
      arrayOfDouble[9] = -1.0D;
    }
    try
    {
      arrayOfDouble[10] = climaxPosition(paramArrayOfNote);
    }
    catch (NoteListException localNoteListException8)
    {
      arrayOfDouble[10] = -1.0D;
    }
    try
    {
      arrayOfDouble[11] = climaxTonality(paramArrayOfNote, paramInt, paramArrayOfInt);
    }
    catch (NoteListException localNoteListException9)
    {
      arrayOfDouble[11] = -1.0D;
    }
    try
    {
      arrayOfDouble[12] = noteDensity(paramArrayOfNote, paramDouble);
    }
    catch (NoteListException localNoteListException10)
    {
      arrayOfDouble[12] = -1.0D;
    }
    catch (QuantisationException localQuantisationException3)
    {
      arrayOfDouble[12] = -2.0D;
    }
    try
    {
      arrayOfDouble[13] = noteDensity(paramArrayOfNote, paramDouble);
    }
    catch (NoteListException localNoteListException11)
    {
      arrayOfDouble[13] = -1.0D;
    }
    catch (QuantisationException localQuantisationException4)
    {
      arrayOfDouble[13] = -2.0D;
    }
    arrayOfDouble[14] = rhythmicVariety(paramArrayOfNote);
    try
    {
      arrayOfDouble[15] = rhythmRangePerSpan(paramArrayOfNote);
    }
    catch (NoteListException localNoteListException12)
    {
      arrayOfDouble[15] = -1.0D;
    }
    arrayOfDouble[16] = syncopation(paramArrayOfNote);
    try
    {
      arrayOfDouble[17] = repeatedPitchDensity(paramArrayOfNote);
    }
    catch (NoteListException localNoteListException13)
    {
      arrayOfDouble[17] = -1.0D;
    }
    try
    {
      arrayOfDouble[18] = repeatedRhythmicValueDensity(paramArrayOfNote);
    }
    catch (NoteListException localNoteListException14)
    {
      arrayOfDouble[18] = -1.0D;
    }
    try
    {
      arrayOfDouble[19] = repeatedPitchPatterns(paramArrayOfNote, 3);
    }
    catch (NoteListException localNoteListException15)
    {
      arrayOfDouble[19] = -1.0D;
    }
    try
    {
      arrayOfDouble[20] = repeatedPitchPatterns(paramArrayOfNote, 4);
    }
    catch (NoteListException localNoteListException16)
    {
      arrayOfDouble[20] = -1.0D;
    }
    try
    {
      arrayOfDouble[21] = repeatedRhythmPatterns(paramArrayOfNote, 3);
    }
    catch (NoteListException localNoteListException17)
    {
      arrayOfDouble[21] = -1.0D;
    }
    try
    {
      arrayOfDouble[22] = repeatedRhythmPatterns(paramArrayOfNote, 4);
    }
    catch (NoteListException localNoteListException18)
    {
      arrayOfDouble[22] = -1.0D;
    }
    return arrayOfDouble;
  }
  
  public static Hashtable getAllStatistics(Phrase paramPhrase, double paramDouble, int paramInt, int[] paramArrayOfInt)
  {
    return getAllStatistics(paramPhrase.getNoteArray(), paramDouble, paramInt, paramArrayOfInt);
  }
  
  public static Hashtable getAllStatistics(Note[] paramArrayOfNote, double paramDouble, int paramInt, int[] paramArrayOfInt)
  {
    String[] arrayOfString = getAllStatisticsAsStrings(paramArrayOfNote, paramDouble, paramInt, paramArrayOfInt);
    Hashtable localHashtable = new Hashtable();
    for (int i = 0; i < featureNames.length; i++) {
      localHashtable.put(featureNames[i], arrayOfString[i]);
    }
    return localHashtable;
  }
  
  public static double noteDensity(Phrase paramPhrase, double paramDouble)
    throws NoteListException, QuantisationException
  {
    return noteDensity(paramPhrase.getNoteArray(), paramDouble);
  }
  
  public static double noteDensity(Note[] paramArrayOfNote, double paramDouble)
    throws NoteListException, QuantisationException
  {
    int i = quantumCount(paramArrayOfNote, paramDouble);
    if (i != 0) {
      return noteCount(paramArrayOfNote) / i;
    }
    throw new NoteListException("The length of the melody should be greater than 0.");
  }
  
  public static double pitchVariety(Phrase paramPhrase)
    throws NoteListException
  {
    return pitchVariety(paramPhrase.getNoteArray());
  }
  
  public static double pitchVariety(Note[] paramArrayOfNote)
    throws NoteListException
  {
    int i = noteCount(paramArrayOfNote);
    if (i != 0) {
      return distinctPitchCount(paramArrayOfNote) / i;
    }
    throw new NoteListException("The melody should contain at least one note.");
  }
  
  public static double rhythmicVariety(Phrase paramPhrase)
  {
    return rhythmicVariety(paramPhrase.getNoteArray());
  }
  
  public static double rhythmicVariety(Note[] paramArrayOfNote)
  {
    return distinctRhythmCount(paramArrayOfNote) / 16.0D;
  }
  
  public static double climaxStrength(Phrase paramPhrase)
    throws NoteListException
  {
    return climaxStrength(paramPhrase.getNoteArray());
  }
  
  public static double climaxStrength(Note[] paramArrayOfNote)
    throws NoteListException
  {
    int i = 0;
    int j = 0;
    for (int m = 0; m < paramArrayOfNote.length; m++)
    {
      int k = paramArrayOfNote[m].getPitch();
      if (k != Integer.MIN_VALUE) {
        if (k > j)
        {
          j = k;
          i = 1;
        }
        else if (k == j)
        {
          i++;
        }
      }
    }
    if (i != 0) {
      return 1.0D / i;
    }
    throw new NoteListException("The melody should contain at least one note.");
  }
  
  public static double restDensity(Phrase paramPhrase, double paramDouble)
    throws NoteListException, QuantisationException
  {
    return restDensity(paramPhrase.getNoteArray(), paramDouble);
  }
  
  public static double restDensity(Note[] paramArrayOfNote, double paramDouble)
    throws NoteListException, QuantisationException
  {
    int i = quantumCount(paramArrayOfNote, paramDouble);
    if (i != 0) {
      return restQuantumCount(paramArrayOfNote, paramDouble) / i;
    }
    throw new NoteListException("The length of the melody should be greater than 0.");
  }
  
  public static double tonalDeviation(Phrase paramPhrase, double paramDouble, int paramInt, int[] paramArrayOfInt)
    throws NoteListException, QuantisationException
  {
    return tonalDeviation(paramPhrase.getNoteArray(), paramDouble, paramInt, paramArrayOfInt);
  }
  
  public static double tonalDeviation(Note[] paramArrayOfNote, double paramDouble, int paramInt, int[] paramArrayOfInt)
    throws NoteListException, QuantisationException
  {
    int i = quantumCount(paramArrayOfNote, paramDouble);
    if (i != 0) {
      return nonScaleQuantumCount(paramArrayOfNote, paramDouble, paramInt, paramArrayOfInt) / i;
    }
    throw new NoteListException("The length of the melody should be greater than 0.");
  }
  
  public static double keyCenteredness(Phrase paramPhrase, double paramDouble, int paramInt)
    throws QuantisationException, NoteListException
  {
    return keyCenteredness(paramPhrase.getNoteArray(), paramDouble, paramInt);
  }
  
  public static double keyCenteredness(Note[] paramArrayOfNote, double paramDouble, int paramInt)
    throws QuantisationException, NoteListException
  {
    int i = quantumCount(paramArrayOfNote, paramDouble);
    if (i > 0) {
      return primaryQuantumCount(paramArrayOfNote, paramDouble, paramInt) / i;
    }
    throw new NoteListException("The length of the melody should be greater than 0.");
  }
  
  public static double pitchRangePerSpan(Phrase paramPhrase)
    throws NoteListException
  {
    return pitchRangePerSpan(paramPhrase.getNoteArray());
  }
  
  public static double pitchRangePerSpan(Note[] paramArrayOfNote)
    throws NoteListException
  {
    double d = pitchRange(paramArrayOfNote) / 24.0D;
    return d < 1.0D ? d : 1.0D;
  }
  
  public static double rhythmRangePerSpan(Phrase paramPhrase)
    throws NoteListException
  {
    return rhythmRangePerSpan(paramPhrase.getNoteArray());
  }
  
  public static double rhythmRangePerSpan(Note[] paramArrayOfNote)
    throws NoteListException
  {
    double d = rhythmRange(paramArrayOfNote) / 16.0D;
    return d < 1.0D ? d : 1.0D;
  }
  
  public static double repeatedPitchDensity(Phrase paramPhrase)
    throws NoteListException
  {
    return repeatedPitchDensity(paramPhrase.getNoteArray());
  }
  
  public static double repeatedPitchDensity(Note[] paramArrayOfNote)
    throws NoteListException
  {
    int i = intervalCount(paramArrayOfNote);
    if (i != 0) {
      return consecutiveIdenticalPitches(paramArrayOfNote) / i;
    }
    throw new NoteListException("The melody should contain at least two notes.");
  }
  
  public static double repeatedRhythmicValueDensity(Phrase paramPhrase)
    throws NoteListException
  {
    return repeatedRhythmicValueDensity(paramPhrase.getNoteArray());
  }
  
  public static double repeatedRhythmicValueDensity(Note[] paramArrayOfNote)
    throws NoteListException
  {
    int i = intervalCount(paramArrayOfNote);
    if (i != 0) {
      return consecutiveIdenticalRhythms(paramArrayOfNote) / i;
    }
    throw new NoteListException("The melody should contain at least two notes.");
  }
  
  public static double melodicDirectionStability(Phrase paramPhrase)
    throws NoteListException
  {
    return melodicDirectionStability(paramPhrase.getNoteArray());
  }
  
  public static double melodicDirectionStability(Note[] paramArrayOfNote)
    throws NoteListException
  {
    int i = intervalCount(paramArrayOfNote);
    if (i - 1 != 0) {
      return sameDirectionIntervalCount(paramArrayOfNote) / i;
    }
    throw new NoteListException("The melody should contain at least three notes.");
  }
  
  public static double overallPitchDirection(Phrase paramPhrase)
  {
    return overallPitchDirection(paramPhrase.getNoteArray());
  }
  
  public static double overallPitchDirection(Note[] paramArrayOfNote)
  {
    double d = intervalSemitoneCount(paramArrayOfNote);
    if (d != 0.0D) {
      return risingSemitoneCount(paramArrayOfNote) / d;
    }
    return 0.5D;
  }
  
  public static double movementByStep(Phrase paramPhrase, int paramInt, int[] paramArrayOfInt)
    throws NoteListException
  {
    return movementByStep(paramPhrase.getNoteArray(), paramInt, paramArrayOfInt);
  }
  
  public static double movementByStep(Note[] paramArrayOfNote, int paramInt, int[] paramArrayOfInt)
    throws NoteListException
  {
    int i = intervalCount(paramArrayOfNote);
    if (i > 0) {
      return stepIntervalCount(paramArrayOfNote, paramInt, paramArrayOfInt) / i;
    }
    throw new NoteListException("The melody should contain at least two notes.");
  }
  
  public static double dissonance(Phrase paramPhrase)
  {
    return dissonance(paramPhrase.getNoteArray());
  }
  
  public static double dissonance(Note[] paramArrayOfNote)
  {
    int[] arrayOfInt = pitchIntervals(paramArrayOfNote);
    int i = 0;
    for (int j = 0; j < arrayOfInt.length; j++)
    {
      if (arrayOfInt[j] > 127) {
        arrayOfInt[j] -= 255;
      }
      arrayOfInt[j] = Math.abs(arrayOfInt[j]);
      if (arrayOfInt[j] > 12) {
        i += 2;
      } else {
        i += rateDissonance(arrayOfInt[j]);
      }
    }
    return i / (2.0D * arrayOfInt.length);
  }
  
  public static double leapCompensation(Phrase paramPhrase)
  {
    return leapCompensation(paramPhrase.getNoteArray());
  }
  
  public static double leapCompensation(Note[] paramArrayOfNote)
  {
    int i = bigJumpCount(paramArrayOfNote);
    if (i != 0) {
      return (i - bigJumpFollowedByStepBackCount(paramArrayOfNote)) / i;
    }
    return 0.0D;
  }
  
  public static double syncopation(Phrase paramPhrase)
  {
    return syncopation(paramPhrase.getNoteArray());
  }
  
  public static double syncopation(Note[] paramArrayOfNote)
  {
    int i = 0;
    double d1 = 0.0D;
    for (int j = 0; j < paramArrayOfNote.length; j++)
    {
      double d2 = paramArrayOfNote[j].getRhythmValue();
      if ((d2 >= 1.0D) && (paramArrayOfNote[j].getPitch() != Integer.MIN_VALUE) && (d1 % 1.0D != 0.0D)) {
        i++;
      }
      d1 += d2;
    }
    return i / Math.floor(rhythmValueCount(paramArrayOfNote) - 1.0D);
  }
  
  public static double repeatedPitchPatterns(Phrase paramPhrase, int paramInt)
    throws NoteListException
  {
    return repeatedPitchPatterns(paramPhrase.getNoteArray(), paramInt);
  }
  
  public static double repeatedPitchPatterns(Note[] paramArrayOfNote, int paramInt)
    throws NoteListException
  {
    int i = intervalCount(paramArrayOfNote) - paramInt;
    if (i > 0) {
      return pitchPatternCount(paramArrayOfNote, paramInt) / i;
    }
    throw new NoteListException("The melody must contain more intervals than the size of the pattern being searched for.");
  }
  
  public static double repeatedRhythmPatterns(Phrase paramPhrase, int paramInt)
    throws NoteListException
  {
    return repeatedRhythmPatterns(paramPhrase.getNoteArray(), paramInt);
  }
  
  public static double repeatedRhythmPatterns(Note[] paramArrayOfNote, int paramInt)
    throws NoteListException
  {
    int i = intervalCount(paramArrayOfNote) - paramInt;
    if (i > 0) {
      return rhythmPatternCount(paramArrayOfNote, paramInt) / i;
    }
    throw new NoteListException("The melody must contain more intervals than the size of the pattern being searched for.");
  }
  
  public static double climaxPosition(Phrase paramPhrase)
    throws NoteListException
  {
    return climaxPosition(paramPhrase.getNoteArray());
  }
  
  public static double climaxPosition(Note[] paramArrayOfNote)
    throws NoteListException
  {
    if (noteCount(paramArrayOfNote) > 0)
    {
      double d1 = 0.0D;
      int i = 0;
      int j = 0;
      for (int k = 0; k < paramArrayOfNote.length; k++)
      {
        d1 += paramArrayOfNote[k].getRhythmValue();
        int m = paramArrayOfNote[k].getPitch();
        if ((m != Integer.MIN_VALUE) && (m >= i))
        {
          i = m;
          j = k;
        }
      }
      double d2 = 0.0D;
      for (int n = 0; n < j - 1; n++) {
        d2 += paramArrayOfNote[n].getRhythmValue();
      }
      return d2 / d1;
    }
    throw new NoteListException("The melody should contain at least one note.");
  }
  
  public static double climaxTonality(Phrase paramPhrase, int paramInt, int[] paramArrayOfInt)
    throws NoteListException
  {
    return climaxTonality(paramPhrase.getNoteArray(), paramInt, paramArrayOfInt);
  }
  
  public static double climaxTonality(Note[] paramArrayOfNote, int paramInt, int[] paramArrayOfInt)
    throws NoteListException
  {
    if (noteCount(paramArrayOfNote) > 0)
    {
      int i = 0;
      for (int k = 0; k < paramArrayOfNote.length; k++)
      {
        int j = paramArrayOfNote[k].getPitch();
        if ((j != Integer.MIN_VALUE) && (j > i)) {
          i = j;
        }
      }
      i = pitchToDegree(i, paramInt);
      if (isElementOf(i, PRIMARY_NOTES)) {
        return 0.0D;
      }
      if (isElementOf(i, paramArrayOfInt)) {
        return 0.5D;
      }
      return 1.0D;
    }
    throw new NoteListException("The melody should contain at least one note.");
  }
  
  public static int noteCount(Phrase paramPhrase)
  {
    return noteCount(paramPhrase.getNoteArray());
  }
  
  public static int noteCount(Note[] paramArrayOfNote)
  {
    int i = 0;
    for (int j = 0; j < paramArrayOfNote.length; j++) {
      if (paramArrayOfNote[j].getPitch() != Integer.MIN_VALUE) {
        i++;
      }
    }
    return i;
  }
  
  public static int quantumCount(Phrase paramPhrase, double paramDouble)
    throws QuantisationException
  {
    return quantumCount(paramPhrase.getNoteArray(), paramDouble);
  }
  
  public static int quantumCount(Note[] paramArrayOfNote, double paramDouble)
    throws QuantisationException
  {
    if (isQuantised(paramArrayOfNote, paramDouble))
    {
      int i = 0;
      for (int j = 0; j < paramArrayOfNote.length; j++) {
        i += (int)(paramArrayOfNote[j].getRhythmValue() / paramDouble);
      }
      return i;
    }
    throw new QuantisationException("Every rhythm value must be a multiple of the quantum duration.");
  }
  
  public static int distinctPitchCount(Phrase paramPhrase)
  {
    return distinctPitchCount(paramPhrase.getNoteArray());
  }
  
  public static int distinctPitchCount(Note[] paramArrayOfNote)
  {
    int[] arrayOfInt = new int[paramArrayOfNote.length];
    int i = 0;
    for (int k = 0; k < paramArrayOfNote.length; k++)
    {
      int j = paramArrayOfNote[k].getPitch();
      if ((j != Integer.MIN_VALUE) && (!isElementOf(j, arrayOfInt, i)))
      {
        arrayOfInt[i] = j;
        i++;
      }
    }
    return i;
  }
  
  public static int distinctRhythmCount(Phrase paramPhrase)
  {
    return distinctRhythmCount(paramPhrase.getNoteArray());
  }
  
  public static int distinctRhythmCount(Note[] paramArrayOfNote)
  {
    double[] arrayOfDouble = new double[paramArrayOfNote.length];
    int i = 0;
    for (int j = 0; j < paramArrayOfNote.length; j++) {
      if (paramArrayOfNote[j].getPitch() <= Integer.MIN_VALUE)
      {
        double d = paramArrayOfNote[j].getRhythmValue();
        if (!isElementOf(d, arrayOfDouble, i))
        {
          arrayOfDouble[i] = d;
          i++;
        }
      }
    }
    return i;
  }
  
  public static int restQuantumCount(Phrase paramPhrase, double paramDouble)
    throws QuantisationException
  {
    return restQuantumCount(paramPhrase.getNoteArray(), paramDouble);
  }
  
  public static int restQuantumCount(Note[] paramArrayOfNote, double paramDouble)
    throws QuantisationException
  {
    if (isQuantised(paramArrayOfNote, paramDouble))
    {
      int i = 0;
      for (int j = 0; j < paramArrayOfNote.length; j++) {
        if (paramArrayOfNote[j].getPitch() == Integer.MIN_VALUE) {
          i += (int)(paramArrayOfNote[j].getRhythmValue() / paramDouble);
        }
      }
      return i;
    }
    throw new QuantisationException("Every rhythm value must be a multiple of the quantum duration.");
  }
  
  public static int nonScaleQuantumCount(Phrase paramPhrase, double paramDouble, int paramInt, int[] paramArrayOfInt)
    throws QuantisationException
  {
    return nonScaleQuantumCount(paramPhrase.getNoteArray(), paramDouble, paramInt, paramArrayOfInt);
  }
  
  public static int nonScaleQuantumCount(Note[] paramArrayOfNote, double paramDouble, int paramInt, int[] paramArrayOfInt)
    throws QuantisationException
  {
    if (isQuantised(paramArrayOfNote, paramDouble))
    {
      int i = 0;
      for (int k = 0; k < paramArrayOfNote.length; k++)
      {
        int j = paramArrayOfNote[k].getPitch();
        if ((j != Integer.MIN_VALUE) && (!isElementOf(pitchToDegree(j, paramInt), paramArrayOfInt))) {
          i += (int)(paramArrayOfNote[k].getRhythmValue() / paramDouble);
        }
      }
      return i;
    }
    throw new QuantisationException("Every rhythm value must be a multiple of the quantum duration.");
  }
  
  public static int primaryQuantumCount(Phrase paramPhrase, double paramDouble, int paramInt)
    throws QuantisationException
  {
    return primaryQuantumCount(paramPhrase.getNoteArray(), paramDouble, paramInt);
  }
  
  public static int primaryQuantumCount(Note[] paramArrayOfNote, double paramDouble, int paramInt)
    throws QuantisationException
  {
    if (isQuantised(paramArrayOfNote, paramDouble))
    {
      int i = 0;
      for (int k = 0; k < paramArrayOfNote.length; k++)
      {
        int j = paramArrayOfNote[k].getPitch();
        if ((j == Integer.MIN_VALUE) || (isElementOf(pitchToDegree(j, paramInt), PRIMARY_NOTES))) {
          i += (int)(paramArrayOfNote[k].getRhythmValue() / paramDouble);
        }
      }
      return i;
    }
    throw new QuantisationException("Every rhythm value must be a multiple of the quantum duration.");
  }
  
  public static int pitchRange(Phrase paramPhrase)
    throws NoteListException
  {
    return pitchRange(paramPhrase.getNoteArray());
  }
  
  public static int pitchRange(Note[] paramArrayOfNote)
    throws NoteListException
  {
    int i = 0;
    int j = 127;
    for (int m = 0; m < paramArrayOfNote.length; m++)
    {
      int k = paramArrayOfNote[m].getPitch();
      if (k != Integer.MIN_VALUE) {
        if (k > i) {
          i = k;
        } else if (k < j) {
          j = k;
        }
      }
    }
    if ((i != 0) && (j != 127)) {
      return i - j;
    }
    throw new NoteListException("There are no notes in the melody.");
  }
  
  public static double rhythmRange(Phrase paramPhrase)
    throws NoteListException
  {
    return rhythmRange(paramPhrase.getNoteArray());
  }
  
  public static double rhythmRange(Note[] paramArrayOfNote)
    throws NoteListException
  {
    double d1 = 0.0D;
    double d2 = Double.MAX_VALUE;
    for (int i = 0; i < paramArrayOfNote.length; i++)
    {
      double d3 = paramArrayOfNote[i].getRhythmValue();
      if (paramArrayOfNote[i].getPitch() != Integer.MIN_VALUE) {
        if (d3 > d1) {
          d1 = d3;
        } else if (d3 < d2) {
          d2 = d3;
        }
      }
    }
    if ((d1 != 0.0D) && (d2 != Double.MAX_VALUE)) {
      return d1 / d2;
    }
    throw new NoteListException("There are no notes in the melody.");
  }
  
  public static int consecutiveIdenticalPitches(Phrase paramPhrase)
  {
    return consecutiveIdenticalPitches(paramPhrase.getNoteArray());
  }
  
  public static int consecutiveIdenticalPitches(Note[] paramArrayOfNote)
  {
    int[] arrayOfInt = pitchIntervals(paramArrayOfNote);
    int i = 0;
    for (int j = 0; j < arrayOfInt.length; j++) {
      if ((arrayOfInt[j] == 0) || (arrayOfInt[j] == 255)) {
        i++;
      }
    }
    return i;
  }
  
  public static int consecutiveIdenticalRhythms(Phrase paramPhrase)
  {
    return consecutiveIdenticalRhythms(paramPhrase.getNoteArray());
  }
  
  public static int consecutiveIdenticalRhythms(Note[] paramArrayOfNote)
  {
    double[] arrayOfDouble = rhythmIntervals(paramArrayOfNote);
    int i = 0;
    for (int j = 0; j < arrayOfDouble.length - 1; j++) {
      if (((arrayOfDouble[j] == 1.0D) && (arrayOfDouble[(j + 1)] > 0.0D)) || ((arrayOfDouble[j] == -1.0D) && (arrayOfDouble[(j + 1)] < 0.0D))) {
        i++;
      }
    }
    if (arrayOfDouble[(arrayOfDouble.length - 1)] == 1.0D) {
      i++;
    }
    return i;
  }
  
  public static int sameDirectionIntervalCount(Phrase paramPhrase)
  {
    return sameDirectionIntervalCount(paramPhrase.getNoteArray());
  }
  
  public static int sameDirectionIntervalCount(Note[] paramArrayOfNote)
  {
    int i = 0;
    int[] arrayOfInt = pitchIntervals(paramArrayOfNote);
    if (arrayOfInt.length > 0)
    {
      if (arrayOfInt[0] > 127) {
        arrayOfInt[0] -= 255;
      }
      for (int j = 1; j < arrayOfInt.length; j++)
      {
        if (arrayOfInt[j] > 127) {
          arrayOfInt[j] -= 255;
        }
        if (((arrayOfInt[j] > 0) && (arrayOfInt[(j - 1)] > 0)) || ((arrayOfInt[j] == 0) && (arrayOfInt[(j - 1)] == 0)) || ((arrayOfInt[j] < 0) && (arrayOfInt[(j - 1)] < 0))) {
          i++;
        }
      }
    }
    return i;
  }
  
  public static int intervalCount(Phrase paramPhrase)
  {
    return intervalCount(paramPhrase.getNoteArray());
  }
  
  public static int intervalCount(Note[] paramArrayOfNote)
  {
    int i = noteCount(paramArrayOfNote) - 1;
    return i < 1 ? 0 : i;
  }
  
  public static int[] pitchIntervals(Phrase paramPhrase)
  {
    return pitchIntervals(paramPhrase.getNoteArray());
  }
  
  public static int[] pitchIntervals(Note[] paramArrayOfNote)
  {
    int i = intervalCount(paramArrayOfNote);
    if (i > 0)
    {
      int[] arrayOfInt = new int[i];
      int j = -1;
      while (paramArrayOfNote[(++j)].getPitch() == Integer.MIN_VALUE) {}
      int k = paramArrayOfNote[j].getPitch();
      for (int n = 0; n < arrayOfInt.length; n++)
      {
        for (int m = paramArrayOfNote[(++j)].getPitch(); m == Integer.MIN_VALUE; m = paramArrayOfNote[(++j)].getPitch()) {
          if (paramArrayOfNote[(j - 1)].getPitch() != Integer.MIN_VALUE) {
            arrayOfInt[n] += 255;
          }
        }
        arrayOfInt[n] += m - k;
        k = m;
      }
      return arrayOfInt;
    }
    return new int[0];
  }
  
  public static double[] rhythmIntervals(Phrase paramPhrase)
  {
    return rhythmIntervals(paramPhrase.getNoteArray());
  }
  
  public static double[] rhythmIntervals(Note[] paramArrayOfNote)
  {
    int i = paramArrayOfNote.length - 1;
    for (int j = paramArrayOfNote.length - 1; (paramArrayOfNote[j].getPitch() == Integer.MIN_VALUE) && (j > -1); j--) {
      i--;
    }
    if (i > 0)
    {
      double[] arrayOfDouble = new double[i];
      for (int k = 0; k < arrayOfDouble.length; k++)
      {
        arrayOfDouble[k] = (paramArrayOfNote[(k + 1)].getRhythmValue() / paramArrayOfNote[k].getRhythmValue());
        if (paramArrayOfNote[k].getPitch() == Integer.MIN_VALUE) {
          arrayOfDouble[k] *= -1.0D;
        }
      }
      return arrayOfDouble;
    }
    return new double[0];
  }
  
  public static int intervalSemitoneCount(Phrase paramPhrase)
  {
    return intervalSemitoneCount(paramPhrase.getNoteArray());
  }
  
  public static int intervalSemitoneCount(Note[] paramArrayOfNote)
  {
    int i = 0;
    int[] arrayOfInt = pitchIntervals(paramArrayOfNote);
    for (int j = 0; j < arrayOfInt.length; j++) {
      i += Math.abs(removeRestMarker(arrayOfInt[j]));
    }
    return i;
  }
  
  public static int risingSemitoneCount(Phrase paramPhrase)
  {
    return risingSemitoneCount(paramPhrase.getNoteArray());
  }
  
  public static int risingSemitoneCount(Note[] paramArrayOfNote)
  {
    int i = 0;
    int[] arrayOfInt = pitchIntervals(paramArrayOfNote);
    for (int j = 0; j < arrayOfInt.length; j++)
    {
      arrayOfInt[j] = removeRestMarker(arrayOfInt[j]);
      if (arrayOfInt[j] > 0) {
        i += arrayOfInt[j];
      }
    }
    return i;
  }
  
  public static int stepIntervalCount(Phrase paramPhrase, int paramInt, int[] paramArrayOfInt)
  {
    return stepIntervalCount(paramPhrase.getNoteArray(), paramInt, paramArrayOfInt);
  }
  
  public static int stepIntervalCount(Note[] paramArrayOfNote, int paramInt, int[] paramArrayOfInt)
  {
    int i = intervalCount(paramArrayOfNote);
    if (i > 0)
    {
      int j = -1;
      int k = 0;
      while (paramArrayOfNote[(++j)].getPitch() == Integer.MIN_VALUE) {}
      int m = paramArrayOfNote[j].getPitch();
      for (int i1 = 0; i1 < i; i1++)
      {
        while (paramArrayOfNote[(++j)].getPitch() == Integer.MIN_VALUE) {}
        int n = paramArrayOfNote[j].getPitch();
        if ((Math.abs(n - m) < 3) && (isElementOf(pitchToDegree(n, paramInt), paramArrayOfInt)) && (isElementOf(pitchToDegree(m, paramInt), paramArrayOfInt))) {
          k++;
        }
        m = n;
      }
      return k;
    }
    return 0;
  }
  
  public static int bigJumpFollowedByStepBackCount(Phrase paramPhrase)
  {
    return bigJumpFollowedByStepBackCount(paramPhrase.getNoteArray());
  }
  
  public static int bigJumpFollowedByStepBackCount(Note[] paramArrayOfNote)
  {
    int i = 0;
    int[] arrayOfInt = pitchIntervals(paramArrayOfNote);
    if (arrayOfInt.length > 0)
    {
      arrayOfInt[0] = removeRestMarker(arrayOfInt[0]);
      for (int j = 1; j < arrayOfInt.length - 1; j++)
      {
        arrayOfInt[j] = removeRestMarker(arrayOfInt[j]);
        if (((arrayOfInt[(j - 1)] >= 8) && (arrayOfInt[j] < 0) && (arrayOfInt[j] >= -8)) || ((arrayOfInt[(j - 1)] <= -8) && (arrayOfInt[j] > 0) && (arrayOfInt[j] <= 8))) {
          i++;
        }
      }
      return i;
    }
    return 0;
  }
  
  public static int bigJumpCount(Phrase paramPhrase)
  {
    return bigJumpCount(paramPhrase.getNoteArray());
  }
  
  public static int bigJumpCount(Note[] paramArrayOfNote)
  {
    int i = 0;
    int[] arrayOfInt = pitchIntervals(paramArrayOfNote);
    if (arrayOfInt.length > 0)
    {
      for (int j = 0; j < arrayOfInt.length - 1; j++)
      {
        arrayOfInt[j] = removeRestMarker(arrayOfInt[j]);
        if (Math.abs(arrayOfInt[j]) >= 8) {
          i++;
        }
      }
      return i;
    }
    return i;
  }
  
  public static int pitchPatternCount(Phrase paramPhrase, int paramInt)
  {
    return pitchPatternCount(paramPhrase.getNoteArray(), paramInt);
  }
  
  public static int pitchPatternCount(Note[] paramArrayOfNote, int paramInt)
  {
    int i = 0;
    int[] arrayOfInt1 = pitchIntervals(paramArrayOfNote);
    if (arrayOfInt1.length > paramInt)
    {
      int[][] arrayOfInt = new int[arrayOfInt1.length - paramInt][paramInt];
      int j = 0;
      for (int k = 0; k < arrayOfInt1.length - paramInt; k++)
      {
        int[] arrayOfInt2 = new int[paramInt];
        for (int m = 0; m < paramInt; m++) {
          arrayOfInt2[m] = arrayOfInt1[(k + m)];
        }
        if (!isAlreadyMatched(arrayOfInt, arrayOfInt2, j)) {
          for (m = k + 1; m < arrayOfInt1.length - paramInt + 1; m++) {
            if (matchPattern(arrayOfInt1, k, m, paramInt))
            {
              if ((j == 0) || (arrayOfInt[(j - 1)] != arrayOfInt2)) {
                arrayOfInt[(j++)] = arrayOfInt2;
              }
              i++;
            }
          }
        }
      }
    }
    return i;
  }
  
  public static int rhythmPatternCount(Phrase paramPhrase, int paramInt)
  {
    return rhythmPatternCount(paramPhrase.getNoteArray(), paramInt);
  }
  
  public static int rhythmPatternCount(Note[] paramArrayOfNote, int paramInt)
  {
    int i = 0;
    double[] arrayOfDouble1 = rhythmIntervals(paramArrayOfNote);
    if (arrayOfDouble1.length > paramInt)
    {
      double[][] arrayOfDouble = new double[arrayOfDouble1.length - paramInt][paramInt];
      int j = 0;
      for (int k = 0; k < arrayOfDouble1.length - paramInt; k++)
      {
        double[] arrayOfDouble2 = new double[paramInt];
        for (int m = 0; m < paramInt; m++) {
          arrayOfDouble2[m] = arrayOfDouble1[(k + m)];
        }
        if (!isAlreadyMatched(arrayOfDouble, arrayOfDouble2, j)) {
          for (m = k + 1; m < arrayOfDouble1.length - paramInt + 1; m++) {
            if (matchPattern(arrayOfDouble1, k, m, paramInt))
            {
              if ((j == 0) || (arrayOfDouble[(j - 1)] != arrayOfDouble2)) {
                arrayOfDouble[(j++)] = arrayOfDouble2;
              }
              i++;
            }
          }
        }
      }
    }
    return i;
  }
  
  public static double rhythmValueCount(Phrase paramPhrase)
  {
    return rhythmValueCount(paramPhrase.getNoteArray());
  }
  
  public static double rhythmValueCount(Note[] paramArrayOfNote)
  {
    double d = 0.0D;
    for (int i = 0; i < paramArrayOfNote.length; i++) {
      d += paramArrayOfNote[i].getRhythmValue();
    }
    return d;
  }
  
  public static int removeRestMarker(int paramInt)
  {
    return paramInt > 127 ? paramInt - 255 : paramInt;
  }
  
  public static boolean isQuantised(Phrase paramPhrase, double paramDouble)
    throws QuantisationException
  {
    return isQuantised(paramPhrase.getNoteArray(), paramDouble);
  }
  
  public static boolean isQuantised(Note[] paramArrayOfNote, double paramDouble)
    throws QuantisationException
  {
    if (paramDouble > 0.0D)
    {
      for (int i = 0; i < paramArrayOfNote.length; i++) {
        if (paramArrayOfNote[i].getRhythmValue() % paramDouble != 0.0D) {
          return false;
        }
      }
      return true;
    }
    throw new QuantisationException("The quantum duration must be greater than zero.");
  }
  
  private static boolean matchPattern(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool = true;
    for (int i = 0; i < paramInt3; i++) {
      if (paramArrayOfInt[(paramInt1 + i)] != paramArrayOfInt[(paramInt2 + i)]) {
        bool = false;
      }
    }
    return bool;
  }
  
  private static boolean matchPattern(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool = true;
    for (int i = 0; i < paramInt3; i++) {
      if (paramArrayOfDouble[(paramInt1 + i)] != paramArrayOfDouble[(paramInt2 + i)]) {
        bool = false;
      }
    }
    return bool;
  }
  
  private static boolean isAlreadyMatched(int[][] paramArrayOfInt, int[] paramArrayOfInt1, int paramInt)
  {
    label41:
    for (int i = 0; i < paramInt; i++)
    {
      for (int j = 0; j < paramArrayOfInt1.length; j++) {
        if (paramArrayOfInt[i][j] != paramArrayOfInt1[j]) {
          break label41;
        }
      }
      return true;
    }
    return false;
  }
  
  private static boolean isAlreadyMatched(double[][] paramArrayOfDouble, double[] paramArrayOfDouble1, int paramInt)
  {
    label42:
    for (int i = 0; i < paramInt; i++)
    {
      for (int j = 0; j < paramArrayOfDouble1.length; j++) {
        if (paramArrayOfDouble[i][j] != paramArrayOfDouble1[j]) {
          break label42;
        }
      }
      return true;
    }
    return false;
  }
  
  private static int rateDissonance(int paramInt)
  {
    for (int i = 0; i < GOOD_INTERVALS.length; i++) {
      if (paramInt == GOOD_INTERVALS[i]) {
        return 0;
      }
    }
    for (i = 0; i < BAD_INTERVALS.length; i++) {
      if (paramInt == BAD_INTERVALS[i]) {
        return 2;
      }
    }
    return 1;
  }
  
  private static boolean isElementOf(int paramInt, int[] paramArrayOfInt)
  {
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      if (paramArrayOfInt[i] == paramInt) {
        return true;
      }
    }
    return false;
  }
  
  private static boolean isElementOf(int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    for (int i = 0; i < paramInt2; i++) {
      if (paramArrayOfInt[i] == paramInt1) {
        return true;
      }
    }
    return false;
  }
  
  private static boolean isElementOf(double paramDouble, double[] paramArrayOfDouble, int paramInt)
  {
    for (int i = 0; i < paramInt; i++) {
      if (paramArrayOfDouble[i] == paramDouble) {
        return true;
      }
    }
    return false;
  }
  
  public static int pitchToDegree(int paramInt1, int paramInt2)
  {
    paramInt1 -= paramInt2;
    if (paramInt1 < 0) {
      paramInt1 += (-paramInt1 / 12 + 1) * 12;
    }
    return paramInt1 % 12;
  }
  
  public static boolean isScale(Note paramNote, int paramInt, int[] paramArrayOfInt)
  {
    int i = paramNote.getPitch();
    if (i == Integer.MIN_VALUE) {
      return true;
    }
    return isElementOf(pitchToDegree(i, paramInt), paramArrayOfInt, paramArrayOfInt.length);
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\PhraseAnalysis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */