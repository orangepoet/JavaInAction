package cn.orangepoet.inaction.algorithm.flightattack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public class Starter {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        FlightMap flightMap = FlightMap.create(10);
        List<FlightUnit> flightUnits = flightMap.listFlightUnits();
        long end = System.currentTimeMillis();
        System.out.println("elapsed: " + (end - start) + " ms, case size: " + flightUnits.size());

        Set<Position> guessPosSet = new HashSet<>();

        List<Position> headPosition = new ArrayList<>();

        try {
            while (true) {
                System.out.println("please input position:");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String[] posArr = reader.readLine().split(",");
                try {
                    int x = Integer.parseInt(posArr[0].trim());
                    int y = Integer.parseInt(posArr[1].trim());

                    System.out.println("please input match result:");
                    String matchRetStr = reader.readLine();
                    MatchResult matchResult = MatchResult.parse(matchRetStr);

                    Position guessPosition = Position.get(x, y);
                    guessPosSet.add(guessPosition);

                    if (matchResult == MatchResult.UNKNOWN) {
                        System.out.println("result is invalid");
                        continue;
                    } else if (matchResult == MatchResult.HEAD) {
                        headPosition.add(guessPosition);
                    }

                    flightUnits = flightMap.refreshFlightUnits(flightUnits, guessPosition, matchResult);
                    System.out.println("left case size: " + flightUnits.size());
                    if (flightUnits.size() == 0) {
                        System.out.println("NOT FOUND ERROR !");
                        break;
                    }
                    if (headPosition.size() >= 3) {
                        System.out.println("SUCCESS");
                    }
                    Position hintPos = flightMap.getHintPos(flightUnits, guessPosSet);
                    if (hintPos != null) {
                        System.out.println("hint position: " + hintPos.toString());
                    } else {
                        System.out.println("no hint left...");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("position is invalid");
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
