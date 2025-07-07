package br.edu.ufrn.distancies.functions;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.edu.ufrn.distancies.enums.Unit;
import br.edu.ufrn.distancies.records.ConversionRequest;
import br.edu.ufrn.distancies.records.ConversionResponse;
import br.edu.ufrn.distancies.records.DistanceRequest;
import br.edu.ufrn.distancies.records.DistanceResponse;
import br.edu.ufrn.distancies.records.Geolocation;

@Configuration
public class DistanciesFunctions {

    @Bean
    public Function<DistanceRequest, DistanceResponse> distance() {
        return req -> {
            double EARTH_RADIUS_KM = 6371.0;
            
            Unit unit = Unit.KILOMETERS;

            Geolocation origin = req.origin();
            Geolocation destination = req.destination();

            double dLat = Math.toRadians(destination.lat() - origin.lat());
            double dLon = Math.toRadians(destination.lon() - origin.lon());

            double rLat1 = Math.toRadians(origin.lat());
            double rLat2 = Math.toRadians(destination.lat());

            double x = Math.pow(Math.sin(dLat/2), 2)
                + Math.cos(rLat1)
                * Math.cos(rLat2)
                * Math.pow(Math.sin(dLon/2), 2);

            double distance = 2 * EARTH_RADIUS_KM * Math.asin(Math.sqrt(x));

            return new DistanceResponse(distance, unit);
        };
    }

    @Bean
    public Function<ConversionRequest, ConversionResponse> convert() {
        return req -> {
            double KILOMETERS_TO_MILES = 0.621371;
            double MILES_TO_KILOMETERS = 1.609344;

            Unit from = req.from();
            Unit to = req.to();
            double originValue = req.value();
            

            double value;

            if (from == Unit.KILOMETERS && to == Unit.MILES) {
                value = originValue * KILOMETERS_TO_MILES;
            } else if (from == Unit.MILES && to == Unit.KILOMETERS) {
                value = originValue * MILES_TO_KILOMETERS;
            } else {
                value = originValue;
            }

            return new ConversionResponse(value);
        };
    }

}
