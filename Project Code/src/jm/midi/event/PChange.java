package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import jm.midi.MidiUtil;

public final class PChange
  implements VoiceEvt, Cloneable
{
  private final short id = 7;
  private short value;
  private short midiChannel;
  private int time;
  
  public PChange()
  {
    this.value = 0;
    this.midiChannel = 0;
    this.time = 0;
  }
  
  public PChange(short paramShort1, short paramShort2, int paramInt)
  {
    this.value = paramShort1;
    this.midiChannel = paramShort2;
    this.time = paramInt;
  }
  
  public short getValue()
  {
    return this.value;
  }
  
  public void setValue(short paramShort)
  {
    this.value = paramShort;
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
    return 7;
  }
  
  public int write(DataOutputStream paramDataOutputStream)
    throws IOException
  {
    int i = MidiUtil.writeVarLength(this.time, paramDataOutputStream);
    paramDataOutputStream.writeByte((byte)(192 + this.midiChannel));
    paramDataOutputStream.writeByte((byte)this.value);
    return i + 2;
  }
  
  public int read(DataInputStream paramDataInputStream)
    throws IOException
  {
    this.value = ((short)paramDataInputStream.readUnsignedByte());
    return 1;
  }
  
  public Event copy()
    throws CloneNotSupportedException
  {
    PChange localPChange;
    try
    {
      localPChange = (PChange)clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException);
      localPChange = new PChange();
    }
    return localPChange;
  }
  
  public void print()
  {
    System.out.println(toString());
  }
  
  public String toString()
  {
    return new String("Program Change(007): [time = " + this.time + "][midiChannel = " + this.midiChannel + "][value = " + this.value + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\PChange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */