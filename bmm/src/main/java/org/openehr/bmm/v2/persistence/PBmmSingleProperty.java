package org.openehr.bmm.v2.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmSimpleType;
import org.openehr.bmm.core.BmmUnitaryProperty;
import org.openehr.bmm.v2.validation.converters.BmmClassProcessor;

public final class PBmmSingleProperty extends PBmmProperty<PBmmSimpleType> {

    private String type;

    /**
     * The P_BMM spec has quite oddly defined typing: there's a simple string form, and two structural forms. For most
     * typing, the simple string is enough. When this is not enough, there's an object variant called type_def.
     * So far, nothing strange going on. however, now there's a third variant:
     *
     * There's type_ref, which should be a computed (non-serialized) version. However, for terminology code constraints
     * it has to be a serialized version of type_ref. To distinguish between the computed typeRef (so, in our case, a
     * method that returns without side-effects) and the serialized type ref, a serialized type ref has been added.
     * It maps to the json property type_ref.
     */
    private PBmmSimpleType serializedTypeRef;

    public PBmmSingleProperty() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    @JsonIgnore
    public PBmmSimpleType getTypeRef() {
        if (serializedTypeRef != null) {
            return serializedTypeRef;
        } else if (getTypeDef() == null) {
            return new PBmmSimpleType(type);
        }
        return getTypeDef();
    }

    @Override
    public BmmUnitaryProperty createBmmProperty(BmmClassProcessor classProcessor, BmmClass bmmClass) {
        PBmmSimpleType typeRef = getTypeRef();
        if (typeRef != null) {
            BmmSimpleType bmmType = typeRef.createBmmType(classProcessor, bmmClass);
            BmmUnitaryProperty bmmProperty = new BmmUnitaryProperty(getName(), bmmType, getDocumentation(), nullToFalse(isMandatory()), nullToFalse(isComputed()));
            populateImBooleans(bmmProperty);
            return bmmProperty;
        }
        throw new RuntimeException("BmmTypeCreate failed for property " + getName() + " in class " + bmmClass.getName());
    }

    @JsonProperty("type_ref")
    public PBmmSimpleType getSerializedTypeRef() {
        return serializedTypeRef;
    }

    public void setSerializedTypeRef(PBmmSimpleType serializedTypeRef) {
        this.serializedTypeRef = serializedTypeRef;
    }
}
