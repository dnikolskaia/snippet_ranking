package rs.dnikolskaia.model.scorers;

public class VariableScorer implements ParameterScorer {
    public static final double INITIALIZED_VARIABLE_PARAMETER_SCORE = 1.0;
    public static final double UNINITIALIZED_VARIABLE_PARAMETER_SCORE = 0.0;
    boolean initialized;

    public VariableScorer(boolean initialized) {
        this.initialized = initialized;
    }

    @Override
    public double getScore() {
        if (initialized)
            return INITIALIZED_VARIABLE_PARAMETER_SCORE;
        return UNINITIALIZED_VARIABLE_PARAMETER_SCORE;
    }
}
