package com.nedap.archie.adl14;

import com.google.common.collect.Lists;
import com.nedap.archie.adl14.log.ADL2ConversionLog;
import com.nedap.archie.adl14.log.ADL2ConversionRunLog;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CObject;
import org.junit.Test;
import org.openehr.referencemodels.BuiltinReferenceModels;

import java.io.InputStream;

import static junit.framework.TestCase.assertEquals;

public class PreviousLogConversionTest {

    @Test
    public void applyConsistentConversion() throws Exception {
        ADL2ConversionRunLog log = null;
        try(InputStream stream = getClass().getResourceAsStream("openehr-EHR-COMPOSITION.review.v1.adl")) {
            ADL2ConversionResultList result = new ADL14Converter().convert(
                    BuiltinReferenceModels.getMetaModels(),
                    Lists.newArrayList(new ADL14Parser(BuiltinReferenceModels.getMetaModels()).parse(stream, ConversionConfigForTest.getConfig())),
                    ConversionConfigForTest.getConfig(), null);
            log = result.getConversionLog();
        }

        assertEquals(1, log.getConvertedArchetypes().size());

        try(InputStream stream = getClass().getResourceAsStream("openEHR-EHR-COMPOSITION.review.v1.modified.adl")) {
            ADL2ConversionResultList result = new ADL14Converter().convert(
                    BuiltinReferenceModels.getMetaModels(),
                    Lists.newArrayList(new ADL14Parser(BuiltinReferenceModels.getMetaModels()).parse(stream, ConversionConfigForTest.getConfig())),
                    ConversionConfigForTest.getConfig(), log);
            CAttribute attribute = result.getConversionResults().get(0).getArchetype().itemAtPath("/category");
            assertEquals(2, attribute.getChildren().size());
            CObject dvText = attribute.getChildren().get(0);
            CObject dvCodedText = attribute.getChildren().get(1);
            assertEquals("DV_CODED_TEXT", dvCodedText.getRmTypeName());
            assertEquals("DV_TEXT", dvText.getRmTypeName());
            assertEquals("id3", dvCodedText.getNodeId());
            assertEquals("id4", dvText.getNodeId());

        }
    }
}
