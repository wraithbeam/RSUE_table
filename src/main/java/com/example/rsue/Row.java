package com.example.rsue;

import java.util.Objects;

public class Row {
    //Объединенные данные
    private String fio;
    private String time;

    //Раздельные данные
    private String surname;
    private String name;
    private String otchestvo;
    private String address;
    private String  index;

    private String type;

    private String izdatel;
    private String title;
    private String article;
    private String addressCompany;

    private String timeInYear;
    private String timeAtAll;


    public Row(){
        this.fio = "";
        this.type = "";
        this.title = "";
        this.time = "";
    }

    public Row(String surname, String name, String otchestvo, String address, String index,
               String izdatel, String title, String addressCompany, String timeInYear, String timeAtAll,
               String article, String type) {
        this.name = name;
        this.address = address;
        this.type = type;
        if (title.charAt(0) != '\"')
            this.title = "\"" + title + "\"";
        else
            this.title = title;
        this.surname = surname;
        this.otchestvo = otchestvo;
        this.izdatel = izdatel;
        this.addressCompany = addressCompany;
        this.timeInYear = timeInYear;
        this.timeAtAll = timeAtAll;
        this.index = index;
        this.article = article;

        if (surname != null && surname != "")
            if (name != null && name != "")
                if (otchestvo != null && otchestvo != "")
                    this.fio = surname + " " +  (name.charAt(0)) + "." + otchestvo.charAt(0) + ".";
                else
                    this.fio = surname + " " +  (name.charAt(0)) + ".";
            else
                this.fio = surname;
        else
            this.fio = "";
        this.time = timeInYear + "/" + timeAtAll;
    }

    public boolean find(String condition){
        if (Objects.equals(fio, condition))
            return true;
        else if (Objects.equals(title, condition))
            return true;
        else if (Objects.equals(type, condition))
            return true;
        else if (Objects.equals(surname, condition))
            return true;
        else if(Objects.equals(name, condition))
            return true;
        else if (Objects.equals(otchestvo, condition))
            return true;
        else if (Objects.equals(izdatel, condition))
            return true;
        else if (Objects.equals(timeInYear, condition))
            return true;
        else if (Objects.equals(time, condition))
            return true;
        else if (Objects.equals(timeAtAll, condition))
            return true;
        else if (Objects.equals(article, condition))
            return true;
        else if (Objects.equals(address, condition))
            return true;
        else if (Objects.equals(addressCompany, condition))
            return true;
        else if (Objects.equals(index, condition))
            return true;
        return false;
    }

    public short getDifference(Row row){
        short difference = 0;
        if (!Objects.equals(this.name, row.name))
            difference += 2;
        if (!Objects.equals(this.address, row.address))
            difference += 1;
        if (!Objects.equals(this.type, row.type))
            difference += 2;
        if (!Objects.equals(this.title, row.title))
            difference += 1;
        if (!Objects.equals(this.surname, row.surname))
            difference += 1;
        if (!Objects.equals(this.otchestvo, row.otchestvo))
            difference += 5;
        if (!Objects.equals(this.izdatel, row.izdatel))
            difference += 1;
        if (!Objects.equals(this.addressCompany, row.addressCompany))
            difference += 1;
        if (!Objects.equals(this.timeInYear, row.timeInYear))
            difference += 1;
        if (!Objects.equals(this.timeAtAll, row.timeAtAll))
            difference += 1;
        if (!Objects.equals(this.article, row.article))
            difference += 5;
        return difference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (this.title == null)
            this.title = title;
        else{
            if (title.charAt(0) != '\"')
                this.title = "\"" + title + "\"";
            else
                this.title = title;
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getTimeInYear() {
        return timeInYear;
    }

    public void setTimeInYear(String timeInYear) {
        this.timeInYear = timeInYear;
    }

    public String getTimeAtAll() {
        return timeAtAll;
    }

    public void setTimeAtAll(String timeAtAll) {
        this.timeAtAll = timeAtAll;
    }

    public String getAddressCompany() {
        return addressCompany;
    }

    public void setAddressCompany(String addressCompany) {
        this.addressCompany = addressCompany;
    }

    public String getIzdatel() {
        return izdatel;
    }

    public void setIzdatel(String izdatel) {
        this.izdatel = izdatel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOtchestvo() {
        return otchestvo;
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
