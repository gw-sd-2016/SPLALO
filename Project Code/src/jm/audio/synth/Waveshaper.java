package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Waveshaper
  extends AudioObject
{
  private int shapeType = 1;
  private int stages = 4;
  private double[] weights;
  public static final int POLYNOMIAL = 0;
  public static final int CHEBYSHEV = 1;
  
  public Waveshaper(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[Waveshaper]");
    this.shapeType = 1;
    this.stages = 4;
    double[] arrayOfDouble = { 0.3D, 0.8D, 0.6D, 0.4D };
    this.weights = arrayOfDouble;
  }
  
  public Waveshaper(AudioObject paramAudioObject, int paramInt1, int paramInt2)
  {
    super(paramAudioObject, "[Waveshaper]");
    this.shapeType = paramInt1;
    this.stages = paramInt2;
    double[] arrayOfDouble = { 0.3D, 0.8D, 0.6D, 0.4D };
    this.weights = arrayOfDouble;
  }
  
  public Waveshaper(AudioObject paramAudioObject, int paramInt1, int paramInt2, double[] paramArrayOfDouble)
  {
    super(paramAudioObject, "[Waveshaper]");
    this.shapeType = paramInt1;
    this.stages = paramInt2;
    this.weights = paramArrayOfDouble;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    int j;
    float f1;
    float f2;
    if (this.shapeType == 0) {
      for (j = 0; j < i; j++)
      {
        f1 = Math.abs(paramArrayOfFloat[j]);
        f2 = f1;
        for (int k = 1; k < this.stages; k++)
        {
          float f3 = f1;
          for (int m = 0; m < k; m++) {
            f3 *= f1;
          }
          f2 = (float)(f2 + f3 * this.weights[k]);
        }
        if (paramArrayOfFloat[j] < 0.0D) {
          f2 *= -1.0F;
        }
        paramArrayOfFloat[j] = f2;
      }
    } else if (this.shapeType == 1) {
      for (j = 0; j < i; j++)
      {
        f1 = Math.abs(paramArrayOfFloat[j]);
        f2 = f1;
        if (this.stages > 1) {
          f2 = (float)(f2 + this.weights[0] * (2.0F * f1 * f1 - 1.0F));
        }
        if (this.stages > 2) {
          f2 = (float)(f2 + this.weights[1] * (4.0F * (float)Math.pow(f1, 3.0D) - 3.0F * f1));
        }
        if (this.stages > 3) {
          f2 = (float)(f2 + this.weights[2] * (8.0F * (float)Math.pow(f1, 4.0D) - 8.0F * (float)Math.pow(f1, 2.0D) + 1.0F));
        }
        if (this.stages > 4) {
          f2 = (float)(f2 + this.weights[3] * (16.0F * (float)Math.pow(f1, 5.0D) - 20.0F * (float)Math.pow(f1, 3.0D) + 5.0F * f1));
        }
        if (this.stages > 5) {
          f2 = (float)(f2 + this.weights[4] * (32.0F * (float)Math.pow(f1, 6.0D) - 48.0F * (float)Math.pow(f1, 4.0D) + 18.0F * (float)Math.pow(f1, 2.0D) - 1.0F));
        }
        if (this.stages > 6) {
          f2 = (float)(f2 + this.weights[5] * (64.0F * (float)Math.pow(f1, 7.0D) - 112.0F * (float)Math.pow(f1, 5.0D) + 56.0F * (float)Math.pow(f1, 3.0D) - 7.0F * f1));
        }
        if (this.stages > 7) {
          f2 = (float)(f2 + this.weights[6] * (128.0F * (float)Math.pow(f1, 8.0D) - 256.0F * (float)Math.pow(f1, 6.0D) + 160.0F * (float)Math.pow(f1, 4.0D) - 32.0F * (float)Math.pow(f1, 2.0D) + 1.0F));
        }
        if (this.stages > 8) {
          f2 = (float)(f2 + this.weights[7] * (256.0F * (float)Math.pow(f1, 9.0D) - 576.0F * (float)Math.pow(f1, 7.0D) + 432.0F * (float)Math.pow(f1, 5.0D) - 120.0F * (float)Math.pow(f1, 3.0D) + 9.0F * f1));
        }
        if (this.stages > 9) {
          f2 = (float)(f2 + this.weights[8] * (512.0F * (float)Math.pow(f1, 10.0D) - 1280.0F * (float)Math.pow(f1, 8.0D) + 1120.0F * (float)Math.pow(f1, 6.0D) - 400.0F * (float)Math.pow(f1, 4.0D) + 50.0F * (float)Math.pow(f1, 2.0D) - 1.0F));
        }
        if (paramArrayOfFloat[j] < 0.0D) {
          f2 *= -1.0F;
        }
        paramArrayOfFloat[j] = f2;
      }
    }
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Waveshaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */