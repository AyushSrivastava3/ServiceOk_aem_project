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
public class ContentBlockModel extends BaseImageModel {

    @ValueMapValue
    private String primaryButtonText;

    @ValueMapValue
    private String primaryButtonLink;

    @ValueMapValue
    private String secondaryButtonText;

    @ValueMapValue
    private String secondaryButtonLink;

    @ValueMapValue
    private String linkText;

    @ValueMapValue
    private String link;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean primaryTarget;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean secondaryTarget;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean linkTarget;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean hideImage;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean hidePrimaryButton;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean hideSecondaryButton;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean hideLink;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean hideTitle;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean hideDescription;

    @PostConstruct
    public void init() {
        primaryButtonLink = isNotEmpty(primaryButtonLink) ? appendHtmlToInternalLink(primaryButtonLink) : primaryButtonLink;
        secondaryButtonLink = isNotEmpty(secondaryButtonLink) ? appendHtmlToInternalLink(secondaryButtonLink) : secondaryButtonLink;
        link = isNotEmpty(link) ? appendHtmlToInternalLink(link) : link;
    }

    public String getLink() {
        return link;
    }

    public String getLinkText() {
        return linkText;
    }

    public String getPrimaryButtonLink() {
        return primaryButtonLink;
    }

    public String getPrimaryButtonText() {
        return primaryButtonText;
    }

    public boolean isLinkTarget() {
        return linkTarget;
    }

    public String getSecondaryButtonLink() {
        return secondaryButtonLink;
    }

    public String getSecondaryButtonText() {
        return secondaryButtonText;
    }

    public boolean isPrimaryTarget() {
        return primaryTarget;
    }

    public boolean isSecondaryTarget() {
        return secondaryTarget;
    }

    public boolean isHideDescription() {
        return hideDescription;
    }

    public boolean isHideImage() {
        return hideImage;
    }

    public boolean isHideLink() {
        return hideLink;
    }

    public boolean isHideSecondaryButton() {
        return hideSecondaryButton;
    }

    public boolean isHidePrimaryButton() {
        return hidePrimaryButton;
    }

    public boolean isHideTitle() {
        return hideTitle;
    }
}