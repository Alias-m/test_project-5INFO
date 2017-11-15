package project;

import java.util.ArrayList;
import java.util.List;

import process.Process_1;
import process.Process_2;
import process.Process_3;
import process.Process_4;
import process.Process_5;
import process.Process_6;
import process.Process_7;
import spoon.Launcher;
import spoon.reflect.CtModel;

/**
 * Created by Mathieu on 18/10/2017.
 */
public class Main
{
    private static List<AbstractProcess> processes = new ArrayList<>();

    public static void main(String[] str)
    {
        Launcher launcher = new Launcher();

        launcher.addInputResource("src/test/java/file.java");
        launcher.getEnvironment().setAutoImports(true); // optional
        launcher.getEnvironment().setNoClasspath(true); // optional
        processes.add(new Process_1());
        processes.add(new Process_2());
        processes.add(new Process_3());
        processes.add(new Process_4());
        processes.add(new Process_5());
        processes.add(new Process_6());
        processes.add(new Process_7());
		launcher.buildModel();
        CtModel model = launcher.getModel();
        for(AbstractProcess p : processes)
            p.process(model);
        launcher.process();
        //model.processWith(new JavaOutputProcessor(new File("./output/"), launcher.createPrettyPrinter()));
    }
}
