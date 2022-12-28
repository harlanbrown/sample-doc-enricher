package org.nuxeo.sample.enrichers;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;
import static org.nuxeo.ecm.core.io.registry.reflect.Priorities.REFERENCE;

import java.io.IOException;

import org.nuxeo.ecm.core.api.CloseableCoreSession;
import org.nuxeo.ecm.core.api.CoreInstance;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.io.marshallers.json.enrichers.AbstractJsonEnricher;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;

import com.fasterxml.jackson.core.JsonGenerator;

@Setup(mode = SINGLETON, priority = REFERENCE)
public class OtherSampleDocEnricher extends AbstractJsonEnricher<DocumentModel> {

  private static final String NAME = "sample2";

  public OtherSampleDocEnricher() {
    super(NAME);
  }

  @Override
  public void write(JsonGenerator jg, DocumentModel doc) throws IOException {
    String ref = (String) doc.getPropertyValue("cd:referencedDocument");
    DocumentModel referencedDoc = doc.getCoreSession().getDocument(new IdRef(ref));
    String name = (String) referencedDoc.getPropertyValue("rd:name");

    jg.writeFieldName(NAME);
    jg.writeString(name);
  }

}
