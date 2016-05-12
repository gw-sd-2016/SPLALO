package jm.gui.graph;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Statistics
  implements Cloneable, Serializable
{
  private double[] elementData;
  private double largestValue = 0.0D;
  private int size;
  
  public Statistics(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Illegal Capacity: " + paramInt);
    }
    this.elementData = new double[paramInt];
  }
  
  public Statistics()
  {
    this(100);
  }
  
  public void trimToSize()
  {
    int i = this.elementData.length;
    if (this.size < i)
    {
      double[] arrayOfDouble = this.elementData;
      this.elementData = new double[this.size];
      System.arraycopy(arrayOfDouble, 0, this.elementData, 0, this.size);
    }
  }
  
  public void ensureCapacity(int paramInt)
  {
    int i = this.elementData.length;
    if (paramInt > i)
    {
      double[] arrayOfDouble = this.elementData;
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      this.elementData = new double[j];
      System.arraycopy(arrayOfDouble, 0, this.elementData, 0, this.size);
    }
  }
  
  public int size()
  {
    return this.size;
  }
  
  public double largestValue()
  {
    return this.largestValue;
  }
  
  public boolean isEmpty()
  {
    return this.size == 0;
  }
  
  public boolean contains(double paramDouble)
  {
    return indexOf(paramDouble) >= 0;
  }
  
  public int indexOf(double paramDouble)
  {
    for (int i = 0; i < this.size; i++) {
      if (paramDouble == this.elementData[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOf(double paramDouble)
  {
    for (int i = this.size - 1; i >= 0; i--) {
      if (paramDouble == this.elementData[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public Object clone()
  {
    try
    {
      Statistics localStatistics = (Statistics)super.clone();
      localStatistics.elementData = new double[this.size];
      System.arraycopy(this.elementData, 0, localStatistics.elementData, 0, this.size);
      return localStatistics;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new InternalError();
    }
  }
  
  public double[] toArray()
  {
    double[] arrayOfDouble = new double[this.size];
    System.arraycopy(this.elementData, 0, arrayOfDouble, 0, this.size);
    return arrayOfDouble;
  }
  
  public double[] toArray(double[] paramArrayOfDouble)
  {
    if (paramArrayOfDouble.length < this.size) {
      paramArrayOfDouble = new double[this.size];
    }
    System.arraycopy(this.elementData, 0, paramArrayOfDouble, 0, this.size);
    if (paramArrayOfDouble.length > this.size) {
      paramArrayOfDouble[this.size] = 0.0D;
    }
    return paramArrayOfDouble;
  }
  
  public double resetLargestValue()
  {
    this.largestValue = 0.0D;
    for (int i = 0; i < this.size; i++) {
      if (get(i) > this.largestValue) {
        this.largestValue = get(i);
      }
    }
    return this.largestValue;
  }
  
  public double get(int paramInt)
  {
    rangeCheck(paramInt);
    return this.elementData[paramInt];
  }
  
  public double set(int paramInt, double paramDouble)
  {
    rangeCheck(paramInt);
    double d = this.elementData[paramInt];
    this.elementData[paramInt] = paramDouble;
    if (d == this.largestValue) {
      resetLargestValue();
    } else if (paramDouble > this.largestValue) {
      this.largestValue = d;
    }
    return d;
  }
  
  public boolean add(double paramDouble)
  {
    ensureCapacity(this.size + 1);
    this.elementData[(this.size++)] = paramDouble;
    if (paramDouble > this.largestValue) {
      this.largestValue = paramDouble;
    }
    return true;
  }
  
  public boolean add(double[] paramArrayOfDouble)
  {
    ensureCapacity(this.size + paramArrayOfDouble.length);
    for (int i = 0; i < paramArrayOfDouble.length; i++)
    {
      this.elementData[(this.size++)] = paramArrayOfDouble[i];
      if (paramArrayOfDouble[i] > this.largestValue) {
        this.largestValue = paramArrayOfDouble[i];
      }
    }
    return true;
  }
  
  public void add(int paramInt, double paramDouble)
  {
    if ((paramInt > this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    ensureCapacity(this.size + 1);
    System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + 1, this.size - paramInt);
    this.elementData[paramInt] = paramDouble;
    this.size += 1;
    if (paramDouble > this.largestValue) {
      this.largestValue = paramDouble;
    }
  }
  
  public double removeIndex(int paramInt)
  {
    rangeCheck(paramInt);
    double d = this.elementData[paramInt];
    int i = this.size - paramInt - 1;
    if (i > 0) {
      System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
    }
    this.elementData[(--this.size)] = 0.0D;
    return d;
  }
  
  public void clear()
  {
    for (int i = 0; i < this.size; i++) {
      this.elementData[i] = 0.0D;
    }
    this.size = 0;
  }
  
  protected void removeRange(int paramInt1, int paramInt2)
  {
    int i = this.size - paramInt2;
    System.arraycopy(this.elementData, paramInt2, this.elementData, paramInt1, i);
    int j = this.size - (paramInt2 - paramInt1);
    while (this.size != j) {
      this.elementData[(--this.size)] = 0.0D;
    }
  }
  
  private void rangeCheck(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
  }
  
  private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeInt(this.elementData.length);
    for (int i = 0; i < this.size; i++) {
      paramObjectOutputStream.writeDouble(this.elementData[i]);
    }
  }
  
  private synchronized void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    int i = paramObjectInputStream.readInt();
    this.elementData = new double[i];
    for (int j = 0; j < this.size; j++) {
      this.elementData[j] = paramObjectInputStream.readDouble();
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof Statistics)) {
      return false;
    }
    Statistics localStatistics = (Statistics)paramObject;
    if (this.size == localStatistics.size())
    {
      for (int i = 0; i < this.size; i++)
      {
        double d1 = get(i);
        double d2 = localStatistics.get(i);
        if (d1 != d2) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public boolean removeValue(double paramDouble)
  {
    for (int i = 0; i < this.size; i++) {
      if (paramDouble == get(i))
      {
        removeIndex(i);
        return true;
      }
    }
    return false;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = size() - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(String.valueOf(get(j)));
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\graph\Statistics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */