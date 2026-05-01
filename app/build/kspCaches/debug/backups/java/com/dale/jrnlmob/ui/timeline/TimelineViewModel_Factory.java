package com.dale.jrnlmob.ui.timeline;

import com.dale.jrnlmob.data.preferences.AppPreferences;
import com.dale.jrnlmob.data.sync.WebDavClient;
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
public final class TimelineViewModel_Factory implements Factory<TimelineViewModel> {
  private final Provider<JournalRepository> repositoryProvider;

  private final Provider<WebDavClient> webDavClientProvider;

  private final Provider<AppPreferences> preferencesProvider;

  private TimelineViewModel_Factory(Provider<JournalRepository> repositoryProvider,
      Provider<WebDavClient> webDavClientProvider, Provider<AppPreferences> preferencesProvider) {
    this.repositoryProvider = repositoryProvider;
    this.webDavClientProvider = webDavClientProvider;
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public TimelineViewModel get() {
    return newInstance(repositoryProvider.get(), webDavClientProvider.get(), preferencesProvider.get());
  }

  public static TimelineViewModel_Factory create(Provider<JournalRepository> repositoryProvider,
      Provider<WebDavClient> webDavClientProvider, Provider<AppPreferences> preferencesProvider) {
    return new TimelineViewModel_Factory(repositoryProvider, webDavClientProvider, preferencesProvider);
  }

  public static TimelineViewModel newInstance(JournalRepository repository,
      WebDavClient webDavClient, AppPreferences preferences) {
    return new TimelineViewModel(repository, webDavClient, preferences);
  }
}
