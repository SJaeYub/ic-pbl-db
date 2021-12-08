package icpbl2.module2.repository;

import icpbl2.module2.domain.PlayingMovie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
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

    public PlayingMovie findOneForReserve(String mName, String cName, int tNum) {

        PlayingMovie query = em.createQuery("select pm from PlayingMovie as pm where pm.playingMovie_name=:m_name and pm.cinema.cinema_name=:c_name and pm.theater.theater_num=:t_num", PlayingMovie.class)
                .setParameter("m_name", mName)
                .setParameter("c_name", cName)
                .setParameter("t_num", tNum)
                .getSingleResult();

        log.info("pm.playingMovie_name={}", query.getPlayingMovie_name());
        log.info("pm.cinema.cinema_name={}", query.getCinema().getCinema_name());
        log.info("pm.theater.theater_num={}", query.getTheater().getTheater_num());
        return query;
    }
}
