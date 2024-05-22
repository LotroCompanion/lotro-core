package delta.games.lotro.utils.strings;

import delta.games.lotro.common.Identifiable;

/**
 * Simple text output.
 * @author DAM
 */
public class TextOutput implements GenericOutput
{
  @Override
  public void startDocument(StringBuilder sb)
  {
    // Nothing!
  }

  @Override
  public void endDocument(StringBuilder sb)
  {
    // Nothing!
  }

  @Override
  public void startBody(StringBuilder sb)
  {
    // Nothing!
  }

  @Override
  public void endBody(StringBuilder sb)
  {
    // Nothing!
  }

  @Override
  public void printLink(StringBuilder sb, String text, Identifiable to)
  {
    sb.append(text);
  }

  @Override
  public void printText(StringBuilder sb, String text)
  {
    text=TextSanitizer.sanitize(text);
    sb.append(text);
  }


  @Override
  public void startParagraph(StringBuilder sb)
  {
    // Nothing!
  }

  @Override
  public void endParagraph(StringBuilder sb)
  {
    // Nothing!
  }

  @Override
  public void startBold(StringBuilder sb)
  {
    // Nothing!
  }

  @Override
  public void endBold(StringBuilder sb)
  {
    // Nothing!
  }

  @Override
  public void startItalic(StringBuilder sb)
  {
    // Nothing!
  }

  @Override
  public void endItalic(StringBuilder sb)
  {
    // Nothing!
  }

  @Override
  public void startTitle(StringBuilder sb, int level)
  {
    // Nothing!
  }

  @Override
  public void endTitle(StringBuilder sb, int level)
  {
    // Nothing!
  }
}
