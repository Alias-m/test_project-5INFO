package project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import process.*;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.support.JavaOutputProcessor;

/**
 * Created by Mathieu on 18/10/2017.
 */
public class Main
{
    private static List<AbstractProcess> processes = new ArrayList<>();

    public static void main(String[] str)
    {
        Launcher launcher = new Launcher();

        launcher.addInputResource("src/test");
        launcher.getEnvironment().setAutoImports(true); // optional
        launcher.getEnvironment().setNoClasspath(true); // optional
        processes.add(new ProcessAnnotations());
        processes.add(new ProcessAssertions());
        processes.add(new ProcessAssumptions());
        processes.add(new ProcessTheories());
		launcher.buildModel();
        CtModel model = launcher.getModel();
        for(AbstractProcess p : processes)
            p.process(model);
        launcher.process();
        model.processWith(new JavaOutputProcessor(new File("./output/"), launcher.createPrettyPrinter()));
    }
}
