package co.com.etn.examen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jose.cardenas on 03/10/2017.
 */

public class Location implements Serializable {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("coordinates")
    @Expose
    private double[] coordinates;

    public Location() {
    }

    public Location(String type, double[] coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

}
