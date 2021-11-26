package icpbl2.module2.controller;

import icpbl2.module2.domain.Movie;
import icpbl2.module2.service.ManageService;
import icpbl2.module2.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ManageController {

    @Autowired
    private final ManageService manageService;
    @Autowired
    private final MovieService movieService;

    @GetMapping("/movie/new")
    public String createForm(Model model) {
        log.info("movie create form");
        model.addAttribute("movieForm", new MovieForm());
        return "/movie/createMovieForm";
    }

    @PostMapping("/movie/new")
    public String createM(MovieForm movieForm) {
        log.info("movie create");
        Movie movie = new Movie(movieForm.getName(), 20000);
        movieService.join(movie);
        return "redirect:/";
    }

    @GetMapping("/movie/list")
    public String listM(Model model) {
        List<Movie> allMovie = movieService.findAllMovie();

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

        List<MovieForm> moviest = new ArrayList<>();
        int idx = 0;
        for (Movie movie : allMovie) {
            moviest.add(new MovieForm(movie.getMovie_name(), movie.getNumOfView(), ++idx));
        }

        model.addAttribute("movies", moviest);
        log.info("call movie list");
        return "/movie/movieList";
    }
}
