/*
 * Copyright (c) 2019-2022. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.checks.predicates;

import io.github.mfvanek.pg.utils.Locales;
import io.github.mfvanek.pg.utils.Validators;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/**
 * Base class for filters by name.
 *
 * @author Ivan Vakhrushev
 * @since 0.6.0
 */
abstract class AbstractFilterByName {

    protected final Set<String> exclusions;

    protected AbstractFilterByName(@Nonnull final Collection<String> exclusions) {
        this.exclusions = Collections.unmodifiableSet(
                Objects.requireNonNull(exclusions, "exclusions cannot be null").stream()
                        .map(s -> s.toLowerCase(Locales.DEFAULT))
                        .collect(Collectors.toSet()));
    }

    protected AbstractFilterByName(@Nonnull final String objectName) {
        this(Collections.singleton(Validators.notBlank(objectName, "objectName")));
    }
}
