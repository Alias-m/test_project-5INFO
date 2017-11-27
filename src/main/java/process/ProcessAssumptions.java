package process;

import assumptions.ReplaceAssumptionSummonProcessor;
import project.AbstractProcess;
import spoon.reflect.CtModel;


public class ProcessAssumptions extends AbstractProcess {
    public void process(CtModel model)
    {
        model.processWith(new ReplaceAssumptionSummonProcessor());
    }
}
