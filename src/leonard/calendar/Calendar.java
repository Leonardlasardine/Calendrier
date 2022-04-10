package leonard.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Calendar {

    //Lists
    private final String[] listDay = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
    private final String[] listMonth = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
    private final int[] nbrDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    //Today
    private int dayNbr;
    private String dayTxt;
    private int monthNbr ;
    private String monthTxt;
    private int year;

    public Calendar() {
        setToday();
    }

    public String today() {
        return dayTxt + " " + dayNbr + " " + monthTxt + " " + year;
    }

    //Get today date
    private void setToday() {
        LocalDate today = LocalDate.now();

        dayNbr = today.getDayOfMonth();
        monthNbr = today.getMonthValue();
        year = today.getYear();

        bissextile();
        dayTxt = listDay[dayToText(dayNbr, monthNbr, year)];
        monthTxt = listMonth[monthNbr - 1];
    }
    public String MonthToTxt(int number) {
        return listMonth[number - 1];
    }

    public int getMonthNbr() {
        return monthNbr;
    }

    public int getDayNbr() {
        return dayNbr;
    }

    public int getYear() {
        return year;
    }

    public int[] array(int m, int y) {
        ArrayList<Integer> boxes = new ArrayList<>(boxesBefore(m, y));

        for (int i = 0; i < nbrDays[m - 1]; i++) {
            boxes.add(i +1);
        }

        //Cases du mois d'après
        int j = 1;
        while (boxes.size() < 42) {
            boxes.add(j);
            j++;
        }

        int[] list = new int[boxes.size()];

        //Conversion en tableau fini
        for(int i = 0; i < boxes.size(); i++) {
            list[i] = boxes.get(i);
        }

        return list;
    }

    private ArrayList<Integer> boxesBefore(int m, int y) {
        //Cases du mois d'avant
        int dayFirst = dayToText(1, m, y);

        //Pour janvier
        int i;
        if (m != 1) { //1 ou 0
            i = nbrDays[m -2] + 1;
        } else {
            i = nbrDays[11] + 1;
        }

        ArrayList<Integer> daysBefore = new ArrayList<>();

        while (dayFirst > 0) {
            dayFirst -= 1;
            i--;
            daysBefore.add(i);
        }
        Collections.reverse(daysBefore);
        return daysBefore;
    }

    private void bissextile() {

        if (checkYear(year)) {
            nbrDays[1] = 29;
        }
    }

    private boolean checkYear (int checkYear) {

        boolean bissextile = false;

        if (checkYear < 2000) {
            General.errorMessageText("La date doit être antérieur au 1er janvier 2000.", 1);
        }

        if (checkYear%4 == 0) {
            if (checkYear%100 == 0) {
                if (checkYear%400 == 0) {
                    bissextile = true;
                }
            } else {
                bissextile = true;
            }
        }
        return bissextile;
    }

    private int dayToText(int d, int m, int y) {
        int day = 5; //Le 1er janvier 2000 était un samedi (j = 5)
        int month = 1;
        int year = 2000;

        if (m > 12) {
            General.errorMessageText("Il ne peut pas avoir plus de douze mois dans une année", 1);
        }
        if (d > nbrDays[m - 1]) {
            General.errorMessageText("Il n'y a pas plus de " + nbrDays[m - 1] + " jours en " + listMonth[m -1] + ".", 1);
        }

        while (year < y) {
            if (checkYear(year)) { //Nombre de jours dans l'année
                day += 366;
            } else {
                day += 365;
            }
            year += 1;
        }

        while (month<m) {
            day += nbrDays[month - 1];
            month += 1;
        }

        day += d - 1;
        return day % 7;
    }
}
