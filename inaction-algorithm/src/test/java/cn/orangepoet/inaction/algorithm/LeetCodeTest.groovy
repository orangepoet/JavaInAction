package cn.orangepoet.inaction.algorithm

import spock.lang.Specification


/**
 * 使用Spock示例
 *
 * @author chengzhi* @date 2019/09/27
 */
class LeetCodeTest extends Specification {
    def solution = new LeetCode()

    def "最大连续子序列"() {
        expect:
        solution.getMaxSubSet(sequence) == max

        where:
        sequence                       | max
        [-1] as int[]                  | -1
        [-1, 1] as int[]               | 1
        [2, -1] as int[]               | 2
        [-2, 3] as int[]               | 3
        [-2, 1] as int[]               | 1
        [1, 2, 3] as int[]             | 6
        [-1, 2, -2, 1, 1, -3] as int[] | 2
    }

    def "斐波那契数列 (非递归解法)"() {
        expect:
        solution.fibonacci0(n) == arr

        where:
        n | arr
        1 | 1
        2 | 1
        3 | 2
        4 | 3

    }

    def "斐波那契数列(递归解法)"() {
        expect:
        solution.fibonacci1(n) == arr

        where:
        n | arr
        1 | 1
        2 | 1
        3 | 2
        4 | 3

    }

    def "硬币找零"() {

        expect:
        solution.makeChange(coins, n, new HashMap<>()) == cnt
        solution.makeChange2(coins, n) == cnt

        where:
        coins              | n    | cnt
        [1, 7, 9] as int[] | 3000 | 334
        [1, 7, 9] as int[] | 1    | 1
        [1, 7, 9] as int[] | 2    | 2
        [1, 7, 9] as int[] | 6    | 6
        [1, 7, 9] as int[] | 7    | 1
        [1, 7, 9] as int[] | 8    | 2
        [1, 7, 9] as int[] | 9    | 1
        [1, 7, 9] as int[] | 10   | 2
        [1, 7, 9] as int[] | 0    | 0
        [1, 7, 9] as int[] | 16   | 2
        [1, 7, 9] as int[] | 17   | 3
    }

    def '查找目标数的范围'() {


        expect:
        solution.searchRange(nums, target) == range

        where:
        nums                         | target | range
        [5, 7, 7, 8, 8, 10] as int[] | 6      | [-1, -1] as int[]
        [5, 7, 7, 8, 8, 10] as int[] | 7      | [1, 2] as int[]
        [5, 7, 7, 8, 8, 10] as int[] | 8      | [3, 4] as int[]
    }

    def '测试目标数解集'() {


        expect:
        solution.combinationSum(candidates, target) == ans
        solution.combinationSumOfDfs(candidates, target) == ans1

        where:
        candidates            | target | ans              | ans1
        [2, 3, 6, 7] as int[] | 7      | [[2, 2, 3], [7]] | [[7], [2, 2, 3]]
    }

    def '三数之和'() {

        expect:
        solution.threeSum(nums) == result

        where:
        nums                                                              | result
        [-1, 0, 1, 2, -1, -4] as int[]                                    | [[-1, -1, 2], [-1, 0, 1]] as List
        [-4, -2, 1, -5, -4, -4, 4, -2, 0, 4, 0, -2, 3, 1, -5, 0] as int[] | [[-5, 1, 4], [-4, 0, 4], [-4, 1, 3], [-2, -2, 4], [-2, 1, 1], [0, 0, 0]] as List
    }

    def '树的路径总和'() {
        given:
        LeetCode.TreeNode n2 = new LeetCode.TreeNode(2)
        LeetCode.TreeNode n3 = new LeetCode.TreeNode(3)
        LeetCode.TreeNode n1 = new LeetCode.TreeNode(1, n2, n3)

        when:
        def ans = solution.pathSum(n1, 4)

        then:
        ans == 1
    }

    def '数组单调测试'() {


        expect:
        solution.isMonotonic(arr) == ans

        where:
        arr                   | ans
        [1, 3, 2] as int[]    | false
        [1, 2, 4, 5] as int[] | true
    }

    def '数独是否合理'() {

        when:

        def board = [
                ["5", "3", ".", ".", "7", ".", ".", ".", "."],
                ["6", ".", ".", "1", "9", "5", ".", ".", "."],
                [".", "9", "8", ".", ".", ".", ".", "6", "."],
                ["8", ".", ".", ".", "6", ".", ".", ".", "3"],
                ["4", ".", ".", "8", ".", "3", ".", ".", "1"],
                ["7", ".", ".", ".", "2", ".", ".", ".", "6"],
                [".", "6", ".", ".", ".", ".", "2", "8", "."],
                [".", ".", ".", "4", "1", "9", ".", ".", "5"],
                [".", ".", ".", ".", "8", ".", ".", "7", "9"]] as char[][]
        def isValid = solution.isValidSudoku(board)
        then:
        isValid

        when:
        board = [[".", ".", ".", ".", "5", ".", ".", "1", "."],
                 [".", "4", ".", "3", ".", ".", ".", ".", "."],
                 [".", ".", ".", ".", ".", "3", ".", ".", "1"],
                 ["8", ".", ".", ".", ".", ".", ".", "2", "."],
                 [".", ".", "2", ".", "7", ".", ".", ".", "."],
                 [".", "1", "5", ".", ".", ".", ".", ".", "."],
                 [".", ".", ".", ".", ".", "2", ".", ".", "."],
                 [".", "2", ".", "9", ".", ".", ".", ".", "."],
                 [".", ".", "4", ".", ".", ".", ".", ".", "."]] as char[][]
        isValid = solution.isValidSudoku(board)
        then:
        !isValid
    }

