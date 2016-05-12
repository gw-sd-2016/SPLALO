package jm.music.data;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;
import jm.JMC;

public class CPhrase
  implements JMC, Cloneable, Serializable
{
  private Vector phraseList;
  private double currentTime;
  private double startTime;
  private String title;
  private int instrument = -1;
  private boolean append = false;
  private Phrase linkedPhrase;
  private double pan = 0.5D;
  private double tempo = -1.0D;
  private int numerator;
  private int denominator;
  
  public CPhrase()
  {
    this("Untitled CPhrase", 0.0D, -1, true);
  }
  
  public CPhrase(double paramDouble)
  {
    this("Untitled CPhrase", paramDouble, -1, false);
  }
  
  public CPhrase(String paramString)
  {
    this(paramString, 0.0D, -1, true);
  }
  
  public CPhrase(String paramString, double paramDouble)
  {
    this(paramString, paramDouble, -1, false);
  }
  
  public CPhrase(double paramDouble, int paramInt)
  {
    this("Untitled CPhrase", paramDouble, paramInt, false);
  }
  
  public CPhrase(String paramString, double paramDouble, int paramInt)
  {
    this(paramString, paramDouble, paramInt, false);
  }
  
  public CPhrase(String paramString, double paramDouble, int paramInt, boolean paramBoolean)
  {
    this.title = paramString;
    this.startTime = paramDouble;
    this.currentTime = paramDouble;
    this.instrument = paramInt;
    this.append = paramBoolean;
    this.phraseList = new Vector();
  }
  
  public Vector getPhraseList()
  {
    return this.phraseList;
  }
  
  public void setPhraseList(Vector paramVector)
  {
    this.phraseList = paramVector;
  }
  
  public void addPhrase(Phrase paramPhrase)
  {
    if (paramPhrase.getAppend()) {
      paramPhrase.setStartTime(this.startTime);
    }
    if (paramPhrase.getStartTime() >= this.startTime) {
      this.phraseList.addElement(paramPhrase);
    } else {
      System.err.println("Phrase to added to CPhrase: Phrases added to a CPhrase must have a start time at ot after the CPhrase start time.");
    }
  }
  
  public void removePhrase(Phrase paramPhrase)
  {
    this.phraseList.removeElement(paramPhrase);
  }
  
  public void addChord(int[] paramArrayOfInt, double paramDouble)
  {
    addChord(paramArrayOfInt, paramDouble, 70);
  }
  
  public void addChord(int[] paramArrayOfInt, double paramDouble, int paramInt)
  {
	  int i;
    if (this.phraseList.size() < paramArrayOfInt.length)
    {
      i = paramArrayOfInt.length - this.phraseList.size();
      for (int j = 0; j < i; j++)
      {
        Phrase localPhrase = new Phrase(getEndTime(), this.instrument);
        this.phraseList.addElement(localPhrase);
      }
    }
    Note localNote;
    for (i = 0; i < paramArrayOfInt.length; i++)
    {
      localNote = new Note(paramArrayOfInt[i], paramDouble, paramInt);
      ((Phrase)this.phraseList.elementAt(i)).addNote(localNote);
    }
    while (i < this.phraseList.size())
    {
      localNote = new Note(Integer.MIN_VALUE, paramDouble);
      ((Phrase)this.phraseList.elementAt(i)).addNote(localNote);
      i++;
    }
  }
  
  public boolean hasNote(Note paramNote)
  {
    for (int i = 0; i < this.phraseList.size(); i++)
    {
      Phrase localPhrase = (Phrase)this.phraseList.get(i);
      Note[] arrayOfNote = localPhrase.getNoteArray();
      for (int j = 0; j < arrayOfNote.length; j++)
      {
        Note localNote = arrayOfNote[j];
        if (paramNote.getNote().equals(localNote.getNote())) {
          return true;
        }
      }
    }
    return false;
  }
  
  public void addChord(Note[] paramArrayOfNote)
  {
    this.currentTime = getEndTime();
    double d = paramArrayOfNote[0].getRhythmValue();
    if (this.phraseList.size() < paramArrayOfNote.length)
    {
      int i = paramArrayOfNote.length - this.phraseList.size();
      for (int j = 0; j < i; j++)
      {
        Phrase localPhrase = new Phrase(getEndTime(), this.instrument);
        this.phraseList.addElement(localPhrase);
      }
    }
    int i;
    for (i = 0; i < paramArrayOfNote.length; i++)
    {
      paramArrayOfNote[i].setRhythmValue(d);
      ((Phrase)this.phraseList.elementAt(i)).addNote(paramArrayOfNote[i]);
    }
    while (i < this.phraseList.size())
    {
      Note localNote = new Note(Integer.MIN_VALUE, d);
      ((Phrase)this.phraseList.elementAt(i)).addNote(localNote);
      i++;
    }
    this.currentTime += d;
  }
  
  public double getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(double paramDouble)
  {
    double d = paramDouble - this.startTime;
    Enumeration localEnumeration = this.phraseList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      localPhrase.setStartTime(localPhrase.getStartTime() + d);
    }
    this.startTime = paramDouble;
    this.append = false;
  }
  
  public double getEndTime()
  {
    double d1 = getStartTime();
    Enumeration localEnumeration = this.phraseList.elements();
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
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public int getInstrument()
  {
    return this.instrument;
  }
  
  public void setTitle(int paramInt)
  {
    if (paramInt < -1) {
      this.instrument = paramInt;
    }
  }
  
  public boolean getAppend()
  {
    return this.append;
  }
  
  public void setAppend(boolean paramBoolean)
  {
    this.append = paramBoolean;
  }
  
  public Phrase getLinkedPhrase()
  {
    return this.linkedPhrase;
  }
  
  public void setLinkedPhrase(Phrase paramPhrase)
  {
    this.linkedPhrase = paramPhrase;
  }
  
  public double getPan()
  {
    return this.pan;
  }
  
  public void setPan(double paramDouble)
  {
    this.pan = paramDouble;
    Enumeration localEnumeration = this.phraseList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      localPhrase.setPan(paramDouble);
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
  
  public CPhrase copy()
  {
    Vector localVector = new Vector();
    CPhrase localCPhrase = new CPhrase(this.title + " copy", this.startTime, this.instrument);
    Enumeration localEnumeration = this.phraseList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = ((Phrase)localEnumeration.nextElement()).copy();
      localVector.addElement(localPhrase);
    }
    localCPhrase.setPhraseList(localVector);
    localCPhrase.setAppend(this.append);
    localCPhrase.setLinkedPhrase(this.linkedPhrase);
    return localCPhrase;
  }
  
  public CPhrase copy(double paramDouble1, double paramDouble2)
  {
    Vector localVector = new Vector();
    CPhrase localCPhrase = new CPhrase(this.title + " copy", paramDouble1, this.instrument);
    Enumeration localEnumeration = this.phraseList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = ((Phrase)localEnumeration.nextElement()).copy(paramDouble1, paramDouble2);
      localPhrase.setStartTime(0.0D);
      localVector.addElement(localPhrase);
    }
    localCPhrase.setPhraseList(localVector);
    localCPhrase.setAppend(this.append);
    localCPhrase.setLinkedPhrase(this.linkedPhrase);
    return localCPhrase;
  }
  
  public void empty()
  {
    this.phraseList.removeAllElements();
  }
  
  public String toString()
  {
    String str = new String("---- jMusic CPHRASE: '" + this.title + "' Start time: " + this.startTime + " ----" + '\n');
    Enumeration localEnumeration = this.phraseList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      str = str + localPhrase.toString() + '\n';
    }
    return str;
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
  
  public void setRhythmValue(int paramInt)
  {
    Enumeration localEnumeration = getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      localPhrase.setRhythmValue(paramInt);
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
  
  public void flam()
  {
    flam(0.05D);
  }
  
  public void flam(double paramDouble)
  {
    int i = 0;
    Enumeration localEnumeration1 = this.phraseList.elements();
    while (localEnumeration1.hasMoreElements())
    {
      double d = paramDouble * i;
      Phrase localPhrase = (Phrase)localEnumeration1.nextElement();
      Enumeration localEnumeration2 = localPhrase.getNoteList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        Note localNote = (Note)localEnumeration2.nextElement();
        localNote.setOffset(d);
      }
      i++;
    }
  }
  
  public void setTempo(double paramDouble)
  {
    if (paramDouble > 0.0D)
    {
      this.tempo = paramDouble;
      Enumeration localEnumeration = this.phraseList.elements();
      while (localEnumeration.hasMoreElements())
      {
        Phrase localPhrase = (Phrase)localEnumeration.nextElement();
        localPhrase.setTempo(paramDouble);
      }
    }
  }
  
  public double getTempo()
  {
    return this.tempo;
  }

public double getNumerator() 
{
	    return this.numerator;
}
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\data\CPhrase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */