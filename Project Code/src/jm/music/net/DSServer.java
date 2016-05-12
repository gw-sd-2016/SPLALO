package jm.music.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Enumeration;
import java.util.Vector;

public class DSServer
  extends Thread
{
  private ServerSocket ss;
  private Vector clientConnections = new Vector();
  
  public DSServer()
  {
    this(6767);
  }
  
  public DSServer(int paramInt)
  {
    try
    {
      this.ss = new ServerSocket(paramInt);
    }
    catch (IOException localIOException) {}
    start();
  }
  
  public void run()
  {
    try
    {
      for (;;)
      {
        DSServerConnector localDSServerConnector = new DSServerConnector(this.ss.accept(), this);
        this.clientConnections.addElement(localDSServerConnector);
      }
    }
    catch (IOException localIOException) {}
  }
  
  public void broadCast(Object paramObject, DSServerConnector paramDSServerConnector)
  {
    Enumeration localEnumeration = this.clientConnections.elements();
    while (localEnumeration.hasMoreElements())
    {
      DSServerConnector localDSServerConnector = (DSServerConnector)localEnumeration.nextElement();
      if (localDSServerConnector != paramDSServerConnector) {
        localDSServerConnector.sendObject(paramObject);
      }
    }
  }
  
  public void deleteConnection(DSServerConnector paramDSServerConnector)
  {
    this.clientConnections.removeElement(paramDSServerConnector);
    paramDSServerConnector = null;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\net\DSServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */