package jm.audio;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;
import jm.JMC;
import jm.music.data.Note;
import jm.music.rt.RTLine;

public abstract class Instrument
  extends Thread
  implements Runnable, JMC
{
  public int iterations;
  protected Vector primaryAO = new Vector();
  protected AudioObject finalAO = null;
  protected int numOfSamples = 0;
  protected int numOfChannels = 0;
  protected int bufsize = 4096;
  protected volatile Vector listeners = new Vector();
  protected long samplesProcessed = 0L;
  private boolean restNote = false;
  private float[] rtBuffer = new float[this.bufsize];
  private boolean finished = true;
  private boolean clear = false;
  private boolean block = true;
  private RTLine rtline;
  private int index = 0;
  public boolean finishedNewData = false;
  private boolean initialised = false;
  private boolean okToRun = true;
  public static final int RENDER = 0;
  public static final int REALTIME = 1;
  protected int output = 0;
  private int returned;
  private float[] buffer;
  
  protected Instrument() {}
  
  public void renderNote(Note paramNote, double paramDouble)
  {
    this.finalAO = null;
    Enumeration localEnumeration = this.primaryAO.elements();
    AudioObject localAudioObject1 = (AudioObject)this.primaryAO.elementAt(0);
    this.numOfSamples = ((int)(localAudioObject1.getSampleRate() * (float)paramNote.getDuration()));
    this.numOfChannels = localAudioObject1.channels;
    if (paramNote.getFrequency() == -2.147483648E9D)
    {
      this.restNote = true;
    }
    else
    {
      double d = 0.0D;
      if ((!paramNote.getPitchType()) && (paramNote.getPitch() != Integer.MIN_VALUE) && (paramNote.getPitch() <= 0) && (paramNote.getPitch() >= 127)) {
        d = JMC.FRQ[paramNote.getPitch()];
      } else {
        d = paramNote.getFrequency();
      }
      if (localAudioObject1.getSampleRate() * 0.5D < d)
      {
        System.out.println("jMusic Instrument error: Sorry, can't render a note above the Nyquist frequency.");
        System.out.println("Sample rate = " + localAudioObject1.getSampleRate() + " Pitch frequency = " + paramNote.getFrequency());
        System.exit(1);
      }
      this.restNote = false;
      while (localEnumeration.hasMoreElements())
      {
        AudioObject localAudioObject2 = (AudioObject)localEnumeration.nextElement();
        localAudioObject2.newNote(paramNote, paramDouble - paramNote.getOffset(), this.numOfSamples);
      }
    }
  }
  
  public void addPrimaryAO(AudioObject paramAudioObject)
  {
    this.primaryAO.addElement(paramAudioObject);
  }
  
  public void setFinalAO(AudioObject paramAudioObject)
    throws AOException
  {
    if ((this.finalAO == null) || (this.finalAO == paramAudioObject)) {
      this.finalAO = paramAudioObject;
    } else {
      throw new AOException("jMusic Instrument error: " + paramAudioObject.name, this.finalAO.name + " is already set as finalAO.\n" + "  There can only be one finalAO.");
    }
  }
  
  public void setFinished(boolean paramBoolean)
  {
    if (!this.finished) {
      return;
    }
    this.finished = paramBoolean;
  }
  
  public boolean getFinished()
  {
    return this.finished;
  }
  
  public void setBufSize(int paramInt)
  {
    this.bufsize = paramInt;
    this.rtBuffer = new float[paramInt];
  }
  
  public int getBufSize()
  {
    return this.bufsize;
  }
  
  public int getChannels()
  {
    try
    {
      if (!getInitialised())
      {
        createChain();
        setInitialised(true);
      }
    }
    catch (AOException localAOException)
    {
      localAOException.printStackTrace();
    }
    return ((AudioObject)this.primaryAO.firstElement()).getChannels();
  }
  
  public int getSampleRate()
  {
    try
    {
      if (!getInitialised())
      {
        createChain();
        setInitialised(true);
      }
    }
    catch (AOException localAOException)
    {
      localAOException.printStackTrace();
    }
    return ((AudioObject)this.primaryAO.firstElement()).getSampleRate();
  }
  
  public void setInitialised(boolean paramBoolean)
  {
    this.initialised = paramBoolean;
  }
  
  public boolean getInitialised()
  {
    return this.initialised;
  }
  
  public void addRTLine(RTLine paramRTLine)
  {
    this.rtline = paramRTLine;
  }
  
  public Enumeration getListeners()
  {
    return this.listeners.elements();
  }
  
  public void addAudioChainListener(AudioChainListener paramAudioChainListener)
  {
    this.listeners.addElement(paramAudioChainListener);
  }
  
  public void setController(double[] paramArrayOfDouble) {}
  
  public void run()
  {
    for (;;)
    {
      if (this.okToRun)
      {
        this.finished = false;
        this.rtline.instNote(this, this.samplesProcessed);
        iterateChain();
      }
      else
      {
        try
        {
          Thread.sleep(10L);
        }
        catch (InterruptedException localInterruptedException) {}
      }
    }
  }
  
  public void pause()
  {
    this.okToRun = false;
  }
  
  public void unPause()
  {
    this.okToRun = true;
  }
  
  public void setBlock(boolean paramBoolean)
  {
    this.block = paramBoolean;
  }
  
  public void setClear(boolean paramBoolean)
  {
    this.clear = paramBoolean;
  }
  
  public void setOutput(int paramInt)
  {
    this.output = paramInt;
  }
  
  public int getOutput()
  {
    return this.output;
  }
  
  public synchronized void release()
  {
    notify();
    this.clear = true;
  }
  
  public synchronized void block()
  {
    if ((!this.clear) && (this.block)) {
      try
      {
        wait();
      }
      catch (InterruptedException localInterruptedException) {}
    }
    this.clear = false;
  }
  
  public abstract void createChain()
    throws AOException;
  
  public void iterateChain()
  {
    this.iterations = 0;
    if (this.numOfSamples > 0) {
      this.iterations = (this.numOfSamples * this.numOfChannels);
    }
    this.returned = 0;
    while (!this.finished)
    {
      this.finished = true;
      this.buffer = null;
      if ((this.iterations > this.bufsize) || (this.iterations <= 0)) {
        this.buffer = new float[this.bufsize];
      } else {
        this.buffer = new float[this.iterations];
      }
      try
      {
        if (this.restNote) {
          this.returned = this.buffer.length;
        } else {
          this.returned = this.finalAO.nextWork(this.buffer);
        }
      }
      catch (AOException localAOException)
      {
        System.out.println(localAOException);
        System.exit(1);
      }
      this.iterations -= this.returned;
      if (this.iterations > 0) {
        this.finished = false;
      }
      this.samplesProcessed += this.returned;
      for (int i = 0; i < this.returned; i++)
      {
        this.rtBuffer[(this.index++)] = this.buffer[i];
        if (this.index == this.bufsize)
        {
          this.index = 0;
          Enumeration localEnumeration = this.listeners.elements();
          while (localEnumeration.hasMoreElements())
          {
            AudioChainListener localAudioChainListener = (AudioChainListener)localEnumeration.nextElement();
            localAudioChainListener.controlChange(this.rtBuffer, this.returned, this.finished);
          }
          block();
        }
      }
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\Instrument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */