package utils;

import java.util.Random;

public class Coordinates {
    public double latitude;
    public double longitude;

//    Class constructors
    public Coordinates(double lat, double lon) {
        latitude = lat;
        longitude = lon;
    }

    public Coordinates() {
        latitude = 0;
        longitude = 0;
    }


//    Generate randomly from 0 to 1000
    public double distanceFrom(Coordinates anotherCoordinates){
        Random r = new Random();
        return 0 + 1000 * r.nextDouble();
    }


//    This one looks awful
    public double REALdistanceFrom(Coordinates anotherCoordinates){

        double lon1 = Math.toRadians(this.longitude);
        double lon2 = Math.toRadians(anotherCoordinates.longitude);
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(anotherCoordinates.latitude);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        double r = 6371;

        return(c * r);
    }


//    Randomly set coordinates
    public Coordinates RandomSet(double from, double to){

        Random r = new Random();
        this.latitude = from + (to - from) * r.nextDouble();
        this.longitude = from + (to - from) * r.nextDouble();
        return null;
    }

}
