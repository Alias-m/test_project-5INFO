package assertions;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLambda;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.code.CtFieldReadImpl;
import spoon.support.reflect.code.CtLambdaImpl;

public class ReplaceExceptedExceptionProcessor extends AbstractProcessor<CtMethod> {

    

		@Override
	public void process(CtMethod object) {

			List<CtAnnotation<? extends Annotation>> annot = object.getAnnotations();
			List<CtAnnotation<? extends Annotation>> annot2 = new ArrayList<>();

			Factory fac = object.getFactory();
			CtTypeReference<?> typeref = fac.Type().createReference(Assertions.class);

			
			for(CtAnnotation annotation : annot){
				if(annotation.getActualAnnotation().annotationType().getSimpleName().equals("Test")){
					annot2.add(annotation);

					CtFieldReadImpl valueExpected = (CtFieldReadImpl) annotation.getValues().get("expected");

					CtInvocation invocation = object.getFactory().createInvocation();
					invocation.setExecutable(object.getFactory().createExecutableReference());
					invocation.getExecutable().setDeclaringType(object.getFactory().createCtTypeReference(Assertions.class));
					invocation.getExecutable().setSimpleName("assertThrows");


					CtLambda l = new CtLambdaImpl();
					l.setBody(object.getBody());

					invocation.addArgument(valueExpected);
					invocation.addArgument(l);

					object.setBody(invocation);

				}
			}

			for(CtAnnotation a : annot2){
				object.removeAnnotation(a);
				CtAnnotation<? extends Annotation> newAnnot = this.getFactory().Annotation().annotate(object, Test.class);

			}

			

			
	}

}

