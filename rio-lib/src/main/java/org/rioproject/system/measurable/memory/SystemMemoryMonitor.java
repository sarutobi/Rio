/*
 * Copyright 2008 to the original author or authors.
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
package org.rioproject.system.measurable.memory;

import org.rioproject.system.measurable.MeasurableMonitor;
import org.rioproject.system.measurable.SigarHelper;
import org.rioproject.watch.ThresholdValues;

/**
 * The <code>SystemMemoryMonitor</code> object provides feedback information to
 * the {@link SystemMemory} object, providing memory usage information for the
 * system using SIGAR. If SIGAR is not available, this utility returns a -1 for
 * system memory utilization.
 *
 * <p><b>Note:</b>
 * <a href="http://www.hyperic.com/products/sigar.html">Hyperic SIGAR</a>
 * is licensed under the GPL with a FLOSS license exception, allowing it to be
 * included with the Rio Apache License v2 distribution. If for some reason the
 * GPL cannot be used with your distribution of Rio,
 * remove the <tt>RIO_HOME/lib/hyperic</tt> directory.
 *
 * @author Dennis Reedy
 */
public class SystemMemoryMonitor implements MeasurableMonitor<SystemMemoryUtilization> {
    private String id;
    private ThresholdValues tVals;
    private SigarHelper sigar;
    private static double KB = 1024;
    private static double MB = Math.pow(KB, 2);

    public SystemMemoryMonitor() {
        sigar = SigarHelper.getInstance();
    }

    public void terminate() {
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setThresholdValues(ThresholdValues tVals) {
        this.tVals = tVals;
    }

    public SystemMemoryUtilization getMeasuredResource() {
        SystemMemoryUtilization smu;
        if(sigar==null) {
            smu = new SystemMemoryUtilization(id, tVals);
        } else {
            long total = sigar.getTotalSystemMemory();
            long free = sigar.getFreeSystemMemory();
            long used = sigar.getUsedSystemMemory();

            double utilization = (double)used/(double)total;
            /*
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);

            long ram = sigar.getRam();
            double d = ((double)total)/MB;
            double e = ((double)used)/MB;
            double f = ((double)ram)/KB;
            double g = ((double)free)/MB;
            String usedPerc = nf.format(sigar.getUsedSystemMemoryPercent());
            String freePerc = nf.format(sigar.getFreeSystemMemoryPercent());
            System.out.println("    Total:       "+nf.format(d)+" MB");
            System.out.println("    Used:        "+nf.format(e)+" MB, "+usedPerc+" %");
            System.out.println("    Free:        "+nf.format(g)+" MB, "+freePerc+" %");
            System.out.println("    RAM:         "+nf.format(f)+" GB");
            System.out.println("    Utilization: "+utilization+" %");
            System.out.println("--------------------");
            */

            smu = new SystemMemoryUtilization(id,
                                              utilization,
                                              ((double)total)/MB,
                                              ((double)free)/MB,
                                              ((double)used)/MB,
                                              sigar.getFreeSystemMemoryPercent(),
                                              sigar.getUsedSystemMemoryPercent(),
                                              sigar.getRam(),
                                              tVals);
        }
        return smu;
    }
}
