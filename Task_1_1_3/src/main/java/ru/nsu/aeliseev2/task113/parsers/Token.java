package ru.nsu.aeliseev2.task113.parsers;

/**
 * A token consisting of some kind of data and its position in the string.
 *
 * @param data     The token data.
 * @param position The index of the first character of this token.
 * @param <DataT>   The type of the data
 */
public record Token<DataT>(DataT data, int position) {
}
