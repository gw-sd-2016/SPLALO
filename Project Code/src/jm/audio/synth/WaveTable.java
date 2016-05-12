package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;
import jm.music.data.Note;

public class WaveTable
  extends AudioObject
{
  private float[] waveTable;
  private float si;
  private float phase;
  private int aoDestination;
  private float amp = 1.0F;
  private float frq = -1.0F;
  private float frqRatio = 1.0F;
  public static final int AMPLITUDE = 0;
  public static final int FREQUENCY = 1;
  public static final int MONO = 1;
  public static final int STEREO = 2;
  
  public WaveTable(AudioObject[] paramArrayOfAudioObject, float[] paramArrayOfFloat)
    throws AOException
  {
    super(paramArrayOfAudioObject, "[WaveTable]");
    if (paramArrayOfAudioObject.length > 2) {
      throw new AOException(this.name, 1);
    }
    this.waveTable = paramArrayOfFloat;
  }
  
  public WaveTable(AudioObject paramAudioObject, float[] paramArrayOfFloat, int paramInt)
  {
    super(paramAudioObject, "[WaveTable]");
    this.waveTable = paramArrayOfFloat;
    this.aoDestination = paramInt;
  }
  
  public WaveTable(Instrument paramInstrument, int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3, float paramFloat)
  {
    super(paramInstrument, paramInt1, "[WaveTable]");
    this.waveTable = paramArrayOfFloat;
    this.channels = paramInt2;
    this.aoDestination = paramInt3;
    if (paramInt3 == 1) {
      this.frq = paramFloat;
    } else {
      this.amp = paramFloat;
    }
  }
  
  public WaveTable(Instrument paramInstrument, int paramInt1, float[] paramArrayOfFloat, int paramInt2)
  {
    super(paramInstrument, paramInt1, "[WaveTable]");
    this.waveTable = paramArrayOfFloat;
    this.channels = paramInt2;
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
        setSI((int)arrayOfFloat2[n]);
        if (this.phase < 0.0F) {
          this.phase = (this.waveTable.length + this.phase);
        }
        float f3 = this.waveTable[((int)this.phase)] * (this.amp * arrayOfFloat1[n]);
        this.phase += this.si;
        if (this.phase >= this.waveTable.length) {
          this.phase -= this.waveTable.length;
        }
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
      if ((this.inputs == 1) && (this.aoDestination == 0))
      {
        arrayOfFloat1 = new float[i];
        k = this.previous[0].nextWork(arrayOfFloat1);
        for (m = 0; j < paramArrayOfFloat.length; m++)
        {
          f2 = this.waveTable[((int)this.phase)] * (this.amp * arrayOfFloat1[m]);
          this.phase += this.si;
          if (this.phase >= this.waveTable.length) {
            this.phase -= this.waveTable.length;
          }
          for (i1 = 0; i1 < this.channels; i1++) {
            paramArrayOfFloat[(j++)] = f2;
          }
        }
      }
      else if ((this.inputs == 1) && (this.aoDestination == 1))
      {
        arrayOfFloat1 = new float[i];
        k = this.previous[0].work(arrayOfFloat1);
        for (m = 0; m < i; m++)
        {
          setSI((int)arrayOfFloat1[m]);
          if (this.phase < 0.0F) {
            this.phase = (this.waveTable.length + this.phase);
          }
          f2 = this.waveTable[((int)this.phase)] * this.amp;
          this.phase += this.si;
          if (this.phase >= this.waveTable.length) {
            this.phase -= this.waveTable.length;
          }
          for (i1 = 0; i1 < this.channels; i1++) {
            paramArrayOfFloat[(j++)] = f2;
          }
        }
      }
      else
      {
        while (j < paramArrayOfFloat.length)
        {
          float f1 = this.waveTable[((int)this.phase)] * this.amp;
          this.phase += this.si;
          if (this.phase >= this.waveTable.length) {
            this.phase -= this.waveTable.length;
          }
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
    float f = (float)this.currentNote.getFrequency() * this.frqRatio;
    if (this.frq < 0.0F) {
      setSI(f);
    } else {
      setSI(this.frq);
    }
  }
  
  public void setAmp(float paramFloat)
  {
    this.amp = paramFloat;
  }
  
  public void setFrq(float paramFloat)
  {
    this.frq = paramFloat;
  }
  
  public void setFrqRatio(float paramFloat)
  {
    this.frqRatio = paramFloat;
  }
  
  protected void setSI(float paramFloat)
  {
    this.si = (paramFloat / this.sampleRate * this.waveTable.length);
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\WaveTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */