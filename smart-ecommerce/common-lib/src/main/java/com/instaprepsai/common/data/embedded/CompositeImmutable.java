package com.instaprepsai.common.data.embedded;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor // required for hibernate but shouldn't be used otherwise
@MappedSuperclass
public abstract class CompositeImmutable {

    @Column(nullable = false, updatable = false)
    @Version
    @ReadOnlyProperty
    private Long version;

    @Column(nullable = false, updatable = false, name = "created_at")
    @CreatedDate
    @JsonIgnore
    private OffsetDateTime created =
            OffsetDateTime.now()
                    .truncatedTo(ChronoUnit.MICROS); // To match with column definition datetime(6)
}
