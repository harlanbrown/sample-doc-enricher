package org.nuxeo.sample.enrichers;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;
import static org.nuxeo.ecm.core.io.registry.reflect.Priorities.REFERENCE;

import java.io.IOException;

import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.io.marshallers.json.enrichers.AbstractJsonEnricher;
import org.nuxeo.ecm.core.io.registry.context.RenderingContext.SessionWrapper;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;
import org.nuxeo.ecm.core.query.sql.NXQL;

import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.blob.BlobInfo;

import com.fasterxml.jackson.core.JsonGenerator;

@Setup(mode = SINGLETON, priority = REFERENCE)
public class SampleDocEnricher extends AbstractJsonEnricher<DocumentModel> {

  private static final String NAME = "blobInMoreThanOneDocument";

  public static final String FETCH_BLOB_QUERY = "SELECT * FROM Document WHERE file:content/digest = %s";

  public SampleDocEnricher() {
    super(NAME);
  }

  @Override
  public void write(JsonGenerator jg, DocumentModel doc) throws IOException {
    if ( doc.getProperty("file:content") != null ){
        jg.writeBooleanField(NAME, check(doc));
    }
  }
  
  private boolean check(DocumentModel doc) throws IOException {
      Blob b = (Blob) doc.getPropertyValue("file:content");
      String digest = b.getDigest();
      try (SessionWrapper wrapper = ctx.getSession(doc)){
          return wrapper.getSession()
                        .queryProjection(String.format(FETCH_BLOB_QUERY, NXQL.escapeString(b.getDigest())), 2, 0) // limit 2
                        .size() > 1; // greater than one = found more than one doc that has this digest
      }
  }

}
