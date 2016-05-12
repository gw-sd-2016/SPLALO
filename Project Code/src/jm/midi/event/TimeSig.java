package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import jm.midi.MidiUtil;

public final class TimeSig
  implements Event
{
  public static final short ID = 17;
  private int time;
  private int numerator;
  private int denominator;
  private int metronomePulse;
  private int thirtySecondNotesPerBeat;
  
  public TimeSig()
  {
    this(0, 4, 4);
  }
  
  public TimeSig(int paramInt1, int paramInt2)
  {
    this(0, paramInt1, paramInt2);
  }
  
  public TimeSig(int paramInt1, int paramInt2, int paramInt3)
  {
    this.time = paramInt1;
    this.numerator = paramInt2;
    this.denominator = paramInt3;
    this.metronomePulse = 24;
    this.thirtySecondNotesPerBeat = 32;
  }
  
  public int getDenominator()
  {
    return this.denominator;
  }
  
  public void setDenominator(int paramInt)
  {
    this.denominator = paramInt;
  }
  
  public int getNumerator()
  {
    return this.numerator;
  }
  
  public void setNumerator(int paramInt)
  {
    this.numerator = paramInt;
  }
  
  public int getMetronomePulse()
  {
    return this.metronomePulse;
  }
  
  public void setMetronomePulse(int paramInt)
  {
    this.metronomePulse = paramInt;
  }
  
  public int getThirtySecondNotesPerBeat()
  {
    return this.thirtySecondNotesPerBeat;
  }
  
  public void setThirtySecondNotesPerBeat(int paramInt)
  {
    this.thirtySecondNotesPerBeat = paramInt;
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
    return 17;
  }
  
  public int write(DataOutputStream paramDataOutputStream)
    throws IOException
  {
    int i = MidiUtil.writeVarLength(this.time, paramDataOutputStream);
    paramDataOutputStream.writeByte(255);
    paramDataOutputStream.writeByte(88);
    i += MidiUtil.writeVarLength(4, paramDataOutputStream);
    paramDataOutputStream.writeByte((byte)this.numerator);
    int j = this.denominator;
    for (int k = 0; j % 2 == 0; k++) {
      j /= 2;
    }
    paramDataOutputStream.writeByte((byte)k);
    paramDataOutputStream.writeByte(24);
    paramDataOutputStream.writeByte(8);
    return i + 6;
  }
  
  public int read(DataInputStream paramDataInputStream)
    throws IOException
  {
    this.numerator = ((short)paramDataInputStream.readUnsignedByte());
    int i = (short)paramDataInputStream.readUnsignedByte();
    this.denominator = (1 << i);
    this.metronomePulse = paramDataInputStream.readUnsignedByte();
    this.thirtySecondNotesPerBeat = paramDataInputStream.readUnsignedByte();
    return 4;
  }
  
  public Event copy()
    throws CloneNotSupportedException
  {
    TimeSig localTimeSig;
    try
    {
      localTimeSig = (TimeSig)clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException);
      localTimeSig = new TimeSig();
    }
    return localTimeSig;
  }
  
  public void print()
  {
    System.out.println("TimeSig(021):             [time = " + this.time + "][numerator = " + this.numerator + "][denominator = " + this.denominator + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\TimeSig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */