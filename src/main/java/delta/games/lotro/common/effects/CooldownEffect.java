package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.enums.AICooldownChannel;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.utils.Proxy;

/**
 * Cooldown effect.
 * @author DAM
 */
public class CooldownEffect extends InstantEffect
{
  private List<Proxy<SkillDescription>> _skills;
  private List<AICooldownChannel> _channels;
  private ModPropertyList _durationModifiers;

  /**
   * Constructor.
   */
  public CooldownEffect()
  {
    super();
    _skills=new ArrayList<Proxy<SkillDescription>>();
    _channels=new ArrayList<AICooldownChannel>();
  }

  /**
   * Get the targeted skills.
   * @return A list of skill proxies, possibly empty but never <code>null</code>.
   */
  public List<Proxy<SkillDescription>> getSkills()
  {
    return _skills;
  }

  /**
   * Add a skill.
   * @param skillID Skill identifier.
   */
  public void addSkill(int skillID)
  {
    Proxy<SkillDescription> proxy=new Proxy<SkillDescription>();
    proxy.setId(skillID);
    _skills.add(proxy);
  }

  /**
   * Get the targeted cooldown channels.
   * @return A list of cooldown channels, possibly empty but never <code>null</code>.
   */
  public List<AICooldownChannel> getCooldownChannels()
  {
    return _channels;
  }

  /**
   * Add a cooldown channel.
   * @param cooldownChannel Cooldown channel to add.
   */
  public void addCooldownChannel(AICooldownChannel cooldownChannel)
  {
    _channels.add(cooldownChannel);
  }

  /**
   * Get the duration modifiers.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getDurationModifiers()
  {
    return _durationModifiers;
  }

  /**
   * Set the duration modifiers.
   * @param durationModifiers the modifiers to set (may be <code>null</code>).
   */
  public void setDurationModifiers(ModPropertyList durationModifiers)
  {
    _durationModifiers=durationModifiers;
  }
}
