# JAR file to compile
JAR=Main.jar

# Entry point for the runnable Jar file
MANIFEST=manifest.txt

# Classes to include
CLASSES=\
	game/Main.class \
	\
	game/engine/Game.class \
	game/engine/GameState.class \
	game/engine/Scene.class \
	game/engine/View.class \
	game/engine/Sprite.class \
	game/engine/Entity.class


SOURCES=$(CLASSES:%.class=%.java)

# Other files to include in the .jar file
OTHER_FILES=\
	makefile \
	README.md

ANONYMOUS_CLASSES=\
  game/MyState.class\
	game/engine/GameFrame.class \
	game/engine/GameCanvas.class\
	# game/engine/Game\$$1.class


all: $(JAR)

$(JAR): $(CLASSES) $(MANIFEST) $(SOURCES) $(OTHER_FILES)
	jar cvfm $@ $(MANIFEST) $(ENTRY) $(ANONYMOUS_CLASSES) $^

%.class: %.java
	javac -source 8 -target 8 $<

# Run the main program
.PHONY: run
run: $(JAR)
	java -jar $(JAR)

.PHONY: clean
clean:
	rm -f $(CLASSES) $(JAR) $(ANONYMOUS_CLASSES)

