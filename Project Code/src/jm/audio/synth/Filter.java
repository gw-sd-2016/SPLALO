package jm.audio.synth;

import java.io.PrintStream;
import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Filter
  extends AudioObject
{
  public static final int LOW_PASS = 0;
  public static final int HIGH_PASS = 1;
  private int type = 0;
  private double cutoff_frequency;
  private double initialCutoff;
  private double cutoff_frq_percent;
  private double ripple = 0.5D;
  private double poles = 2.0D;
  private double[] a = new double[22];
  private double[] ta = new double[22];
  private double[] b = new double[22];
  private double[] tb = new double[22];
  private double[][] xbuf;
  private double[][] ybuf;
  
  public Filter(AudioObject paramAudioObject, double paramDouble)
  {
    this(paramAudioObject, paramDouble, 0, 0.5D, 2.0D);
  }
  
  public Filter(AudioObject paramAudioObject, double paramDouble, int paramInt)
  {
    this(paramAudioObject, paramDouble, paramInt, 0.5D, 2.0D);
  }
  
  public Filter(AudioObject paramAudioObject, double paramDouble1, int paramInt, double paramDouble2, double paramDouble3)
  {
    super(paramAudioObject, "[Filter]");
    this.type = paramInt;
    this.cutoff_frequency = paramDouble1;
    this.ripple = paramDouble2;
    this.poles = paramDouble3;
    if (this.poles > 20.0D)
    {
      System.err.println("More than 20 poles are not allowed (Sorry)");
      System.exit(1);
    }
  }
  
  public Filter(AudioObject[] paramArrayOfAudioObject, double paramDouble, int paramInt)
  {
    this(paramArrayOfAudioObject, paramDouble, paramInt, 0.5D, 2.0D);
  }
  
  public Filter(AudioObject[] paramArrayOfAudioObject, double paramDouble1, int paramInt, double paramDouble2, double paramDouble3)
  {
    super(paramArrayOfAudioObject, "[Filter]");
    this.type = paramInt;
    this.cutoff_frequency = paramDouble1;
    this.initialCutoff = paramDouble1;
    this.ripple = paramDouble2;
    this.poles = paramDouble3;
    if (this.poles > 20.0D)
    {
      System.err.println("More than 20 poles are not allowed (Sorry)");
      System.exit(1);
    }
  }
  
  public void build()
  {
    this.ybuf = new double[this.channels][22];
    this.xbuf = new double[this.channels][22];
    setCutOff(this.cutoff_frequency);
  }
  
  public void printCoefficients()
  {
    for (int i = 0; i < 22; i++) {
      System.out.println("a[" + i + "] " + this.a[i] + "    b[" + i + "] " + this.b[i]);
    }
  }
  
  public void setCutOff(double paramDouble)
  {
    this.cutoff_frequency = paramDouble;
    if (paramDouble <= 0.0D)
    {
      System.err.println("Filter error: You tried to use a cuttoff frequency of " + paramDouble + " - woops! Frequency must be greater than zero. ");
      System.err.println("Exiting from Filter");
      System.exit(1);
    }
    if (paramDouble > 0.5D * this.sampleRate)
    {
      System.err.println("Cutoff frequencies above the Nyquist limit are BAD ;) SampleRate = " + this.sampleRate + " Frequency = " + paramDouble);
      System.err.println("Exiting from Filter");
      System.exit(1);
    }
    this.cutoff_frq_percent = (1.0D / this.sampleRate * paramDouble);
    coefficientCalc();
  }
  
  public void setPoles(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    if (paramInt > 20) {
      paramInt = 20;
    }
    this.poles = paramInt;
    setCutOff(this.cutoff_frequency);
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    float[] arrayOfFloat = null;
    if (this.previous.length > 1)
    {
      arrayOfFloat = new float[i];
      this.previous[1].nextWork(arrayOfFloat);
    }
    int j = 0;
    int k = 0;
    while (j < i)
    {
      if ((j % 100 == 0) && (this.previous.length > 1)) {
        setCutOff(arrayOfFloat[j] + this.initialCutoff);
      }
      for (int m = (int)this.poles; m > 0; m--) {
        this.xbuf[k][m] = this.xbuf[k][(m - 1)];
      }
      this.xbuf[k][0] = paramArrayOfFloat[j];
      for (m = (int)this.poles; m > 0; m--) {
        this.ybuf[k][m] = this.ybuf[k][(m - 1)];
      }
      this.ybuf[k][0] = 0.0D;
      for (m = 0; m < this.poles + 1.0D; m++)
      {
        this.ybuf[k][0] += this.a[m] * this.xbuf[k][m];
        if (m > 0) {
          this.ybuf[k][0] += this.b[m] * this.ybuf[k][m];
        }
      }
      paramArrayOfFloat[j] = ((float)(this.ybuf[k][0] * 1.0D));
      if (this.channels == ++k) {
        k = 0;
      }
      j++;
    }
    return j;
  }
  
  public void coefficientCalc()
  {
    for (int i = 0; i < 22; i++)
    {
      this.a[i] = 0.0D;
      this.b[i] = 0.0D;
    }
    this.a[2] = 1.0D;
    this.b[2] = 1.0D;
    for (i = 1; i <= this.poles * 0.5D; i++)
    {
      double[] arrayOfDouble = coefficientCalcSupport(i);
      for (int j = 0; j < 22; j++)
      {
        this.ta[j] = this.a[j];
        this.tb[j] = this.b[j];
      }
      for (j = 2; j < 22; j++)
      {
        this.a[j] = (arrayOfDouble[0] * this.ta[j] + arrayOfDouble[1] * this.ta[(j - 1)] + arrayOfDouble[2] * this.ta[(j - 2)]);
        this.b[j] = (this.tb[j] - arrayOfDouble[3] * this.tb[(j - 1)] - arrayOfDouble[4] * this.tb[(j - 2)]);
      }
    }
    this.b[2] = 0.0D;
    for (i = 0; i < 20; i++)
    {
      this.a[i] = this.a[(i + 2)];
      this.b[i] = (-this.b[(i + 2)]);
    }
    double d1 = 0.0D;
    double d2 = 0.0D;
    for (int k = 0; k < 20; k++)
    {
      if (this.type == 0) {
        d1 += this.a[k];
      }
      if (this.type == 0) {
        d2 += this.b[k];
      }
      if (this.type == 1) {
        d1 += this.a[k] * Math.pow(-1.0D, k);
      }
      if (this.type == 1) {
        d2 += this.b[k] * Math.pow(-1.0D, k);
      }
    }
    double d3 = d1 / (1.0D - d2);
    for (int m = 0; m < 20; m++) {
      this.a[m] /= d3;
    }
  }
  
  private double[] coefficientCalcSupport(int paramInt)
  {
    double[] arrayOfDouble = new double[5];
    double d1 = -Math.cos(3.141592653589793D / (this.poles * 2.0D) + (paramInt - 1) * 3.141592653589793D / this.poles);
    double d2 = Math.sin(3.141592653589793D / (this.poles * 2.0D) + (paramInt - 1) * 3.141592653589793D / this.poles);
    if (this.ripple != 0.0D)
    {
      d3 = Math.sqrt(Math.pow(100.0D / (100.0D - this.ripple), 2.0D) - 1.0D);
      d4 = 1.0D / this.poles * Math.log(1.0D / d3 + Math.sqrt(1.0D / (d3 * d3) + 1.0D));
      d5 = 1.0D / this.poles * Math.log(1.0D / d3 + Math.sqrt(1.0D / (d3 * d3) - 1.0D));
      d5 = (Math.exp(d5) + Math.exp(-d5)) * 0.5D;
      d1 = d1 * ((Math.exp(d4) - Math.exp(-d4)) * 0.5D) / d5;
      d2 = d2 * ((Math.exp(d4) + Math.exp(-d4)) * 0.5D) / d5;
    }
    double d3 = 2.0D * Math.tan(0.5D);
    double d4 = 6.283185307179586D * this.cutoff_frq_percent;
    double d5 = d1 * d1 + d2 * d2;
    double d6 = 4.0D - 4.0D * d1 * d3 + d5 * (d3 * d3);
    double d7 = d3 * d3 / d6;
    double d8 = 2.0D * d7;
    double d9 = d7;
    double d10 = (8.0D - 2.0D * d5 * (d3 * d3)) / d6;
    double d11 = (-4.0D - 4.0D * d1 * d3 - d5 * (d3 * d3)) / d6;
    double d12 = 0.0D;
    if (this.type == 1) {
      d12 = -Math.cos(d4 * 0.5D + 0.5D) / Math.cos(d4 * 0.5D - 0.5D);
    }
    if (this.type == 0) {
      d12 = Math.sin(0.5D - d4 * 0.5D) / Math.sin(0.5D + d4 * 0.5D);
    }
    d6 = 1.0D + d10 * d12 - d11 * (d12 * d12);
    arrayOfDouble[0] = ((d7 - d8 * d12 + d9 * (d12 * d12)) / d6);
    arrayOfDouble[1] = ((-2.0D * d7 * d12 + d8 + d8 * (d12 * d12) - 2.0D * d9 * d12) / d6);
    arrayOfDouble[2] = ((d7 * (d12 * d12) - d8 * d12 + d9) / d6);
    arrayOfDouble[3] = ((2.0D * d12 + d10 + d10 * (d12 * d12) - 2.0D * d11 * d12) / d6);
    arrayOfDouble[4] = ((-(d12 * d12) - d10 * d12 + d11) / d6);
    if (this.type == 1) {
      arrayOfDouble[1] = (-arrayOfDouble[1]);
    }
    if (this.type == 1) {
      arrayOfDouble[3] = (-arrayOfDouble[3]);
    }
    return arrayOfDouble;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Filter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */