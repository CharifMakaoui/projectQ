package com.aqua_society.quotes.Modules;

/**
 * Created by MrCharif on 04/01/2017.
 */

public class Quote {

    private int id,fav;
    private String author_name,category_name,qte,image;

    public Quote() {
    }

    public Quote(int id, String author_name, String category_name, String qte, int fav, String image) {
        this.id = id;
        this.author_name = author_name;
        this.category_name = category_name;
        this.qte = qte;
        this.fav = fav;
        this.image = image;
    }

    public String getTitle(){
        String[] result = this.qte.split("\n", 2);

        return result[0];
    }

    public String[] getContent(){
        String[] result = this.qte.split("\n", 3);

        return result;
    }

    public String getDescription(int nbrChar){
        String[] result = this.qte.split("\n", 3);

        return result[2].substring(0, nbrChar);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getQte() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte = qte;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", fav=" + fav +
                ", author_name='" + author_name + '\'' +
                ", category_name='" + category_name + '\'' +
                ", qte='" + qte + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
