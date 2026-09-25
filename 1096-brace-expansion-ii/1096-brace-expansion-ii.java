import java.util.*;

class Solution {
    int index = 0;

    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression);

        List<String> answer = new ArrayList<>(result);
        Collections.sort(answer);

        return answer;
    }

    private Set<String> parse(String s) {
        Set<String> result = new HashSet<>();
        result.add("");

        while (index < s.length() && s.charAt(index) != '}') {

            if (s.charAt(index) == ',') {
                index++;

                Set<String> next = parse(s);
                result.addAll(next);

            } else {
                Set<String> next = new HashSet<>();

                if (s.charAt(index) == '{') {
                    index++;

                    next = parse(s);

                    index++;
                } else {
                    next.add(String.valueOf(s.charAt(index)));
                    index++;
                }

                Set<String> combined = new HashSet<>();

                for (String a : result) {
                    for (String b : next) {
                        combined.add(a + b);
                    }
                }

                result = combined;
            }
        }

        return result;
    }
}