package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public final class PWheel
  implements VoiceEvt, Cloneable
{
  private final short id = 6;
  private int value;
  private short midiChannel;
  private int time;
  
  public PWheel()
  {
    this.value = 0;
    this.midiChannel = 0;
    this.time = 0;
  }
  
  public PWheel(int paramInt1, short paramShort, int paramInt2)
  {
    this.value = paramInt1;
    this.midiChannel = paramShort;
    this.time = paramInt2;
  }
  
  public int getValue()
  {
    return this.value;
  }
  
  public void setValue(int paramInt)
  {
    this.value = paramInt;
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
    return 6;
  }
  
  public int write(DataOutputStream paramDataOutputStream)
    throws IOException
  {
    return 0;
  }
  
  public int read(DataInputStream paramDataInputStream)
    throws IOException
  {
    this.value = paramDataInputStream.readUnsignedByte();
    this.value += paramDataInputStream.readUnsignedByte() * 128;
    return 1;
  }
  
  public Event copy()
    throws CloneNotSupportedException
  {
    PWheel localPWheel;
    try
    {
      localPWheel = (PWheel)clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException);
      localPWheel = new PWheel();
    }
    return localPWheel;
  }
  
  public void print()
  {
    System.out.println("Pitch Wheel(006):\t\t\t\t  [time = " + this.time + "][midiChannel = " + this.midiChannel + "][value = " + this.value + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\PWheel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */