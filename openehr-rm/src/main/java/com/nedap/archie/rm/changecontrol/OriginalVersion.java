package com.nedap.archie.rm.changecontrol;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.generic.Attestation;
import com.nedap.archie.rm.support.identification.ObjectVersionId;


import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 08/07/16.
 */
@XmlType(name="ORIGINAL_VERSION", propOrder = {
        "uid",
        "data",
        "precedingVersionUid",
        "otherInputVersionUids",
        "attestations",
        "lifecycleState"

})
public class OriginalVersion<Type> extends Version<Type> {

    private ObjectVersionId uid;
    @Nullable
    @XmlElement(name="preceding_version_uid")
    private ObjectVersionId precedingVersionUid;
    @Nullable
    @XmlElement(name="other_input_version_uids")
    private List<ObjectVersionId> otherInputVersionUids = new ArrayList<>();

    @XmlElement(name="lifecycle_state")
    private DvCodedText lifecycleState;
    @Nullable
    private List<Attestation> attestations = new ArrayList<>();
    @Nullable
    private Type data;


    @Override
    public ObjectVersionId getUid() {
        return uid;
    }

    @Override
    public ObjectVersionId getPrecedingVersionUid() {
        return precedingVersionUid;
    }

    public void setUid(ObjectVersionId uid) {
        this.uid = uid;
    }

    public void setPrecedingVersionUid(ObjectVersionId precedingVersionUid) {
        this.precedingVersionUid = precedingVersionUid;
    }

    @Nullable
    public List<ObjectVersionId> getOtherInputVersionUids() {
        return otherInputVersionUids;
    }

    @JsonAlias({"other_input_version_ids"})
    public void setOtherInputVersionUids(@Nullable List<ObjectVersionId> otherInputVersionUids) {
        this.otherInputVersionUids = otherInputVersionUids;
    }

    @Override
    public DvCodedText getLifecycleState() {
        return lifecycleState;
    }

    @Override
    public String getCanonicalForm() {
        return null;//TODO no idea what this should do
    }

    @Override
    public boolean isBranch() {
        return false;
    }

    public void setLifecycleState(DvCodedText lifecycleState) {
        this.lifecycleState = lifecycleState;
    }

    public List<Attestation> getAttestations() {
        return attestations;
    }

    public void setAttestations(List<Attestation> attestations) {
        this.attestations = attestations;
    }

    @Override
    public Type getData() {
        return data;
    }

    public void setData(Type data) {
        this.data = data;
    }
}
