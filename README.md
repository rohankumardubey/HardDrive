# CSCI-437 Fall 2020 - 2D Graphics Demo

Game of Life

<br />

## Compiling and Running

Make sure you have `javac` version 8 or later installed on your computer.
Then, you can build the program using:

```
make
```

This will create an executable jar file named `Main.jar`.
In Windows, you can double-click on the file to run it. Otherwise, use

```
java -jar Main.jar
```

or

```
make run
```

You will need to have a display attached or the program will not be able to run.

If you need to rebuild the program from scratch, you can do:

```
make clean
make
```

<br />

## The Game of Life

The 2D graphics demo uses [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) to generate really cool
looking filters. The Game of Life takes place on a square grid, where every cell in the grid is either dead or alive.
With this program, the grid starts with each cell dead or alive randomly, although other programs allow you to specify the cells manually.
On each iteration, the program looks at every cell in the grid and counts the number of orthogonal and diagonal neighbors (live cells).
A cell can have between 0 and 8 neighbors:

```
 1  2  3
 4 [#] 4
 6  7  8
```

Next, the program looks at the rules for a particular life instance. Each rule is expressed using `B/S` notation.
For example:

```
B123/S456
```

- `B123` - Any dead cells that have 1, 2, or 3 live cell neighbors will turn into a live cell this iteration. Otherwise, they will stay a dead cell.
- `S456` - Any live cells that have 4, 5, or 6 live cell neighbors will stay alive this iteration. Otherwise, they will turn into a dead cell.

The default rule invented by John Conway is `B3/S23`. However, by chaning these simple rules, we can generate all
kinds of cool life patterns. Computing the new state of all cells in the grid is called the next `Generation`.

## Filter

To convert the grid of cells into a cool filter, we assign an alpha value between 0 and 255 to each cell in the grid.
Initially, all of the alpha values start at 0. If a cell is alive during this generation, we increase the alpha
value by 10. Otherwise, we decrease the alpha value by 3. This simple structure creates some really cool filters and
animations to overlay on top of an image.

<br />

## The Program

![Program](img/Program.png)

The program consists of the filter output on the top and the controls along the bottom.

### Fractal Algorithm

For these cool looking filters to be useful, I needed an intersting set of images that I could use for applying the filters.
My original idea was to have a pre-built set of images, but I later decided to generate the images procedurally.

The program uses a simple polygon fractal algorithm to generate a random picture. It picks either a triangle, square, or pentagon.
It then divides each side into thirds and puts another polygon in the middle of each side. For each polygon on the edge, it also
puts an even smaller polygon to the left and right of the large polygon. In this way, we get polygons of polygons of polygons
in an infinite recursion. The fractal looks really cool!

Unfortunately, I had to write this algorithm twice to actually get it working properly. That is why you see the leftover `TriangleFractal.java`
code file. This was my first attempt to write this fractal algorithm, and it didn't work for polygons with more than three sides
(which is why I called it TriangleFractal). After fixing my algorithm, I also found that polygons with more than 5 sides don't
have enough room to fractal infinitely, even though the algorithm should work with any number of sides. This is why I limit the
algorithm to triangles, squares, and pentagons.

The final image is generated using 6 to 10 random fractals. Each fractal is given a random angle, location, and size on the canvas.
Since the program only has a finite resolution, I limit the number of fractal iterations in the code to avoid wasting computation.
Finally, I change the colors after each iteration to generate a colorful picture. Here is the final result:

![Fractal Generation](img/Fractals.gif)

### Interface Buttons

After the fractal image has been generated, you can mess with the various life filters in the program.
The program has 23 built-in life filters that you can switch between to generate a variety of effects.

![Buttons](img/Buttons.png)

The buttons function as follows

- **Prev** - Go to the previous life rule
- **Random** - Go to a random life rule different from the current rule
- **Next** - Go to the next life rule
- **New Image** - Generate a new fractal image
- **Toggle** - Switch between the image filter and the underlying game of life
- **Start / Stop** - Run the game of life filter automatically
- **Step** - Move ahead one generation in the life filter
- **Reset** - Reset the life filter and clear the grid to a random state. This does not change the current life rule.

In the top left corner of the image is the current game of life rule and the generation number:

![Game of Life Labels](img/Labels.png)

If you click on the **Toggle** button, you can see the life grid behind the scenes that is generating the unique filter:

![Life Grid](img/Life.gif)

Finally, the program allows you to see a small preview of the life while the filter is running. If you click inside the fractal
image, you can move around a small preview box to see the game of life underneath. **Right click** on the image to hide the preview.

![Preview Screen](img/PreviewScreen.gif)

In the end, there are loads of cool combinations that you can use to generate many unique images.
I had so much fun building this program, and even more fun playing around with the life parameters after the program was finished.

<br />

## Final Results

There are so many unique combinations of filters to use with this program.
Here are just a few cool images that I was able to generate:

![Output 1](img/Output1.png)

![Output 2](img/Output2.png)

![Output 3](img/Output3.png)
