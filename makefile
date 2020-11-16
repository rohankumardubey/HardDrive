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
	game/scenes/LevelSelectScene.class \
	game/scenes/LoadingScene.class \
	game/scenes/GameScene.class \
	game/scenes/Level1.class \
	game/scenes/Level2.class \
	game/scenes/Level3.class \
	game/scenes/Level4.class \
	game/scenes/Level5.class \
	game/scenes/Level6.class \
	game/scenes/LevelCompleteScene.class \
	game/scenes/GameOverScene.class \
	game/scenes/YouWinScene.class \
	\
	game/entities/Player.class \
	game/entities/Bullet.class \
	game/entities/BinaryExplosion.class \
	game/entities/PlayerExplosion.class \
	game/entities/HealthEntity.class \
	\
	game/entities/antivirus/Ant.class \
	game/entities/antivirus/AntSpawner.class \
	game/entities/antivirus/AntiVirus.class \
	game/entities/antivirus/Worm.class \
	game/entities/antivirus/Swooper.class \
	game/entities/antivirus/Booger.class \
	game/entities/antivirus/BoogerExplosion.class \
	\
	game/entities/ui/BinaryFlicker.class \
	game/entities/ui/Button.class \
	game/entities/ui/ChangeSceneButton.class \
	game/entities/ui/LevelSelectButton.class \
	game/entities/ui/BackToLevelSelectButton.class \
	\
	game/entities/walls/Wall.class \
	game/entities/walls/Cactus.class \
	game/entities/walls/Pyramid.class \
	game/entities/walls/Rock.class \
	game/entities/walls/SnowTree.class \
	game/entities/walls/Tree.class \
	\
	game/entities/component/Component.class \
	game/entities/component/Resistor.class \
	game/entities/component/Transistor.class \
	game/entities/component/Capacitor.class \
	game/entities/component/Chip.class \
	game/entities/component/DataFile.class \
	\
	game/resources/Lives.class \
	game/resources/UnlockedLevels.class


SOURCES=$(CLASSES:%.class=%.java)

# Other files to include in the .jar file
OTHER_FILES=\
	makefile \
	README.md

ANONYMOUS_CLASSES=\
	game/engine/GameFrame.class \
	game/engine/GameCanvas.class\
	game/engine/Background\$$1.class \
	game/entities/antivirus/WormPhase.class \
	game/entities/antivirus/Worm\$$1.class

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

