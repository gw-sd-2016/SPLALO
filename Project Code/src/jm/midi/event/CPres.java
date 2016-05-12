package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public final class CPres
  implements VoiceEvt, Cloneable
{
  private final short id = 2;
  private short pressure;
  private short midiChannel;
  private int time;
  
  public CPres()
  {
    this.pressure = 0;
    this.midiChannel = 0;
    this.time = 0;
  }
  
  public CPres(short paramShort1, short paramShort2, int paramInt)
  {
    this.pressure = paramShort1;
    this.midiChannel = paramShort2;
    this.time = paramInt;
  }
  
  public short getPressure()
  {
    return this.pressure;
  }
  
  public void setPressure(short paramShort)
  {
    this.pressure = paramShort;
  }
  
  public short getMidiChannel()
  {
    return this.midiChannel;
  }
  
  public void setMidiChannel(short paramShort)
  {
    this.midiChannel = paramShort;
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
    return 2;
  }
  
  public int write(DataOutputStream paramDataOutputStream)
    throws IOException
  {
    return 0;
  }
  
  public int read(DataInputStream paramDataInputStream)
    throws IOException
  {
    this.pressure = ((short)paramDataInputStream.readUnsignedByte());
    return 1;
  }
  
  public Event copy()
    throws CloneNotSupportedException
  {
    CPres localCPres;
    try
    {
      localCPres = (CPres)clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException);
      localCPres = new CPres();
    }
    return localCPres;
  }
  
  public void print()
  {
    System.out.println("Channel Pressure(002):\t[time = " + this.time + "][midiChannel = " + this.midiChannel + "][pressure = " + this.pressure + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\CPres.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */