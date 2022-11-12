/*
 * Copyright © 2020 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.ghrepostools;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * The string resources.
 */

public final class GHRTStrings
{
  private final ResourceBundle resources;

  private GHRTStrings(
    final ResourceBundle inResources)
  {
    this.resources = Objects.requireNonNull(inResources, "inResources");
  }

  /**
   * Create string resources from the given stream.
   *
   * @param stream The input stream
   *
   * @return The string resources
   */

  public static ResourceBundle ofXML(
    final InputStream stream)
  {
    try {
      return new XMLResourceBundle(
        Objects.requireNonNull(stream, "stream")
      );
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Create string resources from the given class and resource name.
   *
   * @param clazz    The class
   * @param resource The resource name
   *
   * @return The string resources
   */

  public static ResourceBundle ofXMLResource(
    final Class<?> clazz,
    final String resource)
  {
    try (var stream = clazz.getResourceAsStream(resource)) {
      return ofXML(stream);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static final class XMLResourceBundle extends ResourceBundle
  {
    private final Properties props;

    XMLResourceBundle(
      final InputStream stream)
      throws IOException
    {
      this.props = new Properties();
      this.props.loadFromXML(
        Objects.requireNonNull(stream, "stream")
      );
    }

    @Override
    protected Object handleGetObject(
      final String key)
    {
      return this.props.getProperty(
        Objects.requireNonNull(key, "key")
      );
    }

    @Override
    public Enumeration<String> getKeys()
    {
      final Set<String> handleKeys = this.props.stringPropertyNames();
      return Collections.enumeration(handleKeys);
    }
  }
}

