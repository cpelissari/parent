/*
 * SearchQueryReplyGen.java criado em 28/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import java.util.List;

import br.com.objectos.comuns.relational.search.Page;
import br.com.objectos.comuns.relational.search.SearchQuery;

import com.google.common.base.Function;
import com.google.inject.ImplementedBy;
import com.google.sitebricks.headless.Reply;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@ImplementedBy(SearchQueryReplyGenGuice.class)
public interface SearchQueryReplyGen {

  interface Json {

    int getTotal();

    List<String> getData();

  }

  <T> Reply<?> replyAll(SearchQuery<T> query, Function<T, String> toJson);

  <T> Reply<?> replyPage(SearchQuery<T> query, Page page, Function<T, String> toJson);

}