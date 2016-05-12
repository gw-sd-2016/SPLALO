package jm.audio;

public final class AOException
  extends Exception
{
  private static String[] MESSAGES = new String[2];
  
  public AOException(String paramString1, String paramString2)
  {
    super(paramString1 + paramString2);
  }
  
  public AOException(String paramString, int paramInt)
  {
    super(paramString + MESSAGES[paramInt]);
  }
  
  static
  {
    MESSAGES[0] = "Unbalanced number of returned samples from multiple inputs.";
    MESSAGES[1] = "Wrong number of inputs for this AudioObject.";
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\AOException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */