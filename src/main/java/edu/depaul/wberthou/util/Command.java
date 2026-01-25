package edu.depaul.wberthou.util;

public enum Command {
    NONE("", ""),
    IDDFS("iddfs", "Performs a search using the Iterative Deepening Depth-First Search algorithm"),
    ASTAR("astar", "Performs a search using the A* (A-Star) search algorithm"),
    HELP("help", "Prints the list of available commands"),
    EXIT("exit", "Exits the program");

    private final String symbol;
    private final String description;
    Command(String symbol, String description) {
        this.symbol = symbol;
        this.description = description;
    }

    private static final Command[] vals = values();

    public static Command matches(String name) {
        for (Command c : vals) {
            if (c.symbol.equalsIgnoreCase(name)) {
                return c;
            }
        }
        return NONE;
    }

    public static Command[] list() {
        return vals;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

}
