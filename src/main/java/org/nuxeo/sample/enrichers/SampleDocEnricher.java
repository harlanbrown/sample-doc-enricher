package org.nuxeo.sample.enrichers;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;
import static org.nuxeo.ecm.core.io.registry.reflect.Priorities.REFERENCE;

import java.io.IOException;

import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.io.marshallers.json.enrichers.AbstractJsonEnricher;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;

import com.fasterxml.jackson.core.JsonGenerator;

@Setup(mode = SINGLETON, priority = REFERENCE)
public class SampleDocEnricher extends AbstractJsonEnricher<DocumentModel> {

  private static final String NAME = "sample";

  public SampleDocEnricher() {
    super(NAME);
  }

  @Override
  public void write(JsonGenerator jg, DocumentModel doc) throws IOException {
    jg.writeFieldName(NAME);
    jg.writeStartArray();
    jg.writeString("sample");
    jg.writeEndArray();
  }

}
