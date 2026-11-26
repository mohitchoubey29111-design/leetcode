
class Solution {
    public boolean canAliceWin(int n) {

        int stones = 10;
        int count = 0;

        while (n >= stones) {
            n = n - stones;
            stones--;
            count++;
        }

        return count % 2 == 1;
    }
}
