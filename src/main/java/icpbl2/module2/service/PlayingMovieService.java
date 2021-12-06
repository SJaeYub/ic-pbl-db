package icpbl2.module2.service;

import icpbl2.module2.domain.PlayingMovie;
import icpbl2.module2.repository.PlayingMovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayingMovieService {

    private final PlayingMovieRepository playingMovieRepository;

    @Transactional
    public Long join(PlayingMovie playingMovie) {
        validateDuplicatePM(playingMovie);
        playingMovieRepository.save(playingMovie);
        return playingMovie.getPlayingMovie_id();
    }

    private void validateDuplicatePM(PlayingMovie playingMovie) {
        List<PlayingMovie> findPM = playingMovieRepository.findById(playingMovie.getPlayingMovie_id());
        if (!findPM.isEmpty()) {
            throw new IllegalStateException("이미 등록된 상영정보 입니다.");
        }
    }

    public List<PlayingMovie> findPM() {
        return playingMovieRepository.findAll();
    }

    /**
     * id로 상영 영화 정보 조회
     * 테스트 완료
     * @param id
     * @return
     */
    public PlayingMovie findOne(Long id) {
        return playingMovieRepository.findOne(id);
    }
}
