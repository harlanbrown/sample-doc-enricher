package com.nuxeo.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.ecm.core.io.marshallers.json.AbstractJsonWriterTest;
import org.nuxeo.ecm.core.io.marshallers.json.document.DocumentModelJsonWriter;
import org.nuxeo.ecm.core.io.marshallers.json.JsonAssert;
import org.nuxeo.ecm.core.io.registry.context.RenderingContext.CtxBuilder;
import javax.inject.Inject;
import java.io.IOException;

@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy("org.nuxeo.sample.sample-doc-enricher")
public class TestSampleDocEnricher extends AbstractJsonWriterTest.Local<DocumentModelJsonWriter, DocumentModel> {

    public TestSampleDocEnricher() {
        super(DocumentModelJsonWriter.class, DocumentModel.class);
    }

    @Inject
    protected CoreSession session;

    @Test
    public void testShouldEnrichSampleSuccessfully() throws IOException {
        DocumentModel document = session.createDocumentModel("/", "test", "File");
        document = session.createDocument(document);
        JsonAssert json = jsonAssert(document, CtxBuilder.enrichDoc("sample").get());
    }

    @Test
    public void testCustomDoc() throws IOException {
        DocumentModel referencedDocument = session.createDocumentModel("/", "ref", "ReferencedDocument");
        referencedDocument.setPropertyValue("rd:name", "referenced document");
        referencedDocument = session.createDocument(referencedDocument);

        DocumentModel document = session.createDocumentModel("/", "test", "CustomDocument");
        document.setPropertyValue("dc:description", "custom document");
        document.setPropertyValue("cd:referencedDocument", referencedDocument.getId());
        document = session.createDocument(document);

        JsonAssert json = jsonAssert(document, CtxBuilder.enrichDoc("sample2").get());
        System.out.println(json);
    }
}

