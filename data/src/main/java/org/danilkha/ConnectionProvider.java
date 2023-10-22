package org.danilkha;

import java.sql.Connection;

public interface ConnectionProvider {
    Connection provide();
}