    def '字母移位'() {
        ;

        expect:
        solution.shiftingLetters(S, arr).equals(ans)

        where:
        S                      | arr                                                                                                                                                                                                                                  | ans
        "abc"                  | [3, 5, 9] as int[]                                                                                                                                                                                                                   | "rpl"
        "mkgfzkkuxownxvfvxasy" | [505870226, 437526072, 266740649, 224336793, 532917782, 311122363, 567754492, 595798950, 81520022, 684110326, 137742843, 275267355, 856903962, 148291585, 919054234, 467541837, 622939912, 116899933, 983296461, 536563513] as int[] | "wqqwlcjnkphhsyvrkdod"
    }

    def "分组最大数"() {


        expect:
        solution.maxSumAfterPartitioning(A, K) == ans

        where:
        A                                          | K | ans
        [1, 15, 7, 9, 2, 5, 10] as int[]           | 3 | 84
        [1, 4, 1, 5, 7, 3, 6, 1, 9, 9, 3] as int[] | 4 | 83
    }

    def "模式匹配"() {


        expect:
        solution.wordPattern(pattern, str) == ans

        where:
        pattern | str               | ans
        "abba"  | "dog cat cat dog" | true
        "abba"  | "dog dog dog dog" | false
    }

    def "优美子数组"() {


        expect:
        solution.subarraySum(nums, k) == ans

        where:
        nums                     | k | ans
        [100, 99, 2, 1] as int[] | 3 | 1
        [1, 1, 2, 1, 1] as int[] | 3 | 2
        [2, 4, 6] as int[]       | 1 | 0
        [1, 100, 1] as int[]     | 2 | 1
    }

    def '和大于等于target的最短子数组'() {
        expect:
        solution.minSubArrayLen(k, nums) == ans
        solution.minSubArrayLen2(k, nums) == ans
        where:
        nums                        | k  | ans
        [2, 3, 1, 2, 4, 3] as int[] | 7  | 2
        [1, 2, 3, 4, 5] as int[]    | 15 | 5
    }

    def '大小为K且平均值大于等于阈值的子数组数目'() {


        expect:
        solution.numOfSubarrays(arr, k, threshold) == nums

        where:
        nums | arr                                           | k | threshold
        5    | [1, 1, 1, 1, 1] as int[]                      | 1 | 0
        3    | [2, 2, 2, 2, 5, 5, 5, 8] as int[]             | 3 | 4
        6    | [11, 13, 17, 23, 29, 31, 7, 5, 2, 3] as int[] | 3 | 6
        1    | [7, 7, 7, 7, 7, 7, 7] as int[]                | 7 | 7
        6    | [11, 13, 17, 23, 29, 31, 7, 5, 2, 3] as int[] | 3 | 5
        1    | [4, 4, 4, 4] as int[]                         | 4 | 1
    }

    def '两数相加'() {
        given:
        def p1 = new LeetCode.ListNode(2)
        def p2 = new LeetCode.ListNode(4)
        def p3 = new LeetCode.ListNode(3)
        p1.next = p2
        p2.next = p3
        def r1 = new LeetCode.ListNode(5)
        def r2 = new LeetCode.ListNode(6)
        def r3 = new LeetCode.ListNode(4)
        r1.next = r2
        r2.next = r3
        def t1 = new LeetCode.ListNode(7)
        def t2 = new LeetCode.ListNode(0)
        def t3 = new LeetCode.ListNode(8)
        t1.next = t2
        t2.next = t3
        def solution = new LeetCode()


        expect:
        solution.addTwoNumbers(p1, r1) == t1
    }

    def '最大无重复子序列'() {


        expect:
        solution.lengthOfLongestSubstring(s) == max

        where:
        s          | max
        "abcabcbb" | 3
        "bbbbbb"   | 1
        "pwwkew"   | 3
        " "        | 1
        "au"       | 2
    }

    def '目标和'() {


        expect:
//        solution.findTargetSumWays(arr, S) == num
        solution.findTargetSumWays1(arr, S) == num

        where:
        arr                      | S | num
        [1, 1, 1, 1, 1] as int[] | 3 | 5
//        [1, 0] as int[] | 1 | 2
    }

    def '搜索词汇'() {
        expect:
        solution.suggestedProducts(products, searchWord) == ans

        where:
        products                                                           | searchWord | ans
        ["mobile", "mouse", "moneypot", "monitor", "mousepad"] as String[] | "mouse"    | [
                ["mobile", "moneypot", "monitor"],
                ["mobile", "moneypot", "monitor"],
                ["mouse", "mousepad"],
                ["mouse", "mousepad"],
                ["mouse", "mousepad"] as List<List<String>>
        ]
    }

    def '验证分数到小数'() {
        expect:
        solution.fractionToDecimal(numerator, denominator) == ans

        where:
        numerator   | denominator | ans
        1           | 2           | "0.5"
        2           | 1           | "2"
        2           | 3           | "0.(6)"
        -1          | -2147483648 | "0.0000000004656612873077392578125"
        -2147483648 | 1           | "-2147483648"
        7           | -12         | "-0.58(3)"
    }

