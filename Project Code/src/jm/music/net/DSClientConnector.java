package jm.music.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import jm.music.data.Phrase;

public class DSClientConnector
  extends Thread
{
  private Socket connection;
  private ObjectInputStream ois;
  private ObjectOutputStream oos;
  private DSClient client;
  private Phrase phr = null;
  
  public DSClientConnector(String paramString, int paramInt, DSClient paramDSClient)
  {
    try
    {
      this.connection = new Socket(paramString, paramInt);
      OutputStream localOutputStream = this.connection.getOutputStream();
      this.oos = new ObjectOutputStream(localOutputStream);
      InputStream localInputStream = this.connection.getInputStream();
      this.ois = new ObjectInputStream(localInputStream);
    }
    catch (IOException localIOException)
    {
      System.out.println("The client is having trouble connecting to the specified server.  Please check the server name and port number.");
      System.exit(1);
    }
    this.client = paramDSClient;
    this.client.setConnection(this);
    start();
  }
  
  public void run()
  {
    for (;;)
    {
      try
      {
        this.client.newObject(this.ois.readObject());
      }
      catch (ClassNotFoundException localClassNotFoundException) {}catch (IOException localIOException) {}
    }
  }
  
  public void sendObject(Object paramObject)
  {
    try
    {
      this.oos.writeObject(paramObject);
    }
    catch (IOException localIOException) {}
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\net\DSClientConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */