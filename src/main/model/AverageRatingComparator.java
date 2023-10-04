package model;


import java.util.Comparator;

public class AverageRatingComparator implements Comparator<Cafe> {

    @Override
    public int compare(Cafe cafe1, Cafe cafe2) {
        double averageRating1 = cafe1.calculateAverageRating();
        double averageRating2 = cafe2.calculateAverageRating();

        return Double.compare(averageRating2, averageRating1);
    }
}