    def '最长重复子字符串'() {
        expect:
        solution.longestDupSubstring(S) == longestDup

        where:
        S | longestDup
        "banana" | "ana"
        "abcd" | ""
        "moplvidmaagmsiyyrkchbyhivlqwqsjcgtumqscmxrxrvwsnjjvygrelcbjgbpounhuyealllginkitfaiviraqcycjmskrozcdqylbuejrgfnquercvghppljmojfvylcxakyjxnampmakyjbqgwbyokaybcuklkaqzawageypfqhhasetugatdaxpvtevrigynxbqodiyioapgxqkndujeranxgebnpgsukybyowbxhgpkwjfdywfkpufcxzzqiuglkakibbkobonunnzwbjktykebfcbobxdflnyzngheatpcvnhdwkkhnlwnjdnrmjaevqopvinnzgacjkbhvsdsvuuwwhwesgtdzuctshytyfugdqswvxisyxcxoihfgzxnidnfadphwumtgdfmhjkaryjxvfquucltmuoosamjwqqzeleaiplwcbbxjxxvgsnonoivbnmiwbnijkzgoenohqncjqnckxbhpvreasdyvffrolobxzrmrbvwkpdbfvbwwyibydhndmpvqyfmqjwosclwxhgxmwjiksjvsnwupraojuatksjfqkvvfroqxsraskbdbgtppjrnzpfzabmcczlwynwomebvrihxugvjmtrkzdwuafozjcfqacenabmmxzcueyqwvbtslhjeiopgbrbvfbnpmvlnyexopoahgmwplwxnxqzhucdieyvbgtkfmdeocamzenecqlbhqmdfrvpsqyxvkkyfrbyolzvcpcbkdprttijkzcrgciidavsmrczbollxbkytqjwbiupvsorvkorfriajdtsowenhpmdtvamkoqacwwlkqfdzorjtepwlemunyrghwlvjgaxbzawmikfhtaniwviqiaeinbsqidetfsdbgsydkxgwoqyztaqmyeefaihmgrbxzyheoegawthcsyyrpyvnhysynoaikwtvmwathsomddhltxpeuxettpbeftmmyrqclnzwljlpxazrzzdosemwmthcvgwtxtinffopqxbufjwsvhqamxpydcnpekqhsovvqugqhbgweaiheeicmkdtxltkalexbeftuxvwnxmqqjeyourvbdfikqnzdipmmmiltjapovlhkpunxljeutwhenrxyfeufmzipqvergdkwptkilwzdxlydxbjoxjzxwcfmznfqgoaemrrxuwpfkftwejubxkgjlizljoynvidqwxnvhngqakmmehtvykbjwrrrjvwnrteeoxmtygiiygynedvfzwkvmffghuduspyyrnftyvsvjstfohwwyxhmlfmwguxxzgwdzwlnnltpjvnzswhmbzgdwzhvbgkiddhirgljbflgvyksxgnsvztcywpvutqryzdeerlildbzmtsgnebvsjetdnfgikrbsktbrdamfccvcptfaaklmcaqmglneebpdxkvcwwpndrjqnpqgbgihsfeotgggkdbvcdwfjanvafvxsvvhzyncwlmqqsmledzfnxxfyvcmhtjreykqlrfiqlsqzraqgtmocijejneeezqxbtomkwugapwesrinfiaxwxradnuvbyssqkznwwpsbgatlsxfhpcidfgzrc" | "akyj"
    }

    def "删列造序"() {
        expect:
        solution.minDeletionSize(A) == N

        where:
        A | N
        ["ca", "bb", "ac"] as String[] | 1
        ["xc", "yb", "za"] as String[] | 0
        ["zyx", "wvu", "tsr"] as String[] | 3
        ["xga", "xfb", "yfa"] as String[] | 1
        ["vqyoysnpxbjiitandmvugsqpfmggkv", "uzdfeclxepjzfecmsxrqqkcomtrnvm", "yvhwrsapfffwehdmvqwxstgeexfeua", "awjymwysjpazpgdeqtvdiebfwuapin", "odhihlbvsnximvdwqntdeqptigiyik", "qtrfpwiilxskcieilfvarqbnpdxham", "whvrqkdwuzbcaagsmlfvfbeataygud", "kncwqrmejjmhtfhppsrdmzqperwlww", "hgphuwaumjjibzhvvejpniopjxizie", "bxvccswqevnudqicgrvjecfqpeppob", "nnmvncnpbksdjyjjelsjizliicxpgz", "oifmofrkbgpxlhkcbibwaoiygmqqio", "ekdfyvsumngcfjlydgpmhgjjyfovfi", "fyqryrpkvauhkylmfzhuasjxpqrohx", "rdvjglvpavzdmtobnpjfwdwivhrpsj", "zahrkuiejecndfprwysunznialtfok", "jlrgpfdptlolmlqoophhciiqjnxdkh", "bhbsdukebqvvemrcunboipprcbrfcl", "kreyeyvsmufolvsrzdyeqpuqlieeij", "vgosaxsfnbsndstjohgyknyionhoga", "igmnlibpadandgtugbgxpxwlqbknmv", "mjdbxxprxbjegvtthlrenhfpdlamww", "qfssehellhvqyntozbrizixptppfpr", "utghfndlcturahtcvmqrjyxqfhrsxt", "xvminqhybbiadetniqfwubqxmjokjv", "udfckncwvhcrmxtbkqbqqptymlqnss", "gwwcmterazvyakuvwtyhthfiohlywq", "mpieryurvarojfvhfbbcwepdeoedri", "lpaonsugmlzuweyvrrlgwwdjsgwmoh", "kexyawgkinwvjvzwvofqlthmhaicgs"] as String[] | 23
    }

    def '测试数组'() {
        expect:
        solution.splitArraySameAverage(A) == ans


        where:
        A                                        | ans
        [1, 2, 3, 4, 5, 6, 7, 8] as int[]        | true
        [17, 5, 5, 1, 14, 10, 13, 1, 6] as int[] | true
    }

    def '四因数'() {
        expect:
        solution.sumFourDivisors(nums) == ans


        where:
        nums                | ans
        [21, 4, 7] as int[] | 32
    }

