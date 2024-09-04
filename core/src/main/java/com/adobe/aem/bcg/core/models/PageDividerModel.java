package com.adobe.aem.bcg.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class PageDividerModel extends BaseImageModel {

    @ValueMapValue
    private String headingTag;

    public String getHeadingTag() {
        return isNotBlank(headingTag) ? headingTag : "h2";
    }
}