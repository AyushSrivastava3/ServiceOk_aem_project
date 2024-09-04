package com.adobe.aem.bcg.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ListModel {

    @ChildResource
    private List<LinkModel> links = new ArrayList<>();

    @ValueMapValue
    private String linksLabel;

    public List<LinkModel> getLinks() {
        return links;
    }

    public String getLinksLabel() {
        return linksLabel;
    }
}