    def '合并区间'() {
        expect:
        solution.merge2(intervals) == ans

        where:
        intervals                                      | ans
        [[1, 3], [2, 6], [8, 10], [15, 18]] as int[][] | [[1, 6], [8, 10], [15, 18]] as int[][]
        [[1, 4], [0, 1]] as int[][]                    | [[0, 4]] as int[][]
    }

    def '生成螺旋'() {
        expect:
        solution.generateMatrix(n) == matrix

        where:
        n | matrix
        3 | [[1, 2, 3], [8, 9, 4], [7, 6, 5]] as int[][]
    }

    def '生成所有括号的组合'() {
        expect:
        solution.generateParenthesis(n) == brack

        where:
        n | brack
        3 | ["((()))", "(()())", "(())()", "()(())", "()()()"] as ArrayList<String>

    }

    def '组合和2'() {
        expect:
        solution.combinationSum2(candidates, target) == ans

        where:
        candidates                      | target | ans
        [10, 1, 2, 7, 6, 1, 5] as int[] | 8      | [
                [1, 7],
                [1, 2, 5],
                [2, 6],
                [1, 1, 6]
        ] as List<List<Integer>>
    }

    def '缺失的第一个正数'() {
        expect:
        solution.firstMissingPositive(nums) == ans
        solution.firstMissingPositive2(nums) == ans

        where:
        nums                       | ans
        [1, 2, 0] as int[]         | 3
        [3, 4, -1, 1] as int[]     | 2
        [7, 8, 9, 11, 12] as int[] | 1
        [1, 1] as int[]            | 2
    }

    def '外观数列'() {
        expect:
        solution.countAndSay(n) == str
        where:
        n | str
        4 | "1211"
    }

    def '判断包含位置'() {
        expect:
        solution.strStr(haystack, needle) == n

        where:
        haystack      | needle        | n
        "hello"       | "ll"          | 2
        "aaaaa"       | "bba"         | -1
        "a"           | "a"           | 0
        "mississippi" | "mississippi" | 0
        "mississippi" | "issipi"      | -1
    }

    def '判断回文字'() {
        expect:
        solution.longestPalindrome(s) == s1

        where:
        s             | s1
        "babad"       | "bab"
        "aacabdkacaa" | "aca"
    }

    def '删除链表节点'() {
        expect:
        solution.removeNthFromEnd(head, n) == res

        where:
        res | head | n
    }

    def '测试编码组合'() {
        expect:
        solution.numDecodings(s) == ret

        where:
        s      | ret
        "06"   | 0
        "12"   | 2
        "226"  | 3
        "2101" | 1
        "1123" | 5
    }

    def '罗马数字'() {
        expect:
        ret == solution.intToRoman(num)

        where:
        ret       | num
        "MCMXCIV" | 1994
    }

    def '螺旋矩阵'() {
        expect:
        ans == solution.spiralOrder(matrix)
        ans == solution.spiralOrder2(matrix)

        where:
        matrix                                                   | ans
        [[1, 2, 3], [4, 5, 6], [7, 8, 9]] as int[][]             | [1, 2, 3, 6, 9, 8, 7, 4, 5]
        [[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]] as int[][] | [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]
    }

