package process;

import part4.ReplaceAssumptionSummonProcessor;
import project.AbstractProcess;
import spoon.reflect.CtModel;

/**
 * Created by Mathieu on 19/10/2017.
 */
public class Process_4 extends AbstractProcess {
    public void process(CtModel model)
    {
        model.processWith(new ReplaceAssumptionSummonProcessor());
    }
}
