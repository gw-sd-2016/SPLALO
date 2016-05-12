package jm.audio.synth;

import jm.JMC;
import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;
import jm.music.data.Note;

public class Envelope
  extends AudioObject
  implements JMC
{
  private EnvPoint[] graphPoints;
  private float[] graphShape;
  private int position = 1;
  private boolean primary;
  private boolean useNotePoints = false;
  private int notePointIndex;
  
  public Envelope(Instrument paramInstrument, int paramInt1, int paramInt2, EnvPoint[] paramArrayOfEnvPoint)
  {
    super(paramInstrument, paramInt1, "[Envelope]");
    this.channels = paramInt2;
    this.graphPoints = paramArrayOfEnvPoint;
    this.primary = true;
  }
  
  public Envelope(Instrument paramInstrument, int paramInt1, int paramInt2, double[] paramArrayOfDouble)
  {
    super(paramInstrument, paramInt1, "[Envelope]");
    this.channels = paramInt2;
    breakPointsToGraphPoints(paramArrayOfDouble);
    this.primary = true;
  }
  
  public Envelope(Instrument paramInstrument, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInstrument, paramInt1, "[Envelope]");
    this.channels = paramInt2;
    this.useNotePoints = true;
    this.notePointIndex = paramInt3;
    this.primary = true;
  }
  
  public Envelope(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[Envelope]");
    this.useNotePoints = true;
    this.notePointIndex = 0;
    this.primary = false;
  }
  
  public Envelope(AudioObject paramAudioObject, int paramInt)
  {
    super(paramAudioObject, "[Envelope]");
    this.useNotePoints = true;
    this.notePointIndex = paramInt;
    this.primary = false;
  }
  
  public Envelope(AudioObject paramAudioObject, EnvPoint[] paramArrayOfEnvPoint)
  {
    super(paramAudioObject, "[Envelope]");
    this.graphPoints = paramArrayOfEnvPoint;
    this.primary = false;
  }
  
  public Envelope(AudioObject paramAudioObject, double[] paramArrayOfDouble)
  {
    super(paramAudioObject, "[Envelope]");
    breakPointsToGraphPoints(paramArrayOfDouble);
    this.primary = false;
  }
  
  public void setBreakPoints(double[] paramArrayOfDouble)
  {
    breakPointsToGraphPoints(paramArrayOfDouble);
  }
  
  private void breakPointsToGraphPoints(double[] paramArrayOfDouble)
  {
    this.graphPoints = new EnvPoint[paramArrayOfDouble.length / 2];
    int i = 0;
    for (int j = 0; j < paramArrayOfDouble.length / 2; j++)
    {
      this.graphPoints[j] = new EnvPoint((float)paramArrayOfDouble[i], (float)paramArrayOfDouble[(i + 1)]);
      i += 2;
    }
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    if ((this.finished == true) && (this.inst.iterations <= 0)) {
      return paramArrayOfFloat.length;
    }
    if (this.primary)
    {
      i = paramArrayOfFloat.length;
      j = 1;
      for (k = 0; k < i; k++)
      {
        try
        {
          paramArrayOfFloat[k] = this.graphShape[this.position];
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1)
        {
          paramArrayOfFloat[k] = 0.0F;
        }
        if (j == this.channels)
        {
          j = 1;
          this.position += 1;
        }
        else
        {
          j++;
        }
      }
      return i;
    }
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    int j = 1;
    for (int k = 0; k < i; k++)
    {
      try
      {
        paramArrayOfFloat[k] *= this.graphShape[this.position];
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2)
      {
        paramArrayOfFloat[k] = 0.0F;
      }
      if (j == this.channels)
      {
        j = 1;
        this.position += 1;
      }
      else
      {
        j++;
      }
    }
    return i;
  }
  
  public void build()
  {
    if (this.useNotePoints) {
      breakPointsToGraphPoints(this.currentNote.getBreakPoints(this.notePointIndex));
    }
    this.graphShape = new float[this.numOfSamples];
    if (this.numOfSamples <= this.graphPoints.length * 4)
    {
      for (i = 0; i < this.graphShape.length; i++) {
        this.graphShape[i] = 1.0F;
      }
      return;
    }
    if (this.graphPoints[0].x != -1.0D) {
      for (i = 0; i < this.graphPoints.length; i++)
      {
        this.graphPoints[i].X = ((int)(this.numOfSamples * this.graphPoints[i].x));
        this.graphPoints[i].X -= 1;
      }
    }
    int i = 0;
    for (int j = 0; j < this.graphPoints.length - 1; j++)
    {
      float f1 = (this.graphPoints[j].y - this.graphPoints[(j + 1)].y) / (this.graphPoints[j].X - this.graphPoints[(j + 1)].X);
      float f2 = this.graphPoints[(j + 1)].y - f1 * this.graphPoints[(j + 1)].X;
      for (;;)
      {
        this.graphShape[i] = (f1 * i + f2);
        if (i >= this.graphPoints[(j + 1)].X) {
          break;
        }
        i++;
      }
    }
    this.position = 0;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Envelope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */