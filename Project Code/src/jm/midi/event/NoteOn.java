package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import jm.midi.MidiUtil;

public final class NoteOn
  implements VoiceEvt, Cloneable
{
  private final short id = 5;
  private short pitch;
  private short velocity;
  private short midiChannel;
  private int time;
  
  public NoteOn()
  {
    this.pitch = 0;
    this.velocity = 0;
    this.midiChannel = 0;
    this.time = 0;
  }
  
  public NoteOn(short paramShort1, short paramShort2, short paramShort3, int paramInt)
  {
    this.pitch = paramShort1;
    this.velocity = paramShort2;
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
  
  public short getVelocity()
  {
    return this.velocity;
  }
  
  public void setVelocity(short paramShort)
  {
    this.velocity = paramShort;
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
    return 5;
  }
  
  public Event copy()
    throws CloneNotSupportedException
  {
    NoteOn localNoteOn;
    try
    {
      localNoteOn = (NoteOn)clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException);
      localNoteOn = new NoteOn();
    }
    return localNoteOn;
  }
  
  public int write(DataOutputStream paramDataOutputStream)
    throws IOException
  {
    int i = MidiUtil.writeVarLength(this.time, paramDataOutputStream);
    paramDataOutputStream.writeByte((byte)(144 + this.midiChannel));
    paramDataOutputStream.writeByte((byte)this.pitch);
    paramDataOutputStream.writeByte((byte)this.velocity);
    return i + 3;
  }
  
  public int read(DataInputStream paramDataInputStream)
    throws IOException
  {
    this.pitch = ((short)paramDataInputStream.readUnsignedByte());
    this.velocity = ((short)paramDataInputStream.readUnsignedByte());
    return 2;
  }
  
  public void print()
  {
    System.out.println("Note On(005): [time = " + this.time + "][midiChannel = " + this.midiChannel + "][pitch = " + this.pitch + "][velocity = " + this.velocity + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\NoteOn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */