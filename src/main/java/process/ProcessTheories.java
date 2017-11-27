package process;

import theories.ReplaceTheoryProcessor;
import project.AbstractProcess;
import spoon.reflect.CtModel;


public class ProcessTheories extends AbstractProcess {
    public void process(CtModel model)
    {
        model.processWith(new ReplaceTheoryProcessor());
    }
}
