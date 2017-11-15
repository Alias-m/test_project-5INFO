package process;

import part1.*;
import project.AbstractProcess;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

/**
 * Created by Mathieu on 19/10/2017.
 */
public class Process_1 extends AbstractProcess {
    public void process(CtModel model)
    {
        model.processWith(new ReplaceAnnotationProcessor<CtClass>());
        model.processWith(new ReplaceAnnotationProcessor<CtMethod>());
    }
}
