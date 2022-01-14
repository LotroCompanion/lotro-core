package delta.games.lotro.character.skills;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.character.skills.filters.SkillCategoryFilter;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

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
    summary.setCharacterClass(CharacterClass.CHAMPION);
    summary.setRace(Race.MAN);
    summary.setLevel(130);
    List<SkillDescription> skills=new SkillsFinder(filter,summary).find();
    Assert.assertNotNull(skills);
  }
}
