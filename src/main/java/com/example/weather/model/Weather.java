package com.example.weather.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.net.URI;

@Data
@NoArgsConstructor
@Entity
@Table(name="WEATHER")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String country;
    private String description;
    private double currentTemp;
    private double feelsLike;
    private double minTemp;
    private double maxTemp;
    private double tempLimit;
    private long frequency;
    private String frequencyUnit;
    private URI uri;


    public Weather(Builder builder){
        this.id = builder.id;
        this.city = builder.city;
        this.country = builder.country;
        this.description = builder.description;
        this.currentTemp = builder.currentTemp;
        this.feelsLike = builder.feelsLike;
        this.minTemp = builder.minTemp;
        this.maxTemp = builder.maxTemp;
        this.tempLimit = builder.tempLimit;
        this.frequency = builder.frequency;
        this.frequencyUnit = builder.frequencyUnit;
        this.uri = builder.uri;
    }

    public static class Builder {
        private Long id;
        private String city;
        private String country;
        private String description;
        private double currentTemp;
        private double feelsLike;
        private double minTemp;
        private double maxTemp;
        private double tempLimit;
        private long frequency;
        private String frequencyUnit;
        private URI uri;

        public Builder id( Long id){
            this.id = id;
            return this;
        }

        public Builder city( String city){
            this.city = city;
            return this;
        }

        public Builder country( String country){
            this.country = country;
            return this;
        }

        public Builder description( String description){
            this.description = description;
            return this;
        }

        public Builder currentTemp( double currentTemp){
            this.currentTemp= currentTemp;
            return this;
        }

        public Builder feelsLike( double feelsLike){
            this.feelsLike= feelsLike;
            return this;
        }

        public Builder minTemp( double minTemp){
            this.minTemp = minTemp;
            return this;
        }

        public Builder maxTemp( double maxTemp){
            this.maxTemp = maxTemp;
            return this;
        }

        public Builder tempLimit( double tempLimit){
            this.tempLimit = tempLimit;
            return this;
        }

        public Builder frequency( long frequency){
            this.frequency = frequency;
            return this;
        }

        public Builder frequencyUnit(String frequencyUnit){
            this.frequencyUnit = frequencyUnit;
            return this;
        }

        public Builder uri( URI uri){
            this.uri = uri;
            return this;
        }

        public Weather build(){
            return new Weather(this);
        }
    }
}
