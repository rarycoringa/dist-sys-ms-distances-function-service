package br.edu.ufrn.distances;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "distances")
public class DistancesProperties {
    private double earthRadiusKilometers;
    private double earthRadiusMiles;
    private double kilometersToMiles;
    private double milesToKilometers;

    public DistancesProperties() {}

    public double getEarthRadiusKilometers() {
        return this.earthRadiusKilometers;
    }

    public void setEarthRadiusKilometers(double earthRadiusKilometers) {
        this.earthRadiusKilometers = earthRadiusKilometers;
    }

    public double getEarthRadiusMiles() {
        return this.earthRadiusMiles;
    }

    public void setEarthRadiusMiles(double earthRadiusMiles) {
        this.earthRadiusMiles = earthRadiusMiles;
    }

    public double getKilometersToMiles() {
        return this.kilometersToMiles;
    }

    public void setKilometersToMiles(double kilometersToMiles) {
        this.kilometersToMiles = kilometersToMiles;
    }

    public double getMilesToKilometers() {
        return this.milesToKilometers;
    }

    public void setMilesToKilometers(double milesToKilometers) {
        this.milesToKilometers = milesToKilometers;
    }

}
