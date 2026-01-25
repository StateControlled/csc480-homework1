# CSC480 - Artificial Intelligence I, Assignment 1

William Berthouex <br>
CSC480 - Artificial Intelligence I <br>
Section 810 <br>
Fall 2026 <br>
Assignment 1 <br>
Due January 28, 2026

---

## Run

This project was built using Gradle v8.14

To run, enter the following in the terminal from the directory homework1:

>./gradlew run

---

## Assignment Questions

#### Problem 1. 20 Points: Question 3.21 Prove each of the following statements, or give a counterexample:

##### Part 1. Breadth-first search is a special case of uniform-cost search.
      
Here is the pseudocode for a UCS:
<pre>
Node uniformCost (Problem problem) {
frontier.clear(); // frontier is a priority queue
explored.clear();
frontier.pushBack( Node(problem.getInitialState()) );

	while ( !frontier.isEmpty() ) {
		node := frontier.removeLowest();
		
		if ( problem. satisfiesGoal(node.state) )
			return node;
			
		explored.insert(node);
		
		forAll ( action in problem.actions(node.state()) ) {
			child := Node(problem,node,action);
			
			if ( !explored.has(child) ) {
				if ( frontier.has(child) ) {
					if ( frontier.get(child.state).cost > child.cost )
						frontier.replaceWith(child);
				} else {
					frontier.insert(nextNode);
				}
			}
		}
	}
	return failure;
}
</pre>
Let the cost of all actions be 1. Since all costs are the same, line 19 will never be called, so
the algorithm will only act at line 21. The cost is effectively equal to the current depth in the graph.
We can then rewrite line 17 as follows:
<pre>
if (!explored.has(child) && !frontier.has(child) {
    frontier.insert(nextNode);
}
</pre>

The frontier is a priority queue sorted by cost, but if all costs are equal, it is then
effectively sorted by depth, becoming a FIFO queue - just like BFS.

##### Part 2. Depth-first search is a special case of best-first tree search. 

The primary difference between best-first and depth-first is that best-first considers the weight of the action required to get to a new node in the graph.
If all nodes are evaluated to be equal, then a best-first tree search will behave exactly as a DFS.

##### Part 3. Uniform-cost search is a special case of A* search.

UCS evaluates the cost of an action with f(n) = g(n), where g(n) is the length of the path from the starting node to n. 
A* uses the evaluation function f(n) = g(n) + h(n) where h(n) is a heuristic function, and is an estimate of the distance from n to the goal node. 
h(n) must be admissible.

UCS is a special case of A* which corresponds to having h(n) = 0, for all n. 
A heuristic function h(n)=0 is admissible, because it never over-estimates the distance to the goal, 
the estimation is simply always zero (0).
Therefor UCS is a special case of A*


#### Problem 2. 20 Points: Iterative Deepening Depth First Search

See edu.depaul.wberthou.search.IterativeDeepeningDFS.java for implementation.


#### Problem 3. 20 Points: A* Search

See edu.depaul.wberthou.search.AStar.java for implementation. 
See problem3-full-disclosure.md for information on AI-generated answer to this problem.


#### Problem 4. 20 Points: Adversial search heuristics functions

The Minimax algorithm assumes optimal moves from 'players.' Computing the hueristic at nodes A and B defeats the purpose
of searching ahead for the best move. Since C and D are states that are a result of A, computing the hueristic at A effectively
means the move or reward is counted twice.


#### Problem 5. 20 Points: Adversial search heuristics

Return E<sub>v</sub> = 53 to A<sub>v</sub> = 53
then A<sub>b</sub> = 53 because there is no other data.

F<sub>v</sub> = 56. F<sub>v</sub> > E<sub>v</sub> so A is not updated because it is a min function.

return A<sub>v</sub> to Root<sub>v</sub>, then Root<sub>v</sub> = 53

B<sub>a</sub> = 53 from Root<sub>v</sub>;
B<sub>v</sub> = 47 from G<sub>v</sub>

Since this is a min function, B<sub>v</sub> will be <= to 47 so B<sub>v</sub> will not update.

Since B<sub>a</sub> > B<sub>v</sub> (Root is a max function) then the branch can be pruned<br>
Root is not updated

C<sub>a</sub> = 53 from Root<sub>v</sub>;
C<sub>v</sub> = 45 from I<sub>v</sub>

Since C<sub>a</sub> > C<sub>v</sub> then the branch can be pruned.

D<sub>a</sub> = 53 from Root<sub>v</sub>;
D<sub>v</sub> = 45 from K<sub>v</sub>

Since C<sub>a</sub> > C<sub>v</sub> then the branch can be pruned.
 
Finally, Root<sub>v</sub> = 53.


## Links

Complete code on GitHub [here](https://github.com/StateControlled/CSC480-AI/tree/main/homework1)

This project uses Google's **Gson** library to parse Json files: [Gson GitHub Repository](https://github.com/google/gson)