    def '搜索旋转排序数组 II'() {
        expect:
        solution.search(nums, target) == ans

        where:
        nums                                                               | target | ans
        [2, 5, 6, 0, 0, 1, 2] as int[]                                     | 0      | true
        [2, 5, 6, 0, 0, 1, 2] as int[]                                     | 3      | false
        [1, 0, 1, 1, 1] as int[]                                           | 0      | true
        [1] as int[]                                                       | 1      | true
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1] as int[] | 2      | true
    }

    def '下一个排列'() {
        given:
        int[] nums = [1, 3, 2]
        when:
        solution.nextPermutation(nums)
        then:
        nums == [2, 1, 3] as int[]
    }

    def '跳跃游戏'() {
        expect:
        solution.canJump(nums) == canJump
        where:
        nums                     | canJump
        [2, 3, 1, 1, 4] as int[] | true
        [3, 2, 1, 0, 4] as int[] | false
        [1, 2, 3] as int[]       | true
        [3, 2, 1, 0, 4] as int[] | false
        [2, 5, 0, 0] as int[]    | true
    }

    def '等式逻辑判断'() {
        expect:
        solution.equationsPossible(equations) == result

        where:
        equations                                                                                            | result
        ["g==c", "f!=e", "e==b", "j==b", "g!=a", "e==c", "b!=f", "d!=a", "j==g", "f!=i", "a==e"] as String[] | false
        ["a!=a"] as String[]                                                                                 | false
        ["a==b", "b==c", "a==c"] as String[]                                                                 | true
        ["a==b", "b==c", "a==c", "b!=c"] as String[]                                                         | false
    }

    def '判断字符串子数组满足K长最大长度'() {
        expect:
        solution.longestSubstring(s, k) == n

        where:
        s        | k | n
        "aaabb"  | 3 | 3
        "ababbc" | 2 | 5
    }

    def "乘积小于 K 的子数组"() {
        expect:
        solution.numSubarrayProductLessThanK(nums, k) == ans

        where:
        nums                   | k   | ans
        [10, 5, 2, 6] as int[] | 100 | 8
    }

    def "质因数分解"() {
        expect:
        solution.resolvePrime(num) == ans

        where:
        num        | ans
        10         | [2, 5] as List
        20         | [2, 2, 5] as List
        2083497593 | [67, 271, 114749] as List
    }

    def '路径种类'() {
        expect:
        solution.uniquePaths(m, n) == ans

        where:
        m | n | ans
        3 | 2 | 3
    }

    def '最小路径'() {
        expect:
        solution.minPathSum(grid) == ans

        where:
        grid                                         | ans
        [[1, 3, 1], [1, 5, 1], [4, 2, 1]] as int[][] | 7
    }

    def '合并排序'() {
        given:
        def arr = [3, 5, 2, 1, 4, 7, 6] as int[]
        when:
        solution.mergeSort(arr)

        then:
        for (i in 1..<arr.length) {
            assert arr[i] >= arr[i - 1]
        }
    }

    def '快排'() {
        given:
        def arr = [3, 5, 2, 1, 4, 7, 6] as int[]
        when:
        solution.quickSort(arr)

        then:
        for (i in 1..<arr.length) {
            assert arr[i] >= arr[i - 1]
        }
    }

    def '单词拆分'() {
        expect:
        solution.wordBreak(s, wordDict) == ans

        where:
        s          | wordDict         | ans
        "leetcode" | ["leet", "code"] | true
    }

    def '测试LRU'() {
        given:
        LRU2 lru = new LRU2(2);

        when:
        lru.put(2, 1)
        lru.put(1, 1)
        lru.put(2, 3)
        lru.put(4, 1)
        def val = lru.get(1)
        def val2 = lru.get(2)

        then:
        val == -1
        val2 == 3
    }

    def '测试LFU'() {
        given:
        LFU lfu = new LFU(2);

        when:
        lfu.put(1, 1)
        lfu.put(2, 2)
        lfu.get(1)
        lfu.get(1)

        lfu.put(3, 3)
        lfu.get(3)

        def val = lfu.get(1)
        def val2 = lfu.get(2)
        def val3 = lfu.get(3)

        then:
        val == 1
        val2 == -1
        val3 == 3
    }

    def '岛屿数量'() {
        expect:
        solution.numIslands(grid) == count

        where:
        grid                                    | count
        [["1", "1", "0", "0", "0"],
         ["1", "1", "0", "0", "0"],
         ["0", "0", "1", "0", "0"],
         ["0", "0", "0", "1", "1"]] as char[][] | 3
    }

    def "股票买卖最佳时间（带冻结）"() {
        expect:
        solution.maxProfit(prices) == ans
        where:
        prices                   | ans
        [1, 2, 3, 0, 2] as int[] | 3
    }

    def "二叉树的最近公共祖先"() {
        given:
        def three = new LeetCode.TreeNode(3)

        def five = new LeetCode.TreeNode(5)
        def one = new LeetCode.TreeNode(1)
        three.left = five
        three.right = one

        def six = new LeetCode.TreeNode(6)
        def two = new LeetCode.TreeNode(2)
        five.left = six
        five.right = two

        def zero = new LeetCode.TreeNode(0)
        def eight = new LeetCode.TreeNode(8)
        one.left = zero
        one.right = eight

        def seven = new LeetCode.TreeNode(7)
        def four = new LeetCode.TreeNode(4)
        two.left = seven
        two.right = four


        when:
        def parent = solution.lowestCommonAncestor(three, five, four)

        then:
        parent == five
    }

    def "分割等和子集"() {
        expect:
        solution.canPartition(nums) == ans
        where:
        nums                     | ans
        [3, 3, 3, 4, 5] as int[] | true
        [1, 1, 1, 1] as int[]    | true
    }

    def '接雨点'() {
        expect:
        solution.trap(heights) == ans
        where:
        heights                     | ans
        [4, 2, 0, 3, 2, 5] as int[] | 9
        [2, 0, 2] as int[]          | 2
        [5, 4, 1, 2] as int[]       | 1
    }

    def '柱状最大矩形'() {
        expect:
        solution.largestRectangleArea(heights) == ans
        where:
        heights                     | ans
        [2, 1, 5, 6, 2, 3] as int[] | 10
        [2, 1, 2] as int[]          | 3
        [0] as int[]                | 0
    }

    def '数字组成的最大数'() {
        expect:
        solution.largestNum(arr, high) == ans
        where:
        arr                           | high | ans
        ["1", "3", "5", "7"] as int[] | 102  | 77
        ["1", "3", "5", "7"] as int[] | 145  | 137
        ["1", "3", "5", "7"] as int[] | 155  | 153
        ["1", "3", "5", "7"] as int[] | 165  | 157
    }

    def '测试区间合并'() {
        given:
        def rl = new RangeList()
        when:
        rl.add(1, 5)
        rl.print()

        rl.add(10, 20)
        rl.print()

        rl.add(20, 20)
        rl.print()

        rl.add(20, 21)
        rl.print()

        rl.add(2, 4)
        rl.print()

        rl.add(3, 8)
        rl.print()

        rl.remove(10, 10)
        rl.print()

        rl.remove(10, 11)
        rl.print()

        rl.remove(15, 17)
        rl.print()

        rl.remove(3, 19)
        rl.print()

        then:
        true
    }

    def "重复数字"() {
        expect:
        solution.findDuplicates(nums) == ans
        where:
        nums | ans
//        [4, 3, 2, 7, 8, 2, 3, 1] as int[] | [3, 2]
        [454, 1763, 1121, 608, 1332, 481, 548, 873, 6, 766, 961, 39, 1712, 1015, 1987, 346, 1604, 1706, 1261, 1489, 658, 1553, 406, 1609, 1377, 1496, 1997, 354, 811, 220, 1742, 544, 1682, 98, 605, 1291, 400, 1570, 1743, 291, 1853, 398, 105, 1621, 298, 786, 832, 722, 186, 42, 1535, 1858, 608, 1050, 422, 1460, 178, 158, 527, 560, 1309, 575, 77, 1020, 83, 1887, 1853, 23, 736, 657, 1998, 1893, 1259, 1000, 1149, 856, 743, 362, 25, 1015, 120, 1632, 1734, 30, 1666, 1101, 797, 161, 1453, 152, 208, 1710, 390, 984, 74, 814, 1775, 1772, 997, 104, 471, 804, 422, 1779, 948, 1103, 1459, 1253, 28, 738, 1876, 28, 194, 1521, 1655, 328, 238, 1629, 189, 1588, 518, 1050, 387, 1439, 344, 972, 1787, 267, 1034, 366, 872, 881, 184, 1710, 409, 1583, 1772, 392, 1452, 192, 1564, 1709, 292, 1094, 1885, 176, 1557, 646, 774, 1518, 1756, 208, 383, 631, 12, 553, 1832, 253, 1555, 1703, 1830, 1992, 1323, 1208, 792, 1763, 1388, 799, 136, 1751, 142, 781, 332, 641, 973, 1976, 1820, 419, 757, 778, 1247, 756, 509, 1401, 782, 916, 1600, 510, 1386, 1937, 550, 660, 217, 303, 833, 47, 1947, 1303, 83, 487, 1483, 1743, 621, 959, 561, 1026, 1410, 1654, 1652, 651, 235, 346, 748, 1747, 1715, 420, 630, 501, 1010, 19, 849, 666, 408, 1752, 662, 535, 1087, 1932, 884, 1167, 1970, 410, 137, 789, 1356, 1324, 733, 1954, 1129, 181, 551, 943, 250, 600, 691, 1536, 628, 1220, 460, 189, 55, 77, 931, 222, 1868, 1799, 1345, 1507, 632, 273, 1897, 909, 382, 1548, 374, 1885, 976, 1847, 436, 902, 1957, 1192, 171, 705, 1734, 1929, 134, 141, 771, 637, 1362, 1393, 1395, 1998, 690, 1008, 856, 756, 351, 1448, 401, 1411, 1255, 1673, 1218, 215, 74, 1788, 1315, 652, 131, 890, 1124, 468, 518, 820, 545, 1994, 1568, 1092, 324, 718, 533, 1897, 491, 899, 885, 399, 739, 819, 617, 1869, 38, 1970, 224, 984, 751, 1375, 1537, 1621, 1558, 1879, 1116, 1468, 1564, 340, 1553, 1256, 52, 1747, 1164, 731, 1676, 1491, 1740, 1358, 1831, 169, 1407, 213, 505, 641, 1164, 1665, 654, 719, 780, 1008, 1063, 179, 709, 1455, 1906, 1002, 1525, 911, 1013, 193, 1253, 1126, 773, 469, 299, 1338, 550, 1323, 149, 376, 474, 1299, 254, 249, 525, 259, 337, 175, 1782, 1327, 643, 911, 1813, 1260, 1613, 92, 1529, 578, 711, 1890, 254, 252, 1984, 203, 307, 1248, 778, 1937, 794, 181, 1523, 177, 1672, 38, 1342, 1512, 1196, 1282, 1103, 204, 1830, 90, 1883, 39, 563, 809, 1496, 23, 727, 463, 1940, 396, 983, 514, 1119, 1275, 934, 1235, 1632, 1195, 1878, 968, 1963, 1161, 697, 961, 1583, 770, 236, 1973, 1979, 862, 1437, 1672, 541, 1462, 923, 874, 1480, 1926, 1129, 1177, 402, 622, 1926, 1320, 50, 332, 1013, 1182, 1233, 1178, 62, 502, 867, 1344, 1824, 1691, 1679, 821, 764, 1053, 843, 817, 1200, 122, 1415, 1770, 85, 859, 1439, 890, 633, 643, 1117, 403, 1927, 1717, 419, 875, 1035, 833, 722, 1854, 1597, 1457, 476, 240, 307, 121, 1001, 633, 277, 610, 448, 282, 832, 1977, 401, 1717, 354, 1618, 1276, 1805, 244, 1826, 124, 102, 1204, 107, 1203, 619, 350, 1349, 1522, 558, 935, 1143, 1957, 1945, 1110, 1156, 458, 372, 48, 274, 1516, 752, 1630, 279, 1184, 800, 1548, 1067, 439, 1152, 723, 750, 892, 453, 383, 666, 1556, 4, 117, 52, 1294, 1407, 1920, 1206, 1576, 196, 882, 854, 276, 863, 1886, 1481, 1016, 1009, 1740, 322, 333, 1932, 1925, 1542, 469, 282, 625, 1611, 1210, 1550, 139, 719, 1231, 130, 1326, 1348, 214, 1631, 1839, 265, 686, 315, 1398, 900, 1237, 845, 1657, 1083, 1026, 588, 1879, 642, 744, 1813, 287, 1550, 1084, 186, 1160, 678, 1721, 1067, 653, 1925, 275, 1154, 1027, 1986, 539, 1351, 1686, 698, 1001, 433, 25, 399, 360, 1152, 1180, 1287, 689, 466, 1436, 155, 1123, 939, 747, 1565, 1702, 1006, 731, 659, 1482, 1353, 636, 867, 1028, 1111, 1750, 1356, 1795, 786, 1203, 416, 1921, 866, 90, 1918, 645, 1047, 374, 1408, 1285, 79, 1269, 718, 1816, 1988, 316, 316, 185, 1493, 648, 551, 1240, 239, 975, 1400, 587, 72, 1029, 258, 626, 1880, 1146, 142, 1064, 261, 321, 1392, 1029, 1481, 821, 672, 1138, 1395, 1234, 1974, 35, 958, 1062, 1216, 627, 1691, 1148, 1803, 1041, 1666, 1181, 1178, 923, 1421, 680, 322, 586, 1351, 1243, 133, 1417, 795, 1982, 700, 1379, 378, 1753, 1952, 154, 1032, 1429, 261, 916, 964, 1568, 768, 82, 1658, 594, 231, 1575, 1261, 849, 1917, 1057, 1945, 259, 1686, 864, 1677, 1911, 133, 692, 1394, 1955, 992, 1828, 1169, 141, 1855, 1432, 1189, 949, 1940, 1300, 331, 1809, 1572, 98, 1494, 363, 1519, 193, 1566, 1793, 1497, 1200, 546, 1084, 1346, 1807, 1962, 1807, 1478, 341, 1142, 1406, 620, 1846, 943, 389, 634, 1397, 930, 1243, 1919, 1491, 1902, 1971, 1245, 602, 81, 775, 1101, 739, 451, 1947, 1371, 450, 1552, 271, 235, 528, 1038, 798, 1544, 1797, 963, 1202, 194, 1680, 715, 924, 912, 309, 1889, 672, 1145, 1974, 170, 1873, 1357, 1690, 1210, 1711, 1369, 357, 1065, 953, 439, 1360, 893, 315, 1293, 1033, 1099, 180, 1540, 674, 376, 897, 89, 1716, 1678, 965, 572, 91, 928, 1000, 1027, 1793, 682, 1140, 267, 1675, 537, 1641, 367, 381, 93, 318, 432, 1108, 1876, 1513, 692, 1430, 962, 683, 244, 996, 1345, 493, 1010, 500, 1344, 230, 1183, 1374, 1958, 1259, 1641, 1422, 293, 303, 917, 1147, 48, 1921, 1391, 200, 663, 402, 190, 1268, 256, 85, 1283, 596, 640, 1038, 288, 1731, 1726, 1661, 1858, 324, 614, 131, 1450, 813, 903, 448, 343, 956, 682, 930, 1025, 1721, 456, 1482, 583, 1487, 498, 900, 1355, 1098, 618, 995, 752, 990, 1414, 170, 89, 1629, 1446, 1702, 1069, 1787, 433, 782, 507, 99, 348, 484, 1517, 387, 697, 423, 1189, 538, 1401, 793, 963, 948, 1791, 1353, 1270, 1479, 1420, 1373, 653, 1852, 825, 1219, 255, 790, 1357, 1375, 746, 1455, 884, 1773, 1601, 547, 1220, 187, 1106, 828, 1488, 1656, 1361, 639, 1425, 1951, 639, 1232, 967, 631, 1714, 424, 1504, 1835, 1554, 328, 787, 765, 1474, 1428, 632, 71, 474, 51, 280, 931, 585, 1668, 1134, 1745, 886, 198, 798, 678, 914, 1324, 822, 1422, 488, 863, 941, 788, 1587, 902, 622, 627, 566, 1052, 1541, 761, 1744, 899, 952, 1409, 823, 1610, 936, 1321, 1004, 1268, 1117, 1536, 232, 1825, 1594, 1909, 914, 950, 880, 234, 977, 947, 148, 1889, 1184, 1966, 18, 854, 689, 983, 802, 158, 1715, 1135, 185, 1393, 988, 1812, 1839, 1910, 530, 1242, 716, 1450, 1145, 135, 1964, 1293, 1462, 1643, 130, 169, 546, 1657, 1880, 1119, 1863, 1700, 880, 660, 334, 1334, 1749, 1965, 1070, 45, 491, 125, 1810, 1578, 1726, 523, 219, 125, 113, 523, 635, 1095, 359, 1281, 1809, 1138, 1685, 540, 1410, 1078, 1924, 828, 904, 266, 1102, 553, 582, 712, 1618, 1032, 1154, 1773, 1784, 341, 308, 407, 277, 1875, 1260, 1049, 1065, 1779, 360, 1037, 1278, 247, 344, 1822, 1718, 160, 243, 1318, 266, 1713, 1165, 994, 394, 1565, 334, 35, 1448, 754, 121, 1197, 1419, 1445, 1441, 707, 1664, 1680, 1803, 400, 995, 843, 762, 159, 1988, 1603, 1460, 284, 630, 1135, 1572, 702, 1290, 1868, 1701, 1390, 989, 1922, 919, 1022, 1989, 231, 1186, 665, 723, 593, 1871, 1762, 732, 221, 1761, 1473, 464, 1417, 313, 192, 1040, 1287, 394, 1768, 386, 337, 1558, 1131, 42, 18, 1545, 1847, 952, 1986, 1272, 1535, 432, 735, 1580, 1012, 403, 140, 1082, 477, 831, 273, 340, 1174, 1594, 871, 1537, 1171, 1175, 1582, 1488, 1694, 1461, 1005, 31, 1923, 1336, 1240, 1216, 522, 532, 297, 796, 318, 1303, 593, 770, 484, 1193, 1467, 62, 878, 406, 1667, 993, 71, 1485, 1294, 1939, 857, 1318, 473, 1689, 1426, 1308, 427, 837, 41, 708, 1883, 1661, 1021, 495, 1742, 234, 1346, 91, 1453, 613, 9, 1547, 1967, 582, 1727, 875, 286, 1011, 1874, 1364, 588, 1493, 1791, 671, 1814, 1891, 533, 1640, 423, 490, 946, 978, 1584, 452, 1814, 127, 1910, 256, 1851, 86, 903, 576, 803, 590, 1490, 2000, 1544, 1589, 645, 927, 1397, 1245, 1800, 219, 1799, 1634, 705, 758, 524, 1170, 888, 1616, 230, 1221, 690, 1492, 939, 1341, 862, 122, 1644, 88, 999, 1776, 187, 958, 975, 1753, 845, 713, 241, 602, 1991, 1797, 1208, 748, 1664, 1254, 769, 1497, 561, 1332, 1644, 1867, 19, 1289, 171, 563, 120, 281, 1766, 626, 1055, 430, 1074, 1980, 288, 405, 612, 738, 835, 407, 580, 46, 724, 1121, 733, 1155, 454, 869, 945, 1538, 980, 1654, 766, 569, 1589, 1162, 1699, 840, 1533, 110, 348, 1054, 236, 565, 1939, 773, 106, 1723, 747, 1456, 217, 1585, 1222, 331, 651, 139, 981, 873, 1006, 456, 1392, 1092, 1562, 451, 515, 26, 775, 1445, 45, 764, 1522, 1328, 481, 1267, 504, 198, 143, 634, 378, 1517, 196, 772, 1196, 1549, 1316, 468, 1631, 1222, 605, 1908, 150, 809, 134, 138, 1560, 1829, 506, 21, 1226, 736, 1339, 1977, 680, 1471, 695, 612, 1605, 148, 1131, 1794, 822, 1576, 1997, 1920, 1085, 1278, 222, 442, 175, 1716, 1614, 1992, 1845, 1290, 1302, 1638, 1137, 693, 372, 1993, 1045, 188, 538, 123, 1829, 814, 570, 3, 1606, 1264, 1812, 293, 1263, 992, 396, 278, 382, 1234, 248, 250, 1419, 276, 53, 418, 227, 365, 1441, 478, 549, 466, 1645, 1660, 1636, 494, 1584, 784, 793, 5, 501, 1704, 76, 869, 1276, 801, 904, 1352, 1857, 665, 1498, 1869, 1993, 1936, 1400, 109, 559, 428, 1724, 1692, 1106, 1068, 78, 1935, 945, 1524, 617, 1014, 1384, 1971, 1972, 1167, 1513, 1379, 1569, 1819, 1273, 113, 1398, 839, 618, 882, 455, 667, 1511, 950, 210, 1194, 794, 1693, 1087, 1058, 1242, 1757, 1374, 1882, 1516, 776, 1698, 1134, 458, 1600, 1640, 367, 1724, 1059, 1402, 115, 1297, 226, 732, 614, 1017, 1377, 1331, 1936, 998, 14, 504, 1816, 1999, 149, 1990, 373, 789, 1805, 195, 940, 1689, 510, 1562, 300, 1837, 308, 1402, 974, 1635, 405, 1120, 1530, 1489, 949, 1595, 1385, 107, 1609, 1762, 1709, 1806, 1794, 1282, 490, 528, 1505, 1124, 1078, 1749, 238, 317, 1115, 1484, 1606, 265, 56, 327, 1478, 1180, 790, 1976, 1071, 1996, 3, 1737, 967, 1982, 960, 1801, 999, 753, 929, 1556, 1660, 724, 440, 1096, 1388, 1923, 13, 852, 1912, 840, 1053, 197, 1605, 711, 1693, 434, 1700, 1115, 1465, 1874, 897, 373, 1133, 537, 570, 1914, 1250, 1627, 1952, 109, 1620, 549, 7, 506, 1569, 898, 1130, 58, 877, 1209, 1870, 413, 1484, 514, 844, 84, 1156, 1409, 1838, 284, 50, 1626, 531, 1941, 513, 1741, 1487, 560, 762, 1950, 450, 644, 327, 1411, 1212, 204, 1183, 1413, 1011, 21, 1752, 390, 1350, 852, 839, 1913, 1079, 1596, 1521, 290, 1126, 1301, 150, 623, 380, 1300, 1415, 968, 425, 650, 1543, 1438, 339, 564, 898, 1917, 1509, 1890, 969, 1611, 165, 1975, 1930, 313, 1125, 151, 379, 621, 754, 559, 1630, 480, 216, 1832, 1321, 7, 708, 1908, 165, 569, 1808, 1914, 628, 1884, 1319, 1181, 1096, 746, 1432, 305, 333, 846, 1912, 1060, 713, 1602, 1060, 1722, 795, 1361, 1718, 179, 1668, 1209, 1252, 1662, 323, 1826, 415, 1541, 791, 1756, 1202, 1959, 252, 1887, 1697, 954, 1169, 1461, 1852, 1833, 100, 953, 647, 1509, 513, 1457, 1649, 1559, 361, 110, 1757, 734, 579, 2, 452, 1677, 1833, 861, 1187, 270, 1924, 1854, 1995, 1373, 688, 1396, 585, 1612, 1080, 1331, 1316, 1928, 589, 177, 1922, 974, 1528, 358, 558, 127, 1469, 351, 735, 115, 824, 1090, 743, 206, 1212, 157, 1610, 1272, 1750, 1132, 167, 1817, 758, 404, 314, 457, 1151, 1933, 502, 522, 654, 483, 1784, 1031, 81, 220, 1652, 492, 861, 1697, 541, 979, 1603, 488, 1476, 1271, 807, 1503, 82, 499, 27, 417, 1266, 841, 668, 704, 1650, 673, 1399, 157, 1520, 1944, 1520, 1990, 1414, 771, 509, 652, 1077, 306, 483, 1665, 349, 1918, 926, 1313, 262, 640, 418, 623, 455, 1054, 695, 1777, 43, 145, 917, 598, 532, 240, 1317, 527, 140, 1515, 1503, 1707, 966, 1295, 1424, 1560, 876, 489, 239, 1447, 1810, 575, 1105, 1685, 629] as int[] | []
    }
}
