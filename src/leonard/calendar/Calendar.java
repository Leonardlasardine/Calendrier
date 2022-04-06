package leonard.calendar;

import java.time.LocalDate;

public class Calendar {

    //Lists
    private final String[] listDay = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
    private final String[] listMonth = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
    private final int[] nbrDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    //Today
    private static int dayNbr ;
    private static String dayTxt;
    private static String monthTxt;
    private static int year;

    public Calendar() {
        setToday();

    }

    public static String today() {
        return dayTxt + " " + dayNbr + " " + monthTxt + " " + year;
    }

    //Get today date
    private void setToday() {
        LocalDate today = LocalDate.now();

        dayNbr = today.getDayOfMonth();
        int monthNbr = today.getMonthValue();
        year = today.getYear();

        bissextile();
        dayTxt = listDay[dayToText(dayNbr, monthNbr, year)];
        monthTxt = listMonth[monthNbr - 1];
    }

    private void bissextile() {

        if (checkYear(year)) {
            nbrDays[1] = 29;
        }
    }

    private boolean checkYear (int checkYear) {

        boolean bissextile = false;

        if (checkYear < 2000) {
            General.errorMessageText("La date doit être antérieur au 1er janvier 2000.", true);
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

    private int dayToText(int d,int m,int y) {
        int day = 5; //Le 1er janvier 2000 était un samedi (j = 5)
        int month = 1;
        int year = 2000;

        if (m > 12) {
            General.errorMessageText("Il ne peut pas avoir plus de douze mois dans une année", true);
        }
        if (d > nbrDays[m - 1]) {
            General.errorMessageText("Il n'y a pas plus de " + nbrDays[m - 1] + " jours en " + listMonth[m -1] + ".", true);
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
