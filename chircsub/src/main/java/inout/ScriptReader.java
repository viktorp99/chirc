package inout;

import fpinjava.Result;
import fpinjava.Tuple;
import list.List;

public class ScriptReader implements Input {
    private List<String> commands;

    public ScriptReader(List<String> commands) {
        super();
        this.commands = commands;
    }

    public ScriptReader(String... commands) {
        super();
        this.commands = List.list(commands);
    }

    @Override
    public Result<Tuple<Integer, Input>> readInt() {
        try {
            return commands.isEmpty() ? Result.failure("Not enough entries in script")
                    : Result.success(new Tuple<>(Integer.parseUnsignedInt(commands.headOption().getOrElse("")),
                    new ScriptReader(commands.drop(1))));
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

    @Override
    public Result readLine() {
        try {
            // System.out.println("Here SCR readLine");
            return commands.isEmpty() ? Result.failure("Not enough entries in script")
                    : Result.success(
                    new Tuple<>(commands.headOption().getOrElse(""), new ScriptReader(commands.drop(1))));
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

    public List<String> toList() {
        return this.commands;

    }

    @Override
    public void close() throws Exception {
        try {
            System.out.println("ScriptWriter closed!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}