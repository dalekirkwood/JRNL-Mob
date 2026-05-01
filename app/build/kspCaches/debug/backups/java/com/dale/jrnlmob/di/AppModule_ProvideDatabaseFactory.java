package com.dale.jrnlmob.di;

import android.content.Context;
import com.dale.jrnlmob.data.local.JrnlMobDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideDatabaseFactory implements Factory<JrnlMobDatabase> {
  private final Provider<Context> contextProvider;

  private AppModule_ProvideDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public JrnlMobDatabase get() {
    return provideDatabase(contextProvider.get());
  }

  public static AppModule_ProvideDatabaseFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideDatabaseFactory(contextProvider);
  }

  public static JrnlMobDatabase provideDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDatabase(context));
  }
}
