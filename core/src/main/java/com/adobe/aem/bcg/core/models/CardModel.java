package com.adobe.aem.bcg.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

import static com.adobe.aem.bcg.core.utils.PageUtils.appendHtmlToInternalLink;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class CardModel extends BaseImageModel {

    @ValueMapValue
    private String linkText;

    @ValueMapValue
    private String overLineText;

    @ValueMapValue
    private String link;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean linkTarget;

    @ValueMapValue
    private String cardImage;

    @PostConstruct
    public void init() {
        link = isNotEmpty(link) ? appendHtmlToInternalLink(link) : link;
    }

    public String getLink() {
        return link;
    }

    public String getOverLineText() {
        return overLineText;
    }
    public boolean isLinkTarget() {
        return linkTarget;
    }

    public String getLinkText() {
        return linkText;
    }

    public String getCardImage() {
        return cardImage;
    }
}