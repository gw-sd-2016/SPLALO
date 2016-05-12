package jm.util;

class XMLStyles
{
  public static final XMLStyle[] styles = { new StandardXMLStyle() };
  
  XMLStyles() {}
  
  public static boolean isValidScoreTag(String paramString)
  {
    for (int i = 0; i < styles.length; i++) {
      if (paramString.equals(styles[i].getScoreTagName())) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isValidPartTag(String paramString)
  {
    for (int i = 0; i < styles.length; i++) {
      if (paramString.equals(styles[i].getPartTagName())) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isValidPhraseTag(String paramString)
  {
    for (int i = 0; i < styles.length; i++) {
      if (paramString.equals(styles[i].getPhraseTagName())) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isValidNoteTag(String paramString)
  {
    for (int i = 0; i < styles.length; i++) {
      if (paramString.equals(styles[i].getNoteTagName())) {
        return true;
      }
    }
    return false;
  }
  
  public static String getTitleAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getTitleAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getTempoAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getTempoAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getKeySignatureAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getKeySignatureAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getKeyQualityAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getKeyQualityAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getNumeratorAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getNumeratorAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getDenominatorAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getDenominatorAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getChannelAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getChannelAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getInstrumentAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getInstrumentAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getPanAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getPanAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getStartTimeAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getStartTimeAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getAppendAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getAppendAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getPitchAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getPitchAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getFrequencyAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getFrequencyAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getDynamicAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getDynamicAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getRhythmValueAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getRhythmValueAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getDurationAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getDurationAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getOffsetAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getOffsetAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
  
  public static String getSampleStartTimeAttributeValue(Element paramElement)
  {
    for (int i = 0; i < styles.length; i++)
    {
      String str = paramElement.getAttribute(styles[i].getSampleStartTimeAttributeName());
      if (!str.equals("")) {
        return str;
      }
    }
    return "";
  }
}
