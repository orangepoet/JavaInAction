package cn.orangepoet.inaction.algorithm.flightattack;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public class FlightMapTest {

    @Test
    public void listFlightByHead() {
        FlightMap flightMap = FlightMap.create(10);
        List<Flight> flights = flightMap.listFlightByHead(Position.get(3, 3));
        flights.forEach(flightMap::printFlight);

        flightMap.listFlightByHead(Position.get(3, 3));
        flights.forEach(flightMap::printFlight);
    }

    @Test
    public void testPosition() {
        Position p1 = Position.get(1, 1);
        Position p2 = Position.get(1, 1);
        Assert.assertEquals(p1, p2);

        Set<Position> posSet = new HashSet<>();
        posSet.add(p1);

        Assert.assertTrue(posSet.contains(p1));

    }
}