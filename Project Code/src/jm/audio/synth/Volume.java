package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.music.data.Note;

public final class Volume
  extends AudioObject
{
  float mainVolume = 1.0F;
  float volume = 1.0F;
  double linearVolumeValue = 1.0D;
  private int returned;
  private int index;
  private float[] tmp;
  
  public Volume(AudioObject paramAudioObject)
  {
    this(paramAudioObject, 1.0F);
  }
  
  public Volume(AudioObject paramAudioObject, double paramDouble)
  {
    this(paramAudioObject, (float)paramDouble);
  }
  
  public Volume(AudioObject paramAudioObject, float paramFloat)
  {
    super(paramAudioObject, "[Volume]");
    this.mainVolume = paramFloat;
  }
  
  public void build()
  {
    this.linearVolumeValue = (this.currentNote.getDynamic() / 127.0D);
    this.volume = ((float)(1.0D - Math.log(128.0D - this.currentNote.getDynamic()) * 0.2D) * this.mainVolume);
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    this.returned = this.previous[0].nextWork(paramArrayOfFloat);
    if (this.inputs == 2)
    {
      if ((this.tmp == null) || (this.tmp.length != paramArrayOfFloat.length)) {
        this.tmp = new float[paramArrayOfFloat.length];
      } else {
        for (this.index = 0; this.index < this.tmp.length; this.index += 1) {
          this.tmp[this.index] = 0.0F;
        }
      }
      if (this.returned != this.previous[1].nextWork(this.tmp)) {
        throw new AOException(this.name, 0);
      }
      for (this.index = 0; this.index < this.returned; this.index += 1) {
        paramArrayOfFloat[this.index] *= this.tmp[this.index];
      }
    }
    for (this.index = 0; this.index < this.returned; this.index += 1) {
      paramArrayOfFloat[this.index] *= this.volume;
    }
    return this.returned;
  }
  
  public void setVolume(double paramDouble)
  {
    this.linearVolumeValue = paramDouble;
    this.volume = ((float)Math.min(1.0D, Math.abs(Math.log(1.0D - paramDouble) * 0.2D)));
  }
  
  public double getVolume()
  {
    return this.linearVolumeValue;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Volume.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */