
package se.blerand.lab1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Blerand Bahtiri on 2016-11-18.
 */
public class MyUser {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("__v")
    @Expose
    private int v;
    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("telno")
    @Expose
    private String telno;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The v
     */
    public int getV() {
        return v;
    }

    /**
     * 
     * @param v
     *     The __v
     */
    public void setV(int v) {
        this.v = v;
    }

    /**
     * 
     * @return
     *     The active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * 
     * @param active
     *     The active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * 
     * @return
     *     The info
     */
    public String getInfo() {
        return info;
    }

    /**
     * 
     * @param info
     *     The info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 
     * @return
     *     The telno
     */
    public String getTelno() {
        return telno;
    }

    /**
     * 
     * @param telno
     *     The telno
     */
    public void setTelno(String telno) {
        this.telno = telno;
    }

}
