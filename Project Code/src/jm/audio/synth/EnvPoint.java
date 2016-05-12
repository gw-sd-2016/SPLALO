package jm.audio.synth;

public final class EnvPoint
{
  public float y;
  public float x = -1.0F;
  public int X = -1;
  
  public EnvPoint(float paramFloat1, float paramFloat2)
  {
    this.y = paramFloat2;
    this.x = paramFloat1;
  }
  
  public EnvPoint(int paramInt, float paramFloat)
  {
    this.y = paramFloat;
    this.X = paramInt;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\EnvPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */