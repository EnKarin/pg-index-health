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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("fast")
class TableTest {

    @Test
    void getTableName() {
        final Table table = Table.of("t", 1L);
        assertThat(table.getTableName()).isEqualTo("t");
        assertThat(table.getTableSizeInBytes()).isEqualTo(1L);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void withInvalidValues() {
        assertThatThrownBy(() -> Table.of(null, 1L))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("tableName cannot be null");
        assertThatThrownBy(() -> Table.of("", 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("tableName cannot be blank");
        assertThatThrownBy(() -> Table.of("  ", 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("tableName cannot be blank");
        assertThatThrownBy(() -> Table.of("t", -1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("tableSizeInBytes cannot be less than zero");
    }

    @Test
    void testToString() {
        assertThat(Table.of("t", 2L))
                .hasToString("Table{tableName='t', tableSizeInBytes=2}");
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testEqualsAndHashCode() {
        final Table first = Table.of("t1", 22L);
        final Table theSame = Table.of("t1", 0L); // different size!
        final Table second = Table.of("t2", 30L);
        final Table third = Table.of("t3", 22L);

        assertThat(first.equals(null)).isFalse();
        //noinspection EqualsBetweenInconvertibleTypes
        assertThat(first.equals(BigDecimal.ZERO)).isFalse();

        // self
        assertThat(first)
                .isEqualTo(first)
                .hasSameHashCodeAs(first);

        // the same
        assertThat(theSame)
                .isEqualTo(first)
                .hasSameHashCodeAs(first);

        // others
        assertThat(second)
                .isNotEqualTo(first)
                .doesNotHaveSameHashCodeAs(first);

        assertThat(third)
                .isNotEqualTo(first)
                .doesNotHaveSameHashCodeAs(first)
                .isNotEqualTo(second)
                .doesNotHaveSameHashCodeAs(second);

        // another implementation of Table
        final TableWithBloat another = TableWithBloat.of("t1", 23L, 11L, 50);
        assertThat(another).isEqualTo(first);
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void equalsHashCodeShouldAdhereContracts() {
        EqualsVerifier.forClass(Table.class)
                .withIgnoredFields("tableSizeInBytes")
                .verify();
    }

    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    @Test
    void compareToTest() {
        final Table first = Table.of("t1", 22L);
        final Table theSame = Table.of("t1", 0L); // different size!
        final Table second = Table.of("t2", 30L);
        final Table third = Table.of("t3", 22L);

        assertThatThrownBy(() -> first.compareTo(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("other cannot be null");

        assertThat(first)
                .isEqualByComparingTo(first) // self
                .isEqualByComparingTo(theSame) // the same
                .isLessThan(second)
                .isLessThan(third);

        assertThat(second)
                .isGreaterThan(first)
                .isLessThan(third);

        assertThat(third)
                .isGreaterThan(first)
                .isGreaterThan(second);
    }
}
