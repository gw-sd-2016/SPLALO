package jm.music.data;

import java.io.PrintStream;
import java.io.Serializable;

public class Note
  implements Cloneable, Serializable
{
  public static final int DEFAULT_PITCH = 60;
  public static final double DEFAULT_RHYTHM_VALUE = 1.0D;
  public static final int DEFAULT_DYNAMIC = 85;
  public static final double DEFAULT_PAN = 0.5D;
  public static final double DEFAULT_DURATION_MULTIPLIER = 0.9D;
  public static final double DEFAULT_ARTICULATION = 0.9D;
  public static final double DEFAULT_DURATION = 0.9D;
  public static final double DEFAULT_OFFSET = 0.0D;
  public static final double DEFAULT_SAMPLE_START_TIME = 0.0D;
  public static final int MIN_PITCH = 0;
  public static final double MIN_FREQUENCY = 1.0E-17D;
  public static final double MAX_MIDI_PITCH = 127.0D;
  public static final int MAX_PITCH = 127;
  public static final double MIN_RHYTHM_VALUE = 0.0D;
  public static final double MAX_RHYTHM_VALUE = Double.MAX_VALUE;
  public static final int MIN_DYNAMIC = 0;
  public static final int MAX_DYNAMIC = 127;
  public static final double MIN_PAN = 0.0D;
  public static final double MAX_PAN = Double.MAX_VALUE;
  public static final double MIN_DURATION = 0.0D;
  public static final double MAX_DURATION = Double.MAX_VALUE;
  public static final int REST = Integer.MIN_VALUE;
  public static final boolean FREQUENCY = true;
  public static final boolean MIDI_PITCH = false;
  public static final int AMP_ENV = 0;
  public static final int PITCH_ENV = 1;
  public static final int FILTER_ENV = 2;
  public static final int PAN_ENV = 3;
  public static final String C = "C";
  public static final String G = "G";
  public static final String D = "D";
  public static final String A = "A";
  public static final String E = "E";
  public static final String B = "B";
  public static final String F_SHARP = "F#";
  public static final String C_SHARP = "C#";
  public static final String G_SHARP = "Ab";
  public static final String D_SHARP = "Eb";
  public static final String A_SHARP = "Bb";
  public static final String A_FLAT = "Ab";
  public static final String E_FLAT = "Eb";
  public static final String B_FLAT = "Bb";
  public static final String F = "F";
  private String noteString = "";
  private double pitch;
  private int dynamic;
  private double rhythmValue;
  private double pan;
  private double duration;
  private double offset;
  private double sampleStartTime;
  private boolean pitchType;
  private Phrase myPhrase = null;
  private double[][] breakPoints = new double[64][];
  
  public Note()
  {
    this(60, 1.0D);
    this.pitch = 60.0D;
    this.pitchType = false;
    this.rhythmValue = 1.0D;
    this.dynamic = 85;
    this.pan = 0.5D;
    this.duration = 0.9D;
    this.offset = 0.0D;
  }
  
  public Note(int paramInt, double paramDouble)
  {
    this(paramInt, paramDouble, 85);
  }
  
  public Note(int paramInt1, double paramDouble, int paramInt2)
  {
    this(paramInt1, paramDouble, paramInt2, 0.5D);
  }
  
  public Note(int paramInt1, double paramDouble1, int paramInt2, double paramDouble2)
  {
    if ((paramInt1 < 0) && (paramInt1 > -2147483646))
    {
      System.err.println("jMusic Note constructor error: Pitch is " + paramInt1 + ", it must be no less than " + 0 + " (REST = " + Integer.MIN_VALUE + ")");
      System.exit(1);
    }
    this.pitchType = false;
    this.pitch = paramInt1;
    this.rhythmValue = paramDouble1;
    this.dynamic = (paramInt2 > 127 ? 127 : paramInt2 < 0 ? 0 : paramInt2);
    this.pan = paramDouble2;
    this.duration = (paramDouble1 * 0.9D);
    this.offset = 0.0D;
  }
  
  public Note(double paramDouble1, double paramDouble2)
  {
    this(paramDouble1, paramDouble2, 85);
  }
  
  public Note(double paramDouble1, double paramDouble2, int paramInt)
  {
    this(paramDouble1, paramDouble2, paramInt, 0.5D);
  }
  
  public Note(double paramDouble1, double paramDouble2, int paramInt, double paramDouble3)
  {
    if (paramDouble1 > 1.0E-17D)
    {
      this.pitch = paramDouble1;
    }
    else
    {
      System.err.println("jMusic Note constructor error: Frequency is " + paramDouble1 + ", it must be greater than " + 1.0E-17D + " hertz.");
      System.exit(1);
    }
    this.pitchType = true;
    this.rhythmValue = paramDouble2;
    this.dynamic = (paramInt > 127 ? 127 : paramInt < 0 ? 0 : paramInt);
    this.pan = paramDouble3;
    this.duration = (paramDouble2 * 0.9D);
    this.offset = 0.0D;
  }
  
  public Note(String paramString)
  {
    this.noteString = paramString;
    setPitch(getPitchValue());
  }
  
  public boolean getPitchType()
  {
    return this.pitchType;
  }
  
  public void setPitchType(boolean paramBoolean)
  {
    this.pitchType = paramBoolean;
  }
  
  public double getFrequency()
  {
    double d = this.pitch;
    if ((!this.pitchType) && (this.pitch != -2.147483648E9D) && (this.pitch <= 127.0D) && (this.pitch >= 0.0D)) {
      d = jm.JMC.FRQ[((int)this.pitch)];
    }
    if (this.pitch == -2.147483648E9D) {
      d = -2.147483648E9D;
    }
    return d;
  }
  
  public void setFrequency(double paramDouble)
  {
    try
    {
      this.pitch = (this.pitch < 1.0E-17D ? 1.0E-17D : paramDouble);
      this.pitchType = true;
    }
    catch (RuntimeException localRuntimeException)
    {
      System.err.println("Error setting note value: You must enter frequency values above 1.0E-17");
      System.exit(1);
    }
  }
  
  public int getPitch()
  {
    if ((this.pitchType == true) && (this.pitch != -2.147483648E9D))
    {
      System.err.println("jMusic error getting Note pitch: Pitch is a frequency - getPitch() can't be used.");
      System.exit(1);
    }
    int i;
    if (this.pitch < -2.147483646E9D) {
      i = Integer.MIN_VALUE;
    } else {
      i = (int)this.pitch;
    }
    return i;
  }
  
  public void setPitch(int paramInt)
  {
    if (paramInt == Integer.MIN_VALUE) {
      this.pitch = -2.147483648E9D;
    } else {
      try
      {
        this.pitch = (paramInt > 127.0D ? 127.0D : paramInt < 0 ? 0.0D : paramInt);
      }
      catch (RuntimeException localRuntimeException)
      {
        System.err.println("Error setting pitch value: You must enter pitch values between 0 and 127.0");
      }
    }
    this.pitchType = false;
  }
  
  public double getRhythmValue()
  {
    return this.rhythmValue;
  }
  
  public void setRhythmValue(double paramDouble)
  {
    this.rhythmValue = (paramDouble > Double.MAX_VALUE ? Double.MAX_VALUE : paramDouble < 0.0D ? 0.0D : paramDouble);
  }
  
  public int getDynamic()
  {
    return this.dynamic;
  }
  
  public void setDynamic(int paramInt)
  {
    this.dynamic = (paramInt > 127 ? 127 : paramInt < 0 ? 0 : paramInt);
  }
  
  public double getPan()
  {
    return this.pan;
  }
  
  public void setPan(double paramDouble)
  {
    this.pan = (paramDouble > Double.MAX_VALUE ? Double.MAX_VALUE : paramDouble < 0.0D ? 0.0D : paramDouble);
  }
  
  public double getDuration()
  {
    return this.duration;
  }
  
  public void setDuration(double paramDouble)
  {
    this.duration = (paramDouble > Double.MAX_VALUE ? Double.MAX_VALUE : paramDouble < 0.0D ? 0.0D : paramDouble);
  }
  
  public double getOffset()
  {
    return this.offset;
  }
  
  public void setOffset(double paramDouble)
  {
    this.offset = paramDouble;
  }
  
  public double getSampleStartTime()
  {
    return this.sampleStartTime;
  }
  
  public void setSampleStartTime(double paramDouble)
  {
    this.sampleStartTime = paramDouble;
  }
  
  public void setMyPhrase(Phrase paramPhrase)
  {
    this.myPhrase = paramPhrase;
  }
  
  public Phrase getMyPhrase()
  {
    return this.myPhrase;
  }
  
  public Note copy()
  {
    Note localNote;
    if (!this.pitchType) {
      localNote = new Note(getPitch(), this.rhythmValue, this.dynamic);
    } else {
      localNote = new Note(getFrequency(), this.rhythmValue, this.dynamic);
    }
    localNote.setPan(this.pan);
    localNote.setDuration(this.duration);
    localNote.setOffset(this.offset);
    localNote.setSampleStartTime(this.sampleStartTime);
    localNote.setMyPhrase(this.myPhrase);
    for (int i = 0; i < this.breakPoints.length; i++) {
      if (this.breakPoints[i] != null) {
        localNote.setBreakPoints(i, getBreakPoints(i));
      }
    }
    return localNote;
  }
  
  public void setBreakPoints(int paramInt, double[] paramArrayOfDouble)
  {
    if ((paramInt < 0) || (paramInt > this.breakPoints.length))
    {
      System.err.println("jMusic Note error: BreakPoint index " + paramInt + " is out of range when setting.");
      System.exit(1);
    }
    this.breakPoints[paramInt] = paramArrayOfDouble;
  }
  
  public double[] getBreakPoints(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > this.breakPoints.length))
    {
      System.err.println("jMusic Note error: BreakPoint index " + paramInt + "is out of range when getting.");
      System.exit(1);
    }
    if (this.breakPoints[paramInt] == null)
    {
      System.err.println("jMusic Note error: Breakpoint index " + paramInt + " is empty.");
      System.exit(1);
    }
    return this.breakPoints[paramInt];
  }
  
  public String toString()
  {
    String str;
    if (!this.pitchType) {
      str = new String("jMusic NOTE: [Pitch = " + (int)this.pitch + "][RhythmValue = " + this.rhythmValue + "][Dynamic = " + this.dynamic + "][Duration = " + this.duration + "][Pan = " + this.pan + "]");
    } else {
      str = new String("Note: [Frequency = " + this.pitch + "][RhythmValue = " + this.rhythmValue + "][Dynamic = " + this.dynamic + "][Duration = " + this.duration + "][Pan = " + this.pan + "]");
    }
    return str;
  }
  
  public boolean isScale(int[] paramArrayOfInt)
  {
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      if (this.pitch % 12.0D == paramArrayOfInt[i]) {
        return true;
      }
    }
    return false;
  }
  
  public void setRhythmValue(double paramDouble, boolean paramBoolean)
  {
    setRhythmValue(paramDouble);
    if (paramBoolean) {
      setDuration(paramDouble * 0.9D);
    }
  }
  
  public static int freqToMidiPitch(double paramDouble)
  {
    if ((paramDouble < 26.73D) || (paramDouble > 14496.0D))
    {
      System.err.println("freqToMidiPitch error: Frequency " + paramDouble + " is not within the MIDI note range.");
      return -1;
    }
    double d1 = Math.pow(2.0D, 0.08333333333333333D);
    double d2 = Math.pow(2.0D, 8.333333333333334E-4D);
    int i = 0;
    int j = 0;
    double d3 = 440.0D;
    if (paramDouble >= d3)
    {
      while (paramDouble > d1 * d3)
      {
        d3 = d1 * d3;
        i++;
      }
      while (paramDouble > d2 * d3)
      {
        d3 = d2 * d3;
        j++;
      }
      if (d2 * d3 - paramDouble < paramDouble - d3) {
        j++;
      }
      if (j > 50)
      {
        i++;
        j = 100 - j;
      }
    }
    else
    {
      while (paramDouble < d3 / d1)
      {
        d3 /= d1;
        i--;
      }
      while (paramDouble < d3 / d2)
      {
        d3 /= d2;
        j++;
      }
      if (paramDouble - d3 / d2 < d3 - paramDouble) {
        j++;
      }
      if (j >= 50)
      {
        i--;
        j = 100 - j;
      }
    }
    return 69 + i;
  }
  
  public static double midiPitchToFreq(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > 127))
    {
      System.err.println("jMusic Note.midiPitchToFreq error:midiPitch of " + paramInt + " is out side valid range.");
      return -1.0D;
    }
    double d1 = Math.pow(2.0D, 0.08333333333333333D);
    int i = paramInt - 69;
    double d2 = 440.0D;
    int j;
    if (paramInt > 69) {
      for (j = 69; j < paramInt; j++) {
        d2 *= d1;
      }
    } else {
      for (j = 69; j > paramInt; j--) {
        d2 /= d1;
      }
    }
    d2 = Math.round(d2 * 1000.0D) / 1000.0D;
    return d2;
  }
  
  public boolean isRest()
  {
    return getPitch() == Integer.MIN_VALUE;
  }
  
  public void setLength(double paramDouble)
  {
    setRhythmValue(paramDouble);
    setDuration(paramDouble * 0.9D);
  }
  
  public boolean isSharp()
  {
    return (getNote().equals("C#")) || (getNote().equals("F#"));
  }
  
  public boolean isFlat()
  {
    return (getNote().equals("Eb")) || (getNote().equals("Ab")) || (getNote().equals("Bb"));
  }
  
  public boolean isNatural()
  {
    return (!isSharp()) && (!isFlat());
  }
  
  public boolean samePitch(Note paramNote)
  {
    return getPitch() == paramNote.getPitch();
  }
  
  public boolean sameDuration(Note paramNote)
  {
    return getDuration() == paramNote.getDuration();
  }
  
  public boolean equals(Note paramNote)
  {
    return (samePitch(paramNote)) && (sameDuration(paramNote));
  }
  
  public Note nextNote(int[] paramArrayOfInt)
  {
    Note localNote = null;
    int i;
    for (i = 0; i < paramArrayOfInt.length; i++) {
      if (getPitchValue() % 12 == 0) {
        localNote = new Note(getPitch() + paramArrayOfInt[i], 1.0D);
      }
    }
    i = getPitch() + paramArrayOfInt[1];
    System.out.println("NEXT PITCH " + i + " " + getPitch() + " " + paramArrayOfInt[1]);
    return new Note(i, 1.0D);
  }
  
  public int getPitchValue()
  {
    int i = 0;
    if (this.noteString.equals("C")) {
      i = 60;
    } else if (this.noteString.equals("C#")) {
      i = 61;
    } else if (this.noteString.equals("D")) {
      i = 62;
    } else if (this.noteString.equals("Eb")) {
      i = 63;
    } else if (this.noteString.equals("E")) {
      i = 64;
    } else if (this.noteString.equals("F")) {
      i = 65;
    } else if (this.noteString.equals("F#")) {
      i = 66;
    } else if (this.noteString.equals("G")) {
      i = 67;
    } else if (this.noteString.equals("Ab")) {
      i = 68;
    } else if (this.noteString.equals("A")) {
      i = 69;
    } else if (this.noteString.equals("Bb")) {
      i = 70;
    } else if (this.noteString.equals("B")) {
      i = 71;
    }
    return i;
  }
  
  public String getNote()
  {
    if (getPitch() % 12 == 0) {
      this.noteString = "C";
    } else if (getPitch() % 12 == 1) {
      this.noteString = "C#";
    } else if (getPitch() % 12 == 2) {
      this.noteString = "D";
    } else if (getPitch() % 12 == 3) {
      this.noteString = "Eb";
    } else if (getPitch() % 12 == 4) {
      this.noteString = "E";
    } else if (getPitch() % 12 == 5) {
      this.noteString = "F";
    } else if (getPitch() % 12 == 6) {
      this.noteString = "F#";
    } else if (getPitch() % 12 == 7) {
      this.noteString = "G";
    } else if (getPitch() % 12 == 8) {
      this.noteString = "Ab";
    } else if (getPitch() % 12 == 9) {
      this.noteString = "A";
    } else if (getPitch() % 12 == 10) {
      this.noteString = "Bb";
    } else if (getPitch() % 12 == 11) {
      this.noteString = "B";
    } else {
      this.noteString = "N/A";
    }
    return this.noteString;
  }
  
  public static String getNote(int paramInt)
  {
    String str = "";
    if (paramInt % 12 == 0) {
      str = "C";
    } else if (paramInt % 12 == 1) {
      str = "C#";
    } else if (paramInt % 12 == 2) {
      str = "D";
    } else if (paramInt % 12 == 3) {
      str = "Eb";
    } else if (paramInt % 12 == 4) {
      str = "E";
    } else if (paramInt % 12 == 5) {
      str = "F";
    } else if (paramInt % 12 == 6) {
      str = "F#";
    } else if (paramInt % 12 == 7) {
      str = "G";
    } else if (paramInt % 12 == 8) {
      str = "Ab";
    } else if (paramInt % 12 == 9) {
      str = "A";
    } else if (paramInt % 12 == 10) {
      str = "Bb";
    } else if (paramInt % 12 == 11) {
      str = "B";
    } else {
      str = "N/A";
    }
    return str;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\data\Note.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */