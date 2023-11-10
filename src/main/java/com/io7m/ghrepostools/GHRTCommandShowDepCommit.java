/*
 * Copyright © 2022 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.io7m.claypot.core.CLPAbstractCommand;
import com.io7m.claypot.core.CLPCommandContextType;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.io7m.claypot.core.CLPCommandType.Status.SUCCESS;
import static java.nio.charset.StandardCharsets.UTF_8;

@Parameters(commandDescription = "Generate a commit message for dependency updates.")
public final class GHRTCommandShowDepCommit extends CLPAbstractCommand
{
  @Parameter(required = false, names = "--output")
  private Path output;

  /**
   * Construct a command.
   *
   * @param inContext The command context
   */

  public GHRTCommandShowDepCommit(
    final CLPCommandContextType inContext)
  {
    super(inContext);
  }

  @Override
  protected Status executeActual()
    throws Exception
  {
    final PrintStream outputWriter;
    if (this.output != null) {
      outputWriter = new PrintStream(Files.newOutputStream(this.output), true, UTF_8);
    } else {
      outputWriter = System.out;
    }

    final var u =
      GHRTCommandShowDepCommit.class.getResource(
        "/com/io7m/ghrepostools/versions-plain.xslt");

    try (var s = u.openStream()) {
      final var transformers =
        TransformerFactory.newInstance();
      final var transformer =
        transformers.newTransformer(new StreamSource(s));
      transformer.transform(
        new StreamSource("target/use-latest-releases.xml"),
        new StreamResult(outputWriter)
      );
    } catch (final Exception e) {
      // Don't care
    }

    try {
      try (var s = u.openStream()) {
        final var transformers =
          TransformerFactory.newInstance();
        final var transformer =
          transformers.newTransformer(new StreamSource(s));
        transformer.transform(
          new StreamSource("target/update-properties.xml"),
          new StreamResult(outputWriter)
        );
      }
    } catch (final Exception e) {
      // Don't care
    }

    System.out.println();
    return SUCCESS;
  }

  @Override
  public String name()
  {
    return "show-dependency-commit";
  }
}
