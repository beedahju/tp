package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.academic.Level;
import seedu.address.model.academic.LevelUtil;
import seedu.address.model.academic.Subject;

/**
 * Utility class for parsing subject and level sequences in academics commands.
 */
public class AcademicsParserUtil {

    /**
     * Parses a string containing subject and level prefixes into a list of subjects.
     * Enforces: level must immediately follow subject, each subject has at most one level.
     * Does not check for duplicate subject names (caller should handle).
     *
     * @param input The string to parse (e.g., "s/Math l/Basic s/English")
     * @return List of parsed subjects
     * @throws ParseException if parsing fails
     */
    public static List<Subject> parseSubjectLevelSequence(String input) throws ParseException {
        List<Subject> subjects = new ArrayList<>();
        Subject current = null;

        int i = 0;
        while (i < input.length()) {
            if (input.startsWith(PREFIX_SUBJECT.getPrefix(), i)) {
                int start = i + PREFIX_SUBJECT.getPrefix().length();
                int next = findNextPrefix(input, start);

                String name = input.substring(start, next).trim();

                if (!Subject.isValidSubjectName(name)) {
                    throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
                }

                current = new Subject(name, null);
                subjects.add(current);

                i = next;
            } else if (input.startsWith(PREFIX_LEVEL.getPrefix(), i)) {
                if (current == null) {
                    throw new ParseException("Level must follow a subject.");
                }

                if (current.getLevel().isPresent()) {
                    throw new ParseException("Each subject can only have one level.");
                }

                int start = i + PREFIX_LEVEL.getPrefix().length();
                int next = findNextPrefix(input, start);

                String levelStr = input.substring(start, next).trim();

                Level level;
                try {
                    level = LevelUtil.levelFromString(levelStr);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(LevelUtil.MESSAGE_CONSTRAINTS);
                }

                subjects.remove(subjects.size() - 1);
                current = new Subject(current.getName(), level);
                subjects.add(current);

                i = next;
            } else {
                throw new ParseException("Invalid format in subject-level sequence.");
            }
        }

        return subjects;
    }

    private static int findNextPrefix(String input, int start) {
        int nextSubject = input.indexOf(PREFIX_SUBJECT.getPrefix(), start);
        int nextLevel = input.indexOf(PREFIX_LEVEL.getPrefix(), start);

        if (nextSubject == -1 && nextLevel == -1) {
            return input.length();
        }
        if (nextSubject == -1) {
            return nextLevel;
        }
        if (nextLevel == -1) {
            return nextSubject;
        }

        return Math.min(nextSubject, nextLevel);
    }
}
