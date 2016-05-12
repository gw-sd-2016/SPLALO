package jm.music.tools.fuzzy;

public class FuzzyNumber
{
  private double peak;
  private double min;
  private double max;
  private double diff;
  private double membership;
  
  public FuzzyNumber(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.peak = paramDouble1;
    this.min = paramDouble2;
    this.max = paramDouble3;
  }
  
  public double getMembership(double paramDouble)
  {
    if ((paramDouble < this.min) || (paramDouble > this.max)) {
      return 0.0D;
    }
    this.diff = (this.peak - paramDouble);
    if (this.diff >= 0.0D) {
      this.membership = (1.0D - this.diff / (this.peak - this.min));
    } else {
      this.membership = (1.0D + this.diff / (this.max - this.peak));
    }
    return this.membership;
  }
  
  public void setPeak(double paramDouble)
  {
    this.peak = paramDouble;
    if (this.min > paramDouble) {
      this.min = paramDouble;
    }
    if (this.max < paramDouble) {
      this.max = paramDouble;
    }
  }
  
  public double getPeak()
  {
    return this.peak;
  }
  
  public void setMin(double paramDouble)
  {
    this.min = paramDouble;
    if (this.peak < paramDouble) {
      this.peak = paramDouble;
    }
    if (this.max < paramDouble) {
      this.max = paramDouble;
    }
  }
  
  public double getMin()
  {
    return this.min;
  }
  
  public void setMax(double paramDouble)
  {
    this.max = paramDouble;
    if (this.min > paramDouble) {
      this.min = paramDouble;
    }
    if (this.peak > paramDouble) {
      this.peak = paramDouble;
    }
  }
  
  public double getMax()
  {
    return this.max;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\fuzzy\FuzzyNumber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */