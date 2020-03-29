package com.aim.project.pwp.solution;

import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;

/**
 *
 * @author Warren G. Jackson
 *
 *
 */
public class SolutionRepresentation implements SolutionRepresentationInterface {

	private int[] aiSolutionRepresentation;

	public SolutionRepresentation(int[] aiRepresentation) {
		this.aiSolutionRepresentation = aiRepresentation;
	}

	@Override
	public int[] getSolutionRepresentation() {
		return aiSolutionRepresentation;
	}

	@Override
	public void setSolutionRepresentation(int[] aiSolutionRepresentation) {
		this.aiSolutionRepresentation = aiSolutionRepresentation;
	}

	/**
	 *
	 * @return The total number of locations in this instance (excludes DEPOT and HOME).
	 */
	@Override
	public int getNumberOfLocations() {
		return aiSolutionRepresentation.length;
	}

	/**
	 *
	 * @return A deep clone of the solution representation.
	 */
	@Override
	public SolutionRepresentationInterface clone() {
		return new SolutionRepresentation(this.aiSolutionRepresentation.clone());
	}
}
