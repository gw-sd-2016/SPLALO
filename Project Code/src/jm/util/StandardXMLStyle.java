package jm.util;

class StandardXMLStyle
  extends XMLStyle
{
  StandardXMLStyle() {}
  
  public final char[] getReferenceChars()
  {
    return new char[0];
  }
  
  public final char[][] getEncodingsOfReferenceChars()
  {
    return new char[0][];
  }
  
  public final char[] getValueReferenceChars()
  {
    return new char[] { '<', '>', '"', '&' };
  }
  
  public final char[][] getEncodingsOfValueReferenceChars()
  {
    return new char[][] { { '&', 'l', 't', ';' }, { '&', 'g', 't', ';' }, { '&', 'q', 'u', 'o', 't', ';' }, { '&', 'a', 'm', 'p', ';' } };
  }
}
