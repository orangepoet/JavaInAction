package cn.orangepoet.inaction.algorithm.flightattack;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public class FlightUnit implements FlightJudge {
    private final List<Flight> flights;

    public FlightUnit(List<Flight> flights) {
        if (CollectionUtils.isEmpty(flights)) {
            throw new IllegalArgumentException("flights is invalid");
        }
        this.flights = flights;
    }

    public List<Flight> getFlights() {
        return new ArrayList<>(flights);
    }

    public List<Position> getAllPos() {
        return this.flights.stream().flatMap(f -> f.allPosSet().stream()).collect(Collectors.toList());
    }

    @Override
    public boolean isHead(Position position) {
        return this.flights.stream()
            .anyMatch(f -> f.isHead(position));
    }

    @Override
    public boolean isBody(Position position) {
        return this.flights.stream()
            .anyMatch(f -> f.isBody(position));
    }

    @Override
    public boolean hasPos(Position position) {
        return this.flights.stream()
            .anyMatch(f -> f.hasPos(position));
    }
}
