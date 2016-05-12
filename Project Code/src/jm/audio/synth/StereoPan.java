package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.music.data.Note;

public final class StereoPan
  extends AudioObject
{
  private float pan;
  private int channel = 1;
  boolean panSet = false;
  
  public StereoPan(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[StereoPan]");
    this.pan = 0.5F;
  }
  
  public StereoPan(AudioObject paramAudioObject, double paramDouble)
  {
    super(paramAudioObject, "[StereoPan]");
    this.panSet = true;
    if (paramDouble < 0.0D) {
      this.pan = 0.0F;
    } else if (paramDouble > 1.0D) {
      this.pan = 1.0F;
    } else {
      this.pan = ((float)paramDouble);
    }
  }
  
  public void build()
  {
    float f = (float)this.currentNote.getPan();
    if (!this.panSet) {
      if (f < 0.0F) {
        this.pan = 0.0F;
      } else if (f > 1.0F) {
        this.pan = 1.0F;
      } else {
        this.pan = f;
      }
    }
    this.channel = 1;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].work(paramArrayOfFloat);
    if (this.channels == 1) {
      return i;
    }
    for (int j = 0; j < i; j++) {
      if (this.channel == 1)
      {
        if (this.pan > 0.5D) {
          paramArrayOfFloat[j] *= (1.0F - (this.pan - 0.5F) * 2.0F);
        }
        this.channel = 2;
      }
      else
      {
        if (this.pan < 0.5D) {
          paramArrayOfFloat[j] = (paramArrayOfFloat[j] * this.pan * 2.0F);
        }
        this.channel = 1;
      }
    }
    return i;
  }
  
  public void setPan(double paramDouble)
  {
    if ((paramDouble >= 0.0D) && (paramDouble <= 1.0D)) {
      this.pan = ((float)paramDouble);
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\StereoPan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */