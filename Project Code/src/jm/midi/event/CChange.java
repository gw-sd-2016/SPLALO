package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import jm.midi.MidiUtil;

public final class CChange
  implements VoiceEvt, Cloneable
{
  private final short id = 3;
  private short controllerNum;
  private short value;
  private short midiChannel;
  private int time;
  
  public CChange()
  {
    this.controllerNum = 0;
    this.value = 0;
    this.midiChannel = 0;
    this.time = 0;
  }
  
  public CChange(short paramShort1, short paramShort2, short paramShort3, int paramInt)
  {
    this.controllerNum = paramShort1;
    this.value = paramShort2;
    this.midiChannel = paramShort3;
    this.time = paramInt;
  }
  
  public short getControllerNum()
  {
    return this.controllerNum;
  }
  
  public void setControllerNum(short paramShort)
  {
    this.controllerNum = paramShort;
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
    return 3;
  }
  
  public int write(DataOutputStream paramDataOutputStream)
    throws IOException
  {
    int i = MidiUtil.writeVarLength(this.time, paramDataOutputStream);
    paramDataOutputStream.writeByte((byte)(176 + this.midiChannel));
    paramDataOutputStream.writeByte((byte)this.controllerNum);
    paramDataOutputStream.writeByte((byte)this.value);
    return i + 3;
  }
  
  public int read(DataInputStream paramDataInputStream)
    throws IOException
  {
    this.controllerNum = ((short)paramDataInputStream.readUnsignedByte());
    this.value = ((short)paramDataInputStream.readUnsignedByte());
    return 2;
  }
  
  public Event copy()
    throws CloneNotSupportedException
  {
    CChange localCChange;
    try
    {
      localCChange = (CChange)clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException);
      localCChange = new CChange();
    }
    return localCChange;
  }
  
  public void print()
  {
    System.out.println("Contol Change(003):\t\t\t [time = " + this.time + "][midiChannel = " + this.midiChannel + "][contoller_num = " + this.controllerNum + "][value = " + this.value + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\CChange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */