/*
 * Copyright 2008 the original author or authors.
 * Copyright 2005 Sun Microsystems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rioproject.watch;

/**
 * Used to archive {@link org.rioproject.watch.Calculable} entries
 * added to a ${@link org.rioproject.watch.WatchDataSource}.
 *
 * @Deprecated Use {@link WatchDataReplicator} instead
 */
public interface Archivable extends WatchDataReplicator {

    /**
     * Archive a record from the WatchDataSource history
     * 
     * @param calculable the Calculable record to archive
     */
    void archive(Calculable calculable);
}