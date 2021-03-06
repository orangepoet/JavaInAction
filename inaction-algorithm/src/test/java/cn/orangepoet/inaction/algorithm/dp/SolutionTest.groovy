package cn.orangepoet.inaction.algorithm.dp


import spock.lang.Specification

/**
 * 使用Spock示例
 *
 * @author chengzhi* @date 2019/09/27
 */
class SolutionTest extends Specification {
    def solution = new Solution()

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

        where:
        candidates            | target | ans
        [2, 3, 6, 7] as int[] | 7      | [[2, 2, 3], [7]]
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
        Solution.TreeNode n6 = new Solution.TreeNode(2)
        Solution.TreeNode n5 = new Solution.TreeNode(7)
        Solution.TreeNode n4 = new Solution.TreeNode(11, n5, n6)
        Solution.TreeNode n2 = new Solution.TreeNode(4, n4, null)

        Solution.TreeNode n10 = new Solution.TreeNode(1)
        Solution.TreeNode n9 = new Solution.TreeNode(5)
        Solution.TreeNode n8 = new Solution.TreeNode(4, n9, n10)
        Solution.TreeNode n7 = new Solution.TreeNode(13)
        Solution.TreeNode n3 = new Solution.TreeNode(8, n7, n8)

        Solution.TreeNode n1 = new Solution.TreeNode(5, n2, n3)

        n1.left
        def solution = new Solution()
        expect:
        solution.pathSum(root, sum) == ret

        where:
        root                           | sum | ret
        [-1, 0, 1, 2, -1, -4] as int[] | 22  | [[5, 4, 11, 2] as int[], [5, 8, 4, 5] as int[]] as int[]
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
        solution.subArrayIsK(nums, k) == ans

        where:
        nums                     | k | ans
        [1, 1, 2, 1, 1] as int[] | 3 | 2
        [2, 4, 6] as int[]       | 1 | 0
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
        def p1 = new Solution.ListNode(2)
        def p2 = new Solution.ListNode(4)
        def p3 = new Solution.ListNode(3)
        p1.next = p2
        p2.next = p3
        def r1 = new Solution.ListNode(5)
        def r2 = new Solution.ListNode(6)
        def r3 = new Solution.ListNode(4)
        r1.next = r2
        r2.next = r3
        def t1 = new Solution.ListNode(7)
        def t2 = new Solution.ListNode(0)
        def t3 = new Solution.ListNode(8)
        t1.next = t2
        t2.next = t3
        def solution = new Solution()


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
        solution.merge0(intervals) == ans

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

        where:
        nums                       | ans
        [1, 2, 0] as int[]         | 3
        [3, 4, -1, 1] as int[]     | 2
        [7, 8, 9, 11, 12] as int[] | 1
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
        haystack | needle | n
        "mississippi"  | "issip"   | 4
    }
}
