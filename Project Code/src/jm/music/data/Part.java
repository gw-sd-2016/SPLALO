package jm.music.data;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;
import jm.JMC;

public class Part
  implements Cloneable, Serializable, JMC
{
  public static final String DEFAULT_TITLE = "Untitled Part";
  public static final int DEFAULT_INSTRUMENT = 0;
  public static final int DEFAULT_CHANNEL = 0;
  public static final double DEFAULT_TEMPO = -1.0D;
  public static final int DEFAULT_KEY_SIGNATURE = Integer.MIN_VALUE;
  public static final int DEFAULT_KEY_QUALITY = Integer.MIN_VALUE;
  public static final int DEFAULT_NUMERATOR = Integer.MIN_VALUE;
  public static final int DEFAULT_DENOMINATOR = Integer.MIN_VALUE;
  public static final double DEFAULT_PAN = 0.5D;
  private Vector cphraseList;
  private String title = "Unamed Part";
  private int channel;
  private int instrument;
  private double tempo;
  private double[] points = null;
  private long[] time = null;
  private int timeIndex = 0;
  private Score myScore = null;
  private int keySignature;
  private int keyQuality;
  private int numerator;
  private int denominator;
  private double pan = 0.5D;
  
  public Part()
  {
    this("Untitled Part");
  }
  
  public Part(String paramString)
  {
    this(paramString, 0);
  }
  
  public Part(int paramInt)
  {
    this("", paramInt);
  }
  
  public Part(String paramString, int paramInt)
  {
    this(paramString, paramInt, 0);
  }
  
  public Part(int paramInt1, int paramInt2)
  {
    this("", paramInt1, paramInt2);
  }
  
  public Part(String paramString, int paramInt1, int paramInt2)
  {
    this.title = paramString;
    this.cphraseList = new Vector();
    if (this.channel > 16)
    {
      System.err.println(new Exception("jMusic Warning: A MIDI Channel cannot be greater than 16. There can be any number of Audio channels."));
      new Exception().printStackTrace();
    }
    this.channel = paramInt2;
    if (paramInt1 < -1)
    {
      System.err.println(new Exception("jMusic EXCEPTION: instrument value must be greater than 0"));
      new Exception().printStackTrace();
      System.exit(1);
    }
    this.instrument = paramInt1;
    this.tempo = -1.0D;
    this.keySignature = Integer.MIN_VALUE;
    this.keyQuality = Integer.MIN_VALUE;
    this.numerator = Integer.MIN_VALUE;
    this.denominator = Integer.MIN_VALUE;
  }
  
  public Part(Phrase paramPhrase)
  {
    this();
    paramPhrase.setMyPart(this);
    addPhrase(paramPhrase);
    setTempo(paramPhrase.getTempo());
  }
  
  public Part(String paramString, int paramInt, Phrase paramPhrase)
  {
    this(paramString, paramInt, 0, paramPhrase);
    setTempo(paramPhrase.getTempo());
  }
  
  public Part(String paramString, int paramInt1, int paramInt2, Phrase paramPhrase)
  {
    this(paramString, paramInt1, paramInt2);
    paramPhrase.setMyPart(this);
    addPhrase(paramPhrase);
    setTempo(paramPhrase.getTempo());
  }
  
  public Part(Phrase[] paramArrayOfPhrase)
  {
    this();
    addPhraseList(paramArrayOfPhrase);
  }
  
  public Part(CPhrase paramCPhrase)
  {
    this();
    addCPhrase(paramCPhrase);
  }
  
  public Part(Phrase paramPhrase, String paramString)
  {
    this(paramString);
    addPhrase(paramPhrase);
    setTempo(paramPhrase.getTempo());
  }
  
  public Part(Phrase[] paramArrayOfPhrase, String paramString)
  {
    this(paramString);
    addPhraseList(paramArrayOfPhrase);
  }
  
  public Part(Phrase paramPhrase, String paramString, int paramInt)
  {
    this(paramString, paramInt);
    addPhrase(paramPhrase);
    setTempo(paramPhrase.getTempo());
  }
  
  public Part(Phrase[] paramArrayOfPhrase, String paramString, int paramInt)
  {
    this(paramString, paramInt);
    addPhraseList(paramArrayOfPhrase);
  }
  
  public Part(Phrase paramPhrase, String paramString, int paramInt1, int paramInt2)
  {
    this(paramString, paramInt1, paramInt2);
    addPhrase(paramPhrase);
    setTempo(paramPhrase.getTempo());
  }
  
  public Part(Phrase[] paramArrayOfPhrase, String paramString, int paramInt1, int paramInt2)
  {
    this(paramString, paramInt1, paramInt2);
    addPhraseList(paramArrayOfPhrase);
  }
  
  public Phrase getPhrase(int paramInt)
  {
    Enumeration localEnumeration = this.cphraseList.elements();
    for (int i = 0; localEnumeration.hasMoreElements(); i++)
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      if (i == paramInt) {
        return localPhrase;
      }
    }
    return null;
  }
  
  public CPhrase getCPhrase(int paramInt)
  {
    Enumeration localEnumeration = this.cphraseList.elements();
    for (int i = 0; localEnumeration.hasMoreElements(); i++)
    {
      CPhrase localPhrase = (CPhrase)localEnumeration.nextElement();
      if (i == paramInt) {
        return localPhrase;
      }
    }
    return null;
  }
  
  public void add(Phrase paramPhrase)
  {
    addPhrase(paramPhrase);
  }
  
  public void addPhrase(Phrase paramPhrase)
  {
    paramPhrase.setMyPart(this);
    if (paramPhrase.getAppend()) {
      paramPhrase.setStartTime(getEndTime());
    }
    this.cphraseList.addElement(paramPhrase);
  }
  
  public void appendPhrase(Phrase paramPhrase)
  {
    Phrase localPhrase = paramPhrase.copy();
    localPhrase.setStartTime(getEndTime());
    addPhrase(localPhrase);
  }
  
  public void addPhraseList(Phrase[] paramArrayOfPhrase)
  {
    for (int i = 0; i < paramArrayOfPhrase.length; i++) {
      if (paramArrayOfPhrase[i].getAppend())
      {
        Phrase localPhrase = paramArrayOfPhrase[i].copy();
        localPhrase.setStartTime(getEndTime());
        addPhrase(localPhrase);
      }
      else
      {
        addPhrase(paramArrayOfPhrase[i]);
      }
    }
  }
  
  public void removePhrase(int paramInt)
  {
    Vector localVector = this.cphraseList;
    try
    {
      localVector.removeElement(localVector.elementAt(paramInt));
    }
    catch (RuntimeException localRuntimeException)
    {
      System.err.println("The Phrase index to be deleted must be within the part.");
    }
  }
  
  public void removePhrase(Phrase paramPhrase)
  {
    this.cphraseList.removeElement(paramPhrase);
  }
  
  public void removeLastPhrase()
  {
    Vector localVector = this.cphraseList;
    localVector.removeElement(localVector.lastElement());
  }
  
  public void removeAllPhrases()
  {
    this.cphraseList.removeAllElements();
  }
  
  public Vector getPhraseList()
  {
    return this.cphraseList;
  }
  
  public void setPhraseList(Vector paramVector)
  {
    this.cphraseList = paramVector;
  }
  
  public Phrase[] getPhraseArray()
  {
    Vector localVector = this.cphraseList;
    Phrase[] arrayOfPhrase = new Phrase[localVector.size()];
    for (int i = 0; i < arrayOfPhrase.length; i++) {
      arrayOfPhrase[i] = ((Phrase)localVector.elementAt(i));
    }
    return arrayOfPhrase;
  }
  
  public void addCPhrase(CPhrase paramCPhrase)
  {
    if (paramCPhrase.getAppend()) {
      paramCPhrase.setStartTime(getEndTime());
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      addPhrase(localPhrase);
    }
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public int getChannel()
  {
    return this.channel;
  }
  
  public void setChannel(int paramInt)
  {
    this.channel = paramInt;
  }
  
  public int getInstrument()
  {
    return this.instrument;
  }
  
  public void setInstrument(int paramInt)
  {
    this.instrument = paramInt;
  }
  
  public void setProgChg(int paramInt)
  {
    this.instrument = paramInt;
  }
  
  public double getTempo()
  {
    return this.tempo;
  }
  
  public void setTempo(double paramDouble)
  {
    this.tempo = paramDouble;
  }
  
  public int getKeySignature()
  {
    return this.keySignature;
  }
  
  public void setKeySignature(int paramInt)
  {
    this.keySignature = paramInt;
  }
  
  public int getKeyQuality()
  {
    return this.keyQuality;
  }
  
  public void setKeyQuality(int paramInt)
  {
    this.keyQuality = paramInt;
  }
  
  public int getNumerator()
  {
    return this.numerator;
  }
  
  public void setNumerator(int paramInt)
  {
    this.numerator = paramInt;
  }
  
  public int getDenominator()
  {
    return this.denominator;
  }
  
  public void setDenominator(int paramInt)
  {
    this.denominator = paramInt;
  }
  
  public double getPan()
  {
    return this.pan;
  }
  
  public void setPan(double paramDouble)
  {
    this.pan = paramDouble;
    Enumeration localEnumeration = this.cphraseList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      localPhrase.setPan(paramDouble);
    }
  }
  
  public void setMyScore(Score paramScore)
  {
    this.myScore = paramScore;
  }
  
  public Score getMyScore()
  {
    return this.myScore;
  }
  
  public Part copy()
  {
    Part localPart = new Part();
    Enumeration localEnumeration = this.cphraseList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      localPart.addPhrase(localPhrase.copy());
    }
    copyAttributes(localPart);
    return localPart;
  }
  
  private void copyAttributes(Part paramPart)
  {
    paramPart.setInstrument(getInstrument());
    paramPart.setChannel(getChannel());
    paramPart.setTitle(getTitle() + " copy");
    paramPart.setTempo(this.tempo);
    paramPart.setPoints(this.points);
    paramPart.setTime(this.time);
    paramPart.setTimeIndex(this.timeIndex);
    paramPart.setMyScore(getMyScore());
  }
  
  public Part copy(double paramDouble1, double paramDouble2)
  {
    Vector localVector = new Vector();
    Part localPart = new Part();
    copyAttributes(localPart);
    Enumeration localEnumeration = this.cphraseList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      if ((localPhrase.getStartTime() < paramDouble2) && (localPhrase.getEndTime() > paramDouble1)) {
        localVector.addElement(localPhrase.copy(paramDouble1, paramDouble2));
      }
    }
    localPart.setPhraseList(localVector);
    return localPart;
  }
  
  public Part copy(double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    Part localPart = new Part();
    copyAttributes(localPart);
    Vector localVector = new Vector();
    localPart.setMyScore(getMyScore());
    Enumeration localEnumeration = this.cphraseList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase1 = (Phrase)localEnumeration.nextElement();
      double d1 = localPhrase1.getStartTime();
      if ((d1 < paramDouble2) && (localPhrase1.getEndTime() > paramDouble1))
      {
        Phrase localPhrase2 = localPhrase1.copy(paramDouble1, paramDouble2, paramBoolean1, paramBoolean2, false);
        double d2 = 0.0D;
        if (d2 < 0.0D) {
          d2 = 0.0D;
        }
        if (paramBoolean3) {
          d2 += paramDouble1;
        }
        localPhrase2.setStartTime(d2);
        localVector.addElement(localPhrase2);
      }
    }
    localPart.setPhraseList(localVector);
    return localPart;
  }
  
  public double getEndTime()
  {
    double d1 = 0.0D;
    Enumeration localEnumeration = this.cphraseList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      double d2 = localPhrase.getEndTime();
      if (d2 > d1) {
        d1 = d2;
      }
    }
    return d1;
  }
  
  public String toString()
  {
    String str = new String("----- jMusic PART: '" + this.title + "' contains " + size() + " phrases.  -----" + '\n');
    str = str + "Channel = " + this.channel + '\n';
    str = str + "Instrument = " + this.instrument + '\n';
    if (this.tempo > 0.0D) {
      str = str + "Part Tempo = " + this.tempo + '\n';
    }
    Enumeration localEnumeration = this.cphraseList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      str = str + localPhrase.toString() + '\n';
    }
    return str;
  }
  
  public void empty()
  {
    this.cphraseList.removeAllElements();
  }
  
  public int length()
  {
    return size();
  }
  
  public int size()
  {
    return this.cphraseList.size();
  }
  
  public int getSize()
  {
    return this.cphraseList.size();
  }
  
  public void clean()
  {
    Enumeration localEnumeration = getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      if (localPhrase.getInstrument() == this.instrument) {
        localPhrase.setInstrument(-1);
      }
      if (localPhrase.getNoteList().size() == 0) {
        removePhrase(localPhrase);
      }
    }
  }
  
  public int getHighestPitch()
  {
    int i = 0;
    Enumeration localEnumeration = getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      if (localPhrase.getHighestPitch() > i) {
        i = localPhrase.getHighestPitch();
      }
    }
    return i;
  }
  
  public int getLowestPitch()
  {
    int i = 127;
    Enumeration localEnumeration = getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      if (localPhrase.getLowestPitch() < i) {
        i = localPhrase.getLowestPitch();
      }
    }
    return i;
  }
  
  public double getLongestRhythmValue()
  {
    double d = 0.0D;
    Enumeration localEnumeration = getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      if (localPhrase.getLongestRhythmValue() > d) {
        d = localPhrase.getLongestRhythmValue();
      }
    }
    return d;
  }
  
  public double getShortestRhythmValue()
  {
    double d = 1000.0D;
    Enumeration localEnumeration = getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      if (localPhrase.getShortestRhythmValue() < d) {
        d = localPhrase.getShortestRhythmValue();
      }
    }
    return d;
  }
  
  public void setDynamic(int paramInt)
  {
    Enumeration localEnumeration = getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      localPhrase.setDynamic(paramInt);
    }
  }
  
  public void setPitch(int paramInt)
  {
    Enumeration localEnumeration = getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      localPhrase.setPitch(paramInt);
    }
  }
  
  public void setRhythmValue(double paramDouble)
  {
    Enumeration localEnumeration = getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      localPhrase.setRhythmValue(paramDouble);
    }
  }
  
  public void setDuration(double paramDouble)
  {
    Enumeration localEnumeration = getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      localPhrase.setDuration(paramDouble);
    }
  }
  
  public void setPoints(double[] paramArrayOfDouble)
  {
    this.points = paramArrayOfDouble;
  }
  
  public double getPoint()
  {
    return this.points[this.timeIndex];
  }
  
  public void setTime(long[] paramArrayOfLong)
  {
    this.time = paramArrayOfLong;
  }
  
  public long getTime()
  {
    return this.time[(this.timeIndex++)];
  }
  
  public void setTimeIndex(int paramInt)
  {
    this.timeIndex = paramInt;
  }
  
  public int getTimeIndex()
  {
    return this.timeIndex;
  }
  
  public void addNote(Note paramNote, double paramDouble)
  {
    Phrase localPhrase = new Phrase("Generated by Part.addNote()", paramDouble);
    localPhrase.addNote(paramNote);
    addPhrase(localPhrase);
  }
  
  public Phrase createPhrase()
  {
    Phrase localPhrase = new Phrase();
    addPhrase(localPhrase);
    return localPhrase;
  }
  
  public void setLength(double paramDouble)
  {
    setRhythmValue(paramDouble);
    setDuration(paramDouble * 0.9D);
  }
  
  public void sort()
  {
    Phrase[] arrayOfPhrase = getPhraseArray();
    quickSort(arrayOfPhrase, 0, arrayOfPhrase.length - 1);
    this.cphraseList.removeAllElements();
    this.cphraseList.ensureCapacity(arrayOfPhrase.length);
    for (int i = 0; i < arrayOfPhrase.length; i++) {
      this.cphraseList.add(arrayOfPhrase[i]);
    }
  }
  
  private void quickSort(Phrase[] paramArrayOfPhrase, int paramInt1, int paramInt2)
  {
    if (paramInt1 >= paramInt2) {
      return;
    }
    swap(paramArrayOfPhrase, paramInt1, (int)(Math.random() * (paramInt2 - paramInt1)) + paramInt1);
    int j = paramInt1;
    for (int i = paramInt1 + 1; i <= paramInt2; i++) {
      if (paramArrayOfPhrase[i].getStartTime() <= paramArrayOfPhrase[paramInt1].getStartTime()) {
        swap(paramArrayOfPhrase, ++j, i);
      }
    }
    swap(paramArrayOfPhrase, paramInt1, j);
    quickSort(paramArrayOfPhrase, paramInt1, j - 1);
    quickSort(paramArrayOfPhrase, j + 1, paramInt2);
  }
  
  private static void swap(Phrase[] paramArrayOfPhrase, int paramInt1, int paramInt2)
  {
    Phrase localPhrase = paramArrayOfPhrase[paramInt1];
    paramArrayOfPhrase[paramInt1] = paramArrayOfPhrase[paramInt2];
    paramArrayOfPhrase[paramInt2] = localPhrase;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\data\Part.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */