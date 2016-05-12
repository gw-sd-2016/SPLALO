package jm.music.data;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;
import jm.JMC;

public class Phrase
  implements JMC, Cloneable, Serializable
{
  public static final double MIN_START_TIME = 0.0D;
  public static final String DEFAULT_TITLE = "Untitled Phrase";
  public static final double DEFAULT_START_TIME = 0.0D;
  public static final int DEFAULT_INSTRUMENT = -1;
  public static final boolean DEFAULT_APPEND = false;
  public static final double DEFAULT_TEMPO = -1.0D;
  public static final double DEFAULT_PAN = 0.5D;
  public static final int DEFAULT_NUMERATOR = 4;
  public static final int DEFAULT_DENOMINATOR = 4;
  private Vector noteList;
  private String title = "Unnamed Phrase";
  private Position position;
  private int instrument;
  private double tempo;
  private boolean append = false;
  private Phrase linkedPhrase;
  private double pan = 0.5D;
  private int numerator;
  private int denominator;
  private Part myPart = null;
  private boolean mute = false;
  
  public Phrase()
  {
    this(0.0D);
    this.append = true;
  }
  
  public Phrase(double paramDouble)
  {
    this(paramDouble, -1);
  }
  
  public Phrase(double paramDouble, int paramInt)
  {
    this("Untitled Phrase", paramDouble, paramInt);
  }
  
  public Phrase(String paramString)
  {
    this(paramString, 0.0D);
    this.append = true;
  }
  
  public Phrase(String paramString, double paramDouble)
  {
    this(paramString, paramDouble, -1);
  }
  
  public Phrase(String paramString, double paramDouble, int paramInt)
  {
    this(paramString, paramDouble, paramInt, false);
  }
  
  public Phrase(String paramString, double paramDouble, int paramInt, boolean paramBoolean)
  {
    this.title = paramString;
    this.position = new Position(paramDouble, this);
    this.append = paramBoolean;
    if (paramInt < -1)
    {
      System.err.println(new Exception("jMusic EXCEPTION: instrument value must be greater than 0"));
      new Exception().printStackTrace();
      System.exit(1);
    }
    this.instrument = paramInt;
    this.noteList = new Vector();
    this.numerator = 4;
    this.denominator = 4;
    this.tempo = -1.0D;
  }
  
  public Phrase(Note paramNote)
  {
    this();
    addNote(paramNote);
  }
  
  public Phrase(Note[] paramArrayOfNote)
  {
    this();
    addNoteList(paramArrayOfNote);
  }
  
  public Phrase(Note paramNote, String paramString)
  {
    this(paramString);
    addNote(paramNote);
  }
  
  public Phrase(Note[] paramArrayOfNote, String paramString)
  {
    this(paramString);
    addNoteList(paramArrayOfNote);
  }
  
  public Phrase(Note paramNote, double paramDouble)
  {
    this(paramNote);
    setStartTime(paramDouble);
  }
  
  public int getInstrument()
  {
    return this.instrument;
  }
  
  public void setInstrument(int paramInt)
  {
    this.instrument = paramInt;
  }
  
  public void addNote(Note paramNote)
  {
    paramNote.setMyPhrase(this);
    this.noteList.addElement(paramNote);
  }
  
  public void addNote(int paramInt, double paramDouble)
  {
    Note localNote = new Note(paramInt, paramDouble);
    addNote(localNote);
  }
  
  public void add(Note paramNote)
  {
    addNote(paramNote);
  }
  
  public void addRest(Rest paramRest)
  {
    paramRest.setMyPhrase(this);
    this.noteList.addElement(paramRest);
  }
  
  public void addNoteList(Note[] paramArrayOfNote)
  {
    for (int i = 0; i < paramArrayOfNote.length; i++) {
      addNote(paramArrayOfNote[i]);
    }
  }
  
  public void addNoteList(Vector paramVector, boolean paramBoolean)
  {
    Enumeration localEnumeration = paramVector.elements();
    if (!paramBoolean) {
      this.noteList.removeAllElements();
    }
    while (localEnumeration.hasMoreElements()) {
      try
      {
        Note localNote = (Note)localEnumeration.nextElement();
        addNote(localNote);
      }
      catch (RuntimeException localRuntimeException)
      {
        System.err.println("The vector passed to this method must contain Notes only!");
      }
    }
  }
  
  public void addNoteList(Note[] paramArrayOfNote, boolean paramBoolean)
  {
    if (!paramBoolean) {
      this.noteList.removeAllElements();
    }
    for (int i = 0; i < paramArrayOfNote.length; i++) {
      addNote(paramArrayOfNote[i]);
    }
  }
  
  public void addNoteList(int[] paramArrayOfInt, double paramDouble)
  {
    double[] arrayOfDouble = new double[paramArrayOfInt.length];
    for (int i = 0; i < arrayOfDouble.length; i++) {
      arrayOfDouble[i] = paramDouble;
    }
    addNoteList(paramArrayOfInt, arrayOfDouble);
  }
  
  public void addNoteList(int[] paramArrayOfInt, double paramDouble, int paramInt)
  {
    double[] arrayOfDouble = new double[paramArrayOfInt.length];
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    for (int i = 0; i < arrayOfDouble.length; i++)
    {
      arrayOfDouble[i] = paramDouble;
      arrayOfInt[i] = paramInt;
    }
    addNoteList(paramArrayOfInt, arrayOfDouble, arrayOfInt);
  }
  
  public void addNoteList(double[] paramArrayOfDouble, double paramDouble)
  {
    double[] arrayOfDouble = new double[paramArrayOfDouble.length];
    for (int i = 0; i < arrayOfDouble.length; i++) {
      arrayOfDouble[i] = paramDouble;
    }
    addNoteList(paramArrayOfDouble, arrayOfDouble);
  }
  
  public void addNoteList(int[] paramArrayOfInt, double[] paramArrayOfDouble)
  {
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      arrayOfInt[i] = 85;
    }
    addNoteList(paramArrayOfInt, paramArrayOfDouble, arrayOfInt);
  }
  
  public void addNoteList(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
  {
    int[] arrayOfInt = new int[paramArrayOfDouble1.length];
    for (int i = 0; i < paramArrayOfDouble1.length; i++) {
      arrayOfInt[i] = 85;
    }
    addNoteList(paramArrayOfDouble1, paramArrayOfDouble2, arrayOfInt);
  }
  
  public void addNoteList(int[] paramArrayOfInt1, double[] paramArrayOfDouble, int[] paramArrayOfInt2)
  {
    addNoteList(paramArrayOfInt1, paramArrayOfDouble, paramArrayOfInt2, true);
  }
  
  public void addNoteList(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int[] paramArrayOfInt)
  {
    addNoteList(paramArrayOfDouble1, paramArrayOfDouble2, paramArrayOfInt, true);
  }
  
  public void addNoteList(int[] paramArrayOfInt1, double[] paramArrayOfDouble, int[] paramArrayOfInt2, boolean paramBoolean)
  {
    if (!paramBoolean) {
      this.noteList.removeAllElements();
    }
    for (int i = 0; i < paramArrayOfInt1.length; i++) {
      try
      {
        Note localNote = new Note(paramArrayOfInt1[i], paramArrayOfDouble[i], paramArrayOfInt2[i]);
        addNote(localNote);
      }
      catch (RuntimeException localRuntimeException)
      {
        System.err.println("You must enter arrays of even length");
      }
    }
  }
  
  public void addNoteList(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int[] paramArrayOfInt, boolean paramBoolean)
  {
    if (!paramBoolean) {
      this.noteList.removeAllElements();
    }
    for (int i = 0; i < paramArrayOfDouble1.length; i++) {
      try
      {
        Note localNote = new Note(paramArrayOfDouble1[i], paramArrayOfDouble2[i], paramArrayOfInt[i]);
        addNote(localNote);
      }
      catch (RuntimeException localRuntimeException)
      {
        System.err.println("jMusic Phrase error: You must enter arrays of even length");
      }
    }
  }
  
  public void addNoteList(double[] paramArrayOfDouble)
  {
    for (int i = 0; i < paramArrayOfDouble.length; i += 2) {
      try
      {
        Note localNote = new Note((int)paramArrayOfDouble[i], paramArrayOfDouble[(i + 1)]);
        addNote(localNote);
      }
      catch (RuntimeException localRuntimeException)
      {
        System.err.println("Error adding note list: Possibly the wrong number of values in the pitch and rhythm array.");
      }
    }
  }
  
  public void addNoteList(int paramInt, double[] paramArrayOfDouble)
  {
    for (int i = 0; i < paramArrayOfDouble.length; i++) {
      addNote(new Note(paramInt, paramArrayOfDouble[i]));
    }
  }
  
  public void addNoteList(double paramDouble, double[] paramArrayOfDouble)
  {
    for (int i = 0; i < paramArrayOfDouble.length; i++) {
      addNote(new Note(paramDouble, paramArrayOfDouble[i]));
    }
  }
  
  public void addChord(int[] paramArrayOfInt, double paramDouble)
  {
    for (int i = 0; i < paramArrayOfInt.length - 1; i++)
    {
      Note localNote = new Note(paramArrayOfInt[i], 0.0D);
      localNote.setDuration(paramDouble * 0.9D);
      addNote(localNote);
    }
    addNote(paramArrayOfInt[(paramArrayOfInt.length - 1)], paramDouble);
  }
  
  public int[] getPitchArray()
  {
    Note[] arrayOfNote = getNoteArray();
    int[] arrayOfInt = new int[arrayOfNote.length];
    for (int i = 0; i < arrayOfNote.length; i++) {
      arrayOfInt[i] = arrayOfNote[i].getPitch();
    }
    return arrayOfInt;
  }
  
  public double[] getRhythmArray()
  {
    Note[] arrayOfNote = getNoteArray();
    double[] arrayOfDouble = new double[arrayOfNote.length];
    for (int i = 0; i < arrayOfNote.length; i++) {
      arrayOfDouble[i] = arrayOfNote[i].getRhythmValue();
    }
    return arrayOfDouble;
  }
  
  public int[] getDynamicArray()
  {
    Note[] arrayOfNote = getNoteArray();
    int[] arrayOfInt = new int[arrayOfNote.length];
    for (int i = 0; i < arrayOfNote.length; i++) {
      arrayOfInt[i] = arrayOfNote[i].getPitch();
    }
    return arrayOfInt;
  }
  
  public void removeNote(int paramInt)
  {
    Vector localVector = this.noteList;
    try
    {
      localVector.removeElement(localVector.elementAt(paramInt));
    }
    catch (RuntimeException localRuntimeException)
    {
      System.err.println("Note index to be deleted must be within the phrase.");
    }
  }
  
  public void removeNote(Note paramNote)
  {
    this.noteList.removeElement(paramNote);
  }
  
  public void removeLastNote()
  {
    Vector localVector = this.noteList;
    localVector.removeElementAt(localVector.size() - 1);
  }
  
  public Vector getNoteList()
  {
    return this.noteList;
  }
  
  public void setNoteList(Vector paramVector)
  {
    this.noteList = paramVector;
  }
  
  public Note[] getNoteArray()
  {
    Vector localVector = this.noteList;
    Note[] arrayOfNote = new Note[localVector.size()];
    for (int i = 0; i < arrayOfNote.length; i++) {
      arrayOfNote[i] = ((Note)localVector.elementAt(i));
    }
    return arrayOfNote;
  }
  
  public double getStartTime()
  {
    return this.position.getStartTime();
  }
  
  public void setStartTime(double paramDouble)
  {
    if (paramDouble >= 0.0D)
    {
      this.position.setStartTime(paramDouble);
      setAppend(false);
    }
    else
    {
      System.err.println("Error setting phrase start time value: You must enter values greater than 0.0");
    }
  }
  
  public boolean attemptAnchoringTo(Phrase paramPhrase, Alignment paramAlignment, double paramDouble)
  {
    Position localPosition = new Position(paramPhrase.position, paramAlignment, paramDouble, this);
    if (localPosition.getStartTime() < 0.0D) {
      return false;
    }
    this.position = localPosition;
    return true;
  }
  
  public Anchoring getAnchoring()
  {
    return this.position.getAnchoring();
  }
  
  public double getEndTime()
  {
    double d1 = getStartTime() < 0.0D ? 0.0D : getStartTime();
    double d2 = d1;
    Enumeration localEnumeration = this.noteList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      d2 += localNote.getRhythmValue();
    }
    return d2;
  }
  
  final double getTotalDuration()
  {
    double d = 0.0D;
    Enumeration localEnumeration = this.noteList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      d += localNote.getRhythmValue();
    }
    return d;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
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
    Enumeration localEnumeration = this.noteList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      localNote.setPan(paramDouble);
    }
  }
  
  public double getTempo()
  {
    return this.tempo;
  }
  
  public void setTempo(double paramDouble)
  {
    this.tempo = paramDouble;
  }
  
  public Note getNote(int paramInt)
  {
    Enumeration localEnumeration = this.noteList.elements();
    for (int i = 0; localEnumeration.hasMoreElements(); i++)
    {
      Note localNote = (Note)localEnumeration.nextElement();
      if (i == paramInt) {
        return localNote;
      }
    }
    return null;
  }
  
  public int length()
  {
    return size();
  }
  
  public int size()
  {
    return this.noteList.size();
  }
  
  public int getSize()
  {
    return this.noteList.size();
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
  
  public void setMyPart(Part paramPart)
  {
    this.myPart = paramPart;
  }
  
  public Part getMyPart()
  {
    return this.myPart;
  }
  
  public Phrase copy()
  {
    Phrase localPhrase = new Phrase();
    copyAttributes(localPhrase);
    Enumeration localEnumeration = this.noteList.elements();
    while (localEnumeration.hasMoreElements()) {
      localPhrase.addNote(((Note)localEnumeration.nextElement()).copy());
    }
    return localPhrase;
  }
  
  private void copyAttributes(Phrase paramPhrase)
  {
    paramPhrase.position = this.position.copy(paramPhrase);
    paramPhrase.setTitle(this.title + " copy");
    paramPhrase.setInstrument(this.instrument);
    paramPhrase.setAppend(this.append);
    paramPhrase.setPan(this.pan);
    paramPhrase.setLinkedPhrase(this.linkedPhrase);
    paramPhrase.setMyPart(getMyPart());
    paramPhrase.setTempo(this.tempo);
    paramPhrase.setNumerator(this.numerator);
    paramPhrase.setDenominator(this.denominator);
  }
  
  public Phrase copy(double paramDouble1, double paramDouble2)
  {
    Phrase localPhrase = copy(paramDouble1, paramDouble2, true);
    return localPhrase;
  }
  
  public Phrase copy(double paramDouble1, double paramDouble2, boolean paramBoolean)
  {
    if ((paramDouble1 >= paramDouble2) || (paramDouble2 < getStartTime())) {
      return null;
    }
    Phrase localPhrase = new Phrase(0.0D);
    copyAttributes(localPhrase);
    double d = getStartTime();
    if (d < 0.0D) {
      d = 0.0D;
    }
    if (paramDouble1 < d)
    {
      Note localNote1 = new Note(Integer.MIN_VALUE, d - paramDouble1);
      localPhrase.addNote(localNote1);
      paramDouble2 += d - paramDouble1;
    }
    for (int i = 0; i < size(); i++)
    {
      Note localNote3;
      if (d < paramDouble1)
      {
        if ((d + getNote(i).getRhythmValue() > paramDouble1) && (d + getNote(i).getRhythmValue() <= paramDouble2)) {
          if (paramBoolean)
          {
            localNote3 = new Note(Integer.MIN_VALUE, d + getNote(i).getRhythmValue() - paramDouble1);
            localPhrase.addNote(localNote3);
          }
          else if (!getNote(i).getPitchType())
          {
            localNote3 = new Note(getNote(i).getPitch(), d + getNote(i).getRhythmValue() - paramDouble1, getNote(i).getDynamic());
            localPhrase.addNote(localNote3);
          }
          else
          {
            localNote3 = new Note(getNote(i).getFrequency(), d + getNote(i).getRhythmValue() - paramDouble1, getNote(i).getDynamic());
            localPhrase.addNote(localNote3);
          }
        }
        if (d + getNote(i).getRhythmValue() > paramDouble2) {
          if (paramBoolean)
          {
            localNote3 = new Note(Integer.MIN_VALUE, d + getNote(i).getRhythmValue() - paramDouble1, getNote(i).getDynamic());
            localPhrase.addNote(localNote3);
          }
          else if (!getNote(i).getPitchType())
          {
            localNote3 = new Note(getNote(i).getPitch(), d + paramDouble2 - paramDouble1, getNote(i).getDynamic());
            localPhrase.addNote(localNote3);
          }
          else
          {
            localNote3 = new Note(getNote(i).getPitch(), d + paramDouble2 - paramDouble1, getNote(i).getDynamic());
            localPhrase.addNote(localNote3);
          }
        }
      }
      if ((d >= paramDouble1) && (d < paramDouble2)) {
        if (d + getNote(i).getRhythmValue() <= paramDouble2)
        {
          localPhrase.addNote(getNote(i));
        }
        else
        {
          localNote3 = new Note(getNote(i).getPitch(), paramDouble2 - d, getNote(i).getDynamic());
          localPhrase.addNote(localNote3);
        }
      }
      d += getNote(i).getRhythmValue();
    }
    if (d < paramDouble2)
    {
      Note localNote2 = new Note(Integer.MIN_VALUE, paramDouble2 - d);
      localPhrase.addNote(localNote2);
    }
    return localPhrase;
  }
  
  public Phrase copy(double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if ((paramDouble1 >= paramDouble2) || (paramDouble2 < getStartTime()))
    {
      System.out.println("invalid arguments in Phrase.copy");
      return null;
    }
    Phrase localPhrase = new Phrase("", paramDouble1, this.instrument);
    localPhrase.setAppend(this.append);
    localPhrase.setPan(this.pan);
    localPhrase.setLinkedPhrase(this.linkedPhrase);
    localPhrase.setMyPart(getMyPart());
    double d1 = getStartTime();
    if (d1 < 0.0D) {
      d1 = 0.0D;
    }
    Enumeration localEnumeration = getNoteList().elements();
    Note localNote1;
    while ((paramDouble1 > d1) && (localEnumeration.hasMoreElements()))
    {
      localNote1 = (Note)localEnumeration.nextElement();
      d1 += localNote1.getRhythmValue();
    }
    if (paramDouble1 < d1) {
      if (d1 < paramDouble2)
      {
        if (paramBoolean3)
        {
          localPhrase.setStartTime(d1 + getStartTime());
        }
        else
        {
          localNote1 = new Note(Integer.MIN_VALUE, d1 - paramDouble1);
          localPhrase.addNote(localNote1);
        }
      }
      else
      {
        localNote1 = new Note(Integer.MIN_VALUE, paramDouble2 - paramDouble1);
        localPhrase.addNote(localNote1);
        return localPhrase;
      }
    }
    double d2 = 0.0D;
    Note localNote2;
    while ((localEnumeration.hasMoreElements()) && (d1 < paramDouble2))
    {
      localNote2 = ((Note)localEnumeration.nextElement()).copy();
      if ((localNote2.getRhythmValue() + d1 > paramDouble2) && (paramBoolean1)) {
        localNote2.setRhythmValue(paramDouble2 - d1, paramBoolean2);
      }
      localPhrase.addNote(localNote2);
      d2 += localNote2.getRhythmValue();
      d1 += localNote2.getRhythmValue();
    }
    if (d1 < paramDouble2)
    {
      localNote2 = new Note(Integer.MIN_VALUE, paramDouble2 - d1);
      localPhrase.addNote(localNote2);
    }
    else if (d2 == 0.0D)
    {
      localNote2 = new Note(Integer.MIN_VALUE, paramDouble2 - paramDouble1);
      localPhrase.addNote(localNote2);
    }
    return localPhrase;
  }
  
  public Phrase copy(int paramInt1, int paramInt2)
  {
    if (paramInt2 >= paramInt1)
    {
      System.err.println("jMusic Phrase copy error: lowset pitch is not lower than highest pitch");
      System.exit(0);
    }
    Phrase localPhrase = new Phrase(this.title + " copy");
    localPhrase.position = this.position.copy(localPhrase);
    localPhrase.setInstrument(this.instrument);
    localPhrase.setAppend(this.append);
    localPhrase.setPan(this.pan);
    localPhrase.setLinkedPhrase(this.linkedPhrase);
    localPhrase.setMyPart(getMyPart());
    Enumeration localEnumeration = this.noteList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = ((Note)localEnumeration.nextElement()).copy();
      if ((localNote.getPitch() > paramInt1) && (localNote.getPitch() < paramInt2)) {
        localNote.setPitch(Integer.MIN_VALUE);
      }
      localPhrase.addNote(localNote);
    }
    return localPhrase;
  }
  
  public String toString()
  {
    String str = new String("-------- jMusic PHRASE: '" + this.title + "' contains " + size() + " notes.  Start time: " + getStartTime() + " --------" + '\n');
    if (this.tempo > 0.0D) {
      str = str + "Phrase Tempo = " + this.tempo + '\n';
    }
    Enumeration localEnumeration = getNoteList().elements();
    int i = 0;
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      str = str + localNote.toString() + '\n';
    }
    return str;
  }
  
  public void empty()
  {
    this.noteList.removeAllElements();
  }
  
  public Phrase alias()
  {
    Phrase localPhrase = new Phrase(this.title + " alias", getStartTime(), this.instrument);
    localPhrase.setTempo(this.tempo);
    localPhrase.setAppend(this.append);
    localPhrase.noteList = this.noteList;
    return localPhrase;
  }
  
  public int getHighestPitch()
  {
    int i = -1;
    Enumeration localEnumeration = getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      if ((!localNote.getPitchType()) && (localNote.getPitch() > i)) {
        i = localNote.getPitch();
      }
    }
    return i;
  }
  
  public int getLowestPitch()
  {
    int i = 128;
    Enumeration localEnumeration = getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      if ((!localNote.getPitchType()) && (localNote.getPitch() < i) && (localNote.getPitch() >= 0)) {
        i = localNote.getPitch();
      }
    }
    return i;
  }
  
  public double getLongestRhythmValue()
  {
    double d = 0.0D;
    Enumeration localEnumeration = getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      if (localNote.getRhythmValue() > d) {
        d = localNote.getRhythmValue();
      }
    }
    return d;
  }
  
  public double getShortestRhythmValue()
  {
    double d = 1000.0D;
    Enumeration localEnumeration = getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      if (localNote.getRhythmValue() < d) {
        d = localNote.getRhythmValue();
      }
    }
    return d;
  }
  
  public void setDynamic(int paramInt)
  {
    Enumeration localEnumeration = getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      localNote.setDynamic(paramInt);
    }
  }
  
  public void setPitch(int paramInt)
  {
    Enumeration localEnumeration = getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      localNote.setPitch(paramInt);
    }
  }
  
  public void setRhythmValue(double paramDouble)
  {
    Enumeration localEnumeration = getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      localNote.setRhythmValue(paramDouble);
    }
  }
  
  public void setDuration(double paramDouble)
  {
    Enumeration localEnumeration = getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      localNote.setDuration(paramDouble);
    }
  }
  
  public double getBeatLength()
  {
    return getEndTime();
  }
  
  public Note createNote()
  {
    Note localNote = new Note();
    addNote(localNote);
    return localNote;
  }
  
  public void setNote(Note paramNote, int paramInt)
  {
    if (paramInt >= getSize())
    {
      System.out.println("jMusic error: Phrase setNote index is too large.");
      return;
    }
    this.noteList.removeElementAt(paramInt);
    this.noteList.insertElementAt(paramNote, paramInt);
  }
  
  public void setMute(boolean paramBoolean)
  {
    this.mute = paramBoolean;
  }
  
  public boolean getMute()
  {
    return this.mute;
  }
  
  public void setLength(double paramDouble)
  {
    setRhythmValue(paramDouble);
    setDuration(paramDouble * 0.9D);
  }
  
  public double getNoteStartTime(int paramInt)
  {
    if (paramInt >= size()) {
      return -1.0D;
    }
    double d = getStartTime();
    for (int i = 0; i < paramInt; i++) {
      d += getNote(i).getRhythmValue();
    }
    return d;
  }
  
  private final class Position
    implements Serializable
  {
    private double startTime = 0.0D;
    private final Phrase phrase;
    private boolean isAbsolute = false;
    private Position anchor;
    private Alignment alignment = Alignment.AFTER;
    private double offset;
    
    private Position(double paramDouble, Phrase paramPhrase)
    {
      this.isAbsolute = true;
      this.startTime = paramDouble;
      this.phrase = paramPhrase;
    }
    
    private Position(Position paramPosition, Alignment paramAlignment, double paramDouble, Phrase paramPhrase)
    {
      this.isAbsolute = false;
      this.anchor = paramPosition;
      this.alignment = paramAlignment;
      this.offset = paramDouble;
      this.phrase = paramPhrase;
    }
    
    private final Anchoring getAnchoring()
    {
      if (this.isAbsolute) {
        return null;
      }
      return new Anchoring(this.anchor.phrase, this.alignment, this.offset);
    }
    
    private final void setStartTime(double paramDouble)
    {
      this.isAbsolute = true;
      this.startTime = paramDouble;
    }
    
    private final double getStartTime()
    {
      if (this.isAbsolute) {
        return this.startTime;
      }
      return this.alignment.determineStartTime(this.phrase.getTotalDuration(), this.anchor.getStartTime(), this.anchor.getEndTime()) + this.offset;
    }
    
    private final double getEndTime()
    {
      return this.phrase.getEndTime();
    }
    
    private final Position copy(Phrase paramPhrase)
    {
      return this.isAbsolute ? new Position(this.startTime, paramPhrase) : new Position(this.anchor, this.alignment, this.offset, paramPhrase);
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\data\Phrase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */