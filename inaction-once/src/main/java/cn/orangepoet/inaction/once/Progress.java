package cn.orangepoet.inaction.once;

import org.hashids.Hashids;

/**
 * @author chengzhi
 * @date 2019/05/10
 */
public class Progress {
    public static void main(String[] args) {
        Hashids hashids = new Hashids("this is my salt");
        String encode = hashids.encode(1013989972);
        System.out.println(encode);

        //Evvwwvq

        
        //
        //int start = 10000;
        //int end = start * 1000;
        //
        //List<String> encodes = new ArrayList<>();
        //for (int i = start; i < end; i++) {
        //    String encode = hashids.encode(i);
        //    encodes.add(encode);
        //    //long[] decode = hashids.decode(encode);
        //}
        //
        //StopWatch stopWatch = StopWatch.createStarted();
        //for (int i = 0; i < encodes.size(); i++) {
        //    hashids.decode(encodes.get(i));
        //}
        //
        //stopWatch.stop();
        //System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));

        //int total = 27227;
        //int each = total / 1000;
        //System.out.println(each);

        //int total = 7553;
        //int each = total / 1000;
        //System.out.println(each);   // 7

        //int total = 26196;
        //int each = total / 1000;
        //System.out.println(each);  // 26

        //long[] abcs = hashids.decode("abc");
        //for (int i = 0; i < abcs.length; i++) {
        //    System.out.println(abcs[0]);
        //}
    }
}
