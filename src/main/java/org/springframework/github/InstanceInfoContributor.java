package org.springframework.github;

import java.util.Collections;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
class InstanceInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("cf_instance",
                Collections.singletonMap("id", System.getenv("INSTANCE_GUID")));
    }

}