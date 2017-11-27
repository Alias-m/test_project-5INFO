package theories;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.declaration.*;
import java.util.*;

public class ReplaceTheoryProcessor extends AbstractProcessor<CtClass> {
    @Override
    public void process(CtClass object) {
        List<Object> parameters = new ArrayList<>();
        List<CtAnnotation<?>> annot = object.getAnnotations();
        for(CtAnnotation a : annot)
            if(a.getActualAnnotation().toString().equals("@ExtendWith(Theories.class)")){
                List<CtField<?>> fields =  object.<CtField<?>>getFields();
                for(CtField<?> f : fields){
                    List<CtAnnotation<?>> annotField = f.getAnnotations();
                    for(CtAnnotation af : annotField)
                        if(af.getActualAnnotation().toString().equals("@DataPoint")){
                            parameters.add(f.getAssignment());
                        }
                }
            }

        CtAnnotation<?> parametersSource = getFactory().createAnnotation().setAnnotationType(getFactory().createCtTypeReference(CsvSource.class));
        CtNewArray<?> array = getFactory().createNewArray();
        for(Object o : parameters)
            array.addElement(getFactory().createLiteral(o));
        parametersSource.addValue("value", array);
        if(parameters.size() > 0)
        {
            Set<CtMethod<?>> methods = object.getMethods();
            for(CtMethod m : methods){
                List<CtAnnotation<?>> am = m.getAnnotations();
                CtAnnotation<?> theory = null;
                for(CtAnnotation a : am){
                    if(a.getActualAnnotation().toString().equals("@Theory")){
                        theory = a;
                    }
                }
                if(theory != null){
                    m.removeAnnotation(theory);
                    this.getFactory().Annotation().annotate(m, ParameterizedTest.class);
                    m.addAnnotation(parametersSource);
                }
            }
        }
    }
}
