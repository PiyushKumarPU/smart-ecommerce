package com.instaprepsai.common.data.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.Data;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Data
@OptimisticLocking
@MappedSuperclass
public abstract class Versioned {

  @Version
  @ReadOnlyProperty
  @Column(name = "version")
  private Long version;

  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  /**
   * Lifecycle callback to set the `createdAt` and `updatedAt` timestamps before persisting.
   */
  @PrePersist
  public void onPrePersist() {
    OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS);
    this.createdAt = now;
    this.updatedAt = now;
  }

  /**
   * Lifecycle callback to update the `updatedAt` timestamp before updating.
   */
  @PreUpdate
  public void onPreUpdate() {
    this.updatedAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS);
  }
}