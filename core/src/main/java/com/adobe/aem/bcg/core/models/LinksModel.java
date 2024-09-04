package com.adobe.aem.bcg.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = OPTIONAL)
public class LinksModel {

    @ChildResource
    private List<LinkModel> links;

    @ValueMapValue
    private String linksLabel;

    @PostConstruct
    public void init() {
        links = !isEmpty(links) ? links.stream().filter(Objects::nonNull).collect(Collectors.toList()) : emptyList();
    }

    public List<LinkModel> getLinks() {
        return links;
    }

    public String getLinksLabel() {
        return linksLabel;
    }
}