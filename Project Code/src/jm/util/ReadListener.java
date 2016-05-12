package jm.util;

import jm.music.data.Score;

public abstract interface ReadListener
{
  public abstract Score scoreRead(Score paramScore);
  
  public abstract void startedReading();
  
  public abstract void finishedReading();
}
