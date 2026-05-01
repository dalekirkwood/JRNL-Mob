package com.dale.jrnlmob.data.weather;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class WeatherService_Factory implements Factory<WeatherService> {
  @Override
  public WeatherService get() {
    return newInstance();
  }

  public static WeatherService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WeatherService newInstance() {
    return new WeatherService();
  }

  private static final class InstanceHolder {
    static final WeatherService_Factory INSTANCE = new WeatherService_Factory();
  }
}
