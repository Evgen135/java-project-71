package hexlet.code;
import static hexlet.code.Differ.generate;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.util.concurrent.Callable;


@Command(name = "gendiff",mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Compares")
public class App implements Callable<Integer> {

    @Option(names = {"-f", "--format"}, description = "out files", defaultValue = "stylish")
    private String format;

    @Parameters(index = "0",description = "enter the path to the file")
    String filePath1;

    @Parameters(index = "1",description ="enter the path to the file")
    String filePath2;

    @Override
    public Integer call() throws Exception {
        String diff = generate(filePath1, filePath2, format);
        System.out.println(diff);
        return 0;
    }

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        int exitCode = new CommandLine(new App()).execute(args); //-h, -f
        System.exit(exitCode);
        //System.exit(exitCode);
    }

}

