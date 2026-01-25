package edu.depaul.wberthou.util;

import edu.depaul.wberthou.graph.Arc;
import edu.depaul.wberthou.graph.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Reads data from a Json file in the <code>resources</code> directory.
 */
public class FileReader {
    protected final Gson gson;

    public FileReader() {
        this.gson = new Gson();
    }

    public ResultPair<Node, Arc> readFileToJavaObjects(String filename) throws IOException {
        String str = readFile(filename);
        return toJava(str);
    }

    /**
     * Read a file from the <code>resources</code> directory.
     *
     * @param filename      the name of the file to open
     * @return              File contents as a <code>String</code>
     * @throws IOException  if an I/O error occurs, if the file cannot be found
     */
    public String readFile(String filename) throws IOException {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename)) {
            if (inputStream == null) {
                throw new IOException("File not found");
            }
            return fileContentToString(inputStream);
        }
    }

    /**
     * @param inputStream   an <code>InputStream</code>
     * @return              InputStream contents as a <code>String</code>
     * @throws IOException  if an I/O error occurs
     */
    private String fileContentToString(InputStream inputStream) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line.strip());
            }
            inputStream.close();
            return builder.toString();
        }
    }

    public ResultPair<Node, Arc> toJava(String json) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        // result1
        JsonArray nodes = jsonObject.get("nodes").getAsJsonArray();
        List<JsonElement> nodeElementList = nodes.asList();

        List<Node> nodeList = new ArrayList<>();
        nodeElementList.forEach((e) -> {
            Node n = gson.fromJson(e, Node.class);
            nodeList.add(n);
        });

        // connections (result2)
        JsonArray arcs = jsonObject.getAsJsonArray("arcs");
        List<JsonElement> arcElementList = arcs.asList();

        List<Arc> arcList = new ArrayList<>();
        arcElementList.forEach((e) -> {
            // convert to intermediate object, then to Arc object
            IntermediateDataObject temp = gson.fromJson(e, IntermediateDataObject.class);
            Optional<Arc> a = IntermediateDataObject.toArc(nodeList, temp);

            if (a.isPresent()) {
                arcList.add(a.get());
            }
        });

        return new ResultPair<>(nodeList, arcList);
    }

    /**
     * Convenience for returning two lists.
     *
     * @param result1 a <code>List</code> of {@link Node Nodes}
     * @param result2  a <code>List</code> of {@link Arc Arcs}
     */
    public record ResultPair<A, B>(List<A> result1, List<B> result2) {

    }

    /**
     * An intermediate object to facilitate converting the given <code>Json</code> file to a Java object.
     */
    public static class IntermediateDataObject {
        protected String first;
        protected String second;
        protected int distance;

        public IntermediateDataObject() {

        }

        /**
         * Converts an <code>IntermediateDataObject</code> to an <code>Arc</code> object by searching for a matching <code>Node</code>
         * object in a given list for a <code>Node</code> with a matching name to the <code>IntermediateDataObject's</code>
         * <code>first</code> and <code>second</code> field.
         *
         * @param nodes                     a list of <code>Nodes</code>
         * @param intermediateDataObject    an intermediate object
         * @return                          an <code>Optional</code> containing a new <code>Arc</code> object or an <code>Empty</code> Optional
         */
        public static Optional<Arc> toArc(List<Node> nodes, IntermediateDataObject intermediateDataObject) {
            Optional<Node> first = find(nodes, intermediateDataObject.first);
            Optional<Node> second = find(nodes, intermediateDataObject.second);

            if (first.isPresent() && second.isPresent()) {
                return Optional.of(new Arc(first.get(), second.get(), intermediateDataObject.distance));
            }

            return Optional.empty();
        }

        private static Optional<Node> find(List<Node> nodes, String name) {
            for (Node n : nodes) {
                if (n.getName().equalsIgnoreCase(name)) {
                    return Optional.of(n);
                }
            }

            return Optional.empty();
        }

    }

}
