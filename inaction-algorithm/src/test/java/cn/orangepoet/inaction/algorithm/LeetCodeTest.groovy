package cn.orangepoet.inaction.algorithm

import cn.orangepoet.inaction.algorithm.model.ListNode
import cn.orangepoet.inaction.algorithm.model.TreeNode
import spock.lang.Specification

import java.rmi.server.ExportException


/**
 * 使用Spock示例
 *
 * @author chengzhi* @date 2019/09/27
 */
class LeetCodeTest extends Specification {
    def solution = new LeetCode()
    def solution2 = new LeetCode2()

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
        TreeNode n2 = new TreeNode(2)
        TreeNode n3 = new TreeNode(3)
        TreeNode n1 = new TreeNode(1, n2, n3)

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
        def p1 = new ListNode(2)
        def p2 = new ListNode(4)
        def p3 = new ListNode(3)
        p1.next = p2
        p2.next = p3
        def r1 = new ListNode(5)
        def r2 = new ListNode(6)
        def r3 = new ListNode(4)
        r1.next = r2
        r2.next = r3
        def t1 = new ListNode(7)
        def t2 = new ListNode(0)
        def t3 = new ListNode(8)
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

    def '快排2'() {
        given:
        def arr = [7, 6, 5, 4, 3, 2, 1] as int[]

        when:
        solution.quickSort(arr)

        then:
        for (i in 1..<arr.length) {
            assert arr[i] >= arr[i - 1]
        }
    }

    def '快排3'() {
        given:
        def arr = [1, 2, 3, 4, 5, 6, 7] as int[]

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
        def three = new TreeNode(3)

        def five = new TreeNode(5)
        def one = new TreeNode(1)
        three.left = five
        three.right = one

        def six = new TreeNode(6)
        def two = new TreeNode(2)
        five.left = six
        five.right = two

        def zero = new TreeNode(0)
        def eight = new TreeNode(8)
        one.left = zero
        one.right = eight

        def seven = new TreeNode(7)
        def four = new TreeNode(4)
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
        nums                              | ans
        [4, 3, 2, 7, 8, 2, 3, 1] as int[] | [3, 2]
    }

    def "解码"() {
        expect:
        solution.decodeString(s) == ans
        where:
        s                              | ans
        "3[z]2[2[y]pq4[2[jk]e1[f]]]ef" | "zzzyypqjkjkefjkjkefjkjkefjkjkefyypqjkjkefjkjkefjkjkefjkjkefef"
    }

    def "查找"() {
        expect:
        solution.search0(arr, target) == ans
        where:
        arr                            | target | ans
        [1, 0, 1, 1, 1] as int[]       | 0      | true
        [2, 5, 6, 0, 0, 1, 2] as int[] | 0      | true
        [2, 5, 6, 0, 0, 1, 2] as int[] | 3      | false
        [3, 1] as int[]                | 1      | true
    }

    def '查找数组最大i~j元素'() {
        expect:
        solution.findTopIJ(arr, i, j) == ans

        where:
        arr                                       | i | j | ans
        [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] as int[]  | 8 | 9 | [8, 9]
        [10, 2, 3, 1, 5, 8, 6, 7, 9, 4] as int[]  | 8 | 9 | [8, 9]
        [10, 2, 3, 10, 3, 8, 6, 9, 9, 2] as int[] | 8 | 9 | [9, 10]
    }

    def '上下翻转二叉树'() {
        given:
        def root = makeTree([1, 2] as int[])

        when:
        def root1 = solution2.upsideDownBinaryTree(root)
        def arr = toArray(root1)

        then:
        arr == [2, null, 1] as Integer[]
    }

    def makeTree(Integer[] trees) {
        def idx = 0
        def root = new TreeNode(trees[idx], null, null)
        def queue = [] as LinkedList<TreeNode>
        queue.offer(root)

        while (!queue.isEmpty()) {
            def size = queue.size()
            for (i in 0..<size) {
                if (idx >= trees.length - 1) {
                    return root
                }
                def tn = queue.poll()
                tn.left = trees[++idx] != null ? new TreeNode(trees[idx], null, null) : null
                tn.right = trees[++idx] != null ? new TreeNode(trees[idx], null, null) : null
                if (tn.left != null) {
                    queue.offer(tn.left)
                }
                if (tn.right != null) {
                    queue.offer(tn.right)
                }
            }
        }
        return root
    }

