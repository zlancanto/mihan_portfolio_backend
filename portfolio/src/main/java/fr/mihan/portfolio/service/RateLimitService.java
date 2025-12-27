package fr.mihan.portfolio.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimitService {

    private final Cache<String, Bucket> buckets = Caffeine.newBuilder()
            .expireAfterAccess(2, TimeUnit.HOURS) // Supprime l'IP si aucune requête après 2h
            .maximumSize(1000) // max 1000 IPs en mémoire
            .build();

    public Bucket resolveBucket(String ip) {
        return buckets.get(ip, key -> newBucket());
    }

    private Bucket newBucket() {
        /*.
         * 3 messages max par heure
         * On récupère 1 jeton toutes les 20 minutes
         */
        return Bucket.builder()
                .addLimit(limit -> limit
                        .capacity(3)
                        .refillIntervally(3, Duration.ofHours(1)))
                .build();
    }
}
