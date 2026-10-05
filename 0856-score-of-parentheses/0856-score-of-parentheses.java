class Solution {
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(0);
            } else {
                int inner = stack.pop();
                int outer = stack.pop();

                if (inner == 0) {
                    outer += 1;
                } else {
                    outer += 2 * inner;
                }

                stack.push(outer);
            }
        }

        return stack.pop();
    }
}