package org.ipo.web.model;

import org.ipo.model.IPOData;

import java.util.Set;

public record DataStore(Set<IPOData> ipoData) {
}
