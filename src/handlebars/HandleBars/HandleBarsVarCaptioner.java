package handlebars.HandleBars;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HandleBarsVarCaptioner {
    final String regex = "\\{\\{" + "(.+)" + "\\}\\}";

    public Set<String> caption(String handlebarsExample) {
        ArrayList<String> matches = new ArrayList<>();
        Matcher matcher = Pattern.compile(regex).matcher(handlebarsExample);

        while (matcher.find()) {
            matches.add(matcher.group(1));
        }
        return matches
                .stream()
                .map(this::clean)
                .collect(Collectors.toSet());
    }

    private String clean(String s) {
        return s.replace("{", "")
                .replace("}", "");
    }
}
