package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import jm.midi.MidiUtil;

public final class TempoEvent
  implements Event
{
  private short id = 16;
  private int time = 0;
  private double tempo = 60.0D;
  
  public TempoEvent()
  {
    this(0, 0.0D);
  }
  
  public TempoEvent(double paramDouble)
  {
    this(0, paramDouble);
  }
  
  public TempoEvent(int paramInt, double paramDouble)
  {
    this.time = paramInt;
    this.tempo = paramDouble;
  }
  
  public double getTempo()
  {
    return this.tempo;
  }
  
  public void setTempo(double paramDouble)
  {
    this.tempo = paramDouble;
  }
  
  public int getTime()
  {
    return this.time;
  }
  
  public void setTime(int paramInt)
  {
    this.time = paramInt;
  }
  
  public short getID()
  {
    return this.id;
  }
  
  public int write(DataOutputStream paramDataOutputStream)
    throws IOException
  {
    int i = MidiUtil.writeVarLength(this.time, paramDataOutputStream);
    paramDataOutputStream.writeByte(-1);
    paramDataOutputStream.writeByte(81);
    i += MidiUtil.writeVarLength(3, paramDataOutputStream);
    int j = (int)(60.0F / (float)this.tempo * 1000000.0F);
    paramDataOutputStream.writeByte((byte)(j >> 16 & 0xFF));
    paramDataOutputStream.writeByte((byte)(j >> 8 & 0xFF));
    paramDataOutputStream.writeByte((byte)(j & 0xFF));
    return i + 5;
  }
  
  public int read(DataInputStream paramDataInputStream)
    throws IOException
  {
    int i = paramDataInputStream.readUnsignedByte();
    int j = paramDataInputStream.readUnsignedByte();
    int k = paramDataInputStream.readUnsignedByte();
    int m = (i << 16) + (j << 8) + k;
    this.tempo = (1000000.0F / m * 60.0F);
    return 3;
  }
  
  public Event copy()
    throws CloneNotSupportedException
  {
    TempoEvent localTempoEvent;
    try
    {
      localTempoEvent = (TempoEvent)clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException);
      localTempoEvent = new TempoEvent();
    }
    return localTempoEvent;
  }
  
  public void print()
  {
    System.out.println("TempoEvent(020):             [time = " + this.time + "][tempo = " + this.tempo + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\TempoEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */