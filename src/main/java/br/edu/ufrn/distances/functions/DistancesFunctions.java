package br.edu.ufrn.distances.functions;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.edu.ufrn.distances.DistancesProperties;
import br.edu.ufrn.distances.enums.Unit;
import br.edu.ufrn.distances.records.ConversionRequest;
import br.edu.ufrn.distances.records.ConversionResponse;
import br.edu.ufrn.distances.records.DistanceRequest;
import br.edu.ufrn.distances.records.DistanceResponse;
import br.edu.ufrn.distances.records.Geolocation;

@Configuration
public class DistancesFunctions {
    
    @Autowired
    private DistancesProperties properties;

    private static final Logger logger = LoggerFactory.getLogger(DistancesFunctions.class);

    @Bean
    public Function<DistanceRequest, DistanceResponse> distance() {
        return request -> {
            logger.info(
                "Function distance() called with request " + request.toString()
            );
            
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

            double earthRadiusKilometers = this.properties.getEarthRadiusKilometers();

            logger.info(
                "Function distance() called with request " + request.toString()
                + " using property earthRadiusKilometers=" + String.valueOf(earthRadiusKilometers)
            );

            double distance = 2 * earthRadiusKilometers * Math.asin(Math.sqrt(x));

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
            
            Unit from = request.from();
            Unit to = request.to();
            double originValue = request.value();            

            double kilometersToMiles = this.properties.getKilometersToMiles();
            double milesToKilometers = this.properties.getMilesToKilometers();

            logger.info(
                "Function convert() called with request " + request.toString()
                + " using property kilometersToMiles=" + String.valueOf(kilometersToMiles)
                + " and property milesToKilometers=" + String.valueOf(milesToKilometers)
            );

            double value;

            if (from == Unit.KILOMETERS && to == Unit.MILES) {
                value = originValue * kilometersToMiles;
            } else if (from == Unit.MILES && to == Unit.KILOMETERS) {
                value = originValue * milesToKilometers;
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
