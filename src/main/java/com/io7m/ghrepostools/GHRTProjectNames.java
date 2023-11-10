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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GHRTProjectNames
{
  private GHRTProjectNames()
  {

  }

  public static GHRTProjectName projectName()
    throws Exception
  {
    final var document = parsePOM();

    final var pomName =
      retrieveOne(document, "/project/name")
        .getTextContent()
        .trim();

    final var groupName =
      Arrays.stream(retrieveOne(document, "/project/groupId")
        .getTextContent()
        .trim()
        .split("\\."))
        .toList();

    final var shortName =
      pomName.replaceFirst("com\\.io7m\\.", "");

    return new GHRTProjectName(
      pomName,
      groupName,
      shortName
    );
  }

  private static List<Element> retrieve(
    final Document document,
    final String expression)
    throws Exception
  {
    final var xPath = XPathFactory.newInstance().newXPath();
    final var nodeList = (NodeList) xPath.evaluate(
      expression,
      document,
      XPathConstants.NODESET);
    final var elements = new ArrayList<Element>(nodeList.getLength());
    for (var index = 0; index < nodeList.getLength(); ++index) {
      elements.add((Element) nodeList.item(index));
    }
    return List.copyOf(elements);
  }

  private static Element retrieveOne(
    final Document document,
    final String expression)
    throws Exception
  {
    final var retrieve = retrieve(document, expression);
    if (retrieve.isEmpty()) {
      throw new IllegalStateException("No elements returned for expression: " + expression);
    }
    return retrieve.get(0);
  }

  private static Document parsePOM()
    throws Exception
  {
    final var documentBuilders = DocumentBuilderFactory.newInstance();
    final var documentBuilder = documentBuilders.newDocumentBuilder();
    return documentBuilder.parse(new File("pom.xml"));
  }
}
