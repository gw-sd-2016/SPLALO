package jm.audio.synth;

public class MassObject
{
  private double massSize = 1.0D;
  private double friction = 3.0E-6D;
  private double inertia = 0.0D;
  private double deltaTime = 1.0D;
  private double yPosition;
  
  public MassObject()
  {
    this(1.0D);
  }
  
  public MassObject(double paramDouble)
  {
    this(paramDouble, 1.0D);
  }
  
  public MassObject(double paramDouble1, double paramDouble2)
  {
    this.massSize = paramDouble2;
    this.friction = paramDouble1;
  }
  
  public void setYPosition(double paramDouble)
  {
    this.yPosition = paramDouble;
  }
  
  public double getYPosition()
  {
    return this.yPosition;
  }
  
  public double getDisplacement(double paramDouble)
  {
    paramDouble += this.inertia;
    if (((this.inertia < 0.0D) && (this.friction > 0.0D)) || ((this.inertia > 0.0D) && (this.friction < 0.0D))) {
      this.friction *= -1.0D;
    }
    if (Math.abs(this.friction) > Math.abs(paramDouble)) {
      this.inertia = 0.0D;
    } else {
      this.inertia = (paramDouble - this.friction);
    }
    double d1 = paramDouble / this.massSize;
    double d2 = d1 / (this.deltaTime * this.deltaTime);
    return d2;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\MassObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */