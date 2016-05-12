package jm.util;

abstract class XMLStyle
{
  final char[] WHITESPACE_CHARS = { '%', '2', '0' };
  final char[] DOUBLE_QUOTE_CHARS = { '%', '2', '2' };
  final char[] HASH_CHARS = { '%', '2', '3' };
  final char[] AMPERSAND_CHARS = { '%', '2', '6' };
  final char[] SLASH_CHARS = { '%', '2', 'F' };
  final char[] SEMICOLON_CHARS = { '%', '3', 'B' };
  final char[] QUESTION_MARK_CHARS = { '%', '3', 'F' };
  final char[] LEFT_ANGLE_CHARS = { '%', '3', 'C' };
  final char[] RIGHT_ANGLE_CHARS = { '%', '3', 'E' };
  
  XMLStyle() {}
  
  public String initialXMLDeclaration()
  {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  }
  
  public String getSpace()
  {
    return " ";
  }
  
  public String getDoubleQuote()
  {
    return "\"";
  }
  
  public String getDoubleQuoteInString()
  {
    return "&quot;";
  }
  
  public String getHash()
  {
    return "#";
  }
  
  public String getAmpersand()
  {
    return "&";
  }
  
  public String getAmpersandInString()
  {
    return "&amp;";
  }
  
  public String getSlash()
  {
    return "/";
  }
  
  public String getSemicolon()
  {
    return ";";
  }
  
  public String getQuestionMark()
  {
    return "?";
  }
  
  public abstract char[] getReferenceChars();
  
  public abstract char[][] getEncodingsOfReferenceChars();
  
  public abstract char[] getValueReferenceChars();
  
  public abstract char[][] getEncodingsOfValueReferenceChars();
  
  public String getLeftAngleBracket()
  {
    return "<";
  }
  
  public String getLeftAngleBracketInString()
  {
    return "&lt;";
  }
  
  public String getRightAngleBracket()
  {
    return ">";
  }
  
  public String getRightAngleBracketInString()
  {
    return "&gt;";
  }
  
  public String getScoreTagName()
  {
    return "Score";
  }
  
  public String getPartTagName()
  {
    return "Part";
  }
  
  public String getPhraseTagName()
  {
    return "Phrase";
  }
  
  public String getNoteTagName()
  {
    return "Note";
  }
  
  public String getTitleAttributeName()
  {
    return "title";
  }
  
  public String getTempoAttributeName()
  {
    return "tempo";
  }
  
  public String getKeySignatureAttributeName()
  {
    return "keySignature";
  }
  
  public String getKeyQualityAttributeName()
  {
    return "keyQuality";
  }
  
  public String getNumeratorAttributeName()
  {
    return "numerator";
  }
  
  public String getDenominatorAttributeName()
  {
    return "denominator";
  }
  
  public String getChannelAttributeName()
  {
    return "channel";
  }
  
  public String getInstrumentAttributeName()
  {
    return "instrument";
  }
  
  public String getPanAttributeName()
  {
    return "pan";
  }
  
  public String getStartTimeAttributeName()
  {
    return "startTime";
  }
  
  public String getAppendAttributeName()
  {
    return "append";
  }
  
  public String getPitchAttributeName()
  {
    return "pitch";
  }
  
  public String getFrequencyAttributeName()
  {
    return "frequency";
  }
  
  public String getDynamicAttributeName()
  {
    return "dynamic";
  }
  
  public String getRhythmValueAttributeName()
  {
    return "rhythmValue";
  }
  
  public String getDurationAttributeName()
  {
    return "duration";
  }
  
  public String getOffsetAttributeName()
  {
    return "offset";
  }
  
  public String getSampleStartTimeAttributeName()
  {
    return "sampleStartTime";
  }
  
  public boolean limitDecimalPlaces()
  {
    return false;
  }
}
