package delta.games.lotro.utils.html;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Simple link generator.
 * @author DAM
 */
public class SimpleLinkGenerator implements LinkGenerator
{
  @Override
  public String buildURL(Identifiable to)
  {
    String key=getKey(to);
    return key+":id="+to.getIdentifier();
  }

  private String getKey(Identifiable to)
  {
    if (to instanceof Item) return "item";
    if (to instanceof DeedDescription) return "deed";
    if (to instanceof QuestDescription) return "quest";
    return "?";
  }
}
