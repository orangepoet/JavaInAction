package cn.orangepoet.inaction.algorithm.games.flightattack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public class Application {
    //public static void main(String[] args) {
    //    long start = System.currentTimeMillis();
    //
    //    FlightMap flightMap = FlightMap.create(10);
    //    List<FlightUnit> flightUnits = flightMap.listFlightUnits();
    //    long end = System.currentTimeMillis();
    //    System.out.println("elapsed: " + (end - start) + " ms, case size: " + flightUnits.size());
    //
    //    Set<Position> guessPosSet = new HashSet<>();
    //
    //    List<Position> headPosition = new ArrayList<>();
    //
    //    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
    //        while (true) {
    //            //System.out.println("please input position:");
    //            //String[] posArr = reader.readLine().split(",");
    //            //int x = Integer.parseInt(posArr[0].trim());
    //            //int y = Integer.parseInt(posArr[1].trim());
    //            Position guessPosition = flightMap.getHintPos(flightUnits, guessPosSet);
    //            if (guessPosition != null) {
    //                System.out.println("guess position: " + guessPosition.toString());
    //            } else {
    //                System.out.println("guess position null, over");
    //                break;
    //            }
    //
    //            System.out.println("please input match result:");
    //            String matchRetStr = reader.readLine();
    //            MatchResult matchResult = MatchResult.parse(matchRetStr);
    //
    //            if (matchResult == MatchResult.UNKNOWN) {
    //                System.out.println("result is invalid");
    //                continue;
    //            } else if (matchResult == MatchResult.HEAD) {
    //                headPosition.add(guessPosition);
    //            }
    //
    //            guessPosSet.add(guessPosition);
    //            flightUnits = flightMap.refreshFlightUnits(flightUnits, guessPosition, matchResult);
    //            System.out.println("left case size: " + flightUnits.size());
    //            if (flightUnits.size() == 0) {
    //                System.out.println("NOT FOUND ERROR !");
    //                break;
    //            }
    //            if (headPosition.size() >= 3) {
    //                System.out.println("SUCCESS");
    //            }
    //        }
    //    } catch (IOException e) {
    //        System.out.println("INPUT ERROR");
    //    }
    //}

    public int guessTimes(FlightMap flightMap, List<FlightUnit> flightUnits, FlightUnit targetFN) {
        int guessWinCnt = 0;
        Set<Position> hintPosSet = new HashSet<>();
        Position guessPos = flightMap.getHintPos(flightUnits, hintPosSet);
        int guessTimes = 1;
        while (guessPos != null) {
            MatchResult matchResult = targetFN.isHead(guessPos) ? MatchResult.HEAD : targetFN.isBody(guessPos)
                ? MatchResult.BODY : MatchResult.NONE;

            System.out.println("guess pos: " + guessPos + ", result: " + matchResult);

            if (matchResult == MatchResult.HEAD) {
                guessWinCnt++;
                if (guessWinCnt >= 3) {
                    System.out.println("guess over, success, times: " + guessTimes);
                    break;
                }
            }

            flightUnits = flightMap.refreshFlightUnits(flightUnits, guessPos, matchResult);
            System.out.println("left case size: " + flightUnits.size());

            if (flightUnits.size() <= 1) {
                break;
            }

            hintPosSet.add(guessPos);
            guessPos = flightMap.getHintPos(flightUnits, hintPosSet);
            guessTimes++;
        }
        return guessTimes;
    }
}
