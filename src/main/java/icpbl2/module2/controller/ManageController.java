package icpbl2.module2.controller;

import icpbl2.module2.domain.Cinema;
import icpbl2.module2.domain.Customer;
import icpbl2.module2.domain.Movie;
import icpbl2.module2.domain.ReservedMovie;
import icpbl2.module2.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class ManageController {

    private final CustomerService customerService;
    private final ManageService manageService;
    private final MovieService movieService;
    private final PlayingMovieService playingMovieService;
    private final ReserveService reserveService;

    @GetMapping
    public String showForm() {
        log.info("shoForm_admin");
        return "admin/admin";
    }

    @GetMapping("/admin_MC")
    public String showMc(Model model) {
        log.info("showMc_admin");
        List<Movie> allMovie = movieService.findAllMovie();

        List<MovieForm> moviest = sortByRank(allMovie);

        model.addAttribute("list", moviest);
        return "admin/admin_MC";
    }

    @PostMapping("/admin_MC")
    public String delMovie(@RequestParam("del_movie") String name, Model model) {
        log.info("delMovie");
        log.info("movie_name in controller={}", name);

        Movie movie = movieService.findOne(name);
        movieService.remove(movie);

        List<Movie> allMovie = movieService.findAllMovie();
        List<MovieForm> moviest = sortByRank(allMovie);

        model.addAttribute("list", moviest);
        log.info("complete deleting movie");
        return "admin/admin_MC";
    }

    @GetMapping("/admin_TC")
    public String showTc(Model model) {
        log.info("showMc_admin");

        List<Cinema> cinemas = manageService.searchAllCinema();
        model.addAttribute("list", cinemas);
        return "admin/admin_TC";
    }

    @GetMapping("/admin_check")
    public String showCheck(Model model) {
        log.info("showMc_admin");

        List<ReservedMovie> reservedMovieList = reserveService.findAllRM();
        model.addAttribute("list", reservedMovieList);
        model.addAttribute("customer", new CustomerForm());
        return "admin/admin_check";
    }

    @PostMapping("/admin_check")
    public String searchCustomer(CustomerForm customerForm, Model model) {
        log.info("searchCustomer in check controller");
        log.info("customerForm.getUser_id()={}", customerForm.getUser_id());

        Customer customer = customerService.findByNickname(customerForm.getUser_id());
        List<ReservedMovie> reservedMovieList = reserveService.list_RM(customer.getId());
        model.addAttribute("list", reservedMovieList);

        log.info("reservedMovieList={}", reservedMovieList);
        return "admin/admin_check";
    }

    @GetMapping("/admin_addMovie")
    public String addMovie_form(Model model) {
        log.info("addMovie_form");
        model.addAttribute("form", new MovieForm());
        return "admin/admin_movie_join";
    }

    @PostMapping("/admin_addMovie")
    public String addMovie(@ModelAttribute MovieForm movieForm, Model model) {
        log.info("addMovie");

        Movie movie = new Movie(movieForm.getName(), 20000);
        movieService.join(movie);

        log.info("complete join Movie");

        List<Movie> allMovie = movieService.findAllMovie();
        List<MovieForm> moviest = sortByRank(allMovie);
        model.addAttribute("list", moviest);

        return "admin/admin_MC";
    }

    private List<MovieForm> sortByRank(List<Movie> allMovie) {
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
        return moviest;
    }


//    @GetMapping("/movie/new")
//    public String createForm(Model model) {
//        log.info("movie create form");
//        model.addAttribute("movieForm", new MovieForm());
//        return "/movie/createMovieForm";
//    }
//
//    @PostMapping("/movie/new")
//    public String createM(MovieForm movieForm) {
//        log.info("movie create");
//        Movie movie = new Movie(movieForm.getName(), 20000);
//        movieService.join(movie);
//        return "redirect:/";
//    }
//
//    @GetMapping("/movie/list")
//    public String listM(Model model) {
//        List<Movie> allMovie = movieService.findAllMovie();
//
//        Collections.sort(allMovie, new Comparator<Movie>() {
//            @Override
//            public int compare(Movie o1, Movie o2) {
//                if (o1.getNumOfView() > o2.getNumOfView()) {
//                    return -1;
//                } else if (o1.getNumOfView() < o2.getNumOfView()) {
//                    return 1;
//                } else {
//                    return 0;
//                }
//            }
//        });
//
//        List<MovieForm> moviest = new ArrayList<>();
//        int idx = 0;
//        for (Movie movie : allMovie) {
//            moviest.add(new MovieForm(movie.getMovie_name(), movie.getNumOfView(), ++idx));
//        }
//
//        model.addAttribute("movies", moviest);
//        log.info("call movie list");
//        return "/movie/movieList";
//    }
}
