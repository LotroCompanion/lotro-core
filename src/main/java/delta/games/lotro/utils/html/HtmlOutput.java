package delta.games.lotro.utils.html;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.utils.strings.GenericOutput;

/**
 * HTML output.
 * @author DAM
 */
public class HtmlOutput implements GenericOutput
{
  private LinkGenerator _linkGenerator;

  /**
   * Constructor.
   * @param linkGenerator
   */
  public HtmlOutput(LinkGenerator linkGenerator)
  {
    _linkGenerator=linkGenerator;
  }

  @Override
  public void startDocument(StringBuilder sb)
  {
    sb.append("<html>");
  }

  @Override
  public void endDocument(StringBuilder sb)
  {
    sb.append("</html>");
  }

  @Override
  public void startBody(StringBuilder sb)
  {
    sb.append("<body>");
  }

  @Override
  public void endBody(StringBuilder sb)
  {
    sb.append("</body>");
  }

  @Override
  public void printLink(StringBuilder sb, String text, Identifiable to)
  {
    String link=_linkGenerator.buildURL(to);
    HtmlUtils.printLink(sb,link,text);
  }

  @Override
  public void printText(StringBuilder sb, String text)
  {
    sb.append(HtmlUtils.toHtml(text));
  }

  @Override
  public void startParagraph(StringBuilder sb)
  {
    sb.append("<p>");
  }

  @Override
  public void endParagraph(StringBuilder sb)
  {
    sb.append("</p>");
  }

  @Override
  public void startBold(StringBuilder sb)
  {
    sb.append("<b>");
  }

  @Override
  public void endBold(StringBuilder sb)
  {
    sb.append("</b>");
  }

  @Override
  public void startItalic(StringBuilder sb)
  {
    sb.append("<i>");
  }

  @Override
  public void endItalic(StringBuilder sb)
  {
    sb.append("</i>");
  }

  @Override
  public void startTitle(StringBuilder sb, int level)
  {
    sb.append("<h").append(level).append('>');
  }

  @Override
  public void endTitle(StringBuilder sb, int level)
  {
    sb.append("</h").append(level).append('>');
  }
}
