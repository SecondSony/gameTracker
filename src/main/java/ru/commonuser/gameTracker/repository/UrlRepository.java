package ru.commonuser.gameTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.commonuser.gameTracker.entity.Url;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findFirstByName(String url);
    Optional<Url> findFirstByUrl(String url);
    Optional<Url> findByName(String url);
    Optional<Url> findByUrl(String url);
    Optional<Url> findFirstById(Long Id);
}
