package process;

import annotations.*;
import project.AbstractProcess;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;


public class ProcessAnnotations extends AbstractProcess {
    public void process(CtModel model)
    {
        model.processWith(new ReplaceAnnotationProcessor<CtClass>());
        model.processWith(new ReplaceAnnotationProcessor<CtMethod>());
    }
}
