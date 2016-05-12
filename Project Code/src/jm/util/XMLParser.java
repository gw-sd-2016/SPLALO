package jm.util;

import java.util.Vector;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

class XMLParser
{
  private static final XMLStyle DEFAULT_XML_STYLE = new StandardXMLStyle();
  
  private XMLParser() {}
  
  public static String scoreToXMLString(Score paramScore)
  {
    return DEFAULT_XML_STYLE.initialXMLDeclaration() + scoreToXMLString(paramScore, DEFAULT_XML_STYLE);
  }
  
  private static String scoreToXMLString(Score paramScore, XMLStyle paramXMLStyle)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramXMLStyle.getLeftAngleBracket() + paramXMLStyle.getScoreTagName());
    int j;
    if (!paramScore.getTitle().equals("Untitled Score"))
    {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getTitleAttributeName() + "=" + paramXMLStyle.getDoubleQuote());
      String str = paramScore.getTitle();
      for (j = 0; j < str.length(); j++)
      {
        char c = str.charAt(j);
        if (c == ' ') {
          localStringBuffer.append(paramXMLStyle.getSpace());
        } else if (c == '/') {
          localStringBuffer.append(paramXMLStyle.getSlash());
        } else if (c == '&') {
          localStringBuffer.append(paramXMLStyle.getAmpersandInString());
        } else if (c == '<') {
          localStringBuffer.append(paramXMLStyle.getLeftAngleBracketInString());
        } else if (c == '>') {
          localStringBuffer.append(paramXMLStyle.getRightAngleBracketInString());
        } else if (c == '"') {
          localStringBuffer.append(paramXMLStyle.getDoubleQuoteInString());
        } else if (c == '#') {
          localStringBuffer.append(paramXMLStyle.getHash());
        } else if (c == '/') {
          localStringBuffer.append(paramXMLStyle.getSlash());
        } else if (c == '?') {
          localStringBuffer.append(paramXMLStyle.getQuestionMark());
        } else if (c == ';') {
          localStringBuffer.append(paramXMLStyle.getSemicolon());
        } else {
          localStringBuffer.append(c);
        }
      }
      localStringBuffer.append(paramXMLStyle.getDoubleQuote());
    }
    if (paramScore.getTempo() != 60.0D) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getTempoAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramXMLStyle.limitDecimalPlaces() ? limitDecimalPlaces(paramScore.getTempo(), 2) : Double.toString(paramScore.getTempo())) + paramXMLStyle.getDoubleQuote());
    }
    if (paramScore.getKeySignature() != 0) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getKeySignatureAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramScore.getKeySignature()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramScore.getKeyQuality() != 0) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getKeyQualityAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramScore.getKeyQuality()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramScore.getNumerator() != 4) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getNumeratorAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramScore.getNumerator()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramScore.getDenominator() != 4) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getDenominatorAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramScore.getDenominator()) + paramXMLStyle.getDoubleQuote());
    }
    int i = paramScore.size();
    if (i == 0)
    {
      localStringBuffer.append(paramXMLStyle.getSlash() + paramXMLStyle.getRightAngleBracket());
    }
    else
    {
      localStringBuffer.append(paramXMLStyle.getRightAngleBracket());
      for (j = 0; j < paramScore.size(); j++) {
        localStringBuffer.append(partToXMLString(paramScore.getPart(j), paramXMLStyle));
      }
      localStringBuffer.append(paramXMLStyle.getLeftAngleBracket() + paramXMLStyle.getSlash() + paramXMLStyle.getScoreTagName() + paramXMLStyle.getRightAngleBracket());
    }
    return localStringBuffer.toString();
  }
  
  public static String partToXMLString(Part paramPart)
  {
    return DEFAULT_XML_STYLE.initialXMLDeclaration() + partToXMLString(paramPart, DEFAULT_XML_STYLE);
  }
  
  private static String partToXMLString(Part paramPart, XMLStyle paramXMLStyle)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramXMLStyle.getLeftAngleBracket() + paramXMLStyle.getPartTagName());
    int j;
    if (!paramPart.getTitle().equals("Untitled Part"))
    {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getTitleAttributeName() + "=" + paramXMLStyle.getDoubleQuote());
      String str = paramPart.getTitle();
      for (j = 0; j < str.length(); j++)
      {
        char c = str.charAt(j);
        if (c == ' ') {
          localStringBuffer.append(paramXMLStyle.getSpace());
        } else if (c == '/') {
          localStringBuffer.append(paramXMLStyle.getSlash());
        } else if (c == '&') {
          localStringBuffer.append(paramXMLStyle.getAmpersandInString());
        } else if (c == '<') {
          localStringBuffer.append(paramXMLStyle.getLeftAngleBracketInString());
        } else if (c == '>') {
          localStringBuffer.append(paramXMLStyle.getRightAngleBracketInString());
        } else if (c == '"') {
          localStringBuffer.append(paramXMLStyle.getDoubleQuoteInString());
        } else if (c == '#') {
          localStringBuffer.append(paramXMLStyle.getHash());
        } else if (c == '?') {
          localStringBuffer.append(paramXMLStyle.getQuestionMark());
        } else if (c == ';') {
          localStringBuffer.append(paramXMLStyle.getSemicolon());
        } else {
          localStringBuffer.append(c);
        }
      }
      localStringBuffer.append(paramXMLStyle.getDoubleQuote());
    }
    if (paramPart.getChannel() != 0) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getChannelAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramPart.getChannel()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramPart.getInstrument() != 0) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getInstrumentAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramPart.getInstrument()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramPart.getTempo() != -1.0D) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getTempoAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramXMLStyle.limitDecimalPlaces() ? limitDecimalPlaces(paramPart.getTempo(), 2) : Double.toString(paramPart.getTempo())) + paramXMLStyle.getDoubleQuote());
    }
    if (paramPart.getKeySignature() != Integer.MIN_VALUE) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getKeySignatureAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramPart.getKeySignature()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramPart.getKeyQuality() != Integer.MIN_VALUE) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getKeyQualityAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramPart.getKeyQuality()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramPart.getNumerator() != Integer.MIN_VALUE) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getNumeratorAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramPart.getNumerator()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramPart.getDenominator() != Integer.MIN_VALUE) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getDenominatorAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramPart.getDenominator()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramPart.getPan() != 0.5D) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getPanAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramXMLStyle.limitDecimalPlaces() ? limitDecimalPlaces(paramPart.getPan(), 2) : Double.toString(paramPart.getPan())) + paramXMLStyle.getDoubleQuote());
    }
    int i = paramPart.size();
    if (i == 0)
    {
      localStringBuffer.append(paramXMLStyle.getSlash() + paramXMLStyle.getRightAngleBracket());
    }
    else
    {
      localStringBuffer.append(paramXMLStyle.getRightAngleBracket());
      for (j = 0; j < paramPart.size(); j++) {
        localStringBuffer.append(phraseToXMLString(paramPart.getPhrase(j), paramXMLStyle));
      }
      localStringBuffer.append(paramXMLStyle.getLeftAngleBracket() + paramXMLStyle.getSlash() + paramXMLStyle.getPartTagName() + paramXMLStyle.getRightAngleBracket());
    }
    return localStringBuffer.toString();
  }
  
  public static String phraseToXMLString(Phrase paramPhrase)
  {
    return DEFAULT_XML_STYLE.initialXMLDeclaration() + phraseToXMLString(paramPhrase, DEFAULT_XML_STYLE);
  }
  
  private static String phraseToXMLString(Phrase paramPhrase, XMLStyle paramXMLStyle)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramXMLStyle.getLeftAngleBracket() + paramXMLStyle.getPhraseTagName());
    int j;
    if (!paramPhrase.getTitle().equals("Untitled Phrase"))
    {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getTitleAttributeName() + "=" + paramXMLStyle.getDoubleQuote());
      String str = paramPhrase.getTitle();
      for (j = 0; j < str.length(); j++)
      {
        char c = str.charAt(j);
        if (c == ' ') {
          localStringBuffer.append(paramXMLStyle.getSpace());
        } else if (c == '/') {
          localStringBuffer.append(paramXMLStyle.getSlash());
        } else if (c == '&') {
          localStringBuffer.append(paramXMLStyle.getAmpersandInString());
        } else if (c == '<') {
          localStringBuffer.append(paramXMLStyle.getLeftAngleBracketInString());
        } else if (c == '>') {
          localStringBuffer.append(paramXMLStyle.getRightAngleBracketInString());
        } else if (c == '"') {
          localStringBuffer.append(paramXMLStyle.getDoubleQuoteInString());
        } else if (c == '#') {
          localStringBuffer.append(paramXMLStyle.getHash());
        } else if (c == '?') {
          localStringBuffer.append(paramXMLStyle.getQuestionMark());
        } else if (c == ';') {
          localStringBuffer.append(paramXMLStyle.getSemicolon());
        } else {
          localStringBuffer.append(c);
        }
      }
      localStringBuffer.append(paramXMLStyle.getDoubleQuote());
    }
    if (paramPhrase.getStartTime() != 0.0D) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getStartTimeAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramXMLStyle.limitDecimalPlaces() ? limitDecimalPlaces(paramPhrase.getStartTime(), 2) : Double.toString(paramPhrase.getStartTime())) + paramXMLStyle.getDoubleQuote());
    }
    if (paramPhrase.getInstrument() != -1) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getInstrumentAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramPhrase.getInstrument()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramPhrase.getPan() != -1.0D) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getTempoAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramXMLStyle.limitDecimalPlaces() ? limitDecimalPlaces(paramPhrase.getTempo(), 2) : Double.toString(paramPhrase.getTempo())) + paramXMLStyle.getDoubleQuote());
    }
    if (paramPhrase.getAppend()) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getAppendAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramPhrase.getAppend() ? Boolean.TRUE.toString() : Boolean.FALSE.toString()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramPhrase.getPan() != 0.5D) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getPanAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramXMLStyle.limitDecimalPlaces() ? limitDecimalPlaces(paramPhrase.getPan(), 2) : Double.toString(paramPhrase.getPan())) + paramXMLStyle.getDoubleQuote());
    }
    int i = paramPhrase.size();
    if (i == 0)
    {
      localStringBuffer.append(paramXMLStyle.getSlash() + paramXMLStyle.getRightAngleBracket());
    }
    else
    {
      localStringBuffer.append(paramXMLStyle.getRightAngleBracket());
      for (j = 0; j < i; j++) {
        localStringBuffer.append(noteToXMLString(paramPhrase.getNote(j), paramXMLStyle));
      }
      localStringBuffer.append(paramXMLStyle.getLeftAngleBracket() + paramXMLStyle.getSlash() + paramXMLStyle.getPhraseTagName() + paramXMLStyle.getRightAngleBracket());
    }
    return localStringBuffer.toString();
  }
  
  public static String noteToXMLString(Note paramNote)
  {
    return DEFAULT_XML_STYLE.initialXMLDeclaration() + noteToXMLString(paramNote, DEFAULT_XML_STYLE);
  }
  
  private static String noteToXMLString(Note paramNote, XMLStyle paramXMLStyle)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramXMLStyle.getLeftAngleBracket() + paramXMLStyle.getNoteTagName());
    if (!paramNote.getPitchType())
    {
      if (paramNote.getPitch() != 60) {
        localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getPitchAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramNote.getPitch()) + paramXMLStyle.getDoubleQuote());
      }
    }
    else {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getFrequencyAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Double.toString(paramNote.getFrequency()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramNote.getDynamic() != 85) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getDynamicAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + Integer.toString(paramNote.getDynamic()) + paramXMLStyle.getDoubleQuote());
    }
    if (paramNote.getRhythmValue() != 1.0D) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getRhythmValueAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramXMLStyle.limitDecimalPlaces() ? limitDecimalPlaces(paramNote.getRhythmValue(), 2) : Double.toString(paramNote.getRhythmValue())) + paramXMLStyle.getDoubleQuote());
    }
    if (paramNote.getPan() != 0.5D) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getPanAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramXMLStyle.limitDecimalPlaces() ? limitDecimalPlaces(paramNote.getPan(), 2) : Double.toString(paramNote.getPan())) + paramXMLStyle.getDoubleQuote());
    }
    if (paramNote.getDuration() != 0.9D) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getDurationAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramXMLStyle.limitDecimalPlaces() ? limitDecimalPlaces(paramNote.getDuration(), 2) : Double.toString(paramNote.getDuration())) + paramXMLStyle.getDoubleQuote());
    }
    if (paramNote.getOffset() != 0.0D) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getOffsetAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramXMLStyle.limitDecimalPlaces() ? limitDecimalPlaces(paramNote.getOffset(), 2) : Double.toString(paramNote.getOffset())) + paramXMLStyle.getDoubleQuote());
    }
    if (paramNote.getSampleStartTime() != 0.0D) {
      localStringBuffer.append(paramXMLStyle.getSpace() + paramXMLStyle.getSampleStartTimeAttributeName() + "=" + paramXMLStyle.getDoubleQuote() + (paramXMLStyle.limitDecimalPlaces() ? limitDecimalPlaces(paramNote.getSampleStartTime(), 2) : Double.toString(paramNote.getSampleStartTime())) + paramXMLStyle.getDoubleQuote());
    }
    localStringBuffer.append(paramXMLStyle.getSlash() + paramXMLStyle.getRightAngleBracket());
    return localStringBuffer.toString();
  }
  
  private static String limitDecimalPlaces(double paramDouble, int paramInt)
  {
    String str = Double.toString(paramDouble);
    int i = str.lastIndexOf(".") + paramInt + 1;
    if (i > str.length()) {
      i = str.length();
    }
    return str.substring(0, i);
  }
  
  public static Score xmlStringToScore(String paramString)
    throws ConversionException
  {
    String str = preprocessString(paramString);
    Element[] arrayOfElement = xmlStringToElements(str);
    if (arrayOfElement.length != 1) {
      throw new ConversionException("There can be only one root element.  This string invalidly has " + arrayOfElement.length + " root elements.");
    }
    Element localElement = arrayOfElement[0];
    if (XMLStyles.isValidScoreTag(arrayOfElement[0].getName())) {
      return elementToScore(arrayOfElement[0]);
    }
    if (XMLStyles.isValidPartTag(arrayOfElement[0].getName())) {
      return new Score(elementToPart(arrayOfElement[0]));
    }
    if (XMLStyles.isValidPhraseTag(arrayOfElement[0].getName())) {
      return new Score(new Part(elementToPhrase(arrayOfElement[0])));
    }
    if (XMLStyles.isValidNoteTag(arrayOfElement[0].getName())) {
      return new Score(new Part(new Phrase(elementToNote(arrayOfElement[0]))));
    }
    throw new ConversionException("Unrecognised root element: " + arrayOfElement[0].getName());
  }
  
  public static Part xmlStringToPart(String paramString)
    throws ConversionException
  {
    String str = preprocessString(paramString);
    Element[] arrayOfElement = xmlStringToElements(str);
    if (arrayOfElement.length != 1) {
      throw new ConversionException("There can be only one root element.  This string invalidly has " + arrayOfElement.length + " root elements.");
    }
    Element localElement = arrayOfElement[0];
    if (XMLStyles.isValidScoreTag(arrayOfElement[0].getName())) {
      throw new ConversionException("This XML string represents a Score, use the xmlStringToScore(String) method instead.");
    }
    if (XMLStyles.isValidPartTag(arrayOfElement[0].getName())) {
      return elementToPart(arrayOfElement[0]);
    }
    if (XMLStyles.isValidPhraseTag(arrayOfElement[0].getName())) {
      return new Part(elementToPhrase(arrayOfElement[0]));
    }
    if (XMLStyles.isValidNoteTag(arrayOfElement[0].getName())) {
      return new Part(new Phrase(elementToNote(arrayOfElement[0])));
    }
    throw new ConversionException("Unrecognised root element: " + arrayOfElement[0].getName());
  }
  
  public static Phrase xmlStringToPhrase(String paramString)
    throws ConversionException
  {
    String str = preprocessString(paramString);
    Element[] arrayOfElement = xmlStringToElements(str);
    if (arrayOfElement.length != 1) {
      throw new ConversionException("There can be only one root element.  This string invalidly has " + arrayOfElement.length + " root elements.");
    }
    Element localElement = arrayOfElement[0];
    if (XMLStyles.isValidScoreTag(arrayOfElement[0].getName())) {
      throw new ConversionException("This XML string represents a Score, use the xmlStringToScore(String) method instead.");
    }
    if (XMLStyles.isValidPartTag(arrayOfElement[0].getName())) {
      throw new ConversionException("This XML string represents a Part, use the xmlStringToPart(String) method instead.");
    }
    if (XMLStyles.isValidPhraseTag(arrayOfElement[0].getName())) {
      return elementToPhrase(arrayOfElement[0]);
    }
    if (XMLStyles.isValidNoteTag(arrayOfElement[0].getName())) {
      return new Phrase(elementToNote(arrayOfElement[0]));
    }
    throw new ConversionException("Unrecognised root element: " + arrayOfElement[0].getName());
  }
  
  public static Note xmlStringToNote(String paramString)
    throws ConversionException
  {
    String str = preprocessString(paramString);
    Element[] arrayOfElement = xmlStringToElements(str);
    if (arrayOfElement.length != 1) {
      throw new ConversionException("There can be only one root element.  This string invalidly has " + arrayOfElement.length + " root elements.");
    }
    Element localElement = arrayOfElement[0];
    if (XMLStyles.isValidScoreTag(arrayOfElement[0].getName())) {
      throw new ConversionException("This XML string represents a Score, use the xmlStringToScore(String) method instead.");
    }
    if (XMLStyles.isValidPartTag(arrayOfElement[0].getName())) {
      throw new ConversionException("This XML string represents a Part, use the xmlStringToPart(String) method instead.");
    }
    if (XMLStyles.isValidPhraseTag(arrayOfElement[0].getName())) {
      throw new ConversionException("This XML string represents a Phrase, use the xmlStringToPhrase(String) method instead.");
    }
    if (XMLStyles.isValidNoteTag(arrayOfElement[0].getName())) {
      return elementToNote(arrayOfElement[0]);
    }
    throw new ConversionException("Unrecognised root element: " + arrayOfElement[0].getName());
  }
  
  private static String preprocessString(String paramString)
    throws ConversionException
  {
    String str1 = paramString;
    Object localObject = null;
    
    for (int i = 0; i < XMLStyles.styles.length; i++)
    {
      localObject = XMLStyles.styles[i].initialXMLDeclaration();
      if (paramString.startsWith((String)localObject))
      {
        str1 = str1.substring(((String)localObject).length());
        break;
      }
    }
    char[] arrayOfChar1 = str1.toCharArray();
    StandardXMLStyle localStandardXMLStyle = new StandardXMLStyle();
    char[][] arrayOfChar = localStandardXMLStyle.getEncodingsOfReferenceChars();
    char[] arrayOfChar2 = localStandardXMLStyle.getReferenceChars();
    for (int j = 0; j < arrayOfChar.length; j++)
    {
      localObject = new StringBuffer();
      String str2 = new String(arrayOfChar[j]);
      int k = 0;
      int m;
      for (m = str1.indexOf(str2); m != -1; m = str1.indexOf(str2, k))
      {
        while (k < m)
        {
          ((StringBuffer)localObject).append(arrayOfChar1[k]);
          k++;
        }
        ((StringBuffer)localObject).append(arrayOfChar2[j]);
        k += 3;
      }
      m = str1.length();
      while (k < m)
      {
        ((StringBuffer)localObject).append(arrayOfChar1[k]);
        k++;
      }
      str1 = ((StringBuffer)localObject).toString();
      arrayOfChar1 = str1.toCharArray();
    }
    return str1;
  }
  
  private static Score elementToScore(Element paramElement)
    throws ConversionException
  {
    StandardXMLStyle localStandardXMLStyle = new StandardXMLStyle();
    if (!XMLStyles.isValidScoreTag(paramElement.getName())) {
      throw new ConversionException("The root element must have the name '" + localStandardXMLStyle.getScoreTagName() + "'.  The invalid name used " + "was '" + paramElement.getName() + "'.");
    }
    Score localScore = new Score();
    String str = XMLStyles.getTitleAttributeValue(paramElement);
    if (!str.equals("")) {
      localScore.setTitle(str);
    }
    str = XMLStyles.getTempoAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localScore.setTempo(Double.valueOf(str).doubleValue());
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getTempoAttributeName() + "' of element '" + localStandardXMLStyle.getScoreTagName() + "' must represent a Java double.");
      }
    }
    str = XMLStyles.getKeySignatureAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localScore.setKeySignature(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getKeySignatureAttributeName() + "' of element '" + localStandardXMLStyle.getScoreTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getKeyQualityAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localScore.setKeyQuality(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getKeyQualityAttributeName() + "' of element '" + localStandardXMLStyle.getScoreTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getNumeratorAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localScore.setNumerator(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getNumeratorAttributeName() + "' of element '" + localStandardXMLStyle.getScoreTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getDenominatorAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localScore.setDenominator(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException5)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getDenominatorAttributeName() + "' of element '" + localStandardXMLStyle.getScoreTagName() + "' must represent a Java integer.");
      }
    }
    Element[] arrayOfElement = paramElement.getChildren();
    for (int i = 0; i < arrayOfElement.length; i++) {
      if (XMLStyles.isValidPartTag(arrayOfElement[i].getName())) {
        localScore.addPart(elementToPart(arrayOfElement[i]));
      }
    }
    return localScore;
  }
  
  private static Part elementToPart(Element paramElement)
    throws ConversionException
  {
    StandardXMLStyle localStandardXMLStyle = new StandardXMLStyle();
    if (!XMLStyles.isValidPartTag(paramElement.getName())) {
      throw new ConversionException("Invalid element: " + paramElement.getName() + ".  The only " + "accepted tag name is '" + localStandardXMLStyle.getPartTagName() + "'.");
    }
    Part localPart = new Part();
    String str = XMLStyles.getTitleAttributeValue(paramElement);
    if (!str.equals("")) {
      localPart.setTitle(str);
    }
    str = XMLStyles.getChannelAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPart.setChannel(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getChannelAttributeName() + "' of element '" + localStandardXMLStyle.getPartTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getInstrumentAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPart.setInstrument(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getInstrumentAttributeName() + "' of element '" + localStandardXMLStyle.getPartTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getTempoAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPart.setTempo(Double.valueOf(str).doubleValue());
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getTempoAttributeName() + "' of element '" + localStandardXMLStyle.getPartTagName() + "' must represent a Java double.");
      }
    }
    str = XMLStyles.getKeySignatureAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPart.setKeySignature(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getKeySignatureAttributeName() + "' of element '" + localStandardXMLStyle.getPartTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getKeyQualityAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPart.setKeyQuality(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException5)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getKeyQualityAttributeName() + "' of element '" + localStandardXMLStyle.getScoreTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getNumeratorAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPart.setNumerator(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException6)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getNumeratorAttributeName() + "' of element '" + localStandardXMLStyle.getPartTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getDenominatorAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPart.setDenominator(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException7)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getDenominatorAttributeName() + "' of element '" + localStandardXMLStyle.getPartTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getPanAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPart.setPan(Double.valueOf(str).doubleValue());
      }
      catch (NumberFormatException localNumberFormatException8)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getPanAttributeName() + "' of element '" + localStandardXMLStyle.getPartTagName() + "' must represent a Java double.");
      }
    }
    Element[] arrayOfElement = paramElement.getChildren();
    for (int i = 0; i < arrayOfElement.length; i++) {
      if (XMLStyles.isValidPhraseTag(arrayOfElement[i].getName())) {
        localPart.addPhrase(elementToPhrase(arrayOfElement[i]));
      }
    }
    return localPart;
  }
  
  private static Phrase elementToPhrase(Element paramElement)
    throws ConversionException
  {
    StandardXMLStyle localStandardXMLStyle = new StandardXMLStyle();
    if (!XMLStyles.isValidPhraseTag(paramElement.getName())) {
      throw new ConversionException("Invalid element: " + paramElement.getName() + ".  The only " + "accepted tag name is '" + localStandardXMLStyle.getPhraseTagName() + "'.");
    }
    Phrase localPhrase = new Phrase();
    String str = XMLStyles.getTitleAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPhrase.setTitle(str);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getTitleAttributeName() + "' of element '" + localStandardXMLStyle.getPhraseTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getStartTimeAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPhrase.setStartTime(Double.valueOf(str).doubleValue());
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getStartTimeAttributeName() + "' of element '" + localStandardXMLStyle.getPhraseTagName() + "' must represent a Java double.");
      }
    }
    str = XMLStyles.getInstrumentAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPhrase.setInstrument(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getInstrumentAttributeName() + "' of element '" + localStandardXMLStyle.getPhraseTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getTempoAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPhrase.setTempo(Double.valueOf(str).doubleValue());
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getTempoAttributeName() + "' of element '" + localStandardXMLStyle.getPhraseTagName() + "' must represent a Java double.");
      }
    }
    str = XMLStyles.getAppendAttributeValue(paramElement);
    if (!str.equals("")) {
      localPhrase.setAppend(new Boolean(str).booleanValue());
    }
    str = XMLStyles.getPanAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localPhrase.setPan(Double.valueOf(str).doubleValue());
      }
      catch (NumberFormatException localNumberFormatException5)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getPanAttributeName() + "' of element '" + localStandardXMLStyle.getPhraseTagName() + "' must represent a Java double.");
      }
    }
    Element[] arrayOfElement = paramElement.getChildren();
    for (int i = 0; i < arrayOfElement.length; i++) {
      if (XMLStyles.isValidNoteTag(arrayOfElement[i].getName())) {
        localPhrase.addNote(elementToNote(arrayOfElement[i]));
      }
    }
    return localPhrase;
  }
  
  private static Note elementToNote(Element paramElement)
    throws ConversionException
  {
    StandardXMLStyle localStandardXMLStyle = new StandardXMLStyle();
    if (!XMLStyles.isValidNoteTag(paramElement.getName())) {
      throw new ConversionException("Invalid element: " + paramElement.getName() + ".  The only " + "accepted tag name is '" + localStandardXMLStyle.getNoteTagName() + "'.");
    }
    Note localNote = new Note();
    String str = XMLStyles.getPitchAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localNote.setPitch(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getPitchAttributeName() + "' of element '" + localStandardXMLStyle.getNoteTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getFrequencyAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        double d = Double.valueOf(str).doubleValue();
        localNote.setFrequency(d);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getFrequencyAttributeName() + "' of element '" + localStandardXMLStyle.getNoteTagName() + "' must represent a Java double.");
      }
    }
    str = XMLStyles.getDynamicAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localNote.setDynamic(Integer.parseInt(str));
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getDynamicAttributeName() + "' of element '" + localStandardXMLStyle.getNoteTagName() + "' must represent a Java integer.");
      }
    }
    str = XMLStyles.getRhythmValueAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localNote.setRhythmValue(Double.valueOf(str).doubleValue());
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getRhythmValueAttributeName() + "' of element '" + localStandardXMLStyle.getNoteTagName() + "' must represent a Java double.");
      }
    }
    str = XMLStyles.getPanAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localNote.setPan(Double.valueOf(str).doubleValue());
      }
      catch (NumberFormatException localNumberFormatException5)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getPanAttributeName() + "' of element '" + localStandardXMLStyle.getNoteTagName() + "' must represent a Java double.");
      }
    }
    str = XMLStyles.getDurationAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localNote.setDuration(Double.valueOf(str).doubleValue());
      }
      catch (NumberFormatException localNumberFormatException6)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getDurationAttributeName() + "' of element '" + localStandardXMLStyle.getNoteTagName() + "' must represent a Java double.");
      }
    }
    str = XMLStyles.getOffsetAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localNote.setOffset(Double.valueOf(str).doubleValue());
      }
      catch (NumberFormatException localNumberFormatException7)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getOffsetAttributeName() + "' of element '" + localStandardXMLStyle.getNoteTagName() + "' must represent a Java double.");
      }
    }
    str = XMLStyles.getSampleStartTimeAttributeValue(paramElement);
    if (!str.equals("")) {
      try
      {
        localNote.setSampleStartTime(Double.valueOf(str).doubleValue());
      }
      catch (NumberFormatException localNumberFormatException8)
      {
        throw new ConversionException("Invalid attribute value: " + str + ".  The " + "attribute '" + localStandardXMLStyle.getSampleStartTimeAttributeName() + "' of " + "element '" + localStandardXMLStyle.getNoteTagName() + "' must represent a " + "Java double.");
      }
    }
    return localNote;
  }
  
  private static Element[] xmlStringToElements(String paramString)
    throws ConversionException
  {
    Vector localVector = new Vector();
    StandardXMLStyle localStandardXMLStyle = new StandardXMLStyle();
    char[][] arrayOfChar = localStandardXMLStyle.getEncodingsOfValueReferenceChars();
    char[] arrayOfChar1 = localStandardXMLStyle.getValueReferenceChars();
    try
    {
      int i = 0;
      if (paramString.charAt(i++) != '<') {
        throw new ConversionException("XML String does not begin with '<'");
      }
      StringBuffer localStringBuffer1 = new StringBuffer();
      char c;
      for (c = paramString.charAt(i++); (c != ' ') && (c != '/') && (c != '>'); c = paramString.charAt(i++)) {
        localStringBuffer1.append(c);
      }
      Element localElement = new Element(localStringBuffer1.toString());
      while (c == ' ')
      {
        StringBuffer localStringBuffer2 = new StringBuffer();
        for (c = paramString.charAt(i++); c != '='; c = paramString.charAt(i++))
        {
          if (c == '/') {
            throw new ConversionException("Illegal character '/' in attribute name of the '" + localElement.getName() + "' element.");
          }
          if (c == '>') {
            throw new ConversionException("Illegal character '>' in attribute name of the '" + localElement.getName() + "' element.");
          }
          localStringBuffer2.append(c);
        }
        Attribute localAttribute = new Attribute(localStringBuffer2.toString());
        c = paramString.charAt(i++);
        if (c != '"') {
          throw new ConversionException("The value of the '" + localAttribute.getName() + "' attribute in the '" + localElement.getName() + "' element does not begin with a double-quote " + "(\").");
        }
        StringBuffer localStringBuffer3 = new StringBuffer();
        label525:
        for (c = paramString.charAt(i++); c != '"'; c = paramString.charAt(i++)) {
          for (int m = 0; m < arrayOfChar.length; m++) {
            for (int n = 0; n < arrayOfChar[m].length; n++) {
              try
              {
                if (arrayOfChar[m][n] != paramString.charAt(i + n - 1))
                {
                  if (m == arrayOfChar.length - 1) {
                    localStringBuffer3.append(c);
                  }
                  break;
                }
                if (n == arrayOfChar[m].length - 1)
                {
                  i += arrayOfChar[m].length - 1;
                  localStringBuffer3.append(arrayOfChar1[m]);
                  break label525;
                }
              }
              catch (IndexOutOfBoundsException localIndexOutOfBoundsException2)
              {
                if (m == arrayOfChar.length - 1) {
                  localStringBuffer3.append(c);
                }
                break;
              }
            }
          }
        }
        localAttribute.setValue(localStringBuffer3.toString());
        localElement.addAttribute(localAttribute);
        c = paramString.charAt(i++);
      }
      if (c == '>')
      {
        int j = paramString.indexOf("</" + localElement.getName() + ">");
        if (j == -1) {
          throw new ConversionException("No closing tag found: </" + localElement.getName() + ">");
        }
        localElement.appendChildren(xmlStringToElements(paramString.substring(i, j)));
        i = j + localElement.getName().length() + 3;
      }
      else if (c == '/')
      {
        c = paramString.charAt(i++);
        if (c != '>') {
          throw new ConversionException("Character '>' is expected to terminate the '" + localElement.getName() + "' element but was not " + "found.");
        }
      }
      else
      {
        throw new ConversionException("Either '>' or '/>' is expected to terminate the '" + localElement.getName() + "' element but neither was " + "found.");
      }
      localVector.addElement(localElement);
      Element[] arrayOfElement = new Element[localVector.size()];
      
      
      if (i < paramString.length())
      {
        arrayOfElement = xmlStringToElements(paramString.substring(i));
        for (int k = 0; k < arrayOfElement.length; k++) {
          localVector.addElement(arrayOfElement[k]);
        }
      }
      localVector.copyInto(arrayOfElement);
      return arrayOfElement;
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException1)
    {
      throw new ConversionException("Xml string ended prematurely.  Further characters were excepted.");
    }
  }
}
