package org.jabref.logic.formatter.bibtexfields;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jabref.logic.l10n.Localization;
import org.jabref.model.cleanup.Formatter;

public class RegexFormatter implements Formatter {

    private static String[] regex;

    @Override
    public String getName() {
        return Localization.lang("Regex");
    }

    @Override
    public String getKey() {
        return "regex";
    }

    @Override
    public String format(String input) {
        if (regex == null) {
            return input;
        }
        String regex = "(\\{.*?})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        String replacement = Character.toString((char)2580);
        List<String> replaced = new ArrayList<>();
        while (matcher.find()) {
            replaced.add(matcher.group(1));
        }
        input = input.replaceAll(regex, replacement);

        input = input.replaceAll(RegexFormatter.regex[0], RegexFormatter.regex[1]);

        for (String r : replaced) {
            input = input.replaceFirst(replacement, r);
        }
        return input;
    }

    @Override
    public String getDescription() {
        return Localization.lang("Add a regular expression for the key pattern.");
    }

    @Override
    public String getExampleInput() {
        return "Please replace the spaces";
    }

    public static void setRegex(String rex) {
        // formatting is like ("exp1","exp2"), we want to remove (" and ")
        rex = rex.substring(2, rex.length() - 2);
        String[] parts = rex.split("\",\"");
        regex = parts;
    }

}
