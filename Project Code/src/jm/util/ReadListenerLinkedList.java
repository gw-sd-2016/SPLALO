package jm.util;

import jm.music.data.Score;

class ReadListenerLinkedList
{
  private ReadListener listener;
  private ReadListenerLinkedList next;
  
  public ReadListenerLinkedList(ReadListener paramReadListener)
  {
    this.listener = paramReadListener;
    this.next = null;
  }
  
  public ReadListenerLinkedList getNext()
  {
    return this.next;
  }
  
  public ReadListener getListener()
  {
    return this.listener;
  }
  
  public void add(ReadListener paramReadListener)
  {
    if (paramReadListener == null) {
      return;
    }
    if (this.next == null) {
      this.next = new ReadListenerLinkedList(paramReadListener);
    }
    this.next.add(paramReadListener);
  }
  
  public void remove(ReadListener paramReadListener)
  {
    if (this.next == null) {
      return;
    }
    if (paramReadListener == this.next.getListener()) {
      this.next = this.next.getNext();
    }
  }
  
  public Score scoreRead(Score paramScore)
  {
    if (this.listener == null) {
      return paramScore;
    }
    if (this.next == null) {
      return this.listener.scoreRead(paramScore);
    }
    return this.next.scoreRead(this.listener.scoreRead(paramScore));
  }
  
  public void startedReading()
  {
    if (this.listener == null) {
      return;
    }
    if (this.next == null)
    {
      this.listener.startedReading();
      return;
    }
    this.listener.startedReading();
    this.next.startedReading();
  }
  
  public void finishedReading()
  {
    if (this.listener == null) {
      return;
    }
    if (this.next == null)
    {
      this.listener.finishedReading();
      return;
    }
    this.listener.finishedReading();
    this.next.finishedReading();
  }
}