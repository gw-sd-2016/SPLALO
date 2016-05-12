package jm.music.tools.ga;

import jm.music.data.Phrase;

public class PhrGeneticAlgorithm
{
  protected Phrase[] population;
  protected double[] fitness;
  protected PopulationInitialiser populationInitialiser;
  protected FitnessEvaluater fitnessEvaluater;
  protected TerminationCriteria terminationCriteria;
  protected ParentSelector parentSelector;
  protected Recombiner recombiner;
  protected Mutater mutater;
  protected SurvivorSelector survivorSelector;
  protected int beatsPerBar;
  protected long iteration;
  protected double initialLength;
  protected int initialSize;
  protected int originalSize;
  protected boolean finished;
  
  public PhrGeneticAlgorithm(Phrase paramPhrase, int paramInt, PopulationInitialiser paramPopulationInitialiser, FitnessEvaluater paramFitnessEvaluater, TerminationCriteria paramTerminationCriteria, ParentSelector paramParentSelector, Recombiner paramRecombiner, Mutater paramMutater, SurvivorSelector paramSurvivorSelector)
  {
    this.beatsPerBar = paramInt;
    this.initialLength = paramPhrase.getEndTime();
    this.initialSize = paramPhrase.size();
    this.originalSize = this.initialSize;
    this.populationInitialiser = paramPopulationInitialiser;
    this.fitnessEvaluater = paramFitnessEvaluater;
    this.terminationCriteria = paramTerminationCriteria;
    this.parentSelector = paramParentSelector;
    this.recombiner = paramRecombiner;
    this.mutater = paramMutater;
    this.survivorSelector = paramSurvivorSelector;
    setUpNewPopulation(paramPhrase);
  }
  
  public void setUpNewPopulation(Phrase paramPhrase)
  {
    this.iteration = 0L;
    this.population = this.populationInitialiser.initPopulation(paramPhrase, this.beatsPerBar);
    this.fitness = this.fitnessEvaluater.evaluate(this.population);
  }
  
  public void setBeatsPerBar(int paramInt)
  {
    this.beatsPerBar = paramInt;
  }
  
  public void zeroInitialSize()
  {
    this.originalSize = this.initialSize;
    this.initialSize = 0;
  }
  
  public void restoreInitialSize()
  {
    this.initialSize = this.originalSize;
  }
  
  public long getIteration()
  {
    return this.iteration;
  }
  
  public boolean iterate()
  {
    this.finished = this.terminationCriteria.isFinished(this.population);
    if (!this.finished)
    {
      this.iteration += 1L;
      Phrase[] arrayOfPhrase1 = this.parentSelector.selectParents(this.population, this.fitness);
      Phrase[] arrayOfPhrase2 = this.recombiner.recombine(arrayOfPhrase1, this.fitness, this.initialLength, this.initialSize, this.beatsPerBar);
      Phrase[] arrayOfPhrase3 = this.mutater.mutate(arrayOfPhrase2, this.initialLength, this.initialSize, this.beatsPerBar);
      double[] arrayOfDouble = this.fitnessEvaluater.evaluate(arrayOfPhrase3);
      this.population = this.survivorSelector.selectSurvivors(this.population, this.fitness, arrayOfPhrase3, arrayOfDouble);
      this.fitness = this.fitnessEvaluater.evaluate(this.population);
      return true;
    }
    return false;
  }
  
  public long iterate(long paramLong)
  {
    long l = paramLong;
    for (int i = 0; i < paramLong; i++)
    {
      iterate();
      if (this.finished)
      {
        l = i;
        break;
      }
    }
    return l;
  }
  
  public double[] getFitness()
  {
    return this.fitness;
  }
  
  public Phrase[] getPopulation()
  {
    return this.population;
  }
  
  public Phrase[] getOrderedPopulation()
  {
    quicksort();
    return this.population;
  }
  
  private void quicksort()
  {
    quicksort(0, this.population.length - 1);
  }
  
  private void quicksort(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= paramInt2) {
      return;
    }
    swap(paramInt1, rand(paramInt1, paramInt2));
    int i = paramInt1;
    for (int j = paramInt1 + 1; j <= paramInt2; j++) {
      if (this.fitness[j] < this.fitness[paramInt1]) {
        swap(++i, j);
      }
    }
    swap(paramInt1, i);
    quicksort(paramInt1, i - 1);
    quicksort(i + 1, paramInt2);
  }
  
  private static int rand(int paramInt1, int paramInt2)
  {
    return paramInt1 + (int)(Math.random() * (paramInt2 - paramInt1)) + 1;
  }
  
  private void swap(int paramInt1, int paramInt2)
  {
    Phrase localPhrase = this.population[paramInt1];
    this.population[paramInt1] = this.population[paramInt2];
    this.population[paramInt2] = localPhrase;
    double d = this.fitness[paramInt1];
    this.fitness[paramInt1] = this.fitness[paramInt2];
    this.fitness[paramInt2] = d;
  }
  
  public double getBestFitness()
  {
    double d = 0.0D;
    int i = -1;
    for (int j = 0; j < this.fitness.length; j++) {
      if (this.fitness[j] > d)
      {
        d = this.fitness[j];
        i = j;
      }
    }
    return d;
  }
  
  public double getAverageFitness()
  {
    double d = 0.0D;
    for (int i = 0; i < this.fitness.length; i++) {
      d += this.fitness[i];
    }
    return d / this.fitness.length;
  }
  
  public double getStandardDeviation()
  {
    double d1 = getAverageFitness();
    double d2 = 0.0D;
    for (int i = 0; i < this.fitness.length; i++) {
      d2 += (d1 - this.fitness[i]) * (d1 - this.fitness[i]);
    }
    return Math.sqrt(d2) / this.fitness.length;
  }
  
  public Phrase getBestIndividual()
  {
    double d = Double.MAX_VALUE;
    int i = -1;
    for (int j = 0; j < this.fitness.length; j++) {
      if (this.fitness[j] < d)
      {
        d = this.fitness[j];
        i = j;
      }
    }
    return this.population[i];
  }
  
  public Mutater getMutater()
  {
    return this.mutater;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\PhrGeneticAlgorithm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */