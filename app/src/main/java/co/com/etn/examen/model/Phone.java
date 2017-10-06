package co.com.etn.examen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jose.cardenas on 03/10/2017.
 */

public class Phone {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("location")
    @Expose
    private Location location;

    public Phone() {
    }

    public Phone(String description, String number, Location location) {
        this.description = description;
        this.number = number;
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
