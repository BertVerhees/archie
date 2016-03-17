package com.nedap.archie.rm.datavalues.quantity.datetime;

import com.nedap.archie.rm.datavalues.SingleValuedDataValue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

/**
 * TODO: implement java.time.Temporal for this object?
 * Created by pieter.bos on 04/11/15.
 */
public class DvDateTime extends DvTemporal<Long> implements SingleValuedDataValue<TemporalAccessor> {

    private TemporalAccessor value;

    @Override
    public TemporalAccessor getValue() {
        return value;
    }

    @Override
    public void setValue(TemporalAccessor value) {
        this.value = value;
    }

    @Override
    public Long getMagnitude() {
        return value == null ? null : (long) ZonedDateTime.from(value).toEpochSecond();
    }

    @Override
    public void setMagnitude(Long magnitude) {
        if(magnitude == null) {
            value = null;
        } else {
            value = LocalDateTime.ofEpochSecond(magnitude, 0, ZoneOffset.UTC);
        }
    }
}