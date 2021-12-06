package icpbl2.module2.service;

import icpbl2.module2.domain.Movie;
import icpbl2.module2.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    /**
     * 영화 등록 (관리자 모드)
     * 테스트 완료
     */
    @Transactional
    public Long join(Movie movie) {
        validateDuplicateMovie(movie);
        movieRepository.save(movie);
        return movie.getMovie_id();
    }

    @Transactional
    public void remove(Movie movie) {
        movieRepository.remove(movie);
    }

    private void validateDuplicateMovie(Movie movie) {
        List<Movie> findMovies = movieRepository.findByMovieId(movie.getMovie_id());
        if (!findMovies.isEmpty()) {
            throw new IllegalStateException("이미 등록된 영화 정보입니다.");
        }
    }

    public Movie findOne(String name) {
        return movieRepository.searchOne(name);
    }

    public List<Movie> findAllMovie() {
        return movieRepository.findAllMovie();
    }
}
