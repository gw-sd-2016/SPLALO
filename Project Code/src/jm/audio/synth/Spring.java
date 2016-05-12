package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;

public class Spring
  extends AudioObject
{
  private SpringPipe springNetwork;
  private int numberOfNodes = 8;
  private double springConstant;
  private double massFriction;
  private double jitter;
  
  public Spring(Instrument paramInstrument, int paramInt)
  {
    this(paramInstrument, paramInt, 44100.0D, 1.0D);
  }
  
  public Spring(Instrument paramInstrument, int paramInt, double paramDouble1, double paramDouble2)
  {
    this(paramInstrument, paramInt, paramDouble1, paramDouble2, 0.0D, 44100, 1);
  }
  
  public Spring(Instrument paramInstrument, int paramInt1, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt2, int paramInt3)
  {
    super(paramInstrument, paramInt2, "[WaveTable]");
    this.numberOfNodes = paramInt1;
    this.springConstant = paramDouble1;
    this.jitter = paramDouble3;
    this.massFriction = paramDouble2;
    this.channels = paramInt3;
  }
  
  public void build()
  {
    this.springNetwork = new SpringPipe(this.numberOfNodes, this.springConstant, this.massFriction, this.jitter);
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = 0;
    while (i < paramArrayOfFloat.length) {
      for (int j = 0; j < this.channels; j++) {
        paramArrayOfFloat[(i++)] = ((float)(this.springNetwork.getNextNodePosition(0) / (40 / this.numberOfNodes) - 3.0D));
      }
    }
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Spring.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */