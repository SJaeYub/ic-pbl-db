package icpbl2.module2.repository;

import icpbl2.module2.domain.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MovieRepository {

    private final EntityManager entityManager;

    public void save(Movie movie) {
        entityManager.persist(movie);
    }

    public Movie findOne(Long id) {
        return entityManager.find(Movie.class, id);
    }

    public List<Movie> findByMovieId(Long id) {
        return entityManager.createQuery("select m from Movie as m where m.movie_id =: mid", Movie.class)
                .setParameter("mid", id)
                .getResultList();
    }

    public List<Movie> findAllMovie() {
        return entityManager.createQuery("select m from Movie as m", Movie.class)
                .getResultList();
    }



    public Movie searchOne(String name) {
        log.info("movie_name in searchOne ={}", name);
        return entityManager.createQuery("select m from Movie as m where m.movie_name=:m_name", Movie.class)
                .setParameter("m_name", name)
                .getSingleResult();
    }

    public void remove(Movie movie) {
        entityManager.remove(movie);
        return;
    }
}
