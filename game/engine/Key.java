package game.engine;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * All valid keyboard keys
 */
public enum Key {
  UP(KeyEvent.VK_UP),
  DOWN(KeyEvent.VK_DOWN),
  LEFT(KeyEvent.VK_LEFT),
  RIGHT(KeyEvent.VK_RIGHT),

  NUM_UP(KeyEvent.VK_KP_UP),
  NUM_DOWN(KeyEvent.VK_KP_DOWN),
  NUM_LEFT(KeyEvent.VK_KP_LEFT),
  NUM_RIGHT(KeyEvent.VK_KP_RIGHT),

  NUM_0(KeyEvent.VK_0),
  NUM_1(KeyEvent.VK_1),
  NUM_2(KeyEvent.VK_2),
  NUM_3(KeyEvent.VK_3),
  NUM_4(KeyEvent.VK_4),
  NUM_5(KeyEvent.VK_5),
  NUM_6(KeyEvent.VK_6),
  NUM_7(KeyEvent.VK_7),
  NUM_8(KeyEvent.VK_8),
  NUM_9(KeyEvent.VK_9),

  A(KeyEvent.VK_A),
  B(KeyEvent.VK_B),
  C(KeyEvent.VK_C),
  D(KeyEvent.VK_D),
  E(KeyEvent.VK_E),
  F(KeyEvent.VK_F),
  G(KeyEvent.VK_G),
  H(KeyEvent.VK_H),
  I(KeyEvent.VK_I),
  J(KeyEvent.VK_J),
  K(KeyEvent.VK_K),
  L(KeyEvent.VK_L),
  M(KeyEvent.VK_M),
  N(KeyEvent.VK_N),
  O(KeyEvent.VK_O),
  P(KeyEvent.VK_P),
  Q(KeyEvent.VK_Q),
  R(KeyEvent.VK_R),
  S(KeyEvent.VK_S),
  T(KeyEvent.VK_T),
  U(KeyEvent.VK_U),
  V(KeyEvent.VK_V),
  W(KeyEvent.VK_W),
  X(KeyEvent.VK_X),
  Y(KeyEvent.VK_Y),
  Z(KeyEvent.VK_Z),

  F1(KeyEvent.VK_F1),
  F2(KeyEvent.VK_F2),
  F3(KeyEvent.VK_F3),
  F4(KeyEvent.VK_F4),
  F5(KeyEvent.VK_F5),
  F6(KeyEvent.VK_F6),
  F7(KeyEvent.VK_F7),
  F8(KeyEvent.VK_F8),
  F9(KeyEvent.VK_F9),
  F10(KeyEvent.VK_F10),
  F11(KeyEvent.VK_F11),
  F12(KeyEvent.VK_F12),
  F13(KeyEvent.VK_F13),
  F14(KeyEvent.VK_F14),
  F15(KeyEvent.VK_F15),
  F16(KeyEvent.VK_F16),
  F17(KeyEvent.VK_F17),
  F18(KeyEvent.VK_F18),
  F19(KeyEvent.VK_F19),
  F20(KeyEvent.VK_F20),
  F21(KeyEvent.VK_F21),
  F22(KeyEvent.VK_F22),
  F23(KeyEvent.VK_F23),
  F24(KeyEvent.VK_F24),

  AMPERSAND(KeyEvent.VK_AMPERSAND),
  ASTERISK(KeyEvent.VK_ASTERISK),
  AT(KeyEvent.VK_AT),
  BACK_SLASH(KeyEvent.VK_BACK_SLASH),
  BRACE_LEFT(KeyEvent.VK_BRACELEFT),
  BRACE_RIGHT(KeyEvent.VK_BRACERIGHT),
  CARET(KeyEvent.VK_CIRCUMFLEX),
  CLOSE_BRACKET(KeyEvent.VK_CLOSE_BRACKET),
  COLON(KeyEvent.VK_COLON),
  COMMA(KeyEvent.VK_COMMA),
  DOLLAR(KeyEvent.VK_DOLLAR),
  EQUAL_SIGN(KeyEvent.VK_EQUALS),
  EXCLAMATION_POINT(KeyEvent.VK_EXCLAMATION_MARK),
  GREATER_THAN(KeyEvent.VK_GREATER),
  LEFT_PAREN(KeyEvent.VK_LEFT_PARENTHESIS),
  LESS_THAN(KeyEvent.VK_RIGHT_PARENTHESIS),
  MINUS(KeyEvent.VK_MINUS),
  HASHTAG(KeyEvent.VK_NUMBER_SIGN),
  OPEN_BRACKET(KeyEvent.VK_OPEN_BRACKET),
  PERIOD(KeyEvent.VK_PERIOD),
  PLUS(KeyEvent.VK_PLUS),
  SINGLE_QUOTE(KeyEvent.VK_QUOTE),
  DOUBLE_QUOTE(KeyEvent.VK_QUOTEDBL),
  RIGHT_PAREN(KeyEvent.VK_RIGHT_PARENTHESIS),
  SEMICOLON(KeyEvent.VK_SEMICOLON),
  FORWARD_SLASH(KeyEvent.VK_SLASH),
  UNDERSCORE(KeyEvent.VK_UNDERSCORE),
  SPACE(KeyEvent.VK_SPACE),

  CONTROL(KeyEvent.VK_CONTROL),
  ALT(KeyEvent.VK_ALT),
  DELETE(KeyEvent.VK_DELETE),
  BACK_SPACE(KeyEvent.VK_BACK_SPACE),
  CAPS_LOCK(KeyEvent.VK_CAPS_LOCK),
  ESCAPE(KeyEvent.VK_ESCAPE),
  END(KeyEvent.VK_END),
  ENTER(KeyEvent.VK_ENTER),
  SHIFT(KeyEvent.VK_SHIFT),
  HOME(KeyEvent.VK_HOME),
  PAGE_UP(KeyEvent.VK_PAGE_UP),
  PAGE_DOWN(KeyEvent.VK_PAGE_DOWN),
  PAUSE(KeyEvent.VK_PAUSE),
  CANCEL(KeyEvent.VK_CANCEL),
  SCROLL_LOCK(KeyEvent.VK_SCROLL_LOCK);

  private final int keyCode;
  private static final Map<Integer, Key> ALL_CODES = getKeyLookup();

  private Key(int keyCode) {
    this.keyCode = keyCode;
  }

  /**
   * Build lookup table of key code to key
   * @return   Lookup table
   */
  private static Map<Integer, Key> getKeyLookup() {
    Map<Integer, Key> m = new HashMap<>();
    for (Key key: Key.values()) { m.put(key.keyCode, key); }

    return m;
  }

  /**
   * Get KeyEvent key code associated with this key
   */
  public int getKeyCode() {
    return this.keyCode;
  }

  /**
   * Convert a key code into a Key object.
   * Returns null if the code is not valid or supported.
   *
   * @param   code    Code to lookup
   * @return          Associated Key or null if not found
   */
  public static Key findKeyFromCode(int code) {
    return ALL_CODES.get(code);
  }
}