    def Integer[] toArray(TreeNode root) {
        def list = new ArrayList<Integer>()
        def queue = [] as LinkedList<TreeNode>
        queue.offer(root)

        while (!queue.isEmpty()) {
            def hasNext = false
            def size = queue.size()
            for (i in 0..<size) {
                def tn = queue.poll()
                list.add(tn?.val)
                if (tn != null) {
                    queue.offer(tn.left)
                    queue.offer(tn.right)
                    if (tn.left != null || tn.right != null) {
                        hasNext = true
                    }
                }
            }
            if (!hasNext) {
                break
            }
        }
        return list.toArray(Integer[]) as Integer[]
    }

    def ListNode makeListNode(int[] arr) {
        ListNode root = new ListNode(arr[0])
        ListNode prev = root;
        for (i in 1..<arr.length) {
            ListNode n = new ListNode(arr[i])
            prev.next = n
            prev = n
        }
        return root
    }

    def toIntArr(ListNode listNode) {
        def list = []
        while (listNode != null) {
            list.add(listNode.val)
            listNode = listNode.next
        }
        return list.toArray(Integer[])
    }

    def '从未排序的链表中移除重复元素'() {
        when:
        def node = solution2.deleteDuplicatesUnsorted(makeListNode([1, 2, 3, 2] as int[]))
        then:
        toIntArr(node) == [1, 3] as Integer[]
    }

    def '回文素数'() {
        when:
        def ans = solution2.primePalindrome(102)
        then:
        ans == 131
    }

    def '到达终点数字'() {
        expect:
        solution2.reachNumber(target) == n

        where:
        target | n
        2      | 3
    }

    def '划分数组为连续数字的集合'() {
        expect:
        solution2.isPossibleDivide(nums, k) == result
        where:
        nums                              | k | result
        [1, 2, 3, 3, 4, 4, 5, 6] as int[] | 4 | true
        [3, 3, 2, 2, 1, 1] as int[]       | 3 | true
        [1, 2, 3, 4] as int[]             | 3 | false
    }

    def '二叉树最长连续序列'() {
        given:
        def root = makeTree([1, 2, 3] as Integer[])
        when:
        def result = solution2.longestConsecutive(root)
        then:
        result == 2
    }

    def '最短的桥'() {
        given:
        def A = [[0, 1, 0], [0, 0, 0], [0, 0, 1]] as int[][]
        when:
        def shortest = solution2.shortestBridge(A)
        then:
        shortest == 2
    }

    def '最大加号标志'() {
        expect:
        solution2.orderOfLargestPlusSign(n, mines) == ans
        where:
        ans | n | mines
        2   | 3 | [[0, 0]] as int[][]
        2   | 5 | [[4, 2]] as int[][]
        2   | 5 | [[3, 0], [3, 3]] as int[][]
        2   | 5 | [[0, 0], [0, 3], [1, 1], [1, 4], [2, 3], [3, 0], [4, 2]] as int[][]
        2   | 2 | [[0, 0], [0, 1], [1, 0]] as int[][]
    }

