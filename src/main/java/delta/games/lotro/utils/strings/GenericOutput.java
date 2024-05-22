package delta.games.lotro.utils.strings;

import delta.games.lotro.common.Identifiable;

/**
 * Generic output interface.
 * @author DAM
 */
public interface GenericOutput
{
  /**
   * Start document.
   * @param sb Output.
   */
  void startDocument(StringBuilder sb);
  /**
   * End document.
   * @param sb Output.
   */
  void endDocument(StringBuilder sb);
  /**
   * Start body.
   * @param sb Output.
   */
  void startBody(StringBuilder sb);
  /**
   * End body.
   * @param sb Output.
   */
  void endBody(StringBuilder sb);
  /**
   * Print a link.
   * @param sb Output.
   * @param text Link text.
   * @param to Link target.
   */
  void printLink(StringBuilder sb, String text, Identifiable to);
  /**
   * Print text.
   * @param sb Output.
   * @param text Text.
   */
  void printText(StringBuilder sb, String text);
  /**
   * Start a paragraph.
   * @param sb Output.
   */
  void startParagraph(StringBuilder sb);
  /**
   * End a paragraph.
   * @param sb Output.
   */
  void endParagraph(StringBuilder sb);
  /**
   * Switch to bold.
   * @param sb Output.
   */
  void startBold(StringBuilder sb);
  /**
   * End bold.
   * @param sb Output.
   */
  void endBold(StringBuilder sb);
  /**
   * Switch italic.
   * @param sb Output.
   */
  void startItalic(StringBuilder sb);
  /**
   * End italic.
   * @param sb Output.
   */
  void endItalic(StringBuilder sb);
  /**
   * Start a title.
   * @param sb Output.
   * @param level Title level.
   */
  void startTitle(StringBuilder sb, int level);
  /**
   * End a title.
   * @param sb Output.
   * @param level Title level.
   */
  void endTitle(StringBuilder sb, int level);
}
