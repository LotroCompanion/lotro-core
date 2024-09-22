package delta.games.lotro.character.skills;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.WellKnownCharacterClassKeys;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.character.skills.filters.SkillCategoryFilter;

/**
 * Test class for the skills finder.
 * @author DAM
 */
class TestSkillsFinder
{
  /**
   * Test finder for travel skills.
   */
  @Test
  void testTravelSkills()
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
    assertNotNull(skills);
  }
}
