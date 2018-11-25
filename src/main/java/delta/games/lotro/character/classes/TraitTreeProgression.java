package delta.games.lotro.character.classes;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;

/**
 * Progression inside a trait tree.
 * @author DAM
 */
public class TraitTreeProgression
{
  private List<Integer> _requiredPoints;
  private List<TraitDescription> _traits;

  /**
   * Constructor.
   */
  public TraitTreeProgression()
  {
    _requiredPoints=new ArrayList<Integer>();
    _traits=new ArrayList<TraitDescription>();
  }

  /**
   * Add a step.
   * @param requiredPoints Required number of points.
   * @param trait Associated trait.
   */
  public void addStep(int requiredPoints, TraitDescription trait)
  {
    _requiredPoints.add(Integer.valueOf(requiredPoints));
    _traits.add(trait);
  }

  /**
   * Get the number of points for each step.
   * @return A list of point counts.
   */
  public List<Integer> getSteps()
  {
    return new ArrayList<Integer>(_requiredPoints);
  }

  /**
   * Get the traits for each step.
   * @return A list of traits.
   */
  public List<TraitDescription> getTraits()
  {
    return new ArrayList<TraitDescription>(_traits);
  }
}
