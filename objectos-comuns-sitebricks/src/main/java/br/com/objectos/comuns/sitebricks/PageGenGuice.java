/*
 * PageGenGuice.java criado em 17/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Lists.transform;

import java.io.StringWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.com.objectos.comuns.sitebricks.MetaPageBuilder.Display;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.jatl.Html;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class PageGenGuice implements PageGen {

  private final BaseUrl baseUrl;

  @Inject
  public PageGenGuice(BaseUrl baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Override
  public Page get(MetaPageScript script) {
    RecordingPageMetaBuilder builder = new RecordingPageMetaBuilder();
    script.configure(builder);

    List<Display> parts = builder.getParts();

    return new PageBuilder(parts).get();
  }

  private class RecordingPageMetaBuilder implements MetaPageBuilder {

    private final List<Display> parts = newArrayList();

    @Override
    public List<Display> getElements() {
      return parts;
    }

    @Override
    public Display display(String title) {
      DisplayImpl impl = new DisplayImpl(title);
      parts.add(impl);
      return impl;
    }

    @Override
    public void install(MetaPageScript script) {
      script.configure(this);
    }

    public List<Display> getParts() {
      List<Display> tmp = newArrayList();

      tmp.addAll(parts);

      return ImmutableList.copyOf(tmp);
    }

  }

  private class DisplayImpl implements Display {

    private final Object title;

    private String url;

    public DisplayImpl(String title) {
      this.title = title;
    }

    @Override
    public void onClick(Object... parts) {
      List<Object> list = newArrayList();
      list.addAll(ImmutableList.copyOf(parts));
      url = Joiner.on("/").skipNulls().join(list);
    }

    @Override
    public String getTitle() {
      String result = title.toString();

      return result;
    }

    @Override
    public String getUrl() {
      String base = baseUrl.get();
      return base + "/" + url;
    }

    @Override
    public String toString() {
      return url;
    }

  }

  private class PageBuilder implements Supplier<Page> {

    private final LinkedList<Display> parts;

    private final List<Anchor> anchors;

    public PageBuilder(List<Display> parts) {
      this.parts = newLinkedList(parts);
      List<Anchor> anchors = transform(parts, new ToAnchor());
      this.anchors = ImmutableList.copyOf(anchors);
    }

    @Override
    public Page get() {
      String title = getTitle();
      String breadcrumbs = getBreadcrumbs();
      String url = getUrl();

      return new PageImpl(anchors, title, breadcrumbs, url);
    }

    private String getTitle() {
      List<String> temp = transform(parts, new ToTitle());
      List<String> titles = newArrayList(temp);
      Collections.reverse(titles);
      return Joiner.on(" < ").skipNulls().join(titles);
    }

    private String getBreadcrumbs() {
      final int size = anchors.size();

      StringWriter writer = new StringWriter();
      new Html(writer) {
        {
          ul().classAttr("breadcrumb");
          for (int i = 0; i < size; i++) {
            Anchor anchor = anchors.get(i);
            li();

            /**/a().attr("data-noxhr", "").href(anchor.getUrl()).text(anchor.getText()).end();
            if (i + 1 < size) {
              /**/span().classAttr("divider").text(">").end();
            }

            end();
          }
          endAll();
        }
      };
      return writer.toString();
    }

    private String getUrl() {
      Display last = parts.getLast();
      return last.getUrl();
    }

  }

  private class ToTitle implements Function<Display, String> {
    @Override
    public String apply(Display input) {
      Object title = input.getTitle();
      String result = title.toString();

      return result;
    }
  }

  private class ToAnchor implements Function<Display, Anchor> {

    private boolean first = true;

    @Override
    public Anchor apply(Display input) {
      String url = input.getUrl();
      String text = input.getTitle();

      AnchorImpl res = new AnchorImpl(url, text);

      if (first) {
        res = new AnchorImpl.First(url, text);
        first = false;
      }

      return res;
    }

  }

  private static class PageImpl implements Page {

    private final List<Anchor> anchors;

    private final String title;

    private final String breadcrumbs;

    private final String url;

    public PageImpl(List<Anchor> anchors, String title, String breadcrumbs, String url) {
      this.anchors = anchors;
      this.title = title;
      this.breadcrumbs = breadcrumbs;
      this.url = url;
    }

    @Override
    public List<Anchor> getAnchors() {
      return anchors;
    }

    @Override
    public String getTitle() {
      return title;
    }

    @Override
    public String getBreadcrumbs() {
      return breadcrumbs;
    }

    @Override
    public String getUrl() {
      return url;
    }

  }

}