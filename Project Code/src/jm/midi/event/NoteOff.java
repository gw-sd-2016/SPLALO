package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public final class NoteOff
  implements VoiceEvt, Cloneable
{
  private final short id = 4;
  private short pitch;
  private short velocity;
  private short midiChannel;
  private int time;
  
  public NoteOff()
  {
    this.pitch = 0;
    this.velocity = 0;
    this.midiChannel = 0;
    this.time = 0;
  }
  
  public NoteOff(short paramShort1, short paramShort2, short paramShort3, int paramInt)
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
    return 4;
  }
  
  public Event copy()
    throws CloneNotSupportedException
  {
    NoteOff localNoteOff;
    try
    {
      localNoteOff = (NoteOff)clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException);
      localNoteOff = new NoteOff();
    }
    return localNoteOff;
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
    this.velocity = ((short)paramDataInputStream.readUnsignedByte());
    return 2;
  }
  
  public void print()
  {
    System.out.println("Note Off(004): [time = " + this.time + "][midiChannel = " + this.midiChannel + "][pitch = " + this.pitch + "][velocity = " + this.velocity + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\NoteOff.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */