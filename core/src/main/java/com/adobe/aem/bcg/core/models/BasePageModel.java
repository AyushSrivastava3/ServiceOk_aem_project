package com.adobe.aem.bcg.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Named;

import static com.day.cq.commons.jcr.JcrConstants.JCR_DESCRIPTION;
import static com.day.cq.commons.jcr.JcrConstants.JCR_TITLE;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;


@Model(adaptables = Resource.class, adapters = PageModel.class, defaultInjectionStrategy = OPTIONAL)
public class BasePageModel implements PageModel {

    @SlingObject
    private Resource resource;

    @ValueMapValue
    @Named(JCR_TITLE)
    private String title;

    @ValueMapValue
    @Named(JCR_DESCRIPTION)
    private String description;

    @ValueMapValue
    private String navTitle;
    @ValueMapValue
    private String pageTitle;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public String getNavTitle() {
        return navTitle;
    }

    @Override
    public String getPath() {
        return resource.getPath();
    }

    @Override
    public String getPageTitle() {
        return pageTitle;
    }
}
