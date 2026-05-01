package com.dale.jrnlmob.data.repository;

import com.dale.jrnlmob.data.local.dao.EntryDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class JournalRepositoryImpl_Factory implements Factory<JournalRepositoryImpl> {
  private final Provider<EntryDao> entryDaoProvider;

  private JournalRepositoryImpl_Factory(Provider<EntryDao> entryDaoProvider) {
    this.entryDaoProvider = entryDaoProvider;
  }

  @Override
  public JournalRepositoryImpl get() {
    return newInstance(entryDaoProvider.get());
  }

  public static JournalRepositoryImpl_Factory create(Provider<EntryDao> entryDaoProvider) {
    return new JournalRepositoryImpl_Factory(entryDaoProvider);
  }

  public static JournalRepositoryImpl newInstance(EntryDao entryDao) {
    return new JournalRepositoryImpl(entryDao);
  }
}
