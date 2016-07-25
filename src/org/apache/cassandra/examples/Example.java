/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.cassandra.examples;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import org.apache.cassandra.io.sstable.*;

public class Example
{
    public static void main(String[] args) throws IOException
    {
        final String KS = "cql_keyspace5";
        final String TABLE = "table5";

        final String schema = "CREATE TABLE " + KS + "." + TABLE + " ("
                              + "  k int,"
                              + "  c1 int,"
                              + "  c2 int,"
                              + "  v text,"
                              + "  PRIMARY KEY (k, c1, c2)"
                              + ")";

        File tempdir = Files.createTempDir();
        File dataDir = new File(tempdir.getAbsolutePath() + File.separator + KS + File.separator + TABLE);
        assert dataDir.mkdirs();

        CQLSSTableWriter writer = CQLSSTableWriter.builder()
                                                  .inDirectory(dataDir)
                                                  .forTable(schema)
                                                  .using("INSERT INTO " + KS + "." + TABLE + " (k, c1, c2, v) " +
                                                         "VALUES (?, ?, ?, ?)")
                                                  .build();

        writer.addRow(5, 5, 5, "5");
        writer.close();

        System.out.println("tempdir = " + tempdir);
        System.out.println("dataDir = " + dataDir);

    }
}
