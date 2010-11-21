/**
 * Generated with Acceleo
 */
package net.certware.argument.gsn.providers;

import org.eclipse.emf.eef.runtime.impl.providers.ComposedPropertiesEditionProvider;

/**
 * 
 * 
 */
public class GsnPackagePropertiesEditionProvider extends ComposedPropertiesEditionProvider {

	/**
	 * Default Constructor
	 * 
	 */
	public GsnPackagePropertiesEditionProvider() {
		super();
		append(createGoalPropertiesEditionProvider());
		append(createArgumentDiagramPropertiesEditionProvider());
		append(createStrategyPropertiesEditionProvider());
		append(createSolutionPropertiesEditionProvider());
		append(createAssumptionPropertiesEditionProvider());
		append(createContextPropertiesEditionProvider());
		append(createJustificationPropertiesEditionProvider());
	}

	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * Goal instances.
	 * 
	 */
	protected GoalPropertiesEditionProvider goalPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a Goal
	 * 
	 */
	public GoalPropertiesEditionProvider createGoalPropertiesEditionProvider() {
		if (goalPropertiesEditionProvider == null)
			goalPropertiesEditionProvider = new GoalPropertiesEditionProvider();
		return goalPropertiesEditionProvider;
	}

	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * ArgumentDiagram instances.
	 * 
	 */
	protected ArgumentDiagramPropertiesEditionProvider argumentDiagramPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a ArgumentDiagram
	 * 
	 */
	public ArgumentDiagramPropertiesEditionProvider createArgumentDiagramPropertiesEditionProvider() {
		if (argumentDiagramPropertiesEditionProvider == null)
			argumentDiagramPropertiesEditionProvider = new ArgumentDiagramPropertiesEditionProvider();
		return argumentDiagramPropertiesEditionProvider;
	}

	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * Strategy instances.
	 * 
	 */
	protected StrategyPropertiesEditionProvider strategyPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a Strategy
	 * 
	 */
	public StrategyPropertiesEditionProvider createStrategyPropertiesEditionProvider() {
		if (strategyPropertiesEditionProvider == null)
			strategyPropertiesEditionProvider = new StrategyPropertiesEditionProvider();
		return strategyPropertiesEditionProvider;
	}

	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * Solution instances.
	 * 
	 */
	protected SolutionPropertiesEditionProvider solutionPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a Solution
	 * 
	 */
	public SolutionPropertiesEditionProvider createSolutionPropertiesEditionProvider() {
		if (solutionPropertiesEditionProvider == null)
			solutionPropertiesEditionProvider = new SolutionPropertiesEditionProvider();
		return solutionPropertiesEditionProvider;
	}

	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * Assumption instances.
	 * 
	 */
	protected AssumptionPropertiesEditionProvider assumptionPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a Assumption
	 * 
	 */
	public AssumptionPropertiesEditionProvider createAssumptionPropertiesEditionProvider() {
		if (assumptionPropertiesEditionProvider == null)
			assumptionPropertiesEditionProvider = new AssumptionPropertiesEditionProvider();
		return assumptionPropertiesEditionProvider;
	}

	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * Context instances.
	 * 
	 */
	protected ContextPropertiesEditionProvider contextPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a Context
	 * 
	 */
	public ContextPropertiesEditionProvider createContextPropertiesEditionProvider() {
		if (contextPropertiesEditionProvider == null)
			contextPropertiesEditionProvider = new ContextPropertiesEditionProvider();
		return contextPropertiesEditionProvider;
	}

	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * Justification instances.
	 * 
	 */
	protected JustificationPropertiesEditionProvider justificationPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a Justification
	 * 
	 */
	public JustificationPropertiesEditionProvider createJustificationPropertiesEditionProvider() {
		if (justificationPropertiesEditionProvider == null)
			justificationPropertiesEditionProvider = new JustificationPropertiesEditionProvider();
		return justificationPropertiesEditionProvider;
	}

}
