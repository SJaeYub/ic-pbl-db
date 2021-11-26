package icpbl2.module2;

import icpbl2.module2.domain.Movie;
import icpbl2.module2.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestGrammer {

    @Autowired
    MovieService movieService;

    @Test
    public void 영화_매출액_정렬() {
        Movie testM1 = new Movie("testM1", 20000);
        Movie testM2 = new Movie("testM2", 20000);
        Movie testM3 = new Movie("testM3", 20000);

        testM1.setNumOfView(1500000);
        testM2.setNumOfView(2000000);
        testM3.setNumOfView(1700000);

        movieService.join(testM1);
        movieService.join(testM2);
        movieService.join(testM3);

        List<Movie> allMovie = movieService.findAllMovie();
        for (Movie movie : allMovie) {
            System.out.println("movie.getMovie_name() = " + movie.getMovie_name());
        }

        System.out.println("======================================");
        Collections.sort(allMovie, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                if (o1.getNumOfView() > o2.getNumOfView()) {
                    return -1;
                } else if (o1.getNumOfView() < o2.getNumOfView()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        for (Movie movie : allMovie) {
            System.out.println("movie.getMovie_name() = " + movie.getMovie_name());
        }
    }

}
