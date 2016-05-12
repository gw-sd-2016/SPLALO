package jm.util;

import java.lang.reflect.Field;
import jm.constants.Pitches;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public class Convert
{
  public static final String DEFAULT_SEPARATOR = ",";
  public static final String LEFT_BRACKET = "[";
  public static final String RIGHT_BRACKET = "]";
  
  private Convert() {}
  
  public static Phrase pitchAndRhythmStringToPhrase(String paramString)
  {
    StringProcessor localStringProcessor = new StringProcessor(paramString);
    Phrase localPhrase = new Phrase();
    try
    {
      for (;;)
      {
        localPhrase.addNote(new Note((int)localStringProcessor.getNextRhythm(), localStringProcessor.getNextRhythm()));
      }
    }
    catch (EOSException localEOSException) {}
    return localPhrase;
  }
  
  public static String phraseToPitchAndRhythmString(Phrase paramPhrase)
  {
    Note[] arrayOfNote = paramPhrase.getNoteArray();
    StringBuffer localStringBuffer = new StringBuffer(arrayOfNote.length * 10);
    for (int i = 0; i < arrayOfNote.length - 1; i++)
    {
      localStringBuffer.append(arrayOfNote[i].getPitch());
      localStringBuffer.append(",");
      localStringBuffer.append(limitDecimalPlaces(arrayOfNote[i].getRhythmValue(), 3));
      localStringBuffer.append(",");
    }
    if (arrayOfNote.length > 0)
    {
      localStringBuffer.append(arrayOfNote[(arrayOfNote.length - 1)].getPitch());
      localStringBuffer.append(",");
      localStringBuffer.append(limitDecimalPlaces(arrayOfNote[(arrayOfNote.length - 1)].getRhythmValue(), 3));
    }
    return localStringBuffer.toString();
  }
  
  public static Phrase pitchRhythmAndDynamicStringToPhrase(String paramString)
  {
    StringProcessor localStringProcessor = new StringProcessor(paramString);
    Phrase localPhrase = new Phrase();
    try
    {
      for (;;)
      {
        localPhrase.addNote(new Note((int)localStringProcessor.getNextRhythm(), localStringProcessor.getNextRhythm(), (int)localStringProcessor.getNextRhythm()));
      }
    }
    catch (EOSException localEOSException) {}
    return localPhrase;
  }
  
  public static String phraseToPitchRhythmAndDynamicString(Phrase paramPhrase)
  {
    Note[] arrayOfNote = paramPhrase.getNoteArray();
    StringBuffer localStringBuffer = new StringBuffer(arrayOfNote.length * 12);
    for (int i = 0; i < arrayOfNote.length - 1; i++)
    {
      localStringBuffer.append("[");
      localStringBuffer.append(arrayOfNote[i].getPitch());
      localStringBuffer.append(",");
      localStringBuffer.append(limitDecimalPlaces(arrayOfNote[i].getRhythmValue(), 3));
      localStringBuffer.append(",");
      localStringBuffer.append(arrayOfNote[i].getDynamic());
      localStringBuffer.append("]");
      localStringBuffer.append(",");
    }
    if (arrayOfNote.length > 0)
    {
      localStringBuffer.append("[");
      localStringBuffer.append(arrayOfNote[(arrayOfNote.length - 1)].getPitch());
      localStringBuffer.append(",");
      localStringBuffer.append(limitDecimalPlaces(arrayOfNote[(arrayOfNote.length - 1)].getRhythmValue(), 3));
      localStringBuffer.append(",");
      localStringBuffer.append(arrayOfNote[(arrayOfNote.length - 1)].getDynamic());
      localStringBuffer.append("]");
    }
    return localStringBuffer.toString();
  }
  
  static String limitDecimalPlaces(double paramDouble, int paramInt)
  {
    String str = Double.toString(paramDouble);
    int i = str.lastIndexOf(".") + paramInt + 1;
    if (i > str.length()) {
      i = str.length();
    }
    return str.substring(0, i);
  }
  
  public static String scoreToXMLString(Score paramScore)
  {
    return XMLParser.scoreToXMLString(paramScore);
  }
  
  public static String partToXMLString(Part paramPart)
  {
    return XMLParser.partToXMLString(paramPart);
  }
  
  public static String phraseToXMLString(Phrase paramPhrase)
  {
    return XMLParser.phraseToXMLString(paramPhrase);
  }
  
  public static String noteToXMLString(Note paramNote)
  {
    return XMLParser.noteToXMLString(paramNote);
  }
  
  public static Score xmlStringToScore(String paramString)
    throws ConversionException
  {
    return XMLParser.xmlStringToScore(paramString);
  }
  
  public static Part xmlStringToPart(String paramString)
    throws ConversionException
  {
    return XMLParser.xmlStringToPart(paramString);
  }
  
  public static Phrase xmlStringToPhrase(String paramString)
    throws ConversionException
  {
    return XMLParser.xmlStringToPhrase(paramString);
  }
  
  public static Note xmlStringToNote(String paramString)
    throws ConversionException
  {
    return XMLParser.xmlStringToNote(paramString);
  }
  
  public static final float getFrequencyByMidiPitch(int paramInt)
  {
    float f = -1.0F;
    if ((paramInt >= 0) && (paramInt <= 127)) {
      f = (float)(6.875D * Math.pow(2.0D, (3 + paramInt) / 12.0D));
    }
    return f;
  }
  
  public static final int getMidiPitchByFrequency(float paramFloat)
  {
    float f = (float)Math.pow(2.0D, 0.0833333358168602D);
    if ((paramFloat < jm.constants.Frequencies.FRQ[0] / f) || (paramFloat > jm.constants.Frequencies.FRQ[127] * f)) {
      return -1;
    }
    int i = Math.round((float)(12.0D * (Math.log(paramFloat / 6.875D) / Math.log(2.0D)) - 3.0D));
    return i;
  }
  
  public static final String getNameOfMidiPitch(int paramInt)
  {
    Field[] arrayOfField = Pitches.class.getFields();
    if (arrayOfField != null) {
      for (int i = 0; i < arrayOfField.length; i++)
      {
        Field localField = arrayOfField[i];
        try
        {
          if (localField.getInt(null) == paramInt) {
            return localField.getName();
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          return "";
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          return "";
        }
      }
    }
    return "";
  }
  
  private static class StringProcessor
  {
    private int i = 0;
    private String string;
    
    StringProcessor(String paramString)
    {
      this.string = paramString;
    }
    
    private int getNextPitch()
      throws ConversionException, Convert.EOSException
    {
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        while (!Character.isDigit(this.string.charAt(this.i++))) {}
        localStringBuffer.append(this.string.charAt(this.i - 1));
        while (Character.isDigit(this.string.charAt(this.i++))) {
          localStringBuffer.append(this.string.charAt(this.i - 1));
        }
        if (this.string.charAt(this.i - 1) == '.') {
          throw new ConversionException("Double value not expected");
        }
        return Integer.parseInt(localStringBuffer.toString());
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
        if (localStringBuffer.length() > 0) {
          return Integer.parseInt(localStringBuffer.toString());
        }
        throw new Convert.EOSException(null);
      }
    }
    
    private double getNextRhythm()
      throws Convert.EOSException
    {
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        while ((!Character.isDigit(this.string.charAt(this.i++))) && (this.string.charAt(this.i) != '.')) {}
        localStringBuffer.append(this.string.charAt(this.i - 1));
        while ((Character.isDigit(this.string.charAt(this.i))) || (this.string.charAt(this.i) == '.'))
        {
          localStringBuffer.append(this.string.charAt(this.i));
          this.i += 1;
        }
        return Double.valueOf(localStringBuffer.toString()).doubleValue();
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
        if (localStringBuffer.length() > 0) {
          return Double.valueOf(localStringBuffer.toString()).doubleValue();
        }
        throw new Convert.EOSException(null);
      }
    }
  }
  
  private static class EOSException
    extends Exception
  {
    private EOSException() {}
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\util\Convert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */