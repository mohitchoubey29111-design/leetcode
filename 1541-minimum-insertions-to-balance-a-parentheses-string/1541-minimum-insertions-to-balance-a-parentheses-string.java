
class Solution {
    public int minInsertions(String s) {
        int open = 0;
        int insertions = 0;
        int i = 0;

        while (i < s.length()) {
            if (s.charAt(i) == '(') {
                open++;
                i++;
            } else {
                // Check whether the next character is ')'
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    i += 2;
                } else {
                    // Insert a missing ')'
                    insertions++;
                    i++;
                }

                // If no '(' is available, insert one
                if (open == 0) {
                    insertions++;
                } else {
                    open--;
                }
            }
        }

        // Each remaining '(' needs two ')'
        return insertions + open * 2;
    }
}