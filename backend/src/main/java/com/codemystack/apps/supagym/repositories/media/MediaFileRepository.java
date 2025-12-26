package com.codemystack.apps.supagym.repositories.media;

import com.codemystack.apps.supagym.models.media.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaFileRepository extends JpaRepository<MediaFile, UUID> {
}
