package jm.music.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;

public final class AdaptiveMatrix
{
  private int depth;
  private Hashtable weightMatrix;
  private Hashtable countMatrix;
  private int indexRange;
  
  public AdaptiveMatrix(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    this.countMatrix = new Hashtable();
    this.weightMatrix = new Hashtable();
    this.depth = paramInt1;
    this.indexRange = paramInt2;
    calcCount(paramArrayOfInt);
    calcWeight();
  }
  
  public AdaptiveMatrix(String paramString)
  {
    read(paramString);
  }
  
  public void update(int[] paramArrayOfInt)
  {
    calcCount(paramArrayOfInt);
    calcWeight();
  }
  
  public int[] generate(int paramInt, int[] paramArrayOfInt)
  {
    if (paramArrayOfInt.length != this.depth)
    {
      System.err.println("[WARNING] Wrong seed length for this Matrix depth");
      return null;
    }
    int[] arrayOfInt1 = new int[paramInt];
    Object localObject1 = "";
    int[] arrayOfInt2 = new int[paramArrayOfInt.length];
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      arrayOfInt1[i] = paramArrayOfInt[i];
      arrayOfInt2[i] = paramArrayOfInt[i];
      localObject1 = (String)localObject1 + paramArrayOfInt[i] + " ";
    }
    Object localObject2 = localObject1;
    if (!this.weightMatrix.containsKey(localObject1))
    {
      System.err.println("[WARNING] This seed is unavailable .. try another");
      return null;
    }
    for (int j = paramArrayOfInt.length; j < arrayOfInt1.length; j++)
    {
      if (!this.weightMatrix.containsKey(localObject1))
      {
        localObject1 = localObject2;
        paramArrayOfInt = arrayOfInt2;
      }
      double[] arrayOfDouble = (double[])this.weightMatrix.get(localObject1);
      localObject1 = "";
      for (int k = 1; k < paramArrayOfInt.length; k++)
      {
        localObject1 = (String)localObject1 + paramArrayOfInt[k] + " ";
        paramArrayOfInt[(k - 1)] = paramArrayOfInt[k];
      }
      double d1 = Math.random();
      double d2 = 0.0D;
      for (int m = 0; m < arrayOfDouble.length; m++)
      {
        d2 += arrayOfDouble[m];
        if (d2 > d1)
        {
          arrayOfInt1[j] = m;
          localObject1 = (String)localObject1 + m + " ";
          paramArrayOfInt[(this.depth - 1)] = m;
          break;
        }
      }
    }
    return arrayOfInt1;
  }
  
  public void read(String paramString)
  {
    AdaptiveMatrix localAdaptiveMatrix = null;
    try
    {
      File localFile = new File(paramString);
      FileInputStream localFileInputStream = new FileInputStream(localFile);
      ObjectInputStream localObjectInputStream = new ObjectInputStream(localFileInputStream);
      localAdaptiveMatrix = (AdaptiveMatrix)localObjectInputStream.readObject();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    this.depth = localAdaptiveMatrix.getDepth();
    this.indexRange = localAdaptiveMatrix.getIndexRange();
    this.countMatrix = localAdaptiveMatrix.getCountMatrix();
    this.weightMatrix = localAdaptiveMatrix.getWeightMatrix();
  }
  
  public void write(String paramString)
  {
    try
    {
      File localFile = new File(paramString);
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localFileOutputStream);
      localObjectOutputStream.writeObject(this);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void print()
  {
    System.out.println();
    System.out.println("MATRIX");
    System.out.println("----------------");
    Enumeration localEnumeration = this.weightMatrix.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      double[] arrayOfDouble = (double[])this.weightMatrix.get(str);
      System.out.print(str + "\t: ");
      for (int i = 0; i < arrayOfDouble.length; i++) {
        System.out.print(" " + arrayOfDouble[i]);
      }
      System.out.println();
    }
  }
  
  public int getDepth()
  {
    return this.depth;
  }
  
  public Hashtable getWeightMatrix()
  {
    return this.weightMatrix;
  }
  
  public Hashtable getCountMatrix()
  {
    return this.countMatrix;
  }
  
  public int getIndexRange()
  {
    return this.indexRange;
  }
  
  private void calcCount(int[] paramArrayOfInt)
  {
    for (int i = this.depth - 1; i < paramArrayOfInt.length - 1; i++)
    {
      String str = "";
      int[] arrayOfInt1 = new int[this.indexRange];
      int j = 0;
      for (int k = this.depth - 1; j < this.depth; k--)
      {
        str = str + paramArrayOfInt[(i - k)] + " ";
        j++;
      }
      if (this.countMatrix.containsKey(str))
      {
        int[] arrayOfInt2 = (int[])this.countMatrix.get(str);
        arrayOfInt2[paramArrayOfInt[(i + 1)]] += 1;
        this.countMatrix.put(str, arrayOfInt2);
      }
      else
      {
        arrayOfInt1[paramArrayOfInt[(i + 1)]] += 1;
        this.countMatrix.put(str, arrayOfInt1);
      }
    }
  }
  
  private void calcWeight()
  {
    Enumeration localEnumeration = this.countMatrix.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      int[] arrayOfInt = (int[])this.countMatrix.get(str);
      int i = 0;
      for (int j = 0; j < arrayOfInt.length; j++) {
        i += arrayOfInt[j];
      }
      double[] arrayOfDouble = new double[this.indexRange];
      for (int k = 0; k < arrayOfInt.length; k++) {
        arrayOfDouble[k] = (arrayOfInt[k] / i);
      }
      this.weightMatrix.put(str, arrayOfDouble);
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\AdaptiveMatrix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */