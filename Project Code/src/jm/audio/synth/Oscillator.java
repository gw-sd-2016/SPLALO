package jm.audio.synth;

import java.io.PrintStream;
import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;
import jm.music.data.Note;

public class Oscillator
  extends AudioObject
{
  public static final int SINE_WAVE = 0;
  public static final int COSINE_WAVE = 1;
  public static final int TRIANGLE_WAVE = 2;
  public static final int SQUARE_WAVE = 3;
  public static final int SAWTOOTH_WAVE = 4;
  public static final int SAWDOWN_WAVE = 5;
  public static final int SABERSAW_WAVE = 6;
  public static final int PULSE_WAVE = 7;
  public static final int AMPLITUDE = 0;
  public static final int FREQUENCY = 1;
  private float si;
  private float phase;
  private int choice;
  private float amp = 1.0F;
  private float frq = -1.0F;
  private float frqRatio = 1.0F;
  private int waveType = 0;
  private double pulseWidth = 0.15D;
  
  public Oscillator(AudioObject[] paramArrayOfAudioObject)
    throws AOException
  {
    super(paramArrayOfAudioObject, "[Oscillator]");
    if (paramArrayOfAudioObject.length > 2) {
      throw new AOException(this.name, 1);
    }
  }
  
  public Oscillator(AudioObject paramAudioObject, int paramInt1, int paramInt2)
  {
    super(paramAudioObject, "[Oscillator]");
    this.waveType = paramInt1;
    this.choice = paramInt2;
  }
  
  public Oscillator(AudioObject paramAudioObject, int paramInt1, int paramInt2, double paramDouble)
  {
    super(paramAudioObject, "[Oscillator]");
    this.waveType = paramInt1;
    this.choice = paramInt2;
    if (paramInt2 == 1) {
      this.frq = ((float)paramDouble);
    } else {
      this.amp = ((float)paramDouble);
    }
  }
  
  public Oscillator(Instrument paramInstrument)
  {
    this(paramInstrument, 0);
  }
  
  public Oscillator(Instrument paramInstrument, int paramInt)
  {
    this(paramInstrument, paramInt, 44100);
  }
  
  public Oscillator(Instrument paramInstrument, int paramInt1, int paramInt2)
  {
    this(paramInstrument, paramInt1, paramInt2, 1);
  }
  
  public Oscillator(Instrument paramInstrument, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInstrument, paramInt2, "[Oscillator]");
    this.waveType = paramInt1;
    this.channels = paramInt3;
  }
  
  public Oscillator(Instrument paramInstrument, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble)
  {
    super(paramInstrument, paramInt2, "[Oscillator]");
    this.waveType = paramInt1;
    this.channels = paramInt3;
    this.choice = paramInt4;
    if (this.choice == 1) {
      this.frq = ((float)paramDouble);
    } else {
      this.amp = ((float)paramDouble);
    }
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = paramArrayOfFloat.length / this.channels;
    int j = 0;
    float[] arrayOfFloat1;
    int k;
    if (this.inputs == 2)
    {
      arrayOfFloat1 = new float[i];
      k = this.previous[0].nextWork(arrayOfFloat1);
      float[] arrayOfFloat2 = new float[k];
      if (k != this.previous[1].work(arrayOfFloat2)) {
        throw new AOException(this.name, 0);
      }
      for (int n = 0; j < paramArrayOfFloat.length; n++)
      {
        setSI(arrayOfFloat2[n] * this.frqRatio);
        float f3 = getWaveSample() * this.amp * arrayOfFloat1[n];
        for (int i2 = 0; i2 < this.channels; i2++) {
          paramArrayOfFloat[(j++)] = f3;
        }
      }
    }
    else
    {
      int m;
      float f2;
      int i1;
      if ((this.inputs == 1) && (this.choice == 0))
      {
        arrayOfFloat1 = new float[i];
        k = this.previous[0].nextWork(arrayOfFloat1);
        for (m = 0; j < paramArrayOfFloat.length; m++)
        {
          f2 = getWaveSample() * this.amp * arrayOfFloat1[m];
          for (i1 = 0; i1 < this.channels; i1++) {
            paramArrayOfFloat[(j++)] = f2;
          }
        }
      }
      else if ((this.inputs == 1) && (this.choice == 1))
      {
        arrayOfFloat1 = new float[i];
        k = this.previous[0].work(arrayOfFloat1);
        for (m = 0; m < i; m++)
        {
          setSI(arrayOfFloat1[m] * this.frqRatio);
          f2 = getWaveSample() * this.amp;
          for (i1 = 0; i1 < this.channels; i1++) {
            paramArrayOfFloat[(j++)] = f2;
          }
        }
      }
      else
      {
        while (j < paramArrayOfFloat.length)
        {
          if (this.choice == 1) {
            setSI(this.frq * this.frqRatio);
          }
          float f1 = getWaveSample() * this.amp;
          for (k = 0; k < this.channels; k++) {
            try
            {
              paramArrayOfFloat[(j++)] = f1;
            }
            catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
            {
              j--;
            }
          }
        }
      }
    }
    return j;
  }
  
  public void build()
  {
    if (this.frq < 0.0F)
    {
      float f = (float)this.currentNote.getFrequency();
      f *= this.frqRatio;
      setSI(f);
    }
    else
    {
      this.frq *= this.frqRatio;
      setSI(this.frq);
    }
  }
  
  public void setChoice(int paramInt)
  {
    this.choice = paramInt;
  }
  
  public void setAmp(float paramFloat)
  {
    this.amp = paramFloat;
    this.choice = 0;
  }
  
  public float getAmp()
  {
    return this.amp;
  }
  
  public void setFrq(float paramFloat)
  {
    this.frq = paramFloat;
    this.choice = 1;
  }
  
  public void setFrqRatio(double paramDouble)
  {
    this.frqRatio = ((float)paramDouble);
  }
  
  protected void setSI(double paramDouble)
  {
    this.si = (6.2831855F / (this.sampleRate / (float)paramDouble));
  }
  
  protected float getWaveSample()
  {
    float f1;
    float f2;
    switch (this.waveType)
    {
    case 0: 
      if (this.phase < 0.0F) {
        this.phase += 6.2831855F;
      }
      f1 = (float)Math.sin(this.phase + 6.2831855F);
      this.phase += this.si;
      if (this.phase >= 6.2831855F) {
        this.phase -= 6.2831855F;
      }
      return f1;
    case 1: 
      if (this.phase < 0.0F) {
        this.phase += 6.2831855F;
      }
      f1 = (float)Math.cos(this.phase + 6.2831855F);
      this.phase += this.si;
      if (this.phase >= 6.2831855F) {
        this.phase -= 6.2831855F;
      }
      return f1;
    case 2: 
      f1 = 0.0F;
      if (this.phase < 0.0F) {
        this.phase += 6.2831855F;
      }
      f2 = 0.15915494F * this.phase;
      if (f2 <= 0.25F) {
        f1 = (float)(f2 * 4.0D);
      }
      if ((f2 > 0.25F) && (f2 <= 0.75F)) {
        f1 = (float)(4.0D * (0.5D - f2));
      }
      if (f2 > 0.75F) {
        f1 = (float)((f2 - 1.0D) * 4.0D);
      }
      this.phase += this.si;
      if (this.phase >= 6.2831855F) {
        this.phase -= 6.2831855F;
      }
      return f1;
    case 3: 
      f1 = 0.0F;
      if (this.phase < 0.0F) {
        this.phase += 6.2831855F;
      }
      f2 = 0.15915494F * this.phase;
      if (f2 < 0.5F) {
        f1 = 1.0F;
      } else {
        f1 = -1.0F;
      }
      this.phase += this.si;
      if (this.phase >= 6.2831855F) {
        this.phase -= 6.2831855F;
      }
      return f1;
    case 4: 
      if (this.phase < 0.0F) {
        this.phase += 6.2831855F;
      }
      f2 = 0.31830987F * this.phase;
      f1 = (float)(f2 - 1.0D);
      this.phase += this.si;
      if (this.phase >= 6.2831855F) {
        this.phase -= 6.2831855F;
      }
      return f1;
    case 5: 
      if (this.phase < 0.0F) {
        this.phase += 6.2831855F;
      }
      f2 = 0.31830987F * this.phase;
      f1 = (float)(1.0D - f2);
      this.phase += this.si;
      if (this.phase >= 6.2831855F) {
        this.phase -= 6.2831855F;
      }
      return f1;
    case 6: 
      if (this.phase < 0.0F) {
        this.phase += 6.2831855F;
      }
      f2 = 0.15915494F * this.phase;
      f1 = (float)Math.exp(f2) - 2.0F;
      this.phase += this.si;
      if (this.phase >= 6.2831855F) {
        this.phase -= 6.2831855F;
      }
      return f1;
    case 7: 
      f1 = 0.0F;
      if (this.phase < 0.0F) {
        this.phase += 6.2831855F;
      }
      f2 = 0.15915494F * this.phase;
      if (f2 < (float)this.pulseWidth) {
        f1 = 1.0F;
      } else {
        f1 = -1.0F;
      }
      this.phase += this.si;
      if (this.phase >= 6.2831855F) {
        this.phase -= 6.2831855F;
      }
      return f1;
    }
    System.err.println("Incorrect oscillator type selected.");
    System.exit(1);
    return 0.0F;
  }
  
  public void setPulseWidth(double paramDouble)
  {
    if ((paramDouble >= 0.0D) && (paramDouble <= 1.0D)) {
      this.pulseWidth = paramDouble;
    } else {
      System.err.println("Pulse wide must be between 0.0 and 1.0");
    }
  }
  
  public void setPhase(double paramDouble)
  {
    this.phase = ((float)paramDouble);
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Oscillator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */