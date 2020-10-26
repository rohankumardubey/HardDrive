package game;

import game.engine.*;

class MyState implements GameState {}

public class Main {
  public static void main(String[] args) {
    Game g = new Game(new MyState());
    g.setTitle("Super Title");
    // g.setScene(new Scene(100, 100));
    g.run();
  }
}