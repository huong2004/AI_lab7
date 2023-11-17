package lab_7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GA_NQueenAlgo {
	public static final int POP_SIZE = 100;// Population size
	public static final double MUTATION_RATE = 0.03;
	public static final int MAX_ITERATIONS = 1000;
	List<Node> population = new ArrayList<Node>();
	Random rd = new Random();

	// initialize the individuals of the population
	public void initPopulation() {
		for (int i = 0; i < POP_SIZE; i++) {
			Node ni = new Node();
			ni.generateBoard();
			population.add(ni);
		}
	}

	public Node execute() {
		// Enter your code here
		initPopulation();
		int k = 0;
		while (k++ < MAX_ITERATIONS) { // Dừng khi hết vòng lặp cho phép
			List<Node> newPopulation = new ArrayList<>(); // DS cac ca the con
			for (int i = 0; i < POP_SIZE; i++) {
				Node x = getParentByRandomSelection();
				Node y = getParentByRandomSelection();
				Node child = reproduce(x, y);
				if (rd.nextDouble() < MUTATION_RATE)
					mutate(child);
				if (child.getH() == 0)
					return child;
				newPopulation.add(child);
			} // end for
			population = newPopulation;
		} // end while
		Collections.sort(population);
		return population.get(0);

	}

	// Mutate an individual by selecting a random Queen and
	// move it to a random row.
	public void mutate(Node node) {
		// Enter your code here
		int randomQueen = rd.nextInt(Node.N);
		int newRow = rd.nextInt(Node.N);
		node.setRow(randomQueen, newRow);
	}

	// Crossover x and y to reproduce a child
	public Node reproduce(Node x, Node y) {
		// Enter your code here
		Node child = new Node();
		child.generateBoard();
		int c = rd.nextInt(Node.N);
		for (int i = 0; i < c; i++) {
			child.setRow(i, x.getRow(i));

		}
		for (int i = c + 1; i < Node.N; i++)
			child.setRow(i, y.getRow(i));
		return child;
	}

	// Select K individuals from the population at random and
	// select the best out of these to become a parent.
	public Node getParentByTournamentSelection() {
		// Select K individuals randomly
		Node bestParent = null;
		for (int i = 0; i < 4; i++) {
			int randomIndex = rd.nextInt(POP_SIZE);
			Node candidate = population.get(randomIndex);

			// Compare the fitness of the candidate with the current best
			if (bestParent == null || candidate.getH() > bestParent.getH()) {
				bestParent = candidate;
			}
		}

		// Return the best individual as the parent
		return bestParent;
	}

	// Select a random parent from the population
	public Node getParentByRandomSelection() {
		// Enter your code here
		int randomIndex = rd.nextInt(POP_SIZE);
		// Return the selected parent
		return population.get(randomIndex);

	}
}
