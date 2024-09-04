package com.adobe.aem.bcg.core.models;

import com.adobe.cq.wcm.core.components.models.Image;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class BaseImageModel implements ImageModel {

    @Self
    @Via(type = ResourceSuperType.class)
    private Image image;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String alt;

    @ValueMapValue
    private boolean isDecorative;

    @ValueMapValue
    private String fileReferenceMobile;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getImage() {
        return image.getSrc();
    }

    @Override
    public String getAltText() {
        return isNotBlank(alt) ? alt : image.getAlt();
    }

    @Override
    public boolean isImageEmpty() {
        return isEmpty(image.getFileReference());
    }

    public boolean isImageDecorativeType() {
        return isDecorative;
    }

    @Override
    public String getMobileImage() {
        return fileReferenceMobile;
    }

}
