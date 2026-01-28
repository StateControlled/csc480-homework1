package edu.depaul.wberthou;

import edu.depaul.wberthou.graph.CampusBuilding;
import edu.depaul.wberthou.graph.Graph;
import edu.depaul.wberthou.graph.Node;
import edu.depaul.wberthou.search.AStar;
import edu.depaul.wberthou.search.IterativeDeepeningDFS;
import edu.depaul.wberthou.util.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * William Berthouex <br>
 * CSC480 - Artificial Intelligence I <br>
 * Section 810 <br>
 * Fall 2026 <br>
 * Assignment 1 <br>
 * Due January 28, 2026 <br>
 * <p>
 * See README.md for answers and explanations.
 * </p>
 *
 * @author William Berthouex
 */
public class Main {
    private static final String INTRO = "William Berthouex%n CSC480 - Artificial Intelligence I%n Section 810%n Fall 2026%n Assignment 1%nDue January 28, 2026%n";
    private static final String QUESTION_TWO = "Consider just the connectivity of the locations in the graph above. Write an iterative deepening depth first search program to find the best path from Class Room Building A to the Conference Center.";
    private static final String QUESTION_THREE = "Consider both the (X,Y) coordinates and the path costs between the locations in the graph above.%nWrite an A* search program to find the best path from Class Room Building A to the Conference Center.";

    /**
     * Search parameters (starting Node and target Node) are hard-coded, but could be easily made dynamic.
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner;
        if (args.length > 0) {
            scanner = new Scanner(new File(args[0]));
        } else {
            scanner = new Scanner(System.in);
        }

        Graph graph = new Graph("campus.json");

        Optional<Node> first = graph.get(CampusBuilding.CLASSROOM_A.id());
        Optional<Node> second = graph.get(CampusBuilding.CONFERENCE_CENTER.id());

        if (first.isEmpty() || second.isEmpty()) {
            throw new RuntimeException("Cannot find start or end location. Graph may not be complete!");
        }

        Node start = first.get();
        Node end = second.get();

        System.out.printf(INTRO);
        System.out.println();
        // parse user input
        // if the user inputs more than one word, only the first will be evaluated
        while (true) {
            System.out.println("ENTER A COMMAND");
            Command userInput;

            if (!scanner.hasNextLine()) {
                System.err.println("Unable to read input.");
                return;
            }

            String intermediate = scanner.nextLine().toLowerCase().strip();
            String[] intermediate2 = intermediate.split(" ");
            userInput = Command.matches(intermediate2[0]);

            if (userInput == Command.EXIT) {
                System.out.println("EXIT");
                break;
            } else if (userInput == Command.IDDFS) {
                System.out.println("IDDFS");
                System.out.println("Iterative Deepening Depth-First Search");
                System.out.println(QUESTION_TWO);
                System.out.printf("Finding the best path from \"%s\" to \"%s\"...%n", start.getName(), end.getName());
                IterativeDeepeningDFS iddfs = new IterativeDeepeningDFS().setMaxSearchDepth(32);
                iddfs.setTarget(end);

                List<Node> result = iddfs.iterativeDeepeningDepthFirstSearch(start);
                System.out.println();
                System.out.println("BEST PATH");
                result.forEach(System.out::println);
                System.out.println();
            } else if (userInput == Command.ASTAR) {
                System.out.println("ASTAR");
                System.out.println("A*");
                System.out.println(QUESTION_THREE);
                System.out.printf("Finding the best path from \"%s\" to \"%s\"...%n", start.getName(), end.getName());

                AStar aStar = new AStar();

                List<Node> a = aStar.aStarSearch(graph, start, end);
                System.out.println();
                System.out.println("BEST PATH");
                a.forEach(System.out::println);
                System.out.println();
            } else if (userInput == Command.HELP) {
                System.out.println("HELP");
                for (Command c : Command.list()) {
                    if (c != Command.NONE) {
                        System.out.printf("%s : \t%s%n", c.getSymbol().toUpperCase(), c.getDescription());
                    }
                }
                System.out.println();
            } else {
                System.out.printf("The string \"%s\" is not a recognized command. Please try again or type \"HELP\".%n", intermediate);
            }
        }

        System.out.println();
        System.out.println("Compete code on GitHub at https://github.com/StateControlled/csc480-homework1");
        System.out.println("Goodbye");
        scanner.close();
    }

}
