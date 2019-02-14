package com.nedap.archie.rm.generic;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 08/07/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AUDIT_DETAILS", propOrder = {
        "systemId",
        "committer",
        "timeCommitted",
        "changeType",
        "description"
})
public class AuditDetails extends RMObject {

    @XmlElement(name="system_id")
    private String systemId;
    @XmlElement(name="time_committed")
    private DvDateTime timeCommitted;
    @XmlElement(name="change_type")
    private DvCodedText changeType;
    @Nullable
    private DvText description;
    private PartyProxy committer;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public DvDateTime getTimeCommitted() {
        return timeCommitted;
    }

    @JsonAlias({"time_commited"})
    public void setTimeCommitted(DvDateTime timeCommitted) {
        this.timeCommitted = timeCommitted;
    }

    public DvCodedText getChangeType() {
        return changeType;
    }

    public void setChangeType(DvCodedText changeType) {
        this.changeType = changeType;
    }

    @Nullable
    public DvText getDescription() {
        return description;
    }

    public void setDescription(@Nullable DvText description) {
        this.description = description;
    }

    public PartyProxy getCommitter() {
        return committer;
    }

    public void setCommitter(PartyProxy committer) {
        this.committer = committer;
    }
}
