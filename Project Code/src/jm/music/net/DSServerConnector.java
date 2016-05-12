package jm.music.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class DSServerConnector
  extends Thread
{
  private Socket connection;
  private ObjectInputStream ois;
  private ObjectOutputStream oos;
  private static DSServer server;
  private Object obj;
  
  public DSServerConnector(Socket paramSocket, DSServer paramDSServer)
  {
    server = paramDSServer;
    this.connection = paramSocket;
    try
    {
      OutputStream localOutputStream = this.connection.getOutputStream();
      this.oos = new ObjectOutputStream(localOutputStream);
      InputStream localInputStream = this.connection.getInputStream();
      this.ois = new ObjectInputStream(localInputStream);
      System.out.println(paramSocket);
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
        Object localObject = this.ois.readObject();
        server.broadCast(localObject, this);
      }
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      for (;;)
      {
        System.out.println(localClassNotFoundException);
      }
    }
    catch (IOException localIOException)
    {
      System.out.println(localIOException);
      server.deleteConnection(this);
    }
  }
  
  public void sendObject(Object paramObject)
  {
    try
    {
      this.oos.writeObject(paramObject);
    }
    catch (IOException localIOException)
    {
      server.deleteConnection(this);
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\net\DSServerConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */