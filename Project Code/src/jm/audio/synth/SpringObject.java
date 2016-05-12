package jm.audio.synth;

public class SpringObject
{
  private int restingLength;
  private double k = 0.002D;
  
  public SpringObject() {}
  
  public SpringObject(double paramDouble)
  {
    this.k = paramDouble;
  }
  
  public double getCurrentForce(double paramDouble1, double paramDouble2)
  {
    double d1 = -1.0D * (paramDouble2 - paramDouble1 - this.restingLength);
    double d2 = this.k * d1;
    return d2;
  }
  
  public void setRestingLength(int paramInt)
  {
    this.restingLength = paramInt;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\SpringObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */