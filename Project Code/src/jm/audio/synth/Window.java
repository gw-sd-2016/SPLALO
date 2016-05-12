package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Window
  extends AudioObject
{
  private int type;
  private boolean direction;
  
  public Window(AudioObject paramAudioObject, int paramInt, boolean paramBoolean)
  {
    super(paramAudioObject, "[Window]");
    this.type = paramInt;
    this.direction = paramBoolean;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    int j;
    if (this.direction) {
      for (j = 0; j < i; j++) {
        paramArrayOfFloat[j] *= (float)Math.sin(3.141592653589793D * j / i);
      }
    } else {
      for (j = 0; j < i; j++) {
        paramArrayOfFloat[j] /= (float)Math.sin(3.141592653589793D * j / i);
      }
    }
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Window.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */