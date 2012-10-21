/*
 * AnexoGenGuice.java criado em 06/11/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.objectos.comuns.web.upload.PotentialFile;
import br.com.objectos.comuns.web.upload.UploadRequest;
import br.com.objectos.comuns.web.upload.UploadRequestException;
import br.com.objectos.comuns.web.upload.UploadRequests;
import br.com.objectos.comuns.web.upload.UploadedFile;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class AttachmentsGuice implements Attachments {

  private static final Logger logger = LoggerFactory.getLogger(Attachments.class);

  private final UploadRequests requests;

  private final Pages pages;

  @Inject
  public AttachmentsGuice(UploadRequests requests, Pages pages) {
    this.requests = requests;
    this.pages = pages;
  }

  @Override
  public SaveTo getFromRequest() {
    return new SaveTo() {
      @Override
      public List<Attachment> andSaveTo(File baseDir) {
        UploadRequest request = requests.get();

        Iterable<PotentialFile> files;
        files = request.getPotentialFiles();

        Iterable<Attachment> attachs;
        attachs = Iterables.transform(files, new ToAttachment(baseDir));

        return ImmutableList.copyOf(attachs);
      }
    };
  }

  private class ToAttachment implements Function<PotentialFile, Attachment> {

    private final File baseDir;

    public ToAttachment(File baseDir) {
      this.baseDir = baseDir;
    }

    @Override
    public Attachment apply(PotentialFile input) {
      try {

        UploadedFile file = input.get();
        Attachment attach = new ProtoBuilder(baseDir, file).build();

        UUID uuid = attach.getUuid();
        InputStream in = file.openStream();
        AttachmentIO.write(baseDir, uuid, in);

        return pages.addPagesTo(attach);

      } catch (UploadRequestException e) {

        logger.error("Could not process attachment", e);
        return new InvalidAttachment(e);

      } catch (IOException e) {

        logger.error("Could not save attachment", e);
        return new InvalidAttachment(e);

      }
    }

  }

  private class ProtoBuilder implements Attachment.Builder {

    private final File baseDir;

    private final UploadedFile file;

    public ProtoBuilder(File baseDir, UploadedFile file) {
      this.baseDir = baseDir;
      this.file = file;
    }

    @Override
    public Attachment build() {
      return new ProtoAttachment(this);
    }

    @Override
    public File getBaseDir() {
      return baseDir;
    }

    @Override
    public UUID getUuid() {
      return UUID.randomUUID();
    }

    @Override
    public Mime getMime() {
      return Mime.valueOf(file);
    }

    @Override
    public String getName() {
      return file.getName();
    }

  }

}