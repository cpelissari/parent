/*
 * MimeConvPdf.java criado em 16/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import br.com.objectos.comuns.base.Construtor;

import com.google.common.base.Preconditions;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class MimeConvPdf implements MimeConv {

  private final Attachment attach;

  public MimeConvPdf(Attachment attach) {
    Mime mime = attach.getMime();
    Preconditions.checkArgument(mime.equals(Mime.PDF));
    this.attach = attach;
  }

  @Override
  public List<AttachmentPage> extract() {
    FileChannel channel = null;

    try {

      channel = openFileChannel();

      PDFFile pdf = openPdfFile(channel);

      List<AttachmentPage> res = openAllPages(pdf);

      return res;

    } catch (IOException e) {
      throw new AttachmentException(
          "Could not open or convert the PDF " + attach.getUuid(), e);
    } finally {
      if (channel != null) {
        Closeables.closeQuietly(channel);
      }
    }
  }

  private FileChannel openFileChannel() throws IOException {
    File pdfFile = attach.getFile();

    InputSupplier<FileInputStream> supplier = Files.newInputStreamSupplier(pdfFile);
    FileInputStream stream = supplier.getInput();

    return stream.getChannel();
  }

  private PDFFile openPdfFile(FileChannel channel) throws IOException {
    ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
    return new PDFFile(buf);
  }

  private List<AttachmentPage> openAllPages(PDFFile pdf) throws IOException {
    int numPages = pdf.getNumPages();
    List<AttachmentPage> res = newArrayListWithCapacity(numPages);

    for (int i = 1; i <= pdf.getNumPages(); i++) {
      PDFPage page = pdf.getPage(i);

      AttachmentPage attachPage = toAttachmentPage(page);

      res.add(attachPage);
    }

    return res;
  }

  private AttachmentPage toAttachmentPage(PDFPage page) throws IOException {

    AttachmentPage attachPage = new PageBuilder(page).novaInstancia();

    Rectangle2D box = page.getBBox();

    int width = (int) box.getWidth();
    int height = (int) box.getHeight();

    Dimension sz = page.getUnstretchedSize(width * 2, height * 2, null);

    Image image = page.getImage(sz.width, sz.height, box, null, true, true);

    BufferedImage bufferedImage = new BufferedImage(sz.width, sz.height, BufferedImage.TYPE_INT_RGB);
    Graphics2D bufImageGraphics = bufferedImage.createGraphics();
    bufImageGraphics.drawImage(image, 0, 0, null);

    ImageIO.write(bufferedImage, "png", attachPage.getFile());

    return attachPage;

  }

  private class PageBuilder implements AttachmentPage.Builder, Construtor<AttachmentPage> {

    private final PDFPage page;

    public PageBuilder(PDFPage page) {
      this.page = page;
    }

    @Override
    public AttachmentPage novaInstancia() {
      return new AttachmentPageImpl(this);
    }

    @Override
    public String getBaseDir() {
      return attach.getBaseDir();
    }

    @Override
    public UUID getUuid() {
      return attach.getUuid();
    }

    @Override
    public int getNumber() {
      return page.getPageNumber();
    }

  }

}