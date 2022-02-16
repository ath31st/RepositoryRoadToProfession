package maze;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.IntStream.range;

public class Maze {
    private static final Logger LOG = Logger.getLogger(Maze.class.getName());
    private static final String CELL_EMPTY = "  ";
    private static final String CELL_WALL = "\u2588\u2588";
    private static final String CELL_PATH = "//";
    private static final int MAX_WEIGHT = 10;

    private int height;
    private int width;
    private int start;
    private int finish;
    private BitSet maze;
    private BitSet path;

    public Maze() {
    }

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        maze = new BitSet(height * width);
        path = new BitSet(height * width);
        maze.set(0, maze.size());
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public long[] getMap() {
        return maze.toLongArray();
    }

    public void setMap(long[] maze) {
        this.maze = BitSet.valueOf(maze);
        this.path = new BitSet(this.maze.size());
    }

    public Maze generate() {
        final var random = new Random();
        final int rows = (height - 1) / 2;
        final int cols = (width - 1) / 2;
        final int step = 2 * cols - 1;
        final var edges = range(0, 2 * cols * rows - rows - cols)
                .mapToObj(i -> {
                    int row = 1 + i / step * 2
                            + (i % step < cols - 1 ? 0 : 1);
                    int col = i % step < cols - 1
                            ? 2 + i % step * 2
                            : 1 + (i % step - cols + 1) * 2;
                    int edgeIndex = row * width + col;
                    var isHorizontal = i % step < cols - 1;
                    int dx = isHorizontal ? 1 : width;
                    int nodeA = edgeIndex - dx;
                    int nodeB = edgeIndex + dx;
                    int edgeWeight = 1 + random.nextInt(MAX_WEIGHT);
                    return new Edge(edgeIndex, edgeWeight, nodeA, nodeB);
                }).toArray(Edge[]::new);

        maze.clear(width + 1);
        range(1, rows * cols)
                .forEach(i -> Arrays.stream(edges)
                        .filter(Edge::isBorder)
                        .min(comparing(Edge::getWeight))
                        .orElseThrow()
                        .clearEdge());
        clearDoors();
        return this;
    }

    private void clearDoors() {
        start = width;
        finish = width * (height - (height % 2 == 0 ? 2 : 1)) - 1;

        maze.clear(start);
        maze.clear(finish);
        if (width % 2 == 0) {
            maze.clear(finish - 1);
        }
    }


    @Override
    public String toString() {
        path.clear();
        return range(0, height * width).mapToObj(this::getCell).collect(Collectors.joining());
    }

    public boolean findPath(int index) {
        if (index < 0 || index >= height * width || maze.get(index) || path.get(index)) {
            return false;
        }
        path.set(index);

        if (index == finish
                || findPath(index - 1)
                || findPath(index + 1)
                || findPath(index + width)
                || findPath(index - width)) {
            return true;
        }
        path.clear(index);
        return false;
    }

    @JsonIgnore
    public String getPath() {
        path.clear();
        var isFound = findPath(start);
        LOG.log(Level.FINER, "is the path found: {0}", isFound);
        return range(0, height * width).mapToObj(this::getCell).collect(Collectors.joining());
    }

    @JsonIgnore
    private String getCell(int index) {
        var separator = index % width == 0 ? "\n" : "";

        if (maze.get(index)) {
            return separator + CELL_WALL;
        }
        if (path.get(index)) {
            return separator + CELL_PATH;
        }
        return separator + CELL_EMPTY;
    }

    class Edge {
        final int edgeIndex;
        final int weight;
        final int nodeA;
        final int nodeB;

        Edge(int edgeIndex, int weight, int nodeA, int nodeB) {
            this.weight = weight;
            this.nodeA = nodeA;
            this.nodeB = nodeB;
            this.edgeIndex = edgeIndex;
        }

        boolean isBorder() {
            return maze.get(nodeA) ^ maze.get(nodeB);
        }

        int getWeight() {
            return weight;
        }

        void clearEdge() {
            maze.clear(nodeA);
            maze.clear(nodeB);
            maze.clear(edgeIndex);
        }
    }
}