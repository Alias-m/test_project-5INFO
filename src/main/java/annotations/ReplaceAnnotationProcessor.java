package annotations;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import spoon.processing.*;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplaceAnnotationProcessor<T extends CtElement> extends AbstractProcessor<T> {
    @Override
    public void process(T object) {

        List<CtAnnotation<? extends Annotation>> annot = object.getAnnotations();
        List<CtAnnotation<? extends Annotation>> annot2 = new ArrayList<>();

        Map<CtAnnotation<? extends  Annotation>, Class<? extends  Annotation>> conversion = new HashMap<>();

        for (CtAnnotation annotation : annot){
            try
            {
                annot2.add(annotation);
                switch (annotation.getActualAnnotation().annotationType().getSimpleName()){
                    case "Before" :
                        conversion.put(annotation, BeforeEach.class);
                        break;
                    case "After" :
                        conversion.put(annotation, AfterEach.class);
                        break;
                    case "BeforeClass" :
                        conversion.put(annotation, BeforeAll.class);
                        break;
                    case "AfterClass" :
                        conversion.put(annotation, AfterAll.class);
                        break;
                    case "Ignore" :
                        conversion.put(annotation, Disabled.class);
                        break;
                    case "Category" :
                        conversion.put(annotation, Tag.class);
                        break;
                    case "RunWith" :
                    case "Rule" :
                    case "ClassRule" :
                        conversion.put(annotation, ExtendWith.class);
                        break;
                    default :
                        annot2.remove(annotation);
                        break;
                }
            }
            catch(Exception e){

            }
        }

        for (CtAnnotation annotation : conversion.keySet()){
            object.removeAnnotation(annotation);
            CtAnnotation<? extends Annotation> newAnnot = this.getFactory().Annotation().annotate(object, conversion.get(annotation));
            newAnnot.setValues(annotation.getValues());
        }

    }}
