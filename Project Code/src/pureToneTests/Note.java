package pureToneTests;

import java.util.ArrayList;
import java.util.List;

public class Note {

	private List<Float> frequency;
	private List<String> noteValue;
	private double startTime;
	//private double endTime;
	
	public Note()
	{
		frequency = new ArrayList<Float>();
		noteValue = new ArrayList<String>();
		startTime = 0;
		
	}
	
	public void setStartTime(double start)
	{startTime = start;}
	
	public void setFrequency(List<Float> freq)
	{frequency = freq;}
	
	public double getStartTime()
	{return startTime;}
	
	public List<Float> getFrequency()
	{return frequency;}

	
}
