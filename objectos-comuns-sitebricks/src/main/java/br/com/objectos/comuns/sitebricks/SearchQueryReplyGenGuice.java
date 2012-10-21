/*
 * SearchQueryReplyGenGuice.java criado em 28/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import br.com.objectos.comuns.relational.search.Page;
import br.com.objectos.comuns.relational.search.SearchQuery;
import br.com.objectos.comuns.relational.search.SearchQueryExec;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.sitebricks.headless.Reply;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class SearchQueryReplyGenGuice implements SearchQueryReplyGen {

  private final SearchQueryExec queryExec;

  @Inject
  public SearchQueryReplyGenGuice(SearchQueryExec queryExec) {
    this.queryExec = queryExec;
  }

  @Override
  public <T> Reply<?> replyAll(SearchQuery<T> query, Function<T, String> toJson) {
    int total = queryExec.count(query);

    List<T> resultado = queryExec.list(query);
    List<String> data = transform(resultado, toJson);

    JsonImpl json = new JsonImpl(total, data);

    return Reply.with(json).as(RawJson.class);
  }

  @Override
  public <T> Reply<?> replyPage(SearchQuery<T> query, Page page, Function<T, String> toJson) {
    int total = queryExec.count(query);

    List<T> resultado = queryExec.listPage(query, page);
    List<String> data = transform(resultado, toJson);

    JsonImpl json = new JsonImpl(total, data);

    return Reply.with(json).as(RawJson.class);
  }

  private static class JsonImpl implements Json {

    private final int total;

    private final List<String> data;

    public JsonImpl(int total, List<String> data) {
      this.total = total;
      this.data = data;
    }

    @Override
    public int getTotal() {
      return total;
    }

    @Override
    public List<String> getData() {
      return data;
    }

  }

}