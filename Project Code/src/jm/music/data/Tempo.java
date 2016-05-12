package jm.music.data;

import java.io.PrintStream;

public class Tempo
{
  public static double ANDANTE = 140.0D;
  public static double DEFAULT_TEMPO = 60.0D;
  public static double DEFAULT_LOW = 1.0E-7D;
  public static double DEFAULT_HIGH = 1000000.0D;
  private double value = DEFAULT_TEMPO;
  private double lowestTempo = DEFAULT_LOW;
  private double highestTempo = DEFAULT_HIGH;
  
  public Tempo() {}
  
  public Tempo(double paramDouble)
  {
    this.value = paramDouble;
  }
  
  public void setTempo(double paramDouble)
  {
    this.value = setInBounds(paramDouble);
  }
  
  private double setInBounds(double paramDouble)
  {
    if (paramDouble <= this.lowestTempo) {
      paramDouble = this.lowestTempo;
    } else if (paramDouble >= this.highestTempo) {
      paramDouble = this.highestTempo;
    }
    return paramDouble;
  }
  
  public double getPerMinute()
  {
    return this.value;
  }
  
  public double getPerSecond()
  {
    return 60.0D / this.value;
  }
  
  public void setHighestTempo(double paramDouble)
  {
    paramDouble = checkBelowZero(paramDouble);
    this.highestTempo = paramDouble;
  }
  
  public double getHighestTempo()
  {
    return this.highestTempo;
  }
  
  public void setLowestTempo(double paramDouble)
  {
    paramDouble = checkBelowZero(paramDouble);
    this.lowestTempo = paramDouble;
  }
  
  private double checkBelowZero(double paramDouble)
  {
    if (paramDouble < 0.0D)
    {
      System.out.println("lowestTempo must be positive number!");
      System.out.println("setting it to 0.001");
      paramDouble = 0.001D;
    }
    return paramDouble;
  }
  
  public double getLowestTempo()
  {
    return this.lowestTempo;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\data\Tempo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */