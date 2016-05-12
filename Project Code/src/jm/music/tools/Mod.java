package jm.music.tools;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;
import jm.JMC;
import jm.music.data.CPhrase;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public class Mod
  implements JMC
{
  private Mod() {}
  
  public static void append(Note paramNote1, Note paramNote2)
  {
    try
    {
      if ((paramNote1 == null) || (paramNote2 == null)) {
        new NullPointerException();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
    }
    paramNote1.setRhythmValue(paramNote1.getRhythmValue() + paramNote2.getRhythmValue());
    paramNote1.setDuration(paramNote1.getDuration() + paramNote2.getDuration());
  }
  
  public static void transpose(Note paramNote, int paramInt)
  {
    try
    {
      if (paramNote == null) {
        new NullPointerException();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
    }
    if ((!paramNote.getPitchType()) && (paramNote.getPitch() != Integer.MIN_VALUE)) {
      paramNote.setPitch(paramNote.getPitch() + paramInt);
    }
    if (paramNote.getPitchType() == true) {
      System.err.println("jMusic Mod transpose: No action taken - notes with frequency values cannot yet be transposed.");
    }
  }
  
  public static void transpose(Note paramNote, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    try
    {
      if (paramNote == null) {
        new NullPointerException();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
    }
    int i = paramNote.getPitch();
    if (i != Integer.MIN_VALUE)
    {
      int j = i / 12;
      int k = 0;
      Note localNote = paramNote.copy();
      while (!localNote.isScale(paramArrayOfInt))
      {
        localNote.setPitch(localNote.getPitch() - 1);
        k++;
      }
      int m = 0;
      for (int n = 0; n < paramArrayOfInt.length; n++) {
        if (i % 12 - k == paramArrayOfInt[n])
        {
          m = n;
          n = paramArrayOfInt.length;
        }
      }
      n = m + paramInt1;
      while (n >= paramArrayOfInt.length)
      {
        j++;
        n -= paramArrayOfInt.length;
      }
      while (n < 0)
      {
        j--;
        n += paramArrayOfInt.length;
      }
      paramNote.setPitch(paramArrayOfInt[n] + j * 12 + k);
    }
  }
  
  public static void quantizePitch(Note paramNote, int[] paramArrayOfInt, int paramInt)
  {
    while (!paramNote.isScale(paramArrayOfInt)) {
      paramNote.setPitch(paramNote.getPitch() - 1);
    }
  }
  
  public static final void crescendo(Phrase paramPhrase, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    double d1 = paramInt2 - paramInt1;
    double d2 = paramDouble2 - paramDouble1;
    if (d2 == 0.0D) {
      return;
    }
    double d3 = 0.0D;
    Vector localVector = paramPhrase.getNoteList();
    for (int i = 0; i < localVector.size(); i++)
    {
      Note localNote = (Note)localVector.elementAt(i);
      if (d3 >= paramDouble1) {
        localNote.setDynamic((int)((d3 - paramDouble1) / d2 * d1 + paramInt1));
      }
      d3 += localNote.getRhythmValue();
      if (d3 > paramDouble2) {
        break;
      }
    }
  }
  
  public static final void diminuendo(Phrase paramPhrase, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    crescendo(paramPhrase, paramDouble1, paramDouble2, paramInt1, paramInt2);
  }
  
  public static final void decrescendo(Phrase paramPhrase, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    crescendo(paramPhrase, paramDouble1, paramDouble2, paramInt1, paramInt2);
  }
  
  public static void transpose(Phrase paramPhrase, int paramInt)
  {
    try
    {
      if (paramPhrase == null) {
        new NullPointerException();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
    }
    Vector localVector = paramPhrase.getNoteList();
    Enumeration localEnumeration = localVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      if (localNote.getPitch() != Integer.MIN_VALUE) {
        localNote.setPitch(localNote.getPitch() + paramInt);
      }
    }
    paramPhrase.setNoteList(localVector);
  }
  
  public static void transpose(Phrase paramPhrase, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    try
    {
      if (paramPhrase == null) {
        new NullPointerException();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
    }
    int i = paramInt2 % 12;
    Vector localVector = paramPhrase.getNoteList();
    Enumeration localEnumeration = localVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      transpose(localNote, paramInt1, paramArrayOfInt, paramInt2);
    }
    paramPhrase.setNoteList(localVector);
  }
  
  public static void repeat(Phrase paramPhrase)
  {
    repeat(paramPhrase, 2);
  }
  
  public static void repeat(Phrase paramPhrase, int paramInt)
  {
    try
    {
      if (paramPhrase == null) {
        new NullPointerException();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
    }
    int i = paramPhrase.size();
    for (int j = 0; j < paramInt - 1; j++) {
      for (int k = 0; k < i; k++) {
        paramPhrase.addNote(paramPhrase.getNote(k).copy());
      }
    }
  }
  
  public static void repeat(Phrase paramPhrase, double paramDouble1, double paramDouble2)
  {
    repeat(paramPhrase, 2, paramDouble1, paramDouble2);
  }
  
  public static void repeat(Phrase paramPhrase, int paramInt, double paramDouble1, double paramDouble2)
  {
    if (paramPhrase == null)
    {
      System.err.println("phrase is null");
      return;
    }
    if (paramDouble1 >= paramDouble2)
    {
      System.err.println("startlocation is bigger or equal to end location");
      return;
    }
    if (paramInt < 2)
    {
      System.err.println("times is smaller than 2");
      return;
    }
    if (paramDouble1 < 0.0D)
    {
      System.err.println("startLoc is smaller than 0");
      return;
    }
    Phrase localPhrase1 = paramPhrase.copy(paramDouble1, paramDouble2);
    Phrase localPhrase2 = new Phrase();
    int i = 0;
    int j = 0;
    int k = 0;
    double d = paramPhrase.getStartTime() < 0.0D ? 0.0D : paramPhrase.getStartTime();
    for (int m = 0; (m < paramPhrase.size()) && (d + paramPhrase.getNote(m).getRhythmValue() <= paramDouble2); m++)
    {
      localPhrase2.addNote(paramPhrase.getNote(m));
      if ((d < paramDouble1) && (d + paramPhrase.getNote(m).getRhythmValue() > paramDouble1))
      {
        i = 1;
        j = m;
      }
      d += paramPhrase.getNote(m).getRhythmValue();
    }
    if ((m + 1 < paramPhrase.size()) && (d < paramDouble2) && (d + paramPhrase.getNote(m + 1).getRhythmValue() > paramDouble2))
    {
      k = 1;
      Note localNote = paramPhrase.getNote(m).copy();
      localNote.setDuration(localNote.getDuration() * paramDouble2 - d / localNote.getRhythmValue());
      localNote.setRhythmValue(paramDouble2 - d);
      localPhrase2.addNote(localNote);
    }
    int n = 0;
    for (int i1 = 0; i1 < paramInt - 1; i1++) {
      for (int i2 = 0; i2 < localPhrase1.size(); i2++) {
        if (n == 0) {
          localPhrase2.addNote(localPhrase1.getNote(i2));
        }
      }
    }
    if (k != 0) {
      localPhrase2.removeLastNote();
    }
    for (i1 = m; i1 < paramPhrase.size(); i1++) {
      localPhrase2.addNote(paramPhrase.getNote(i1));
    }
    paramPhrase.setNoteList(localPhrase2.getNoteList());
  }
  
  public static void increaseDynamic(Phrase paramPhrase, int paramInt)
  {
    try
    {
      if (paramPhrase == null) {
        new NullPointerException();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.toString();
      return;
    }
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      localNote.setDynamic(localNote.getDynamic() + paramInt);
    }
  }
  
  public static void fadeIn(Phrase paramPhrase, double paramDouble)
  {
    if ((paramPhrase == null) || (paramDouble <= 0.0D)) {
      return;
    }
    double d1 = 0.0D;
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while ((localEnumeration.hasMoreElements()) && (d1 <= paramDouble))
    {
      Note localNote = (Note)localEnumeration.nextElement();
      double d2 = d1 / paramDouble;
      int i = (int)(localNote.getDynamic() * d2);
      if (i == 0) {
        i = 1;
      }
      localNote.setDynamic(i);
      d1 += localNote.getRhythmValue();
    }
  }
  
  public static void fadeIn(Phrase paramPhrase, double paramDouble1, double paramDouble2)
  {
    if ((paramPhrase == null) || (paramDouble1 <= 0.0D) || (paramDouble2 < 0.0D)) {
      return;
    }
    double d1 = paramDouble2;
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while ((localEnumeration.hasMoreElements()) && (d1 < paramDouble1))
    {
      Note localNote = (Note)localEnumeration.nextElement();
      double d2 = d1 / paramDouble1;
      int i = (int)(localNote.getDynamic() * d2);
      if (i == 0) {
        i = 1;
      }
      localNote.setDynamic(i);
      d1 += localNote.getRhythmValue();
    }
  }
  
  public static void fadeOut(Phrase paramPhrase, double paramDouble)
  {
    if ((paramPhrase == null) || (paramDouble <= 0.0D)) {
      return;
    }
    double d1 = 0.0D;
    int i = paramPhrase.size() - 1;
    for (int j = 0; j <= i; j++)
    {
      Note localNote = (Note)paramPhrase.getNoteList().elementAt(i - j);
      if (d1 > paramDouble) {
        break;
      }
      double d2 = d1 / paramDouble;
      int k = (int)(localNote.getDynamic() * d2);
      if (k == 0) {
        k = 1;
      }
      localNote.setDynamic(k);
      d1 += localNote.getRhythmValue();
    }
  }
  
  public static void fadeOut(Phrase paramPhrase, double paramDouble1, double paramDouble2)
  {
    if ((paramPhrase == null) || (paramDouble1 <= 0.0D) || (paramDouble2 < 0.0D)) {
      return;
    }
    double d1 = paramDouble2;
    int i = paramPhrase.size() - 1;
    for (int j = 0; j <= i; j++)
    {
      Note localNote = (Note)paramPhrase.getNoteList().elementAt(i - j);
      if (d1 >= paramDouble1) {
        break;
      }
      double d2 = d1 / paramDouble1;
      int k = (int)(localNote.getDynamic() * d2);
      if (k == 0) {
        k = 1;
      }
      localNote.setDynamic(k);
      d1 += localNote.getRhythmValue();
    }
  }
  
  public static void compress(Phrase paramPhrase, double paramDouble)
  {
    if (paramPhrase == null) {
      return;
    }
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    int i = 0;
    int j = 127;
    Note localNote;
    int k;
    while (localEnumeration.hasMoreElements())
    {
      localNote = (Note)localEnumeration.nextElement();
      if (localNote.getPitch() != Integer.MIN_VALUE)
      {
        k = localNote.getDynamic();
        if (k > i) {
          i = k;
        }
        if (k < j) {
          j = k;
        }
      }
    }
    int m = (j + i) / 2;
    localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      localNote = (Note)localEnumeration.nextElement();
      k = (int)(m + (localNote.getDynamic() - m) * paramDouble);
      localNote.setDynamic(k);
    }
  }
  
  public static void append(Phrase paramPhrase1, Phrase paramPhrase2)
  {
    if ((paramPhrase1 == null) || (paramPhrase2 == null)) {
      return;
    }
    Enumeration localEnumeration = paramPhrase2.getNoteList().elements();
    while (localEnumeration.hasMoreElements()) {
      paramPhrase1.addNote(((Note)localEnumeration.nextElement()).copy());
    }
  }
  
  public static void quantize(Phrase paramPhrase, double paramDouble)
  {
    quantise(paramPhrase, paramDouble);
  }
  
  public static void quantise(Phrase paramPhrase, double paramDouble)
  {
    quantize(paramPhrase, paramDouble, CHROMATIC_SCALE, 0);
  }
  
  public static void quantize(Phrase paramPhrase, double paramDouble, int[] paramArrayOfInt, int paramInt)
  {
    if ((paramPhrase == null) || (paramDouble <= 0.0D) || (paramArrayOfInt == null) || (paramInt < 0)) {
      return;
    }
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      double d = localNote.getRhythmValue();
      localNote.setRhythmValue((int)Math.round(d / paramDouble) * paramDouble);
      quantizePitch(localNote, paramArrayOfInt, paramInt);
    }
  }
  
  public static void cycle(Phrase paramPhrase, int paramInt)
  {
    if (paramPhrase == null) {
      return;
    }
    int i = paramPhrase.size();
    if (paramInt <= i) {
      return;
    }
    Phrase localPhrase = new Phrase();
    for (int j = 0; j < paramInt; j++) {
      localPhrase.addNote(paramPhrase.getNote(j % i).copy());
    }
    paramPhrase.getNoteList().removeAllElements();
    Enumeration localEnumeration = localPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements()) {
      paramPhrase.getNoteList().addElement(localEnumeration.nextElement());
    }
  }
  
  public static void cycle(Phrase paramPhrase, double paramDouble)
  {
    if ((paramPhrase == null) || (paramDouble <= paramPhrase.getEndTime())) {
      return;
    }
    int i = paramPhrase.size();
    Phrase localPhrase = new Phrase();
    for (int j = 0; localPhrase.getEndTime() < paramDouble; j++) {
      localPhrase.addNote(paramPhrase.getNote(j % i).copy());
    }
    paramPhrase.getNoteList().removeAllElements();
    Enumeration localEnumeration = localPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements()) {
      paramPhrase.getNoteList().addElement(localEnumeration.nextElement());
    }
  }
  
  public static void shuffle(Phrase paramPhrase)
  {
    if (paramPhrase == null) {
      return;
    }
    Phrase localPhrase = new Phrase();
    localPhrase.addNote(paramPhrase.getNote((int)(Math.random() * paramPhrase.size())));
    int i = 0;
    while (i < paramPhrase.size() - 1)
    {
      Note localNote = paramPhrase.getNote((int)(Math.random() * paramPhrase.size()));
      int j = 1;
      for (int k = 0; k < localPhrase.size(); k++) {
        if (localNote == localPhrase.getNote(k)) {
          j = 0;
        }
      }
      if (j != 0)
      {
        localPhrase.addNote(localNote);
        i++;
      }
    }
    paramPhrase.getNoteList().removeAllElements();
    Enumeration localEnumeration = localPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements()) {
      paramPhrase.getNoteList().addElement(localEnumeration.nextElement());
    }
  }
  
  public static void palindrome(Phrase paramPhrase)
  {
    palindrome(paramPhrase, true);
  }
  
  public static void palindrome(Phrase paramPhrase, boolean paramBoolean)
  {
    if (paramPhrase == null) {
      return;
    }
    int i = paramBoolean ? paramPhrase.size() : paramPhrase.size() - 1;
    for (int j = i - 1; j >= 0; j--) {
      paramPhrase.addNote(paramPhrase.getNote(j));
    }
  }
  
  public static void rotate(Phrase paramPhrase)
  {
    rotate(paramPhrase, 1);
  }
  
  public static void rotate(Phrase paramPhrase, int paramInt)
  {
    if (paramPhrase == null) {
      return;
    }
    Vector localVector = paramPhrase.getNoteList();
    for (int i = 0; i < paramInt; i++)
    {
      localVector.insertElementAt(localVector.lastElement(), 0);
      localVector.removeElementAt(localVector.size() - 1);
    }
  }
  
  public static void retrograde(Phrase paramPhrase)
  {
    if (paramPhrase == null) {
      return;
    }
    Phrase localPhrase = new Phrase();
    for (int i = paramPhrase.size(); i > 0; i--) {
      localPhrase.addNote(paramPhrase.getNote(i - 1));
    }
    paramPhrase.getNoteList().removeAllElements();
    Enumeration localEnumeration = localPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements()) {
      paramPhrase.getNoteList().addElement(localEnumeration.nextElement());
    }
  }
  
  public static void inversion(Phrase paramPhrase)
  {
    if (paramPhrase == null) {
      return;
    }
    int i = 0;
    for (int j = Integer.MIN_VALUE; (i < paramPhrase.size()) && (j == Integer.MIN_VALUE); j = paramPhrase.getNote(i++).getPitch()) {}
    while (i < paramPhrase.size())
    {
      paramPhrase.getNote(i).setPitch(j - (paramPhrase.getNote(i).getPitch() - j));
      i++;
    }
  }
  
  public static void invert(Phrase paramPhrase)
  {
    if (paramPhrase == null) {
      return;
    }
    int i = 0;
    for (int j = Integer.MIN_VALUE; (i < paramPhrase.size()) && (j == Integer.MIN_VALUE); j = paramPhrase.getNote(i++).getPitch()) {}
    while (i < paramPhrase.size())
    {
      paramPhrase.getNote(i).setPitch(j - (paramPhrase.getNote(i).getPitch() - j));
      i++;
    }
  }
  
  public static void diatonicInvert(Phrase paramPhrase, int[] paramArrayOfInt, int paramInt)
  {
    if ((paramPhrase == null) || (paramInt < 0) || (paramInt > 11) || (paramArrayOfInt == null)) {
      return;
    }
    int i = 0;
    int j = Integer.MIN_VALUE;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    while ((i < paramPhrase.size()) && (j == Integer.MIN_VALUE))
    {
      j = paramPhrase.getNote(i++).getPitch();
      if (paramInt == 0) {
        k = j / 12;
      } else {
        k = (j - 12 + paramInt) / 12;
      }
      m = stepIs(j, paramInt, paramArrayOfInt);
    }
    while (i < paramPhrase.size())
    {
      for (int i6 = paramPhrase.getNote(i).getPitch(); i6 == Integer.MIN_VALUE; i6 = paramPhrase.getNote(i).getPitch()) {
        i++;
      }
      int i7 = 0;
      Note localNote = new Note(i6, 1.0D);
      while (!localNote.isScale(paramArrayOfInt))
      {
        localNote.setPitch(localNote.getPitch() - 1);
        i7++;
      }
      if (paramInt == 0) {
        i1 = i6 / 12;
      } else {
        i1 = (i6 - 12 + paramInt) / 12;
      }
      i2 = stepIs(i6, paramInt, paramArrayOfInt);
      i3 = i2 - m + paramArrayOfInt.length * (i1 - k);
      if (i3 < 0)
      {
        i4 = (m + i3 * -1) % paramArrayOfInt.length;
        i5 = k + (m + i3 * -1) / paramArrayOfInt.length;
      }
      else
      {
        i4 = (m - i3) % paramArrayOfInt.length;
        if (i4 < 0) {
          i4 = paramArrayOfInt.length + i4;
        }
        int i8 = m - i3;
        if (i8 >= 0) {
          i5 = k;
        } else {
          i5 = k + (i8 + 1) / paramArrayOfInt.length - 1;
        }
      }
      paramPhrase.getNote(i).setPitch(i5 * 12 + paramArrayOfInt[i4] - i7);
      i++;
    }
  }
  
  private static int stepIs(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    int i = -1;
    int j = -1;
    int k = 1000;
    for (int m = 0; m < paramArrayOfInt.length; m++) {
      if (paramInt1 % 12 == (paramArrayOfInt[m] + paramInt2) % 12)
      {
        i = m;
        k = 0;
      }
      else
      {
        int n = paramInt1 % 12 - (paramArrayOfInt[m] + paramInt2) % 12;
        if (Math.abs(k) > Math.abs(n))
        {
          k = n;
          j = m;
        }
      }
    }
    if (i > -1) {
      return i;
    }
    return j;
  }
  
  public static void changeLength(Phrase paramPhrase, double paramDouble)
  {
    if ((paramPhrase == null) || (paramDouble <= 0.0D)) {
      return;
    }
    double d = paramPhrase.getEndTime() - paramPhrase.getStartTime();
    elongate(paramPhrase, paramDouble / d);
  }
  
  public static void elongate(Phrase paramPhrase, double paramDouble)
  {
    if ((paramPhrase == null) || (paramDouble <= 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      localNote.setRhythmValue(localNote.getRhythmValue() * paramDouble);
      localNote.setDuration(localNote.getDuration() * paramDouble);
    }
  }
  
  public static void accents(Phrase paramPhrase, double paramDouble)
  {
    double[] arrayOfDouble = { 0.0D };
    accents(paramPhrase, paramDouble, arrayOfDouble);
  }
  
  public static void accent(Phrase paramPhrase, double paramDouble)
  {
    double[] arrayOfDouble = { 0.0D };
    accents(paramPhrase, paramDouble, arrayOfDouble);
  }
  
  public static void accents(Phrase paramPhrase, double paramDouble, double[] paramArrayOfDouble)
  {
    accents(paramPhrase, paramDouble, paramArrayOfDouble, 20);
  }
  
  public static void accent(Phrase paramPhrase, double paramDouble, double[] paramArrayOfDouble)
  {
    accents(paramPhrase, paramDouble, paramArrayOfDouble, 20);
  }
  
  public static void accents(Phrase paramPhrase, double paramDouble, double[] paramArrayOfDouble, int paramInt)
  {
    if ((paramPhrase == null) || (paramDouble <= 0.0D)) {
      return;
    }
    for (int i = 0; i < paramArrayOfDouble.length; i++) {
      if ((paramArrayOfDouble[i] < 0.0D) || (paramArrayOfDouble[i] >= paramDouble)) {
        return;
      }
    }
    double d = paramPhrase.getStartTime() < 0.0D ? 0.0D : paramPhrase.getStartTime();
    Vector localVector = paramPhrase.getNoteList();
    for (int j = 0; j < localVector.size(); j++)
    {
      Note localNote = (Note)localVector.elementAt(j);
      for (int k = 0; k < paramArrayOfDouble.length; k++) {
        if (d % paramDouble == paramArrayOfDouble[k])
        {
          int m = localNote.getDynamic();
          m += paramInt;
          localNote.setDynamic(m);
        }
      }
      d += localNote.getRhythmValue();
    }
  }
  
  public static void accent(Phrase paramPhrase, double paramDouble, double[] paramArrayOfDouble, int paramInt)
  {
    if ((paramPhrase == null) || (paramDouble <= 0.0D)) {
      return;
    }
    for (int i = 0; i < paramArrayOfDouble.length; i++) {
      if ((paramArrayOfDouble[i] < 0.0D) || (paramArrayOfDouble[i] >= paramDouble)) {
        return;
      }
    }
    double d = paramPhrase.getStartTime() < 0.0D ? 0.0D : paramPhrase.getStartTime();
    Vector localVector = paramPhrase.getNoteList();
    for (int j = 0; j < localVector.size(); j++)
    {
      Note localNote = (Note)localVector.elementAt(j);
      for (int k = 0; k < paramArrayOfDouble.length; k++) {
        if (d % paramDouble == paramArrayOfDouble[k])
        {
          int m = localNote.getDynamic();
          m += paramInt;
          localNote.setDynamic(m);
        }
      }
      d += localNote.getRhythmValue();
    }
  }
  
  public static void normalise(Phrase paramPhrase)
  {
    if (paramPhrase == null) {
      return;
    }
    int i = 0;
    Enumeration localEnumeration1 = paramPhrase.getNoteList().elements();
    while (localEnumeration1.hasMoreElements())
    {
      Note localNote1 = (Note)localEnumeration1.nextElement();
      if (localNote1.getDynamic() > i) {
        i = localNote1.getDynamic();
      }
    }
    if (i == 127) {
      return;
    }
    int j = 127 - i;
    Enumeration localEnumeration2 = paramPhrase.getNoteList().elements();
    while (localEnumeration2.hasMoreElements())
    {
      Note localNote2 = (Note)localEnumeration2.nextElement();
      localNote2.setDynamic(localNote2.getDynamic() + j);
    }
  }
  
  public static void shake(Phrase paramPhrase)
  {
    shake(paramPhrase, 20);
  }
  
  public static void shake(Phrase paramPhrase, int paramInt)
  {
    if (paramPhrase == null) {
      return;
    }
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      int i = localNote.getDynamic();
      int j;
      do
      {
        j = i + (int)(Math.random() * 2.0D * paramInt - paramInt);
      } while ((j < 0) || (j > 127));
      localNote.setDynamic(j);
    }
  }
  
  public static void mutate(Phrase paramPhrase)
  {
    mutate(paramPhrase, 1, 1, CHROMATIC_SCALE, paramPhrase.getLowestPitch(), paramPhrase.getHighestPitch(), new double[] { 0.25D, 0.5D, 1.0D, 1.5D, 2.0D });
  }
  
  public static void mutate(Phrase paramPhrase, int paramInt, int[] paramArrayOfInt)
  {
    mutate(paramPhrase, 1, 0, paramArrayOfInt, paramPhrase.getLowestPitch(), paramPhrase.getHighestPitch(), new double[0]);
  }
  
  public static void mutate(Phrase paramPhrase, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, double[] paramArrayOfDouble)
  {
    Note localNote;
    for (int i = 0; i < paramInt1; i++)
    {
      int j = (int)(Math.random() * (paramInt4 - paramInt3) + paramInt3);
      int k = (int)(Math.random() * paramPhrase.size());
      localNote = paramPhrase.getNote(k);
      localNote.setPitch(j);
      while (!localNote.isScale(paramArrayOfInt))
      {
        j = (int)(Math.random() * (paramInt4 - paramInt3) + paramInt3);
        k = (int)(Math.random() * paramPhrase.size());
        localNote = paramPhrase.getNote(k);
      }
    }
    for (i = 0; i < paramInt2; i++)
    {
      double d = paramArrayOfDouble[((int)(Math.random() * paramArrayOfDouble.length))];
      localNote = paramPhrase.getNote((int)(Math.random() * paramPhrase.size()));
      localNote.setRhythmValue(d);
      localNote.setDuration(d * 0.9D);
    }
  }
  
  public static void tiePitches(Phrase paramPhrase)
  {
    int i = 0;
    while (i < paramPhrase.size() - 1)
    {
      Note localNote1 = paramPhrase.getNote(i);
      Note localNote2 = paramPhrase.getNote(i + 1);
      if (localNote1.getPitch() == localNote2.getPitch())
      {
        localNote1.setRhythmValue(localNote1.getRhythmValue() + localNote2.getRhythmValue());
        localNote1.setDuration(localNote1.getDuration() + localNote2.getDuration());
        paramPhrase.removeNote(i + 1);
      }
      else
      {
        i++;
      }
    }
  }
  
  public static void tieRests(Phrase paramPhrase)
  {
    int i = 0;
    while (i < paramPhrase.size() - 1)
    {
      Note localNote1 = paramPhrase.getNote(i);
      Note localNote2 = paramPhrase.getNote(i + 1);
      if ((localNote1.getPitch() == Integer.MIN_VALUE) && (localNote2.getPitch() == Integer.MIN_VALUE))
      {
        localNote1.setRhythmValue(localNote1.getRhythmValue() + localNote2.getRhythmValue());
        localNote1.setDuration(localNote1.getDuration() + localNote2.getDuration());
        paramPhrase.removeNote(i + 1);
      }
      else
      {
        i++;
      }
    }
  }
  
  public static void fillRests(Phrase paramPhrase)
  {
    int i = 0;
    while (i < paramPhrase.size() - 1)
    {
      Note localNote1 = paramPhrase.getNote(i);
      Note localNote2 = paramPhrase.getNote(i + 1);
      if ((localNote1.getPitch() != Integer.MIN_VALUE) && (localNote2.getPitch() == Integer.MIN_VALUE))
      {
        localNote1.setRhythmValue(localNote1.getRhythmValue() + localNote2.getRhythmValue());
        localNote1.setDuration(localNote1.getDuration() + localNote2.getDuration());
        paramPhrase.removeNote(i + 1);
      }
      else
      {
        i++;
      }
    }
  }
  
  public static void spread(Phrase paramPhrase)
  {
    if (paramPhrase == null) {
      return;
    }
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      localNote.setPan(Math.random());
    }
  }
  
  public static void bounce(Phrase paramPhrase)
  {
    if (paramPhrase == null) {
      return;
    }
    int i = 1;
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      if (i != 0) {
        localNote.setPan(0.0D);
      } else {
        localNote.setPan(1.0D);
      }
      i = i == 0 ? 1 : 0;
    }
  }
  
  public static void varyLength(Phrase paramPhrase, double paramDouble1, double paramDouble2)
  {
    if ((paramPhrase == null) || (paramDouble2 < paramDouble1)) {
      return;
    }
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      double d = Math.random() * (paramDouble2 - paramDouble1) + paramDouble1;
      localNote.setDuration(d);
    }
  }
  
  public static void randomize(Phrase paramPhrase, int paramInt)
  {
    randomize(paramPhrase, paramInt, 0.0D);
  }
  
  public static void randomize(Phrase paramPhrase, int paramInt, double paramDouble)
  {
    randomize(paramPhrase, paramInt, paramDouble, 0);
  }
  
  public static void randomize(Phrase paramPhrase, int paramInt1, double paramDouble, int paramInt2)
  {
    if (paramPhrase == null) {
      return;
    }
    int i = 1;
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      if (paramInt1 > 0) {
        localNote.setPitch(localNote.getPitch() + (int)(Math.random() * (paramInt1 * 2) - paramInt1));
      }
      if (paramDouble > 0.0D)
      {
        double d = Math.random() * (paramDouble * 2.0D) - paramDouble;
        localNote.setRhythmValue(localNote.getRhythmValue() + d);
        localNote.setDuration(localNote.getDuration() + d);
      }
      if (paramInt2 > 0) {
        localNote.setDynamic(localNote.getDynamic() + (int)(Math.random() * (paramInt2 * 2) - paramInt2));
      }
    }
  }
  
  public static void slurUp(Phrase paramPhrase)
  {
    slurUp(paramPhrase, 2);
  }
  
  public static void slurDown(Phrase paramPhrase)
  {
    slurDown(paramPhrase, 2);
  }
  
  public static void slurUp(Phrase paramPhrase, int paramInt)
  {
    if ((paramPhrase == null) || (paramPhrase.size() < paramInt) || (paramInt < 2))
    {
      System.err.println("jMusic Mod.slurUp error: Arguments not valid.");
      return;
    }
    int i = 0;
    int j = paramPhrase.size() - paramInt;
    int k = 0;
    while (k < j)
    {
      for (int m = 0; m < paramInt - 1; m++) {
        if ((paramPhrase.getNote(k + m).getPitch() >= 0) && (paramPhrase.getNote(k + m).getPitch() < paramPhrase.getNote(k + m + 1).getPitch()))
        {
          i = 1;
        }
        else
        {
          i = 0;
          break;
        }
      }
      if (i != 0)
      {
        for (m = 0; m < paramInt - 1; m++) {
          paramPhrase.getNote(k + m).setDuration(paramPhrase.getNote(k + m).getRhythmValue());
        }
        k += paramInt - 1;
      }
      else
      {
        k++;
      }
      i = 0;
    }
  }
  
  public static void slurDown(Phrase paramPhrase, int paramInt)
  {
    if ((paramPhrase == null) || (paramPhrase.size() < paramInt) || (paramInt < 2))
    {
      System.err.println("jMusic Mod.slurDown error: Arguments not valid.");
      return;
    }
    int i = 0;
    int j = paramPhrase.size() - paramInt;
    int k = 0;
    while (k < j)
    {
      for (int m = 0; m < paramInt - 1; m++) {
        if ((paramPhrase.getNote(k + m).getPitch() >= 0) && (paramPhrase.getNote(k + m).getPitch() > paramPhrase.getNote(k + m + 1).getPitch()))
        {
          i = 1;
        }
        else
        {
          i = 0;
          break;
        }
      }
      if (i != 0)
      {
        for (m = 0; m < paramInt - 1; m++) {
          paramPhrase.getNote(k + m).setDuration(paramPhrase.getNote(k + m).getRhythmValue());
        }
        k += paramInt - 1;
      }
      else
      {
        k++;
      }
      i = 0;
    }
  }
  
  public static void increaseDuration(Phrase paramPhrase, double paramDouble)
  {
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      localNote.setDuration(localNote.getDuration() * paramDouble);
    }
  }
  
  public static void addToDuration(Phrase paramPhrase, double paramDouble)
  {
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      localNote.setDuration(localNote.getDuration() + paramDouble);
    }
  }
  
  public static void addToRhythmValue(Phrase paramPhrase, double paramDouble)
  {
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      localNote.setRhythmValue(localNote.getRhythmValue() + paramDouble);
    }
  }
  
  public static void addToLength(Phrase paramPhrase, double paramDouble)
  {
    Enumeration localEnumeration = paramPhrase.getNoteList().elements();
    double d = 0.0D;
    while (localEnumeration.hasMoreElements())
    {
      Note localNote = (Note)localEnumeration.nextElement();
      d = localNote.getRhythmValue() / localNote.getDuration();
      localNote.setRhythmValue(localNote.getRhythmValue() + paramDouble);
      localNote.setDuration(localNote.getRhythmValue() * d);
    }
  }
  
  public static void expandIntervals(Phrase paramPhrase, double paramDouble)
  {
    int i = paramPhrase.size();
    if (i < 2) {
      return;
    }
    Note localNote1 = paramPhrase.getNote(0);
    for (int j = 1; j < i; j++)
    {
      Note localNote2 = paramPhrase.getNote(j);
      int k = (int)((localNote2.getPitch() - localNote1.getPitch()) * paramDouble);
      localNote2.setPitch(localNote2.getPitch() + k);
    }
  }
  
  public static void transpose(CPhrase paramCPhrase, int paramInt)
  {
    if (paramCPhrase == null) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements()) {
      transpose((Phrase)localEnumeration.nextElement(), paramInt);
    }
  }
  
  public static void transpose(CPhrase paramCPhrase, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    if (paramCPhrase == null) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements()) {
      transpose((Phrase)localEnumeration.nextElement(), paramInt1, paramArrayOfInt, paramInt2);
    }
  }
  
  public static void repeat(CPhrase paramCPhrase)
  {
    repeat(paramCPhrase, 2);
  }
  
  public static void repeat(CPhrase paramCPhrase, int paramInt)
  {
    if (paramCPhrase == null) {
      return;
    }
    int i = paramCPhrase.getPhraseList().size();
    for (int j = 0; j < paramInt - 1; j++)
    {
      double d = paramCPhrase.getEndTime();
      for (int k = 0; k < i; k++)
      {
        Phrase localPhrase1 = (Phrase)paramCPhrase.getPhraseList().elementAt(k);
        Phrase localPhrase2 = localPhrase1.copy();
        localPhrase2.setStartTime(d + localPhrase1.getStartTime());
        paramCPhrase.addPhrase(localPhrase2);
      }
    }
  }
  
  public static void repeat(CPhrase paramCPhrase, double paramDouble1, double paramDouble2)
  {
    repeat(paramCPhrase, 2, paramDouble1, paramDouble2);
  }
  
  public static void repeat(CPhrase paramCPhrase, int paramInt, double paramDouble1, double paramDouble2)
  {
    if ((paramCPhrase == null) || (paramDouble1 >= paramDouble2) || (paramInt < 2)) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      repeat(localPhrase, paramInt, paramDouble1 - paramCPhrase.getStartTime(), paramDouble2 - paramCPhrase.getStartTime());
    }
  }
  
  public static void fadeIn(CPhrase paramCPhrase, double paramDouble)
  {
    if ((paramCPhrase == null) || (paramDouble <= 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      fadeIn(localPhrase, paramDouble, localPhrase.getStartTime());
    }
  }
  
  public static void fadeIn(CPhrase paramCPhrase, double paramDouble1, double paramDouble2)
  {
    if ((paramCPhrase == null) || (paramDouble1 < 0.0D) || (paramDouble2 < 0.0D) || (paramDouble1 <= paramDouble2)) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      fadeIn(localPhrase, paramDouble1, paramDouble2 + localPhrase.getStartTime());
    }
  }
  
  public static void fadeOut(CPhrase paramCPhrase, double paramDouble)
  {
    if ((paramCPhrase == null) || (paramDouble <= 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      fadeOut(localPhrase, paramDouble, paramCPhrase.getEndTime() - localPhrase.getEndTime());
    }
  }
  
  public static void fadeOut(CPhrase paramCPhrase, double paramDouble1, double paramDouble2)
  {
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      fadeOut(localPhrase, paramDouble1, paramDouble2 + paramCPhrase.getEndTime() - localPhrase.getEndTime());
    }
  }
  
  public static void compress(CPhrase paramCPhrase, double paramDouble)
  {
    if (paramCPhrase == null) {
      return;
    }
    double d = 0.0D;
    int k = 0;
    Enumeration localEnumeration1 = paramCPhrase.getPhraseList().elements();
    Phrase localPhrase;
    Enumeration localEnumeration2;
    Note localNote;
    int i;
    while (localEnumeration1.hasMoreElements())
    {
      localPhrase = (Phrase)localEnumeration1.nextElement();
      if (localPhrase == null) {
        break;
      }
      localEnumeration2 = localPhrase.getNoteList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        localNote = (Note)localEnumeration2.nextElement();
        if (localNote.getPitch() != Integer.MIN_VALUE)
        {
          i = localNote.getDynamic();
          d += i;
          k++;
        }
      }
    }
    int j = (int)(d / k);
    localEnumeration1 = paramCPhrase.getPhraseList().elements();
    while (localEnumeration1.hasMoreElements())
    {
      localPhrase = (Phrase)localEnumeration1.nextElement();
      if (localPhrase == null) {
        break;
      }
      localEnumeration2 = localPhrase.getNoteList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        localNote = (Note)localEnumeration2.nextElement();
        System.out.println("note was =" + localNote.getDynamic());
        i = (int)(j + (localNote.getDynamic() - j) * paramDouble);
        localNote.setDynamic(i);
      }
    }
  }
  
  public static void append(CPhrase paramCPhrase1, CPhrase paramCPhrase2)
  {
    if ((paramCPhrase1 == null) || (paramCPhrase2 == null)) {
      return;
    }
    double d = paramCPhrase1.getEndTime();
    Enumeration localEnumeration = paramCPhrase2.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      localPhrase.setStartTime(d + localPhrase.getStartTime());
      paramCPhrase1.addPhrase(localPhrase);
    }
  }
  
  public static void merge(CPhrase paramCPhrase1, CPhrase paramCPhrase2)
  {
    if ((paramCPhrase1 == null) || (paramCPhrase2 == null)) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase2.getPhraseList().elements();
    while (localEnumeration.hasMoreElements()) {
      paramCPhrase1.addPhrase((Phrase)localEnumeration.nextElement());
    }
  }
  
  public static void quantize(CPhrase paramCPhrase, double paramDouble)
  {
    quantise(paramCPhrase, paramDouble);
  }
  
  public static void quantise(CPhrase paramCPhrase, double paramDouble)
  {
    quantize(paramCPhrase, paramDouble, CHROMATIC_SCALE, 0);
  }
  
  public static void quantize(CPhrase paramCPhrase, double paramDouble, int[] paramArrayOfInt, int paramInt)
  {
    if ((paramCPhrase == null) || (paramDouble <= 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements()) {
      quantize((Phrase)localEnumeration.nextElement(), paramDouble, paramArrayOfInt, paramInt);
    }
  }
  
  public static void shuffle(CPhrase paramCPhrase)
  {
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements()) {
      shuffle((Phrase)localEnumeration.nextElement());
    }
  }
  
  public static void cycle(Part paramPart, double paramDouble)
  {
    if ((paramPart == null) || (paramDouble <= 0.0D) || (paramDouble == paramPart.getEndTime())) {
      return;
    }
    double d1 = paramPart.getEndTime();
    if (paramDouble < d1)
    {
      Part localPart1 = paramPart.copy(0.0D, paramDouble);
      paramPart.empty();
      paramPart.addPhraseList(localPart1.getPhraseArray());
      return;
    }
    int i = 1;
    double d2 = paramDouble;
    for (d2 = paramDouble; (int)(d2 / d1) > 1; d2 -= d1)
    {
      Phrase[] arrayOfPhrase1 = paramPart.getPhraseArray();
      for (int j = 0; j < arrayOfPhrase1.length; j++)
      {
        arrayOfPhrase1[j].setStartTime(arrayOfPhrase1[j].getStartTime() + i * d1);
        paramPart.addPhrase(arrayOfPhrase1[j]);
      }
      i++;
    }
    double d3 = paramDouble - i * d1;
    if (d3 > 0.0D)
    {
      Part localPart2 = paramPart.copy(0.0D, d3, true, true, false);
      Phrase[] arrayOfPhrase2 = localPart2.getPhraseArray();
      for (int k = 0; k < arrayOfPhrase2.length; k++)
      {
        arrayOfPhrase2[k].setStartTime(arrayOfPhrase2[k].getStartTime() + i * d1);
        paramPart.addPhrase(arrayOfPhrase2[k]);
      }
    }
  }
  
  public static void elongate(CPhrase paramCPhrase, double paramDouble)
  {
    if ((paramCPhrase == null) || (paramDouble <= 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      elongate(localPhrase, paramDouble);
      localPhrase.setStartTime(localPhrase.getStartTime() * paramDouble);
    }
  }
  
  public static void accents(CPhrase paramCPhrase, double paramDouble)
  {
    double[] arrayOfDouble = { 0.0D };
    accents(paramCPhrase, paramDouble, arrayOfDouble);
  }
  
  public static void accents(CPhrase paramCPhrase, double paramDouble, double[] paramArrayOfDouble)
  {
    accents(paramCPhrase, paramDouble, paramArrayOfDouble, 20);
  }
  
  public static void accents(CPhrase paramCPhrase, double paramDouble, double[] paramArrayOfDouble, int paramInt)
  {
    if ((paramCPhrase == null) || (paramDouble <= 0.0D)) {
      return;
    }
    for (int i = 0; i < paramArrayOfDouble.length; i++) {
      if ((paramArrayOfDouble[i] < 0.0D) || (paramArrayOfDouble[i] >= paramDouble)) {
        return;
      }
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      accents(localPhrase, paramDouble, paramArrayOfDouble, paramInt);
    }
  }
  
  public static void normalise(CPhrase paramCPhrase)
  {
    if (paramCPhrase == null) {
      return;
    }
    int i = 0;
    Enumeration localEnumeration1 = paramCPhrase.getPhraseList().elements();
    Object localObject;
    while (localEnumeration1.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration1.nextElement();
      localEnumeration2 = localPhrase.getNoteList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        localObject = (Note)localEnumeration2.nextElement();
        if (((Note)localObject).getDynamic() > i) {
          i = ((Note)localObject).getDynamic();
        }
      }
    }
    if (i == 127) {
      return;
    }
    int j = 127 - i;
    Enumeration localEnumeration2 = paramCPhrase.getPhraseList().elements();
    while (localEnumeration2.hasMoreElements())
    {
      localObject = (Phrase)localEnumeration2.nextElement();
      Enumeration localEnumeration3 = ((Phrase)localObject).getNoteList().elements();
      while (localEnumeration3.hasMoreElements())
      {
        Note localNote = (Note)localEnumeration3.nextElement();
        localNote.setDynamic(localNote.getDynamic() + j);
      }
    }
  }
  
  public static void spread(CPhrase paramCPhrase)
  {
    if (paramCPhrase == null) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      spread(localPhrase);
    }
  }
  
  public static void bounce(CPhrase paramCPhrase)
  {
    if (paramCPhrase == null) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      bounce(localPhrase);
    }
  }
  
  public static void tiePitches(CPhrase paramCPhrase)
  {
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      tiePitches(localPhrase);
    }
  }
  
  public static void tieRests(CPhrase paramCPhrase)
  {
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      tieRests(localPhrase);
    }
  }
  
  public static void fillRests(CPhrase paramCPhrase)
  {
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      fillRests(localPhrase);
    }
  }
  
  public static void varyLength(CPhrase paramCPhrase, double paramDouble1, double paramDouble2)
  {
    if ((paramCPhrase == null) || (paramDouble2 < paramDouble1)) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      varyLength(localPhrase, paramDouble1, paramDouble2);
    }
  }
  
  public static void randomize(CPhrase paramCPhrase, int paramInt)
  {
    randomize(paramCPhrase, paramInt, 0.0D);
  }
  
  public static void randomize(CPhrase paramCPhrase, int paramInt, double paramDouble)
  {
    randomize(paramCPhrase, paramInt, paramDouble, 0);
  }
  
  public static void randomize(CPhrase paramCPhrase, int paramInt1, double paramDouble, int paramInt2)
  {
    if (paramCPhrase == null) {
      return;
    }
    int i = 1;
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      randomize(localPhrase, paramInt1, paramDouble, paramInt2);
    }
  }
  
  public static void slurUp(CPhrase paramCPhrase, int paramInt)
  {
    if (paramCPhrase == null) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      slurUp(localPhrase, paramInt);
    }
  }
  
  public static void slurDown(CPhrase paramCPhrase, int paramInt)
  {
    if (paramCPhrase == null) {
      return;
    }
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      slurDown(localPhrase, paramInt);
    }
  }
  
  public static void increaseDuration(CPhrase paramCPhrase, double paramDouble)
  {
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      increaseDuration(localPhrase, paramDouble);
    }
  }
  
  public static void addToDuration(CPhrase paramCPhrase, double paramDouble)
  {
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      addToDuration(localPhrase, paramDouble);
    }
  }
  
  public static void addToRhythmValue(CPhrase paramCPhrase, double paramDouble)
  {
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      addToRhythmValue(localPhrase, paramDouble);
    }
  }
  
  public static void addToLength(CPhrase paramCPhrase, double paramDouble)
  {
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      addToLength(localPhrase, paramDouble);
    }
  }
  
  public static void expandIntervals(CPhrase paramCPhrase, double paramDouble)
  {
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      expandIntervals(localPhrase, paramDouble);
    }
  }
  
  public static void shake(CPhrase paramCPhrase, int paramInt)
  {
    Enumeration localEnumeration = paramCPhrase.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      shake(localPhrase, paramInt);
    }
  }
  
  public static void repeat(Part paramPart)
  {
    repeat(paramPart, 2);
  }
  
  public static void repeat(Part paramPart, int paramInt)
  {
    if (paramPart == null) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      repeat(localPhrase, paramInt);
    }
  }
  
  public static void repeat(Part paramPart, double paramDouble1, double paramDouble2)
  {
    repeat(paramPart, 2, paramDouble1, paramDouble2);
  }
  
  public static void repeat(Part paramPart, int paramInt, double paramDouble1, double paramDouble2)
  {
    if ((paramPart == null) || (paramDouble1 >= paramDouble2) || (paramInt < 2)) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      repeat(localPhrase, paramInt, paramDouble1, paramDouble2);
    }
  }
  
  public static void transpose(Part paramPart, int paramInt)
  {
    if ((paramPart == null) || (paramInt == 0)) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      transpose(localPhrase, paramInt);
    }
  }
  
  public static void transpose(Part paramPart, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    if (paramPart == null) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements()) {
      transpose((Phrase)localEnumeration.nextElement(), paramInt1, paramArrayOfInt, paramInt2);
    }
  }
  
  public static void compress(Part paramPart, double paramDouble)
  {
    if (paramPart == null) {
      return;
    }
    double d = 0.0D;
    int k = 0;
    Enumeration localEnumeration1 = paramPart.getPhraseList().elements();
    Phrase localPhrase;
    Enumeration localEnumeration2;
    Note localNote;
    int i;
    while (localEnumeration1.hasMoreElements())
    {
      localPhrase = (Phrase)localEnumeration1.nextElement();
      if (localPhrase == null) {
        break;
      }
      localEnumeration2 = localPhrase.getNoteList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        localNote = (Note)localEnumeration2.nextElement();
        if (localNote.getPitch() != Integer.MIN_VALUE)
        {
          i = localNote.getDynamic();
          d += i;
          k++;
        }
      }
    }
    int j = (int)(d / k);
    localEnumeration1 = paramPart.getPhraseList().elements();
    while (localEnumeration1.hasMoreElements())
    {
      localPhrase = (Phrase)localEnumeration1.nextElement();
      if (localPhrase == null) {
        break;
      }
      localEnumeration2 = localPhrase.getNoteList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        localNote = (Note)localEnumeration2.nextElement();
        System.out.println("note was =" + localNote.getDynamic());
        i = (int)(j + (localNote.getDynamic() - j) * paramDouble);
        localNote.setDynamic(i);
      }
    }
  }
  
  public static void append(Part paramPart1, Part paramPart2)
  {
    append(paramPart1, paramPart2, paramPart1.getEndTime());
  }
  
  public static void append(Part paramPart1, Part paramPart2, double paramDouble)
  {
    if ((paramPart1 == null) || (paramPart2 == null)) {
      return;
    }
    Enumeration localEnumeration = paramPart2.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = ((Phrase)localEnumeration.nextElement()).copy();
      localPhrase.setStartTime(paramDouble + localPhrase.getStartTime());
      if (localPhrase.getInstrument() == paramPart1.getInstrument()) {
        localPhrase.setInstrument(-1);
      }
      paramPart1.addPhrase(localPhrase);
    }
  }
  
  public static void increaseDynamic(Part paramPart, int paramInt)
  {
    try
    {
      if (paramPart == null) {
        new NullPointerException();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.toString();
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      increaseDynamic(localPhrase, paramInt);
    }
  }
  
  public static void fadeIn(Part paramPart, double paramDouble)
  {
    if ((paramPart == null) || (paramDouble <= 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      fadeIn(localPhrase, paramDouble, localPhrase.getStartTime());
    }
  }
  
  public static void fadeIn(Part paramPart, double paramDouble1, double paramDouble2)
  {
    if ((paramPart == null) || (paramDouble1 <= 0.0D) || (paramDouble2 < 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      fadeIn(localPhrase, paramDouble1, paramDouble2 + localPhrase.getStartTime());
    }
  }
  
  public static void fadeOut(Part paramPart, double paramDouble)
  {
    if ((paramPart == null) || (paramDouble <= 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      fadeOut(localPhrase, paramDouble, paramPart.getEndTime() - localPhrase.getEndTime());
    }
  }
  
  public static void fadeOut(Part paramPart, double paramDouble1, double paramDouble2)
  {
    if ((paramPart == null) || (paramDouble1 <= 0.0D) || (paramDouble2 < 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      fadeOut(localPhrase, paramDouble1, paramDouble2 + paramPart.getEndTime() - localPhrase.getEndTime());
    }
  }
  
  public static void merge(Part paramPart1, Part paramPart2)
  {
    if ((paramPart1 == null) || (paramPart2 == null)) {
      return;
    }
    Enumeration localEnumeration = paramPart2.getPhraseList().elements();
    while (localEnumeration.hasMoreElements()) {
      paramPart1.addPhrase((Phrase)localEnumeration.nextElement());
    }
  }
  
  public static void quantize(Part paramPart, double paramDouble)
  {
    quantise(paramPart, paramDouble);
  }
  
  public static void quantise(Part paramPart, double paramDouble)
  {
    quantize(paramPart, paramDouble, CHROMATIC_SCALE, 0);
  }
  
  public static void quantize(Part paramPart, double paramDouble, int[] paramArrayOfInt, int paramInt)
  {
    if ((paramPart == null) || (paramDouble <= 0.0D) || (paramArrayOfInt == null) || (paramInt < 0)) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      quantize(localPhrase, paramDouble, paramArrayOfInt, paramInt);
    }
  }
  
  public static void shuffle(Part paramPart)
  {
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements()) {
      shuffle((Phrase)localEnumeration.nextElement());
    }
  }
  
  public static void accents(Part paramPart, double paramDouble)
  {
    double[] arrayOfDouble = { 0.0D };
    accents(paramPart, paramDouble, arrayOfDouble);
  }
  
  public static void accents(Part paramPart, double paramDouble, double[] paramArrayOfDouble)
  {
    accents(paramPart, paramDouble, paramArrayOfDouble, 20);
  }
  
  public static void accents(Part paramPart, double paramDouble, double[] paramArrayOfDouble, int paramInt)
  {
    if ((paramPart == null) || (paramDouble <= 0.0D)) {
      return;
    }
    for (int i = 0; i < paramArrayOfDouble.length; i++) {
      if ((paramArrayOfDouble[i] < 0.0D) || (paramArrayOfDouble[i] >= paramDouble)) {
        return;
      }
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      accents(localPhrase, paramDouble, paramArrayOfDouble, paramInt);
    }
  }
  
  public static void normalise(Part paramPart)
  {
    if (paramPart == null) {
      return;
    }
    int i = 0;
    Enumeration localEnumeration1 = paramPart.getPhraseList().elements();
    Object localObject;
    while (localEnumeration1.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration1.nextElement();
      localEnumeration2 = localPhrase.getNoteList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        localObject = (Note)localEnumeration2.nextElement();
        if (((Note)localObject).getDynamic() > i) {
          i = ((Note)localObject).getDynamic();
        }
      }
    }
    if (i == 127) {
      return;
    }
    int j = 127 - i;
    Enumeration localEnumeration2 = paramPart.getPhraseList().elements();
    while (localEnumeration2.hasMoreElements())
    {
      localObject = (Phrase)localEnumeration2.nextElement();
      Enumeration localEnumeration3 = ((Phrase)localObject).getNoteList().elements();
      while (localEnumeration3.hasMoreElements())
      {
        Note localNote = (Note)localEnumeration3.nextElement();
        localNote.setDynamic(localNote.getDynamic() + j);
      }
    }
  }
  
  public static void elongate(Part paramPart, double paramDouble)
  {
    if ((paramPart == null) || (paramDouble <= 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      elongate(localPhrase, paramDouble);
    }
  }
  
  public static void tiePitches(Part paramPart)
  {
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      tiePitches(localPhrase);
    }
  }
  
  public static void tieRests(Part paramPart)
  {
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      tieRests(localPhrase);
    }
  }
  
  public static void fillRests(Part paramPart)
  {
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      fillRests(localPhrase);
    }
  }
  
  public static void spread(Part paramPart)
  {
    if (paramPart == null) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      spread(localPhrase);
    }
  }
  
  public static void bounce(Part paramPart)
  {
    if (paramPart == null) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      bounce(localPhrase);
    }
  }
  
  public static void varyLength(Part paramPart, double paramDouble1, double paramDouble2)
  {
    if ((paramPart == null) || (paramDouble2 < paramDouble1)) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      varyLength(localPhrase, paramDouble1, paramDouble2);
    }
  }
  
  public static void randomize(Part paramPart, int paramInt)
  {
    randomize(paramPart, paramInt, 0.0D);
  }
  
  public static void randomize(Part paramPart, int paramInt, double paramDouble)
  {
    randomize(paramPart, paramInt, paramDouble, 0);
  }
  
  public static void randomize(Part paramPart, int paramInt1, double paramDouble, int paramInt2)
  {
    if (paramPart == null) {
      return;
    }
    int i = 1;
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      randomize(localPhrase, paramInt1, paramDouble, paramInt2);
    }
  }
  
  public static void slurUp(Part paramPart, int paramInt)
  {
    if (paramPart == null) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      slurUp(localPhrase, paramInt);
    }
  }
  
  public static void slurDown(Part paramPart, int paramInt)
  {
    if (paramPart == null) {
      return;
    }
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      slurDown(localPhrase, paramInt);
    }
  }
  
  public static void increaseDuration(Part paramPart, double paramDouble)
  {
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      increaseDuration(localPhrase, paramDouble);
    }
  }
  
  public static void addToDuration(Part paramPart, double paramDouble)
  {
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      addToDuration(localPhrase, paramDouble);
    }
  }
  
  public static void addToRhythmValue(Part paramPart, double paramDouble)
  {
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      addToRhythmValue(localPhrase, paramDouble);
    }
  }
  
  public static void addToLength(Part paramPart, double paramDouble)
  {
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      addToLength(localPhrase, paramDouble);
    }
  }
  
  public static void expandIntervals(Part paramPart, double paramDouble)
  {
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      expandIntervals(localPhrase, paramDouble);
    }
  }
  
  public static void shake(Part paramPart, int paramInt)
  {
    Enumeration localEnumeration = paramPart.getPhraseList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Phrase localPhrase = (Phrase)localEnumeration.nextElement();
      shake(localPhrase, paramInt);
    }
  }
  
  public static void consolidate(Part paramPart)
  {
    Phrase[] arrayOfPhrase = paramPart.getPhraseArray();
    if (arrayOfPhrase.length < 2) {
      return;
    }
    Phrase localPhrase1 = new Phrase(arrayOfPhrase[0].getStartTime());
    int i = 0;
    double d2;
    for (double d1 = arrayOfPhrase[0].getStartTime(); i == 0; d1 = d2)
    {
      d2 = Double.POSITIVE_INFINITY;
      Phrase localPhrase2 = null;
      for (int j = 0; j < arrayOfPhrase.length; j++) {
        if ((arrayOfPhrase[j].getSize() > 0) && (arrayOfPhrase[j].getStartTime() < d2))
        {
          d2 = arrayOfPhrase[j].getStartTime();
          localPhrase2 = arrayOfPhrase[j];
        }
      }
      if (localPhrase2 == null)
      {
        i = 1;
        break;
      }
      Note localNote = localPhrase2.getNote(0);
      if (!localNote.isRest())
      {
        if (localPhrase1.getSize() > 0) {
          localPhrase1.getNote(localPhrase1.getSize() - 1).setRhythmValue((int)((d2 - d1) * 100000.0D + 0.5D) / 100000.0D);
        } else {
          localPhrase1.setStartTime(d2);
        }
        localPhrase1.addNote(localNote);
      }
      localPhrase2.setStartTime((int)((d2 + localNote.getRhythmValue()) * 100000.0D + 0.5D) / 100000.0D);
      localPhrase2.removeNote(0);
    }
    paramPart.empty();
    paramPart.addPhrase(localPhrase1);
  }
  
  public static void transpose(Score paramScore, int paramInt)
  {
    if ((paramScore == null) || (paramInt == 0)) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      transpose(localPart, paramInt);
    }
  }
  
  public static void transpose(Score paramScore, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    if (paramScore == null) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements()) {
      transpose((Part)localEnumeration.nextElement(), paramInt1, paramArrayOfInt, paramInt2);
    }
  }
  
  public static void fadeIn(Score paramScore, double paramDouble)
  {
    if ((paramScore == null) || (paramDouble <= 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      fadeIn(localPart, paramDouble);
    }
  }
  
  public static void increaseDynamic(Score paramScore, int paramInt)
  {
    try
    {
      if (paramScore == null) {
        new NullPointerException();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.toString();
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      increaseDynamic(localPart, paramInt);
    }
  }
  
  public static void fadeOut(Score paramScore, double paramDouble)
  {
    if ((paramScore == null) || (paramDouble <= 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      fadeOut(localPart, paramDouble, paramScore.getEndTime() - localPart.getEndTime());
    }
  }
  
  public static void compress(Score paramScore, double paramDouble)
  {
    if (paramScore == null) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      compress(localPart, paramDouble);
    }
  }
  
  public static void repeat(Score paramScore)
  {
    repeat(paramScore, 2);
  }
  
  public static void repeat(Score paramScore, int paramInt)
  {
    if ((paramScore == null) || (paramInt < 2)) {
      return;
    }
    double d1 = 0.0D;
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart1 = (Part)localEnumeration.nextElement();
      if (d1 < localPart1.getEndTime()) {
        d1 = localPart1.getEndTime();
      }
    }
    for (int i = 0; i < paramScore.getPartList().size(); i++)
    {
      Part localPart2 = (Part)paramScore.getPartList().elementAt(i);
      int j = localPart2.getPhraseList().size();
      for (int k = 0; k < paramInt - 1; k++)
      {
        double d2 = d1 * (k + 1);
        for (int m = 0; m < j; m++)
        {
          Phrase localPhrase1 = (Phrase)localPart2.getPhraseList().elementAt(m);
          Phrase localPhrase2 = localPhrase1.copy();
          localPhrase2.setStartTime(d2 + localPhrase1.getStartTime());
          localPart2.addPhrase(localPhrase2);
        }
      }
    }
  }
  
  public static void append(Score paramScore1, Score paramScore2)
  {
    if ((paramScore1 == null) || (paramScore2 == null)) {
      return;
    }
    paramScore2.clean();
    if (paramScore2.size() == 0) {
      return;
    }
    double d = paramScore1.getEndTime();
    Enumeration localEnumeration1 = paramScore2.getPartList().elements();
    while (localEnumeration1.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration1.nextElement();
      Enumeration localEnumeration2 = localPart.getPhraseList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        Phrase localPhrase = (Phrase)localEnumeration2.nextElement();
        localPhrase.setStartTime(localPhrase.getStartTime() + d);
        if ((localPhrase.getInstrument() != 250) && (localPhrase.getInstrument() != localPart.getInstrument())) {
          localPhrase.setInstrument(localPart.getInstrument());
        }
        if (localPhrase.getInstrument() == localPart.getInstrument()) {
          localPhrase.setInstrument(-1);
        }
      }
    }
    merge(paramScore1, paramScore2);
  }
  
  public static void merge(Score paramScore1, Score paramScore2)
  {
    if ((paramScore1 == null) || (paramScore2 == null)) {
      return;
    }
    int i = 0;
    int j = paramScore1.size();
    int k = paramScore2.size();
    for (int m = 0; m < k; m++)
    {
      Part localPart2 = paramScore2.getPart(m);
      int n = localPart2.getChannel();
      for (int i1 = 0; i1 < j; i1++)
      {
        Part localPart1 = paramScore1.getPart(i1);
        if (n == localPart1.getChannel())
        {
          int i2 = localPart2.size();
          for (int i3 = 0; i3 < i2; i3++) {
            localPart1.addPhrase(localPart2.getPhrase(i3));
          }
          i = 1;
          i1 = j;
        }
      }
      if (i == 0)
      {
        paramScore1.addPart(localPart2);
        i = 0;
      }
    }
  }
  
  public static void quantize(Score paramScore, double paramDouble)
  {
    quantise(paramScore, paramDouble);
  }
  
  public static void quantise(Score paramScore, double paramDouble)
  {
    quantize(paramScore, paramDouble, CHROMATIC_SCALE, 0);
  }
  
  public static void quantize(Score paramScore, double paramDouble, int[] paramArrayOfInt, int paramInt)
  {
    if ((paramScore == null) || (paramDouble <= 0.0D) || (paramArrayOfInt == null) || (paramInt < 0)) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      quantize(localPart, paramDouble, paramArrayOfInt, paramInt);
    }
  }
  
  public static void shuffle(Score paramScore)
  {
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements()) {
      shuffle((Part)localEnumeration.nextElement());
    }
  }
  
  public static void accents(Score paramScore, double paramDouble)
  {
    double[] arrayOfDouble = { 0.0D };
    accents(paramScore, paramDouble, arrayOfDouble);
  }
  
  public static void accents(Score paramScore, double paramDouble, double[] paramArrayOfDouble)
  {
    accents(paramScore, paramDouble, paramArrayOfDouble, 20);
  }
  
  public static void accents(Score paramScore, double paramDouble, double[] paramArrayOfDouble, int paramInt)
  {
    if ((paramScore == null) || (paramDouble <= 0.0D)) {
      return;
    }
    for (int i = 0; i < paramArrayOfDouble.length; i++) {
      if ((paramArrayOfDouble[i] < 0.0D) || (paramArrayOfDouble[i] >= paramDouble)) {
        return;
      }
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      accents(localPart, paramDouble, paramArrayOfDouble, paramInt);
    }
  }
  
  public static void normalise(Score paramScore)
  {
    if (paramScore == null) {
      return;
    }
    int i = 0;
    Enumeration localEnumeration1 = paramScore.getPartList().elements();
    Object localObject1;
    Enumeration localEnumeration3;
    Object localObject2;
    while (localEnumeration1.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration1.nextElement();
      localEnumeration2 = localPart.getPhraseList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        localObject1 = (Phrase)localEnumeration2.nextElement();
        localEnumeration3 = ((Phrase)localObject1).getNoteList().elements();
        while (localEnumeration3.hasMoreElements())
        {
          localObject2 = (Note)localEnumeration3.nextElement();
          if (((Note)localObject2).getDynamic() > i) {
            i = ((Note)localObject2).getDynamic();
          }
        }
      }
    }
    if (i == 127) {
      return;
    }
    int j = 127 - i;
    Enumeration localEnumeration2 = paramScore.getPartList().elements();
    while (localEnumeration2.hasMoreElements())
    {
      localObject1 = (Part)localEnumeration2.nextElement();
      localEnumeration3 = ((Part)localObject1).getPhraseList().elements();
      while (localEnumeration3.hasMoreElements())
      {
        localObject2 = (Phrase)localEnumeration3.nextElement();
        Enumeration localEnumeration4 = ((Phrase)localObject2).getNoteList().elements();
        while (localEnumeration4.hasMoreElements())
        {
          Note localNote = (Note)localEnumeration4.nextElement();
          localNote.setDynamic(localNote.getDynamic() + j);
        }
      }
    }
  }
  
  public static void consolidate(Score paramScore)
  {
    if (paramScore == null) {
      return;
    }
    for (int m = 0; m < paramScore.size(); m++)
    {
      Part localPart1 = paramScore.getPart(m);
      int i = localPart1.getChannel();
      int k = paramScore.size();
      for (int n = k - 1; n > m; n--)
      {
        Part localPart2 = paramScore.getPart(n);
        if (localPart2.getChannel() == i)
        {
          int j = localPart2.size();
          for (int i1 = 0; i1 < j; i1++)
          {
            Phrase localPhrase = localPart2.getPhrase(i1);
            localPhrase.setAppend(false);
            localPart1.addPhrase(localPhrase);
          }
          paramScore.removePart(n);
        }
      }
    }
  }
  
  public static void elongate(Score paramScore, double paramDouble)
  {
    if ((paramScore == null) || (paramDouble <= 0.0D)) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      elongate(localPart, paramDouble);
    }
  }
  
  public static void tiePitches(Score paramScore)
  {
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      tiePitches(localPart);
    }
  }
  
  public static void tieRests(Score paramScore)
  {
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      tieRests(localPart);
    }
  }
  
  public static void fillRests(Score paramScore)
  {
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      fillRests(localPart);
    }
  }
  
  public static void spread(Score paramScore)
  {
    if (paramScore == null) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      spread(localPart);
    }
  }
  
  public static void bounce(Score paramScore)
  {
    if (paramScore == null) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      bounce(localPart);
    }
  }
  
  public static void varyLength(Score paramScore, double paramDouble1, double paramDouble2)
  {
    if ((paramScore == null) || (paramDouble2 < paramDouble1)) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      varyLength(localPart, paramDouble1, paramDouble2);
    }
  }
  
  public static void randomize(Score paramScore, int paramInt)
  {
    randomize(paramScore, paramInt, 0.0D);
  }
  
  public static void randomize(Score paramScore, int paramInt, double paramDouble)
  {
    randomize(paramScore, paramInt, paramDouble, 0);
  }
  
  public static void randomize(Score paramScore, int paramInt1, double paramDouble, int paramInt2)
  {
    if (paramScore == null) {
      return;
    }
    int i = 1;
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      randomize(localPart, paramInt1, paramDouble, paramInt2);
    }
  }
  
  public static void slurUp(Score paramScore, int paramInt)
  {
    if (paramScore == null) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      slurUp(localPart, paramInt);
    }
  }
  
  public static void slurDown(Score paramScore, int paramInt)
  {
    if (paramScore == null) {
      return;
    }
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      slurDown(localPart, paramInt);
    }
  }
  
  public static void increaseDuration(Score paramScore, double paramDouble)
  {
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      increaseDuration(localPart, paramDouble);
    }
  }
  
  public static void addToDuration(Score paramScore, double paramDouble)
  {
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      addToDuration(localPart, paramDouble);
    }
  }
  
  public static void addToRhythmValue(Score paramScore, double paramDouble)
  {
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      addToRhythmValue(localPart, paramDouble);
    }
  }
  
  public static void addToLength(Score paramScore, double paramDouble)
  {
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      addToLength(localPart, paramDouble);
    }
  }
  
  public static void expandIntervals(Score paramScore, double paramDouble)
  {
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      expandIntervals(localPart, paramDouble);
    }
  }
  
  public static void shake(Score paramScore, int paramInt)
  {
    Enumeration localEnumeration = paramScore.getPartList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration.nextElement();
      shake(localPart, paramInt);
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\Mod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */