package icpbl2.module2.repository;

import icpbl2.module2.domain.PlayingMovie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlayingMovieRepository {

    private final EntityManager em;

    public void save(PlayingMovie playingMovie) {
        em.persist(playingMovie);
    }

    public PlayingMovie findOne(Long id) {
        return em.find(PlayingMovie.class, id);
    }

    public List<PlayingMovie> findAll() {
        return em.createQuery("select pm from PlayingMovie as pm", PlayingMovie.class)
                .getResultList();
    }

    public List<PlayingMovie> findById(Long id) {
        return em.createQuery("select pm from PlayingMovie as pm where pm.playingMovie_id=:pmid", PlayingMovie.class)
                .setParameter("pmid", id)
                .getResultList();
    }
}
