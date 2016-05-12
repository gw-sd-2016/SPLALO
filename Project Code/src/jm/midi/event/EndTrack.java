package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import jm.midi.MidiUtil;

public final class EndTrack
  implements Event
{
  private final short id = 23;
  private int time = 0;
  
  public EndTrack() {}
  
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
    paramDataOutputStream.writeByte(47);
    paramDataOutputStream.writeByte(0);
    return i + 2;
  }
  
  public int read(DataInputStream paramDataInputStream)
    throws IOException
  {
    return 0;
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
    System.out.println("EndTrack(023):             [time = " + this.time + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\EndTrack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */