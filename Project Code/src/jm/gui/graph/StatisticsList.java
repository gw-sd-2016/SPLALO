package jm.gui.graph;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;

public class StatisticsList
  implements Cloneable, Serializable
{
  private transient Statistics[] elementData;
  private int size;
  protected transient int modCount = 0;
  
  public StatisticsList(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Illegal Capacity: " + paramInt);
    }
    this.elementData = new Statistics[paramInt];
  }
  
  public StatisticsList()
  {
    this(10);
  }
  
  public void trimToSize()
  {
    this.modCount += 1;
    int i = this.elementData.length;
    if (this.size < i)
    {
      Statistics[] arrayOfStatistics = this.elementData;
      this.elementData = new Statistics[this.size];
      System.arraycopy(arrayOfStatistics, 0, this.elementData, 0, this.size);
    }
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.modCount += 1;
    int i = this.elementData.length;
    if (paramInt > i)
    {
      Statistics[] arrayOfStatistics = this.elementData;
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      this.elementData = new Statistics[j];
      System.arraycopy(arrayOfStatistics, 0, this.elementData, 0, this.size);
    }
  }
  
  public int size()
  {
    return this.size;
  }
  
  public boolean isEmpty()
  {
    return this.size == 0;
  }
  
  public boolean contains(Statistics paramStatistics)
  {
    return indexOf(paramStatistics) >= 0;
  }
  
  public int indexOf(Statistics paramStatistics)
  {
    int i;
    if (paramStatistics == null) {
      for (i = 0; i < this.size; i++) {
        if (this.elementData[i] == null) {
          return i;
        }
      }
    } else {
      for (i = 0; i < this.size; i++) {
        if (paramStatistics.equals(this.elementData[i])) {
          return i;
        }
      }
    }
    return -1;
  }
  
  public int lastIndexOf(Statistics paramStatistics)
  {
    int i;
    if (paramStatistics == null) {
      for (i = this.size - 1; i >= 0; i--) {
        if (this.elementData[i] == null) {
          return i;
        }
      }
    } else {
      for (i = this.size - 1; i >= 0; i--) {
        if (paramStatistics.equals(this.elementData[i])) {
          return i;
        }
      }
    }
    return -1;
  }
  
  public Object clone()
  {
    try
    {
      StatisticsList localStatisticsList = (StatisticsList)super.clone();
      localStatisticsList.elementData = new Statistics[this.size];
      System.arraycopy(this.elementData, 0, localStatisticsList.elementData, 0, this.size);
      localStatisticsList.modCount = 0;
      return localStatisticsList;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new InternalError();
    }
  }
  
  public Statistics[] toArray()
  {
    Statistics[] arrayOfStatistics = new Statistics[this.size];
    System.arraycopy(this.elementData, 0, arrayOfStatistics, 0, this.size);
    return arrayOfStatistics;
  }
  
  public Statistics[] toArray(Statistics[] paramArrayOfStatistics)
  {
    if (paramArrayOfStatistics.length < this.size) {
      paramArrayOfStatistics = (Statistics[])Array.newInstance(paramArrayOfStatistics.getClass().getComponentType(), this.size);
    }
    System.arraycopy(this.elementData, 0, paramArrayOfStatistics, 0, this.size);
    if (paramArrayOfStatistics.length > this.size) {
      paramArrayOfStatistics[this.size] = null;
    }
    return paramArrayOfStatistics;
  }
  
  public Statistics get(int paramInt)
  {
    rangeCheck(paramInt);
    return this.elementData[paramInt];
  }
  
  public Statistics set(int paramInt, Statistics paramStatistics)
  {
    rangeCheck(paramInt);
    Statistics localStatistics = this.elementData[paramInt];
    this.elementData[paramInt] = paramStatistics;
    return localStatistics;
  }
  
  public boolean add(Statistics paramStatistics)
  {
    ensureCapacity(this.size + 1);
    this.elementData[(this.size++)] = paramStatistics;
    return true;
  }
  
  public void add(int paramInt, Statistics paramStatistics)
  {
    if ((paramInt > this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    ensureCapacity(this.size + 1);
    System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + 1, this.size - paramInt);
    this.elementData[paramInt] = paramStatistics;
    this.size += 1;
  }
  
  public Statistics remove(int paramInt)
  {
    rangeCheck(paramInt);
    this.modCount += 1;
    Statistics localStatistics = this.elementData[paramInt];
    int i = this.size - paramInt - 1;
    if (i > 0) {
      System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
    }
    this.elementData[(--this.size)] = null;
    return localStatistics;
  }
  
  public void clear()
  {
    this.modCount += 1;
    for (int i = 0; i < this.size; i++) {
      this.elementData[i] = null;
    }
    this.size = 0;
  }
  
  protected void removeRange(int paramInt1, int paramInt2)
  {
    this.modCount += 1;
    int i = this.size - paramInt2;
    System.arraycopy(this.elementData, paramInt2, this.elementData, paramInt1, i);
    int j = this.size - (paramInt2 - paramInt1);
    while (this.size != j) {
      this.elementData[(--this.size)] = null;
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
      paramObjectOutputStream.writeObject(this.elementData[i]);
    }
  }
  
  private synchronized void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    int i = paramObjectInputStream.readInt();
    this.elementData = new Statistics[i];
    for (int j = 0; j < this.size; j++) {
      this.elementData[j] = ((Statistics)paramObjectInputStream.readObject());
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof StatisticsList)) {
      return false;
    }
    StatisticsList localStatisticsList = (StatisticsList)paramObject;
    if (this.size == localStatisticsList.size())
    {
      for (int i = 0; i < this.size; i++)
      {
        Statistics localStatistics1 = get(i);
        Statistics localStatistics2 = localStatisticsList.get(i);
        if (localStatistics1 == null ? localStatistics2 != null : !localStatistics1.equals(localStatistics2)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public int hashCode()
  {
    int i = 1;
    for (int j = 0; j < this.size; j++)
    {
      Statistics localStatistics = get(j);
      i = 31 * i + (localStatistics == null ? 0 : localStatistics.hashCode());
    }
    return i;
  }
  
  public boolean remove(Statistics paramStatistics)
  {
    int i;
    if (paramStatistics == null) {
      for (i = 0; i < this.size; i++) {
        if (get(i) == null)
        {
          remove(i);
          return true;
        }
      }
    } else {
      for (i = 0; i < this.size; i++) {
        if (paramStatistics.equals(get(i)))
        {
          remove(i);
          return true;
        }
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


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\graph\StatisticsList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */