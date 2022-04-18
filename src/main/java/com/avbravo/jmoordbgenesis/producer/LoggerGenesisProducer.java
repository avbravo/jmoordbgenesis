package com.avbravo.jmoordbgenesis.producer;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import java.util.logging.Logger;

/**
 *
 * @author hantsy
 */
@Dependent
public class LoggerGenesisProducer {

    @Produces
    @Loggerenesis
    public Logger getLogger(InjectionPoint p) {
        return Logger.getLogger(p.getMember().getDeclaringClass().getName());
    }

}
