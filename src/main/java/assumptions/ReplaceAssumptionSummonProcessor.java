package assumptions;

import org.junit.jupiter.api.Assumptions;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.reference.CtExecutableReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class ReplaceAssumptionSummonProcessor extends AbstractProcessor<CtInvocation> {
    private static HashMap<String, Action> methods;
    static{
        methods = new HashMap<>();
        methods.put("assumeTrue", (object, ref)-> {
            ref.setDeclaringType(object.getFactory().createCtTypeReference(Assumptions.class));
            Collections.reverse(object.getArguments());
            generateLambda(object);
        });
        methods.put("assumeFalse", (object, ref)-> {
            ref.setDeclaringType(object.getFactory().createCtTypeReference(Assumptions.class));
            Collections.reverse(object.getArguments());
            generateLambda(object);
        });
        methods.put("assumeNotNull", (object, ref)-> {
            ref.setDeclaringType(object.getFactory().createCtTypeReference(Assumptions.class));
            ref.setSimpleName("assumeTrue");
            CtLambda l = object.getFactory().createLambda();
            CtBlock c = object.getFactory().createBlock();

            //Creation du tableau
            CtLocalVariable e = c.getFactory().createLocalVariable();
            e.setSimpleName("tmp_lambda_array");
            e.setType(object.getFactory().createArrayTypeReference());
            e.getType().setDeclaringType(object.getFactory().createCtTypeReference(Object.class));
            CtNewArray create = e.getFactory().createNewArray();

            create.setType(object.getFactory().createArrayTypeReference());
            create.getType().setDeclaringType(object.getFactory().createCtTypeReference(Object.class));
            create.addDimensionExpression(object.getFactory().createLiteral(object.getArguments().size()));
            e.setAssignment(create);
            c.addStatement(e);

            //Ajout des éléments dans le tableau
            for(int i = 0; i < object.getArguments().size(); i++){
                CtAssignment assign = object.getFactory().createAssignment();
                CtArrayWrite write = object.getFactory().createArrayWrite();
                write.setIndexExpression(object.getFactory().createLiteral(i));
                write.setTarget(object.getFactory().createVariableRead().setVariable(e.getReference()));
                assign.setAssigned(write);
                assign.setAssignment((CtExpression) (object.getArguments().get(i)));
                c.addStatement(assign);
            }
            //Foreach
            CtForEach foreach = object.getFactory().createForEach();
            CtLocalVariable iterator = c.getFactory().createLocalVariable();
            iterator.setSimpleName("o");
            iterator.setType(object.getFactory().createCtTypeReference(Object.class));
            foreach.setVariable(iterator);
            foreach.setExpression(object.getFactory().createVariableRead().setVariable(e.getReference()));
            //If dans le foreach
            CtIf ctif = object.getFactory().createIf();
            CtBinaryOperator operator = object.getFactory().createBinaryOperator(
                    object.getFactory().createVariableRead().setVariable(iterator.getReference()),
                    object.getFactory().createLiteral().setValue(null),
                    BinaryOperatorKind.EQ
            );
            ctif.setCondition(operator);
            ctif.setThenStatement(object.getFactory().createReturn().setReturnedExpression(object.getFactory().createLiteral(false)));
            foreach.setBody(ctif);
            c.addStatement(foreach);
            c.addStatement( object.getFactory().createReturn().setReturnedExpression(object.getFactory().createLiteral(true)));

            object.getArguments();
            l.setBody(c);
            object.getArguments().clear();
            object.getArguments().add(l);
            generateLambda(object);
        });
        methods.put("assumeThat", (object, ref)-> {
            ref.setDeclaringType(object.getFactory().createCtTypeReference(Assumptions.class));
            ref.setSimpleName("assumingThat");
            List<CtExpression> l = object.getArguments();
            if(l.size() == 3)
                l.remove(0);
            CtInvocation invocation = object.getFactory().createInvocation();
            invocation.setExecutable(object.getFactory().createExecutableReference());
            invocation.getExecutable().setSimpleName("equals");
            invocation.setTarget(l.get(0));
            invocation.setArguments(((CtInvocation)l.get(1)).getArguments());
            List<CtExpression<?>> args = new ArrayList<>();
            args.add(invocation);
            object.setArguments(args);
            generateLambda(object);
        });
    }
    @Override
    public void process(CtInvocation object) {
        CtExecutableReference<?> ref = object.getExecutable();
        methods.getOrDefault(ref.getSimpleName(), (o, r) -> {}).execute(object, ref);
    }

    private interface Action{
        void execute(CtInvocation o, CtExecutableReference<?> ref);
    }

    private static void generateLambda(CtInvocation object){
        if(object.getArguments().size() == 1)
        {
            CtLambda l = object.getFactory().createLambda();
            CtBlock parent = (CtBlock)object.getParent();
            CtBlock c = object.getFactory().createBlock();

            for(int i = parent.getStatements().indexOf(object) + 1; i < parent.getStatements().size();){
                c.addStatement(parent.getStatement(i));
                parent.getStatements().remove(i);
            }
            l.setBody(c);
            object.addArgument(l);
        }
    }
}
