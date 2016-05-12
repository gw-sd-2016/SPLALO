package jm.audio.synth;

import java.io.PrintStream;
import java.util.Random;
import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;

public class Noise
  extends AudioObject
{
  private int noiseType = 0;
  private int noiseDensity = 10;
  private float amp = 1.0F;
  private static float sum;
  private static float[] rg = new float[16];
  private static int k;
  private static int kg;
  private static int ng;
  private static int threshold;
  private static int np = 1;
  private static int nbits = 1;
  private static int numbPoints = 48000;
  private static float nr = numbPoints;
  private static float result;
  private static int counter = 0;
  private double standardDeviation = 0.25D;
  private double mean = 0.0D;
  private float walkLastValue = 0.0F;
  private float walkStepSize = 0.3F;
  private float walkMax = 1.0F;
  private float walkMin = -1.0F;
  private int walkNoiseDensity = 500;
  private long walkDensityCounter = 0L;
  private boolean walkVaryDensity = true;
  private int walkNoiseDensityMin = 1;
  private int walkNoiseDensityMax = 1500;
  private int walkNoiseDensityStepSize = 100;
  private Random RandomGenerator = new Random();
  private int gendynAmpGranularity = 128;
  private double gendynPrevTime = 50.0D;
  private int gendynTimeMirror = 80;
  private int gendynAmpMirror = 80;
  private int tempAmpMirror;
  private boolean ampMirrorUpdate = false;
  private int gendynPointSize = 4;
  private boolean pointSizeReset = false;
  private int newPointSize;
  private double[] gendynAmpArray = new double[this.gendynPointSize];
  private double[] gendynTimeArray = new double[this.gendynPointSize];
  private double gendynAmp0 = 0.0D;
  private int[] gendynIntArray;
  private double gendynIntArrayLength;
  private int gendynIntArrayCounter = 0;
  private double gendynTimeStepSize = 10.0D;
  private double maxGendynTimeStepSize = 100.0D;
  private double gendynAmpStepSize = 10.0D;
  private double maxGendynAmpStepSize = 100.0D;
  private int mirrorMax = 100;
  private boolean gendynGaussian = false;
  private double gendynPrimaryTimeStepSize = 10.0D;
  private double gendynPrimaryAmpStepSize = 10.0D;
  private int gendynPrimaryTimeMirror = 100;
  private int gendynPrimaryAmpMirror = 100;
  private int gendynInterpolation = 1;
  private boolean gendynGranularityUpdate = false;
  private int tempGendynGranularity;
  public static final int WHITE_NOISE = 0;
  public static final int STEP_NOISE = 1;
  public static final int SMOOTH_NOISE = 2;
  public static final int BROWN_NOISE = 3;
  public static final int FRACTAL_NOISE = 4;
  public static final int GAUSSIAN_NOISE = 5;
  public static final int WALK_NOISE = 6;
  public static final int GENDYN_NOISE = 7;
  private float gnSampleVal;
  private int gnj;
  private int mgaCounter;
  private double mgaInc;
  private int index;
  private int jindex;
  private double rwNewVal;
  
  public Noise(Instrument paramInstrument)
  {
    this(paramInstrument, 0);
  }
  
  public Noise(Instrument paramInstrument, int paramInt)
  {
    this(paramInstrument, paramInt, 44100);
  }
  
  public Noise(Instrument paramInstrument, int paramInt1, int paramInt2)
  {
    this(paramInstrument, paramInt1, paramInt2, 1);
  }
  
  public Noise(Instrument paramInstrument, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInstrument, paramInt2, "[WaveTable]");
    this.noiseType = paramInt1;
    this.channels = paramInt3;
    if (paramInt1 == 4) {
      setUpFractalMath();
    }
    if (paramInt1 == 7) {
      makeGendynArray();
    }
    for (int i = 0; i < this.gendynPointSize; i++) {
      this.gendynAmpArray[i] = 50.0D;
    }
  }
  
  public void setAmp(float paramFloat)
  {
    this.amp = paramFloat;
  }
  
  public float getAmp()
  {
    return this.amp;
  }
  
  private void setUpFractalMath()
  {
    for (nr /= 2.0F; nr > 1.0F; nr /= 2.0F)
    {
      nbits += 1;
      np = 2 * np;
    }
    for (kg = 0; kg < nbits; kg += 1) {
      rg[kg] = ((float)Math.random());
    }
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = 0;
    switch (this.noiseType)
    {
    }
    while (i < paramArrayOfFloat.length)
    {
      for (int j = 0; j < this.channels; j++) {
        paramArrayOfFloat[(i++)] = ((float)(Math.random() * 2.0D - 1.0D) * this.amp);
      }
      continue;
      float f1 = 0.0F;
      float f2 = 0.0F;
      float f3 = 0.0F;
      while (i < paramArrayOfFloat.length)
      {
        for (int m = 0; m < this.channels; m++)
        {
          float f5 = (float)(Math.random() * 2.0D - 1.0D) * this.amp;
          float f4 = (f1 + f2 + f3 + f5) / 4.0F;
          paramArrayOfFloat[(i++)] = f4;
          f1 = f2;
          f2 = f3;
          f3 = f5;
        }
        continue;
        m = this.noiseDensity;
        float f6 = (float)(Math.random() * 2.0D - 1.0D) * this.amp;
        while (i < paramArrayOfFloat.length)
        {
          for (int n = 0; n < this.channels; n++)
          {
            if (i % m == 0) {
              f6 = (float)(Math.random() * 2.0D - 1.0D) * this.amp;
            }
            paramArrayOfFloat[(i++)] = f6;
          }
          continue;
          m = this.noiseDensity;
          f6 = (float)(Math.random() * 2.0D - 1.0D) * this.amp;
          float f7 = (float)(Math.random() * 2.0D - 1.0D) * this.amp;
          while (i < paramArrayOfFloat.length)
          {
            for (int i1 = 0; i1 < this.channels; i1++) {
              if ((i + 1) % m == 0)
              {
                paramArrayOfFloat[(i++)] = f7;
                f6 = f7;
                f7 = (float)(Math.random() * 2.0D - 1.0D) * this.amp;
              }
              else
              {
                paramArrayOfFloat[(i++)] = (f6 + (f7 - f6) / m * (i % m));
              }
            }
            continue;
            while (i < paramArrayOfFloat.length)
            {
              for (i1 = 0; i1 < this.channels; i1++)
              {
                if (counter % this.noiseDensity == 0)
                {
                  threshold = np;
                  ng = nbits;
                  while (k % threshold != 0)
                  {
                    ng -= 1;
                    threshold /= 2;
                  }
                  sum = 0.0F;
                  for (kg = 0; kg < nbits; kg += 1)
                  {
                    if (kg < ng) {
                      rg[kg] = ((float)Math.random());
                    }
                    sum += rg[kg];
                  }
                  result = (float)((sum / nbits - 0.17D) * 2.85D - 1.0D);
                  if (result > 1.0D) {
                    result = 1.0F;
                  } else if (result < -1.0D) {
                    result = -1.0F;
                  }
                }
                counter += 1;
                paramArrayOfFloat[(i++)] = (result * this.amp);
              }
              if (counter > 67000)
              {
                counter = 0;
                continue;
                Random localRandom = new Random();
                while (i < paramArrayOfFloat.length)
                {
                  for (int i2 = 0; i2 < this.channels; i2++)
                  {
                    float f8 = (float)(localRandom.nextGaussian() * this.standardDeviation + this.mean);
                    if (f8 < -1.0F) {
                      f8 = -1.0F;
                    } else if (f8 > 1.0F) {
                      f8 = 1.0F;
                    }
                    paramArrayOfFloat[(i++)] = (f8 * this.amp);
                  }
                  continue;
                  while (i < paramArrayOfFloat.length)
                  {
                    for (i2 = 0; i2 < this.channels; i2++)
                    {
                      paramArrayOfFloat[(i++)] = this.walkLastValue;
                      this.walkDensityCounter += 1L;
                      if ((int)this.walkDensityCounter % this.walkNoiseDensity == 0)
                      {
                        this.walkLastValue += (float)Math.random() * this.walkStepSize * 2.0F - this.walkStepSize;
                        while ((this.walkLastValue > this.walkMax) || (this.walkLastValue < this.walkMin))
                        {
                          if (this.walkLastValue > this.walkMax) {
                            this.walkLastValue -= (this.walkLastValue - this.walkMax) * 2.0F;
                          }
                          if (this.walkLastValue < this.walkMin) {
                            this.walkLastValue += (this.walkMin - this.walkLastValue) * 2.0F;
                          }
                        }
                        if (this.walkVaryDensity)
                        {
                          this.walkNoiseDensity += (int)(Math.random() * this.walkNoiseDensityStepSize * 2.0D - this.walkNoiseDensityStepSize);
                          if (this.walkNoiseDensity < this.walkNoiseDensityMin) {
                            this.walkNoiseDensity = this.walkNoiseDensityMin;
                          } else if (this.walkNoiseDensity > this.walkNoiseDensityMax) {
                            this.walkNoiseDensity = this.walkNoiseDensityMax;
                          }
                        }
                      }
                    }
                    continue;
                    this.gnSampleVal = 0.0F;
                    while (i < paramArrayOfFloat.length)
                    {
                      this.gnSampleVal = ((this.gendynIntArray[this.gendynIntArrayCounter] / this.gendynAmpGranularity - 0.5F) * 2.0F);
                      if (this.gnSampleVal > 1.0D) {
                        this.gnSampleVal = 1.0F;
                      } else if (this.gnSampleVal < -1.0D) {
                        this.gnSampleVal = -1.0F;
                      }
                      for (this.gnj = 0; this.gnj < this.channels; this.gnj += 1) {
                        paramArrayOfFloat[(i++)] = this.gnSampleVal;
                      }
                      this.gendynIntArrayCounter += 1;
                      if (this.gendynIntArrayCounter >= (int)this.gendynIntArrayLength)
                      {
                        makeGendynArray();
                        continue;
                        System.err.println(this.name + "jMusic error: Noise type " + this.noiseType + " not supported yet.");
                        System.exit(1);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return i;
  }
  
  private void makeGendynArray()
  {
    this.gendynTimeStepSize = randWalk(this.gendynTimeStepSize, this.gendynPrimaryTimeStepSize, this.gendynPrimaryTimeMirror, true);
    if (Math.abs(this.gendynTimeStepSize) > this.maxGendynTimeStepSize) {
      this.gendynTimeStepSize = this.maxGendynTimeStepSize;
    }
    this.gendynAmpStepSize = randWalk(this.gendynAmpStepSize, this.gendynPrimaryAmpStepSize, this.gendynPrimaryAmpMirror, false);
    if (Math.abs(this.gendynAmpStepSize) > this.maxGendynAmpStepSize) {
      this.gendynAmpStepSize = this.maxGendynAmpStepSize;
    }
    for (this.index = 0; this.index < this.gendynPointSize; this.index += 1)
    {
      this.gendynTimeArray[this.index] = Math.abs(randWalk(this.gendynTimeArray[this.index], this.gendynTimeStepSize, this.gendynTimeMirror, true));
      if (this.gendynTimeArray[this.index] < 1.0D) {
        this.gendynTimeArray[this.index] = 1.0D;
      }
      this.gendynAmpArray[this.index] = randWalk(this.gendynAmpArray[this.index], this.gendynAmpStepSize, this.gendynAmpMirror / 2 + 51, false);
    }
    this.gendynIntArrayLength = 0.0D;
    for (this.index = 0; this.index < this.gendynPointSize; this.index += 1) {
      this.gendynIntArrayLength += this.gendynTimeArray[this.index];
    }
    this.gendynIntArray = new int[(int)this.gendynIntArrayLength];
    this.mgaCounter = 0;
    this.mgaInc = ((this.gendynAmpArray[0] - this.gendynAmp0) / this.gendynTimeArray[0]);
    double d;
    for (this.jindex = 0; this.jindex < (int)this.gendynTimeArray[0]; this.jindex += 1) {
      switch (this.gendynInterpolation)
      {
      case 2: 
        d = (1.0D - (Math.cos(this.jindex / this.gendynTimeArray[0] * 3.14D) / 2.0D + 0.5D)) * (this.gendynAmpArray[0] - this.gendynAmp0);
        this.gendynIntArray[(this.mgaCounter++)] = ((int)((this.gendynAmp0 + d) / 100.0D * this.gendynAmpGranularity));
        break;
      case 1: 
        this.gendynIntArray[(this.mgaCounter++)] = ((int)((this.gendynAmp0 + this.mgaInc * this.jindex) / 100.0D * this.gendynAmpGranularity));
        break;
      case 3: 
        this.gendynIntArray[(this.mgaCounter++)] = ((int)(this.gendynAmp0 / 100.0D * this.gendynAmpGranularity));
      }
    }
    for (this.index = 1; this.index < this.gendynPointSize - 1; this.index += 1)
    {
      this.mgaInc = ((this.gendynAmpArray[this.index] - this.gendynAmpArray[(this.index - 1)]) / this.gendynTimeArray[this.index]);
      for (this.jindex = 0; this.jindex < (int)this.gendynTimeArray[this.index]; this.jindex += 1) {
        switch (this.gendynInterpolation)
        {
        case 2: 
          d = (1.0D - (Math.cos(this.jindex / this.gendynTimeArray[this.index] * 3.14D) / 2.0D + 0.5D)) * (this.gendynAmpArray[this.index] - this.gendynAmpArray[(this.index - 1)]);
          this.gendynIntArray[(this.mgaCounter++)] = ((int)((this.gendynAmpArray[(this.index - 1)] + d) / 100.0D * this.gendynAmpGranularity));
          break;
        case 1: 
          this.gendynIntArray[(this.mgaCounter++)] = ((int)((this.gendynAmpArray[(this.index - 1)] + this.mgaInc * this.jindex) / 100.0D * this.gendynAmpGranularity));
          break;
        case 3: 
          this.gendynIntArray[(this.mgaCounter++)] = ((int)(this.gendynAmpArray[(this.index - 1)] / 100.0D * this.gendynAmpGranularity));
        }
      }
    }
    this.gendynAmp0 = this.gendynAmpArray[(this.gendynPointSize - 1)];
    this.gendynIntArrayCounter = 0;
    if (this.pointSizeReset) {
      resetPointSize();
    }
    if (this.gendynGranularityUpdate) {
      updateGranularity();
    }
  }
  
  private double randWalk(double paramDouble1, double paramDouble2, int paramInt, boolean paramBoolean)
  {
    this.rwNewVal = 0.0D;
    if (this.gendynGaussian) {
      this.rwNewVal = (paramDouble1 + this.RandomGenerator.nextGaussian() * paramDouble2);
    } else {
      this.rwNewVal = (paramDouble1 + (this.RandomGenerator.nextDouble() * paramDouble2 * 2.0D - paramDouble2));
    }
    if (paramBoolean)
    {
      if (paramDouble2 == 0.0D) {
        this.rwNewVal = paramDouble1;
      } else {
        while ((this.rwNewVal > paramInt) || (this.rwNewVal < 0.0D))
        {
          if (this.rwNewVal > paramInt) {
            this.rwNewVal = (paramInt - (this.rwNewVal - paramInt));
          }
          if (this.rwNewVal < 0.0D) {
            this.rwNewVal = (this.rwNewVal / 2.0D * -1.0D);
          }
        }
      }
      if (this.rwNewVal < 0.0D) {
        this.rwNewVal = 0.0D;
      }
    }
    else
    {
      int i = this.mirrorMax - paramInt;
      while ((this.rwNewVal > paramInt) || (this.rwNewVal < i))
      {
        if (this.rwNewVal > paramInt) {
          this.rwNewVal = (paramInt - (this.rwNewVal - paramInt));
        }
        if (this.rwNewVal < i) {
          this.rwNewVal = (i + (i - this.rwNewVal));
        }
      }
      if (this.rwNewVal < 0.0D) {
        this.rwNewVal = 0.0D;
      }
    }
    return this.rwNewVal;
  }
  
  public void setNoiseDensity(int paramInt)
  {
    this.noiseDensity = paramInt;
  }
  
  public void setStandardDeviation(double paramDouble)
  {
    this.standardDeviation = paramDouble;
  }
  
  public void setMean(double paramDouble)
  {
    this.mean = paramDouble;
  }
  
  public void setWalkStepSize(double paramDouble)
  {
    if (paramDouble > 0.0D) {
      this.walkStepSize = ((float)paramDouble);
    } else {
      System.err.println("Walk step size must be greater than zero.");
    }
  }
  
  public void setWalkMax(double paramDouble)
  {
    if (paramDouble > 0.0D) {
      this.walkMax = ((float)paramDouble);
    } else {
      System.err.println("Walk maximum value must be greater than zero.");
    }
  }
  
  public void setWalkMin(double paramDouble)
  {
    if (paramDouble < 0.0D) {
      this.walkMin = ((float)paramDouble);
    } else {
      System.err.println("Walk minimum value must be less than zero.");
    }
  }
  
  public void setWalkNoiseDensity(int paramInt)
  {
    if (paramInt > 0) {
      this.walkNoiseDensity = paramInt;
    } else {
      System.err.println("walkNoiseDensity must be greater than zero.");
    }
  }
  
  public void setWalkVaryDensity(boolean paramBoolean)
  {
    this.walkVaryDensity = paramBoolean;
  }
  
  public void setWalkNoiseDensityMin(int paramInt)
  {
    if (paramInt > 0) {
      this.walkNoiseDensityMin = paramInt;
    } else {
      System.err.println("walkNoiseDensityMin must be greater than zero.");
    }
  }
  
  public void setWalkNoiseDensityMax(int paramInt)
  {
    if (paramInt > 0) {
      this.walkNoiseDensityMax = paramInt;
    } else {
      System.err.println("walkNoiseDensityMax must be greater than zero.");
    }
  }
  
  public void setWalkNoiseDensityStepSize(int paramInt)
  {
    if (paramInt > 0) {
      this.walkNoiseDensityStepSize = paramInt;
    } else {
      System.err.println("walkNoiseDensityMax must be greater than zero.");
    }
  }
  
  public void setGendynTimeMirror(double paramDouble)
  {
    if ((paramDouble > 1.0D) && (paramDouble <= 100.0D)) {
      this.gendynTimeMirror = ((int)paramDouble);
    } else {
      System.err.println("GendynTimeMirror must be between 3 and 100, not " + paramDouble);
    }
  }
  
  public void setGendynAmpMirror(double paramDouble)
  {
    if ((paramDouble > 0.0D) && (paramDouble <= 100.0D)) {
      this.gendynAmpMirror = ((int)paramDouble);
    } else {
      System.err.println("GendynAmpMirror must be between 1 and 100, not " + paramDouble);
    }
  }
  
  public double getGendynAmp0()
  {
    return this.gendynAmp0;
  }
  
  public int getGendynPointSize()
  {
    return this.gendynPointSize;
  }
  
  public void setGendynPointSize(int paramInt)
  {
    this.pointSizeReset = true;
    this.newPointSize = paramInt;
  }
  
  private void resetPointSize()
  {
    this.gendynPointSize = this.newPointSize;
    this.gendynAmpArray = new double[this.gendynPointSize];
    this.gendynTimeArray = new double[this.gendynPointSize];
    for (int i = 0; i < this.gendynPointSize; i++)
    {
      this.gendynAmpArray[i] = 50.0D;
      this.gendynTimeArray[i] = 30.0D;
    }
    if (getGendynAmpStepSize() < 3.0D) {
      setGendynAmpStepSize(3);
    }
    this.pointSizeReset = false;
  }
  
  public double getGendynAmpArray(int paramInt)
  {
    return this.gendynAmpArray[paramInt];
  }
  
  public double getGendynTimeArray(int paramInt)
  {
    return this.gendynTimeArray[paramInt];
  }
  
  public double getGendynAmpStepSize()
  {
    return this.gendynAmpStepSize;
  }
  
  public double getGendynTimeStepSize()
  {
    return this.gendynTimeStepSize;
  }
  
  public void setGendynAmpStepSize(int paramInt)
  {
    if (paramInt >= 0) {
      this.gendynAmpStepSize = paramInt;
    }
  }
  
  public void setMaxGendynAmpStepSize(int paramInt)
  {
    if (paramInt >= 0) {
      this.maxGendynAmpStepSize = paramInt;
    }
  }
  
  public void setGendynTimeStepSize(double paramDouble)
  {
    if (paramDouble >= 0.0D) {
      this.gendynTimeStepSize = paramDouble;
    }
  }
  
  public void setMaxGendynTimeStepSize(int paramInt)
  {
    if (paramInt >= 0) {
      this.maxGendynTimeStepSize = paramInt;
    }
  }
  
  public void setGendynPrimaryAmpStepSize(int paramInt)
  {
    if (paramInt >= 0) {
      this.gendynPrimaryAmpStepSize = paramInt;
    }
  }
  
  public void setGendynPrimaryTimeStepSize(int paramInt)
  {
    if (paramInt >= 0) {
      this.gendynPrimaryTimeStepSize = paramInt;
    }
  }
  
  public void setGendynAmpGranularity(int paramInt)
  {
    this.gendynGranularityUpdate = true;
    this.tempGendynGranularity = paramInt;
  }
  
  private void updateGranularity()
  {
    if (this.tempGendynGranularity > 0) {
      this.gendynAmpGranularity = this.tempGendynGranularity;
    }
  }
  
  public void setGendynPrimaryTimeMirror(int paramInt)
  {
    if (paramInt >= 0) {
      this.gendynPrimaryTimeMirror = paramInt;
    }
  }
  
  public void setGendynPrimaryAmpMirror(int paramInt)
  {
    if (paramInt >= 0) {
      this.gendynPrimaryAmpMirror = paramInt;
    }
  }
  
  public void setGendynInterpolation(int paramInt)
  {
    this.gendynInterpolation = paramInt;
  }
  
  public int getGendynInterpolation()
  {
    return this.gendynInterpolation;
  }
  
  public void setGendynGaussian(boolean paramBoolean)
  {
    this.gendynGaussian = paramBoolean;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Noise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */