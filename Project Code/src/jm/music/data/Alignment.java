package jm.music.data;

import java.io.Serializable;

public abstract class Alignment
  implements Serializable
{
  public static final Alignment START_TOGETHER = new Alignment()
  {
    public double determineStartTime(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3)
    {
      return paramAnonymousDouble2;
    }
  };
  public static final Alignment END_TOGETHER = new Alignment()
  {
    public double determineStartTime(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3)
    {
      return paramAnonymousDouble3 - paramAnonymousDouble1;
    }
  };
  public static final Alignment AFTER = new Alignment()
  {
    public double determineStartTime(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3)
    {
      return paramAnonymousDouble3;
    }
  };
  public static final Alignment BEFORE = new Alignment()
  {
    public double determineStartTime(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3)
    {
      return paramAnonymousDouble2 - paramAnonymousDouble1;
    }
  };
  public static final Alignment CENTRE_ALIGN = new Alignment()
  {
    public double determineStartTime(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3)
    {
      return (paramAnonymousDouble3 + paramAnonymousDouble2 - paramAnonymousDouble1) / 2.0D;
    }
  };
  public static final Alignment CENTER_ALIGN = CENTRE_ALIGN;
  public static final Alignment START_ON_CENTRE = new Alignment()
  {
    public double determineStartTime(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3)
    {
      return (paramAnonymousDouble2 + paramAnonymousDouble3) / 2.0D;
    }
  };
  public static final Alignment START_ON_CENTER = START_ON_CENTRE;
  public static final Alignment END_ON_CENTRE = new Alignment()
  {
    public double determineStartTime(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3)
    {
      return (paramAnonymousDouble2 + paramAnonymousDouble3) / 2.0D - paramAnonymousDouble1;
    }
  };
  public static final Alignment END_ON_CENTER = END_ON_CENTRE;
  public static final Alignment CENTRE_ON_START = new Alignment()
  {
    public double determineStartTime(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3)
    {
      return paramAnonymousDouble2 - paramAnonymousDouble1 / 2.0D;
    }
  };
  public static final Alignment CENTER_ON_START = CENTRE_ON_START;
  public static final Alignment CENTRE_ON_END = new Alignment()
  {
    public double determineStartTime(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3)
    {
      return paramAnonymousDouble3 - paramAnonymousDouble1 / 2.0D;
    }
  };
  public static final Alignment CENTER_ON_END = CENTRE_ON_END;
  
  protected Alignment() {}
  
  abstract double determineStartTime(double paramDouble1, double paramDouble2, double paramDouble3);
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\data\Alignment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */