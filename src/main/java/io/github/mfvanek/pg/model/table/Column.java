/*
 * Copyright (c) 2019-2022. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.model.table;

import io.github.mfvanek.pg.utils.Validators;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * A representation of column in database table.
 *
 * @author Ivan Vakhrushev
 * @since 0.5.0
 */
@Immutable
public class Column implements ColumnNameAware, Comparable<Column> {

    private final String tableName;
    private final String columnName;
    private final boolean notNull;

    protected Column(@Nonnull final String tableName,
                     @Nonnull final String columnName,
                     final boolean notNull) {
        this.tableName = Validators.tableNameNotBlank(tableName);
        this.columnName = Validators.notBlank(columnName, "columnName");
        this.notNull = notNull;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public String getTableName() {
        return tableName;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public String getColumnName() {
        return columnName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNotNull() {
        return notNull;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Column.class.getSimpleName() + "{tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", notNull=" + notNull + '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Column)) {
            return false;
        }

        final Column that = (Column) other;
        return Objects.equals(tableName, that.tableName) &&
                Objects.equals(columnName, that.columnName) &&
                notNull == that.notNull;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return Objects.hash(tableName, columnName, notNull);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(@Nonnull final Column other) {
        Objects.requireNonNull(other, "other cannot be null");
        if (!tableName.equals(other.tableName)) {
            return tableName.compareTo(other.tableName);
        }
        if (!columnName.equals(other.columnName)) {
            return columnName.compareTo(other.columnName);
        }
        return Boolean.compare(notNull, other.notNull);
    }

    @Nonnull
    public static Column ofNotNull(@Nonnull final String tableName, @Nonnull final String columnName) {
        return new Column(tableName, columnName, true);
    }

    @Nonnull
    public static Column ofNullable(@Nonnull final String tableName, @Nonnull final String columnName) {
        return new Column(tableName, columnName, false);
    }
}