    def '二叉树的序列化与反序列化'() {
        def intArr = [1, null, 2, null, 3, null, 4, null, 5, null, 6, null, 7, null, 8, null, 9, null, 10, null, 11, null, 12, null, 13, null, 14, null, 15, null, 16, null, 17, null, 18, null, 19, null, 20, null, 21, null, 22, null, 23, null, 24, null, 25, null, 26, null, 27, null, 28, null, 29, null, 30, null, 31, null, 32, null, 33, null, 34, null, 35, null, 36, null, 37, null, 38, null, 39, null, 40, null, 41, null, 42, null, 43, null, 44, null, 45, null, 46, null, 47, null, 48, null, 49, null, 50, null, 51, null, 52, null, 53, null, 54, null, 55, null, 56, null, 57, null, 58, null, 59, null, 60, null, 61, null, 62, null, 63, null, 64, null, 65, null, 66, null, 67, null, 68, null, 69, null, 70, null, 71, null, 72, null, 73, null, 74, null, 75, null, 76, null, 77, null, 78, null, 79, null, 80, null, 81, null, 82, null, 83, null, 84, null, 85, null, 86, null, 87, null, 88, null, 89, null, 90, null, 91, null, 92, null, 93, null, 94, null, 95, null, 96, null, 97, null, 98, null, 99, null, 100, null, 101, null, 102, null, 103, null, 104, null, 105, null, 106, null, 107, null, 108, null, 109, null, 110, null, 111, null, 112, null, 113, null, 114, null, 115, null, 116, null, 117, null, 118, null, 119, null, 120, null, 121, null, 122, null, 123, null, 124, null, 125, null, 126, null, 127, null, 128, null, 129, null, 130, null, 131, null, 132, null, 133, null, 134, null, 135, null, 136, null, 137, null, 138, null, 139, null, 140, null, 141, null, 142, null, 143, null, 144, null, 145, null, 146, null, 147, null, 148, null, 149, null, 150, null, 151, null, 152, null, 153, null, 154, null, 155, null, 156, null, 157, null, 158, null, 159, null, 160, null, 161, null, 162, null, 163, null, 164, null, 165, null, 166, null, 167, null, 168, null, 169, null, 170, null, 171, null, 172, null, 173, null, 174, null, 175, null, 176, null, 177, null, 178, null, 179, null, 180, null, 181, null, 182, null, 183, null, 184, null, 185, null, 186, null, 187, null, 188, null, 189, null, 190, null, 191, null, 192, null, 193, null, 194, null, 195, null, 196, null, 197, null, 198, null, 199, null, 200, null, 201, null, 202, null, 203, null, 204, null, 205, null, 206, null, 207, null, 208, null, 209, null, 210, null, 211, null, 212, null, 213, null, 214, null, 215, null, 216, null, 217, null, 218, null, 219, null, 220, null, 221, null, 222, null, 223, null, 224, null, 225, null, 226, null, 227, null, 228, null, 229, null, 230, null, 231, null, 232, null, 233, null, 234, null, 235, null, 236, null, 237, null, 238, null, 239, null, 240, null, 241, null, 242, null, 243, null, 244, null, 245, null, 246, null, 247, null, 248, null, 249, null, 250, null, 251, null, 252, null, 253, null, 254, null, 255, null, 256, null, 257, null, 258, null, 259, null, 260, null, 261, null, 262, null, 263, null, 264, null, 265, null, 266, null, 267, null, 268, null, 269, null, 270, null, 271, null, 272, null, 273, null, 274, null, 275, null, 276, null, 277, null, 278, null, 279, null, 280, null, 281, null, 282, null, 283, null, 284, null, 285, null, 286, null, 287, null, 288, null, 289, null, 290, null, 291, null, 292, null, 293, null, 294, null, 295, null, 296, null, 297, null, 298, null, 299, null, 300, null, 301, null, 302, null, 303, null, 304, null, 305, null, 306, null, 307, null, 308, null, 309, null, 310, null, 311, null, 312, null, 313, null, 314, null, 315, null, 316, null, 317, null, 318, null, 319, null, 320, null, 321, null, 322, null, 323, null, 324, null, 325, null, 326, null, 327, null, 328, null, 329, null, 330, null, 331, null, 332, null, 333, null, 334, null, 335, null, 336, null, 337, null, 338, null, 339, null, 340, null, 341, null, 342, null, 343, null, 344, null, 345, null, 346, null, 347, null, 348, null, 349, null, 350, null, 351, null, 352, null, 353, null, 354, null, 355, null, 356, null, 357, null, 358, null, 359, null, 360, null, 361, null, 362, null, 363, null, 364, null, 365, null, 366, null, 367, null, 368, null, 369, null, 370, null, 371, null, 372, null, 373, null, 374, null, 375, null, 376, null, 377, null, 378, null, 379, null, 380, null, 381, null, 382, null, 383, null, 384, null, 385, null, 386, null, 387, null, 388, null, 389, null, 390, null, 391, null, 392, null, 393, null, 394, null, 395, null, 396, null, 397, null, 398, null, 399, null, 400, null, 401, null, 402, null, 403, null, 404, null, 405, null, 406, null, 407, null, 408, null, 409, null, 410, null, 411, null, 412, null, 413, null, 414, null, 415, null, 416, null, 417, null, 418, null, 419, null, 420, null, 421, null, 422, null, 423, null, 424, null, 425, null, 426, null, 427, null, 428, null, 429, null, 430, null, 431, null, 432, null, 433, null, 434, null, 435, null, 436, null, 437, null, 438, null, 439, null, 440, null, 441, null, 442, null, 443, null, 444, null, 445, null, 446, null, 447, null, 448, null, 449, null, 450, null, 451, null, 452, null, 453, null, 454, null, 455, null, 456, null, 457, null, 458, null, 459, null, 460, null, 461, null, 462, null, 463, null, 464, null, 465, null, 466, null, 467, null, 468, null, 469, null, 470, null, 471, null, 472, null, 473, null, 474, null, 475, null, 476, null, 477, null, 478, null, 479, null, 480, null, 481, null, 482, null, 483, null, 484, null, 485, null, 486, null, 487, null, 488, null, 489, null, 490, null, 491, null, 492, null, 493, null, 494, null, 495, null, 496, null, 497, null, 498, null, 499, null, 500, null, 501, null, 502, null, 503, null, 504, null, 505, null, 506, null, 507, null, 508, null, 509, null, 510, null, 511, null, 512, null, 513, null, 514, null, 515, null, 516, null, 517, null, 518, null, 519, null, 520, null, 521, null, 522, null, 523, null, 524, null, 525, null, 526, null, 527, null, 528, null, 529, null, 530, null, 531, null, 532, null, 533, null, 534, null, 535, null, 536, null, 537, null, 538, null, 539, null, 540, null, 541, null, 542, null, 543, null, 544, null, 545, null, 546, null, 547, null, 548, null, 549, null, 550, null, 551, null, 552, null, 553, null, 554, null, 555, null, 556, null, 557, null, 558, null, 559, null, 560, null, 561, null, 562, null, 563, null, 564, null, 565, null, 566, null, 567, null, 568, null, 569, null, 570, null, 571, null, 572, null, 573, null, 574, null, 575, null, 576, null, 577, null, 578, null, 579, null, 580, null, 581, null, 582, null, 583, null, 584, null, 585, null, 586, null, 587, null, 588, null, 589, null, 590, null, 591, null, 592, null, 593, null, 594, null, 595, null, 596, null, 597, null, 598, null, 599, null, 600, null, 601, null, 602, null, 603, null, 604, null, 605, null, 606, null, 607, null, 608, null, 609, null, 610, null, 611, null, 612, null, 613, null, 614, null, 615, null, 616, null, 617, null, 618, null, 619, null, 620, null, 621, null, 622, null, 623, null, 624, null, 625, null, 626, null, 627, null, 628, null, 629, null, 630, null, 631, null, 632, null, 633, null, 634, null, 635, null, 636, null, 637, null, 638, null, 639, null, 640, null, 641, null, 642, null, 643, null, 644, null, 645, null, 646, null, 647, null, 648, null, 649, null, 650, null, 651, null, 652, null, 653, null, 654, null, 655, null, 656, null, 657, null, 658, null, 659, null, 660, null, 661, null, 662, null, 663, null, 664, null, 665, null, 666, null, 667, null, 668, null, 669, null, 670, null, 671, null, 672, null, 673, null, 674, null, 675, null, 676, null, 677, null, 678, null, 679, null, 680, null, 681, null, 682, null, 683, null, 684, null, 685, null, 686, null, 687, null, 688, null, 689, null, 690, null, 691, null, 692, null, 693, null, 694, null, 695, null, 696, null, 697, null, 698, null, 699, null, 700, null, 701, null, 702, null, 703, null, 704, null, 705, null, 706, null, 707, null, 708, null, 709, null, 710, null, 711, null, 712, null, 713, null, 714, null, 715, null, 716, null, 717, null, 718, null, 719, null, 720, null, 721, null, 722, null, 723, null, 724, null, 725, null, 726, null, 727, null, 728, null, 729, null, 730, null, 731, null, 732, null, 733, null, 734, null, 735, null, 736, null, 737, null, 738, null, 739, null, 740, null, 741, null, 742, null, 743, null, 744, null, 745, null, 746, null, 747, null, 748, null, 749, null, 750, null, 751, null, 752, null, 753, null, 754, null, 755, null, 756, null, 757, null, 758, null, 759, null, 760, null, 761, null, 762, null, 763, null, 764, null, 765, null, 766, null, 767, null, 768, null, 769, null, 770, null, 771, null, 772, null, 773, null, 774, null, 775, null, 776, null, 777, null, 778, null, 779, null, 780, null, 781, null, 782, null, 783, null, 784, null, 785, null, 786, null, 787, null, 788, null, 789, null, 790, null, 791, null, 792, null, 793, null, 794, null, 795, null, 796, null, 797, null, 798, null, 799, null, 800, null, 801, null, 802, null, 803, null, 804, null, 805, null, 806, null, 807, null, 808, null, 809, null, 810, null, 811, null, 812, null, 813, null, 814, null, 815, null, 816, null, 817, null, 818, null, 819, null, 820, null, 821, null, 822, null, 823, null, 824, null, 825, null, 826, null, 827, null, 828, null, 829, null, 830, null, 831, null, 832, null, 833, null, 834, null, 835, null, 836, null, 837, null, 838, null, 839, null, 840, null, 841, null, 842, null, 843, null, 844, null, 845, null, 846, null, 847, null, 848, null, 849, null, 850, null, 851, null, 852, null, 853, null, 854, null, 855, null, 856, null, 857, null, 858, null, 859, null, 860, null, 861, null, 862, null, 863, null, 864, null, 865, null, 866, null, 867, null, 868, null, 869, null, 870, null, 871, null, 872, null, 873, null, 874, null, 875, null, 876, null, 877, null, 878, null, 879, null, 880, null, 881, null, 882, null, 883, null, 884, null, 885, null, 886, null, 887, null, 888, null, 889, null, 890, null, 891, null, 892, null, 893, null, 894, null, 895, null, 896, null, 897, null, 898, null, 899, null, 900, null, 901, null, 902, null, 903, null, 904, null, 905, null, 906, null, 907, null, 908, null, 909, null, 910, null, 911, null, 912, null, 913, null, 914, null, 915, null, 916, null, 917, null, 918, null, 919, null, 920, null, 921, null, 922, null, 923, null, 924, null, 925, null, 926, null, 927, null, 928, null, 929, null, 930, null, 931, null, 932, null, 933, null, 934, null, 935, null, 936, null, 937, null, 938, null, 939, null, 940, null, 941, null, 942, null, 943, null, 944, null, 945, null, 946, null, 947, null, 948, null, 949, null, 950, null, 951, null, 952, null, 953, null, 954, null, 955, null, 956, null, 957, null, 958, null, 959, null, 960, null, 961, null, 962, null, 963, null, 964, null, 965, null, 966, null, 967, null, 968, null, 969, null, 970, null, 971, null, 972, null, 973, null, 974, null, 975, null, 976, null, 977, null, 978, null, 979, null, 980, null, 981, null, 982, null, 983, null, 984, null, 985, null, 986, null, 987, null, 988, null, 989, null, 990, null, 991, null, 992, null, 993, null, 994, null, 995, null, 996, null, 997, null, 998, null, 999, null, 1000] as Integer[]
        given:
        def treeNode = makeTree(intArr)
        when:
        def t1 = System.currentTimeMillis()
        def x = solution2.serialize(treeNode)
        def t2 = System.currentTimeMillis()
        println("m1: ${t2 - t1}")

        def t3 = System.currentTimeMillis()
        def n = solution2.deserialize(x)
        def t4 = System.currentTimeMillis()
        println("m2: ${t4 - t3}")
        then:
        n.val == treeNode.val
    }

    def '丑数'() {
        expect:
        solution2.isUgly(n) == ret
        where:
        n  | ret
        14 | true
        9  | true
    }

    def '第n丑数'() {
        expect:
        solution2.nthUglyNumber(n) == num
        where:
        n  | num
        10 | 12
    }

    def '会议室'() {
        expect:
        solution2.canAttendMeetings(intervals) == ret
        where:
        intervals                      | ret
        [[13, 15], [1, 13]] as int[][] | false
    }

    def '会议室2'() {
        expect:
        solution2.minMeetingRooms(intervals) == ret
        where:
        intervals                               | ret
        [[0, 30], [5, 10], [15, 20]] as int[][] | 2
    }

    def '摆动排序'() {
        given:
        def nums = [3,5,2,1,6,4] as int[]
        when:
        solution2.wiggleSort(nums)
        then:
        nums == [3, 5, 1, 6, 2, 4] as int[]
    }
}
