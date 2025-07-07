package br.edu.ufrn.distancies.functions;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(DistanciesFunctions.class);
    
    @Bean
    public Function<DistanceRequest, DistanceResponse> distance() {
        return request -> {
            logger.info(
                "Function distance() called with request " + request.toString()
            );

            double EARTH_RADIUS_KM = 6371.0;
            
            Unit unit = Unit.KILOMETERS;

            Geolocation origin = request.origin();
            Geolocation destination = request.destination();

            double dLat = Math.toRadians(destination.lat() - origin.lat());
            double dLon = Math.toRadians(destination.lon() - origin.lon());

            double rLat1 = Math.toRadians(origin.lat());
            double rLat2 = Math.toRadians(destination.lat());

            double x = Math.pow(Math.sin(dLat/2), 2)
                + Math.cos(rLat1)
                * Math.cos(rLat2)
                * Math.pow(Math.sin(dLon/2), 2);

            double distance = 2 * EARTH_RADIUS_KM * Math.asin(Math.sqrt(x));

            DistanceResponse response = new DistanceResponse(distance, unit);

                        logger.info(
                "Function distance() called with request " + request.toString()
                + " returning response " + response.toString()
            );

            return response;
        };
    }

    @Bean
    public Function<ConversionRequest, ConversionResponse> convert() {
        return request -> {
            logger.info(
                "Function convert() called with request " + request.toString()
            );

            double KILOMETERS_TO_MILES = 0.621371;
            double MILES_TO_KILOMETERS = 1.609344;
            
            Unit from = request.from();
            Unit to = request.to();
            double originValue = request.value();            

            double value;

            if (from == Unit.KILOMETERS && to == Unit.MILES) {
                value = originValue * KILOMETERS_TO_MILES;
            } else if (from == Unit.MILES && to == Unit.KILOMETERS) {
                value = originValue * MILES_TO_KILOMETERS;
            } else {
                value = originValue;
            }

            ConversionResponse response = new ConversionResponse(value);

            logger.info(
                "Function convert() called with request " + request.toString()
                + " returning response " + response.toString()
            );

            return response;
        };
    }

}
