package process;

import assertions.ReplaceExceptedExceptionProcessor;
import project.AbstractProcess;
import spoon.reflect.CtModel;


public class ProcessAssertions extends AbstractProcess {
    public void process(CtModel model)
    {
        model.processWith(new ReplaceExceptedExceptionProcessor());
    }
}
