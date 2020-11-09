# JAR file to compile
JAR=Main.jar

# Entry point for the runnable Jar file
MANIFEST=manifest.txt

# Classes to include
CLASSES=\
	game/Main.class \
	\
	game/engine/Game.class \
	game/engine/Resource.class \
	game/engine/Scene.class \
	game/engine/View.class \
	game/engine/Entity.class \
	game/engine/AnimatedImage.class \
	game/engine/Sprite.class \
	game/engine/Background.class \
	game/engine/BackgroundType.class \
	game/engine/Mask.class \
	game/engine/GameAssets.class \
	game/engine/Sound.class \
	game/engine/Key.class \
	game/engine/TimerEntry.class \
	game/engine/Helpers.class \
	game/engine/Point2d.class \
	game/engine/Vector2d.class \
	\
	game/scenes/TitleScene.class \
	game/scenes/MainScene.class \
	game/scenes/LoadingScene.class \
	game/scenes/GameScene.class \
	\
	game/entities/StartButton.class \
	game/entities/Player.class \
	game/entities/Bullet.class \
	game/entities/Asteroid.class \
	game/entities/AsteroidExplosion.class \
	game/entities/PlayerExplosion.class \
	game/entities/WinSmiley.class \
	game/entities/BinaryExplosion.class \
	game/entities/Ant.class \
	game/entities/AntSpawner.class \
	game/entities/AntiVirus.class \
	game/entities/Worm.class \
	game/entities/Swooper.class \
	game/entities/Booger.class \
	game/entities/BoogerExplosion.class \
	\
	game/resources/Score.class


SOURCES=$(CLASSES:%.class=%.java)

# Other files to include in the .jar file
OTHER_FILES=\
	makefile \
	README.md

ANONYMOUS_CLASSES=\
	game/engine/GameFrame.class \
	game/engine/GameCanvas.class\
	game/engine/Background\$$1.class \
	game/entities/WormPhase.class \
	game/entities/Worm\$$1.class

ASSETS_FOLDER=assets

all: $(JAR)

$(JAR): $(CLASSES) $(MANIFEST) $(SOURCES) $(OTHER_FILES)
	jar cvfm $@ $(MANIFEST) $(ENTRY) $(ANONYMOUS_CLASSES) $(ASSETS_FOLDER)/* $^

%.class: %.java
	javac -source 8 -target 8 $<

# Run the main program
.PHONY: run
run: $(JAR)
	java -jar $(JAR)

.PHONY: clean
clean:
	rm -f $(CLASSES) $(JAR) $(ANONYMOUS_CLASSES)

