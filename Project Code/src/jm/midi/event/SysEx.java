package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

public final class SysEx
  implements SysComEvt
{
  private Vector message = new Vector();
  private int time;
  private final short id = 8;
  
  public SysEx() {}
  
  public void addToList(byte paramByte) {}
  
  public Vector getList()
  {
    return this.message;
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
    return 8;
  }
  
  public int write(DataOutputStream paramDataOutputStream)
    throws IOException
  {
    return 0;
  }
  
  public int read(DataInputStream paramDataInputStream)
    throws IOException
  {
    return 0;
  }
  
  public Event copy()
    throws CloneNotSupportedException
  {
    SysEx localSysEx = null;
    try
    {
      localSysEx = (SysEx)clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException);
      localSysEx = new SysEx();
    }
    return localSysEx;
  }
  
  public void print()
  {
    System.out.println("System Exclusive(010): [time =" + this.time + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\SysEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */