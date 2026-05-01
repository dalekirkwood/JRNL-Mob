package com.dale.jrnlmob.ui.compose;

import com.dale.jrnlmob.data.location.LocationHelper;
import com.dale.jrnlmob.data.preferences.AppPreferences;
import com.dale.jrnlmob.data.sync.WebDavClient;
import com.dale.jrnlmob.data.weather.WeatherService;
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
public final class ComposeViewModel_Factory implements Factory<ComposeViewModel> {
  private final Provider<JournalRepository> repositoryProvider;

  private final Provider<LocationHelper> locationHelperProvider;

  private final Provider<WeatherService> weatherServiceProvider;

  private final Provider<WebDavClient> webDavClientProvider;

  private final Provider<AppPreferences> preferencesProvider;

  private ComposeViewModel_Factory(Provider<JournalRepository> repositoryProvider,
      Provider<LocationHelper> locationHelperProvider,
      Provider<WeatherService> weatherServiceProvider, Provider<WebDavClient> webDavClientProvider,
      Provider<AppPreferences> preferencesProvider) {
    this.repositoryProvider = repositoryProvider;
    this.locationHelperProvider = locationHelperProvider;
    this.weatherServiceProvider = weatherServiceProvider;
    this.webDavClientProvider = webDavClientProvider;
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public ComposeViewModel get() {
    return newInstance(repositoryProvider.get(), locationHelperProvider.get(), weatherServiceProvider.get(), webDavClientProvider.get(), preferencesProvider.get());
  }

  public static ComposeViewModel_Factory create(Provider<JournalRepository> repositoryProvider,
      Provider<LocationHelper> locationHelperProvider,
      Provider<WeatherService> weatherServiceProvider, Provider<WebDavClient> webDavClientProvider,
      Provider<AppPreferences> preferencesProvider) {
    return new ComposeViewModel_Factory(repositoryProvider, locationHelperProvider, weatherServiceProvider, webDavClientProvider, preferencesProvider);
  }

  public static ComposeViewModel newInstance(JournalRepository repository,
      LocationHelper locationHelper, WeatherService weatherService, WebDavClient webDavClient,
      AppPreferences preferences) {
    return new ComposeViewModel(repository, locationHelper, weatherService, webDavClient, preferences);
  }
}
