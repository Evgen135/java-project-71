package hexlet.code;

import static hexlet.code.Differ.generate;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Compares")
public class App implements Callable<Integer> {

    @Option(
            names = {"-f", "--format"},
            description = "out format [default: stylish]",
            defaultValue = "stylish")
    private String format;

    @Parameters(index = "0", description = "path to first file")
    String filePath1;

    @Parameters(index = "1", description = "path to second file")
    String filePath2;

    @Override
    public Integer call() throws Exception {
        String diff = generate(filePath1, filePath2, format);
        System.out.println(diff);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args); // -h, -f
        System.exit(exitCode);
    }
}
