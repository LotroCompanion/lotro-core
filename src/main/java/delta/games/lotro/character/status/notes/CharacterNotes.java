package delta.games.lotro.character.status.notes;

/**
 * Free-text notes about a single character.
 * @author DAM
 */
public class CharacterNotes
{
  private String _text;

  /**
   * Constructor.
   */
  public CharacterNotes()
  {
    _text="";
  }

  /**
   * Get the managed text.
   * @return some text.
   */
  public String getText()
  {
    return _text;
  }

  /**
   * Set the managed text.
   * @param text Text to set.
   */
  public void setText(String text)
  {
    if (text==null)
    {
      text="";
    }
    _text=text;
  }
}
