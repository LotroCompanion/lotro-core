package delta.games.lotro.character.skills;

import java.util.List;

import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.WellKnownCharacterClassKeys;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.character.skills.filters.SkillCategoryFilter;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test class for the skills finder.
 * @author DAM
 */
public class TestSkillsFinder extends TestCase
{
  /**
   * Test finder for travel skills.
   */
  public void testTravelSkills()
  {
    SkillCategoryFilter filter=new SkillCategoryFilter();
    filter.setCategory(102);
    BaseCharacterSummary summary=new BaseCharacterSummary();
    ClassDescription champion=ClassesManager.getInstance().getCharacterClassByKey(WellKnownCharacterClassKeys.CHAMPION);
    summary.setCharacterClass(champion);
    RaceDescription man=RacesManager.getInstance().getByKey("man");
    summary.setRace(man);
    summary.setLevel(130);
    List<SkillDescription> skills=new SkillsFinder(filter,summary).find();
    Assert.assertNotNull(skills);
  }
}
