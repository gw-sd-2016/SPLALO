package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public final class ATouch
  implements VoiceEvt, Cloneable
{
  private final short id = 1;
  private short pitch;
  private short pressure;
  private short midiChannel;
  private int time;
  
  public ATouch()
  {
    this.pitch = 0;
    this.pressure = 0;
    this.midiChannel = 0;
    this.time = 0;
  }
  
  public ATouch(short paramShort1, short paramShort2, short paramShort3, int paramInt)
  {
    this.pitch = paramShort1;
    this.pressure = paramShort2;
    this.midiChannel = paramShort3;
    this.time = paramInt;
  }
  
  public short getPitch()
  {
    return this.pitch;
  }
  
  public void setPitch(short paramShort)
  {
    this.pitch = paramShort;
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
    return 1;
  }
  
  public Event copy()
    throws CloneNotSupportedException
  {
    ATouch localATouch;
    try
    {
      localATouch = (ATouch)clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException);
      localATouch = new ATouch();
    }
    return localATouch;
  }
  
  public int write(DataOutputStream paramDataOutputStream)
    throws IOException
  {
    return 0;
  }
  
  public int read(DataInputStream paramDataInputStream)
    throws IOException
  {
    this.pitch = ((short)paramDataInputStream.readUnsignedByte());
    this.pressure = ((short)paramDataInputStream.readUnsignedByte());
    return 2;
  }
  
  public void print()
  {
    System.out.println("ATouch(001):    \t\t\t\t   [time = " + this.time + "][midiChannel = " + this.midiChannel + "][pitch = " + this.pitch + "][pressure = " + this.pressure + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\ATouch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */