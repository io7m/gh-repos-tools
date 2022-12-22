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

import com.beust.jcommander.Parameters;
import com.io7m.claypot.core.CLPAbstractCommand;
import com.io7m.claypot.core.CLPCommandContextType;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

import static com.io7m.claypot.core.CLPCommandType.Status.SUCCESS;

@Parameters(commandDescription = "Generate a README file.")
public final class GHRTCommandReadme extends CLPAbstractCommand
{
  /**
   * Construct a command.
   *
   * @param inContext The command context
   */

  public GHRTCommandReadme(
    final CLPCommandContextType inContext)
  {
    super(inContext);
  }

  @Override
  protected Status executeActual()
    throws Exception
  {
    final var names =
      GHRTProjectNames.projectName();

    final var resources =
      GHRTStrings.ofXMLResource(
        GHRTCommandReadme.class,
        "/com/io7m/ghrepostools/Strings.xml");

    System.out.println(MessageFormat.format(
      resources.getString("readmeTemplate"),
      names.projectName(),
      names.shortName()
    ));

    final var workflows = new GHRTWorkflows().workflows();
    for (final var workflow : workflows) {
      final var row = new StringBuilder();
      row.append(
        "| OpenJDK (%s) %s | %s | "
          .formatted(
            workflow.jdkDistribution().humanName(),
            workflow.jdkCategory().humanName(),
            workflow.platform().humanName()
          )
      );
      row.append(
        "[![Build (OpenJDK (%s) %s, %s)](%s)](%s)|"
          .formatted(
            workflow.jdkDistribution().humanName(),
            workflow.jdkCategory().humanName(),
            workflow.platform().humanName(),
            shieldsURI(names, workflow),
            workflowURI(names, workflow)
          )
      );

      System.out.println(row);
    }

    final var extra = Paths.get("README.in");
    if (Files.isRegularFile(extra)) {
      System.out.println(Files.readString(extra));
    }

    return SUCCESS;
  }

  private static URI workflowURI(
    final GHRTProjectName names,
    final GHRTWorkflow workflow)
  {
    return URI.create(
      "https://github.com/io7m/%s/actions?query=workflow%%3A%s"
        .formatted(names.shortName(), workflow.name())
    );
  }

  private static URI shieldsURI(
    final GHRTProjectName names,
    final GHRTWorkflow workflow)
  {
    return URI.create(
      "https://img.shields.io/github/actions/workflow/status/io7m/%s/%s.yml"
        .formatted(names.shortName(), workflow.name())
    );
  }

  @Override
  public String name()
  {
    return "readme";
  }
}
