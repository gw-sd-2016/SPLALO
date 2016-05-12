package jm.music.data;

import java.awt.Point;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;
import jm.JMC;

public class Score
  implements JMC, Cloneable, Serializable
{
  public static final String DEFAULT_TITLE = "Untitled Score";
  public static final double DEFAULT_TEMPO = 60.0D;
  public static final int DEFAULT_KEY_SIGNATURE = 0;
  public static final int DEFAULT_KEY_QUALITY = 0;
  public static final int DEFAULT_NUMERATOR = 4;
  public static final int DEFAULT_DENOMINATOR = 4;
  private String title = "Unnamed Score";
  private Vector partList;
  private double tempo;
  private int keySignature;
  private int keyQuality;
  private int numerator;
  private int denominator;
  
  public Score()
  {
    this("Untitled Score");
  }
  
  public Score(String paramString)
  {
    this(paramString, 60.0D);
  }
  
  public Score(double paramDouble)
  {
    this("Untitled Score", paramDouble);
  }
  
  public Score(String paramString, double paramDouble)
  {
    this.title = paramString;
    this.tempo = paramDouble;
    this.partList = new Vector();
    this.keySignature = 0;
    this.keyQuality = 0;
    this.numerator = 4;
    this.denominator = 4;
  }
  
  public Score(Part paramPart)
  {
    this();
    if (paramPart.getTempo() > 0.0D) {
      this.tempo = paramPart.getTempo();
    } else {
      this.tempo = this.tempo;
    }
    addPart(paramPart);
  }
  
  public Score(String paramString, double paramDouble, Part paramPart)
  {
    this(paramString, paramDouble);
    addPart(paramPart);
  }
  
  public Score(Part[] paramArrayOfPart)
  {
    this();
    addPartList(paramArrayOfPart);
  }
  
  public Score(Part paramPart, String paramString)
  {
    this(paramString, paramPart.getTempo());
    addPart(paramPart);
  }
  
  public Score(Part[] paramArrayOfPart, String paramString)
  {
    this(paramString);
    addPartList(paramArrayOfPart);
  }
  
  public Score(Part paramPart, String paramString, double paramDouble)
  {
    this(paramString, paramDouble);
    addPart(paramPart);
  }
  
  public Score(Part[] paramArrayOfPart, String paramString, double paramDouble)
  {
    this(paramString, paramDouble);
    addPartList(paramArrayOfPart);
  }
  
  public void add(Part paramPart)
  {
    addPart(paramPart);
  }
  
  public void addPart(Part paramPart)
  {
    paramPart.setMyScore(this);
    this.partList.addElement(paramPart);
  }
  
  public void insertPart(Part paramPart, int paramInt)
    throws ArrayIndexOutOfBoundsException
  {
    this.partList.insertElementAt(paramPart, paramInt);
    paramPart.setMyScore(this);
  }
  
  public void addPartList(Part[] paramArrayOfPart)
  {
    for (int i = 0; i < paramArrayOfPart.length; i++) {
      addPart(paramArrayOfPart[i]);
    }
  }
  
  public void removePart(int paramInt)
  {
    Vector localVector = this.partList;
    try
    {
      localVector.removeElement(localVector.elementAt(paramInt));
    }
    catch (RuntimeException localRuntimeException)
    {
      System.err.println("The Part index to be deleted must be within the score.");
    }
  }
  
  public void removePart(Part paramPart)
  {
    this.partList.removeElement(paramPart);
  }
  
  public void removeLastPart()
  {
    Vector localVector = this.partList;
    localVector.removeElement(localVector.lastElement());
  }
  
  public void removeAllParts()
  {
    this.partList.removeAllElements();
  }
  
  public Vector getPartList()
  {
    return this.partList;
  }
  
  public Part[] getPartArray()
  {
    Vector localVector = (Vector)this.partList.clone();
    Part[] arrayOfPart = new Part[localVector.size()];
    for (int i = 0; i < arrayOfPart.length; i++) {
      arrayOfPart[i] = ((Part)localVector.elementAt(i));
    }
    return arrayOfPart;
  }
  
  public Part getPart(String paramString)
  {
    Enumeration localEnumeration = this.partList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      if (localPart.getTitle().equalsIgnoreCase(paramString)) {
        return localPart;
      }
    }
    return null;
  }
  
  public Part getPart(int paramInt)
  {
    Enumeration localEnumeration = this.partList.elements();
    for (int i = 0; localEnumeration.hasMoreElements(); i++)
    {
      Part localPart = (Part)localEnumeration.nextElement();
      if (i == paramInt) {
        return localPart;
      }
    }
    return null;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
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
  
  public void setTimeSignature(int paramInt1, int paramInt2)
  {
    this.numerator = paramInt1;
    this.denominator = paramInt2;
  }
  
  public Point getTimeSignature()
  {
    return new Point(this.numerator, this.denominator);
  }
  
  public void setNumerator(int paramInt)
  {
    this.numerator = paramInt;
  }
  
  public void setDenominator(int paramInt)
  {
    this.denominator = paramInt;
  }
  
  public int getNumerator()
  {
    return this.numerator;
  }
  
  public int getDenominator()
  {
    return this.denominator;
  }
  
  public Score copy()
  {
    Score localScore = new Score(this.title + " copy");
    localScore.setTempo(this.tempo);
    localScore.setTimeSignature(this.numerator, this.denominator);
    Enumeration localEnumeration = this.partList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      localScore.addPart(localPart.copy());
    }
    return localScore;
  }
  
  public Score copy(double paramDouble1, double paramDouble2)
  {
    Score localScore = copy();
    localScore.removeAllParts();
    localScore.setTempo(this.tempo);
    localScore.setTimeSignature(this.numerator, this.denominator);
    int i = size();
    for (int j = 0; j < i; j++) {
      localScore.addPart(getPart(j).copy(paramDouble1, paramDouble2));
    }
    return localScore;
  }
  
  public double getEndTime()
  {
    double d1 = 0.0D;
    Enumeration localEnumeration = this.partList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      double d2 = localPart.getEndTime();
      if (d2 > d1) {
        d1 = d2;
      }
    }
    return d1;
  }
  
  public String toString()
  {
    String str = new String("***** jMusic SCORE: '" + this.title + "' contains " + size() + " parts. ****" + '\n');
    str = str + "Score Tempo = " + this.tempo + " bpm" + '\n';
    Enumeration localEnumeration = this.partList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      str = str + localPart.toString() + '\n';
    }
    return str;
  }
  
  public void empty()
  {
    empty(false);
  }
  
  public void empty(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      Enumeration localEnumeration1 = getPartList().elements();
      while (localEnumeration1.hasMoreElements())
      {
        Part localPart = (Part)localEnumeration1.nextElement();
        Enumeration localEnumeration2 = localPart.getPhraseList().elements();
        while (localEnumeration2.hasMoreElements())
        {
          Phrase localPhrase = (Phrase)localEnumeration2.nextElement();
          Enumeration localEnumeration3 = localPart.getPhraseList().elements();
          while (localEnumeration3.hasMoreElements())
          {
            Note localNote = (Note)localEnumeration3.nextElement();
            localNote = null;
          }
          localPhrase = null;
        }
        localPart = null;
      }
    }
    this.partList.removeAllElements();
  }
  
  public int length()
  {
    return size();
  }
  
  public int size()
  {
    return this.partList.size();
  }
  
  public int getSize()
  {
    return this.partList.size();
  }
  
  public void clean()
  {
    Enumeration localEnumeration = getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      localPart.clean();
      if (localPart.getPhraseList().size() == 0) {
        removePart(localPart);
      }
    }
  }
  
  public int getHighestPitch()
  {
    int i = 0;
    Enumeration localEnumeration = getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      if (localPart.getHighestPitch() > i) {
        i = localPart.getHighestPitch();
      }
    }
    return i;
  }
  
  public int getLowestPitch()
  {
    int i = 127;
    Enumeration localEnumeration = getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      if (localPart.getLowestPitch() < i) {
        i = localPart.getLowestPitch();
      }
    }
    return i;
  }
  
  public double getLongestRhythmValue()
  {
    double d = 0.0D;
    Enumeration localEnumeration = getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      if (localPart.getLongestRhythmValue() > d) {
        d = localPart.getLongestRhythmValue();
      }
    }
    return d;
  }
  
  public double getShortestRhythmValue()
  {
    double d = 1000.0D;
    Enumeration localEnumeration = getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      if (localPart.getShortestRhythmValue() < d) {
        d = localPart.getShortestRhythmValue();
      }
    }
    return d;
  }
  
  public void setPan(double paramDouble)
  {
    Enumeration localEnumeration = this.partList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      localPart.setPan(paramDouble);
    }
  }
  
  public Part createPart()
  {
    Part localPart = new Part();
    addPart(localPart);
    return localPart;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\data\Score.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */