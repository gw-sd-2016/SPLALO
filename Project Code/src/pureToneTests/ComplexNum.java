package pureToneTests;

public class ComplexNum {

	private double realNum;
	private double imagNum;

	public ComplexNum()
	{
		realNum = 0.0;
		imagNum = 0.0;
	}

	public ComplexNum(double real, double imaginary)
	{
		realNum = real;
		imagNum = imaginary;
	}

	

	public double getReal()		
		{return realNum;}

	public double getImaginary()		
		{return imagNum;}


	public void setReal(double real)		
		{realNum = real;}
	
	public void setImaginary(double imaginary)		
		{imagNum = imaginary;}
	

	

	public ComplexNum add(ComplexNum c2)
	{
		ComplexNum newComplexNum = new ComplexNum();
		newComplexNum.realNum = this.realNum + c2.realNum;
		newComplexNum.imagNum = this.imagNum + c2.imagNum;

		return newComplexNum;
	}


	public ComplexNum multiply(ComplexNum c2)
	{
		ComplexNum newComplexNum = new ComplexNum();
		newComplexNum.realNum = (this.realNum * c2.realNum) - (this.imagNum * c2.imagNum);
		newComplexNum.imagNum = (this.realNum * c2.imagNum) + (this.imagNum * c2.realNum);

		return newComplexNum;
	}

	public double magnitude()
	{
		return (Math.sqrt(Math.pow(this.realNum, 2) + Math.pow(this.imagNum, 2)));
	}

}
