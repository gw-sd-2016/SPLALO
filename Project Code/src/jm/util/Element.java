package jm.util;

import java.util.Enumeration;
import java.util.Vector;

class Element
{
  private String name;
  private Vector attributeVector = new Vector();
  private Vector childrenVector = new Vector();
  
  public Element(String paramString)
  {
    this.name = paramString;
  }
  
  public void addAttribute(Attribute paramAttribute)
  {
    this.attributeVector.addElement(paramAttribute);
  }
  
  public void appendChildren(Element[] paramArrayOfElement)
  {
    for (int i = 0; i < paramArrayOfElement.length; i++) {
      this.childrenVector.addElement(paramArrayOfElement[i]);
    }
  }
  
  public String getAttribute(String paramString)
  {
    Enumeration localEnumeration = this.attributeVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      Attribute localAttribute = (Attribute)localEnumeration.nextElement();
      if (localAttribute.getName().equals(paramString)) {
        return localAttribute.getValue();
      }
    }
    return "";
  }
  
  public Element[] getChildren()
  {
    Element[] arrayOfElement = new Element[this.childrenVector.size()];
    this.childrenVector.copyInto(arrayOfElement);
    return arrayOfElement;
  }
  
  public String getName()
  {
    return this.name;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\util\Element.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */