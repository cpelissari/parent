/*
 * CacheStatsWrapper.java criado em 04/06/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.base.cache;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class CacheStatsWrapper {

  private final String description;

  private final CacheStats stats;

  CacheStatsWrapper(String description, Cache<?, ?> cache) {
    this.description = description;
    this.stats = cache.stats();
  }

  public static ListBuilder listBuilder() {
    return new ListBuilder();
  }

  public String getDescription() {
    return description;
  }

  public CacheStats getStats() {
    return stats;
  }

  public static class ListBuilder {

    private final ImmutableList.Builder<CacheStatsWrapper> wrappers = ImmutableList.builder();

    public ListBuilder add(Cache<?, ?> cache, String description) {
      Preconditions.checkNotNull(cache);
      Preconditions.checkNotNull(description);
      wrappers.add(new CacheStatsWrapper(description, cache));
      return this;
    }

    public List<CacheStatsWrapper> build() {
      return wrappers.build();
    }

  }

}