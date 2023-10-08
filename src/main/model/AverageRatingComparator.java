package model;


import java.util.Comparator;

public class AverageRatingComparator implements Comparator<Cafe> {

    @Override
    // EFFECTS: compares the average rating of two cafes, returns -1 if the first cafe has a lower average rating than
    // the second, 0 if their average ratings are equal, and 1 if the first cafe has a higher rating
    public int compare(Cafe cafe1, Cafe cafe2) {
        double averageRating1 = cafe1.calculateAverageRating();
        double averageRating2 = cafe2.calculateAverageRating();

        return Double.compare(averageRating2, averageRating1);
    }
}
