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

import java.util.List;

import static com.io7m.ghrepostools.GHRTJDKDistribution.TEMURIN;
import static com.io7m.ghrepostools.GHRTJDKCategory.CURRENT;
import static com.io7m.ghrepostools.GHRTJDKCategory.LTS;
import static com.io7m.ghrepostools.GHRTPlatform.LINUX;
import static com.io7m.ghrepostools.GHRTPlatform.WINDOWS;

public final class GHRTWorkflows
{
  public GHRTWorkflows()
  {

  }

  public List<GHRTWorkflow> workflows()
  {
    return List.of(
      new GHRTWorkflow(
        LINUX,
        TEMURIN,
        CURRENT,
        19,
        false
      ),
      new GHRTWorkflow(
        LINUX,
        TEMURIN,
        LTS,
        17,
        true
      ),
      new GHRTWorkflow(
        WINDOWS,
        TEMURIN,
        CURRENT,
        19,
        false
      ),
      new GHRTWorkflow(
        WINDOWS,
        TEMURIN,
        LTS,
        17,
        false
      )
    );
  }
}
