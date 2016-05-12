package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import jm.midi.MidiUtil;

public final class KeySig
  implements Event
{
  private static final short ID = 18;
  private int time;
  private int keySig;
  private int keyQual;
  
  public KeySig()
  {
    this.time = 0;
    this.keySig = 0;
    this.keyQual = 0;
  }
  
  public KeySig(int paramInt1, int paramInt2)
  {
    this.time = 0;
    this.keySig = paramInt1;
    this.keyQual = paramInt2;
  }
  
  public KeySig(int paramInt1, int paramInt2, int paramInt3)
  {
    this.time = paramInt1;
    this.keySig = paramInt2;
    this.keyQual = paramInt3;
  }
  
  public int getKeyQuality()
  {
    return this.keyQual;
  }
  
  public void setKeyQuality(int paramInt)
  {
    this.keyQual = paramInt;
  }
  
  public int getKeySig()
  {
    return this.keySig;
  }
  
  public void setKeySig(int paramInt)
  {
    this.keySig = paramInt;
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
    return 18;
  }
  
  public int write(DataOutputStream paramDataOutputStream)
    throws IOException
  {
    int i = MidiUtil.writeVarLength(this.time, paramDataOutputStream);
    paramDataOutputStream.writeByte(255);
    paramDataOutputStream.writeByte(89);
    i += MidiUtil.writeVarLength(2, paramDataOutputStream);
    paramDataOutputStream.writeByte((byte)this.keySig);
    paramDataOutputStream.writeByte((byte)this.keyQual);
    return i + 4;
  }
  
  public int read(DataInputStream paramDataInputStream)
    throws IOException
  {
    this.keySig = paramDataInputStream.readByte();
    this.keyQual = paramDataInputStream.readUnsignedByte();
    return 2;
  }
  
  public Event copy()
    throws CloneNotSupportedException
  {
    KeySig localKeySig;
    try
    {
      localKeySig = (KeySig)clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException);
      localKeySig = new KeySig();
    }
    return localKeySig;
  }
  
  public void print()
  {
    System.out.println("KeySig(022):             [time = " + this.time + "][keySig = " + this.keySig + "][keyQual = " + this.keyQual + "]");
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\KeySig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */