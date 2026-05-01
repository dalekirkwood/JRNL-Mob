package com.dale.jrnlmob.di;

import com.dale.jrnlmob.data.repository.JournalRepositoryImpl;
import com.dale.jrnlmob.domain.repository.JournalRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideJournalRepositoryFactory implements Factory<JournalRepository> {
  private final Provider<JournalRepositoryImpl> implProvider;

  private AppModule_ProvideJournalRepositoryFactory(Provider<JournalRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public JournalRepository get() {
    return provideJournalRepository(implProvider.get());
  }

  public static AppModule_ProvideJournalRepositoryFactory create(
      Provider<JournalRepositoryImpl> implProvider) {
    return new AppModule_ProvideJournalRepositoryFactory(implProvider);
  }

  public static JournalRepository provideJournalRepository(JournalRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideJournalRepository(impl));
  }
}
