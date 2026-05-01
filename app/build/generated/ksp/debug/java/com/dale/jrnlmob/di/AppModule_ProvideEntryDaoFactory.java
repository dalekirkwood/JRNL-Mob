package com.dale.jrnlmob.di;

import com.dale.jrnlmob.data.local.JrnlMobDatabase;
import com.dale.jrnlmob.data.local.dao.EntryDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideEntryDaoFactory implements Factory<EntryDao> {
  private final Provider<JrnlMobDatabase> databaseProvider;

  private AppModule_ProvideEntryDaoFactory(Provider<JrnlMobDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public EntryDao get() {
    return provideEntryDao(databaseProvider.get());
  }

  public static AppModule_ProvideEntryDaoFactory create(
      Provider<JrnlMobDatabase> databaseProvider) {
    return new AppModule_ProvideEntryDaoFactory(databaseProvider);
  }

  public static EntryDao provideEntryDao(JrnlMobDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideEntryDao(database));
  }
}
