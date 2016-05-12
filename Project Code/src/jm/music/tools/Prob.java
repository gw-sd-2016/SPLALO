package jm.music.tools;

import java.util.Random;

public final class Prob
{
  private static final Random RNG = new Random();
  
  private Prob() {}
  
  public static final int gaussianPitch(int paramInt1, int paramInt2)
  {
    long l;
    do
    {
      l = Math.round(RNG.nextGaussian() * paramInt2 + paramInt1);
    } while ((l < 0L) || (l > 127L));
    return (int)l;
  }
  
  public static final double gaussianFrequency(double paramDouble1, double paramDouble2)
  {
    double d;
    do
    {
      d = RNG.nextGaussian() * paramDouble2 + paramDouble1;
    } while (d < 1.0E-17D);
    return d;
  }
  
  public static final double gaussianRhythmValue(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d;
    do
    {
      d = RNG.nextGaussian() * paramDouble2 + paramDouble1;
      d /= paramDouble3;
      d = Math.round(d);
      d *= paramDouble3;
    } while ((d < 0.0D) || (d > Double.MAX_VALUE));
    return d;
  }
  
  public static final int gaussianDynamic(int paramInt1, int paramInt2)
  {
    long l;
    do
    {
      l = Math.round(RNG.nextGaussian() * paramInt2 + paramInt1);
    } while ((l < 0L) || (l > 127L));
    return (int)l;
  }
  
  public static final double gaussianPan(double paramDouble1, double paramDouble2)
  {
    return gaussianPan(paramDouble1, paramDouble2, 1.0D);
  }
  
  public static final double gaussianPan(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    paramDouble3 = paramDouble3 >= 0.0D ? paramDouble3 : 0.0D;
    long l;
    do
    {
      l = Math.round(RNG.nextGaussian() * paramDouble2 + paramDouble1);
    } while ((l < 0.0D) || (l > paramDouble3));
    return (int)l;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\Prob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */