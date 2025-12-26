package com.codemystack.apps.supagym.models.media;

import com.codemystack.apps.supagym.models.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "media_files")
@Getter
@Setter
public class MediaFile extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String filename;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String filepath;

    @Column(name = "file_type", length = 50)
    private String fileType; // video, image, document

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "related_entity", length = 50)
    private String relatedEntity; // exercise, user, inventory, etc.

    @Column(name = "related_entity_id", columnDefinition = "UUID")
    private UUID relatedEntityId;

    @Column(name = "uploaded_by", columnDefinition = "UUID")
    private UUID uploadedBy;
}
