package com.aqua_society.quotes.Modules;

/**
 * Created by MrCharif on 01/01/2017.
 */

public class Apps {
    private int id;
    private String name, cover, description, package_name, nbr_download;

    public Apps(int id, String name, String cover, String description, String package_name, String nbr_download) {
        this.id = id;
        this.name = name;
        this.cover = cover;
        this.description = description;
        this.package_name = package_name;
        this.nbr_download = nbr_download;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getNbr_download() {
        return nbr_download;
    }

    public void setNbr_download(String nbr_download) {
        this.nbr_download = nbr_download;
    }
}
