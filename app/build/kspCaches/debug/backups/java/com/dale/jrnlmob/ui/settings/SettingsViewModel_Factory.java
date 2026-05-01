package com.dale.jrnlmob.ui.settings;

import com.dale.jrnlmob.data.preferences.AppPreferences;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<AppPreferences> preferencesProvider;

  private SettingsViewModel_Factory(Provider<AppPreferences> preferencesProvider) {
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(preferencesProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<AppPreferences> preferencesProvider) {
    return new SettingsViewModel_Factory(preferencesProvider);
  }

  public static SettingsViewModel newInstance(AppPreferences preferences) {
    return new SettingsViewModel(preferences);
  }
}
