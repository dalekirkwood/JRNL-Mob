package com.dale.jrnlmob.ui.detail;

import com.dale.jrnlmob.domain.repository.JournalRepository;
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
public final class DetailViewModel_Factory implements Factory<DetailViewModel> {
  private final Provider<JournalRepository> repositoryProvider;

  private DetailViewModel_Factory(Provider<JournalRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DetailViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static DetailViewModel_Factory create(Provider<JournalRepository> repositoryProvider) {
    return new DetailViewModel_Factory(repositoryProvider);
  }

  public static DetailViewModel newInstance(JournalRepository repository) {
    return new DetailViewModel(repository);
  }
}
