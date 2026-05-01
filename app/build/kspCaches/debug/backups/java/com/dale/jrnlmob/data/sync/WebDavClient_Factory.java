package com.dale.jrnlmob.data.sync;

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
public final class WebDavClient_Factory implements Factory<WebDavClient> {
  @Override
  public WebDavClient get() {
    return newInstance();
  }

  public static WebDavClient_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WebDavClient newInstance() {
    return new WebDavClient();
  }

  private static final class InstanceHolder {
    static final WebDavClient_Factory INSTANCE = new WebDavClient_Factory();
  }
}
