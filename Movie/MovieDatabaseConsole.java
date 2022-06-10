import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MovieDatabaseConsole {
	/**

	 * @param args
	 *            an array of arguments supplied from the command line
	 */
	public static void main(String args[]) {
		// 표준 입력을 읽을 준비를 한다.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 프로그램에서 사용할 MovieDB 객체를 생성한다.
		MovieDB db = new MovieDB();

		String input = null;
		while (true) {
			try {
				// 표준 입력으로부터 한 줄을 입력받는다.
				input = br.readLine().trim();

				if (input.isEmpty())
					continue;

				if (input.toUpperCase().equals("QUIT"))
					break;

				// 입력을 해석한다.
				ConsoleCommand command = parse(input);

				command.apply(db);

			} catch (CommandParseException e) {
				System.err.printf("command parse failure: %s [cmd=%s, input=%s]\n",
						e.getMessage(), e.getCommand(), e.getInput());
				e.printStackTrace(System.err);
			} catch (CommandNotFoundException e) {
				System.err.printf("command not found: %s\n", e.getCommand());
				e.printStackTrace(System.err);
			} catch (Exception e) {
				System.err.printf("unexpected exception with input: [%s]\n", input);
				e.printStackTrace(System.err);
			}
		}
	}

	/**
	 * {@code input}을 해석(parse)하여 ConsoleCommand 객체를 생성해 반환한다.
	 *
	 * @param input
	 * @return
	 * @throws Exception
	 */
	private static ConsoleCommand parse(String input) throws Exception {
		// 우선 어떤 종류의 ConsoleCommand 를 생성할 것인지 결정한다.
		ConsoleCommand command = null;
		if (input.startsWith("INSERT")) {
			command = new InsertCmd();
		} else if (input.startsWith("DELETE")) {
			command = new DeleteCmd();
		} else if (input.startsWith("SEARCH")) {
			command = new SearchCmd();
		} else if (input.startsWith("PRINT")) {
			command = new PrintCmd();
		} else {
			throw new CommandNotFoundException(input);
		}
		command.parse(input);
		return command;
	}

}